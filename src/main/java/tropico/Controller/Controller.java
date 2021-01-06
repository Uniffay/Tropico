package tropico.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import tropico.Model.DataManagement;
import tropico.Model.FactionAddManagement;
import tropico.Model.FoodManagement;
import tropico.Model.ImageManagement;
import tropico.Object.Choice;
import tropico.Object.Data;
import tropico.Object.Faction;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Controller {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView background;

    @FXML
    private ScrollPane factionScrollPane;

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
    private AnchorPane eventPane;

    @FXML
    private Label eventLabel;

    @FXML
    private HBox choice1;

    @FXML
    private Label labelChoice1;

    @FXML
    private Label money1;

    @FXML
    private HBox choice2;

    @FXML
    private Label labelChoice2;

    @FXML
    private Label money2;

    @FXML
    private HBox choice3;

    @FXML
    private Label labelChoice3;

    @FXML
    private Label money3;

    @FXML
    private HBox choice4;

    @FXML
    private Label labelChoice4;

    @FXML
    private Label money4;

    @FXML
    private Arc buttonFaction;

    @FXML
    private Polygon arrowAddFaction;

    @FXML
    private Arc buttonEvent;

    @FXML
    private Polygon arrowAddEvent;

    @FXML
    private AnchorPane resultEventPane;

    @FXML
    private ScrollPane scrollPaneLabel;

    @FXML
    private Text effect;

    @FXML
    private Label next;

    @FXML
    private AnchorPane header;

    @FXML
    private Label date;

    @FXML
    private Label season;

    @FXML
    private Label money;

    @FXML
    private Label industry;

    @FXML
    private Label farming;

    @FXML
    private HBox debtShow;

    @FXML
    private Label debtValue;

    @FXML
    private Label debtMessage;

    @FXML
    private HBox loanMenu;

    @FXML
    private Label loan;

    @FXML
    private AnchorPane endYearMenu;

    @FXML
    private Text report;

    @FXML
    private Label foodInPossession;

    @FXML
    private Label totalPartisan;

    @FXML
    private Label foodNeeded;

    @FXML
    private Label missingFood;

    @FXML
    private Label totalPriceFood;

    @FXML
    private TextField foodBought;

    @FXML
    private Label extraFood;

    @FXML
    private Label capitalistHappiness;

    @FXML
    private TextField capitalist;

    @FXML
    private Label communistHappiness;

    @FXML
    private TextField communist;

    @FXML
    private Label liberalHappiness;

    @FXML
    private TextField liberal;

    @FXML
    private Label religiousHappiness;

    @FXML
    private TextField religious;

    @FXML
    private Label militaristHappiness;

    @FXML
    private TextField militarist;

    @FXML
    private Label ecologistHappiness;

    @FXML
    private TextField ecologist;

    @FXML
    private Label nationalistHappiness;

    @FXML
    private TextField nationalist;

    @FXML
    private Label totalPriceFaction;

    @FXML
    private Label totalPrice;

    private final HashMap<String,TextField> factionsTextField = new HashMap<>();

    private boolean haveInitiateFactionsTextField = false;

    private void initializeFactionsTextField() {
        initializeHashMapTextField();
        initializeTextFieldToZero();
    }

    private void initializeTextFieldToZero() {
        for(String name: factionsTextField.keySet()){
            factionsTextField.get(name).setText("0");
        }
    }

    private void initializeHashMapTextField() {
        if(haveInitiateFactionsTextField)
            return;
        factionsTextField.put("capitalist", capitalist);
        factionsTextField.put("communist", communist);
        factionsTextField.put("nationalist", nationalist);
        factionsTextField.put("ecologist", ecologist);
        factionsTextField.put("militarist", militarist);
        factionsTextField.put("religious", religious);
        factionsTextField.put("liberal", liberal);
        haveInitiateFactionsTextField = true;
    }



    private String foodBoughtString = "0";

    public List<Label> getFulfillmentFactionList(){
        return List.of(
                nationalistHappiness,
                capitalistHappiness,
                ecologistHappiness,
                militaristHappiness,
                liberalHappiness,
                religiousHappiness,
                communistHappiness
        );
    }

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
                header,
                loanMenu,
                endYearMenu
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
        setListVisible(farming, false, farming.size()); // remove all farm from screen in case an industry need to be removed
        setListVisible(farming, true, gameData.getPlayerPlaying().getResource().get("farming") / 20);
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
        setListVisible(industry, false, industry.size()); // remove all industry from screen in case an industry need to be removed
        setListVisible(industry, true, gameData.getPlayerPlaying().getResource().get("industry") / 20);
    }

    private void initializeTrees(Data gameData) {
        List<ImageView> trees = ImageManagement.getTrees(gameData.getSeason(), anchorPane);
        setListVisible(trees, true, (5 - gameData.getPlayerPlaying().getResource().get("industry") / 20));
        trees = ImageManagement.getTrees(gameData.getSeason().previous(), anchorPane);
        setListVisible(trees, false, 5);
    }

    private void setListVisible(List<ImageView> trees, boolean visible, int number) {
        for (int i = 0 ; i < number ; i++){
            trees.get(i).setVisible(visible);
        }
    }

    private void setTextHeaderBar(Data gameData){
        int turn = gameData.getTurn();
        if (gameData.getPlayerPlaying().haveDebt())
            debtShow.setVisible(true);
        date.setText("24/" + getMonth(turn) + "/"+ (turn / 4 + 2015));
        season.setText(gameData.getSeason().getName());
        money.setText(String.valueOf(gameData.getPlayerPlaying().getResource().get("money")));
        farming.setText(gameData.getPlayerPlaying().getResource().get("farming") + "%");
        industry.setText(gameData.getPlayerPlaying().getResource().get("industry") + "%");
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
    void choice1Handle() {
        choiceHandle(0);
    }

    @FXML
    void choice2Handle() {
        choiceHandle(1);
    }

    @FXML
    void choice3Handle() {
        choiceHandle(2);
    }

    @FXML
    void choice4Handle() {
        choiceHandle(3);
    }

    private void choiceHandle(int choiceNumber){
        Data gameData = DataManagement.getData();
        gameData.getPlayerPlaying().haveChosen(gameData.getEventChosen().getChoices().get(choiceNumber));
        showEffect(gameData.getEventChosen().getChoices().get(choiceNumber));
        debtMessage.setVisible(false);
    }

    private void setBackground(Data gameData) {
        InputStream input = getClass().getResourceAsStream("background" + gameData.getSeason() + ".png");
        Image image = new Image(input);
        background.setImage(image);
    }

    @FXML
    void removeFactionBar() {
        eventPane.setLayoutX(275);
        resultEventPane.setLayoutX(275);
        endYearMenu.setLayoutX(275);
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
    void nextEvent() {
        Data gameData = DataManagement.getData();
        gameData.endTurn();
        next.setVisible(false);
        debtMessage.setVisible(false);
        if(gameData.isYearEnding()) {
            setTextHeaderBar(gameData);
            initializeFactionLabel(gameData);
            openEndYearMenu();
        }
        else
            showEvent();

    }

    private void showEvent() {
        eventPane.setVisible(true);
        resultEventPane.setVisible(false);
        initialize();
    }

    @FXML
    void removeEventBar() {
        eventPane.setVisible(false);
        arrowAddEvent.setVisible(true);
        buttonEvent.setVisible(true);

    }

    @FXML
    public void addFactionBar() {
        eventPane.setLayoutX(361);
        resultEventPane.setLayoutX(361);
        endYearMenu.setLayoutX(361);
        buttonFaction.setVisible(false);
        factionScrollPane.setVisible(true);
        arrowAddFaction.setVisible(false);
    }

    @FXML
    void addEventBar() {
        eventPane.setVisible(true);
        arrowAddEvent.setVisible(false);
        buttonEvent.setVisible(false);
    }

    @FXML
    void openLoanMenu(){
        loanMenu.setVisible(!loanMenu.isVisible());

    }

    @FXML
    void addLoan(){
        loan.setText(String.valueOf(Math.min(Integer.parseInt(loan.getText()) + 500, 10000)));
    }

    @FXML
    void removeLoan(){
        loan.setText(String.valueOf(Math.max(Integer.parseInt(loan.getText()) - 500,0)));
    }

    @FXML
    void validLoan(){
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

    public void openEndYearMenu(){
        Data gameData = DataManagement.getData();
        endYearMenu.setVisible(true);
        initializeReport(gameData);
        initializeFoodMenu(gameData);
        initializeFactionMenu(gameData);
        initializeAllTextFieldListener();
        totalPrice.setText("0$");
    }

    private void initializeAllTextFieldListener() {
        initializeFoodTextFieldListener();
        factionsTextField.keySet().forEach(f->initializeTextFieldListener(f, factionsTextField.get(f)));
    }

    private void initializeTextFieldListener(String name, TextField textField) {
        textField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue)
                modifyAndRefreshTextField(name, Integer.parseInt(textField.getText()) - FactionAddManagement.get(name));
        });

    }

    private void initializeReport(Data gameData) {
        var resource = gameData.getPlayerPlaying().getResource();
        report.setText(
                "------Début du rapport------\n\n" +
                "Nourriture = 40 * " + resource.get("farming") + " = " + (resource.get("farming") * 40) + "\n\n" +
                "Argent Supplémentaire = 10 * " + resource.get("industry") + " = " + (resource.get("industry") * 10) + "$\n\n" +
                "------Fin du rapport------"
        );
    }

    private void initializeFoodTextFieldListener() {
        foodBought.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue)
                modifyFoodBoughtInput();
        });
    }

    @FXML
    void enterFoodBought(){
        modifyFoodBoughtInput();
    }

    private void modifyFoodBoughtInput() {
        int totalPriceAdded = FoodManagement.addFoodBought(Integer.parseInt(foodBought.getText()) - FoodManagement.getFoodBought()) * 8;
        refreshFoodLabel();
        setTotalPrice(totalPriceAdded);
    }

    private void refreshFoodLabel() {
        foodBought.setText(String.valueOf(FoodManagement.getFoodBought()));
        totalPriceFood.setText(FoodManagement.getFoodBought() * 8 + "$");
        missingFood.setText(String.valueOf(FoodManagement.getFoodMissingWithFoodBought()));
    }

    private void initializeFoodMenu(Data gameData) {
        FoodManagement.initialize(gameData);
        initializeFoodLabel(gameData);
        foodBought.setText("0$");

    }

    private void initializeFoodLabel(Data gameData) {
        int missingFoodValue = FoodManagement.getFoodMissing();
        foodInPossession.setText(String.valueOf(FoodManagement.getFood()));
        totalPartisan.setText(String.valueOf(gameData.getPlayerPlaying().getFactions().getTotalPartisan()));
        foodNeeded.setText(String.valueOf(FoodManagement.getFoodNeeded()));
        missingFood.setText(String.valueOf(Math.max(missingFoodValue, 0)));
        extraFood.setText(String.valueOf(Math.max(-missingFoodValue, 0)));
        totalPriceFood.setText("0$");
    }

    @FXML
    void addFood(){
        manageModifyFoodBought(1);
    }

    private void manageModifyFoodBought(int number) {
        int totalPriceAdded = FoodManagement.addFoodBought(number) * 8;
        refreshFoodLabel();
        setTotalPrice(totalPriceAdded);
    }

    @FXML
    void removeFood(){
        manageModifyFoodBought(-1);
    }

    @FXML
    void verifyKeyTypedFood(KeyEvent event){
        if(notNumeric(event.getCharacter())){
            foodBought.setText(foodBought.getText().replace(event.getCharacter(), ""));
        }
        if(foodBought.getLength() == 0){
            foodBought.setText("0");
        }
        if(foodBought.getLength() > 8){
            foodBought.setText(foodBoughtString);
            return;
        }
        foodBoughtString = foodBought.getText();
    }

    public static boolean notNumeric(String strNum) {
        if (strNum == null) {
            return true;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return true;
        }
        return false;
    }

    private void initializeFactionMenu(Data gameData) {
        initializeFactionsTextField();
        totalPriceFaction.setText("0$");
        FactionAddManagement.initializeFactions();
        List<Label> fulfillment = getFulfillmentFactionList();
        initializeFulfillmentValue(fulfillment, gameData);

    }

    private void initializeFulfillmentValue(List<Label> fulfillment, Data gameData) {
        for(Label fulfillmentLabel: fulfillment){
            String name = fulfillmentLabel.getId().replace("Happiness", "");
            fulfillmentLabel.setText(String.valueOf(gameData.getPlayerPlaying().getFactions().getFaction(name).getFulfillment()));
        }
    }

    @FXML
    void enterFaction(ActionEvent event){
        var source = (TextField)event.getSource();
        String name = source.getId();
        modifyAndRefreshTextField(name, Integer.parseInt(source.getText()) - FactionAddManagement.get(name));
    }

    @FXML
    void addFaction(MouseEvent event){
        ImageView source = (ImageView)event.getSource();
        String name = source.getId().substring(0,  source.getId().length() - 1);
        modifyAndRefreshTextField(name, 1);
    }

    void modifyAndRefreshTextField(String name, int number){
        int numberAdded = FactionAddManagement.addFactionFulfillment(name, number);
        refreshFactionTextField(name);
        int totalPriceAdded = setTotalPriceFaction(name, numberAdded);
        setTotalPrice(totalPriceAdded);
    }

    private void setTotalPrice(int totalPriceAdded) {
        String totalPriceString = totalPrice.getText().substring(0, totalPrice.getText().length() - 1);
        int totalPriceValue = Integer.parseInt(totalPriceString);
        totalPrice.setText(totalPriceValue + totalPriceAdded + "$");
        totalPrice.setTextFill(Color.GREEN);
        if(totalPriceAdded + totalPriceValue > DataManagement.getData().getPlayerPlaying().getResource().get("money"))
            totalPrice.setTextFill(Color.RED);
    }

    private int setTotalPriceFaction(String name, int numberAdded) {
        Faction faction = DataManagement.getData().getPlayerPlaying().getFactions().getFaction(name);
        String totalPriceString = totalPriceFaction.getText().substring(0, totalPriceFaction.getText().length() - 1);
        int totalPriceValue = Integer.parseInt(totalPriceString);
        totalPriceFaction.setText((totalPriceValue + (numberAdded * faction.getPartisan()) * 15) + "$");
        return numberAdded * faction.getPartisan() * 15;
    }

    private void refreshFactionTextField(String name) {
        var textField = factionsTextField.get(name);
        textField.setText(String.valueOf(FactionAddManagement.get(name)));
    }

    @FXML
    void removeFaction(MouseEvent event){
        ImageView cc = (ImageView)event.getSource();
        String name = cc.getId().substring(0,  cc.getId().length() - 1);
        modifyAndRefreshTextField(name, -1);
    }

    @FXML
    void validEndYearChoice(){
        Data gameData = DataManagement.getData();
        var totalPriceString = totalPrice.getText().substring(0, totalPrice.getText().length() - 1);
        int totalPriceValue = Integer.parseInt(totalPriceString);
        if(totalPriceValue > gameData.getPlayerPlaying().getResource().get("money")){
            return;
        }
        endYearMenu.setVisible(false);
        nextEvent();
    }

    @FXML
    void verifyKeyTypedFaction(KeyEvent event){
        TextField source = (TextField)(event.getSource());
        if(notNumeric(event.getCharacter())){
            source.setText(source.getText().replace(event.getCharacter(), ""));
        }
        if(source.getLength() == 0){
            source.setText("0");
        }
        if(source.getLength() > 4 || Integer.parseInt(source.getText()) > 100){
            source.setText(String.valueOf(100));
        }
    }
}
