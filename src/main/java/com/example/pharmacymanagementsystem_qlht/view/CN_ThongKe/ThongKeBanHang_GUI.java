package com.example.pharmacymanagementsystem_qlht.view.CN_ThongKe;

// Import Controller
import com.example.pharmacymanagementsystem_qlht.controller.CN_ThongKe.ThongKeBanHang_Ctrl;

import com.example.pharmacymanagementsystem_qlht.model.HoaDonDisplay;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeBanHang;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Objects;

public class ThongKeBanHang_GUI {

    // Bỏ hết các khai báo public ở đây, chúng sẽ được tạo cục bộ

    // Sửa lại phương thức:
    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, ThongKeBanHang_Ctrl ctrl) {

        // --- Tạo các thành phần giao diện (dưới dạng biến cục bộ) ---
        Button btnBang = new Button("Bảng");
        Button btnBieuDo = new Button("Biểu đồ");
        Button btnXuat = new Button("Xuất File 💾");
        ComboBox<String> cboThoiGian = new ComboBox<>();
        ComboBox<String> cboXuatfile = new ComboBox<>();
        DatePicker dateTu = new DatePicker();
        DatePicker dateDen = new DatePicker();
        Label lblTu = new Label("Từ:");
        Label lblDen = new Label("Đến:");

        TableView<ThongKeBanHang> tableDoanhThu = new TableView<>();
        TableColumn<ThongKeBanHang, String> cotTG = new TableColumn<>("Thời gian");
        TableColumn<ThongKeBanHang, Integer> cotSLHoaDon = new TableColumn<>("Số lượng HĐ");
        TableColumn<ThongKeBanHang, Double> cotTongGT = new TableColumn<>("Tổng giá trị");
        TableColumn<ThongKeBanHang, Double> cotGG = new TableColumn<>("Giảm giá");
        TableColumn<ThongKeBanHang, Integer> cotDT = new TableColumn<>("Số lượng đơn trả");
        TableColumn<ThongKeBanHang, Double> cotGTDonTra = new TableColumn<>("Giá trị đơn trả");
        TableColumn<ThongKeBanHang, Double> cotDoanhThu = new TableColumn<>("Doanh thu");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chartDoanhThu = new BarChart<>(xAxis, yAxis);

        TableView<HoaDonDisplay> tableHoaDon = new TableView<>();
        TableColumn<HoaDonDisplay, String> cotMaHoaDon = new TableColumn<>("Mã Hóa Đơn");
        TableColumn<HoaDonDisplay, LocalDate> cotNgayLap = new TableColumn<>("Ngày Lập");
        TableColumn<HoaDonDisplay, String> cotMaKhachHang = new TableColumn<>("Mã Khách Hàng");
        TableColumn<HoaDonDisplay, String> cotMaNhanVien = new TableColumn<>("Mã Nhân Viên");
        TableColumn<HoaDonDisplay, Double> cotTongTien = new TableColumn<>("Tổng Tiền");

        // --- Cấu hình Bảng Doanh Thu ---
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

        // --- Cấu hình Biểu đồ ---
        chartDoanhThu.setPrefHeight(510.0);
        chartDoanhThu.setPrefWidth(1161.0);
        chartDoanhThu.setVisible(false);
        xAxis.setSide(javafx.geometry.Side.BOTTOM);

        // --- Cấu hình Bảng Hóa Đơn ---
        cotMaHoaDon.setPrefWidth(200.0);
        cotNgayLap.setPrefWidth(200.0);
        cotMaKhachHang.setPrefWidth(200.0);
        cotMaNhanVien.setPrefWidth(200.0);
        cotTongTien.setPrefWidth(359.0);
        tableHoaDon.getColumns().addAll(cotMaHoaDon, cotNgayLap, cotMaKhachHang, cotMaNhanVien, cotTongTien);
        tableHoaDon.setPrefHeight(273.0);
        tableHoaDon.setPrefWidth(1161.0);


        // --- Dựng VBox bên trái (Panel điều khiển) ---
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

        // --- Dựng VBox bên phải ---
        VBox rightVBox = new VBox();
        rightVBox.setPrefHeight(1126.0);
        rightVBox.setPrefWidth(1161.0);

        Label revenueLabel = new Label("Doanh thu");
        revenueLabel.setFont(new Font(18.0));
        revenueLabel.setAlignment(Pos.CENTER);
        revenueLabel.setPrefWidth(1167.0);

        Label invoiceListLabel = new Label("Danh sách Hóa đơn 🧾");
        invoiceListLabel.setTextFill(javafx.scene.paint.Color.rgb(18, 115, 170));
        invoiceListLabel.setFont(new Font(24.0));
        VBox.setMargin(invoiceListLabel, new Insets(8.0, 0, 8.0, 10.0));

        rightVBox.getChildren().addAll(revenueLabel, tableDoanhThu, chartDoanhThu, invoiceListLabel, tableHoaDon);

        // --- Dựng HBox gốc ---
        HBox mainHBox = new HBox(leftVBox, rightVBox);

        // --- Dựng Pane gốc ---
        Pane root = new Pane();
        root.setPrefHeight(895.0);
        root.setPrefWidth(1646.0);

        mainHBox.setLayoutX(14.0);
        mainHBox.setLayoutY(14.0);
        root.getChildren().add(mainHBox);

        // --- BƯỚC 1: Tiêm vào Controller ---
        // Panel trái
        ctrl.btnBang = btnBang;
        ctrl.btnBieuDo = btnBieuDo;
        ctrl.btnXuat = btnXuat;
        ctrl.cboThoiGian = cboThoiGian;
        ctrl.cboXuatfile = cboXuatfile;
        ctrl.dateTu = dateTu;
        ctrl.dateDen = dateDen;
        ctrl.lblTu = lblTu;
        ctrl.lblDen = lblDen;
        // Bảng Doanh Thu
        ctrl.tableDoanhThu = (TableView<ThongKeBanHang>) tableDoanhThu;
        ctrl.cotTG = cotTG;
        ctrl.cotSLHoaDon = cotSLHoaDon;
        ctrl.cotTongGT = cotTongGT;
        ctrl.cotGG = cotGG;
        ctrl.cotDT = cotDT;
        ctrl.cotGTDonTra = cotGTDonTra;
        ctrl.cotDoanhThu = cotDoanhThu;
        // Biểu đồ
        ctrl.xAxis = xAxis;
        ctrl.yAxis = yAxis;
        ctrl.chartDoanhThu = (BarChart<String, Number>) chartDoanhThu;
        // Bảng Hóa Đơn
        ctrl.tableHoaDon = (TableView<HoaDonDisplay>) tableHoaDon;
        ctrl.cotMaHoaDon = cotMaHoaDon;
        ctrl.cotNgayLap = cotNgayLap;
        ctrl.cotMaKhachHang = cotMaKhachHang;
        ctrl.cotMaNhanVien = cotMaNhanVien;
        ctrl.cotTongTien = cotTongTien;


        // --- BƯỚC 2: Tạo Scene, gọi initialize, và hiển thị ---
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThongKeBanHang.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css")).toExternalForm());

        // BƯỚC 3: Gọi initialize của Controller
        ctrl.initialize();

        stage.setScene(scene);
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