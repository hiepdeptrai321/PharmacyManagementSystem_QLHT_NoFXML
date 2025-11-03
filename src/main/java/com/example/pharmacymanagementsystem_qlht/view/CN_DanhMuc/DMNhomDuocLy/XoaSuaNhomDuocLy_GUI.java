package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhomDuocLy;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhomDuocLy.SuaXoaNhomDuocLy;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class XoaSuaNhomDuocLy_GUI {

    public Parent createContent(SuaXoaNhomDuocLy controller) {
        Pane root = new Pane();
        root.setPrefSize(582.0, 251.0);
        root.setStyle("-fx-font-size: 13;");

        Label lbTitle = new Label("Chi tiết nhóm dược lý");
        lbTitle.setLayoutX(13.5); lbTitle.setLayoutY(8.0);
        lbTitle.setPrefSize(555.0, 31.0);
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setFont(new Font("System Bold", 18.0));

        Separator separator = new Separator();
        separator.setLayoutX(-8.0); separator.setLayoutY(38.0);
        separator.setPrefSize(589.0, 0.0);

        Label lbTenNDL = new Label("Tên nhóm dược lý");
        lbTenNDL.setLayoutX(13.5); lbTenNDL.setLayoutY(49.0);

        TextField txtTenNDL = new TextField();
        txtTenNDL.setLayoutX(14.0); txtTenNDL.setLayoutY(66.0);
        txtTenNDL.setPrefSize(555.0, 25.0);
        controller.txtTenNDL = txtTenNDL; // Bơm

        Label lbMoTa = new Label("Mô tả");
        lbMoTa.setLayoutX(9.0); lbMoTa.setLayoutY(97.0);

        TextArea txtMota = new TextArea();
        txtMota.setLayoutX(14.0); txtMota.setLayoutY(120.0);
        txtMota.setPrefSize(555.0, 69.0);
        controller.txtMota = txtMota; // Bơm

        Pane btnLuu = new Pane();
        btnLuu.setId("nutThem");
        btnLuu.setLayoutX(477.0); btnLuu.setLayoutY(202.0);
        btnLuu.setPrefSize(91.0, 25.0);
        btnLuu.getChildren().add(new Label("Lưu") {{ setId("txtTrang_Bold"); setLayoutX(30.0); setLayoutY(4.0); }});
        controller.btnLuu = btnLuu; // Bơm

        Pane btnXoa = new Pane();
        btnXoa.setId("nutXoa");
        btnXoa.setLayoutX(14.0); btnXoa.setLayoutY(202.0);
        btnXoa.setPrefSize(91.0, 25.0);
        btnXoa.getChildren().add(new Label("Xóa") {{ setId("txtTrang_Bold"); setLayoutX(34.0); setLayoutY(4.0); }});
        controller.btnXoa = btnXoa; // Bơm

        root.getChildren().addAll(lbTitle, separator, lbTenNDL, txtTenNDL, lbMoTa, txtMota, btnLuu, btnXoa);

        try {
            root.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhaCungCap.css").toExternalForm());
        } catch (Exception e) { e.printStackTrace(); }

        return root;
    }
}