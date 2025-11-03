package com.example.pharmacymanagementsystem_qlht.view;

import com.example.pharmacymanagementsystem_qlht.controller.CuaSoChinh_NhanVien_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class CuaSoChinh_NhanVien_GUI extends Application {

    @Override
    public void start(Stage stage) {
        // chạy test độc lập (không controller)
        ViewRefs v = buildUIForController();
        Scene scene = new Scene(v.root, 1646, 1029);
        addStyles(scene);
        stage.setTitle("Cửa sổ chính – Nhân viên");
        stage.setScene(scene);
        stage.show();
    }

    /** Dùng trong app: KHÔNG lookup – tạo control và gán thẳng cho controller */
    public void showWithController(Stage stage, CuaSoChinh_NhanVien_Ctrl ctrl) {
        ViewRefs v = buildUIForController();

        // ===== GÁN CONTROL VỀ CONTROLLER (đúng fx:id như FXML) =====
        ctrl.pnlChung = v.pnlChung;

        ctrl.menuTimKiem = v.menuTimKiem;
        ctrl.menuDanhMuc = v.menuDanhMuc;
        ctrl.menuCapNhat = v.menuCapNhat;
        ctrl.menuXuLy    = v.menuXuLy;

        ctrl.txtNgayThangNam = v.txtNgayThangNam;
        ctrl.txtNguoiDung    = v.txtNguoiDung;

        ctrl.pnlThongTin = v.pnlThongTin;
        ctrl.lblVaiTro   = v.lblVaiTro;

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

        ctrl.lblHoaDonThangTruoc = v.lblHoaDonThangTruoc;
        ctrl.lblHoaDonThangNay   = v.lblHoaDonThangNay;

        // ===== GẮN HANDLER SANG HÀM CỦA CONTROLLER (theo onAction/onMouseClicked FXML) =====
        v.miTK_HoaDon       .setOnAction(ctrl::timKiemHoaDon);
        v.miTK_PhieuNhap    .setOnAction(ctrl::timKiemPhieuNhap);
        v.miTK_PhieuDoiHang .setOnAction(ctrl::timKiemPhieuDoiHang);
        v.miTK_PhieuTraHang .setOnAction(ctrl::timKiemPhieuTraHang);
        v.miTK_PhieuDatHang .setOnAction(ctrl::timKiemPhieuDatHang);
        v.miTK_NCC          .setOnAction(ctrl::timKiemNhaCungCap);
        v.miTK_Thuoc        .setOnAction(ctrl::timKiemThuoc);
        v.miTK_KhachHang    .setOnAction(ctrl::timKiemKhachHang);

        v.miDM_Thuoc        .setOnAction(ctrl::danhMucThuoc);
        v.miDM_KhachHang    .setOnAction(ctrl::danhMucKhachHang);
        v.miDM_KeHang       .setOnAction(ctrl::danhMucKeHang);
        v.miDM_NCC          .setOnAction(ctrl::danhMucNhaCungCap);
        v.miDM_NhomDuocLy   .setOnAction(ctrl::danhMucNhomDuocLy);

        v.miCN_GiaBan       .setOnAction(ctrl::CapNhatGiaBan);
        v.miCN_TonKho       .setOnAction(ctrl::capNhatTonKho);

        v.miXL_LapHoaDon    .setOnAction(ctrl::lapHoaDon);
        v.miXL_LapPhieuDoi  .setOnAction(ctrl::lapPhieuDoiHang);
        v.miXL_LapPhieuTra  .setOnAction(ctrl::lapPhieuTraHang);
        v.miXL_LapPhieuDat  .setOnAction(ctrl::lapPhieuDatHang);
        v.miXL_NhapHang     .setOnAction(ctrl::nhapHang);

        v.imgLogo.setOnMouseClicked(ctrl::AnhChuyenTrangChu);
        v.pnlTenNguoiDung.setOnMouseClicked(ctrl::pnlNguoiDungClick);
        v.btnDangXuat.setOnAction(ctrl::btnDangXuatClick);

        // ===== initialize() của controller (nếu có) =====
        try { ctrl.initialize(); } catch (Exception ignore) {}

        Scene scene = new Scene(v.root, 1646, 1029);
        addStyles(scene);
        stage.setTitle("Cửa sổ chính – Nhân viên");
        stage.setScene(scene);
    }

    // ===================== XÂY UI & GIỮ THAM CHIẾU CONTROL =====================
    private ViewRefs buildUIForController() {
        ViewRefs v = new ViewRefs();

        // Root
        v.root = new Pane();
        v.root.setId("main");
        v.root.setPrefSize(1646, 1029);

        AnchorPane ap = new AnchorPane();
        ap.setPrefSize(1646, 1029);

        // ====== MENU BAR ======
        MenuBar menuBar = new MenuBar();
        menuBar.setId("menuBar");
        menuBar.setLayoutY(86);
        menuBar.setPrefSize(1646, 50);
        menuBar.getStyleClass().add("menu");

        // -- Menu Tìm kiếm
        v.menuTimKiem = new Menu("⌕ Tìm kiếm");
        v.miTK_HoaDon       = new MenuItem("Tìm hóa đơn");
        v.miTK_PhieuNhap    = new MenuItem("Tìm phiếu nhập hàng");
        v.miTK_PhieuDoiHang = new MenuItem("Tìm phiếu đổi hàng");
        v.miTK_PhieuTraHang = new MenuItem("Tìm phiếu trả hàng");
        v.miTK_PhieuDatHang = new MenuItem("Tìm phiếu đặt hàng");
        v.miTK_NCC          = new MenuItem("Tìm nhà cung cấp");
        v.miTK_Thuoc        = new MenuItem("Tìm thuốc");
        v.miTK_KhachHang    = new MenuItem("Tìm khách hàng");
        v.menuTimKiem.getItems().addAll(
                v.miTK_HoaDon, v.miTK_PhieuNhap, v.miTK_PhieuDoiHang, v.miTK_PhieuTraHang,
                v.miTK_PhieuDatHang, v.miTK_NCC, v.miTK_Thuoc, v.miTK_KhachHang
        );

        // -- Menu Danh mục
        v.menuDanhMuc = new Menu("📁 Danh mục");
        v.miDM_Thuoc      = new MenuItem("Danh mục thuốc");
        v.miDM_KhachHang  = new MenuItem("Danh mục khách hàng");
        v.miDM_KeHang     = new MenuItem("Danh mục kệ hàng");
        v.miDM_NCC        = new MenuItem("Danh mục nhà cung cấp");
        v.miDM_NhomDuocLy = new MenuItem("Danh mục nhóm dược lý");
        v.menuDanhMuc.getItems().addAll(
                v.miDM_Thuoc, v.miDM_KhachHang, v.miDM_KeHang, v.miDM_NCC, v.miDM_NhomDuocLy
        );

        // -- Menu Cập nhật
        v.menuCapNhat = new Menu("🔄 Cập nhật");
        v.miCN_GiaBan = new MenuItem("Cập nhật giá bán");
        v.miCN_TonKho = new MenuItem("Cập nhật tồn kho");
        v.menuCapNhat.getItems().addAll(v.miCN_GiaBan, v.miCN_TonKho);

        // -- Menu Xử lý
        v.menuXuLy = new Menu("🛠 Xử lý");
        Menu mnBanHang = new Menu("Bán hàng");
        v.miXL_LapHoaDon   = new MenuItem("Lập hóa đơn");
        v.miXL_LapPhieuDoi = new MenuItem("Lập phiếu đổi hàng");
        v.miXL_LapPhieuTra = new MenuItem("Lập phiếu trả hàng");
        v.miXL_LapPhieuDat = new MenuItem("Lập phiếu đặt hàng");
        mnBanHang.getItems().addAll(v.miXL_LapHoaDon, v.miXL_LapPhieuDoi, v.miXL_LapPhieuTra, v.miXL_LapPhieuDat);
        v.miXL_NhapHang = new MenuItem("Nhập hàng");
        v.menuXuLy.getItems().addAll(mnBanHang, v.miXL_NhapHang);

        menuBar.getMenus().addAll(v.menuTimKiem, v.menuDanhMuc, v.menuCapNhat, v.menuXuLy);

        // ====== pnlChung (Pane) + Dash Grid bên trong ======
        v.pnlChung = new Pane();
        v.pnlChung.setLayoutY(137);

        GridPane pnlGrid = new GridPane();
        pnlGrid.setId("pnlGrid");
        pnlGrid.setPrefSize(1646, 895);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.SOMETIMES);
        c1.setPrefWidth(1072.9047328404017);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.SOMETIMES);
        c2.setPrefWidth(573.6666957310267);
        pnlGrid.getColumnConstraints().addAll(c1, c2);

        RowConstraints r1 = new RowConstraints();
        r1.setVgrow(Priority.SOMETIMES);
        r1.setPrefHeight(361.0);
        RowConstraints r2 = new RowConstraints();
        r2.setVgrow(Priority.SOMETIMES);
        r2.setPrefHeight(519.4285714285714);
        pnlGrid.getRowConstraints().addAll(r1, r2);

        // ---- (col=1,row=0): Điều kiện bảo quản
        Pane topRight = new Pane();
        Pane paneMain1 = new Pane();
        paneMain1.setId("paneMain");
        paneMain1.setLayoutX(31);
        paneMain1.setLayoutY(29);
        paneMain1.setPrefSize(509, 308);

        Label lbNhiet = bold("35℃", 45, 127, 138);
        Label lbDoAm  = bold("50%", 45, 359, 137);
        Label lbDKBQ  = bold("Điều kiện bảo quản", 35, 97, 24);

        ImageView ivNhiet = imageView("/com/example/pharmacymanagementsystem_qlht/img/nhietDo.png", 84, 130, 56, 107, true);
        ImageView ivDoAm  = imageView("/com/example/pharmacymanagementsystem_qlht/img/DoAm.png", 163, 179, 236, 84, true);
        paneMain1.getChildren().addAll(lbNhiet, lbDoAm, ivNhiet, ivDoAm, lbDKBQ);
        topRight.getChildren().add(paneMain1);
        GridPane.setColumnIndex(topRight, 1);

        // ---- (col=0,row=1): Biểu đồ doanh thu
        Pane chartWrap = new Pane();
        Pane paneMain2 = new Pane();
        paneMain2.setId("paneMain");
        paneMain2.setLayoutX(26);
        paneMain2.setLayoutY(27);
        paneMain2.setPrefSize(1013, 454);

        Label lbTitleChart = bold("Doanh thu bán hàng tháng này", 35, 266, 19);
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis   yAxis = new NumberAxis();
        v.chartDoanhThuThangNay = new LineChart<>(xAxis, yAxis);
        v.chartDoanhThuThangNay.setLayoutX(29);
        v.chartDoanhThuThangNay.setLayoutY(71);
        v.chartDoanhThuThangNay.setPrefSize(953, 340);
        Label lbVND  = bold("VND", 15, 12, 230); lbVND.setRotate(-90);
        Label lbNgay = bold("Ngày", 15, 487, 411);
        paneMain2.getChildren().addAll(lbTitleChart, v.chartDoanhThuThangNay, lbVND, lbNgay);
        chartWrap.getChildren().add(paneMain2);
        GridPane.setRowIndex(chartWrap, 1);

        // ---- (col=1,row=1): Cảnh báo hàng hóa
        Pane warnWrap = new Pane();
        Pane paneMain3 = new Pane();
        paneMain3.setId("paneMain");
        paneMain3.setLayoutX(31);
        paneMain3.setLayoutY(28);
        paneMain3.setPrefSize(509, 454);

        Label lbWarn = bold("Cảnh báo hàng hóa", 35, 135, 22);
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

        v.lbl_SoLuongHangHetHan    = bold("Hàng hết hạn: 0", 20, 18, 87);
        v.lbl_SoLuongHangSapHetHan = bold("Hàng sắp hết hạn: 0", 20, 21, 262);

        ImageView ivWarn = imageView("/com/example/pharmacymanagementsystem_qlht/img/canhBao.png", 74, 74, 41, 10, true);

        paneMain3.getChildren().addAll(
                lbWarn, v.tblThuocHetHan, v.tblThuocSapHetHan,
                v.lbl_SoLuongHangHetHan, v.lbl_SoLuongHangSapHetHan, ivWarn
        );
        warnWrap.getChildren().add(paneMain3);
        GridPane.setColumnIndex(warnWrap, 1);
        GridPane.setRowIndex(warnWrap, 1);

        // ---- Grid nhỏ trên cùng: Doanh thu + Hóa đơn
        GridPane smallTop = new GridPane();
        ColumnConstraints s1 = new ColumnConstraints(); s1.setHgrow(Priority.SOMETIMES); s1.setPrefWidth(537.7619018554688);
        ColumnConstraints s2 = new ColumnConstraints(); s2.setHgrow(Priority.SOMETIMES); s2.setPrefWidth(536.2380981445312);
        smallTop.getColumnConstraints().addAll(s1, s2);
        smallTop.getRowConstraints().add(new RowConstraints());

        // Doanh thu
        Pane pLeft = new Pane();
        Pane pm4 = new Pane(); pm4.setId("paneMain"); pm4.setLayoutX(27); pm4.setLayoutY(27); pm4.setPrefSize(480, 308);
        Label lbDT = bold("Doanh thu", 35, 176, 50);
        Label lbTruoc = bold("Tháng trước:", 25, 62, 153);
        Label lbNay   = bold("Tháng này:",   25, 62, 202);
        ImageView ivTien = imageView("/com/example/pharmacymanagementsystem_qlht/img/Tien.png", 123, 123, 29, 14, true);
        v.lblDoanhThuThangTruoc = bold("0 VND", 25, 231, 153);
        v.lblDoanhThuThangNay   = bold("0 VND", 25, 231, 202);
        pm4.getChildren().addAll(lbDT, lbTruoc, lbNay, ivTien, v.lblDoanhThuThangTruoc, v.lblDoanhThuThangNay);
        pLeft.getChildren().add(pm4);

        // Hóa đơn
        Pane pRight = new Pane();
        Pane pm5 = new Pane(); pm5.setId("paneMain"); pm5.setLayoutX(22); pm5.setLayoutY(28); pm5.setPrefSize(480, 308);
        Label lbHD = bold("Hóa đơn", 35, 190, 50);
        Label lbHDNay   = bold("Tháng này: ", 25, 64, 202);
        Label lbHDTruoc = bold("Tháng trước: ", 25, 64, 153);
        ImageView ivHD = imageView("/com/example/pharmacymanagementsystem_qlht/img/hoaDon.png", 98, 105, 44, 26, true);
        v.lblHoaDonThangTruoc = bold("0 hóa đơn", 25, 240, 153);
        v.lblHoaDonThangNay   = bold("0 hóa đơn", 25, 240, 202);
        pm5.getChildren().addAll(lbHD, lbHDNay, lbHDTruoc, ivHD, v.lblHoaDonThangTruoc, v.lblHoaDonThangNay);
        pRight.getChildren().add(pm5);

        smallTop.add(pLeft, 0, 0);
        smallTop.add(pRight, 1, 0);

        // add vào pnlGrid
        pnlGrid.getChildren().addAll(smallTop, topRight, chartWrap, warnWrap);
        v.pnlChung.getChildren().add(pnlGrid);

        // ====== ẢNH LOGO (click về trang chủ) ======
        v.imgLogo = imageView("/com/example/pharmacymanagementsystem_qlht/img/logo.png", 241, 89, 0, -3, true);

        // ====== Pane Thời gian ======
        Pane paneTime = new Pane();
        paneTime.setId("paneMainThoiGian");
        paneTime.setLayoutX(1271);
        paneTime.setLayoutY(93);
        paneTime.setPrefSize(337, 36);
        v.txtNgayThangNam = new Label();
        v.txtNgayThangNam.setLayoutX(24);
        v.txtNgayThangNam.setLayoutY(8);
        v.txtNgayThangNam.setPrefSize(291, 21);
        v.txtNgayThangNam.setFont(Font.font(15));
        v.txtNgayThangNam.setStyle("-fx-alignment: CENTER;");
        paneTime.getChildren().add(v.txtNgayThangNam);

        // ====== pnlThongTin (ẩn/hiện khi click tên người dùng) ======
        v.pnlThongTin = new Pane();
        v.pnlThongTin.setId("pnlThongTin");
        v.pnlThongTin.setLayoutX(1287);
        v.pnlThongTin.setLayoutY(59);
        v.pnlThongTin.setPrefSize(305, 107);

        VBox vbInfo = new VBox();
        vbInfo.setPrefSize(307, 107);

        HBox hbRole = new HBox();
        hbRole.setPrefSize(307, 35);
        Label lbRole = new Label("Vai trò:");
        lbRole.setLayoutY(15);
        Pane paneRole = new Pane();
        paneRole.setId("paneLabelThongTin");
        paneRole.setPrefSize(191, 30);
        v.lblVaiTro = new Label();
        v.lblVaiTro.setLayoutX(14);
        v.lblVaiTro.setLayoutY(1);
        v.lblVaiTro.setPrefSize(158, 30);
        paneRole.getChildren().add(v.lblVaiTro);
        hbRole.getChildren().addAll(spacerLeft(35), lbRole, paneRole);
        VBox.setMargin(hbRole, new javafx.geometry.Insets(20, 0, 0, 0));

        Pane paneLogout = new Pane();
        paneLogout.setPrefSize(307, 62);
        v.btnDangXuat = new Button("Đăng xuất");
        v.btnDangXuat.setId("btnDangXuat");
        v.btnDangXuat.setLayoutX(110);
        v.btnDangXuat.setLayoutY(12);
        paneLogout.getChildren().add(v.btnDangXuat);

        vbInfo.getChildren().addAll(hbRole, paneLogout);
        v.pnlThongTin.getChildren().add(vbInfo);

        // ====== Pane tên người dùng (click để mở pnlThongTin) ======
        v.pnlTenNguoiDung = new Pane();
        v.pnlTenNguoiDung.setId("paneMainTenSD");
        v.pnlTenNguoiDung.setLayoutX(1271);
        v.pnlTenNguoiDung.setLayoutY(24);
        v.pnlTenNguoiDung.setPrefSize(337, 36);

        v.txtNguoiDung = new Label("Người dùng:");
        v.txtNguoiDung.setLayoutX(24);
        v.txtNguoiDung.setLayoutY(8);
        v.txtNguoiDung.setPrefSize(291, 21);
        v.txtNguoiDung.setFont(Font.font(15));
        v.txtNguoiDung.setStyle("-fx-alignment: CENTER;");
        v.pnlTenNguoiDung.getChildren().add(v.txtNguoiDung);

        // ====== Add vào AnchorPane ======
        ap.getChildren().addAll(menuBar, v.pnlChung, v.imgLogo, paneTime, v.pnlThongTin, v.pnlTenNguoiDung);

        // Neo AnchorPane vào root
        v.root.getChildren().add(ap);
        return v;
    }

    private void addStyles(Scene scene) {
        var css = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/TrangChu.css"),
                "Không tìm thấy TrangChu.css"
        ).toExternalForm();
        scene.getStylesheets().add(css);
    }

    // ===================== Helpers =====================
    private static Label bold(String text, double size, double x, double y) {
        Label lb = new Label(text);
        lb.setLayoutX(x);
        lb.setLayoutY(y);
        lb.setFont(Font.font("System Bold", size));
        return lb;
    }

    private static Region spacerLeft(double w) {
        Region r = new Region();
        r.setPrefWidth(w);
        return r;
    }

    private static ImageView imageView(String resourcePath, double fitW, double fitH,
                                       double x, double y, boolean preserveRatio) {
        ImageView iv = new ImageView(new Image(Objects.requireNonNull(
                CuaSoChinh_NhanVien_GUI.class.getResource(resourcePath),
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

    // ===================== Giữ tham chiếu control =====================
    private static class ViewRefs {
        Pane root;

        // thanh menu
        Menu menuTimKiem, menuDanhMuc, menuCapNhat, menuXuLy;

        // menu items
        MenuItem miTK_HoaDon, miTK_PhieuNhap, miTK_PhieuDoiHang, miTK_PhieuTraHang, miTK_PhieuDatHang, miTK_NCC, miTK_Thuoc, miTK_KhachHang;
        MenuItem miDM_Thuoc, miDM_KhachHang, miDM_KeHang, miDM_NCC, miDM_NhomDuocLy;
        MenuItem miCN_GiaBan, miCN_TonKho;
        MenuItem miXL_LapHoaDon, miXL_LapPhieuDoi, miXL_LapPhieuTra, miXL_LapPhieuDat, miXL_NhapHang;

        // khu vực nội dung
        Pane pnlChung;

        // dashboard controls
        LineChart<String, Number> chartDoanhThuThangNay;

        TableView<Thuoc_SP_TheoLo> tblThuocHetHan;
        TableColumn<Thuoc_SP_TheoLo, String> colMaThuocHetHan, colLoHangHetHan;
        TableColumn<Thuoc_SP_TheoLo, java.sql.Date> colHSDHetHan;

        TableView<Thuoc_SP_TheoLo> tblThuocSapHetHan;
        TableColumn<Thuoc_SP_TheoLo, String> colMaThuocSapHetHan, colLoHangSapHetHan;
        TableColumn<Thuoc_SP_TheoLo, java.sql.Date> colHSDSapHetHan;

        Label lbl_SoLuongHangHetHan, lbl_SoLuongHangSapHetHan;
        Label lblDoanhThuThangTruoc, lblDoanhThuThangNay;
        Label lblHoaDonThangTruoc, lblHoaDonThangNay;

        // top area
        ImageView imgLogo;
        Pane pnlTenNguoiDung;
        Label txtNguoiDung;

        Pane pnlThongTin;
        Label lblVaiTro;
        Button btnDangXuat;

        Label txtNgayThangNam;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
