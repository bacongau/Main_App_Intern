package com.example.ngay_29_7_2021.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CustomerRealm extends RealmObject {

    @PrimaryKey
    private int id;

    private String tentk;
    private String matkhau;
    private boolean ghinho;

    public CustomerRealm(int id, String tentk, String matkhau, boolean ghinho) {
        this.id = id;
        this.tentk = tentk;
        this.matkhau = matkhau;
        this.ghinho = ghinho;
    }

    public CustomerRealm() {
    }

    @Override
    public String toString() {
        return "CustomerRealm{" +
                "id=" + id +
                ", tentk='" + tentk + '\'' +
                ", matkhau='" + matkhau + '\'' +
                ", ghinho=" + ghinho +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTentk() {
        return tentk;
    }

    public void setTentk(String tentk) {
        this.tentk = tentk;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public boolean isGhinho() {
        return ghinho;
    }

    public void setGhinho(boolean ghinho) {
        this.ghinho = ghinho;
    }
}
