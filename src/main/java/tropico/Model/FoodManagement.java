package tropico.Model;

import tropico.Object.Data;

/**
 * manage the food importation at the end of the year
 * @author Cléis & Quentin
 */
public class FoodManagement {
    /**
     * stock value of possessed food
     */
    private static int FOOD_SAVE;
    /**
     * stock value of food needed
     */
    private static int FOOD_NEEDED;
    /**
     * stock value of food bought
     */
    private static int FOOD_BOUGHT;

    /**
     * get total food missing
     * @return food missing
     */
    public static int getFoodMissing() {
        return FOOD_NEEDED - FOOD_SAVE;
    }

    /**
     * get food missing while counting food that will be bought
     * @return food missing
     */
    public static int getFoodMissingWithFoodBought() {
        return FOOD_NEEDED - (FOOD_SAVE + FOOD_BOUGHT);
    }

    /**
     * get food needed
     * @return food needed
     */
    public static int getFoodNeeded() {
        return FOOD_NEEDED;
    }

    /**
     * get food that will be bought
     * @return food that will be bought
     */
    public static int getFoodBought() {
        return FOOD_BOUGHT;
    }

    /**
     * get food
     * @return food
     */
    public static int getFood() {
        return FOOD_SAVE;
    }

    /**
     * initialize field of class
     * @param gameData
     *      Data of the game
     */
    public static void initialize(Data gameData) {
        FOOD_SAVE = gameData.getPlayerPlaying().getResource().get("farming") * 40;
        FOOD_NEEDED = gameData.getPlayerPlaying().getFactions().getTotalPartisan() * 4;
        FOOD_BOUGHT = 0;
    }

    /**
     * modify value of food bought by number
     * @param number
     *      number of food bought
     * @return value modified (can be different than number if min or max is reached)
     */
    public static int addFoodBought(int number) {
        int missingFood = Math.max(getFoodMissing(), 0);
        int foodBoughtBefore = FOOD_BOUGHT;
        FOOD_BOUGHT = (missingFood < FOOD_BOUGHT + number)? missingFood : Math.max(FOOD_BOUGHT + number, 0);
        return FOOD_BOUGHT - foodBoughtBefore;
    }

    /**
     * manage partisan when user valid his choice
     * @param gameData
     *      data of the game
     */
    public static void validate(Data gameData) {
        gameData.getPlayerPlaying().managePartisan(getFoodMissing() - FOOD_BOUGHT);
    }
}

