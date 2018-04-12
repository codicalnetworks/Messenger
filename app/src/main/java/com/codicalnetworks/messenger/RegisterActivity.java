package com.codicalnetworks.messenger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabase;

    private EditText mRegisterEmail;
    private EditText mRegisterPassword;
    private EditText mRegisterName;
    private Button mRegisterButton;
    private ProgressDialog mProgressDialog;

    private String email;
    private String password;
    private String name;
    private String userId;

    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Firebase reference
        mAuth = FirebaseAuth.getInstance();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


        // Widget reference
        mRegisterName = (EditText) findViewById(R.id.register_name);
        mRegisterEmail = (EditText) findViewById(R.id.register_email);
        mRegisterPassword = (EditText) findViewById(R.id.register_password);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mProgressDialog = new ProgressDialog(this);

        name = mRegisterName.getText().toString();
        email = mRegisterEmail.getText().toString();
        password = mRegisterPassword.getText().toString();

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    registerAccount(name, email, password);
                } else {
                    Toast.makeText(RegisterActivity.this, "Check your input and try again", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void registerAccount(final String name, String email, String password) {
        mProgressDialog.setMessage("Creating Account....");
        mProgressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            registerMap(name);
                            mProgressDialog.dismiss();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            afterOperation();

                        } else {
                            mProgressDialog.hide();
                            Snackbar snackbar2 = Snackbar
                                    .make(view, "Check your details and try again", Snackbar.LENGTH_LONG);
                            snackbar2.show();
                        }
                    }
                });
    }

    private void afterOperation() {
        Snackbar snackbar = Snackbar
                .make(view, "Account created successfully", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void registerMap(String name) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);

        if (mAuth.getUid() != null) {
            userId = mAuth.getUid();
        }

        mUsersDatabase.push().child(userId).setValue(map).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        } else {
                            Toast.makeText(RegisterActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}
