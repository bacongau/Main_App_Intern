package com.example.ngay_29_7_2021.model;

public class Customer {
    private String tentk;
    private String mk;
    private String hoten;
    private String diachi;
    private String sdt;

    public Customer() {
    }

    public Customer(String tentk, String mk) {
        this.tentk = tentk;
        this.mk = mk;
    }

    public Customer(String tentk, String mk, String hoten, String diachi, String sdt) {
        this.tentk = tentk;
        this.mk = mk;
        this.hoten = hoten;
        this.diachi = diachi;
        this.sdt = sdt;
    }

    public Customer(String hoten, String diachi, String sdt) {
        this.hoten = hoten;
        this.diachi = diachi;
        this.sdt = sdt;
    }

    public String getTentk() {
        return tentk;
    }

    public void setTentk(String tentk) {
        this.tentk = tentk;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "tentk='" + tentk + '\'' +
                ", mk='" + mk + '\'' +
                ", hoten='" + hoten + '\'' +
                ", diachi='" + diachi + '\'' +
                ", sdt='" + sdt + '\'' +
                '}';
    }
}
