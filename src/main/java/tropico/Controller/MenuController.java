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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tropico.Model.*;
import tropico.Object.Data;
import tropico.Object.Event;
import tropico.Object.FactionsList;
import tropico.view.StageEnum;
import tropico.view.StageManagement;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;

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

    @FXML
    private HBox musicBox;

    @FXML
    private TextField music;

    @FXML
    private ImageView exitButton;

    @FXML
    private AnchorPane exitMenu;

    @FXML
    private AnchorPane tutorial;

    @FXML
    private Text tutorialText;

    @FXML
    private Polygon arrow1;

    @FXML
    private Label confirmButton;

    @FXML
    private Label managePlayerButton;

    @FXML
    private Polygon arrow2;

    @FXML
    private MenuItem easy;

    @FXML
    private MenuItem sandBox;

    @FXML
    private Label confirmButtonPlayer;

    /**
     * to run next step (all the step are recorded here in order of apparition)
     */
    private final ArrayList<Runnable> tutorialOrder = new ArrayList<>();
    /**
     * verification to go next step in the tutorial
     */
    private final ArrayList<Callable<Boolean>> tutorialVerification = new ArrayList<>();
    /**
     * current step in the tutorial
     */
    private int stepTutorial = 0;

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
        initializeTutorial();
        setMedia("menu.mp4");
        initializeTimer();
        initializeBlinking();
        initializeSettingManagement();
        players.add(player1);
    }

    private void initializeTutorial() {
        initializeTutorialOrder();
        initializeTutorialVerification();

    }

    private void initializeTutorialVerification() {
        tutorialVerification.add(()-> SettingManagement.getDifficultyFromMenuItem().equals(Difficulty.PERSONALIZED));
        tutorialVerification.add(()-> SettingManagement.getDifficultyFromMenuItem().equals(Difficulty.EASY));
        tutorialVerification.add(()-> SettingManagement.getModeFromMenuItem().equals(Mode.PERSONALIZED));
        tutorialVerification.add(()-> SettingManagement.getModeFromMenuItem().equals(Mode.SANDBOX));
        tutorialVerification.add(()-> playerMenu.isVisible());
        tutorialVerification.add(()->
                players.size() == 2 && players.stream().filter(textField -> textField.getText().length() > 0).count() == 2);
        tutorialVerification.add(()-> !playerMenu.isVisible());
    }

    private void initializeTutorialOrder() {
        tutorialOrder.add(this::first);
        tutorialOrder.add(this::second);
        tutorialOrder.add(this::third);
        tutorialOrder.add(this::fourth);
        tutorialOrder.add(this::fifth);
        tutorialOrder.add(this::sixth);
        tutorialOrder.add(this::seventh);
        tutorialOrder.add(this::eighth);
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
        SoundManagement.switchSound();
        String suffix = (SoundManagement.isSoundOff())? "Off": "On";
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
        musicBox.setVisible(bool);
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
        DataManagement.initializeData(initializeListName());
        if(stepTutorial > 0){
            DataManagement.getData().setGameTutorial(true);
        }
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
        setMediaFromMode(SettingManagement.getModeFromMenuItem());
    }

    private void setMediaFromMode(Mode modeFromMenuItem) {
        if(modeFromMenuItem != Mode.PERSONALIZED){
            setMedia(modeFromMenuItem.getMusic());
            return;
        }
        setMedia(music.getText());
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
        Data gameData = Data.recuperation();
        if(Objects.isNull(gameData))
            return;
        DataManagement.setData(gameData);
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
        if(stepTutorial > 0)
            return;
        backArrow.setVisible(!backArrow.isVisible());
    }

    @FXML
    private void verifyKey(KeyEvent event){
        TextField source = (TextField)event.getSource();
        if(source.getText().length() > 20){
            source.setText(source.getText().substring(0, 20));
        }
    }

    @FXML
    private void openTutorial(){
        menuList.getLast().setVisible(false);
        backArrow.setVisible(false);
        openPlayMenuTutorial();
        exitButton.setVisible(true);
        tutorialOrder.get(0).run();
        setNodeDisable(true);
        reinitializeSettings();
    }

    private void openPlayMenuTutorial() {
        openPlayMenu();
        tutorial.setVisible(true);
        arrow1.setVisible(true);
    }

    @FXML
    private void openExitMenu(){
        exitMenu.setVisible(true);
    }

    @FXML
    private void yesLeave(){
        menuList.getLast().setVisible(false);
        menuList.removeLast();
        menuList.getLast().setVisible(true);
        setAllVisibleTutorialFalse();
        setNodeDisable(false);
        difficulty.setDisable(false);
        title.setVisible(true);
        backArrow.setVisible(true);
        players.forEach(textField -> textField.setDisable(false));
        reinitializeSettings();
    }

    private void initializeMenuItem(MenuButton menu, MenuItem item){
        menu.getItems().forEach(menuItem -> menuItem.setDisable(false));
        item.setDisable(true);
    }

    private void reinitializeSettings() {
        SettingManagement.setDifficulty(easy);
        difficulty.setText("Facile");
        initializeMenuItem(difficulty, easy);
        setVisibleAdvancedDifficultySetting(false);
        mode.setText("Bac à Sable");
        initializeMenuItem(mode, sandBox);
        setVisibleAdvancedModeSetting(false);
        SettingManagement.setMode(sandBox);
        stepTutorial = 0;
    }

    private void setAllVisibleTutorialFalse() {
        arrow2.setVisible(false);
        arrow1.setVisible(false);
        tutorial.setVisible(false);
        playerMenu.setVisible(false);
        exitMenu.setVisible(false);
        exitButton.setVisible(false);
    }

    private void setNodeDisable(boolean bool) {
        mode.setDisable(bool);
        managePlayerButton.setDisable(bool);
        confirmButton.setDisable(bool);
        confirmButtonPlayer.setDisable(bool);
    }

    @FXML
    private void noLeave(){
        exitMenu.setVisible(false);
    }

    @FXML
    private void next() throws Exception {
        if(tutorialVerification.get(stepTutorial).call()){
            stepTutorial++;
            tutorialOrder.get(stepTutorial).run();
        }
    }

    private void initializePosition() {
        arrow1.setLayoutX(662);
        arrow1.setLayoutY(208);
        arrow2.setLayoutX(809);
        arrow2.setLayoutY(330);
        tutorial.setLayoutX(711);
        tutorial.setLayoutY(94);
    }

    private void first(){
        tutorialText.setText("Vous pouvez choisir la difficulté ici ! (choisissez personnalisé et cliquez sur \"OK\")");
        initializePosition();
    }

    private void second(){
        tutorialText.setFont(Font.font(18));
        tutorialText.setText("Quand vous choisissez la difficulté personnalisée, vous devez mettre" +
                            "les fichiers json que vous avez dans votre dossier du jeu ! Pour le moment, " +
                            "choisissez la difficulté facile !");
        tutorial.setLayoutY(343);
        arrow1.setLayoutX(803);
        arrow1.setLayoutY(270);
        arrow2.setVisible(true);
    }

    private void third(){
        difficulty.setDisable(true);
        mode.setDisable(false);
        tutorialText.setText("Vous pouvez aussi choisir le mode de jeux. Le mode change les évènements que vous" +
                            " rencontrerez au cours de la partie ainsi que la musique ! (choisissez personnalisé et" +
                            " cliquez sur \"OK\"");
        tutorial.setLayoutX(714);
        tutorial.setLayoutY(211);
        arrow1.setLayoutX(698);
        arrow1.setLayoutY(389);
        arrow2.setVisible(false);
    }

    private void fourth(){
        tutorialText.setText("Quand vous choisissez le mode personalisé, vous devez choisir le json contenant les" +
                            " évènements que vous souhaitez ainsi que la musique. Vous avez juste à mettre leur nom " +
                            " et les ranger au bonne endroit dans le dossier jeu! (choisissez le mode Bac à sable pour continuer)");
        tutorial.setLayoutY(147);
        arrow1.setLayoutX(834);
        arrow1.setLayoutY(445);
        arrow2.setVisible(true);
        arrow2.setLayoutX(851);
        arrow2.setLayoutY(504);
    }

    private void fifth(){
        tutorialText.setText("Vous pouvez changer le nombre des joueurs ainsi que leur nom en cliquant sur" +
                            " \"Gérer les Joueurs\" (Cliquez dessus et cliquez sur \"OK\")");
        tutorial.setLayoutX(682);
        tutorial.setLayoutY(310);
        arrow1.setLayoutX(518);
        arrow1.setLayoutY(647);
        arrow2.setVisible(false);
        mode.setDisable(true);
        managePlayerButton.setDisable(false);
    }

    private void sixth(){
        tutorialText.setText("Dans ce menu, vous pouvez ajoutez des joueurs en appuyant sur le bouton plus ou/et" +
                            " changer le nom de chaque joueur dans les zones de texte. Ajoutez un joueur et" +
                            " renommez les !");
        tutorial.setLayoutY(258);
        arrow1.setLayoutX(611);
        arrow1.setLayoutY(237);
        arrow2.setVisible(true);
        arrow2.setLayoutX(699);
        arrow2.setLayoutY(561);
        confirmButtonPlayer.setDisable(true);
    }

    private void seventh(){
        tutorialText.setText("Vous pouvez maintenant confirmer ! (cliquez ensuite sur \"OK\")");
        confirmButtonPlayer.setDisable(false);
        players.forEach(textField -> textField.setDisable(true));
        arrow1.setLayoutX(476);
        arrow1.setLayoutY(570);
        arrow2.setVisible(false);
        managePlayerButton.setDisable(true);
    }

    private void eighth(){
        tutorialText.setText("Vous pouvez désormais lancez la game !");
        confirmButton.setDisable(false);
        arrow1.setLayoutX(810);
        arrow1.setLayoutY(642);
    }
}

