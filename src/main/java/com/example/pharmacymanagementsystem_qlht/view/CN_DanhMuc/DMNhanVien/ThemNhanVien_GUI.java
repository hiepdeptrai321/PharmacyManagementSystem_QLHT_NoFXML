package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.ThemNhanVien_Ctrl;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class ThemNhanVien_GUI {

    // === Controls (tương đương fx:id) ===
    public TextField txtTenNV;
    public TextField txtSDT;
    public TextField txtEmail;
    public TextField txtDiaChi;
    public ComboBox<String> cbxGioiTinh;
    public DatePicker txtNgaySinh;
    public Button btnThem;
    public Button btnThemTaiKhoan;
    public Button btnHuy;

    /**
     * Hiển thị dialog: build UI, inject controller, gắn handler và gọi initialize().
     * LƯU Ý: Caller phải initOwner/title/icon trước khi gọi hàm này.
     */
    public void showWithController(Stage dialog, ThemNhanVien_Ctrl ctrl) {
        AnchorPane root = buildUI();

        // ---- Inject control sang controller (tránh null khi không dùng FXML) ----
        safeSet(ctrl, "txtTenNV", txtTenNV);
        safeSet(ctrl, "txtSDT", txtSDT);
        safeSet(ctrl, "txtEmail", txtEmail);
        safeSet(ctrl, "txtDiaChi", txtDiaChi);
        safeSet(ctrl, "cbxGioiTinh", cbxGioiTinh);
        safeSet(ctrl, "txtNgaySinh", txtNgaySinh);
        safeSet(ctrl, "btnThem", btnThem);
        safeSet(ctrl, "btnHuy", btnHuy);

        // ---- Gắn handler tương đương onAction="#..." ----
        btnThem.setOnAction(e -> {
            try { ctrl.getClass().getMethod("btnThem", javafx.event.ActionEvent.class).invoke(ctrl, e); }
            catch (NoSuchMethodException ns) { try { ctrl.getClass().getMethod("btnThem").invoke(ctrl); } catch (Exception ignored) {} }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        btnThemTaiKhoan.setOnAction(e -> {
            try { ctrl.getClass().getMethod("btnThemTaiKhoan", javafx.event.ActionEvent.class).invoke(ctrl, e); }
            catch (NoSuchMethodException ns) { try { ctrl.getClass().getMethod("btnThemTaiKhoan").invoke(ctrl); } catch (Exception ignored) {} }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        btnHuy.setOnAction(e -> {
            try { ctrl.getClass().getMethod("btnHuy", javafx.event.ActionEvent.class).invoke(ctrl, e); }
            catch (NoSuchMethodException ns) { // fallback: đóng Stage
                Stage s = (Stage) btnHuy.getScene().getWindow();
                if (s != null) s.close();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        // ---- Gọi initialize() thủ công (vì không có FXML lifecycle) ----
        try { ctrl.getClass().getMethod("initialize").invoke(ctrl); } catch (Exception ignored) {}

        // ---- Scene + CSS ----
        Scene scene = new Scene(root, 456, 273);
        scene.getStylesheets().add(requireCss("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhanVien.css"));

        // giống form “Sửa tài khoản”: set modality & show tại đây
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private AnchorPane buildUI() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(456, 273);
        root.setStyle("-fx-font-size: 14;");

        // Title
        Label lbTitle = new Label("Thêm nhân viên");
        lbTitle.setId("lbtitle");
        lbTitle.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 24));
        AnchorPane.setLeftAnchor(lbTitle, 14.0);
        AnchorPane.setTopAnchor(lbTitle, 8.0);

        // Icon
        ImageView imageView = new ImageView(new Image(requireRes(
                "/com/example/pharmacymanagementsystem_qlht/img/free-icon-person-and-gear-9847.png")));
        imageView.setFitWidth(41);
        imageView.setFitHeight(49);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        AnchorPane.setLeftAnchor(imageView, 200.0);
        AnchorPane.setTopAnchor(imageView, -2.0);

        // Separator
        Separator sep = new Separator();
        sep.setPrefSize(455, 3);
        AnchorPane.setLeftAnchor(sep, 0.0);
        AnchorPane.setTopAnchor(sep, 38.0);

        // VBox chính
        VBox vboxRoot = new VBox();
        vboxRoot.setPrefSize(431, 178);
        AnchorPane.setLeftAnchor(vboxRoot, 14.0);
        AnchorPane.setTopAnchor(vboxRoot, 36.0);

        VBox vboxInner = new VBox();
        vboxInner.setPrefSize(790, 193);

        Pane pane = new Pane();
        pane.setPrefSize(632, 178);

        // ==== Labels & controls ====
        Label lbTen = new Label("Tên nhân viên");
        lbTen.setLayoutX(3);  lbTen.setLayoutY(15);
        lbTen.setFont(Font.font(14));

        txtTenNV = new TextField();
        txtTenNV.setLayoutX(3); txtTenNV.setLayoutY(38);
        txtTenNV.setPrefSize(194, 25); txtTenNV.getStyleClass().add("tf");

        Label lbNgaySinh = new Label("Ngày sinh:");
        lbNgaySinh.setLayoutX(207); lbNgaySinh.setLayoutY(15);
        lbNgaySinh.setFont(Font.font(14));

        txtNgaySinh = new DatePicker();
        txtNgaySinh.setLayoutX(206); txtNgaySinh.setLayoutY(38);
        txtNgaySinh.setPrefSize(223, 25);

        Label lbSDT = new Label("Số điện thoại:");
        lbSDT.setLayoutX(3);  lbSDT.setLayoutY(70);
        lbSDT.setPrefSize(98, 20); lbSDT.setFont(Font.font(14));

        txtSDT = new TextField();
        txtSDT.setLayoutX(3); txtSDT.setLayoutY(94);
        txtSDT.setPrefSize(194, 25); txtSDT.getStyleClass().add("tf");

        Label lbEmail = new Label("Email:");
        lbEmail.setLayoutX(205); lbEmail.setLayoutY(69);
        lbEmail.setFont(Font.font(14));

        txtEmail = new TextField();
        txtEmail.setLayoutX(206); txtEmail.setLayoutY(95);
        txtEmail.setPrefSize(223, 25); txtEmail.getStyleClass().add("tf");

        Label lbDiaChi = new Label("Địa chỉ");
        lbDiaChi.setLayoutX(4);  lbDiaChi.setLayoutY(129);
        lbDiaChi.setPrefSize(55, 20); lbDiaChi.setFont(Font.font(14));

        txtDiaChi = new TextField();
        txtDiaChi.setLayoutX(4); txtDiaChi.setLayoutY(152);
        txtDiaChi.setPrefSize(267, 30); txtDiaChi.getStyleClass().add("tf");

        Label lbGioiTinh = new Label("Giới tính:");
        lbGioiTinh.setLayoutX(278); lbGioiTinh.setLayoutY(129);
        lbGioiTinh.setPrefSize(80, 20); lbGioiTinh.setFont(Font.font(14));

        cbxGioiTinh = new ComboBox<>();
        cbxGioiTinh.setLayoutX(278); cbxGioiTinh.setLayoutY(152);
        cbxGioiTinh.setPrefSize(150, 30);
        cbxGioiTinh.getItems().addAll("Chọn giới tính", "Nam", "Nữ");
        cbxGioiTinh.getSelectionModel().selectFirst();

        pane.getChildren().addAll(
                lbTen, txtTenNV,
                lbNgaySinh, txtNgaySinh,
                lbSDT, txtSDT,
                lbEmail, txtEmail,
                lbDiaChi, txtDiaChi,
                lbGioiTinh, cbxGioiTinh
        );

        vboxInner.getChildren().add(pane);
        vboxRoot.getChildren().add(vboxInner);

        // Buttons
        btnThemTaiKhoan = new Button("Thêm tài khoản");
        btnThemTaiKhoan.setPrefSize(131, 30);
        AnchorPane.setLeftAnchor(btnThemTaiKhoan, 168.0);
        AnchorPane.setTopAnchor(btnThemTaiKhoan, 229.0);
        btnThemTaiKhoan.setStyle("-fx-background-color: rgb(60,164,248);");

        btnHuy = new Button("Hủy");
        btnHuy.setPrefSize(55, 25);
        AnchorPane.setLeftAnchor(btnHuy, 306.0);
        AnchorPane.setTopAnchor(btnHuy, 229.0);
        btnHuy.setStyle("-fx-background-color: rgb(246,72,72);");

        btnThem = new Button("Thêm");
        btnThem.setPrefSize(73, 25);
        AnchorPane.setLeftAnchor(btnThem, 368.0);
        AnchorPane.setTopAnchor(btnThem, 229.0);
        btnThem.setStyle("-fx-background-color: rgb(122,239,102);");

        root.getChildren().addAll(lbTitle, sep, imageView, vboxRoot, btnThemTaiKhoan, btnHuy, btnThem);
        return root;
    }

    // ===== Helpers =====
    private void safeSet(Object ctrl, String field, Object value) {
        try { ctrl.getClass().getField(field).set(ctrl, value); } catch (Exception ignored) {}
    }
    private String requireCss(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Không tìm thấy CSS: " + path).toExternalForm();
    }
    private String requireRes(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Không tìm thấy resource: " + path).toExternalForm();
    }
}
