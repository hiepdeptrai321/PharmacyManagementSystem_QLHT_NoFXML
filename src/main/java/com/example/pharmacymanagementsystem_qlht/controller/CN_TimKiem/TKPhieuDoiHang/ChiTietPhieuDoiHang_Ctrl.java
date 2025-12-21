package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDoiHang;

import com.example.pharmacymanagementsystem_qlht.dao.ChiTietPhieuDoiHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuDoiHang;
import com.example.pharmacymanagementsystem_qlht.model.PhieuDoiHang;
import com.example.pharmacymanagementsystem_qlht.TienIch.DoiNgay;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDoi.ChiTietPhieuDoiHang_GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;

public class ChiTietPhieuDoiHang_Ctrl extends Application {
    public Button btnDong;
    public Button btnInPhieuDoi;
    public TableColumn<ChiTietPhieuDoiHang, String> colLyDo;
    public TableColumn<ChiTietPhieuDoiHang, String> colSTT;
    public TableColumn<ChiTietPhieuDoiHang, String> colSoLuong;
    public TableColumn<ChiTietPhieuDoiHang, String> colDonVi;
    public TableColumn<ChiTietPhieuDoiHang, String> colTenSP;


    public Label lblGhiChuValue;
    public Label lblMaPhieuDoiValue;
    public Label lblNgayLapValue;
    public Label lblSDTKhachHangValue;
    public Label lblTenKhachHangValue;
    public Label lblTenNhanVienValue;

    public TableView<ChiTietPhieuDoiHang> tblChiTietPhieuDoi;

    private PhieuDoiHang phieuDoiHang;
    @Override
    public void start(Stage stage) throws Exception {
        ChiTietPhieuDoiHang_GUI gui = new ChiTietPhieuDoiHang_GUI();
        gui.showWithController(stage, this);
    }
    public void initialize() {
        btnDong.setOnAction(e -> ((Stage) btnDong.getScene().getWindow()).close());
        Platform.runLater(()->{
            Stage dialog = (Stage) btnDong.getScene().getWindow();
            dialog.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
        });
        btnInPhieuDoi.setOnAction(e -> xuLyXuatPDFDoiHang());

    }



