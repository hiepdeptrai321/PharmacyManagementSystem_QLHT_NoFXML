package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhaCungCap;

import com.example.pharmacymanagementsystem_qlht.dao.NhaCungCap_Dao;
import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatGia.CapNhatGiaThuoc_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNCC.DanhMucNhaCungCap_GUI;
import javafx.application.Application;
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

import java.util.List;
import java.util.Objects;

public class DanhMucNhaCungCap_Ctrl extends Application {

    public TableColumn colChiTietNhaCungCap;
    public TableColumn<NhaCungCap, String> colChiTiet;
    public TableView<NhaCungCap> tblNhaCungCap;
    public TableColumn<NhaCungCap, String> colMaNCC;
    public TableColumn<NhaCungCap, String> colTenNCC;
    public TableColumn<NhaCungCap, String> colDiaChi;
    public TableColumn<NhaCungCap, String> colSDT;
    public TableColumn<NhaCungCap, String> colEmail;
    public TableColumn<NhaCungCap, String> colGhiChu;
    private TableColumn<NhaCungCap, String> colTenCongTy;
    public TableColumn<NhaCungCap, String> colSTT;
    public TextField txtTimKiem;
    public Button btnLamMoi;
    public Button btnThemNCC;
    private NhaCungCap_Dao nhaCungCapDao =  new NhaCungCap_Dao();
    public Button btnTim;


//  Phương thức khởi tạo
    @FXML
    public void initialize() {
        loadNhaCungCap();
        btnLamMoi.setOnAction(e-> LamMoi());
        btnTim.setOnAction(e-> TimKiem());
        txtTimKiem.setOnAction(e-> TimKiem());
        btnThemNCC.setOnAction(e -> btnThemNCC());
    }

//  Load nhà cung cấp vào bảng
    public void loadNhaCungCap() {
        List<NhaCungCap> list = new NhaCungCap_Dao().selectAll();
        ObservableList<NhaCungCap> data = FXCollections.observableArrayList(list);

        colSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tblNhaCungCap.getItems().indexOf(cellData.getValue()) + 1))
        );
        colMaNCC.setCellValueFactory(new PropertyValueFactory<>("maNCC"));
        colTenNCC.setCellValueFactory(new PropertyValueFactory<>("tenNCC"));
        colTenNCC.setCellFactory(col -> new TableCell<NhaCungCap, String>() {
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
        colSDT.setCellValueFactory(new PropertyValueFactory<>("SDT"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colGhiChu.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));
        colChiTiet.setCellFactory(cel-> new TableCell<NhaCungCap, String>(){
            private final Button btn = new Button("Chi tiết");
            {
//              Thêm sự kiện cho Button chi tiết
                btn.setOnAction(event -> {
                    NhaCungCap ncc = getTableView().getItems().get(getIndex());
                    suaXoaNhaCungCap(ncc);
                });
                btn.setStyle("-fx-text-fill: white;-fx-background-color: #218cff;");
            }
//          Thêm button vào cột chi tiết
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        tblNhaCungCap.setItems(data);
    }

//  Nhớ xóa
    @Override
    public void start(Stage stage) throws Exception {
        new DanhMucNhaCungCap_GUI()
                .showWithController(stage, this);
    }

//  Button mở giao diện sửa xóa nhà cung cấp
private void suaXoaNhaCungCap(NhaCungCap ncc) {
    try {
        // Tạo GUI view
        var gui  = new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNCC.SuaXoaNhaCungCap_GUI();

        // Tạo controller tương ứng
        var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhaCungCap.SuaXoaNhaCungCap_Ctrl();

        // Truyền controller cha (để reload danh sách)
        ctrl.setDanhMucNhaCungCap_ctrl(this);

        // Truyền NCC để form fill dữ liệu
        ctrl.loadData(ncc);

        // Tạo stage
        Stage dialog = new Stage();
        dialog.initOwner(btnLamMoi.getScene().getWindow());
        dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
        dialog.setTitle("Chi tiết nhà cung cấp");

        // Show thông qua hàm view
        gui.showWithController(dialog, ctrl);

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    //  Button mở giao diện thêm nhà cung cấp
    public void btnThemNCC() {
        try {
            var gui = new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNCC.ThemNhaCungCap_GUI();

            var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhaCungCap.ThemNhaCungCap_Ctrl();

//          Thêm dữ liệu ctrl cha vào ctrl thêm
            ctrl.setParentCtrl(this);

            Stage dialog = new Stage();
            dialog.initOwner(btnLamMoi.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Thêm nhà cung cấp");

            gui.showWithController(dialog, ctrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void TimKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        List<NhaCungCap> list = nhaCungCapDao.selectAll();
        if (keyword.isEmpty()) {
            tblNhaCungCap.setItems(FXCollections.observableArrayList(list));
            return;
        }


        List<NhaCungCap> filtered = list.stream()
                .filter(ncc ->
                        (ncc.getMaNCC() != null && ncc.getMaNCC().toLowerCase().contains(keyword)) ||
                                (ncc.getTenNCC() != null && ncc.getTenNCC().toLowerCase().contains(keyword))

                )
                .toList();

        tblNhaCungCap.setItems(FXCollections.observableArrayList(filtered));
    }

    public void refreshTable() {
        loadNhaCungCap();
    }

    private void LamMoi() {
        txtTimKiem.clear();
        loadNhaCungCap();
    }
}
