package com.example.door;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPass extends AppCompatActivity {

    protected Button continueButton;
    protected EditText verifEmail;

    TextView back;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mAuth = FirebaseAuth.getInstance();

        back = findViewById(R.id.back);
        View.OnClickListener backClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(forgotPass.this, MainActivity.class));
                finish();
            }
        };

        back.setOnClickListener(backClick);

        continueButton = findViewById(R.id.continueButton);
        View.OnClickListener continueClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recover();
            }
        };

        continueButton.setOnClickListener(continueClick);

        verifEmail = findViewById(R.id.verifEmail);

    }

    private void recover() {
        String email;
        email = verifEmail.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Email sent! Please check email inbox or spam to reset your password.",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(forgotPass.this, MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid email or email is not linked to an account, please try again.",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error, please try again", Toast.LENGTH_LONG).show();
            }
        });

    }


}