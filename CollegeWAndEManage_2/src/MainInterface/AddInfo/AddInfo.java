package MainInterface.AddInfo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AddInfo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddInfo.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("添加用户");
        primaryStage.getIcons().add(new Image("file:src/res/login_logo.png"));
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
