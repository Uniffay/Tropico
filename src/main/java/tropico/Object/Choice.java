package tropico.Object;

import tropico.Model.Difficulty;
import tropico.Model.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Choice implements Serializable {
    /**
     * label of the choice
     */
    private final String label;
    /**
     * difficulty where the choice can be taken
     */
    private final ArrayList<Difficulty> difficulty = new ArrayList<>();
    private final HashMap<String, Integer> effectFulfillment = new HashMap<>();
    private final HashMap<String, Integer> effectPartisan = new HashMap<>();
    private final HashMap<String, Integer> effectResource = new HashMap<>();
    private final ArrayList<Event> nextForMe = new ArrayList<>();
    private final ArrayList<Event> nextForMultiplayer = new ArrayList<>();


    public Choice(String label){
        this.label = label;
    }


    @Override
    public String toString() {
        return toStringGeneral(effectFulfillment, "Satisfaction des ", suffix->"%") +
                toStringGeneral(effectPartisan, "Partisan des ", suffix ->"%") +
                toStringGeneral(effectResource, "", suffix->suffix.equals("money")? "$": "%") ;
    }

    private static String toStringGeneral(HashMap<String, Integer> effects, String prefix, Function<String, String> suffix){
        StringBuilder text = new StringBuilder();
        for (String effect : effects.keySet()) {
            text.append(prefix).append(FactionsList.get(effect)).append(": ").append(Utils.modifiedByDifficulty(effects.get(effect)));
            text.append(suffix.apply(effect));
            text.append("\n");
        }
        return text.toString();
    }

    public String getLabel() {
        return label;
    }

    public Map<String, Integer> getEffect_fulfillment() {
        return effectFulfillment;
    }

    public Map<String, Integer> getEffect_partisan() {
        return effectPartisan;
    }

    public Map<String, Integer> getEffect_resource() {
        return effectResource;
    }

    public ArrayList<Difficulty> getDifficulty() {
        return difficulty;
    }

    public ArrayList<Event> getNextForMe() {
        return nextForMe;
    }

    public ArrayList<Event> getNextForMultiplayer() {
        return nextForMultiplayer;
    }

    public int getPrice(){
        return Math.max(-effectResource.getOrDefault("money", 0), 0);
    }
}
