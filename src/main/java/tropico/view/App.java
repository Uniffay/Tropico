package tropico.view;

import javafx.application.Application;
import javafx.stage.Stage;
import tropico.Model.DataManagement;
import tropico.Object.Data;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Data gameData = new Data(1, new String[]{"Player1"}, "json/setting/setting.json", "json/faction/faction.json", "json/event/Event.json");
        DataManagement.setData(gameData);
        try {
            StageManagement.setStage(stage);
            StageManagement.setScene(StageEnum.MENU);
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