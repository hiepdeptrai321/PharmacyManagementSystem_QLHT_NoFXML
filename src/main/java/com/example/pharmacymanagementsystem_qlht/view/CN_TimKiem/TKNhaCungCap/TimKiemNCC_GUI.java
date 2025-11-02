package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKNhaCungCap;

import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TimKiemNCC_GUI {

    // --- Khai báo public các thành phần ---
    public ComboBox<String> cboTimKiem = new ComboBox<>();
    public TextField txtTimKiem = new TextField();
    public Button btnTim = new Button();
    public Button btnLamMoi = new Button();
    public TableView<NhaCungCap> tbNCC = new TableView<>();
    public TableColumn<NhaCungCap, String> cotSTT = new TableColumn<>("STT");
    public TableColumn<NhaCungCap, String> cotMNCC = new TableColumn<>("Mã nhà cung cấp");
    public TableColumn<NhaCungCap, String> cotTenNCC = new TableColumn<>("Tên nhà cung cấp");
    public TableColumn<NhaCungCap, String> cotEmil = new TableColumn<>("Email");
    public TableColumn<NhaCungCap, String> cotSDT = new TableColumn<>("Số điện thoại");
    public TableColumn<NhaCungCap, String> cotDiaChi = new TableColumn<>("Địa chỉ");
    public TableColumn<NhaCungCap, Void> cotChiTiet = new TableColumn<>();

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
        titleHBox.setPrefHeight(45.0);
        titleHBox.setPrefWidth(1615.0);

        Label titleLabel = new Label("Tìm kiếm nhà cung cấp");
        titleLabel.setFont(new Font("System Bold", 30.0));
        HBox.setMargin(titleLabel, new Insets(0, 0, 0, 10.0));

        ImageView titleIcon = createIcon("/com/example/pharmacymanagementsystem_qlht/img/supplier-icon-png-9.jpg", 40.0, 40.0);
        HBox.setMargin(titleIcon, new Insets(0, 0, 0, 10.0));
        titleHBox.getChildren().addAll(titleLabel, titleIcon);
        VBox.setMargin(titleHBox, new Insets(0, 0, 10.0, 0));

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
        tbNCC.setPrefHeight(738.0);
        tbNCC.setPrefWidth(1613.0);

        cotSTT.setPrefWidth(64.0);
        cotSTT.setStyle("-fx-alignment: CENTER;");
        cotMNCC.setPrefWidth(152.0);
        cotMNCC.setStyle("-fx-alignment: CENTER;");
        cotTenNCC.setPrefWidth(331.0);
        cotEmil.setPrefWidth(280.0);
        cotSDT.setPrefWidth(256.0);
        cotSDT.setStyle("-fx-alignment: CENTER;");
        cotDiaChi.setPrefWidth(415.0);
        cotChiTiet.setPrefWidth(100.0);
        cotChiTiet.setStyle("-fx-alignment: CENTER;");

        tbNCC.getColumns().addAll(cotSTT, cotMNCC, cotTenNCC, cotEmil, cotSDT, cotDiaChi, cotChiTiet);
        scrollPane.setContent(tbNCC);

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