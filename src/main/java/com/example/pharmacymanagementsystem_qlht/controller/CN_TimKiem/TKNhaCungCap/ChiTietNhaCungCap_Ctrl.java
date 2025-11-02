package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKNhaCungCap;

import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKNhaCungCap.ChiTietNhaCungCap_GUI;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChiTietNhaCungCap_Ctrl {

    // --- 1. KHAI BÁO VIEW ---
    private ChiTietNhaCungCap_GUI view;

    // --- 2. BIẾN LOGIC ---
    private NhaCungCap nhaCungCap;

    // --- 3. CONSTRUCTOR (SỬA ĐỔI) ---
    public ChiTietNhaCungCap_Ctrl(ChiTietNhaCungCap_GUI view) {
        this.view = view;
        setupLogic();
    }

    // --- 4. HÀM SETUP LOGIC (Thay thế initialize) ---
    private void setupLogic() {
        view.btnThoat.setOnAction(e -> anThoat());

        // Cấu hình các trường không thể chỉnh sửa
        view.maNCC.setEditable(false);
        view.tenNCC.setEditable(false);
        view.DiaChi.setEditable(false);
        view.SDT.setEditable(false);
        view.Email.setEditable(false);
        view.GPKD.setEditable(false);
        view.MST.setEditable(false);
        view.TenCongTy.setEditable(false);
        view.GhiChu.setEditable(false);
    }

    // --- 5. CÁC HÀM XỬ LÝ (SỬA ĐỔI ĐỂ DÙNG VIEW) ---
    public void setNhaCungCap(NhaCungCap ncc) {
        this.nhaCungCap = ncc;
        hienThiThongTin(ncc);
    }

    public void hienThiThongTin(NhaCungCap ncc) {
        if (ncc != null) {
            view.maNCC.setText(ncc.getMaNCC());
            view.tenNCC.setText(ncc.getTenNCC() != null ? ncc.getTenNCC() : "");
            view.DiaChi.setText(ncc.getDiaChi() != null ? ncc.getDiaChi() : "");
            view.SDT.setText(ncc.getSDT() != null ? ncc.getSDT() : "");
            view.Email.setText(ncc.getEmail() != null ? ncc.getEmail() : "");
            view.GPKD.setText(ncc.getGPKD() != null ? ncc.getGPKD() : "");
            view.MST.setText(ncc.getMSThue() != null ? ncc.getMSThue() : "");
            view.TenCongTy.setText(ncc.getTenCongTy() != null ? ncc.getTenCongTy() : "");
            view.GhiChu.setText(ncc.getGhiChu() != null ? ncc.getGhiChu() : "");
        }
    }

    private void anThoat() {
        Stage stage = (Stage) view.btnThoat.getScene().getWindow();
        stage.close();
    }
}