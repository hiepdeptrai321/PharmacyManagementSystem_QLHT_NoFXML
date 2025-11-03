package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKKhachHang;

import com.example.pharmacymanagementsystem_qlht.TienIch.DoiNgay;
import com.example.pharmacymanagementsystem_qlht.dao.KhachHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML; // (Có thể giữ lại)
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class TimKiemKhachHang_Ctrl extends Application {
    // 1. CÁC THÀNH PHẦN GIAO DIỆN (ĐÃ CHUYỂN SANG PUBLIC)
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

    // 2. KHỞI TẠO (INITIALIZE)
    @FXML
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
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        // --- ĐÃ THAY THẾ FXML LOADER ---
        new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKKhachHang.TimKiemKhachHang_GUI()
                .showWithController(primaryStage, this);
    }

    // 3. XỬ LÝ SỰ KIỆN GIAO DIỆN (LOGIC GIỮ NGUYÊN)
    public void loadTable() {
        List<KhachHang> list = khachHangDao.selectAll();
        ObservableList<KhachHang> data = FXCollections.observableArrayList(list);
        cotSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tbKhachHang.getItems().indexOf(cellData.getValue()) + 1))
        );
        cotMaKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        cotTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
        cotTenKH.setCellFactory(col -> new TableCell<KhachHang, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER_LEFT);
                }
            }
        });
        cotGT.setCellValueFactory(cellData -> {
            Boolean gt = cellData.getValue().getGioiTinh(); // handles primitive or boxed boolean
            String text = Boolean.TRUE.equals(gt) ? "Nam" : "Nữ";
            return new SimpleStringProperty(text);
        });
        // format date as dd-MM-yyyy
        cotNgaySinh.setCellValueFactory(cellData -> new SimpleStringProperty(DoiNgay.dinhDangNgay(cellData.getValue().getNgaySinh())));
        cotSDT.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        cotEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cotEmail.setCellFactory(col -> new TableCell<KhachHang, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER_LEFT);
                }
            }
        });
        cotDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        cotDiaChi.setCellFactory(col -> new TableCell<KhachHang, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER_LEFT);
                }
            }
        });
        tbKhachHang.setItems(data);
    }

    @FXML
    private void LamMoi() {
        txtTimKiem.clear();
        cboTimKiem.setValue("Theo mã, tên khách hàng");
        loadTable();
    }

    @FXML
    private void TimKiem() {
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