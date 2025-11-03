package com.example.pharmacymanagementsystem_qlht.controller.CN_ThongKe;

// Sửa đường dẫn package của View
import com.example.pharmacymanagementsystem_qlht.view.CN_ThongKe.ThongKeBanHang_GUI;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

import java.text.DecimalFormat;
import javafx.scene.control.TableCell;
import com.example.pharmacymanagementsystem_qlht.dao.ThongKe_Dao;
// THÊM CÁC IMPORT CẦN THIẾT
import com.example.pharmacymanagementsystem_qlht.model.ThongKeBanHang;
import com.example.pharmacymanagementsystem_qlht.model.HoaDonDisplay;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.time.LocalDate;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

// ĐÃ XÓA: import liên quan đến ChiTietHoaDon_Ctrl


public class ThongKeBanHang_Ctrl extends Application {

    private ThongKeBanHang_GUI view;
    private ThongKe_Dao tkDao = new ThongKe_Dao();
    private ObservableList<ThongKeBanHang> listThongKe;
    private ObservableList<HoaDonDisplay> listHoaDon;
    private DecimalFormat formatter;

    @Override
    public void start(Stage stage) throws Exception {
        view = new ThongKeBanHang_GUI();
        Parent root = view.createContent();
        setupLogic();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThongKeBanHang.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public ThongKeBanHang_Ctrl(ThongKeBanHang_GUI view) {
        this.view = view;
        setupLogic();
    }

    public ThongKeBanHang_Ctrl() {}

    private void setupLogic() {
        // Gắn sự kiện
        view.btnXuat.setOnAction(e -> xuatFile(e));
        view.btnBang.setOnAction(e -> hienThiBang(e));
        view.btnBieuDo.setOnAction(e -> hienThiBieuDo(e));

        // Binding
        view.chartDoanhThu.managedProperty().bind(view.chartDoanhThu.visibleProperty());
        view.tableDoanhThu.managedProperty().bind(view.tableDoanhThu.visibleProperty());

        formatter = new DecimalFormat("#,##0");

        // Setup ComboBoxes
        view.cboThoiGian.getItems().addAll("Hôm nay", "Tuần này", "Tháng này", "Năm Nay", "Tùy chọn");
        view.cboXuatfile.getItems().addAll("Excel", "PDF");

        // Cấu hình biểu đồ
        view.chartDoanhThu.setTitle("Biểu đồ Doanh thu");
        view.xAxis.setLabel("Thời gian");
        view.yAxis.setLabel("Doanh thu (VNĐ)");
        view.yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(view.yAxis) {
            @Override
            public String toString(Number object) {
                return formatter.format(object.doubleValue());
            }
        });

        setupTables();

        // Setup ẩn/hiện DatePicker
        view.lblTu.setVisible(false);
        view.dateTu.setVisible(false);
        view.lblDen.setVisible(false);
        view.dateDen.setVisible(false);
        view.lblTu.managedProperty().bind(view.lblTu.visibleProperty());
        view.dateTu.managedProperty().bind(view.dateTu.visibleProperty());
        view.lblDen.managedProperty().bind(view.lblDen.visibleProperty());
        view.dateDen.managedProperty().bind(view.dateDen.visibleProperty());

        // Gắn Listeners
        view.cboThoiGian.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                boolean isCustom = newValue.equals("Tùy chọn");
                view.lblTu.setVisible(isCustom);
                view.dateTu.setVisible(isCustom);
                view.lblDen.setVisible(isCustom);
                view.dateDen.setVisible(isCustom);
                if (isCustom) {
                    attemptAutoLoadTuyChon();
                } else {
                    loadData(newValue);
                }
            }
        });
        view.dateTu.valueProperty().addListener((options, oldValue, newValue) -> attemptAutoLoadTuyChon());
        view.dateDen.valueProperty().addListener((options, oldValue, newValue) -> attemptAutoLoadTuyChon());

        // Tải dữ liệu ban đầu
        view.cboThoiGian.setValue("Hôm nay");
        view.chartDoanhThu.setAnimated(false);
    }

    private void setupTables() {
        // --- Setup Bảng Doanh Thu ---
        view.cotTG.setCellValueFactory(new PropertyValueFactory<>("thoiGian"));
        view.cotSLHoaDon.setCellValueFactory(new PropertyValueFactory<>("soLuongHoaDon"));
        view.cotTongGT.setCellValueFactory(new PropertyValueFactory<>("tongGiaTri"));
        view.cotTongGT.setCellFactory(col -> createFormattedCell(formatter));
        view.cotGG.setCellValueFactory(new PropertyValueFactory<>("giamGia"));
        view.cotGG.setCellFactory(col -> createFormattedCell(formatter));
        view.cotDT.setCellValueFactory(new PropertyValueFactory<>("soLuongDonTra"));
        view.cotGTDonTra.setCellValueFactory(new PropertyValueFactory<>("giaTriDonTra"));
        view.cotGTDonTra.setCellFactory(col -> createFormattedCell(formatter));
        view.cotDoanhThu.setCellValueFactory(new PropertyValueFactory<>("doanhThu"));
        view.cotDoanhThu.setCellFactory(col -> createFormattedCell(formatter));

        // --- Setup Bảng Hóa Đơn ---
        view.cotMaHoaDon.setCellValueFactory(new PropertyValueFactory<>("maHD"));
        view.cotNgayLap.setCellValueFactory(new PropertyValueFactory<>("ngayLap"));
        view.cotMaKhachHang.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        view.cotMaNhanVien.setCellValueFactory(new PropertyValueFactory<>("maNV"));
        view.cotTongTien.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
        view.cotTongTien.setCellFactory(col -> createFormattedCell(formatter));

        // ĐÃ XÓA: Logic nút "Chi tiết"
    }

    private <T> TableCell<T, Double> createFormattedCell(DecimalFormat formatter) {
        return new TableCell<T, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatter.format(item));
            }
        };
    }

    // ĐÃ XÓA: Hàm hienThiChiTietHoaDon()

    // --- 5. CÁC HÀM LOGIC (SỬA ĐỔI) ---

    private void loadData(String thoiGian) {
        listThongKe = FXCollections.observableArrayList(tkDao.getThongKeBanHang(thoiGian));
        view.tableDoanhThu.setItems(listThongKe);

        listHoaDon = tkDao.getHoaDonTheoThoiGian(thoiGian);
        view.tableHoaDon.setItems(listHoaDon);

        updateChart(listThongKe);
        view.chartDoanhThu.setVisible(false);
        view.tableDoanhThu.setVisible(true);

        if (listThongKe.size() > 12) {
            view.xAxis.setTickLabelRotation(-90);
        } else {
            view.xAxis.setTickLabelRotation(-20);
        }
    }

    private void attemptAutoLoadTuyChon() {
        String selectedTime = view.cboThoiGian.getValue();
        if (selectedTime == null || !selectedTime.equals("Tùy chọn")) return;

        LocalDate tuNgay = view.dateTu.getValue();
        LocalDate denNgay = view.dateDen.getValue();
        if (tuNgay == null || denNgay == null) return;

        if (tuNgay.isAfter(denNgay)) {
            view.tableDoanhThu.getItems().clear();
            view.tableHoaDon.getItems().clear();
            view.chartDoanhThu.getData().clear();
            return;
        }
        loadDataTuyChon(tuNgay, denNgay);
    }

    private void loadDataTuyChon(LocalDate tuNgay, LocalDate denNgay) {
        listThongKe = FXCollections.observableArrayList(tkDao.getThongKeBanHang_TuyChon(tuNgay, denNgay));
        view.tableDoanhThu.setItems(listThongKe);

        listHoaDon = tkDao.getHoaDonTheoTuyChon(tuNgay, denNgay);
        view.tableHoaDon.setItems(listHoaDon);

        updateChart(listThongKe);
        view.chartDoanhThu.setVisible(false);
        view.tableDoanhThu.setVisible(true);

        if (listThongKe.size() > 12) {
            view.xAxis.setTickLabelRotation(-90);
        } else {
            view.xAxis.setTickLabelRotation(-20);
        }
    }

    private void updateChart(ObservableList<ThongKeBanHang> data) {
        view.chartDoanhThu.getData().clear();
        if (view.xAxis != null) view.xAxis.getCategories().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Doanh thu");

        double yAxisMax = 0;
        for (ThongKeBanHang tk : data) {
            if (tk.getDoanhThu() > yAxisMax) {
                yAxisMax = tk.getDoanhThu();
            }
        }
        final double max = (yAxisMax == 0) ? 1.0 : yAxisMax;

        ObservableList<String> categories = FXCollections.observableArrayList();
        for (ThongKeBanHang tk : data) {
            String label = tk.getThoiGian() == null ? "" : tk.getThoiGian();
            categories.add(label);

            double yValue = tk.getDoanhThu();
            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(label, yValue);

            dataPoint.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    Tooltip.install(newNode, new Tooltip(
                            dataPoint.getXValue() + "\nDoanh thu: " + formatter.format(dataPoint.getYValue()) + " VNĐ"
                    ));

                    Text dataText = new Text(formatter.format(yValue));
                    StackPane bar = (StackPane) newNode;
                    bar.getChildren().removeIf(node -> node instanceof Text);

                    if (yValue < max * 0.07) {
                        dataText.setStyle("-fx-font-size: 10px; -fx-fill: black; -fx-font-weight: bold;");
                        StackPane.setAlignment(dataText, Pos.TOP_CENTER);
                        StackPane.setMargin(dataText, new Insets(-15, 0, 0, 0));
                    }
                    else {
                        dataText.setStyle("-fx-font-size: 10px; -fx-fill: white; -fx-font-weight: bold;");
                        StackPane.setAlignment(dataText, Pos.TOP_CENTER);
                        StackPane.setMargin(dataText, new Insets(5, 0, 0, 0));
                    }

                    bar.getChildren().add(dataText);
                }
            });

            series.getData().add(dataPoint);
        }

        if (view.xAxis != null) view.xAxis.setCategories(categories);
        view.chartDoanhThu.getData().add(series);
    }


    // --- 6. CÁC HÀM XUẤT FILE ---
    private void xuatFile(ActionEvent event) {
        String selectedFormat = view.cboXuatfile.getValue();
        if (selectedFormat == null) {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn định dạng", "Vui lòng chọn định dạng file (Excel hoặc PDF) để xuất.");
            return;
        }
        if ((listThongKe == null || listThongKe.isEmpty()) && (listHoaDon == null || listHoaDon.isEmpty())) {
            showAlert(Alert.AlertType.INFORMATION, "Không có dữ liệu", "Không có dữ liệu thống kê để xuất.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Lưu file thống kê");
        fileChooser.setInitialFileName("BaoCao_" + LocalDate.now());

        if (selectedFormat.equals("Excel")) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx"));
            File file = fileChooser.showSaveDialog(view.btnXuat.getScene().getWindow());
            if (file != null) {
                try {
                    xuatExcel(file);
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xuất file Excel thành công!");
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra khi xuất file Excel: " + e.getMessage());
                }
            }
        } else if (selectedFormat.equals("PDF")) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf"));
            File file = fileChooser.showSaveDialog(view.btnXuat.getScene().getWindow());
            if (file != null) {
                try {
                    xuatPDF(file);
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xuất file PDF thành công!");
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra khi xuất file PDF: " + e.getMessage());
                }
            }
        }
    }

    private void xuatExcel(File file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy"));

            // --- Sheet 1: Thống kê Doanh thu ---
            Sheet sheetDT = workbook.createSheet("Thong ke Doanh thu");
            String[] headersDT = {
                    view.cotTG.getText(), view.cotSLHoaDon.getText(), view.cotTongGT.getText(),
                    view.cotGG.getText(), view.cotDT.getText(), view.cotGTDonTra.getText(), view.cotDoanhThu.getText()
            };
            Row headerRowDT = sheetDT.createRow(0);
            for (int i = 0; i < headersDT.length; i++) {
                Cell cell = headerRowDT.createCell(i);
                cell.setCellValue(headersDT[i]);
                cell.setCellStyle(headerStyle);
            }
            int rowNumDT = 1;
            for (ThongKeBanHang tk : listThongKe) {
                Row row = sheetDT.createRow(rowNumDT++);
                row.createCell(0).setCellValue(tk.getThoiGian());
                row.createCell(1).setCellValue(tk.getSoLuongHoaDon());
                row.createCell(2).setCellValue(tk.getTongGiaTri());
                row.createCell(3).setCellValue(tk.getGiamGia());
                row.createCell(4).setCellValue(tk.getSoLuongDonTra());
                row.createCell(5).setCellValue(tk.getGiaTriDonTra());
                row.createCell(6).setCellValue(tk.getDoanhThu());
            }
            for (int i = 0; i < headersDT.length; i++) {
                sheetDT.autoSizeColumn(i);
            }

            // --- Sheet 2: Danh sách Hóa đơn ---
            Sheet sheetHD = workbook.createSheet("Danh sach Hoa don");
            String[] headersHD = {
                    view.cotMaHoaDon.getText(), view.cotNgayLap.getText(),
                    view.cotMaKhachHang.getText(), view.cotMaNhanVien.getText(), view.cotTongTien.getText()
            };
            Row headerRowHD = sheetHD.createRow(0);
            for (int i = 0; i < headersHD.length; i++) {
                Cell cell = headerRowHD.createCell(i);
                cell.setCellValue(headersHD[i]);
                cell.setCellStyle(headerStyle);
            }
            int rowNumHD = 1;
            for (HoaDonDisplay hd : listHoaDon) {
                Row row = sheetHD.createRow(rowNumHD++);
                row.createCell(0).setCellValue(hd.getMaHD());

                Cell dateCell = row.createCell(1);
                dateCell.setCellValue(hd.getNgayLap());
                dateCell.setCellStyle(dateCellStyle);

                row.createCell(2).setCellValue(hd.getMaKH());
                row.createCell(3).setCellValue(hd.getMaNV());
                row.createCell(4).setCellValue(hd.getTongTien());
            }
            for (int i = 0; i < headersHD.length; i++) {
                sheetHD.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }
        }
    }

    public static final String FONT_PATH = "C:/Windows/Fonts/arial.ttf";

    private void xuatPDF(File file) throws IOException {
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        PdfFont font;
        try {
            font = PdfFontFactory.createFont(FONT_PATH, "Identity-H");
        } catch (IOException e) {
            System.err.println("Không tìm thấy font tại: " + FONT_PATH + ". Sử dụng font mặc định.");
            font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        }
        document.setFont(font);

        document.add(new Paragraph("BÁO CÁO THỐNG KÊ BÁN HÀNG")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));

        // Phần 1: Thống kê Doanh thu
        document.add(new Paragraph("I. Thống kê Doanh thu")
                .setFontSize(14)
                .setBold()
                .setMarginTop(15));
        float[] columnWidthsDT = {2, 1, 1, 1, 1, 1, 1};
        Table tableDT = new Table(UnitValue.createPercentArray(columnWidthsDT));
        tableDT.setWidth(UnitValue.createPercentValue(100));
        tableDT.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(view.cotTG.getText()).setBold()));
        tableDT.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(view.cotSLHoaDon.getText()).setBold()));
        tableDT.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(view.cotTongGT.getText()).setBold()));
        tableDT.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(view.cotGG.getText()).setBold()));
        tableDT.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(view.cotDT.getText()).setBold()));
        tableDT.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(view.cotGTDonTra.getText()).setBold()));
        tableDT.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(view.cotDoanhThu.getText()).setBold()));
        for (ThongKeBanHang tk : listThongKe) {
            tableDT.addCell(tk.getThoiGian());
            tableDT.addCell(String.valueOf(tk.getSoLuongHoaDon()));
            tableDT.addCell(formatter.format(tk.getTongGiaTri()));
            tableDT.addCell(formatter.format(tk.getGiamGia()));
            tableDT.addCell(String.valueOf(tk.getSoLuongDonTra()));
            tableDT.addCell(formatter.format(tk.getGiaTriDonTra()));
            tableDT.addCell(formatter.format(tk.getDoanhThu()));
        }
        document.add(tableDT);

        // Phần 2: Danh sách Hóa đơn
        document.add(new Paragraph("II. Danh sách Hóa đơn")
                .setFontSize(14)
                .setBold()
                .setMarginTop(15));
        float[] columnWidthsHD = {2, 2, 2, 2, 2}; // 5 cột
        Table tableHD = new Table(UnitValue.createPercentArray(columnWidthsHD));
        tableHD.setWidth(UnitValue.createPercentValue(100));
        tableHD.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(view.cotMaHoaDon.getText()).setBold()));
        tableHD.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(view.cotNgayLap.getText()).setBold()));
        tableHD.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(view.cotMaKhachHang.getText()).setBold()));
        tableHD.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(view.cotMaNhanVien.getText()).setBold()));
        tableHD.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(view.cotTongTien.getText()).setBold()));
        for (HoaDonDisplay hd : listHoaDon) {
            tableHD.addCell(hd.getMaHD());
            tableHD.addCell(hd.getNgayLap().toString());
            tableHD.addCell(hd.getMaKH() != null ? hd.getMaKH() : ""); // Kiểm tra null
            tableHD.addCell(hd.getMaNV() != null ? hd.getMaNV() : ""); // Kiểm tra null
            tableHD.addCell(formatter.format(hd.getTongTien()));
        }
        document.add(tableHD);

        document.close();
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // --- 7. XỬ LÝ SỰ KIỆN GIAO DIỆN (Giữ nguyên) ---
    private void hienThiBieuDo(ActionEvent event) {
        view.chartDoanhThu.setVisible(true);
        view.tableDoanhThu.setVisible(false);
    }

    private void hienThiBang(ActionEvent event) {
        view.chartDoanhThu.setVisible(false);
        view.tableDoanhThu.setVisible(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}