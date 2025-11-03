package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.ThietLapLuongNV_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.LuongNhanVien;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class ThietLapLuongNV_GUI {

    // ==== Controls trùng fx:id ====
    public TextField txtMaNV;
    public TextField txtTenNV;
    public TextField txtLuongHT;
    public TextField txtPhuCapHienTai;
    public TextField txtLuongMoi;
    public TextField txtPhuCapMoi;
    public TextArea  txtGhiChu;
    public Button btnLuu, btnHuy;

    /** Hiển thị dialog, inject sang controller, nạp dữ liệu hiện tại và gắn handler ActionEvent */
    public void showWithController(Stage stage,
                                   ThietLapLuongNV_Ctrl ctrl,
                                   NhanVien nhanVien,
                                   LuongNhanVien luongHienHanh) {

        AnchorPane root = buildUI();

        // ---- inject fields sang controller ----
        try { ctrl.getClass().getField("txtMaNV").set(ctrl, txtMaNV); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("txtTenNV").set(ctrl, txtTenNV); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("txtLuongHT").set(ctrl, txtLuongHT); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("txtPhuCapHienTai").set(ctrl, txtPhuCapHienTai); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("txtLuongMoi").set(ctrl, txtLuongMoi); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("txtPhuCapMoi").set(ctrl, txtPhuCapMoi); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("txtGhiChu").set(ctrl, txtGhiChu); } catch (Exception ignored) {}

        // ---- gắn ActionEvent đúng yêu cầu ----
        btnLuu.setOnAction(ctrl::btnLuu);
        btnHuy.setOnAction(ctrl::btnHuy);

        // ---- GỌI initialize của controller (để controller set dữ liệu + formatter) ----
        ctrl.initialize(nhanVien, luongHienHanh);

        // ---- scene + css ----
        Scene scene = new Scene(root, 407, 446);
        scene.getStylesheets().add(requireCss("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhanVien.css"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }


    private AnchorPane buildUI() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(407, 446);

        // Tiêu đề
        Label lbTitle = new Label("                       Thiết lập lương nhân viên");
        lbTitle.setId("lbtitle");
        lbTitle.setLayoutX(21); lbTitle.setLayoutY(18);
        lbTitle.setPrefSize(365, 26);
        lbTitle.setTextFill(javafx.scene.paint.Paint.valueOf("#0623ff"));
        lbTitle.setFont(Font.font("System Bold", 17));

        ImageView iv = new ImageView(new Image(requireRes("/com/example/pharmacymanagementsystem_qlht/img/dvt2.png")));
        iv.setFitHeight(46); iv.setFitWidth(31);
        iv.setLayoutX(93); iv.setLayoutY(16);
        iv.setPreserveRatio(true); iv.setPickOnBounds(true);

        // VBox content
        VBox box = new VBox(10);
        box.setLayoutX(25); box.setLayoutY(56);
        box.setPrefSize(354, 332);

        // Hàng: Mã NV
        HBox rowMa = new HBox();
        rowMa.setSpacing(10);
        Label lbMa = labelLeft("Mã nhân viên:", 150);
        txtMaNV = new TextField(); txtMaNV.setPrefSize(262, 30); txtMaNV.setEditable(false); txtMaNV.setDisable(true); txtMaNV.getStyleClass().add("tf");
        rowMa.getChildren().addAll(lbMa, txtMaNV);
        VBox.setMargin(rowMa, new Insets(0,0,10,0));

        // Hàng: Tên NV
        HBox rowTen = new HBox();
        rowTen.setSpacing(10);
        Label lbTen = labelLeft("Tên nhân viên:", 143);
        txtTenNV = new TextField(); txtTenNV.setPrefSize(252, 30); txtTenNV.setEditable(false); txtTenNV.getStyleClass().add("tf");
        rowTen.getChildren().addAll(lbTen, txtTenNV);
        VBox.setMargin(rowTen, new Insets(0,0,10,0));

        // Hàng: Lương hiện tại
        HBox rowLHT = new HBox();
        rowLHT.setSpacing(10);
        Label lbLHT = labelLeft("Lương hiện tại:", 132);
        txtLuongHT = new TextField(); txtLuongHT.setPrefSize(241, 30); txtLuongHT.setEditable(false); txtLuongHT.setDisable(true); txtLuongHT.getStyleClass().add("tf");
        rowLHT.getChildren().addAll(lbLHT, txtLuongHT);
        VBox.setMargin(rowLHT, new Insets(0,0,10,0));

        // Hàng: Phụ cấp hiện tại
        HBox rowPCHT = new HBox();
        rowPCHT.setSpacing(10);
        Label lbPCHT = labelLeft("Phụ cấp hiện tại:", 124);
        txtPhuCapHienTai = new TextField(); txtPhuCapHienTai.setPrefSize(230, 30); txtPhuCapHienTai.setEditable(false);
        rowPCHT.getChildren().addAll(lbPCHT, txtPhuCapHienTai);
        VBox.setMargin(rowPCHT, new Insets(0,0,10,0));

        // Hàng: Lương mới
        HBox rowLM = new HBox();
        rowLM.setSpacing(10);
        Label lbLM = labelLeft("Lương mới:", 124);
        txtLuongMoi = new TextField(); txtLuongMoi.setPrefSize(229, 30);
        rowLM.getChildren().addAll(lbLM, txtLuongMoi);
        VBox.setMargin(rowLM, new Insets(0,0,10,0));

        // Hàng: Phụ cấp mới
        HBox rowPCM = new HBox();
        rowPCM.setSpacing(10);
        Label lbPCM = labelLeft("Phụ cấp mới:", 124);
        txtPhuCapMoi = new TextField(); txtPhuCapMoi.setPrefSize(229, 30); txtPhuCapMoi.setMaxHeight(30);
        rowPCM.getChildren().addAll(lbPCM, txtPhuCapMoi);
        VBox.setMargin(rowPCM, new Insets(0,0,10,0));

        // Hàng: Ghi chú
        HBox rowGC = new HBox();
        rowGC.setSpacing(10);
        Label lbGC = labelLeft("Ghi chú:", 124);
        txtGhiChu = new TextArea(); txtGhiChu.setPrefSize(231, 55);
        rowGC.getChildren().addAll(lbGC, txtGhiChu);

        box.getChildren().addAll(rowMa, rowTen, rowLHT, rowPCHT, rowLM, rowPCM, rowGC);

        // Buttons
        btnLuu = new Button("Lưu");
        btnLuu.setId("btnLuu");
        btnLuu.setLayoutX(319); btnLuu.setLayoutY(396);
        btnLuu.setPrefSize(60, 25);

        btnHuy = new Button("Hủy");
        btnHuy.setId("btnXoa");
        btnHuy.setLayoutX(253); btnHuy.setLayoutY(397);
        btnHuy.setPrefSize(60, 25);

        // Add all
        root.getChildren().addAll(lbTitle, iv, box, btnLuu, btnHuy);
        return root;
    }

    // Helpers
    private Label labelLeft(String text, double prefWidth) {
        Label lb = new Label(text);
        lb.setPrefWidth(prefWidth);
        lb.setPrefHeight(30);
        lb.setTextFill(javafx.scene.paint.Paint.valueOf("#005711"));
        lb.setFont(Font.font(15));
        return lb;
    }

    private String requireCss(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Không tìm thấy CSS: " + path).toExternalForm();
    }

    private String requireRes(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Không tìm thấy resource: " + path).toExternalForm();
    }
}
