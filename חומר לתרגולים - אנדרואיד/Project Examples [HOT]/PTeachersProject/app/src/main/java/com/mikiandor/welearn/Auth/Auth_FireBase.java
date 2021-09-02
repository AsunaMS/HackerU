package com.mikiandor.welearn.Auth;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikiandor.welearn.Interfaces.Auth_Interfaces;

import java.util.concurrent.Executor;

public class Auth_FireBase {



    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public interface CallBack_resetPassword
    {
        void onSuccess();
        void onFailure();
    }

    public static void createUser( String email, String password, Auth_Interfaces.CreateUser_callBack createUser_callBack)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("mikior_createUser", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            createUser_callBack.success();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("mikior_createUser", "Authentication failed");
                            createUser_callBack.failed("can't create new user");

                        }
                    }
                });
    }
    public static String getCurrentUserId()
    {
        return mAuth.getUid();
    }

    public static void logOut()
    {
         mAuth.signOut();
    }


    public static void loginUser( String email, String password, Auth_Interfaces.Login_callBack login_callBack)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("mikior_loginUser", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            login_callBack.success();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("mikior_loginUser", "Authentication failed");
                            login_callBack.failed("can't login");

                        }
                    }
                });
    }

    public static void resetPassword(String emailAddress,CallBack_resetPassword callBack_resetPassword)
    {

        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // do something when mail was sent successfully.
                            callBack_resetPassword.onSuccess();
                        } else {
                            callBack_resetPassword.onFailure();
                        }
                    }
                });
    }




}
