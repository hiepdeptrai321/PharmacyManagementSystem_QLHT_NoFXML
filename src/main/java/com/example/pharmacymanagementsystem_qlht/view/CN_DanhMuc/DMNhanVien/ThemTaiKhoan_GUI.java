package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.ThemTaiKhoan_Ctrl;
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

public class ThemTaiKhoan_GUI {

    // giữ tham chiếu control (không lookup)
    private TextField txtTaiKhoan;
    private PasswordField txtMatKhau;
    private Button btnLuu, btnHuy;

    public void showWithController(Stage dialog, ThemTaiKhoan_Ctrl ctrl) {
        // Root
        AnchorPane root = new AnchorPane();
        root.setPrefSize(407, 171);

        // CSS (tùy chỉnh path resources của bạn)
        addStylesheetSafely(dialog, root, "../../css/SuaXoaNhanVien.css",
                "/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhanVien.css");

        // Title
        Label lbTitle = new Label("Thêm tài khoản");
        lbTitle.setLayoutX(157);
        lbTitle.setLayoutY(7);
        lbTitle.setPrefSize(180, 26);
        lbTitle.setTextFill(javafx.scene.paint.Color.web("#0623ff"));
        lbTitle.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 17));
        lbTitle.setPadding(new Insets(0, 0, 10, 0));

        // Icon
        ImageView iv = new ImageView();
        iv.setFitWidth(31);
        iv.setFitHeight(46);
        iv.setLayoutX(117);
        iv.setLayoutY(5);
        iv.setPickOnBounds(true);
        iv.setPreserveRatio(true);
        setImageSafely(iv, "../../img/free-icon-user-login-5867.png",
                "/com/example/pharmacymanagementsystem_qlht/img/free-icon-user-login-5867.png");

        // Form
        VBox vbox = new VBox(10);
        vbox.setLayoutX(25);
        vbox.setLayoutY(43);
        vbox.setPrefSize(365, 98);

        // Row: Tài khoản
        Label lbTK = new Label("Tài khoản");
        lbTK.setPrefSize(100, 30);
        lbTK.setTextFill(javafx.scene.paint.Color.web("#005711"));
        lbTK.setFont(Font.font(15));

        txtTaiKhoan = new TextField();
        txtTaiKhoan.setPrefSize(287, 30);
        txtTaiKhoan.getStyleClass().add("tf");

        HBox rowTK = new HBox();
        rowTK.getChildren().addAll(lbTK, txtTaiKhoan);
        rowTK.setPrefSize(365, 34);

        // Row: Mật khẩu
        Label lbMK = new Label("Mật khẩu");
        lbMK.setPrefSize(99, 30);
        lbMK.setTextFill(javafx.scene.paint.Color.web("#005711"));
        lbMK.setFont(Font.font(15));

        txtMatKhau = new PasswordField();
        txtMatKhau.setPrefSize(289, 31);
        txtMatKhau.getStyleClass().add("tf");

        HBox rowMK = new HBox();
        rowMK.getChildren().addAll(lbMK, txtMatKhau);

        vbox.getChildren().addAll(rowTK, rowMK);

        // Buttons
        btnLuu = new Button("Lưu");
        btnLuu.setLayoutX(330);
        btnLuu.setLayoutY(129);
        btnLuu.setPrefSize(60, 25);

        btnHuy = new Button("Hủy");
        btnHuy.setLayoutX(257);
        btnHuy.setLayoutY(129);
        btnHuy.setPrefSize(60, 25);

        // Add to root
        root.getChildren().addAll(lbTitle, iv, vbox, btnHuy, btnLuu);

        // Gắn vào controller (vì không có @FXML/initialize tự động)
        if (ctrl != null) {
            // public field trong Ctrl (hoặc viết setter nếu bạn muốn đóng gói)
            ctrl.txtTaiKhoan = txtTaiKhoan;
            ctrl.txtMatKhau = txtMatKhau;

            // handler tương đương onAction="#btnLuu/#btnHuy"
            btnLuu.setOnAction(ctrl::btnLuu);
            btnHuy.setOnAction(ctrl::btnHuy);

            // nếu controller có initialize() thì gọi tay
            try { ctrl.initialize(); } catch (Exception ignored) {}
        }

        // Scene
        Scene scene = new Scene(root, 407, 171);
        dialog.setScene(scene);
    }

    private void addStylesheetSafely(Stage stage, AnchorPane root, String rel, String abs) {
        var u1 = getClass().getResource(rel);
        var u2 = getClass().getResource(abs);
        if (u1 != null) {
            root.getStylesheets().add(u1.toExternalForm());
        } else if (u2 != null) {
            root.getStylesheets().add(u2.toExternalForm());
        }
    }

    private void setImageSafely(ImageView view, String rel, String abs) {
        var u1 = getClass().getResource(rel);
        var u2 = getClass().getResource(abs);
        var picked = (u1 != null) ? u1 : u2;
        if (picked != null) view.setImage(new Image(picked.toExternalForm()));
    }
}
