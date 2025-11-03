package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDatHang;

import com.example.pharmacymanagementsystem_qlht.TienIch.VNDFormatter;
import com.example.pharmacymanagementsystem_qlht.dao.PhieuDatHang_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.PhieuNhap_Dao;
import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import com.example.pharmacymanagementsystem_qlht.model.PhieuDatHang;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.util.List;
import com.example.pharmacymanagementsystem_qlht.TienIch.DoiNgay;

public class TKPhieuDatHang_Ctrl extends Application {
    @FXML
    public TableView<PhieuDatHang> tblPD;
    @FXML
    public TableColumn<PhieuDatHang, Number> colSTT;
    @FXML
    public TableColumn<PhieuDatHang, String> colMaPD;
    @FXML
    public TableColumn<PhieuDatHang, String> colNgayLap;
    @FXML
    public TableColumn<PhieuDatHang, String> colTenKH;
    @FXML
    public TableColumn<PhieuDatHang, String> colSdtKH;
    @FXML
    public TableColumn<PhieuDatHang, String> colTenNV;
    @FXML
    public TableColumn<PhieuDatHang, String> colSoTienCoc;
    @FXML
    public TableColumn<PhieuDatHang, String> colTT;
    @FXML
    public TableColumn<PhieuDatHang, String> colChiTiet;
    @FXML
    public ComboBox<String> cboTimKiem; //
    @FXML
    public TextField txtNoiDungTimKiem; //
    @FXML
    public DatePicker dpTuNgay;
    @FXML
    public DatePicker dpDenNgay;
    @FXML
    public ComboBox<String> cbLoc;
    @FXML
    public Button btnTimKiem;
    @FXML
    public Button btnHuyBo;

    private final PhieuDatHang_Dao phieuDatHangDao = new PhieuDatHang_Dao();

