package com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuDoi;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LapPhieuDoi_GUI {

    private final com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuDoi.LapPhieuDoiHang_Ctrl ctrl;

    public LapPhieuDoi_GUI(com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuDoi.LapPhieuDoiHang_Ctrl ctrl) {
        this.ctrl = ctrl;
    }

    public void show(Stage stage) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 13px;");

        // ===== Header =====
        Pane headerPane = new Pane();
        headerPane.setPrefSize(1598, 54);
        headerPane.getStyleClass().add("header-pane");
        AnchorPane.setTopAnchor(headerPane, 8.0);
        AnchorPane.setLeftAnchor(headerPane, 28.0);
        AnchorPane.setRightAnchor(headerPane, 20.0);

        ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/exchange_6049036.png")));
        img.setFitWidth(61);
        img.setFitHeight(44);
        img.setLayoutX(288);
        img.setLayoutY(5);
        headerPane.getChildren().add(img);

        Label lblTitle = new Label("LẬP PHIẾU ĐỔI HÀNG");
        lblTitle.getStyleClass().add("title-label");
        lblTitle.setLayoutX(40);
        lblTitle.setLayoutY(21);
        AnchorPane.setTopAnchor(lblTitle, 18.0);
        AnchorPane.setLeftAnchor(lblTitle, 40.0);

        // ===== Search Pane =====
        Pane searchPane = new Pane();
        searchPane.setPrefSize(1167, 70);
        searchPane.getStyleClass().add("search-pane");
        AnchorPane.setTopAnchor(searchPane, 71.0);
        AnchorPane.setLeftAnchor(searchPane, 30.0);
        AnchorPane.setRightAnchor(searchPane, 449.0);

        Label lblTimHD = new Label("Tìm hóa đơn gốc:");
        lblTimHD.setFont(Font.font("System Bold", 13));
        lblTimHD.setLayoutX(20);
        lblTimHD.setLayoutY(20);

        ctrl.txtTimHoaDonGoc = new TextField();
        ctrl.txtTimHoaDonGoc.setPromptText("Nhập mã hóa đơn hoặc tên khách hàng ...");
        ctrl.txtTimHoaDonGoc.setLayoutX(140);
        ctrl.txtTimHoaDonGoc.setLayoutY(15);
        ctrl.txtTimHoaDonGoc.setPrefSize(939, 29);

        ctrl.btnTimHD = new Button("Tìm");
        ctrl.btnTimHD.setLayoutX(1098);
        ctrl.btnTimHD.setLayoutY(15);
        ctrl.btnTimHD.setPrefSize(54, 28);
        ctrl.btnTimHD.setOnAction(e -> ctrl.xuLyTimHoaDonGoc());

        searchPane.getChildren().addAll(lblTimHD, ctrl.txtTimHoaDonGoc, ctrl.btnTimHD);

        // ===== Hint =====
        Label lblHint = new Label("Chọn hóa đơn gốc để bắt đầu đổi hàng");
        lblHint.getStyleClass().add("hint-label");
        AnchorPane.setTopAnchor(lblHint, 150.0);
        AnchorPane.setLeftAnchor(lblHint, 40.0);

        // ===== Table sản phẩm gốc =====
        Label lblSPGoc = new Label("Sản phẩm trong hóa đơn gốc");
        lblSPGoc.getStyleClass().add("section-label");
        AnchorPane.setTopAnchor(lblSPGoc, 180.0);
        AnchorPane.setLeftAnchor(lblSPGoc, 40.0);

        ctrl.tblSanPhamGoc = new TableView<>();
        ctrl.tblSanPhamGoc.setPrefSize(1156, 248);
        ctrl.tblSanPhamGoc.getStyleClass().add("main-table");
        AnchorPane.setTopAnchor(ctrl.tblSanPhamGoc, 210.0);
        AnchorPane.setLeftAnchor(ctrl.tblSanPhamGoc, 40.0);
        AnchorPane.setRightAnchor(ctrl.tblSanPhamGoc, 450.0);

        ctrl.colSTTGoc = new TableColumn<>("STT");
        ctrl.colTenSPGoc = new TableColumn<>("Tên sản phẩm");
        ctrl.colSoLuongGoc = new TableColumn<>("Số lượng");
        ctrl.colDonViGoc = new TableColumn<>("Đơn vị");
        ctrl.colDonGiaGoc = new TableColumn<>("Đơn giá");
        ctrl.colGiamGiaGoc = new TableColumn<>("Giảm giá");
        ctrl.colThanhTienGoc = new TableColumn<>("Thành tiền");
        ctrl.colDoi = new TableColumn<>("Đổi");

        ctrl.tblSanPhamGoc.getColumns().addAll(
                ctrl.colSTTGoc, ctrl.colTenSPGoc, ctrl.colSoLuongGoc, ctrl.colDonViGoc,
                ctrl.colDonGiaGoc, ctrl.colGiamGiaGoc, ctrl.colThanhTienGoc, ctrl.colDoi
        );

        // ===== Table sản phẩm đổi =====
        Label lblSPDoi = new Label("Sản phẩm đổi");
        lblSPDoi.getStyleClass().add("section-label");
        AnchorPane.setTopAnchor(lblSPDoi, 474.0);
        AnchorPane.setLeftAnchor(lblSPDoi, 43.0);

        ctrl.tblSanPhamDoi = new TableView<>();
        ctrl.tblSanPhamDoi.setPrefSize(1160, 263);
        ctrl.tblSanPhamDoi.getStyleClass().add("main-table");
        AnchorPane.setTopAnchor(ctrl.tblSanPhamDoi, 510.0);
        AnchorPane.setLeftAnchor(ctrl.tblSanPhamDoi, 38.0);
        AnchorPane.setRightAnchor(ctrl.tblSanPhamDoi, 451.6);
        AnchorPane.setBottomAnchor(ctrl.tblSanPhamDoi, 122.8);

        ctrl.colSTTDoi = new TableColumn<>("STT");
        ctrl.colTenSPDoi = new TableColumn<>("Tên sản phẩm");
        ctrl.colSoLuongDoi = new TableColumn<>("Số lượng đổi");
        ctrl.colDonViDoi = new TableColumn<>("Đơn vị");
        ctrl.colLyDo = new TableColumn<>("Lý do đổi");
        ctrl.colBo = new TableColumn<>("Bỏ");

        ctrl.tblSanPhamDoi.getColumns().addAll(
                ctrl.colSTTDoi, ctrl.colTenSPDoi, ctrl.colSoLuongDoi,
                ctrl.colDonViDoi, ctrl.colLyDo, ctrl.colBo
        );

        // ===== Info Panel (phải) =====
        Pane infoPane = new Pane();
        infoPane.setPrefWidth(391);
        infoPane.getStyleClass().add("info-pane");
        AnchorPane.setTopAnchor(infoPane, 70.0);
        AnchorPane.setRightAnchor(infoPane, 30.0);
        AnchorPane.setBottomAnchor(infoPane, 120.0);

        Label lblInfoTitle = new Label("THÔNG TIN PHIẾU ĐỔI HÀNG");
        lblInfoTitle.getStyleClass().add("bold-label");
        lblInfoTitle.setLayoutX(30);
        lblInfoTitle.setLayoutY(61);

        ctrl.lblMaHDGoc = new TextField();
        ctrl.lblMaHDGoc.setEditable(false);
        ctrl.lblMaHDGoc.setLayoutX(170);
        ctrl.lblMaHDGoc.setLayoutY(95);
        ctrl.lblMaHDGoc.setPrefWidth(140);

        ctrl.lblTenKH = new TextField();
        ctrl.lblTenKH.setEditable(false);
        ctrl.lblTenKH.setLayoutX(170);
        ctrl.lblTenKH.setLayoutY(136);
        ctrl.lblTenKH.setPrefWidth(140);

        ctrl.lblSDT = new TextField();
        ctrl.lblSDT.setEditable(false);
        ctrl.lblSDT.setLayoutX(170);
        ctrl.lblSDT.setLayoutY(183);
        ctrl.lblSDT.setPrefWidth(140);

        ctrl.dpNgayLapPhieu = new DatePicker();
        ctrl.dpNgayLapPhieu.setLayoutX(170);
        ctrl.dpNgayLapPhieu.setLayoutY(227);
        ctrl.dpNgayLapPhieu.setPrefWidth(140);

        ctrl.txtGhiChu = new TextArea();
        ctrl.txtGhiChu.setLayoutX(30);
        ctrl.txtGhiChu.setLayoutY(315);
        ctrl.txtGhiChu.setPrefSize(322, 172);

        Button btnDoi = new Button("Đổi hàng");
        btnDoi.getStyleClass().add("success-btn");
        btnDoi.setPrefSize(327, 44);
        btnDoi.setLayoutX(29);
        btnDoi.setLayoutY(532);
        btnDoi.setOnAction(e -> ctrl.xuLyDoiHang());

        Button btnIn = new Button("Đổi hàng và in phiếu đổi");
        btnIn.getStyleClass().add("print-btn");
        btnIn.setPrefSize(327, 44);
        btnIn.setLayoutX(30);
        btnIn.setLayoutY(584);
        btnIn.setOnAction(e -> ctrl.xuLyInPhieuDoi());

        Button btnHuy = new Button("Hủy");
        btnHuy.setStyle("-fx-background-color: red;");
        btnHuy.setPrefSize(331, 44);
        btnHuy.setLayoutX(28);
        btnHuy.setLayoutY(636);
        btnHuy.setOnAction(e -> ctrl.xuLyHuy());

        infoPane.getChildren().addAll(lblInfoTitle,
                new Label("Mã hóa đơn gốc:") {{ setLayoutX(30); setLayoutY(100); }},
                ctrl.lblMaHDGoc,
                new Label("Tên khách hàng:") {{ setLayoutX(31); setLayoutY(141); }},
                ctrl.lblTenKH,
                new Label("Số điện thoại:") {{ setLayoutX(30); setLayoutY(189); }},
                ctrl.lblSDT,
                new Label("Ngày lập phiếu:") {{ setLayoutX(30); setLayoutY(233); }},
                ctrl.dpNgayLapPhieu,
                new Label("Ghi chú:") {{ setLayoutX(30); setLayoutY(269); }},
                ctrl.txtGhiChu, btnDoi, btnIn, btnHuy
        );

        // ===== Add all to root =====
        root.getChildren().addAll(headerPane, lblTitle, searchPane, lblHint, lblSPGoc, ctrl.tblSanPhamGoc, lblSPDoi, ctrl.tblSanPhamDoi, infoPane);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/LapPhieuDoiHang.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Lập Phiếu Đổi Hàng");
        stage.show();
    }
}