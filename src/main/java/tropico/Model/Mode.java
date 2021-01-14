package tropico.Model;

/**
 * represents the possible mode of the game and his respective json name
 * @author Cléis & Quentin
 */
public enum Mode {
    SANDBOX("Event"),
    IUT("Event"),
    SEAOFTHIEVES("Event"),
    MARIO("Event"),
    PERSONALIZED("");

    /**
     * json name of the mode (scenario or sandbox)
     */
    private final String jsonName;

    Mode(String jsonName){
        this.jsonName = jsonName;
    }

    /**
     * get the json name of the mode
     * @return the json name of the mode
     */
    public String getJsonName() {
        return jsonName;
    }
}
