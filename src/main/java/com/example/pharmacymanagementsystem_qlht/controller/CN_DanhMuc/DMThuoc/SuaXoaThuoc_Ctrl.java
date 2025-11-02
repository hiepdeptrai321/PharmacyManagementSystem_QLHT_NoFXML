package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc;

import com.example.pharmacymanagementsystem_qlht.dao.*;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoatChat;
import com.example.pharmacymanagementsystem_qlht.model.HoatChat;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class SuaXoaThuoc_Ctrl {
    public TextField txtTenThuoc;
    public ComboBox<String> cbxLoaiHang;
    public ComboBox<String> cbxViTri;
    public TextField txtHamLuong;
    public TextField txtHangSanXuat;
    public ComboBox<String> cbxNhomDuocLy;
    public TextField txtNuocSanXuat;
    public TextField txtQuyCachDongGoi;
    public TextField txtSDK_GPNK;

    public TableView<ChiTietHoatChat> tblHoatChat;
    public TableColumn<ChiTietHoatChat, String> colMaHoatChat;
    public TableColumn<ChiTietHoatChat, String> colTenHoatChat;
    public TableColumn<ChiTietHoatChat, Float>  colHamLuong;
    public TableColumn<ChiTietHoatChat, String> colXoa;

    public TextField txtDuongDung;
    public TextField txtDonViHamLuong;

    public ListView<HoatChat> listViewHoatChat;
    public TextField txtTimKiemHoatChat;

    public TextField txtMaThuoc;
    public ImageView imgThuoc_SanPham;

    private ObservableList<HoatChat> allHoatChat;
    private final List<ChiTietHoatChat> listChiTietHoatChat = new ArrayList<>();
    private Thuoc_SanPham thuocTempDeXemSoLuongTon;
    private DanhMucThuoc_Ctrl danhMucThuoc_Ctrl;

    public Button btnXoa;
    public Button btnHuy;
    public Button btnLuu;
    public Button btnChonAnh;

    // ===== Khởi tạo hành vi KHÔNG phụ thuộc dữ liệu thuốc =====
    public void initialize() {
        if (listViewHoatChat != null) listViewHoatChat.setVisible(false);

        // Nạp danh mục hoạt chất 1 lần
        listView();

        // Filter theo ô tìm kiếm
        if (txtTimKiemHoatChat != null) {
            txtTimKiemHoatChat.textProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null && !newVal.trim().isEmpty()) {
                    listViewHoatChat.setVisible(true);
                    locDanhSachHoatChat(newVal);
                } else {
                    listViewHoatChat.setVisible(false);
                }
            });
        }

        // Chọn hoạt chất để thêm vào bảng
        if (listViewHoatChat != null) {
            listViewHoatChat.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal == null) return;

                // Chỉ cho thêm khi đã có thuốc (load đã chạy)
                if (thuocTempDeXemSoLuongTon == null || tblHoatChat == null) {
                    Platform.runLater(() -> {
                        listViewHoatChat.getSelectionModel().clearSelection();
                        listViewHoatChat.setVisible(false);
                    });
                    return;
                }

                HoatChat hoatChat = newVal;
                txtTimKiemHoatChat.clear();
                listViewHoatChat.setVisible(false);

                boolean notExists = tblHoatChat.getItems().stream()
                        .noneMatch(item -> item.getHoatChat().getMaHoatChat().equals(hoatChat.getMaHoatChat()));

                if (notExists) {
                    ChiTietHoatChat chtc = new ChiTietHoatChat();
                    chtc.setHoatChat(hoatChat);
                    chtc.setThuoc(thuocTempDeXemSoLuongTon);

                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Nhập hàm lượng");
                    dialog.setHeaderText("Vui lòng nhập hàm lượng cho hoạt chất: " + hoatChat.getTenHoatChat());
                    dialog.setContentText("Hàm lượng:");
                    dialog.showAndWait().ifPresent(hamLuong -> {
                        try {
                            float val = Float.parseFloat(hamLuong);
                            chtc.setHamLuong(val);
                            listChiTietHoatChat.add(chtc);
                            tblHoatChat.getItems().add(chtc);
                        } catch (NumberFormatException ex) {
                            Alert a = new Alert(Alert.AlertType.ERROR, "Hàm lượng không hợp lệ! Vui lòng nhập số.");
                            a.setHeaderText(null);
                            a.showAndWait();
                        }
                    });

                    Platform.runLater(() -> {
                        listViewHoatChat.getSelectionModel().clearSelection();
                        listViewHoatChat.refresh();
                    });
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Hoạt chất đã tồn tại trong danh sách!");
                    alert.showAndWait();

                    Platform.runLater(() -> {
                        listViewHoatChat.getSelectionModel().clearSelection();
                        listViewHoatChat.refresh();
                    });
                }
            });
        }
        btnLuu.setOnAction(event -> btnCapNhat());
        btnHuy.setOnAction(event -> btnHuy());
        btnXoa.setOnAction(event -> btnXoa());
        btnChonAnh.setOnAction(event -> chonFile());
    }

    // ===== Nạp dữ liệu PHỤ THUỘC thuốc được chọn =====
    public void load(Thuoc_SanPham thuoc) {
        // Chống gọi load() trước khi inject UI
        if (tblHoatChat == null || colMaHoatChat == null || colTenHoatChat == null
                || colHamLuong == null || colXoa == null) {
            throw new IllegalStateException("Hãy gọi gui.showWithController(stage, ctrl) để inject UI trước, rồi mới gọi ctrl.load(thuoc).");
        }

        thuocTempDeXemSoLuongTon = thuoc;

        tblHoatChat.setEditable(true);
        colHamLuong.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        colHamLuong.setOnEditCommit(event -> {
            ChiTietHoatChat hoatChatMoi = event.getRowValue();
            hoatChatMoi.setHamLuong(event.getNewValue());

            for (ChiTietHoatChat chtc : listChiTietHoatChat) {
                if (chtc.getThuoc() != null && hoatChatMoi.getThuoc() != null
                        && Objects.equals(chtc.getThuoc().getMaThuoc(), hoatChatMoi.getThuoc().getMaThuoc())
                        && Objects.equals(chtc.getHoatChat().getMaHoatChat(), hoatChatMoi.getHoatChat().getMaHoatChat())) {
                    chtc.setHamLuong(hoatChatMoi.getHamLuong());
                    chtc.setThuoc(thuoc);
                    break;
                }
            }
        });

        // Nguồn dữ liệu cho ComboBox
        cbxLoaiHang.getItems().setAll(new LoaiHang_Dao().getAllTenLH());
        cbxLoaiHang.getItems().add(0, "Chọn loại hàng");

        cbxViTri.getItems().setAll(new KeHang_Dao().getAllTenKe());
        cbxViTri.getItems().add(0, "Chọn vị trí");

        cbxNhomDuocLy.getItems().setAll(new NhomDuocLy_Dao().getAllTenNhomDuocLy());
        cbxNhomDuocLy.getItems().add(0, "Chọn nhóm dược lý");

        loadDuLieuThuoc(thuoc);
    }

    public void loadDuLieuThuoc(Thuoc_SanPham thuoc) {
        txtMaThuoc.setText(thuoc.getMaThuoc());
        txtTenThuoc.setText(thuoc.getTenThuoc());
        cbxLoaiHang.setValue(thuoc.getLoaiHang() != null ? thuoc.getLoaiHang().getTenLoaiHang() : cbxLoaiHang.getItems().get(0));
        cbxViTri.setValue(thuoc.getVitri() != null ? thuoc.getVitri().getTenKe() : cbxViTri.getItems().get(0));
        txtHamLuong.setText(String.valueOf(thuoc.getHamLuong()));
        txtHangSanXuat.setText(thuoc.getHangSX());
        txtDonViHamLuong.setText(thuoc.getDonViHamLuong());
        txtDuongDung.setText(thuoc.getDuongDung());

        if (thuoc.getNhomDuocLy() != null) {
            cbxNhomDuocLy.setValue(thuoc.getNhomDuocLy().getTenNDL());
        } else {
            cbxNhomDuocLy.getSelectionModel().selectFirst();
        }

        txtNuocSanXuat.setText(thuoc.getNuocSX());
        txtQuyCachDongGoi.setText(thuoc.getQuyCachDongGoi());
        txtSDK_GPNK.setText(thuoc.getSDK_GPNK());

        try {
            if (thuoc.getHinhAnh() == null) {
                imgThuoc_SanPham.setImage(new Image(
                        getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/noimage.jpg").toExternalForm()
                ));
            } else {
                imgThuoc_SanPham.setImage(new Image(new ByteArrayInputStream(thuoc.getHinhAnh())));
            }
        } catch (Exception e) {
            imgThuoc_SanPham.setImage(new Image(
                    getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/noimage.jpg").toExternalForm()
            ));
        }

        // Đổ bảng hoạt chất
        List<ChiTietHoatChat> listHoatChat = new ChiTietHoatChat_Dao().selectByMaThuoc(thuoc.getMaThuoc());
        ObservableList<ChiTietHoatChat> data = FXCollections.observableArrayList(listHoatChat);

        colMaHoatChat.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getHoatChat().getMaHoatChat()));
        colTenHoatChat.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getHoatChat().getTenHoatChat()));
        colHamLuong.setCellValueFactory(new PropertyValueFactory<>("hamLuong"));

        colXoa.setCellFactory(celldata -> new TableCell<>() {
            private final Button btn = new Button("Xóa");
            {
                btn.setOnAction(event -> {
                    ChiTietHoatChat chtc = getTableView().getItems().get(getIndex());
                    new ChiTietHoatChat_Dao().deleteById(
                            chtc.getThuoc().getMaThuoc(),
                            chtc.getHoatChat().getMaHoatChat()
                    );
                    getTableView().getItems().remove(chtc);
                    listChiTietHoatChat.removeIf(item ->
                            Objects.equals(item.getThuoc().getMaThuoc(), chtc.getThuoc().getMaThuoc()) &&
                                    Objects.equals(item.getHoatChat().getMaHoatChat(), chtc.getHoatChat().getMaHoatChat())
                    );
                });
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        tblHoatChat.setItems(data);
    }

    // Hiển thị danh sách hoạt chất
    public void listView() {
        List<HoatChat> listHoatChat = new HoatChat_Dao().selectAll();
        allHoatChat = FXCollections.observableArrayList(listHoatChat);
        listViewHoatChat.setItems(allHoatChat);
        listViewHoatChat.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(HoatChat item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getMaHoatChat() + " - " + item.getTenHoatChat());
            }
        });
    }

    // Lọc danh sách hoạt chất
    private void locDanhSachHoatChat(String keywordRaw) {
        if (keywordRaw == null || keywordRaw.isEmpty()) {
            Platform.runLater(() -> listViewHoatChat.setItems(allHoatChat));
            return;
        }
        String keyword = keywordRaw.toLowerCase();
        ObservableList<HoatChat> filtered = FXCollections.observableArrayList();
        for (HoatChat hoatChat : allHoatChat) {
            if (hoatChat.getMaHoatChat().toLowerCase().contains(keyword)
                    || hoatChat.getTenHoatChat().toLowerCase().contains(keyword)) {
                filtered.add(hoatChat);
            }
        }
        Platform.runLater(() -> listViewHoatChat.setItems(filtered.isEmpty()
                ? FXCollections.observableArrayList()
                : filtered));
    }

    // ================== Hành động nút ==================
    public void btnCapNhat() {
        if (btnLuu == null || btnLuu.getScene() == null) return;

        AnchorPane root = (AnchorPane) btnLuu.getScene().getRoot();

        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.4);");
        ProgressIndicator progress = new ProgressIndicator();
        overlay.getChildren().add(progress);

        AnchorPane.setTopAnchor(overlay, 0.0);
        AnchorPane.setRightAnchor(overlay, 0.0);
        AnchorPane.setBottomAnchor(overlay, 0.0);
        AnchorPane.setLeftAnchor(overlay, 0.0);

        root.getChildren().add(overlay);

        if (!kiemTraHopLe()) {
            root.getChildren().remove(overlay);
            return;
        }

        new Thread(() -> {
            try {
                Thuoc_SanPham thuoc = new Thuoc_SanPham();
                thuoc.setMaThuoc(txtMaThuoc.getText());
                thuoc.setTenThuoc(txtTenThuoc.getText().trim());

                if (cbxLoaiHang.getSelectionModel().getSelectedIndex() == 0) {
                    thuoc.setLoaiHang(null);
                } else {
                    thuoc.setLoaiHang(new LoaiHang_Dao()
                            .selectByTenLH(cbxLoaiHang.getSelectionModel().getSelectedItem()));
                }
                if (cbxViTri.getSelectionModel().getSelectedIndex() == 0) {
                    thuoc.setVitri(null);
                } else {
                    thuoc.setVitri(new KeHang_Dao()
                            .selectByTenKe(cbxViTri.getSelectionModel().getSelectedItem()));
                }

                thuoc.setHamLuong(Float.parseFloat(txtHamLuong.getText().trim()));
                thuoc.setHangSX(txtHangSanXuat.getText().trim());
                thuoc.setDonViHamLuong(txtDonViHamLuong.getText().trim());
                thuoc.setDuongDung(txtDuongDung.getText().trim());

                if (cbxNhomDuocLy.getSelectionModel().getSelectedIndex() == 0) {
                    thuoc.setNhomDuocLy(null);
                } else {
                    thuoc.setNhomDuocLy(new NhomDuocLy_Dao()
                            .selectByTenNhomDuocLy(cbxNhomDuocLy.getSelectionModel().getSelectedItem()));
                }

                thuoc.setNuocSX(txtNuocSanXuat.getText().trim());
                thuoc.setQuyCachDongGoi(txtQuyCachDongGoi.getText().trim());
                thuoc.setSDK_GPNK(txtSDK_GPNK.getText().trim());

                // Ảnh
                Image image = imgThuoc_SanPham.getImage();
                if (image != null) {
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        ImageIO.write(bufferedImage, "png", baos);
                        baos.flush();
                        thuoc.setHinhAnh(baos.toByteArray());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Thuoc_SanPham_Dao thuoc_dao = new Thuoc_SanPham_Dao();
                thuoc_dao.update(thuoc);

                if (!listChiTietHoatChat.isEmpty()) {
                    // ====== Đồng bộ ChiTietHoatChat theo state hiện tại của bảng ======
                    ChiTietHoatChat_Dao chtc_dao = new ChiTietHoatChat_Dao();

//                  1) Lấy những record đã có trong DB cho MÃ THUỐC này
                    List<ChiTietHoatChat> existingList = chtc_dao.selectByMaThuoc(thuoc.getMaThuoc());
                    Map<String, Float> existingMap = new HashMap<>();
                    for (ChiTietHoatChat c : existingList) {
                        existingMap.put(c.getHoatChat().getMaHoatChat(), c.getHamLuong());
                    }

//                  2) State hiện tại trên UI (bảng là nguồn sự thật)
                    List<ChiTietHoatChat> current = new ArrayList<>(tblHoatChat.getItems());

//                  3) Insert/Update những gì đang có trên UI
                    for (ChiTietHoatChat c : current) {
                        // đảm bảo reference Thuoc không bị null khi insert/update
                        c.setThuoc(thuoc);

                        String key = c.getHoatChat().getMaHoatChat();
                        if (!existingMap.containsKey(key)) {
                            // chưa có trong DB -> INSERT
                            chtc_dao.insert(c);
                        } else if (!Objects.equals(existingMap.get(key), c.getHamLuong())) {
                            // có rồi nhưng hàm lượng đổi -> UPDATE
                            chtc_dao.update(c);
                        }
                    }

//                  4) Xóa những record trong DB nhưng đã bị xóa khỏi UI
                    Set<String> currentKeys = current.stream()
                            .map(ci -> ci.getHoatChat().getMaHoatChat())
                            .collect(java.util.stream.Collectors.toSet());

                    for (ChiTietHoatChat old : existingList) {
                        String key = old.getHoatChat().getMaHoatChat();
                        if (!currentKeys.contains(key)) {
                            chtc_dao.deleteById(thuoc.getMaThuoc(), key);
                        }
                    }
                }

                Platform.runLater(() -> {
                    root.getChildren().remove(overlay);
                    if (danhMucThuoc_Ctrl != null) danhMucThuoc_Ctrl.refestTable();
                    dong();
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> root.getChildren().remove(overlay));
            }
        }).start();
    }

    private void dong() {
        Stage stage = (Stage) txtMaThuoc.getScene().getWindow();
        stage.close();
    }

    public void btnHuy() {
        dong();
    }

    public void setParent(DanhMucThuoc_Ctrl parent) {
        danhMucThuoc_Ctrl = parent;
    }

    public void btnXoa() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc muốn xoá thuốc này?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                Thuoc_SanPham_Dao thuoc_dao = new Thuoc_SanPham_Dao();
                Thuoc_SP_TheoLo_Dao thuocSpTheoLoDao = new Thuoc_SP_TheoLo_Dao();
                if (thuocSpTheoLoDao.selectSoLuongTonByMaThuoc(thuocTempDeXemSoLuongTon.getMaThuoc()) > 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Thuốc " + thuocTempDeXemSoLuongTon.getTenThuoc() + " hiện đang có tồn kho, bạn có muốn xóa không");
                    alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.YES) {
                            thuoc_dao.xoaThuoc_SanPham(thuocTempDeXemSoLuongTon.getMaThuoc());
                            if (danhMucThuoc_Ctrl != null) danhMucThuoc_Ctrl.refestTable();
                            dong();
                        }
                    });
                } else {
                    thuoc_dao.xoaThuoc_SanPham(thuocTempDeXemSoLuongTon.getMaThuoc());
                    if (danhMucThuoc_Ctrl != null) danhMucThuoc_Ctrl.refestTable();
                    dong();
                }
            }
        });
    }

    public void chonFile() {
        Stage stage = (Stage) txtMaThuoc.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh thuốc");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        java.io.File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imgThuoc_SanPham.setImage(image);
        }
    }

    public boolean kiemTraHopLe() {
        if (txtTenThuoc.getText().isEmpty()) {
            showErr("Tên thuốc không được để trống!"); return false;
        } else if (txtHamLuong.getText().isEmpty()) {
            showErr("Hàm lượng không được để trống!"); return false;
        } else if (!txtHamLuong.getText().matches("\\d+(\\.\\d+)?")) {
            showErr("Hàm lượng không hợp lệ! Vui lòng nhập số."); return false;
        } else if (txtDonViHamLuong.getText().isEmpty()) {
            showErr("Đơn vị hàm lượng không được để trống!"); return false;
        } else if (txtDuongDung.getText().isEmpty()) {
            showErr("Đường dùng không được để trống!"); return false;
        } else if (txtSDK_GPNK.getText().isEmpty()) {
            showErr("SĐK/GPNK không được để trống!"); return false;
        }
        return true;
    }

    private void showErr(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
