package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKThuocTrongKho;

import com.example.pharmacymanagementsystem_qlht.TienIch.LoadingOverlay;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SP_TheoLo_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SanPham_Dao;
import com.example.pharmacymanagementsystem_qlht.model.ThuocTonKho;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import javafx.util.Duration;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;



public class TimKiemThuocTrongKho_Ctrl {
    // 1. KHAI BÁO THÀNH PHẦN GIAO DIỆN
    public TextField tfTimThuoc;
    public Button btnTimThuoc;
    public TableView<Object> tbThuoc;
    public TableColumn<Object, String> colSTT;
    public TableColumn<Object, String> colMaThuoc;
    public TableColumn<Object, String> colTenThuoc;
    public TableColumn<Object, String> colDVT;
    public TableColumn<Object, Integer> colSLTon;
    public TableColumn<Object, String> colMaLo;
    public TableColumn<Object, String> colChiTiet;
    public Button btnLamMoi;
    public ToggleButton hienThiTheoLo;
    public TableColumn<Object, Integer> colSoLoTon;
    public TableColumn<Object, String> colNSX;
    public TableColumn<Object, String> colHSD;
    private Timeline colorTimeline;
    private Color current;
    public StackPane rootTablePane;
    private LoadingOverlay loadingOverlay;

    // 2. KHỞI TẠO (INITIALIZE)
    public void start(Stage stage) throws Exception {
        new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKThuocTrongKho.TKThuocTrongKho_GUI().showWithController(stage, this);
    }

