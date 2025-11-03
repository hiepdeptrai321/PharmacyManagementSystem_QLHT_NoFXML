package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhachHang;

import com.example.pharmacymanagementsystem_qlht.dao.KhachHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML; // (Có thể giữ lại)
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThemKhachHang_Ctrl extends Application implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(ThemKhachHang_Ctrl.class.getName());

    // --- ĐÃ CHUYỂN SANG PUBLIC ---
    public TextField txtTenKH;
    public DatePicker dpNgaySinh;
    public TextField txtSDT;
    public TextField txtEmail;
    public TextField txtDiaChi;
    public ComboBox<String> cbGioiTinh;
    public ComboBox<String> cbTrangThai; // Vẫn là null vì không có trong FXML
    public Label lblMessage;
    public Button btnThem;
    public Button btnHuy;

    // Per-field error labels
    public Label errTenKH;
    public Label errNgaySinh;
    public Label errSDT;
    public Label errEmail;
    public Label errDiaChi;
    public Label errGioiTinh;


    private final KhachHang_Dao khDao = new KhachHang_Dao();

    private Consumer<KhachHang> onSaved;

    public void setOnSaved(Consumer<KhachHang> onSaved) {
        this.onSaved = onSaved;
    }

    // --- LOGIC GIỮ NGUYÊN ---
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate gender and status combos
        if (cbGioiTinh != null) {
            cbGioiTinh.getItems().clear();
            cbGioiTinh.getItems().addAll("Nam", "Nữ");
            // cbGioiTinh.setValue("Nam"); // Bỏ setValue để prompt text hiển thị
        }
        if (cbTrangThai != null) { // Code này vẫn an toàn dù cbTrangThai là null
            cbTrangThai.getItems().clear();
            cbTrangThai.getItems().addAll("Hoạt động", "Không hoạt động");
            cbTrangThai.setValue("Hoạt động");
        }

        // Clear all messages
        clearFieldErrors();
        clearMessage();

        // Clear only the corresponding field error when user edits
        if (txtTenKH != null) txtTenKH.textProperty().addListener((obs, o, n) -> { if (errTenKH != null) { errTenKH.setText(""); errTenKH.setVisible(false); errTenKH.setManaged(false); } });
        if (txtSDT != null) txtSDT.textProperty().addListener((obs, o, n) -> { if (errSDT != null) { errSDT.setText(""); errSDT.setVisible(false); errSDT.setManaged(false); } });
        if (txtEmail != null) txtEmail.textProperty().addListener((obs, o, n) -> { if (errEmail != null) { errEmail.setText(""); errEmail.setVisible(false); errEmail.setManaged(false); } });
        if (txtDiaChi != null) txtDiaChi.textProperty().addListener((obs, o, n) -> { if (errDiaChi != null) { errDiaChi.setText(""); errDiaChi.setVisible(false); errDiaChi.setManaged(false); } });
        if (dpNgaySinh != null) dpNgaySinh.valueProperty().addListener((obs, o, n) -> { if (errNgaySinh != null) { errNgaySinh.setText(""); errNgaySinh.setVisible(false); errNgaySinh.setManaged(false); } });
        if (cbGioiTinh != null) cbGioiTinh.valueProperty().addListener((obs, o, n) -> { if (errGioiTinh != null) { errGioiTinh.setText(""); errGioiTinh.setVisible(false); errGioiTinh.setManaged(false); } });

        if (btnThem != null) btnThem.setOnAction(this::handleSave);
        if (btnHuy != null) btnHuy.setOnAction(this::handleCancel);
    }

    private void clearMessage() {
        if (lblMessage != null) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("");
        }
    }

    private void clearFieldErrors() {
        if (errTenKH != null) { errTenKH.setText(""); errTenKH.setVisible(false); errTenKH.setManaged(false); }
        if (errNgaySinh != null) { errNgaySinh.setText(""); errNgaySinh.setVisible(false); errNgaySinh.setManaged(false); }
        if (errSDT != null) { errSDT.setText(""); errSDT.setVisible(false); errSDT.setManaged(false); }
        if (errEmail != null) { errEmail.setText(""); errEmail.setVisible(false); errEmail.setManaged(false); }
        if (errDiaChi != null) { errDiaChi.setText(""); errDiaChi.setVisible(false); errDiaChi.setManaged(false); }
        if (errGioiTinh != null) { errGioiTinh.setText(""); errGioiTinh.setVisible(false); errGioiTinh.setManaged(false); }
    }

    private void setError(Label lbl, String msg) {
        if (lbl != null) {
            lbl.setStyle("-fx-text-fill: red;");
            lbl.setText(msg);
            lbl.setVisible(true);
            lbl.setManaged(true);
        }
    }

    @FXML
    public void handleSave(ActionEvent event) {
        try {
            // Clear previous messages
            clearMessage();
            clearFieldErrors();

            // Gather inputs
            String ten = txtTenKH == null ? null : txtTenKH.getText().trim();
            String sdt = txtSDT == null ? null : txtSDT.getText().trim();
            String email = txtEmail == null ? null : txtEmail.getText().trim();
            String diaChi = txtDiaChi == null ? null : txtDiaChi.getText().trim();
            LocalDate ns = dpNgaySinh == null ? null : dpNgaySinh.getValue();
            String gioiTinh = cbGioiTinh == null ? null : cbGioiTinh.getValue();
            String trangThai = cbTrangThai == null ? "Hoạt động" : cbTrangThai.getValue();

            boolean valid = true;

            // Validate name and gender
            if (ten == null || ten.isEmpty()) {
                setError(errTenKH, "Tên khách hàng không được để trống.");
                valid = false;
            }
            if (gioiTinh == null || gioiTinh.trim().isEmpty()) {
                setError(errGioiTinh, "Vui lòng chọn giới tính phù hợp.");
                valid = false;
            }

            // Validate birthdate
            String nsText = (dpNgaySinh != null && dpNgaySinh.getEditor() != null)
                    ? dpNgaySinh.getEditor().getText().trim()
                    : "";
            if (ns == null) {
                if (nsText.isEmpty()) {
                    setError(errNgaySinh, "Ngày sinh không được để trống.");
                } else {
                    setError(errNgaySinh, "Ngày sinh không hợp lệ.");
                }
                valid = false;
            } else {
                LocalDate today = LocalDate.now();
                if (ns.isAfter(today)) {
                    setError(errNgaySinh, "Ngày sinh không hợp lệ (lớn hơn ngày hiện tại).");
                    valid = false;
                } else {
                    int years = Period.between(ns, today).getYears();
                    if (years < 0 || years > 150) {
                        setError(errNgaySinh, "Ngày sinh không hợp lệ.");
                        valid = false;
                    }
                }
            }

            // Validate phone
            if (sdt == null || sdt.isEmpty()) {
                setError(errSDT, "Số điện thoại không được để trống.");
                valid = false;
            } else {
                // Sửa logic validate SĐT theo FXML `SuaXoa` (10 số)
                if (!sdt.matches("\\d{10}")) {
                    setError(errSDT, "Số điện thoại không hợp lệ (10 số).");
                    valid = false;
                }
            }

            // Validate email
            if (email == null || email.isEmpty()) {
                setError(errEmail, "Email không được để trống.");
                valid = false;
            } else if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                setError(errEmail, "Email không hợp lệ.");
                valid = false;
            }

            // Validate address
            if (diaChi == null || diaChi.isEmpty()) {
                setError(errDiaChi, "Địa chỉ không được để trống.");
                valid = false;
            }

            // If any invalid, show a summary message and stop
            if (!valid) {
                if (lblMessage != null) {
                    lblMessage.setStyle("-fx-text-fill: red;");
                    lblMessage.setText(""); // Lỗi hiển thị bên cạnh trường
                }
                return;
            }

            // Prepare model
            KhachHang kh = new KhachHang();
            kh.setMaKH(khDao.generateNewMaKH());
            kh.setTenKH(ten);
            kh.setSdt(sdt);
            kh.setEmail(email);
            kh.setDiaChi(diaChi);
            if (ns != null) kh.setNgaySinh(ns);
            if (gioiTinh != null) {
                kh.setGioiTinh(gioiTinh.equals("Nam")); // "Nam" → true, "Nữ" → false
            }
            kh.setTrangThai(true); // Mặc định là true khi thêm mới
            boolean ok = khDao.insert(kh);
            if (!ok) {
                showError("Thêm khách hàng thất bại (lỗi cơ sở dữ liệu).");
                return;
            }

            // Success
            if (onSaved != null) onSaved.accept(kh);
            if (lblMessage != null) {
                lblMessage.setStyle("-fx-text-fill: green;");
                lblMessage.setText("Thêm khách hàng thành công.");
            }
            Stage st = (Stage) (btnThem != null ? btnThem.getScene().getWindow() : null);
            if (st != null) st.close();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi thêm khách hàng", ex);
            showError("Đã có lỗi xảy ra: " + ex.getMessage());
        }
    }



    @FXML
    public void handleCancel(ActionEvent event) {
        Stage st = (Stage) (btnHuy != null ? btnHuy.getScene().getWindow() : null);
        if (st != null) st.close();
    }

    private void showError(String msg) {
        if (lblMessage != null) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setWrapText(true);
            lblMessage.setText(msg);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        // --- ĐÃ THAY THẾ FXML LOADER ---
        new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKhachHang.ThemKhachHang_GUI()
                .showWithController(stage, this);
        stage.setTitle("Thêm Khách Hàng");
        stage.show();
    }
}