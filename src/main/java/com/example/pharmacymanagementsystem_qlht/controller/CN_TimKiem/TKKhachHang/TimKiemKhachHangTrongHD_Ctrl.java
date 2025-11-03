package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKKhachHang;

import com.example.pharmacymanagementsystem_qlht.dao.KhachHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;
import com.example.pharmacymanagementsystem_qlht.TienIch.DoiNgay;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKKhachHang.TimKiemKhachHangTrongHD_GUI;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class TimKiemKhachHangTrongHD_Ctrl extends Application {
    // 1. KHAI BÁO THÀNH PHẦN GIAO DIỆN (FXML)
    public Button btnLamMoi;
    public Button btnTim;
    public ComboBox<String> cboTimKiem;
    public TableColumn<KhachHang, String> cotDiaChi;
    public TableColumn<KhachHang, String> cotEmail;
    public TableColumn<KhachHang, String> cotGT;
    public TableColumn<KhachHang, String> cotMaKH;
    public TableColumn<KhachHang, String> cotNgaySinh;
    public TableColumn<KhachHang, String> cotSDT;
    public TableColumn<KhachHang, String> cotTenKH;
    public TableColumn<KhachHang, String> cotSTT;
    public TableView<KhachHang> tbKhachHang;
    public Pane mainPane;
    public TextField txtTimKiem;
    private KhachHang_Dao khachHangDao = new KhachHang_Dao();
    private Consumer<KhachHang> onSelected;

    public void setOnSelected(Consumer<KhachHang> onSelected) {
        this.onSelected = onSelected;
    }
    @Override
    public void start(Stage stage) throws IOException {
        TimKiemKhachHangTrongHD_GUI gui = new TimKiemKhachHangTrongHD_GUI();
        gui.showWithController(stage, this);

    }
    // 2. KHỞI TẠO (INITIALIZE)
    public void initialize() {
        cboTimKiem.getItems().addAll(
                "Theo mã, tên khách hàng",
                "Theo email",
                "Theo SDT"

        );
        cboTimKiem.setValue("Theo mã, tên khách hàng");
        loadTable();
        btnLamMoi.setOnAction(e -> LamMoi());
        btnTim.setOnAction(e -> TimKiem());
        tbKhachHang.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            chonKhachHang(newSel);
        });
        tbKhachHang.setRowFactory(tv -> {
            TableRow<KhachHang> row = new TableRow<>();
            row.setOnMouseEntered(e -> {
                if (!row.isEmpty()) {
                    row.setStyle("-fx-background-color: #f2f2f2;");
                }
            });
            row.setOnMouseExited(e -> {
                row.setStyle("");
            });
            return row;
        });

        tbKhachHang.setRowFactory(tv -> {
            TableRow<KhachHang> row = new TableRow<>();
            row.setOnMouseEntered(e -> {
                if (!row.isEmpty()) {
                    row.setStyle("-fx-background-color: #f2f2f2;");
                }
            });
            row.setOnMouseExited(e -> {
                row.setStyle("");
            });
            return row;
        });

    }
    public void chonKhachHang(KhachHang kh) {
        if (kh == null) return;
        if (onSelected != null) onSelected.accept(kh);
        // Close this window
        if (tbKhachHang != null && tbKhachHang.getScene() != null) {
            Stage st = (Stage) tbKhachHang.getScene().getWindow();
            if (st != null) st.close();
        }
    }


    // 3. XỬ LÝ SỰ KIỆN GIAO DIỆN
    public void loadTable() {
        List<KhachHang> list = khachHangDao.selectAll();
        ObservableList<KhachHang> data = FXCollections.observableArrayList(list);
        cotSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tbKhachHang.getItems().indexOf(cellData.getValue()) + 1))
        );
        cotMaKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        cotTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
        cotGT.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        // format date as dd-MM-yyyy
        cotNgaySinh.setCellValueFactory(cellData -> new SimpleStringProperty(DoiNgay.dinhDangNgay(cellData.getValue().getNgaySinh())));
        cotSDT.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        cotEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cotDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        tbKhachHang.setItems(data);
    }
    public void LamMoi() {
        txtTimKiem.clear();
        cboTimKiem.setValue("Theo mã, tên khách hàng");
        loadTable();
    }
    public void TimKiem() {
        String criteria = cboTimKiem.getValue();
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        List<KhachHang> list = khachHangDao.selectAll();
        List<KhachHang> filtered = list.stream().filter(kh -> {
            switch (criteria) {
                case "Theo mã, tên khách hàng":
                    return kh.getMaKH().toLowerCase().contains(keyword) ||
                            kh.getTenKH().toLowerCase().contains(keyword);
                case "Theo email":
                    return kh.getEmail().toLowerCase().contains(keyword);
                case "Theo SDT":
                    return kh.getSdt().toLowerCase().contains(keyword);
                default:
                    return true;
            }
        }).toList();
        tbKhachHang.setItems(FXCollections.observableArrayList(filtered));
    }

}
