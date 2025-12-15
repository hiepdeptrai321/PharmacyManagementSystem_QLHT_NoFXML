package com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatGia;

import com.example.pharmacymanagementsystem_qlht.TienIch.LoadingOverlay;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SanPham_Dao;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CapNhatGiaThuoc_Ctrl extends Application {

    // 1. KHAI BÁO THÀNH PHẦN GIAO DIỆN

    public TextField tfTimThuoc;
    public Button btnTimThuoc;
    public TableView<Thuoc_SanPham> tbThuoc;
    public TableColumn<Thuoc_SanPham,String> colSTT;
    public TableColumn<Thuoc_SanPham,String> colMaThuoc;
    public TableColumn<Thuoc_SanPham,String> colTenThuoc;
    public TableColumn<Thuoc_SanPham,String> colDVT;
    public TableColumn<Thuoc_SanPham,String> colGiaNhap;
    public TableColumn<Thuoc_SanPham,String> colGiaBan;
    public TableColumn<Thuoc_SanPham,String> colChiTiet;
    public Button btnReset;

    public StackPane rootTablePane;
    private LoadingOverlay loadingOverlay;

    // 2. KHỞI TẠO (INITIALIZE)

    @Override
    public void start(Stage stage) throws Exception {
        // call the GUI helper that injects controls into this controller then initializes
        new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatGia.CapNhatGiaThuoc_GUI()
                .showWithController(stage, this);
    }

    public void initialize() {
        tfTimThuoc.setOnAction(e-> timThuoc());
        btnReset.setOnAction(e-> LamMoi());

        Platform.runLater(()->{
            loadTable();
        });
        loadingOverlay = new LoadingOverlay();
        // đặt overlay lên trên TableView
        rootTablePane.getChildren().add(loadingOverlay);
        tbThuoc.setPlaceholder(new Label("")); // hoặc new Label() cũng được
    }

    // 3. XỬ LÝ SỰ KIỆN GIAO DIỆN

    public void loadTable() {
        runWithLoading(() -> {
            Thuoc_SanPham_Dao thuocDao = new Thuoc_SanPham_Dao();
            List<Thuoc_SanPham> list = thuocDao.selectAllSLTheoDonViCoBan_ChiTietDVT_Ver2();
            ObservableList<Thuoc_SanPham> data = FXCollections.observableArrayList(list);
            Platform.runLater(()->{
                colSTT.setCellFactory(col -> new TableCell<>() {
                    @Override protected void updateItem(String it, boolean empty) {
                        super.updateItem(it, empty);
                        setText(empty ? null : Integer.toString(getIndex() + 1));
                        setGraphic(null);
                    }
                });
                colMaThuoc.setCellValueFactory(new PropertyValueFactory<>("maThuoc"));
                colTenThuoc.setCellValueFactory(new PropertyValueFactory<>("tenThuoc"));
                NumberFormat vnFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                vnFormat.setGroupingUsed(true);
                vnFormat.setMaximumFractionDigits(0);

                colGiaNhap.setCellValueFactory(cd -> {
                    Object val = cd.getValue().getDvcb().getGiaNhap();
                    if (val == null) return new SimpleStringProperty("");
                    Number num;
                    if (val instanceof Number) num = (Number) val;
                    else {
                        try { num = Double.parseDouble(val.toString()); }
                        catch (Exception e) { return new SimpleStringProperty(""); }
                    }
                    return new SimpleStringProperty(vnFormat.format(num));
                });

                colGiaBan.setCellValueFactory(cd -> {
                    Object val = cd.getValue().getDvcb().getGiaBan();
                    if (val == null) return new SimpleStringProperty("");
                    Number num;
                    if (val instanceof Number) num = (Number) val;
                    else {
                        try { num = Double.parseDouble(val.toString()); }
                        catch (Exception e) { return new SimpleStringProperty(""); }
                    }
                    return new SimpleStringProperty(vnFormat.format(num));
                });
                colDVT.setCellValueFactory(cd->{
                    String tenDVT = cd.getValue().getDvcb().getDvt().getTenDonViTinh();
                    return new SimpleStringProperty(tenDVT != null ? tenDVT : "");
                });
                colChiTiet.setCellFactory(col -> new TableCell<Thuoc_SanPham, String>() {
                    private final Button btn = new Button("Chi tiết");
                    {
                        btn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                        btn.getStyleClass().add("btn");
                        btn.setOnAction(event -> {
                            Thuoc_SanPham thuoc = getTableView().getItems().get(getIndex());
                            showSuaGiaThuoc(thuoc);
                        });
                    }
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : btn);
                    }
                });

                tbThuoc.setItems(data);
            });
        });
    }

    // 4. XỬ LÝ NGHIỆP VỤ

    public void timThuoc() {
        runWithLoading(() -> {
            String keyword = tfTimThuoc.getText().trim().toLowerCase();
            Thuoc_SanPham_Dao ts_dao = new Thuoc_SanPham_Dao();
            List<Thuoc_SanPham> dsTSLoc;
            if (keyword.isEmpty()) {
                dsTSLoc = ts_dao.selectAllSLTheoDonViCoBan_ChiTietDVT_Ver2();
            } else {
                dsTSLoc = ts_dao.selectSLTheoDonViCoBanByTuKhoa_ChiTietDVT_Ver2(keyword);
            }
            ObservableList<Thuoc_SanPham> data = FXCollections.observableArrayList(dsTSLoc);
            Platform.runLater(() -> {
                tbThuoc.setItems(data);
            });
        });
    }


    private void showSuaGiaThuoc(Thuoc_SanPham thuoc) {
        try {
            // create controller and pass the selected model
            SuaGiaThuoc_Ctrl controller = new SuaGiaThuoc_Ctrl();

            // prepare stage and reload table when window closes
            Stage stage = new Stage();
            stage.setTitle("Sửa giá thuốc");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.setOnHidden(e -> loadTable());

            // show UI built in code (SuaGiaThuoc_GUI)
            new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatGia.SuaGiaThuoc_GUI()
                    .showWithController(stage, controller);
            controller.setThuoc(thuoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runWithLoading(Runnable backgroundJob) {
        loadingOverlay.show();

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                backgroundJob.run();
                return null;
            }
        };

        task.setOnSucceeded(e -> loadingOverlay.hide());
        task.setOnFailed(e -> loadingOverlay.hide());

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    private void LamMoi() {
        tfTimThuoc.clear();
        loadTable();
    }
}