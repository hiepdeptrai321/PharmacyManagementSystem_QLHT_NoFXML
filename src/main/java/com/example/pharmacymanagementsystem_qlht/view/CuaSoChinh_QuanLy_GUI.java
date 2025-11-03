package com.example.pharmacymanagementsystem_qlht.view;

import com.example.pharmacymanagementsystem_qlht.controller.CuaSoChinh_QuanLy_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.Objects;

public class CuaSoChinh_QuanLy_GUI {

    // ====== Public API ======
    public void showWithController(Stage stage, CuaSoChinh_QuanLy_Ctrl ctrl) {
        ViewRefs v = buildUIForController();

        // 1) WIRE: inject tất cả control cần dùng trong controller
        ctrlRef = ctrl; // lưu reference controller nếu cần
        ctrl.pnlChung = v.pnlChung;

        ctrl.menuTimKiem = v.menuTimKiem;
        ctrl.menuDanhMuc = v.menuDanhMuc;
        ctrl.menuCapNhat = v.menuCapNhat;
        ctrl.menuThongKe = v.menuThongKe;
        ctrl.menuXuLy   = v.menuXuLy;

        ctrl.txtNguoiDung    = v.txtNguoiDung;
        ctrl.txtNgayThangNam = v.txtNgayThangNam;

        ctrl.tblThuocHetHan     = v.tblThuocHetHan;
        ctrl.colMaThuocHetHan   = v.colMaThuocHetHan;
        ctrl.colLoHangHetHan    = v.colLoHangHetHan;
        ctrl.colHSDHetHan       = v.colHSDHetHan;

        ctrl.tblThuocSapHetHan    = v.tblThuocSapHetHan;
        ctrl.colMaThuocSapHetHan  = v.colMaThuocSapHetHan;
        ctrl.colLoHangSapHetHan   = v.colLoHangSapHetHan;
        ctrl.colHSDSapHetHan      = v.colHSDSapHetHan;

        ctrl.lbl_SoLuongHangHetHan    = v.lbl_SoLuongHangHetHan;
        ctrl.lbl_SoLuongHangSapHetHan = v.lbl_SoLuongHangSapHetHan;

        ctrl.chartDoanhThuThangNay = v.chartDoanhThuThangNay;
        ctrl.lblDoanhThuThangTruoc = v.lblDoanhThuThangTruoc;
        ctrl.lblDoanhThuThangNay   = v.lblDoanhThuThangNay;
        ctrl.lblHoaDonThangTruoc   = v.lblHoaDonThangTruoc;
        ctrl.lblHoaDonThangNay     = v.lblHoaDonThangNay;

        ctrl.pnlThongTin = v.pnlThongTin;
        ctrl.lblVaiTro   = v.lblVaiTro;
        // gán pnlNguoiDung = panel tên người dùng (panel bạn đang click)
        ctrl.pnlNguoiDung = v.paneMainTenSD;

        // 2) Gắn handler UI -> controller (sau khi có controller thật)
        v.logo.setOnMouseClicked(ctrl::AnhChuyenTrangChu);
        v.paneMainTenSD.setOnMouseClicked(ctrl::pnlNguoiDungClick);

        // Menu TÌM KIẾM
        v.miTKHoaDon.setOnAction(ctrl::timKiemHoaDon);
        v.miTKPhieuNhap.setOnAction(ctrl::timKiemPhieuNhap);
        v.miTKPhieuDoi.setOnAction(ctrl::timKiemPhieuDoiHang);
        v.miTKPhieuTra.setOnAction(ctrl::timKiemPhieuTraHang);
        v.miTKPhieuDat.setOnAction(ctrl::timKiemPhieuDatHang);
        v.miTKNCC.setOnAction(ctrl::timKiemNhaCungCap);
        v.miTimThuoc.setOnAction(ctrl::timKiemThuoc);
        v.miTimKhachHang.setOnAction(ctrl::timKiemKhachHang);
        v.miTKHoatDong.setOnAction(ctrl::timKiemHoatDong);

        // Menu DANH MỤC
        v.miDMThuoc.setOnAction(ctrl::danhMucThuoc);
        v.miDMNhanVien.setOnAction(ctrl::danhMucNhanVien);
        v.miDMKeHang.setOnAction(ctrl::danhMucKeHang);
        v.miDMKhuyenMai.setOnAction(ctrl::danhMucKhuyenMai);
        v.miDMNCC.setOnAction(ctrl::danhMucNhaCungCap);
        v.miDMNhomDL.setOnAction(ctrl::danhMucNhomDuocLy);

        // Menu CẬP NHẬT
        v.miCapNhatGia.setOnAction(ctrl::CapNhatGiaBan);
        v.miCapNhatTonKho.setOnAction(ctrl::capNhatTonKho);
        v.miCapNhatKM.setOnAction(ctrl::capNhatKhuyenMai);

        // Menu THỐNG KÊ
        v.miTKDoanhThu.setOnAction(ctrl::thongKeDoanhThu);
        v.miTKXNT.setOnAction(ctrl::thongKeXuatNhap);

        // Menu XỬ LÝ
        v.miLapHoaDon.setOnAction(ctrl::lapHoaDon);
        v.miLapPhieuDoi.setOnAction(ctrl::lapPhieuDoiHang);
        v.miLapPhieuTra.setOnAction(ctrl::lapPhieuTraHang);
        v.miLapPhieuDatHang.setOnAction(ctrl::lapPhieuDatHang);
        v.miNhapHang.setOnAction(ctrl::nhapHang);

        // 3) Tạo Scene & CSS TRƯỚC
        Scene scene = new Scene(v.root, 1646, 1029);
        addStyles(scene, "/com/example/pharmacymanagementsystem_qlht/css/TrangChu.css");

        // Accelerators (phím tắt)
        v.miTimThuoc.setAccelerator(new KeyCodeCombination(KeyCode.F6));
        v.miTimKhachHang.setAccelerator(new KeyCodeCombination(KeyCode.F5));
        v.miLapHoaDon.setAccelerator(new KeyCodeCombination(KeyCode.F2));
        v.miLapPhieuDatHang.setAccelerator(new KeyCodeCombination(KeyCode.F3));
        v.miNhapHang.setAccelerator(new KeyCodeCombination(KeyCode.F4));
        v.miCapNhatGia.setAccelerator(new KeyCodeCombination(KeyCode.F7));
        v.miCapNhatTonKho.setAccelerator(new KeyCodeCombination(KeyCode.F8));

        stage.setTitle("Trang chủ - Quản lý");
        stage.setScene(scene);

        // 4) Đánh dấu đã wire + gọi initialize SAU KHI scene đã gán
        ctrl.markViewWired();
        ctrl.initialize();

        // 5) (tuỳ bạn) show tại caller hoặc bật ở đây
        // stage.show();
    }

