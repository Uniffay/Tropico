package tropico.Model;

import tropico.Object.Data;

public class FoodManagement {
    private static int FOOD_SAVE;
    private static int FOOD_NEEDED;
    private static int FOOD_BOUGHT;

    public static int getFoodMissing() {
        return FOOD_NEEDED - FOOD_SAVE;
    }

    public static int getFoodMissingWithFoodBought() {
        return FOOD_NEEDED - (FOOD_SAVE + FOOD_BOUGHT);
    }

    public static int getFoodNeeded() {
        return FOOD_NEEDED;
    }

    public static int getFoodBought() {
        return FOOD_BOUGHT;
    }

    public static int getFood() {
        return FOOD_SAVE;
    }

    public static void initialize(Data gameData) {
        FOOD_SAVE = gameData.getPlayerPlaying().getResource().get("farming") * 40;
        FOOD_NEEDED = gameData.getPlayerPlaying().getFactions().getTotalPartisan() * 4;
        FOOD_BOUGHT = 0;
    }

    public static int addFoodBought(int number) {
        int missingFood = Math.max(getFoodMissing(), 0);
        int foodBoughtBefore = FOOD_BOUGHT;
        FOOD_BOUGHT = (missingFood < FOOD_BOUGHT + number)? missingFood : Math.max(FOOD_BOUGHT + number, 0);
        return FOOD_BOUGHT - foodBoughtBefore;
    }

    public static void validate(Data gameData) {
        System.out.println(getFoodMissing() - FOOD_BOUGHT);
        gameData.getPlayerPlaying().managePartisan(getFoodMissing() - FOOD_BOUGHT);
    }
}

