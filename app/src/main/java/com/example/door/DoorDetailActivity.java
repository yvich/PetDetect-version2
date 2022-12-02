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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DoorDetailActivity extends AppCompatActivity implements View.OnClickListener{
    TextView backTxt;
    protected TextView imagetimeTxt;
    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference refDate;
    DatabaseReference refP;
    String uid;
    String move = "";
    String image;
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
        refDate = database.getReference("UsersData").child(uid).child("date");
        refP = database.getReference("UsersData").child(uid).child("previous");

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.detected_shared_preferences), Context.MODE_PRIVATE);
        image = sharedPreferences.getString(getString(R.string.detected_shared_preferences), null);

        data();

        createNotificationChannel();
    }

    private void data() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                refP.setValue(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                imagetimeTxt.setText("No data is read");
            }
        });

        refP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = sdf.format(c.getTime());
                imagetimeTxt.setText(snapshot.getValue(String.class) + " on " + strDate);
                refDate.setValue(strDate);
                move = snapshot.getValue(String.class) + " on " + strDate;
                Notification();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
    }
}