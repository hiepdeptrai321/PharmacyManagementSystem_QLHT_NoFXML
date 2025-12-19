package com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuTra;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuTraHang.ChiTietPhieuTraHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.controller.DangNhap_Ctrl;
import com.example.pharmacymanagementsystem_qlht.dao.*;
import com.example.pharmacymanagementsystem_qlht.model.*;
import com.example.pharmacymanagementsystem_qlht.service.TraHangItem;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuTra.ChiTietPhieuTraHang_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuTra.LapPhieuTra_GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.lang.reflect.Method;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

import static com.example.pharmacymanagementsystem_qlht.TienIch.TuyChinhAlert.hien;
import static javafx.scene.control.Alert.AlertType.*;

public class LapPhieuTraHang_Ctrl extends Application {
    public TextField lblMaHDGoc;
    public TextField lblTenKH;
    public TextField lblSDT;
    public DatePicker dpNgayLapPhieu;
    public Label lblTongTienGoc;
    public Label lblTongTienTraLai;
    public Label lblVAT;
    public Label lblSoTienTraLai;
    public TextArea txtGhiChu;
    public TextField txtTimHoaDon;
    public Button btnTimHoaDon;
    public Button btnTraHang;
    public Button btnHuy;

    public TableView<ChiTietHoaDon> tblSanPhamHoaDon;
    public TableColumn<ChiTietHoaDon, String> colSTTGoc;
    public TableColumn<ChiTietHoaDon, String> colTenSPGoc;
    public TableColumn<ChiTietHoaDon, String> colSLGoc;
    public TableColumn<ChiTietHoaDon, String> colDonViGoc;
    public TableColumn<ChiTietHoaDon, String> colDonGiaGoc;
    public TableColumn<ChiTietHoaDon, String> colGiamGiaGoc;
    public TableColumn<ChiTietHoaDon, String> colThanhTienGoc;
    public TableColumn<ChiTietHoaDon, Void> colTra;

    public TableView<TraHangItem> tblChiTietTraHang;
    public TableColumn<TraHangItem, String> colSTTTra;
    public TableColumn<TraHangItem, String> colTenSPTra;
    public TableColumn<TraHangItem, Number> colSLTra;
    public TableColumn<TraHangItem, String> colLyDo;
    public TableColumn<TraHangItem, String> colDonViTra;
    public TableColumn<TraHangItem, Double> colDonGiaTra;
    public TableColumn<TraHangItem, Double> colThanhTienTra;
    public TableColumn<TraHangItem, Void> colBo;

    private final ObservableList<ChiTietHoaDon> dsGoc = FXCollections.observableArrayList();
    private final ObservableList<TraHangItem> dsTra = FXCollections.observableArrayList();
    private final Map<String, TraHangItem> traByKey = new HashMap<>();
    private final HoaDon_Dao hoaDonDao = new HoaDon_Dao();
    private final ChiTietHoaDon_Dao cthdDao = new ChiTietHoaDon_Dao();
    private final KhachHang_Dao khDao = new KhachHang_Dao();
    private ChiTietPhieuTraHang_Dao ctpthDao = new ChiTietPhieuTraHang_Dao();
    private HoaDon hoaDonGoc;

