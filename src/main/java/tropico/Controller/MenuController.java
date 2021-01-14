package tropico.Controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import tropico.Model.*;
import tropico.Object.Data;
import tropico.Object.FactionsList;
import tropico.view.StageEnum;
import tropico.view.StageManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * control the action the user made in the menu
 * @author Quentin & Cléis
 */
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

    @FXML
    private AnchorPane playerMenu;

    @FXML
    private TextField player1;

    /**
     * save name and number of player
     */
    private final ArrayList<TextField> players = new ArrayList<>();

    /**
     * stock menu that already appeared or is being used
     */
    private final LinkedList<Node> menuList = new LinkedList<>();

    /**
     * time line used to show the title and blinking start game not directly
     */
    private Timeline timeline;

    /**
     * initialize menu of the game
     */
    public void initialize() {
        setMedia("menu.mp4");
        initializeTimer();
        initializeBlinking();
        initializeSettingManagement();
        players.add(player1);
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
        MediaManagement.setMedia(name);
        MediaPlayer mediaPlayer = MediaManagement.getMedia();
        mediaView.setMediaPlayer(mediaPlayer);
        MediaManagement.setAutoReplay(5.2);
        mediaPlayer.play();
    }

    @FXML
    private void openMenu() {
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
    private void previousMenu(){
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
    private void enteredOption(MouseEvent event) {
        ((Label)event.getSource()).setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @FXML
    private void exitedOption(MouseEvent event) {
        ((Label)event.getSource()).setBackground(new Background(new BackgroundFill(Color.HOTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @FXML
    private void leave() {
        Platform.exit();
    }

    @FXML
    private void openPlayMenu(){
        openMenu(playMenu);
    }

    @FXML
    private void openOptionMenu(){
        openMenu(optionMenu);
    }

    @FXML
    private void openCredits(){
        openMenu(creditsMenu);
    }

    private void openMenu(Node menu){
        menuList.getLast().setVisible(false);
        menuList.add(menu);
        menu.setVisible(true);
        title.setVisible(false);
    }

    @FXML
    private void muteSounds(){
        SoundManagement.switchSoundOn();
        String suffix = (SoundManagement.isSoundOn())? "On": "Off";
        imageSound.setImage(ImageManagement.createImage("sound" + suffix + ".png"));
        unicornSound.setImage(ImageManagement.createImage("unicorn" + suffix + ".png"));
    }

    @FXML
    private void basicDifficulty(ActionEvent event){
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
    private void basicMode(ActionEvent event){
        setVisibleAdvancedModeSetting(false);
        manageMenuMode((MenuItem)event.getSource());
    }

    @FXML
    private void personalizedDifficulty(ActionEvent event){
        setVisibleAdvancedDifficultySetting(true);
        manageMenuDifficulty((MenuItem)event.getSource());
    }

    @FXML
    private void personalizedMode(ActionEvent event){
        setVisibleAdvancedModeSetting(true);
        manageMenuMode((MenuItem)event.getSource());
    }

    @FXML
    private void startGame() throws IOException {
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
        mediaGameStart();
        List<String> names = new ArrayList<>();
        DataManagement.initializeData(initializeListName());
        StageManagement.setScene(StageEnum.GAME);
    }

    private List<String> initializeListName() {
        List<String> names = new ArrayList<>();
        int i = 0;
        for (TextField t: players){
            names.add((t.getText().length() > 0)? t.getText(): "Player" + (i + 1));
            i++;
        }
        return names;
    }

    private void mediaGameStart(){
        MediaManagement.dispose();
        setMedia("music.mp4");
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

    @FXML
    private void load() throws IOException, ClassNotFoundException {
        DataManagement.setData(Data.recuperation());
        StageManagement.setScene(StageEnum.GAME);
        mediaGameStart();
        FactionsList.initializeFactionName();
    }

    @FXML
    private void addPlayer(){
        if(players.size() > 8)
            return;
        TextField textField = new TextField();
        players.add(textField);
        textField.setPromptText("Player" + (players.size()));
        textField.setAlignment(Pos.CENTER);
        textField.setOnKeyPressed(this::verifyKey);
        putTextField(textField);
    }

    private void putTextField(TextField t) {
        AnchorPane.setTopAnchor(t, 97.0 + 30 * players.size());
        AnchorPane.setLeftAnchor(t, 227d);
        playerMenu.getChildren().add(t);
    }

    @FXML
    private void removePlayer(){
        if(players.size() == 1){
            return;
        }
        playerMenu.getChildren().remove(players.get(players.size() - 1));
        players.remove(players.size() - 1);
    }

    @FXML
    private void switchPlayerState(){
        playerMenu.setVisible(!playerMenu.isVisible());
        playMenu.setVisible(!playMenu.isVisible());
        backArrow.setVisible(!backArrow.isVisible());
    }

    @FXML
    private void verifyKey(KeyEvent event){
        TextField source = (TextField)event.getSource();
        if(source.getText().length() > 20){
            source.setText(source.getText().substring(0, 20));
        }
    }
}