    @FXML
    public void initialize() {
        cboTimKiem.getItems().addAll(
            "Mã phiếu đặt", "Tên khách hàng", "SĐT khách hàng", "Tên nhân viên", "Ngày lập"
        );
        cboTimKiem.setValue("Tiêu chí");
        cbLoc.getItems().addAll(
            "Tất cả", "Hôm nay", "7 ngày gần nhất", "Tháng này", "Năm nay"
        );
        cbLoc.setValue("⌛ Bộ lọc nhanh");
        // Sự kiện double click vào dòng TableView để xem chi tiết
        tblPD.setRowFactory(tv -> {
            TableRow<PhieuDatHang> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    PhieuDatHang rowData = row.getItem();
                    btnChiTietClick(rowData);
                }
            });
            return row;
        });
        // Sự kiện tìm kiếm
        btnTimKiem.setOnAction(e -> timKiem());
        // Sự kiện làm mới
        btnHuyBo.setOnAction(e -> lamMoi());
        // Sự kiện lọc nhanh
        cbLoc.setOnAction(e -> boLocNhanh());
        Platform.runLater(()->{
            loadTable();
        });
    }

    public void loadTable() {
        List<PhieuDatHang> list = phieuDatHangDao.selectAll();
        ObservableList<PhieuDatHang> data = FXCollections.observableArrayList(list);
        colSTT.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(tblPD.getItems().indexOf(cellData.getValue()) + 1)
        );
        colSTT.setSortable(false);
        colMaPD.setCellValueFactory(new PropertyValueFactory<>("maPDat"));
        colNgayLap.setCellValueFactory(cellData -> new SimpleStringProperty(DoiNgay.dinhDangThoiGian(cellData.getValue().getNgayLap())));
        colTenKH.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKhachHang().getTenKH()));
        colSdtKH.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKhachHang().getSdt()));
        colSoTienCoc.setCellValueFactory(cellData ->
                new SimpleStringProperty(VNDFormatter.format(cellData.getValue().getSoTienCoc()))
        );
        colTenKH.setCellFactory(col -> new TableCell<PhieuDatHang, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER_LEFT);
                }
            }
        });
        colTenNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNhanVien().getTenNV()));
        colTenNV.setCellFactory(col -> new TableCell<PhieuDatHang, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER_LEFT);
                }
            }
        });
        colChiTiet.setCellFactory(col -> new TableCell<PhieuDatHang, String>() {
            private final Button btn = new Button("Chi tiết");
            {
                btn.setOnAction(event -> {
                    PhieuDatHang pdh = getTableView().getItems().get(getIndex());
                    btnChiTietClick(pdh);
                });
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        colTT.setCellValueFactory(cellData ->{
            String trangThai;
            if(cellData.getValue().getTrangthai() == 0) trangThai = "Chưa có hàng";
            else if(cellData.getValue().getTrangthai() == 1) trangThai = "Sẵn hàng";
            else trangThai = "Đã hoàn thành";
            return new SimpleStringProperty(trangThai);
        });

        tblPD.setItems(data);
    }

    private void btnChiTietClick(PhieuDatHang pdh) {
        try {
            ChiTietPhieuDatHang_Ctrl ctrl = new ChiTietPhieuDatHang_Ctrl();
            PhieuDatHang_Dao pdhdao = new PhieuDatHang_Dao();
            pdhdao.duyetPhieuDatHang(pdh.getMaPDat());

            Stage stage = new Stage();
            stage.setTitle("Chi tiết phiếu đặt hàng");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDatHang.ChiTietPhieuDatHang_GUI()
                    .showWithController(stage, ctrl);
            ctrl.setPhieuDatHang(pdhdao.selectById(pdh.getMaPDat()));
            ctrl.setPhieuDatHang(pdh);
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void timKiem() {
        String tieuChi = cboTimKiem.getValue();
        String noiDung = txtNoiDungTimKiem.getText().trim().toLowerCase();
        var tuNgay = dpTuNgay != null ? dpTuNgay.getValue() : null;
        var denNgay = dpDenNgay != null ? dpDenNgay.getValue() : null;
        List<PhieuDatHang> list = phieuDatHangDao.selectAll();
        if ((tieuChi == null || tieuChi.equals("Tiêu chí")) && (tuNgay == null && denNgay == null) && noiDung.isEmpty()) {
            loadTable();
            return;
        }
        List<PhieuDatHang> filtered = list.stream().filter(pd -> {
            boolean match = true;
            switch (tieuChi) {
                case "Mã phiếu đặt":
                    match = pd.getMaPDat().toLowerCase().contains(noiDung);
                    break;
                case "Tên khách hàng":
                    match = pd.getKhachHang() != null && pd.getKhachHang().getTenKH() != null && pd.getKhachHang().getTenKH().toLowerCase().contains(noiDung);
                    break;
                case "SĐT khách hàng":
                    match = pd.getKhachHang() != null && pd.getKhachHang().getSdt() != null && pd.getKhachHang().getSdt().toLowerCase().contains(noiDung);
                    break;
                case "Tên nhân viên":
                    match = pd.getNhanVien() != null && pd.getNhanVien().getTenNV().toLowerCase().contains(noiDung);
                    break;
                case "Ngày lập":
                    match = pd.getNgayLap() != null && DoiNgay.dinhDangThoiGian(pd.getNgayLap()).contains(noiDung);
                    break;
            }
            if (tuNgay != null && pd.getNgayLap() != null) {
                match = match && !pd.getNgayLap().toLocalDateTime().toLocalDate().isBefore(tuNgay);
            }
            if (denNgay != null && pd.getNgayLap() != null) {
                match = match && !pd.getNgayLap().toLocalDateTime().toLocalDate().isAfter(denNgay);
            }
            return match;
        }).toList();
        tblPD.setItems(FXCollections.observableArrayList(filtered));
    }

    private void lamMoi() {
        txtNoiDungTimKiem.clear();
        cboTimKiem.setValue("Mã phiếu đặt");
        if (dpTuNgay != null) dpTuNgay.setValue(null);
        if (dpDenNgay != null) dpDenNgay.setValue(null);
        cbLoc.setValue("Tất cả");
        loadTable();
    }

    private void boLocNhanh() {
        String loc = cbLoc.getValue();
        var today = java.time.LocalDate.now();
        if (loc == null) return;
        switch (loc) {
            case "Tất cả":
                if (dpTuNgay != null) dpTuNgay.setValue(null);
                if (dpDenNgay != null) dpDenNgay.setValue(null);
                break;
            case "Hôm nay":
                if (dpTuNgay != null) dpTuNgay.setValue(today);
                if (dpDenNgay != null) dpDenNgay.setValue(today);
                break;
            case "7 ngày gần nhất":
                if (dpTuNgay != null) dpTuNgay.setValue(today.minusDays(6));
                if (dpDenNgay != null) dpDenNgay.setValue(today);
                break;
            case "Tháng này":
                if (dpTuNgay != null) dpTuNgay.setValue(today.withDayOfMonth(1));
                if (dpDenNgay != null) dpDenNgay.setValue(today);
                break;
            case "Năm nay":
                if (dpTuNgay != null) dpTuNgay.setValue(today.withDayOfYear(1));
                if (dpDenNgay != null) dpDenNgay.setValue(today);
                break;
        }
        timKiem();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDatHang.TKPhieuDatHang_GUI()
                .showWithController(primaryStage, this);
    }
}
