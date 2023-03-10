package QuanLyNhanKhau.controllers.hokhau;

import QuanLyNhanKhau.controllers.tables.HoKhauTable;
import QuanLyNhanKhau.models.NhanKhau;
import QuanLyNhanKhau.services.MySQL;
import QuanLyNhanKhau.services.HoKhauDB;
import QuanLyNhanKhau.services.NhanKhauDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class TachHoKhauController implements Initializable {
    private final ObservableList<NhanKhau> listNKMoi = FXCollections.observableArrayList();
    private ObservableList<NhanKhau> listNKCu = FXCollections.observableArrayList();
    private final NhanKhauDB nhankhauDB = new NhanKhauDB();
    private final HoKhauDB hokhauDB = new HoKhauDB();
    private String soHoKhauCu = "";
    @FXML
    private Button btnChuyenSang;
    @FXML
    private Button btnHuy;
    @FXML
    private Button btnLuu;
    @FXML
    private Button btnTim;
    @FXML
    private Label chuHoHienTai;
    @FXML
    private Label chuHoMoi;
    @FXML
    private TextField duong;
    @FXML
    private TextField input;
    @FXML
    private TextField soHoKhauMoi;
    @FXML
    private TextField ngo;
    @FXML
    private TextField soNha;
    @FXML
    private TableView<NhanKhau> tableChonNguoiSangHoMoi;
    @FXML
    private TableColumn<NhanKhau, String> tableChonNguoiSangHoMoi_HoTen;
    @FXML
    private TableColumn<NhanKhau, String> tableChonNguoiSangHoMoi_NgaySinh;
    @FXML
    private TableColumn<NhanKhau, String> tableChonNguoiSangHoMoi_QuanHeVoiChuHo;
    @FXML
    private TableView<HoKhauTable> tableHoKhauCanTach;
    @FXML
    private TableColumn<HoKhauTable, String> tableHoKhauCanTach_DiaChi;
    @FXML
    private TableColumn<HoKhauTable, String> tableHoKhauCanTach_HoTenChuHo;
    @FXML
    private TableColumn<HoKhauTable, String> tableHoKhauCanTach_soHoKhau;
    @FXML
    private TableView<NhanKhau> tableNhungNguoiOHoMoi;
    @FXML
    private TableColumn<NhanKhau, String> tableNhungNguoiOHoMoi_HoTen;
    @FXML
    private TableColumn<NhanKhau, String> tableNhungNguoiOHoMoi_NgaySinh;
    @FXML
    private TableColumn<NhanKhau, String> tableNhungNguoiOHoMoi_QuanHeVoiChuHo;

    private ObservableList<HoKhauTable> getHoKhauList() {
        try {
            return hokhauDB.getListHoKhauTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ObservableList<NhanKhau> getNhanKhauListWithSoHoKhau(String soHoKhau) {
        try {
            return nhankhauDB.getListNhanKhauWithSoHoKhau(soHoKhau);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement updateHoKhau(String soHoKhauMoi, int idChuHo, String soNha, String ngo, String duong) throws SQLException {
        return hokhauDB.insertHoKhau(soHoKhauMoi, idChuHo, soNha, ngo, duong);
    }

    @FXML
    void handleClicks(ActionEvent event) throws SQLException {
        if (event.getSource() == btnTim) {
            ObservableList<HoKhauTable> listHK = getHoKhauList();
            listHK.removeIf(HoKhauTable -> !HoKhauTable.getSoHoKhau().toLowerCase().contains(input.getText().toLowerCase()));
            tableHoKhauCanTach.setItems(listHK);
        } else {
            if (event.getSource() == btnLuu) {
                if (soHoKhauMoi.getText().isEmpty() || listNKMoi.isEmpty() || chuHoMoi.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Kh??ng th??? l??u");
                    alert.setHeaderText("Thi???u th??ng tin");
                    alert.setContentText("Vui l??ng ??i???n t???t c??? c??c tr?????ng b???t bu???c.\nC??c tr?????ng c?? d???u (*) l?? c??c tr?????ng b???t bu???c.");
                    alert.showAndWait();
                    return;
                }
                int idNhanKhauChuHo = listNKMoi.stream()
                        .filter(nk -> nk.getQuanHeVoiChuHo().equals("Ch??? h???"))
                        .findFirst()
                        .map(NhanKhau::getId)
                        .orElse(-1);
                String hoTenChuHo = listNKMoi.stream()
                        .filter(nk -> nk.getQuanHeVoiChuHo().equals("Ch??? h???"))
                        .findFirst()
                        .map(NhanKhau::getHoTen)
                        .orElse(null);
                PreparedStatement psmt = updateHoKhau(soHoKhauMoi.getText(), idNhanKhauChuHo, soNha.getText(), ngo.getText(), duong.getText());
                hokhauDB.addThayDoiNhanKhauTrongHoKhau(soHoKhauMoi.getText(), hoTenChuHo, "Tr??? th??nh ch??? h???");
                ResultSet rs = psmt.getGeneratedKeys();
                int idHoKhau = 0;
                if (rs.next()) {
                    idHoKhau = rs.getInt(1);
                }
                for (NhanKhau n : listNKMoi) {
                    if (!n.getHoTen().equals(hoTenChuHo)) {
                        hokhauDB.addThayDoiNhanKhauTrongHoKhau(soHoKhauMoi.getText(), n.getHoTen(), "Chuy???n ?????n h??? kh???u m???i");
                    }
                    hokhauDB.addThayDoiNhanKhauTrongHoKhau(soHoKhauCu, n.getHoTen(), "B??? x??a kh???i h??? kh???u c??");
                    n.setIdHoKhau(idHoKhau);
                }

                Connection connection = MySQL.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE NhanKhau SET idHoKhau = ?, quanHeVoiChuHo = ? WHERE id = ?");
                for (NhanKhau nhanKhau : listNKMoi) {
                    preparedStatement.setInt(1, nhanKhau.getIdHoKhau());
                    preparedStatement.setString(2, nhanKhau.getQuanHeVoiChuHo());
                    preparedStatement.setInt(3, nhanKhau.getId());
                    preparedStatement.executeUpdate();
                }
            }

            // T???t c???a s???
            ((Node) event.getSource()).getScene().getWindow().hide();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableHoKhauCanTach.setOnMouseClicked(event -> {
            HoKhauTable selectedHoKhauTable = tableHoKhauCanTach.getSelectionModel().getSelectedItem();
            if (selectedHoKhauTable != null) {
                soHoKhauCu = selectedHoKhauTable.getSoHoKhau();
                chuHoHienTai.setText(selectedHoKhauTable.getHoTen());

                listNKCu = getNhanKhauListWithSoHoKhau(selectedHoKhauTable.getSoHoKhau());
                tableChonNguoiSangHoMoi_HoTen.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("hoTen"));
                tableChonNguoiSangHoMoi_NgaySinh.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("ngaySinh"));
                tableChonNguoiSangHoMoi_QuanHeVoiChuHo.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("quanHeVoiChuHo"));
                tableChonNguoiSangHoMoi.setItems(listNKCu);
            }
        });
        tableChonNguoiSangHoMoi.setOnMouseClicked(event -> {
            NhanKhau selectedNhanKhau = tableChonNguoiSangHoMoi.getSelectionModel().getSelectedItem();
            btnChuyenSang.setOnMouseClicked(click_event -> {
                if (selectedNhanKhau.getQuanHeVoiChuHo().equals("Ch??? h???")) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Th??ng b??o");
                    alert.setHeaderText("Chuy???n th???t b???i");
                    alert.setContentText("Kh??ng th??? chuy???n ch??? h??? sang h??? kh???u m???i");
                    alert.showAndWait();
                    return;
                }
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Quan h??? v???i ch??? h???");
                dialog.setHeaderText("Nh???p quan h??? v???i ch??? h???:");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    String quanHeVoiChuHo = result.get();
                    if (quanHeVoiChuHo.equals("Ch??? h???")) {
                        chuHoMoi.setText(selectedNhanKhau.getHoTen());
                    }
                    selectedNhanKhau.setQuanHeVoiChuHo(quanHeVoiChuHo);
                    if (!listNKMoi.stream().anyMatch(obj -> obj.getId() == selectedNhanKhau.getId())) {
                        listNKMoi.add(selectedNhanKhau);
                        listNKCu.remove(selectedNhanKhau);
                    }

                }
            });
        });
        tableHoKhauCanTach_soHoKhau.setCellValueFactory(new PropertyValueFactory<HoKhauTable, String>("soHoKhau"));
        tableHoKhauCanTach_HoTenChuHo.setCellValueFactory(new PropertyValueFactory<HoKhauTable, String>("hoTen"));
        tableHoKhauCanTach_DiaChi.setCellValueFactory(new PropertyValueFactory<HoKhauTable, String>("diaChi"));
        tableHoKhauCanTach.setItems(getHoKhauList());

        tableNhungNguoiOHoMoi_HoTen.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("hoTen"));
        tableNhungNguoiOHoMoi_NgaySinh.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("ngaySinh"));
        tableNhungNguoiOHoMoi_QuanHeVoiChuHo.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("quanHeVoiChuHo"));
        tableNhungNguoiOHoMoi.setItems(listNKMoi);
    }
}

