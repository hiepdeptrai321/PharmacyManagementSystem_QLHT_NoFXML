package com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuNhapHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc.DanhMucThuoc_Ctrl;
import com.example.pharmacymanagementsystem_qlht.dao.*;
import com.example.pharmacymanagementsystem_qlht.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NhapHangBangExcel_Ctrl {

    public Label lblThongTinFile;
    public ImageView btnXoa;
    public Button btnLuu;
    private LapPhieuNhapHang_Ctrl lapPhieuNhapHangCtrl;

//    public List<Thuoc_SanPham> danhSachThuoc = new ArrayList<>();

    //  ==================================================================Khởi tạo
    public void initialize() {
        lblThongTinFile.setText("Kéo & thả file Excel vào đây");
        btnXoa.setVisible(false);
    }

    public void setLapPhieuNhapHangCtrl(LapPhieuNhapHang_Ctrl ctrl){
        this.lapPhieuNhapHangCtrl = ctrl;
    }

    //  ==================================================================hàm xử lý
    //  Thả file dữ liệu
    public void thaFileDuLieu(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;

        if (db.hasFiles()) {
            success = true;
            for (File file : db.getFiles()) {
                if (file.getName().endsWith(".xlsx") || file.getName().endsWith(".xls")) {
                    try {
                        lblThongTinFile.setText("File đã tải: " + file.getName());
                        btnXoa.setVisible(true);
                        try (FileInputStream fis = new FileInputStream(file)) {
                            lblThongTinFile.setText("File đã chọn: " + file.getName());
                            btnXoa.setVisible(true);
//                            importExcel(file);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Vui lòng chọn file Excel hợp lệ (.xlsx hoặc .xls)");
                    alert.showAndWait();
                }
            }
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }

    //  Kéo file dữ liệu
    public void onDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    }

    //  Chọn file dữ liệu
    public void btnChonTapTinClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn file Excel");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls")
        );

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (FileInputStream fis = new FileInputStream(file)) {
                lblThongTinFile.setText("File đã chọn: " + file.getName());
                btnXoa.setVisible(true);
//                importExcel(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //  Xóa file đã chọn
    public void btnXoaClick(MouseEvent mouseEvent) {
        lblThongTinFile.setText("Kéo & thả file Excel vào đây");
        btnXoa.setVisible(false);
//        danhSachThuoc.clear();
    }

    //  Nhập dữ liệu từ file Excel
    public void importExcel(File excelFile) {
        try {
            FileInputStream fis = new FileInputStream(excelFile);
            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet();
            int count = 0;

            for(Row row : sheet) {

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //  Lưu dữ liệu vào database
    public void btnLuuClick(ActionEvent actionEvent) {
//        Lấy root hiện tại
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        AnchorPane root = (AnchorPane) scene.getRoot();
//      Tạo overlay làm mờ nền
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.4);");
        ProgressIndicator progress = new ProgressIndicator();
        overlay.getChildren().add(progress);

//      Căn overlay phủ toàn màn hình
        AnchorPane.setTopAnchor(overlay, 0.0);
        AnchorPane.setRightAnchor(overlay, 0.0);
        AnchorPane.setBottomAnchor(overlay, 0.0);
        AnchorPane.setLeftAnchor(overlay, 0.0);

//      Thêm overlay vào AnchorPane
        root.getChildren().add(overlay);

//      Tạo luồng riêng để xử lý cập nhật (tránh lag UI)
        new Thread(() -> {

            // Quay lại luồng giao diện để loại bỏ overlay
            Platform.runLater(() -> {
                root.getChildren().remove(overlay);
                stage.close();
            });
        }).start();

    }

    //  Tải file mẫu
    public void btnTaiTapTinMauClick(ActionEvent actionEvent) {
        try (InputStream inputStream = getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/file_Excel/MauThemThuoc.xlsx")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Không tìm thấy file mẫu trong resources!");
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Lưu file mẫu");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
            );
            fileChooser.setInitialFileName("MauThemThuoc.xlsx");

            File destFile = fileChooser.showSaveDialog(null);
            if (destFile != null) {
                Files.copy(inputStream, destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Không thể tải file mẫu: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
