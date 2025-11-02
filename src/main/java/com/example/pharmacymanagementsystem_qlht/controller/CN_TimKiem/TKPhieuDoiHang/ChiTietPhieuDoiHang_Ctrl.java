package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDoiHang;

import com.example.pharmacymanagementsystem_qlht.dao.ChiTietPhieuDoiHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuDoiHang;
import com.example.pharmacymanagementsystem_qlht.model.PhieuDoiHang;
import com.example.pharmacymanagementsystem_qlht.TienIch.DoiNgay;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDoi.ChiTietPhieuDoiHang_GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;

public class ChiTietPhieuDoiHang_Ctrl extends Application {
    public Button btnDong;
    public Button btnInPhieuDoi;
    public TableColumn<ChiTietPhieuDoiHang, String> colLyDo;
    public TableColumn<ChiTietPhieuDoiHang, String> colSTT;
    public TableColumn<ChiTietPhieuDoiHang, String> colSoLuong;
    public TableColumn<ChiTietPhieuDoiHang, String> colDonVi;
    public TableColumn<ChiTietPhieuDoiHang, String> colTenSP;


    public Label lblGhiChuValue;
    public Label lblMaPhieuDoiValue;
    public Label lblNgayLapValue;
    public Label lblSDTKhachHangValue;
    public Label lblTenKhachHangValue;
    public Label lblTenNhanVienValue;


    public TableView<ChiTietPhieuDoiHang> tblChiTietPhieuDoi;

    private PhieuDoiHang phieuDoiHang;
    @Override
    public void start(Stage stage) throws Exception {
        ChiTietPhieuDoiHang_GUI gui = new ChiTietPhieuDoiHang_GUI();
        gui.showWithController(stage, this);
    }
    public void initialize() {
        btnDong.setOnAction(e -> ((Stage) btnDong.getScene().getWindow()).close());
        Platform.runLater(()->{
            Stage dialog = (Stage) btnDong.getScene().getWindow();
            dialog.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
        });

    }
    public void setPhieuDoiHang(PhieuDoiHang pDoi) {
        this.phieuDoiHang = pDoi;
        hienThiThongTin();
    }
    private void hienThiThongTin() {
        if (phieuDoiHang != null) {
            lblMaPhieuDoiValue.setText(phieuDoiHang.getMaPD());
            lblNgayLapValue.setText(DoiNgay.dinhDangThoiGian(phieuDoiHang.getNgayLap()));
            lblTenNhanVienValue.setText(phieuDoiHang.getNhanVien().getTenNV());
            lblTenKhachHangValue.setText(phieuDoiHang.getKhachHang() != null ? phieuDoiHang.getKhachHang().getTenKH() : "Khách lẻ");
            lblSDTKhachHangValue.setText(phieuDoiHang.getKhachHang() != null ? phieuDoiHang.getKhachHang().getSdt() : "");
            lblGhiChuValue.setText(phieuDoiHang.getGhiChu() != null ? phieuDoiHang.getGhiChu() : "");
            //tblChiTietPhieuDoi.setItems(phieuDoiHang.getChiTietPhieuDoiHang());

            List<ChiTietPhieuDoiHang> list = new ChiTietPhieuDoiHang_Dao().getChiTietPhieuDoiByMaPT(phieuDoiHang.getMaPD());
            tblChiTietPhieuDoi.getItems().clear();
            tblChiTietPhieuDoi.getItems().addAll(list);

            colSTT.setCellValueFactory(cellData ->
                    new SimpleStringProperty(String.valueOf(tblChiTietPhieuDoi.getItems().indexOf(cellData.getValue()) + 1))
            );
            colTenSP.setCellValueFactory(cel -> new SimpleStringProperty(cel.getValue().getThuoc().getTenThuoc())
            );
            colSoLuong.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSoLuong()))
            );
            colDonVi.setCellValueFactory(cel -> {
                if (cel.getValue().getDvt() != null && cel.getValue().getDvt().getTenDonViTinh() != null) {
                    return new SimpleStringProperty(cel.getValue().getDvt().getTenDonViTinh());
                }
                return new SimpleStringProperty("");
            });
            colLyDo.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getLyDoDoi())
            );
        }
    }

    public void xuLyInPhieu() {
    }
    public void xuLyDong() {
        Stage stage = (Stage) btnDong.getScene().getWindow();
        stage.close();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
