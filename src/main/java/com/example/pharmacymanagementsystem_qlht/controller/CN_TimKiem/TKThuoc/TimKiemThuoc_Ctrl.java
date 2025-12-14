package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKThuoc;

import com.example.pharmacymanagementsystem_qlht.dao.LoaiHang_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SanPham_Dao;
import com.example.pharmacymanagementsystem_qlht.model.LoaiHang;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKThuoc.ChiTietThuoc_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKThuoc.TKThuoc_GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class TimKiemThuoc_Ctrl extends Application {

    public TableColumn<Thuoc_SanPham, String> colTenThuoc;
    public TableColumn<Thuoc_SanPham, String> colMaThuoc;
    public TableColumn<Thuoc_SanPham, String> colHamLuong;
    public TableColumn<Thuoc_SanPham, String> colXuatXu;
    public TableColumn<Thuoc_SanPham, String> colSDK_GPNK;
    public TableColumn<Thuoc_SanPham, String> colLoaiHang;
    public TableColumn<Thuoc_SanPham, String> colViTri;
    public TableColumn<Thuoc_SanPham, String> colChiTiet;
    public TableView<Thuoc_SanPham> tbl_Thuoc;
    public ComboBox cbxLoaiHang;
    public ComboBox cbxXuatSu;
    public TextField txtHamLuongMin;
    public TextField txtHamLuongMax;
    public ComboBox<String> cboTimKiem;
    public TextField txtTimKiem;
    public Button btnReset;
    private ObservableList<Thuoc_SanPham> duLieuChinh = FXCollections.observableArrayList();
    private FilteredList<Thuoc_SanPham> duLieu;

    @Override
    public void start(Stage primaryStage) throws Exception {
        new TKThuoc_GUI().showWithController(primaryStage, this);
    }

    //  phương thức khởi tạo
    public void initialize() {
        // 1) Setup control cơ bản
        duLieu = new FilteredList<>(duLieuChinh, sp -> true);
        tbl_Thuoc.setItems(duLieu);

        cboTimKiem.getItems().setAll("Loại tìm kiếm", "Mã thuốc", "Tên thuốc", "Nước sản xuất", "Loại hàng", "Vị trí");
        cboTimKiem.setValue("Loại tìm kiếm");
        cbxLoaiHang.getItems().setAll("Chọn loại hàng");
        cbxLoaiHang.setValue("Chọn loại hàng");
        cbxXuatSu.getItems().setAll("Chọn xuất xứ");
        cbxXuatSu.setValue("Chọn xuất xứ");

        // 2) Lắng nghe thay đổi để lọc (giữ như cũ)
        txtTimKiem.textProperty().addListener((o, ov, nv) -> TimKiemTxt());
        cboTimKiem.valueProperty().addListener((o, ov, nv) -> TimKiemTxt());
        cbxLoaiHang.setOnAction(e -> TimKiemLoc());
        cbxXuatSu.setOnAction(e -> TimKiemLoc());
        txtHamLuongMin.textProperty().addListener((o, ov, nv) -> TimKiemLoc());
        txtHamLuongMax.textProperty().addListener((o, ov, nv) -> TimKiemLoc());

        // 3) CHỈ 1 LẦN gọi DAO ở background
        javafx.concurrent.Task<List<Thuoc_SanPham>> task = new javafx.concurrent.Task<>() {
            @Override
            protected List<Thuoc_SanPham> call() {
                return new Thuoc_SanPham_Dao().selectAll();
            }
        };

        task.setOnSucceeded(e -> {
            List<Thuoc_SanPham> list = task.getValue();

            // Đổ bảng
            duLieuChinh.setAll(list);
            loadTable();

            // Tự suy ra combobox từ dữ liệu vừa tải (không gọi thêm DAO)
            List<String> loaiHangs = list.stream()
                    .map(Thuoc_SanPham::getLoaiHang)   // → object LoaiHang
                    .filter(Objects::nonNull)
                    .map(LoaiHang::getTenLoaiHang)           // → chuyển sang String
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .distinct()
                    .sorted()
                    .toList();

            List<String> xuatXus = list.stream()
                    .map(Thuoc_SanPham::getNuocSX)
                    .filter(s -> s != null && !s.isBlank())
                    .distinct().sorted().toList();

            cbxLoaiHang.getItems().setAll("Chọn loại hàng");
            cbxLoaiHang.getItems().addAll(loaiHangs);

            cbxXuatSu.getItems().setAll("Chọn xuất xứ");
            cbxXuatSu.getItems().addAll(xuatXus);
        });

        task.setOnFailed(e -> task.getException().printStackTrace());
        new Thread(task, "LoadThuocSanPham").start();
    }


    //  button chuyển sang giao diện chi tiết thuốc
    private void btnChiTietClick(Thuoc_SanPham sp) {
        try {
            // 1) Tạo controller
            ChiTietThuoc_Ctrl ctrl = new ChiTietThuoc_Ctrl();

            // 2) Tạo GUI Java thuần
            ChiTietThuoc_GUI gui = new ChiTietThuoc_GUI();

            // 3) Tạo Stage cho form chi tiết
            Stage dialog = new Stage();
            dialog.initOwner(txtTimKiem.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiết thuốc");
            dialog.getIcons().add(
                    new javafx.scene.image.Image(
                            getClass().getResourceAsStream(
                                    "/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png"))
            );

            // 4) Tạo UI + gán controller vào UI
            gui.showWithController(dialog, ctrl);

            // 5) Load dữ liệu (thay cho loader.getController())
            ctrl.initialize(sp);   // hoặc ctrl.load(sp) tùy bạn đặt tên

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //  button xóa rỗng các trường lọc và tìm kiếm
    public void btnXoaRong(ActionEvent actionEvent) {
        txtTimKiem.clear();
        cbxLoaiHang.setValue("Chọn loại hàng");
        cbxXuatSu.setValue("Chọn xuất xứ");
        txtHamLuongMin.clear();
        txtHamLuongMax.clear();
        TimKiemLoc();
    }

    private void loadTable() {
        colMaThuoc.setCellValueFactory(new PropertyValueFactory<>("maThuoc"));
        colTenThuoc.setCellValueFactory(new PropertyValueFactory<>("tenThuoc"));
        colHamLuong.setCellValueFactory(new PropertyValueFactory<>("HamLuongDonVi"));
        colXuatXu.setCellValueFactory(new PropertyValueFactory<>("nuocSX"));
        colSDK_GPNK.setCellValueFactory(new PropertyValueFactory<>("SDK_GPNK"));
        colLoaiHang.setCellValueFactory(cel -> new SimpleStringProperty(cel.getValue().getLoaiHang().getTenLoaiHang()));
        colViTri.setCellValueFactory(cel -> new SimpleStringProperty(cel.getValue()==null?"":
                (cel.getValue().getVitri()==null?"":cel.getValue().getVitri().getTenKe())));
        colChiTiet.setCellFactory(cel -> new TableCell<Thuoc_SanPham, String>() {
            private final Button btn = new Button("Chi tiết");

            {
                btn.setOnAction(event -> {
                    Thuoc_SanPham temp = getTableView().getItems().get(getIndex());
                    btnChiTietClick(temp);
                });
                btn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                btn.getStyleClass().add("btn");
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    //  funtion lọc dữ liệu từ các combobox và textfield
    private void TimKiemLoc() {
        String loaiHang = (String) cbxLoaiHang.getValue();
        String xuatXu = (String) cbxXuatSu.getValue();
        String hamLuongMinStr = txtHamLuongMin.getText().trim();
        String hamLuongMaxStr = txtHamLuongMax.getText().trim();

        Double hamLuongMin = hamLuongMinStr.isEmpty() ? null : Double.parseDouble(hamLuongMinStr);
        Double hamLuongMax = hamLuongMaxStr.isEmpty() ? null : Double.parseDouble(hamLuongMaxStr);

        duLieu.setPredicate(sp -> {
            boolean matchesLoaiHang = (loaiHang == null || loaiHang.isEmpty() || loaiHang == "Chọn loại hàng" || sp.getLoaiHang().getTenLoaiHang().equals(loaiHang));
            boolean matchesXuatXu = (xuatXu == null || xuatXu.isEmpty() || xuatXu == "Chọn xuất xứ" || sp.getNuocSX().equals(xuatXu));
            boolean matchesHamLuong = true;

            if (hamLuongMin != null) {
                matchesHamLuong = sp.getHamLuong() >= hamLuongMin;
            }
            if (hamLuongMax != null) {
                matchesHamLuong = matchesHamLuong && sp.getHamLuong() <= hamLuongMax;
            }

            return matchesLoaiHang && matchesXuatXu && matchesHamLuong;
        });
    }

    private void TimKiemTxt() {
        String filterType = cboTimKiem.getValue();
        String filterText = txtTimKiem.getText().toLowerCase().trim();

        duLieu.setPredicate(thuoc -> {
            if (filterText.isEmpty() || filterType.equals("Tất cả")) {
                return true;
            }

            switch (filterType) {
                case "Mã thuốc":
                    return thuoc.getMaThuoc().toLowerCase().contains(filterText);
                case "Tên thuốc":
                    return thuoc.getTenThuoc().toLowerCase().contains(filterText);
                case "Nước sản xuất":
                    return thuoc.getNuocSX().toLowerCase().contains(filterText);
                case "Loại hàng":
                    return thuoc.getLoaiHang().getTenLoaiHang().toLowerCase().contains(filterText);
                case "Vị trí":
                    return thuoc.getVitri().getTenKe().toLowerCase().contains(filterText);
                default:
                    return false;
            }
        });
    }
}