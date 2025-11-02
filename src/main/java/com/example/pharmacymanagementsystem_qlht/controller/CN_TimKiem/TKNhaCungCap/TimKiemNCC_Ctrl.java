package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKNhaCungCap;

import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import com.example.pharmacymanagementsystem_qlht.dao.NhaCungCap_Dao;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKNhaCungCap.ChiTietNhaCungCap_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKNhaCungCap.TimKiemNCC_GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader; // Cần giữ lại để mở cửa sổ mới (nếu cửa sổ đó vẫn dùng FXML)
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class TimKiemNCC_Ctrl extends Application {

    // --- 1. KHAI BÁO VIEW ---
    private TimKiemNCC_GUI view;

    // --- 2. KHAI BÁO LOGIC (GIỮ NGUYÊN) ---
    private NhaCungCap_Dao nhaCungCapDao = new NhaCungCap_Dao();
    private ObservableList<NhaCungCap> listNCC = FXCollections.observableArrayList();

    // --- 3. HÀM START (SỬA ĐỔI) ---
    @Override
    public void start(Stage stage) throws Exception {
        view = new TimKiemNCC_GUI();
        Parent root = view.createContent();

        // Tải CSS
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/TimKiemNhanVien.css").toExternalForm());
        // Thêm CSS cho nút (nếu cần)
        scene.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css").toExternalForm());

        // Gọi logic
        setupLogic();

        stage.setScene(scene);
        stage.show();
    }

    // --- 4. HÀM SETUP LOGIC (Thay thế initialize) ---
    private void setupLogic() {
        // Cấu hình bảng
        view.cotSTT.setCellFactory(column -> {
            return new TableCell<NhaCungCap, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (this.getTableRow() != null && !empty) {
                        setText(Integer.toString(this.getTableRow().getIndex() + 1));
                    } else {
                        setText("");
                    }
                }
            };
        });
        view.cotMNCC.setCellValueFactory(new PropertyValueFactory<>("maNCC"));
        view.cotTenNCC.setCellValueFactory(new PropertyValueFactory<>("tenNCC"));
        view.cotEmil.setCellValueFactory(new PropertyValueFactory<>("email"));
        view.cotSDT.setCellValueFactory(new PropertyValueFactory<>("SDT"));
        view.cotDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

        // Thêm nút "Chi Tiết" vào cột
        Callback<TableColumn<NhaCungCap, Void>, TableCell<NhaCungCap, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<NhaCungCap, Void> call(final TableColumn<NhaCungCap, Void> param) {
                final TableCell<NhaCungCap, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Chi tiết");
                    {
                        btn.getStyleClass().add("btn-Sua"); // Dùng style cũ
                        btn.setOnAction(event -> {
                            NhaCungCap ncc = getTableView().getItems().get(getIndex());
                            handleRowClick(ncc);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
                return cell;
            }
        };
        view.cotChiTiet.setCellFactory(cellFactory);

        // Tải dữ liệu ban đầu
        listNCC.setAll(nhaCungCapDao.selectAll());
        view.tbNCC.setItems(listNCC);
        view.cboTimKiem.setItems(FXCollections.observableArrayList(
                "Theo mã, tên nhà cung cấp",
                "Theo email",
                "Theo SDT"
        ));
        view.cboTimKiem.setValue("Theo mã, tên nhà cung cấp");

        // Gắn sự kiện
        view.btnTim.setOnAction(e -> TimKiem());
        view.btnLamMoi.setOnAction(e -> LamMoi());
    }

    // --- 5. CÁC HÀM XỬ LÝ (SỬA ĐỔI ĐỂ DÙNG VIEW) ---
    private void handleRowClick(NhaCungCap selectedNCC) {
        try {
            // --- SỬ DỤNG LOGIC MỚI ĐỂ MỞ CỬA SỔ CHI TIẾT ---

            // 1. Tạo View
            ChiTietNhaCungCap_GUI detailView = new ChiTietNhaCungCap_GUI();
            Parent root = detailView.createContent();

            // 2. Tạo Controller và liên kết với View
            ChiTietNhaCungCap_Ctrl detailCtrl = new ChiTietNhaCungCap_Ctrl(detailView);

            // 3. Truyền dữ liệu
            detailCtrl.setNhaCungCap(selectedNCC);

            // 4. Hiển thị cửa sổ
            Stage dialog = new Stage();
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.initOwner(view.tbNCC.getScene().getWindow()); // Đặt cửa sổ cha

            Scene scene = new Scene(root);
            // Tải CSS cho cửa sổ chi tiết
            scene.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ChiTietNhaCungCap.css").toExternalForm());

            dialog.setScene(scene);
            dialog.setTitle("Chi tiết nhà cung cấp");
            dialog.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void TimKiem() {
        String criteria = view.cboTimKiem.getValue();
        String keyword = view.txtTimKiem.getText().trim().toLowerCase();
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
        view.tbNCC.setItems(FXCollections.observableArrayList(filtered));
    }

    private void LamMoi() {
        view.txtTimKiem.clear();
        view.cboTimKiem.setValue("Theo mã, tên nhà cung cấp");
        listNCC.setAll(nhaCungCapDao.selectAll());
        view.tbNCC.setItems(listNCC);
    }
}