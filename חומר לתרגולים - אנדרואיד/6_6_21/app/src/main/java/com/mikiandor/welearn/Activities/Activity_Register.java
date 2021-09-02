package com.mikiandor.welearn.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mikiandor.welearn.Auth.Auth_FireBase;
import com.mikiandor.welearn.Data.FireBase_RealTime;
import com.mikiandor.welearn.Interfaces.Auth_Interfaces;
import com.mikiandor.welearn.Objects.User;
import com.mikiandor.welearn.R;

public class Activity_Register extends AppCompatActivity {

    private EditText register_EDT_firstName;
    private EditText register_EDT_lastName;
    private EditText register_EDT_email;
    private EditText register_EDT_password;
    private Button register_BTN_register;
    private Button register_BTN_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViews();
        initViews();

    }

    private void findViews() {
        register_EDT_firstName = findViewById(R.id.register_EDT_firstName);
        register_EDT_lastName = findViewById(R.id.register_EDT_lastName);
        register_EDT_email = findViewById(R.id.register_EDT_email);
        register_EDT_password = findViewById(R.id.register_EDT_password);
        register_BTN_register = findViewById(R.id.register_BTN_register);
        register_BTN_login =findViewById(R.id.register_BTN_login);
    }

    private void initViews() {

        register_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Activity_Register.this, Activity_Login.class);
                startActivity(i);
                finish();
            }
        });
        register_BTN_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkDataEntered())
                    Auth_FireBase.createUser(register_EDT_email.getText().toString(),register_EDT_password.getText().toString(), new Auth_Interfaces.CreateUser_callBack() {
                        @Override
                        public void success() {
                            String name = register_EDT_firstName.getText().toString().trim() +" "+ register_EDT_lastName.getText().toString().trim();
                            name.trim();
                            User newUser = new User(Auth_FireBase.getCurrentUserId(),register_EDT_email.getText().toString(),name);
                            FireBase_RealTime.saveNewUser(newUser, new FireBase_RealTime.CallBack_save() {
                                @Override
                                public void onSuccess() {
                                    successRegister();
                                }

                                @Override
                                public void onFailure() {

                                }
                            });

                        }

                        @Override
                        public void failed(String message) {
                            failedRegister(message);

                        }
                    });

            }
        });
    }

    private void failedRegister(String message) {
        Toast t=Toast.makeText(this,message,Toast.LENGTH_SHORT);
        t.show();
    }

    private void successRegister() {
    Toast.makeText(this, "successful registerd!", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, Activity_homePage.class);
        startActivity(i);
        finish();
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

   private boolean checkDataEntered() {
        if (isEmpty(register_EDT_firstName)) {
            Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        if (isEmpty(register_EDT_lastName)) {
            register_EDT_lastName.setError("Last name is required!");
            return false;

        }

        if (isEmail(register_EDT_email) == false) {
            register_EDT_email.setError("Enter valid email!");
            return false;

        }
        return true;

    }
}
