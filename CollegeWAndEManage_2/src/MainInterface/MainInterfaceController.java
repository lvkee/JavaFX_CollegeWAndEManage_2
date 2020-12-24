package MainInterface;

import MainInterface.AddInfo.AddInfo;
import MainInterface.SearchInfo.SearchInfo;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.util.Duration;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class MainInterfaceController implements Initializable {
/*
    static {
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
    }
*/

    public TableColumn col_num;
    public TableColumn col_name;
    public TableColumn col_sex;
    public TableColumn col_age;
    public TableColumn col_class;
    public TableColumn col_wConsume;
    public TableColumn col_eConsume;
    public TableColumn col_wCost;
    public TableColumn col_eCost;
    public TableView<Person> tblMainList;
    public Button button_add;
    public Label label_prompt;
    public Label dateTime;
    public Label label_Status;
    public Button button_Edit;
    public Button button_Delete;
    public TableColumn col_isPay;

    String password = "12345678Aa.";
    PersonDAO personDAO = new PersonDAOImpl();
    ObservableList<Person> data = FXCollections.observableArrayList();

    public MainInterfaceController() throws SQLException, DaoException {
    }

    //  初始化
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_num.setCellValueFactory(new PropertyValueFactory<Person, String>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        col_sex.setCellValueFactory(new PropertyValueFactory<Person, String>("sex"));
        col_age.setCellValueFactory(new PropertyValueFactory<Person, String>("age"));
        col_class.setCellValueFactory(new PropertyValueFactory<Person, String>("department"));
        col_wConsume.setCellValueFactory(new PropertyValueFactory<Person, String>("waterConsumption"));
        col_eConsume.setCellValueFactory(new PropertyValueFactory<Person, String>("electricityConsumption"));
        col_wCost.setCellValueFactory(new PropertyValueFactory<Person, String>("waterCost"));
        col_eCost.setCellValueFactory(new PropertyValueFactory<Person, String>("electricityCost"));
        col_isPay.setCellValueFactory(new PropertyValueFactory<Person, String>("isPay"));
        tblMainList.setEditable(true);
        col_num.setCellFactory(TextFieldTableCell.<String>forTableColumn());
        col_name.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        col_sex.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        col_age.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_class.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        col_wConsume.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        col_eConsume.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        col_wCost.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        col_eCost.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        col_isPay.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        initClock();
        showAll(personDAO.getAllPerson());
        button_Delete.setDisable(true);
    }

    //    显示按钮点击事件
    public void openTables(ActionEvent actionEvent) {
        showAll(personDAO.getAllPerson());
    }

    //    将数据库中的数据全部显示在Tableview上
    public void showAll(ObservableList<Person> data) {
        this.data = data;
        tblMainList.setItems(data);
    }

    //   添加按钮点击函数
    public void toAddPerson(ActionEvent actionEvent) throws IOException {
        new AddInfo().start(new Stage());
    }

    //  查询按钮点击函数
    public void toSearch(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        new SearchInfo().start(stage);

    }

    //  读取按钮点击事件
    public void toLoad(MouseEvent mouseEvent) throws IOException, SQLException {
        ReadExcel("exp5", "person", password);
        showAll(personDAO.getAllPerson());
    }

    //  保存按钮点击事件
    public void toSave(MouseEvent mouseEvent) throws IOException {
        writeExcel("exp5", "person", password);
    }

    //  保存文件
    public void writeExcel(String dname, String tname, String dPassword) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("保存文件");
        chooser.setInitialDirectory(new File("D:\\"));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx");
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showSaveDialog(new Stage());
        if (file != null) {
            //写入的文件路径名
            HSSFWorkbook book = new HSSFWorkbook();
            //设置表的名字（sheet）
            HSSFSheet sheet = book.createSheet("表");
            try {
                //-------连接数据库--------
                Connection con;
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dname + "?serverTimezone=GMT", "root", dPassword);
                Statement st = con.createStatement();
                String sql = "select * from " + tname;
                ResultSet resultSet = st.executeQuery(sql);
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();//得到结果集的字段名
                int count = resultSetMetaData.getColumnCount();//得到字段名的数量
                //创建表单第一行，表头
                HSSFRow row0 = sheet.createRow(0);
                for (int i = 0; i < count; i++) {
                    HSSFCell cel = row0.createCell(i);//创建第一行的第i列
                    cel.setCellValue(resultSetMetaData.getColumnName(i + 1));//设置第一行列名
                }
                int r = 1;
                while (resultSet.next()) {
                    HSSFRow row = sheet.createRow(r++);
                    for (int i = 0; i < count; i++) {
                        HSSFCell cel = row.createCell(i);
                        cel.setCellValue(resultSet.getString(i + 1));//将当前行的指定列的值存入
                    }
                }
                FileOutputStream out = new FileOutputStream(file.getAbsolutePath());
                Platform.runLater(() -> {
                    label_Status.setText("当前状态：保存成功 文件路径：" + file.getAbsolutePath());
                });
                book.write(out);
                out.close();
            } catch (Exception e) {
                Platform.runLater(() -> {
                    label_Status.setText("当前状态：保存失败");
                });
                e.printStackTrace();
            }
        }
    }

    //  读取文件
    public void ReadExcel(String dname, String tname, String dPassword) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开文件");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                Connection con;
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dname + "?serverTimezone=GMT", "root", dPassword);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                String add_sql = "select * from " + tname;
                String delete_sql = "delete from " + tname;
                st.executeUpdate(delete_sql);
                ResultSet resultSet = st.executeQuery(add_sql);
                HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(file.getAbsolutePath()));
                HSSFSheet sheet = book.getSheet("表");
                HSSFRow row;
                Iterator<Cell> ite;
                Iterator<Row> it = sheet.rowIterator();
                it.next();
                while (it.hasNext()) {
                    row = (HSSFRow) it.next();
                    ite = row.cellIterator();
                    int j = 1;
                    resultSet.moveToInsertRow();
                    while (ite.hasNext()) {
                        HSSFCell cell = (HSSFCell) ite.next();
                        cell.setCellType(CellType.STRING);
                        String cname = cell.getStringCellValue();
                        resultSet.updateString(j, cname);
                        j++;
                    }
                    resultSet.insertRow();
                }
                Platform.runLater(() -> {
                    label_Status.setText("当前状态：读取成功 文件路径：" + file.getAbsolutePath());
                });
                book.close();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    label_Status.setText("当前状态：读取失败");
                });
            }
        }
    }

    //    显示时间
    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTime.setText("当前时间: " + LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    //    删除行
    public void toDelete(ActionEvent actionEvent) {
        Object selectedItem = tblMainList.getSelectionModel().getSelectedItem();
        tblMainList.getItems().remove(selectedItem);
        Platform.runLater(() -> {
            label_Status.setText("当前状态：删除成功 点击[保留修改]按钮即可保留修改的数据");
        });
    }

    //    保留修改的数据
    public void toEdit(ActionEvent actionEvent) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.titleProperty().set("提示");
        alert.headerTextProperty().set("确认修改？");
//            对用户选择做出反应动作
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Connection con;
                    String tname = "person";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exp5?serverTimezone=GMT", "root", password);
                    Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    String add_sql = "select * from " + tname;
                    String delete_sql = "delete from " + tname;
                    st.executeUpdate(delete_sql);//清空当前数据库信息
                    ResultSet resultSet = st.executeQuery(add_sql);
                    for (int i = 0; i < tblMainList.getItems().size(); i++) {
                        resultSet.moveToInsertRow();
                        for (int j = 0; j < tblMainList.getColumns().size(); j++) {
                            String s = tblMainList.getColumns().get(j).getCellData(i).toString();
                            System.out.print(s + ",");
                            resultSet.updateString(j + 1, s);
                        }
                        System.out.println();
                        resultSet.insertRow();
                    }
                    Platform.runLater(() -> {
                        label_Status.setText("当前状态：修改成功");
                    });
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.titleProperty().set("提示");
                    alert1.headerTextProperty().set("修改成功！");
                    alert1.showAndWait();
                } catch (SQLException throwables) {
                    Platform.runLater(() -> {
                        label_Status.setText("当前状态：修改失败");
                    });
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.titleProperty().set("错误");
                    alert1.headerTextProperty().set("修改失败，请检查后重试！");
                    alert1.showAndWait();
                    throwables.printStackTrace();
                }
            }
        });
    }

