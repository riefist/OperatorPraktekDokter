package com.muhamadarief.operatorpraktek.Model;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.muhamadarief.operatorpraktek.R;

/**
 * Created by Muhamad Arief on 26/04/2017.
 */

public class Pendaftaran {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_praktek")
    @Expose
    private String id_praktek;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("nohp")
    @Expose
    private String nohp;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("no_antrian")
    @Expose
    private String no_antrian;

    public Pendaftaran() {
    }

    public Pendaftaran(String id, String id_praktek, String tanggal, String nama, String alamat, String nohp, String status, String no_antrian) {
        this.id = id;
        this.id_praktek = id_praktek;
        this.tanggal = tanggal;
        this.nama = nama;
        this.alamat = alamat;
        this.nohp = nohp;
        this.status = status;
        this.no_antrian = no_antrian;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_praktek() {
        return id_praktek;
    }

    public void setId_praktek(String id_praktek) {
        this.id_praktek = id_praktek;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNo_antrian() {
        return no_antrian;
    }

    public void setNo_antrian(String no_antrian) {
        this.no_antrian = no_antrian;
    }

}

