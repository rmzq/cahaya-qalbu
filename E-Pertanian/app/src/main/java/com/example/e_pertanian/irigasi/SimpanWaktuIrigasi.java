package com.example.e_pertanian.irigasi;

import android.content.Context;
import android.content.SharedPreferences;

public class SimpanWaktuIrigasi {

    private SharedPreferences mPreferencesIrigasi; //mendefinisikan sharedPreferences pada aplikasi ini
    private String sharedPrefFile = "com.example.e_pertanian.irigasi"; //lokasi file sharedPreferences disimpan yang com. itu harus sama kayak nama packagenya
    private Context mContext;

    private final String COUNT_JAM = "jam";
    private final String COUNT_MENIT = "menit";
    private final String COUNT_DETIK = "detik";

    private static SimpanWaktuIrigasi instance;

    private int jam, menit, detik;


    public static SimpanWaktuIrigasi getInstance(Context context) {
        if (instance == null) {
            instance = new SimpanWaktuIrigasi(context);
        }
        return instance;
    }

    private SimpanWaktuIrigasi(Context context) {
        mContext = context;
//        mPreferences = mContext.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        mPreferencesIrigasi = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    }

    public void writeJam(int jam) {
        SharedPreferences.Editor preferencesEditor = mPreferencesIrigasi.edit();
        preferencesEditor.putInt(COUNT_JAM, jam);
        preferencesEditor.apply();
    }

    public void writeMenit(int menit) {
        SharedPreferences.Editor preferencesEditor = mPreferencesIrigasi.edit();
        preferencesEditor.putInt(COUNT_MENIT, menit);
        preferencesEditor.apply();
    }

    public void writeDetik(int detik) {
        SharedPreferences.Editor preferencesEditor = mPreferencesIrigasi.edit();
        preferencesEditor.putInt(COUNT_DETIK, detik);
        preferencesEditor.apply();
    }

    public int getJam() {
        return  mPreferencesIrigasi.getInt(COUNT_JAM, 0);
    }

    public int getMenit() {
        return  mPreferencesIrigasi.getInt(COUNT_MENIT, 0);
    }

    public int getDetik() {
        return  mPreferencesIrigasi.getInt(COUNT_DETIK, 0);
    }
}
