package com.example.door;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    TextView signupTxt;
    RelativeLayout signinLyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        signupTxt = findViewById(R.id.signupTxt);
        signupTxt.setOnClickListener(this);

        signinLyt = findViewById(R.id.signinLyt);
        signinLyt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == signupTxt){

            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            finish();
        }

        if (v == signinLyt){

            startActivity(new Intent(MainActivity.this, DoorAdapter.class));
            //change DoorAdapter to Home
            finish();
        }
    }
}