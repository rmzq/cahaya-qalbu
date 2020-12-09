package com.example.e_pertanian.watering;

import android.content.Context;
import android.content.SharedPreferences;

public class SimpanWaktuTimer {

    private SharedPreferences mPreferences; //mendefinisikan sharedPreferences pada aplikasi ini
    private String sharedPrefFile = "com.example.timer"; //lokasi file sharedPreferences disimpan yang com. itu harus sama kayak nama packagenya
    private Context mContext;

    private final String COUNT_JAM = "jam";
    private final String COUNT_MENIT = "menit";
    private final String COUNT_DETIK = "detik";

    private static SimpanWaktuTimer instance;

    private int jam, menit, detik;


    public static SimpanWaktuTimer getInstance(Context context) {
        if (instance == null) {
            instance = new SimpanWaktuTimer(context);
        }
        return instance;
    }

    private SimpanWaktuTimer(Context context) {
        mContext = context;
        mPreferences = mContext.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
    }

    public void writeJam(int jam) {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(COUNT_JAM, jam);
        preferencesEditor.apply();
    }

    public void writeMenit(int menit) {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(COUNT_MENIT, menit);
        preferencesEditor.apply();
    }

    public void writeDetik(int detik) {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(COUNT_DETIK, detik);
        preferencesEditor.apply();
    }

    public int getJam() {
        return  mPreferences.getInt(COUNT_JAM, 0);
    }

    public int getMenit() {
        return  mPreferences.getInt(COUNT_MENIT, 0);
    }

    public int getDetik() {
        return  mPreferences.getInt(COUNT_DETIK, 0);
    }
}
