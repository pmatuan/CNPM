package QuanLyNhanKhau.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/quan_ly_nhan_khau",
                    "root", "abc123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