    // ================== UI Builder ==================
    private ViewRefs buildUIForController() {
        ViewRefs v = new ViewRefs();

        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 1029);

        // ===== MenuBar =====
        MenuBar menuBar = new MenuBar();
        AnchorPane.setLeftAnchor(menuBar, 0.0);
        AnchorPane.setRightAnchor(menuBar, 0.0);
        menuBar.setLayoutY(86);
        menuBar.setPrefSize(1646, 50);
        menuBar.setId("menuBar");
        menuBar.getStyleClass().add("menu-bar");

        // === Menus ===
        v.menuTimKiem = new Menu("⌕ Tìm kiếm");
        v.miTKHoaDon    = mi("Tìm hóa đơn");
        v.miTKPhieuNhap = mi("Tìm phiếu nhập hàng");
        v.miTKPhieuDoi  = mi("Tìm phiếu đổi hàng");
        v.miTKPhieuTra  = mi("Tìm phiếu trả hàng");
        v.miTKPhieuDat  = mi("Tìm phiếu đặt hàng");
        v.miTKNCC       = mi("Tìm nhà cung cấp");
        v.miTimThuoc    = mi("Tìm thuốc");
        v.miTimKhachHang= mi("Tìm khách hàng");
        v.miTKHoatDong  = mi("Tìm kiếm hoạt động");
        v.menuTimKiem.getItems().addAll(
                v.miTKHoaDon, v.miTKPhieuNhap, v.miTKPhieuDoi, v.miTKPhieuTra,
                v.miTKPhieuDat, v.miTKNCC, v.miTimThuoc, v.miTimKhachHang, v.miTKHoatDong
        );
        v.menuTimKiem.getStyleClass().add("m-timkiem");

