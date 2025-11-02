package com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuTra;

import com.example.pharmacymanagementsystem_qlht.dao.ChiTietHoaDon_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.HoaDon_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.KhachHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon;
import com.example.pharmacymanagementsystem_qlht.service.TraHangItem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.time.LocalDate;

public class LapPhieuTra_GUI extends Application {

    // === Khai báo các control ===
    private AnchorPane root;
    public TextField txtTimHoaDon, lblMaHDGoc;
    public Button btnTimHoaDon, btnTraHang, btnInPhieuTra, btnHuy;
    public TableView<ChiTietHoaDon> tblSanPhamHoaDon;
    public TableColumn<ChiTietHoaDon, String> colSTTGoc, colTenSPGoc, colSLGoc, colDonViGoc, colDonGiaGoc, colThanhTienGoc;
    public TableColumn<ChiTietHoaDon, Void> colTra;

    public TableView<TraHangItem> tblChiTietTraHang;
    public TableColumn<TraHangItem, String> colSTTTra, colTenSPTra, colDonViTra, colLyDo;
    public TableColumn<TraHangItem, Number> colSLTra;
    public TableColumn<TraHangItem, Double> colDonGiaTra, colThanhTienTra;
    public TableColumn<TraHangItem, Void> colBo;

    public Label lblTenKH, lblSDT, lblTongTienGoc, lblTongTienTraLai, lblVAT, lblSoTienTraLai;
    public TextArea txtGhiChu;
    public DatePicker dpNgayLapPhieu;


    @Override
    public void start(Stage stage) {
        root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/LapPhieuTraHang.css").toExternalForm());
        taoGiaoDien();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Lập Phiếu Trả Hàng");
        stage.show();
    }

