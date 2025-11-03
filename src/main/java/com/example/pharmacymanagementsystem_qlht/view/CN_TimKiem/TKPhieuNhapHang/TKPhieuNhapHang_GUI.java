package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuNhapHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuNhapHang.TimKiemPhieuNhap_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.PhieuNhap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class TKPhieuNhapHang_GUI extends Application {

    @Override
    public void start(Stage stage) {
        ViewRefs v = buildUIForController();
        Scene scene = new Scene(v.root, 1646, 895);
        addStyles(scene);
        stage.setTitle("Tìm kiếm phiếu nhập hàng");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Dùng trong app: tạo UI và GÁN trực tiếp vào controller (không lookup).
     */
    public void showWithController(Stage stage, TimKiemPhieuNhap_Ctrl ctrl) {
        ViewRefs v = buildUIForController();

        // ==== GÁN CONTROL về controller (đúng fx:id FXML) ====
        ctrl.cbxTimKiem = v.cbxTimKiem;
        ctrl.txtTimKiem = v.txtTimKiem;
        ctrl.tpBoLoc = v.tpBoLoc;

        ctrl.cbxChonNhaCC = v.cbxChonNhaCC;
        ctrl.chonNhanVien = v.chonNhanVien;
        ctrl.cboxTrangThai = v.cboxTrangThai;
        ctrl.txtNgayNhapMin = v.txtNgayNhapMin;
        ctrl.txtNgayNhapMax = v.txtNgayNhapMax;

        ctrl.tblPhieuNhap = v.tblPhieuNhap;
        ctrl.colMaPN = v.colMaPN;
        ctrl.colNgayNhap = v.colNgayNhap;
        ctrl.colNhaCungCap = v.colNhaCungCap;
        ctrl.colNhanVien = v.colNhanVien;
        ctrl.colGhiChu = v.colGhiChu;
        ctrl.colTrangThai = v.colTrangThai;
        ctrl.colChiTiet = v.colChiTiet;

        // Gán handler cho nút Xóa rỗng (onAction="#btnXoaRong")
        v.btnReset.setOnAction(ctrl::btnXoaRong);

        // Nếu controller có initialize()
        try {
            ctrl.initialize();
        } catch (Exception ignore) {
        }

        Scene scene = new Scene(v.root, 1646, 895);
        addStyles(scene);
        stage.setTitle("Tìm kiếm phiếu nhập hàng");
        stage.setScene(scene);
    }

    // ================== XÂY UI & GIỮ THAM CHIẾU ==================
    private ViewRefs buildUIForController() {
        ViewRefs v = new ViewRefs();

        // Root Pane
        v.root = new Pane();
        v.root.setPrefSize(1646, 895);
        v.root.setStyle("-fx-font-size: 14px;");

        // ===== Tiêu đề =====
        HBox hbTitle = new HBox();
        hbTitle.setLayoutX(10);
        hbTitle.setLayoutY(14);
        hbTitle.setPrefSize(1613, 53);

        Label lbTitle = new Label("Tìm kiếm phiếu nhập hàng");
        lbTitle.getStyleClass().add("title");
        lbTitle.setPrefSize(456, 53);
        lbTitle.setFont(Font.font(36));

        Region rg = new Region();
        rg.setPrefWidth(19);

        ImageView ivTitle = imageView("/com/example/pharmacymanagementsystem_qlht/img/bill-8854.png", 48, 46, true);

        hbTitle.getChildren().addAll(lbTitle, rg, ivTitle);

        Separator sp = new Separator();
        sp.setLayoutX(10);
        sp.setLayoutY(67);
        sp.setPrefWidth(1633);

        // ===== Thanh tìm kiếm nhanh =====
        v.cbxTimKiem = new ComboBox<>();
        v.cbxTimKiem.setLayoutX(21);
        v.cbxTimKiem.setLayoutY(79);
        v.cbxTimKiem.setPrefSize(179, 40);
        v.cbxTimKiem.setPromptText("Tìm theo");
        v.cbxTimKiem.getStyleClass().add("btntim");

        v.txtTimKiem = new TextField();
        v.txtTimKiem.setLayoutX(210);
        v.txtTimKiem.setLayoutY(79);
        v.txtTimKiem.setPrefSize(379, 40);
        v.txtTimKiem.setPromptText("Tìm kiếm");
        v.txtTimKiem.getStyleClass().add("tftim");

        v.btnReset = new Button();
        v.btnReset.setId("btnReset");
        v.btnReset.setLayoutX(601);
        v.btnReset.setLayoutY(79);
        v.btnReset.setPrefSize(52, 40);
        v.btnReset.getStyleClass().add("btntim");
        // Graphic refresh
        ImageView ivRefresh = imageView("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png", 34, 20, true);
        v.btnReset.setGraphic(ivRefresh);

        // ===== Khối chính (VBox) =====
        VBox vbMain = new VBox(8);
        vbMain.setLayoutX(20);
        vbMain.setLayoutY(126);
        vbMain.setPrefWidth(1613);

        // --- TitledPane: Bộ lọc bổ sung ---
        StackPane spFilterWrap = new StackPane();
        spFilterWrap.setPrefWidth(1613);
        v.tpBoLoc = new TitledPane();
        v.tpBoLoc.setText("Bộ lọc bổ sung");
        v.tpBoLoc.setAnimated(false);
        v.tpBoLoc.setExpanded(false);
        v.tpBoLoc.setPrefWidth(1613);

        AnchorPane apFilter = new AnchorPane();
        apFilter.setMinHeight(50);
        apFilter.setPrefSize(1611, 50);

        v.cbxChonNhaCC = new ComboBox<>();
        v.cbxChonNhaCC.setLayoutY(7);
        v.cbxChonNhaCC.setPrefSize(302, 44);
        v.cbxChonNhaCC.setPromptText("Chọn nhà cung cấp");

        v.chonNhanVien = new ComboBox<>();
        v.chonNhanVien.setLayoutX(318);
        v.chonNhanVien.setLayoutY(7);
        v.chonNhanVien.setPrefSize(347, 44);
        v.chonNhanVien.setPromptText("Chọn nhân viên");

        Label lbTrangThai = new Label("Trạng thái nhập hàng:");
        lbTrangThai.setLayoutX(693);
        lbTrangThai.setLayoutY(9);
        lbTrangThai.getStyleClass().add("tftim");
        lbTrangThai.setPrefSize(136, 37);

        v.cboxTrangThai = new CheckBox("Hoàn thành");
        v.cboxTrangThai.setLayoutX(841);
        v.cboxTrangThai.setLayoutY(18);

        Label lbTu = new Label("Từ:");
        lbTu.setLayoutX(991);
        lbTu.setLayoutY(7);
        lbTu.getStyleClass().add("tftim");
        lbTu.setPrefSize(27, 37);

        v.txtNgayNhapMin = new DatePicker();
        v.txtNgayNhapMin.setLayoutX(1027);
        v.txtNgayNhapMin.setLayoutY(7);
        v.txtNgayNhapMin.setPrefSize(136, 37);
        v.txtNgayNhapMin.getStyleClass().add("tftim");

        Label lbDen = new Label("Đến:");
        lbDen.setLayoutX(1181);
        lbDen.setLayoutY(7);
        lbDen.getStyleClass().add("tftim");
        lbDen.setPrefSize(33, 37);

        v.txtNgayNhapMax = new DatePicker();
        v.txtNgayNhapMax.setLayoutX(1214);
        v.txtNgayNhapMax.setLayoutY(7);
        v.txtNgayNhapMax.setPrefSize(136, 37);
        v.txtNgayNhapMax.getStyleClass().add("tftim");

        apFilter.getChildren().addAll(
                v.cbxChonNhaCC, v.chonNhanVien, lbTrangThai, v.cboxTrangThai,
                lbTu, v.txtNgayNhapMin, lbDen, v.txtNgayNhapMax
        );
        v.tpBoLoc.setContent(apFilter);
        spFilterWrap.getChildren().add(v.tpBoLoc);

        // --- Bảng dữ liệu ---
        v.tblPhieuNhap = new TableView<>();
        v.tblPhieuNhap.setPrefSize(1613, 707);
        VBox.setVgrow(v.tblPhieuNhap, Priority.ALWAYS);

        v.colMaPN = new TableColumn<>("Mã phiếu nhập");
        v.colMaPN.setPrefWidth(176);
        v.colMaPN.setStyle("-fx-alignment: CENTER;");

        v.colNgayNhap = new TableColumn<>("Ngày nhập");
        v.colNgayNhap.setPrefWidth(217);
        v.colNgayNhap.setStyle("-fx-alignment: CENTER;");

        v.colNhaCungCap = new TableColumn<>("Nhà cung cấp");
        v.colNhaCungCap.setPrefWidth(259);

        v.colNhanVien = new TableColumn<>("Nhân viên");
        v.colNhanVien.setPrefWidth(236);

        v.colGhiChu = new TableColumn<>("Ghi chú");
        v.colGhiChu.setPrefWidth(355);

        v.colTrangThai = new TableColumn<>("Trạng thái");
        v.colTrangThai.setPrefWidth(259);
        v.colTrangThai.setStyle("-fx-alignment: CENTER;");

        v.colChiTiet = new TableColumn<>("");
        v.colChiTiet.setPrefWidth(93);
        v.colChiTiet.setStyle("-fx-alignment: CENTER;");

        v.tblPhieuNhap.getColumns().addAll(
                v.colMaPN, v.colNgayNhap, v.colNhaCungCap, v.colNhanVien,
                v.colGhiChu, v.colTrangThai, v.colChiTiet
        );

        vbMain.getChildren().addAll(spFilterWrap, v.tblPhieuNhap);

        // ===== Add tất cả vào root =====
        v.root.getChildren().addAll(hbTitle, sp, v.cbxTimKiem, v.txtTimKiem, v.btnReset, vbMain);
        return v;
    }

    private void addStyles(Scene scene) {
        // TimKiemHoaDon.css
        var css1 = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css"),
                "Không tìm thấy css/TimKiemHoaDon.css"
        ).toExternalForm();
        scene.getStylesheets().add(css1);

        // (nút reset tham chiếu thêm QuanLyThuoc.css trong FXML)
        var css2 = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css"),
                "Không tìm thấy css/QuanLyThuoc.css"
        ).toExternalForm();
        scene.getStylesheets().add(css2);
    }

    private static ImageView imageView(String resource, double fitW, double fitH, boolean preserveRatio) {
        ImageView iv = new ImageView(new Image(Objects.requireNonNull(
                TKPhieuNhapHang_GUI.class.getResource(resource),
                "Không tìm thấy ảnh: " + resource
        ).toExternalForm()));
        iv.setFitWidth(fitW);
        iv.setFitHeight(fitH);
        iv.setPreserveRatio(preserveRatio);
        iv.setPickOnBounds(true);
        return iv;
    }

    // Giữ tham chiếu control cho controller
    private static class ViewRefs {
        Pane root;

        ComboBox<String> cbxTimKiem;
        TextField txtTimKiem;
        Button btnReset;

        TitledPane tpBoLoc;
        ComboBox<String> cbxChonNhaCC;
        ComboBox<String> chonNhanVien;
        CheckBox cboxTrangThai;
        DatePicker txtNgayNhapMin, txtNgayNhapMax;

        TableView<PhieuNhap> tblPhieuNhap;
        TableColumn<PhieuNhap, String> colMaPN, colNgayNhap, colNhaCungCap, colNhanVien, colGhiChu, colTrangThai;
        TableColumn<PhieuNhap, String> colChiTiet; // để neutral; đổi generic theo model thực tế
    }

    public static void main(String[] args) {
        launch(args);
    }
}
