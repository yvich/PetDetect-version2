package com.example.door;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    EditText passEdt, emailEdt;
    TextView signupTxt;
    //TextView forgotPass;
    RelativeLayout signinLyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        passEdt = findViewById(R.id.passEdt);
        emailEdt = findViewById(R.id.emailEdt);

        signupTxt = findViewById(R.id.signupTxt);
        signupTxt.setOnClickListener(this);

        signinLyt = findViewById(R.id.signinLyt);
        signinLyt.setOnClickListener(this);

       // forgotPass = findViewById(R.id.forgotPass);
       // forgotPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == signupTxt){

            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            finish();
        }

        if (v == signinLyt){
            if(emailEdt.getText().length() > 0 && passEdt.getText().length() > 0){
                startActivity(new Intent(MainActivity.this, Home.class));
            }
            else{
                String toastErrorMsg = "username or Password incorrect";
                Toast.makeText(getApplicationContext(),toastErrorMsg,Toast.LENGTH_SHORT).show();
            }
        }
    }
}