        v.menuDanhMuc = new Menu("📁 Danh mục");
        v.miDMThuoc     = mi("Danh mục thuốc");
        v.miDMNhanVien  = mi("Danh mục nhân viên");
        v.miDMKeHang    = mi("Danh mục kệ hàng");
        v.miDMKhuyenMai = mi("Danh mục khuyến mãi");
        v.miDMNCC       = mi("Danh mục nhà cung cấp");
        v.miDMNhomDL    = mi("Danh mục nhóm dược lý");
        v.menuDanhMuc.getItems().addAll(
                v.miDMThuoc, v.miDMNhanVien, v.miDMKeHang, v.miDMKhuyenMai, v.miDMNCC, v.miDMNhomDL
        );
        v.menuDanhMuc.getStyleClass().add("m-danhmuc");

        v.menuCapNhat = new Menu("🔄 Cập nhật");
        v.miCapNhatGia    = mi("Cập nhật giá bán");
        v.miCapNhatTonKho = mi("Cập nhật tồn kho");
        v.miCapNhatKM     = mi("Cập nhật khuyến mãi");
        v.menuCapNhat.getItems().addAll(v.miCapNhatGia, v.miCapNhatTonKho, v.miCapNhatKM);
        v.menuCapNhat.getStyleClass().add("m-capnhat");

        v.menuThongKe = new Menu("📊 Thống kê");
        v.miTKDoanhThu = mi("Thống kê doanh thu");
        v.miTKXNT      = mi("Thống kê xuất nhập tồn");
        v.menuThongKe.getItems().addAll(v.miTKDoanhThu, v.miTKXNT);
        v.menuThongKe.getStyleClass().add("m-thongke");

        v.menuXuLy = new Menu("🛠 Xử lý");
        Menu mBanHang = new Menu("Bán hàng");
        v.miLapHoaDon   = mi("Lập hóa đơn");
        v.miLapPhieuDoi = mi("Lập phiếu đổi hàng");
        v.miLapPhieuTra = mi("Lập phiếu trả hàng");
        v.miLapPhieuDatHang = mi("Lập phiếu đặt hàng");
        mBanHang.getItems().addAll(v.miLapHoaDon, v.miLapPhieuDoi, v.miLapPhieuTra, v.miLapPhieuDatHang);
        v.miNhapHang = mi("Nhập hàng");
        v.menuXuLy.getItems().addAll(mBanHang, v.miNhapHang);
        v.menuXuLy.getStyleClass().add("m-xuly");

        menuBar.getMenus().addAll(v.menuTimKiem, v.menuDanhMuc, v.menuCapNhat, v.menuThongKe, v.menuXuLy);

        // ===== pnlChung =====
        v.pnlChung = new StackPane();
        v.pnlChung.setLayoutY(137);
        v.pnlChung.setPrefSize(1646, 895);

        // ===== nội dung mặc định trên pnlChung =====
        GridPane pnlGrid = new GridPane();
        pnlGrid.setId("pnlGrid");
        pnlGrid.setPrefSize(1646, 895);

        ColumnConstraints c1 = new ColumnConstraints(); c1.setHgrow(Priority.SOMETIMES); c1.setPrefWidth(1072.9);
        ColumnConstraints c2 = new ColumnConstraints(); c2.setHgrow(Priority.SOMETIMES); c2.setPrefWidth(573.66);
        pnlGrid.getColumnConstraints().addAll(c1, c2);
        RowConstraints r1 = new RowConstraints(); r1.setVgrow(Priority.SOMETIMES); r1.setPrefHeight(361);
        RowConstraints r2 = new RowConstraints(); r2.setVgrow(Priority.SOMETIMES); r2.setPrefHeight(519.43);
        pnlGrid.getRowConstraints().addAll(r1, r2);

