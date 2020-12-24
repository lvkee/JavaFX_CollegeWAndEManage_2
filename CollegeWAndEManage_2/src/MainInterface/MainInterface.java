package MainInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainInterface extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainInterface.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("高校水电费管理系统 作者：LIU.W.Q");
        primaryStage.getIcons().add(new Image("file:src/res/login_logo.png"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
