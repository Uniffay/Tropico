package tropico.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import tropico.Model.DataManagement;
import tropico.Object.Data;
import tropico.Object.Faction;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private Label Money;

    @FXML
    private Label Industry;

    @FXML
    private Label Farming;

    @FXML
    private Label Capitalist_partisan;

    @FXML
    private Label Capitalist_fulfillment;

    @FXML
    private Label Communist_partisan;

    @FXML
    private Label Communist_fulfillment;

    @FXML
    private Label Liberal_partisan;

    @FXML
    private Label Liberal_fulfillment;

    @FXML
    private Label Religious_partisan;

    @FXML
    private Label Religious_fulfillment;

    @FXML
    private Label Militarist_partisan;

    @FXML
    private Label Militarist_fulfillment;

    @FXML
    private Label Ecologist_partisan;

    @FXML
    private Label Ecologist_fulfillment;

    @FXML
    private Label Nationalist_partisan;

    @FXML
    private Label Nationalist_fulfillment;

    @FXML
    private Label Loyalist_partisan;

    @FXML
    private Label Loyalist_fulfillment;

    @FXML
    private Label Event;

    @FXML
    private Label Choice1;

    @FXML
    private Label Choice2;

    @FXML
    private Label Choice3;

    @FXML
    private Label Choice4;

    @FXML
    private Label Date;

    @FXML
    private Label Season;

    public void initialize() {
        Data gameData = DataManagement.getData();
        int turn = gameData.getTurn();
        Date.setText("24/" + getMonth(turn) + "/"+ String.valueOf(turn % 4 + 2015));
        Season.setText(getSeasonFromTurn(turn));
        Money.setText(String.valueOf(gameData.getPlayerPlaying().getResource().getMoney()));
        Farming.setText(String.valueOf(gameData.getPlayerPlaying().getResource().getFarming()) + "%");
        Industry.setText(String.valueOf(gameData.getPlayerPlaying().getResource().getIndustry()) + "%");

        Event.setText(gameData.getEventChosen().getLabel());
        initialize_event(gameData);


    }

    private void initialize_event(Data gameData) {
        initialize_factionLabel(gameData);

        ArrayList<Label> Choices = initialize_choicesManagement();

        for (int i = 0; i < 4; i++){
            if(i > gameData.getEventChosen().getChoices().size()){
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
                Choice1,
                Choice2,
                Choice3,
                Choice4
        ));
    }

    private String getSeasonFromTurn(int turn) {
        switch(turn % 4){
            case 0:
                return "Printemps";
            case 1:
                return "Été";
            case 2:
                return "Automne";
            default:
                return "Hiver";
        }
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
                Capitalist_partisan,
                Capitalist_fulfillment,
                Communist_partisan,
                Communist_fulfillment,
                Liberal_partisan,
                Liberal_fulfillment,
                Religious_partisan,
                Religious_fulfillment,
                Militarist_partisan,
                Militarist_fulfillment,
                Ecologist_partisan,
                Ecologist_fulfillment,
                Nationalist_partisan,
                Nationalist_fulfillment,
                Loyalist_partisan,
                Loyalist_fulfillment
        ));
    }

    @FXML
    void choice1Handle(MouseEvent event) {
        System.out.println("1");
    }

    @FXML
    void choice2Handle(MouseEvent event) {
        System.out.println("2");
    }

    @FXML
    void choice3Handle(MouseEvent event) {
        System.out.println("3");
    }

    @FXML
    void choice4Handle(MouseEvent event) {
        System.out.println("4");
    }


}
