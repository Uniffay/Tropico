package tropico.Model;

import java.io.File;

/**
 * manage the json entered by the user for the game
 * @author Cléis & Quentin
 */
public class JSonPathManagement {
    private static String JSON_SETTING = "";
    private static String JSON_FACTION = "";
    private static String JSON_EVENT = "";

    public static void setJsonEvent(String jsonEvent) {
        JSON_EVENT = jsonEvent;
    }

    public static void setJsonFaction(String jsonFaction) {
        JSON_FACTION = jsonFaction;
    }

    public static void setJsonSetting(String jsonSetting) {
        JSON_SETTING = jsonSetting;
    }

    /**
     * get the json event name
     * @return json event name
     */
    public static String getJsonEvent() {
        return JSON_EVENT;
    }

    /**
     * get the json faction name
     * @return json faction name
     */
    public static String getJsonFaction() {
        return JSON_FACTION;
    }

    /**
     * get the json setting name
     * @return json setting name
     */
    public static String getJsonSetting() {
        return JSON_SETTING;
    }

    /**
     * create path from name and directory entered
     * @param nameSetting
     *      name of the json
     * @param directory
     *      directory of the json
     * @return a string corresponding to the path of the file
     */
    public static String createPath(String nameSetting, String directory) {
        return "json/" + directory + "/" + nameSetting + ".json";
    }

    /**
     * verify the json of the setting and the faction exists and is a file
     * @return 1 if the file setting doesn't exist, 2 if the file faction doesn't exist and 0 otherwise
     */
    public static int verifyDifficultyPath() {
        if(!new File(JSON_SETTING).isFile()){
            return 1;
        }
        if(!new File(JSON_FACTION).isFile()){
            return 2;
        }
        return 0;
    }

    /**
     * verify the json of the mode exists and is a file
     * @return 1 if the file don't exist and 0 otherwise
     */
    public static int verifyModePath() {
        if(!new File(JSON_EVENT).isFile()){
            return 1;
        }
        return 0;
    }

    public static String toStringS() {
        return "event;" + JSON_EVENT + " faction:" + JSON_FACTION + " setting: " +JSON_SETTING;
    }
}
