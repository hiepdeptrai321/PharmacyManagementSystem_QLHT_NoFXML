package com.example.pharmacymanagementsystem_qlht.model;

public class CaiDat {
    private String tenThongSo;
    private String giaTri;

    public CaiDat() {
    }

    public CaiDat(String tenThongSo, String giaTri) {
        this.tenThongSo = tenThongSo;
        this.giaTri = giaTri;
    }

    public String getTenThongSo() {
        return tenThongSo;
    }

    public void setTenThongSo(String tenThongSo) {
        this.tenThongSo = tenThongSo;
    }

    public String getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(String giaTri) {
        this.giaTri = giaTri;
    }
}
