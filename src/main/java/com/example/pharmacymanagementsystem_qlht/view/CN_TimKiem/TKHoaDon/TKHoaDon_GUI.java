package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKHoaDon;

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
import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon.TimKiemHoaDon_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.HoaDon;

public class TKHoaDon_GUI extends Application {

    public Pane mainPane;
    public TableView<HoaDon> tblHD;
    public TableColumn<HoaDon, String> colMaHD;
    public TableColumn<HoaDon, String> colNgayLap;
    public TableColumn<HoaDon, String> colTenKH;
    public TableColumn<HoaDon, String> colSdtKH;
    public TableColumn<HoaDon, String> colTenNV;
    public TableColumn<HoaDon, Integer> colSLP;
    public TableColumn<HoaDon, String> colChiTiet;

    public ComboBox<String> cboTieuChiTimKiem;
    public TextField txtNoiDungTimKiem;
    public DatePicker dpTuNgay;
    public DatePicker dpDenNgay;
    public ComboBox<String> cboBoLocNhanh;
    public Button btnTimKiem;
    public Button btnHuyBo;

    @Override
    public void start(Stage stage) {
        mainPane = new Pane();
        mainPane.setPrefSize(1646, 895);
        mainPane.setStyle("-fx-font-size: 14px;");

        VBox vbox = new VBox();
        vbox.setLayoutX(10);
        vbox.setLayoutY(14);
        vbox.setPrefSize(1619, 871);

        // === Tiêu đề ===
        HBox hbTitle = new HBox();
        hbTitle.setPrefSize(1544, 58);

        Label lbTimKiem = new Label("Tìm kiếm hóa đơn");
        lbTimKiem.setPrefSize(328, 53);
        lbTimKiem.getStyleClass().add("title");
        lbTimKiem.setFont(new Font(36));

        ImageView imgBill = new ImageView(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/bill-8854.png")));
        imgBill.setFitHeight(46);
        imgBill.setFitWidth(48);
        imgBill.setPreserveRatio(true);

        hbTitle.getChildren().addAll(lbTimKiem, new Label(), imgBill);

        Separator separator = new Separator();

        // === HBox tìm kiếm ===
        HBox hbSearch = new HBox();
        hbSearch.setPrefSize(1618, 63);
        VBox.setMargin(hbSearch, new Insets(0, 0, 5, 0));

        cboTieuChiTimKiem = new ComboBox<>();
        cboTieuChiTimKiem.setPromptText("Tiêu chí tìm kiếm");
        cboTieuChiTimKiem.setPrefSize(184, 40);
        cboTieuChiTimKiem.getStyleClass().add("btntim");
        HBox.setMargin(cboTieuChiTimKiem, new Insets(10, 5, 0, 0));

        txtNoiDungTimKiem = new TextField();
        txtNoiDungTimKiem.setPromptText("Nhập nội dung tìm kiếm");
        txtNoiDungTimKiem.setPrefSize(260, 40);
        txtNoiDungTimKiem.getStyleClass().add("tftim");
        HBox.setMargin(txtNoiDungTimKiem, new Insets(10, 0, 0, 0));

        Region rg1 = new Region();
        rg1.setPrefSize(32, 63);

        ImageView imgTime = new ImageView(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/time-2623.png")));
        imgTime.setFitHeight(34);
        imgTime.setFitWidth(30);
        imgTime.setPreserveRatio(true);
        HBox.setMargin(imgTime, new Insets(16, 4, 0, 0));

        Label lbTu = new Label("Từ: ");
        lbTu.setPrefSize(32, 37);
        lbTu.getStyleClass().add("tftim");
        lbTu.setFont(new Font(14));
        HBox.setMargin(lbTu, new Insets(10, 0, 0, 0));

        dpTuNgay = new DatePicker();
        dpTuNgay.setPrefSize(125, 40);
        dpTuNgay.getStyleClass().add("tftim");
        HBox.setMargin(dpTuNgay, new Insets(10, 10, 0, 0));

        Label lbDen = new Label("Đến: ");
        lbDen.setPrefSize(38, 37);
        lbDen.getStyleClass().add("tftim");
        lbDen.setFont(new Font(14));
        HBox.setMargin(lbDen, new Insets(10, 0, 0, 0));

        dpDenNgay = new DatePicker();
        dpDenNgay.setPrefSize(125, 40);
        dpDenNgay.getStyleClass().add("tftim");
        HBox.setMargin(dpDenNgay, new Insets(10, 0, 0, 0));

        Region rg2 = new Region();
        rg2.setPrefSize(25, 63);

        btnTimKiem = new Button("Tìm");
        btnTimKiem.setPrefSize(72, 40);
        btnTimKiem.getStyleClass().add("btntim");
        btnTimKiem.setDefaultButton(true);
        HBox.setMargin(btnTimKiem, new Insets(10, 0, 0, 8));

        ImageView imgSearch = new ImageView(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/free-search-icon-2911-thumb.png")));
        imgSearch.setFitHeight(20);
        imgSearch.setFitWidth(150);
        imgSearch.setPreserveRatio(true);
        imgSearch.setCursor(Cursor.DEFAULT);
        btnTimKiem.setGraphic(imgSearch);

        btnHuyBo = new Button();
        btnHuyBo.setPrefSize(52, 40);
        btnHuyBo.getStyleClass().add("btntim");
        btnHuyBo.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css").toExternalForm());
        HBox.setMargin(btnHuyBo, new Insets(10, 0, 0, 8));

        ImageView imgRefresh = new ImageView(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")));
        imgRefresh.setFitHeight(20);
        imgRefresh.setFitWidth(40);
        imgRefresh.setPreserveRatio(true);
        btnHuyBo.setGraphic(imgRefresh);

        Region rg3 = new Region();
        rg3.setPrefSize(395, 63);

        cboBoLocNhanh = new ComboBox<>();
        cboBoLocNhanh.setPromptText("⌛ Bộ lọc nhanh");
        cboBoLocNhanh.setPrefSize(222, 41);
        cboBoLocNhanh.getStyleClass().add("btntim");
        HBox.setMargin(cboBoLocNhanh, new Insets(10, 0, 0, 0));

        hbSearch.getChildren().addAll(
                cboTieuChiTimKiem, txtNoiDungTimKiem, rg1,
                imgTime, lbTu, dpTuNgay, lbDen, dpDenNgay,
                rg2, btnTimKiem, btnHuyBo, rg3, cboBoLocNhanh
        );

        // === TableView ===
        tblHD = new TableView<>();
        tblHD.setPrefSize(1619, 735);
        tblHD.setStyle("-fx-font-size: 14px;");

        colMaHD = new TableColumn<>("Mã hóa đơn");
        colMaHD.setPrefWidth(162);
        colNgayLap = new TableColumn<>("Ngày lập");
        colNgayLap.setPrefWidth(255);
        colTenKH = new TableColumn<>("Khách hàng");
        colTenKH.setPrefWidth(351);
        colSdtKH = new TableColumn<>("SĐT");
        colSdtKH.setPrefWidth(229);
        colTenNV = new TableColumn<>("Nhân viên");
        colTenNV.setPrefWidth(293);
        colSLP = new TableColumn<>("Số lượng phiếu đổi trả");
        colSLP.setPrefWidth(205);
        colChiTiet = new TableColumn<>();
        colChiTiet.setPrefWidth(105);

        tblHD.getColumns().addAll(colMaHD, colNgayLap, colTenKH, colSdtKH, colTenNV, colSLP, colChiTiet);

        vbox.getChildren().addAll(hbTitle, separator, hbSearch, tblHD);
        mainPane.getChildren().add(vbox);

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Tìm kiếm hóa đơn");
        stage.show();

        // Gắn controller xử lý logic
        TimKiemHoaDon_Ctrl ctrl = new TimKiemHoaDon_Ctrl();
        ctrl.tblHD = tblHD;
        ctrl.colMaHD = colMaHD;
        ctrl.colNgayLap = colNgayLap;
        ctrl.colTenKH = colTenKH;
        ctrl.colSdtKH = colSdtKH;
        ctrl.colTenNV = colTenNV;
        ctrl.colSLP = colSLP;
        ctrl.colChiTiet = colChiTiet;
        ctrl.cboTieuChiTimKiem = cboTieuChiTimKiem;
        ctrl.txtNoiDungTimKiem = txtNoiDungTimKiem;
        ctrl.dpTuNgay = dpTuNgay;
        ctrl.dpDenNgay = dpDenNgay;
        ctrl.cboBoLocNhanh = cboBoLocNhanh;
        ctrl.btnTimKiem = btnTimKiem;
        ctrl.btnHuyBo = btnHuyBo;
        ctrl.initialize();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
