package MainInterface.SearchInfo;

import MainInterface.Person;
import MainInterface.PersonDAO;
import MainInterface.PersonDAOImpl;
import MainInterface.DaoException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.*;

public class SearchInfoController {
    public TextField label_name;
    public TextField label_waterC;
    public TextField label_eC;
    public Button button_exit;
    public TextField textField_Id;
    public TextField textField_Name;
    public TextField textField_Sex;
    public TextField textField_Age;
    public TextField textField_Class;
    public TextField textField_WaterC;
    public TextField textField_EC;
    public TextField textField_WaterCost;
    public TextField textField_ECost;
    public TextField textField_IsPay;
    PersonDAO personDAO = new PersonDAOImpl();

    public SearchInfoController() throws SQLException, DaoException {
    }
//  查询
    public void toSearch(ActionEvent actionEvent) {
        if (label_waterC.getText().isEmpty()) {
            label_waterC.setText("0");
        }
        if (label_eC.getText().isEmpty()) {
            label_eC.setText("0");
        }
        if (label_waterC.getText().isEmpty() && label_eC.getText().isEmpty()) {
            label_waterC.setText("0");
            label_eC.setText("0");
        }
        Person person = personDAO.searchPerson(label_name.getText(), Double.parseDouble(label_waterC.getText()), Double.parseDouble(label_eC.getText()));
        if (person != null) {
            textField_Id.setText(person.getId());
            textField_Name.setText(person.getName());
            textField_Sex.setText(person.getSex());
            textField_Age.setText(String.valueOf(person.getAge()));
            textField_Class.setText(person.getDepartment());
            textField_WaterC.setText(String.valueOf(person.getWaterConsumption()));
            textField_EC.setText(String.valueOf(person.getElectricityConsumption()));
            textField_WaterCost.setText(String.valueOf(person.getWaterCost()));
            textField_ECost.setText(String.valueOf(person.getElectricityCost()));
            textField_IsPay.setText(person.getIsPay());
        }
    }

    //    退出
    public void toExit(MouseEvent mouseEvent) {
        Stage stage = (Stage) button_exit.getScene().getWindow();
        stage.close();
    }

//    修改
    public void toEdit(ActionEvent actionEvent) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exp5?serverTimezone=GMT", "root", "12345678Aa.");
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rst = stmt.executeQuery("SELECT * FROM person");
            rst.first();
            do {
                if (rst.getString(2).equals(textField_Name.getText())) {
                    break;
                }
            } while (rst.next());
            if (!rst.isClosed()) {
                rst.updateString(1, textField_Id.getText());
                rst.updateString(2, textField_Name.getText());
                rst.updateString(3, textField_Sex.getText());
                rst.updateInt(4, Integer.parseInt(textField_Age.getText()));
                rst.updateString(5, textField_Class.getText());
                rst.updateDouble(6, Double.parseDouble(textField_WaterC.getText()));
                rst.updateDouble(7, Double.parseDouble(textField_EC.getText()));
                rst.updateDouble(8, Double.parseDouble(textField_WaterCost.getText()));
                rst.updateDouble(9, Double.parseDouble(textField_ECost.getText()));
                rst.updateString(10, textField_IsPay.getText());
                rst.updateRow();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.titleProperty().set("提示");
                alert.headerTextProperty().set("修改成功！");
                alert.show();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.titleProperty().set("提示");
            alert.headerTextProperty().set("请注意输入项对应数据类型，请检查后重试！");
            alert.show();
        }
    }

//    删除
    public void toDelete(ActionEvent actionEvent) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exp5?serverTimezone=GMT", "root", "12345678Aa.");
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rst = stmt.executeQuery("SELECT * FROM person");
            rst.first();
            do {
                if (rst.getString(2).equals(textField_Name.getText())) {
                    break;
                }
            } while (rst.next());

            rst.deleteRow();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("提示");
            alert.headerTextProperty().set("删除成功！");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.titleProperty().set("提示");
            alert.headerTextProperty().set("删除失败！");
            alert.show();
        }
    }
}
