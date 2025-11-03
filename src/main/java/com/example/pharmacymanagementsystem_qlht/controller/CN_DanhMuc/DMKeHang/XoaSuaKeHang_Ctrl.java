package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang;

import com.example.pharmacymanagementsystem_qlht.dao.KeHang_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SanPham_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KeHang;
// Import file GUI mới
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.XoaSuaKeHang_GUI;
import javafx.application.Application;
import javafx.fxml.FXML; // Giữ nguyên
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class XoaSuaKeHang_Ctrl extends Application {

    // *** THAY ĐỔI 1: Bỏ 'private' ***
    @FXML
    public Pane btnLuu;

    @FXML
    public Pane btnXoa;

    @FXML
    public TextArea txtMota;

    @FXML
    public TextField txtTenKe;

    private KeHang_Dao keHangDao = new KeHang_Dao(); //
    private Thuoc_SanPham_Dao thuocDao = new Thuoc_SanPham_Dao(); //
    private KeHang keHangHienTai; //


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // *** THAY ĐỔI 2: Thay thế FXMLLoader ***
        // Parent root = FXMLLoader.load(getClass().getResource(".../XoaSuakeHang.fxml")); //

        XoaSuaKeHang_GUI gui = new XoaSuaKeHang_GUI();
        Parent root = gui.createContent(this); // Bơm component vào 'this'

        // *** THAY ĐỔI 3: Gọi initialize() bằng tay ***
        initialize();

        // Phần còn lại giữ nguyên
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemNhaCungCap.css").toExternalForm()); //
        stage.setScene(scene);
        stage.show();
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    public void initialize() {
        btnLuu.setOnMouseClicked(event -> luuKeHang());
        btnXoa.setOnMouseClicked(event -> xoaKeHang());
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    public void hienThiThongTin(KeHang kh) {
        if (kh != null) {
            keHangHienTai = kh;
            txtTenKe.setText(kh.getTenKe());
            txtMota.setText(kh.getMoTa() != null ? kh.getMoTa() : "");
        }
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    private void luuKeHang() {
        if (keHangHienTai == null) {
            showAlert("Lỗi", "Không có dữ liệu kệ hàng để cập nhật!", Alert.AlertType.ERROR);
            return;
        }
        String tenKe = txtTenKe.getText().trim();
        String moTa = txtMota.getText().trim();

        if (tenKe.isEmpty()) {
            showAlert("Lỗi", "Tên kệ không được để trống!", Alert.AlertType.ERROR);
            return;
        }

        keHangHienTai.setTenKe(tenKe);
        keHangHienTai.setMoTa(moTa);

        boolean success = keHangDao.update(keHangHienTai);
        if (success) {
            showAlert("Thành công", "Cập nhật kệ hàng thành công!", Alert.AlertType.INFORMATION);
            dongCuaSo();
        } else {
            showAlert("Thất bại", "Không thể cập nhật kệ hàng!", Alert.AlertType.ERROR);
        }
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    private void xoaKeHang() {
        if (keHangHienTai == null) {
            showAlert("Lỗi", "Không có dữ liệu kệ hàng để xóa!", Alert.AlertType.ERROR);
            return;
        }
        try {
            List<String> thuocTrongKe = thuocDao.layDanhSachThuocTheoKe(keHangHienTai.getMaKe());

            if (thuocTrongKe != null && !thuocTrongKe.isEmpty()) {
                showThuocConTrongKe(thuocTrongKe);
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Xác nhận");
            confirm.setHeaderText("Bạn có chắc muốn xóa kệ này?");
            confirm.setContentText("Tên kệ: " + keHangHienTai.getTenKe());
            if (confirm.showAndWait().orElse(null) != ButtonType.OK) {
                return;
            }

            boolean success = keHangDao.deleteById(keHangHienTai.getMaKe());
            if (success) {
                showAlert("Thành công", "Đã xóa kệ hàng thành công!", Alert.AlertType.INFORMATION);
                dongCuaSo();
            } else {
                showAlert("Thất bại", "Không thể xóa kệ hàng!", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi hệ thống", "Đã xảy ra lỗi khi xóa: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    private void showThuocConTrongKe(List<String> thuocTrongKe) {
        StringBuilder sb = new StringBuilder("Kệ này vẫn còn các thuốc sau:\n\n");
        for (String tenThuoc : thuocTrongKe) {
            sb.append("- ").append(tenThuoc).append("\n");
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Không thể xóa kệ hàng");
        alert.setHeaderText("Không thể xóa! Kệ này vẫn còn thuốc.");
        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefWidth(400);
        textArea.setPrefHeight(250);
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    private void dongCuaSo() {
        Stage stage = (Stage) txtTenKe.getScene().getWindow();
        stage.close();
    }
}