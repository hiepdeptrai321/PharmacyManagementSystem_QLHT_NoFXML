package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.SuaTaiKhoan_Ctrl;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class SuaTaiKhoan_GUI {

    // === Controls trùng fx:id trong FXML ===
    public TextField txtTaiKhoan;
    public TextField txtMatKhau;
    public Button btnLuu;
    public Button btnHuy;

    /**
     * Hiển thị dialog, inject controller, gắn sự kiện và gọi load dữ liệu từ NhanVien.
     * Phương thức này sẽ block bằng showAndWait() như FXML dialog trước đây.
     */
    public void showWithController(Stage dialog, SuaTaiKhoan_Ctrl ctrl, NhanVien nhanVien) {
        AnchorPane root = buildUI();

        // ---- Inject fields sang controller (để tránh null như trước) ----
        try { ctrl.getClass().getField("txtTaiKhoan").set(ctrl, txtTaiKhoan); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("txtMatKhau").set(ctrl, txtMatKhau); } catch (Exception ignored) {}

        // ---- Gọi load dữ liệu vào form (ưu tiên loadTaiKhoan, fallback initialize) ----
        try {
            ctrl.getClass().getMethod("loadTaiKhoan", NhanVien.class).invoke(ctrl, nhanVien);
        } catch (NoSuchMethodException ns) {
            try { ctrl.getClass().getMethod("initialize", NhanVien.class).invoke(ctrl, nhanVien); } catch (Exception ignored) {}
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // ---- Gắn handler nút Lưu/Hủy về controller (hỗ trợ cả (ActionEvent) và không tham số) ----
        btnLuu.setOnAction(e -> {
            try { ctrl.getClass().getMethod("btnLuu", javafx.event.ActionEvent.class).invoke(ctrl, e); }
            catch (NoSuchMethodException ns) { try { ctrl.getClass().getMethod("btnLuu").invoke(ctrl); } catch (Exception ignored) {} }
            catch (Exception ex) { ex.printStackTrace(); }
        });
        btnHuy.setOnAction(e -> {
            try { ctrl.getClass().getMethod("btnHuy", javafx.event.ActionEvent.class).invoke(ctrl, e); }
            catch (NoSuchMethodException ns) { try { ctrl.getClass().getMethod("btnHuy").invoke(ctrl); } catch (Exception ignored) {} }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        // ---- Scene + CSS ----
        Scene scene = new Scene(root, 407, 182);
        scene.getStylesheets().add(requireCss("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhanVien.css"));
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setScene(scene);
        dialog.setTitle("Sửa tài khoản");
        dialog.showAndWait(); // block cho tới khi controller đóng Stage
    }

    private AnchorPane buildUI() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(407, 182);

        // Tiêu đề
        Label lbTitle = new Label("Sửa tài khoản");
        lbTitle.setId("lbtitle");
        lbTitle.setTextFill(javafx.scene.paint.Color.web("#0623ff"));
        lbTitle.setFont(Font.font("System Bold", 17));
        AnchorPane.setLeftAnchor(lbTitle, 157.0);
        AnchorPane.setTopAnchor(lbTitle, 10.0);

        // Icon trái
        ImageView iv = new ImageView(new Image(requireRes(
                "/com/example/pharmacymanagementsystem_qlht/img/free-icon-user-login-5867.png")));
        iv.setFitHeight(31); iv.setFitWidth(31);
        AnchorPane.setLeftAnchor(iv, 117.0);
        AnchorPane.setTopAnchor(iv, 8.0);

        // VBox form
        VBox vbox = new VBox();
        vbox.setLayoutX(25); vbox.setLayoutY(46);
        vbox.setPrefSize(365, 81);

        // HBox Tài khoản
        HBox rowTK = new HBox();
        rowTK.setSpacing(8);
        Label lbTK = new Label("Tài khoản:");
        lbTK.setPrefSize(89, 26);
        lbTK.setTextFill(javafx.scene.paint.Color.web("#005711"));
        lbTK.setFont(Font.font(15));
        txtTaiKhoan = new TextField();
        txtTaiKhoan.getStyleClass().add("tf");
        txtTaiKhoan.setPrefSize(303, 25);
        rowTK.getChildren().addAll(lbTK, txtTaiKhoan);
        VBox.setMargin(rowTK, new Insets(0,0,10,0));

        // HBox Mật khẩu
        HBox rowMK = new HBox();
        rowMK.setSpacing(8);
        Label lbMK = new Label("Mật khẩu:");
        lbMK.setPrefSize(99, 25);
        lbMK.setTextFill(javafx.scene.paint.Color.web("#005711"));
        lbMK.setFont(Font.font(15));
        txtMatKhau = new TextField();
        txtMatKhau.getStyleClass().add("tf");
        txtMatKhau.setPrefSize(312, 25);
        rowMK.getChildren().addAll(lbMK, txtMatKhau);
        VBox.setMargin(rowMK, new Insets(0,0,10,0));

        vbox.getChildren().addAll(rowTK, rowMK);

        // Nút
        btnLuu = new Button("Lưu");
        btnLuu.setId("btnLuu");
        btnLuu.setPrefSize(60, 25);
        AnchorPane.setLeftAnchor(btnLuu, 330.0);
        AnchorPane.setTopAnchor(btnLuu, 141.0);

        btnHuy = new Button("Hủy");
        btnHuy.setId("btnXoa"); // giữ nguyên id như FXML (nếu CSS đang dùng)
        btnHuy.setPrefSize(60, 25);
        AnchorPane.setLeftAnchor(btnHuy, 260.0);
        AnchorPane.setTopAnchor(btnHuy, 141.0);

        root.getChildren().addAll(lbTitle, vbox, iv, btnLuu, btnHuy);
        return root;
    }

    private String requireCss(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Không tìm thấy CSS: " + path).toExternalForm();
    }

    private String requireRes(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Không tìm thấy resource: " + path).toExternalForm();
    }
}
