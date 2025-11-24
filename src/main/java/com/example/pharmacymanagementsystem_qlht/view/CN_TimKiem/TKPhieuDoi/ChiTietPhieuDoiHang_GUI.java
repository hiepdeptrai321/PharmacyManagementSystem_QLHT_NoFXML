package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDoi;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDoiHang.ChiTietPhieuDoiHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuDoiHang;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ChiTietPhieuDoiHang_GUI {

    public void showWithController(Stage stage, ChiTietPhieuDoiHang_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14px;");

        // SplitPane
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.7007);
        AnchorPane.setTopAnchor(splitPane, 0.0);
        AnchorPane.setBottomAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);

        /* ==================== LEFT PANE ==================== */
        AnchorPane leftPane = new AnchorPane();
        Pane paneLeft = new Pane();
        AnchorPane.setTopAnchor(paneLeft, 0.0);
        AnchorPane.setBottomAnchor(paneLeft, 0.0);
        AnchorPane.setLeftAnchor(paneLeft, 0.0);
        AnchorPane.setRightAnchor(paneLeft, 0.0);

        Label lblTitleLeft = new Label("DANH SÁCH SẢN PHẨM ĐỔI");
        lblTitleLeft.getStyleClass().add("section-label");
        lblTitleLeft.setLayoutX(9);
        lblTitleLeft.setLayoutY(43);

        // TableView
        TableView<ChiTietPhieuDoiHang> tblChiTietPhieuDoi = new TableView<>();
        tblChiTietPhieuDoi.setId("tblChiTietPhieuDoi");
        tblChiTietPhieuDoi.setLayoutX(11);
        tblChiTietPhieuDoi.setLayoutY(73);
        tblChiTietPhieuDoi.setPrefSize(1134, 806);
        tblChiTietPhieuDoi.getStyleClass().add("main-table");

        TableColumn<ChiTietPhieuDoiHang, String> colSTT = new TableColumn<>("STT");
        colSTT.setPrefWidth(73.66);

        TableColumn<ChiTietPhieuDoiHang, String> colTenSP = new TableColumn<>("Tên sản phẩm/Hoạt chất");
        colTenSP.setPrefWidth(475);

        TableColumn<ChiTietPhieuDoiHang, String> colSoLuong = new TableColumn<>("Số lượng đổi");
        colSoLuong.setPrefWidth(145.66);

        TableColumn<ChiTietPhieuDoiHang, String> colDonVi = new TableColumn<>("Đơn vị");
        colDonVi.setPrefWidth(113.66);

        TableColumn<ChiTietPhieuDoiHang, String> colLyDo = new TableColumn<>("Lý do đổi");
        colLyDo.setPrefWidth(323.33);

        tblChiTietPhieuDoi.getColumns().addAll(colSTT, colTenSP, colSoLuong, colDonVi, colLyDo);
        tblChiTietPhieuDoi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label lblMaPhieuDoi = new Label("Mã phiếu đổi:");
        lblMaPhieuDoi.getStyleClass().add("bold-label");
        lblMaPhieuDoi.setLayoutX(10);
        lblMaPhieuDoi.setLayoutY(13);

        Label lblMaPhieuDoiValue = new Label("PDH012345");
        lblMaPhieuDoiValue.setId("lblMaPhieuDoiValue");
        lblMaPhieuDoiValue.getStyleClass().add("value-label");
        lblMaPhieuDoiValue.setLayoutX(119);
        lblMaPhieuDoiValue.setLayoutY(14);

        Label lblNgayLap = new Label("Ngày lập:");
        lblNgayLap.getStyleClass().add("bold-label");
        lblNgayLap.setLayoutX(228);
        lblNgayLap.setLayoutY(14);

        Label lblNgayLapValue = new Label("08/10/2025");
        lblNgayLapValue.setId("lblNgayLapValue");
        lblNgayLapValue.getStyleClass().add("value-label");
        lblNgayLapValue.setLayoutX(306);
        lblNgayLapValue.setLayoutY(14);
        lblNgayLapValue.setPrefSize(317, 20);

        paneLeft.getChildren().addAll(lblTitleLeft, tblChiTietPhieuDoi,
                lblMaPhieuDoi, lblMaPhieuDoiValue, lblNgayLap, lblNgayLapValue);

        leftPane.getChildren().add(paneLeft);

        /* ==================== RIGHT PANE ==================== */
        AnchorPane rightPane = new AnchorPane();
        rightPane.getStyleClass().add("right-pane-bg");

        Pane paneRight = new Pane();
        paneRight.setLayoutY(10);
        paneRight.setPrefWidth(379);
        AnchorPane.setTopAnchor(paneRight, 10.0);
        AnchorPane.setBottomAnchor(paneRight, 10.0);
        AnchorPane.setLeftAnchor(paneRight, 0.0);
        AnchorPane.setRightAnchor(paneRight, 20.0);

        // Section: Thông tin phiếu đổi
        Label lblThongTin = new Label("THÔNG TIN PHIẾU ĐỔI");
        lblThongTin.getStyleClass().add("section-label");

        Pane infoPane = new Pane();
        infoPane.setLayoutY(41);
        infoPane.setPrefSize(469, 362);
        infoPane.getStyleClass().add("info-pane");

        Label lblTenNV = new Label("Tên nhân viên lập:");
        lblTenNV.getStyleClass().add("bold-label");
        lblTenNV.setLayoutX(5);
        lblTenNV.setLayoutY(5);

        Label lblTenNhanVienValue = new Label();
        lblTenNhanVienValue.setId("lblTenNhanVienValue");
        lblTenNhanVienValue.getStyleClass().add("value-label");
        lblTenNhanVienValue.setLayoutX(160);
        lblTenNhanVienValue.setLayoutY(5);

        Label lblTenKH = new Label("Tên khách hàng:");
        lblTenKH.getStyleClass().add("bold-label");
        lblTenKH.setLayoutX(5);
        lblTenKH.setLayoutY(46);

        Label lblTenKhachHangValue = new Label();
        lblTenKhachHangValue.setId("lblTenKhachHangValue");
        lblTenKhachHangValue.getStyleClass().add("value-label");
        lblTenKhachHangValue.setLayoutX(160);
        lblTenKhachHangValue.setLayoutY(35);

        Label lblSDTKH = new Label("SĐT Khách hàng:");
        lblSDTKH.getStyleClass().add("bold-label");
        lblSDTKH.setLayoutX(5);
        lblSDTKH.setLayoutY(87);

        Label lblSDTKhachHangValue = new Label();
        lblSDTKhachHangValue.setId("lblSDTKhachHangValue");
        lblSDTKhachHangValue.getStyleClass().add("value-label");
        lblSDTKhachHangValue.setLayoutX(160);
        lblSDTKhachHangValue.setLayoutY(65);

        Label lblGhiChu = new Label("Ghi chú:");
        lblGhiChu.getStyleClass().add("bold-label");
        lblGhiChu.setLayoutX(5);
        lblGhiChu.setLayoutY(127);

        Label lblGhiChuValue = new Label();
        lblGhiChuValue.setId("lblGhiChuValue");
        lblGhiChuValue.getStyleClass().add("value-label");
        lblGhiChuValue.setLayoutX(90);
        lblGhiChuValue.setLayoutY(127);
        lblGhiChuValue.setPrefSize(288, 40);
        lblGhiChuValue.setWrapText(true);

        infoPane.getChildren().addAll(lblTenNV, lblTenNhanVienValue,
                lblTenKH, lblTenKhachHangValue,
                lblSDTKH, lblSDTKhachHangValue,
                lblGhiChu, lblGhiChuValue);

        Separator sep1 = new Separator();
        sep1.setLayoutY(216);



        // Buttons
        Button btnInPhieuDoi = new Button("In Phiếu Đổi");
        btnInPhieuDoi.setId("btnInPhieuDoi");
        btnInPhieuDoi.setPrefSize(471, 51);
        btnInPhieuDoi.setLayoutX(9);
        btnInPhieuDoi.setLayoutY(570);
        btnInPhieuDoi.getStyleClass().add("print-btn");
        btnInPhieuDoi.setFont(Font.font("System Bold", 14));
        btnInPhieuDoi.setEffect(new DropShadow(5, Color.rgb(166, 164, 164)));

        Button btnDong = new Button("Đóng");
        btnDong.setId("btnDong");
        btnDong.setPrefSize(470, 51);
        btnDong.setLayoutX(11);
        btnDong.setLayoutY(630);
        btnDong.getStyleClass().add("danger-btn");
        btnDong.setFont(Font.font("System Bold", 14));
        btnDong.setEffect(new DropShadow(5, Color.rgb(166, 164, 164)));

        paneRight.getChildren().addAll(lblThongTin, infoPane, sep1, btnInPhieuDoi, btnDong);
        rightPane.getChildren().add(paneRight);

        // Add to splitPane
        splitPane.getItems().addAll(leftPane, rightPane);
        root.getChildren().add(splitPane);

        // Scene setup
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ChiTietPhieuDoi.css")).toExternalForm());

        // Inject into controller
        ctrl.tblChiTietPhieuDoi = tblChiTietPhieuDoi;
        ctrl.colSTT = colSTT;
        ctrl.colTenSP = colTenSP;
        ctrl.colSoLuong = colSoLuong;
        ctrl.colDonVi = colDonVi;
        ctrl.colLyDo = colLyDo;

        ctrl.lblMaPhieuDoiValue = lblMaPhieuDoiValue;
        ctrl.lblNgayLapValue = lblNgayLapValue;
        ctrl.lblTenNhanVienValue = lblTenNhanVienValue;
        ctrl.lblTenKhachHangValue = lblTenKhachHangValue;
        ctrl.lblSDTKhachHangValue = lblSDTKhachHangValue;
        ctrl.lblGhiChuValue = lblGhiChuValue;

        ctrl.btnInPhieuDoi = btnInPhieuDoi;
        ctrl.btnDong = btnDong;

        // Initialize controller logic
        ctrl.initialize();
        stage.setTitle("Chi tiết phiếu đổi hàng");
        stage.setScene(scene);
        stage.show();
    }
}
