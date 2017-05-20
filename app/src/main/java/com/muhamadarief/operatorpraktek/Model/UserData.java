package com.muhamadarief.operatorpraktek.Model;

/**
 * Created by Muhamad Arief on 29/04/2017.
 */

public class UserData {
    private String id_praktek;
    private String firstname;
    private String lastname;
    private String email;
    private String nohp;
    private String jenis_kelamin;

    public UserData() {
    }

    public String getId_praktek() {
        return id_praktek;
    }

    public void setId_praktek(String id_praktek) {
        this.id_praktek = id_praktek;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }
}
