package com.example.e_pertanian.irigasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_pertanian.R;
import com.example.e_pertanian.watering.SimpanWaktuTimer;
import com.example.e_pertanian.watering.Watering;
import com.example.e_pertanian.watering.WateringPause;

import java.util.Locale;

public class IrigasiPause extends AppCompatActivity {

    private long START_TIME_IN_MILIS;

    private TextView countdown;
    private Button pause;
    private Button reset;
    Animation toPlant;
    ImageView waterdrop;


    private CountDownTimer timer;
    private boolean timerRunning;
    private long timeLeftMilis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irigasi_pause);


        START_TIME_IN_MILIS = ((Long.valueOf(SimpanWaktuIrigasi.getInstance(getApplicationContext()).getJam())*60*60) + (Long.valueOf(SimpanWaktuIrigasi.getInstance(getApplicationContext()).getMenit())*60) + Long.valueOf(SimpanWaktuIrigasi.getInstance(getApplicationContext()).getDetik()))*1000;
        timeLeftMilis = START_TIME_IN_MILIS;

        toPlant = AnimationUtils.loadAnimation(this, R.anim.to_plant);
        waterdrop = findViewById(R.id.imageView_waterdrops);

        countdown = findViewById(R.id.textView_countdown);
        pause = findViewById(R.id.button_jeda);
        reset = findViewById(R.id.button_batal);

        startTimer();

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountdownText();
    }

    private void pauseTimer() {
        waterdrop.clearAnimation();
        waterdrop.setVisibility(View.INVISIBLE);

        timer.cancel();
        timerRunning = false;
        pause.setText("lanjut");
        reset.setVisibility(View.VISIBLE);
    }

    private void startTimer() {
        waterdrop.setAnimation(toPlant);
        timer = new CountDownTimer(timeLeftMilis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMilis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                pause.setText("lanjut");
                pause.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.VISIBLE);
            }
        }.start();
        timerRunning = true;
        pause.setText("jeda");
        reset.setVisibility(View.INVISIBLE);
    }

    private void resetTimer() {
        Intent intent =new Intent(IrigasiPause.this, Watering.class);
        startActivity(intent);
    }

    private void updateCountdownText() {
        int hours = (int) (timeLeftMilis/1000) / 3600;
        int minutes = (int) ((timeLeftMilis/1000) % 3600) / 60;
        int seconds = (int) (timeLeftMilis/1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d", hours, minutes, seconds);

        countdown.setText(timeLeftFormatted);
    }
}