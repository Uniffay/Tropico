package tropico.Model;

import javafx.scene.control.MenuItem;

/**
 * SettingManagement help in managing the choice of the mode and difficulty made by
 * the user.
 * @author Quentin and Cléis
 */
public class SettingManagement {
    /**
     * The current difficulty in the menu
     */
    private static MenuItem DIFFICULTY_SAVE;
    /**
     * the current mode in the menu
     */
    private static MenuItem MODE_SAVE;

    /**
     * Update difficulty in the menu
     * @param difficulty
     *        new difficulty in the menu
     */
    public static void setDifficulty(MenuItem difficulty) {
        DIFFICULTY_SAVE = difficulty;
    }

    /**
     *
     * @return menuItem where the string of the difficulty is written
     */
    public static MenuItem getDifficulty() {
        return DIFFICULTY_SAVE;
    }

    /**
     * Update mode in the menu
     * @param mode
     *         new mode in the menu
     */
    public static void setMode(MenuItem mode) {
        MODE_SAVE = mode;
    }

    /**
     *
     * @return mode in the menu
     */
    public static MenuItem getMode() {
        return MODE_SAVE;
    }


    /**
     *
     * @return an Instance of Difficulty, that represent the difficult in the menu
     */
    public static Difficulty getDifficultyFromMenuItem() {
        switch (DIFFICULTY_SAVE.getText()){
            case "Facile":
                return Difficulty.EASY;
            case "Moyen":
                return Difficulty.MEDIUM;
            case "Difficile":
                return Difficulty.HARD;
            default:
                return Difficulty.PERSONALIZED;
        }
    }

    /**
     *
     * @return an Instance of Mode, that represent the mode in the menu
     */
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
