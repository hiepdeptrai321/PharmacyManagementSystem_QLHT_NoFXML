package com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapHoaDon;

import com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapHoaDon.LapHoaDon_Ctrl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.FontWeight;

public class LapHoaDon_GUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        LapHoaDon_Ctrl ctrl = new LapHoaDon_Ctrl();
        Scene scene = new Scene(buildPane(ctrl));
        stage.setScene(scene);
        stage.setTitle("Lập Hóa Đơn");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
        stage.show();
    }

    public static void showWithController(Stage stage, LapHoaDon_Ctrl ctrl) {
        Scene scene = new Scene(new LapHoaDon_GUI().buildPane(ctrl));
        stage.setScene(scene);
        stage.setTitle("Lập Hóa Đơn");
        stage.getIcons().add(new Image(LapHoaDon_GUI.class.getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
        stage.show();
    }

    private AnchorPane buildPane(LapHoaDon_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/LapHoaDon.css").toExternalForm());

        // ======= TIÊU ĐỀ =======
        Label lblTitle = new Label("LẬP HÓA ĐƠN");
        lblTitle.getStyleClass().add("title-label");
        lblTitle.setLayoutX(40);
        lblTitle.setLayoutY(21);
        root.getChildren().add(lblTitle);

        // ======= ICON =======
        ImageView iconView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/transaction.png")));
        iconView.setLayoutX(234);
        iconView.setLayoutY(6);
        iconView.setFitHeight(53);
        iconView.setFitWidth(62);
        iconView.setPreserveRatio(true);
        root.getChildren().add(iconView);

        // ======= KHUNG TÌM SẢN PHẨM =======
        Pane searchPane = new Pane();
        searchPane.getStyleClass().add("search-pane");
        searchPane.setLayoutX(40);
        searchPane.setLayoutY(66);
        searchPane.setPrefSize(1106, 70);

        Label lblTim = new Label("Tìm sản phẩm:");
        lblTim.setLayoutX(15);
        lblTim.setLayoutY(24);
        lblTim.setFont(Font.font("System", FontWeight.BOLD, 13));

        ctrl.txtTimThuoc = new TextField();
        ctrl.txtTimThuoc.setLayoutX(118);
        ctrl.txtTimThuoc.setLayoutY(14);
        ctrl.txtTimThuoc.setPrefSize(845, 40);
        ctrl.txtTimThuoc.setPromptText("Nhập tên thuốc/ tên sản phẩm ...");

        Button btnXoaRong = new Button("Xóa rỗng");
        ctrl.btnThanhToanVaIn = btnXoaRong;
        btnXoaRong.setLayoutX(987);
        btnXoaRong.setLayoutY(14);
        btnXoaRong.setOnAction(e -> ctrl.xoaRong());

        searchPane.getChildren().addAll(lblTim, ctrl.txtTimThuoc, btnXoaRong);
        root.getChildren().add(searchPane);

        // ======= BẢNG CHI TIẾT SẢN PHẨM =======
        Label lblChiTiet = new Label("Chi tiết sản phẩm");
        lblChiTiet.getStyleClass().add("section-label");
        lblChiTiet.setLayoutX(40);
        lblChiTiet.setLayoutY(150);
        root.getChildren().add(lblChiTiet);

        ctrl.tblChiTietHD = new TableView<>();
        ctrl.tblChiTietHD.setLayoutX(40);
        ctrl.tblChiTietHD.setLayoutY(180);
        ctrl.tblChiTietHD.setPrefSize(1104, 686);
        ctrl.tblChiTietHD.setItems(FXCollections.observableArrayList());
        ctrl.tblChiTietHD.getStyleClass().add("main-table");

        ctrl.colSTT = new TableColumn<>("STT");
        ctrl.colSTT.setPrefWidth(50);
        ctrl.colTenSP = new TableColumn<>("Tên sản phẩm/Hoạt chất");
        ctrl.colTenSP.setPrefWidth(325);
        ctrl.colDonGia = new TableColumn<>("Đơn giá");
        ctrl.colDonGia.setPrefWidth(143);
        ctrl.colSL = new TableColumn<>("Số lượng");
        ctrl.colSL.setPrefWidth(131);
        ctrl.colDonVi = new TableColumn<>("Đơn vị");
        ctrl.colDonVi.setPrefWidth(100);
        ctrl.colChietKhau = new TableColumn<>("Giảm giá");
        ctrl.colChietKhau.setPrefWidth(123);
        ctrl.colThanhTien = new TableColumn<>("Thành tiền");
        ctrl.colThanhTien.setPrefWidth(174);
        ctrl.colBo = new TableColumn<>();
        ctrl.colBo.setPrefWidth(55);

        ctrl.tblChiTietHD.getColumns().addAll(ctrl.colSTT, ctrl.colTenSP, ctrl.colDonGia, ctrl.colSL, ctrl.colDonVi,
                ctrl.colChietKhau, ctrl.colThanhTien, ctrl.colBo);

        root.getChildren().add(ctrl.tblChiTietHD);

        // ======= KHUNG THÔNG TIN GIAO DỊCH =======
        Pane infoPane = new Pane();
        infoPane.getStyleClass().add("info-pane");
        infoPane.setLayoutX(1181);
        infoPane.setLayoutY(14);
        infoPane.setPrefSize(451, 867);

        Label lblTTGD = new Label("THÔNG TIN GIAO DỊCH");
        lblTTGD.getStyleClass().add("bold-label");
        lblTTGD.setLayoutX(30);
        lblTTGD.setLayoutY(186);
        infoPane.getChildren().add(lblTTGD);

        ctrl.txtTenKH = new TextField();
        ctrl.txtTenKH.setEditable(false);
        ctrl.txtTenKH.setLayoutX(191);
        ctrl.txtTenKH.setLayoutY(220);
        ctrl.txtTenKH.setPrefSize(208, 27);
        infoPane.getChildren().add(ctrl.txtTenKH);

        ctrl.txtSDT = new TextField();
        ctrl.txtSDT.setEditable(false);
        ctrl.txtSDT.setLayoutX(190);
        ctrl.txtSDT.setLayoutY(260);
        ctrl.txtSDT.setPrefSize(209, 27);
        infoPane.getChildren().add(ctrl.txtSDT);

        ctrl.txtMaDonThuoc = new TextField();
        ctrl.txtMaDonThuoc.setLayoutX(191);
        ctrl.txtMaDonThuoc.setLayoutY(351);
        ctrl.txtMaDonThuoc.setPrefSize(209, 27);
        infoPane.getChildren().add(ctrl.txtMaDonThuoc);

        ctrl.dpNgayLap = new DatePicker();
        ctrl.dpNgayLap.setLayoutX(191);
        ctrl.dpNgayLap.setLayoutY(395);
        ctrl.dpNgayLap.setPrefSize(209, 27);
        infoPane.getChildren().add(ctrl.dpNgayLap);

        ctrl.lblGiamGia = new Label("0 VNĐ");
        ctrl.lblGiamGia.getStyleClass().add("value-label");
        ctrl.lblGiamGia.setLayoutX(203);
        ctrl.lblGiamGia.setLayoutY(470);
        infoPane.getChildren().add(ctrl.lblGiamGia);

        ctrl.lblTongTien = new Label("0 VNĐ");
        ctrl.lblTongTien.getStyleClass().add("value-label");
        ctrl.lblTongTien.setLayoutX(202);
        ctrl.lblTongTien.setLayoutY(501);
        infoPane.getChildren().add(ctrl.lblTongTien);

        ctrl.lblVAT = new Label("0 VNĐ");
        ctrl.lblVAT.getStyleClass().add("value-label");
        ctrl.lblVAT.setLayoutX(202);
        ctrl.lblVAT.setLayoutY(559);
        infoPane.getChildren().add(ctrl.lblVAT);

        ctrl.lblThanhTien = new Label("0 VNĐ");
        ctrl.lblThanhTien.getStyleClass().add("main-value-label");
        ctrl.lblThanhTien.setLayoutX(200);
        ctrl.lblThanhTien.setLayoutY(601);
        infoPane.getChildren().add(ctrl.lblThanhTien);

        ctrl.cbPhuongThucTT = new ChoiceBox<>();
        ctrl.cbPhuongThucTT.setLayoutX(201);
        ctrl.cbPhuongThucTT.setLayoutY(640);
        ctrl.cbPhuongThucTT.getItems().addAll("Tiền mặt", "Chuyển khoản");
        ctrl.cbPhuongThucTT.getSelectionModel().selectFirst();
        infoPane.getChildren().add(ctrl.cbPhuongThucTT);

        ctrl.paneTienMat = new Pane();
        ctrl.paneTienMat.setLayoutX(9);
        ctrl.paneTienMat.setLayoutY(669);
        ctrl.paneTienMat.setPrefSize(414, 87);

        Label lblTienKhach = new Label("Số tiền khách đưa:");
        lblTienKhach.setLayoutX(25);
        lblTienKhach.setLayoutY(10);

        ctrl.txtSoTienKhachDua = new TextField();
        ctrl.txtSoTienKhachDua.setLayoutX(191);
        ctrl.txtSoTienKhachDua.setLayoutY(4);
        ctrl.txtSoTienKhachDua.setPrefSize(204, 27);

        Label lblTienThua = new Label("Tiền thừa:");
        lblTienThua.setLayoutX(24);
        lblTienThua.setLayoutY(40);

        ctrl.lblTienThua = new Label("0 VNĐ");
        ctrl.lblTienThua.getStyleClass().add("value-label");
        ctrl.lblTienThua.setLayoutX(191);
        ctrl.lblTienThua.setLayoutY(40);

        ctrl.paneTienMat.getChildren().addAll(lblTienKhach, ctrl.txtSoTienKhachDua, lblTienThua, ctrl.lblTienThua);
        infoPane.getChildren().add(ctrl.paneTienMat);

        ctrl.rbOTC = new RadioButton("Không kê đơn (OTC)");
        ctrl.rbOTC.setLayoutX(231);
        ctrl.rbOTC.setLayoutY(188);
        infoPane.getChildren().add(ctrl.rbOTC);

        ctrl.btnThanhToan = new Button("Thanh toán");
        ctrl.btnThanhToan.setLayoutX(9);
        ctrl.btnThanhToan.setLayoutY(755);
        ctrl.btnThanhToan.setPrefSize(427, 40);
        ctrl.btnThanhToan.setStyle("-fx-background-color: green;");
        ctrl.btnThanhToan.setOnAction(e -> ctrl.xuLyThanhToan());
        infoPane.getChildren().add(ctrl.btnThanhToan);

        ctrl.btnThemKH = new Button("Thêm khách hàng");
        ctrl.btnThemKH.setLayoutX(258);
        ctrl.btnThemKH.setLayoutY(300);
        ctrl.btnThemKH.setPrefSize(124, 30);
        ctrl.btnThemKH.setOnAction(e -> ctrl.xuLyThemKH());
        infoPane.getChildren().add(ctrl.btnThemKH);

        root.getChildren().add(infoPane);

        return root;
    }
}