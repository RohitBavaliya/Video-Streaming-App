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
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    TextInputLayout name, email, password;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pwd);

        progressBar = findViewById(R.id.progressBar);


    }

    public void signuphere(View view) {
        progressBar.setVisibility(View.VISIBLE);
        String regName = name.getEditText().getText().toString();
        String regEmail = email.getEditText().getText().toString();
        String regPassword = password.getEditText().getText().toString();


        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(regEmail,regPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        progressBar.setVisibility(View.INVISIBLE);
                        email.getEditText().setText("");
                        password.getEditText().setText("");
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Registration Successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Registration Fail!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void goToLogin(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}