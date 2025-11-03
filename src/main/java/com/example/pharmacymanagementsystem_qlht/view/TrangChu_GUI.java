package com.example.pharmacymanagementsystem_qlht.view;

import com.example.pharmacymanagementsystem_qlht.controller.TrangChu_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.Objects;

public class TrangChu_GUI extends Application {

    @Override
    public void start(Stage stage) {
        // chạy thử độc lập (không controller)
        AnchorPane root = buildUI();
        Scene scene = new Scene(root, 1646, 895);
        addStyles(scene);
        stage.setTitle("Trang chủ");
        stage.setScene(scene);
        stage.show();
    }

    /** Dùng trong app: tạo control và truyền trực tiếp cho controller, KHÔNG lookup */
    public void showWithController(Stage stage, TrangChu_Ctrl ctrl) {
        ViewRefs v = buildUIForController();

        // Gắn thẳng field giống fx:id trong FXML cũ
        ctrl.chartDoanhThuThangNay = v.chartDoanhThuThangNay;

        ctrl.tblThuocHetHan       = v.tblThuocHetHan;
        ctrl.colMaThuocHetHan     = v.colMaThuocHetHan;
        ctrl.colLoHangHetHan      = v.colLoHangHetHan;
        ctrl.colHSDHetHan         = v.colHSDHetHan;

        ctrl.tblThuocSapHetHan    = v.tblThuocSapHetHan;
        ctrl.colMaThuocSapHetHan  = v.colMaThuocSapHetHan;
        ctrl.colLoHangSapHetHan   = v.colLoHangSapHetHan;
        ctrl.colHSDSapHetHan      = v.colHSDSapHetHan;

        ctrl.lbl_SoLuongHangHetHan    = v.lbl_SoLuongHangHetHan;
        ctrl.lbl_SoLuongHangSapHetHan = v.lbl_SoLuongHangSapHetHan;

        ctrl.lblDoanhThuThangTruoc = v.lblDoanhThuThangTruoc;
        ctrl.lblDoanhThuThangNay   = v.lblDoanhThuThangNay;

        // Nếu controller có initialize()
        try { ctrl.initialize(); } catch (Exception ignore) {}

        Scene scene = new Scene(v.root, 1646, 895);
        addStyles(scene);
        stage.setTitle("Trang chủ");
        stage.setScene(scene);
        stage.show();
    }

    // ================== UI cho test độc lập ==================
    private AnchorPane buildUI() {
        return buildUIForController().root;
    }

