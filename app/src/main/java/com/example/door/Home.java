package com.example.door;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements View.OnClickListener{

    GridView doorlist;
    ImageView alertImg, doorImg;
    ArrayList birdList=new ArrayList<>();
    String st;
    Context context;
    String [] flowerName={ };
    int [] flowerImages={ };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        doorlist = (GridView) findViewById(R.id.doorgridView);
        alertImg = findViewById(R.id.alertImg);
        doorImg = findViewById(R.id.doorImg);
        alertImg.setOnClickListener(this);

        st = getIntent().getExtras().getString("Value");

        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context.getApplicationContext());
        String door = sharedPreferencesHelper.getDoor();
        int numDoors = Integer.parseInt(door);

        StringBuffer stringBuffer = new StringBuffer(String.valueOf(flowerName));

        if(flowerImages.length==numDoors){
            DoorAdapter gridAdapter = new DoorAdapter(Home.this,flowerName,flowerImages);
            doorlist.setAdapter(gridAdapter);
        }
        else{
            int n = numDoors-flowerImages.length;
            for(int i=0; i<n;i++){
                int flowerImages = R.drawable.dooricon;
                stringBuffer.append(" new door");
                DoorAdapter gridAdapter = new DoorAdapter(Home.this,flowerName, new int[]{flowerImages});
                doorlist.setAdapter(gridAdapter);
            }
        }

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
    }
}