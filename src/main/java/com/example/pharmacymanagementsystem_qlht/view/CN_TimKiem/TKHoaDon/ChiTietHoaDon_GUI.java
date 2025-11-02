package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKHoaDon;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon.ChiTietHoaDon_Ctrl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDoi.ChiTietPhieuDoiHang_GUI.RESOURCE_BASE_PATH;

public class ChiTietHoaDon_GUI extends Application {

    public static void showWithController(Stage stage, ChiTietHoaDon_Ctrl ctrl) {
        Pane root = buildPane(ctrl);
        Scene scene = new Scene(root, 1646, 895);
        scene.getStylesheets().add((new ChiTietHoaDon_GUI()).getClass().getResource(RESOURCE_BASE_PATH + "css/ChiTietHoaDon.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Chi tiết hóa đơn");
        stage.show();
    }

    private static Pane buildPane(ChiTietHoaDon_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14px;");

        SplitPane splitPane = new SplitPane();
        AnchorPane.setTopAnchor(splitPane, 0.0);
        AnchorPane.setBottomAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);

        // === Left Pane ===
        AnchorPane leftPane = new AnchorPane();
        Pane paneLeft = new Pane();

        Label lblTitle = new Label("CHI TIẾT HÓA ĐƠN");
        lblTitle.getStyleClass().add("title-label");
        lblTitle.setLayoutX(10);
        lblTitle.setLayoutY(6);

        Pane infoPane = new Pane();
        infoPane.setLayoutX(6);
        infoPane.setLayoutY(43);
        infoPane.setPrefSize(857, 29);

        Label lblMaHD = new Label("Mã hóa đơn: ");
        lblMaHD.getStyleClass().add("bold-label");
        lblMaHD.setFont(Font.font(13));

        ctrl.lblMaHoaDonValue = new Label("HD012345");
        ctrl.lblMaHoaDonValue.setLayoutX(93.6);
        ctrl.lblMaHoaDonValue.setLayoutY(0.8);
        ctrl.lblMaHoaDonValue.getStyleClass().add("value-label");

        Label lblNgayLap = new Label("Ngày lập: ");
        lblNgayLap.setLayoutX(282);
        lblNgayLap.setLayoutY(2);
        lblNgayLap.setFont(Font.font("System Bold", 14));
        lblNgayLap.getStyleClass().addAll("bold-label", "label-padding");

        ctrl.lblNgayLapValue = new Label("01/10/2025");
        ctrl.lblNgayLapValue.setLayoutX(353);
        ctrl.lblNgayLapValue.setLayoutY(2);
        ctrl.lblNgayLapValue.getStyleClass().add("value-label");

        ctrl.lblLoaiHoaDon = new Label("Hóa đơn cho thuốc kê đơn(OTC):");
        ctrl.lblLoaiHoaDon.setLayoutX(537);
        ctrl.lblLoaiHoaDon.setLayoutY(2);
        ctrl.lblLoaiHoaDon.getStyleClass().addAll("bold-label", "label-padding");

        ctrl.lblMaDonThuocValue = new Label("0");
        ctrl.lblMaDonThuocValue.setLayoutX(759);
        ctrl.lblMaDonThuocValue.setLayoutY(3);
        ctrl.lblMaDonThuocValue.getStyleClass().add("value-label");

        infoPane.getChildren().addAll(lblMaHD, ctrl.lblMaHoaDonValue, lblNgayLap, ctrl.lblNgayLapValue,
                ctrl.lblLoaiHoaDon, ctrl.lblMaDonThuocValue);

        Label lblSP = new Label("DANH SÁCH SẢN PHẨM");
        lblSP.setLayoutX(5);
        lblSP.setLayoutY(69);
        lblSP.getStyleClass().add("section-label");

        ctrl.tblChiTietHoaDon = new TableView<>();
        ctrl.tblChiTietHoaDon.setLayoutX(9);
        ctrl.tblChiTietHoaDon.setLayoutY(103);
        ctrl.tblChiTietHoaDon.setPrefSize(1130, 765);
        ctrl.tblChiTietHoaDon.setStyle("-fx-font-size: 14;");

        ctrl.colNSTT = new TableColumn<>("STT");
        ctrl.colNSTT.setPrefWidth(50);
        ctrl.colNSTT.setStyle("-fx-alignment: CENTER;");

        ctrl.colNTen = new TableColumn<>("Tên sản phẩm/Hoạt chất");
        ctrl.colNTen.setPrefWidth(345);
        ctrl.colNTen.setStyle("-fx-alignment: CENTER_LEFT;");

        ctrl.colNSL = new TableColumn<>("Số lượng");
        ctrl.colNSL.setPrefWidth(130);
        ctrl.colNSL.setStyle("-fx-alignment: CENTER;");

        ctrl.colNDonVi = new TableColumn<>("Đơn vị");
        ctrl.colNDonVi.setPrefWidth(103);
        ctrl.colNDonVi.setStyle("-fx-alignment: CENTER;");

        ctrl.colNDonGia = new TableColumn<>("Đơn giá");
        ctrl.colNDonGia.setPrefWidth(170);
        ctrl.colNDonGia.setStyle("-fx-alignment: CENTER;");

        ctrl.colNChietKhau = new TableColumn<>("Giảm giá");
        ctrl.colNChietKhau.setPrefWidth(148);
        ctrl.colNChietKhau.setStyle("-fx-alignment: CENTER;");

        ctrl.colNThanhTien = new TableColumn<>("Thành tiền");
        ctrl.colNThanhTien.setPrefWidth(149);
        ctrl.colNThanhTien.setStyle("-fx-alignment: CENTER;");

        ctrl.tblChiTietHoaDon.getColumns().addAll(ctrl.colNSTT, ctrl.colNTen, ctrl.colNSL,
                ctrl.colNDonVi, ctrl.colNDonGia, ctrl.colNChietKhau, ctrl.colNThanhTien);

        paneLeft.getChildren().addAll(lblTitle, infoPane, lblSP, ctrl.tblChiTietHoaDon);
        leftPane.getChildren().add(paneLeft);

        // === Right Pane ===
        AnchorPane rightPane = new AnchorPane();
        rightPane.getStyleClass().add("right-pane-bg");

        Pane rightMain = new Pane();
        AnchorPane.setTopAnchor(rightMain, 10.0);
        AnchorPane.setLeftAnchor(rightMain, 22.0);
        AnchorPane.setRightAnchor(rightMain, 10.0);
        AnchorPane.setBottomAnchor(rightMain, 10.0);
        rightMain.setPrefSize(492, 873);

        Label lblThongTinGD = new Label("THÔNG TIN GIAO DỊCH");
        lblThongTinGD.setFont(Font.font("Arial Bold", 24));

        // Pane thông tin khách hàng / nhân viên
        Pane infoKhach = new Pane();
        infoKhach.setLayoutY(41);
        infoKhach.setPrefSize(453, 213);

        Label lblTenNV = new Label("Tên nhân viên:");
        lblTenNV.setLayoutX(5);
        lblTenNV.setLayoutY(5);
        lblTenNV.getStyleClass().add("bold-label");

        ctrl.lblTenNhanVienValue = new Label();
        ctrl.lblTenNhanVienValue.setLayoutX(140);
        ctrl.lblTenNhanVienValue.setLayoutY(5);
        ctrl.lblTenNhanVienValue.getStyleClass().add("value-label");

        Label lblTenKH = new Label("Tên khách hàng:");
        lblTenKH.setLayoutX(5);
        lblTenKH.setLayoutY(35);
        lblTenKH.getStyleClass().add("bold-label");

        ctrl.lblTenKhachHangValue = new Label();
        ctrl.lblTenKhachHangValue.setLayoutX(140);
        ctrl.lblTenKhachHangValue.setLayoutY(35);
        ctrl.lblTenKhachHangValue.getStyleClass().add("value-label");

        Label lblSDT = new Label("SĐT Khách hàng:");
        lblSDT.setLayoutX(5);
        lblSDT.setLayoutY(65);
        lblSDT.getStyleClass().add("bold-label");

        ctrl.lblSDTKhachHangValue = new Label();
        ctrl.lblSDTKhachHangValue.setLayoutX(140);
        ctrl.lblSDTKhachHangValue.setLayoutY(65);
        ctrl.lblSDTKhachHangValue.getStyleClass().add("value-label");

        Label lblGhiChu = new Label("Ghi chú:");
        lblGhiChu.setLayoutX(5);
        lblGhiChu.setLayoutY(95);
        lblGhiChu.getStyleClass().add("bold-label");

        ctrl.lblGhiChuValue = new Label("Không có.");
        ctrl.lblGhiChuValue.setLayoutX(140);
        ctrl.lblGhiChuValue.setLayoutY(96);
        ctrl.lblGhiChuValue.setPrefWidth(250);
        ctrl.lblGhiChuValue.getStyleClass().add("value-label");
        ctrl.lblGhiChuValue.setWrapText(true);

        infoKhach.getChildren().addAll(lblTenNV, ctrl.lblTenNhanVienValue, lblTenKH, ctrl.lblTenKhachHangValue,
                lblSDT, ctrl.lblSDTKhachHangValue, lblGhiChu, ctrl.lblGhiChuValue);

        // Pane tóm tắt thanh toán
        Label lblTT = new Label("TÓM TẮT THANH TOÁN");
        lblTT.setLayoutY(297);
        lblTT.setFont(Font.font(24));
        lblTT.setStyle("-fx-font-weight: bold;");

        Pane summaryPane = new Pane();
        summaryPane.setLayoutY(297);
        summaryPane.setPrefSize(452, 361);

        Label lblGiamSP = new Label("Giảm theo sản phẩm:");
        lblGiamSP.setLayoutX(5);
        lblGiamSP.setLayoutY(76);
        lblGiamSP.getStyleClass().add("label-normal");

        ctrl.lblGiamTheoSP = new Label("0 VNĐ");
        ctrl.lblGiamTheoSP.setLayoutX(240);
        ctrl.lblGiamTheoSP.setLayoutY(78);
        ctrl.lblGiamTheoSP.getStyleClass().addAll("bold-label", "value-label");

        Label lblTongHang = new Label("Tổng tiền hàng (trước VAT):");
        lblTongHang.setLayoutX(5);
        lblTongHang.setLayoutY(130);
        lblTongHang.getStyleClass().add("label-normal");

        ctrl.lblTongTienHang = new Label("0 VNĐ");
        ctrl.lblTongTienHang.setLayoutX(240);
        ctrl.lblTongTienHang.setLayoutY(130);
        ctrl.lblTongTienHang.getStyleClass().add("bold-label");

        Label lblVAT = new Label("Thuế (VAT):");
        lblVAT.setLayoutX(5);
        lblVAT.setLayoutY(184);
        lblVAT.getStyleClass().add("label-normal");

        ctrl.lblVAT = new Label("0 VNĐ");
        ctrl.lblVAT.setLayoutX(240);
        ctrl.lblVAT.setLayoutY(184);
        ctrl.lblVAT.getStyleClass().add("bold-label");

        Label lblGiamHD = new Label("Giảm theo hóa đơn:");
        lblGiamHD.setLayoutX(6);
        lblGiamHD.setLayoutY(230);
        lblGiamHD.getStyleClass().add("label-normal");

        ctrl.lblGiamTheoHD = new Label("0 VNĐ");
        ctrl.lblGiamTheoHD.setLayoutX(241);
        ctrl.lblGiamTheoHD.setLayoutY(230);
        ctrl.lblGiamTheoHD.getStyleClass().add("bold-label");

        Label lblTongTT = new Label("TỔNG THANH TOÁN:");
        lblTongTT.setLayoutX(5);
        lblTongTT.setLayoutY(278);
        lblTongTT.getStyleClass().addAll("bold-label", "section-label");

        ctrl.lblTongThanhToan = new Label("0 VNĐ");
        ctrl.lblTongThanhToan.setLayoutX(240);
        ctrl.lblTongThanhToan.setLayoutY(278);
        ctrl.lblTongThanhToan.getStyleClass().addAll("main-value-label", "bold-label");

        summaryPane.getChildren().addAll(lblGiamSP, ctrl.lblGiamTheoSP, lblTongHang, ctrl.lblTongTienHang,
                lblVAT, ctrl.lblVAT, lblGiamHD, ctrl.lblGiamTheoHD, lblTongTT, ctrl.lblTongThanhToan);

        // Các nút thao tác
        Pane actionPane = new Pane();
        actionPane.setLayoutY(619);
        actionPane.setPrefSize(452, 220);

        ctrl.btnInHoaDon = new Button("In Hóa Đơn");
        ctrl.btnInHoaDon.setLayoutX(9);
        ctrl.btnInHoaDon.setLayoutY(40);
        ctrl.btnInHoaDon.setPrefSize(435, 70);
        ctrl.btnInHoaDon.getStyleClass().add("print-btn");
        ctrl.btnInHoaDon.setFont(Font.font("System Bold", 14));
        ctrl.btnInHoaDon.setEffect(new DropShadow(5, Color.GRAY));

        ctrl.btnDong = new Button("Hoàn Tất");
        ctrl.btnDong.setLayoutX(11);
        ctrl.btnDong.setLayoutY(136);
        ctrl.btnDong.setPrefSize(429, 70);
        ctrl.btnDong.getStyleClass().add("danger-btn");
        ctrl.btnDong.setFont(Font.font("System Bold", 14));
        ctrl.btnDong.setEffect(new DropShadow(5, Color.GRAY));

        actionPane.getChildren().addAll(ctrl.btnInHoaDon, ctrl.btnDong);

        rightMain.getChildren().addAll(lblThongTinGD, infoKhach, lblTT, summaryPane, actionPane);
        rightPane.getChildren().add(rightMain);

        splitPane.getItems().addAll(leftPane, rightPane);
        root.getChildren().add(splitPane);

        return root;
    }

    @Override
    public void start(Stage stage) {
        ChiTietHoaDon_Ctrl ctrl = new ChiTietHoaDon_Ctrl();
        showWithController(stage, ctrl);
        ctrl.initialize();
    }

    public static void main(String[] args) {
        launch(args);
    }
}