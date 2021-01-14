package tropico.Controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import tropico.Model.DataManagement;
import tropico.Model.MediaManagement;
import tropico.view.StageEnum;
import tropico.view.StageManagement;

import java.io.IOException;

/**
 * control the action the user made in the score menu
 * @author Quentin & Cléis
 */
public class ScoreController {

    @FXML
    private MediaView mediaView;

    @FXML
    private ImageView image;

    @FXML
    private AnchorPane score;

    @FXML
    private Text scoreText;

    /**
     * initialize score menu
     */
    public void initialize(){
        initializeMedia();
    }

    private void initializeMedia() {
        MediaManagement.dispose();
        MediaManagement.setMedia("end.mp4");
        mediaView.setMediaPlayer(MediaManagement.getMedia());
        MediaManagement.play();
        MediaManagement.getMedia().setOnEndOfMedia(this::endVideo);
    }

    @FXML
    private void endVideo(){
        MediaManagement.dispose();
        MediaManagement.setMedia("lost.mp4");
        MediaManagement.play();
        MediaManagement.setAutoReplay(0);
        image.setVisible(true);
    }

    @FXML
    private void showScore(){
        score.setVisible(true);
        scoreText.setText(DataManagement.getData().getStringScore());
    }

    @FXML
    private void backToMenu() throws IOException {
        StageManagement.setScene(StageEnum.MENU);
    }
}