    private void taoGiaoDien() {
        // ===== HEADER =====
        Pane headerPane = new Pane();
        headerPane.getStyleClass().add("header-pane");
        headerPane.setPrefSize(1587, 54);
        AnchorPane.setTopAnchor(headerPane, 8.0);
        AnchorPane.setLeftAnchor(headerPane, 28.0);
        AnchorPane.setRightAnchor(headerPane, 31.0);

        ImageView img = new ImageView(new Image(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/return_11153370.png").toExternalForm()));
        img.setFitHeight(53);
        img.setFitWidth(67);
        img.setLayoutX(297);
        img.setLayoutY(1);
        img.setPreserveRatio(true);
        headerPane.getChildren().add(img);

        // ===== TIÊU ĐỀ =====
        Label lblTitle = new Label("LẬP PHIẾU TRẢ HÀNG");
        lblTitle.getStyleClass().add("title-label");
        AnchorPane.setTopAnchor(lblTitle, 18.0);
        AnchorPane.setLeftAnchor(lblTitle, 40.0);

        // ===== KHUNG TÌM HÓA ĐƠN =====
        Pane searchPane = new Pane();
        searchPane.getStyleClass().add("search-pane");
        searchPane.setPrefSize(766, 70);
        AnchorPane.setTopAnchor(searchPane, 71.0);
        AnchorPane.setLeftAnchor(searchPane, 30.0);
        AnchorPane.setRightAnchor(searchPane, 850.0);

        Label lblTim = new Label("Tìm hóa đơn gốc:");
        lblTim.setFont(Font.font("System", FontWeight.BOLD, 13));
        lblTim.setLayoutX(20);
        lblTim.setLayoutY(20);

        txtTimHoaDon = new TextField();
        txtTimHoaDon.setLayoutX(130);
        txtTimHoaDon.setLayoutY(15);
        txtTimHoaDon.setPrefSize(563, 27);
        txtTimHoaDon.setPromptText("Nhập mã hóa đơn hoặc tên khách hàng ...");

        btnTimHoaDon = new Button("Tìm");
        btnTimHoaDon.setLayoutX(702);
        btnTimHoaDon.setLayoutY(16);
        btnTimHoaDon.setOnAction(e -> xuLyTimHDGoc());

        searchPane.getChildren().addAll(lblTim, txtTimHoaDon, btnTimHoaDon);

        // ===== HƯỚNG DẪN =====
        Label lblHint = new Label("Chọn hóa đơn gốc để bắt đầu trả hàng");
        lblHint.getStyleClass().add("hint-label");
        AnchorPane.setTopAnchor(lblHint, 150.0);
        AnchorPane.setLeftAnchor(lblHint, 40.0);

        // ===== BẢNG SẢN PHẨM GỐC =====
        Label lblSPGoc = new Label("Sản phẩm trong hóa đơn gốc");
        lblSPGoc.getStyleClass().add("section-label");
        AnchorPane.setTopAnchor(lblSPGoc, 180.0);
        AnchorPane.setLeftAnchor(lblSPGoc, 40.0);

        tblSanPhamHoaDon = new TableView<>();
        tblSanPhamHoaDon.getStyleClass().add("main-table");
        tblSanPhamHoaDon.setPrefSize(1156, 304);
        AnchorPane.setTopAnchor(tblSanPhamHoaDon, 210.0);
        AnchorPane.setLeftAnchor(tblSanPhamHoaDon, 40.0);
        AnchorPane.setRightAnchor(tblSanPhamHoaDon, 450.0);

        colSTTGoc = new TableColumn<>("STT");
        colTenSPGoc = new TableColumn<>("Tên sản phẩm");
        colSLGoc = new TableColumn<>("Số lượng");
        colDonViGoc = new TableColumn<>("Đơn vị");
        colDonGiaGoc = new TableColumn<>("Đơn giá");
        colThanhTienGoc = new TableColumn<>("Thành tiền");
        colTra = new TableColumn<>("Trả");
        tblSanPhamHoaDon.getColumns().addAll(colSTTGoc, colTenSPGoc, colSLGoc, colDonViGoc, colDonGiaGoc, colThanhTienGoc, colTra);

        // ===== BẢNG TRẢ HÀNG =====
        Label lblTra = new Label("Chi tiết trả hàng");
        lblTra.getStyleClass().add("section-label");
        AnchorPane.setTopAnchor(lblTra, 528.0);
        AnchorPane.setLeftAnchor(lblTra, 40.0);

        tblChiTietTraHang = new TableView<>();
        tblChiTietTraHang.getStyleClass().add("main-table");
        tblChiTietTraHang.setPrefSize(1160, 295);
        AnchorPane.setTopAnchor(tblChiTietTraHang, 559.0);
        AnchorPane.setLeftAnchor(tblChiTietTraHang, 40.0);
        AnchorPane.setRightAnchor(tblChiTietTraHang, 446.0);
        AnchorPane.setBottomAnchor(tblChiTietTraHang, 41.0);

        colSTTTra = new TableColumn<>("STT");
        colTenSPTra = new TableColumn<>("Tên sản phẩm");
        colSLTra = new TableColumn<>("Số lượng");
        colDonViTra = new TableColumn<>("Đơn vị");
        colDonGiaTra = new TableColumn<>("Đơn giá");
        colThanhTienTra = new TableColumn<>("Thành tiền");
        colLyDo = new TableColumn<>("Lý do trả");
        colBo = new TableColumn<>("Bỏ");
        tblChiTietTraHang.getColumns().addAll(colSTTTra, colTenSPTra, colSLTra, colDonViTra, colDonGiaTra, colThanhTienTra, colLyDo, colBo);

        // ===== PANE THÔNG TIN PHIẾU TRẢ =====
        Pane infoPane = new Pane();
        infoPane.getStyleClass().add("info-pane");
        infoPane.setPrefSize(391, 786);
        AnchorPane.setTopAnchor(infoPane, 70.0);
        AnchorPane.setRightAnchor(infoPane, 30.0);
        AnchorPane.setBottomAnchor(infoPane, 39.0);

        Label lblHeader = new Label("THÔNG TIN PHIẾU TRẢ HÀNG");
        lblHeader.getStyleClass().add("bold-label");
        lblHeader.setLayoutX(30);
        lblHeader.setLayoutY(14);

        Label lblMaHDText = new Label("Mã hóa đơn gốc:");
        lblMaHDText.setLayoutX(32);
        lblMaHDText.setLayoutY(55);

        lblMaHDGoc = new TextField();
        lblMaHDGoc.setEditable(false);
        lblMaHDGoc.setLayoutX(211);
        lblMaHDGoc.setLayoutY(49);
        lblMaHDGoc.setPrefWidth(120);

        Label lblTenKHText = new Label("Tên khách hàng:");
        lblTenKHText.setLayoutX(34);
        lblTenKHText.setLayoutY(100);

        lblTenKH = new Label();
        lblTenKH.setLayoutX(213);
        lblTenKH.setLayoutY(94);
        lblTenKH.setPrefWidth(120);

        Label lblSDTText = new Label("Số điện thoại:");
        lblSDTText.setLayoutX(35);
        lblSDTText.setLayoutY(145);

        lblSDT = new Label();
        lblSDT.setLayoutX(214);
        lblSDT.setLayoutY(139);
        lblSDT.setPrefWidth(120);

        Label lblNgayLapText = new Label("Ngày lập phiếu:");
        lblNgayLapText.setLayoutX(36);
        lblNgayLapText.setLayoutY(194);

        dpNgayLapPhieu = new DatePicker(LocalDate.now());
        dpNgayLapPhieu.setLayoutX(215);
        dpNgayLapPhieu.setLayoutY(193);
        dpNgayLapPhieu.setPrefSize(119, 27);

        Label lblTongGocText = new Label("Thành tiền gốc:");
        lblTongGocText.setLayoutX(28);
        lblTongGocText.setLayoutY(270);

        lblTongTienGoc = new Label("0 VNĐ");
        lblTongTienGoc.getStyleClass().add("value-label");
        lblTongTienGoc.setLayoutX(207);
        lblTongTienGoc.setLayoutY(269);

        Label lblTongTraText = new Label("Tổng tiền hàng trả lại:");
        lblTongTraText.setLayoutX(28);
        lblTongTraText.setLayoutY(310);

        lblTongTienTraLai = new Label("0 VNĐ");
        lblTongTienTraLai.getStyleClass().add("value-label");
        lblTongTienTraLai.setLayoutX(207);
        lblTongTienTraLai.setLayoutY(309);

        Label lblVatText = new Label("Thuế (VAT) hoàn lại:");
        lblVatText.setLayoutX(27);
        lblVatText.setLayoutY(351);

        lblVAT = new Label("0 VNĐ");
        lblVAT.getStyleClass().add("value-label");
        lblVAT.setLayoutX(207);
        lblVAT.setLayoutY(349);

        Label lblSoTienText = new Label("Số tiền trả lại:");
        lblSoTienText.getStyleClass().add("bold-label");
        lblSoTienText.setLayoutX(28);
        lblSoTienText.setLayoutY(400);

        lblSoTienTraLai = new Label("0 VNĐ");
        lblSoTienTraLai.getStyleClass().add("main-value-label");
        lblSoTienTraLai.setLayoutX(207);
        lblSoTienTraLai.setLayoutY(399);

        Label lblGhiChuText = new Label("Ghi chú:");
        lblGhiChuText.setLayoutX(34);
        lblGhiChuText.setLayoutY(454);

        txtGhiChu = new TextArea();
        txtGhiChu.setLayoutX(110);
        txtGhiChu.setLayoutY(449);
        txtGhiChu.setPrefSize(247, 128);

        btnTraHang = new Button("Trả hàng");
        btnTraHang.getStyleClass().add("success-btn");
        btnTraHang.setLayoutX(28);
        btnTraHang.setLayoutY(603);
        btnTraHang.setPrefSize(329, 44);
        btnTraHang.setFont(Font.font(14));

        btnInPhieuTra = new Button("Trả hàng và in phiếu trả");
        btnInPhieuTra.getStyleClass().add("print-btn");
        btnInPhieuTra.setLayoutX(30);
        btnInPhieuTra.setLayoutY(657);
        btnInPhieuTra.setPrefSize(325, 44);
        btnInPhieuTra.setFont(Font.font(14));

        btnHuy = new Button("Hủy");
        btnHuy.getStyleClass().add("print-btn");
        btnHuy.setStyle("-fx-background-color: red;");
        btnHuy.setLayoutX(32);
        btnHuy.setLayoutY(710);
        btnHuy.setPrefSize(325, 44);
        btnHuy.setFont(Font.font(14));

        infoPane.getChildren().addAll(lblHeader, lblMaHDText, lblMaHDGoc, lblTenKHText, lblTenKH, lblSDTText, lblSDT,
                lblNgayLapText, dpNgayLapPhieu, lblTongGocText, lblTongTienGoc, lblTongTraText, lblTongTienTraLai,
                lblVatText, lblVAT, lblSoTienText, lblSoTienTraLai, lblGhiChuText, txtGhiChu, btnTraHang, btnInPhieuTra, btnHuy);

        // ===== ADD TO ROOT =====
        root.getChildren().addAll(headerPane, lblTitle, searchPane, lblHint, lblSPGoc, tblSanPhamHoaDon,
                lblTra, tblChiTietTraHang, infoPane);
    }

    // ==================== CÁC XỬ LÝ GIẢ LẬP ====================
    private void xuLyTimHDGoc() {
        System.out.println("Đang tìm hóa đơn gốc: " + txtTimHoaDon.getText());
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {
        launch(args);
    }
}
