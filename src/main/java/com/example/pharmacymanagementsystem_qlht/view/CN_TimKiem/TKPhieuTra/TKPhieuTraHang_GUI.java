package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuTra;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuTraHang.TKPhieuTraHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.PhieuTraHang;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;


import java.util.Objects;

public class TKPhieuTraHang_GUI extends Application {

    @Override
    public void start(Stage stage) {
        buildAndShow(stage);
    }

    private void buildAndShow(Stage stage) {
        Pane mainPane = new Pane();
        mainPane.setId("mainPane");
        mainPane.setPrefSize(1646, 895);
        mainPane.setStyle("-fx-font-size: 14px;");

        VBox vbox = new VBox();
        vbox.setLayoutX(10);
        vbox.setLayoutY(14);

        // ======== HBox Tiêu đề ========
        HBox hbTitle = new HBox();
        hbTitle.setPrefSize(782, 46);

        Label lbTimKiem = new Label("Tìm kiếm phiếu trả hàng");
        lbTimKiem.setId("lbTimKiem");
        lbTimKiem.setPrefSize(449, 53);
        lbTimKiem.getStyleClass().add("title");
        lbTimKiem.setFont(new Font(36));

        Label lbEmpty = new Label();

        ImageView imgBill = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/bill-8854.png")).toExternalForm())
        );
        imgBill.setFitHeight(46);
        imgBill.setFitWidth(48);
        imgBill.setPreserveRatio(true);
        imgBill.setPickOnBounds(true);

        hbTitle.getChildren().addAll(lbTimKiem, lbEmpty, imgBill);

        Separator separator = new Separator();
        separator.setPrefWidth(200);

        // ======== HBox tìm kiếm ========
        HBox hbSearch = new HBox();
        hbSearch.setPrefSize(1607, 68);

        ComboBox<String> cboTimKiem = new ComboBox<>();
        cboTimKiem.setId("cboTimKiem");
        cboTimKiem.setPrefSize(140, 40);
        cboTimKiem.setPromptText("Tìm theo");
        cboTimKiem.getStyleClass().add("btntim");
        HBox.setMargin(cboTimKiem, new Insets(10, 5, 0, 0));

        TextField txtNoiDungTimKiem = new TextField();
        txtNoiDungTimKiem.setId("txtNoiDungTimKiem");
        txtNoiDungTimKiem.setPrefSize(251, 40);
        txtNoiDungTimKiem.setPromptText("Nhập thông tin phiếu trả");
        txtNoiDungTimKiem.getStyleClass().add("tftim");
        HBox.setMargin(txtNoiDungTimKiem, new Insets(10, 0, 0, 0));

        Region rg1 = new Region();
        rg1.setPrefSize(30, 51);

