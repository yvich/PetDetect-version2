package com.example.door;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class Home extends AppCompatActivity implements View.OnClickListener{
    GridView doorlist;
    ImageView alertImg, doorImg, logout;
    ArrayList birdList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        doorlist = (GridView) findViewById(R.id.doorgridView);
        alertImg = findViewById(R.id.alertImg);
        alertImg.setOnClickListener(this);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

        String[] flowerName = {"Door"};

        int[] flowerImages = {R.drawable.dooricon,};

        DoorAdapter gridAdapter = new DoorAdapter(Home.this,flowerName,flowerImages);
        doorlist.setAdapter(gridAdapter);


        doorlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(Home.this,"You Clicked on "+ flowerName[position],Toast.LENGTH_SHORT).show();
                doorImg = view.findViewById(R.id.imageView);
                doorImg.setImageResource(R.drawable.dooriconclicked);
                startActivity(new Intent(Home.this, DoorDetailActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == alertImg){
            startActivity(new Intent(Home.this, AlertListActivity.class));
            finish();
        }

        if (v == logout) {
            FirebaseAuth.getInstance().signOut();
            Intent profileIntent = new Intent(this, MainActivity.class);
            startActivity(profileIntent);
        }
    }
}