package com.example.pharmacymanagementsystem_qlht.view.CN_ThongKe;

// Import Controller
import com.example.pharmacymanagementsystem_qlht.controller.CN_ThongKe.ThongKeXNT_Ctrl;

import com.example.pharmacymanagementsystem_qlht.model.ThongKeTonKho;
import com.example.pharmacymanagementsystem_qlht.model.ThuocHetHan;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ThongKeXNT_GUI {

    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, ThongKeXNT_Ctrl ctrl) {

        // --- Tạo các thành phần giao diện ---

        // Bảng Tồn kho
        TableView<ThongKeTonKho> tbTon = new TableView<>();
        TableColumn<ThongKeTonKho, Integer> ColTDK = new TableColumn<>("Tồn đầu kỳ");
        TableColumn<ThongKeTonKho, String> colDVT = new TableColumn<>("ĐVT");
        TableColumn<ThongKeTonKho, String> colMaThuoc = new TableColumn<>("Mã thuốc");
        TableColumn<ThongKeTonKho, Integer> colNTK = new TableColumn<>("Nhập trong kỳ");
        TableColumn<ThongKeTonKho, String> colTenThuoc = new TableColumn<>("Tên thuốc");
        TableColumn<ThongKeTonKho, Integer> colTCK = new TableColumn<>("Tồn cuối kỳ");
        TableColumn<ThongKeTonKho, Integer> colXTK = new TableColumn<>("Xuất trong kỳ");

        // Bảng Hết hạn
        TableView<ThuocHetHan> tbHetHan = new TableView<>();
        TableColumn<ThuocHetHan, String> colMaThuocHH = new TableColumn<>("Mã Thuốc");
        TableColumn<ThuocHetHan, LocalDate> colNgayHH = new TableColumn<>("Ngày hết hạn");
        TableColumn<ThuocHetHan, Integer> colSoLuong = new TableColumn<>("Số Lượng");
        TableColumn<ThuocHetHan, String> cotTenThuocHH = new TableColumn<>("Tên Thuốc");

        // Panel bên trái
        Button btnXuat = new Button("Xuất File 💾");
        ComboBox<String> cboThoiGian = new ComboBox<>();
        ComboBox<String> cboXuat = new ComboBox<>();
        DatePicker dateDen = new DatePicker();
        DatePicker dateTu = new DatePicker();
        TextField txtTimNhanh = new TextField();

        // Label
        Label lblTu = new Label("Từ:");
        Label lblDen = new Label("Đến:");

        // --- Cấu hình các cột cho bảng ---
        // Bảng Tồn kho
        colMaThuoc.setPrefWidth(92.33);
        colTenThuoc.setPrefWidth(186.0);
        colDVT.setPrefWidth(112.0);
        ColTDK.setPrefWidth(184.66);
        colNTK.setPrefWidth(173.66);
        colXTK.setPrefWidth(224.33);
        colTCK.setPrefWidth(246.0);
        tbTon.getColumns().addAll(colMaThuoc, colTenThuoc, colDVT, ColTDK, colNTK, colXTK, colTCK);
        tbTon.setPrefHeight(538.0);
        tbTon.setPrefWidth(1223.0);

        // Bảng Hết hạn
        colMaThuocHH.setPrefWidth(197.66);
        cotTenThuocHH.setPrefWidth(478.33);
        colSoLuong.setPrefWidth(164.66);
        colNgayHH.setPrefWidth(379.0);
        tbHetHan.getColumns().addAll(colMaThuocHH, cotTenThuocHH, colSoLuong, colNgayHH);
        tbHetHan.setPrefHeight(259.0);
        tbHetHan.setPrefWidth(1161.0);


        // --- Dựng VBox bên trái (Panel điều khiển) ---
        VBox leftVBox = new VBox();
        leftVBox.setPrefHeight(863.0);
        leftVBox.setPrefWidth(394.0);

        // [STYLE] Thêm class vbox để nhận khung trắng bo góc
        leftVBox.getStyleClass().add("vbox");

        // Tiêu đề
        Label titleLabel = new Label("Thống kê XNT");
        // [STYLE] Thêm class title thay vì setFont thủ công
        titleLabel.getStyleClass().add("title");

        // Icon
        ImageView titleIcon = createIcon("/com/example/pharmacymanagementsystem_qlht/img/boxes-11430.png", 40, 44);
        HBox titleHBox = new HBox(titleLabel, new Label("", titleIcon));
        titleHBox.setAlignment(Pos.CENTER_LEFT);

        Separator separator = new Separator();
        separator.setPrefWidth(200.0);

        // Tìm nhanh
        Label searchLabel = new Label("Tìm nhanh");
        // [STYLE] Thêm class header-label
        searchLabel.getStyleClass().add("header-label");

        txtTimNhanh.setPromptText("Nhập mã, tên thuốc...");
        txtTimNhanh.setPrefHeight(40.0);

        // Thời gian
        Label timeLabel = new Label("Thời gian");
        timeLabel.getStyleClass().add("header-label");
        VBox.setMargin(timeLabel, new Insets(10.0, 0, 0, 0));

        cboThoiGian.setPrefHeight(49.0);
        cboThoiGian.setPrefWidth(378.0);

        // Từ/Đến
        lblTu.getStyleClass().add("header-label");
        dateTu.setPrefHeight(45.0);
        dateTu.setPrefWidth(377.0);

        lblDen.getStyleClass().add("header-label");
        dateDen.setPrefHeight(45.0);
        dateDen.setPrefWidth(376.0);

        // Xuất file
        Label exportLabel = new Label("Xuất file");
        exportLabel.getStyleClass().add("header-label");
        VBox.setMargin(exportLabel, new Insets(10.0, 0, 0, 0));

        cboXuat.setPrefHeight(49.0);
        cboXuat.setPrefWidth(375.0);
        cboXuat.setPromptText("Chọn định dạng");

        btnXuat.setMnemonicParsing(false);
        btnXuat.setPrefHeight(62.0);
        btnXuat.setPrefWidth(438.0);
        // [STYLE] Nút sẽ tự nhận class .button từ CSS
        VBox.setMargin(btnXuat, new Insets(10.0, 0, 0, 0));

        // Thêm tất cả vào VBox trái
        leftVBox.getChildren().addAll(
                titleHBox, separator,
                searchLabel, txtTimNhanh,
                timeLabel, cboThoiGian,
                lblTu, dateTu,
                lblDen, dateDen,
                exportLabel, cboXuat, btnXuat
        );

        // --- Dựng VBox bên phải (Chứa 2 bảng) ---
        VBox rightVBox = new VBox();
        // [STYLE] Thêm class vbox
        rightVBox.getStyleClass().add("vbox");

        Label warningLabel = new Label("Những sản phẩm đã hết hạn ⚠");
        // [STYLE] Sử dụng class sub-title (màu đỏ đậm, font to) thay vì set cứng
        warningLabel.getStyleClass().add("sub-title");
        VBox.setMargin(warningLabel, new Insets(10.0, 0, 5.0, 10.0));

        rightVBox.getChildren().addAll(tbTon, warningLabel, tbHetHan);

        // --- Dựng HBox gốc (chứa 2 VBox) ---
        // [LAYOUT] Thêm spacing 15 để tách 2 panel ra
        HBox mainHBox = new HBox(15, leftVBox, rightVBox);

        // --- Dựng Pane gốc ---
        Pane root = new Pane();
        root.setPrefHeight(895.0);
        root.setPrefWidth(1646.0);

        // [STYLE] Set ID mainPane để nhận nền gradient
        root.setId("mainPane");

        mainHBox.setLayoutX(14.0);
        mainHBox.setLayoutY(14.0);
        root.getChildren().add(mainHBox);

        // --- BƯỚC 1: Tiêm vào Controller ---
        // Bảng Tồn kho
        ctrl.tbTon = (TableView<ThongKeTonKho>) tbTon;
        ctrl.ColTDK = ColTDK;
        ctrl.colDVT = colDVT;
        ctrl.colMaThuoc = colMaThuoc;
        ctrl.colNTK = colNTK;
        ctrl.colTenThuoc = colTenThuoc;
        ctrl.colTCK = colTCK;
        ctrl.colXTK = colXTK;
        // Bảng Hết hạn
        ctrl.tbHetHan = (TableView<ThuocHetHan>) tbHetHan;
        ctrl.colMaThuocHH = colMaThuocHH;
        ctrl.colNgayHH = colNgayHH;
        ctrl.colSoLuong = colSoLuong;
        ctrl.cotTenThuocHH = cotTenThuocHH;
        // Panel bên trái
        ctrl.btnXuat = btnXuat;
        ctrl.cboThoiGian = cboThoiGian;
        ctrl.cboXuat = cboXuat;
        ctrl.dateDen = dateDen;
        ctrl.dateTu = dateTu;
        ctrl.txtTimNhanh = txtTimNhanh;
        ctrl.lblTu = lblTu;
        ctrl.lblDen = lblDen;

        // --- BƯỚC 2: Tạo Scene ---
        Scene scene = new Scene(root);

        // --- BƯỚC 3: Gắn CSS (Quan trọng) ---
        // Dùng chung CSS với ThongKeBanHang
        String cssPath = "/com/example/pharmacymanagementsystem_qlht/css/ThongKeBanHang.css";
        java.net.URL cssUrl = getClass().getResource(cssPath);

        if (cssUrl != null) {
            root.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("Đã gắn CSS vào Root Pane thành công!");
        } else {
            // Fallback tìm đường dẫn ngắn
            var shortUrl = getClass().getResource("/css/ThongKeBanHang.css");
            if(shortUrl != null) {
                root.getStylesheets().add(shortUrl.toExternalForm());
            } else {
                System.err.println("Không tìm thấy CSS ThongKeBanHang.css!");
            }
        }

        // --- BƯỚC 4: Set Scene vào Stage và Init ---
        stage.setScene(scene);
        ctrl.initialize();
        stage.setTitle("Báo cáo Xuất - Nhập - Tồn");
    }

    // Hàm hỗ trợ tạo icon (cho code gọn gàng giống mẫu)
    private ImageView createIcon(String path, double height, double width) {
        try {
            Image image = new Image(getClass().getResourceAsStream(path));
            ImageView icon = new ImageView(image);
            icon.setFitHeight(height);
            icon.setFitWidth(width);
            icon.setPreserveRatio(true);
            return icon;
        } catch (Exception e) {
            System.err.println("Lỗi tải icon: " + path);
            return new ImageView();
        }
    }
}