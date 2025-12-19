package com.example.pharmacymanagementsystem_qlht.controller.CN_ThongKe;

import com.example.pharmacymanagementsystem_qlht.dao.ThongKeTopSP_Dao;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeTopSanPham;
import com.example.pharmacymanagementsystem_qlht.view.CN_ThongKe.ThongKeTopSanPham_GUI;

import javafx.application.Application;
import javafx.application.Platform; // Quan trọng
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.io.font.constants.StandardFonts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class ThongKeTopSanPham_Ctrl extends Application {

    public ComboBox<String> cboTieuChi, cboThoiGian, cboXuatFile;
    public ComboBox<Integer> cboTop;
    public DatePicker dateTu, dateDen;
    public Label lblTu, lblDen;
    public Button btnXuat;

    public PieChart pieChart;
    public BarChart<String, Number> barChart;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;
    public TableView<ThongKeTopSanPham> table;
    public TableColumn<ThongKeTopSanPham, Integer> colSTT, colSL;
    public TableColumn<ThongKeTopSanPham, String> colMa, colTen, colDVT;
    public TableColumn<ThongKeTopSanPham, Double> colTien;

    private ThongKeTopSP_Dao dao = new ThongKeTopSP_Dao();
    private ObservableList<ThongKeTopSanPham> listData = FXCollections.observableArrayList();
    private DecimalFormat moneyFormat = new DecimalFormat("#,##0 VNĐ");
    public static final String FONT_PATH = "C:/Windows/Fonts/arial.ttf";

    @Override
    public void start(Stage stage) {
        new ThongKeTopSanPham_GUI().showWithController(stage, this);
    }

    public void initialize() {

        cboTieuChi.getItems().addAll("Top Bán Chạy", "Top Doanh Thu Cao");
        cboThoiGian.getItems().addAll("Hôm nay", "Tuần này", "Tháng này", "Năm nay", "Tùy chọn");
        cboTop.getItems().addAll(5, 10, 20, 50, 100);
        cboXuatFile.getItems().addAll("Excel", "PDF");

        cboTieuChi.setValue("Top Bán Chạy");
        cboThoiGian.setValue("Tháng này");
        cboTop.setValue(10);

        lblTu.setVisible(false); dateTu.setVisible(false);
        lblDen.setVisible(false); dateDen.setVisible(false);
        lblTu.managedProperty().bind(lblTu.visibleProperty());
        dateTu.managedProperty().bind(dateTu.visibleProperty());
        lblDen.managedProperty().bind(lblDen.visibleProperty());
        dateDen.managedProperty().bind(dateDen.visibleProperty());

        colSTT.setCellValueFactory(new PropertyValueFactory<>("stt"));
        colMa.setCellValueFactory(new PropertyValueFactory<>("maThuoc"));
        colTen.setCellValueFactory(new PropertyValueFactory<>("tenThuoc"));
        colDVT.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        colSL.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colTien.setCellValueFactory(new PropertyValueFactory<>("tongTien"));

        colTien.setCellFactory(tc -> new TableCell<>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : moneyFormat.format(item));
            }
        });

        table.setItems(listData);

        cboTieuChi.setOnAction(e -> loadData());
        cboTop.setOnAction(e -> loadData());
        cboThoiGian.setOnAction(e -> { handleThoiGianChange(); loadData(); });
        dateTu.valueProperty().addListener((obs, old, newVal) -> { if ("Tùy chọn".equals(cboThoiGian.getValue())) loadData(); });
        dateDen.valueProperty().addListener((obs, old, newVal) -> { if ("Tùy chọn".equals(cboThoiGian.getValue())) loadData(); });

        btnXuat.setOnAction(e -> xuatFileHandler());

        // Auto refresh
        btnXuat.sceneProperty().addListener((obs, old, scene) -> { if(scene != null) loadData(); });

        handleThoiGianChange();
        loadData();
    }

    private void handleThoiGianChange() {
        String type = cboThoiGian.getValue();
        if (type == null) return;
        boolean custom = "Tùy chọn".equals(type);
        lblTu.setVisible(custom); dateTu.setVisible(custom);
        lblDen.setVisible(custom); dateDen.setVisible(custom);
        LocalDate now = LocalDate.now();
        if(!custom) {
            if(type.equals("Hôm nay")) { dateTu.setValue(now); dateDen.setValue(now); }
            else if(type.equals("Tuần này")) {
                dateTu.setValue(now.minusDays(now.getDayOfWeek().getValue() - 1));
                dateDen.setValue(now.plusDays(7 - now.getDayOfWeek().getValue()));
            }
            else if(type.equals("Tháng này")) { dateTu.setValue(now.withDayOfMonth(1)); dateDen.setValue(now.withDayOfMonth(now.lengthOfMonth())); }
            else if(type.equals("Năm nay")) { dateTu.setValue(now.withDayOfYear(1)); dateDen.setValue(now.withDayOfYear(now.lengthOfYear())); }
        }
    }

    private void loadData() {
        LocalDate from = dateTu.getValue();
        LocalDate to = dateDen.getValue();
        if(from == null || to == null) return;

        String tieuChi = cboTieuChi.getValue();
        // Lấy số lượng Top từ ComboBox để hiển thị cho BẢNG
        Integer topVal = cboTop.getValue();
        int top = (topVal != null) ? topVal : 10;

        listData.clear();
        if("Top Bán Chạy".equals(tieuChi)) {
            listData.setAll(dao.getTopBanChay(from, to, top));
            colSL.setText("Số Lượng Bán");
            updateCharts(true);
        }
        else if ("Top Doanh Thu Cao".equals(tieuChi)) {
            listData.setAll(dao.getTopDoanhThu(from, to, top));
            colSL.setText("Số Lượng Bán");
            updateCharts(false);
        }
    }

    // --- ĐÃ SỬA: Dùng Platform.runLater chuẩn để tránh nhân đôi và luôn hiện Top 5 ---
    private void updateCharts(boolean useQuantity) {
        // Tính toán tổng số liệu trước (ở luồng hiện tại)
        double totalValueTemp = 0;
        for (ThongKeTopSanPham sp : listData) {
            totalValueTemp += useQuantity ? sp.getSoLuong() : sp.getTongTien();
        }
        final double totalValue = totalValueTemp;

        // Đẩy toàn bộ việc Xóa và Vẽ vào luồng giao diện để đồng bộ
        Platform.runLater(() -> {
            // 1. Xóa dữ liệu cũ (Nằm TRONG runLater để tránh xung đột với lệnh Add)
            pieChart.getData().clear();
            barChart.getData().clear();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(useQuantity ? "Số lượng" : "Doanh thu");

            // 2. Chỉ lấy Top 5 cho biểu đồ (Dù listData có 10, 20...)
            int limit = Math.min(listData.size(), 5);

            for (int i = 0; i < limit; i++) {
                ThongKeTopSanPham sp = listData.get(i);
                double value = useQuantity ? sp.getSoLuong() : sp.getTongTien();

                double percent = (totalValue == 0) ? 0 : (value / totalValue) * 100;
                String nameWithPercent = String.format("%s (%.1f%%)", sp.getTenThuoc(), percent);

                pieChart.getData().add(new PieChart.Data(nameWithPercent, value));
                series.getData().add(new XYChart.Data<>(sp.getTenThuoc(), value));
            }

            pieChart.setTitle(useQuantity ? "Tỷ trọng số lượng (Top 5)" : "Tỷ trọng doanh thu (Top 5)");
            barChart.getData().add(series);
            yAxis.setLabel(useQuantity ? "Đơn vị" : "VNĐ");
        });
    }

    private void xuatFileHandler() {
        String selectedFormat = cboXuatFile.getValue();
        if (selectedFormat == null) {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn định dạng", "Vui lòng chọn Excel hoặc PDF.");
            return;
        }
        if (listData.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Không có dữ liệu", "Không có dữ liệu để xuất.");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Lưu báo cáo");
        fileChooser.setInitialFileName("BaoCao_XepHang_" + LocalDate.now());

        if (selectedFormat.equals("Excel")) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            File file = fileChooser.showSaveDialog(btnXuat.getScene().getWindow());
            if (file != null) {
                try { xuatExcel(file); showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xuất Excel thành công!"); }
                catch (IOException e) { e.printStackTrace(); }
            }
        } else if (selectedFormat.equals("PDF")) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(btnXuat.getScene().getWindow());
            if (file != null) {
                try { xuatPDF(file); showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xuất PDF thành công!"); }
                catch (IOException e) { e.printStackTrace(); }
            }
        }
    }

    private void xuatExcel(File file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("XepHang");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Hạng", "Mã Thuốc", "Tên Thuốc", "ĐVT", "Số Lượng", "Doanh Thu"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }
            int rowNum = 1;
            for (ThongKeTopSanPham sp : listData) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(sp.getStt());
                row.createCell(1).setCellValue(sp.getMaThuoc());
                row.createCell(2).setCellValue(sp.getTenThuoc());
                row.createCell(3).setCellValue(sp.getDonViTinh());
                row.createCell(4).setCellValue(sp.getSoLuong());
                row.createCell(5).setCellValue(sp.getTongTien());
            }
            for (int i = 0; i < columns.length; i++) sheet.autoSizeColumn(i);
            try (FileOutputStream fileOut = new FileOutputStream(file)) { workbook.write(fileOut); }
        }
    }

    private void xuatPDF(File file) throws IOException {
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        PdfFont font;
        try { font = PdfFontFactory.createFont(FONT_PATH, "Identity-H"); }
        catch (IOException e) { font = PdfFontFactory.createFont(StandardFonts.HELVETICA); }
        document.setFont(font);

        document.add(new Paragraph("BÁO CÁO XẾP HẠNG SẢN PHẨM").setFontSize(18).setBold().setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Thời gian: " + cboThoiGian.getValue()).setTextAlignment(TextAlignment.CENTER).setMarginBottom(10));

        float[] colWidths = {1, 2, 4, 1, 2, 3};
        Table tablePDF = new Table(UnitValue.createPercentArray(colWidths));
        tablePDF.setWidth(UnitValue.createPercentValue(100));
        String[] headers = {"Hạng", "Mã Thuốc", "Tên Thuốc", "ĐVT", "Số Lượng", "Doanh Thu"};
        for (String h : headers) tablePDF.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(h).setBold()));

        for (ThongKeTopSanPham sp : listData) {
            tablePDF.addCell(String.valueOf(sp.getStt()));
            tablePDF.addCell(sp.getMaThuoc());
            tablePDF.addCell(sp.getTenThuoc());
            tablePDF.addCell(sp.getDonViTinh());
            tablePDF.addCell(String.valueOf(sp.getSoLuong()));
            tablePDF.addCell(moneyFormat.format(sp.getTongTien()));
        }
        document.add(tablePDF);
        document.add(new Paragraph("Ngày lập: " + LocalDate.now()).setTextAlignment(TextAlignment.RIGHT).setFontSize(10).setMarginTop(10));
        document.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}