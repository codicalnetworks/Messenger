package com.codicalnetworks.messenger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mUserDatabase;
    private EditText mLoginEmail;
    private EditText mLoginPassword;
    private Button mLoginButton;

    private String email;
    private String password;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Firebase references
        mAuth = FirebaseAuth.getInstance();



        // Widget references
        mLoginEmail = (EditText) findViewById(R.id.login_email);
        mLoginPassword = (EditText) findViewById(R.id.login_password);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mProgressDialog = new ProgressDialog(this);


        // Get texts from both email and password input
        email = mLoginEmail.getText().toString();
        password = mLoginPassword.getText().toString();



        // Login button click listener
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    login(email, password);
                } else {
                    Toast.makeText(LoginActivity.this, "Check your input and try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /* This is the login method that allows users to sign into the application */

    private void login(String email, String password) {
        mProgressDialog.setMessage("Validating user...");
        mProgressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mProgressDialog.dismiss();
                            MainIntent();
                            finish();
                            Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                        } else {
                            mProgressDialog.hide();
                            Toast.makeText(LoginActivity.this, "Operation not successful, try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }


    /* This is the method that sends the activity to the main Activity */
    private void MainIntent() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }


}
