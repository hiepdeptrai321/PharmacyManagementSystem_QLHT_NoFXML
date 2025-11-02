package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeBanHang;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeSanPham;
import java.sql.Date;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.example.pharmacymanagementsystem_qlht.model.HoaDonDisplay; // <-- THÊM IMPORT NÀY
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class ThongKe_Dao {


    public List<ThongKeBanHang> getThongKeBanHang(String thoiGian) {
        List<ThongKeBanHang> list = new ArrayList<>();
        String sql; // Tên Stored Procedure

        // Chọn Stored Procedure dựa trên đầu vào
        switch (thoiGian) {
            case "Hôm nay":
                sql = "{call sp_ThongKeBanHang_HomNay}";
                break;
            case "Tuần này":
                sql = "{call sp_ThongKeBanHang_TuanNay}";
                break;
            case "Tháng này":
                sql = "{call sp_ThongKeBanHang_ThangNay}";
                break;
            case "Năm Nay":
                sql = "{call sp_ThongKeBanHang_NamNay}";
                break;
            default:
                sql = "{call sp_ThongKeBanHang_HomNay}"; // Mặc định
        }

        try (ResultSet rs = ConnectDB.query(sql)) {
            while (rs.next()) {
                ThongKeBanHang tk = new ThongKeBanHang(
                        rs.getString("ThoiGian"),
                        rs.getInt("SoLuongHoaDon"),
                        rs.getDouble("TongGiaTri"),
                        rs.getDouble("GiamGia"),
                        rs.getInt("SoLuongDonTra"),
                        rs.getDouble("GiaTriDonTra"),
                        rs.getDouble("DoanhThu")
                );
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy Top 5 sản phẩm bán chạy theo thời gian
     * @param thoiGian "Hôm nay", "Tuần này", "Tháng này", "Quý này"
     * @return Danh sách đối tượng ThongKeSanPham
     */
    public List<ThongKeSanPham> getTop5SanPham(String thoiGian) {
        List<ThongKeSanPham> list = new ArrayList<>();
        String sql; // Tên Stored Procedure

        switch (thoiGian) {
            case "Hôm nay":
                sql = "{call sp_Top5SanPham_HomNay}";
                break;
            case "Tuần này":
                sql = "{call sp_Top5SanPham_TuanNay}";
                break;
            case "Tháng này":
                sql = "{call sp_Top5SanPham_ThangNay}";
                break;
            case "Năm Nay":
                sql = "{call sp_Top5SanPham_NamNay}";
                break;
            default:
                sql = "{call sp_Top5SanPham_HomNay}";
        }

        try (ResultSet rs = ConnectDB.query(sql)) {
            while (rs.next()) {
                ThongKeSanPham sp = new ThongKeSanPham(
                        rs.getString("MaThuoc"),
                        rs.getString("TenThuoc"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("ThanhTien")
                );
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ThongKeBanHang> getThongKeBanHang_TuyChon(LocalDate tuNgay, LocalDate denNgay) {
        List<ThongKeBanHang> list = new ArrayList<>();
        String sql = "{call sp_ThongKeBanHang_TuyChon(?, ?)}";

        Date sqlTuNgay = Date.valueOf(tuNgay);
        Date sqlDenNgay = Date.valueOf(denNgay);

        try (ResultSet rs = ConnectDB.query(sql, sqlTuNgay, sqlDenNgay)) {
            while (rs.next()) {
                ThongKeBanHang tk = new ThongKeBanHang(
                        rs.getString("ThoiGian"), rs.getInt("SoLuongHoaDon"),
                        rs.getDouble("TongGiaTri"), rs.getDouble("GiamGia"),
                        rs.getInt("SoLuongDonTra"), rs.getDouble("GiaTriDonTra"),
                        rs.getDouble("DoanhThu")
                );
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy Top 5 SP theo khoảng ngày TÙY CHỌN
     */
    public List<ThongKeSanPham> getTop5SanPham_TuyChon(LocalDate tuNgay, LocalDate denNgay) {
        List<ThongKeSanPham> list = new ArrayList<>();
        String sql = "{call sp_Top5SanPham_TuyChon(?, ?)}";

        Date sqlTuNgay = Date.valueOf(tuNgay);
        Date sqlDenNgay = Date.valueOf(denNgay);

        try (ResultSet rs = ConnectDB.query(sql, sqlTuNgay, sqlDenNgay)) {
            while (rs.next()) {
                ThongKeSanPham sp = new ThongKeSanPham(
                        rs.getString("MaThuoc"), rs.getString("TenThuoc"),
                        rs.getInt("SoLuong"), rs.getDouble("ThanhTien")
                );
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ObservableList<HoaDonDisplay> getHoaDonTheoThoiGian(String thoiGian) {
        ObservableList<HoaDonDisplay> list = FXCollections.observableArrayList();
        String sql = "{CALL sp_GetHoaDonTheoThoiGian(?)}";

        try (Connection conn = ConnectDB.getInstance();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, thoiGian);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    // Lấy dữ liệu thô từ SQL
                    String maHD = rs.getString("maHD");
                    LocalDate ngayLap = rs.getDate("ngayLap").toLocalDate();
                    String maKH = rs.getString("maKH");
                    String maNV = rs.getString("maNV");
                    double tongTien = rs.getDouble("tongGiaTri"); // Lấy từ cột 'tongGiaTri' của SP

                    // Tạo đối tượng HoaDonDisplay
                    list.add(new HoaDonDisplay(maHD, ngayLap, maKH, maNV, tongTien));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // --- HÀM MỚI 2 (Sửa lại để trả về HoaDonDisplay) ---
    public ObservableList<HoaDonDisplay> getHoaDonTheoTuyChon(LocalDate tuNgay, LocalDate denNgay) {
        ObservableList<HoaDonDisplay> list = FXCollections.observableArrayList();
        String sql = "{CALL sp_GetHoaDonTheoTuyChon(?, ?)}";

        try (Connection conn = ConnectDB.getInstance();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setDate(1, java.sql.Date.valueOf(tuNgay));
            cs.setDate(2, java.sql.Date.valueOf(denNgay));

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    // Lấy dữ liệu thô từ SQL
                    String maHD = rs.getString("maHD");
                    LocalDate ngayLap = rs.getDate("ngayLap").toLocalDate();
                    String maKH = rs.getString("maKH");
                    String maNV = rs.getString("maNV");
                    double tongTien = rs.getDouble("tongGiaTri");

                    // Tạo đối tượng HoaDonDisplay
                    list.add(new HoaDonDisplay(maHD, ngayLap, maKH, maNV, tongTien));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
