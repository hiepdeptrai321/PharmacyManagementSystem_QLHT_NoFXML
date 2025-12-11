package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuTraHang;

import com.example.pharmacymanagementsystem_qlht.dao.PhieuTraHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.PhieuTraHang;
import com.example.pharmacymanagementsystem_qlht.TienIch.DoiNgay;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuTra.ChiTietPhieuTraHang_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuTra.TKPhieuTraHang_GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.List;
import java.util.Objects;

public class TKPhieuTraHang_Ctrl extends Application {
    public TableView<PhieuTraHang> tblPT;
    public TableColumn<PhieuTraHang, Number> colSTT;
    public TableColumn<PhieuTraHang, String> colMaPT;
    public TableColumn<PhieuTraHang, String> colMaHD;
    public TableColumn<PhieuTraHang, String> colNgayLap;
    public TableColumn<PhieuTraHang, String> colTenKH;
    public TableColumn<PhieuTraHang, String> colSdtKH;
    public TableColumn<PhieuTraHang, String> colTenNV;
    public TableColumn<PhieuTraHang, String> colChiTiet;
    public ComboBox<String> cboTimKiem;
    public TextField txtNoiDungTimKiem;
    public DatePicker dpTuNgay;
    public DatePicker dpDenNgay;
    public ComboBox<String> cbLoc;
    public Button btnTimKiem;
    public Button btnHuyBo;

    private final PhieuTraHang_Dao phieuTraHangDao = new PhieuTraHang_Dao();

    @Override
    public void start(Stage stage) throws Exception {
        new TKPhieuTraHang_GUI().showWithController(stage, this);
        stage.show();

    }

    public void initialize() {

        cboTimKiem.getItems().addAll(
            "Mã phiếu trả", "Mã hóa đơn", "Tên khách hàng", "SĐT khách hàng", "Tên nhân viên", "Ngày lập"
        );
        cboTimKiem.setValue("Tiêu chí");
        cbLoc.getItems().addAll(
            "Tất cả", "Hôm nay", "7 ngày gần nhất", "Tháng này", "Năm nay"
        );
        cbLoc.setValue("⌛ Bộ lọc nhanh");
        tblPT.setRowFactory(tv -> {
            TableRow<PhieuTraHang> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    PhieuTraHang rowData = row.getItem();
                    btnChiTietClick(rowData);
                }
            });
            return row;
        });
        btnTimKiem.setOnAction(e -> timKiem());
        btnHuyBo.setOnAction(e -> lamMoi());
        cbLoc.setOnAction(e -> boLocNhanh());
        Platform.runLater(()->{
            loadTable();
        });
    }

    public void loadTable() {
        List<PhieuTraHang> list = phieuTraHangDao.selectAll();
        ObservableList<PhieuTraHang> data = FXCollections.observableArrayList(list);
        colSTT.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(tblPT .getItems().indexOf(cellData.getValue()) + 1)
        );
        colMaPT.setCellValueFactory(new PropertyValueFactory<>("maPT"));
