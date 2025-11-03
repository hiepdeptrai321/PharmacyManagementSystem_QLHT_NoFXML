package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang;

import com.example.pharmacymanagementsystem_qlht.dao.KeHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KeHang;
// Import file GUI mới
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.ThemKe_GUI;
import javafx.application.Application;
import javafx.fxml.FXML; // Giữ nguyên
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ThemKe_Ctrl extends Application {
    // *** THAY ĐỔI 1: Bỏ 'private' để GUI có thể bơm component vào ***
    @FXML
    public Button btnHuy;

    @FXML
    public Button btnThem;

    @FXML
    public TextArea txtMoTa;

    @FXML
    public TextField txtTenKe;

    private KeHang_Dao keHangDao = new KeHang_Dao(); //


    // HÀM NÀY GIỮ NGUYÊN 100%
    @FXML
    public void initialize() {
        btnThem.setOnAction(e -> themKe());
        btnHuy.setOnAction(e -> btnHuyClick());
    }

    @Override
    public void start(Stage stage) throws IOException {
        // *** THAY ĐỔI 2: Thay thế FXMLLoader ***
        // Parent root = FXMLLoader.load(getClass().getResource(".../ThemKe.fxml")); //

        ThemKe_GUI gui = new ThemKe_GUI();
        Parent root = gui.createContent(this); // 'this' là controller

        // *** THAY ĐỔI 3: Gọi initialize() bằng tay ***
        initialize();

        // Phần còn lại giữ nguyên
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemNhaCungCap.css").toExternalForm()); //
        stage.setScene(scene);
        stage.show();
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    public void btnHuyClick(){
        Stage stage = (Stage) txtTenKe.getScene().getWindow();
        stage.close();
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    @FXML
    void themKe() {
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

    // HÀM NÀY GIỮ NGUYÊN 100%
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    private void dongCuaSo() {
        Stage stage = (Stage) txtTenKe.getScene().getWindow();
        stage.close();
    }
}