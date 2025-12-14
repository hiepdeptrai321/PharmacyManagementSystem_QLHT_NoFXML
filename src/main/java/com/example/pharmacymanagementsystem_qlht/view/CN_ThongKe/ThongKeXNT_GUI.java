package com.example.pharmacymanagementsystem_qlht.view.CN_ThongKe;

// Import Controller
import com.example.pharmacymanagementsystem_qlht.controller.CN_ThongKe.ThongKeXNT_Ctrl;

import com.example.pharmacymanagementsystem_qlht.model.ThongKeTonKho;
import com.example.pharmacymanagementsystem_qlht.model.ThuocHetHan;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class ThongKeXNT_GUI {

    // Bỏ hết các khai báo public ở đây

    // Sửa lại phương thức:
    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, ThongKeXNT_Ctrl ctrl) {

        // --- Tạo các thành phần giao diện (dưới dạng biến cục bộ) ---
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

        // Tiêu đề
        Label titleLabel = new Label("Báo cáo Xuất-Nhập-Tồn");
        titleLabel.setFont(new Font(28.0));
        titleLabel.setPrefHeight(34.0);
        titleLabel.setPrefWidth(316.0);

        // Icon
        ImageView titleIcon = new ImageView();
        try {
            Image iconImage = new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/boxes-11430.png"));
            titleIcon.setImage(iconImage);
            titleIcon.setFitHeight(40.0);
            titleIcon.setFitWidth(44.0);
            titleIcon.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Không tải được icon: " + e.getMessage());
        }
        Label iconLabel = new Label();
        iconLabel.setGraphic(titleIcon);
        iconLabel.setPrefHeight(38.0);
        iconLabel.setPrefWidth(30.0);
        HBox.setMargin(iconLabel, new Insets(0, 0, 0, 10.0));

        HBox titleHBox = new HBox(titleLabel, iconLabel);
        titleHBox.setPrefHeight(41.0);
        titleHBox.setPrefWidth(281.0);

        Separator separator = new Separator();
        separator.setPrefWidth(200.0);

        // Tìm nhanh
        Label searchLabel = new Label("Tìm nhanh");
        searchLabel.setFont(new Font(18.0));
        txtTimNhanh.setPromptText("Nhập mã, tên");
        txtTimNhanh.setPrefHeight(40.0);

        HBox spacer = new HBox();
        spacer.setAlignment(javafx.geometry.Pos.CENTER);
        VBox.setMargin(spacer, new Insets(5.0, 0, 0, 0));

        // Thời gian
        Label timeLabel = new Label("Thời gian");
        timeLabel.setFont(new Font(18.0));
        timeLabel.setPrefHeight(27.0);
        VBox.setMargin(timeLabel, new Insets(10.0, 0, 0, 0));
        cboThoiGian.setPrefHeight(49.0);
        cboThoiGian.setPrefWidth(378.0);

        // Từ/Đến
        lblTu.setFont(new Font(18.0));
        dateTu.setPrefHeight(52.0);
        dateTu.setPrefWidth(377.0);
        lblDen.setFont(new Font(18.0));
        dateDen.setPrefHeight(52.0);
        dateDen.setPrefWidth(376.0);

        // Xuất file
        Label exportLabel = new Label("Xuất file");
        exportLabel.setFont(new Font(18.0));
        VBox.setMargin(exportLabel, new Insets(10.0, 0, 0, 0));
        cboXuat.setPrefHeight(49.0);
        cboXuat.setPrefWidth(375.0);
        cboXuat.setPromptText("Chọn đinh dạng ");

        btnXuat.setMnemonicParsing(false);
        btnXuat.setPrefHeight(62.0);
        btnXuat.setPrefWidth(438.0);
        VBox.setMargin(btnXuat, new Insets(10.0, 0, 0, 0));

        // Thêm tất cả vào VBox trái
        leftVBox.getChildren().addAll(
                titleHBox, separator, searchLabel, txtTimNhanh, spacer,
                timeLabel, cboThoiGian, lblTu, dateTu, lblDen, dateDen,
                exportLabel, cboXuat, btnXuat
        );

        // --- Dựng VBox bên phải (Chứa 2 bảng) ---
        VBox rightVBox = new VBox();

        Label warningLabel = new Label("Những sản phẩm đã hết hạn⚠");
        warningLabel.setTextFill(javafx.scene.paint.Color.rgb(196, 35, 35));
        warningLabel.setFont(new Font(18.0));
        VBox.setMargin(warningLabel, new Insets(10.0, 0, 10.0, 10.0));

        rightVBox.getChildren().addAll(tbTon, warningLabel, tbHetHan);

        // --- Dựng HBox gốc (chứa 2 VBox) ---
        HBox mainHBox = new HBox(leftVBox, rightVBox);

        // --- Dựng Pane gốc ---
        Pane root = new Pane();
        root.setPrefHeight(895.0);
        root.setPrefWidth(1646.0);

        // Đặt HBox vào trong Pane (giống FXML)
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
        // (Vẫn giữ dòng này để tránh lỗi NullPointer nếu ViewEmbedder cần Scene)
        Scene scene = new Scene(root);

        // --- SỬA LẠI: Gắn CSS trực tiếp vào ROOT (Quan trọng nhất) ---
        String cssPath = "/com/example/pharmacymanagementsystem_qlht/css/ThongKeBanHang.css";
        java.net.URL cssUrl = getClass().getResource(cssPath);

        if (cssUrl != null) {
            // CÁCH CŨ (Chỉ add vào Scene -> Sai khi nhúng):
            // scene.getStylesheets().add(cssUrl.toExternalForm());

            // CÁCH MỚI (Add thẳng vào Pane gốc -> Đi đâu cũng có CSS):
            root.getStylesheets().add(cssUrl.toExternalForm());

            System.out.println("Đã gắn CSS vào Root Pane thành công!");
        } else {
            // Thử tìm đường dẫn ngắn nếu đường dẫn dài lỗi
            var shortUrl = getClass().getResource("/css/ThongKeBanHang.css");
            if(shortUrl != null) {
                root.getStylesheets().add(shortUrl.toExternalForm());
            } else {
                System.err.println("Không tìm thấy CSS!");
            }
        }

        // --- BƯỚC 3: Set Scene vào Stage ---
        stage.setScene(scene);

        // --- BƯỚC 4: Khởi tạo dữ liệu ---
        ctrl.initialize();
        stage.setTitle("Báo cáo Xuất - Nhập - Tồn");
    }
}