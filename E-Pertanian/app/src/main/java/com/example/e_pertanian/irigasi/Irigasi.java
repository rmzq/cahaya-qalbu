package com.example.e_pertanian.irigasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.e_pertanian.R;
import com.example.e_pertanian.watering.SimpanWaktuTimer;
import com.example.e_pertanian.watering.Watering;
import com.example.e_pertanian.watering.WateringPause;

public class Irigasi extends AppCompatActivity {

    NumberPicker npJamm, npMenitt, npDetikk;
    int jam,  menit, detik;
    Button startTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irigasi);

        npJamm = findViewById(R.id.npJam);
        npMenitt = findViewById(R.id.npMenit);
        npDetikk = findViewById(R.id.npDetik);
        startTimer = findViewById(R.id.button_start);

        npJamm.setMaxValue(24);
        npMenitt.setMaxValue(59);
        npDetikk.setMaxValue(59);

        npJamm.setValue(SimpanWaktuIrigasi.getInstance(getApplicationContext()).getJam());
        npMenitt.setValue(SimpanWaktuIrigasi.getInstance(getApplicationContext()).getMenit());
        npDetikk.setValue(SimpanWaktuIrigasi.getInstance(getApplicationContext()).getDetik());


        npJamm.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                SimpanWaktuIrigasi.getInstance(getApplicationContext()).writeJam(newVal);
            }
        });

        npMenitt.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                SimpanWaktuIrigasi.getInstance(getApplicationContext()).writeMenit(newVal);
            }
        });

        npDetikk.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                SimpanWaktuIrigasi.getInstance(getApplicationContext()).writeDetik(newVal);
            }
        });


        startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Irigasi.this, IrigasiPause.class);
                startActivity(intent);
            }
        });
    }
}