package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuNhapHang;

import com.example.pharmacymanagementsystem_qlht.TienIch.DoiNgay;
import com.example.pharmacymanagementsystem_qlht.dao.PhieuNhap_Dao;
import com.example.pharmacymanagementsystem_qlht.model.PhieuNhap;
import com.example.pharmacymanagementsystem_qlht.model.PhieuTraHang;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuNhapHang.ChiTietPhieuNhapHang_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuNhapHang.TKPhieuNhapHang_GUI;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;
import  java.sql.Date;

public class TimKiemPhieuNhap_Ctrl {
    public TableColumn<PhieuNhap, String> colChiTiet;
    public ComboBox cbxTimKiem;
    public TextField txtTimKiem;
    public DatePicker txtNgayNhapMax;
    public DatePicker txtNgayNhapMin;
    public ComboBox chonNhanVien;
    public ComboBox cbxChonNhaCC;
    public CheckBox cboxTrangThai;
    public TableView<PhieuNhap> tblPhieuNhap;
    public TableColumn<PhieuNhap, String> colMaPN;
    public TableColumn<PhieuNhap, String> colNhaCungCap;
    public TableColumn<PhieuNhap, String> colNgayNhap;
    public TableColumn<PhieuNhap, String> colTrangThai;
    public TableColumn<PhieuNhap, String> colGhiChu;
    public TableColumn<PhieuNhap, String> colNhanVien;
    public TitledPane tpBoLoc;
    private ObservableList<PhieuNhap> duLieuChinh = FXCollections.observableArrayList();
    private FilteredList<PhieuNhap> duLieu;
    private boolean tpBoLocOnClick = false;

