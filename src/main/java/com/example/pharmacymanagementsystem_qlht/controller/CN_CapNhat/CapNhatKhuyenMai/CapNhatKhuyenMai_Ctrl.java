package com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatKhuyenMai;

import com.example.pharmacymanagementsystem_qlht.dao.ChiTietKhuyenMai_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.KhuyenMai_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SP_TangKem_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KhuyenMai;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class CapNhatKhuyenMai_Ctrl{

    // 1. KHAI BÁO THÀNH PHẦN GIAO DIỆN
    public TableView<KhuyenMai> tbKM;
    public TextField tfTimKM;
    public Button btnTimKM;
    public TableColumn<KhuyenMai, String> colChiTiet;
    public TableColumn<KhuyenMai, String> colSTT;
    public TableColumn<KhuyenMai, String> colMaKM;
    public TableColumn<KhuyenMai, String> colTenKM;
    public TableColumn<KhuyenMai, String> colLoaiKM;
    public TableColumn<KhuyenMai, Float> colGiaTri;
    public TableColumn<KhuyenMai, java.sql.Date> colNBD;
    public TableColumn<KhuyenMai, java.sql.Date> colNKT;
    public Button btnReset;
    private KhuyenMai_Dao khuyenMaiDao = new KhuyenMai_Dao();

    // 2. KHỞI TẠO (INITIALIZE)
    public void initialize() {
        tfTimKM.setOnAction(e -> timKhuyenMai());
        btnReset.setOnAction(e -> LamMoi());

        Platform.runLater(()->{
            loadTable();
        });

        btnTimKM.setOnAction(e-> timKhuyenMai());
    }

    // 3. XỬ LÝ SỰ KIỆN GIAO DIỆN

    public void loadTable() {

        List<KhuyenMai> list = khuyenMaiDao.selectAll();
        ObservableList<KhuyenMai> data = FXCollections.observableArrayList(list);
        colSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tbKM.getItems().indexOf(cellData.getValue()) + 1))
        );
        colMaKM.setCellValueFactory(new PropertyValueFactory<>("maKM"));
        colTenKM.setCellValueFactory(new PropertyValueFactory<>("tenKM"));
        colLoaiKM.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLoaiKM().getMaLoai()));
        colGiaTri.setCellValueFactory(new PropertyValueFactory<>("giaTriKM"));
        colNBD.setCellValueFactory(new PropertyValueFactory<>("ngayBatDau"));
        colNKT.setCellValueFactory(new PropertyValueFactory<>("ngayKetThuc"));
        colChiTiet.setCellFactory(col -> new TableCell<KhuyenMai, String>() {
            private final Button btn = new Button("Chi tiết");
            {
                btn.setOnAction(event -> {
                    KhuyenMai km = getTableView().getItems().get(getIndex());
                    btnChiTietClick(km);
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
        tbKM.setItems(data);
    }

    public void btnChiTietClick(KhuyenMai km) {
        try {


            SuaKhuyenMai_Ctrl ctrl = new SuaKhuyenMai_Ctrl();

            // prepare stage and reload table when window closes
            Stage stage = new Stage();
            stage.setTitle("Sửa khuyến mãi");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // show UI built in code (SuaGiaThuoc_GUI)
            new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatKhuyenMai.SuaKhuyenMai_GUI()
                    .showWithController(stage, ctrl);
            ctrl.loadData(km);
            ctrl.loadDatatbCTKM(new ChiTietKhuyenMai_Dao().selectByMaKM(km.getMaKM()));
            if("LKM001".equalsIgnoreCase(km.getLoaiKM().getMaLoai()))
                ctrl.loadDatatbQuaTang(new Thuoc_SP_TangKem_Dao().selectByMaKM(km.getMaKM()));
            stage.setResizable(true); // must be true to allow sizeToScene to change size

            stage.show();

            stage.setOnHidden(e -> loadTable());

            Platform.runLater(stage::sizeToScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void timKhuyenMai(){
        String keyword = tfTimKM.getText().trim().toLowerCase();
        KhuyenMai_Dao km_dao = new KhuyenMai_Dao();
        List<KhuyenMai> dsKMLoc = km_dao.selectByTuKhoa(keyword);
        ObservableList<KhuyenMai> data = FXCollections.observableArrayList(dsKMLoc);
        tbKM.setItems(data);
    }

    private void LamMoi() {
        tfTimKM.clear();
        loadTable();
    }

}
