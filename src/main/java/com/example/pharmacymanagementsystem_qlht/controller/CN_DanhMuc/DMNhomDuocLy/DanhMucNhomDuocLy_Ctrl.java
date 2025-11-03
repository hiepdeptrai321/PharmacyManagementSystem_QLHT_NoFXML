package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhomDuocLy;

import com.example.pharmacymanagementsystem_qlht.dao.NhomDuocLy_Dao;
import com.example.pharmacymanagementsystem_qlht.model.NhomDuocLy;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML; // (Có thể giữ lại)
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

// Import các file GUI và Ctrl cho cửa sổ con
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhomDuocLy.ThemNhomDuocLy_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhomDuocLy.XoaSuaNhomDuocLy_GUI;


public class DanhMucNhomDuocLy_Ctrl extends Application {
    // 1. CÁC THÀNH PHẦN GIAO DIỆN (ĐÃ CHUYỂN SANG PUBLIC)
    public Button btnThem;
    public Button btnLamMoi;
    public Button btnTim;
    public Button btnXoa; // Biến này có trong code của bạn nhưng không có trong FXML
    public TableColumn<NhomDuocLy, String> cotMaNDL;
    public TableColumn<NhomDuocLy, String> cotSTT;
    public TableColumn<NhomDuocLy, String> cotTenNDL;
    public TableColumn<NhomDuocLy, String> colChiTiet;
    public TableView<NhomDuocLy> tbNhomDuocLy;
    public TextField txtTimKiem;

    private NhomDuocLy_Dao nhomDuocLyDao = new NhomDuocLy_Dao();

    // 2. KHỞI TẠO (INITIALIZE)

    @Override
    public void start(Stage stage) throws Exception {
        // --- ĐÃ THAY THẾ FXML LOADER ---
        new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhomDuocLy.DanhMucNhomDuocLy_GUI()
                .showWithController(stage, this);
    }

    // Gán sự kiện (được gọi bởi GUI)
    public void initialize() {
        loadTable();
        btnTim.setOnAction(e -> TimKiem());
        btnLamMoi.setOnAction(e -> LamMoi());
        btnThem.setOnAction(e -> btnThemClick(new NhomDuocLy()));
        txtTimKiem.setOnAction(e-> TimKiem());
    }

    // 3. XỬ LÝ SỰ KIỆN GIAO DIỆN (LOGIC GIỮ NGUYÊN)
    public void loadTable() {
        List<NhomDuocLy> list = nhomDuocLyDao.selectAll();
        ObservableList<NhomDuocLy> data = FXCollections.observableArrayList(list);
        cotSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tbNhomDuocLy.getItems().indexOf(cellData.getValue()) + 1))
        );
        cotMaNDL.setCellValueFactory(new PropertyValueFactory<>("maNDL"));
        cotTenNDL.setCellValueFactory(new PropertyValueFactory<>("tenNDL"));
        cotTenNDL.setCellFactory(col -> new TableCell<NhomDuocLy, String>() {
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
        colChiTiet.setCellFactory(col -> new TableCell<NhomDuocLy, String>() {
            private final Button btn = new Button("Chi tiết");
            {
                btn.setOnAction(event -> {
                    NhomDuocLy ndl = getTableView().getItems().get(getIndex());
                    btnChiTietClick(ndl);
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
        tbNhomDuocLy.setItems(data);

    }

    private void TimKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        List<NhomDuocLy> list = nhomDuocLyDao.selectAll();
        if (keyword.isEmpty()) {
            tbNhomDuocLy.setItems(FXCollections.observableArrayList(list));
            return;
        }


        List<NhomDuocLy> filtered = list.stream()
                .filter(NhomDuocLy ->
                        (NhomDuocLy.getMaNDL() != null && NhomDuocLy.getMaNDL().toLowerCase().contains(keyword)) ||
                                (NhomDuocLy.getTenNDL() != null && NhomDuocLy.getTenNDL().toLowerCase().contains(keyword))

                )
                .toList();
        tbNhomDuocLy.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void LamMoi() {
        txtTimKiem.clear();
        loadTable();
    }

    // --- ĐÃ CẬP NHẬT: Gọi GUI thuần ---
    public void btnThemClick(NhomDuocLy ndl) {
        try {
            Stage stage = new Stage();
            stage.initOwner(btnThem.getScene().getWindow());
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            stage.setTitle("Thêm nhóm dược lý");

            // Code GUI thuần mới
            ThemNhomDuocLy_Ctrl ctrl = new ThemNhomDuocLy_Ctrl();
            new ThemNhomDuocLy_GUI().showWithController(stage, ctrl);

            stage.showAndWait();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- ĐÃ CẬP NHẬT: Gọi GUI thuần ---
    public void btnChiTietClick(NhomDuocLy ndl) {
        try {
            Stage stage = new Stage();
            stage.initOwner(tbNhomDuocLy.getScene().getWindow());
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            stage.setTitle("Chi tiết nhóm dược lý");

            // Code GUI thuần mới
            SuaXoaNhomDuocLy ctrl = new SuaXoaNhomDuocLy();
            ctrl.hienThiThongTin(ndl); // GỌI TRƯỚC
            new XoaSuaNhomDuocLy_GUI().showWithController(stage, ctrl);

            stage.showAndWait();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}