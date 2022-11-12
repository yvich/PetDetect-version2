package com.example.door;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DoorDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView backTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        backTxt = findViewById(R.id.backTxt);
        backTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == backTxt){

            startActivity(new Intent(DoorDetailActivity.this, Home.class));
            finish();
        }

    }
}