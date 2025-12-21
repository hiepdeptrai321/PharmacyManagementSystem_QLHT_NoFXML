package com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuDoi;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhachHang.ThemKhachHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDoiHang.ChiTietPhieuDoiHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.controller.DangNhap_Ctrl;
import com.example.pharmacymanagementsystem_qlht.dao.*;
import com.example.pharmacymanagementsystem_qlht.model.*;
import com.example.pharmacymanagementsystem_qlht.service.DoiHangItem;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKhachHang.ThemKhachHang_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDoi.ChiTietPhieuDoiHang_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuDoi.LapPhieuDoi_GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

import static com.example.pharmacymanagementsystem_qlht.TienIch.TuyChinhAlert.hien;
import static com.example.pharmacymanagementsystem_qlht.TienIch.TuyChinhAlert.hoi;
import static javafx.scene.control.Alert.AlertType.*;

public class LapPhieuDoiHang_Ctrl extends Application {

    public TableView<ChiTietHoaDon> tblSanPhamGoc;
    public TableColumn<ChiTietHoaDon, String> colSTTGoc;
    public TableColumn<ChiTietHoaDon, String> colTenSPGoc;
    public TableColumn<ChiTietHoaDon, String> colSoLuongGoc;
    public TableColumn<ChiTietHoaDon, String> colDonViGoc;
    public TableColumn<ChiTietHoaDon, String> colDonGiaGoc;
    public TableColumn<ChiTietHoaDon, String> colGiamGiaGoc;
    public TableColumn<ChiTietHoaDon, String> colThanhTienGoc;
    public TableColumn<ChiTietHoaDon, Void> colDoi;

    public TableView<DoiHangItem> tblSanPhamDoi;
    public TableColumn<DoiHangItem, String> colSTTDoi;
    public TableColumn<DoiHangItem, String> colTenSPDoi;
    public TableColumn<DoiHangItem, Number> colSoLuongDoi;
    public TableColumn<DoiHangItem, String> colDonViDoi;
    public TableColumn<DoiHangItem, String> colLyDo;
    public TableColumn<DoiHangItem, Void> colBo;

    public TextField txtTimHoaDonGoc;
    public Button btnTimHD;
    public Button btnDoi;
    public Button btnHuy;
    public TextField lblMaHDGoc;
    public TextField lblTenKH;
    public TextField lblSDT;
    public DatePicker dpNgayLapPhieu;
    public TextArea txtGhiChu;

    // State
    private final ObservableList<ChiTietHoaDon> dsGoc = FXCollections.observableArrayList();
    private final ObservableList<DoiHangItem> dsDoi = FXCollections.observableArrayList();
    private final Map<String, DoiHangItem> doiByMaLH = new HashMap<>();

    // DAO
    private final HoaDon_Dao hoaDonDao = new HoaDon_Dao();
    private final ChiTietHoaDon_Dao cthdDao = new ChiTietHoaDon_Dao();
    private final KhachHang_Dao khDao = new KhachHang_Dao();
    private final ChiTietPhieuDoiHang_Dao ctpdDao = new ChiTietPhieuDoiHang_Dao();
    private  HoaDon hoaDonGoc ;

    @Override
    public void start(Stage stage) throws Exception {
        LapPhieuDoi_GUI gui = new LapPhieuDoi_GUI();
        gui.showWithController(stage, this);
    }
    public void initialize() {
        dpNgayLapPhieu.setValue(LocalDate.now());
        txtTimHoaDonGoc.setOnAction(e -> btnTimHD.fire());
        Platform.runLater(() -> txtTimHoaDonGoc.getParent().requestFocus());
        guiMacDinh();
        btnTimHD.setOnAction(e-> xuLyTimHoaDonGoc());
        btnDoi.setOnAction(e-> xuLyDoiHang());
        btnHuy.setOnAction(e-> xuLyHuy());
    }