        // (0,1) Điều kiện bảo quản
        Pane cell01 = new Pane(); GridPane.setColumnIndex(cell01, 1);
        Pane paneMain01 = paneMain(509, 308); paneMain01.setLayoutX(31); paneMain01.setLayoutY(29);
        Label lbDK = bold("Điều kiện bảo quản", 35); lbDK.setLayoutX(97); lbDK.setLayoutY(24);
        Label lb35 = bold("35℃", 45); lb35.setLayoutX(127); lb35.setLayoutY(138);
        Label lb50 = bold("50%", 45); lb50.setLayoutX(359); lb50.setLayoutY(137);
        ImageView ivNhiet = img("/com/example/pharmacymanagementsystem_qlht/img/nhietDo.png", 84, 130); ivNhiet.setLayoutX(56); ivNhiet.setLayoutY(107);
        ImageView ivDoAm  = img("/com/example/pharmacymanagementsystem_qlht/img/DoAm.png", 163, 179); ivDoAm.setLayoutX(236); ivDoAm.setLayoutY(84);
        paneMain01.getChildren().addAll(lbDK, lb35, lb50, ivNhiet, ivDoAm);
        cell01.getChildren().add(paneMain01);

        // (1,0) LineChart
        Pane cell10 = new Pane(); GridPane.setRowIndex(cell10, 1);
        Pane paneMain10 = paneMain(1013, 454); paneMain10.setLayoutX(26); paneMain10.setLayoutY(27);
        Label lbDT = bold("Doanh thu bán hàng tháng này", 35); lbDT.setLayoutX(266); lbDT.setLayoutY(19);
        v.chartDoanhThuThangNay = new LineChart<>(new CategoryAxis(), new NumberAxis());
        v.chartDoanhThuThangNay.setLayoutX(29); v.chartDoanhThuThangNay.setLayoutY(71);
        v.chartDoanhThuThangNay.setPrefSize(953, 340);
        Label lbVND = bold("VND", 15); lbVND.setLayoutX(12); lbVND.setLayoutY(230); lbVND.setRotate(-90);
        Label lbNgay = bold("Ngày", 15); lbNgay.setLayoutX(487); lbNgay.setLayoutY(411);
        paneMain10.getChildren().addAll(lbDT, v.chartDoanhThuThangNay, lbVND, lbNgay);
        cell10.getChildren().add(paneMain10);

        // (1,1) Cảnh báo hàng hóa
        Pane cell11 = new Pane(); GridPane.setColumnIndex(cell11, 1); GridPane.setRowIndex(cell11, 1);
        Pane paneMain11 = paneMain(509, 454); paneMain11.setLayoutX(31); paneMain11.setLayoutY(28);
        Label lbCB = bold("Cảnh báo hàng hóa", 35); lbCB.setLayoutX(135); lbCB.setLayoutY(22);

        v.tblThuocHetHan = new TableView<>();
        v.tblThuocHetHan.setLayoutX(18); v.tblThuocHetHan.setLayoutY(116); v.tblThuocHetHan.setPrefSize(473, 139);
        v.colMaThuocHetHan = new TableColumn<>("Mã thuốc"); v.colMaThuocHetHan.setPrefWidth(148);
        v.colLoHangHetHan = new TableColumn<>("Lô hàng"); v.colLoHangHetHan.setPrefWidth(173.14);
        v.colHSDHetHan = new TableColumn<>("Hạn sử dụng"); v.colHSDHetHan.setPrefWidth(156);
        v.tblThuocHetHan.getColumns().addAll(v.colMaThuocHetHan, v.colLoHangHetHan, v.colHSDHetHan);

        v.tblThuocSapHetHan = new TableView<>();
        v.tblThuocSapHetHan.setLayoutX(20); v.tblThuocSapHetHan.setLayoutY(291); v.tblThuocSapHetHan.setPrefSize(473, 141);
        v.colMaThuocSapHetHan = new TableColumn<>("Mã thuốc"); v.colMaThuocSapHetHan.setPrefWidth(143.43);
        v.colLoHangSapHetHan = new TableColumn<>("Lô hàng"); v.colLoHangSapHetHan.setPrefWidth(179.43);
        v.colHSDSapHetHan = new TableColumn<>("Hạn sử dụng"); v.colHSDSapHetHan.setPrefWidth(151.43);
        v.tblThuocSapHetHan.getColumns().addAll(v.colMaThuocSapHetHan, v.colLoHangSapHetHan, v.colHSDSapHetHan);

