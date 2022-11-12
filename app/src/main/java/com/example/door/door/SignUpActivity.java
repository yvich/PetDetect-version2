package com.example.door;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText emailEdt, passEdt, firstEdt, lastEdt, phoneEdt;
    TextView backTxt;
    RelativeLayout registerLyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setupUI();

        backTxt.setOnClickListener(this);
        registerLyt.setOnClickListener(this);
    }

    private void setupUI(){
        emailEdt = findViewById(R.id.emailEdt);
        passEdt = findViewById(R.id.passEdt);
        firstEdt = findViewById(R.id.firstEdt);
        lastEdt = findViewById(R.id.lastEdt);
        phoneEdt = findViewById(R.id.phoneEdt);
        backTxt = findViewById(R.id.backTxt);
        registerLyt = findViewById(R.id.registerLyt);
    }

    @Override
    public void onClick(View v) {

        if (v == backTxt){

            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        }

        if (v == registerLyt){
            verifyAccount();
            startActivity(new Intent(SignUpActivity.this, InputActivity2.class));
            finish();
        }
    }


    private boolean isEmail(EditText txt){
        CharSequence email = txt.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isEmpty(EditText txt){
        CharSequence str = txt.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private void verifyAccount(){
        if(isEmail(emailEdt) == false){
            emailEdt.setError("Must enter valid email");
        }

        if(isEmpty(passEdt)){
            Toast toast = Toast.makeText(this, "Must enter password", Toast.LENGTH_SHORT);
            toast.show();
        }

        if(isEmpty(firstEdt)){
            Toast toast = Toast.makeText(this, "Must enter first name", Toast.LENGTH_SHORT);
            toast.show();
        }

        if(isEmpty(lastEdt)){
            Toast toast = Toast.makeText(this, "Must enter last name", Toast.LENGTH_SHORT);
            toast.show();
        }

        if(isEmpty(phoneEdt)){
            Toast toast = Toast.makeText(this, "Must enter phone number", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}