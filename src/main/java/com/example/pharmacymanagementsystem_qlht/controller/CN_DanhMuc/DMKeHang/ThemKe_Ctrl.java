package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang;

import com.example.pharmacymanagementsystem_qlht.dao.KeHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KeHang;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ThemKe_Ctrl extends Application {
    // ĐÃ CHUYỂN SANG PUBLIC
    public Button btnHuy;
    public Button btnThem;
    public TextArea txtMoTa;
    public TextField txtTenKe;

    private KeHang_Dao keHangDao = new KeHang_Dao();

    // Gán sự kiện, được gọi bởi file GUI
    public void initialize() {
        btnThem.setOnAction(e -> themKe());
        btnHuy.setOnAction(e -> btnHuyClick());
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Thay đổi để gọi GUI mới
        new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.ThemKe_GUI()
                .showWithController(stage, this);

        // Cần thêm stage.show() nếu bạn chạy file này độc lập
        stage.setTitle("Thêm kệ hàng");
        stage.show();
    }

    // Logic của bạn được giữ nguyên
    public void btnHuyClick(){
        Stage stage = (Stage) txtTenKe.getScene().getWindow();
        stage.close();
    }

    // Logic của bạn được giữ nguyên
    void themKe() {
        // Sinh mã tự động
        String maKe = keHangDao.generateNewMaKeHang();
        String tenKe = txtTenKe.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (tenKe.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng nhập tên kệ hàng!");
            return;
        }

        KeHang keHang = new KeHang(maKe, tenKe, moTa);
        boolean success = keHangDao.insert(keHang);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Thêm kệ hàng thành công!\nMã: " + maKe);
            txtTenKe.clear();
            txtMoTa.clear();
            dongCuaSo();
        } else {
            showAlert(Alert.AlertType.ERROR, "Thêm kệ hàng thất bại!");
        }
    }

    // Logic của bạn được giữ nguyên
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Logic của bạn được giữ nguyên
    private void dongCuaSo() {
        Stage stage = (Stage) txtTenKe.getScene().getWindow();
        stage.close();
    }
}