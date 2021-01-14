package tropico.Model;

import tropico.Object.Data;
import tropico.Object.Dictator;

import java.util.HashMap;
import java.util.function.Predicate;

/**
 * manage the faction fulfillment modification for the bribe at the end of the year
 * @author Quentin & Cléis
 */
public class FactionAddManagement {
    /**
     * stock faction changes fulfillment from bribe
     */
    private static final HashMap<String, Integer> FACTIONS_SAVE = new HashMap<>();

    /**
     * initialize field
     */
    public static void initializeFactions(){
        FACTIONS_SAVE.put("capitalist", 0);
        FACTIONS_SAVE.put("ecologist", 0);
        FACTIONS_SAVE.put("nationalist", 0);
        FACTIONS_SAVE.put("religious", 0);
        FACTIONS_SAVE.put("militarist", 0);
        FACTIONS_SAVE.put("liberal", 0);
        FACTIONS_SAVE.put("communist", 0);
    }

    /**
     * add fulfillment to the field that stock the change for the faction name given
     * @param name
     *      name of the faction
     * @param number
     *      number added
     * @return number modified (can be different of number if max or min is reached)
     */
    public static int addFactionFulfillment(String name, int number){
        Data gameData = DataManagement.getData();
        // we take actual faction fulfillment
        int factionFulfillment = gameData.getPlayerPlaying().getFactions().getFaction(name).getFulfillment();
        // we take old Fulfillment added saved
        int oldFulfillment = FACTIONS_SAVE.get(name);
        //we made the new fulfillment with the round to superior number added + 2 previous fulfillment
        int newFulfillment = factionFulfillment + ((number + oldFulfillment + 9) / 10) * 10;
        // +9 / 10 * 10 to round to superior (11 + 9 = 20 / 10 = 2 * 10 = 20)
        //total fulfillment cant be inferior to 100
        newFulfillment = Math.min(newFulfillment, 100);
        //total fulfillment need to be at least faction fulfillment
        newFulfillment = Math.max(newFulfillment, factionFulfillment);
        //we only take added fulfillment
        newFulfillment = newFulfillment - factionFulfillment;
        FACTIONS_SAVE.replace(name, newFulfillment);
        return newFulfillment - oldFulfillment;
    }

    /**
     * get changes fulfillment of a faction
     * @param name
     *      faction name
     * @return changes fulfillment
     */
    public static int get(String name) {
        return FACTIONS_SAVE.get(name);
    }

    /**
     * modify fulfillment of faction by the changes stocked in the field of this class
     * @param gameData
     *      data of the game
     */
    public static void validate(Data gameData) {
        Dictator playerPlaying = gameData.getPlayerPlaying();
        Predicate<String> predicate = faction -> FACTIONS_SAVE.get(faction) > 0;
        FACTIONS_SAVE.keySet().stream().filter(predicate).forEach(name->playerPlaying.bribeFulfillment(name, FACTIONS_SAVE.get(name)));

    }
}
