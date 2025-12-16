package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKThuocTrongKho;

import com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatSoLuong.CapNhatSoLuongThuoc_Ctrl;
import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKThuocTrongKho.TimKiemThuocTrongKho_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class TKThuocTrongKho_GUI {

    public void showWithController(Stage stage, TimKiemThuocTrongKho_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14;");

        TextField tfTimThuoc = new TextField();
        tfTimThuoc.setId("tfTimThuoc");
        tfTimThuoc.setLayoutX(12);
        tfTimThuoc.setLayoutY(59);
        tfTimThuoc.setPrefHeight(40);
        tfTimThuoc.setPrefWidth(767);
        tfTimThuoc.setPromptText("Tìm theo mã, tên thuốc");

        ToggleButton hienThiTheoLo = new ToggleButton("Theo lô hàng");
        hienThiTheoLo.setPrefSize(220, 40);
        hienThiTheoLo.setLayoutX(1415);
        hienThiTheoLo.setLayoutY(61);
        hienThiTheoLo.setStyle(" -fx-background-color: #36983b; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-text-fill: white;");

        Button btnTimThuoc = new Button("🔍 Tìm");
        btnTimThuoc.setId("btntim");
        btnTimThuoc.setLayoutX(789);
        btnTimThuoc.setLayoutY(59);
        btnTimThuoc.setPrefHeight(40);
        btnTimThuoc.setPrefWidth(81);
        btnTimThuoc.setStyle("-fx-font-size: 14;");
        btnTimThuoc.getStyleClass().add("btnTim");
        btnTimThuoc.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/Chung.css")
        ).toExternalForm());

        Pane lblPaneTitle = new Pane();
        lblPaneTitle.setId("lblpaneTitle");
        lblPaneTitle.setPrefSize(1646, 40);

        Label lbTitle = new Label("Tìm kiếm thuốc trong kho");
        lbTitle.setId("lbtitle");
        lbTitle.setLayoutX(12);
        lbTitle.setLayoutY(2);
        lbTitle.setPrefSize(1646, 36);
        lbTitle.getStyleClass().add("title");
        lbTitle.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/Chung.css")
        ).toExternalForm());

        ImageView imgSyringe = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/boxes-11430.png")).toExternalForm())
        );

        imgSyringe.setFitHeight(50);
        imgSyringe.setFitWidth(50);
        imgSyringe.setLayoutX(475);
        imgSyringe.setLayoutY(4);
        imgSyringe.setPickOnBounds(true);
        imgSyringe.setPreserveRatio(true);

        lblPaneTitle.getChildren().addAll(lbTitle, imgSyringe);

        StackPane rootTablePane = new StackPane();
        rootTablePane.setId("rootTablePane");

        // position and size like the old table
        rootTablePane.setLayoutX(12);
        rootTablePane.setLayoutY(107);
        rootTablePane.setPrefSize(1621, 780);

        TableView<Object> tbThuoc = new TableView<>();
        tbThuoc.setId("tablethuoc");
        tbThuoc.setLayoutX(12);
        tbThuoc.setLayoutY(107);
        tbThuoc.setPrefSize(1621, 780);
        tbThuoc.setFixedCellSize(35);

        TableColumn<Object, String> colSTT = new TableColumn<>("STT");
        colSTT.setPrefWidth(48.83333206176758);
        colSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colMaThuoc = new TableColumn<>("Mã thuốc");
        colMaThuoc.setPrefWidth(273);
        colMaThuoc.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colTenThuoc = new TableColumn<>("Tên thuốc");
        colTenThuoc.setPrefWidth(473);
        colTenThuoc.setStyle("-fx-alignment: CENTER-LEFT;");

        TableColumn<Object, String> colDVT = new TableColumn<>("Đơn vị tính (cơ bản)");
        colDVT.setPrefWidth(241);
        colDVT.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colMaLo = new TableColumn<>("Mã lô hàng");
        colMaLo.setPrefWidth(252);
        colMaLo.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colSLTon = new TableColumn<>("Số lượng tồn");
        colSLTon.setPrefWidth(313);
        colSLTon.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, Integer> colSoLoTon = new TableColumn<>("Số lô tồn");
        colSoLoTon.setPrefWidth(252);
        colSoLoTon.setStyle("-fx-alignment: CENTER;");
        colSoLoTon.setVisible(false);


        tbThuoc.getColumns().addAll(colSTT, colMaThuoc, colTenThuoc, colDVT, colMaLo, colSoLoTon,colSLTon);
        rootTablePane.getChildren().add(tbThuoc);

        Button btnLamMoi = new Button();
        btnLamMoi.setId("btnLamMoi");
        btnLamMoi.setLayoutX(882);
        btnLamMoi.setLayoutY(59);
        btnLamMoi.setPrefHeight(40);
        btnLamMoi.setPrefWidth(47);
        ImageView imgRefresh = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")).toExternalForm())
        );
        imgRefresh.setFitHeight(20);
        imgRefresh.setFitWidth(34);
        imgRefresh.setPreserveRatio(true);
        imgRefresh.setPickOnBounds(true);
        btnLamMoi.setGraphic(imgRefresh);

        root.getChildren().addAll(tfTimThuoc, hienThiTheoLo,btnTimThuoc, lblPaneTitle, rootTablePane, btnLamMoi);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css")).toExternalForm());

        // Inject controls into controller (unchecked casts for generics)
        ctrl.tfTimThuoc = tfTimThuoc;
        ctrl.btnTimThuoc = btnTimThuoc;
        ctrl.btnLamMoi = btnLamMoi;
        ctrl.tbThuoc = (TableView<Object>) (TableView<?>) tbThuoc;
        ctrl.colSTT = (TableColumn<Object, String>) (TableColumn<?, ?>) colSTT;
        ctrl.colMaThuoc = (TableColumn<Object, String>) (TableColumn<?, ?>) colMaThuoc;
        ctrl.colTenThuoc = (TableColumn<Object, String>) (TableColumn<?, ?>) colTenThuoc;
        ctrl.colDVT = (TableColumn<Object, String>) (TableColumn<?, ?>) colDVT;
        ctrl.colMaLo = (TableColumn<Object, String>) (TableColumn<?, ?>) colMaLo;
        ctrl.colSLTon = (TableColumn<Object, Integer>) (TableColumn<?, ?>) colSLTon;
        ctrl.hienThiTheoLo = hienThiTheoLo;
        ctrl.colSoLoTon = (TableColumn<Object, Integer>) (TableColumn<?, ?>) colSoLoTon;
        ctrl.rootTablePane = rootTablePane;

        // Initialize controller logic
        ctrl.initialize();

        stage.setTitle("Cập nhật số lượng thuốc");
        stage.setScene(scene);
    }
}
