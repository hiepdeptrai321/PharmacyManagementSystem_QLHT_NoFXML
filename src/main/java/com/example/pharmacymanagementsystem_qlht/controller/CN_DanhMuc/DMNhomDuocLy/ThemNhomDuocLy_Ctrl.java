package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhomDuocLy;

import com.example.pharmacymanagementsystem_qlht.dao.NhomDuocLy_Dao;
import com.example.pharmacymanagementsystem_qlht.model.NhomDuocLy;
// Import file GUI mới
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhomDuocLy.ThemNhomDuocLy_GUI;
import javafx.application.Application;
import javafx.fxml.FXML; // Giữ nguyên FXML
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ThemNhomDuocLy_Ctrl extends Application {
    // *** THAY ĐỔI 1: Bỏ 'private' và giữ nguyên '@FXML' (hoặc bỏ '@FXML' cũng được) ***
    @FXML
    public Button btnHuy;

    @FXML
    public Button btnThem;

    @FXML
    public TextArea txtMoTa;

    @FXML
    public TextField txtTenNhomDuocLy;

    private NhomDuocLy_Dao nhomDuocLyDao = new NhomDuocLy_Dao();


    @FXML
    public void initialize() {
        // HÀM NÀY GIỮ NGUYÊN 100%
        btnThem.setOnAction(e -> themNDL());
        btnHuy.setOnAction(e -> btnHuyClick());
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // *** THAY ĐỔI 2: Thay thế 2 dòng FXMLLoader ***
        // Parent root = FXMLLoader.load(getClass().getResource(".../ThemNhomDuocLy.fxml"));

        ThemNhomDuocLy_GUI gui = new ThemNhomDuocLy_GUI();
        Parent root = gui.createContent(this); // 'this' là controller, giao diện sẽ bơm component vào

        // *** THAY ĐỔI 3: Gọi initialize() bằng tay ***
        initialize();

        // Phần còn lại giữ nguyên
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemNhaCungCap.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    public void btnHuyClick(){
        Stage stage = (Stage) txtTenNhomDuocLy.getScene().getWindow();
        stage.close();
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    @FXML
    void themNDL() {
        String maNDL = nhomDuocLyDao.generateNewMaNhomDL();
        String tenNDL = txtTenNhomDuocLy.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (tenNDL.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng nhập tên nhóm dược lý!");
            return;
        }

        NhomDuocLy nhomDuocLy = new NhomDuocLy(maNDL, tenNDL, moTa);
        boolean success = nhomDuocLyDao.insert(nhomDuocLy);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Thêm nhóm dược lý thành công!\nMã: " + maNDL);
            txtTenNhomDuocLy.clear();
            txtMoTa.clear();
            dongCuaSo();
        } else {
            showAlert(Alert.AlertType.ERROR, "Thêm nhoóm dược lý thất bại!");
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
        Stage stage = (Stage) txtTenNhomDuocLy.getScene().getWindow();
        stage.close();
    }
}