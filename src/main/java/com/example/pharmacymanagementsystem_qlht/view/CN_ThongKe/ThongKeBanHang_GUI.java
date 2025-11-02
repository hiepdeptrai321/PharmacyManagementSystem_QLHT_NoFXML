package com.example.pharmacymanagementsystem_qlht.view.CN_ThongKe;

import com.example.pharmacymanagementsystem_qlht.model.HoaDonDisplay; // <-- SỬA
import com.example.pharmacymanagementsystem_qlht.model.ThongKeBanHang;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate; // <-- THÊM

/**
 * Lớp View (Đã cập nhật)
 * Sử dụng HoaDonDisplay thay vì HoaDon
 */
public class ThongKeBanHang_GUI {

    // --- Panel trái (Giữ nguyên) ---
    public Button btnBang = new Button("Bảng");
    public Button btnBieuDo = new Button("Biểu đồ");
    public Button btnXuat = new Button("Xuất File 💾");
    public ComboBox<String> cboThoiGian = new ComboBox<>();
    public ComboBox<String> cboXuatfile = new ComboBox<>();
    public DatePicker dateTu = new DatePicker();
    public DatePicker dateDen = new DatePicker();
    public Label lblTu = new Label("Từ:");
    public Label lblDen = new Label("Đến:");

    // --- Panel phải - Bảng Doanh Thu (Giữ nguyên) ---
    public TableView<ThongKeBanHang> tableDoanhThu = new TableView<>();
    public TableColumn<ThongKeBanHang, String> cotTG = new TableColumn<>("Thời gian");
    public TableColumn<ThongKeBanHang, Integer> cotSLHoaDon = new TableColumn<>("Số lượng HĐ");
    public TableColumn<ThongKeBanHang, Double> cotTongGT = new TableColumn<>("Tổng giá trị");
    public TableColumn<ThongKeBanHang, Double> cotGG = new TableColumn<>("Giảm giá");
    public TableColumn<ThongKeBanHang, Integer> cotDT = new TableColumn<>("Số lượng đơn trả");
    public TableColumn<ThongKeBanHang, Double> cotGTDonTra = new TableColumn<>("Giá trị đơn trả");
    public TableColumn<ThongKeBanHang, Double> cotDoanhThu = new TableColumn<>("Doanh thu");

    // --- Panel phải - Biểu đồ (Giữ nguyên) ---
    public CategoryAxis xAxis = new CategoryAxis();
    public NumberAxis yAxis = new NumberAxis();
    public BarChart<String, Number> chartDoanhThu = new BarChart<>(xAxis, yAxis);

    // --- Panel phải - Bảng Hóa Đơn (THAY ĐỔI) ---
    public TableView<HoaDonDisplay> tableHoaDon = new TableView<>();
    public TableColumn<HoaDonDisplay, String> cotMaHoaDon = new TableColumn<>("Mã Hóa Đơn");
    public TableColumn<HoaDonDisplay, LocalDate> cotNgayLap = new TableColumn<>("Ngày Lập");
    public TableColumn<HoaDonDisplay, String> cotMaKhachHang = new TableColumn<>("Mã Khách Hàng");
    public TableColumn<HoaDonDisplay, Double> cotTongTien = new TableColumn<>("Tổng Tiền");
    public TableColumn<HoaDonDisplay, Void> cotChiTiet = new TableColumn<>("Chi Tiết");


