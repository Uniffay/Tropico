package tropico.Controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import tropico.Model.ImageManagement;
import tropico.Model.SoundManagement;

import java.net.URL;
import java.util.LinkedList;

public class MenuController {

    @FXML
    private MediaView mediaView;

    @FXML
    private VBox firstOption;

    @FXML
    private Label title;

    @FXML
    private Label clickLabel;

    @FXML
    private Polygon backArrow;

    @FXML
    private VBox creditsMenu;

    @FXML
    private ImageView imageSound;

    @FXML
    private ImageView unicornSound;

    @FXML
    private VBox optionMenu;

    private MediaPlayer mediaPlayer;

    private LinkedList<Node> menuList = new LinkedList<>();

    private Timeline timeline;

    public void initialize() {
        initializeMedia();
        initializeTimer();
        initializeBlinking();
    }

    private void initializeTimer() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> title.setVisible(true)),
                new KeyFrame(Duration.seconds(5), e -> clickLabel.setVisible(true))
        );
        timeline.play();
    }

    private void initializeBlinking() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), clickLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
    }

    private void initializeMedia() {
        URL musicURL = getClass().getResource("menu.mp4");
        Media media = new Media(musicURL.toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        setAutoReplay(mediaPlayer);
        mediaPlayer.play();
    }

    private void setAutoReplay(MediaPlayer mediaPlayer) {
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.seconds(5.2));
            }
        });
    }

    @FXML
    public void openMenu(MouseEvent event) {
        if (menuList.isEmpty()) {
            timeline.stop();
            title.setVisible(true);
            firstOption.setVisible(true);
            clickLabel.setVisible(false);
            backArrow.setVisible(true);
            menuList.add(firstOption);
        }
    }

    @FXML
    public void previousMenu(MouseEvent event){
        menuList.getLast().setVisible(false);
        menuList.pollLast();
        if(menuList.isEmpty()){
            clickLabel.setVisible(true);
            backArrow.setVisible(false);
            return;
        }
        menuList.getLast().setVisible(true);
        title.setVisible(true);
    }

    @FXML
    void enteredOption(MouseEvent event) {
        ((Label)event.getSource()).setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @FXML
    void exitedOption(MouseEvent event) {
        ((Label)event.getSource()).setBackground(new Background(new BackgroundFill(Color.HOTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @FXML
    void leave(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void playMenu(MouseEvent event){
    }

    @FXML
    void optionMenu(MouseEvent event){
        openMenu(optionMenu);
        title.setVisible(false);
    }

    @FXML
    void openCredits(MouseEvent event){
        openMenu(creditsMenu);
        title.setVisible(false);
    }

    private void openMenu(Node menu){
        menuList.getLast().setVisible(false);
        menuList.add(menu);
        menu.setVisible(true);
    }

    @FXML
    void muteSounds(MouseEvent event){
        SoundManagement.switchSoundOn();
        String suffix = (SoundManagement.isSoundOn())? "On": "Off";
        imageSound.setImage(ImageManagement.createImage("sound" + suffix + ".png"));
        unicornSound.setImage(ImageManagement.createImage("unicorn" + suffix + ".png"));
        mediaPlayer.setMute(!mediaPlayer.isMute());
    }
}

