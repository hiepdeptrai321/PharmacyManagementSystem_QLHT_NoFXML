package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang;

// Import file GUI mới
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.DanhMucKeHang_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.ThemKe_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.XoaSuaKeHang_GUI;
// Các import khác giữ nguyên
import com.example.pharmacymanagementsystem_qlht.dao.KeHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KeHang;
import javafx.application.Application;
import javafx.application.Platform;
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

public class DanhMucKeHang_Ctrl extends Application {

    // *** THAY ĐỔI 1: Bỏ 'private' ***
    @FXML
    public Button btnThem;
    @FXML
    public Button btnLamMoi;
    @FXML
    public Button btnTim;
    @FXML
    public Button btnXoa; //
    @FXML
    public TableColumn<KeHang, String> cotMaKe;
    @FXML
    public TableColumn<KeHang, String> cotSTT;
    @FXML
    public TableColumn<KeHang, String> cotTenKe;
    @FXML
    public TableColumn<KeHang, String> colChiTiet; // Giữ nguyên kiểu String như file của bạn
    @FXML
    public TableView<KeHang> tblKeHang;
    @FXML
    public TextField txtTimKiem;

    private KeHang_Dao keHangDao = new KeHang_Dao(); //

    @Override
    public void start(Stage stage) throws Exception {
        // *** THAY ĐỔI 2: Thay thế FXMLLoader ***
        // Parent root = FXMLLoader.load(getClass().getResource(".../DanhMucKeHang_GUI.fxml")); //

        DanhMucKeHang_GUI gui = new DanhMucKeHang_GUI();
        Parent root = gui.createContent(this); // Bơm component vào 'this'

        // *** THAY ĐỔI 3: Gọi initialize() bằng tay ***
        initialize();

        // Phần còn lại giữ nguyên
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyKeHang.css").toExternalForm()); //
        stage.setScene(scene);
        stage.show();
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    public void initialize() {
        btnTim.setOnAction(e -> TimKiem());
        btnLamMoi.setOnAction(e -> LamMoi());
        btnThem.setOnAction(e -> btnThemClick(new KeHang()));
        txtTimKiem.setOnAction(e -> TimKiem());
        Platform.runLater(()->{
            loadTable();
        });
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    public void loadTable() {
        List<KeHang> list = keHangDao.selectAll();
        ObservableList<KeHang> data = FXCollections.observableArrayList(list);
        cotSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tblKeHang.getItems().indexOf(cellData.getValue()) + 1))
        );
        cotMaKe.setCellValueFactory(new PropertyValueFactory<>("maKe"));
        cotTenKe.setCellValueFactory(new PropertyValueFactory<>("tenKe"));
        cotTenKe.setCellFactory(col -> new TableCell<KeHang, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) { setText(null); setGraphic(null); }
                else { setText(item); setAlignment(Pos.CENTER_LEFT); }
            }
        });
        colChiTiet.setCellFactory(col -> new TableCell<KeHang, String>() {
            private final Button btn = new Button("Chi tiết");
            {
                btn.setOnAction(event -> {
                    KeHang kh = getTableView().getItems().get(getIndex());
                    btnChiTietClick(kh);
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
        tblKeHang.setItems(data);
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    private void TimKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        List<KeHang> list = keHangDao.selectAll();
        if (keyword.isEmpty()) {
            tblKeHang.setItems(FXCollections.observableArrayList(list));
            return;
        }

        List<KeHang> filtered = list.stream()
                .filter(keHang ->
                        (keHang.getMaKe() != null && keHang.getMaKe().toLowerCase().contains(keyword)) ||
                                (keHang.getTenKe() != null && keHang.getTenKe().toLowerCase().contains(keyword))
                )
                .toList();
        tblKeHang.setItems(FXCollections.observableArrayList(filtered));
    }

    // HÀM NÀY GIỮ NGUYÊN 100%
    @FXML
    private void LamMoi() {
        txtTimKiem.clear();
        loadTable();
    }

    // *** THAY ĐỔI 4: Sửa lại hàm mở cửa sổ con (Thêm) ***
    public void btnThemClick(KeHang ke) {
        try {
            Stage dialog = new Stage();

            // 1. Tạo Controller mới
            ThemKe_Ctrl ctrl = new ThemKe_Ctrl();
            // 2. Tạo GUI và bơm component
            ThemKe_GUI gui = new ThemKe_GUI();
            Parent root = gui.createContent(ctrl);
            // 3. Gọi initialize() của controller con
            ctrl.initialize();

            // Phần logic còn lại giữ nguyên
            dialog.initOwner(btnLamMoi.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setScene(new Scene(root));
            dialog.setTitle("Thêm kệ hàng");
            dialog.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            dialog.showAndWait();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // *** THAY ĐỔI 5: Sửa lại hàm mở cửa sổ con (Chi tiết) ***
    public void btnChiTietClick(KeHang kh) {
        try {
            Stage dialog = new Stage();

            // 1. Tạo Controller mới
            XoaSuaKeHang_Ctrl ctrl = new XoaSuaKeHang_Ctrl();
            // 2. Tạo GUI và bơm component
            XoaSuaKeHang_GUI gui = new XoaSuaKeHang_GUI();
            Parent root = gui.createContent(ctrl);
            // 3. Gọi initialize() của controller con
            ctrl.initialize();

            // Phần logic còn lại giữ nguyên
            ctrl.hienThiThongTin(kh);
            dialog.initOwner(btnLamMoi.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setScene(new Scene(root));
            dialog.setTitle("Chi tiết kệ hàng");
            dialog.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            dialog.showAndWait();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}