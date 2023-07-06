package com.example.mangxahoigump.Model;

import java.io.Serializable;

public class Nhom implements Serializable {
    private int manhom;
    private String tennhom;
    private String hinhanh;
    private String mota;
    private int soluongtv;

    @Override
    public String toString() {
        return "Nhom{" +
                "manhom=" + manhom +
                ", tennhom='" + tennhom + '\'' +
                ", hinhanh='" + hinhanh + '\'' +
                ", mota='" + mota + '\'' +
                ", soluongtv=" + soluongtv +
                '}';
    }

    public Nhom(int manhom, String tennhom, String hinhanh, String mota, int soluongtv) {
        this.manhom = manhom;
        this.tennhom = tennhom;
        this.hinhanh = hinhanh;
        this.mota = mota;
        this.soluongtv = soluongtv;
    }

    public int getManhom() {
        return manhom;
    }

    public void setManhom(int manhom) {
        this.manhom = manhom;
    }

    public String getTennhom() {
        return tennhom;
    }

    public void setTennhom(String tennhom) {
        this.tennhom = tennhom;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getSoluongtv() {
        return soluongtv;
    }

    public void setSoluongtv(int soluongtv) {
        this.soluongtv = soluongtv;
    }
}