    private int tinhSoLuongConDoi(ChiTietHoaDon ct) {
        if (ct == null || ct.getHoaDon() == null || ct.getLoHang() == null)
            return 0;
        int daDoi = ctpdDao.tongSoLuongDaDoi(
                ct.getHoaDon().getMaHD(),
                ct.getLoHang().getMaLH()
        );
        return Math.max(0, ct.getSoLuong() - daDoi);
    }
    private void xuLyChuyenSangDoi(ChiTietHoaDon cthdGoc) {
        if (cthdGoc == null || cthdGoc.getLoHang() == null) return;
        if (hoaDonDaDoiHet(cthdGoc.getHoaDon())) {
            hien(WARNING,
                    "Hóa đơn đã đổi hết hàng",
                    "Tất cả sản phẩm trong hóa đơn này đã được đổi hết.");
            return;
        }

        String key = cthdGoc.getLoHang().getMaLH() + "_" +
                (cthdGoc.getDvt() != null ? cthdGoc.getDvt().getMaDVT() : "null");
        // check sl doi
        int daDoi = ctpdDao.tongSoLuongDaDoi(
                cthdGoc.getHoaDon().getMaHD(),
                cthdGoc.getLoHang().getMaLH()
        );
        int slConDoi = Math.max(0, cthdGoc.getSoLuong() - daDoi);
        if (slConDoi <= 0) {
            hien(WARNING,
                    "Sản phẩm đã đổi hết",
                    "Sản phẩm \"" + tenSP(cthdGoc) + "\" đã được đổi hết số lượng cho phép.");
            return;
        }
        // check ton kho
        Thuoc_SP_TheoLo_Dao loDao = new Thuoc_SP_TheoLo_Dao();
        int tonKho = loDao.selectSoLuongTonByMaThuoc(
                cthdGoc.getLoHang().getThuoc().getMaThuoc()
        );
        int max = Math.min(slConDoi, tonKho);
        if (max <= 0) {
            hien(WARNING,
                    "Không đủ tồn kho",
                    "Sản phẩm \"" + tenSP(cthdGoc) + "\" hiện không đủ tồn kho để đổi.");
            return;
        }
        // them dong doi
        DoiHangItem vm = doiByMaLH.get(key);
        if (vm == null) {
            vm = new DoiHangItem(cthdGoc, 1, "");
            dsDoi.add(vm);
            doiByMaLH.put(key, vm);
        } else {
            vm.setSoLuongDoi(Math.min(max, vm.getSoLuongDoi() + 1));
        }
    }

    public void guiMacDinh(){

        if (tblSanPhamGoc != null) {
            tblSanPhamGoc.setItems(dsGoc);
            tblSanPhamGoc.setPlaceholder(new Label("Chưa có sản phẩm gốc"));
            safeSetConstrainedResize(tblSanPhamGoc);
        }
        if (tblSanPhamDoi != null) {
            tblSanPhamDoi.setItems(dsDoi);
            tblSanPhamDoi.setEditable(true);
            tblSanPhamDoi.setPlaceholder(new Label("Chưa có chi tiết đổi hàng"));
            safeSetConstrainedResize(tblSanPhamDoi);
        }
        setupTblGocColumns();
        setupTblDoiColumns();
    }

