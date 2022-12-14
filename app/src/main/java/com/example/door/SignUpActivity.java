package com.example.door;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    TextView backTxt;
    RelativeLayout registerLyt;

    protected EditText emailEdt;
    protected EditText passEdt;
    protected EditText firstEdt;
    protected EditText lastEdt;
    protected EditText phoneEdt;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        backTxt = findViewById(R.id.backTxt);
        backTxt.setOnClickListener(this);

        registerLyt = findViewById(R.id.registerLyt);
        registerLyt.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        emailEdt = findViewById(R.id.emailEdt);
        passEdt = findViewById(R.id.passEdt);
        firstEdt = findViewById(R.id.firstEdt);
        lastEdt = findViewById(R.id.lastEdt);
        phoneEdt = findViewById(R.id.phoneEdt);
    }

    @Override
    public void onClick(View v) {

        if (v == backTxt){

            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        }

        if (v == registerLyt){
            registerNewUser();
        }
    }

    private void registerNewUser() {
        String email, password, firstName, lastName, phoneNum;
        email = emailEdt.getText().toString();
        password = passEdt.getText().toString();
        firstName = firstEdt.getText().toString();
        lastName = lastEdt.getText().toString();
        phoneNum = phoneEdt.getText().toString();

        if (TextUtils.isEmpty(email) | TextUtils.isEmpty(firstName) | TextUtils.isEmpty(password) | TextUtils.isEmpty(lastName) | TextUtils.isEmpty(phoneNum)) {
            Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Please enter a password with a minimum of 6 characters", Toast.LENGTH_LONG).show();
            return;
        }

        if (phoneNum.length() < 10) {
            Toast.makeText(getApplicationContext(), "Please enter a valid phone number", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                else {
                    Toast.makeText(getApplicationContext(), "Registration failed!" + " Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}