package com.example.e_pertanian.watering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.e_pertanian.R;

public class Watering extends AppCompatActivity {

    NumberPicker npJamm, npMenitt, npDetikk;
    int jam,  menit, detik;
    Button startTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watering);

        npJamm = findViewById(R.id.npJam);
        npMenitt = findViewById(R.id.npMenit);
        npDetikk = findViewById(R.id.npDetik);
        startTimer = findViewById(R.id.button_start);

        npJamm.setMaxValue(24);
        npMenitt.setMaxValue(59);
        npDetikk.setMaxValue(59);

        npJamm.setValue(SimpanWaktuTimer.getInstance(getApplicationContext()).getJam());
        npMenitt.setValue(SimpanWaktuTimer.getInstance(getApplicationContext()).getMenit());
        npDetikk.setValue(SimpanWaktuTimer.getInstance(getApplicationContext()).getDetik());


        npJamm.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                SimpanWaktuTimer.getInstance(getApplicationContext()).writeJam(newVal);
            }
        });

        npMenitt.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                SimpanWaktuTimer.getInstance(getApplicationContext()).writeMenit(newVal);
            }
        });

        npDetikk.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                SimpanWaktuTimer.getInstance(getApplicationContext()).writeDetik(newVal);
            }
        });


        startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Watering.this, WateringPause.class);
                startActivity(intent);
            }
        });
    }
}