package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKHoaDon;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon.TimKiemHoaDon_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.HoaDon;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.Objects;

/**
 * GUI Java thuần cho màn "Tìm kiếm hóa đơn"
 * - Có showWithController(Stage, Ctrl) để mở riêng khi cần
 * - Có buildForEmbed(Ctrl) để NHÚNG trực tiếp vào pnlChung (không mở dialog)
 */
public class TKHoaDon_GUI extends Application {

    // ===== Dùng khi chạy độc lập để test nhanh =====
    @Override
    public void start(Stage stage) {
        ViewRefs v = buildUIForController();

        // test không controller: chỉ hiển thị form
        Scene scene = new Scene(v.root, 1646, 895);
        addStyles(scene, "/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css");
        stage.setTitle("Tìm kiếm hóa đơn");
        stage.setScene(scene);
        stage.show();
    }

    // ===== Dùng trong app: mở ở Stage riêng (nếu thật sự muốn mở cửa sổ) =====
    public void showWithController(Stage stage, TimKiemHoaDon_Ctrl ctrl) {
        Parent root = buildForEmbed(ctrl); // tái sử dụng buildForEmbed
        Scene scene = new Scene(root, 1646, 895);
        addStyles(scene, "/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css");
        stage.setTitle("Tìm kiếm hóa đơn");
        stage.setScene(scene);
        // stage.show(); // để caller quyết định có show hay không
    }

    // ===== Quan trọng: dùng để NHÚNG vào pnlChung (không mở dialog mới) =====
    public Parent buildForEmbed(TimKiemHoaDon_Ctrl ctrl) {
        ViewRefs v = buildUIForController();

        // inject control sang controller – khớp fx:id trong FXML cũ
        ctrl.cboTieuChiTimKiem = v.cboTieuChiTimKiem;
        ctrl.txtNoiDungTimKiem = v.txtNoiDungTimKiem;
        ctrl.dpTuNgay         = v.dpTuNgay;
        ctrl.dpDenNgay        = v.dpDenNgay;
        ctrl.cboBoLocNhanh    = v.cboBoLocNhanh;

        ctrl.btnTimKiem       = v.btnTimKiem;
        ctrl.btnHuyBo         = v.btnHuyBo;

        ctrl.tblHD            = v.tblHD;
        ctrl.colMaHD          = v.colMaHD;
        ctrl.colNgayLap       = v.colNgayLap;
        ctrl.colTenKH         = v.colTenKH;
        ctrl.colSdtKH         = v.colSdtKH;
        ctrl.colTenNV         = v.colTenNV;
        ctrl.colSLP           = v.colSLP;
        ctrl.colChiTiet       = v.colChiTiet;

        // nếu controller có initialize() thì gọi
        try { ctrl.initialize(); } catch (Exception ignore) {}

        return v.root;
    }

