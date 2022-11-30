package com.example.door;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        et = findViewById(R.id.numEdt);

        nextLyt = findViewById(R.id.nextLyt);
        nextLyt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == nextLyt){

            if(et.getText().toString().isEmpty()){
                Toast.makeText(this, "Please Fill no. of door", Toast.LENGTH_SHORT).show();
            }else{
                DatabaseReference database = FirebaseDatabase.getInstance().getReference("UsersData")
                        .child(FirebaseAuth.getInstance().getUid());
                database.child("Doors").setValue(et.getText().toString());
                startActivity(new Intent(InputActivity2.this, Home.class));
                finish();
            }


        }
    }
}
