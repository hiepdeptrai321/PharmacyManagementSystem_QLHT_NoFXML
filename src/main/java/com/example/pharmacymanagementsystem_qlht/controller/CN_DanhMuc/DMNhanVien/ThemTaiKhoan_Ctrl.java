package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien;

import com.example.pharmacymanagementsystem_qlht.dao.NhanVien_Dao;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ThemTaiKhoan_Ctrl {
    public TextField txtTaiKhoan;
    public TextField txtMatKhau;

    public NhanVien nhanVien;             // bản đang chỉnh
    public boolean isSaved = false;

    public NhanVien_Dao nhanVien_Dao = new NhanVien_Dao();
    List<NhanVien> listNhanVien = new ArrayList<>();

    public void initialize() { }

    // GỌI từ nơi mở dialog để nạp dữ liệu (hoặc tạo mới)
    public void loadTaiKhoan(NhanVien nv) {
        if (nv == null) {
            nhanVien = new NhanVien();
        } else {
            nhanVien = new NhanVien(nv);  // copy để tránh đụng bản gốc
        }
        // fill UI nếu đã có sẵn
        if (nhanVien.getTaiKhoan() != null) txtTaiKhoan.setText(nhanVien.getTaiKhoan());
        if (nhanVien.getMatKhau() != null) txtMatKhau.setText(nhanVien.getMatKhau());
    }

    public NhanVien getUpdatedNhanVien() { return nhanVien; }

    public void btnLuu(ActionEvent actionEvent) {
        if (!kiemTraTrungTaiKhoan()) return;

        if (nhanVien == null) nhanVien = new NhanVien(); // CHỐT chống NPE
        nhanVien.setTaiKhoan(txtTaiKhoan.getText().trim());
        nhanVien.setMatKhau(txtMatKhau.getText());

        isSaved = true;
        dong();
    }

    public void btnHuy(ActionEvent actionEvent) {
        isSaved = false;
        dong();
    }

    private void dong() {
        Stage stage = (Stage) txtTaiKhoan.getScene().getWindow();
        stage.close();
    }

    public boolean kiemTraTrungTaiKhoan() {
        String taiKhoanNhap = (txtTaiKhoan.getText() == null) ? "" : txtTaiKhoan.getText().trim();
        String matKhauNhap  = (txtMatKhau.getText() == null) ? "" : txtMatKhau.getText();

        if (taiKhoanNhap.isEmpty())  { showErr("Tài khoản không được để trống!"); return false; }
        if (matKhauNhap.isEmpty())   { showErr("Mật khẩu không được để trống!");  return false; }
        if (matKhauNhap.length() < 6){ showErr("Mật khẩu phải có ít nhất 6 ký tự!"); return false; }

        listNhanVien = nhanVien_Dao.selectAll();
        for (NhanVien nv : listNhanVien) {
            String tk = nv.getTaiKhoan();
            if (tk != null && tk.equals(taiKhoanNhap)) {
                showErr("Tài khoản đã tồn tại!");
                return false;
            }
        }
        return true;
    }

    private void showErr(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Lỗi");
        a.setHeaderText(null);
        a.setContentText(msg);
        if (txtTaiKhoan != null && txtTaiKhoan.getScene() != null) {
            a.initOwner(txtTaiKhoan.getScene().getWindow());
        }
        a.showAndWait();
    }
}
