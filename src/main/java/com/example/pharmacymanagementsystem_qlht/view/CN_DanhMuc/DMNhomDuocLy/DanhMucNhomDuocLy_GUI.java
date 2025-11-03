package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhomDuocLy;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhomDuocLy.DanhMucNhomDuocLy_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.NhomDuocLy;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.InputStream;

public class DanhMucNhomDuocLy_GUI {

    // Hàm này tạo UI và bơm component vào controller
    public Parent createContent(DanhMucNhomDuocLy_Ctrl controller) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646.0, 895.0);
        root.setStyle("-fx-font-size: 14;");

        TextField txtTimKiem = new TextField();
        txtTimKiem.setPromptText("Tìm theo mã, tên kệ");
        txtTimKiem.setPrefSize(772.0, 40.0);
        AnchorPane.setTopAnchor(txtTimKiem, 51.0);
        AnchorPane.setLeftAnchor(txtTimKiem, 11.0);
        controller.txtTimKiem = txtTimKiem; // Bơm

        Button btnTim = new Button("🔍 Tìm");
        btnTim.setId("btntim");
        btnTim.getStyleClass().add("btn");
        btnTim.setPrefSize(77.0, 40.0);
        AnchorPane.setTopAnchor(btnTim, 51.0);
        AnchorPane.setLeftAnchor(btnTim, 794.0);
        controller.btnTim = btnTim; // Bơm

        Button btnLamMoi = new Button();
        btnLamMoi.setId("btnReset");
        btnLamMoi.setPrefSize(45.0, 40.0);
        AnchorPane.setTopAnchor(btnLamMoi, 51.0);
        AnchorPane.setLeftAnchor(btnLamMoi, 879.0);
        controller.btnLamMoi = btnLamMoi; // Bơm

        // Nút Xóa (btnXoa) có trong controller nhưng không có trên FXML,
        // nên tôi không tạo và không bơm nó

        Button btnThem = new Button("✚Thêm nhóm dược lý");
        btnThem.setId("btnthemthuoc");
        btnThem.getStyleClass().add("btn");
        btnThem.setPrefSize(176.0, 40.0);
        AnchorPane.setTopAnchor(btnThem, 52.0);
        AnchorPane.setLeftAnchor(btnThem, 1450.0);
        controller.btnThem = btnThem; // Bơm

        TableView<NhomDuocLy> tbNhomDuocLy = new TableView<>();
        tbNhomDuocLy.setId("tablethuoc");
        tbNhomDuocLy.setPrefSize(1624.0, 785.0);
        AnchorPane.setTopAnchor(tbNhomDuocLy, 102.0);
        AnchorPane.setLeftAnchor(tbNhomDuocLy, 12.0);
        AnchorPane.setBottomAnchor(tbNhomDuocLy, 8.0);
        AnchorPane.setRightAnchor(tbNhomDuocLy, 10.0);
        controller.tbNhomDuocLy = tbNhomDuocLy; // Bơm

        TableColumn<NhomDuocLy, String> cotSTT = new TableColumn<>("STT");
        cotSTT.setPrefWidth(90.33); cotSTT.setStyle("-fx-alignment: CENTER;");
        controller.cotSTT = cotSTT; // Bơm

        TableColumn<NhomDuocLy, String> cotMaNDL = new TableColumn<>("Mã Nhóm Dược Lý");
        cotMaNDL.setPrefWidth(260.66); cotMaNDL.setStyle("-fx-alignment: CENTER;");
        controller.cotMaNDL = cotMaNDL; // Bơm

        TableColumn<NhomDuocLy, String> cotTenNDL = new TableColumn<>("Tên Nhóm Dược Lý");
        cotTenNDL.setPrefWidth(1139.0);
        controller.cotTenNDL = cotTenNDL; // Bơm

        TableColumn<NhomDuocLy, String> colChiTiet = new TableColumn<>();
        colChiTiet.setPrefWidth(117.0); colChiTiet.setStyle("-fx-alignment: CENTER;");
        controller.colChiTiet = colChiTiet; // Bơm

        tbNhomDuocLy.getColumns().addAll(cotSTT, cotMaNDL, cotTenNDL, colChiTiet);

        // --- Title Pane và Ảnh (Đã sửa lỗi đường dẫn) ---
        Pane titlePane = new Pane();
        titlePane.setId("lblpaneTitle");
        titlePane.setPrefSize(1634.0, 40.0);
        AnchorPane.setTopAnchor(titlePane, -2.0);
        AnchorPane.setLeftAnchor(titlePane, 11.0);

        Label lbTitle = new Label("Danh mục nhóm dược lý");
        lbTitle.setId("lbtitle");
        lbTitle.setPrefSize(306.0, 36.0);
        lbTitle.setLayoutY(2.0);
        lbTitle.setFont(new Font(24.0)); // FXML của bạn là 24.0 (trong <Font size="48.0" /> là sai)
        titlePane.getChildren().add(lbTitle);

        try {
            String refreshPath = "com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png";
            InputStream refreshStream = getClass().getClassLoader().getResourceAsStream(refreshPath);
            ImageView iconLamMoi = new ImageView(new Image(refreshStream));
            iconLamMoi.setFitHeight(23.0); iconLamMoi.setFitWidth(30.0);
            iconLamMoi.setPickOnBounds(true); iconLamMoi.setPreserveRatio(true);
            AnchorPane.setTopAnchor(iconLamMoi, 60.0);
            AnchorPane.setLeftAnchor(iconLamMoi, 890.0);
            root.getChildren().add(iconLamMoi);

            String drugPath = "com/example/pharmacymanagementsystem_qlht/img/drugs.png";
            InputStream drugStream = getClass().getClassLoader().getResourceAsStream(drugPath);
            ImageView iconTitle = new ImageView(new Image(drugStream));
            iconTitle.setFitHeight(34.0); iconTitle.setFitWidth(46.0);
            iconTitle.setLayoutX(299.0); iconTitle.setLayoutY(4.0);
            iconTitle.setPickOnBounds(true); iconTitle.setPreserveRatio(true);
            titlePane.getChildren().add(iconTitle);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("LỖI: Không tìm thấy file ảnh (refresh-3104.png hoặc drugs.png)");
        }

        root.getChildren().addAll(txtTimKiem, btnTim, btnLamMoi, btnThem, tbNhomDuocLy, titlePane);
        try {
            root.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css").toExternalForm());
        } catch (Exception e) { e.printStackTrace(); }

        return root;
    }
}