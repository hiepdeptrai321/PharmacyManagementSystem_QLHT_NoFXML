package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang.DanhMucKeHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.KeHang;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.InputStream;

public class DanhMucKeHang_GUI {

    public Parent createContent(DanhMucKeHang_Ctrl controller) {
        AnchorPane root = new AnchorPane(); // [cite: 28]
        root.setPrefSize(1646.0, 895.0); // [cite: 28]
        root.setStyle("-fx-font-size: 14;"); // [cite: 28]

        TextField txtTimKiem = new TextField(); // [cite: 29]
        txtTimKiem.setPromptText("Tìm theo mã, tên kệ");
        txtTimKiem.setPrefSize(772.0, 40.0);
        AnchorPane.setTopAnchor(txtTimKiem, 51.0); AnchorPane.setLeftAnchor(txtTimKiem, 11.0);
        controller.txtTimKiem = txtTimKiem; // [cite: 29]

        Button btnTim = new Button("🔍 Tìm"); // [cite: 29]
        btnTim.setId("btntim"); btnTim.getStyleClass().add("btn");
        btnTim.setPrefSize(77.0, 40.0);
        AnchorPane.setTopAnchor(btnTim, 51.0); AnchorPane.setLeftAnchor(btnTim, 794.0);
        controller.btnTim = btnTim; // [cite: 29]

        Button btnThem = new Button("✚Thêm kệ hàng"); // [cite: 29]
        btnThem.setId("btnthemthuoc"); btnThem.getStyleClass().add("btn");
        btnThem.setPrefSize(144.0, 40.0);
        AnchorPane.setTopAnchor(btnThem, 52.0); AnchorPane.setLeftAnchor(btnThem, 1482.0);
        controller.btnThem = btnThem; // [cite: 29]

        Button btnLamMoi = new Button(); // [cite: 29]
        btnLamMoi.setId("btnReset");
        btnLamMoi.setPrefSize(45.0, 40.0);
        AnchorPane.setTopAnchor(btnLamMoi, 51.0); AnchorPane.setLeftAnchor(btnLamMoi, 879.0);
        controller.btnLamMoi = btnLamMoi; // [cite: 29]

        // Nút Xóa (btnXoa) có trong controller nhưng không có trên FXML
        // nên tôi không tạo và không bơm nó

        TableView<KeHang> tblKeHang = new TableView<>(); // [cite: 29]
        tblKeHang.setId("tablethuoc");
        tblKeHang.setPrefSize(1624.0, 785.0);
        AnchorPane.setTopAnchor(tblKeHang, 102.0); AnchorPane.setLeftAnchor(tblKeHang, 12.0);
        AnchorPane.setBottomAnchor(tblKeHang, 8.0); AnchorPane.setRightAnchor(tblKeHang, 10.0);
        controller.tblKeHang = tblKeHang; // [cite: 29]

        TableColumn<KeHang, String> cotSTT = new TableColumn<>("STT"); // [cite: 31]
        cotSTT.setPrefWidth(90.33); cotSTT.setStyle("-fx-alignment: CENTER;"); // [cite: 30]
        controller.cotSTT = cotSTT; // [cite: 30]

        TableColumn<KeHang, String> cotMaKe = new TableColumn<>("Mã Kệ"); // [cite: 32]
        cotMaKe.setPrefWidth(260.66); cotMaKe.setStyle("-fx-alignment: CENTER;"); // [cite: 31]
        controller.cotMaKe = cotMaKe; // [cite: 31]

        TableColumn<KeHang, String> cotTenKe = new TableColumn<>("Tên Kệ"); // [cite: 32]
        cotTenKe.setPrefWidth(1143.0);
        controller.cotTenKe = cotTenKe; // [cite: 32]

        TableColumn<KeHang, Void> colChiTiet = new TableColumn<>(); // [cite: 32]
        colChiTiet.setPrefWidth(111.0); colChiTiet.setStyle("-fx-alignment: CENTER;"); // [cite: 32]
        controller.colChiTiet = (TableColumn) colChiTiet; // Bơm (ép kiểu) [cite: 32]

        tblKeHang.getColumns().addAll(cotSTT, cotMaKe, cotTenKe, colChiTiet); // [cite: 30]

        Pane titlePane = new Pane(); // [cite: 33]
        titlePane.setId("lblpaneTitle");
        titlePane.setPrefSize(1634.0, 40.0);
        AnchorPane.setTopAnchor(titlePane, -2.0); AnchorPane.setLeftAnchor(titlePane, 11.0);

        Label lbTitle = new Label("Danh mục kệ hàng"); // [cite: 34]
        lbTitle.setId("lbtitle");
        lbTitle.setLayoutY(2.0); lbTitle.setPrefSize(306.0, 36.0);
        lbTitle.setFont(new Font(48.0)); // [cite: 34]
        titlePane.getChildren().add(lbTitle);

        try {
            String refreshPath = "com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png";
            InputStream refreshStream = getClass().getClassLoader().getResourceAsStream(refreshPath);
            ImageView iconLamMoi = new ImageView(new Image(refreshStream)); // [cite: 33]
            iconLamMoi.setFitHeight(23.0); iconLamMoi.setFitWidth(30.0);
            iconLamMoi.setPickOnBounds(true); iconLamMoi.setPreserveRatio(true);
            AnchorPane.setTopAnchor(iconLamMoi, 60.0); AnchorPane.setLeftAnchor(iconLamMoi, 890.0);
            root.getChildren().add(iconLamMoi);

            // Sửa lại tên file ảnh (bỏ %20)
            String shelfPath = "com/example/pharmacymanagementsystem_qlht/img/shelf_8693463 (1).png"; // [cite: 35]
            InputStream shelfStream = getClass().getClassLoader().getResourceAsStream(shelfPath);
            ImageView iconTitle = new ImageView(new Image(shelfStream)); // [cite: 34]
            iconTitle.setFitHeight(38.0); iconTitle.setFitWidth(38.0);
            iconTitle.setLayoutX(226.0); iconTitle.setLayoutY(1.0);
            iconTitle.setPickOnBounds(true); iconTitle.setPreserveRatio(true);
            titlePane.getChildren().add(iconTitle);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("LỖI: Không tìm thấy file ảnh (refresh-3104.png hoặc shelf...png)");
        }

        root.getChildren().addAll(txtTimKiem, btnTim, btnThem, btnLamMoi, tblKeHang, titlePane);
        try {
            root.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css").toExternalForm()); // [cite: 29]
        } catch (Exception e) { e.printStackTrace(); }

        return root;
    }
}