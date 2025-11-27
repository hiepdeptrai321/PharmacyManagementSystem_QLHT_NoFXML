package com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatGia;

import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class CapNhatGiaThuoc_GUI {

    public void showWithController(Stage stage, com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatGia.CapNhatGiaThuoc_Ctrl ctrl) {
        // Build the UI (reuse the same build code but capture local controls to inject)
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14;");

        Pane lblPaneTitle = new Pane();
        lblPaneTitle.setId("lblpaneTitle");
        lblPaneTitle.setLayoutX(-1);
        lblPaneTitle.setPrefSize(1646, 53);


        Label lbTitle = new Label("Cập nhật giá thuốc");
        lbTitle.setId("lbtitle");
        lbTitle.setLayoutX(15);
        lbTitle.setLayoutY(0);
        lbTitle.setPrefSize(1646, 40);
        lbTitle.getStyleClass().add("title");
        lbTitle.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/Chung.css")
        ).toExternalForm());


        ImageView imgIncrease = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/increase.png")).toExternalForm())
        );
        imgIncrease.setFitHeight(39);
        imgIncrease.setFitWidth(47);
        imgIncrease.setLayoutX(345);
        imgIncrease.setLayoutY(4);
        imgIncrease.setPickOnBounds(true);
        imgIncrease.setPreserveRatio(true);

        lblPaneTitle.getChildren().addAll(lbTitle, imgIncrease);

        TextField tfTimThuoc = new TextField();
        tfTimThuoc.setId("tfTimThuoc");
        tfTimThuoc.setLayoutX(12);
        tfTimThuoc.setLayoutY(59);
        tfTimThuoc.setPrefSize(793, 40);
        tfTimThuoc.setPromptText("Tìm theo mã, tên thuốc");

        Button btnTimThuoc = new Button("🔍 Tìm");
        btnTimThuoc.setId("btntim");
        btnTimThuoc.setLayoutX(817);
        btnTimThuoc.setLayoutY(59);
        btnTimThuoc.setPrefSize(78, 40);
        btnTimThuoc.setStyle("-fx-font-size: 14;");
        btnTimThuoc.getStyleClass().add("btnTim");

        Button btnReset = new Button();
        btnReset.setId("btnReset");
        btnReset.setLayoutX(906);
        btnReset.setLayoutY(59);
        btnReset.setPrefSize(45, 40);
        btnReset.getStyleClass().add("btnXoaRong");

        ImageView imgRefresh = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")).toExternalForm())
        );
        imgRefresh.setFitHeight(20);
        imgRefresh.setFitWidth(35);
        imgRefresh.setPreserveRatio(true);
        imgRefresh.setPickOnBounds(true);
        btnReset.setGraphic(imgRefresh);

        TableView<Object> tbThuoc = new TableView<>();
        tbThuoc.setId("tablethuoc");
        tbThuoc.setLayoutX(12);
        tbThuoc.setLayoutY(106);
        tbThuoc.setPrefSize(1623, 781);
        tbThuoc.setStyle("-fx-font-size: 14;");

        TableColumn<Object, String> colSTT = new TableColumn<>("STT");
        colSTT.setPrefWidth(60);
        colSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colMaThuoc = new TableColumn<>("Mã thuốc");
        colMaThuoc.setPrefWidth(190);
        colMaThuoc.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colTenThuoc = new TableColumn<>("Tên thuốc");
        colTenThuoc.setPrefWidth(498);
        colTenThuoc.setStyle("-fx-alignment: CENTER_LEFT;");

        TableColumn<Object, String> colDVT = new TableColumn<>("Đơn vị tính (cơ bản)");
        colDVT.setPrefWidth(217);
        colDVT.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colGiaNhap = new TableColumn<>("Giá nhập (VNĐ)");
        colGiaNhap.setPrefWidth(272);
        colGiaNhap.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colGiaBan = new TableColumn<>("Giá bán (VNĐ)");
        colGiaBan.setPrefWidth(272);
        colGiaBan.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colChiTiet = new TableColumn<>("Chi tiết");
        colChiTiet.setPrefWidth(97);
        colChiTiet.setStyle("-fx-alignment: CENTER;");

        tbThuoc.getColumns().addAll(colSTT, colMaThuoc, colTenThuoc, colDVT, colGiaNhap, colGiaBan, colChiTiet);

        root.getChildren().addAll(lblPaneTitle, tfTimThuoc, btnTimThuoc, btnReset, tbThuoc);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css")).toExternalForm());

        // Inject controls into controller (unchecked casts due to generics mismatch)
        ctrl.tfTimThuoc = tfTimThuoc;
        ctrl.btnTimThuoc = btnTimThuoc;
        ctrl.btnReset = btnReset;
        ctrl.tbThuoc = (TableView<Thuoc_SanPham>)(TableView<?>) tbThuoc;
        ctrl.colSTT = (TableColumn<Thuoc_SanPham, String>) (TableColumn<?, ?>) colSTT;
        ctrl.colMaThuoc = (TableColumn<Thuoc_SanPham, String>) (TableColumn<?, ?>) colMaThuoc;
        ctrl.colTenThuoc = (TableColumn<Thuoc_SanPham, String>) (TableColumn<?, ?>) colTenThuoc;
        ctrl.colDVT = (TableColumn<Thuoc_SanPham, String>) (TableColumn<?, ?>) colDVT;
        ctrl.colGiaNhap = (TableColumn<Thuoc_SanPham, String>) (TableColumn<?, ?>) colGiaNhap;
        ctrl.colGiaBan = (TableColumn<Thuoc_SanPham, String>) (TableColumn<?, ?>) colGiaBan;
        ctrl.colChiTiet = (TableColumn<Thuoc_SanPham, String>) (TableColumn<?, ?>) colChiTiet;

        // Now initialize controller logic which will set listeners and load data
        ctrl.initialize();

        stage.setTitle("Cập nhật giá thuốc");
        stage.setScene(scene);
    }
}