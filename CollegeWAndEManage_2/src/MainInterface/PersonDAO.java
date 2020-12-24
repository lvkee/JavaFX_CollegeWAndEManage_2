package MainInterface;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import javax.xml.transform.Result;
import java.sql.ResultSet;

public interface PersonDAO extends DAO {
    void addPerson(String id, String name, String sex, int age, String department, double waterConsumption, double electricityConsumption, String isPay);

    ObservableList<Person> getAllPerson();

    Person searchPerson(String name, Double waterC, Double eC);
}
