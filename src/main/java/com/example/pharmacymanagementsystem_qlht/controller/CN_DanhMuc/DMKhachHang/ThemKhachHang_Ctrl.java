package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhachHang;

import com.example.pharmacymanagementsystem_qlht.dao.KhachHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML; // (Có thể giữ lại)
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThemKhachHang_Ctrl {

    // Các control được inject bởi GUI Java
    public TextField txtTenKH;
    public DatePicker dpNgaySinh;
    public TextField txtSDT;
    public TextField txtEmail;
    public TextField txtDiaChi;
    public ComboBox<String> cbGioiTinh;
    public Label lblMessage;
    public Button btnThem;
    public Button btnHuy;

    public Label errTenKH;
    public Label errNgaySinh;
    public Label errSDT;
    public Label errEmail;
    public Label errDiaChi;
    public Label errGioiTinh;

    private final KhachHang_Dao khDao = new KhachHang_Dao();
    private Consumer<KhachHang> onSaved;

    public void setOnSaved(Consumer<KhachHang> onSaved) {
        this.onSaved = onSaved;
    }

    // Gọi từ GUI sau khi inject control
    public void init() {
        cbGioiTinh.getItems().addAll("Nam", "Nữ");
        clearErrors();

        btnThem.setOnAction(e -> handleSave());
        btnHuy.setOnAction(e -> handleCancel());
    }

    private void clearErrors() {
        errTenKH.setText("");
        errTenKH.setVisible(false);
        errNgaySinh.setText("");
        errNgaySinh.setVisible(false);
        errSDT.setText("");
        errSDT.setVisible(false);
        errEmail.setText("");
        errEmail.setVisible(false);
        errDiaChi.setText("");
        errDiaChi.setVisible(false);
        errGioiTinh.setText("");
        errGioiTinh.setVisible(false);

        lblMessage.setText("");
    }

    private void setError(Label lbl, String msg) {
        lbl.setText(msg);
        lbl.setVisible(true);
    }

    public void handleSave() {
        clearErrors();

        String ten = txtTenKH.getText().trim();
        String sdt = txtSDT.getText().trim();
        String email = txtEmail.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        LocalDate ns = dpNgaySinh.getValue();
        String gioiTinh = cbGioiTinh.getValue();

        boolean valid = true;

        if (ten.isEmpty()) { setError(errTenKH, "Không được bỏ trống"); valid = false; }
        if (gioiTinh == null) { setError(errGioiTinh, "Chọn giới tính"); valid = false; }

        if (ns == null) { setError(errNgaySinh, "Chọn ngày sinh"); valid = false; }

        if (!sdt.matches("\\d{10}")) { setError(errSDT, "SĐT phải 10 số"); valid = false; }

        if (email.isEmpty()) { setError(errEmail, "Không được rỗng"); valid = false; }
        else if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            setError(errEmail, "Email không hợp lệ");
            valid = false;
        }

        if (diaChi.isEmpty()) { setError(errDiaChi, "Không được rỗng"); valid = false; }

        if (!valid) return;

        KhachHang kh = new KhachHang();
        kh.setMaKH(khDao.generateNewMaKH());
        kh.setTenKH(ten);
        kh.setSdt(sdt);
        kh.setEmail(email);
        kh.setDiaChi(diaChi);
        kh.setNgaySinh(ns);
        kh.setGioiTinh(gioiTinh.equals("Nam"));
        kh.setTrangThai(true);

        if (!khDao.insert(kh)) {
            lblMessage.setText("Lỗi cơ sở dữ liệu");
            return;
        }

        if (onSaved != null)
            onSaved.accept(kh);

        Stage st = (Stage) btnThem.getScene().getWindow();
        st.close();
    }

    public void handleCancel() {
        Stage st = (Stage) btnHuy.getScene().getWindow();
        st.close();
    }
}