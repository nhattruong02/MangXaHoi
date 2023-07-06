package com.example.mangxahoigump.Model;

import java.util.Date;

public class TinNhan {
    private int matinnhan;
    private int mand;
    private int mannhan;
    private int manhom;
    private String noidung;
    private boolean trangthai;
    private int thoigian;

    @Override
    public String toString() {
        return "TinNhan{" +
                "matinnhan=" + matinnhan +
                ", mand=" + mand +
                ", mannhan=" + mannhan +
                ", manhom=" + manhom +
                ", noidung='" + noidung + '\'' +
                ", trangthai=" + trangthai +
                ", thoigian=" + thoigian +
                '}';
    }

    public TinNhan() {
    }

    public TinNhan(int mand, int mannhan, String noidung,  int  thoigian) {
        this.mand = mand;
        this.mannhan = mannhan;
        this.noidung = noidung;
        this.thoigian = thoigian;
    }

    public TinNhan(int matinnhan, int mand, int mannhan, int manhom, String noidung, boolean trangthai,  int  thoigian) {
        this.matinnhan = matinnhan;
        this.mand = mand;
        this.mannhan = mannhan;
        this.manhom = manhom;
        this.noidung = noidung;
        this.trangthai = trangthai;
        this.thoigian = thoigian;
    }

    public TinNhan(int mand, int mannhan, String noidung, boolean trangthai,  int  thoigian) {
        this.mand = mand;
        this.mannhan = mannhan;
        this.noidung = noidung;
        this.trangthai = trangthai;
        this.thoigian = thoigian;
    }

    public int getMatinnhan() {
        return matinnhan;
    }

    public void setMatinnhan(int matinnhan) {
        this.matinnhan = matinnhan;
    }

    public int getMand() {
        return mand;
    }

    public void setMand(int mand) {
        this.mand = mand;
    }

    public int getMannhan() {
        return mannhan;
    }

    public void setMannhan(int mannhan) {
        this.mannhan = mannhan;
    }

    public int getManhom() {
        return manhom;
    }

    public void setManhom(int manhom) {
        this.manhom = manhom;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public boolean getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }

    public int getThoigian() {
        return thoigian;
    }

    public void setThoigian(int thoigian) {
        this.thoigian = thoigian;
    }
}
