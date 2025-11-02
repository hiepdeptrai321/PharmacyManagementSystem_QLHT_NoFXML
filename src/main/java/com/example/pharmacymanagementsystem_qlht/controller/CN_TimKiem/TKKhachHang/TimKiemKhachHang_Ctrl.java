package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKKhachHang;

import com.example.pharmacymanagementsystem_qlht.TienIch.DoiNgay;
import com.example.pharmacymanagementsystem_qlht.dao.KhachHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKKhachHang.TimKiemKhachHang_GUI;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import javafx.util.Callback;

import java.util.List;
import java.util.function.Consumer;


public class TimKiemKhachHang_Ctrl extends Application {

    // --- 1. KHAI BÁO VIEW ---
    private TimKiemKhachHang_GUI view;

    // --- 2. KHAI BÁO LOGIC (GIỮ NGUYÊN) ---
    private KhachHang_Dao khachHangDao = new KhachHang_Dao();
    private ObservableList<KhachHang> data;
    private Consumer<KhachHang> onKhachHangSelected;

    // --- 3. HÀM START (SỬA ĐỔI) ---
    @Override
    public void start(Stage stage) throws Exception {
        view = new TimKiemKhachHang_GUI();
        Parent root = view.createContent();

        Scene scene = new Scene(root);
        try {
            scene.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/TimKiemNhanVien.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Không thể tải CSS: " + e.getMessage());
        }

        setupLogic();

        stage.setScene(scene);
        stage.show();
    }

    // --- HÀM MỚI: Constructor để nhận View ---
    public TimKiemKhachHang_Ctrl(TimKiemKhachHang_GUI view) {
        this.view = view;
        setupLogic();
    }

    // --- CONSTRUCTOR MẶC ĐỊNH ---
    public TimKiemKhachHang_Ctrl() {}


    // --- 4. HÀM SETUP LOGIC (Thay thế initialize) ---
    private void setupLogic() {
        loadTable();
        view.cboTimKiem.setItems(FXCollections.observableArrayList(
                "Theo mã, tên khách hàng",
                "Theo email",
                "Theo SDT"
        ));
        view.cboTimKiem.setValue("Theo mã, tên khách hàng");

        view.btnTim.setOnAction(e -> TimKiem());
        view.btnLamMoi.setOnAction(e -> LamMoi());

        view.tbKhachHang.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                handleRowClick();
            }
        });
    }

    // --- 5. CÁC HÀM XỬ LÝ (SỬA ĐỔI ĐỂ DÙNG VIEW) ---
    public void setOnKhachHangSelected(Consumer<KhachHang> onKhachHangSelected) {
        this.onKhachHangSelected = onKhachHangSelected;
    }

    private void handleRowClick() {
        KhachHang selectedKhachHang = view.tbKhachHang.getSelectionModel().getSelectedItem();
        if (selectedKhachHang != null && onKhachHangSelected != null) {
            onKhachHangSelected.accept(selectedKhachHang);
            Stage stage = (Stage) view.tbKhachHang.getScene().getWindow();
            stage.close();
        }
    }

    private void loadTable() {
        data = FXCollections.observableArrayList(khachHangDao.selectAll());

        view.cotSTT.setCellFactory(column -> {
            return new TableCell<KhachHang, String>() {
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
        view.cotMaKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        view.cotTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
        view.cotNgaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));

        view.cotNgaySinh.setCellFactory(column -> {
            return new TableCell<KhachHang, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(DoiNgay.dinhDangNgay(item));
                    }
                }
            };
        });

        view.cotEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        view.cotSDT.setCellValueFactory(new PropertyValueFactory<>("sdt"));

        // ==========================================================
        // SỬA LỖI Ở ĐÂY
        // Thay "isGioiTinh()" thành "getGioiTinh()"
        // ==========================================================
        view.cotGT.setCellValueFactory(cellData -> {
            Boolean gioiTinh = cellData.getValue().getGioiTinh(); // SỬA DÒNG NÀY
            if (gioiTinh == null) {
                return new SimpleStringProperty("N/A");
            }
            return new SimpleStringProperty(gioiTinh ? "Nam" : "Nữ");
        });

        view.cotDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        view.cotDiaChi.setCellFactory(column -> {
            return new TableCell<KhachHang, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item);
                        setAlignment(Pos.CENTER_LEFT);
                    }
                }
            };
        });
        view.tbKhachHang.setItems(data);
    }

    private void LamMoi() {
        view.txtTimKiem.clear();
        view.cboTimKiem.setValue("Theo mã, tên khách hàng");
        loadTable();
    }

    private void TimKiem() {
        String criteria = view.cboTimKiem.getValue();
        String keyword = view.txtTimKiem.getText().trim().toLowerCase();
        List<KhachHang> list = khachHangDao.selectAll();

        List<KhachHang> filtered = list.stream().filter(kh -> {
            if (keyword.isEmpty()) return true;
            switch (criteria) {
                case "Theo mã, tên khách hàng":
                    return (kh.getMaKH() != null && kh.getMaKH().toLowerCase().contains(keyword)) ||
                            (kh.getTenKH() != null && kh.getTenKH().toLowerCase().contains(keyword));
                case "Theo email":
                    return (kh.getEmail() != null && kh.getEmail().toLowerCase().contains(keyword));
                case "Theo SDT":
                    return (kh.getSdt() != null && kh.getSdt().toLowerCase().contains(keyword));
                default:
                    return true;
            }
        }).toList();

        view.tbKhachHang.setItems(FXCollections.observableArrayList(filtered));
    }

    public static void main(String[] args) {
        launch(args);
    }
}