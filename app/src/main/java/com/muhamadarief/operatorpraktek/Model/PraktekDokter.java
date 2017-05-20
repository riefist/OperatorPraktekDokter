package com.muhamadarief.operatorpraktek.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhamad Arief on 15/05/2017.
 */

public class PraktekDokter extends BaseResponse {


    @SerializedName("id_praktek")
    private String id_praktek;

    @SerializedName("nama_dokter")
    private String nama_dokter;

    @SerializedName("id_spesialis")
    private String id_spesialis;

    @SerializedName("tempat_praktek")
    private String tempat_praktek;

    @SerializedName("jadwal")
    private String jadwal;

    @SerializedName("jam")
    private String jam;

    @SerializedName("keterangan")
    private String keterangan;

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("status")
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public PraktekDokter() {
    }


    public String getId_praktek() {
        return id_praktek;
    }

    public void setId_praktek(String id_praktek) {
        this.id_praktek = id_praktek;
    }

    public String getNama_dokter() {
        return nama_dokter;
    }

    public void setNama_dokter(String nama_dokter) {
        this.nama_dokter = nama_dokter;
    }

    public String getId_spesialis() {
        return id_spesialis;
    }

    public void setId_spesialis(String id_spesialis) {
        this.id_spesialis = id_spesialis;
    }

    public String getTempat_praktek() {
        return tempat_praktek;
    }

    public void setTempat_praktek(String tempat_praktek) {
        this.tempat_praktek = tempat_praktek;
    }

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
