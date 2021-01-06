package tropico.Model;

import tropico.Object.Data;
import tropico.Object.Faction;

import java.util.HashMap;

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
        Faction faction = gameData.getPlayerPlaying().getFactions().getFaction(name);
        int factionsStart = FACTIONS_SAVE.get(name);
        if(faction.getFulfillment() + FACTIONS_SAVE.get(name) + number > 100){
            FACTIONS_SAVE.replace(name, 100 - faction.getFulfillment());
            return FACTIONS_SAVE.get(name) - factionsStart;
        }
        if(FACTIONS_SAVE.get(name) + number < 0){
            FACTIONS_SAVE.replace(name, 0);
            return FACTIONS_SAVE.get(name) - factionsStart;
        }
        FACTIONS_SAVE.replace(name, FACTIONS_SAVE.get(name) + number);
        return FACTIONS_SAVE.get(name) - factionsStart;


    }

    public static int get(String name) {
        return FACTIONS_SAVE.get(name);
    }
}
