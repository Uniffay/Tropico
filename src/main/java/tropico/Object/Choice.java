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
    /**
     * map with name of faction as key and changes of fulfillment as value
     */
    private final HashMap<String, Integer> effectFulfillment = new HashMap<>();
    /**
     * map with name of faction as key and changes of partisan as value
     */
    private final HashMap<String, Integer> effectPartisan = new HashMap<>();
    /**
     * map with name of faction as key and changes of resource as value
     */
    private final HashMap<String, Integer> effectResource = new HashMap<>();
    /**
     * id of unlocked event of playerPlaying
     */
    private final ArrayList<Integer> nextForMe = new ArrayList<>();
    /**
     * id of unlocked event of other player
     */
    private final ArrayList<Integer> nextForMultiplayer = new ArrayList<>();


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

    /**
     * get label of choice
     * @return label of choice
     */
    public String getLabel() {
        return label;
    }

    /**
     * get map with name of faction as key and changes of fulfillment as value
     * @return map with name of faction as key and changes of fulfillment as value
     */
    public Map<String, Integer> getEffect_fulfillment() {
        return effectFulfillment;
    }

    /**
     * get map with name of faction as key and changes of partisan as value
     * @return map with name of faction as key and changes of partisan as value
     */
    public Map<String, Integer> getEffect_partisan() {
        return effectPartisan;
    }

    /**
     * get map with name of faction as key and changes of resource as value
     * @return map with name of faction as key and changes of resource as value
     */
    public Map<String, Integer> getEffect_resource() {
        return effectResource;
    }

    /**
     * get difficulty of choice
     * @return difficulty of choice
     */
    public ArrayList<Difficulty> getDifficulty() {
        return difficulty;
    }

    /**
     * get event unlocked for player playing
     * @return event unlocked for player playing
     */
    public ArrayList<Integer> getNextForMe() {
        return nextForMe;
    }

    /**
     * get event unlocked for other player
     * @return event unlocked for other player
     */
    public ArrayList<Integer> getNextForMultiplayer() {
        return nextForMultiplayer;
    }

    /**
     * get price of event
     * @return price of event
     */
    public int getPrice(){
        return Math.max(-effectResource.getOrDefault("money", 0), 0);
    }
}
