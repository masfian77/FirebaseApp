package com.imastudio.firebaseapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Peserta implements Parcelable {
    String id, nama, asal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.asal);
    }

    public Peserta() {
    }

    protected Peserta(Parcel in) {
        this.id = in.readString();
        this.nama = in.readString();
        this.asal = in.readString();
    }

    public static final Parcelable.Creator<Peserta> CREATOR = new Parcelable.Creator<Peserta>() {
        @Override
        public Peserta createFromParcel(Parcel source) {
            return new Peserta(source);
        }

        @Override
        public Peserta[] newArray(int size) {
            return new Peserta[size];
        }
    };
}
