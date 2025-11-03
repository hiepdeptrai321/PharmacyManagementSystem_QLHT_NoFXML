package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuTra;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuTraHang.TKPhieuTraHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.PhieuTraHang;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class TKPhieuTraHang_GUI extends Application {

    // ===================== Entry cho test độc lập =====================
    @Override
    public void start(Stage stage) {
        // Demo mở bằng Stage riêng
        TKPhieuTraHang_Ctrl ctrl = new TKPhieuTraHang_Ctrl();
        showWithController(stage, ctrl);
        stage.show();
    }

    // ===================== 1) MỞ BẰNG STAGE RIÊNG =====================
    /** Mở cửa sổ mới, vẫn giữ nguyên UI/ID/css, wire hết control vào controller */
    public void showWithController(Stage stage, TKPhieuTraHang_Ctrl ctrl) {
        ViewRefs v = buildUI();       // tạo toàn bộ UI + control
        wireToController(v, ctrl);    // gán control sang ctrl + gọi initialize()

        Scene scene = new Scene(v.root, 1646, 895);
        addStyles(scene);

        stage.setTitle("Tìm kiếm phiếu trả hàng");
        stage.setScene(scene);
        // (để caller .show() hoặc bạn có thể stage.show() ngay tại đây)
    }

    // ===================== 2) NHÚNG VÀO pnlChung =====================
    /** Trả về Node đã build và wire sẵn controller để gắn vào pnlChung (KHÔNG mở Stage) */
    public Parent createContentForEmbed(TKPhieuTraHang_Ctrl ctrl) {
        ViewRefs v = buildUI();       // tạo UI
        wireToController(v, ctrl);    // gán control -> ctrl + initialize()
        // Trả về root để Main Controller setAll() vào pnlChung
        return v.root;
    }

    // ===================== BUILD UI (giữ nguyên layout/IDs) =====================
    private ViewRefs buildUI() {
        ViewRefs v = new ViewRefs();

        v.root = new Pane();
        v.root.setId("mainPane");
        v.root.setPrefSize(1646, 895);
        v.root.setStyle("-fx-font-size: 14px;");

        VBox vbox = new VBox();
        vbox.setLayoutX(10);
        vbox.setLayoutY(14);

        // ======== HBox Tiêu đề ========
        HBox hbTitle = new HBox();
        hbTitle.setPrefSize(782, 46);

        Label lbTimKiem = new Label("Tìm kiếm phiếu trả hàng");
        lbTimKiem.setId("lbTimKiem");
        lbTimKiem.setPrefSize(449, 53);
        lbTimKiem.getStyleClass().add("title");
        lbTimKiem.setFont(new Font(36));

        Label lbEmpty = new Label();

        ImageView imgBill = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/bill-8854.png")).toExternalForm())
        );
        imgBill.setFitHeight(46);
        imgBill.setFitWidth(48);
        imgBill.setPreserveRatio(true);
        imgBill.setPickOnBounds(true);

        hbTitle.getChildren().addAll(lbTimKiem, lbEmpty, imgBill);

        Separator separator = new Separator();
        separator.setPrefWidth(200);

        // ======== HBox tìm kiếm ========
        HBox hbSearch = new HBox();
        hbSearch.setPrefSize(1607, 68);

        v.cboTimKiem = new ComboBox<>();
        v.cboTimKiem.setId("cboTimKiem");
        v.cboTimKiem.setPrefSize(140, 40);
        v.cboTimKiem.setPromptText("Tìm theo");
        v.cboTimKiem.getStyleClass().add("btntim");
        HBox.setMargin(v.cboTimKiem, new Insets(10, 5, 0, 0));

        v.txtNoiDungTimKiem = new TextField();
        v.txtNoiDungTimKiem.setId("txtNoiDungTimKiem");
        v.txtNoiDungTimKiem.setPrefSize(251, 40);
        v.txtNoiDungTimKiem.setPromptText("Nhập thông tin phiếu trả");
        v.txtNoiDungTimKiem.getStyleClass().add("tftim");
        HBox.setMargin(v.txtNoiDungTimKiem, new Insets(10, 0, 0, 0));

        Region rg1 = new Region();
        rg1.setPrefSize(30, 51);

        ImageView imgTime = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/time-2623.png")).toExternalForm())
        );
        imgTime.setFitHeight(27);
        imgTime.setFitWidth(28);
        imgTime.setPreserveRatio(true);
        imgTime.setPickOnBounds(true);
        HBox.setMargin(imgTime, new Insets(16, 4, 0, 0));

        Label lbTu = new Label("Từ: ");
        lbTu.setPrefSize(27, 37);
        lbTu.getStyleClass().add("tftim");
        HBox.setMargin(lbTu, new Insets(10, 0, 0, 0));

        v.dpTuNgay = new DatePicker();
        v.dpTuNgay.setId("dpTuNgay");
        v.dpTuNgay.setPrefSize(125, 40);
        v.dpTuNgay.getStyleClass().add("tftim");
        HBox.setMargin(v.dpTuNgay, new Insets(10, 10, 0, 0));

        Label lbDen = new Label("Đến: ");
        lbDen.setPrefSize(35, 37);
        lbDen.getStyleClass().add("tftim");
        HBox.setMargin(lbDen, new Insets(10, 0, 0, 0));

        v.dpDenNgay = new DatePicker();
        v.dpDenNgay.setId("dpDenNgay");
        v.dpDenNgay.setPrefSize(125, 40);
        v.dpDenNgay.getStyleClass().add("tftim");
        HBox.setMargin(v.dpDenNgay, new Insets(10, 0, 0, 0));

        Region rg2 = new Region();
        rg2.setPrefSize(30, 51);

        v.btnTimKiem = new Button("Tìm");
        v.btnTimKiem.setId("btnTimKiem");
        v.btnTimKiem.setPrefSize(78, 40);
        v.btnTimKiem.setDefaultButton(true);
        v.btnTimKiem.getStyleClass().add("btntim");
        v.btnTimKiem.setContentDisplay(ContentDisplay.RIGHT);
        HBox.setMargin(v.btnTimKiem, new Insets(10, 0, 0, 8));

        ImageView imgSearch = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/free-search-icon-2911-thumb.png")).toExternalForm())
        );
        imgSearch.setFitHeight(20);
        imgSearch.setFitWidth(150);
        imgSearch.setPreserveRatio(true);
        imgSearch.setPickOnBounds(true);
        imgSearch.setCursor(Cursor.DEFAULT);
        v.btnTimKiem.setGraphic(imgSearch);

        v.btnHuyBo = new Button();
        v.btnHuyBo.setId("btnHuyBo");
        v.btnHuyBo.setPrefSize(52, 40);
        v.btnHuyBo.getStyleClass().add("btntim");
        v.btnHuyBo.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css")).toExternalForm());
        HBox.setMargin(v.btnHuyBo, new Insets(10, 0, 0, 8));

        ImageView imgRefresh = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")).toExternalForm())
        );
        imgRefresh.setFitHeight(20);
        imgRefresh.setFitWidth(34);
        imgRefresh.setPreserveRatio(true);
        imgRefresh.setPickOnBounds(true);
        v.btnHuyBo.setGraphic(imgRefresh);

        Region rg3 = new Region();
        rg3.setPrefSize(465, 68);

        v.cbLoc = new ComboBox<>();
        v.cbLoc.setId("cbLoc");
        v.cbLoc.setPrefSize(185, 41);
        v.cbLoc.setPromptText("⌛ Bộ lọc nhanh");
        v.cbLoc.getStyleClass().add("btntim");
        HBox.setMargin(v.cbLoc, new Insets(10, 0, 0, 0));

        hbSearch.getChildren().addAll(
                v.cboTimKiem, v.txtNoiDungTimKiem, rg1, imgTime, lbTu, v.dpTuNgay, lbDen, v.dpDenNgay,
                rg2, v.btnTimKiem, v.btnHuyBo, rg3, v.cbLoc
        );
        VBox.setMargin(hbSearch, new Insets(0, 0, 5, 0));

        // ======== TableView ========
        v.tblPT = new TableView<>();
        v.tblPT.setId("tblPT");
        v.tblPT.setPrefSize(1605, 749);

        v.colSTT = new TableColumn<>("STT");
        v.colSTT.setId("colSTT");
        v.colSTT.setPrefWidth(51);
        v.colSTT.setStyle("-fx-alignment: CENTER;");

        v.colMaPT = new TableColumn<>("Mã phiếu trả");
        v.colMaPT.setId("colMaPT");
        v.colMaPT.setPrefWidth(189.33);
        v.colMaPT.setStyle("-fx-alignment: CENTER;");

        v.colMaHD = new TableColumn<>("Mã hóa đơn");
        v.colMaHD.setId("colMaHD");
        v.colMaHD.setPrefWidth(182.66);
        v.colMaHD.setStyle("-fx-alignment: CENTER;");

        v.colNgayLap = new TableColumn<>("Ngày lập");
        v.colNgayLap.setId("colNgayLap");
        v.colNgayLap.setPrefWidth(216.33);
        v.colNgayLap.setStyle("-fx-alignment: CENTER;");

        v.colTenKH = new TableColumn<>("Khách hàng");
        v.colTenKH.setId("colTenKH");
        v.colTenKH.setPrefWidth(285);

        v.colSdtKH = new TableColumn<>("SĐT");
        v.colSdtKH.setId("colSdtKH");
        v.colSdtKH.setPrefWidth(243);
        v.colSdtKH.setStyle("-fx-alignment: CENTER;");

        v.colTenNV = new TableColumn<>("Nhân viên");
        v.colTenNV.setId("colTenNV");
        v.colTenNV.setPrefWidth(337);

        v.colChiTiet = new TableColumn<>("Chi tiết");
        v.colChiTiet.setId("colChiTiet");
        v.colChiTiet.setPrefWidth(84);
        v.colChiTiet.setStyle("-fx-alignment: CENTER;");

        v.tblPT.getColumns().addAll(
                v.colSTT, v.colMaPT, v.colMaHD, v.colNgayLap, v.colTenKH, v.colSdtKH, v.colTenNV, v.colChiTiet
        );

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(v.tblPT);

        vbox.getChildren().addAll(hbTitle, separator, hbSearch, scrollPane);

        ImageView imgTimeIcon = new ImageView();
        imgTimeIcon.setFitHeight(35);
        imgTimeIcon.setFitWidth(40);
        imgTimeIcon.setLayoutX(500);
        imgTimeIcon.setLayoutY(-91);
        imgTimeIcon.setPreserveRatio(true);
        imgTimeIcon.getStyleClass().add("icontime");

        v.root.getChildren().addAll(vbox, imgTimeIcon);

        return v;
    }

    // ===================== GÁN CONTROL -> CONTROLLER =====================
    private void wireToController(ViewRefs v, TKPhieuTraHang_Ctrl ctrl) {
        // Combo & textfields & datepickers & buttons
        ctrl.cboTimKiem         = v.cboTimKiem;
        ctrl.txtNoiDungTimKiem  = v.txtNoiDungTimKiem;
        ctrl.dpTuNgay           = v.dpTuNgay;
        ctrl.dpDenNgay          = v.dpDenNgay;
        ctrl.btnTimKiem         = v.btnTimKiem;
        ctrl.btnHuyBo           = v.btnHuyBo;
        ctrl.cbLoc              = v.cbLoc;

        // Table & columns (kiểu dữ liệu khớp controller của bạn)
        ctrl.tblPT              = v.tblPT;
        // bạn đang dùng Number hay String cho STT? Ở đây set Number cho phổ biến:
        ctrl.colSTT             = new TableColumn<>("STT");
        // Nếu controller của bạn đã khai báo sẵn colSTT là Number, dùng v.colSTT luôn:
        // ctrl.colSTT = v.colSTT;

        ctrl.colSTT             = (TableColumn<PhieuTraHang, Number>) (TableColumn<?, ?>) v.colSTT;
        ctrl.colMaPT            = v.colMaPT;
        ctrl.colMaHD            = v.colMaHD;
        ctrl.colNgayLap         = v.colNgayLap;
        ctrl.colTenKH           = v.colTenKH;
        ctrl.colSdtKH           = v.colSdtKH;
        ctrl.colTenNV           = v.colTenNV;
        ctrl.colChiTiet         = v.colChiTiet;

        // Gọi initialize() sau khi đã gán đủ control
        try { ctrl.initialize(); } catch (Exception ignore) {}
    }

    // ===================== CSS =====================
    private void addStyles(Scene scene) {
        var css = getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css");
        if (css != null) scene.getStylesheets().add(css.toExternalForm());
        else System.err.println("Không tìm thấy TimKiemHoaDon.css");
    }

    // ===================== Holder cho toàn bộ control =====================
    private static class ViewRefs {
        Pane root;

        // search bar controls
        ComboBox<String> cboTimKiem;
        TextField txtNoiDungTimKiem;
        DatePicker dpTuNgay, dpDenNgay;
        Button btnTimKiem, btnHuyBo;
        ComboBox<String> cbLoc;

        // table
        TableView<PhieuTraHang> tblPT;
        TableColumn<PhieuTraHang, Number> colSTT;
        TableColumn<PhieuTraHang, String> colMaPT, colMaHD, colNgayLap, colTenKH, colSdtKH, colTenNV, colChiTiet;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
