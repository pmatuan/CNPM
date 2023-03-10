package QuanLyNhanKhau.controllers;

import QuanLyNhanKhau.services.TrangChuDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class TrangChuToDanPhoController implements Initializable {

    @FXML
    private Label SoLuongHoKhau;

    @FXML
    private Label SoLuongNhanKhau;

    @FXML
    private Label SoLuongTamTru;

    @FXML
    private Label SoLuongTamVang;

    @FXML
    private GridPane contentTrangChu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            TrangChuDB trangchuDB = new TrangChuDB();
            Map<String, Integer> map = trangchuDB.getQuantityToDanPho();
            SoLuongNhanKhau.setText(map.get("NhanKhau").toString());
            SoLuongHoKhau.setText(map.get("HoKhau").toString());
            SoLuongTamTru.setText(map.get("TamTru").toString());
            SoLuongTamVang.setText(map.get("TamVang").toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
