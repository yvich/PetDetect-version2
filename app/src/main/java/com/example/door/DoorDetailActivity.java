package com.example.door;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;

public class DoorDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView backTxt;
    protected TextView imagetimeTxt;
    RelativeLayout updateLyt;
    RelativeLayout deleteLyt;
    RelativeLayout DownloadLyt;
    ImageView doorImg;

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

        updateLyt = findViewById(R.id.updateLyt);
        updateLyt.setOnClickListener(this);

        deleteLyt = findViewById(R.id.deleteLyt);
        deleteLyt.setOnClickListener(this);

        DownloadLyt = findViewById(R.id.DownloadLyt);
        DownloadLyt.setOnClickListener(this);

        doorImg = findViewById(R.id.doorImg);

        database = FirebaseDatabase.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = database.getReference("UsersData").child(uid).child("a");

        data();

        ActivityCompat.requestPermissions(DoorDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(DoorDetailActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

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

        if (v == deleteLyt){
            Toast.makeText(getApplicationContext(), "Door deleted", Toast.LENGTH_LONG).show();
        }

        if (v == updateLyt){
            Toast.makeText(getApplicationContext(), "Door updated", Toast.LENGTH_LONG).show();
        }

        if (v == DownloadLyt){
            BitmapDrawable bitmapDrawable = (BitmapDrawable) doorImg.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            FileOutputStream outputStream = null;
            File file = Environment.getExternalStorageDirectory();
            File dir = new File(file.getAbsolutePath() + "/MyPictures");
            dir.mkdirs();

            String fileName = String.format("%d.png", System.currentTimeMillis());
            File outFile = new File(dir,fileName);
            try{
                outputStream = new FileOutputStream(outFile);
            }catch(Exception e){
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            try{
                outputStream.flush();
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
                outputStream.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "Image downloaded", Toast.LENGTH_LONG).show();
        }

    }
}