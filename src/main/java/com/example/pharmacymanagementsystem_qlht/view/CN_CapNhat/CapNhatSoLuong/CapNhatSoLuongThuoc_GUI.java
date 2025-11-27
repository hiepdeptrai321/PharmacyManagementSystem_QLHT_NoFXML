package com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatSoLuong;

import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatSoLuong.CapNhatSoLuongThuoc_Ctrl;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class CapNhatSoLuongThuoc_GUI {

    public void showWithController(Stage stage, CapNhatSoLuongThuoc_Ctrl ctrl) {
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

        Button btnTimThuoc = new Button("🔍 Tìm");
        btnTimThuoc.setId("btntim");
        btnTimThuoc.setLayoutX(789);
        btnTimThuoc.setLayoutY(59);
        btnTimThuoc.setPrefHeight(40);
        btnTimThuoc.setPrefWidth(81);
        btnTimThuoc.setStyle("-fx-font-size: 14;");

        Pane lblPaneTitle = new Pane();
        lblPaneTitle.setId("lblpaneTitle");
        lblPaneTitle.setPrefSize(1646, 40);

        Label lbTitle = new Label("Cập nhật số lượng thuốc");
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
        imgSyringe.setLayoutX(445);
        imgSyringe.setLayoutY(4);
        imgSyringe.setPickOnBounds(true);
        imgSyringe.setPreserveRatio(true);

        lblPaneTitle.getChildren().addAll(lbTitle, imgSyringe);

        TableView<Object> tbThuoc = new TableView<>();
        tbThuoc.setId("tablethuoc");
        tbThuoc.setLayoutX(12);
        tbThuoc.setLayoutY(107);
        tbThuoc.setPrefSize(1621, 780);

        TableColumn<Object, String> colSTT = new TableColumn<>("STT");
        colSTT.setPrefWidth(48.83333206176758);
        colSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colMaThuoc = new TableColumn<>("Mã thuốc");
        colMaThuoc.setPrefWidth(273);
        colMaThuoc.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colTenThuoc = new TableColumn<>("Tên thuốc");
        colTenThuoc.setPrefWidth(473);

        TableColumn<Object, String> colDVT = new TableColumn<>("Đơn vị tính (cơ bản)");
        colDVT.setPrefWidth(241);
        colDVT.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colMaLo = new TableColumn<>("Mã lô hàng");
        colMaLo.setPrefWidth(252);
        colMaLo.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colSLTon = new TableColumn<>("Số lượng tồn");
        colSLTon.setPrefWidth(213);
        colSLTon.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colChiTiet = new TableColumn<>("");
        colChiTiet.setPrefWidth(101);
        colChiTiet.setStyle("-fx-alignment: CENTER;");

        tbThuoc.getColumns().addAll(colSTT, colMaThuoc, colTenThuoc, colDVT, colMaLo, colSLTon, colChiTiet);

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

        root.getChildren().addAll(tfTimThuoc, btnTimThuoc, lblPaneTitle, tbThuoc, btnLamMoi);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css")).toExternalForm());

        // Inject controls into controller (unchecked casts for generics)
        ctrl.tfTimThuoc = tfTimThuoc;
        ctrl.btnTimThuoc = btnTimThuoc;
        ctrl.btnLamMoi = btnLamMoi;
        ctrl.tbThuoc = (TableView<Thuoc_SP_TheoLo>) (TableView<?>) tbThuoc;
        ctrl.colSTT = (TableColumn<Thuoc_SP_TheoLo, String>) (TableColumn<?, ?>) colSTT;
        ctrl.colMaThuoc = (TableColumn<Thuoc_SP_TheoLo, String>) (TableColumn<?, ?>) colMaThuoc;
        ctrl.colTenThuoc = (TableColumn<Thuoc_SP_TheoLo, String>) (TableColumn<?, ?>) colTenThuoc;
        ctrl.colDVT = (TableColumn<Thuoc_SP_TheoLo, String>) (TableColumn<?, ?>) colDVT;
        ctrl.colMaLo = (TableColumn<Thuoc_SP_TheoLo, String>) (TableColumn<?, ?>) colMaLo;
        ctrl.colSLTon = (TableColumn<Thuoc_SP_TheoLo, Integer>) (TableColumn<?, ?>) colSLTon;
        ctrl.colChiTiet = (TableColumn<Thuoc_SP_TheoLo, String>) (TableColumn<?, ?>) colChiTiet;

        // Initialize controller logic
        ctrl.initialize();

        stage.setTitle("Cập nhật số lượng thuốc");
        stage.setScene(scene);
    }
}