    public void initialize() {
        tfTimThuoc.setOnAction(e-> timThuoc());
        btnLamMoi.setOnAction(e-> LamMoi());
        btnTimThuoc.setOnAction(e-> timThuoc());
        hienThiTheoLo.setOnAction(e-> onToggleHienThiTheoLo());
        hienThiTheoLo.setSelected(true);
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
            boolean isTheoLo = hienThiTheoLo != null && hienThiTheoLo.isSelected();
            Thuoc_SP_TheoLo_Dao dao = new Thuoc_SP_TheoLo_Dao();
            if (isTheoLo) {
                List<Thuoc_SP_TheoLo> list = dao.selectAll();
                ObservableList<Object> data = FXCollections.observableArrayList(list);

                Platform.runLater(()->{
                    colSTT.setCellValueFactory(cellData ->
                            new SimpleStringProperty(String.valueOf(tbThuoc.getItems().indexOf(cellData.getValue()) + 1))
                    );
                    colMaThuoc.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof Thuoc_SP_TheoLo) {
                            return new SimpleStringProperty(((Thuoc_SP_TheoLo) cellData.getValue()).getThuoc().getMaThuoc());
                        }
                        return new SimpleStringProperty("");
                    });
                    colTenThuoc.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof Thuoc_SP_TheoLo) {
                            return new SimpleStringProperty(((Thuoc_SP_TheoLo) cellData.getValue()).getThuoc().getTenThuoc());
                        }
                        return new SimpleStringProperty("");
                    });
                    colDVT.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof Thuoc_SP_TheoLo) {
                            return new SimpleStringProperty(new Thuoc_SanPham_Dao().getTenDVTByMaThuoc(((Thuoc_SP_TheoLo) cellData.getValue()).getThuoc().getMaThuoc()));
                        }
                        return new SimpleStringProperty("");
                    });
                    colMaLo.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof Thuoc_SP_TheoLo) {
                            return new SimpleStringProperty(((Thuoc_SP_TheoLo) cellData.getValue()).getMaLH());
                        }
                        return new SimpleStringProperty("");
                    });
                    colNSX.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof Thuoc_SP_TheoLo) {
                            Thuoc_SP_TheoLo lo = (Thuoc_SP_TheoLo) cellData.getValue();
                            if (lo.getNsx() != null) {
                                return new SimpleStringProperty(com.example.pharmacymanagementsystem_qlht.TienIch.DoiNgay.dinhDangNgay(lo.getNsx()));
                            }
                        }
                        return new SimpleStringProperty("");
                    });

                    colHSD.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof Thuoc_SP_TheoLo) {
                            Thuoc_SP_TheoLo lo = (Thuoc_SP_TheoLo) cellData.getValue();
                            if (lo.getHsd() != null) {
                                return new SimpleStringProperty(com.example.pharmacymanagementsystem_qlht.TienIch.DoiNgay.dinhDangNgay(lo.getHsd()));
                            }
                        }
                        return new SimpleStringProperty("");
                    });
                    colSLTon.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof Thuoc_SP_TheoLo) {
                            return new javafx.beans.property.SimpleIntegerProperty(((Thuoc_SP_TheoLo) cellData.getValue()).getSoLuongTon()).asObject();
                        }
                        return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
                    });

                    tbThuoc.setItems(data);
                });
            } else {
                List<ThuocTonKho> list = dao.getThuocTonKho();
                ObservableList<Object> data = FXCollections.observableArrayList(list);

                Platform.runLater(()->{
                    colSTT.setCellValueFactory(cellData ->
                            new SimpleStringProperty(String.valueOf(tbThuoc.getItems().indexOf(cellData.getValue()) + 1))
                    );
                    colMaThuoc.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof ThuocTonKho) {
                            return new SimpleStringProperty(((ThuocTonKho) cellData.getValue()).getMaThuoc());
                        }
                        return new SimpleStringProperty("");
                    });
                    colTenThuoc.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof ThuocTonKho) {
                            return new SimpleStringProperty(((ThuocTonKho) cellData.getValue()).getTenThuoc());
                        }
                        return new SimpleStringProperty("");
                    });
                    colDVT.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof ThuocTonKho) {
                            return new SimpleStringProperty(((ThuocTonKho) cellData.getValue()).getDonViTinh());
                        }
                        return new SimpleStringProperty("");
                    });
                    colSoLoTon.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof ThuocTonKho) {
                            return new javafx.beans.property.SimpleIntegerProperty(((ThuocTonKho) cellData.getValue()).getSoLoTon()).asObject();
                        }
                        return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
                    });
                    colSLTon.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof ThuocTonKho) {
                            return new javafx.beans.property.SimpleIntegerProperty(((ThuocTonKho) cellData.getValue()).getTongSoLuongTon()).asObject();
                        }
                        return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
                    });

                    tbThuoc.setItems(data);
                });
            }
        });
    }


    // 4. XỬ LÝ NGHIỆP VỤ
    public void timThuoc() {
        String keyword = tfTimThuoc.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {
            loadTable();
            return;
        }

        runWithLoading(() -> {
            boolean isTheoLo = hienThiTheoLo != null && hienThiTheoLo.isSelected();
            ObservableList<Object> filteredList = FXCollections.observableArrayList();

            if (isTheoLo) {
                // Tìm kiếm trong danh sách Thuoc_SP_TheoLo
                for (Object item : tbThuoc.getItems()) {
                    if (item instanceof Thuoc_SP_TheoLo) {
                        Thuoc_SP_TheoLo lo = (Thuoc_SP_TheoLo) item;
                        String maThuoc = lo.getThuoc().getMaThuoc().toLowerCase();
                        String tenThuoc = lo.getThuoc().getTenThuoc().toLowerCase();

                        if (maThuoc.contains(keyword) || tenThuoc.contains(keyword)) {
                            filteredList.add(item);
                        }
                    }
                }
            } else {
                // Tìm kiếm trong danh sách ThuocTonKho
                for (Object item : tbThuoc.getItems()) {
                    if (item instanceof ThuocTonKho) {
                        ThuocTonKho thuoc = (ThuocTonKho) item;
                        String maThuoc = thuoc.getMaThuoc().toLowerCase();
                        String tenThuoc = thuoc.getTenThuoc().toLowerCase();

                        if (maThuoc.contains(keyword) || tenThuoc.contains(keyword)) {
                            filteredList.add(item);
                        }
                    }
                }
            }

            Platform.runLater(() -> {
                tbThuoc.setItems(filteredList);
            });
        });
    }

    private void LamMoi() {

        tfTimThuoc.clear();
        loadTable();
    }

    public void onToggleHienThiTheoLo() {
        if (hienThiTheoLo == null) return;

        boolean selected = hienThiTheoLo.isSelected();
        Stage stage = (Stage) btnTimThuoc.getScene().getWindow();

        // Animation chạy ngay trên FX thread
        updateToggleAppearance(selected);

        if (selected) {
            colMaLo.setVisible(true);
            colNSX.setVisible(true);
            colHSD.setVisible(true);
            colSoLoTon.setVisible(false);
            tbThuoc.getColumns().setAll(colSTT, colMaThuoc, colTenThuoc, colDVT, colMaLo, colNSX, colHSD, colSLTon);
        } else {
            colMaLo.setVisible(false);
            colNSX.setVisible(false);
            colHSD.setVisible(false);
            colSoLoTon.setVisible(true);
            tbThuoc.getColumns().setAll(colSTT, colMaThuoc, colTenThuoc, colDVT, colSoLoTon, colSLTon);
        }
        loadTable();
    }

    private void updateToggleAppearance(boolean selected) {

        hienThiTheoLo.setText(selected ? "Theo lô hàng" : "Theo sản phẩm");
        hienThiTheoLo.setTextFill(Color.WHITE);

        Color green  = Color.web("#36983b");
        Color orange = Color.web("#ec6a02");

        Color target = selected ? green : orange;
        hienThiTheoLo.setStyle("");
        animateBackground(hienThiTheoLo, current, target);
        current = target;
    }


    private void animateBackground(Control btn, Color from, Color to) {
        if (colorTimeline != null) {
            colorTimeline.stop();
        }

        // Lấy màu hiện tại của button nếu from đang null
        if (from == null) {
            Color currentColor = null;

            if (btn.getBackground() != null
                    && !btn.getBackground().getFills().isEmpty()
                    && btn.getBackground().getFills().get(0).getFill() instanceof Color) {
                currentColor = (Color) btn.getBackground().getFills().get(0).getFill();
            }

            // Nếu vẫn chưa có màu thì cho from = to để tránh nhảy màu đột ngột
            from = currentColor != null ? currentColor : to;
        }

        ObjectProperty<Color> color = new SimpleObjectProperty<>(from);

        color.addListener((obs, oldColor, newColor) -> {
            btn.setBackground(new Background(
                    new BackgroundFill(
                            newColor,
                            new CornerRadii(25),
                            Insets.EMPTY
                    )
            ));
        });

        colorTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(color, from)),
                new KeyFrame(Duration.millis(200), new KeyValue(color, to, Interpolator.EASE_BOTH))
        );
        colorTimeline.play();
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
}
