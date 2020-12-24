package MainInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class PersonDAOImpl implements PersonDAO {


    public PersonDAOImpl() throws DaoException, SQLException {

    }

//    添加用户
    @Override
    public void addPerson(String id, String name, String sex, int age, String department, double waterConsumption, double electricityConsumption, String isPay) {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rst = stmt.executeQuery("SELECT * FROM person");
            rst.moveToInsertRow();
            rst.updateString(1, id);
            rst.updateString(2, name);
            rst.updateString(3, sex);
            rst.updateInt(4, age);
            rst.updateString(5, department);
            rst.updateDouble(6, waterConsumption);
            rst.updateDouble(7, electricityConsumption);
            rst.updateDouble(8, waterConsumption*2);
            rst.updateDouble(9, waterConsumption*2);
            rst.updateString(10, isPay);
            rst.insertRow();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("提示");
            alert.headerTextProperty().set("添加成功！");
            alert.show();
            System.out.println("添加成功！");
        } catch (SQLException | DaoException throwables) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.titleProperty().set("提示");
            alert.headerTextProperty().set("信息有误，请检查后重试！");
            alert.show();
            throwables.printStackTrace();
        }
    }


//    获取数据库中所有用户
    @Override
    public ObservableList<Person> getAllPerson() {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, theName, sex, age, department, waterConsumption, electricityConsumption, waterCost, electricityCost, isPayBill  FROM person");
            ObservableList<Person> data = FXCollections.observableArrayList();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("theName");
                String sex = rs.getString("sex");
                int age = rs.getInt("age");
                String department = rs.getString("department");
                double waterConsumption = rs.getDouble("waterConsumption");
                double electricityConsumption = rs.getDouble("electricityConsumption");
                double waterCost = rs.getDouble("waterCost");
                double electricityCost = rs.getDouble("electricityCost");
                String isPayBill = rs.getString("isPayBill");
                Person person = new Person(id, name, sex, age, department, waterConsumption, electricityConsumption, waterCost, electricityCost, isPayBill);
                data.add(person);
            }
            System.out.println("========数据库连接成功========");
            return data;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

//    查找用户
    @Override
    public Person searchPerson(String name, Double waterC, Double eC) {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, theName, sex, age, department, waterConsumption, electricityConsumption, waterCost, electricityCost, isPayBill  FROM person where theName = '" + name + "'");
            ObservableList<Person> data = FXCollections.observableArrayList();
            if (rs.next()) {
                String id = rs.getString("id");
                String theName = rs.getString("theName");
                String sex = rs.getString("sex");
                int age = rs.getInt("age");
                String department = rs.getString("department");
                double waterConsumption = rs.getDouble("waterConsumption");
                double electricityConsumption = rs.getDouble("electricityConsumption");
                double waterCost = rs.getDouble("waterCost");
                double electricityCost = rs.getDouble("electricityCost");
                String isPayBill = rs.getString("isPayBill");
                System.out.println("查询结果：" + id);
                if (waterConsumption >= waterC && electricityConsumption >= eC) {
                    Person person = new Person(id, theName, sex, age, department, waterConsumption, electricityConsumption, waterCost, electricityCost, isPayBill);
                    return person;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.titleProperty().set("提示");
                alert.headerTextProperty().set("无查询结果，请检查后重试！");
                alert.show();
            }
        } catch (SQLException | DaoException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}