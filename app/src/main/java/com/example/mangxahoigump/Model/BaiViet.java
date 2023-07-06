package com.example.mangxahoigump.Model;

import java.util.Date;

public class BaiViet {
    private int mabv;
    private int mand;
    private String tennd;
    private String hinhanh;
    private String noidung;
    private Date thoigian;
    private int soluongbl;
    private int soluonglike;
    private String chedo;

    public BaiViet(int mabv, int mand, String tennd, String hinhanh, String noidung, Date thoigian, int soluongbl, int soluonglike, String chedo) {
        this.mabv = mabv;
        this.mand = mand;
        this.tennd = tennd;
        this.hinhanh = hinhanh;
        this.noidung = noidung;
        this.thoigian = thoigian;
        this.soluongbl = soluongbl;
        this.soluonglike = soluonglike;
        this.chedo = chedo;
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

    public String getTennd() {
        return tennd;
    }

    public void setTennd(String tennd) {
        this.tennd = tennd;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
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

    public int getSoluongbl() {
        return soluongbl;
    }

    public void setSoluongbl(int soluongbl) {
        this.soluongbl = soluongbl;
    }

    public int getSoluonglike() {
        return soluonglike;
    }

    public void setSoluonglike(int soluonglike) {
        this.soluonglike = soluonglike;
    }

    public String getChedo() {
        return chedo;
    }

    public void setChedo(String chedo) {
        this.chedo = chedo;
    }
}
