package MainInterface.Login;

import MainInterface.MainInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Login implements Initializable {

    public ComboBox comboBox;
    public Button button_login;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll("管理员", "教职员工", "学生");
        comboBox.getSelectionModel().select("管理员");
    }

//    退出
    public void toExit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void toOpenMain(ActionEvent actionEvent) throws IOException {
        new MainInterface().start(new Stage());
        Stage stage = (Stage) button_login.getScene().getWindow();
        stage.close();
    }


}
