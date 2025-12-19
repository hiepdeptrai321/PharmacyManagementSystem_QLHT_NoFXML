package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeTopSanPham;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ThongKeTopSP_Dao {

    // Hàm chung thực thi
    private List<ThongKeTopSanPham> executeQuery(String sql, LocalDate from, LocalDate to, int top) {
        List<ThongKeTopSanPham> list = new ArrayList<>();
        try (Connection conn = ConnectDB.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, top);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));

            try (ResultSet rs = ps.executeQuery()) {
                int i = 1;
                while (rs.next()) {
                    ThongKeTopSanPham sp = new ThongKeTopSanPham();
                    sp.setStt(i++);
                    sp.setMaThuoc(rs.getString("MaThuoc"));
                    sp.setTenThuoc(rs.getString("TenThuoc"));
                    sp.setDonViTinh(rs.getString("DVT_CoBan"));
                    sp.setSoLuong(rs.getInt("TongSoLuong"));
                    sp.setTongTien(rs.getDouble("TongDoanhThu"));
                    list.add(sp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ThongKeTopSanPham> getTopBanChay(LocalDate from, LocalDate to, int top) {
        String sql = "SELECT TOP (?) T.MaThuoc, T.TenThuoc, ISNULL(DVT.TenDonViTinh, N'Chưa cập nhật') AS DVT_CoBan, " +
                "SUM(CTHD.SoLuong) AS TongSoLuong, SUM(CTHD.SoLuong * (CTHD.DonGia - CTHD.GiamGia)) AS TongDoanhThu " +
                "FROM ChiTietHoaDon CTHD " +
                "JOIN HoaDon HD ON CTHD.MaHD = HD.MaHD " +
                "JOIN Thuoc_SP_TheoLo L ON CTHD.MaLH = L.MaLH " +
                "JOIN Thuoc_SanPham T ON L.MaThuoc = T.MaThuoc " +
                "LEFT JOIN ChiTietDonViTinh CTDVT ON T.MaThuoc = CTDVT.MaThuoc AND CTDVT.DonViCoBan = 1 " +
                "LEFT JOIN DonViTinh DVT ON CTDVT.MaDVT = DVT.MaDVT " +
                "WHERE CAST(HD.NgayLap AS DATE) BETWEEN ? AND ? " +
                "GROUP BY T.MaThuoc, T.TenThuoc, DVT.TenDonViTinh " +
                "ORDER BY TongSoLuong DESC";
        return executeQuery(sql, from, to, top);
    }

    public List<ThongKeTopSanPham> getTopDoanhThu(LocalDate from, LocalDate to, int top) {
        String sql = "SELECT TOP (?) T.MaThuoc, T.TenThuoc, ISNULL(DVT.TenDonViTinh, N'Chưa cập nhật') AS DVT_CoBan, " +
                "SUM(CTHD.SoLuong) AS TongSoLuong, SUM(CTHD.SoLuong * (CTHD.DonGia - CTHD.GiamGia)) AS TongDoanhThu " +
                "FROM ChiTietHoaDon CTHD " +
                "JOIN HoaDon HD ON CTHD.MaHD = HD.MaHD " +
                "JOIN Thuoc_SP_TheoLo L ON CTHD.MaLH = L.MaLH " +
                "JOIN Thuoc_SanPham T ON L.MaThuoc = T.MaThuoc " +
                "LEFT JOIN ChiTietDonViTinh CTDVT ON T.MaThuoc = CTDVT.MaThuoc AND CTDVT.DonViCoBan = 1 " +
                "LEFT JOIN DonViTinh DVT ON CTDVT.MaDVT = DVT.MaDVT " +
                "WHERE CAST(HD.NgayLap AS DATE) BETWEEN ? AND ? " +
                "GROUP BY T.MaThuoc, T.TenThuoc, DVT.TenDonViTinh " +
                "ORDER BY TongDoanhThu DESC";
        return executeQuery(sql, from, to, top);
    }

    // --- SỬA LẠI HÀM NÀY ĐỂ BÁN CHẬM HOẠT ĐỘNG ĐÚNG ---
    public List<ThongKeTopSanPham> getTopBanCham(LocalDate from, LocalDate to, int top) {
        String sql = "SELECT TOP (?) " +
                "   T.MaThuoc, " +
                "   T.TenThuoc, " +
                "   ISNULL(DVT.TenDonViTinh, N'Chưa cập nhật') AS DVT_CoBan, " +
                "   ISNULL(Sub.TongSL, 0) AS TongSoLuong, " +
                "   ISNULL(Sub.TongTien, 0) AS TongDoanhThu " +
                "FROM Thuoc_SanPham T " +
                "LEFT JOIN ( " +
                "   SELECT L.MaThuoc, SUM(CT.SoLuong) as TongSL, SUM(CT.SoLuong * (CT.DonGia - CT.GiamGia)) as TongTien " +
                "   FROM ChiTietHoaDon CT " +
                "   JOIN HoaDon H ON CT.MaHD = H.MaHD " +
                "   JOIN Thuoc_SP_TheoLo L ON CT.MaLH = L.MaLH " +
                "   WHERE CAST(H.NgayLap AS DATE) BETWEEN ? AND ? " +
                "   GROUP BY L.MaThuoc " +
                ") Sub ON T.MaThuoc = Sub.MaThuoc " +
                "LEFT JOIN ChiTietDonViTinh CTDVT ON T.MaThuoc = CTDVT.MaThuoc AND CTDVT.DonViCoBan = 1 " +
                "LEFT JOIN DonViTinh DVT ON CTDVT.MaDVT = DVT.MaDVT " +
                "WHERE T.TrangThaiXoa = 0 " +
                // Sắp xếp tăng dần (0 lên đầu), thêm MaThuoc để danh sách ổn định nếu trùng số lượng
                "ORDER BY TongSoLuong ASC, T.MaThuoc ASC";
        return executeQuery(sql, from, to, top);
    }
}