    public Parent createContent() {

        // --- Cấu hình Bảng Doanh Thu (Giữ nguyên) ---
        cotTG.setPrefWidth(147.33); cotTG.setStyle("-fx-alignment: CENTER;");
        cotSLHoaDon.setPrefWidth(170.66); cotSLHoaDon.setStyle("-fx-alignment: CENTER;");
        cotTongGT.setPrefWidth(129.66); cotTongGT.setStyle("-fx-alignment: CENTER;");
        cotGG.setPrefWidth(142.99); cotGG.setStyle("-fx-alignment: CENTER;");
        cotDT.setPrefWidth(150.66); cotDT.setStyle("-fx-alignment: CENTER;");
        cotGTDonTra.setPrefWidth(193.33); cotGTDonTra.setStyle("-fx-alignment: CENTER;");
        cotDoanhThu.setPrefWidth(229.66); cotDoanhThu.setStyle("-fx-alignment: CENTER;");
        tableDoanhThu.getColumns().addAll(cotTG, cotSLHoaDon, cotTongGT, cotGG, cotDT, cotGTDonTra, cotDoanhThu);
        tableDoanhThu.setPrefHeight(510.0);
        tableDoanhThu.setPrefWidth(1161.0);

        // --- Cấu hình Biểu đồ (Giữ nguyên) ---
        chartDoanhThu.setPrefHeight(510.0);
        chartDoanhThu.setPrefWidth(1161.0);
        chartDoanhThu.setVisible(false);
        xAxis.setSide(javafx.geometry.Side.BOTTOM);

        // --- Cấu hình Bảng Hóa Đơn (THAY ĐỔI) ---
        cotMaHoaDon.setPrefWidth(200.0);
        cotNgayLap.setPrefWidth(200.0);
        cotMaKhachHang.setPrefWidth(200.0);
        cotTongTien.setPrefWidth(300.0);
        cotChiTiet.setPrefWidth(259.0); // Căn chỉnh lại
        tableHoaDon.getColumns().addAll(cotMaHoaDon, cotNgayLap, cotMaKhachHang, cotTongTien, cotChiTiet);
        tableHoaDon.setPrefHeight(273.0);
        tableHoaDon.setPrefWidth(1161.0);


        // --- Dựng VBox bên trái (Panel điều khiển) (Giữ nguyên) ---
        VBox leftVBox = new VBox();
        leftVBox.setPrefHeight(1126.0);
        leftVBox.setPrefWidth(449.0);

        Label titleLabel = new Label("Báo cáo bán hàng ");
        titleLabel.setFont(new Font(28.0));
        ImageView titleIcon = createIcon("/com/example/pharmacymanagementsystem_qlht/img/bar-chart.png", 33, 40);
        HBox titleHBox = new HBox(titleLabel, new Label("", titleIcon));

        Separator separator = new Separator();
        separator.setPrefWidth(200.0);

        Label displayLabel = new Label("Kiểu hiển thị");
        displayLabel.setFont(new Font(18.0));

        btnBang.setId("btnBang");
        btnBang.setPrefHeight(61.0);
        btnBang.setPrefWidth(103.0);
        btnBang.setGraphic(createIcon("/com/example/pharmacymanagementsystem_qlht/img/table.png", 40, 38));
        HBox.setMargin(btnBang, new Insets(0, 0, 0, 30.0));

        btnBieuDo.setId("btnBieuDo");
        btnBieuDo.setPrefHeight(62.0);
        btnBieuDo.setPrefWidth(104.0);
        btnBieuDo.setGraphic(createIcon("/com/example/pharmacymanagementsystem_qlht/img/improvement.png", 35, 34));
        HBox.setMargin(btnBieuDo, new Insets(0, 0, 0, 30.0));

        HBox buttonHBox = new HBox(btnBang, btnBieuDo);
        buttonHBox.setAlignment(Pos.CENTER);
        VBox.setMargin(buttonHBox, new Insets(5.0, 0, 0, 0));

        Label timeLabel = new Label("Thời gian");
        timeLabel.setFont(new Font(18.0));
        VBox.setMargin(timeLabel, new Insets(10.0, 0, 0, 0));
        cboThoiGian.setPrefHeight(49.0);
        cboThoiGian.setPrefWidth(446.0);
        cboThoiGian.setPromptText("Hôm Nay");

        lblTu.setFont(new Font(18.0));
        dateTu.setPrefHeight(39.0);
        dateTu.setPrefWidth(442.0);

        lblDen.setFont(new Font(18.0));
        dateDen.setPrefHeight(39.0);
        dateDen.setPrefWidth(441.0);

        Label exportLabel = new Label("Xuất file");
        exportLabel.setFont(new Font(18.0));
        VBox.setMargin(exportLabel, new Insets(10.0, 0, 0, 0));
        cboXuatfile.setPrefHeight(49.0);
        cboXuatfile.setPrefWidth(441.0);
        cboXuatfile.setPromptText("Chọn định dạng file");

        btnXuat.setPrefHeight(53.0);
        btnXuat.setPrefWidth(438.0);
        VBox.setMargin(btnXuat, new Insets(10.0, 0, 0, 0));

        leftVBox.getChildren().addAll(
                titleHBox, separator, displayLabel, buttonHBox,
                timeLabel, cboThoiGian, lblTu, dateTu, lblDen, dateDen,
                exportLabel, cboXuatfile, btnXuat
        );

        // --- Dựng VBox bên phải (THAY ĐỔI) ---
        VBox rightVBox = new VBox();
        rightVBox.setPrefHeight(1126.0);
        rightVBox.setPrefWidth(1161.0);

        Label revenueLabel = new Label("Doanh thu");
        revenueLabel.setFont(new Font(18.0));
        revenueLabel.setAlignment(Pos.CENTER);
        revenueLabel.setPrefWidth(1167.0);

        // Thay đổi Label
        Label invoiceListLabel = new Label("Danh sách Hóa đơn 🧾");
        invoiceListLabel.setTextFill(javafx.scene.paint.Color.rgb(18, 115, 170)); // Đổi màu
        invoiceListLabel.setFont(new Font(24.0));
        VBox.setMargin(invoiceListLabel, new Insets(8.0, 0, 8.0, 10.0));

        // Thay đổi Bảng
        rightVBox.getChildren().addAll(revenueLabel, tableDoanhThu, chartDoanhThu, invoiceListLabel, tableHoaDon);

        // --- Dựng HBox gốc (Giữ nguyên) ---
        HBox mainHBox = new HBox(leftVBox, rightVBox);

        // --- Dựng Pane gốc (Giữ nguyên) ---
        Pane root = new Pane();
        root.setPrefHeight(895.0);
        root.setPrefWidth(1646.0);

        mainHBox.setLayoutX(14.0);
        mainHBox.setLayoutY(14.0);
        root.getChildren().add(mainHBox);

        return root;
    }

    // Hàm trợ giúp (Giữ nguyên)
    private ImageView createIcon(String path, double height, double width) {
        try {
            Image image = new Image(getClass().getResourceAsStream(path));
            ImageView icon = new ImageView(image);
            icon.setFitHeight(height);
            icon.setFitWidth(width);
            icon.setPreserveRatio(true);
            return icon;
        } catch (Exception e) {
            System.err.println("Không tải được icon: " + path);
            return new ImageView();
        }
    }
}