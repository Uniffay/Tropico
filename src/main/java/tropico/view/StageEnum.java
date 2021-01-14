package tropico.view;

/**
 * all the possible scene that can be show in the stage
 * @author Cléis & Quentin
 */
public enum StageEnum {

    MENU("gameMenu.fxml"),
    GAME("gameView.fxml"),
    END("gameScore.fxml");

    /**
     * name of the file fxml for the stage
     */
    private final String name ;

    StageEnum(String name){
        this.name = name;
    }

    /**
     * get name of the file fxml
     * @return name of the file fxml
     */
    public String getName() {
        return name;
    }
}
