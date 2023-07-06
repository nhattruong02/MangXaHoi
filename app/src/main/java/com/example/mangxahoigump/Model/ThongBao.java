package com.example.mangxahoigump.Model;

import java.util.Date;

public class ThongBao {
    private int matb;
    private int mand;
    private String noidung;
    private Date thoigian;

    public ThongBao(int matb, int mand, String noidung, Date thoigian) {
        this.matb = matb;
        this.mand = mand;
        this.noidung = noidung;
        this.thoigian = thoigian;
    }


    public int getMatb() {
        return matb;
    }

    public void setMatb(int matb) {
        this.matb = matb;
    }

    public int getMand() {
        return mand;
    }

    public void setMand(int mand) {
        this.mand = mand;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Date getThoigian() {
        return thoigian;
    }

    public void setThoigian(Date thoigian) {
        this.thoigian = thoigian;
    }
}
