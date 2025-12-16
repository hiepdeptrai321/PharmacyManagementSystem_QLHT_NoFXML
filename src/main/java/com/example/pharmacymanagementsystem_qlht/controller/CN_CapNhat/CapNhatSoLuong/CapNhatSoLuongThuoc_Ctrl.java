package com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatSoLuong;

import com.example.pharmacymanagementsystem_qlht.TienIch.LoadingOverlay;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SP_TheoLo_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SanPham_Dao;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
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

import java.util.List;

public class CapNhatSoLuongThuoc_Ctrl extends Application {
    // 1. KHAI BÁO THÀNH PHẦN GIAO DIỆN
    public TextField tfTimThuoc;
    public Button btnTimThuoc;
    public TableView<Thuoc_SP_TheoLo> tbThuoc;
    public TableColumn<Thuoc_SP_TheoLo, String> colSTT;
    public TableColumn<Thuoc_SP_TheoLo, String> colMaThuoc;
    public TableColumn<Thuoc_SP_TheoLo, String> colTenThuoc;
    public TableColumn<Thuoc_SP_TheoLo, String> colDVT;
    public TableColumn<Thuoc_SP_TheoLo, Integer> colSLTon;
    public TableColumn<Thuoc_SP_TheoLo, String> colMaLo;
    public TableColumn<Thuoc_SP_TheoLo, String> colChiTiet;
    public Button btnLamMoi;
    // StackPane bọc TableView (khai báo trong FXML hoặc GUI code, fx:id: rootTablePane)
    public StackPane rootTablePane;
    private LoadingOverlay loadingOverlay;

    // 2. KHỞI TẠO (INITIALIZE)
    @Override
    public void start(Stage stage) throws Exception {
        new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatSoLuong.CapNhatSoLuongThuoc_GUI().showWithController(stage, this);
    }

    public void initialize() {
        tfTimThuoc.setOnAction(e-> timThuoc());
        btnLamMoi.setOnAction(e-> LamMoi());
        Platform.runLater(()->{
            loadTable();
        });
        btnTimThuoc.setOnAction(e-> timThuoc());
        loadingOverlay = new LoadingOverlay();
        // đặt overlay lên trên TableView
        rootTablePane.getChildren().add(loadingOverlay);
        tbThuoc.setPlaceholder(new Label("")); // hoặc new Label() cũng được

    }
    // 3. XỬ LÝ SỰ KIỆN GIAO DIỆN
    public void loadTable() {
        runWithLoading(() -> {
            Thuoc_SP_TheoLo_Dao dao = new Thuoc_SP_TheoLo_Dao();
            List<Thuoc_SP_TheoLo> list = dao.selectAll();
            ObservableList<Thuoc_SP_TheoLo> data = FXCollections.observableArrayList(list);

            Platform.runLater(() -> {
                colSTT.setCellValueFactory(cellData ->
                        new SimpleStringProperty(String.valueOf(tbThuoc.getItems().indexOf(cellData.getValue()) + 1))
                );
                colMaThuoc.setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getThuoc().getMaThuoc())
                );
                colTenThuoc.setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getThuoc().getTenThuoc())
                );
                colDVT.setCellValueFactory(cellData ->
                        new SimpleStringProperty(String.valueOf(
                                new Thuoc_SanPham_Dao().getTenDVTByMaThuoc(
                                        cellData.getValue().getThuoc().getMaThuoc()
                                )))
                );
                colSLTon.setCellValueFactory(new PropertyValueFactory<>("soLuongTon"));
                colMaLo.setCellValueFactory(new PropertyValueFactory<>("maLH"));

                colChiTiet.setCellFactory(col -> new TableCell<Thuoc_SP_TheoLo, String>() {
                    private final Button btn = new Button("Sửa");
                    {
                        btn.setOnAction(event -> {
                            Thuoc_SP_TheoLo thuocLo = getTableView().getItems().get(getIndex());
                            showSuaSoLuongThuoc(thuocLo);
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

                tbThuoc.setItems(data);
            });
        });
    }

    // 4. XỬ LÝ NGHIỆP VỤ
    public void timThuoc() {
        String keyword = tfTimThuoc.getText().trim().toLowerCase();
        runWithLoading(() -> {
            Thuoc_SP_TheoLo_Dao dao = new Thuoc_SP_TheoLo_Dao();
            List<Thuoc_SP_TheoLo> list = keyword.isEmpty()
                    ? dao.selectAll()
                    : dao.selectByTuKhoa(keyword);
            ObservableList<Thuoc_SP_TheoLo> data = FXCollections.observableArrayList(list);

            Platform.runLater(() -> tbThuoc.setItems(data));
        });
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


    private void showSuaSoLuongThuoc(Thuoc_SP_TheoLo thuocLo) {
        try {
            SuaSoLuongThuoc_Ctrl controller = new SuaSoLuongThuoc_Ctrl();

            // prepare stage and reload table when window closes
            Stage stage = new Stage();
            stage.setTitle("Sửa giá thuốc");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.setOnHidden(e -> loadTable());

            // show UI built in code (SuaGiaThuoc_GUI)
            new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatSoLuong.SuaSoLuongThuoc_GUI()
                    .showWithController(stage, controller);
            controller.setThuoc(thuocLo);

            tbThuoc.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LamMoi() {
        tfTimThuoc.clear();
        loadTable();
    }
}
