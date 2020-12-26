package tropico.Object;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Choice {

    private final String label;
    private final HashMap<String, Integer> effectFulfillment = new HashMap<>();
    private final HashMap<String, Integer> effectPartisan = new HashMap<>();
    private final HashMap<String, Integer> effectResource = new HashMap<>();


    public Choice(String label){
        this.label = label;
    }


    @Override
    public String toString() {
        return toStringGeneral(effectFulfillment, "Satisfaction des ", suffix->"%") +
                toStringGeneral(effectPartisan, "Partisan des ", suffix ->"%") +
                toStringGeneral(effectResource, "", suffix->suffix.equals("money")? "$": "%") + "\n";
    }

    private static String toStringGeneral(HashMap<String, Integer> effects, String prefix, Function<String, String> suffix){
        StringBuilder text = new StringBuilder();
        for (String effect : effects.keySet()) {
            text.append(prefix).append(FactionsList.get(effect)).append(": ").append(effects.get(effect));
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

    public int getPrice(){
        return Math.max(-effectResource.getOrDefault("money", 0), 0);
    }
}
