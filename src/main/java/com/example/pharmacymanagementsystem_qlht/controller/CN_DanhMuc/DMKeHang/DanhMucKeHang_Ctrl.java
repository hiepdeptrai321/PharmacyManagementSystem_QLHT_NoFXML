package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang;

import com.example.pharmacymanagementsystem_qlht.dao.KeHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KeHang;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML; // (Có thể giữ lại, không ảnh hưởng)
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

// Import các file GUI và Ctrl cho cửa sổ con
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.ThemKe_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.XoaSuaKeHang_GUI;


public class DanhMucKeHang_Ctrl extends Application {
    // 1. CÁC THÀNH PHẦN GIAO DIỆN (ĐÃ CHUYỂN SANG PUBLIC)
    // Bỏ @FXML và private, chuyển thành public
    public Button btnThem;
    public Button btnLamMoi;
    public Button btnTim;
    public TableColumn<KeHang, String> cotMaKe;
    public TableColumn<KeHang, String> cotSTT;
    public TableColumn<KeHang, String> cotTenKe;
    public TableColumn<KeHang, String> colChiTiet;
    public TableView<KeHang> tblKeHang;
    public TextField txtTimKiem;

    private KeHang_Dao keHangDao = new KeHang_Dao();

    // 2. KHỞI TẠO (INITIALIZE)

    @Override
    public void start(Stage stage) throws Exception {
        // Thay đổi để gọi GUI mới
        new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.DanhMucKeHang_GUI()
                .showWithController(stage, this);
    }

    // Phương thức này gán sự kiện, được gọi bởi file GUI
    // SAU KHI các nút (btnTim, btnLamMoi...) đã được tiêm vào
    public void initialize() {
        btnTim.setOnAction(e -> TimKiem());
        btnLamMoi.setOnAction(e -> LamMoi());
        btnThem.setOnAction(e -> btnThemClick(new KeHang()));
        txtTimKiem.setOnAction(e -> TimKiem());
        Platform.runLater(()->{
            loadTable();
        });
    }

    // 3. XỬ LÝ SỰ KIỆN GIAO DIỆN (Giữ nguyên 100% logic của bạn)
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
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER_LEFT);
                }
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

    private void LamMoi() {
        txtTimKiem.clear();
        loadTable();
    }

    // ĐÃ CẬP NHẬT: Gọi GUI thuần
    public void btnThemClick(KeHang ke) {
        try {
            Stage dialog = new Stage();
            dialog.initOwner(btnLamMoi.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Thêm kệ hàng");
            dialog.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));

            // Code GUI thuần mới:
            ThemKe_Ctrl themCtrl = new ThemKe_Ctrl();
            new ThemKe_GUI().showWithController(dialog, themCtrl);

            dialog.showAndWait();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ĐÃ CẬP NHẬT: Gọi GUI thuần
    public void btnChiTietClick(KeHang kh) {
        try {
            Stage dialog = new Stage();
            dialog.initOwner(btnLamMoi.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiết kệ hàng");
            dialog.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));

            // Code GUI thuần mới:
            XoaSuaKeHang_Ctrl ctrl = new XoaSuaKeHang_Ctrl();
            ctrl.hienThiThongTin(kh); // Quan trọng: Phải gọi hienThiThongTin TRƯỚC khi show
            new XoaSuaKeHang_GUI().showWithController(dialog, ctrl);

            dialog.showAndWait();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}