package QuanLyNhanKhau.controllers.nhankhau;

import QuanLyNhanKhau.models.NhanKhau;
import QuanLyNhanKhau.services.MySQL;
import QuanLyNhanKhau.services.NhanKhauDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SuaNhanKhauController implements Initializable {

    private final NhanKhauDB nhankhauDB = new NhanKhauDB();

    @FXML
    private Button btnHuy;

    @FXML
    private Button btnLuu;

    @FXML
    private TextField cccd;

    @FXML
    private TextField danToc;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton gioiTinhNam;

    @FXML
    private RadioButton gioiTinhNu;

    @FXML
    private TextField hoTen;

    @FXML
    private DatePicker ngayCap;

    @FXML
    private DatePicker ngaySinh;

    @FXML
    private TextField ngheNghiep;

    @FXML
    private TextField nguyenQuan;

    @FXML
    private TextField noiCap;

    @FXML
    private TextField noiLamViec;

    @FXML
    private TextField noiSinh;

    private NhanKhau nhankhau;

    public void setNhankhau(NhanKhau nhankhau) {
        this.nhankhau = nhankhau;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NhanKhauDB nhankhauDB = new NhanKhauDB();
        PreparedStatement pstmt = null;
        try {
            pstmt = nhankhauDB.getFullCCCD(nhankhau.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String cccd_text = "";
        LocalDate ngaycap = null;
        String noicap = "";
        try {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("idNhanKhau") != nhankhau.getId()) continue;
                cccd_text = rs.getString("cccd");
                ngaycap = rs.getDate("ngaycap").toLocalDate();
                noicap = rs.getString("noicap");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cccd.setText(cccd_text);
        danToc.setText(nhankhau.getDanToc());
        if (nhankhau.getGioiTinh().equals("Nam")){
            gioiTinhNam.setSelected(true);
        }
        else {
            gioiTinhNu.setSelected(true);
        }
        hoTen.setText(nhankhau.getHoTen());
        ngayCap.setValue(ngaycap);
        ngaySinh.setValue(nhankhau.getNgaySinh());
        ngheNghiep.setText(nhankhau.getNgheNghiep());
        nguyenQuan.setText(nhankhau.getNguyenQuan());
        noiCap.setText(noicap);
        noiLamViec.setText(nhankhau.getNoiLamViec());
        noiSinh.setText(nhankhau.getNoiSinh());
    }

    @FXML
    void handleClicks(ActionEvent event) throws SQLException {
        if (event.getSource() == btnLuu) {
            if (hoTen.getText().isEmpty() || ngaySinh.getValue() == null || noiSinh.getText().isEmpty() || (!gioiTinhNam.isSelected() && !gioiTinhNu.isSelected()) ||
                    nguyenQuan.getText().isEmpty() || danToc.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Kh??ng th??? l??u");
                alert.setHeaderText("Thi???u th??ng tin");
                alert.setContentText("Vui l??ng ??i???n t???t c??? c??c tr?????ng b???t bu???c.\nC??c tr?????ng c?? d???u (*) l?? c??c tr?????ng b???t bu???c.");
                alert.showAndWait();
                return;
            }
            String gioiTinh = gioiTinhNam.isSelected() ? "Nam" : (gioiTinhNu.isSelected() ? "N???" : "Kh??ng r??");

            Connection connection = MySQL.getConnection();
            PreparedStatement pstmt_nhankhau = nhankhauDB.updateNhanKhau(nhankhau.getId(), hoTen.getText(), ngaySinh.getValue(), gioiTinh, noiSinh.getText(),
                    nguyenQuan.getText(), danToc.getText(), ngheNghiep.getText(), noiLamViec.getText());
            if (cccd.getText() != null && ngayCap.getValue() != null && noiCap.getText() != null) {
                nhankhauDB.deleteCCCD(cccd.getText());
                nhankhauDB.addCCCD(cccd.getText(), nhankhau.getId(), ngayCap.getValue(), noiCap.getText());
            }

        }
        // T???t c???a s???
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
