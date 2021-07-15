package com.example.videostreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout userEmail, userPassword;
    ProgressBar loginProgressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPwd);

        loginProgressBar = findViewById(R.id.loginProgressBar);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null)
        {
            startActivity(new Intent(getApplicationContext(),DashBoard.class));
            finish();
        }
    }

    public void login(View view) {
        loginProgressBar.setVisibility(View.VISIBLE);
        String logEmail = userEmail.getEditText().getText().toString();
        String logPassword = userPassword.getEditText().getText().toString();
        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(logEmail,logPassword)
                .addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        loginProgressBar.setVisibility(View.INVISIBLE);
                        userEmail.getEditText().setText("");
                        userPassword.getEditText().setText("");
                        if (task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this, "Login Successfull..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),DashBoard.class));
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Invalid Email or Password!! ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    public void gotoRegister(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}