package com.example.mangxahoigump.Model;

public class Likes {
    private int mal;
    private int mand;
    private int mabv;
    private boolean trangthai;

    @Override
    public String toString() {
        return "Likes{" +
                "mal=" + mal +
                ", mand=" + mand +
                ", mabv=" + mabv +
                ", trangthai=" + trangthai +
                '}';
    }

    public Likes(int mal, int mand, int mabv, boolean trangthai) {
        this.mal = mal;
        this.mand = mand;
        this.mabv = mabv;
        this.trangthai = trangthai;
    }

    public int getMal() {
        return mal;
    }

    public void setMal(int mal) {
        this.mal = mal;
    }

    public int getMand() {
        return mand;
    }

    public void setMand(int mand) {
        this.mand = mand;
    }

    public int getMabv() {
        return mabv;
    }

    public void setMabv(int mabv) {
        this.mabv = mabv;
    }

    public boolean isTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }
}
