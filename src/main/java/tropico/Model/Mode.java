package tropico.Model;

public enum Mode {
    SANDBOX("Event1"),
    IUT("Event2"),
    SEAOFTHIEVES("Event3"),
    MARIO("Event4"),
    PERSONALIZED("");

    private String jsonName;

    Mode(String jsonName){
        this.jsonName = jsonName;
    }

    public String getJsonName() {
        return jsonName;
    }
}
