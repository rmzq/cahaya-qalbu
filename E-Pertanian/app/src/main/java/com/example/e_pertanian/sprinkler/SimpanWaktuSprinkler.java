package com.example.e_pertanian.sprinkler;

import android.content.Context;
import android.content.SharedPreferences;

public class SimpanWaktuSprinkler {

    private SharedPreferences mPreferences; //mendefinisikan sharedPreferences pada aplikasi ini
    private String sharedPrefFile = "com.example.e_pertanian.sprinkler"; //lokasi file sharedPreferences disimpan yang com. itu harus sama kayak nama packagenya
    private Context mContext;

    private final String COUNT_JAM = "jam";
    private final String COUNT_MENIT = "menit";
    private final String COUNT_DETIK = "detik";

    private static SimpanWaktuSprinkler instance;

    private int jam, menit, detik;


    public static SimpanWaktuSprinkler getInstance(Context context) {
        if (instance == null) {
            instance = new SimpanWaktuSprinkler(context);
        }
        return instance;
    }

    private SimpanWaktuSprinkler(Context context) {
        mContext = context;
//        mPreferences = mContext.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        mPreferences = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
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
