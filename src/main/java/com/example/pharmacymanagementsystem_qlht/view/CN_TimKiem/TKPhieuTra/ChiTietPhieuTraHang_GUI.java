package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuTra;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuTraHang.ChiTietPhieuTraHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuTraHang;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox; // Import VBox dù không dùng trực tiếp, vì cấu trúc bên phải gần như là VBox
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class ChiTietPhieuTraHang_GUI extends Application {

    private static final String RESOURCE_BASE_PATH = "/com/example/pharmacymanagementsystem_qlht/";

    public static void showWithController(Stage stage,ChiTietPhieuTraHang_Ctrl ctrl) {
        AnchorPane root = buildPane(ctrl);
        Scene scene = new Scene(root);
        try {
            scene.getStylesheets().add(Objects.requireNonNull(ChiTietPhieuTraHang_GUI.class.getResource(RESOURCE_BASE_PATH + "css/ChiTietPhieuTra.css")).toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("Lỗi tải CSS: Kiểm tra đường dẫn " + RESOURCE_BASE_PATH + "css/ChiTietPhieuTra.css");
        }        stage.setTitle("Chi Tiết Phiếu Trả Hàng");
        stage.setScene(scene);
        stage.show();
        ctrl.initialize();
    }

    @Override
    public void start(Stage stage) throws Exception {
        showWithController(stage, new ChiTietPhieuTraHang_Ctrl());
    }
    private static AnchorPane buildPane(ChiTietPhieuTraHang_Ctrl ctrl) {

        // === 1. Root AnchorPane ===
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14 px;");

        // === 2. SplitPane ===
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.7037712895377128);
        AnchorPane.setBottomAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);
        AnchorPane.setTopAnchor(splitPane, 0.0);

        // === 3. Left AnchorPane (Danh sách sản phẩm) ===
        AnchorPane leftAnchor = new AnchorPane();
        leftAnchor.setMinHeight(0.0);
        leftAnchor.setMinWidth(0.0);

        Pane leftPane = new Pane();
        AnchorPane.setBottomAnchor(leftPane, 0.0);
        AnchorPane.setLeftAnchor(leftPane, 0.0);
        AnchorPane.setRightAnchor(leftPane, 0.0);
        AnchorPane.setTopAnchor(leftPane, 0.0);

        // - Tiêu đề danh sách
        Label lbDSPTra = new Label("DANH SÁCH SẢN PHẨM TRẢ");
        lbDSPTra.setLayoutX(14.0);
        lbDSPTra.setLayoutY(52.0);
        lbDSPTra.getStyleClass().add("section-label");

        // - Thông tin Mã và Ngày lập (Top left)
        Label lbMaPT = new Label("Mã phiếu trả: ");
        lbMaPT.setLayoutX(10.0);
        lbMaPT.setLayoutY(13.0);
        lbMaPT.getStyleClass().add("bold-label");

        Label lblMaPhieuTraValue = new Label("PT012345");
        lblMaPhieuTraValue.setId("lblMaPhieuTraValue");
        lblMaPhieuTraValue.setLayoutX(117.0);
        lblMaPhieuTraValue.setLayoutY(14.0);
        lblMaPhieuTraValue.getStyleClass().add("value-label");

        Label lbNgayLap = new Label("Ngày lập: ");
        lbNgayLap.setLayoutX(203.0);
        lbNgayLap.setLayoutY(13.0);
        lbNgayLap.getStyleClass().add("bold-label");
        lbNgayLap.setFont(Font.font("System Bold", 14.0));

        Label lblNgayLapValue = new Label("01/10/2025");
        lblNgayLapValue.setId("lblNgayLapValue");
        lblNgayLapValue.setLayoutX(282.0);
        lblNgayLapValue.setLayoutY(14.0);
        lblNgayLapValue.setPrefHeight(20.0);
        lblNgayLapValue.setPrefWidth(315.0);
        lblNgayLapValue.getStyleClass().add("value-label");

        // - TableView Chi Tiết
        TableView<ChiTietPhieuTraHang> tblChiTietPhieuTra = new TableView<>();
        tblChiTietPhieuTra.setId("tblChiTietPhieuTra");
        tblChiTietPhieuTra.setLayoutX(15.0);
        tblChiTietPhieuTra.setLayoutY(80.0);
        tblChiTietPhieuTra.setPrefHeight(792.0);
        tblChiTietPhieuTra.setPrefWidth(1134.0);
        tblChiTietPhieuTra.getStyleClass().add("main-table");
        tblChiTietPhieuTra.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);

        // Cột STT
        TableColumn<ChiTietPhieuTraHang, String> colSTT = new TableColumn<>("STT");
        colSTT.setId("colSTT");
        colSTT.setPrefWidth(61.333335876464844);
        colSTT.setStyle("-fx-alignment: CENTER;");

        // Cột Tên SP
        TableColumn<ChiTietPhieuTraHang, String> colTenSP = new TableColumn<>("Tên sản phẩm/Hoạt chất");
        colTenSP.setId("colTenSP");
        colTenSP.setPrefWidth(439.6666259765625);
        colTenSP.setStyle("-fx-alignment: CENTER;");

        // Cột Số lượng
        TableColumn<ChiTietPhieuTraHang, String> colSoLuong = new TableColumn<>("Số lượng trả");
        colSoLuong.setId("colSoLuong");
        colSoLuong.setPrefWidth(138.0);
        colSoLuong.setStyle("-fx-alignment: CENTER;");

        // Cột Đơn giá
        TableColumn<ChiTietPhieuTraHang, String> colDonGia = new TableColumn<>("Đơn giá trả");
        colDonGia.setId("colDonGia");
        colDonGia.setPrefWidth(133.33331298828125);
        colDonGia.setStyle("-fx-alignment: CENTER;");

        // Cột Lý do
        TableColumn<ChiTietPhieuTraHang, String> colLyDo = new TableColumn<>("Lý do trả");
        colLyDo.setId("colLyDo");
        colLyDo.setPrefWidth(242.0);
        colLyDo.setStyle("-fx-alignment: CENTER;");

        // Cột Thành tiền
        TableColumn<ChiTietPhieuTraHang, Double> colThanhTien = new TableColumn<>("Thành tiền trả");
        colThanhTien.setId("colThanhTien");
        colThanhTien.setPrefWidth(116.0);
        colThanhTien.setStyle("-fx-alignment: CENTER;");

        tblChiTietPhieuTra.getColumns().addAll(colSTT, colTenSP, colSoLuong, colDonGia, colLyDo, colThanhTien);

        leftPane.getChildren().addAll(lbDSPTra, tblChiTietPhieuTra, lbMaPT, lblMaPhieuTraValue, lbNgayLap, lblNgayLapValue);
        leftAnchor.getChildren().add(leftPane);

        // === 4. Right AnchorPane (Thông tin chi tiết) ===
        AnchorPane rightAnchor = new AnchorPane();
        rightAnchor.setMinHeight(0.0);
        rightAnchor.setMinWidth(0.0);
        rightAnchor.getStyleClass().add("right-pane-bg");

        Pane rightPane = new Pane();
        AnchorPane.setBottomAnchor(rightPane, 10.0);
        AnchorPane.setLeftAnchor(rightPane, 10.0);
        AnchorPane.setRightAnchor(rightPane, 10.0);
        AnchorPane.setTopAnchor(rightPane, 10.0);

        // - Tiêu đề 1
        Label lbTitle1 = new Label("THÔNG TIN PHIẾU TRẢ");
        lbTitle1.setFont(Font.font("System Bold", 24.0));
        lbTitle1.getStyleClass().add("section-label"); // FXML có section-label ở đây

        // - Pane Thông tin chi tiết (NV, KH, SĐT, Ghi chú)
        Pane infoPane = new Pane();
        infoPane.setLayoutY(41.0);
        infoPane.setPrefHeight(160.0);
        infoPane.setPrefWidth(462.0); // FXML has two overlapping panes, using the smaller one with details
        infoPane.getStyleClass().add("info-pane");

        // --- Nội dung Info Pane ---
        Label lbTenNV = new Label("Tên nhân viên lập:");
        lbTenNV.setLayoutX(5.0); lbTenNV.setLayoutY(5.0); lbTenNV.getStyleClass().add("bold-label");

        Label lblTenNV = new Label();
        lblTenNV.setId("lblTenNV");
        lblTenNV.setLayoutX(164.0); lblTenNV.setLayoutY(7.0);
        lblTenNV.setPrefHeight(20.0); lblTenNV.setPrefWidth(207.0);
        lblTenNV.setTextFill(Color.web("#3581bc"));
        lblTenNV.setFont(Font.font("System Bold", 14.0));

        Label lbTenKH = new Label("Tên khách hàng:");
        lbTenKH.setLayoutX(5.0); lbTenKH.setLayoutY(35.0); lbTenKH.getStyleClass().add("bold-label");

        Label lblTenKH = new Label();
        lblTenKH.setId("lblTenKH");
        lblTenKH.setLayoutX(151.0); lblTenKH.setLayoutY(37.0);
        lblTenKH.setPrefHeight(17.0); lblTenKH.setPrefWidth(232.0);

        Label lbSDTKH = new Label("SĐT Khách hàng:");
        lbSDTKH.setLayoutX(5.0); lbSDTKH.setLayoutY(65.0); lbSDTKH.getStyleClass().add("bold-label");

        Label lblSDTKH = new Label();
        lblSDTKH.setId("lblSDTKH");
        lblSDTKH.setLayoutX(150.0); lblSDTKH.setLayoutY(67.0);
        lblSDTKH.setPrefHeight(17.0); lblSDTKH.setPrefWidth(238.0);

        Label lbGhiChu = new Label("Ghi chú:");
        lbGhiChu.setLayoutX(5.0); lbGhiChu.setLayoutY(95.0); lbGhiChu.getStyleClass().add("bold-label");

        Label lblGhiChuValue = new Label("Không có.");
        lblGhiChuValue.setId("lblGhiChuValue");
        lblGhiChuValue.setLayoutX(93.0); lblGhiChuValue.setLayoutY(95.0);
        lblGhiChuValue.setPrefWidth(200.0); lblGhiChuValue.getStyleClass().add("value-label");
        lblGhiChuValue.setWrapText(true);

        // NOTE: FXML có 2 sets of labels cho NV/KH/SDT. Tôi sẽ ưu tiên set có ID được Controller dùng (lblTenNV, lblTenKH, lblSDTKH) và set cố định FXML (lblTenNhanVienValue/lblTenKhachHangValue/lblSDTKhachHangValue) cho việc gán ID.
        // Tuy nhiên, FXML đang gán text cho lblTenNV/lblTenKH/lblSDTKH là trống, và FXML có 3 labels khác không có ID nhưng có text: "Tên nhân viên lập:", "Tên khách hàng:", "SĐT Khách hàng:", và 3 labels có ID nhưng không có text ban đầu: lblTenNhanVienValue/lblTenKhachHangValue/lblSDTKhachHangValue.
        // Để đảm bảo mapping đúng FXML, tôi sẽ tạo tất cả IDs.

        // FXML Labels (redundant but need to be mapped)
        Label lblTenNhanVienValue = new Label();
        lblTenNhanVienValue.setId("lblTenNhanVienValue");
        lblTenNhanVienValue.setLayoutX(160.0); lblTenNhanVienValue.setLayoutY(5.0);
        lblTenNhanVienValue.getStyleClass().add("value-label");

        Label lblTenKhachHangValue = new Label();
        lblTenKhachHangValue.setId("lblTenKhachHangValue");
        lblTenKhachHangValue.setLayoutX(160.0); lblTenKhachHangValue.setLayoutY(35.0);
        lblTenKhachHangValue.getStyleClass().add("value-label");

        Label lblSDTKhachHangValue = new Label();
        lblSDTKhachHangValue.setId("lblSDTKhachHangValue");
        lblSDTKhachHangValue.setLayoutX(160.0); lblSDTKhachHangValue.setLayoutY(65.0);
        lblSDTKhachHangValue.getStyleClass().add("value-label");

        infoPane.getChildren().addAll(lbTenNV, lblTenNhanVienValue, lbTenKH, lblTenKhachHangValue, lbSDTKH, lblSDTKhachHangValue, lbGhiChu, lblGhiChuValue, lblTenNV, lblTenKH, lblSDTKH);

        // - Separator 1
        Separator separator1 = new Separator();
        separator1.setLayoutY(216.0);

        // - Tiêu đề 2
        Label lbTitle2 = new Label("TÓM TẮT TRẢ HÀNG");
        lbTitle2.setLayoutY(234.4);
        lbTitle2.setFont(Font.font("System Bold", 24.0));

        // - Pane Tóm tắt trả hàng
        Pane summaryPane = new Pane();
        summaryPane.setLayoutY(275.0);
        summaryPane.setPrefHeight(280.0);
        summaryPane.setPrefWidth(545.0);
        summaryPane.getStyleClass().add("summary-pane");

        // --- Nội dung Summary Pane ---

        // Tổng tiền trả (trước CK/VAT)
        Label lbTongTienTra = new Label("Tổng tiền trả (trước CK/VAT):");
        lbTongTienTra.setLayoutX(5.0); lbTongTienTra.setLayoutY(5.0); lbTongTienTra.getStyleClass().add("label-normal");
        lbTongTienTra.setFont(Font.font("System Bold", 12.0));

        Label lblTongTienTraValue = new Label("0 VNĐ");
        lblTongTienTraValue.setId("lblTongTienTraValue");
        lblTongTienTraValue.setLayoutX(240.0); lblTongTienTraValue.setLayoutY(5.0);
        lblTongTienTraValue.getStyleClass().addAll("bold-label", "value-label");

        // Chiết khấu Phiếu trả
        Label lbChietKhauPT = new Label("Chiết khấu Phiếu trả:");
        lbChietKhauPT.setLayoutX(5.0); lbChietKhauPT.setLayoutY(30.0); lbChietKhauPT.getStyleClass().add("label-normal");
        lbChietKhauPT.setFont(Font.font("System Bold", 12.0));

        Label lblChietKhauPTValue = new Label("0 VNĐ");
        lblChietKhauPTValue.setId("lblChietKhauPTValue");
        lblChietKhauPTValue.setLayoutX(240.0); lblChietKhauPTValue.setLayoutY(30.0);
        lblChietKhauPTValue.getStyleClass().addAll("label-discount", "bold-label", "value-label");

        // Thuế (VAT)
        Label lbThueVAT = new Label("Thuế (VAT):");
        lbThueVAT.setLayoutX(5.0); lbThueVAT.setLayoutY(55.0); lbThueVAT.getStyleClass().add("label-normal");
        lbThueVAT.setFont(Font.font("System Bold", 12.0));

        Label lblThueVATValue = new Label("0 VNĐ");
        lblThueVATValue.setId("lblThueVATValue");
        lblThueVATValue.setLayoutX(240.0); lblThueVATValue.setLayoutY(55.0);
        lblThueVATValue.getStyleClass().addAll("bold-label", "value-label");

        // Separator 2
        Separator separator2 = new Separator();
        separator2.setLayoutY(85.0);
        separator2.setPrefHeight(2.0);
        separator2.setPrefWidth(544.0);

        // TỔNG TIỀN TRẢ
        Label lbTongTienPhaiTra = new Label("TỔNG TIỀN TRẢ:");
        lbTongTienPhaiTra.setLayoutX(5.0); lbTongTienPhaiTra.setLayoutY(100.0);
        lbTongTienPhaiTra.getStyleClass().addAll("bold-label", "section-label");
        lbTongTienPhaiTra.setFont(Font.font("System Bold", 12.0));

        Label lblTongTienPhaiTraValue = new Label("0 VNĐ");
        lblTongTienPhaiTraValue.setId("lblTongTienPhaiTraValue");
        lblTongTienPhaiTraValue.setLayoutX(240.0); lblTongTienPhaiTraValue.setLayoutY(100.0);
        lblTongTienPhaiTraValue.getStyleClass().addAll("main-value-label", "grand-total-value", "bold-label", "value-label");

        // Separator 3
        Separator separator3 = new Separator();
        separator3.setLayoutY(130.0);
        separator3.setPrefHeight(2.0);
        separator3.setPrefWidth(547.0);

        // Phương thức hoàn tiền
        Label lbPTHT = new Label("Phương thức hoàn tiền:");
        lbPTHT.setLayoutX(5.0); lbPTHT.setLayoutY(150.0); lbPTHT.getStyleClass().add("label-normal");
        lbPTHT.setFont(Font.font("System Bold", 12.0));

        Label lblPTHTValue = new Label("Tiền mặt");
        lblPTHTValue.setId("lblPTHTValue");
        lblPTHTValue.setLayoutX(240.0); lblPTHTValue.setLayoutY(150.0);
        lblPTHTValue.getStyleClass().addAll("bold-label", "value-label");

        // Số tiền khách nhận
        Label lbTienKhachNhan = new Label("Số tiền khách nhận:");
        lbTienKhachNhan.setLayoutX(5.0); lbTienKhachNhan.setLayoutY(175.0); lbTienKhachNhan.getStyleClass().add("label-normal");
        lbTienKhachNhan.setFont(Font.font("System Bold", 12.0));

        Label lblTienKhachNhanValue = new Label("0 VNĐ");
        lblTienKhachNhanValue.setId("lblTienKhachNhanValue");
        lblTienKhachNhanValue.setLayoutX(240.0); lblTienKhachNhanValue.setLayoutY(175.0);
        lblTienKhachNhanValue.getStyleClass().addAll("bold-label", "value-label");

        // Tiền thừa trả lại
        Label lbTienThua = new Label("Tiền thừa trả lại:");
        lbTienThua.setLayoutX(5.0); lbTienThua.setLayoutY(210.0); lbTienThua.getStyleClass().add("bold-label");

        Label lblTienThuaValue = new Label("0 VNĐ");
        lblTienThuaValue.setId("lblTienThuaValue");
        lblTienThuaValue.setLayoutX(240.0); lblTienThuaValue.setLayoutY(210.0);
        lblTienThuaValue.getStyleClass().addAll("change-due-value", "bold-label", "value-label");

        summaryPane.getChildren().addAll(lbTongTienTra, lblTongTienTraValue, lbChietKhauPT, lblChietKhauPTValue,
                lbThueVAT, lblThueVATValue, separator2, lbTongTienPhaiTra,
                lblTongTienPhaiTraValue, separator3, lbPTHT, lblPTHTValue,
                lbTienKhachNhan, lblTienKhachNhanValue, lbTienThua, lblTienThuaValue);

        // - Pane Buttons
        Pane buttonPane = new Pane();
        buttonPane.setLayoutX(-11.0);
        buttonPane.setLayoutY(561.4000244140625);

        DropShadow dropShadow = new DropShadow(10.0, Color.web("#a7a4a4")); // Màu đổ bóng theo FXML

        // Button In Phiếu Trả
        Button btnInPhieuTra = new Button("In Phiếu Trả");
        btnInPhieuTra.setId("btnInPhieuTra");
        btnInPhieuTra.setLayoutX(10.0);
        btnInPhieuTra.setPrefHeight(58.0);
        btnInPhieuTra.setPrefWidth(472.0);
        btnInPhieuTra.getStyleClass().add("print-btn");
        btnInPhieuTra.setFont(Font.font("System Bold", 14.0));
        btnInPhieuTra.setEffect(dropShadow);

        // Button Đóng
        Button btnDong = new Button("Đóng");
        btnDong.setId("btnDong");
        btnDong.setLayoutX(9.0);
        btnDong.setLayoutY(68.0);
        btnDong.setPrefHeight(58.0);
        btnDong.setPrefWidth(473.0);
        btnDong.getStyleClass().add("danger-btn");
        btnDong.setFont(Font.font("System Bold", 14.0));
        btnDong.setEffect(dropShadow);

        buttonPane.getChildren().addAll(btnInPhieuTra, btnDong);

        rightPane.getChildren().addAll(lbTitle1, infoPane, separator1, lbTitle2, summaryPane, buttonPane);
        rightAnchor.getChildren().add(rightPane);

        // === 5. Kết hợp và Tiêm Controls vào Controller ===
        splitPane.getItems().addAll(leftAnchor, rightAnchor);
        root.getChildren().add(splitPane);

        // Gán Controls vào Controller
        if (ctrl != null) {
            ctrl.tblChiTietPhieuTra = tblChiTietPhieuTra;
            ctrl.colSTT = colSTT;
            ctrl.colTenSP = colTenSP;
            ctrl.colSoLuong = colSoLuong;
            ctrl.colDonGia = colDonGia;
            ctrl.colLyDo = colLyDo;
            ctrl.colThanhTien = colThanhTien;

            ctrl.lblMaPhieuTraValue = lblMaPhieuTraValue;
            ctrl.lblNgayLapValue = lblNgayLapValue;

            ctrl.lblTenNhanVienValue = lblTenNhanVienValue;
            ctrl.lblTenNV = lblTenNV;

            ctrl.lblTenKhachHangValue = lblTenKhachHangValue;
            ctrl.lblTenKH = lblTenKH;

            ctrl.lblSDTKhachHangValue = lblSDTKhachHangValue;
            ctrl.lblSDTKH = lblSDTKH;

            ctrl.lblGhiChuValue = lblGhiChuValue;

            ctrl.lblTongTienTraValue = lblTongTienTraValue;
            ctrl.lblChietKhauPTValue = lblChietKhauPTValue;
            ctrl.lblThueVATValue = lblThueVATValue;
            ctrl.lblTongTienPhaiTraValue = lblTongTienPhaiTraValue;

            ctrl.lblPTHTValue = lblPTHTValue;
            ctrl.lblTienKhachNhanValue = lblTienKhachNhanValue;
            ctrl.lblTienThuaValue = lblTienThuaValue;

            ctrl.btnInPhieuTra = btnInPhieuTra;
            ctrl.btnDong = btnDong;
        }

        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}