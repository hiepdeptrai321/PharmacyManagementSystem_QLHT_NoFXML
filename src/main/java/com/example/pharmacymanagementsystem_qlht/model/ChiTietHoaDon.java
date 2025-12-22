package com.example.pharmacymanagementsystem_qlht.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Objects;

public class ChiTietHoaDon {
    private final IntegerProperty stt = new SimpleIntegerProperty();
    private HoaDon hoaDon;
    private Thuoc_SP_TheoLo loHang;
    private int soLuong;
    private DonViTinh dvt;
    private double donGia;
    private double giamGia;
    private final BooleanProperty keDon = new SimpleBooleanProperty(false);

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(HoaDon hoaDon, Thuoc_SP_TheoLo loHang, int soLuong, DonViTinh dvt, double donGia, double giamGia) {
        this.hoaDon = hoaDon;
        this.loHang = loHang;
        this.soLuong = soLuong;
        this.dvt = dvt;
        this.donGia = donGia;
        this.giamGia = giamGia;
    }
    public ChiTietHoaDon(HoaDon hoaDon, Thuoc_SP_TheoLo loHang, int soLuong, DonViTinh dvt, double donGia, double giamGia, boolean keDon) {
        this.hoaDon = hoaDon;
        this.loHang = loHang;
        this.soLuong = soLuong;
        this.dvt = dvt;
        this.donGia = donGia;
        this.giamGia = giamGia;
        this.keDon.set(keDon);
    }
    public int getStt() {
        return stt.get();
    }

    public void setStt(int stt) {
        this.stt.set(stt);
    }

    public IntegerProperty sttProperty() {
        return stt;
    }
    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public Thuoc_SP_TheoLo getLoHang() {
        return loHang;
    }

    public void setLoHang(Thuoc_SP_TheoLo loHang) {
        this.loHang = loHang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    public DonViTinh getDvt() {
        return dvt;
    }

    public void setDvt(DonViTinh dvt) {
        this.dvt = dvt;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }
    public boolean isKeDon() {
        return keDon.get();
    }

    public void setKeDon(boolean keDon) {
        this.keDon.set(keDon);
    }

    public BooleanProperty keDonProperty() {
        return keDon;
    }

    public double tinhThanhTien() {
        double thanhTien = this.soLuong * this.donGia - this.giamGia;
        return thanhTien;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "hoaDon=" + hoaDon +
                ", loHang=" + loHang +
                ", dvt=" + dvt +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", giamGia=" + giamGia +
                '}';
    }
}