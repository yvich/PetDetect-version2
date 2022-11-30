package com.example.door;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
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
    String move = "";
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

        createNotificationChannel();

    }

    private void data() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imagetimeTxt.setText(snapshot.getValue(String.class));
                move = snapshot.getValue(String.class);
                Notification();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                imagetimeTxt.setText("No data is read");
            }
        });
    }

    private void Notification() {
        Intent intent = new Intent(this, DoorDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notification")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Something was detected")
                .setContentText(move)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notification", "notification", importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
            //Notification();
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