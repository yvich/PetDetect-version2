package com.example.door;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class TermsConditionsActivity extends AppCompatActivity {

    CheckBox cb; //checkbox

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // hook for checkbox
        cb = findViewById(R.id.checkBox);
        //onclick listener
        findViewById(R.id.signinLyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb.isChecked()){
                    //switch from termsconditionsactivity to mainactivity
                    startActivity(new Intent(TermsConditionsActivity.this,MainActivity.class));
                }else{
                    cb.setError("Please check this checkbox to approve terms ");
                    Toast.makeText(TermsConditionsActivity.this, "Please check the below box for approval", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}