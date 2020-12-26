package tropico.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tropico.Controller.Controller;
import tropico.Model.DataManagement;
import tropico.Model.StageManagement;
import tropico.Object.Data;

import java.io.IOException;
import java.net.URL;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Data gameData = new Data(1, new String[]{"Player1"}, "json/setting/setting.json", "json/faction/faction.json", "json/event/Event.json");
        DataManagement.setData(gameData);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameView.fxml"));
            StageManagement.setStage(stage);
            StageManagement.setScene(loader);
            stage.show();
            stage.setResizable(false);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}