    private void setupTblGocColumns() {
        tblSanPhamGoc.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(ChiTietHoaDon ct, boolean empty) {
                super.updateItem(ct, empty);

                if (empty || ct == null) {
                    setStyle("");
                    return;
                }

                int slConDoi = tinhSoLuongConDoi(ct);

                if (slConDoi <= 0) {
                    setStyle("""
                -fx-background-color: #ffe6e6;
                -fx-text-fill: #b00020;
            """);
                } else {
                    setStyle("");
                }
            }
        });
        if (colSTTGoc != null) {
            colSTTGoc.setCellFactory(tc -> new TableCell<>() {
                @Override protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : String.valueOf(getIndex() + 1));
                }
            });
            colSTTGoc.setSortable(false);
            colSTTGoc.setReorderable(false);
        }
        if (colTenSPGoc != null) {
            colTenSPGoc.setCellValueFactory(p -> javafx.beans.binding.Bindings.createStringBinding(
                    () -> tenSP(p.getValue())));
        }
        if (colSoLuongGoc != null) {
            colSoLuongGoc.setCellValueFactory(p -> javafx.beans.binding.Bindings.createStringBinding(
                    () -> String.valueOf(p.getValue().getSoLuong())));
            alignRight(colSoLuongGoc);
        }
        if (colDonViGoc != null) {
            colDonViGoc.setCellValueFactory(p -> javafx.beans.binding.Bindings.createStringBinding(() -> {
                ChiTietHoaDon cthd = p.getValue();
                if (cthd == null) return "";
                DonViTinh dvt = cthd.getDvt();
                if (dvt != null && dvt.getTenDonViTinh() != null) {
                    return dvt.getTenDonViTinh();
                }
                return donViCoBan(cthd); // fallback
            }));
            alignRight(colDonViGoc);
        }
        if (colDonGiaGoc != null) {
            colDonGiaGoc.setCellValueFactory(p -> javafx.beans.binding.Bindings.createStringBinding(
                    () -> vnd(p.getValue().getDonGia())));
            alignRight(colDonGiaGoc);
        }
        if (colGiamGiaGoc != null) {
            colGiamGiaGoc.setCellValueFactory(p -> javafx.beans.binding.Bindings.createStringBinding(
                    () -> vnd(p.getValue().getGiamGia())));
            alignRight(colGiamGiaGoc);
        }
        if (colThanhTienGoc != null) {
            colThanhTienGoc.setCellValueFactory(p -> javafx.beans.binding.Bindings.createStringBinding(() -> {
                ChiTietHoaDon r = p.getValue();
                double tt = Math.max(0, r.getSoLuong() * r.getDonGia() - r.getGiamGia());
                return vnd(tt);
            }));
            alignRight(colThanhTienGoc);
        }
        if (colDoi != null) {
            colDoi.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(null));

            colDoi.setCellFactory(tc -> new TableCell<>() {

                private final Button btn = new Button("↓");

                {
                    btn.getStyleClass().add("btn-doi");
                    setStyle("-fx-alignment: center;");

                    btn.setOnAction(e -> {
                        ChiTietHoaDon ct =
                                getTableView().getItems().get(getIndex());
                        xuLyChuyenSangDoi(ct);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        return;
                    }

                    ChiTietHoaDon ct =
                            getTableView().getItems().get(getIndex());
                    int slConDoi = tinhSoLuongConDoi(ct);

                    btn.setDisable(slConDoi <= 0);
                    btn.setOpacity(slConDoi <= 0 ? 0.4 : 1);

                    btn.setTooltip(new Tooltip(
                            slConDoi <= 0
                                    ? "Sản phẩm đã được đổi hết"
                                    : "Chuyển sang danh sách đổi"
                    ));

                    setGraphic(btn);
                }
            });

            colDoi.setSortable(false);
            colDoi.setReorderable(false);
        }
    }

    private void setupTblDoiColumns() {
        if (colSTTDoi != null) {
            colSTTDoi.setCellFactory(tc -> new TableCell<>() {
                @Override protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : String.valueOf(getIndex() + 1));
                }
            });
            colSTTDoi.setSortable(false);
            colSTTDoi.setReorderable(false);
        }
        if (colTenSPDoi != null) {
            colTenSPDoi.setCellValueFactory(p -> javafx.beans.binding.Bindings.createStringBinding(
                    () -> tenSP(p.getValue().getGoc())));
        }
        if (colDonViDoi != null) {
            colDonViDoi.setCellValueFactory(p -> javafx.beans.binding.Bindings.createStringBinding(() -> {
                ChiTietHoaDon goc = p.getValue().getGoc();
                if (goc == null) return "";
                DonViTinh dvt = goc.getDvt();
                if (dvt != null && dvt.getTenDonViTinh() != null) {
                    return dvt.getTenDonViTinh();
                }
                return donViCoBan(goc);
            }));
            alignRight(colDonViDoi);
        }
        if (colSoLuongDoi != null) {
            colSoLuongDoi.setCellValueFactory(p -> p.getValue().soLuongDoiProperty());
            colSoLuongDoi.setCellFactory(tc -> new TableCell<>() {
                private final Button btnMinus = new Button("-");
                private final Button btnPlus = new Button("+");
                private final TextField tf = new TextField();
                private final HBox box = new HBox(6, btnMinus, tf, btnPlus);
                {
                    tf.setPrefWidth(56);
                    tf.setMaxWidth(56);
                    tf.setStyle("-fx-alignment: center-right;");
                    tf.setTextFormatter(new TextFormatter<>(chg ->
                            chg.getControlNewText().matches("\\d{0,6}") ? chg : null));

                    btnMinus.setOnAction(e -> adjust(-1));
                    btnPlus.setOnAction(e -> adjust(+1));
                    tf.setOnAction(e -> commitFromText());
                    tf.focusedProperty().addListener((o, was, now) -> { if (!now) commitFromText(); });
                }
                private void adjust(int delta) {
                    int idx = getIndex();
                    if (idx < 0 || idx >= tblSanPhamDoi.getItems().size()) return;
                    DoiHangItem vm = tblSanPhamDoi.getItems().get(idx);
                    if (vm == null) return;
                    int cur = vm.getSoLuongDoi();
                    int max = vm.getGoc() == null ? Integer.MAX_VALUE : Math.max(0, vm.getGoc().getSoLuong());
                    int target = Math.max(1, Math.min(max, cur + delta));
                    if (target != cur) vm.setSoLuongDoi(target);
                    tf.setText(String.valueOf(vm.getSoLuongDoi()));
                }
                private void commitFromText() {
                    int idx = getIndex();
                    if (idx < 0 || idx >= tblSanPhamDoi.getItems().size()) return;
                    DoiHangItem vm = tblSanPhamDoi.getItems().get(idx);
                    if (vm == null) return;
                    String s = tf.getText();
                    try {
                        int val = Integer.parseInt(s);
                        if (val <= 0) val = 1;
                        int max = vm.getGoc() == null ? Integer.MAX_VALUE : Math.max(0, vm.getGoc().getSoLuong());
                        if (val > max) val = max;
                        vm.setSoLuongDoi(val);
                    } catch (NumberFormatException ignore) {
                        tf.setText(String.valueOf(vm.getSoLuongDoi()));
                    }
                }
                @Override protected void updateItem(Number item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) { setGraphic(null); setText(null); }
                    else {
                        DoiHangItem vm = getTableView().getItems().get(getIndex());
                        tf.setText(vm != null ? String.valueOf(vm.getSoLuongDoi()) : "1");
                        setGraphic(box);
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                }
            });
        }
        if (colLyDo != null) {
            colLyDo.setCellValueFactory(p -> p.getValue().lyDoProperty());
            colLyDo.setCellFactory(tc -> {
                TextFieldTableCell<DoiHangItem, String> cell =
                        new TextFieldTableCell<>(new DefaultStringConverter());

                cell.setAlignment(Pos.CENTER_LEFT); // quan trọng
                cell.setStyle("-fx-padding: 0 6 0 6;");

                return cell;
            });
            colLyDo.setOnEditCommit(ev -> {
                DoiHangItem vm = ev.getRowValue();
                if (vm != null) vm.setLyDo(ev.getNewValue() == null ? "" : ev.getNewValue().trim());
            });
        }
        if (colBo != null) {
            colBo.setCellFactory(tc -> new TableCell<>() {
                private final Button btn = new Button("✕");
                {
                    btn.getStyleClass().add("btn-bo");
                    btn.setTooltip(new Tooltip("Bỏ khỏi danh sách đổi"));
                    btn.setOnAction(e -> {
                        int idx = getIndex();
                        if (idx < 0 || idx >= tblSanPhamDoi.getItems().size()) return;

                        DoiHangItem vm = tblSanPhamDoi.getItems().get(idx);
                        if (vm != null && vm.getGoc() != null && vm.getGoc().getLoHang() != null) {
                            String key = vm.getGoc().getLoHang().getMaLH() + "_" +
                                    (vm.getGoc().getDvt() != null ? vm.getGoc().getDvt().getMaDVT() : "null");
                            doiByMaLH.remove(key);
                        }
                        dsDoi.remove(vm);
                        tblSanPhamDoi.refresh();
                    });
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setStyle("-fx-alignment: center;");
                }
                @Override protected void updateItem(Void it, boolean empty) {
                    super.updateItem(it, empty);
                    setGraphic(empty ? null : btn);
                    setText(null);
                }
            });
            colBo.setSortable(false);
            colBo.setReorderable(false);
        }
    }

    private <S, T> void alignRight(TableColumn<S, T> col) {
        col.setCellFactory(tc -> new TableCell<S, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(item));
                setStyle(empty ? "" : "-fx-alignment: CENTER-RIGHT;");
            }
        });
    }

    private String tenSP(ChiTietHoaDon row) {
        if (row == null || row.getLoHang() == null) return "";
        Thuoc_SP_TheoLo lo = row.getLoHang();
        Thuoc_SanPham sp = lo.getThuoc();
        return (sp != null && sp.getTenThuoc() != null) ? sp.getTenThuoc() : "";
    }

    private String donViCoBan(ChiTietHoaDon row) {
        if (row == null || row.getLoHang() == null) return "";
        Thuoc_SanPham sp = row.getLoHang().getThuoc();
        if (sp == null || sp.getDsCTDVT() == null || sp.getDsCTDVT().isEmpty()) return "";
        for (ChiTietDonViTinh ct : sp.getDsCTDVT()) {
            if (ct.isDonViCoBan() && ct.getDvt() != null && ct.getDvt().getTenDonViTinh() != null) {
                return ct.getDvt().getTenDonViTinh();
            }
        }
        ChiTietDonViTinh first = sp.getDsCTDVT().get(0);
        return first.getDvt() != null ? first.getDvt().getTenDonViTinh() : "";
    }

    private String vnd(double v) { return vnd(Math.round(Math.max(0, v))); }
    private String vnd(long amount) {
        DecimalFormat df = new DecimalFormat("#,###");
        df.setGroupingUsed(true);
        return df.format(Math.max(0, amount)) + " VNĐ";
    }

    private void safeSetConstrainedResize(TableView<?> tv) {
        try { tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); } catch (Exception ignored) {}
    }



    private boolean hoaDonDaDoiHet(HoaDon hd) {
        if (hd == null) return true;

        List<ChiTietHoaDon> ds = cthdDao.selectByMaHD(hd.getMaHD());
        if (ds == null || ds.isEmpty()) return true;

        for (ChiTietHoaDon ct : ds) {
            if (ct.getLoHang() == null) continue;

            int daDoi = ctpdDao.tongSoLuongDaDoi(
                    hd.getMaHD(),
                    ct.getLoHang().getMaLH()
            );

            int slCon = ct.getSoLuong() - daDoi;
            if (slCon > 0) {
                return false; // còn ít nhất 1 SP đổi được
            }
        }
        return true; // tất cả đã đổi hết
    }
    public void xuLyTimHoaDonGoc() {
        String ma = txtTimHoaDonGoc == null ? null : txtTimHoaDonGoc.getText();
        if (ma == null || ma.isBlank()) {
            hien(WARNING,
                    "Mã hóa đơn gốc không thể trống",
                    "Vui lòng nhập mã hóa đơn gốc.");
            return;
        }

        try {
            HoaDon hd = hoaDonDao.selectById(ma);
            if (hd == null) {
                hien(WARNING,
                        "Không thể tìm thấy hóa đơn gốc",
                        "Vui lòng kiểm tra lại mã hóa đơn.");
                return;
            }
            this.hoaDonGoc = hd;
            lblMaHDGoc.setText(hd.getMaHD());
            dpNgayLapPhieu.setValue(LocalDate.now());

            List<ChiTietHoaDon> lines = cthdDao.selectByMaHD(ma);
            dsGoc.setAll(lines);

            // check hạn đổi 7 ngày
            LocalDate ngayHD = hd.getNgayLap().toLocalDateTime().toLocalDate();
            if (ngayHD.isBefore(LocalDate.now().minusDays(7))) {
                hien(WARNING,
                        "Hóa đơn đã quá hạn 7 ngày",
                        "Vui lòng kiểm tra lại!");
                return;
            }

            // load khach hang
            String tenKH = "";
            String sdt = "";
            if (hd.getMaKH() != null && hd.getMaKH().getMaKH() != null) {
                KhachHang kh = khDao.selectById(hd.getMaKH().getMaKH());
                if (kh != null) {
                    tenKH = kh.getTenKH() == null ? "" : kh.getTenKH();
                    sdt = kh.getSdt() == null ? "" : kh.getSdt();
                }
            }
            lblTenKH.setText(tenKH);
            lblSDT.setText(sdt);
            if (chuaCoKhachHang(hd)) {

                hien(
                        WARNING,
                        "Hóa đơn chưa có khách hàng",
                        "Hóa đơn này là khách lẻ.\n" +
                                "Vui lòng bổ sung thông tin khách hàng để tiếp tục."
                );
                moFormThemKhachHang(hd);
                return;
            }
            dsDoi.clear();
            doiByMaLH.clear();

            if (hoaDonDaDoiHet(hd)) {
                hien(WARNING,
                        "Hóa đơn đã đổi hết hàng",
                        "Tất cả sản phẩm trong hóa đơn này đã được đổi hết.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            hien(ERROR,
                    "Lỗi tải hóa đơn gốc",
                    "Không thể tải hóa đơn gốc. Vui lòng thử lại.");
        }
    }
    private boolean chuaCoKhachHang(HoaDon hd) {
        if (hd == null) return true;
        if (hd.getMaKH() == null) return true;
        return hd.getMaKH().getMaKH() == null
                || hd.getMaKH().getMaKH().trim().isEmpty();
    }
    private void moFormThemKhachHang(HoaDon hd) {
        try {
            // 1. Tạo Stage
            Stage stage = new Stage();
            stage.setTitle("Thêm khách hàng");
            stage.initOwner(btnTimHD.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);

            // 2. Tạo Controller
            ThemKhachHang_Ctrl ctrl = new ThemKhachHang_Ctrl();

            // 3. Set callback khi lưu thành công
            ctrl.setOnSaved(kh -> {

                hoaDonDao.updateKhachHang(
                        hoaDonGoc.getMaHD(),
                        kh.getMaKH()
                );

                // 🔥 LOAD LẠI HÓA ĐƠN GỐC TỪ DB
                hoaDonGoc = hoaDonDao.selectById(hoaDonGoc.getMaHD());

                lblTenKH.setText(kh.getTenKH());
                lblSDT.setText(kh.getSdt());

                hien(
                        INFORMATION,
                        "Thành công",
                        "Đã bổ sung thông tin khách hàng."
                );
            });

            // 4. Tạo GUI và gắn controller
            ThemKhachHang_GUI gui = new ThemKhachHang_GUI();
            gui.showWithController(stage, ctrl);

            // 5. Hiển thị
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            hien(ERROR, "Lỗi", "Không thể mở form thêm khách hàng");
        }
    }

    public void xuLyDoiHang() {
        if (hoaDonGoc == null || hoaDonGoc.getMaHD() == null) {
            hien(
                    WARNING,
                    "Chưa chọn hóa đơn gốc",
                    "Vui lòng tìm hóa đơn gốc trước khi đổi hàng."
            );
            return;
        }
        if (dsDoi.isEmpty()) {
            hien(WARNING, "Danh sách đổi hàng trống", "Vui lòng thêm sản phẩm để đổi.");
            return;
        }
        if (chuaCoKhachHang(hoaDonGoc)) {
            hien(
                    WARNING,
                    "Chưa có khách hàng",
                    "Vui lòng bổ sung thông tin khách hàng trước khi đổi / trả."
            );

            moFormThemKhachHang(hoaDonGoc);
            return;
        }
        for (DoiHangItem item : dsDoi) {
            if (item.getLyDo() == null || item.getLyDo().trim().isEmpty()) {
                hien(WARNING, "Thiếu lý do đổi hàng",
                         "Vui lòng nhập lý do đổi cho sản phẩm: " + tenSP(item.getGoc()));

                tblSanPhamDoi.getSelectionModel().select(item);
                tblSanPhamDoi.scrollTo(item);
                return;
            }
        }
        Thuoc_SP_TheoLo_Dao loDaoCheck = new Thuoc_SP_TheoLo_Dao();

        for (DoiHangItem item : dsDoi) {
            ChiTietHoaDon goc = item.getGoc();
            if (goc == null || goc.getLoHang() == null) {
                hien(ERROR, "Lỗi dữ liệu", "Sản phẩm đổi không hợp lệ");
                return;
            }

            int soLuongCan = item.getSoLuongDoi();

            List<Thuoc_SP_TheoLo> loList =
                    loDaoCheck.selectLoHangFEFO_Multi(
                            goc.getLoHang().getThuoc().getMaThuoc(),
                            soLuongCan
                    );

            int tongTon = loList.stream()
                    .mapToInt(Thuoc_SP_TheoLo::getSoLuongTon)
                    .sum();

            if (tongTon < soLuongCan) {
                hien(
                        WARNING,
                        "Không đủ tồn kho",
                        "Sản phẩm: " + tenSP(goc)
                                + "\nCần: " + soLuongCan
                                + " | Còn: " + tongTon
                                + "\n→ Phiếu đổi KHÔNG được tạo"
                );
                return;
            }
        }

        try {
            // Tạo phiếu đổi
            PhieuDoiHang phieu = new PhieuDoiHang();
            PhieuDoiHang_Dao pdDao = new PhieuDoiHang_Dao();
            phieu.setMaPD(pdDao.generateNewMaPD());
            phieu.setNgayLap(new Timestamp(System.currentTimeMillis()));

            // Gán hóa đơn gốc
            if (lblMaHDGoc != null && lblMaHDGoc.getText() != null) {
                HoaDon hdGoc = hoaDonDao.selectById(lblMaHDGoc.getText());
                phieu.setHoaDon(hdGoc);

                // Nếu cần lưu khách hàng theo hoá đơn luôn
                if (hdGoc != null && hdGoc.getMaKH() != null) {
                    phieu.setKhachHang(hdGoc.getMaKH());
                }
            }

            phieu.setNhanVien(DangNhap_Ctrl.user);
            phieu.setGhiChu(txtGhiChu == null ? "" : txtGhiChu.getText());

            new PhieuDoiHang_Dao().insert(phieu);

            Thuoc_SP_TheoLo_Dao loDao = new Thuoc_SP_TheoLo_Dao();
            ChiTietPhieuDoiHang_Dao ctpdDao = new ChiTietPhieuDoiHang_Dao();

            for (DoiHangItem item : dsDoi) {
                ChiTietHoaDon goc = item.getGoc();
                if (goc == null || goc.getLoHang() == null) continue;

                Thuoc_SP_TheoLo loCu = goc.getLoHang();
                int soLuong = item.getSoLuongDoi();


                // FEFO: phân bổ từ nhiều lô
                int soLuongCan = soLuong;
                List<Thuoc_SP_TheoLo> loMoiList =
                        loDao.selectLoHangFEFO_Multi(loCu.getThuoc().getMaThuoc(), soLuongCan);

                if (loMoiList == null || loMoiList.isEmpty()) {
                    throw new RuntimeException(
                            "Không tìm thấy lô hàng để đổi cho sản phẩm: " + tenSP(goc)
                    );
                }

                List<ChiTietPhieuDoiHang> dsChiTietTheoLo = new ArrayList<>();

                for (Thuoc_SP_TheoLo loMoi : loMoiList) {
                    if (soLuongCan <= 0) break;

                    int tonLoMoi = loMoi.getSoLuongTon();
                    if (tonLoMoi <= 0) continue;

                    int soXuat = Math.min(tonLoMoi, soLuongCan);

                    loMoi.setSoLuongTon(tonLoMoi - soXuat);
                    loDao.update(loMoi);

                    soLuongCan -= soXuat;
                    String lyDoItem = item.getLyDo().trim();
                    ChiTietPhieuDoiHang ctpd = new ChiTietPhieuDoiHang(
                            loMoi,
                            phieu,
                            loMoi.getThuoc(),
                            soXuat,
                            goc.getDvt(),
                            lyDoItem

                    );
                    dsChiTietTheoLo.add(ctpd);
                }

                // Nếu vẫn còn thiếu hàng => rollback riêng sản phẩm này
                if (soLuongCan > 0) {
                    throw new RuntimeException(
                            "Không đủ tồn kho khi phân bổ lô cho sản phẩm: " + tenSP(goc)
                    );
                }

                for (ChiTietPhieuDoiHang ctSave : dsChiTietTheoLo) {
                    ctpdDao.insert(ctSave);
                }
            }
            if (ctpdDao.countByMaPD(phieu.getMaPD()) == 0) {
                throw new RuntimeException("Phiếu đổi không có chi tiết");
            }
            Optional<ButtonType> result = hoi(
                    "Thành công",
                    "Lập phiếu đổi hàng thành công",
                    "Bạn có muốn xem chi tiết phiếu đổi hàng không?");

            if (result.orElse(ButtonType.CANCEL).getButtonData() == ButtonType.OK.getButtonData()) {
                try {
                    ChiTietPhieuDoiHang_Ctrl ctrl = new ChiTietPhieuDoiHang_Ctrl();

                    Stage stage = new Stage();
                    stage.setTitle("Chi tiết phiếu đổi " + phieu.getMaPD());

                    ChiTietPhieuDoiHang_GUI gui = new ChiTietPhieuDoiHang_GUI();
                    gui.showWithController(stage, ctrl);

                    // Lấy lại phiếu đổi đầy đủ từ DB
                    PhieuDoiHang_Dao phieuDoiDao = new PhieuDoiHang_Dao();
                    PhieuDoiHang phieuDoiDayDu =
                            phieuDoiDao.selectById(phieu.getMaPD());

                    ctrl.setPhieuDoiHang(phieuDoiDayDu);

                    stage.show();

                } catch (Exception e) {
                    hien(ERROR, "Lỗi", "Không thể mở chi tiết phiếu đổi hàng:\n" + e.getMessage());
                    e.printStackTrace();
                }
            }

// Reset / đóng form hiện tại
            xuLyHuy();
            dsDoi.clear();
            dsGoc.clear();
            doiByMaLH.clear();
            txtTimHoaDonGoc.clear();
            lblTenKH.clear();
            lblSDT.clear();
            lblMaHDGoc.clear();
            lblMaHDGoc.setText("");
            txtGhiChu.clear();
            tblSanPhamGoc.getItems().clear();
            tblSanPhamDoi.getItems().clear();

        } catch (Exception e) {
            e.printStackTrace();
            hien(ERROR," Lỗi khi đổi hàng", "Đã xảy ra lỗi trong quá trình đổi hàng: " + e.getMessage());
        }
    }

    public void xuLyHuy() {
        dsDoi.clear();
        dsGoc.clear();
        doiByMaLH.clear();
        lblTenKH.clear();
        lblSDT.clear();
        lblMaHDGoc.clear();
        lblMaHDGoc.setText("");
        txtGhiChu.clear();
        tblSanPhamGoc.getItems().clear();
        tblSanPhamDoi.getItems().clear();
        txtTimHoaDonGoc.clear();
        txtTimHoaDonGoc.setPromptText("Nhập mã hóa đơn gốc...");
    }
}
