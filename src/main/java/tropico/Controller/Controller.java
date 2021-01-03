package tropico.Controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import tropico.Model.DataManagement;
import tropico.Model.ImageManagement;
import tropico.Object.Choice;
import tropico.Object.Data;
import tropico.Object.Faction;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class Controller {

    @FXML
    private AnchorPane anchorPane;

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
    private HBox choice1;

    @FXML
    private HBox choice2;

    @FXML
    private HBox choice3;

    @FXML
    private HBox choice4;

    @FXML
    private Label labelChoice1;

    @FXML
    private Label labelChoice2;

    @FXML
    private Label labelChoice3;

    @FXML
    private Label labelChoice4;

    @FXML
    private Label money1;

    @FXML
    private Label money2;

    @FXML
    private Label money3;

    @FXML
    private Label money4;

    @FXML
    private Label date;

    @FXML
    private Label season;

    @FXML
    private ImageView background;

    @FXML
    private Label next;

    @FXML
    private Text effect;

    @FXML
    private ScrollPane scrollPaneLabel;

    @FXML
    private AnchorPane resultEventPane;

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
    private AnchorPane header;

    @FXML
    private HBox loanMenu;

    @FXML
    private Label loan;

    @FXML
    private HBox debtShow;

    @FXML
    private Label debtValue;

    @FXML
    private Label debtMessage;

    public void initialize() {
        Data gameData = DataManagement.getData();
        setTextHeaderBar(gameData);
        initializeGraphics(gameData);
        initializeEvent(gameData);
        initializeFactionLabel(gameData);
        List<Node> needInFront = initializeFrontNodes();
        nodesToFront(needInFront);
    }

    private List<Node> initializeFrontNodes() {
        return List.of(
                eventPane,
                resultEventPane,
                factionScrollPane,
                buttonEvent,
                buttonFaction,
                arrowAddEvent,
                arrowAddFaction,
                header
        );
    }

    private void nodesToFront(List<Node> nodes) {
        nodes.forEach(Node::toFront);
    }

    private void initializeGraphics(Data gameData) {
        setBackground(gameData);
        initializeIndustry(gameData);
        initializeTrees(gameData);
        initializeFarm(gameData);
        initializePollution(gameData);
    }

    private void initializeFarm(Data gameData) {
        List<ImageView> farming = ImageManagement.getFarm(anchorPane, 5);
        setListVisible(farming, gameData, false, farming.size()); // remove all farm from screen in case an industry need to be removed
        setListVisible(farming, gameData, true, gameData.getPlayerPlaying().getResource().get("farming") / 20);
    }

    private void initializePollution(Data gameData) {
        int pollutionLevel = gameData.getPlayerPlaying().getResource().get("industry") / 20;
        resetPollution();
        if(pollutionLevel < 3) //pollution start at level 3
            return;
        initializePollutionElement("sea", pollutionLevel);
        initializePollutionElement("cloud", pollutionLevel);
    }

    private void resetPollution() {
        reset("sea");
        reset("cloud");
    }

    private void reset(String name) {
        ImageView image = ImageManagement.getShow(name);
        if(Objects.isNull(image))
            return;
        image.setVisible(false);
    }

    private void initializePollutionElement(String name, int pollutionLevel) {
        ImageView image = ImageManagement.getOthers(name, anchorPane, 3).get(pollutionLevel % 3);
        image.setVisible(true);
        ImageManagement.setShow(name, image);
    }

    private void initializeIndustry(Data gameData) {
        List<ImageView> industry = ImageManagement.getOthers("industry", anchorPane, 5);
        setListVisible(industry, gameData, false, industry.size()); // remove all industry from screen in case an industry need to be removed
        setListVisible(industry, gameData, true, gameData.getPlayerPlaying().getResource().get("industry") / 20);
    }

    private void initializeTrees(Data gameData) {
        List<ImageView> trees = ImageManagement.getTrees(gameData.getSeason(), anchorPane);
        setListVisible(trees, gameData, true, (5 - gameData.getPlayerPlaying().getResource().get("industry") / 20));
        trees = ImageManagement.getTrees(gameData.getSeason().previous(), anchorPane);
        setListVisible(trees, gameData, false, 5);
    }

    private void setListVisible(List<ImageView> trees, Data gameData, boolean visible, int number) {
        for (int i = 0 ; i < number ; i++){
            trees.get(i).setVisible(visible);
        }
    }

    private void setTextHeaderBar(Data gameData){
        int turn = gameData.getTurn();
        if (gameData.getPlayerPlaying().haveDebt())
            debtShow.setVisible(true);
        date.setText("24/" + getMonth(turn) + "/"+ String.valueOf(turn / 4 + 2015));
        season.setText(gameData.getSeason().getName());
        money.setText(String.valueOf(gameData.getPlayerPlaying().getResource().get("money")));
        farming.setText(String.valueOf(gameData.getPlayerPlaying().getResource().get("farming") + "%"));
        industry.setText(String.valueOf(gameData.getPlayerPlaying().getResource().get("industry") + "%"));
    }

    private void initializeEvent(Data gameData) {
        eventLabel.setText(gameData.getEventChosen().getLabel());
        List<HBox> choices = initializeChoicesManagement();
        List<Label> labels = initializeLabelChoiceManagement();
        List<Label> moneyManagement = initializeMoneyManagement();
        for (int i = 0; i < 4; i++){
            if(i >= gameData.getEventChosen().getChoices().size()){
                choices.get(i).setVisible(false);
                continue;
            }
            choices.get(i).setVisible(true);
            labels.get(i).setText(gameData.getEventChosen().getChoices().get(i).getLabel());
            int price = gameData.getEventChosen().getChoices().get(i).getPrice();
            moneyManagement.get(i).setText(price + "$");
            setFillMoney(price, gameData, moneyManagement.get(i));
        }
    }

    private void setFillMoney(int price, Data gameData, Label money) {
        if(price <= gameData.getPlayerPlaying().getResource().get("money") || price == 0) {
            money.setTextFill(Color.GREEN);
            return;
        }
        money.setTextFill(Color.RED);

    }

    private List<Label> initializeLabelChoiceManagement() {
        return List.of(
            labelChoice1,
            labelChoice2,
            labelChoice3,
            labelChoice4
        );
    }

    private List<HBox> initializeChoicesManagement() {
        return List.of(
            choice1,
            choice2,
            choice3,
            choice4
        );
    }

    private List<Label> initializeMoneyManagement() {
        return List.of(
            money1,
            money2,
            money3,
            money4
        );
    }

    private String getMonth(int turn) {
        String month = String.valueOf(3 + turn % 4 * 3);
        month = (month.length() < 2)? "0" + month : month;
        return month;
    }

    private void initializeFactionLabel(Data gameData) {
        List<Label> LabelManagement = initializeLabelManagement();
        int i = 0;
        for (Faction faction: gameData.getPlayerPlaying().getFactions().getFactions()){
            LabelManagement.get(i).setText(String.valueOf(faction.getPartisan()));
            LabelManagement.get(i + 1).setText(String.valueOf(faction.getFulfillment()) + "%");
            i += 2;
        }
    }

    private List<Label> initializeLabelManagement() {
        return  List.of(
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
        );
    }

    @FXML
    void choice1Handle(MouseEvent event) {
        choiceHandle(0);
    }

    @FXML
    void choice2Handle(MouseEvent event) {
        choiceHandle(1);
    }

    @FXML
    void choice3Handle(MouseEvent event) {
        choiceHandle(2);
    }

    @FXML
    void choice4Handle(MouseEvent event) {
        choiceHandle(3);
    }

    private void choiceHandle(int i){
        Data gameData = DataManagement.getData();
        gameData.getPlayerPlaying().haveChosen(gameData.getEventChosen().getChoices().get(i));
        showEffect(gameData.getEventChosen().getChoices().get(i));
        debtMessage.setVisible(false);
    }

    private void setBackground(Data gameData) {
        InputStream input = getClass().getResourceAsStream("background" + gameData.getSeason() + ".png");
        Image image = new Image(input);
        background.setImage(image);
    }

    @FXML
    void removeFactionBar(MouseEvent event) throws IOException {
        eventPane.setLayoutX(275);
        eventPane.setLayoutY(158);
        resultEventPane.setLayoutX(275);
        resultEventPane.setLayoutY(158);
        buttonFaction.setVisible(true);
        factionScrollPane.setVisible(false);
        arrowAddFaction.setVisible(true);
    }

    private void showEffect(Choice choice) {
        eventPane.setVisible(false);
        resultEventPane.setVisible(true);
        effect.setText(choice.toString());
        replaceText();
        next.setVisible(true);
    }

    private void replaceText() {
        double y = scrollPaneLabel.getBoundsInLocal().getHeight() / 2 - effect.getBoundsInLocal().getHeight() / 2 + 21;
        effect.setLayoutY(Math.max(y, 21));
    }

    @FXML
    void nextEvent(MouseEvent event) {
        Data gameData = DataManagement.getData();
        eventPane.setVisible(true);
        resultEventPane.setVisible(false);
        next.setVisible(false);
        gameData.endTurn();
        debtMessage.setVisible(false);
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
        resultEventPane.setLayoutX(361);
        resultEventPane.setLayoutY(158);
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
    void openLoanMenu(MouseEvent event){
        loanMenu.setVisible(!loanMenu.isVisible());
    }

    @FXML
    void addLoan(MouseEvent event){
        loan.setText(String.valueOf(Math.min(Integer.parseInt(loan.getText()) + 500, 10000)));
    }

    @FXML
    void removeLoan(MouseEvent event){
        loan.setText(String.valueOf(Math.max(Integer.parseInt(loan.getText()) - 500,0)));
    }

    @FXML
    void validLoan(MouseEvent event){
        Data gameData = DataManagement.getData();
        int loanValue = Integer.parseInt(loan.getText());
        if (gameData.getPlayerPlaying().canLoan(loanValue)) {
            gameData.getPlayerPlaying().addDebt(loanValue);
            gameData.getPlayerPlaying().addMoney(loanValue);
            debtValue.setText(String.valueOf(gameData.getPlayerPlaying().getDebt()));
            setTextHeaderBar(gameData);
        }
        else
            debtMessage.setVisible(true);
        money.setText(String.valueOf(gameData.getPlayerPlaying().getResource().get("money")));
        loan.setText("0");
        loanMenu.setVisible(false);

    }
}
