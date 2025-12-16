package com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.CaiDat;

import com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.CaiDat.caiDat_GUI;

public class caiDat_Ctrl {
    private final caiDat_GUI view;

    public caiDat_Ctrl(caiDat_GUI view) {
        this.view = view;
        initEvent();
    }

    private void initEvent() {
        view.btnLuu.setOnAction(e -> luu());
        view.btnHuy.setOnAction(e -> huy());
    }

    private void luu() {
        String thue = view.txtThue.getText();
        String ngay = view.txtNgay.getText();
        String donVi = view.cbxdonViNgay.getValue();

        System.out.println(thue + " - " + ngay + " " + donVi);
    }

    private void huy() {
        view.txtThue.clear();
        view.txtNgay.clear();
    }
}
