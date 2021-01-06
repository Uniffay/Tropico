package tropico.Model;

import javafx.scene.control.TextField;

import java.io.File;

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

    public static String getJsonEvent() {
        return JSON_EVENT;
    }

    public static String getJsonFaction() {
        return JSON_FACTION;
    }

    public static String getJsonSetting() {
        return JSON_SETTING;
    }

    public static String createPath(String nameSetting, String directory) {
        return "json/" + directory + "/" + nameSetting + ".json";
    }

    public static int verifyDifficultyPath() {
        if(!new File(JSON_SETTING).isFile()){
            return 1;
        }
        if(!new File(JSON_FACTION).isFile()){
            return 2;
        }
        return 0;
    }

    public static int verifyModePath() {
        if(!new File(JSON_EVENT).isFile()){
            return 1;
        }
        return 0;
    }
}
