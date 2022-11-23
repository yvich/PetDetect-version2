package com.example.door;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class InputActivity2 extends AppCompatActivity implements View.OnClickListener{

    RelativeLayout nextLyt;
    EditText numEdt;
    String st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        nextLyt = findViewById(R.id.nextLyt);
        nextLyt.setOnClickListener(this);

        numEdt = findViewById(R.id.numEdt);

       SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.Door_SharedPreferences), Context.MODE_PRIVATE);
       String door = sharedPreferences.getString(getString(R.string.Door_SharedPreferences_Name_Key),null);


    }

    @Override
    public void onClick(View v) {

        if (v == nextLyt){
            Intent intent = new Intent(InputActivity2.this, Home.class);
            st = numEdt.getText().toString();
            intent.putExtra("Value",st);
            startActivity(intent);
            finish();
        }
    }
}