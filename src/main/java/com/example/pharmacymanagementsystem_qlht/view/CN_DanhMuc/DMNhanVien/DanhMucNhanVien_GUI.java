package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.DanhMucNhanVien_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

/** UI thuần Java tương đương DanhMucNhanVien.fxml */
@SuppressWarnings("unchecked")
public class DanhMucNhanVien_GUI {

    // ==== Controls trùng fx:id trong FXML ====
    public TextField txtTim;
    public Button btnTim, btnLamMoi, btnThemNhanVien;
    public TableView<NhanVien> tblNhanVien;
    public TableColumn<NhanVien, String> colSTT, colMaNV, colTenNV, colGioiTinh, colSDT, colNgaySinh, colEmail, colDiaChi, colTrangThai, colCapNhat;

    /** Hiển thị + inject controller + gắn handlers */
    public void showWithController(Stage stage, DanhMucNhanVien_Ctrl ctrl) {
        AnchorPane root = buildUI();

        // ---- inject fields sang controller (nếu controller khai báo public) ----
        try { ctrl.getClass().getField("txtTim").set(ctrl, txtTim); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("btnTim").set(ctrl, btnTim); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("btnLamMoi").set(ctrl, btnLamMoi); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("tblNhanVien").set(ctrl, tblNhanVien); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colSTT").set(ctrl, colSTT); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colMaNV").set(ctrl, colMaNV); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colTenNV").set(ctrl, colTenNV); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colGioiTinh").set(ctrl, colGioiTinh); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colSDT").set(ctrl, colSDT); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colNgaySinh").set(ctrl, colNgaySinh); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colEmail").set(ctrl, colEmail); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colDiaChi").set(ctrl, colDiaChi); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colTrangThai").set(ctrl, colTrangThai); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colCapNhat").set(ctrl, colCapNhat); } catch (Exception ignored) {}

        // ---- gắn handler tương đương onAction="#btnThemNhanVien" ----
        btnThemNhanVien.setOnAction(e -> {
            try {
                ctrl.getClass().getMethod("btnThemNhanVien", javafx.event.ActionEvent.class).invoke(ctrl, e);
            } catch (NoSuchMethodException ns) {
                // fallback: thử method không tham số
                try { ctrl.getClass().getMethod("btnThemNhanVien").invoke(ctrl); } catch (Exception ignored) {}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        btnTim.setOnAction(e -> {
            try { ctrl.getClass().getMethod("timNhanVien", javafx.event.ActionEvent.class).invoke(ctrl, e); }
            catch (NoSuchMethodException ns) { try { ctrl.getClass().getMethod("timNhanVien").invoke(ctrl); } catch (Exception ignored) {} }
            catch (Exception ex) { ex.printStackTrace(); }
        });
        btnLamMoi.setOnAction(e -> {
            try { ctrl.getClass().getMethod("LamMoi", javafx.event.ActionEvent.class).invoke(ctrl, e); }
            catch (NoSuchMethodException ns) { try { ctrl.getClass().getMethod("LamMoi").invoke(ctrl); } catch (Exception ignored) {} }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        // Gọi initialize() nếu controller có
        try { ctrl.getClass().getMethod("initialize").invoke(ctrl); } catch (Exception ignored) {}

        // ---- Scene + CSS ----
        Scene scene = new Scene(root, 1646, 895);
        scene.getStylesheets().add(requireCss("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css"));
        stage.setScene(scene);
        stage.setTitle("Danh mục nhân viên");
    }

    private AnchorPane buildUI() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14;");

        // Ô tìm kiếm
        txtTim = new TextField();
        txtTim.setId("txtTim");
        txtTim.setLayoutX(12); txtTim.setLayoutY(47);
        txtTim.setPrefSize(766, 40);
        txtTim.setPromptText("Tìm theo mã, tên nhân viên");

        // Nút tìm
        btnTim = new Button("🔍 Tìm");
        btnTim.setId("btntim");
        btnTim.setLayoutX(790); btnTim.setLayoutY(47);
        btnTim.setPrefSize(69, 30);

        // Pane title
        Pane paneTitle = new Pane();
        paneTitle.setId("lblpaneTitle");
        paneTitle.setPrefSize(1632, 40);

        Label lbTitle = new Label("Danh mục nhân viên");
        lbTitle.setId("lbtitle");
        lbTitle.setLayoutX(12);
        lbTitle.setLayoutY(2);
        lbTitle.setPrefSize(350, 36);
        lbTitle.setStyle("-fx-font-size: 36;");

        ImageView ivOfficer = new ImageView(new Image(requireRes(
                "/com/example/pharmacymanagementsystem_qlht/img/officer.png")));
        ivOfficer.setFitHeight(36); ivOfficer.setFitWidth(40);
        ivOfficer.setLayoutX(350); ivOfficer.setLayoutY(2);
        ivOfficer.setPreserveRatio(true); ivOfficer.setPickOnBounds(true);

        paneTitle.getChildren().addAll(lbTitle, ivOfficer);

        // Nút Thêm nhân viên
        btnThemNhanVien = new Button("✚Thêm nhân viên");
        btnThemNhanVien.setId("btnthemthuoc");
        btnThemNhanVien.setLayoutX(1488); btnThemNhanVien.setLayoutY(47);
        btnThemNhanVien.setPrefSize(144, 30);

        // Nút Làm mới (refresh)
        btnLamMoi = new Button();
        btnLamMoi.setId("btnReset");
        btnLamMoi.setLayoutX(874); btnLamMoi.setLayoutY(47);
        btnLamMoi.setPrefSize(47, 39);
        ImageView ivRefresh = new ImageView(new Image(requireRes(
                "/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")));
        ivRefresh.setFitWidth(34); ivRefresh.setFitHeight(21);
        ivRefresh.setPreserveRatio(true); ivRefresh.setPickOnBounds(true);
        btnLamMoi.setGraphic(ivRefresh);

        // Bảng nhân viên
        tblNhanVien = new TableView<>();
        tblNhanVien.setId("tablethuoc");
        tblNhanVien.setLayoutX(12); tblNhanVien.setLayoutY(98);
        tblNhanVien.setPrefSize(1624, 789);

        colSTT       = new TableColumn<>("STT");            ((TableColumn<NhanVien, ?>) colSTT).setPrefWidth(48.8333);  colSTT.setStyle("-fx-alignment: CENTER;");
        colMaNV      = new TableColumn<>("Mã nhân viên");  ((TableColumn<NhanVien, ?>) colMaNV).setPrefWidth(125);      colMaNV.setStyle("-fx-alignment: CENTER;");
        colTenNV     = new TableColumn<>("Tên nhân viên"); ((TableColumn<NhanVien, ?>) colTenNV).setPrefWidth(224);
        colGioiTinh  = new TableColumn<>("Giới tính");     ((TableColumn<NhanVien, ?>) colGioiTinh).setPrefWidth(134);
        colSDT       = new TableColumn<>("SDT");           ((TableColumn<NhanVien, ?>) colSDT).setPrefWidth(191);       colSDT.setStyle("-fx-alignment: CENTER;");
        colNgaySinh  = new TableColumn<>("Ngày sinh");     ((TableColumn<NhanVien, ?>) colNgaySinh).setPrefWidth(197);  colNgaySinh.setStyle("-fx-alignment: CENTER;");
        colEmail     = new TableColumn<>("Email");         ((TableColumn<NhanVien, ?>) colEmail).setPrefWidth(236);
        colDiaChi    = new TableColumn<>("Địa chỉ");       ((TableColumn<NhanVien, ?>) colDiaChi).setPrefWidth(210);
        colTrangThai = new TableColumn<>("Trạng thái");    ((TableColumn<NhanVien, ?>) colTrangThai).setPrefWidth(135);
        colCapNhat   = new TableColumn<>("Cập nhật");      ((TableColumn<NhanVien, ?>) colCapNhat).setPrefWidth(108);   colCapNhat.setStyle("-fx-alignment: CENTER;");

        tblNhanVien.getColumns().addAll(
                colSTT, colMaNV, colTenNV, colGioiTinh, colSDT, colNgaySinh, colEmail, colDiaChi, colTrangThai, colCapNhat
        );

        // Ảnh rỗng phía cuối (như FXML có 1 ImageView rỗng)
        ImageView emptyIv = new ImageView();
        emptyIv.setFitWidth(200); emptyIv.setFitHeight(150);
        emptyIv.setPreserveRatio(true); emptyIv.setPickOnBounds(true);

        // Add all
        root.getChildren().addAll(
                txtTim, btnTim, paneTitle, btnThemNhanVien, tblNhanVien, btnLamMoi, emptyIv
        );
        return root;
    }

    // ==== Helpers ====
    private String requireCss(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Không tìm thấy CSS: " + path).toExternalForm();
    }

    private String requireRes(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Không tìm thấy resource: " + path).toExternalForm();
    }
}
