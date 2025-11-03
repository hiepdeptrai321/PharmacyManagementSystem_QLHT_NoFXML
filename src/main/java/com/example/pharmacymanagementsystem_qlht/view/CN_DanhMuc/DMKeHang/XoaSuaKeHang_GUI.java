package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang.XoaSuaKeHang_Ctrl;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class XoaSuaKeHang_GUI {

    public Parent createContent(XoaSuaKeHang_Ctrl controller) {
        Pane root = new Pane(); //
        root.setPrefSize(582.0, 251.0); //
        root.setStyle("-fx-font-size: 13;"); //

        Label lbTitle = new Label("Chi tiết kệ hàng"); //
        lbTitle.setLayoutX(13.5); lbTitle.setLayoutY(8.0);
        lbTitle.setPrefSize(555.0, 31.0);
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setFont(new Font("System Bold", 18.0)); //

        Label lbTenKe = new Label("Tên kệ"); //
        lbTenKe.setLayoutX(13.5); lbTenKe.setLayoutY(49.0);

        TextField txtTenKe = new TextField(); //
        txtTenKe.setLayoutX(14.0); txtTenKe.setLayoutY(66.0);
        txtTenKe.setPrefSize(555.0, 25.0);
        controller.txtTenKe = txtTenKe; //

        Label lbMoTa = new Label("Mô tả"); //
        lbMoTa.setLayoutX(13.0); lbMoTa.setLayoutY(104.0);

        TextArea txtMota = new TextArea(); //
        txtMota.setLayoutX(14.0); txtMota.setLayoutY(120.0);
        txtMota.setPrefSize(555.0, 69.0);
        controller.txtMota = txtMota; //

        Pane btnLuu = new Pane(); //
        btnLuu.setId("nutThem");
        btnLuu.setLayoutX(477.0); btnLuu.setLayoutY(202.0);
        btnLuu.setPrefSize(91.0, 25.0);
        btnLuu.getChildren().add(new Label("Lưu") {{ setId("txtTrang_Bold"); setLayoutX(30.0); setLayoutY(4.0); }}); //
        controller.btnLuu = btnLuu; //

        Pane btnXoa = new Pane(); //
        btnXoa.setId("nutXoa");
        btnXoa.setLayoutX(14.0); btnXoa.setLayoutY(202.0);
        btnXoa.setPrefSize(91.0, 25.0);
        btnXoa.getChildren().add(new Label("Xóa") {{ setId("txtTrang_Bold"); setLayoutX(34.0); setLayoutY(4.0); }}); //
        controller.btnXoa = btnXoa; //

        root.getChildren().addAll(lbTitle, lbTenKe, txtTenKe, lbMoTa, txtMota, btnLuu, btnXoa);

        try {
            root.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhaCungCap.css").toExternalForm()); //
        } catch (Exception e) { e.printStackTrace(); }

        return root;
    }
}