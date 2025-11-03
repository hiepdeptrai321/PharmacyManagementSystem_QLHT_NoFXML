package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhachHang;

import com.example.pharmacymanagementsystem_qlht.dao.KhachHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML; // (Có thể giữ lại)
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ChiTietKhachHang_Ctrl extends Application {

    // --- ĐÃ CHUYỂN SANG PUBLIC ---
    public Button btnLuu;
    public Button btnHuy;
    public Button btnXoa;
    public TextField txtDiaChi;
    public TextField txtEmail;
    public DatePicker txtNgaySinh;
    public TextField txtSDT;
    public TextField txtTenKH;
    public ComboBox<String> cboGioiTinh;
    public Label errTenKH, errDiaChi, errEmail, errSDT;

    private final KhachHang_Dao khachHangDao = new KhachHang_Dao();
    private KhachHang khachHang; // Dữ liệu được truyền vào

    // Main không cần thiết nếu không chạy file này độc lập
    // public static void main(String[] args) {
    //     launch(args);
    // }

    @Override
    public void start(Stage stage) throws Exception {
        // --- ĐÃ THAY THẾ FXML LOADER ---
        // GỌI GUI TƯƠNG ỨNG (SuaXoaKhachHang_GUI)
        new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKhachHang.SuaXoaKhachHang_GUI()
                .showWithController(stage, this);

        stage.setTitle("Chi tiết khách hàng"); // Giữ title từ FXML
        // CSS được load trong file GUI
        stage.show();
    }

    // --- ĐÃ CẬP NHẬT LOGIC `initialize` VÀ `hienThiThongTin` ---

    // 1. Gán sự kiện VÀ điền dữ liệu
    public void initialize() {
        cboGioiTinh.setItems(FXCollections.observableArrayList("Nam", "Nữ"));
        btnHuy.setOnAction(e -> HuyClick());
        btnXoa.setOnAction(e -> XoaClick());
        btnLuu.setOnAction(e -> LuuClick());

        // Điền dữ liệu vào form (được gọi sau khi hienThiThongTin đã lưu khachHang)
        if (khachHang != null) {
            txtTenKH.setText(khachHang.getTenKH());
            txtDiaChi.setText(khachHang.getDiaChi() != null ? khachHang.getDiaChi() : "");
            txtEmail.setText(khachHang.getEmail() != null ? khachHang.getEmail() : "");
            txtSDT.setText(khachHang.getSdt() != null ? khachHang.getSdt() : "");
            txtNgaySinh.setValue(khachHang.getNgaySinh());
            cboGioiTinh.setValue(Boolean.TRUE.equals(khachHang.getGioiTinh()) ? "Nam" : "Nữ");
        }
    }

    // 2. Chỉ lưu dữ liệu
    public void hienThiThongTin(KhachHang kh) {
        if (kh != null) {
            this.khachHang = kh;
        }
    }

    // --- LOGIC CÒN LẠI GIỮ NGUYÊN ---

    private boolean validateFields() {
        boolean isValid = true;

        // Reset lỗi
        errTenKH.setText("");
        errSDT.setText("");
        errEmail.setText("");
        errDiaChi.setText("");

        // Tên KH
        if (txtTenKH.getText().trim().isEmpty()) {
            errTenKH.setText("Tên khách hàng không được để trống.");
            isValid = false;
        }

        // Số điện thoại
        String sdt = txtSDT.getText().trim();
        if (sdt.isEmpty()) {
            errSDT.setText("Số điện thoại không được để trống.");
            isValid = false;
        } else if (!sdt.matches("\\d{10}")) {
            errSDT.setText("Số điện thoại không hợp lệ (10số).");
            isValid = false;
        }

        // Email
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            errEmail.setText("Email không được để trống.");
            isValid = false;
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errEmail.setText("Email không hợp lệ.");
            isValid = false;
        }

        // Địa chỉ
        if (txtDiaChi.getText().trim().isEmpty()) {
            errDiaChi.setText("Địa chỉ không được để trống.");
            isValid = false;
        }

        return isValid;
    }


    @FXML
    private void LuuClick() {
        try {
            // Kiểm tra dữ liệu trước khi lưu
            if (!validateFields()) {
                return;
            }

            if (khachHang == null) {
                // (Mặc dù trong luồng chi tiết thì KH không bao giờ null,
                // nhưng đây là logic phòng vệ tốt)
                khachHang = new KhachHang();
            }

            khachHang.setTenKH(txtTenKH.getText().trim());
            khachHang.setDiaChi(txtDiaChi.getText().trim());
            khachHang.setEmail(txtEmail.getText().trim());
            khachHang.setSdt(txtSDT.getText().trim());
            khachHang.setNgaySinh(txtNgaySinh.getValue() != null ? txtNgaySinh.getValue() : LocalDate.now());
            khachHang.setGioiTinh("Nam".equals(cboGioiTinh.getValue()));
            khachHang.setTrangThai(true); // Logic của bạn là luôn set true khi Lưu

            boolean success;
            if (khachHang.getMaKH() == null || khachHang.getMaKH().trim().isEmpty()) {
                // Trường hợp này không nên xảy ra khi "Chi tiết"
                success = khachHangDao.insert(khachHang);
            } else {
                success = khachHangDao.update(khachHang);
            }

            if (success) {
                thongBao("Lưu thành công!");
                dongCuaSo();
            } else {
                thongBao("Lưu thất bại!");
            }
        } catch (Exception e) {
            thongBao("Lỗi khi lưu: " + e.getMessage());
        }
    }


    @FXML
    private void XoaClick() {
        if (khachHang == null || khachHang.getMaKH() == null) {
            thongBao("Chưa chọn khách hàng để xóa!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc muốn xóa khách hàng này?");
        alert.setContentText("Khách hàng sẽ được ẩn (trạng thái = 0).");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean result = khachHangDao.deleteById(khachHang.getMaKH());
                if (result) {
                    thongBao("Đã xóa khách hàng!");
                    dongCuaSo();
                } else {
                    thongBao("Xóa thất bại!");
                }
            }
        });
    }


    @FXML
    private void HuyClick() {
        dongCuaSo();
    }

    private void thongBao(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void dongCuaSo() {
        Stage stage = (Stage) btnHuy.getScene().getWindow();
        stage.close();
    }
}