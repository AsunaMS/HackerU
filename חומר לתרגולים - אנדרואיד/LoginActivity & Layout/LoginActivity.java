package com.example.loginproject;

import android.content.Intent;
import android.os.Bundle;

import com.example.loginproject.database.UserViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class LoginActivity extends AppCompatActivity {
    UserViewModel viewModel;
    EditText etEmail;
    EditText etPassword;
    Button btnRegister;
    Button btnLogin;
    static int PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(UserViewModel.class);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btnRegister.setOnClickListener((v) -> {
            if (getEmail().length() < 1 || getPassword().length() < 1) {
                Toast.makeText(LoginActivity.this, "Please fill credentials to register", Toast.LENGTH_LONG).show();
            } else if (getPassword().length() < PASSWORD_LENGTH) {
                Toast.makeText(LoginActivity.this, "Password should contain atleast 6 figures", Toast.LENGTH_LONG).show();
            } else {
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(getEmail(), getPassword())
                        .addOnSuccessListener(this, successListener)
                        .addOnFailureListener(this, failureListener);
            }
        });

        btnLogin.setOnClickListener(v -> {
            if (getEmail().length() < 1 || getPassword().length() < 1) {
                Toast.makeText(LoginActivity.this, "Please fill credentials to login", Toast.LENGTH_LONG).show();
            } else {
                FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(getEmail(), getPassword())
                        .addOnSuccessListener(this, successListener)
                        .addOnFailureListener(this, failureListener);

            }

        });
    }

    private String getEmail() {
        return etEmail.getText().toString();
    }

    private String getPassword() {
        return etPassword.getText().toString();
    }

    OnSuccessListener<AuthResult> successListener = authResult -> {
        //onSuccess
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    };

    OnFailureListener failureListener = e -> {
        //use a dialog instead:
        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

  
}