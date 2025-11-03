package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang;

import com.example.pharmacymanagementsystem_qlht.dao.KeHang_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SanPham_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KeHang;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class XoaSuaKeHang_Ctrl extends Application {

    // ĐÃ CHUYỂN SANG PUBLIC
    public Pane btnLuu;
    public Pane btnXoa;
    public TextArea txtMota;
    public TextField txtTenKe;

    private KeHang_Dao keHangDao = new KeHang_Dao();
    private Thuoc_SanPham_Dao thuocDao = new Thuoc_SanPham_Dao();
    private KeHang keHangHienTai;

    @Override
    public void start(Stage stage) throws IOException {
        // Thay đổi để gọi GUI mới
        new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.XoaSuaKeHang_GUI()
                .showWithController(stage, this);

        // Cần thêm stage.show() nếu bạn chạy file này độc lập
        stage.setTitle("Chi tiết kệ hàng");
        stage.show();
    }

    // Logic của bạn được giữ nguyên
    public void hienThiThongTin(KeHang kh) {
        if (kh != null) {
            keHangHienTai = kh;
            this.keHangHienTai = kh;


        }
    }

    // HÀM INITIALIZE ĐÃ CẬP NHẬT
    public void initialize() {
        // 1. Gán sự kiện
        btnLuu.setOnMouseClicked(event -> luuKeHang());
        btnXoa.setOnMouseClicked(event -> xoaKeHang());

        // 2. Gán dữ liệu (được gọi SAU KHI `hienThiThongTin` đã lưu keHangHienTai)
        if (keHangHienTai != null) {
            txtTenKe.setText(keHangHienTai.getTenKe());
            txtMota.setText(keHangHienTai.getMoTa() != null ? keHangHienTai.getMoTa() : "");
        }
    }

    // Logic của bạn được giữ nguyên
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

    // Logic của bạn được giữ nguyên
    private void xoaKeHang() {
        if (keHangHienTai == null) {
            showAlert("Lỗi", "Không có dữ liệu kệ hàng để xóa!", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Lấy danh sách thuốc còn trong kệ
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

            // Thực hiện xóa
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

    // Logic của bạn được giữ nguyên
    private void showThuocConTrongKe(List<String> thuocTrongKe) {
        StringBuilder sb = new StringBuilder("Kệ này vẫn còn các thuốc sau:\n\n");
        for (String tenThuoc : thuocTrongKe) {
            sb.append("- ").append(tenThuoc).append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Không thể xóa kệ hàng");
        alert.setHeaderText("Không thể xóa! Kệ này vẫn còn thuốc.");

        // Tạo TextArea có scrollbar
        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefWidth(400);
        textArea.setPrefHeight(250);

        alert.getDialogPane().setContent(textArea);

        alert.showAndWait();
    }

    // Logic của bạn được giữ nguyên
    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // Logic của bạn được giữ nguyên
    private void dongCuaSo() {
        Stage stage = (Stage) txtTenKe.getScene().getWindow();
        stage.close();
    }
}