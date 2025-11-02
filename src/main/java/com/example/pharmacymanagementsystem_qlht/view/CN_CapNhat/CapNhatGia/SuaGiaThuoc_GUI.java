package com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatGia;

import com.example.pharmacymanagementsystem_qlht.model.ChiTietDonViTinh;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class SuaGiaThuoc_GUI {

    public void showWithController(Stage stage, com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatGia.SuaGiaThuoc_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(955, 384);
        root.setStyle("-fx-font-size: 13;");
        // Title label
        Label lbTitle = new Label("Sửa giá thuốc");
        lbTitle.setLayoutX(14);
        lbTitle.setLayoutY(8);
        lbTitle.setPrefSize(932, 31);
        lbTitle.getStyleClass().add("lbtitle");

        // Main VBox
        VBox mainVBox = new VBox();
        mainVBox.setLayoutX(14);
        mainVBox.setLayoutY(36);
        mainVBox.setPrefSize(932, 301);

        // Top pane with text fields
        Pane topPane = new Pane();
        topPane.setPrefSize(932, 70);

        TextField tfMaThuoc = new TextField();
        tfMaThuoc.setId("tfMaThuoc");
        tfMaThuoc.setLayoutX(67);
        tfMaThuoc.setLayoutY(22);
        tfMaThuoc.setPrefSize(187, 25);
        tfMaThuoc.setStyle("-fx-max-height: 30;");

        Label lbMa = new Label("Mã thuốc");
        lbMa.setLayoutY(25);

        Label lbTen = new Label("Tên thuốc");
        lbTen.setLayoutX(280);
        lbTen.setLayoutY(25);

        TextField tfTenThuoc = new TextField();
        tfTenThuoc.setId("tfTenThuoc");
        tfTenThuoc.setLayoutX(353);
        tfTenThuoc.setLayoutY(22);
        tfTenThuoc.setPrefSize(356, 25);
        tfTenThuoc.setStyle("-fx-max-height: 30;");

        Label lbLoai = new Label("Loại hàng");
        lbLoai.setLayoutX(735);
        lbLoai.setLayoutY(25);
        lbLoai.setPrefSize(69, 20);

        TextField tfLoaiHang = new TextField();
        tfLoaiHang.setId("tfLoaiHang");
        tfLoaiHang.setLayoutX(804);
        tfLoaiHang.setLayoutY(22);
        tfLoaiHang.setPrefSize(125, 25);
        tfLoaiHang.setStyle("-fx-max-height: 30;");

        topPane.getChildren().addAll(tfMaThuoc, lbMa, lbTen, tfTenThuoc, lbLoai, tfLoaiHang);

        // Info pane with button
        Pane infoPane = new Pane();
        infoPane.setStyle("-fx-max-height: 30;");
        infoPane.setPrefSize(977, 37);
        infoPane.getStyleClass().add("pnlthongtin");

        Label lbInfo = new Label("Đơn vị tính & giá bán");
        lbInfo.setLayoutY(-2);
        lbInfo.setPrefSize(942, 16);
        lbInfo.getStyleClass().add("lbtitle");

        Button btnThietLapGia = new Button("✚Thiết lập giá bán & đơn vị tính");
        btnThietLapGia.setId("btnthemdvt");
        btnThietLapGia.setLayoutX(707);
        btnThietLapGia.setLayoutY(-9);
        btnThietLapGia.setPrefSize(225, 32);
        btnThietLapGia.setMinHeight(30);

        infoPane.getChildren().addAll(lbInfo, btnThietLapGia);

        // TableView for DVT
        VBox tableBox = new VBox();
        tableBox.setPrefSize(100, 200);

        TableView<Object> tbDVT = new TableView<>();
        tbDVT.setPrefSize(924, 200);
        tbDVT.setId("tbDVT");

        TableColumn<Object, String> colDVT = new TableColumn<>("Đơn vị tính");
        colDVT.setPrefWidth(128);
        colDVT.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colKH = new TableColumn<>("Kí hiệu");
        colKH.setPrefWidth(103);
        colKH.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colHeSo = new TableColumn<>("Hệ số quy đổi");
        colHeSo.setPrefWidth(141);
        colHeSo.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colGiaNhap = new TableColumn<>("Giá nhập (VNĐ)");
        colGiaNhap.setPrefWidth(186);
        colGiaNhap.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colGiaBan = new TableColumn<>("Giá bán (VNĐ)");
        colGiaBan.setPrefWidth(189);
        colGiaBan.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colDVCB = new TableColumn<>("Đơn vị cơ bản");
        colDVCB.setPrefWidth(106);
        colDVCB.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colXoa = new TableColumn<>("");
        colXoa.setPrefWidth(78);
        colXoa.setStyle("-fx-alignment: CENTER;");

        tbDVT.getColumns().addAll(colDVT, colKH, colHeSo, colGiaNhap, colGiaBan, colDVCB, colXoa);
        tableBox.getChildren().add(tbDVT);

        // Buttons Lưu and Hủy
        Button btnLuu = new Button("Lưu");
        btnLuu.setId("btnLuu");
        btnLuu.setLayoutX(872);
        btnLuu.setLayoutY(343);
        btnLuu.getStyleClass().add("btnthemhuy");
        btnLuu.setPrefSize(69, 32);

        Button btnHuy = new Button("Hủy");
        btnHuy.setId("btnHuy");
        btnHuy.setLayoutX(793);
        btnHuy.setLayoutY(343);
        btnHuy.getStyleClass().add("btnthemhuy");
        btnHuy.setPrefSize(69, 32);

        // Tag image
        ImageView imgTag = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/tag.png")).toExternalForm())
        );
        imgTag.setFitHeight(32);
        imgTag.setFitWidth(36);
        imgTag.setLayoutX(138);
        imgTag.setLayoutY(3);
        imgTag.setPickOnBounds(true);
        imgTag.setPreserveRatio(true);

        // Assemble main VBox
        mainVBox.getChildren().addAll(topPane, infoPane, tableBox);

        // Add to root
        root.getChildren().addAll(lbTitle, mainVBox, btnLuu, btnHuy, imgTag);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css")).toExternalForm());

        // Inject controls into controller (unchecked casts where needed)
        ctrl.tfMaThuoc = tfMaThuoc;
        ctrl.tfTenThuoc = tfTenThuoc;
        ctrl.tfLoaiHang = tfLoaiHang;
        ctrl.btnThietLapGia = btnThietLapGia;
        ctrl.btnLuu = btnLuu;
        ctrl.btnHuy = btnHuy;
        ctrl.tbDVT = (TableView<ChiTietDonViTinh>)(TableView<?>) tbDVT;
        ctrl.colDVT = (TableColumn<ChiTietDonViTinh, String>) (TableColumn<?, ?>) colDVT;
        ctrl.colKH = (TableColumn<ChiTietDonViTinh, String>) (TableColumn<?, ?>) colKH;
        ctrl.colHeSo = (TableColumn<ChiTietDonViTinh, Object>) (TableColumn<?, ?>) colHeSo;
        ctrl.colGiaNhap = (TableColumn<ChiTietDonViTinh, String>) (TableColumn<?, ?>) colGiaNhap;
        ctrl.colGiaBan = (TableColumn<ChiTietDonViTinh, String>) (TableColumn<?, ?>) colGiaBan;
        ctrl.colDVCB = (TableColumn<ChiTietDonViTinh, String>) (TableColumn<?, ?>) colDVCB;
        ctrl.colXoa = (TableColumn<ChiTietDonViTinh, Void>) (TableColumn<?, ?>) colXoa;

        // Initialize controller logic
        ctrl.initialize();

        stage.setTitle("Sửa giá thuốc");
        stage.setScene(scene);
        stage.show();
    }
}
