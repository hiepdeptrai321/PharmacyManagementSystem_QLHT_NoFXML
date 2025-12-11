package com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuNhapHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc.ThemThuocBangFileExcel;
import com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuNhapHang.NhapHangBangExcel_Ctrl;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

/** UI thuần Java tương đương FXML ThemThuocBangFileExcel.fxml */
public class NhapHangBangExcel_GUI {

    // ==== Controls tương đương fx:id / id trong FXML ====
    public Label lblThongTinFile;
    public ImageView btnXoa;
    public Button btnTaiFileMau, btnChonTapTin, btnLuu;

    /** Hiển thị + inject controller + gắn handlers (thay cho onAction / onDragOver / onDragDropped trong FXML) */
    public void showWithController(Stage stage, NhapHangBangExcel_Ctrl ctrl) {
        AnchorPane root = buildUI();

        // --- inject sang controller nếu controller có field trùng tên ---
        try { ctrl.getClass().getField("lblThongTinFile").set(ctrl, lblThongTinFile); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("btnXoa").set(ctrl, btnXoa); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("btnTaiFileMau").set(ctrl, btnTaiFileMau); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("btnChonTapTin").set(ctrl, btnChonTapTin); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("btnLuu").set(ctrl, btnLuu); } catch (Exception ignored) {}

        // --- gắn event tương đương onAction / onMouseClicked / drag events ---
        btnTaiFileMau.setOnAction(ctrl::btnTaiTapTinMauClick);
        btnChonTapTin.setOnAction(ctrl::btnChonTapTinClick);
        btnLuu.setOnAction(ctrl::btnLuuClick);
        btnXoa.setOnMouseClicked(ctrl::btnXoaClick);

        // các handler kéo/thả trên Pane info
        Pane infoPane = (Pane) btnXoa.getParent(); // chính là Pane chứa ảnh cloud + label
        infoPane.setOnDragOver(ctrl::onDragOver);
        infoPane.setOnDragDropped(ctrl::thaFileDuLieu);

        // nếu controller có initialize() thì gọi
        try { ctrl.getClass().getMethod("initialize").invoke(ctrl); } catch (Exception ignored) {}

        // --- tạo Scene + gán CSS như trong FXML ---
        Scene scene = new Scene(root, 378, 353);
        scene.getStylesheets().add(requireCss("/com/example/pharmacymanagementsystem_qlht/css/ThemFileExcel.css"));
        // Pane info trong FXML cũng có stylesheets="@../../css/LapHoaDon.css"
        infoPane.getStylesheets().add(requireCss("/com/example/pharmacymanagementsystem_qlht/css/LapHoaDon.css"));

        stage.setScene(scene);
        stage.setTitle("Gửi tài liệu");
        stage.show();
    }

    private AnchorPane buildUI() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(378, 353);

        // Tiêu đề
        Label title = new Label("Gửi tài liệu");
        title.setLayoutX(117);
        title.setLayoutY(14);
        title.setFont(Font.font("System Bold", 25));

        // Nút "Tải tập tin mẫu"
        btnTaiFileMau = new Button("Tải tập tin mẫu");
        btnTaiFileMau.setId("btnTaiFileMau");
        btnTaiFileMau.setLayoutX(178);
        btnTaiFileMau.setLayoutY(304);
        btnTaiFileMau.setPrefSize(117, 29);

        // Nút "Chọn tập tin"
        btnChonTapTin = new Button("Chọn tập tin");
        btnChonTapTin.setId("btnChonTapTin");
        btnChonTapTin.setLayoutX(37);
        btnChonTapTin.setLayoutY(238);
        btnChonTapTin.setPrefSize(305, 36);

        // Pane thông tin (kéo/thả)
        Pane infoPane = new Pane();
        infoPane.setLayoutX(36);
        infoPane.setLayoutY(70);
        infoPane.setPrefSize(305, 158);
        infoPane.getStyleClass().add("info-pane"); // styleClass

        // Label hướng dẫn
        lblThongTinFile = new Label("Kéo & thả file vào đây");
        lblThongTinFile.setAlignment(Pos.CENTER);
        lblThongTinFile.setLayoutX(9);
        lblThongTinFile.setLayoutY(127);
        lblThongTinFile.setPrefSize(285, 17);

        // Icon cloud
        ImageView cloud = new ImageView(new Image(requireRes("/com/example/pharmacymanagementsystem_qlht/img/cloud-computing.png")));
        cloud.setFitHeight(102);
        cloud.setFitWidth(201);
        cloud.setLayoutX(101);
        cloud.setLayoutY(19);
        cloud.setPreserveRatio(true);
        cloud.setPickOnBounds(true);

        // Nút xóa (ảnh)
        btnXoa = new ImageView(new Image(requireRes("/com/example/pharmacymanagementsystem_qlht/img/delete.png")));
        btnXoa.setFitHeight(17);
        btnXoa.setFitWidth(17);
        btnXoa.setLayoutX(283);
        btnXoa.setLayoutY(11);
        btnXoa.setPreserveRatio(true);
        btnXoa.setPickOnBounds(true);

        infoPane.getChildren().addAll(lblThongTinFile, cloud, btnXoa);

        // Nút Lưu
        btnLuu = new Button("Lưu");
        btnLuu.setId("btnLuu");
        btnLuu.setLayoutX(305);
        btnLuu.setLayoutY(304);

        root.getChildren().addAll(title, btnTaiFileMau, btnChonTapTin, infoPane, btnLuu);
        return root;
    }

    // ==== helpers ====
    private String requireCss(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Không tìm thấy CSS: " + path).toExternalForm();
    }

    private String requireRes(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Không tìm thấy resource: " + path).toExternalForm();
    }
}
