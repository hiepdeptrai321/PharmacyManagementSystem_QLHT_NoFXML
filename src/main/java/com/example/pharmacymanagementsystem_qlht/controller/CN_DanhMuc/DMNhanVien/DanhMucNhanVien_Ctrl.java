package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc.SuaXoaThuoc_Ctrl;
import com.example.pharmacymanagementsystem_qlht.dao.NhanVien_Dao;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien.DanhMucNhanVien_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien.ThemNhanVien_GUI;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DanhMucNhanVien_Ctrl extends Application {

    public TableView<NhanVien> tblNhanVien;
    public TableColumn<NhanVien, String> colSTT;
    public TableColumn<NhanVien, String> colMaNV;
    public TableColumn<NhanVien, String> colTenNV;
    public TableColumn<NhanVien, String> colGioiTinh;
    public TableColumn<NhanVien, String> colSDT;
    public TableColumn<NhanVien, String> colNgaySinh;
    public TableColumn<NhanVien, String> colEmail;
    public TableColumn<NhanVien, String> colDiaChi;
    public TableColumn<NhanVien, String> colTrangThai;
    public TableColumn<NhanVien, String> colCapNhat;
    public Button btnLamMoi;
    public Button btnTim;
    public TextField txtTim;
    private NhanVien_Dao nhanVienDao = new NhanVien_Dao();


    @Override
    public void start(Stage stage) throws Exception {
        new DanhMucNhanVien_GUI().showWithController(stage, this);
    }

    public void initialize() {
        txtTim.setOnAction(e->TimKiem());
        btnTim.setOnAction(e->TimKiem());
        btnLamMoi.setOnAction(e-> LamMoi());
        loadData();
    }

    public void loadData() {
        List<NhanVien> listTemp = new NhanVien_Dao().selectAll();
        List<NhanVien> list = new ArrayList<>();
        for(NhanVien nv : listTemp){
            if(!nv.isTrangThaiXoa()){
                list.add(nv);
            }
        }
        ObservableList<NhanVien> data = FXCollections.observableList(list);


        colSTT.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(String it, boolean empty) {
                super.updateItem(it, empty);
                setText(empty ? null : Integer.toString(getIndex() + 1));
                setGraphic(null);
            }
        });

        colMaNV.setCellValueFactory(new PropertyValueFactory<>("maNV"));
        colTenNV.setCellValueFactory(new PropertyValueFactory<>("tenNV"));
        colTenNV.setCellFactory(col -> new TableCell<NhanVien, String>() {
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
        colGioiTinh.setCellValueFactory(cellData -> {
            boolean gioiTinh = cellData.getValue().isGioiTinh();
            String text = gioiTinh ? "Nữ" : "Nam";
            return new SimpleStringProperty(text);
        });
        colGioiTinh.setCellFactory(col -> new TableCell<NhanVien, String>() {
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
        colSDT.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        colNgaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setCellFactory(col -> new TableCell<NhanVien, String>() {
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
        colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        colDiaChi.setCellFactory(col -> new TableCell<NhanVien, String>() {
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
        colTrangThai.setCellValueFactory(cellData -> {
            boolean trangThai = cellData.getValue().isTrangThai();
            String text = trangThai ? "Đang làm việc" : "Đã nghỉ việc";
            return new SimpleStringProperty(text);
        });
        colTrangThai.setCellFactory(col -> new TableCell<NhanVien, String>() {
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
        colCapNhat.setCellFactory(col -> new TableCell<NhanVien, String>() {
            private final Button btn = new Button("Cập nhật");
            {
                btn.setOnAction(event -> {
                    NhanVien nhanVien = getTableView().getItems().get(getIndex());
                    btnCapNhat(nhanVien);
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
        tblNhanVien.setItems(data);
    }

    public void btnCapNhat(NhanVien nhanVien) {
        try {
            NhanVien copy = new NhanVien(nhanVien);

            var gui  = new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien.SuaXoaNhanVien_GUI();
            var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.SuaXoaNhanVien_Ctrl();

            Stage dialog = new Stage();
            dialog.initOwner(txtTim.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Cập nhật nhân viên");

            // 1) build UI + inject + initialize
            gui.showWithController(dialog, ctrl, copy);
            // 2) set parent để refresh bảng sau khi lưu/xóa
            ctrl.setParent(this);
            // 3) nạp dữ liệu thuốc (PHẢI gọi sau inject)
            ctrl.load(copy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void btnThemNhanVien(ActionEvent e) {
        try {
            var gui  = new ThemNhanVien_GUI();
            var ctrl = new ThemNhanVien_Ctrl();

            Stage dialog = new Stage();
            dialog.initOwner(txtTim.getScene().getWindow());                 // ĐẶT TRƯỚC
            dialog.setTitle("Thêm nhân viên");
            dialog.getIcons().add(new Image(getClass().getResourceAsStream(
                    "/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png"
            )));

            ctrl.setParent(this);                                            // nếu cần
            gui.showWithController(dialog, ctrl);                            // hàm này tự showAndWait()

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void TimKiem() {
        String keyword = txtTim.getText().trim().toLowerCase();
        List<NhanVien> list = nhanVienDao.selectAll();
        if (keyword.isEmpty()) {
            tblNhanVien.setItems(FXCollections.observableArrayList(list));
            return;
        }


        List<NhanVien> filtered = list.stream()
                .filter(nhanVien ->
                        (nhanVien.getMaNV() != null && nhanVien.getMaNV().toLowerCase().contains(keyword)) ||
                                (nhanVien.getTenNV() != null && nhanVien.getTenNV().toLowerCase().contains(keyword))

                )
                .toList();

        tblNhanVien.setItems(FXCollections.observableArrayList(filtered));
    }
    @FXML
    private void LamMoi() {
        txtTim.clear();
        loadData();
    }
}