    public void initialize(){
        duLieuChinh.addAll(new PhieuNhap_Dao().selectAll());
        duLieu = new FilteredList<>(duLieuChinh, sp -> true);
        tblPhieuNhap.setItems(duLieu);
        cbxTimKiem.getItems().addAll("Loại tìm kiếm", "Mã phiếu nhập", "Nhà cung cấp", "Nhân viên");
        cbxTimKiem.setValue("Loại tìm kiếm");
        txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> TimKiemtxt());
        cbxChonNhaCC.getItems().addAll(new PhieuNhap_Dao().getAllNCC());
        cbxChonNhaCC.getItems().addFirst("Chọn nhà cung cấp");
        chonNhanVien.getItems().addAll(new PhieuNhap_Dao().getAllNhanVien());
        chonNhanVien.getItems().addFirst("Chọn nhân viên");
        cbxChonNhaCC.setOnAction(event -> Loc());
        chonNhanVien.setOnAction(event -> Loc());
        tpBoLoc.setExpanded(false);
        cboxTrangThai.setOnAction(event -> Loc());
        txtNgayNhapMin.setOnAction(event -> Loc());
        txtNgayNhapMax.setOnAction(event -> Loc());
        tpBoLoc.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
            if (isNowExpanded) {
                tpBoLoc.setMinHeight(Region.USE_COMPUTED_SIZE);
                tpBoLoc.setPrefHeight(Region.USE_COMPUTED_SIZE);
            } else {
                tpBoLoc.setMaxHeight(41); // chỉ giữ phần tiêu đề
            }
            tpBoLoc.requestLayout(); // ép VBox tính lại layout
        });
        Platform.runLater(()->{
            loadTable();
        });
        tpBoLoc.setOnMouseClicked(event -> {
            tpBoLocOnClick = !tpBoLocOnClick;
            if(tpBoLocOnClick){
                tblPhieuNhap.setPrefSize(1613, 667);
            }else{
                tblPhieuNhap.setPrefSize(1613, 707);
            }
        });
    }

    public void loadTable() {
        colMaPN.setCellValueFactory(new PropertyValueFactory<>("maPN"));
        colNhaCungCap.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getNhaCungCap().getTenNCC())
        );
        colNhaCungCap.setCellFactory(col -> new TableCell<PhieuNhap, String>() {
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
        colNgayNhap.setCellValueFactory(cellData -> new SimpleStringProperty(DoiNgay.dinhDangNgay(cellData.getValue().getNgayNhap())));
        colTrangThai.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getTrangThai() ? "Hoàn tất" : "Chưa hoàn tất")
        );
        colGhiChu.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));
        colGhiChu.setCellFactory(col -> new TableCell<PhieuNhap, String>() {
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
        colNhanVien.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getNhanVien().getTenNV())
        );
        colNhanVien.setCellFactory(col -> new TableCell<PhieuNhap, String>() {
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
        colChiTiet.setCellFactory(cel -> new TableCell<PhieuNhap, String>() {
            private final Button btn = new Button("Chi tiết");
            {
                btn.setOnAction(event -> {
                    PhieuNhap temp = getTableView().getItems().get(getIndex());
                    btnChiTietClick(temp);
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
    }
    private void btnChiTietClick(PhieuNhap pn) {
        try {
            // 1) Tạo controller
            ChiTietPhieuNhap_Ctrl ctrl = new ChiTietPhieuNhap_Ctrl();

            // 2) Tạo GUI Java thuần
            ChiTietPhieuNhapHang_GUI gui = new ChiTietPhieuNhapHang_GUI();

            // 3) Tạo Stage cho cửa sổ chi tiết
            Stage dialog = new Stage();
            dialog.initOwner(txtTimKiem.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiết phiếu nhập hàng");
            dialog.getIcons().add(
                    new javafx.scene.image.Image(
                            getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png"))
            );

            // 4) Gán GUI + controller
            gui.showWithController(dialog, ctrl);

            // 5) Tải dữ liệu phiếu nhập
            ctrl.load(pn);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void TimKiemtxt() {
        String text = txtTimKiem.getText().toLowerCase();
        if (text == null || text.isEmpty()) {
            duLieu.setPredicate(sp -> true);
        } else {
            switch (cbxTimKiem.getValue().toString()) {
                case "Mã phiếu nhập":
                    duLieu.setPredicate(sp -> sp.getMaPN().toLowerCase().contains(text));
                    break;
                case "Nhà cung cấp":
                    duLieu.setPredicate(sp -> sp.getNhaCungCap().getTenNCC().toLowerCase().contains(text));
                    break;
                case "Nhân viên":
                    duLieu.setPredicate(sp -> sp.getNhanVien().getTenNV().toLowerCase().contains(text));
                    break;
                default:
                    duLieu.setPredicate(sp -> true);
                    break;
            }
        }
    }
    private void Loc() {
        String ncc = (String) cbxChonNhaCC.getValue();
        String nv = (String) chonNhanVien.getValue();
        Boolean trangThai = cboxTrangThai.isSelected() ? true : null;
        Timestamp ngayMin = txtNgayNhapMin.getValue() != null ? Timestamp.valueOf(txtNgayNhapMin.getValue().atStartOfDay()) : null;
        Timestamp ngayMax = txtNgayNhapMax.getValue() != null ? Timestamp.valueOf(txtNgayNhapMax.getValue().atStartOfDay()) : null;

        duLieu.setPredicate(sp -> {
            boolean matchesNCC = (ncc == null || ncc.equals("Chọn nhà cung cấp")) || sp.getNhaCungCap().getTenNCC().equals(ncc);
            boolean matchesNV = (nv == null || nv.equals("Chọn nhân viên")) || sp.getNhanVien().getTenNV().equals(nv);
            boolean matchesTrangThai = (trangThai == null) || sp.getTrangThai() == trangThai;
            boolean matchesNgayMin = (ngayMin == null) || !Date.valueOf(sp.getNgayNhap()).before(ngayMin);
            boolean matchesNgayMax = (ngayMax == null) || !Date.valueOf(sp.getNgayNhap()).after(ngayMax);

            return matchesNCC && matchesNV && matchesTrangThai && matchesNgayMin && matchesNgayMax;
        });
    }

    public void btnXoaRong(ActionEvent actionEvent) {
        txtTimKiem.clear();
        cbxChonNhaCC.setValue("Chọn nhà cung cấp");
        chonNhanVien.setValue("Chọn nhân viên");
        cboxTrangThai.setSelected(false);
        txtNgayNhapMin.setValue(null);
        txtNgayNhapMax.setValue(null);
        Loc();
    }
}
