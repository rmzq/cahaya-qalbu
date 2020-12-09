package com.example.e_pertanian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.example.e_pertanian.fragments.HomeFragment;
import com.example.e_pertanian.fragments.ScheduleFragment;
import com.example.e_pertanian.schedule.SchedulingActivity;
import com.example.e_pertanian.watering.Watering;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //buat button navigation bar

        getSupportFragmentManager().beginTransaction().replace(R.id.pager,new HomeFragment()).commit();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.tapBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navHome:
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navSchedule:
                        Toast.makeText(MainActivity.this, "schedule", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, SchedulingActivity.class);
//                        startActivity(intent);
                        selectedFragment = new ScheduleFragment();
                        break;
                    case R.id.navChat:
                        Toast.makeText(MainActivity.this, "chat", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navProfile:
                        Toast.makeText(MainActivity.this, "profile", Toast.LENGTH_SHORT).show();
                    default: selectedFragment = null;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.pager,selectedFragment).commit();
                return true;
            }
        });
    }
}