package tropico.Model;

public enum Difficulty {

    EASY("setting", "faction"),
    MEDIUM("setting", "faction"),
    HARD("setting", "faction"),
    PERSONALIZED("", "");

    private final String jsonSetting;
    private final String jsonFaction;

    Difficulty(String jsonSetting, String jsonFaction){
        this.jsonSetting = jsonSetting;
        this.jsonFaction = jsonFaction;
    }

    public String getJsonSetting() {
        return jsonSetting;
    }

    public String getJsonFaction() {
        return jsonFaction;
    }
}
