package tropico.Model;

public enum Mode {
    SANDBOX("Event"),
    IUT("Event"),
    SEAOFTHIEVES("Event"),
    MARIO("Event"),
    PERSONALIZED("");

    private final String jsonName;

    Mode(String jsonName){
        this.jsonName = jsonName;
    }

    public String getJsonName() {
        return jsonName;
    }
}
