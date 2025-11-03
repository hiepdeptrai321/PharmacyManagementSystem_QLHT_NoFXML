package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKThuoc.ChiTietThuoc_Ctrl;
import com.example.pharmacymanagementsystem_qlht.dao.KhuyenMai_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SanPham_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class DanhMucThuoc_Ctrl extends Application {

    public TableColumn<Thuoc_SanPham,String> colSTT;
    public TableColumn<Thuoc_SanPham,String> colMaThuoc;
    public TableColumn<Thuoc_SanPham,String> colTenThuoc;
    public TableColumn<Thuoc_SanPham,String> colChiTiet;
    public TableColumn<Thuoc_SanPham,String> colHamLuong;
    public TableColumn<Thuoc_SanPham,String> colSDK_GPNK;
    public TableColumn<Thuoc_SanPham,String> colXuatXu;
    public TableColumn<Thuoc_SanPham,String> colLoaiHang;
    public TableColumn<Thuoc_SanPham,String> colViTri;
    public TableView<Thuoc_SanPham> tbl_Thuoc;
    public TextField tfTimThuoc;
    public Button btnTimThuoc;
    public Button btnThemThuoc;
    public Button btnLamMoi;
    Thuoc_SanPham_Dao thuocDao = new Thuoc_SanPham_Dao();
    List<Thuoc_SanPham> list;

//  2. Khởi tạo
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/CN_DanhMuc/DMThuoc/DanhMucThuoc_GUI.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        loadTable();
        btnLamMoi.setOnAction(e-> LamMoi());
        tfTimThuoc.setOnAction(e-> timThuoc());
        btnTimThuoc.setOnAction(e-> timThuoc());
    }

//  3. Tải bảng
    public void loadTable() {
        list = thuocDao.selectAll();
        ObservableList<Thuoc_SanPham> data = FXCollections.observableArrayList(list);
        colSTT.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(String it, boolean empty) {
                super.updateItem(it, empty);
                setText(empty ? null : Integer.toString(getIndex() + 1));
                setGraphic(null);
            }
        });
        colMaThuoc.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getMaThuoc()));
        colTenThuoc.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getTenThuoc()));
        colHamLuong.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getHamLuongDonVi()));
        colSDK_GPNK.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getSDK_GPNK()));
        colXuatXu.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getNuocSX()));
        colLoaiHang.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getLoaiHang() != null ? cd.getValue().getLoaiHang().getTenLoaiHang() : ""));
        colViTri.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getVitri() != null ? cd.getValue().getVitri().getTenKe() : ""));

        colTenThuoc.setCellFactory(col -> new TableCell<Thuoc_SanPham, String>() {
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

        colHamLuong.setCellFactory(col -> new TableCell<Thuoc_SanPham, String>() {
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

        colXuatXu.setCellFactory(col -> new TableCell<Thuoc_SanPham, String>() {
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

        colLoaiHang.setCellFactory(col -> new TableCell<Thuoc_SanPham, String>() {
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

        colChiTiet.setCellFactory(col -> new TableCell<Thuoc_SanPham, String>() {
            private final Button btn = new Button("Chi tiết");
            {
                btn.setOnAction(event -> {
                    Thuoc_SanPham thuoc = getTableView().getItems().get(getIndex());
                    btnCapNhat(thuoc);
                });
                btn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                btn.getStyleClass().add("btn");
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        tbl_Thuoc.setItems(data);
    }

    public void timThuoc(){
        String keyword = tfTimThuoc.getText().trim().toLowerCase();
        Thuoc_SanPham_Dao ts_dao = new Thuoc_SanPham_Dao();
        List<Thuoc_SanPham> dsTSLoc = ts_dao.selectByTuKhoa(keyword);
        ObservableList<Thuoc_SanPham> data = FXCollections.observableArrayList(dsTSLoc);
        tbl_Thuoc.setItems(data);
    }

//  Thêm thuốc
    public void themthuoc() {
        try {
            var gui  = new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMThuoc.ThemThuoc_GUI();
            var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc.ThemThuoc_Ctrl();

            Stage dialog = new Stage();
            dialog.initOwner(tbl_Thuoc.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Thêm thuốc");

            // 1) build UI + inject + initialize
            gui.showWithController(dialog, ctrl);
            dialog.showAndWait();
            // 2) set parent để refresh bảng sau khi lưu/xóa
            ctrl.setParent(this);
            // 3) nạp dữ liệu thuốc (PHẢI gọi sau inject)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//  Mở giao diện cập nhật
    public void btnCapNhat(Thuoc_SanPham thuoc) {
    try {
        if (thuoc == null) return; // an toàn

        var gui  = new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMThuoc.SuaXoaThuoc_GUI();
        var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc.SuaXoaThuoc_Ctrl();

        Stage dialog = new Stage();
        dialog.initOwner(tbl_Thuoc.getScene().getWindow());
        dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
        dialog.setTitle("Chi tiết thuốc");

        // 1) build UI + inject + initialize
        gui.showWithController(dialog, ctrl);
        // 2) set parent để refresh bảng sau khi lưu/xóa
        ctrl.setParent(this);
        // 3) nạp dữ liệu thuốc (PHẢI gọi sau inject)
        ctrl.load(thuoc);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void refestTable(){
        list.clear();
        list = thuocDao.selectAll();
        loadTable();
    }

    @FXML
    private void LamMoi() {
        tfTimThuoc.clear();
        loadTable();
    }

    public void btnThemThuocByExcel() {
        try{
            var gui  = new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMThuoc.ThemThuocBangFileExcel_GUI();
            var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc.ThemThuocBangFileExcel();

            Stage dialog = new Stage();
            dialog.initOwner(tbl_Thuoc.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Thêm thuốc");

            // 1) build UI + inject + initialize
            gui.showWithController(dialog, ctrl);
            // 2) set parent để refresh bảng sau khi lưu/xóa
            ctrl.setDanhMucThuocCtrl(this);
            // 3) nạp dữ liệu thuốc (PHẢI gọi sau inject)
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
