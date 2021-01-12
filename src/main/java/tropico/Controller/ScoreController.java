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

public class ScoreController {

    @FXML
    private MediaView mediaView;

    @FXML
    private ImageView image;

    @FXML
    private AnchorPane score;

    @FXML
    private Text scoreText;

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
    void endVideo(){
        MediaManagement.dispose();
        MediaManagement.setMedia("lost.mp4");
        MediaManagement.play();
        MediaManagement.setAutoReplay(0);
        image.setVisible(true);
    }

    @FXML
    void showScore(){
        score.setVisible(true);
        scoreText.setText(DataManagement.getData().getStringScore());
    }

    @FXML
    void backToMenu() throws IOException {
        StageManagement.setScene(StageEnum.MENU);
    }
}
