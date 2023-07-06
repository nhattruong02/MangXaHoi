package com.example.mangxahoigump.Model;

import java.util.Date;

public class DongBinhLuan {
    private int madbl;
    private int mabv;
    private int mand;
    private String noidung;
    private Date thoigian;
    private String hinh;

    public DongBinhLuan(int madbl, int mabv, int mand, String noidung, Date thoigian, String hinh) {
        this.madbl = madbl;
        this.mabv = mabv;
        this.mand = mand;
        this.noidung = noidung;
        this.thoigian = thoigian;
        this.hinh = hinh;
    }

    public int getMadbl() {
        return madbl;
    }

    public void setMadbl(int madbl) {
        this.madbl = madbl;
    }

    public int getMabv() {
        return mabv;
    }

    public void setMabv(int mabv) {
        this.mabv = mabv;
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

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}
