package com.example.door;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoorDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView backTxt;
    protected TextView imagetimeTxt;

    FirebaseDatabase database;
    DatabaseReference reference;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        backTxt = findViewById(R.id.backTxt);
        backTxt.setOnClickListener(this);
        imagetimeTxt = findViewById(R.id.imagetimeTxt);

        database = FirebaseDatabase.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = database.getReference("UsersData").child(uid).child("a");

        data();
    }

    private void data() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imagetimeTxt.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                imagetimeTxt.setText("No data is read");
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == backTxt){

            startActivity(new Intent(DoorDetailActivity.this, Home.class));
            finish();
        }

    }
}