//    激活删除按钮
    public void toActiveButton_Delete(MouseEvent mouseEvent) {
        button_Delete.setDisable(false);
    }

//    Tableview编辑修改
    public void toEditNum(TableColumn.CellEditEvent<Person, String> cellEditEvent) {
        (cellEditEvent.getTableView().getItems().get(cellEditEvent.getTablePosition().getRow())).setId(cellEditEvent.getNewValue());
    }

    public void toEditName(TableColumn.CellEditEvent<Person, String> cellEditEvent) {
        (cellEditEvent.getTableView().getItems().get(cellEditEvent.getTablePosition().getRow())).setName(cellEditEvent.getNewValue());
    }

    public void toEditSex(TableColumn.CellEditEvent<Person, String> cellEditEvent) {
        (cellEditEvent.getTableView().getItems().get(cellEditEvent.getTablePosition().getRow())).setSex(cellEditEvent.getNewValue());
    }

    public void toEditAge(TableColumn.CellEditEvent<Person, Integer> cellEditEvent) {
        (cellEditEvent.getTableView().getItems().get(cellEditEvent.getTablePosition().getRow())).setAge(cellEditEvent.getNewValue());
    }

    public void toEditClass(TableColumn.CellEditEvent<Person, String> cellEditEvent) {
        (cellEditEvent.getTableView().getItems().get(cellEditEvent.getTablePosition().getRow())).setDepartment(cellEditEvent.getNewValue());
    }

    public void toEditWConsume(TableColumn.CellEditEvent<Person, Double> cellEditEvent) {
        (cellEditEvent.getTableView().getItems().get(cellEditEvent.getTablePosition().getRow())).setWaterConsumption(cellEditEvent.getNewValue());
    }

    public void toEditEConsume(TableColumn.CellEditEvent<Person, Double> cellEditEvent) {
        (cellEditEvent.getTableView().getItems().get(cellEditEvent.getTablePosition().getRow())).setElectricityConsumption(cellEditEvent.getNewValue());

    }

    public void toEditWCost(TableColumn.CellEditEvent<Person, Double> cellEditEvent) {
        (cellEditEvent.getTableView().getItems().get(cellEditEvent.getTablePosition().getRow())).setWaterCost(cellEditEvent.getNewValue());
    }

    public void toEditECost(TableColumn.CellEditEvent<Person, Double> cellEditEvent) {
        (cellEditEvent.getTableView().getItems().get(cellEditEvent.getTablePosition().getRow())).setElectricityCost(cellEditEvent.getNewValue());
    }

    public void toEditIsPay(TableColumn.CellEditEvent<Person, String> cellEditEvent) {
        (cellEditEvent.getTableView().getItems().get(cellEditEvent.getTablePosition().getRow())).setIsPay(cellEditEvent.getNewValue());
    }

//    统计
    public void toStatistic(MouseEvent mouseEvent) {
        int count = 0;
        for (int i = 0; i < tblMainList.getItems().size(); i++) {
            if (tblMainList.getColumns().get(9).getCellData(i) == null)
                continue;
            String s = tblMainList.getColumns().get(9).getCellData(i).toString();
            if (s.equals("否"))
                count++;
        }
        int finalCount = count;
        Platform.runLater(() -> {
            label_Status.setText("统计结果：目前有" + finalCount + "人未缴纳水电费");
        });
    }
}