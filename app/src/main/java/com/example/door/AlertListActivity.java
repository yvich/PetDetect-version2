package com.example.door;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AlertListActivity extends AppCompatActivity implements View.OnClickListener{


    TextView backTxt;
    ListView simpleList;
    ArrayList<String> doornameStr = new ArrayList<>();
    ArrayList <String> doortimeStr = new ArrayList<>();
    ArrayList <Integer> flags = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference refDate;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        backTxt = findViewById(R.id.backTxt);
        backTxt.setOnClickListener(this);

        simpleList = (ListView) findViewById(R.id.doorListView);
        AlertListAdapter customAdapter = new AlertListAdapter(getApplicationContext(), doornameStr, doortimeStr, flags);
        simpleList.setAdapter(customAdapter);

        database = FirebaseDatabase.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = database.getReference("UsersData").child(uid).child("a");
        refDate = database.getReference("UsersData").child(uid).child("date");

        history();
        historyDate();
    }

    private void history() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doornameStr.add(snapshot.getValue(String.class));
                flags.add(R.drawable.dog);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void historyDate() {
        refDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doortimeStr.add(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    // database.getReference("UsersData").child(uid).child("date")

    @Override
    public void onClick(View v) {

        if (v == backTxt){

            startActivity(new Intent(AlertListActivity.this, Home.class));
            finish();
        }
    }
}