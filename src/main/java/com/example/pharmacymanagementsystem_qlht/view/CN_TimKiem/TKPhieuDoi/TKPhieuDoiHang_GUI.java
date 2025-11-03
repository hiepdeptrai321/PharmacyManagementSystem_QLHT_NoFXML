package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDoi;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDoiHang.TKPhieuDoiHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.PhieuDoiHang;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class TKPhieuDoiHang_GUI {

    public Pane mainPane;
    public ComboBox<String> cboTimKiem;
    public TextField txtNoiDungTimKiem;
    public DatePicker dpTuNgay;
    public DatePicker dpDenNgay;
    public Button btnTimKiem;
    public Button btnHuyBo;
    public ComboBox<String> cbLoc;
    public TableView<PhieuDoiHang> tblPD;
    public TableColumn<PhieuDoiHang, Number> colSTT;
    public TableColumn<PhieuDoiHang, String> colMaPD;
    public TableColumn<PhieuDoiHang, String> colMaHD;
    public TableColumn<PhieuDoiHang, String> colNgayLap;
    public TableColumn<PhieuDoiHang, String> colTenKH;
    public TableColumn<PhieuDoiHang, String> colSdtKH;
    public TableColumn<PhieuDoiHang, String> colTenNV;
    public TableColumn<PhieuDoiHang, String> colChiTiet;


    public TKPhieuDoiHang_GUI() {
        mainPane = new Pane();
        mainPane.setPrefSize(1646, 895);
        mainPane.setStyle("-fx-font-size: 14px;");

        // VBox tổng
        VBox vbox = new VBox();
        vbox.setLayoutX(10);
        vbox.setLayoutY(14);

        // Tiêu đề
        HBox hboxTitle = new HBox();
        hboxTitle.setPrefSize(782, 46);

        Label lbTimKiem = new Label("Tìm kiếm phiếu đổi hàng");
        lbTimKiem.setPrefSize(449, 53);
        lbTimKiem.getStyleClass().add("title");
        lbTimKiem.setFont(new Font(36));

        ImageView imgTitle = new ImageView(new Image(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/bill-8854.png").toExternalForm()));
        imgTitle.setFitWidth(48);
        imgTitle.setFitHeight(46);
        imgTitle.setPreserveRatio(true);
        imgTitle.setPickOnBounds(true);

        hboxTitle.getChildren().addAll(lbTimKiem, new Label(), imgTitle);

        // Separator
        Separator separator = new Separator();
        separator.setPrefWidth(200);

        // HBox tìm kiếm
        HBox hboxSearch = new HBox();
        hboxSearch.setPrefSize(1620, 69);
        VBox.setMargin(hboxSearch, new Insets(0, 0, 5, 0));

        cboTimKiem = new ComboBox<>();
        cboTimKiem.setPrefSize(152, 40);
        cboTimKiem.setPromptText("Tìm theo");
        cboTimKiem.getStyleClass().add("btntim");
        HBox.setMargin(cboTimKiem, new Insets(10, 5, 0, 0));

        txtNoiDungTimKiem = new TextField();
        txtNoiDungTimKiem.setPrefSize(261, 40);
        txtNoiDungTimKiem.setPromptText("Nhập thông tin phiếu đổi");
        txtNoiDungTimKiem.getStyleClass().add("tftim");
        HBox.setMargin(txtNoiDungTimKiem, new Insets(10, 0, 0, 0));

        Region region1 = new Region();
        region1.setPrefSize(30, 51);

        ImageView imgTime = new ImageView(new Image(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/time-2623.png").toExternalForm()));
        imgTime.setFitWidth(28);
        imgTime.setFitHeight(27);
        imgTime.setPreserveRatio(true);
        imgTime.setPickOnBounds(true);
        HBox.setMargin(imgTime, new Insets(16, 4, 0, 0));

        Label lbTu = new Label("Từ: ");
        lbTu.setPrefSize(25, 37);
        lbTu.getStyleClass().add("tftim");
        HBox.setMargin(lbTu, new Insets(10, 0, 0, 0));

        dpTuNgay = new DatePicker();
        dpTuNgay.setPrefSize(125, 40);
        dpTuNgay.getStyleClass().add("tftim");
        HBox.setMargin(dpTuNgay, new Insets(10, 10, 0, 0));

        Label lbDen = new Label("Đến: ");
        lbDen.setPrefSize(35, 37);
        lbDen.getStyleClass().add("tftim");
        HBox.setMargin(lbDen, new Insets(10, 0, 0, 0));

        dpDenNgay = new DatePicker();
        dpDenNgay.setPrefSize(125, 40);
        dpDenNgay.getStyleClass().add("tftim");
        HBox.setMargin(dpDenNgay, new Insets(10, 0, 0, 0));

        Region region2 = new Region();
        region2.setPrefSize(30, 51);

        btnTimKiem = new Button("Tìm");
        btnTimKiem.setPrefSize(73, 40);
        btnTimKiem.setDefaultButton(true);
        btnTimKiem.getStyleClass().add("btntim");
        HBox.setMargin(btnTimKiem, new Insets(10, 0, 0, 8));

        ImageView imgSearch = new ImageView(new Image(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/free-search-icon-2911-thumb.png").toExternalForm()));
        imgSearch.setFitWidth(20);
        imgSearch.setFitHeight(20);
        imgSearch.setPreserveRatio(true);
        imgSearch.setCursor(Cursor.DEFAULT);
        btnTimKiem.setGraphic(imgSearch);

        btnHuyBo = new Button();
        btnHuyBo.setPrefSize(52, 40);
        btnHuyBo.getStyleClass().add("btntim");
        btnHuyBo.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css").toExternalForm());
        HBox.setMargin(btnHuyBo, new Insets(10, 0, 0, 8));

        ImageView imgRefresh = new ImageView(new Image(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png").toExternalForm()));
        imgRefresh.setFitWidth(34);
        imgRefresh.setFitHeight(20);
        imgRefresh.setPreserveRatio(true);
        btnHuyBo.setGraphic(imgRefresh);

        Region region3 = new Region();
        region3.setPrefSize(448, 69);

        cbLoc = new ComboBox<>();
        cbLoc.setPrefSize(200, 41);
        cbLoc.setPromptText("⌛ Bộ lọc nhanh");
        cbLoc.getStyleClass().add("btntim");
        HBox.setMargin(cbLoc, new Insets(10, 0, 0, 0));

        hboxSearch.getChildren().addAll(cboTimKiem, txtNoiDungTimKiem, region1, imgTime, lbTu, dpTuNgay, lbDen, dpDenNgay, region2, btnTimKiem, btnHuyBo, region3, cbLoc);

        // Bảng dữ liệu
        ScrollPane scrollPane = new ScrollPane();
        tblPD = new TableView<>();
        tblPD.setPrefSize(1618, 747);

        colSTT = new TableColumn<>("STT");
        colSTT.setPrefWidth(51);
        colSTT.setStyle("-fx-alignment: CENTER;");

        colMaPD = new TableColumn<>("Mã phiếu đổi");
        colMaPD.setPrefWidth(193.333);
        colMaPD.setStyle("-fx-alignment: CENTER;");

        colMaHD = new TableColumn<>("Mã hóa đơn");
        colMaHD.setPrefWidth(213.666);
        colMaHD.setStyle("-fx-alignment: CENTER;");

        colNgayLap = new TableColumn<>("Ngày lập");
        colNgayLap.setPrefWidth(228.666);
        colNgayLap.setStyle("-fx-alignment: CENTER;");

        colTenKH = new TableColumn<>("Khách hàng");
        colTenKH.setPrefWidth(320.799);

        colSdtKH = new TableColumn<>("SĐT");
        colSdtKH.setPrefWidth(235.2);
        colSdtKH.setStyle("-fx-alignment: CENTER;");

        colTenNV = new TableColumn<>("Nhân viên");
        colTenNV.setPrefWidth(262);

        colChiTiet = new TableColumn<>();
        colChiTiet.setPrefWidth(95);
        colChiTiet.setStyle("-fx-alignment: CENTER;");

        tblPD.getColumns().addAll(colSTT, colMaPD, colMaHD, colNgayLap, colTenKH, colSdtKH, colTenNV, colChiTiet);
        scrollPane.setContent(tblPD);

        // Thêm tất cả vào VBox
        vbox.getChildren().addAll(hboxTitle, separator, hboxSearch, scrollPane);

        // Icon time
        ImageView iconTime = new ImageView();
        iconTime.setLayoutX(500);
        iconTime.setLayoutY(-91);
        iconTime.setFitWidth(40);
        iconTime.setFitHeight(35);
        iconTime.setPreserveRatio(true);
        iconTime.setPickOnBounds(true);
        iconTime.getStyleClass().add("icontime");

        mainPane.getChildren().addAll(vbox, iconTime);

        // Thêm CSS
        mainPane.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css").toExternalForm());
    }

    public void showWithController(Stage stage, TKPhieuDoiHang_Ctrl ctrl) {
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setTitle("Tìm kiếm phiếu đổi hàng");

        // Liên kết controller
        ctrl.tblPD = this.tblPD;
        ctrl.colSTT = this.colSTT;
        ctrl.colMaPD = this.colMaPD;
        ctrl.colMaHD = this.colMaHD;
        ctrl.colNgayLap = this.colNgayLap;
        ctrl.colTenKH = this.colTenKH;
        ctrl.colSdtKH = this.colSdtKH;
        ctrl.colTenNV = this.colTenNV;
        ctrl.colChiTiet = this.colChiTiet;
        ctrl.cboTimKiem = this.cboTimKiem;
        ctrl.txtNoiDungTimKiem = this.txtNoiDungTimKiem;
        ctrl.dpTuNgay = this.dpTuNgay;
        ctrl.dpDenNgay = this.dpDenNgay;
        ctrl.cbLoc = this.cbLoc;
        ctrl.btnTimKiem = this.btnTimKiem;
        ctrl.btnHuyBo = this.btnHuyBo;

        ctrl.initialize();
    }


}