package tropico.Controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import tropico.Model.*;
import tropico.view.StageEnum;
import tropico.view.StageManagement;

import java.io.IOException;
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

    @FXML
    private VBox playMenu;

    @FXML
    private MenuButton difficulty;

    @FXML
    private HBox setting;

    @FXML
    private TextField nameSetting;

    @FXML
    private HBox faction;

    @FXML
    private TextField nameFaction;

    @FXML
    private MenuButton mode;

    @FXML
    private HBox eventBox;

    @FXML
    private TextField nameEvent;

    @FXML
    private Label errorSetting;

    @FXML
    private Label errorFaction;

    @FXML
    private Label errorEvent;

    private MediaPlayer mediaPlayer;

    private final LinkedList<Node> menuList = new LinkedList<>();

    private Timeline timeline;

    public void initialize() {
        setMedia("menu.mp4");
        initializeTimer();
        initializeBlinking();
        initializeSettingManagement();
    }

    private void initializeSettingManagement() {
        SettingManagement.setDifficulty(difficulty.getItems().get(0));
        SettingManagement.setMode(mode.getItems().get(0));
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

    private void setMedia(String name) {
        URL musicURL = getClass().getResource(name);
        Media media = new Media(musicURL.toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        setAutoReplay(mediaPlayer);
        mediaPlayer.play();
    }

    private void setAutoReplay(MediaPlayer mediaPlayer) {
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.seconds(5.2)));
    }

    @FXML
    public void openMenu() {
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
    public void previousMenu(){
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
    void leave() {
        Platform.exit();
    }

    @FXML
    void openPlayMenu(){
        openMenu(playMenu);
    }

    @FXML
    void openOptionMenu(){
        openMenu(optionMenu);
    }

    @FXML
    void openCredits(){
        openMenu(creditsMenu);
    }

    private void openMenu(Node menu){
        menuList.getLast().setVisible(false);
        menuList.add(menu);
        menu.setVisible(true);
        title.setVisible(false);
    }

    @FXML
    void muteSounds(){
        SoundManagement.switchSoundOn();
        String suffix = (SoundManagement.isSoundOn())? "On": "Off";
        imageSound.setImage(ImageManagement.createImage("sound" + suffix + ".png"));
        unicornSound.setImage(ImageManagement.createImage("unicorn" + suffix + ".png"));
        mediaPlayer.setMute(!mediaPlayer.isMute());
    }

    @FXML
    void basicDifficulty(ActionEvent event){
        setVisibleAdvancedDifficultySetting(false);
        manageMenuDifficulty((MenuItem)event.getSource());
    }

    private void manageMenuDifficulty(MenuItem source){
        difficulty.setText(source.getText());
        source.setDisable(true);
        SettingManagement.getDifficulty().setDisable(false);
        SettingManagement.setDifficulty(source);
    }

    private void manageMenuMode(MenuItem source){
        mode.setText(source.getText());
        source.setDisable(true);
        SettingManagement.getMode().setDisable(false);
        SettingManagement.setMode(source);
    }

    private void setVisibleAdvancedDifficultySetting(boolean bool){
        setting.setVisible(bool);
        faction.setVisible(bool);
    }

    private void setVisibleAdvancedModeSetting(boolean bool){
        eventBox.setVisible(bool);
    }

    @FXML
    void basicMode(ActionEvent event){
        setVisibleAdvancedModeSetting(false);
        manageMenuMode((MenuItem)event.getSource());
    }

    @FXML
    void personalizedDifficulty(ActionEvent event){
        setVisibleAdvancedDifficultySetting(true);
        manageMenuDifficulty((MenuItem)event.getSource());
    }

    @FXML
    void personalizedMode(ActionEvent event){
        setVisibleAdvancedModeSetting(true);
        manageMenuMode((MenuItem)event.getSource());
    }

    @FXML
    void startGame() throws IOException {
        setErrorVisibleFalse();
        int errorNumber = setPathForSetting();
        if(errorNumber == 1){
            errorSetting.setVisible(true);
            return;
        }
        if(errorNumber == 2){
            errorFaction.setVisible(true);
            return;
        }
        if(setPathForMode() == 1){
            errorEvent.setVisible(true);
            return;
        }
        mediaPlayer.dispose();
        setMedia("music.mp4");
        StageManagement.setScene(StageEnum.GAME);
    }

    private void setErrorVisibleFalse() {
        errorEvent.setVisible(false);
        errorFaction.setVisible(false);
        errorSetting.setVisible(false);
    }

    private int setPathForSetting() {
        Difficulty difficulty = SettingManagement.getDifficultyFromMenuItem();
        if(difficulty.equals(Difficulty.PERSONALIZED)){
            JSonPathManagement.setJsonSetting(JSonPathManagement.createPath(nameSetting.getText(), "setting"));
            JSonPathManagement.setJsonFaction(JSonPathManagement.createPath(nameFaction.getText(), "faction"));
            return JSonPathManagement.verifyDifficultyPath();
        }
        JSonPathManagement.setJsonSetting(JSonPathManagement.createPath(difficulty.getJsonSetting(), "setting"));
        JSonPathManagement.setJsonFaction(JSonPathManagement.createPath(difficulty.getJsonFaction(), "faction"));
        return 0;
    }

    private int setPathForMode(){
        Mode mode = SettingManagement.getModeFromMenuItem();
        if(mode.equals(Mode.PERSONALIZED)){
            JSonPathManagement.setJsonEvent(JSonPathManagement.createPath(nameEvent.getText(), "event"));
            return JSonPathManagement.verifyModePath();
        }
        JSonPathManagement.setJsonEvent(JSonPathManagement.createPath(mode.getJsonName(), "event"));
        return 0;
    }
}

