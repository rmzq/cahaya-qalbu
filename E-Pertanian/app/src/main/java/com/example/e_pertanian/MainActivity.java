package com.example.e_pertanian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


import com.example.e_pertanian.schedule.SchedulingActivity;
import com.example.e_pertanian.watering.Watering;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    Button btnLogout;
    FrameLayout schedule, watering, irigasi;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = findViewById(R.id.btnLogout);
        schedule = findViewById(R.id.scheduling);
        watering = findViewById(R.id.watering);
        irigasi = findViewById(R.id.irigasi);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(MainActivity.this, Login.class);
                startActivity(intToMain);
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToMain = new Intent(MainActivity.this, SchedulingActivity.class);
                startActivity(intToMain);
            }
        });

        watering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToMain = new Intent(MainActivity.this, Watering.class);
                startActivity(intToMain);
            }
        });

        irigasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToMain = new Intent(MainActivity.this, Watering.class);
                startActivity(intToMain);
            }
        });
    }
}