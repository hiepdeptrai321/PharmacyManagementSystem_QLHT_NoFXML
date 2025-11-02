package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKKhachHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKKhachHang.TimKiemKhachHangTrongHD_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;
import javafx.application.Application;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TimKiemKhachHangTrongHD_GUI extends Application {
    public static final String RESOURCE_BASE_PATH = "/com/example/pharmacymanagementsystem_qlht/";

    @Override
    public void start(Stage stage) {
        TimKiemKhachHangTrongHD_Ctrl ctrl = new TimKiemKhachHangTrongHD_Ctrl();
        showWithController(stage, ctrl);
    }

    public void showWithController(Stage stage, TimKiemKhachHangTrongHD_Ctrl ctrl) {
        Pane root = buildPane(ctrl);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(RESOURCE_BASE_PATH + "css/TimKiemNhanVien.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Tìm Kiếm Khách Hàng Trong Hóa Đơn");
        stage.show();
    }

    private Pane buildPane(TimKiemKhachHangTrongHD_Ctrl ctrl) {
        Pane mainPane = new Pane();
        ctrl.mainPane = mainPane;
        mainPane.setPrefSize(1646, 895);
        mainPane.setStyle("-fx-font-size: 14px;");

        VBox vbox = new VBox();
        vbox.setLayoutX(10);
        vbox.setLayoutY(14);

        // ==== HBox Tiêu đề ====
        HBox hboxTitle = new HBox();
        hboxTitle.setPrefSize(1170, 62);

        Label lblTitle = new Label("Tìm kiếm khách hàng");
        lblTitle.setPrefSize(381, 54);
        lblTitle.getStyleClass().add("title");
        lblTitle.setFont(new Font(36));

        ImageView imgTitle = new ImageView(new Image(getClass().getResourceAsStream(RESOURCE_BASE_PATH + "img/free-icon-user-group-296.png")));
        imgTitle.setFitHeight(51);
        imgTitle.setFitWidth(67);
        HBox.setMargin(imgTitle, new Insets(5, 0, 0, 0));

        hboxTitle.getChildren().addAll(lblTitle, new Label(), imgTitle);

        // ==== Separator ====
        Separator sep = new Separator();
        sep.setPrefWidth(200);

        // ==== HBox Tìm kiếm ====
        HBox hboxSearch = new HBox();
        hboxSearch.setPrefSize(1617, 65);

        ComboBox<String> cboTimKiem = new ComboBox<>();
        ctrl.cboTimKiem = cboTimKiem;
        cboTimKiem.setPromptText("Tìm kiếm theo");
        cboTimKiem.setPrefSize(265, 40);
        HBox.setMargin(cboTimKiem, new Insets(10, 5, 0, 0));

        TextField txtTimKiem = new TextField();
        ctrl.txtTimKiem = txtTimKiem;
        txtTimKiem.setPromptText("Nhập thông tin");
        txtTimKiem.setPrefSize(368, 44);
        HBox.setMargin(txtTimKiem, new Insets(10, 0, 0, 0));

        Button btnTim = new Button("Tìm");
        ctrl.btnTim = btnTim;
        btnTim.setPrefSize(73, 40);
        btnTim.setDefaultButton(true);
        HBox.setMargin(btnTim, new Insets(10, 10, 0, 10));

        ImageView iconSearch = new ImageView(new Image(getClass().getResourceAsStream(RESOURCE_BASE_PATH + "img/free-search-icon-2911-thumb.png")));
        iconSearch.setFitHeight(20);
        iconSearch.setFitWidth(34);
        btnTim.setGraphic(iconSearch);

        Button btnLamMoi = new Button();
        ctrl.btnLamMoi = btnLamMoi;
        btnLamMoi.setPrefSize(52, 40);
        btnLamMoi.getStylesheets().add(getClass().getResource(RESOURCE_BASE_PATH + "css/QuanLyThuoc.css").toExternalForm());
        HBox.setMargin(btnLamMoi, new Insets(10, 0, 0, 0));

        ImageView iconRefresh = new ImageView(new Image(getClass().getResourceAsStream(RESOURCE_BASE_PATH + "img/refresh-3104.png")));
        iconRefresh.setFitHeight(20);
        iconRefresh.setFitWidth(34);
        btnLamMoi.setGraphic(iconRefresh);

        hboxSearch.getChildren().addAll(cboTimKiem, txtTimKiem, btnTim, btnLamMoi);
        VBox.setMargin(hboxSearch, new Insets(0, 0, 10, 0));

        // ==== TableView ====
        TableView<KhachHang> tbKhachHang = new TableView<>();
        ctrl.tbKhachHang = tbKhachHang;
        tbKhachHang.setPrefSize(1615, 723);

        TableColumn<KhachHang, String> cotSTT = new TableColumn<>("STT");
        ctrl.cotSTT = cotSTT;
        cotSTT.setPrefWidth(61);
        cotSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotMaKH = new TableColumn<>("Mã KH");
        ctrl.cotMaKH = cotMaKH;
        cotMaKH.setPrefWidth(142);
        cotMaKH.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotTenKH = new TableColumn<>("Tên khách hàng");
        ctrl.cotTenKH = cotTenKH;
        cotTenKH.setPrefWidth(273);

        TableColumn<KhachHang, String> cotNgaySinh = new TableColumn<>("Ngày sinh");
        ctrl.cotNgaySinh = cotNgaySinh;
        cotNgaySinh.setPrefWidth(172);
        cotNgaySinh.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotEmail = new TableColumn<>("Email");
        ctrl.cotEmail = cotEmail;
        cotEmail.setPrefWidth(251);

        TableColumn<KhachHang, String> cotSDT = new TableColumn<>("SDT");
        ctrl.cotSDT = cotSDT;
        cotSDT.setPrefWidth(240);
        cotSDT.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotGT = new TableColumn<>("Giới tính");
        ctrl.cotGT = cotGT;
        cotGT.setPrefWidth(131);
        cotGT.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotDiaChi = new TableColumn<>("Địa chỉ");
        ctrl.cotDiaChi = cotDiaChi;
        cotDiaChi.setPrefWidth(345);

        tbKhachHang.getColumns().addAll(
                cotSTT, cotMaKH, cotTenKH, cotNgaySinh,
                cotEmail, cotSDT, cotGT, cotDiaChi
        );

        ScrollPane scrollPane = new ScrollPane(tbKhachHang);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // ==== Ghép layout ====
        vbox.getChildren().addAll(hboxTitle, sep, hboxSearch, scrollPane);
        mainPane.getChildren().add(vbox);

        return mainPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}