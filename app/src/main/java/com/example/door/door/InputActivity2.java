package com.example.door;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class InputActivity2 extends AppCompatActivity implements View.OnClickListener{

    RelativeLayout nextLyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        nextLyt = findViewById(R.id.nextLyt);
        nextLyt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == nextLyt){

            startActivity(new Intent(InputActivity2.this, Home.class));
            finish();
        }
    }
}