        v.lbl_SoLuongHangHetHan = bold("Hàng hết hạn: 0", 20); v.lbl_SoLuongHangHetHan.setLayoutX(18); v.lbl_SoLuongHangHetHan.setLayoutY(87);
        v.lbl_SoLuongHangSapHetHan = bold("Hàng sắp hết hạn: 0", 20); v.lbl_SoLuongHangSapHetHan.setLayoutX(21); v.lbl_SoLuongHangSapHetHan.setLayoutY(262);
        ImageView ivCB = img("/com/example/pharmacymanagementsystem_qlht/img/canhBao.png", 74, 74); ivCB.setLayoutX(41); ivCB.setLayoutY(10);
        paneMain11.getChildren().addAll(lbCB, v.tblThuocHetHan, v.tblThuocSapHetHan, v.lbl_SoLuongHangHetHan, v.lbl_SoLuongHangSapHetHan, ivCB);
        cell11.getChildren().add(paneMain11);

        // grid dưới: Doanh thu / Hóa đơn tổng hợp
        GridPane gridBottom = new GridPane();
        ColumnConstraints gc1 = new ColumnConstraints(); gc1.setHgrow(Priority.SOMETIMES); gc1.setPrefWidth(537.76);
        ColumnConstraints gc2 = new ColumnConstraints(); gc2.setHgrow(Priority.SOMETIMES); gc2.setPrefWidth(536.24);
        gridBottom.getColumnConstraints().addAll(gc1, gc2);
        gridBottom.getRowConstraints().add(new RowConstraints());

        // trái: Doanh thu
        Pane pLeft = new Pane();
        Pane boxLeft = paneMain(480, 308); boxLeft.setLayoutX(27); boxLeft.setLayoutY(27);
        Label lbDTTitle = bold("Doanh thu", 35); lbDTTitle.setLayoutX(176); lbDTTitle.setLayoutY(50);
        Label lbTTruoc = bold("Tháng trước:", 25); lbTTruoc.setLayoutX(62); lbTTruoc.setLayoutY(153);
        Label lbTNay = bold("Tháng này:", 25); lbTNay.setLayoutX(62); lbTNay.setLayoutY(202);
        ImageView ivTien = img("/com/example/pharmacymanagementsystem_qlht/img/Tien.png", 123, 123); ivTien.setLayoutX(29); ivTien.setLayoutY(14);
        v.lblDoanhThuThangTruoc = bold("0 VND", 25); v.lblDoanhThuThangTruoc.setLayoutX(231); v.lblDoanhThuThangTruoc.setLayoutY(153);
        v.lblDoanhThuThangNay = bold("0 VND", 25); v.lblDoanhThuThangNay.setLayoutX(231); v.lblDoanhThuThangNay.setLayoutY(202);
        boxLeft.getChildren().addAll(lbDTTitle, lbTTruoc, lbTNay, ivTien, v.lblDoanhThuThangTruoc, v.lblDoanhThuThangNay);
        pLeft.getChildren().add(boxLeft);

        // phải: Hóa đơn
        Pane pRight = new Pane(); GridPane.setColumnIndex(pRight, 1);
        Pane boxRight = paneMain(480, 308); boxRight.setLayoutX(22); boxRight.setLayoutY(28);
        Label lbHDTitle = bold("Hóa đơn", 35); lbHDTitle.setLayoutX(190); lbHDTitle.setLayoutY(50);
        Label lbHDTNay = bold("Tháng này: ", 25); lbHDTNay.setLayoutX(64); lbHDTNay.setLayoutY(202);
        Label lbHDTT = bold("Tháng trước: ", 25); lbHDTT.setLayoutX(64); lbHDTT.setLayoutY(153);
        ImageView ivHD = img("/com/example/pharmacymanagementsystem_qlht/img/hoaDon.png", 98, 105); ivHD.setLayoutX(44); ivHD.setLayoutY(26);
        v.lblHoaDonThangTruoc = bold("0 hóa đơn", 25); v.lblHoaDonThangTruoc.setLayoutX(240); v.lblHoaDonThangTruoc.setLayoutY(153);
        v.lblHoaDonThangNay = bold("0 hóa đơn", 25); v.lblHoaDonThangNay.setLayoutX(240); v.lblHoaDonThangNay.setLayoutY(202);
        boxRight.getChildren().addAll(lbHDTitle, lbHDTNay, lbHDTT, ivHD, v.lblHoaDonThangTruoc, v.lblHoaDonThangNay);
        pRight.getChildren().add(boxRight);

