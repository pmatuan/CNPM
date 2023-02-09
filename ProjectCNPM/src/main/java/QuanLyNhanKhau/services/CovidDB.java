package QuanLyNhanKhau.services;

import QuanLyNhanKhau.controllers.tables.CovidTable;
import QuanLyNhanKhau.models.MacCOVID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CovidDB {
    public ObservableList<CovidTable> getListCovidTable() throws SQLException {
        ObservableList<CovidTable> list = FXCollections.observableArrayList();
        Connection connection = MySQL.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MacCOVID.id, NhanKhau.hoTen, MacCOVID.ngayMac, MacCOVID.ngayKhoi, KhaiBao.tinhTrangSucKhoe, KhaiBao.ketQuaTest, KhaiBao.ngayKhaiBao FROM (MacCOVID INNER JOIN KhaiBao on KhaiBao.idMacCOVID = MacCOVID.id) INNER JOIN NhanKhau on NhanKhau.id = MacCOVID.idNhanKhau");
        while(rs.next()) {
            if(rs.getDate("ngayKhoi") != null) {
                CovidTable covid = new CovidTable(rs.getInt("id"), rs.getString("hoTen"), rs.getDate("ngayMac").toString(), rs.getDate("ngayKhoi").toString(), rs.getString("tinhTrangSucKhoe"),
                        rs.getString("ketQuaTest"));
                list.add(covid);
            } else {
                CovidTable covid = new CovidTable(rs.getInt("id"), rs.getString("hoTen"), rs.getDate("ngayMac").toString(), "Chưa khỏi", rs.getString("tinhTrangSucKhoe"),
                        rs.getString("ketQuaTest"));
                list.add(covid);
            }
        }
        rs.close();
        stmt.close();
        connection.close();
        return list;
    }

    public ObservableList<MacCOVID> getListMacCovid() throws SQLException {
        ObservableList<MacCOVID> list = FXCollections.observableArrayList();
        Connection connection = MySQL.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MacCOVID.id, NhanKhau.hoTen, MacCOVID.ngayMac, MacCOVID.ngayKhoi, KhaiBao.tinhTrangSucKhoe, KhaiBao.ketQuaTest, KhaiBao.ngayKhaiBao, KhaiBao.thoiDiemTest, KhaiBao.ngayKhaiBao, KhaiBao.hinhThucTest FROM (MacCOVID INNER JOIN KhaiBao on KhaiBao.idMacCOVID = MacCOVID.id) INNER JOIN NhanKhau on NhanKhau.id = MacCOVID.idNhanKhau");
        while(rs.next()) {
            if(rs.getDate("ngayKhoi") != null) {
                MacCOVID covid = new MacCOVID(rs.getInt("id"), rs.getString("hoTen"), rs.getDate("ngayMac").toString(), rs.getDate("ngayKhoi").toString(), rs.getString("tinhTrangSucKhoe"),
                        rs.getString("ketQuaTest"), rs.getString("hinhThucTest"), rs.getDate("thoiDiemTest").toString(), rs.getDate("ngayKhaiBao").toString());
                list.add(covid);
            } else {
                MacCOVID covid = new MacCOVID(rs.getInt("id"), rs.getString("hoTen"), rs.getDate("ngayMac").toString(), "Chưa khỏi", rs.getString("tinhTrangSucKhoe"),
                        rs.getString("ketQuaTest"), rs.getString("hinhThucTest"), rs.getDate("thoiDiemTest").toString(), rs.getDate("ngayKhaiBao").toString());
                list.add(covid);
            }
        }
        rs.close();
        stmt.close();
        connection.close();
        return list;
    }

}