package com.example.mangxahoigump.Model;

import java.io.Serializable;
import java.util.Date;

public class NguoiDung implements Serializable {
    private int mand;
    private String taikhoan;
    private String matkhau;
    private String hoten;
    private String gioitinh;
    private String sdt;
    private String mota;
    private String hinhanh;
    private boolean trangthai;
    private Date namsinh;

    public NguoiDung(int mand, String taikhoan, String matkhau, String hoten, String gioitinh, String sdt, String mota, String hinhanh, boolean trangthai, Date namsinh) {
        this.mand = mand;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.sdt = sdt;
        this.mota = mota;
        this.hinhanh = hinhanh;
        this.trangthai = trangthai;
        this.namsinh = namsinh;
    }

    public NguoiDung(int mand, String hoten, String hinhanh) {
        this.mand = mand;
        this.hoten = hoten;
        this.hinhanh = hinhanh;
    }

    @Override
    public String toString() {
        return "NguoiDung{" +
                "mand=" + mand +
                ", taikhoan='" + taikhoan + '\'' +
                ", matkhau='" + matkhau + '\'' +
                ", hoten='" + hoten + '\'' +
                ", gioitinh='" + gioitinh + '\'' +
                ", sdt='" + sdt + '\'' +
                ", mota='" + mota + '\'' +
                ", hinhanh='" + hinhanh + '\'' +
                ", trangthai=" + trangthai +
                ", ngaysinh=" + namsinh +
                '}';
    }

    public int getMand() {
        return mand;
    }

    public void setMand(int mand) {
        this.mand = mand;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public boolean isTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }

    public Date getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(Date namsinh) {
        this.namsinh = namsinh;
    }
}
