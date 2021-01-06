package tropico.view;

public enum StageEnum {

    MENU("gameMenu.fxml"),
    GAME("gameView.fxml");

    private final String name ;

    StageEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
