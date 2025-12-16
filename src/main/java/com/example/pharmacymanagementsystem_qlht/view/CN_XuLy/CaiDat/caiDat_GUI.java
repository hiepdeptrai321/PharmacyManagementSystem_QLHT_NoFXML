package com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.CaiDat;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class caiDat_GUI {
    public TextField txtThue;
    public ComboBox<String> cbxdonViNgay;
    public TextField txtNgay;
    public Button btnLuu;
    public Button btnHuy;

    public Parent createUI(){
        BorderPane root = new BorderPane();

        VBox pnlChinh = new VBox();
        pnlChinh.setSpacing(10);
        Line line = new Line(0,0,310,0);
        line.setStroke(Color.gray(0.2));
        line.setStrokeWidth(1);
        VBox vbox = new VBox();

        HBox pnlThue = new HBox();

        pnlThue.setSpacing(10);
        Label lblThue = new Label("Thuế (%):");
        txtThue = new TextField();
        txtThue.setPrefSize(35, 25);
        Region paddingTraiThue = new  Region();
        paddingTraiThue.setPadding(new Insets(0,88,0,0));
        pnlThue.getChildren().addAll(paddingTraiThue);
        pnlThue.getChildren().add(lblThue);
        pnlThue.getChildren().add(txtThue);
        pnlChinh.getChildren().add(pnlThue);

        HBox pnlNgayhetHan = new HBox();
        pnlNgayhetHan.setSpacing(10);
        Label lblCanhBaoNgayHetHan = new Label("Thời gian cảnh báo hết hạn:");
        txtNgay = new TextField();
        txtNgay.setPrefSize(35, 25);
        cbxdonViNgay = new ComboBox<>();
        List<String> items = new ArrayList<String>(Arrays.asList("Ngày", "Tháng", "Năm"));
        ObservableList<String> observableItems = FXCollections.observableArrayList(items);
        cbxdonViNgay.setItems(observableItems);
        cbxdonViNgay.getSelectionModel().select(0);
        pnlNgayhetHan.getChildren().add(lblCanhBaoNgayHetHan);
        pnlNgayhetHan.getChildren().add(txtNgay);
        pnlNgayhetHan.getChildren().add(cbxdonViNgay);
        pnlChinh.getChildren().add(pnlNgayhetHan);
        Region paddingTrai = new Region();
        paddingTrai.setPadding(new Insets(0,5,0,5));
        root.setLeft(paddingTrai);
        Region paddingPhai = new Region();
        paddingPhai.setPadding(new Insets(0,5,0,5));
        root.setRight(paddingPhai);

        btnLuu = new Button("Lưu");
        btnLuu.setStyle("-fx-text-fill: #ffffff;\n" +
                "    -fx-background-color: #0c81ff;"+
                "-fx-font-weight: bold");
        btnHuy = new Button("Hủy");
        btnHuy.setStyle("-fx-text-fill: #fbfbfb;\n" +
                "    -fx-background-color: #F0AD4EFF;"+
                "-fx-font-weight: bold");
        Region paddingTraiNut = new Region();
        paddingTraiNut.setPadding(new Insets(0,165,0,20));
        HBox bottomPane = new HBox(paddingTraiNut,btnHuy,btnLuu);
        bottomPane.setSpacing(10);
        pnlChinh.getChildren().add(bottomPane);
        root.setCenter(pnlChinh);

        Label lblTitle = new Label("Cài đặt");
        lblTitle.setStyle("-fx-font-size: 20px;"
        + "-fx-font-weight: bold;");
        HBox topPane = new HBox(lblTitle);
        topPane.setAlignment(Pos.CENTER);
        vbox.getChildren().add(topPane);
        vbox.getChildren().addAll(line);
        vbox.setPadding(new Insets(0,0,10,0));
        root.setTop(vbox);

//        Scene scene = new Scene(root,310,150);
        return root;
    }
}