    // ====== Builder UI: tạo toàn bộ node & tham chiếu control ======
    private ViewRefs buildUIForController() {
        ViewRefs v = new ViewRefs();

        // root = Pane + VBox giống FXML
        Pane root = new Pane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14;");

        VBox vb = new VBox();
        vb.setLayoutX(10);
        vb.setLayoutY(14);
        vb.setPrefSize(1619, 871);

        // ===== HBox Title =====
        HBox hbTitle = new HBox();
        hbTitle.setPrefSize(1544, 58);

        Label lbTimKiem = new Label("Tìm kiếm hóa đơn");
        lbTimKiem.setPrefSize(328, 53);
        lbTimKiem.getStyleClass().add("title");
        lbTimKiem.setFont(Font.font(36));

        Region rgTitleSpacer = new Region();
        HBox.setHgrow(rgTitleSpacer, Priority.ALWAYS);

        ImageView ivBill = imageView("/com/example/pharmacymanagementsystem_qlht/img/bill-8854.png", 48, 46);

        hbTitle.getChildren().addAll(lbTimKiem, new Label(), ivBill);

        Separator sp = new Separator();

        // ===== HBox filter line =====
        HBox hbFilter = new HBox();
        hbFilter.setPrefSize(1618, 63);

        v.cboTieuChiTimKiem = new ComboBox<>();
        v.cboTieuChiTimKiem.setPromptText("Tiêu chí tìm kiếm");
        v.cboTieuChiTimKiem.getStyleClass().add("btntim");
        v.cboTieuChiTimKiem.setPrefSize(184, 40);
        HBox.setMargin(v.cboTieuChiTimKiem, new Insets(10, 5, 0, 0));

        v.txtNoiDungTimKiem = new TextField();
        v.txtNoiDungTimKiem.setPromptText("Nhập nội dung tìm kiếm");
        v.txtNoiDungTimKiem.getStyleClass().add("tftim");
        v.txtNoiDungTimKiem.setPrefSize(260, 40);
        HBox.setMargin(v.txtNoiDungTimKiem, new Insets(10, 0, 0, 0));

        Region rg1 = new Region(); rg1.setPrefSize(32, 63);

        ImageView ivTime = imageView("/com/example/pharmacymanagementsystem_qlht/img/time-2623.png", 30, 34);
        HBox.setMargin(ivTime, new Insets(16, 4, 0, 0));

        Label lbTu = new Label("Từ: ");
        lbTu.getStyleClass().add("tftim");
        lbTu.setPrefSize(32, 37);
        lbTu.setFont(Font.font(14));
        HBox.setMargin(lbTu, new Insets(10, 0, 0, 0));

        v.dpTuNgay = new DatePicker();
        v.dpTuNgay.getStyleClass().add("tftim");
        v.dpTuNgay.setPrefSize(125, 40);
        HBox.setMargin(v.dpTuNgay, new Insets(10, 10, 0, 0));

        Label lbDen = new Label("Đến: ");
        lbDen.getStyleClass().add("tftim");
        lbDen.setPrefSize(38, 37);
        lbDen.setFont(Font.font(14));
        HBox.setMargin(lbDen, new Insets(10, 0, 0, 0));

        v.dpDenNgay = new DatePicker();
        v.dpDenNgay.getStyleClass().add("tftim");
        v.dpDenNgay.setPrefSize(125, 40);
        HBox.setMargin(v.dpDenNgay, new Insets(10, 0, 0, 0));

        Region rg2 = new Region(); rg2.setPrefSize(25, 63);

        v.btnTimKiem = new Button("Tìm");
        v.btnTimKiem.setDefaultButton(true);
        v.btnTimKiem.getStyleClass().add("btntim");
        v.btnTimKiem.setPrefSize(72, 40);
        HBox.setMargin(v.btnTimKiem, new Insets(10, 0, 0, 8));
        v.btnTimKiem.setGraphic(imageView("/com/example/pharmacymanagementsystem_qlht/img/free-search-icon-2911-thumb.png", 150, 20));

        v.btnHuyBo = new Button();
        v.btnHuyBo.setId("btnReset");
        v.btnHuyBo.getStyleClass().add("btntim");
        v.btnHuyBo.setPrefSize(52, 40);
        HBox.setMargin(v.btnHuyBo, new Insets(10, 0, 0, 8));
        v.btnHuyBo.setGraphic(imageView("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png", 40, 20));

        Region rg3 = new Region(); rg3.setPrefSize(395, 63);
        HBox.setHgrow(rg3, Priority.ALWAYS);

        v.cboBoLocNhanh = new ComboBox<>();
        v.cboBoLocNhanh.setPromptText("⌛ Bộ lọc nhanh");
        v.cboBoLocNhanh.getStyleClass().add("btntim");
        v.cboBoLocNhanh.setPrefSize(222, 41);
        HBox.setMargin(v.cboBoLocNhanh, new Insets(10, 0, 0, 0));

        hbFilter.getChildren().addAll(
                v.cboTieuChiTimKiem, v.txtNoiDungTimKiem,
                rg1, ivTime, lbTu, v.dpTuNgay, lbDen, v.dpDenNgay,
                rg2, v.btnTimKiem, v.btnHuyBo,
                rg3, v.cboBoLocNhanh
        );

        VBox.setMargin(hbFilter, new Insets(0, 0, 5, 0));

        // ===== Bảng kết quả =====
        v.tblHD = new TableView<>();
        v.tblHD.setPrefSize(1619, 735);
        v.tblHD.setStyle("-fx-font-size: 14;");

        v.colMaHD    = new TableColumn<>("Mã hóa đơn");
        v.colMaHD.setPrefWidth(162.0);
        v.colMaHD.setStyle("-fx-alignment: CENTER;");

        v.colNgayLap = new TableColumn<>("Ngày lập");
        v.colNgayLap.setPrefWidth(255.0);
        v.colNgayLap.setStyle("-fx-alignment: CENTER;");

        v.colTenKH   = new TableColumn<>("Khách hàng");
        v.colTenKH.setPrefWidth(351.0);

        v.colSdtKH   = new TableColumn<>("SĐT");
        v.colSdtKH.setPrefWidth(229.6);
        v.colSdtKH.setStyle("-fx-alignment: CENTER;");

        v.colTenNV   = new TableColumn<>("Nhân viên");
        v.colTenNV.setPrefWidth(293.0);

        v.colSLP     = new TableColumn<>("Số lượng phiếu đổi trả");
        v.colSLP.setPrefWidth(205.0);
        v.colSLP.setStyle("-fx-alignment: CENTER;");

        v.colChiTiet = new TableColumn<>("");
        v.colChiTiet.setPrefWidth(105.0);
        v.colChiTiet.setStyle("-fx-alignment: CENTER;");

        v.tblHD.getColumns().addAll(
                v.colMaHD, v.colNgayLap, v.colTenKH, v.colSdtKH, v.colTenNV, v.colSLP, v.colChiTiet
        );

        // gom vào VBox
        vb.getChildren().addAll(hbTitle, sp, hbFilter, v.tblHD);

        // icon “icontime” rời (nếu cần)
        ImageView iconTimeFloat = new ImageView();
        iconTimeFloat.getStyleClass().add("icontime");
        iconTimeFloat.setFitWidth(40);
        iconTimeFloat.setFitHeight(35);
        iconTimeFloat.setLayoutX(500);
        iconTimeFloat.setLayoutY(-91);

        // add vào root
        root.getChildren().addAll(vb, iconTimeFloat);
        v.root = root;

        return v;
    }

