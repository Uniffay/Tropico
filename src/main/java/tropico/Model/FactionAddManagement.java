package tropico.Model;

import tropico.Object.Data;
import tropico.Object.Dictator;
import tropico.Object.Faction;

import java.util.HashMap;
import java.util.function.Predicate;

public class FactionAddManagement {
    private static final HashMap<String, Integer> FACTIONS_SAVE = new HashMap<>();

    public static void initializeFactions(){
        FACTIONS_SAVE.put("capitalist", 0);
        FACTIONS_SAVE.put("ecologist", 0);
        FACTIONS_SAVE.put("nationalist", 0);
        FACTIONS_SAVE.put("religious", 0);
        FACTIONS_SAVE.put("militarist", 0);
        FACTIONS_SAVE.put("liberal", 0);
        FACTIONS_SAVE.put("communist", 0);
    }

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

    public static int get(String name) {
        return FACTIONS_SAVE.get(name);
    }

    public static void validate(Data gameData) {
        Dictator playerPlaying = gameData.getPlayerPlaying();
        Predicate<String> predicate = faction -> FACTIONS_SAVE.get(faction) > 0;
        FACTIONS_SAVE.keySet().stream().filter(predicate).forEach(name->playerPlaying.addFulfillment(name, FACTIONS_SAVE.get(name)));
    }
}
