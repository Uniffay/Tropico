package tropico.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import tropico.Model.DataManagement;
import tropico.Model.StageManagement;
import tropico.Model.ViewManagement;
import tropico.Object.Choice;
import tropico.Object.Data;
import tropico.Object.Faction;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private Label money;

    @FXML
    private Label industry;

    @FXML
    private Label farming;

    @FXML
    private Label capitalistPartisan;

    @FXML
    private Label capitalistFulfillment;

    @FXML
    private Label communistPartisan;

    @FXML
    private Label communistFulfillment;

    @FXML
    private Label liberalPartisan;

    @FXML
    private Label liberalFulfillment;

    @FXML
    private Label religiousPartisan;

    @FXML
    private Label religiousFulfillment;

    @FXML
    private Label militaristPartisan;

    @FXML
    private Label militaristFulfillment;

    @FXML
    private Label ecologistPartisan;

    @FXML
    private Label ecologistFulfillment;

    @FXML
    private Label nationalistPartisan;

    @FXML
    private Label nationalistFulfillment;

    @FXML
    private Label loyalistPartisan;

    @FXML
    private Label loyalistFulfillment;

    @FXML
    private Label eventLabel;

    @FXML
    private Label choice1;

    @FXML
    private Label choice2;

    @FXML
    private Label choice3;

    @FXML
    private Label choice4;

    @FXML
    private Label date;

    @FXML
    private Label season;

    @FXML
    private ImageView background;

    @FXML
    private Label next;

    @FXML
    private Label effect;

    @FXML
    private ScrollPane scrollPaneLabel;

    @FXML
    private ScrollPane factionScrollPane;

    @FXML
    private AnchorPane eventPane;

    @FXML
    private Arc buttonFaction;

    @FXML
    private Polygon arrowAddFaction;

    @FXML
    private Arc buttonEvent;

    @FXML
    private Polygon arrowAddEvent;

    @FXML
    private AnchorPane results;

    @FXML
    private Label resultLabel;


    public void initialize() {
        Data gameData = DataManagement.getData();
        int turn = gameData.getTurn();
        date.setText("24/" + getMonth(turn) + "/"+ String.valueOf(turn / 4 + 2015));
        season.setText(gameData.getSeason().getName());
        money.setText(String.valueOf(gameData.getPlayerPlaying().getResource().get("money")));
        farming.setText(String.valueOf(gameData.getPlayerPlaying().getResource().get("farming") + "%"));
        industry.setText(String.valueOf(gameData.getPlayerPlaying().getResource().get("industry") + "%"));
        setBackground(gameData);
        initialize_event(gameData);
        initialize_factionLabel(gameData);
    }

    private void initialize_event(Data gameData) {
        eventLabel.setText(gameData.getEventChosen().getLabel());
        ArrayList<Label> Choices = initialize_choicesManagement();
        for (int i = 0; i < 4; i++){
            if(i >= gameData.getEventChosen().getChoices().size()){
                Choices.get(i).setVisible(false);
            }
            else{
                Choices.get(i).setVisible(true);
                Choices.get(i).setText(gameData.getEventChosen().getChoices().get(i).getLabel());
            }
        }
    }

    private ArrayList<Label> initialize_choicesManagement() {
        return new ArrayList<>(List.of(
                choice1,
                choice2,
                choice3,
                choice4
        ));
    }

    private String getMonth(int turn) {
        String month = String.valueOf(3 + turn % 4 * 3);
        month = (month.length() < 2)? "0" + month : month;
        return month;
    }

    private void initialize_factionLabel(Data gameData) {
        ArrayList<Label> LabelManagement = initialize_labelManagement();
        int i = 0;
        for (Faction faction: gameData.getPlayerPlaying().getFactions().getFactions()){
            LabelManagement.get(i).setText(String.valueOf(faction.getPartisan()));
            LabelManagement.get(i + 1).setText(String.valueOf(faction.getFulfillment()));
            i += 2;
        }
    }

    private ArrayList<Label> initialize_labelManagement() {
        return  new ArrayList<>(List.of(
                capitalistPartisan,
                capitalistFulfillment,
                communistPartisan,
                communistFulfillment,
                liberalPartisan,
                liberalFulfillment,
                religiousPartisan,
                religiousFulfillment,
                militaristPartisan,
                militaristFulfillment,
                ecologistPartisan,
                ecologistFulfillment,
                nationalistPartisan,
                nationalistFulfillment,
                loyalistPartisan,
                loyalistFulfillment
        ));
    }

    @FXML
    void choice1Handle(MouseEvent event) {
        Data gameData = DataManagement.getData();
        gameData.getPlayerPlaying().haveChosen(gameData.getEventChosen().getChoices().get(0));

        showEffect(gameData.getEventChosen().getChoices().get(0));

    }

    @FXML
    void choice2Handle(MouseEvent event) {
        Data gameData = DataManagement.getData();
        gameData.getPlayerPlaying().haveChosen(gameData.getEventChosen().getChoices().get(1));
        showEffect(gameData.getEventChosen().getChoices().get(1));
    }

    @FXML
    void choice3Handle(MouseEvent event) {
        Data gameData = DataManagement.getData();
        gameData.getPlayerPlaying().haveChosen(gameData.getEventChosen().getChoices().get(2));
        showEffect(gameData.getEventChosen().getChoices().get(2));
    }

    @FXML
    void choice4Handle(MouseEvent event) {
        Data gameData = DataManagement.getData();
        gameData.getPlayerPlaying().haveChosen(gameData.getEventChosen().getChoices().get(3));
        showEffect(gameData.getEventChosen().getChoices().get(3));
    }

    private void setBackground(Data gameData) {
        int numberIndustry = gameData.getPlayerPlaying().getResource().get("industry") / 20 * 20;
        int numberFarming = gameData.getPlayerPlaying().getResource().get("farming") / 20 * 20;
        InputStream input = getClass().getResourceAsStream("background" + gameData.getSeason().name() + numberIndustry + "_0" + ".png");
        Image image = new Image(input);
        background.setImage(image);
    }

    @FXML
    void removeFactionBar(MouseEvent event) throws IOException {
        eventPane.setLayoutX(275);
        eventPane.setLayoutY(158);
        buttonFaction.setVisible(true);
        factionScrollPane.setVisible(false);
        arrowAddFaction.setVisible(true);
    }

    private void showEffect(Choice choice) {
        eventLabel.setVisible(false);
        choice1.setVisible(false);
        choice2.setVisible(false);
        choice3.setVisible(false);
        choice4.setVisible(false);
        effect.setVisible(true);
        scrollPaneLabel.setVisible(true);
        effect.setText(choice.toString());
        next.setVisible(true);
    }

    @FXML
    void nextEvent(MouseEvent event) {
        Data gameData = DataManagement.getData();
        eventLabel.setVisible(true);
        effect.setVisible(false);
        scrollPaneLabel.setVisible(false);
        next.setVisible(false);
        gameData.endTurn();
        initialize();
    }

    @FXML
    void removeEventBar(MouseEvent event) throws IOException {
        eventPane.setVisible(false);
        arrowAddEvent.setVisible(true);
        buttonEvent.setVisible(true);

    }

    @FXML
    public void addFactionBar(MouseEvent mouseEvent) {
        eventPane.setLayoutX(361);
        eventPane.setLayoutY(158);
        buttonFaction.setVisible(false);
        factionScrollPane.setVisible(true);
        arrowAddFaction.setVisible(false);
    }

    @FXML
    void addEventBar(MouseEvent event) {
        eventPane.setVisible(true);
        arrowAddEvent.setVisible(false);
        buttonEvent.setVisible(false);
    }

    @FXML
    void factionResults(MouseEvent event) {

    }

    @FXML
    void farmingResults(MouseEvent event) {

    }

    @FXML
    void industryResults(MouseEvent event) {

    }

    @FXML
    public void removeBilanBar(MouseEvent mouseEvent) {
    }
}
