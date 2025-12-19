package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuTraHang;

import com.example.pharmacymanagementsystem_qlht.dao.ChiTietHoaDon_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.ChiTietPhieuTraHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.*;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Image;

import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.io.image.ImageDataFactory;

import com.itextpdf.io.font.constants.StandardFonts;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.example.pharmacymanagementsystem_qlht.TienIch.TuyChinhAlert.hien;
import static com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon.ChiTietHoaDon_Ctrl.FONT_PATH;
import static com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon.ChiTietHoaDon_Ctrl.formatVNDTable;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class ChiTietPhieuTraHang_Ctrl {

    public PhieuTraHang phieuTraHang;
    public TableColumn<ChiTietPhieuTraHang, String> colDonGia;
    public Button btnDong;
    public Button btnInPhieuTra;
    public Label lblTongTienHDGocValue;
    public Label lblSoTienTraLaiValue;


    public TableColumn<ChiTietPhieuTraHang, String> colLyDo;
    public TableColumn<ChiTietPhieuTraHang, String> colSTT;
    public TableColumn<ChiTietPhieuTraHang, String> colSoLuong;
    public TableColumn<ChiTietPhieuTraHang, String> colTenSP;
    public TableColumn<ChiTietPhieuTraHang, Double> colThanhTien;
    public Label lblGhiChuValue;
    public Label lblMaPhieuTraValue;
    public Label lblNgayLapValue;
    public Label lblSDTKH;
    public Label lblSDTKhachHangValue;
    public Label lblTenKH;
    public Label lblTenKhachHangValue;
    public Label lblTenNV;
    public Label lblTenNhanVienValue;
    public TableView<ChiTietPhieuTraHang> tblChiTietPhieuTra;
    private boolean uiReady = false;

    public void initialize() {
        btnDong.setOnAction(e -> ((Stage) btnDong.getScene().getWindow()).close());
        btnInPhieuTra.setOnAction(e -> xuLyXuatPDFPhieuTra());
    }
//    public void setPhieuTraHang(PhieuTraHang pTra) {
//        this.phieuTraHang = pTra;
//        hienThiThongTin();
   // }
    public void onUIReady() {
        uiReady = true;
        tryRender();
    }
    public void setPhieuTraHang(PhieuTraHang pTra) {
        this.phieuTraHang = pTra;
        tryRender();
    }
    private void tryRender() {
        if (!uiReady || phieuTraHang == null) return;
        hienThiThongTin();
    }
    private void hienThiThongTin() {
        if (phieuTraHang != null) {

            HoaDon hdGoc = phieuTraHang.getHoaDon();
            List<ChiTietHoaDon> dsCTHDGoc =
                    new ChiTietHoaDon_Dao().selectByMaHD(hdGoc.getMaHD());
        lblMaPhieuTraValue.setText(phieuTraHang.getMaPT());

            Timestamp ngayLapTimestamp = phieuTraHang.getNgayLap();

            if (ngayLapTimestamp != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String ngayLapFormatted = formatter.format(ngayLapTimestamp);
                lblNgayLapValue.setText(ngayLapFormatted);
            } else {
                lblNgayLapValue.setText("Không rõ");
            }
            lblTenNhanVienValue.setText(
                    phieuTraHang.getNhanVien() != null
                            ? phieuTraHang.getNhanVien().getTenNV()
                            : ""
            );
            if (phieuTraHang.getKhachHang() != null) {
                lblTenKhachHangValue.setText(
                        Objects.toString(phieuTraHang.getKhachHang().getTenKH(), "")
                );
                lblSDTKhachHangValue.setText(
                        Objects.toString(phieuTraHang.getKhachHang().getSdt(), "")
                );
            } else {
                lblTenKhachHangValue.setText("Khách lẻ");
                lblSDTKhachHangValue.setText("");
            }

            lblGhiChuValue.setText(
                    Objects.toString(phieuTraHang.getGhiChu(), "")
            );

            List<ChiTietPhieuTraHang> list = new ChiTietPhieuTraHang_Dao().getChiTietPhieuTraByMaPT(phieuTraHang.getMaPT());

            tblChiTietPhieuTra.getItems().clear();
            tblChiTietPhieuTra.getItems().addAll(list);

            colSTT.setCellValueFactory(cellData ->
                    new SimpleStringProperty(String.valueOf(tblChiTietPhieuTra.getItems()
                            .indexOf(cellData.getValue()) + 1))
            );

            colTenSP.setCellValueFactory(cel ->
                    new SimpleStringProperty(cel.getValue().getThuoc().getTenThuoc())
            );

            colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
            colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));

            colLyDo.setCellValueFactory(cel ->
                    new SimpleStringProperty(cel.getValue().getLyDoTra())
            );

            colThanhTien.setCellValueFactory(cel ->
                    new ReadOnlyObjectWrapper<>(cel.getValue().getThanhTienTra())
            );
            colThanhTien.setCellFactory(tc -> new TableCell<>() {
                @Override protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : formatVNDTable(item == null ? 0 : item));
                    setStyle(empty ? "" : "-fx-alignment: CENTER-RIGHT;");
                }
            });

            double tongTienHDGoc = hdGoc.tinhTongTien(dsCTHDGoc);
            lblTongTienHDGocValue.setText(formatVNDTable(tongTienHDGoc));
            double tongTienTra = tblChiTietPhieuTra.getItems()
                    .stream()
                    .mapToDouble(ChiTietPhieuTraHang::getThanhTienTra)
                    .sum();

            lblSoTienTraLaiValue.setText(formatVNDTable(tongTienTra));
        }
    }

    public void xuLyXuatPDFPhieuTra() {

        if (phieuTraHang == null || tblChiTietPhieuTra.getItems().isEmpty()) {
            hien(INFORMATION, "Không có dữ liệu", "Không có phiếu trả hàng để xuất PDF.");
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Lưu Phiếu Trả Hàng PDF");
        chooser.setInitialFileName(
                "PhieuTra_" + phieuTraHang.getMaPT() + "_" + LocalDate.now()
        );
        chooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf"));

        Stage stage = (Stage) btnInPhieuTra.getScene().getWindow();
        File file = chooser.showSaveDialog(stage);

        if (file != null) {
            try {
                xuatPhieuTraPDF(file);
                hien(INFORMATION, "Thành công", "Xuất PDF phiếu trả hàng thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                hien(ERROR, "Lỗi", "Xuất PDF thất bại: " + e.getMessage());
            }
        }
    }
    private void xuatPhieuTraPDF(File file) throws Exception {

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // ===== FONT =====
        PdfFont font;
        try {
            font = PdfFontFactory.createFont(FONT_PATH, PdfEncodings.IDENTITY_H);
        } catch (IOException e) {
            font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        }
        document.setFont(font);

        // ===== LOGO =====
        try (InputStream is = getClass().getResourceAsStream(
                "/com/example/pharmacymanagementsystem_qlht/img/logo.png")) {
            if (is != null) {
                ImageData data = ImageDataFactory.create(is.readAllBytes());
                Image logo = new Image(data);
                logo.scaleToFit(120, 120);
                logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
                document.add(logo);
            }
        }

        // ===== HEADER =====
        document.add(new Paragraph("QUỐC KHÁNH PHARMACY")
                .setBold().setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("Địa chỉ: 12 Nguyễn Văn Bảo, Gò Vấp, TP.HCM")
                .setFontSize(10).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Hotline: 1800 6868")
                .setFontSize(10).setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("\nPHIẾU TRẢ HÀNG")
                .setBold().setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER));

        // ===== INFO =====
        document.add(new Paragraph("Mã phiếu trả: " + phieuTraHang.getMaPT())
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Ngày lập: " +
                new SimpleDateFormat("dd/MM/yyyy HH:mm")
                        .format(phieuTraHang.getNgayLap()))
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("\nTHÔNG TIN GIAO DỊCH")
                .setBold().setFontSize(14));

        document.add(new Paragraph("Khách hàng: " +
                (phieuTraHang.getKhachHang() != null
                        ? phieuTraHang.getKhachHang().getTenKH()
                        : "Khách lẻ")));

        document.add(new Paragraph("SĐT: " +
                (phieuTraHang.getKhachHang() != null
                        ? phieuTraHang.getKhachHang().getSdt()
                        : "")));

        document.add(new Paragraph("Nhân viên lập: " +
                phieuTraHang.getNhanVien().getTenNV()));

        // ===== TABLE =====
        document.add(new Paragraph("\nCHI TIẾT TRẢ HÀNG")
                .setBold().setFontSize(14));

        float[] widths = {1, 4, 1.5f, 2.5f, 2.5f};
        Table table = new Table(UnitValue.createPercentArray(widths));
        table.setWidth(UnitValue.createPercentValue(100));

        addHeader(table, "STT");
        addHeader(table, "Tên thuốc");
        addHeader(table, "SL");
        addHeader(table, "Đơn giá");
        addHeader(table, "Thành tiền");

        int stt = 1;
        double tongTra = 0;

        for (ChiTietPhieuTraHang ct : tblChiTietPhieuTra.getItems()) {
            table.addCell(String.valueOf(stt++));
            table.addCell(ct.getThuoc().getTenThuoc());
            table.addCell(String.valueOf(ct.getSoLuong()))
                    .setTextAlignment(TextAlignment.CENTER);
            table.addCell(formatVNDTable(ct.getDonGia()))
                    .setTextAlignment(TextAlignment.RIGHT);
            table.addCell(formatVNDTable(ct.getThanhTienTra()))
                    .setTextAlignment(TextAlignment.RIGHT);

            tongTra += ct.getThanhTienTra();
        }

        document.add(table);

        // ===== TOTAL =====
        document.add(new Paragraph("\nTỔNG TIỀN TRẢ LẠI: "
                + formatVNDTable(tongTra))
                .setBold()
                .setFontSize(14)
                .setTextAlignment(TextAlignment.RIGHT));

        // ===== FOOTER =====
        document.add(new Paragraph("\n\nNgười lập phiếu")
                .setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph(phieuTraHang.getNhanVien().getTenNV())
                .setTextAlignment(TextAlignment.RIGHT)
                .setItalic());

        document.close();
    }
    private void addHeader(Table table, String text) {
        table.addHeaderCell(
                new Cell()
                        .add(new Paragraph(text).setBold())
                        .setBackgroundColor(new DeviceRgb(230, 230, 230))
                        .setTextAlignment(TextAlignment.CENTER)
        );
    }


}