    // ===== helpers =====
    private static ImageView imageView(String res, double fitW, double fitH) {
        var url = Objects.requireNonNull(
                TKHoaDon_GUI.class.getResource(res),
                "Missing resource: " + res
        ).toExternalForm();
        ImageView iv = new ImageView(new Image(url));
        iv.setFitWidth(fitW);
        iv.setFitHeight(fitH);
        iv.setPickOnBounds(true);
        iv.setPreserveRatio(true);
        return iv;
    }

    private static void addStyles(Scene scene, String cssPath) {
        var url = TKHoaDon_GUI.class.getResource(cssPath);
        if (url != null) scene.getStylesheets().add(url.toExternalForm());
        else System.err.println("CSS not found: " + cssPath);
    }

    /** Gói toàn bộ control để truyền controller (không lookup) */
    private static class ViewRefs {
        Pane root;

        // filter controls
        ComboBox<String> cboTieuChiTimKiem;
        TextField txtNoiDungTimKiem;
        DatePicker dpTuNgay, dpDenNgay;
        ComboBox<String> cboBoLocNhanh;

        Button btnTimKiem, btnHuyBo;

        // table
        TableView<HoaDon> tblHD;
        TableColumn<HoaDon, String> colMaHD, colTenKH, colSdtKH, colTenNV, colChiTiet, colNgayLap;
        TableColumn<HoaDon, Integer> colSLP;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
