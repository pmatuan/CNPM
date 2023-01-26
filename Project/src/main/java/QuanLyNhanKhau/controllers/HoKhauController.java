package QuanLyNhanKhau.controllers;

import QuanLyNhanKhau.controllers.tables.HoKhauTable;
import QuanLyNhanKhau.models.HoKhau;
import QuanLyNhanKhau.services.hokhauDB;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class HoKhauController {
    @FXML
    private TableColumn<HoKhauTable, String> soHK;
    @FXML
    private TableColumn<HoKhauTable, String> hoTenChuHK;
    @FXML
    private TableColumn<HoKhauTable, String> diaChiHK;
    @FXML
    private TableView<HoKhauTable> tableHoKhau;

    public HoKhauController(TableColumn<HoKhauTable, String> soHK, TableColumn<HoKhauTable, String> hoTenChuHK, TableColumn<HoKhauTable, String> diaChiHK, TableView<HoKhauTable> tableHoKhau) {
        this.soHK = soHK;
        this.hoTenChuHK = hoTenChuHK;
        this.diaChiHK = diaChiHK;
        this.tableHoKhau = tableHoKhau;
    }

    public void initialize(){
        hokhauDB hokhauinDB = new hokhauDB();
        ObservableList<HoKhauTable> listHK = null;
        try {
            listHK = hokhauinDB.getListHoKhauTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        soHK.setCellValueFactory(new PropertyValueFactory<HoKhauTable, String>("soHoKhau"));
        hoTenChuHK.setCellValueFactory(new PropertyValueFactory<HoKhauTable, String>("hoTen"));
        diaChiHK.setCellValueFactory(new PropertyValueFactory<HoKhauTable, String>("diaChi"));
        tableHoKhau.setItems(listHK);
    }
}
