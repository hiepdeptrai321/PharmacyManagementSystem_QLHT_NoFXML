package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuTraHang;

import com.example.pharmacymanagementsystem_qlht.dao.ChiTietPhieuNhap_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.ChiTietPhieuTraHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.*;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuTra.ChiTietPhieuTraHang_GUI;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import static com.example.pharmacymanagementsystem_qlht.TienIch.TuyChinhAlert.hien;
import static com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon.ChiTietHoaDon_Ctrl.formatVNDTable;
import static javafx.scene.control.Alert.AlertType.ERROR;

public class ChiTietPhieuTraHang_Ctrl extends Application{

    public PhieuTraHang phieuTraHang;
    public TableColumn<ChiTietPhieuTraHang, String> colDonGia;
    public Button btnDong;
    public Button btnInPhieuTra;


    public TableColumn<ChiTietPhieuTraHang, String> colLyDo;
    public TableColumn<ChiTietPhieuTraHang, String> colSTT;
    public TableColumn<ChiTietPhieuTraHang, String> colSoLuong;
    public TableColumn<ChiTietPhieuTraHang, String> colTenSP;
    public TableColumn<ChiTietPhieuTraHang, Double> colThanhTien;
    public Label lblChietKhauPTValue;
    public Label lblGhiChuValue;
    public Label lblMaPhieuTraValue;
    public Label lblNgayLapValue;

    public Label lblPTHTValue;
    public Label lblSDTKH;
    public Label lblSDTKhachHangValue;
    public Label lblTenKH;
    public Label lblTenKhachHangValue;
    public Label lblTenNV;
    public Label lblTenNhanVienValue;
    public Label lblThueVATValue;
    public Label lblTienKhachNhanValue;
    public Label lblTienThuaValue;
    public Label lblTongTienPhaiTraValue;
    public Label lblTongTienTraValue;
    public TableView<ChiTietPhieuTraHang> tblChiTietPhieuTra;

    @Override
    public void start(Stage stage) throws Exception {
        ChiTietPhieuTraHang_GUI.showWithController(stage, new ChiTietPhieuTraHang_Ctrl());
    }
    public void initialize() {
        btnDong.setOnAction(e -> ((Stage) btnDong.getScene().getWindow()).close());
        try {
            hienThiThongTin();

        } catch (Exception e) {
            hien(ERROR, "Lỗi Dữ Liệu", "Không thể hiển thị chi tiết phiếu trả hàng do lỗi dữ liệu hoặc CSDL.");
            e.printStackTrace();
            Platform.runLater(() -> {
                ((Stage) btnDong.getScene().getWindow()).close();
            });
            throw new RuntimeException("Lỗi fatal khi khởi tạo ChiTietPhieuTraHang.", e);
        }
    }
    public void setPhieuTraHang(PhieuTraHang pTra) {
        this.phieuTraHang = pTra;
    }

    private void hienThiThongTin() {
        if (phieuTraHang != null) {
        lblMaPhieuTraValue.setText(phieuTraHang.getMaPT());

            Timestamp ngayLapTimestamp = phieuTraHang.getNgayLap();

            if (ngayLapTimestamp != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String ngayLapFormatted = formatter.format(ngayLapTimestamp);
                lblNgayLapValue.setText(ngayLapFormatted);
            } else {
                lblNgayLapValue.setText("Không rõ");
            }
            lblTenNV.setText(phieuTraHang.getNhanVien().getTenNV());
            lblTenKhachHangValue.setText(phieuTraHang.getKhachHang() != null ? phieuTraHang.getKhachHang().getTenKH() : "Khách lẻ");
            lblSDTKhachHangValue.setText(phieuTraHang.getKhachHang().getSdt());
            lblGhiChuValue.setText(phieuTraHang.getGhiChu() != null ? phieuTraHang.getGhiChu() : "");

            List<ChiTietPhieuTraHang> list = new ChiTietPhieuTraHang_Dao().getChiTietPhieuTraByMaPT(phieuTraHang.getMaPT());

            tblChiTietPhieuTra.getItems().clear();
            tblChiTietPhieuTra.getItems().addAll(list);

            colSTT.setCellValueFactory(cellData ->
                    new SimpleStringProperty(String.valueOf(tblChiTietPhieuTra.getItems()
                            .indexOf(cellData.getValue()) + 1))
            );

            colTenSP.setCellValueFactory(cel ->
                    new SimpleStringProperty(cel.getValue().getThuoc().getTenThuoc())
            );

            colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
            colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));

            colLyDo.setCellValueFactory(cel ->
                    new SimpleStringProperty(cel.getValue().getLyDoTra())
            );

            colThanhTien.setCellValueFactory(cel ->
                    new ReadOnlyObjectWrapper<>(cel.getValue().getThanhTienTra())
            );
            colThanhTien.setCellFactory(tc -> new TableCell<>() {
                @Override protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : formatVNDTable(item == null ? 0 : item));
                    setStyle(empty ? "" : "-fx-alignment: CENTER-RIGHT;");
                }
            });
            //Double tongGiaNhap = temp.getTongTien();
            //lblTongGiaNhap.setText("Tổng giá nhập: " + String.format("%.2f", tongGiaNhap) +" VND");

        }
    }

}