//        colMaHD.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoaDon().getMaHD()));
        colMaHD.setCellValueFactory(cellData -> {
            PhieuTraHang pt = cellData.getValue();
            if (pt.getHoaDon() != null) {
                // k null
                return new SimpleStringProperty(pt.getHoaDon().getMaHD());
            } else {
                // null
                return new SimpleStringProperty("");
            }
        });
        colNgayLap.setCellValueFactory(cellData -> new SimpleStringProperty(DoiNgay.dinhDangThoiGian(cellData.getValue().getNgayLap())));
        colTenKH.setCellValueFactory(cellData -> {
            var kh = cellData.getValue().getKhachHang();
            String name = (kh != null && kh.getTenKH() != null) ? kh.getTenKH() : "";
            return new SimpleStringProperty(name);
        });
        colTenKH.setCellFactory(col -> new TableCell<PhieuTraHang, String>() {
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

        colSdtKH.setCellValueFactory(cellData -> {
            var kh = cellData.getValue().getKhachHang();
            String sdt = (kh != null && kh.getSdt() != null) ? kh.getSdt() : "";
            return new SimpleStringProperty(sdt);
        });

        colTenNV.setCellValueFactory(cellData -> {
            var nv = cellData.getValue().getNhanVien();
            String ten = (nv != null && nv.getTenNV() != null) ? nv.getTenNV() : "";
            return new SimpleStringProperty(ten);
        });
        colTenNV.setCellFactory(col -> new TableCell<PhieuTraHang, String>() {
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
        colChiTiet.setCellFactory(col -> new TableCell<PhieuTraHang, String>() {
            final Button btn = new Button("Xem");
            {
                btn.getStyleClass().add("btn-detail");
                btn.setOnAction(event -> {
                    PhieuTraHang data = getTableView().getItems().get(getIndex());
                    btnChiTietClick(data);
                });
            }
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(btn);
                    setText(null);
                }
            }
        });
        tblPT.setItems(data);
    }

    private void btnChiTietClick(PhieuTraHang pTra) {
        try {
            ChiTietPhieuTraHang_Ctrl chiTietCtrl = new ChiTietPhieuTraHang_Ctrl();
            chiTietCtrl.setPhieuTraHang(pTra);
            Stage dialog = new Stage();
            dialog.initOwner(tblPT.getScene().getWindow());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiết phiếu trả hàng: " + pTra.getMaPT());
            String iconPath = "/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png";
            dialog.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath))));
            ChiTietPhieuTraHang_GUI.showWithController(dialog, chiTietCtrl);
        } catch (Exception e) {
            System.err.println("Cửa sổ chi tiết phiếu trả bị lỗi và đã tự đóng.");
            e.printStackTrace();
        }
    }

    private void timKiem() {
        String tieuChi = cboTimKiem.getValue();

        String noiDung = txtNoiDungTimKiem.getText().trim().toLowerCase();
        var tuNgay = dpTuNgay != null ? dpTuNgay.getValue() : null;
        var denNgay = dpDenNgay != null ? dpDenNgay.getValue() : null;
        List<PhieuTraHang> list = phieuTraHangDao.selectAll();
        if ((tieuChi == null || tieuChi.equals("Tiêu chí")) && (tuNgay == null && denNgay == null) && noiDung.isEmpty()) {
            loadTable();
            return;
        }
        List<PhieuTraHang> filtered = list.stream().filter(pt -> {
            boolean match = true;
            switch (tieuChi) {
                case "Mã phiếu trả":
                    match = pt.getMaPT().toLowerCase().contains(noiDung);
                    break;
                case "Mã hóa đơn":
                    match = pt.getHoaDon() != null && pt.getHoaDon().getMaHD() != null && pt.getHoaDon().getMaHD().toLowerCase().contains(noiDung);
                    break;
                case "Tên khách hàng":
                    match = pt.getKhachHang() != null && pt.getKhachHang().getTenKH() != null && pt.getKhachHang().getTenKH().toLowerCase().contains(noiDung);
                    break;
                case "SĐT khách hàng":
                    match = pt.getKhachHang() != null && pt.getKhachHang().getSdt() != null && pt.getKhachHang().getSdt().toLowerCase().contains(noiDung);
                    break;
                case "Tên nhân viên":
                    match = pt.getNhanVien() != null && pt.getNhanVien().getTenNV().toLowerCase().contains(noiDung);
                    break;
                case "Ngày lập":
                    match = pt.getNgayLap() != null && DoiNgay.dinhDangThoiGian(pt.getNgayLap()).contains(noiDung);
                    break;
            }
            if (tuNgay != null && pt.getNgayLap() != null) {
                match = match && !pt.getNgayLap().toLocalDateTime().toLocalDate().isBefore(tuNgay);
            }
            if (denNgay != null && pt.getNgayLap() != null) {
                match = match && !pt.getNgayLap().toLocalDateTime().toLocalDate().isAfter(denNgay);
            }
            return match;
        }).toList();
        tblPT.setItems(FXCollections.observableArrayList(filtered));
    }

    private void lamMoi() {
        txtNoiDungTimKiem.clear();
        cboTimKiem.setValue("Mã phiếu trả");
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


}
