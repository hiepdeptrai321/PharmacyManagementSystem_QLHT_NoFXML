package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKKhachHang;

import com.example.pharmacymanagementsystem_qlht.model.KhachHang;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;

public class TimKiemKhachHang_GUI {

    // --- Khai báo public các thành phần ---
    public ComboBox<String> cboTimKiem = new ComboBox<>();
    public TextField txtTimKiem = new TextField();
    public Button btnTim = new Button();
    public Button btnLamMoi = new Button();
    public TableView<KhachHang> tbKhachHang = new TableView<>();
    public TableColumn<KhachHang, String> cotSTT = new TableColumn<>("STT");
    public TableColumn<KhachHang, String> cotMaKH = new TableColumn<>("Mã KH");
    public TableColumn<KhachHang, String> cotTenKH = new TableColumn<>("Tên khách hàng");
    public TableColumn<KhachHang, LocalDate> cotNgaySinh = new TableColumn<>("Ngày sinh");
    public TableColumn<KhachHang, String> cotEmail = new TableColumn<>("Email");
    public TableColumn<KhachHang, String> cotSDT = new TableColumn<>("SDT");
    public TableColumn<KhachHang, String> cotGT = new TableColumn<>("Giới tính");
    public TableColumn<KhachHang, String> cotDiaChi = new TableColumn<>("Địa chỉ");

    private Pane root;

    public Parent createContent() {
        root = new Pane();
        root.setPrefHeight(895.0);
        root.setPrefWidth(1646.0);
        root.setStyle("-fx-font-size: 14 px;");

        VBox mainVBox = new VBox();
        mainVBox.setLayoutX(10.0);
        mainVBox.setLayoutY(14.0);

        // --- Tiêu đề ---
        HBox titleHBox = new HBox();
        titleHBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        titleHBox.setSpacing(10.0);
        VBox.setMargin(titleHBox, new Insets(0, 0, 10.0, 10.0));

        Label titleLabel = new Label("Tìm kiếm khách hàng");
        titleLabel.setFont(new Font("System Bold", 30.0));

        ImageView titleIcon = createIcon("/com/example/pharmacymanagementsystem_qlht/img/search-employee-8969.png", 40.0, 40.0);
        titleHBox.getChildren().addAll(titleLabel, titleIcon);

        Separator separator = new Separator();
        separator.setPrefWidth(1615.0);
        VBox.setMargin(separator, new Insets(0, 0, 10.0, 0));

        // --- Thanh tìm kiếm ---
        HBox searchHBox = new HBox();
        searchHBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        searchHBox.setSpacing(10.0);
        VBox.setMargin(searchHBox, new Insets(0, 0, 10.0, 0));

        cboTimKiem.setPrefHeight(40.0);
        cboTimKiem.setPrefWidth(214.0);
        cboTimKiem.setPromptText("Tiêu chí tìm kiếm");

        txtTimKiem.setPrefHeight(40.0);
        txtTimKiem.setPrefWidth(1253.0);
        txtTimKiem.setPromptText("Nhập từ khóa tìm kiếm");

        btnTim.setPrefHeight(40.0);
        btnTim.setPrefWidth(47.0);
        btnTim.setGraphic(createIcon("/com/example/pharmacymanagementsystem_qlht/img/free-search-icon-2911-thumb.png", 30.0, 30.0));
        btnTim.getStyleClass().add("btnSearch");

        btnLamMoi.setPrefHeight(40.0);
        btnLamMoi.setPrefWidth(47.0);
        btnLamMoi.setGraphic(createIcon("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png", 30.0, 30.0));
        btnLamMoi.getStyleClass().add("btnSearch");

        searchHBox.getChildren().addAll(cboTimKiem, txtTimKiem, btnTim, btnLamMoi);

        // --- Bảng ---
        ScrollPane scrollPane = new ScrollPane();
        tbKhachHang.setPrefHeight(723.0);
        tbKhachHang.setPrefWidth(1615.0);

        cotSTT.setPrefWidth(61.0);
        cotSTT.setStyle("-fx-alignment: CENTER;");
        cotMaKH.setPrefWidth(142.0);
        cotMaKH.setStyle("-fx-alignment: CENTER;");
        cotTenKH.setPrefWidth(273.0);
        cotNgaySinh.setPrefWidth(172.0);
        cotNgaySinh.setStyle("-fx-alignment: CENTER;");
        cotEmail.setPrefWidth(251.0);
        cotSDT.setPrefWidth(240.0);
        cotSDT.setStyle("-fx-alignment: CENTER;");
        cotGT.setPrefWidth(131.0);
        cotGT.setStyle("-fx-alignment: CENTER;");
        cotDiaChi.setPrefWidth(345.0);

        tbKhachHang.getColumns().addAll(cotSTT, cotMaKH, cotTenKH, cotNgaySinh, cotEmail, cotSDT, cotGT, cotDiaChi);
        scrollPane.setContent(tbKhachHang);

        mainVBox.getChildren().addAll(titleHBox, separator, searchHBox, scrollPane);
        root.getChildren().add(mainVBox);

        return root;
    }

    private ImageView createIcon(String path, double fitHeight, double fitWidth) {
        try {
            Image image = new Image(getClass().getResourceAsStream(path));
            ImageView icon = new ImageView(image);
            icon.setFitHeight(fitHeight);
            icon.setFitWidth(fitWidth);
            icon.setPreserveRatio(true);
            return icon;
        } catch (Exception e) {
            System.err.println("Không tải được icon: " + path);
            return new ImageView();
        }
    }
}