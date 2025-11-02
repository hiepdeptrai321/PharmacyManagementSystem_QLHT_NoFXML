package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMThuoc;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc.SuaXoaThuoc_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoatChat;
import com.example.pharmacymanagementsystem_qlht.model.HoatChat;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class SuaXoaThuoc_GUI extends Application {

    // Bỏ hết các field control ở đây (txt..., cbx..., btn...) để không lệ thuộc instance

    /** Nhóm tham chiếu control */
    private static final class Refs {
        TextField txtMaThuoc, txtTenThuoc, txtHamLuong, txtDonViHamLuong, txtDuongDung,
                txtHangSanXuat, txtNuocSanXuat, txtQuyCachDongGoi, txtSDK_GPNK, txtTimKiemHoatChat;
        ComboBox<?> cbxLoaiHang, cbxViTri, cbxNhomDuocLy;
        ImageView imgThuoc;
        TableView<ChiTietHoatChat> tblHoatChat;
        TableColumn<ChiTietHoatChat, String> colMaHoatChat, colTenHoatChat, colHamLuong, colXoa;
        ListView<HoatChat> listViewHoatChat;
        Button btnChonAnh, btnXoa, btnHuy, btnLuu;
    }

    @Override
    public void start(Stage stage) {
        AnchorPane root = buildUI();
        Scene scene = new Scene(root, 1004, 690);
        addStyles(scene);
        stage.setTitle("Sửa/Xóa thuốc");
        stage.setScene(scene);
        stage.show();
    }

    /** Show + inject controller (không lookup, không dùng field instance) */
    public void showWithController(Stage stage, SuaXoaThuoc_Ctrl ctrl) {
        AnchorPane root = buildUI();
        Refs r = (Refs) root.getUserData();      // LẤY CONTROL Ở ĐÂY

        // Inject sang controller
        ctrl.txtMaThuoc       = r.txtMaThuoc;
        ctrl.txtTenThuoc      = r.txtTenThuoc;
        ctrl.cbxLoaiHang      = (ComboBox) r.cbxLoaiHang;
        ctrl.cbxViTri         = (ComboBox) r.cbxViTri;
        ctrl.imgThuoc_SanPham = r.imgThuoc;

        ctrl.txtHamLuong        = r.txtHamLuong;
        ctrl.txtDonViHamLuong   = r.txtDonViHamLuong;
        ctrl.txtDuongDung       = r.txtDuongDung;
        ctrl.txtHangSanXuat     = r.txtHangSanXuat;
        ctrl.txtNuocSanXuat     = r.txtNuocSanXuat;
        ctrl.txtQuyCachDongGoi  = r.txtQuyCachDongGoi;
        ctrl.txtSDK_GPNK        = r.txtSDK_GPNK;
        ctrl.cbxNhomDuocLy      = (ComboBox) r.cbxNhomDuocLy;

        ctrl.tblHoatChat    = r.tblHoatChat;
        ctrl.colMaHoatChat  = (TableColumn) r.colMaHoatChat;
        ctrl.colTenHoatChat = (TableColumn) r.colTenHoatChat;
        ctrl.colHamLuong    = (TableColumn) r.colHamLuong;
        ctrl.colXoa         = (TableColumn) r.colXoa;

        ctrl.txtTimKiemHoatChat = r.txtTimKiemHoatChat;
        ctrl.listViewHoatChat   = r.listViewHoatChat;

        // Gắn handler
        r.btnChonAnh.setOnAction(e -> ctrl.chonFile());
        r.btnXoa.setOnAction(e    -> ctrl.btnXoa());
        r.btnHuy.setOnAction(e    -> ctrl.btnHuy());
        r.btnLuu.setOnAction(e    -> ctrl.btnCapNhat());

        try { ctrl.initialize(); } catch (Exception ignore) {}

        Scene scene = new Scene(root, 1004, 690);
        addStyles(scene);
        stage.setTitle("Sửa/Xóa thuốc");
        stage.setScene(scene);
        stage.show();
    }

    /** Build UI và nhét tất cả control vào Refs, gắn vào root.setUserData(refs) */
    private AnchorPane buildUI() {
        Refs r = new Refs();
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1004, 690);
        root.setStyle("-fx-font-size: 14;");

        Label lbTitle = new Label("Thông tin chung");
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setLayoutX(14); lbTitle.setLayoutY(8);
        lbTitle.setPrefSize(977, 31);
        lbTitle.setFont(Font.font("System", 18));

        VBox vbox = new VBox();
        vbox.setLayoutX(14); vbox.setLayoutY(36);
        vbox.setPrefSize(977, 305);

        Pane topPane = new Pane(); topPane.setPrefSize(956, 121);
        VBox leftBox = new VBox(); leftBox.setLayoutY(1); leftBox.setPrefSize(668, 136);

        Pane spacer = new Pane(); spacer.setPrefSize(668, 207);
        Pane infoLine = new Pane(); infoLine.setPrefSize(574, 240);

        Label lbTenThuoc = new Label("Tên thuốc"); lbTenThuoc.setLayoutX(209); lbTenThuoc.setLayoutY(-25); lbTenThuoc.setFont(Font.font(14));
        r.txtTenThuoc = new TextField(); r.txtTenThuoc.setLayoutX(283); r.txtTenThuoc.setLayoutY(-28); r.txtTenThuoc.setPrefSize(360, 25);

        Label lbLoaiHang = new Label("Loại hàng"); lbLoaiHang.setLayoutX(0); lbLoaiHang.setLayoutY(15); lbLoaiHang.setFont(Font.font(14));
        r.cbxLoaiHang = new ComboBox<>(); r.cbxLoaiHang.setLayoutX(70); r.cbxLoaiHang.setLayoutY(15); r.cbxLoaiHang.setPrefSize(158, 25);

        Label lbViTri = new Label("Vị trí"); lbViTri.setLayoutX(248); lbViTri.setLayoutY(18); lbViTri.setFont(Font.font(14));
        r.cbxViTri = new ComboBox<>(); r.cbxViTri.setLayoutX(286); r.cbxViTri.setLayoutY(16); r.cbxViTri.setPrefSize(158, 25);

        Label lbMa = new Label("Mã thuốc:"); lbMa.setLayoutX(1); lbMa.setLayoutY(-27); lbMa.setFont(Font.font(14));
        r.txtMaThuoc = new TextField(); r.txtMaThuoc.setEditable(false); r.txtMaThuoc.setLayoutX(74); r.txtMaThuoc.setLayoutY(-28); r.txtMaThuoc.setPrefSize(120, 25);

        infoLine.getChildren().addAll(lbTenThuoc, r.txtTenThuoc, lbLoaiHang, r.cbxLoaiHang, lbViTri, r.cbxViTri, lbMa, r.txtMaThuoc);
        leftBox.getChildren().addAll(spacer, infoLine);

        r.imgThuoc = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/noimage.jpg")
        ).toExternalForm()));
        r.imgThuoc.setFitHeight(222); r.imgThuoc.setFitWidth(227);
        r.imgThuoc.setLayoutX(729); r.imgThuoc.setLayoutY(12);
        r.imgThuoc.setPreserveRatio(true); r.imgThuoc.setPickOnBounds(true);

        topPane.getChildren().addAll(leftBox, r.imgThuoc);

        // Pane thông tin chi tiết
        Pane detailPane = new Pane(); detailPane.setPrefSize(970, 184);
        detailPane.getStyleClass().add("pnlthongtin");

        Label lbTTCT = new Label("Thông tin chi tiết");
        lbTTCT.getStyleClass().add("lbtitle");
        lbTTCT.setLayoutX(4); lbTTCT.setLayoutY(-6); lbTTCT.setFont(Font.font(18));

        Label lbHL = new Label("Hàm lượng"); lbHL.setLayoutX(4); lbHL.setLayoutY(26); lbHL.setFont(Font.font(13));
        r.txtHamLuong = new TextField(); r.txtHamLuong.setLayoutX(4); r.txtHamLuong.setLayoutY(45); r.txtHamLuong.setPrefSize(136, 25);

        Label lbDVHL = new Label("Đơn vị hàm lượng"); lbDVHL.setLayoutX(154); lbDVHL.setLayoutY(26); lbDVHL.setFont(Font.font(13));
        r.txtDonViHamLuong = new TextField(); r.txtDonViHamLuong.setLayoutX(154); r.txtDonViHamLuong.setLayoutY(45); r.txtDonViHamLuong.setPrefSize(104, 30);

        Label lbDuong = new Label("Đường dùng"); lbDuong.setLayoutX(272); lbDuong.setLayoutY(26); lbDuong.setFont(Font.font(13));
        r.txtDuongDung = new TextField(); r.txtDuongDung.setLayoutX(272); r.txtDuongDung.setLayoutY(45); r.txtDuongDung.setPrefSize(120, 25);

        Label lbQCDG = new Label("Quy cách đóng gói"); lbQCDG.setLayoutX(401); lbQCDG.setLayoutY(26); lbQCDG.setFont(Font.font(13));
        r.txtQuyCachDongGoi = new TextField(); r.txtQuyCachDongGoi.setLayoutX(401); r.txtQuyCachDongGoi.setLayoutY(45); r.txtQuyCachDongGoi.setPrefSize(242, 25);

        Label lbSDK = new Label("SDK_GPNK"); lbSDK.setLayoutX(401); lbSDK.setLayoutY(76); lbSDK.setFont(Font.font(13));
        r.txtSDK_GPNK = new TextField(); r.txtSDK_GPNK.setLayoutX(401); r.txtSDK_GPNK.setLayoutY(92); r.txtSDK_GPNK.setPrefSize(242, 25);

        Label lbHSX = new Label("Hãng sản xuất"); lbHSX.setLayoutX(4); lbHSX.setLayoutY(76); lbHSX.setFont(Font.font(13));
        r.txtHangSanXuat = new TextField(); r.txtHangSanXuat.setLayoutX(4); r.txtHangSanXuat.setLayoutY(92); r.txtHangSanXuat.setPrefSize(228, 25);

        Label lbNSX = new Label("Nước sản xuất"); lbNSX.setLayoutX(247); lbNSX.setLayoutY(76); lbNSX.setFont(Font.font(13));
        r.txtNuocSanXuat = new TextField(); r.txtNuocSanXuat.setLayoutX(247); r.txtNuocSanXuat.setLayoutY(92); r.txtNuocSanXuat.setPrefSize(143, 25);

        Label lbNDL = new Label("Nhóm dược lý"); lbNDL.setLayoutX(4); lbNDL.setLayoutY(125); lbNDL.setFont(Font.font(13));
        r.cbxNhomDuocLy = new ComboBox<>(); r.cbxNhomDuocLy.setLayoutX(4); r.cbxNhomDuocLy.setLayoutY(142); r.cbxNhomDuocLy.setPrefSize(388, 30);

        r.btnChonAnh = new Button("Chọn ảnh");
        r.btnChonAnh.setLayoutX(800); r.btnChonAnh.setLayoutY(124); r.btnChonAnh.setPrefSize(92, 32);

        detailPane.getChildren().addAll(
                lbTTCT, lbHL, r.txtHamLuong, lbDVHL, r.txtDonViHamLuong, lbDuong, r.txtDuongDung,
                lbQCDG, r.txtQuyCachDongGoi, lbSDK, r.txtSDK_GPNK, lbHSX, r.txtHangSanXuat,
                lbNSX, r.txtNuocSanXuat, lbNDL, r.cbxNhomDuocLy, r.btnChonAnh
        );

        vbox.getChildren().addAll(topPane, detailPane);

        // Bảng hoạt chất + tìm
        Label lbHC = new Label("Hoạt chất"); lbHC.getStyleClass().add("lbtitle");
        lbHC.setLayoutX(18); lbHC.setLayoutY(334); lbHC.setFont(Font.font(18));

        Label lbTim = new Label("Tìm hoạt chất:"); lbTim.getStyleClass().add("lblChinh");
        lbTim.setLayoutX(18); lbTim.setLayoutY(378); lbTim.setFont(Font.font(14));

        r.txtTimKiemHoatChat = new TextField(); r.txtTimKiemHoatChat.setLayoutX(108); r.txtTimKiemHoatChat.setLayoutY(375); r.txtTimKiemHoatChat.setPrefSize(388, 30);
        r.listViewHoatChat = new ListView<>(); r.listViewHoatChat.setLayoutX(108); r.listViewHoatChat.setLayoutY(405); r.listViewHoatChat.setPrefSize(388, 194);

        r.tblHoatChat = new TableView<>();
        r.tblHoatChat.setLayoutX(18); r.tblHoatChat.setLayoutY(411); r.tblHoatChat.setPrefSize(966, 213);
        r.colMaHoatChat = new TableColumn<>("Mã hoạt chất"); r.colMaHoatChat.setPrefWidth(221.14); r.colMaHoatChat.setStyle("-fx-alignment: CENTER;");
        r.colTenHoatChat = new TableColumn<>("Tên hoạt chất"); r.colTenHoatChat.setPrefWidth(448); r.colTenHoatChat.setStyle("-fx-alignment: CENTER-LEFT;");
        r.colHamLuong = new TableColumn<>("Hàm lượng"); r.colHamLuong.setPrefWidth(209.14); r.colHamLuong.setStyle("-fx-alignment: CENTER-LEFT;");
        r.colXoa = new TableColumn<>("Xóa"); r.colXoa.setPrefWidth(86.28); r.colXoa.setStyle("-fx-alignment: CENTER;");
        r.tblHoatChat.getColumns().addAll(r.colMaHoatChat, r.colTenHoatChat, r.colHamLuong, r.colXoa);

        // Nút dưới
        r.btnXoa = new Button("Xóa"); r.btnXoa.setLayoutX(17); r.btnXoa.setLayoutY(641); r.btnXoa.setPrefSize(69, 32); r.btnXoa.setAlignment(Pos.CENTER);
        r.btnHuy = new Button("Hủy"); r.btnHuy.setLayoutX(836); r.btnHuy.setLayoutY(641); r.btnHuy.setPrefSize(69, 32);
        r.btnLuu = new Button("Lưu"); r.btnLuu.setLayoutX(915); r.btnLuu.setLayoutY(641); r.btnLuu.setPrefSize(69, 32);

        // Add all
        root.getChildren().addAll(
                lbTitle, vbox, r.btnXoa, r.btnHuy, new Pane(), new Pane(),
                r.tblHoatChat, lbTim, r.txtTimKiemHoatChat, r.listViewHoatChat, lbHC
        );

        // Gắn bó tham chiếu vào root
        root.setUserData(r);
        return root;
    }

    private void addStyles(Scene scene) {
        var css = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css"),
                "Không tìm thấy ThemThuoc.css!"
        ).toExternalForm();
        scene.getStylesheets().add(css);
    }

    public static void main(String[] args) { launch(args); }
}
