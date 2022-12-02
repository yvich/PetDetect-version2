package com.example.door;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    }

    @Override
    public void onClick(View v) {

        if (v == nextLyt){
            if (numEdt.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please fill no. of door", Toast.LENGTH_SHORT).show();
            }
            else {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference("UsersData").child(FirebaseAuth.getInstance().getUid());
                database.child("Doors").setValue(numEdt.getText().toString());
                startActivity(new Intent(InputActivity2.this, Home.class));
                finish();
            }
        }
    }
}