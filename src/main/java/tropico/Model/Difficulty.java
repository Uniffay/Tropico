package tropico.Model;

public enum Difficulty {

    EASY("setting", "faction"),
    MEDIUM("setting", "faction"),
    HARD("setting", "faction"),
    PERSONALIZED("", "");

    /**
     * name of file json that have the setting for resource and difficulty ration
     */
    private final String jsonSetting;
    /**
     * name of file json that have the setting for faction
     */
    private final String jsonFaction;

    Difficulty(String jsonSetting, String jsonFaction){
        this.jsonSetting = jsonSetting;
        this.jsonFaction = jsonFaction;
    }

    /**
     *
     * @return name of file json that have resource setting and difficulty ratio
     */
    public String getJsonSetting() {
        return jsonSetting;
    }

    /**
     *
     * @return name of the file json that have faction setting
     */
    public String getJsonFaction() {
        return jsonFaction;
    }
}