        gridBottom.getChildren().addAll(pLeft, pRight);

        pnlGrid.getChildren().addAll(cell01, cell10, cell11, gridBottom);
        v.pnlChung.getChildren().add(pnlGrid);

        // ===== Logo (click về Trang chủ) =====
        v.logo = img("/com/example/pharmacymanagementsystem_qlht/img/logo.png", 241, 89);
        v.logo.setLayoutY(-3);

        // ===== Panel thời gian =====
        Pane paneMainThoiGian = paneMain(337, 36);
        paneMainThoiGian.setId("paneMainThoiGian");
        paneMainThoiGian.setLayoutX(1271); paneMainThoiGian.setLayoutY(93);
        v.txtNgayThangNam = new Label();
        v.txtNgayThangNam.setStyle("-fx-text-fill: -fx-text-base-color;");
        v.txtNgayThangNam.setPrefSize(291, 21); v.txtNgayThangNam.setLayoutX(24); v.txtNgayThangNam.setLayoutY(8);
        v.txtNgayThangNam.setFont(Font.font(15)); v.txtNgayThangNam.setAlignment(javafx.geometry.Pos.CENTER);
        paneMainThoiGian.getChildren().add(v.txtNgayThangNam);

        // ===== Panel info user =====
        v.pnlThongTin = new Pane();
        v.pnlThongTin.setId("pnlThongTin");
        v.pnlThongTin.setPrefSize(305, 107);
        v.pnlThongTin.setLayoutX(1287); v.pnlThongTin.setLayoutY(59);

        VBox vbInfo = new VBox(); vbInfo.setPrefSize(307, 107);
        HBox hbRole = new HBox(); hbRole.setPrefSize(307, 35); VBox.setMargin(hbRole, new Insets(20,0,0,0));
        Label lbRole = new Label("Vai trò:"); lbRole.setPrefSize(52, 30); HBox.setMargin(lbRole, new Insets(0,0,0,35));
        Pane paneLabelThongTin = paneMain(191, 30);
        v.lblVaiTro = new Label(); v.lblVaiTro.setPrefSize(158, 30); v.lblVaiTro.setLayoutX(14); v.lblVaiTro.setLayoutY(1);
        paneLabelThongTin.setId("paneLabelThongTin");
        paneLabelThongTin.getChildren().add(v.lblVaiTro);
        hbRole.getChildren().addAll(lbRole, paneLabelThongTin);

        Pane pDangXuat = new Pane(); pDangXuat.setPrefSize(307, 62);
        Button btnDangXuat = new Button("Đăng xuất"); btnDangXuat.setId("btnDangXuat");
        btnDangXuat.setLayoutX(110); btnDangXuat.setLayoutY(12);
        // handler sẽ set trong showWithController qua ctrl.btnDangXuatClick
        pDangXuat.getChildren().add(btnDangXuat);
        // Đặt handler ngay tại đây để không quên:
        btnDangXuat.setOnAction(e -> {
            if (ctrlRef != null) ctrlRef.btnDangXuatClick(e);
        });

        vbInfo.getChildren().addAll(hbRole, pDangXuat);
        v.pnlThongTin.getChildren().add(vbInfo);

        // ===== Panel tên người dùng (click hiện pnlThongTin) =====
        v.paneMainTenSD = paneMain(337, 36);
        v.paneMainTenSD.setId("paneMainTenSD");
        v.paneMainTenSD.setLayoutX(1271); v.paneMainTenSD.setLayoutY(24);
        v.txtNguoiDung = new Label("Người dùng:");
        v.txtNguoiDung.setPrefSize(291, 21); v.txtNguoiDung.setLayoutX(24); v.txtNguoiDung.setLayoutY(8);
        v.txtNguoiDung.setFont(Font.font(15)); v.txtNguoiDung.setAlignment(javafx.geometry.Pos.CENTER);
        v.paneMainTenSD.getChildren().add(v.txtNguoiDung);

