package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKNhaCungCap;

import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import com.example.pharmacymanagementsystem_qlht.dao.NhaCungCap_Dao;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML; // (Có thể giữ lại)
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;

// Import các file GUI và Ctrl cho cửa sổ con
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKNhaCungCap.ChiTietNhaCungCap_GUI;


public class TimKiemNCC_Ctrl extends Application {
    // 1. CÁC THÀNH PHẦN GIAO DIỆN (ĐÃ CHUYỂN SANG PUBLIC)
    public ComboBox<String> cboTimKiem;
    public TextField txtTimKiem;
    public Button btnTim;
    public Button btnLamMoi;
    public TableColumn<NhaCungCap, String> cotDiaChi;
    public TableColumn<NhaCungCap, String> cotEmil;
    public TableColumn<NhaCungCap, String> cotMNCC;
    public TableColumn<NhaCungCap, String> cotSDT;
    public TableColumn<NhaCungCap, String> cotSTT;
    public TableColumn<NhaCungCap, String> cotTenNCC;
    public TableColumn<NhaCungCap, String> cotChiTiet;
    public TableView<NhaCungCap> tbNCC;

    private NhaCungCap_Dao nhaCungCapDao = new NhaCungCap_Dao();

    // 2. KHỞI TẠO (INITIALIZE)
    @FXML
    public void initialize() {
        cboTimKiem.getItems().addAll(
                "Theo mã, tên nhà cung cấp",
                "Theo email",
                "Theo SDT"
        );
        cboTimKiem.setValue("Theo mã, tên nhà cung cấp");

        // Gán sự kiện cho button (btnChiTietClick được xử lý bên dưới)
        btnTim.setOnAction(e -> TimKiem());
        btnLamMoi.setOnAction(e -> LamMoi());

        Platform.runLater(()->{
            loadTable();
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        // --- ĐÃ THAY THẾ FXML LOADER ---
        new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKNhaCungCap.TimKiemNCC_GUI()
                .showWithController(stage, this);
    }

    // 3. XỬ LÝ SỰ KIỆN GIAO DIỆN (LOGIC GIỮ NGUYÊN)
    public void loadTable() {
        List<NhaCungCap> list = nhaCungCapDao.selectAll();
        ObservableList<NhaCungCap> data = FXCollections.observableArrayList(list);
        cotSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tbNCC.getItems().indexOf(cellData.getValue()) + 1))
        );
        cotMNCC.setCellValueFactory(new PropertyValueFactory<>("maNCC"));
        cotTenNCC.setCellValueFactory(new PropertyValueFactory<>("tenNCC"));
        cotTenNCC.setCellFactory(col -> new TableCell<NhaCungCap, String>() {
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
        cotDiaChi.setCellFactory(col -> new TableCell<NhaCungCap, String>() {
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
        cotSDT.setCellValueFactory(new PropertyValueFactory<>("SDT"));
        cotEmil.setCellValueFactory(new PropertyValueFactory<>("email"));
        cotEmil.setCellFactory(col -> new TableCell<NhaCungCap, String>() {
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

        // Gán CellFactory cho cột Chi Tiết
        cotChiTiet.setCellFactory(col -> new TableCell<NhaCungCap, String>() {
            private final Button btn = new Button("Chi tiết");
            {
                btn.setOnAction(event -> {
                    NhaCungCap ncc = getTableView().getItems().get(getIndex());
                    btnChiTietClick(ncc);
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

        tbNCC.setItems(data);
    }

    // --- ĐÃ CẬP NHẬT: Gọi GUI thuần ---
    public void btnChiTietClick(NhaCungCap ncc) {
        try {
            Stage dialog = new Stage();
            dialog.initOwner(btnLamMoi.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiết nhà cung cấp");
            dialog.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));

            // Code GUI thuần mới
            ChiTietNhaCungCap_Ctrl ctrl = new ChiTietNhaCungCap_Ctrl();
            ctrl.hienThiThongTin(ncc); // GỌI TRƯỚC
            new ChiTietNhaCungCap_GUI().showWithController(dialog, ctrl);

            dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void TimKiem() {
        String criteria = cboTimKiem.getValue();
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        List<NhaCungCap> list = nhaCungCapDao.selectAll();
        List<NhaCungCap> filtered = list.stream().filter(ncc -> {
            switch (criteria) {
                case "Theo mã, tên nhà cung cấp":
                    return ncc.getMaNCC().toLowerCase().contains(keyword) ||
                            ncc.getTenNCC().toLowerCase().contains(keyword);
                case "Theo email":
                    return ncc.getEmail().toLowerCase().contains(keyword);
                case "Theo SDT":
                    return ncc.getSDT().toLowerCase().contains(keyword);
                default:
                    return true;
            }
        }).toList();
        tbNCC.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void LamMoi() {
        txtTimKiem.clear();
        cboTimKiem.setValue("Theo mã, tên nhà cung cấp");
        loadTable();
    }
}