    public void setPhieuDoiHang(PhieuDoiHang pDoi) {
        this.phieuDoiHang = pDoi;
        hienThiThongTin();
    }
    private void hienThiThongTin() {
        if (phieuDoiHang != null) {
            lblMaPhieuDoiValue.setText(phieuDoiHang.getMaPD());
            lblNgayLapValue.setText(DoiNgay.dinhDangThoiGian(phieuDoiHang.getNgayLap()));
            lblTenNhanVienValue.setText(phieuDoiHang.getNhanVien().getTenNV());
            lblTenKhachHangValue.setText(phieuDoiHang.getKhachHang() != null ? phieuDoiHang.getKhachHang().getTenKH() : "Khách lẻ");
            lblSDTKhachHangValue.setText(phieuDoiHang.getKhachHang() != null ? phieuDoiHang.getKhachHang().getSdt() : "");
            lblGhiChuValue.setText(phieuDoiHang.getGhiChu() != null ? phieuDoiHang.getGhiChu() : "");
            //tblChiTietPhieuDoi.setItems(phieuDoiHang.getChiTietPhieuDoiHang());

            List<ChiTietPhieuDoiHang> list = new ChiTietPhieuDoiHang_Dao().getChiTietPhieuDoiByMaPT(phieuDoiHang.getMaPD());
            tblChiTietPhieuDoi.getItems().clear();
            tblChiTietPhieuDoi.getItems().addAll(list);

            colSTT.setCellValueFactory(cellData ->
                    new SimpleStringProperty(String.valueOf(tblChiTietPhieuDoi.getItems().indexOf(cellData.getValue()) + 1))
            );
            colTenSP.setCellValueFactory(cel -> new SimpleStringProperty(cel.getValue().getThuoc().getTenThuoc())
            );
            colSoLuong.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSoLuong()))
            );
            colDonVi.setCellValueFactory(cel -> {
                if (cel.getValue().getDvt() != null && cel.getValue().getDvt().getTenDonViTinh() != null) {
                    return new SimpleStringProperty(cel.getValue().getDvt().getTenDonViTinh());
                }
                return new SimpleStringProperty("");
            });
            colLyDo.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getLyDoDoi())
            );
        }
    }

    public void xuLyXuatPDFDoiHang() {

        if (phieuDoiHang == null || tblChiTietPhieuDoi.getItems().isEmpty()) {
            com.example.pharmacymanagementsystem_qlht.TienIch.TuyChinhAlert.hien(
                    javafx.scene.control.Alert.AlertType.INFORMATION,
                    "Không có dữ liệu",
                    "Không có phiếu đổi hàng để xuất PDF."
            );
            return;
        }

        javafx.stage.FileChooser chooser = new javafx.stage.FileChooser();
        chooser.setTitle("Lưu Phiếu Đổi Hàng PDF");
        chooser.setInitialFileName(
                "PhieuDoi_" + phieuDoiHang.getMaPD() + "_" + java.time.LocalDate.now()
        );
        chooser.getExtensionFilters()
                .add(new javafx.stage.FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf"));

        Stage stage = (Stage) btnInPhieuDoi.getScene().getWindow();
        java.io.File file = chooser.showSaveDialog(stage);

        if (file != null) {
            try {
                xuatPhieuDoiPDF(file);
                com.example.pharmacymanagementsystem_qlht.TienIch.TuyChinhAlert.hien(
                        javafx.scene.control.Alert.AlertType.INFORMATION,
                        "Thành công",
                        "Xuất PDF phiếu đổi hàng thành công!"
                );
            } catch (Exception e) {
                e.printStackTrace();
                com.example.pharmacymanagementsystem_qlht.TienIch.TuyChinhAlert.hien(
                        javafx.scene.control.Alert.AlertType.ERROR,
                        "Lỗi",
                        "Xuất PDF thất bại: " + e.getMessage()
                );
            }
        }
    }
    private void xuatPhieuDoiPDF(java.io.File file) throws Exception {

        com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(file);
        com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf);

        // ===== FONT =====
        com.itextpdf.kernel.font.PdfFont font;
        try {
            font = com.itextpdf.kernel.font.PdfFontFactory.createFont(
                    com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon.ChiTietHoaDon_Ctrl.FONT_PATH,
                    com.itextpdf.io.font.PdfEncodings.IDENTITY_H
            );
        } catch (Exception e) {
            font = com.itextpdf.kernel.font.PdfFontFactory.createFont(
                    com.itextpdf.io.font.constants.StandardFonts.HELVETICA
            );
        }
        document.setFont(font);

        // ===== LOGO =====
        try (java.io.InputStream is = getClass().getResourceAsStream(
                "/com/example/pharmacymanagementsystem_qlht/img/logo.png")) {
            if (is != null) {
                com.itextpdf.layout.element.Image logo =
                        new com.itextpdf.layout.element.Image(
                                com.itextpdf.io.image.ImageDataFactory.create(is.readAllBytes())
                        );
                logo.scaleToFit(120, 120);
                logo.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);
                document.add(logo);
            }
        }

        // ===== HEADER =====
        document.add(new com.itextpdf.layout.element.Paragraph("QUỐC KHÁNH PHARMACY")
                .setBold().setFontSize(18)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER));

        document.add(new com.itextpdf.layout.element.Paragraph("PHIẾU ĐỔI HÀNG")
                .setBold().setFontSize(18)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER));

        document.add(new com.itextpdf.layout.element.Paragraph(
                "Mã phiếu đổi: " + phieuDoiHang.getMaPD())
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER));

        document.add(new com.itextpdf.layout.element.Paragraph(
                "Ngày lập: " + DoiNgay.dinhDangThoiGian(phieuDoiHang.getNgayLap()))
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER));

        // ===== INFO =====
        document.add(new com.itextpdf.layout.element.Paragraph("\nTHÔNG TIN KHÁCH HÀNG")
                .setBold());

        document.add(new com.itextpdf.layout.element.Paragraph(
                "Khách hàng: " +
                        (phieuDoiHang.getKhachHang() != null
                                ? phieuDoiHang.getKhachHang().getTenKH()
                                : "Khách lẻ")));

        document.add(new com.itextpdf.layout.element.Paragraph(
                "SĐT: " +
                        (phieuDoiHang.getKhachHang() != null
                                ? phieuDoiHang.getKhachHang().getSdt()
                                : "")));

        document.add(new com.itextpdf.layout.element.Paragraph(
                "Nhân viên lập: " + phieuDoiHang.getNhanVien().getTenNV()));

        // ===== TABLE =====
        document.add(new com.itextpdf.layout.element.Paragraph("\nCHI TIẾT ĐỔI HÀNG")
                .setBold());

        float[] widths = {1, 4, 2, 2, 3};
        com.itextpdf.layout.element.Table table =
                new com.itextpdf.layout.element.Table(
                        com.itextpdf.layout.properties.UnitValue.createPercentArray(widths))
                        .setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(100));

        addHeader(table, "STT");
        addHeader(table, "Tên thuốc");
        addHeader(table, "Số lượng");
        addHeader(table, "Đơn vị");
        addHeader(table, "Lý do đổi");

        int stt = 1;
        for (ChiTietPhieuDoiHang ct : tblChiTietPhieuDoi.getItems()) {
            table.addCell(String.valueOf(stt++));
            table.addCell(ct.getThuoc().getTenThuoc());
            table.addCell(String.valueOf(ct.getSoLuong()))
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            table.addCell(
                    ct.getDvt() != null ? ct.getDvt().getTenDonViTinh() : ""
            ).setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            table.addCell(ct.getLyDoDoi());
        }

        document.add(table);

        // ===== FOOTER =====
        document.add(new com.itextpdf.layout.element.Paragraph("\n\nNgười lập phiếu")
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.RIGHT));
        document.add(new com.itextpdf.layout.element.Paragraph(
                phieuDoiHang.getNhanVien().getTenNV())
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.RIGHT)
                .setItalic());

        document.close();
    }
    private void addHeader(com.itextpdf.layout.element.Table table, String text) {
        table.addHeaderCell(
                new com.itextpdf.layout.element.Cell()
                        .add(new com.itextpdf.layout.element.Paragraph(text).setBold())
                        .setBackgroundColor(new com.itextpdf.kernel.colors.DeviceRgb(230, 230, 230))
                        .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
        );
    }
    public void xuLyDong() {
        Stage stage = (Stage) btnDong.getScene().getWindow();
        stage.close();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
