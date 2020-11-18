package tropico.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tropico.Controller.Controller;
import tropico.Object.Data;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Data gameData = new Data(1, new String[]{"Player1"}, "json/setting/setting.json", "json/faction/faction.json", "json/event/Event.json");
        FXMLLoader loader = loadFXML("gameView");
        System.out.println(loader);
        Controller c = loader.getController();
        System.out.println(c);
        scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml).load());
    }

    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch(args);
    }

}