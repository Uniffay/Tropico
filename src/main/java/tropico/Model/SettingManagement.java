package tropico.Model;

import javafx.scene.control.MenuItem;

public class SettingManagement {
    private static MenuItem DIFFICULTY_SAVE;
    private static MenuItem MODE_SAVE;

    public static void setDifficulty(MenuItem difficulty) {
        DIFFICULTY_SAVE = difficulty;
    }

    public static MenuItem getDifficulty() {
        return DIFFICULTY_SAVE;
    }

    public static void setMode(MenuItem mode) {
        MODE_SAVE = mode;
    }

    public static MenuItem getMode() {
        return MODE_SAVE;
    }

    public static Difficulty getDifficultyFromMenuItem() {
        switch (DIFFICULTY_SAVE.getText()){
            case "Facile":
                return Difficulty.EASY;
            case "Moyen":
                return Difficulty.MEDIUM;
            case "Difficile":
                return Difficulty.DIFFICULT;
            default:
                return Difficulty.PERSONALIZED;
        }
    }

    public static Mode getModeFromMenuItem() {
        switch (MODE_SAVE.getText()){
            case "Sea of Thieves":
                return Mode.SEAOFTHIEVES;
            case "Bac à Sable":
                return Mode.SANDBOX;
            case "Mario":
                return Mode.MARIO;
            case "IUT":
                return Mode.IUT;
            default:
                return Mode.PERSONALIZED;
        }
    }
}
