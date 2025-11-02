package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhomDuocLy;

import com.example.pharmacymanagementsystem_qlht.dao.NhomDuocLy_Dao;
import com.example.pharmacymanagementsystem_qlht.model.NhomDuocLy;
// Import các file GUI mới
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhomDuocLy.DanhMucNhomDuocLy_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhomDuocLy.ThemNhomDuocLy_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhomDuocLy.XoaSuaNhomDuocLy_GUI;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML; // Giữ nguyên
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class DanhMucNhomDuocLy_Ctrl extends Application {
    // *** THAY ĐỔI 1: Bỏ 'private' ***
    @FXML
    public Button btnThem;
    @FXML
    public Button btnLamMoi;
    @FXML
    public Button btnTim;
    @FXML
    public Button btnXoa; // Biến này có khai báo nhưng FXML không có, vẫn giữ cho bạn
    @FXML
    public TableColumn<NhomDuocLy, String> cotMaNDL;
    @FXML
    public TableColumn<NhomDuocLy, String> cotSTT;
    @FXML
    public TableColumn<NhomDuocLy, String> cotTenNDL;
    @FXML
    public TableColumn<NhomDuocLy, String> colChiTiet;
    @FXML
    public TableView<NhomDuocLy> tbNhomDuocLy;
    @FXML
    public TextField txtTimKiem;

    private NhomDuocLy_Dao nhomDuocLyDao = new NhomDuocLy_Dao();

    @Override
    public void start(Stage stage) throws Exception {
        // *** THAY ĐỔI 2: Thay thế 2 dòng FXMLLoader ***
        // Parent root = FXMLLoader.load(getClass().getResource(".../DanhMucNhomDuocLy.fxml"));

        DanhMucNhomDuocLy_GUI gui = new DanhMucNhomDuocLy_GUI();
        Parent root = gui.createContent(this); // Bơm component vào 'this'

        // *** THAY ĐỔI 3: Gọi initialize() bằng tay ***
        // (Trong FXML, hàm initialize() của bạn tự loadTable(), ở đây ta làm tương tự)
        initialize();

        // Phần còn lại giữ nguyên
        Scene scene = new Scene(root);
        // CSS đã được load trong file GUI, dòng này không cần thiết nữa
        // scene.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyKeHang.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    public void initialize() {
        loadTable();
        btnTim.setOnAction(e -> TimKiem());
        btnLamMoi.setOnAction(e -> LamMoi());
        btnThem.setOnAction(e -> btnThemClick(new NhomDuocLy()));
        txtTimKiem.setOnAction(e-> TimKiem());
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
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
                if (empty) { setText(null); setGraphic(null); }
                else { setText(item); setAlignment(Pos.CENTER_LEFT); }
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

    // HÀM NÀY GIỮ NGUYÊN 100%
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

    // HÀM NÀY GIỮ NGUYÊN 100%
    @FXML
    private void LamMoi() {
        txtTimKiem.clear();
        loadTable();
    }

    // *** THAY ĐỔI 4: Sửa lại hàm mở cửa sổ con (Thêm) ***
    public void btnThemClick(NhomDuocLy ndl) {
        try {
            Stage stage = new Stage();

            // 1. Tạo Controller mới
            ThemNhomDuocLy_Ctrl ctrl = new ThemNhomDuocLy_Ctrl();
            // 2. Tạo GUI và bơm component vào controller
            ThemNhomDuocLy_GUI gui = new ThemNhomDuocLy_GUI();
            Parent root = gui.createContent(ctrl);
            // 3. Gọi initialize() của controller con
            ctrl.initialize();

            // Phần còn lại giữ nguyên
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(btnThem.getScene().getWindow()); // Thêm 2 dòng này
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL); // để làm dialog
            stage.showAndWait();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // *** THAY ĐỔI 5: Sửa lại hàm mở cửa sổ con (Chi tiết) ***
    public void btnChiTietClick(NhomDuocLy ndl) {
        try {
            Stage stage = new Stage();

            // 1. Tạo Controller mới
            SuaXoaNhomDuocLy ctrl = new SuaXoaNhomDuocLy();
            // 2. Tạo GUI và bơm component vào controller
            XoaSuaNhomDuocLy_GUI gui = new XoaSuaNhomDuocLy_GUI();
            Parent root = gui.createContent(ctrl);
            // 3. Gọi initialize() của controller con
            ctrl.initialize();

            // 4. Gọi hàm hienThiThongTin (giữ nguyên)
            ctrl.hienThiThongTin(ndl);

            // Phần còn lại giữ nguyên
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initOwner(btnThem.getScene().getWindow()); // Thêm 2 dòng này
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL); // để làm dialog
            stage.showAndWait();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}