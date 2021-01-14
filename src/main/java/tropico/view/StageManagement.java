package tropico.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * manage the stage
 * @author Cléis & Quentin
 */
public class StageManagement {

    /**
     * represent current stage show
     */
    private static Stage STAGE_SAVE;

    /**
     * get current stage
     * @return current stage
     */
    public static Stage getStage(){
        return STAGE_SAVE;
    }

    /**
     * set current stage
     * @param stage
     *      new stage
     */
    public static void setStage(Stage stage){
        STAGE_SAVE = Objects.requireNonNull(stage);
    }

    /**
     * change scene show
     * @param stageEnum
     *      new scene to show
     * @throws IOException if an I/O error occurs
     */
    public static void setScene(StageEnum stageEnum) throws IOException {
        FXMLLoader loader = new FXMLLoader(StageManagement.class.getResource(stageEnum.getName()));
        AnchorPane gameViewFactionLess = loader.load();
        Scene scene = new Scene(gameViewFactionLess);
        STAGE_SAVE.setScene(scene);
    }
}
