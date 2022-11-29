package com.example.door;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DoorDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView backTxt;
    ImageView imv;
    String img,name,time,door;
    TextView doorname,doortime;
    ProgressDialog mProgressDialog;

    RelativeLayout del,update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        imv = findViewById(R.id.doorImg);
        doorname = findViewById(R.id.doorTxt);
        doortime = findViewById(R.id.imagetimeTxt);
        del = findViewById(R.id.deleteLyt);
        update = findViewById(R.id.updateLyt);

        img = getIntent().getStringExtra("image");
        name = getIntent().getStringExtra("name");
        door = getIntent().getStringExtra("Door");
        time = getIntent().getStringExtra("time");

        doortime.setText("Taken at "+time);
        doorname.setText(name);
        Glide
                .with(getApplicationContext())
                .load(img)
                .centerCrop()
                .placeholder(R.drawable.image1)
                .into(imv);

        findViewById(R.id.DownloadLyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(DoorDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(DoorDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DoorDetailActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
                    ActivityCompat.requestPermissions(DoorDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
                    //showToast("Need Permission to access storage for Downloading Image");
                    Toast.makeText(DoorDetailActivity.this, "Need Permission to access storage for Downloading Image", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DoorDetailActivity.this, "Downloading Image...", Toast.LENGTH_SHORT).show();
                    //showToast("Downloading Image...");
                    //Asynctask to create a thread to downlaod image in the background
                    new DownloadsImage().execute(img);
                }
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DoorDetailActivity.this, door, Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference("UsersData")
                        .child(FirebaseAuth.getInstance().getUid()).child("DoorData").child(door).removeValue();

                startActivity(new Intent(DoorDetailActivity.this,Home.class));
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DoorDetailActivity.this);
                builder.setTitle("Title");

                View viewInflated = LayoutInflater.from(DoorDetailActivity.this).inflate(R.layout.dialog_input,(ViewGroup) findViewById(android.R.id.content), false);

                final EditText input = (EditText) viewInflated.findViewById(R.id.input);

                builder.setView(viewInflated);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        if(m_Text.isEmpty()){
                            Toast.makeText(DoorDetailActivity.this, "Please Enter Some name", Toast.LENGTH_SHORT).show();
                        }else{
                            dialog.dismiss();

                            FirebaseDatabase.getInstance().getReference("UsersData")
                                    .child(FirebaseAuth.getInstance().getUid()).child("DoorData").child(door)
                                    .child("Name").setValue(m_Text);

                            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                            String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

                            FirebaseDatabase.getInstance().getReference("UsersData")
                                    .child(FirebaseAuth.getInstance().getUid()).child("DoorData").child(door)
                                    .child("Time").setValue(currentTime+", "+currentDate);

                            startActivity(new Intent(DoorDetailActivity.this,Home.class));
                            finish();
                        }

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        backTxt = findViewById(R.id.backTxt);
        backTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == backTxt){

            startActivity(new Intent(DoorDetailActivity.this, Home.class));
            finish();
        }

        if (v == del){
            Toast.makeText(getApplicationContext(), "Door deleted", Toast.LENGTH_LONG).show();
        }

        if (v == update){
            Toast.makeText(getApplicationContext(), "Door updated", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DoorDetailActivity.this, Home.class));
        finish();
    }


    class DownloadsImage extends AsyncTask<String, Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+ "/AndroidDvlpr"); //Creates app specific folder

            if(!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis())+".png"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try{
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(DoorDetailActivity.this,new String[] { imageFile.getAbsolutePath() }, null,new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        // Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch(Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //showToast("Image Saved!");
            Toast.makeText(DoorDetailActivity.this, "Image Saved !!", Toast.LENGTH_SHORT).show();
        }
    }
}
