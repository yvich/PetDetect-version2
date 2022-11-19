package com.example.door;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        //progressBar = findViewById(R.id.progressBar);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //progressBar.setVisibility(View.GONE);

                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    startActivity(new Intent(SplashActivity.this,Home.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this, TermsConditionsActivity.class));
                    finish();
                }


            }
        }, 4500);

    }
}