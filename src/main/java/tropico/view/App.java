package tropico.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tropico.Controller.Controller;
import tropico.Model.DataManagement;
import tropico.Object.Data;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        Data gameData = new Data(1, new String[]{"Player1"}, "json/setting/setting.json", "json/faction/faction.json", "json/event/Event.json");
        DataManagement.setData(gameData);
        try {
            primaryStage = stage;
            AnchorPane root = FXMLLoader.load(getClass().getResource("gameView.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}