package com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuNhapHang;

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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class NhapHangBangExcel_Ctrl {

    public Label lblThongTinFile;
    public ImageView btnXoa;
    public Button btnLuu;
    private LapPhieuNhapHang_Ctrl lapPhieuNhapHangCtrl;
    public List<CTPN_TSPTL_CHTDVT> danhSachThuocNhapHang = new ArrayList<>();

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
                            importExcel(file);
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
                importExcel(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //  Xóa file đã chọn
    public void btnXoaClick(MouseEvent mouseEvent) {
        lblThongTinFile.setText("Kéo & thả file Excel vào đây");
        btnXoa.setVisible(false);
        danhSachThuocNhapHang.clear();
    }

    //  Nhập dữ liệu từ file Excel
    public void importExcel(File excelFile) {
        try (FileInputStream fis = new FileInputStream(excelFile);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                if (row.getCell(0) == null) continue;

                CTPN_TSPTL_CHTDVT ct = new CTPN_TSPTL_CHTDVT();

                Thuoc_SanPham thuoc = new Thuoc_SanPham();
                DataFormatter fmt = new DataFormatter();
                thuoc.setTenThuoc(fmt.formatCellValue(row.getCell(0)));
                thuoc.setHamLuong((float) row.getCell(1).getNumericCellValue());
                thuoc.setDonViHamLuong(fmt.formatCellValue(row.getCell(2)));
                thuoc.setDuongDung(fmt.formatCellValue(row.getCell(3)));
                thuoc.setQuyCachDongGoi(fmt.formatCellValue(row.getCell(4)));
                thuoc.setSDK_GPNK(fmt.formatCellValue(row.getCell(5)));
                thuoc.setHangSX(fmt.formatCellValue(row.getCell(6)));
                thuoc.setNuocSX(fmt.formatCellValue(row.getCell(7)));

                Thuoc_SP_TheoLo lo = new Thuoc_SP_TheoLo();
                lo.setThuoc(thuoc);
                lo.setNsx(new java.sql.Date(row.getCell(8).getDateCellValue().getTime()));
                lo.setHsd(new java.sql.Date(row.getCell(9).getDateCellValue().getTime()));

                DonViTinh dvt = new DonViTinh();
                dvt.setTenDonViTinh(fmt.formatCellValue(row.getCell(10)));

                ChiTietDonViTinh ctDVT = new ChiTietDonViTinh();
                ctDVT.setThuoc(thuoc);
                ctDVT.setDvt(dvt);
                ctDVT.setGiaNhap(row.getCell(12).getNumericCellValue());

                ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
                ctpn.setThuoc(thuoc);
                ctpn.setSoLuong((int) row.getCell(11).getNumericCellValue());
                ctpn.setGiaNhap(row.getCell(12).getNumericCellValue());
                ctpn.setChietKhau((float) row.getCell(13).getNumericCellValue());
                ctpn.setThue((float) row.getCell(14).getNumericCellValue());

                ct.setChiTietSP_theoLo(lo);
                ct.setChiTietDonViTinh(ctDVT);
                ct.setChiTietPhieuNhap(ctpn);

                danhSachThuocNhapHang.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            for(CTPN_TSPTL_CHTDVT ct : danhSachThuocNhapHang) {
                System.out.println(ct.toString());
            }
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
