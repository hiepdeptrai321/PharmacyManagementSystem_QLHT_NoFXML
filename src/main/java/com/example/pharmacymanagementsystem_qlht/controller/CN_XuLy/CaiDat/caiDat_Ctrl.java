package com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.CaiDat;

import com.example.pharmacymanagementsystem_qlht.controller.CuaSoChinh_QuanLy_Ctrl;
import com.example.pharmacymanagementsystem_qlht.dao.CaiDat_Dao;
import com.example.pharmacymanagementsystem_qlht.model.CaiDat;
import com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.CaiDat.caiDat_GUI;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class caiDat_Ctrl {
    private final caiDat_GUI view;
    private CaiDat_Dao caiDat_Dao;
    private List<CaiDat> caiDat_List;

    public caiDat_Ctrl(caiDat_GUI view) {
        this.view = view;
        caiDat_Dao = new CaiDat_Dao();
        caiDat_List = new ArrayList<>();
        caiDat_List = caiDat_Dao.selectAll();
        loadThongSo();
        initEvent();
    }

    public void loadThongSo() {
        for(CaiDat caiDat:caiDat_List){
            switch(caiDat.getTenThongSo()){
                case "GiaTriThue":{
                    Float giaTriThue =  Float.parseFloat(caiDat.getGiaTri())*100;
                    String[] giaTriThueSplit =  giaTriThue.toString().split("\\.");

                    view.txtThue.setText(giaTriThueSplit[0]);
                }break;
                case "NgayHetHan":{
                    int soNgay = Integer.parseInt(caiDat.getGiaTri());
                    view.txtNgay.setText(soNgay+"");
                    view.cbxdonViNgay.getSelectionModel().select(0);

                }break;
                default:break;
            }
        }
    }

    private void initEvent() {
        view.btnLuu.setOnAction(e -> luu());
        view.btnHuy.setOnAction(e -> huy());
    }

    private void luu() {
        if(!validateInput()) return;
        if(!xacNhanLuu()) return;
        int cachLuu = cachLuu();
        if (cachLuu == 0) return;

        for (CaiDat caiDat : caiDat_List) {
            switch (caiDat.getTenThongSo()) {

                case "GiaTriThue":
                    if (cachLuu == 1||cachLuu == 3) {
                        Float thueTemp = Float.parseFloat(view.txtThue.getText());
                        String thueDaChuyen = String.valueOf(thueTemp/100);
                        caiDat.setGiaTri(thueDaChuyen);
                        caiDat_Dao.update(caiDat);
                    }
                    break;

                case "NgayHetHan":
                    if (cachLuu == 2||cachLuu == 3) {
                        String donVi = view.cbxdonViNgay.getValue();
                        int soNgay = 0;
                        soNgay = Integer.parseInt(view.txtNgay.getText());
                        switch(donVi){
                            case "Tháng":{
                                soNgay *= 30;
                            } case "Năm":{
                                soNgay *=365;
                            }
                            default:break;
                        }
                        caiDat.setGiaTri(String.valueOf(soNgay));
                        caiDat_Dao.update(caiDat);
                    }break;
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hoàn tất");
        alert.setHeaderText(null);
        alert.setContentText("Lưu cài đặt thành công.\nBạn có muốn khởi động lại ứng dụng để áp dụng thay đổi không?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            restartApp();
        }
        dong();
    }

    private void restartApp(){
        // Đóng toàn bộ cửa sổ hiện tại
        Stage currentStage = (Stage) view.btnLuu.getScene().getWindow();
        currentStage.close();
        CuaSoChinh_QuanLy_Ctrl.instance.dong();

        // Mở lại màn hình đăng nhập
        javafx.application.Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                new com.example.pharmacymanagementsystem_qlht.controller.DangNhap_Ctrl()
                        .start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private int cachLuu(){
        int cachLuu = 0;
        for(CaiDat caiDat:caiDat_List){
            switch(caiDat.getTenThongSo()){
                case "GiaTriThue":{
                    Float thueTemp = Float.parseFloat(view.txtThue.getText());
                    String thueDaChuyen = String.valueOf(thueTemp/100);
                    if(!thueDaChuyen.equals(caiDat.getGiaTri())) {
                        cachLuu+=1;
                    }
                }break;
                case "NgayHetHan":{
                    if(!view.txtNgay.getText().equals(caiDat.getGiaTri())) {
                        cachLuu+=2;
                    }
                }break;
                default:break;
            }
        }
        return cachLuu;
    }

    private boolean xacNhanLuu() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận lưu");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn lưu các thay đổi không?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private boolean validateInput() {
        String thueText = view.txtThue.getText().trim();

        if (thueText.isEmpty()) {
            showError("Thuế không được để trống");
            return false;
        }

        try {
            float thue = Float.parseFloat(thueText);

            if (thue < 0) {
                showError("Thuế không được là số âm");
                return false;
            }

            if (thue > 100) {
                showError("Thuế không được vượt quá 100%");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Thuế phải là số");
            return false;
        }

        String ngayText = view.txtNgay.getText().trim();

        if (ngayText.isEmpty()) {
            showError("Thời gian không được để trống");
            return false;
        }

        try {
            int ngay = Integer.parseInt(ngayText);

            if (ngay < 0) {
                showError("Thời gian không được là số âm");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Thời gian phải là số nguyên");
            return false;
        }

        return true;
    }

    private boolean xacNhanHuy() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn hủy không?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi nhập liệu");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    private void huy() {
        if(!xacNhanHuy()) return;
        dong();
    }

    private void dong(){
        Stage stage = (Stage) view.btnLuu.getScene().getWindow();
        stage.close();
    }
}
