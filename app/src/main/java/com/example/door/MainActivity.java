package com.example.door;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    TextView signupTxt;
    RelativeLayout signinLyt;
    TextView forgotPasswordTxt;

    protected EditText emailEdt;
    protected EditText passEdt;

    private FirebaseAuth mAuth;

    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.Door_SharedPreferences), Context.MODE_PRIVATE);
    String door = sharedPreferences.getString(getString(R.string.Door_SharedPreferences_Name_Key),null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        signupTxt = findViewById(R.id.signupTxt);
        signupTxt.setOnClickListener(this);


        forgotPasswordTxt = findViewById(R.id.forgotPasswordTxt);
        forgotPasswordTxt.setOnClickListener(this);

        signinLyt = findViewById(R.id.signinLyt);
        signinLyt.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        emailEdt = findViewById(R.id.emailEdt);
        passEdt = findViewById(R.id.passEdt);

    }

    @Override
    public void onClick(View v) {

        if (v == signupTxt){

            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            finish();
        }

        if (v == signinLyt){
            loginUserAccount();
        }

        if (v == forgotPasswordTxt){
            Intent intent = new Intent(MainActivity.this, forgotPass.class);
            startActivity(intent);
        }
    }

    private void loginUserAccount() {
        String email, password;
        email = emailEdt.getText().toString();
        password = passEdt.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();

                    if(door==" "){
                        Intent intent = new Intent(MainActivity.this, InputActivity2.class);
                        startActivity(intent);
                    }

                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                }

                else {
                    Toast.makeText(getApplicationContext(), "Login failed! Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
