package com.example.e_pertanian.weather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_pertanian.R;

public class weather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }

    public void Lihat_cuaca(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.bmkg.go.id/cuaca/prakiraan-cuaca-indonesia.bmkg?Prov=06&NamaProv=DI%20Yogyakarta"));
        startActivity(intent);
    }
}