    // ================== Xây UI & trả về toàn bộ control cho controller ==================
    private ViewRefs buildUIForController() {
        ViewRefs v = new ViewRefs();

        // Root giống kích thước FXML
        v.root = new AnchorPane();
        v.root.setPrefSize(1646, 895);
        v.root.setStyle("-fx-font-size: 14;");

        // ===== GridPane chính (id=pnlGrid) =====
        GridPane pnlGrid = new GridPane();
        pnlGrid.setId("pnlGrid");
        pnlGrid.setPrefSize(1646, 895);

        // Cột
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.SOMETIMES);
        c1.setPrefWidth(1072.9047328404017);

        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.SOMETIMES);
        c2.setPrefWidth(573.6666957310267);

        pnlGrid.getColumnConstraints().addAll(c1, c2);

        // Hàng
        RowConstraints r1 = new RowConstraints();
        r1.setVgrow(Priority.SOMETIMES);
        r1.setPrefHeight(361.0);

        RowConstraints r2 = new RowConstraints();
        r2.setVgrow(Priority.SOMETIMES);
        r2.setPrefHeight(519.4285714285714);

        pnlGrid.getRowConstraints().addAll(r1, r2);

        // ================== Ô (0,1): Khối nhiệt độ/độ ẩm ==================
        Pane paneTopRight = new Pane();
        paneTopRight.setPrefSize(630, 395);

        Pane paneMain1 = new Pane();
        paneMain1.setId("paneMain");
        paneMain1.setLayoutX(31);
        paneMain1.setLayoutY(29);
        paneMain1.setPrefSize(509, 308);

        Label lbNhiet = boldLabel("35℃", 45, 127, 138);
        Label lbDoAm = boldLabel("50%", 45, 359, 137);
        Label lbTitleDieuKien = boldLabel("Điều kiện bảo quản", 35, 97, 24);

        ImageView ivNhiet = imageView("/com/example/pharmacymanagementsystem_qlht/img/nhietDo.png", 84, 130, 56, 107, true);
        ImageView ivDoAm  = imageView("/com/example/pharmacymanagementsystem_qlht/img/DoAm.png", 163, 179, 236, 84, true);

        paneMain1.getChildren().addAll(lbNhiet, lbDoAm, ivNhiet, ivDoAm, lbTitleDieuKien);
        paneTopRight.getChildren().add(paneMain1);
        GridPane.setColumnIndex(paneTopRight, 1);

        // ================== Ô (0,0) / row=1 col=0: Biểu đồ doanh thu tháng này ==================
        Pane paneChartWrap = new Pane();
        paneChartWrap.setPrefSize(959, 515);

        Pane paneMain2 = new Pane();
        paneMain2.setId("paneMain");
        paneMain2.setLayoutX(26);
        paneMain2.setLayoutY(27);
        paneMain2.setPrefSize(1013, 454);

        Label lbChartTitle = boldLabel("Doanh thu bán hàng tháng này", 35, 266, 19);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setSide(javafx.geometry.Side.BOTTOM);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setSide(javafx.geometry.Side.LEFT);

        v.chartDoanhThuThangNay = new LineChart<>(xAxis, yAxis);
        v.chartDoanhThuThangNay.setLayoutX(29);
        v.chartDoanhThuThangNay.setLayoutY(71);
        v.chartDoanhThuThangNay.setPrefSize(953, 340);

        Label lbVND = boldLabel("VND", 15, 12, 230);
        lbVND.setRotate(-90);

        Label lbNgay = boldLabel("Ngày", 15, 487, 411);

        paneMain2.getChildren().addAll(lbChartTitle, v.chartDoanhThuThangNay, lbVND, lbNgay);
        paneChartWrap.getChildren().add(paneMain2);
        GridPane.setRowIndex(paneChartWrap, 1);

        // ================== Ô (1,1): Cảnh báo hàng hóa (bảng) ==================
        Pane paneWarnWrap = new Pane();
        paneWarnWrap.setPrefSize(681, 521);

        Pane paneMain3 = new Pane();
        paneMain3.setId("paneMain");
        paneMain3.setLayoutX(31);
        paneMain3.setLayoutY(28);
        paneMain3.setPrefSize(509, 454);

        Label lbWarnTitle = boldLabel("Cảnh báo hàng hóa", 35, 135, 22);

        ImageView ivWarn = imageView("/com/example/pharmacymanagementsystem_qlht/img/canhBao.png", 74, 74, 41, 10, true);

        // Bảng: Hàng hết hạn
        v.tblThuocHetHan = new TableView<>();
        v.tblThuocHetHan.setLayoutX(18);
        v.tblThuocHetHan.setLayoutY(116);
        v.tblThuocHetHan.setPrefSize(473, 139);

        v.colMaThuocHetHan = new TableColumn<>("Mã thuốc");
        v.colMaThuocHetHan.setPrefWidth(148.00012588500977);

        v.colLoHangHetHan = new TableColumn<>("Lô hàng");
        v.colLoHangHetHan.setPrefWidth(173.142822265625);

        v.colHSDHetHan = new TableColumn<>("Hạn sử dụng");
        v.colHSDHetHan.setPrefWidth(155.99996948242188);

        v.tblThuocHetHan.getColumns().addAll(v.colMaThuocHetHan, v.colLoHangHetHan, v.colHSDHetHan);

        // Bảng: Hàng sắp hết hạn
        v.tblThuocSapHetHan = new TableView<>();
        v.tblThuocSapHetHan.setLayoutX(20);
        v.tblThuocSapHetHan.setLayoutY(291);
        v.tblThuocSapHetHan.setPrefSize(473, 141);

        v.colMaThuocSapHetHan = new TableColumn<>("Mã thuốc");
        v.colMaThuocSapHetHan.setPrefWidth(143.42859268188477);

        v.colLoHangSapHetHan = new TableColumn<>("Lô hàng");
        v.colLoHangSapHetHan.setPrefWidth(179.4285888671875);

        v.colHSDSapHetHan = new TableColumn<>("Hạn sử dụng");
        v.colHSDSapHetHan.setPrefWidth(151.42855834960938);

        v.tblThuocSapHetHan.getColumns().addAll(v.colMaThuocSapHetHan, v.colLoHangSapHetHan, v.colHSDSapHetHan);

        // Labels số lượng
        v.lbl_SoLuongHangHetHan = boldLabel("Hàng hết hạn: 0", 20, 18, 87);
        v.lbl_SoLuongHangSapHetHan = boldLabel("Hàng sắp hết hạn: 0", 20, 21, 262);

        paneMain3.getChildren().addAll(
                lbWarnTitle, v.tblThuocHetHan, v.tblThuocSapHetHan,
                v.lbl_SoLuongHangHetHan, v.lbl_SoLuongHangSapHetHan, ivWarn
        );
        paneWarnWrap.getChildren().add(paneMain3);
        GridPane.setColumnIndex(paneWarnWrap, 1);
        GridPane.setRowIndex(paneWarnWrap, 1);

        // ================== Grid nhỏ (trên hàng 0): Doanh thu + Hóa đơn ==================
        GridPane gridSmallTop = new GridPane();

        ColumnConstraints s1 = new ColumnConstraints();
        s1.setHgrow(Priority.SOMETIMES);
        s1.setPrefWidth(537.7619018554688);

        ColumnConstraints s2 = new ColumnConstraints();
        s2.setHgrow(Priority.SOMETIMES);
        s2.setPrefWidth(536.2380981445312);

        gridSmallTop.getColumnConstraints().addAll(s1, s2);
        gridSmallTop.getRowConstraints().add(new RowConstraints());
        // gridSmallTop mặc định ở (0,0)

        // --- Pane Doanh thu (trái)
        Pane paneDoanhThuWrap = new Pane();

        Pane paneMain4 = new Pane();
        paneMain4.setId("paneMain");
        paneMain4.setLayoutX(27);
        paneMain4.setLayoutY(27);
        paneMain4.setPrefSize(480, 308);

        Label lbDoanhThu = boldLabel("Doanh thu", 35, 176, 50);
        Label lbThangTruoc = boldLabel("Tháng trước:", 25, 62, 153);
        Label lbThangNay = boldLabel("Tháng này:", 25, 62, 202);

        ImageView ivTien = imageView("/com/example/pharmacymanagementsystem_qlht/img/Tien.png", 123, 123, 29, 14, true);

        v.lblDoanhThuThangTruoc = boldLabel("0 VND", 25, 231, 153);
        v.lblDoanhThuThangNay   = boldLabel("0 VND", 25, 231, 202);

        paneMain4.getChildren().addAll(lbDoanhThu, lbThangTruoc, lbThangNay, ivTien,
                v.lblDoanhThuThangTruoc, v.lblDoanhThuThangNay);
        paneDoanhThuWrap.getChildren().add(paneMain4);

        // --- Pane Hóa đơn (phải)
        Pane paneHoaDonWrap = new Pane();

        Pane paneMain5 = new Pane();
        paneMain5.setId("paneMain");
        paneMain5.setLayoutX(22);
        paneMain5.setLayoutY(28);
        paneMain5.setPrefSize(480, 308);

        Label lbHoaDon = boldLabel("Hóa đơn", 35, 190, 50);
        Label lbHDThangNay = boldLabel("Tháng này: ", 25, 64, 202);
        Label lbHDThangTruoc = boldLabel("Tháng trước: ", 25, 64, 153);

        ImageView ivHD = imageView("/com/example/pharmacymanagementsystem_qlht/img/hoaDon.png", 98, 105, 44, 26, true);

        // Hai label này không có fx:id trong FXML; chỉ hiển thị số.
        Label dummyHoaDonThangTruoc = boldLabel("0 hóa đơn", 25, 240, 153);
        Label dummyHoaDonThangNay   = boldLabel("0 hóa đơn", 25, 240, 202);

        paneMain5.getChildren().addAll(lbHoaDon, lbHDThangNay, lbHDThangTruoc, ivHD,
                dummyHoaDonThangTruoc, dummyHoaDonThangNay);
        paneHoaDonWrap.getChildren().add(paneMain5);

        gridSmallTop.add(paneDoanhThuWrap, 0, 0);
        gridSmallTop.add(paneHoaDonWrap, 1, 0);

        // ===== Thêm tất cả vào Grid chính =====
        pnlGrid.getChildren().addAll(gridSmallTop, paneTopRight, paneChartWrap, paneWarnWrap);

        // Neo Grid vào root
        AnchorPane.setTopAnchor(pnlGrid, 0.0);
        AnchorPane.setRightAnchor(pnlGrid, 0.0);
        AnchorPane.setBottomAnchor(pnlGrid, 0.0);
        AnchorPane.setLeftAnchor(pnlGrid, 0.0);

        v.root.getChildren().add(pnlGrid);
        return v;
    }

    private void addStyles(Scene scene) {
        // TrangChu.css
        var css = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/TrangChu.css"),
                "Không tìm thấy TrangChu.css"
        ).toExternalForm();
        scene.getStylesheets().add(css);
    }

    // ================== Helper UI ==================
    private static Label boldLabel(String text, double size, double x, double y) {
        Label lb = new Label(text);
        lb.setLayoutX(x);
        lb.setLayoutY(y);
        lb.setFont(Font.font("System Bold", size));
        return lb;
    }

    private static ImageView imageView(String resourcePath, double fitW, double fitH,
                                       double x, double y, boolean preserveRatio) {
        ImageView iv = new ImageView(new Image(Objects.requireNonNull(
                TrangChu_GUI.class.getResource(resourcePath),
                "Không tìm thấy ảnh: " + resourcePath
        ).toExternalForm()));
        iv.setFitWidth(fitW);
        iv.setFitHeight(fitH);
        iv.setLayoutX(x);
        iv.setLayoutY(y);
        iv.setPickOnBounds(true);
        iv.setPreserveRatio(preserveRatio);
        return iv;
    }

    // Gói control cho controller
    private static class ViewRefs {
        AnchorPane root;

        // Chart
        LineChart<String, Number> chartDoanhThuThangNay;

        // Bảng cảnh báo
        TableView<Thuoc_SP_TheoLo> tblThuocHetHan;
        TableColumn<Thuoc_SP_TheoLo, String> colMaThuocHetHan, colLoHangHetHan;
        TableColumn<Thuoc_SP_TheoLo, Date> colHSDHetHan;

        TableView<Thuoc_SP_TheoLo> tblThuocSapHetHan;
        TableColumn<Thuoc_SP_TheoLo, String> colMaThuocSapHetHan, colLoHangSapHetHan;
        TableColumn<Thuoc_SP_TheoLo, Date> colHSDSapHetHan;

        // Labels
        Label lbl_SoLuongHangHetHan, lbl_SoLuongHangSapHetHan;
        Label lblDoanhThuThangTruoc, lblDoanhThuThangNay;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
