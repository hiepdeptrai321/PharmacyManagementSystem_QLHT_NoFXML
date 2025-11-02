package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhomDuocLy;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhomDuocLy.ThemNhomDuocLy_Ctrl;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class ThemNhomDuocLy_GUI {

    // Không cần biến public ở đây

    // Phương thức này nhận controller làm tham số
    public Parent createContent(ThemNhomDuocLy_Ctrl controller) {
        Pane root = new Pane();
        root.setPrefSize(582.0, 251.0);
        root.setStyle("-fx-font-size: 13;");

        Label lbTitle = new Label("Thêm nhóm dược lý");
        lbTitle.setLayoutX(13.5); lbTitle.setLayoutY(8.0);
        lbTitle.setPrefSize(555.0, 31.0);
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setFont(new Font("System Bold", 18.0));

        Label lbTenNDL = new Label("Tên nhóm dược lý");
        lbTenNDL.setLayoutX(13.5); lbTenNDL.setLayoutY(49.0);

        // 1. Tạo component
        TextField txtTenNhomDuocLy = new TextField();
        txtTenNhomDuocLy.setLayoutX(14.0); txtTenNhomDuocLy.setLayoutY(66.0);
        txtTenNhomDuocLy.setPrefSize(555.0, 25.0);
        // 2. Bơm (inject) vào controller
        controller.txtTenNhomDuocLy = txtTenNhomDuocLy;

        Label lbMoTa = new Label("Mô tả");
        lbMoTa.setLayoutX(9.0); lbMoTa.setLayoutY(97.0);

        TextArea txtMoTa = new TextArea();
        txtMoTa.setLayoutX(14.0); txtMoTa.setLayoutY(120.0);
        txtMoTa.setPrefSize(555.0, 69.0);
        controller.txtMoTa = txtMoTa;

        Button btnThem = new Button();
        btnThem.setId("nutThem");
        btnThem.setLayoutX(479.0); btnThem.setLayoutY(202.0);
        btnThem.setPrefSize(74.0, 25.0);
        btnThem.setGraphic(new Label("Thêm") {{ setId("txtTrang_Bold"); }});
        controller.btnThem = btnThem;

        Button btnHuy = new Button();
        btnHuy.setId("nutXoa");
        btnHuy.setLayoutX(25.0); btnHuy.setLayoutY(202.0);
        btnHuy.setPrefSize(63.0, 25.0);
        btnHuy.setGraphic(new Label("Hủy") {{ setId("txtTrang_Bold"); }});
        controller.btnHuy = btnHuy;

        root.getChildren().addAll(lbTitle, lbTenNDL, txtTenNhomDuocLy, lbMoTa, txtMoTa, btnThem, btnHuy);

        try {
            root.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css").toExternalForm());
            root.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhaCungCap.css").toExternalForm());
        } catch (Exception e) { e.printStackTrace(); }

        return root;
    }
}