    private final NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));


    @Override
    public void start(Stage stage) throws Exception {
        LapPhieuTra_GUI gui = new LapPhieuTra_GUI();
        gui.showWithController(stage, this);
    }
    public void initialize(URL location, ResourceBundle resources) {
        dpNgayLapPhieu.setValue(LocalDate.now());
        if (btnTimHoaDon != null) {
            btnTimHoaDon.setOnAction(e -> xuLyTimHDGoc());
        }
        if (txtTimHoaDon != null) {
            txtTimHoaDon.setOnAction(e -> xuLyTimHDGoc());
        }
        Platform.runLater(() -> txtTimHoaDon.getParent().requestFocus());
        if (tblSanPhamHoaDon != null) {
            tblSanPhamHoaDon.setItems(dsGoc);
            tblSanPhamHoaDon.setPlaceholder(new Label("Chưa có sản phẩm gốc"));
            safeSetConstrainedResize(tblSanPhamHoaDon);
        }
        if (tblChiTietTraHang != null) {
            tblChiTietTraHang.setItems(dsTra);
            tblChiTietTraHang.setEditable(true);
            tblChiTietTraHang.setPlaceholder(new Label("Chưa có chi tiết đổi hàng"));
            safeSetConstrainedResize(tblChiTietTraHang);
        }
        setupTblGocColumns();
        setupTblTraColumns();
    }
    public void setHoaDonGoc(HoaDon hd) {
        this.hoaDonGoc = hd;

        List<ChiTietHoaDon> list = cthdDao.selectByMaHD(hd.getMaHD());
        dsGoc.setAll(list);
    }
    private void safeSetConstrainedResize(TableView<?> tv) {
        try { tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); } catch (Exception ignored) {}
    }

    private void setupTblGocColumns() {
        if (colSTTGoc != null) {
            colSTTGoc.setCellFactory(tc -> new TableCell<>() {
                @Override protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) setText(null);
                    else setText(String.valueOf(getIndex() + 1));
                }
            });
            colSTTGoc.setSortable(false);
            colSTTGoc.setReorderable(false);
        }
        if (colTenSPGoc != null) {

            colTenSPGoc.setCellValueFactory(cd ->
                    Bindings.createStringBinding(() -> tenSP(cd.getValue()))
            );
        }
        if (colSLGoc != null) {
            colSLGoc.setCellValueFactory(cd ->
                    new ReadOnlyObjectWrapper<>(String.valueOf(cd.getValue().getSoLuong()))
            );
            alignRight(colSLGoc);
        }

        // Đơn vị
        if (colDonViGoc != null) {
            colDonViGoc.setCellValueFactory(cd -> {
                ChiTietHoaDon c = cd.getValue();
                String dv = "";
                if (c != null && c.getDvt() != null && c.getDvt().getTenDonViTinh() != null) {
                    dv = c.getDvt().getTenDonViTinh();
                } else {
                    dv = donViCoBan(c);
                }
                return new ReadOnlyObjectWrapper<>(dv);
            });
            alignRight(colDonViGoc);
        }
        if (colDonGiaGoc != null) {
            colDonGiaGoc.setCellValueFactory(cd ->
                    Bindings.createStringBinding(() -> vnd(cd.getValue().getDonGia()))
            );
            alignRight(colDonGiaGoc);
        }
        if (colGiamGiaGoc != null) {
            colGiamGiaGoc.setCellValueFactory(cd ->
                    new ReadOnlyObjectWrapper<>(vnd(cd.getValue().getGiamGia()))
            );
            alignRight(colGiamGiaGoc);
        }
        if (colThanhTienGoc != null) {
            colThanhTienGoc.setCellValueFactory(cd ->
                    Bindings.createStringBinding(() -> {
                        ChiTietHoaDon r = cd.getValue();
                        if (r == null) return "";
                        double tt = Math.max(0,
                                r.getSoLuong() * r.getDonGia() - r.getGiamGia());
                        return vnd(tt);
                    })
            );
            alignRight(colThanhTienGoc);
        }
        if (colTra != null) {
            colTra.setCellFactory(tc -> new TableCell<ChiTietHoaDon, Void>() {

                private final Button btn = new Button("↓");

                {
                    btn.getStyleClass().add("btn-doi");
                    btn.setTooltip(new Tooltip("Chuyển sang danh sách trả"));

                    btn.setOnAction(e -> {
                        ChiTietHoaDon goc =
                                getTableView().getItems().get(getIndex());

                        if (goc == null) return;

                        int slDaTra = ctpthDao.tongSoLuongDaTra(
                                lblMaHDGoc.getText(),
                                goc.getLoHang().getMaLH()
                        );

                        int slConTra = goc.getSoLuong() - slDaTra;

                        if (slConTra <= 0) {
                            hien(
                                    WARNING,
                                    "Không thể trả",
                                    "Sản phẩm này đã được trả hết."
                            );
                            return;
                        }

                        xuLyChuyenSangTra(goc);
                    });

                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setStyle("-fx-alignment: center;");
                }

                @Override
                protected void updateItem(Void it, boolean empty) {
                    super.updateItem(it, empty);

                    if (empty) {
                        setGraphic(null);
                        return;
                    }

                    ChiTietHoaDon goc =
                            getTableView().getItems().get(getIndex());

                    int slDaTra = ctpthDao.tongSoLuongDaTra(
                            lblMaHDGoc.getText(),
                            goc.getLoHang().getMaLH()
                    );

                    btn.setDisable(goc.getSoLuong() - slDaTra <= 0);

                    setGraphic(btn);
                    setText(null);
                }
            });
            colTra.setSortable(false);
            colTra.setReorderable(false);
        }

    }

    private void xuLyChuyenSangTra(ChiTietHoaDon cthdGoc) {

        if (cthdGoc == null || cthdGoc.getLoHang() == null) return;

        int slBan = cthdGoc.getSoLuong(); // SỐ BÁN GỐC

        int slDaTra = ctpthDao.tongSoLuongDaTra(
                cthdGoc.getHoaDon().getMaHD(),
                cthdGoc.getLoHang().getMaLH()
        );

        int slConTra = slBan - slDaTra;

        if (slConTra <= 0) {
            hien(
                    WARNING,
                    "Không thể trả",
                    "Sản phẩm này đã được trả hết."
            );
            return;
        }

        String key =
                cthdGoc.getHoaDon().getMaHD() + "_" +
                        cthdGoc.getLoHang().getMaLH();

        TraHangItem vm = traByKey.get(key);

        if (vm == null) {
            vm = new TraHangItem(cthdGoc, 1, "");
            dsTra.add(vm);
            traByKey.put(key, vm);
        } else {
            int next = Math.min(slConTra, vm.getSoLuongTra() + 1);
            vm.setSoLuongTra(next);
        }

        capNhatTienTra();
        tblChiTietTraHang.refresh();
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

    
    private void setupTblTraColumns() {
        if (colSTTTra != null) {
            colSTTTra.setCellFactory(tc -> new TableCell<>() {
                @Override protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : String.valueOf(getIndex() + 1));
                }
            });
            colSTTTra.setSortable(false);
            colSTTTra.setReorderable(false);
        }
        if (colTenSPTra != null) {
            colTenSPTra.setCellValueFactory(p -> javafx.beans.binding.Bindings.createStringBinding(
                    () -> tenSP(p.getValue().getGoc())));
        }
        if (colDonViTra != null) {
            colDonViTra.setCellValueFactory(p -> javafx.beans.binding.Bindings.createStringBinding(() -> {
                ChiTietHoaDon goc = p.getValue().getGoc();
                if (goc == null) return "";
                DonViTinh dvt = goc.getDvt();
                if (dvt != null && dvt.getTenDonViTinh() != null) {
                    return dvt.getTenDonViTinh();
                }
                return donViCoBan(goc);
            }));
            alignRight(colDonViTra);
        }
        if (colSLTra != null) {
            colSLTra.setCellValueFactory(p -> p.getValue().soLuongTraProperty());
            colSLTra.setCellFactory(tc -> new TableCell<>() {
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
                    if (idx < 0 || idx >= tblChiTietTraHang.getItems().size()) return;
                    TraHangItem vm = tblChiTietTraHang.getItems().get(idx);
                    if (vm == null) return;
                    int cur = vm.getSoLuongTra();
                    int max = vm.getGoc() == null ? Integer.MAX_VALUE : Math.max(0, vm.getGoc().getSoLuong());
                    int target = Math.max(1, Math.min(max, cur + delta));
                    if (target != cur) vm.setSoLuongTra(target);
                    capNhatTienTra();
                    tf.setText(String.valueOf(vm.getSoLuongTra()));
                }
                private void commitFromText() {
                    int idx = getIndex();
                    if (idx < 0 || idx >= tblChiTietTraHang.getItems().size()) return;
                    TraHangItem vm = tblChiTietTraHang.getItems().get(idx);
                    if (vm == null) return;
                    String s = tf.getText();
                    try {
                        int val = Integer.parseInt(s);
                        if (val <= 0) val = 1;
                        int max = vm.getGoc() == null ? Integer.MAX_VALUE : Math.max(0, vm.getGoc().getSoLuong());
                        if (val > max) val = max;
                        vm.setSoLuongTra(val);
                        capNhatTienTra();
                    } catch (NumberFormatException ignore) {
                        tf.setText(String.valueOf(vm.getSoLuongTra()));
                    }
                }
                @Override protected void updateItem(Number item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) { setGraphic(null); setText(null); }
                    else {
                        TraHangItem vm = getTableView().getItems().get(getIndex());
                        tf.setText(vm != null ? String.valueOf(vm.getSoLuongTra()) : "1");
                        setGraphic(box);
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                }
            });
        }
        if (colDonGiaTra != null) {
            colDonGiaTra.setCellValueFactory(cd ->
                    new ReadOnlyObjectWrapper<>(cd.getValue().getDonGiaUnit())
            );
            colDonGiaTra.setCellFactory(tc -> new TableCell<TraHangItem, Double>() {
                @Override protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        double val = item == null ? 0.0 : item.doubleValue();
                        setText(vnd(val)); // dùng hàm vnd(double) của controller (đã có)
                        setStyle("-fx-alignment: CENTER-RIGHT;");
                    }
                }
            });
        }
        if (colThanhTienTra != null) {
            colThanhTienTra.setCellValueFactory(cd ->
                    new ReadOnlyObjectWrapper<>(cd.getValue().getThanhTienTra())
            );
            colThanhTienTra.setCellFactory(tc -> new TableCell<TraHangItem, Double>() {
                @Override protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(vnd(item));
                    }
                    setStyle("-fx-alignment: CENTER-RIGHT;");
                }
            });
        }
        if (colLyDo != null) {
            colLyDo.setCellValueFactory(p -> p.getValue().lyDoProperty());
            colLyDo.setCellFactory(TextFieldTableCell.forTableColumn());
            colLyDo.setOnEditCommit(ev -> {
                TraHangItem vm = ev.getRowValue();
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
                        if (idx < 0 || idx >= tblChiTietTraHang.getItems().size()) return;

                        TraHangItem vm = tblChiTietTraHang.getItems().get(idx);
                        if (vm != null && vm.getGoc() != null && vm.getGoc().getLoHang() != null) {
                            String key = vm.getGoc().getHoaDon().getMaHD() + "_" +
                                    vm.getGoc().getLoHang().getMaLH() + "_" +
                                    (vm.getGoc().getDvt() != null ? vm.getGoc().getDvt().getMaDVT() : "null");
                            traByKey.remove(key);
                        }
                        dsTra.remove(vm);
                        tblChiTietTraHang.refresh();
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



    // ========== Nghiệp vụ ==========

    public void xuLyTimHDGoc() {
        String ma = txtTimHoaDon.getText().trim();

        if (ma.isBlank()) {
            hien(WARNING, "Thiếu thông tin", "Vui lòng nhập mã hóa đơn.");
            return;
        }


        try {
            HoaDon hd = hoaDonDao.selectById(ma);

            if (hd == null) {
                hien(WARNING, "Không tìm thấy", "Hóa đơn không tồn tại.");
                return;
            }

            // load line items first to compute discounts
            List<ChiTietHoaDon> lines = cthdDao.selectByMaHD(ma);
            if (lines == null || lines.isEmpty()) {
                hien(WARNING, "Không có sản phẩm", "Hóa đơn không có chi tiết.");
                return;
            }
            double perLineDiscount = 0;
            if (lines != null) {
                for (ChiTietHoaDon ct : lines) {
                    if (ct != null) perLineDiscount += ct.getGiamGia();
                }
            }

            double invoiceLevelDiscount = 0;
            try {
                String[] names = {"getGiamGia", "getGiamGiaTong", "getTongGiamGia", "getTienGiamGia", "getKhuyenMai"};
                for (String nm : names) {
                    try {
                        Method m = hd.getClass().getMethod(nm);
                        if (m != null) {
                            Object val = m.invoke(hd);
                            if (val instanceof Number) {
                                invoiceLevelDiscount = ((Number) val).doubleValue();
                                break;
                            } else if (val != null) {
                                invoiceLevelDiscount = Double.parseDouble(val.toString());
                                break;
                            }
                        }
                    } catch (NoSuchMethodException ignored) { }
                }
            } catch (Exception ignored) { }

            if (invoiceLevelDiscount + perLineDiscount > 0) {
                hien(WARNING,
                        "Hóa đơn có áp dụng giảm giá tổng!",
                        "Không thể thực hiện trả hàng với hóa đơn này.");
                return;
            }

            LocalDate ngayHD = hd.getNgayLap().toLocalDateTime().toLocalDate();
            if (ngayHD.isBefore(LocalDate.now().minusDays(7))) {
                hien(WARNING, "Quá hạn", "Hóa đơn đã quá 7 ngày, không thể trả hàng.");
                return;
            }

            lblMaHDGoc.setText(hd.getMaHD());


            if (hd.getMaKH() != null) {
                String maKH = hd.getMaKH().getMaKH();
                KhachHang kh = khDao.selectById(maKH);

                if (kh != null) {
                    lblTenKH.setText(kh.getTenKH());
                    lblSDT.setText(kh.getSdt());
                } else {
                    lblTenKH.setText("(không tìm thấy KH)");
                    lblSDT.setText("");
                }
            }

            dpNgayLapPhieu.setValue(LocalDate.now());
            dsTra.clear();
            traByKey.clear();
//            dsGoc.setAll(lines);
            dsGoc.clear();
            boolean conTraDuoc = false;

            for (ChiTietHoaDon ct : lines) {

                int slBan = ct.getSoLuong();
                int slDaTra = ctpthDao.tongSoLuongDaTra(
                        ma,
                        ct.getLoHang().getMaLH()
                );

                if (slBan - slDaTra > 0) {
                    conTraDuoc = true;
                    break;
                }
            }
            dsGoc.setAll(lines);

            if (!conTraDuoc) {
                hien(
                        INFORMATION,
                        "Không thể trả hàng",
                        "Các sản phẩm trong hóa đơn này đã được trả hết."
                );
                return;
            }

            dsGoc.setAll(lines);
            double tongTienGoc = 0;
            for (ChiTietHoaDon ct : lines) {
                double tt = ct.getSoLuong() * ct.getDonGia() - ct.getGiamGia();
                tongTienGoc += Math.max(0, tt);
            }
            lblTongTienGoc.setText(vnd(tongTienGoc));
            tblChiTietTraHang.refresh();

            tblSanPhamHoaDon.refresh();
        } catch (Exception e) {
            e.printStackTrace();
            hien(ERROR, "Lỗi", "Không thể tải hóa đơn.");
        }
    }



    public void xuLyTraHang() {
        if (lblMaHDGoc.getText() == null || lblMaHDGoc.getText().isBlank()) {
            hien(WARNING, "Thiếu thông tin", "Chưa chọn hóa đơn gốc.");
            return;
        }

        if (dsTra.isEmpty()) {
            hien(WARNING, "Chưa có sản phẩm", "Vui lòng chọn sản phẩm cần trả.");
            return;
        }

        try {
            // ===== 1. Lấy hóa đơn gốc =====
            String maHD = lblMaHDGoc.getText().trim();
            HoaDon hdGoc = hoaDonDao.selectById(maHD);
            if (hdGoc == null) {
                hien(ERROR, "Lỗi", "Không tìm thấy hóa đơn gốc.");
                return;
            }

            // ===== 2. Tạo Phiếu Trả =====
            PhieuTraHang phieuTra = new PhieuTraHang();
            phieuTra.setHoaDon(hdGoc);

            if (DangNhap_Ctrl.user == null) {
                hien(ERROR, "Lỗi đăng nhập", "Chưa xác định nhân viên đang đăng nhập.");
                return;
            }
            phieuTra.setNhanVien(DangNhap_Ctrl.user);

            if (hdGoc.getMaKH() != null) {
                phieuTra.setKhachHang(hdGoc.getMaKH());
            }

            phieuTra.setNgayLap(
                    java.sql.Timestamp.valueOf(dpNgayLapPhieu.getValue().atStartOfDay())
            );

            phieuTra.setGhiChu(txtGhiChu == null ? "" : txtGhiChu.getText());

            PhieuTraHang_Dao pthDao = new PhieuTraHang_Dao();
            String maPhieuTra = pthDao.insertAndReturnId(phieuTra);

            if (maPhieuTra == null) {
                hien(ERROR, "Lỗi", "Không thể tạo phiếu trả.");
                return;
            }
            phieuTra.setMaPT(maPhieuTra);
            PhieuTraHang phieuTraHangMoi = phieuTra;
            // ===== 3. Lưu chi tiết trả =====
            ChiTietPhieuTraHang_Dao ctpthDao = new ChiTietPhieuTraHang_Dao();
            Thuoc_SP_TheoLo_Dao loDao = new Thuoc_SP_TheoLo_Dao();
            for (TraHangItem item : dsTra) {

                ChiTietHoaDon cthdGoc = item.getGoc();
                if (cthdGoc == null) continue;

                int slTra = item.getSoLuongTra();
                if (slTra <= 0) continue;

                // ===== CHECK SỐ LƯỢNG CÒN TRẢ =====
                int slBan = cthdGoc.getSoLuong();

                int slDaTra = ctpthDao.tongSoLuongDaTra(
                        maHD,
                        cthdGoc.getLoHang().getMaLH()
                );

                int slConTra = slBan - slDaTra;

                if (slTra > slConTra) {
                    hien(
                            WARNING,
                            "Số lượng không hợp lệ",
                            "Sản phẩm " + cthdGoc.getLoHang().getThuoc().getTenThuoc()
                                    + " chỉ còn trả được " + slConTra
                    );
                    return;
                }

                // ===== LƯU CHI TIẾT PHIẾU TRẢ =====
                ChiTietPhieuTraHang ct = new ChiTietPhieuTraHang();
                ct.setPhieuTraHang(phieuTra);
                ct.setLoHang(cthdGoc.getLoHang());
                ct.setThuoc(cthdGoc.getLoHang().getThuoc());
                ct.setDvt(cthdGoc.getDvt());
                ct.setSoLuong(slTra);
                ct.setDonGia(cthdGoc.getDonGia());
                ct.setLyDoTra(item.getLyDo());

                ctpthDao.insert(ct);

                // ===== CỘNG LẠI TỒN KHO =====
                loDao.congSoLuong(cthdGoc.getLoHang().getMaLH(), slTra);
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText("Lập phiếu trả thành công");
            alert.setContentText("Bạn có muốn xem chi tiết phiếu trả không?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    ChiTietPhieuTraHang_Ctrl ctrl = new ChiTietPhieuTraHang_Ctrl();

                    Stage stage = new Stage();
                    stage.setTitle("Chi tiết phiếu trả " + phieuTraHangMoi.getMaPT());

                    ChiTietPhieuTraHang_GUI.showWithController(stage, ctrl);

                    PhieuTraHang_Dao phieuTraDao = new PhieuTraHang_Dao();
                    PhieuTraHang phieuTraDayDu = phieuTraDao.selectById(maPhieuTra);

                    ctrl.setPhieuTraHang(phieuTraDayDu);

                    stage.show();

                } catch (Exception e) {
                    hien(ERROR, "Lỗi", "Không thể mở chi tiết phiếu trả:\n" + e.getMessage());
                    e.printStackTrace();
                }
            }
            xuLyHuy();

        } catch (Exception e) {
            e.printStackTrace();
            hien(ERROR, "Lỗi", "Không thể lưu phiếu trả.");
        }
    }
    private void capNhatTienTra() {
        double tongTienTraHang = 0;

        for (TraHangItem item : dsTra) {
            ChiTietHoaDon goc = item.getGoc();
            if (goc == null) continue;

            double tt = item.getSoLuongTra() * goc.getDonGia() - goc.getGiamGia();
            tongTienTraHang += Math.max(0, tt);
        }

        lblTongTienTraLai.setText(vnd(tongTienTraHang));

        double soTienTra = tongTienTraHang;
        lblSoTienTraLai.setText(vnd(soTienTra));
    }
    private double parseVnd(String text) {
        if (text == null) return 0;
        return Double.parseDouble(
                text.replace("VNĐ", "")
                        .replace(".", "")
                        .replace(",", "")
                        .trim()
        );
    }

    private void thongBaoTuyChinh(Alert.AlertType type, String header, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(header);
        alert.setHeaderText(header);
        alert.setContentText(message);
        DialogPane pane = alert.getDialogPane();
        pane.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThongBaoAlert.css"
        ).toExternalForm());
        Stage stage = (Stage) pane.getScene().getWindow();
        switch (type) {
            case WARNING:
                pane.getStyleClass().add("warning-alert");
                stage.getIcons().add(new Image(
                        getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/iconcanhbao.jpg")));
                break;
            case INFORMATION:
                pane.getStyleClass().add("info-alert");
                break;
            case ERROR:
                pane.getStyleClass().add("error-alert");
                break;
        }

        stage.setWidth(550);
        stage.setHeight(260);
        alert.showAndWait();
    }

    public void xuLyHuy() {
        dsGoc.clear();
        dsTra.clear();
        lblMaHDGoc.setText("");
        lblTenKH.setText("");
        lblSDT.setText("");
        dpNgayLapPhieu.setValue(LocalDate.now());
        txtGhiChu.setText("");
        txtTimHoaDon.clear();
        tblSanPhamHoaDon.getItems().clear();
        tblChiTietTraHang.getItems().clear();
        lblSoTienTraLai.setText("");
        lblTongTienGoc.setText("");
        lblTongTienTraLai.setText("");
    }
}