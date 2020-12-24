package MainInterface.SearchInfo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchInfo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SearchInfo.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("查找");
        primaryStage.getIcons().add(new Image("file:src/res/login_logo.png"));
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
