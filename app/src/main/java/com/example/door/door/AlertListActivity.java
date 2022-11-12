package com.example.door;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AlertListActivity extends AppCompatActivity implements View.OnClickListener{


    TextView backTxt;
    ListView simpleList;
    String doornameStr[] = {"Front1 Door", "Back 2 Door", "Right Door", "Left Door", "Test Door", "Test 4 door"};
    String doortimeStr[] = {"01/10/2021", "01/10/2021", "01/10/2021", "01/10/2021", "01/10/2021", "01/10/2021"};
    int flags[] = {R.drawable.image1,
            R.drawable.image2,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image1};

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

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                                "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == backTxt){

            startActivity(new Intent(AlertListActivity.this, Home.class));
            finish();
        }
    }
}