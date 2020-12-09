package com.example.e_pertanian.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Schedule implements Parcelable {
    String id;
    Long jenisKg;
    String tanggal;
    String waktu;
    Long lama;
    boolean isAuto;

    public Schedule() {
    }

    protected Schedule(Parcel in) {
        id = in.readString();
        jenisKg = in.readLong();
        tanggal = in.readString();
        waktu = in.readString();
        lama = in.readLong();
        isAuto = in.readByte() != 0;
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getJenisKg() {
        return jenisKg;
    }

    public void setJenisKg(Long jenisKg) {
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

    public Long getLama() {
        return lama;
    }

    public void setLama(Long lama) {
        this.lama = lama;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public void setAuto(boolean auto) {
        isAuto = auto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeLong(jenisKg);
        dest.writeString(tanggal);
        dest.writeString(waktu);
        dest.writeLong(lama);
        dest.writeByte((byte) (isAuto ? 1 : 0));
    }
}
