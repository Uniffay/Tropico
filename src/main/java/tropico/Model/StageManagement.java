package tropico.Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tropico.Object.Data;

import java.io.IOException;
import java.util.Objects;

public class StageManagement {

    private static Stage STAGE_SAVE;

    public static Stage getStage(){
        return STAGE_SAVE;
    }

    public static void setStage(Stage stage){
        STAGE_SAVE = Objects.requireNonNull(stage);
    }

    public static void setScene(FXMLLoader loader) throws IOException {
        AnchorPane gameViewFactionLess = loader.load();
        Scene scene = new Scene(gameViewFactionLess);
        STAGE_SAVE.setScene(scene);
    }
}
