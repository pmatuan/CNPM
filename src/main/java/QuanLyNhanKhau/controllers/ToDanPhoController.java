package QuanLyNhanKhau.controllers;

import QuanLyNhanKhau.views.Windows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ToDanPhoController implements Initializable {

    @FXML
    private AnchorPane content;

    @FXML
    private Button dangXuat;

    @FXML
    private Button hoKhau;

    @FXML
    private Button nhanKhau;

    @FXML
    private Button thongKe;

    @FXML
    private Button trangChu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent root = null;
        try {
            root = Windows.getRoot("home/trangchu_todanpho.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        content.getChildren().setAll(root);
    }

    @FXML
    void handleClicksSidebar(ActionEvent event) throws IOException {
        if (event.getSource() == trangChu) {
            Parent root = Windows.getRoot("home/trangchu_todanpho.fxml");
            content.getChildren().setAll(root);
        } else if (event.getSource() == nhanKhau) {
            Parent root = Windows.getRoot("home/nhankhau.fxml");
            content.getChildren().setAll(root);
        } else if (event.getSource() == hoKhau) {
            Parent root = Windows.getRoot("home/hokhau.fxml");
            content.getChildren().setAll(root);
        } else if (event.getSource() == thongKe) {
            Parent root = Windows.getRoot("home/thongke_todanpho.fxml");
            content.getChildren().setAll(root);
        } else if (event.getSource() == dangXuat) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có muốn đăng xuất không?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.exit(0);
            }
        }
    }
}

