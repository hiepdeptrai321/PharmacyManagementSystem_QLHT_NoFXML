package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKNhaCungCap;

import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class ChiTietNhaCungCap_GUI {

    // --- Khai báo public các thành phần ---
    public TextField DiaChi = new TextField();
    public TextField Email = new TextField();
    public TextField GPKD = new TextField();
    public TextArea GhiChu = new TextArea();
    public TextField MST = new TextField();
    public TextField SDT = new TextField();
    public TextField TenCongTy = new TextField();
    public Button btnThoat = new Button("Thoát");
    public TextField maNCC = new TextField();
    public TextField tenNCC = new TextField();

    public Parent createContent() {
        Pane root = new Pane();
        root.setPrefHeight(326.0);
        root.setPrefWidth(601.0);
        root.setStyle("-fx-font-size: 13 px;");

        // --- Header Pane ---
        Pane headerPane = new Pane();
        headerPane.setId("paneGachChan");
        headerPane.setPrefHeight(38.0);
        headerPane.setPrefWidth(601.0);
        Label titleLabel = new Label("Chi tiết nhà cung cấp");
        titleLabel.setLayoutX(14.0);
        titleLabel.setLayoutY(2.0);
        titleLabel.setPrefWidth(263.0);
        titleLabel.setFont(new Font("System Bold", 25.0));
        headerPane.getChildren().add(titleLabel);

        // --- Dòng 1 ---
        Label lblMaNCC = new Label("Mã NCC:");
        lblMaNCC.setLayoutX(14.0);
        lblMaNCC.setLayoutY(46.0);
        maNCC.setEditable(false);
        maNCC.setLayoutX(14.0);
        maNCC.setLayoutY(63.0);
        maNCC.setPrefHeight(25.0);
        maNCC.setPrefWidth(129.0);

        Label lblTenNCC = new Label("Tên nhà cung cấp:");
        lblTenNCC.setLayoutX(148.0);
        lblTenNCC.setLayoutY(46.0);
        tenNCC.setEditable(false);
        tenNCC.setLayoutX(148.0);
        tenNCC.setLayoutY(63.0);
        tenNCC.setPrefHeight(25.0);
        tenNCC.setPrefWidth(342.0);

        Label lblSDT = new Label("Số điện thoại");
        lblSDT.setLayoutX(484.0);
        lblSDT.setLayoutY(45.0);
        SDT.setEditable(false);
        SDT.setLayoutX(484.0);
        SDT.setLayoutY(63.0);
        SDT.setPrefHeight(25.0);
        SDT.setPrefWidth(105.0);

        // --- Dòng 2 ---
        Label lblEmail = new Label("Email:");
        lblEmail.setLayoutX(14.0);
        lblEmail.setLayoutY(94.0);
        Email.setEditable(false);
        Email.setLayoutX(14.0);
        Email.setLayoutY(111.0);
        Email.setPrefHeight(25.0);
        Email.setPrefWidth(208.0);

        Label lblDiaChi = new Label("Địa chỉ:");
        lblDiaChi.setLayoutX(227.0);
        lblDiaChi.setLayoutY(94.0);
        DiaChi.setEditable(false);
        DiaChi.setLayoutX(227.0);
        DiaChi.setLayoutY(111.0);
        DiaChi.setPrefHeight(25.0);
        DiaChi.setPrefWidth(362.0);

        // --- Dòng 3 ---
        Label lblGPKD = new Label("GPKD:");
        lblGPKD.setLayoutX(14.0);
        lblGPKD.setLayoutY(142.0);
        GPKD.setEditable(false);
        GPKD.setLayoutX(14.0);
        GPKD.setLayoutY(159.0);
        GPKD.setPrefHeight(25.0);
        GPKD.setPrefWidth(134.0);

        TenCongTy.setEditable(false);
        TenCongTy.setLayoutX(153.0);
        TenCongTy.setLayoutY(159.0);
        TenCongTy.setPrefHeight(25.0);
        TenCongTy.setPrefWidth(300.0);

        MST.setEditable(false);
        MST.setLayoutX(459.0);
        MST.setLayoutY(159.0);
        MST.setPrefHeight(25.0);
        MST.setPrefWidth(134.0);

        // --- Ghi chú ---
        Label lblGhiChu = new Label("Ghi chú:");
        lblGhiChu.setLayoutX(14.0);
        lblGhiChu.setLayoutY(190.0);
        GhiChu.setEditable(false);
        GhiChu.setLayoutX(14.0);
        GhiChu.setLayoutY(207.0);
        GhiChu.setPrefHeight(67.0);
        GhiChu.setPrefWidth(575.0);
        GhiChu.setWrapText(true);

        // --- Nút Thoát ---
        btnThoat.setLayoutX(534.0);
        btnThoat.setLayoutY(287.0);
        btnThoat.setPrefHeight(25.0);
        btnThoat.setPrefWidth(55.0);

        root.getChildren().addAll(
                headerPane, lblMaNCC, maNCC, lblTenNCC, tenNCC, lblSDT, SDT,
                lblEmail, Email, lblDiaChi, DiaChi, lblGPKD, GPKD, TenCongTy, MST,
                lblGhiChu, GhiChu, btnThoat
        );

        return root;
    }
}