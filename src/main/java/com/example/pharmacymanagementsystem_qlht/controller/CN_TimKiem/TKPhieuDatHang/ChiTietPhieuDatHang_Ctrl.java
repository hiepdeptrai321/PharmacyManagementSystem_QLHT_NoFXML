package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDatHang;

import com.example.pharmacymanagementsystem_qlht.dao.ChiTietPhieuDatHang_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.DonViTinh_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.PhieuDatHang_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.PhieuNhap_Dao;
import com.example.pharmacymanagementsystem_qlht.model.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public void onLapHoaDon(){

    }

}
