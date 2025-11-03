package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.SuaXoaNhanVien_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.LuongNhanVien;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class SuaXoaNhanVien_GUI {

    // === Controls (trùng fx:id trong FXML) ===
    public TextField txtMaNV, txtTenNV, txtSDT, txtEmail, txtDiaChi, txtNgayBD, txtNgayKT, txtTim;
    public ComboBox<String> cbxGioiTinh, cbxTrangThai;
    public DatePicker txtNgaySinh;
    public Button btnLuu, btnXoa, btnHuy, btnSuaTaiKhoan, btnThayDoiLuong;
    public TableView<LuongNhanVien> tblLuongNV;
    public TableColumn<LuongNhanVien, String> colMaLuong, colTuNgay, colDenNgay, colGhiChu;
    public TableColumn<LuongNhanVien, Double> colLuongCoBan, colPhuCap;

    /** Hiển thị + inject controller + gán handlers + nạp dữ liệu nhân viên */
    public void showWithController(Stage stage, SuaXoaNhanVien_Ctrl ctrl, NhanVien nhanVien) {
        AnchorPane root = buildUI();

        // --- inject sang controller ---
        try { ctrl.getClass().getField("txtMaNV").set(ctrl, txtMaNV); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("txtTenNV").set(ctrl, txtTenNV); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("txtSDT").set(ctrl, txtSDT); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("txtEmail").set(ctrl, txtEmail); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("cbxGioiTinh").set(ctrl, cbxGioiTinh); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("txtDiaChi").set(ctrl, txtDiaChi); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("txtNgayBD").set(ctrl, txtNgayBD); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("txtNgayKT").set(ctrl, txtNgayKT); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("cbxTrangThai").set(ctrl, cbxTrangThai); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("txtNgaySinh").set(ctrl, txtNgaySinh); } catch (Exception ignored) {}

        try { ctrl.getClass().getField("tblLuongNV").set(ctrl, tblLuongNV); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colMaLuong").set(ctrl, colMaLuong); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colTuNgay").set(ctrl, colTuNgay); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colDenNgay").set(ctrl, colDenNgay); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colLuongCoBan").set(ctrl, colLuongCoBan); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colPhuCap").set(ctrl, colPhuCap); } catch (Exception ignored) {}
        try { ctrl.getClass().getField("colGhiChu").set(ctrl, colGhiChu); } catch (Exception ignored) {}

        // --- gán handlers như FXML onAction ---
        btnLuu.setOnAction(e -> {
            try { ctrl.getClass().getMethod("btnLuu", javafx.event.ActionEvent.class).invoke(ctrl, e); }
            catch (NoSuchMethodException ns) { try { ctrl.getClass().getMethod("btnLuu").invoke(ctrl); } catch (Exception ignored) {} }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        btnXoa.setOnAction(e -> {
            try { ctrl.getClass().getMethod("btnXoa", javafx.event.ActionEvent.class).invoke(ctrl, e); }
            catch (NoSuchMethodException ns) { try { ctrl.getClass().getMethod("btnXoa").invoke(ctrl); } catch (Exception ignored) {} }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        btnHuy.setOnAction(e -> {
            try { ctrl.getClass().getMethod("btnHuy", javafx.event.ActionEvent.class).invoke(ctrl, e); }
            catch (NoSuchMethodException ns) { try { ctrl.getClass().getMethod("btnHuy").invoke(ctrl); } catch (Exception ignored) {} }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        btnSuaTaiKhoan.setOnAction(e -> {
            try { ctrl.getClass().getMethod("btnSuaTaiKhoan", javafx.event.ActionEvent.class).invoke(ctrl, e); }
            catch (NoSuchMethodException ns) { try { ctrl.getClass().getMethod("btnSuaTaiKhoan").invoke(ctrl); } catch (Exception ignored) {} }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        btnThayDoiLuong.setOnAction(e -> {
            try { ctrl.getClass().getMethod("btnThayDoiLuong", javafx.event.ActionEvent.class).invoke(ctrl, e); }
            catch (NoSuchMethodException ns) { try { ctrl.getClass().getMethod("btnThayDoiLuong").invoke(ctrl); } catch (Exception ignored) {} }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        // --- gọi initialize() không tham số (nếu có), rồi nạp nhân viên cụ thể ---
        try { ctrl.getClass().getMethod("initialize").invoke(ctrl); } catch (Exception ignored) {}
        try { ctrl.getClass().getMethod("initialize", NhanVien.class).invoke(ctrl, nhanVien); } catch (Exception ignored) {}

        // --- Scene + CSS ---
        Scene scene = new Scene(root, 804, 582);
        scene.getStylesheets().add(requireCss("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhanVien.css"));

        stage.setScene(scene);
        stage.setTitle("Cập nhật nhân viên");
        stage.show();
    }

    private AnchorPane buildUI() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(804, 582);
        root.setStyle("-fx-font-size: 14;");

        // Title
        Label lbTitle = new Label("Cập nhật nhân viên");
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setLayoutX(14); lbTitle.setLayoutY(8);
        lbTitle.setPrefSize(226, 35);
        lbTitle.setFont(Font.font("System Bold", 24));

        // Icon cạnh title
        ImageView ivTitle = new ImageView(new Image(requireRes(
                "/com/example/pharmacymanagementsystem_qlht/img/free-icon-person-and-gear-9847.png")));
        ivTitle.setFitHeight(49); ivTitle.setFitWidth(41);
        ivTitle.setLayoutX(233); ivTitle.setLayoutY(-2);
        ivTitle.setPreserveRatio(true); ivTitle.setPickOnBounds(true);

        // Kẻ ngăn
        Separator sep = new Separator();
        sep.setLayoutY(38);
        sep.setPrefWidth(804);

        // Vùng thông tin trên
        VBox outer = new VBox(); outer.setLayoutX(14); outer.setLayoutY(36);
        outer.setPrefSize(790, 178);

        VBox box = new VBox(); box.setPrefSize(790, 193);
        Pane pane = new Pane(); pane.setPrefSize(790, 195);

        // Mã NV
        Label lbMa = new Label("Mã nhân viên:"); lbMa.setLayoutX(2); lbMa.setLayoutY(15); lbMa.setFont(Font.font(14));
        txtMaNV = new TextField(); txtMaNV.setDisable(true); txtMaNV.setLayoutX(2); txtMaNV.setLayoutY(38); txtMaNV.setPrefSize(315, 25);

        // Tên NV
        Label lbTen = new Label("Tên nhân viên"); lbTen.setLayoutX(325); lbTen.setLayoutY(15); lbTen.setFont(Font.font(14));
        txtTenNV = new TextField(); txtTenNV.setLayoutX(326); txtTenNV.setLayoutY(38); txtTenNV.setPrefSize(453, 25);

        // SDT
        Label lbSDT = new Label("Số điện thoại:"); lbSDT.setLayoutX(6); lbSDT.setLayoutY(70); lbSDT.setFont(Font.font(14));
        txtSDT = new TextField(); txtSDT.setLayoutX(2); txtSDT.setLayoutY(94); txtSDT.setPrefSize(315, 25);

        // Email
        Label lbEmail = new Label("Email:"); lbEmail.setLayoutX(325); lbEmail.setLayoutY(70); lbEmail.setFont(Font.font(14));
        txtEmail = new TextField(); txtEmail.setLayoutX(326); txtEmail.setLayoutY(96); txtEmail.setPrefSize(453, 25);

        // Giới tính + Địa chỉ
        Label lbGT = new Label("Giới tính:"); lbGT.setLayoutX(5); lbGT.setLayoutY(129); lbGT.setFont(Font.font(14));
        cbxGioiTinh = new ComboBox<>(); cbxGioiTinh.setLayoutX(2); cbxGioiTinh.setLayoutY(152); cbxGioiTinh.setPrefSize(182, 25);

        Label lbDC = new Label("Địa chỉ"); lbDC.setLayoutX(199); lbDC.setLayoutY(129); lbDC.setFont(Font.font(14));
        txtDiaChi = new TextField(); txtDiaChi.setLayoutX(199); txtDiaChi.setLayoutY(152); txtDiaChi.setPrefSize(579, 25);

        pane.getChildren().addAll(lbMa, txtMaNV, lbTen, txtTenNV, lbSDT, txtSDT, lbEmail, txtEmail, lbGT, cbxGioiTinh, lbDC, txtDiaChi);
        box.getChildren().add(pane);
        outer.getChildren().add(box);

        // Ngày sinh + Trạng thái
        Label lbNgaySinh = new Label("Ngày sinh:"); lbNgaySinh.setLayoutX(14); lbNgaySinh.setLayoutY(230); lbNgaySinh.setFont(Font.font(14));
        txtNgaySinh = new DatePicker(); txtNgaySinh.setLayoutX(14); txtNgaySinh.setLayoutY(258); txtNgaySinh.setPrefSize(249, 25);

        Label lbTrangThai = new Label("Trạng thái:"); lbTrangThai.setLayoutX(278); lbTrangThai.setLayoutY(230); lbTrangThai.setFont(Font.font(14));
        cbxTrangThai = new ComboBox<>(); cbxTrangThai.setLayoutX(279); cbxTrangThai.setLayoutY(258); cbxTrangThai.setPrefSize(157, 25);

        // Ngày vào/ra
        Label lbBD = new Label("Ngày vào làm:"); lbBD.setLayoutX(14); lbBD.setLayoutY(298); lbBD.setFont(Font.font(14));
        txtNgayBD = new TextField(); txtNgayBD.setDisable(true); txtNgayBD.setLayoutX(14); txtNgayBD.setLayoutY(323); txtNgayBD.setPrefSize(384, 25);

        Label lbKT = new Label("Ngày nghỉ làm:"); lbKT.setLayoutX(407); lbKT.setLayoutY(298); lbKT.setFont(Font.font(14));
        txtNgayKT = new TextField(); txtNgayKT.setDisable(true); txtNgayKT.setLayoutX(408); txtNgayKT.setLayoutY(323); txtNgayKT.setPrefSize(384, 25);

        // Bảng lương
        Label lbBangLuong = new Label("Bảng lương nhân viên:"); lbBangLuong.setLayoutX(14); lbBangLuong.setLayoutY(360); lbBangLuong.setFont(Font.font(14));

        tblLuongNV = new TableView<>(); tblLuongNV.setLayoutX(14); tblLuongNV.setLayoutY(386); tblLuongNV.setPrefSize(776, 147);
        colMaLuong = new TableColumn<>("Mã lương"); colMaLuong.setPrefWidth(102.33);
        colTuNgay = new TableColumn<>("Từ ngày"); colTuNgay.setPrefWidth(105.33);
        colDenNgay = new TableColumn<>("Đến ngày"); colDenNgay.setPrefWidth(116.0);
        colLuongCoBan = new TableColumn<>("Lương cơ bản"); colLuongCoBan.setPrefWidth(147.33);
        colPhuCap = new TableColumn<>("Phụ cấp"); colPhuCap.setPrefWidth(142.67);
        colGhiChu = new TableColumn<>("Ghi chú"); colGhiChu.setPrefWidth(163.33);
        tblLuongNV.getColumns().addAll(colMaLuong, colTuNgay, colDenNgay, colLuongCoBan, colPhuCap, colGhiChu);

        // Nút hành động
        btnXoa = new Button("Xóa"); btnXoa.setId("btnXoa"); btnXoa.setLayoutX(13); btnXoa.setLayoutY(546); btnXoa.setPrefSize(55, 25);
        btnThayDoiLuong = new Button("Thay đổi lương"); btnThayDoiLuong.setId("btnThemtk"); btnThayDoiLuong.setLayoutX(390); btnThayDoiLuong.setLayoutY(546); btnThayDoiLuong.setPrefSize(132, 30);
        btnSuaTaiKhoan = new Button("Sửa tài khoản"); btnSuaTaiKhoan.setId("btnThemtk"); btnSuaTaiKhoan.setLayoutX(532); btnSuaTaiKhoan.setLayoutY(546); btnSuaTaiKhoan.setPrefSize(116, 30);
        btnLuu = new Button("Lưu"); btnLuu.setId("btnLuu"); btnLuu.setLayoutX(717); btnLuu.setLayoutY(546); btnLuu.setPrefSize(73, 25);
        btnHuy = new Button("Hủy"); btnHuy.setId("btnHuy"); btnHuy.setLayoutX(655); btnHuy.setLayoutY(546); btnHuy.setPrefSize(55, 25);

        // Add all
        root.getChildren().addAll(
                lbTitle, ivTitle, sep,
                outer,
                lbNgaySinh, txtNgaySinh,
                lbTrangThai, cbxTrangThai,
                lbBD, txtNgayBD, lbKT, txtNgayKT,
                lbBangLuong, tblLuongNV,
                btnXoa, btnThayDoiLuong, btnSuaTaiKhoan, btnLuu, btnHuy
        );

        return root;
    }

    // Helpers
    private String requireCss(String path) {
        return Objects.requireNonNull(getClass().getResource(path),
                "Không tìm thấy CSS: " + path).toExternalForm();
    }

    private String requireRes(String path) {
        return Objects.requireNonNull(getClass().getResource(path),
                "Không tìm thấy resource: " + path).toExternalForm();
    }
}
