package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDoi;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDoiHang.ChiTietPhieuDoiHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuDoiHang;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ChiTietPhieuDoiHang_GUI extends Application {
    public static final String RESOURCE_BASE_PATH = "/com/example/pharmacymanagementsystem_qlht/";

    @Override
    public void start(Stage stage) {
        ChiTietPhieuDoiHang_Ctrl ctrl = new ChiTietPhieuDoiHang_Ctrl();
        showWithController(stage, ctrl);
    }

    public void showWithController(Stage stage, ChiTietPhieuDoiHang_Ctrl ctrl) {
        AnchorPane root = buildPane(ctrl);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(RESOURCE_BASE_PATH + "css/ChiTietPhieuDoi.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Chi Tiết Phiếu Đổi Hàng");
        stage.show();
    }

    private AnchorPane buildPane(ChiTietPhieuDoiHang_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);

        SplitPane splitPane = new SplitPane();
        AnchorPane.setTopAnchor(splitPane, 0.0);
        AnchorPane.setBottomAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);
        splitPane.setDividerPositions(0.70);

        // Left side
        AnchorPane leftPane = new AnchorPane();
        Pane leftContent = new Pane();
        leftContent.setLayoutX(6);
        leftContent.setLayoutY(43);

        Label lblDanhSach = new Label("DANH SÁCH SẢN PHẨM ĐỔI");
        lblDanhSach.getStyleClass().add("section-label");
        lblDanhSach.setLayoutX(9);
        lblDanhSach.setLayoutY(43);

        TableView<ChiTietPhieuDoiHang> tblChiTietPhieuDoi = new TableView<>();
        ctrl.tblChiTietPhieuDoi = tblChiTietPhieuDoi;
        tblChiTietPhieuDoi.setLayoutX(11);
        tblChiTietPhieuDoi.setLayoutY(73);
        tblChiTietPhieuDoi.setPrefSize(1134, 806);
        tblChiTietPhieuDoi.getStyleClass().add("main-table");
        tblChiTietPhieuDoi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<ChiTietPhieuDoiHang, String> colSTT = new TableColumn<>("STT");
        ctrl.colSTT = colSTT;
        colSTT.setPrefWidth(74);

        TableColumn<ChiTietPhieuDoiHang, String> colTenSP = new TableColumn<>("Tên sản phẩm/Hoạt chất");
        ctrl.colTenSP = colTenSP;
        colTenSP.setPrefWidth(475);

        TableColumn<ChiTietPhieuDoiHang, String> colSoLuong = new TableColumn<>("Số lượng đổi");
        ctrl.colSoLuong = colSoLuong;
        colSoLuong.setPrefWidth(146);

        TableColumn<ChiTietPhieuDoiHang, String> colDonVi = new TableColumn<>("Đơn vị");
        ctrl.colDonVi = colDonVi;
        colDonVi.setPrefWidth(114);

        TableColumn<ChiTietPhieuDoiHang, String> colLyDo = new TableColumn<>("Lý do đổi");
        ctrl.colLyDo = colLyDo;
        colLyDo.setPrefWidth(323);

        tblChiTietPhieuDoi.getColumns().addAll(colSTT, colTenSP, colSoLuong, colDonVi, colLyDo);

        Label lblMaPhieuDoi = new Label("Mã phiếu đổi:");
        lblMaPhieuDoi.getStyleClass().add("bold-label");
        lblMaPhieuDoi.setLayoutX(10);
        lblMaPhieuDoi.setLayoutY(13);

        Label lblMaPhieuDoiValue = new Label("PDH012345");
        ctrl.lblMaPhieuDoiValue = lblMaPhieuDoiValue;
        lblMaPhieuDoiValue.getStyleClass().add("value-label");
        lblMaPhieuDoiValue.setLayoutX(119);
        lblMaPhieuDoiValue.setLayoutY(14);

        Label lblNgayLap = new Label("Ngày lập:");
        lblNgayLap.getStyleClass().add("bold-label");
        lblNgayLap.setLayoutX(228);
        lblNgayLap.setLayoutY(14);

        Label lblNgayLapValue = new Label("08/10/2025");
        ctrl.lblNgayLapValue = lblNgayLapValue;
        lblNgayLapValue.getStyleClass().add("value-label");
        lblNgayLapValue.setLayoutX(306);
        lblNgayLapValue.setLayoutY(14);

        leftContent.getChildren().addAll(lblDanhSach, tblChiTietPhieuDoi, lblMaPhieuDoi, lblMaPhieuDoiValue, lblNgayLap, lblNgayLapValue);
        leftPane.getChildren().add(leftContent);

        // Right side
        AnchorPane rightPane = new AnchorPane();
        rightPane.getStyleClass().add("right-pane-bg");

        Pane infoPane = new Pane();
        infoPane.setLayoutY(10);
        infoPane.setPrefWidth(379);
        AnchorPane.setTopAnchor(infoPane, 10.0);
        AnchorPane.setBottomAnchor(infoPane, 10.0);
        AnchorPane.setRightAnchor(infoPane, 20.0);

        Label lblTitle = new Label("THÔNG TIN PHIẾU ĐỔI");
        lblTitle.getStyleClass().add("section-label");

        Pane detailsPane = new Pane();
        detailsPane.setLayoutY(41);
        detailsPane.setPrefSize(469, 362);
        detailsPane.getStyleClass().add("info-pane");

        Label lblTenNV = new Label("Tên nhân viên lập:");
        lblTenNV.getStyleClass().add("bold-label");
        lblTenNV.setLayoutX(5);
        lblTenNV.setLayoutY(5);

        Label lblTenNhanVienValue = new Label();
        ctrl.lblTenNhanVienValue = lblTenNhanVienValue;
        lblTenNhanVienValue.getStyleClass().add("value-label");
        lblTenNhanVienValue.setLayoutX(160);
        lblTenNhanVienValue.setLayoutY(5);

        Label lblTenKH = new Label("Tên khách hàng:");
        lblTenKH.getStyleClass().add("bold-label");
        lblTenKH.setLayoutX(5);
        lblTenKH.setLayoutY(46);

        Label lblTenKhachHangValue = new Label();
        ctrl.lblTenKhachHangValue = lblTenKhachHangValue;
        lblTenKhachHangValue.getStyleClass().add("value-label");
        lblTenKhachHangValue.setLayoutX(160);
        lblTenKhachHangValue.setLayoutY(35);

        Label lblSDT = new Label("SĐT Khách hàng:");
        lblSDT.getStyleClass().add("bold-label");
        lblSDT.setLayoutX(5);
        lblSDT.setLayoutY(87);

        Label lblSDTKhachHangValue = new Label();
        ctrl.lblSDTKhachHangValue = lblSDTKhachHangValue;
        lblSDTKhachHangValue.getStyleClass().add("value-label");
        lblSDTKhachHangValue.setLayoutX(160);
        lblSDTKhachHangValue.setLayoutY(65);

        Label lblGhiChu = new Label("Ghi chú:");
        lblGhiChu.getStyleClass().add("bold-label");
        lblGhiChu.setLayoutX(5);
        lblGhiChu.setLayoutY(127);

        Label lblGhiChuValue = new Label();
        ctrl.lblGhiChuValue = lblGhiChuValue;
        lblGhiChuValue.getStyleClass().add("value-label");
        lblGhiChuValue.setLayoutX(72);
        lblGhiChuValue.setLayoutY(95);
        lblGhiChuValue.setWrapText(true);
        lblGhiChuValue.setPrefSize(288, 9);

        detailsPane.getChildren().addAll(lblTenNV, lblTenNhanVienValue, lblTenKH, lblTenKhachHangValue, lblSDT, lblSDTKhachHangValue, lblGhiChu, lblGhiChuValue);

        // Buttons
        Button btnInPhieu = new Button("In Phiếu Đổi");
        ctrl.btnInPhieuDoi = btnInPhieu;
        btnInPhieu.getStyleClass().add("print-btn");
        btnInPhieu.setPrefSize(471, 51);
        btnInPhieu.setOnAction(e -> ctrl.xuLyInPhieu());

        Button btnDong = new Button("Đóng");
        ctrl.btnDong = btnDong;
        btnDong.getStyleClass().add("danger-btn");
        btnDong.setPrefSize(470, 51);
        btnDong.setOnAction(e -> ctrl.xuLyDong());

        VBox vboxButtons = new VBox(10, btnInPhieu, btnDong);
        vboxButtons.setLayoutY(565);
        vboxButtons.setPrefWidth(470);

        infoPane.getChildren().addAll(lblTitle, detailsPane, vboxButtons);
        rightPane.getChildren().add(infoPane);

        splitPane.getItems().addAll(leftPane, rightPane);
        root.getChildren().add(splitPane);
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