        // gom tất cả
        root.getChildren().addAll(menuBar, v.pnlChung, v.logo, paneMainThoiGian, v.pnlThongTin, v.paneMainTenSD);
        AnchorPane.setLeftAnchor(menuBar, 0.0); AnchorPane.setRightAnchor(menuBar, 0.0);
        AnchorPane.setLeftAnchor(v.pnlChung, 0.0); AnchorPane.setRightAnchor(v.pnlChung, 0.0);

        v.root = root;
        return v;
    }

    // ====== helpers ======
    // để nút Đăng xuất truy cập được controller thật sau khi showWithController chạy
    private static CuaSoChinh_QuanLy_Ctrl ctrlRef;

    private static class ViewRefs {
        AnchorPane root;

        // top bar
        Menu menuTimKiem, menuDanhMuc, menuCapNhat, menuThongKe, menuXuLy;

        // các MenuItem cần gắn handler
        MenuItem miTKHoaDon, miTKPhieuNhap, miTKPhieuDoi, miTKPhieuTra, miTKPhieuDat, miTKNCC, miTKHoatDong;
        MenuItem miDMThuoc, miDMNhanVien, miDMKeHang, miDMKhuyenMai, miDMNCC, miDMNhomDL;
        MenuItem miTKDoanhThu, miTKXNT;
        MenuItem miTimThuoc, miTimKhachHang;
        MenuItem miLapHoaDon, miLapPhieuDoi, miLapPhieuTra, miLapPhieuDatHang, miNhapHang;
        MenuItem miCapNhatGia, miCapNhatTonKho, miCapNhatKM;

        // center
        Pane pnlChung;

        // chart + tables
        LineChart<String, Number> chartDoanhThuThangNay;
        TableView<Thuoc_SP_TheoLo> tblThuocHetHan, tblThuocSapHetHan;
        TableColumn<Thuoc_SP_TheoLo, String> colMaThuocHetHan, colLoHangHetHan;
        TableColumn<Thuoc_SP_TheoLo, Date> colHSDHetHan;
        TableColumn<Thuoc_SP_TheoLo, String> colMaThuocSapHetHan, colLoHangSapHetHan;
        TableColumn<Thuoc_SP_TheoLo, Date> colHSDSapHetHan;
        Label lbl_SoLuongHangHetHan, lbl_SoLuongHangSapHetHan;

        // summary labels
        Label lblDoanhThuThangTruoc, lblDoanhThuThangNay, lblHoaDonThangTruoc, lblHoaDonThangNay;

        // header panels
        ImageView logo;
        Label txtNgayThangNam, txtNguoiDung;
        Pane pnlThongTin, paneMainTenSD;
        Label lblVaiTro;
    }

    private static MenuItem mi(String text) {
        return new MenuItem(text); // handler gắn sau ở showWithController
    }

    private static Pane paneMain(double w, double h) {
        Pane p = new Pane();
        p.setId("paneMain");
        p.setPrefSize(w, h);
        return p;
    }

    private static Label bold(String text, double size) {
        Label l = new Label(text);
        l.setFont(Font.font("System Bold", size));
        return l;
    }

    private static ImageView img(String resPath, double fitW, double fitH) {
        var url = Objects.requireNonNull(CuaSoChinh_QuanLy_GUI.class.getResource(resPath), "Missing resource: " + resPath);
        ImageView iv = new ImageView(new Image(url.toExternalForm()));
        iv.setFitWidth(fitW); iv.setFitHeight(fitH); iv.setPickOnBounds(true); iv.setPreserveRatio(true);
        return iv;
    }

    private static void addStyles(Scene scene, String cssPath) {
        var url = CuaSoChinh_QuanLy_GUI.class.getResource(cssPath);
        if (url != null) scene.getStylesheets().add(url.toExternalForm());
        else System.err.println("CSS not found: " + cssPath);
    }
}
