package tropico.Model;

/**
 * represents the possible mode of the game and his respective json name
 * @author Cléis & Quentin
 */
public enum Mode {
    SANDBOX("Event", "music.mp4"),
    IUT("IUT", "music.mp4"),
    SEAOFTHIEVES("SeaOfThieves", "SeaOfThieves.mp4"),
    MARIO("Mario", "Mario.mp4"),
    PERSONALIZED("", "");

    /**
     * json name of the mode (scenario or sandbox)
     */
    private final String jsonName;

    /**
     * mp4 name of the music
     */
    private final String musicName;


    Mode(String jsonName, String musicName){
        this.jsonName = jsonName;
        this.musicName = musicName;
    }

    /**
     * get the json name of the mode
     * @return the json name of the mode
     */
    public String getJsonName() {
        return jsonName;
    }

    public String getMusic() {
        return musicName;
    }
}
