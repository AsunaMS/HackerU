package com.mikiandor.welearn.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikiandor.welearn.Auth.Auth_FireBase;
import com.mikiandor.welearn.Interfaces.Auth_Interfaces;
import com.mikiandor.welearn.R;

public class Activity_Login extends AppCompatActivity {
   private EditText login_EDT_username;
   private EditText login_EDT_password;
   private Button login_EDT_register;
   private Button login_EDT_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIfUserLogedIn();
        setContentView(R.layout.activity_login);
        findViews();
        setupListeners();
        Log.d("mikior", "onCreate: ");
        //mDatabase = FirebaseDatabase.getInstance().getReference();




    }

    private void checkIfUserLogedIn() {
        if(Auth_FireBase.getCurrentUserId()!=null)
            openHomePage();
    }

    private void findViews() {
        login_EDT_username = findViewById(R.id.login_EDT_username);
        login_EDT_password = findViewById(R.id.login_EDT_password);
        login_EDT_register = findViewById(R.id.login_EDT_register);
        login_EDT_login = findViewById(R.id.login_EDT_login);
    }
    private void setupListeners() {
        login_EDT_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkUsername();
                Auth_FireBase.loginUser(login_EDT_username.getText().toString(), login_EDT_password.getText().toString(), new Auth_Interfaces.Login_callBack() {
                    @Override
                    public void success() {
                        loginSuccess();
                    }

                    @Override
                    public void failed(String message) {
                        loginFailed( message);
                    }
                });
            }
        });

        login_EDT_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Activity_Login.this, Activity_Register.class);
                startActivity(i);
            }
        });
    }

    private void loginFailed(String message) {
        Toast.makeText(this,"can't login!",Toast.LENGTH_SHORT).show();

    }

    private void loginSuccess() {
        Toast.makeText(this,"login success!",Toast.LENGTH_SHORT).show();
        openHomePage();

    }

    private void openHomePage() {
        Intent i=new Intent(this,Activity_homePage.class);
        startActivity(i);
        this.finish();
    }


    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    void checkUsername(){
        boolean isVaild=true;
        if(isEmpty(login_EDT_username)){
            login_EDT_username.setError("you must enter username to login");
            isVaild=false;
        }
        else{
            if (!isEmail(login_EDT_username)){
                login_EDT_username.setError("enter valid email!");
                isVaild=false;
            }
        }
        if (isVaild){
            String usernameValue= login_EDT_username.getText().toString();
            String passwordValue= login_EDT_password.getText().toString();
            if (usernameValue.equals("test@test.com")&&passwordValue.equals("password1234"))
            {
                Intent i=new Intent(Activity_Login.this,Activity_Login.class);
                startActivity(i);
                this.finish();
            }else{
                Toast t=Toast.makeText(this,"worng email or password",Toast.LENGTH_SHORT);
                t.show();
            }
        }

    }


    public void onClickResetPassword(View view) {
        if(!checkResetPassword())
            return;
        Auth_FireBase.resetPassword(login_EDT_username.getText().toString(), new Auth_FireBase.CallBack_resetPassword() {
            @Override
            public void onSuccess() {
                Toast.makeText(Activity_Login.this, "mail was sent successfully!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure() {
                Toast.makeText(Activity_Login.this, "can't find mail!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean checkResetPassword() {
        if(login_EDT_username.getText().toString().isEmpty()) {
            Toast.makeText(Activity_Login.this, "fill mail!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}