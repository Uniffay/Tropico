package tropico.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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


    public static void setScene(StageEnum stageEnum) throws IOException {
        FXMLLoader loader = new FXMLLoader(StageManagement.class.getResource(stageEnum.getName()));
        AnchorPane gameViewFactionLess = loader.load();
        Scene scene = new Scene(gameViewFactionLess);
        STAGE_SAVE.setScene(scene);
    }
}
