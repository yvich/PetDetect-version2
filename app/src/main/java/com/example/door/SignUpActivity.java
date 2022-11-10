package com.example.door;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    TextView backTxt;
    RelativeLayout registerLyt;
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
    }

    @Override
    public void onClick(View v) {

        if (v == backTxt){

            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        }

        if (v == registerLyt){

            startActivity(new Intent(SignUpActivity.this, InputActivity2.class));
            finish();
        }
    }
}