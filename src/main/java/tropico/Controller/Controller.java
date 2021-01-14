package tropico.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import tropico.Model.*;
import tropico.Object.Choice;
import tropico.Object.Data;
import tropico.Object.Dictator;
import tropico.Object.Faction;
import tropico.view.StageEnum;
import tropico.view.StageManagement;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * control the action the user made during the game
 * @author Quentin & Cléis
 */
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
    private TextField refund;

    @FXML
    private Label refundLeft;

    @FXML
    private Label totalPrice;

    @FXML
    private HBox setting;

    @FXML
    private Rectangle graySetting;

    @FXML
    private AnchorPane settingMenu;

    @FXML
    private ImageView soundImage;

    @FXML
    private ImageView unicornImage;

    @FXML
    private AnchorPane warningMenu;

    @FXML
    private Text warning;

    @FXML
    private AnchorPane playerInfo;

    @FXML
    private Label player;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab defaultTab;

    @FXML
    private ImageView imageEvent;

    @FXML
    private Rectangle grayPlayerInfo;

    /**
     * action made when player accept warning menu
     */
    private Runnable actionAccepted;

    /**
     * hashMap of name faction -> text field
     */
    private final HashMap<String,TextField> factionsTextField = new HashMap<>();

    /**
     * false if the faction Text Field hasn't been initialized
     */
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

    private List<Label> getFulfillmentFactionList(){
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

    /**
     * initialize graphics
     */
    public void initialize() {
        Data gameData = DataManagement.getData();
        setTextHeaderBar(gameData);
        if(gameData.nonSolo())
            showPlayerPlaying();
        initializeGraphics(gameData);
        initializeEvent(gameData);
        initializeFactionLabel(gameData);
        setSoundSetting();
        List<Node> needInFront = initializeFrontNodes();
        nodesToFront(needInFront);
    }

    private void setSoundSetting() {
        String suffix = (SoundManagement.isSoundOn())? "On2.png":"Off2.png";
        soundImage.setImage(ImageManagement.createImage("sound" + suffix));
        unicornImage.setImage(ImageManagement.createImage("unicorn" + suffix.replace("2", "")));
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
                endYearMenu,
                graySetting,
                setting,
                grayPlayerInfo,
                playerInfo,
                settingMenu,
                warningMenu
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
        initializeImageEvent(gameData);
    }

    private void initializeImageEvent(Data gameData) {
        imageEvent.setImage(ImageManagement.createImage(gameData.getEventChosen().getImage()));
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
        ImageManagement.putShow(name, image);
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
        debtShow.setVisible(gameData.getPlayerPlaying().haveDebt());
        debtValue.setText(String.valueOf(gameData.getPlayerPlaying().getDebt()));
        date.setText("24/" + getMonth(turn) + "/"+ (turn / 4 + 2020));
        season.setText(gameData.getSeason().getName());
        money.setText(String.valueOf(gameData.getPlayerPlaying().getMoney()));
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
            int price = Utils.modifiedByDifficulty(-gameData.getEventChosen().getChoices().get(i).getPrice());
            moneyManagement.get(i).setText(price + "$");
            setFillMoney(-price, gameData, moneyManagement.get(i));
        }
    }

    private void showPlayerPlaying(){
        grayPlayerInfo.setVisible(true);
        playerInfo.setVisible(true);
        player.setText(DataManagement.getData().getPlayerPlaying().getName());
    }

    private void setFillMoney(int price, Data gameData, Label money) {
        if(price <= gameData.getPlayerPlaying().getMoney() || price == 0) {
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

    /**
     * manage choice 1 modification
     */
    @FXML
    void choice1Handle() {
        choiceHandle(0);
    }

    /**
     * manage choice 2 modification
     */
    @FXML
    void choice2Handle() {
        choiceHandle(1);
    }

    /**
     * manage choice 3 modification
     */
    @FXML
    void choice3Handle() {
        choiceHandle(2);
    }

    /**
     * manage choice 4 modification
     */
    @FXML
    void choice4Handle() {
        choiceHandle(3);
    }

    private void choiceHandle(int choiceNumber){
        Data gameData = DataManagement.getData();
        Dictator playerPlaying = gameData.getPlayerPlaying();
        Choice choice = gameData.getEventChosen().getChoices().get(choiceNumber);
        if(playerPlaying.getMoney() < choice.getPrice())
            return;
        playerPlaying.haveChosen(choice);
        showEffect(choice);
        debtMessage.setVisible(false);
    }

    private void setBackground(Data gameData) {
        InputStream input = getClass().getResourceAsStream("background" + gameData.getSeason() + ".png");
        Image image = new Image(input);
        background.setImage(image);
    }

    /**
     * remove faction bar
     */
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

    /**
     * manage the end of the turn
     */
    @FXML
    void nextEvent() {
        Data gameData = DataManagement.getData();
        gameData.endTurn();
        if(gameData.isGameEnded()){
            return;
        }
        if(gameData.nonSolo())
            showPlayerPlaying();
        next.setVisible(false);
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

    /**
     * remove event bar
     */
    @FXML
    void removeEventBar() {
        eventPane.setVisible(false);
        arrowAddEvent.setVisible(true);
        buttonEvent.setVisible(true);

    }

    /**
     * show faction bar
     */
    @FXML
    public void addFactionBar() {
        eventPane.setLayoutX(361);
        resultEventPane.setLayoutX(361);
        endYearMenu.setLayoutX(361);
        buttonFaction.setVisible(false);
        factionScrollPane.setVisible(true);
        arrowAddFaction.setVisible(false);
    }

    /**
     * show event window
     */
    @FXML
    void addEventBar() {
        eventPane.setVisible(true);
        arrowAddEvent.setVisible(false);
        buttonEvent.setVisible(false);
    }

    /**
     * open the loan menu when clicking in the money bar
     */
    @FXML
    void openLoanMenu(){
        loanMenu.setVisible(!loanMenu.isVisible());

    }

    /**
     * add 10 to the loan value when the user click on the "-" button
     */
    @FXML
    void addLoan(){
        loan.setText(String.valueOf(Math.min(Integer.parseInt(loan.getText()) + 500, 10000)));
    }

    /**
     * remove 10 to the loan value when the user click on the "-" button
     */
    @FXML
    void removeLoan(){
        loan.setText(String.valueOf(Math.max(Integer.parseInt(loan.getText()) - 500,0)));
    }

    /**
     * add debt and money to player when the player valid by clicking in the button
     */
    @FXML
    void validLoan() {
        Data gameData = DataManagement.getData();
        manageAddDebt(gameData);
        manageTextMoney(gameData);
        manageTextCostEvent(gameData);
        setTextHeaderBar(gameData);
        loanMenu.setVisible(false);
    }

    private void manageTextCostEvent(Data gameData) {
        for (var money: initializeMoneyManagement()){
            if(Integer.parseInt(money.getText().substring(0, money.getText().length() - 1)) < gameData.getPlayerPlaying().getMoney())
                money.setTextFill(Color.GREEN);
        }
    }

    private void manageTextMoney(Data gameData) {
        int totalPriceValue = Integer.parseInt(totalPrice.getText().substring(0, totalPrice.getText().length() - 1));
        totalPrice.setTextFill((gameData.getPlayerPlaying().getMoney() > totalPriceValue) ? Color.GREEN : Color.RED);
        RefundManagement.update(gameData);
        refundLeft.setText(RefundManagement.getLoanSave() - RefundManagement.getLoanRefund() + "$");
        money.setText(String.valueOf(gameData.getPlayerPlaying().getMoney()));
        loan.setText("0");
    }

    private void manageAddDebt(Data gameData) {
        int loanValue = Integer.parseInt(loan.getText());
        Dictator playerPlaying = gameData.getPlayerPlaying();
        playerPlaying.addDebt(loanValue);
        playerPlaying.addMoney(loanValue);
        debtValue.setText(String.valueOf(playerPlaying.getDebt()));
    }

    /**
     * Open the end year menu
     */
    public void openEndYearMenu(){
        Data gameData = DataManagement.getData();
        endYearMenu.setVisible(true);
        tabPane.getSelectionModel().select(defaultTab);
        initializeReport(gameData);
        initializeFoodMenu(gameData);
        initializeFactionMenu(gameData);
        initializeRefundMenu(gameData);
        initializeAllTextFieldListener();
        totalPrice.setText("0$");
    }

    private void initializeRefundMenu(Data gameData) {
        RefundManagement.initialize(gameData);
        refundLeft.setText(RefundManagement.getLoanSave() + "$");
        refund.setText("0");
    }

    private void initializeAllTextFieldListener() {
        initializeFoodTextFieldListener();
        initializeRefundTextFieldListener();
        factionsTextField.keySet().forEach(f->initializeTextFieldListener(f, factionsTextField.get(f)));
    }

    private void initializeRefundTextFieldListener() {
        refund.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if(!newPropertyValue)
                enterRefund();
        });
    }

    private void initializeTextFieldListener(String name, TextField textField) {
        textField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue)
                modifyAndRefreshTextField(name, (Integer.parseInt(textField.getText()) - FactionAddManagement.get(name)));
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

    /**
     * refresh the value food bought by what was written by the user
     */
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
        missingFood.setText(String.valueOf(Math.max(FoodManagement.getFoodMissingWithFoodBought(), 0)));
    }

    private void initializeFoodMenu(Data gameData) {
        FoodManagement.initialize(gameData);
        initializeFoodLabel(gameData);
        foodBought.setText("0");

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

    /**
     * add 1 to the value food bough
     */
    @FXML
    void addFood(){
        manageModifyFoodBought(1);
    }

    private void manageModifyFoodBought(int number) {
        int totalPriceAdded = FoodManagement.addFoodBought(number) * 8;
        refreshFoodLabel();
        setTotalPrice(totalPriceAdded);
    }

    /**
     * remove 1 to the value food bough
     */
    @FXML
    void removeFood(){
        manageModifyFoodBought(-1);
    }

    /**
     * verify that the key the user typed verify the condition and modify the text otherwise
     * @param event
     *      event of the user (key)
     */
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

    private static boolean notNumeric(String strNum) {
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

    /**
     * refresh the value of corresponding text field by what was written by the user
     * @param event
     *          event of the user (enter)
     */
    @FXML
    void enterFaction(ActionEvent event){
        var source = (TextField)event.getSource();
        String name = source.getId();
        modifyAndRefreshTextField(name, (Integer.parseInt(source.getText()) - FactionAddManagement.get(name)) );
    }

    /**
     * remove 10 to the value of the corresponding text field
     * @param event
     *          event of the user (mouse click)
     */
    @FXML
    void addFaction(MouseEvent event){
        ImageView source = (ImageView)event.getSource();
        String name = source.getId().substring(0,  source.getId().length() - 1);
        modifyAndRefreshTextField(name, 10);
    }

    private void modifyAndRefreshTextField(String name, int number){
        if(DataManagement.getData().getPlayerPlaying().getFactions().getFaction(name).getFulfillment() == 0){
            return;
        }
        int numberAdded = FactionAddManagement.addFactionFulfillment(name, number);
        refreshFactionTextField(name);
        int totalPriceAdded = setTotalPriceFaction(name, round(numberAdded));//rounded to superior
        setTotalPrice(totalPriceAdded);
    }

    private int round(int numberAdded) {
        return (numberAdded < 0)? (numberAdded - 9) / 10 : (numberAdded + 9) / 10;
    }

    private void setTotalPrice(int totalPriceAdded) {
        String totalPriceString = totalPrice.getText().substring(0, totalPrice.getText().length() - 1);
        int totalPriceValue = Integer.parseInt(totalPriceString);
        totalPrice.setText(totalPriceValue + totalPriceAdded + "$");
        totalPrice.setTextFill(Color.GREEN);
        if(totalPriceAdded + totalPriceValue > DataManagement.getData().getPlayerPlaying().getMoney())
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

    /**
     * remove 10 to the value of the corresponding text field
     * @param event
     *          event of the user (mouse click)
     */
    @FXML
    void removeFaction(MouseEvent event){
        ImageView cc = (ImageView)event.getSource();
        String name = cc.getId().substring(0,  cc.getId().length() - 1);
        modifyAndRefreshTextField(name, -10);
    }

    /**
     * Manage choice of the end year menu when the user validate it and have enough money
     */
    @FXML
    void validEndYearChoice(){
        Data gameData = DataManagement.getData();
        Dictator playerPlaying = gameData.getPlayerPlaying();
        if(!canBuyChangeMoney(playerPlaying))
            return;
        FactionAddManagement.validate(gameData);
        FoodManagement.validate(gameData);
        validateRefund(playerPlaying);
        manageEndTurn(gameData);
    }

    private void manageEndTurn(Data gameData) {
        gameData.endTurn();
        if(gameData.isGameEnded())
            return;
        if(!gameData.isYearEnding()) {
            endYearMenu.setVisible(false);
            showEvent();
        }
        else
            openEndYearMenu();
        initialize();
    }

    private boolean canBuyChangeMoney(Dictator playerPlaying) {
        var totalPriceString = totalPrice.getText().substring(0, totalPrice.getText().length() - 1);
        int totalPriceValue = Integer.parseInt(totalPriceString);
        if(totalPriceValue > playerPlaying.getMoney()){
            return false;
        }
        playerPlaying.changeMoney(-totalPriceValue);
        return true;
    }

    private void validateRefund(Dictator playerPlaying) {
        playerPlaying.repayDebt(RefundManagement.getLoanRefund());
        if(playerPlaying.getDebt() == 0)
            debtShow.setVisible(false);
        playerPlaying.addInterest();
        debtValue.setText(String.valueOf(playerPlaying.getDebt()));
    }

    /**
     * verify that the key the user typed verify the condition and modify the text otherwise
     * @param event
     *      event of the user (key)
     */
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

    /**
     * modify refund value when the user click on the "+" button
     */
    @FXML
    void addRefund(){
        modifyAndRefreshRefund(10);
    }

    /**
     * modify refund value when the user click on the "-" button
     */
    @FXML
    void removeRefund(){
        modifyAndRefreshRefund(-10);
    }

    /**
     * save the result entered in the refund text field when the user tap on enter
     */
    @FXML
    void enterRefund(){
        modifyAndRefreshRefund(Integer.parseInt(refund.getText()) - RefundManagement.getLoanRefund());
    }

    private void modifyAndRefreshRefund(int number) {
        int modified = RefundManagement.updateRefund(number);
        refundLeft.setText((RefundManagement.getLoanSave() - RefundManagement.getLoanRefund()) + "$");
        refund.setText(String.valueOf(RefundManagement.getLoanRefund()));
        setTotalPrice(modified);
    }

    /**
     * verify that the key the user typed verify the condition and modify the text otherwise
     * @param event
     *      event of the user (key)
     */
    @FXML
    void verifyKeyTypedRefund(KeyEvent event){
        TextField source = (TextField)(event.getSource());
        if(notNumeric(event.getCharacter())){
            source.setText(source.getText().replace(event.getCharacter(), ""));
        }
        if(source.getLength() == 0){
            source.setText("0");
        }
        if(source.getLength() > 8){
            source.setText(source.getText().substring(1));
        }
    }

    /**
     * Open the setting
     */
    @FXML
    void openSetting(){
        settingMenu.setVisible(!settingMenu.isVisible());
        graySetting.setVisible(!graySetting.isVisible());
    }

    /**
     * Resume the game when the player click on "Continuer" (in the setting)
     */
    @FXML
    void continuePlaying(){
        settingMenu.setVisible(false);
        graySetting.setVisible(false);
    }

    /**
     * Save the game when the player click on "Sauvegarder" (in the setting)
     */
    @FXML
    void saveGame(){
        warningMenu.setVisible(true);
        warning.setText("Sauvegarder écrasera la dernière sauvegarde !");
        actionAccepted = () -> {
            try {
                DataManagement.getData().save();
                warningMenu.setVisible(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * Go back to the menu when the player click on "Retour au menu" (in the setting)
     */
    @FXML
    void backToMenu(){
        warningMenu.setVisible(true);
        warning.setText("Vous allez perdre tous les progrès que vous n'avez pas sauvegardé !");
        actionAccepted = () -> {
            try {
                StageManagement.setScene(StageEnum.MENU);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * switch sound off the media to On or Off when the player click on the speaker (in the setting)
     */
    @FXML
    void switchSound(){
        SoundManagement.switchSoundOn();
        setSoundSetting();
    }

    /**
     * when player click "NON" in the warning menu
     */
    @FXML
    void denied(){
        warningMenu.setVisible(false);
    }

    /**
     * when player click on "OUI" in the warning menu
     */
    @FXML
    void accepted(){
        actionAccepted.run();
    }

    /**
     * when player click on OK in the window that inform him which player turn it is.
     */
    @FXML
    void playerFinish(){
        playerInfo.setVisible(false);
        grayPlayerInfo.setVisible(false);
    }
}
