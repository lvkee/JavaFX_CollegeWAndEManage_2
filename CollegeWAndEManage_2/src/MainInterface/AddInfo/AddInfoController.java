package MainInterface.AddInfo;

import MainInterface.*;
import MainInterface.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddInfoController implements Initializable {
    public TextField name;
    public TextField num;
    public TextField age;
    public TextField department;
    public TextField wConsume;
    public TextField eConsume;
    public Button button_exit;
    public ComboBox sex;
    public ComboBox isPay;
    PersonDAO personDAO = new PersonDAOImpl();

    public AddInfoController() throws SQLException, DaoException {
    }


    public void addPerson(ActionEvent actionEvent) {
        if (!name.getText().isEmpty() && !num.getText().isEmpty() && !age.getText().isEmpty() && !department.getText().isEmpty() && !wConsume.getText().isEmpty() && !eConsume.getText().isEmpty()) {
            try {
                int theAge = Integer.parseInt(age.getText());
                double thewConsume = Double.parseDouble(wConsume.getText());
                double theeConsume = Double.parseDouble(eConsume.getText());
                personDAO.addPerson(num.getText(), name.getText(), sex.getValue().toString(), theAge, department.getText(), thewConsume, theeConsume, isPay.getValue().toString());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.titleProperty().set("提示");
                alert.headerTextProperty().set("请注意输入项对应数据类型，请检查后重试！");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.titleProperty().set("提示");
            alert.headerTextProperty().set("存在未填项，请检查后重试！");
            alert.show();
        }
    }

//    退出
    public void toExit(ActionEvent actionEvent) {
        Stage stage = (Stage)  button_exit.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sex.getItems().removeAll(sex.getItems());
        sex.getItems().addAll("男", "女");
        sex.getSelectionModel().select("男");
        isPay.getItems().removeAll(sex.getItems());
        isPay.getItems().addAll("是", "否");
        isPay.getSelectionModel().select("是");
    }
}