        ImageView imgTime = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/time-2623.png")).toExternalForm())
        );
        imgTime.setFitHeight(27);
        imgTime.setFitWidth(28);
        imgTime.setPreserveRatio(true);
        imgTime.setPickOnBounds(true);
        HBox.setMargin(imgTime, new Insets(16, 4, 0, 0));

        Label lbTu = new Label("Từ: ");
        lbTu.setPrefSize(27, 37);
        lbTu.getStyleClass().add("tftim");
        HBox.setMargin(lbTu, new Insets(10, 0, 0, 0));

        DatePicker dpTuNgay = new DatePicker();
        dpTuNgay.setId("dpTuNgay");
        dpTuNgay.setPrefSize(125, 40);
        dpTuNgay.getStyleClass().add("tftim");
        HBox.setMargin(dpTuNgay, new Insets(10, 10, 0, 0));

        Label lbDen = new Label("Đến: ");
        lbDen.setPrefSize(35, 37);
        lbDen.getStyleClass().add("tftim");
        HBox.setMargin(lbDen, new Insets(10, 0, 0, 0));

        DatePicker dpDenNgay = new DatePicker();
        dpDenNgay.setId("dpDenNgay");
        dpDenNgay.setPrefSize(125, 40);
        dpDenNgay.getStyleClass().add("tftim");
        HBox.setMargin(dpDenNgay, new Insets(10, 0, 0, 0));

        Region rg2 = new Region();
        rg2.setPrefSize(30, 51);

        Button btnTimKiem = new Button("Tìm");
        btnTimKiem.setId("btnTimKiem");
        btnTimKiem.setPrefSize(78, 40);
        btnTimKiem.setDefaultButton(true);
        btnTimKiem.getStyleClass().add("btntim");
        btnTimKiem.setContentDisplay(ContentDisplay.RIGHT);
        HBox.setMargin(btnTimKiem, new Insets(10, 0, 0, 8));

        ImageView imgSearch = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/free-search-icon-2911-thumb.png")).toExternalForm())
        );
        imgSearch.setFitHeight(20);
        imgSearch.setFitWidth(150);
        imgSearch.setPreserveRatio(true);
        imgSearch.setPickOnBounds(true);
        imgSearch.setCursor(Cursor.DEFAULT);
        btnTimKiem.setGraphic(imgSearch);

        Button btnHuyBo = new Button();
        btnHuyBo.setId("btnHuyBo");
        btnHuyBo.setPrefSize(52, 40);
        btnHuyBo.getStyleClass().add("btntim");
        btnHuyBo.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css")).toExternalForm());
        HBox.setMargin(btnHuyBo, new Insets(10, 0, 0, 8));

        ImageView imgRefresh = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")).toExternalForm())
        );
        imgRefresh.setFitHeight(20);
        imgRefresh.setFitWidth(34);
        imgRefresh.setPreserveRatio(true);
        imgRefresh.setPickOnBounds(true);
        btnHuyBo.setGraphic(imgRefresh);

        Region rg3 = new Region();
        rg3.setPrefSize(465, 68);

        ComboBox<String> cbLoc = new ComboBox<>();
        cbLoc.setId("cbLoc");
        cbLoc.setPrefSize(185, 41);
        cbLoc.setPromptText("⌛ Bộ lọc nhanh");
        cbLoc.getStyleClass().add("btntim");
        HBox.setMargin(cbLoc, new Insets(10, 0, 0, 0));

        hbSearch.getChildren().addAll(cboTimKiem, txtNoiDungTimKiem, rg1, imgTime, lbTu, dpTuNgay, lbDen, dpDenNgay, rg2, btnTimKiem, btnHuyBo, rg3, cbLoc);
        VBox.setMargin(hbSearch, new Insets(0, 0, 5, 0));

        // ======== ScrollPane chứa TableView ========
        TableView<PhieuTraHang> tblPT = new TableView<>();
        tblPT.setId("tblPT");
        tblPT.setPrefSize(1605, 749);

        TableColumn<PhieuTraHang, String> colSTT = new TableColumn<>("STT");
        colSTT.setId("colSTT");
        colSTT.setPrefWidth(51);
        colSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuTraHang, String> colMaPT = new TableColumn<>("Mã phiếu trả");
        colMaPT.setId("colMaPT");
        colMaPT.setPrefWidth(189.33);
        colMaPT.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuTraHang, String> colMaHD = new TableColumn<>("Mã hóa đơn");
        colMaHD.setId("colMaHD");
        colMaHD.setPrefWidth(182.66);
        colMaHD.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuTraHang, String> colNgayLap = new TableColumn<>("Ngày lập");
        colNgayLap.setId("colNgayLap");
        colNgayLap.setPrefWidth(216.33);
        colNgayLap.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuTraHang, String> colTenKH = new TableColumn<>("Khách hàng");
        colTenKH.setId("colTenKH");
        colTenKH.setPrefWidth(285);

        TableColumn<PhieuTraHang, String> colSdtKH = new TableColumn<>("SĐT");
        colSdtKH.setId("colSdtKH");
        colSdtKH.setPrefWidth(243);
        colSdtKH.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuTraHang, String> colTenNV = new TableColumn<>("Nhân viên");
        colTenNV.setId("colTenNV");
        colTenNV.setPrefWidth(337);

        TableColumn<PhieuTraHang, String> colChiTiet = new TableColumn<>("Chi tiết");
        colChiTiet.setId("colChiTiet");
        colChiTiet.setPrefWidth(84);
        colChiTiet.setStyle("-fx-alignment: CENTER;");

        tblPT.getColumns().addAll(colSTT, colMaPT, colMaHD, colNgayLap, colTenKH, colSdtKH, colTenNV, colChiTiet);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(tblPT);

        vbox.getChildren().addAll(hbTitle, separator, hbSearch, scrollPane);

        ImageView imgTimeIcon = new ImageView();
        imgTimeIcon.setFitHeight(35);
        imgTimeIcon.setFitWidth(40);
        imgTimeIcon.setLayoutX(500);
        imgTimeIcon.setLayoutY(-91);
        imgTimeIcon.setPreserveRatio(true);
        imgTimeIcon.getStyleClass().add("icontime");

        mainPane.getChildren().addAll(vbox, imgTimeIcon);

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css")).toExternalForm());

        stage.setTitle("Tìm kiếm phiếu trả hàng");
        stage.setScene(scene);
        stage.show();
    }

    // ======== Hiển thị với Controller ========
    public void showWithController(Stage stage, TKPhieuTraHang_Ctrl ctrl) {
        Pane mainPane = new Pane();
        mainPane.setId("mainPane");
        mainPane.setPrefSize(1646, 895);
        mainPane.setStyle("-fx-font-size: 14px;");

        VBox vbox = new VBox();
        vbox.setLayoutX(10);
        vbox.setLayoutY(14);

        // ======== HBox Tiêu đề ========
        HBox hbTitle = new HBox();
        hbTitle.setPrefSize(782, 46);

        Label lbTimKiem = new Label("Tìm kiếm phiếu trả hàng");
        lbTimKiem.setId("lbTimKiem");
        lbTimKiem.setPrefSize(449, 53);
        lbTimKiem.getStyleClass().add("title");
        lbTimKiem.setFont(new Font(36));

        Label lbEmpty = new Label();

        ImageView imgBill = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/bill-8854.png")).toExternalForm())
        );
        imgBill.setFitHeight(46);
        imgBill.setFitWidth(48);
        imgBill.setPreserveRatio(true);
        imgBill.setPickOnBounds(true);

        hbTitle.getChildren().addAll(lbTimKiem, lbEmpty, imgBill);

        Separator separator = new Separator();
        separator.setPrefWidth(200);

        // ======== HBox tìm kiếm ========
        HBox hbSearch = new HBox();
        hbSearch.setPrefSize(1607, 68);

        ComboBox<String> cboTimKiem = new ComboBox<>();
        cboTimKiem.setId("cboTimKiem");
        cboTimKiem.setPrefSize(140, 40);
        cboTimKiem.setPromptText("Tìm theo");
        cboTimKiem.getStyleClass().add("btntim");
        HBox.setMargin(cboTimKiem, new Insets(10, 5, 0, 0));

        TextField txtNoiDungTimKiem = new TextField();
        txtNoiDungTimKiem.setId("txtNoiDungTimKiem");
        txtNoiDungTimKiem.setPrefSize(251, 40);
        txtNoiDungTimKiem.setPromptText("Nhập thông tin phiếu trả");
        txtNoiDungTimKiem.getStyleClass().add("tftim");
        HBox.setMargin(txtNoiDungTimKiem, new Insets(10, 0, 0, 0));

        Region rg1 = new Region();
        rg1.setPrefSize(30, 51);

        ImageView imgTime = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/time-2623.png")).toExternalForm())
        );
        imgTime.setFitHeight(27);
        imgTime.setFitWidth(28);
        imgTime.setPreserveRatio(true);
        imgTime.setPickOnBounds(true);
        HBox.setMargin(imgTime, new Insets(16, 4, 0, 0));

        Label lbTu = new Label("Từ: ");
        lbTu.setPrefSize(27, 37);
        lbTu.getStyleClass().add("tftim");
        HBox.setMargin(lbTu, new Insets(10, 0, 0, 0));

        DatePicker dpTuNgay = new DatePicker();
        dpTuNgay.setId("dpTuNgay");
        dpTuNgay.setPrefSize(125, 40);
        dpTuNgay.getStyleClass().add("tftim");
        HBox.setMargin(dpTuNgay, new Insets(10, 10, 0, 0));

        Label lbDen = new Label("Đến: ");
        lbDen.setPrefSize(35, 37);
        lbDen.getStyleClass().add("tftim");
        HBox.setMargin(lbDen, new Insets(10, 0, 0, 0));

        DatePicker dpDenNgay = new DatePicker();
        dpDenNgay.setId("dpDenNgay");
        dpDenNgay.setPrefSize(125, 40);
        dpDenNgay.getStyleClass().add("tftim");
        HBox.setMargin(dpDenNgay, new Insets(10, 0, 0, 0));

        Region rg2 = new Region();
        rg2.setPrefSize(30, 51);

        Button btnTimKiem = new Button("Tìm");
        btnTimKiem.setId("btnTimKiem");
        btnTimKiem.setPrefSize(78, 40);
        btnTimKiem.setDefaultButton(true);
        btnTimKiem.getStyleClass().add("btntim");
        btnTimKiem.setContentDisplay(ContentDisplay.RIGHT);
        HBox.setMargin(btnTimKiem, new Insets(10, 0, 0, 8));

        ImageView imgSearch = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/free-search-icon-2911-thumb.png")).toExternalForm())
        );
        imgSearch.setFitHeight(20);
        imgSearch.setFitWidth(150);
        imgSearch.setPreserveRatio(true);
        imgSearch.setPickOnBounds(true);
        imgSearch.setCursor(Cursor.DEFAULT);
        btnTimKiem.setGraphic(imgSearch);

        Button btnHuyBo = new Button();
        btnHuyBo.setId("btnHuyBo");
        btnHuyBo.setPrefSize(52, 40);
        btnHuyBo.getStyleClass().add("btntim");
        btnHuyBo.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css")).toExternalForm());
        HBox.setMargin(btnHuyBo, new Insets(10, 0, 0, 8));

        ImageView imgRefresh = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")).toExternalForm())
        );
        imgRefresh.setFitHeight(20);
        imgRefresh.setFitWidth(34);
        imgRefresh.setPreserveRatio(true);
        imgRefresh.setPickOnBounds(true);
        btnHuyBo.setGraphic(imgRefresh);

        Region rg3 = new Region();
        rg3.setPrefSize(465, 68);

        ComboBox<String> cbLoc = new ComboBox<>();
        cbLoc.setId("cbLoc");
        cbLoc.setPrefSize(185, 41);
        cbLoc.setPromptText("⌛ Bộ lọc nhanh");
        cbLoc.getStyleClass().add("btntim");
        HBox.setMargin(cbLoc, new Insets(10, 0, 0, 0));

        hbSearch.getChildren().addAll(cboTimKiem, txtNoiDungTimKiem, rg1, imgTime, lbTu, dpTuNgay, lbDen, dpDenNgay, rg2, btnTimKiem, btnHuyBo, rg3, cbLoc);
        VBox.setMargin(hbSearch, new Insets(0, 0, 5, 0));

        // ======== ScrollPane chứa TableView ========
        TableView<PhieuTraHang> tblPT = new TableView<>();
        tblPT.setId("tblPT");
        tblPT.setPrefSize(1605, 749);

        TableColumn<PhieuTraHang, Number> colSTT = new TableColumn<>("STT");
        colSTT.setId("colSTT");
        colSTT.setPrefWidth(51);
        colSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuTraHang, String> colMaPT = new TableColumn<>("Mã phiếu trả");
        colMaPT.setId("colMaPT");
        colMaPT.setPrefWidth(189.33);
        colMaPT.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuTraHang, String> colMaHD = new TableColumn<>("Mã hóa đơn");
        colMaHD.setId("colMaHD");
        colMaHD.setPrefWidth(182.66);
        colMaHD.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuTraHang, String> colNgayLap = new TableColumn<>("Ngày lập");
        colNgayLap.setId("colNgayLap");
        colNgayLap.setPrefWidth(216.33);
        colNgayLap.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuTraHang, String> colTenKH = new TableColumn<>("Khách hàng");
        colTenKH.setId("colTenKH");
        colTenKH.setPrefWidth(285);

        TableColumn<PhieuTraHang, String> colSdtKH = new TableColumn<>("SĐT");
        colSdtKH.setId("colSdtKH");
        colSdtKH.setPrefWidth(243);
        colSdtKH.setStyle("-fx-alignment: CENTER;");

        TableColumn<PhieuTraHang, String> colTenNV = new TableColumn<>("Nhân viên");
        colTenNV.setId("colTenNV");
        colTenNV.setPrefWidth(337);

        TableColumn<PhieuTraHang, String> colChiTiet = new TableColumn<>("Chi tiết");
        colChiTiet.setId("colChiTiet");
        colChiTiet.setPrefWidth(84);
        colChiTiet.setStyle("-fx-alignment: CENTER;");

        tblPT.getColumns().addAll(colSTT, colMaPT, colMaHD, colNgayLap, colTenKH, colSdtKH, colTenNV, colChiTiet);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(tblPT);

        vbox.getChildren().addAll(hbTitle, separator, hbSearch, scrollPane);

        ImageView imgTimeIcon = new ImageView();
        imgTimeIcon.setFitHeight(35);
        imgTimeIcon.setFitWidth(40);
        imgTimeIcon.setLayoutX(500);
        imgTimeIcon.setLayoutY(-91);
        imgTimeIcon.setPreserveRatio(true);
        imgTimeIcon.getStyleClass().add("icontime");

        mainPane.getChildren().addAll(vbox, imgTimeIcon);

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css")).toExternalForm());

        stage.setTitle("Tìm kiếm phiếu trả hàng");
        stage.setScene(scene);
        stage.show();


         ctrl.cboTimKiem = cboTimKiem;
         ctrl.txtNoiDungTimKiem = txtNoiDungTimKiem;
         ctrl.dpTuNgay = dpTuNgay;
         ctrl.dpDenNgay = dpDenNgay;
         ctrl.btnTimKiem = btnTimKiem;
         ctrl.btnHuyBo = btnHuyBo;
         ctrl.cbLoc = cbLoc;
         ctrl.tblPT = tblPT;
         ctrl.colSTT = colSTT;
         ctrl.colMaPT = colMaPT;
         ctrl.colMaHD = colMaHD;
         ctrl.colNgayLap = colNgayLap;
         ctrl.colTenKH = colTenKH;
         ctrl.colSdtKH = colSdtKH;
         ctrl.colTenNV = colTenNV;
         ctrl.colChiTiet = colChiTiet;
         ctrl.initialize();
    }

    public static void main(String[] args) {
        launch(args);
    }
}