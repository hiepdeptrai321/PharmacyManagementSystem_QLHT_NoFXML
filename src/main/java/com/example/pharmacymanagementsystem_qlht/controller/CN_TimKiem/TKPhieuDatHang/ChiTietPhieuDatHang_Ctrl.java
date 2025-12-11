package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDatHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapHoaDon.LapHoaDon_Ctrl;
import com.example.pharmacymanagementsystem_qlht.dao.ChiTietPhieuDatHang_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.DonViTinh_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.PhieuDatHang_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.PhieuNhap_Dao;
import com.example.pharmacymanagementsystem_qlht.model.*;
import com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapHoaDon.LapHoaDon_GUI;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ChiTietPhieuDatHang_Ctrl  {
    @FXML
    private PhieuDatHang phieuDatHang;

    @FXML
    public TableColumn<ChiTietPhieuDatHang, Number> colSTT;

    @FXML
    public TableColumn<ChiTietPhieuDatHang, String> colTenSP;

    @FXML
    public TableColumn<ChiTietPhieuDatHang, Integer> colSoLuong;

    @FXML
    public TableColumn<ChiTietPhieuDatHang, String> colDonVi;

    @FXML
    public TableColumn<ChiTietPhieuDatHang, Double> colDonGia;

    @FXML
    public TableColumn<ChiTietPhieuDatHang, String> colNhaCungCap; // used for chiết khấu percent

    @FXML
    public TableColumn<ChiTietPhieuDatHang, String> colThanhTien;
    @FXML
    public TableColumn<ChiTietPhieuDatHang, String> colTT;

    @FXML
    public TableView<ChiTietPhieuDatHang> tblChiTietPhieuDat;

    @FXML
    public Label lblMaPhieuDatValue;

    @FXML
    public Label lblNgayLapValue;

    @FXML
    public Label lblTenNhanVienValue;

    @FXML
    public Label lblTenNCCValue;

    @FXML
    public Label lblSDTNCCValue;

    @FXML
    public Label lblGhiChuValue;

    @FXML
    public Label lblTongTienDatValue;

    @FXML
    public Label lblChietKhauPDValue;

    @FXML
    public Label lblThueVATValue;

    @FXML
    public Label lblTongTienPhaiDatValue;

    @FXML
    public Label lblPTTTValue;

    @FXML
    public Label lblTienDaThanhToanValue;

    @FXML
    public Label lblTienConLaiValue;
    @FXML
    public Label lbTT;

    @FXML
    public Button btnInPhieuDat;

    @FXML
    public Button btnLapHoaDon;

    @FXML
    public Button btnDong;

    public final NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

    @FXML
    public void initialize() {
        // Close button
        if (btnDong != null) {
            btnDong.setOnAction(e -> ((Stage) btnDong.getScene().getWindow()).close());
        }
        btnLapHoaDon.setOnAction(e -> onLapHoaDon());

        Platform.runLater(()->{
            Stage dialog = (Stage) btnDong.getScene().getWindow();
            dialog.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
        });
    }

    public void setPhieuDatHang(PhieuDatHang pDat) {
        this.phieuDatHang = pDat;
        hienThiThongTin();
    }

    private void hienThiThongTin() {
        if (phieuDatHang == null) return;

        lblMaPhieuDatValue.setText(phieuDatHang.getMaPDat());

        if (phieuDatHang.getNgayLap() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            lblNgayLapValue.setText(formatter.format(phieuDatHang.getNgayLap()));
        } else {
            lblNgayLapValue.setText("Không rõ");
        }

        if (phieuDatHang.getNhanVien() != null)
            lblTenNhanVienValue.setText(phieuDatHang.getNhanVien().getTenNV());
        else
            lblTenNhanVienValue.setText("");

        // Supplier (NCC) - in PhieuDat model KhachHang used as supplier in some contexts; set if available
        if (phieuDatHang.getKhachHang() != null) {
            lblTenNCCValue.setText(phieuDatHang.getKhachHang().getTenKH());
            lblSDTNCCValue.setText(phieuDatHang.getKhachHang().getSdt());
        } else {
            lblTenNCCValue.setText("");
            lblSDTNCCValue.setText("");
        }

        lblGhiChuValue.setText(phieuDatHang.getGhiChu() != null ? phieuDatHang.getGhiChu() : "");
        if (phieuDatHang != null && lbTT != null) {
            final String STATUS_AV = "status-available";
            final String STATUS_UNAV = "status-unavailable";
            final String STATUS_FI = "status-finished";

            Platform.runLater(() -> {
                lbTT.getStyleClass().removeAll(STATUS_AV, STATUS_UNAV);
                if(phieuDatHang.getTrangthai() == 0){
                    lbTT.setText("Chưa có hàng");
                    lbTT.getStyleClass().add(STATUS_AV);
                }
                else if(phieuDatHang.getTrangthai() == 1){
                    lbTT.setText("Sẵn hàng");
                    lbTT.getStyleClass().add(STATUS_UNAV);
                }
                else{
                    lbTT.setText("Đã hoàn thành");
                    lbTT.getStyleClass().add(STATUS_FI);
                }
            });
        }
        // Load detail rows
        List<ChiTietPhieuDatHang> list = new ChiTietPhieuDatHang_Dao().selectBySql("SELECT * FROM ChiTietPhieuDatHang WHERE MaPDat = ?", phieuDatHang.getMaPDat());

        tblChiTietPhieuDat.getItems().clear();
        tblChiTietPhieuDat.getItems().addAll(list);


        // STT
        colSTT.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(tblChiTietPhieuDat.getItems().indexOf(cellData.getValue()) + 1)
        );

        // Ten san pham
        colTenSP.setCellValueFactory(cel -> new SimpleStringProperty(cel.getValue().getThuoc().getTenThuoc()));

        // So luong
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));

        // Đơn vị
        colDonVi.setCellValueFactory(cel -> {
            String tenDVT = "";
            if (cel.getValue() != null && cel.getValue().getThuoc() != null) {
                tenDVT =  new DonViTinh_Dao().selectById(cel.getValue().getDvt()).getTenDonViTinh();
                if (tenDVT == null) tenDVT = "";
            }
            return new SimpleStringProperty(tenDVT);
        });

        // Đơn giá
        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));

        // Chiết khấu column (giamGia)
        colNhaCungCap.setCellValueFactory(cel -> new SimpleStringProperty(String.format("%.2f", cel.getValue().getGiamGia())));

        // Thành tiền
        colThanhTien.setCellValueFactory(cel -> {
            ChiTietPhieuDatHang item = cel.getValue();
            double thanh = item.getSoLuong() * item.getDonGia();
            // Apply giamGia if giamGia is percentage (assumption)
            if (item.getGiamGia() != 0) {
                thanh = thanh * (1 - item.getGiamGia() / 100.0);
            }
            return new SimpleStringProperty(String.format("%.2f", thanh));
        });

        colTT.setCellValueFactory(cellData ->{
            return new SimpleStringProperty(cellData.getValue().isTrangThai() ? "Sẵn hàng" : "Hết hàng");
        });

        // Compute summary totals
        double tongTruocCK = 0.0;
        double tongGiamGiaValue = 0.0; // total discount amount
        for (ChiTietPhieuDatHang item : list) {
            double line = item.getSoLuong() * item.getDonGia();
            double discountAmount = 0.0;
            if (item.getGiamGia() != 0) {
                discountAmount = line * item.getGiamGia() / 100.0;
            }
            tongTruocCK += line;
            tongGiamGiaValue += discountAmount;
        }

        double tongSauCK = tongTruocCK - tongGiamGiaValue;
        double thueVAT = 0.0; // If your app stores VAT separately, adjust here. Default 0.
        double tongPhaiDat = tongSauCK + thueVAT;

        lblTongTienDatValue.setText(String.format("%.2f VND", tongTruocCK));
        lblChietKhauPDValue.setText(String.format("-%.2f VND", tongGiamGiaValue));
        lblThueVATValue.setText(String.format("%.2f VND", thueVAT));
        lblTongTienPhaiDatValue.setText(String.format("%.2f VND", tongPhaiDat));
        lblTienDaThanhToanValue.setText(String.format("%.2f VND", phieuDatHang.getSoTienCoc()));
        lblTienConLaiValue.setText(String.format("%.2f VND", Math.max(0.0, tongPhaiDat - phieuDatHang.getSoTienCoc())));

        // Payment method placeholder
        lblPTTTValue.setText("Chuyển khoản");
    }

    public void onLapHoaDon() {
        try {
            // 1) Lấy Stage hiện tại từ pnlChung (cửa sổ chính)
            if (pnlChung == null || pnlChung.getScene() == null) {
                throw new IllegalStateException("pnlChung chưa được gắn vào Scene.");
            }
            javafx.stage.Stage stage = (javafx.stage.Stage) pnlChung.getScene().getWindow();

            // 2) Tạo Controller và GUI cho Lập Hóa Đơn
            com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapHoaDon.LapHoaDon_Ctrl lapCtrl =
                    new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapHoaDon.LapHoaDon_Ctrl();
            com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapHoaDon.LapHoaDon_GUI lapGui =
                    new com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapHoaDon.LapHoaDon_GUI();

            // 3) Map dữ liệu từ Phiếu đặt sang ChiTietHoaDon cho LapHoaDon_Ctrl
            //    \- Thay thế bằng 2 hàm bạn đã viết để chia sẻ dữ liệu qua pnlChung.
            //    Ví dụ: mapPhieuToCTHD(phieuDatHangHienTai, lapCtrl);
            //    Ví dụ: applySharedPanelBindings(pnlChung, lapCtrl);
            //
            //    Dưới đây là khung mẫu: lấy danh sách chi tiết PDH và nạp sang dsChiTietHD của HĐ.
            java.util.List<com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon> dsCTHD = new java.util.ArrayList<>();
            for (var ctpdh : getChiTietPhieuDatHienTai()) { // TODO: thay bằng cách lấy danh sách chi tiết PDH hiện tại
                com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon r =
                        new com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon();
                // Gán sản phẩm qua lô (tối thiểu cần sp)
                com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo lo = new com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo();
                lo.setThuoc(ctpdh.getThuoc()); // TODO: đảm bảo ChiTietPhieuDatHang có getThuoc()
                r.setLoHang(lo);

                // Số lượng, đơn giá, đơn vị
                r.setSoLuong(ctpdh.getSoLuong());          // TODO: lấy từ chi tiết PDH
                r.setDonGia(ctpdh.getDonGia());            // TODO: lấy từ chi tiết PDH
                r.setGiamGia(0);                           // KM sẽ tính lại bên LapHoaDon_Ctrl
                r.setDvt(ctpdh.getDvt());                  // TODO: nếu có đơn vị trên chi tiết PDH

                dsCTHD.add(r);
            }
            lapCtrl.dsChiTietHD.setAll(dsCTHD); // Đẩy dữ liệu vào controller HĐ

            // 4) Áp dụng các ràng buộc/đối tượng dùng chung qua pnlChung
            //    Gọi 2 hàm bạn đã viết để "map dữ liệu thông qua pnlChung".
            //    Ví dụ:
            //    applySharedPanelBindings(pnlChung, lapCtrl);

            // 5) Hiển thị giao diện Lập Hóa Đơn lên cửa sổ chính (thay thế pnlChung bằng giao diện HĐ)
            //    LapHoaDon_GUI hiện có API nhận Stage và tự tạo Scene; dùng luôn Stage chính.
            lapGui.showWithController(stage, lapCtrl);

            // 6) Nếu cần, sau khi GUI khởi tạo, gọi tính tổng và refresh
            lapCtrl.tinhTongTien();
            if (lapCtrl.tblChiTietHD != null) lapCtrl.tblChiTietHD.refresh();

        } catch (Exception ex) {
            ex.printStackTrace();
            hien(javafx.scene.control.Alert.AlertType.ERROR, "Lỗi", "Không thể chuyển Phiếu đặt sang Hóa đơn:\n" + ex.getMessage());
        }
    }

    private java.util.List<com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuDatHang> getChiTietPhieuDatHienTai() {
        return java.util.Collections.emptyList();
    }

    private ChiTietHoaDon convertToChiTietHoaDon(ChiTietPhieuDatHang src) {
        ChiTietHoaDon dst = new ChiTietHoaDon();
        // Map quantity, price, discount
        dst.setSoLuong(src.getSoLuong());
        dst.setDonGia(src.getDonGia());
        dst.setGiamGia(src.getGiamGia());

        // Map DVT from String id to DonViTinh entity
        DonViTinh dvtEntity = null;
        if (src.getDvt() != null) {
            dvtEntity = new DonViTinh_Dao().selectById(src.getDvt());
        }
        if (dvtEntity != null) {
            dst.setDvt(dvtEntity);
        }

        // Unable to map lot info: ChiTietHoaDon expects Thuoc_SP_TheoLo (loHang),
        // while ChiTietPhieuDatHang provides Thuoc_SanPham. Leave loHang null or adapt if a DAO is available.
        dst.setLoHang(null);

        // dst.tinhThanhTien() will be used where needed
        return dst;
    }

    // Helper: convert PhieuDatHang -> HoaDon
    private HoaDon convertPhieuDatToHoaDon(PhieuDatHang pd) {
        if (pd == null) return null;
        HoaDon hd = new HoaDon();

        // Basic mappings
        hd.setMaKH(pd.getKhachHang());
        hd.setMaNV(pd.getNhanVien());

        // Use existing Timestamp from PhieuDatHang
        hd.setNgayLap(pd.getNgayLap());

        // Optional mappings
        hd.setTrangThai(Boolean.TRUE);
        hd.setLoaiHoaDon("PhieuDatHang");

        // Attach converted details
        List<ChiTietHoaDon> ctList = tblChiTietPhieuDat.getItems()
                .stream()
                .map(this::convertToChiTietHoaDon)
                .collect(java.util.stream.Collectors.toList());
        hd.setChiTietHD(ctList);

        return hd;
    }

}
