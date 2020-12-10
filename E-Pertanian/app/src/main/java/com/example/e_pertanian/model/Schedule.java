package com.example.e_pertanian.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Schedule{
    String id;
    String jenisKg;
    String tanggal;
    String waktu;
    String lama;
    boolean isAuto;

    public Schedule(String id, String jenisKg, String tanggal, String waktu, String lama, boolean isAuto) {
        this.id = id;
        this.jenisKg = jenisKg;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.lama = lama;
        this.isAuto = isAuto;
    }

    public Schedule(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJenisKg() {
        return jenisKg;
    }

    public void setJenisKg(String jenisKg) {
        this.jenisKg = jenisKg;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getLama() {
        return lama;
    }

    public void setLama(String lama) {
        this.lama = lama;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public void setAuto(boolean auto) {
        isAuto = auto;
    }
}
