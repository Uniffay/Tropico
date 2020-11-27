package tropico.Object;

import java.util.HashMap;

public class Choice {

    private final String label;
    private HashMap<String, Integer> effect_fulfillment;
    private HashMap<String, Integer> effect_partisan;
    private HashMap<String, Integer> effect_resource;


    public Choice(String label){
        this.label = label;
    }


    @Override
    public String toString() {
        return "Choice{" +
                "label='" + label + '\'' +
                ", effect_fulfillment=" + effect_fulfillment +
                ", effect_partisan=" + effect_partisan +
                ", effect_resource=" + effect_resource +
                '}';
    }

    public String getLabel() {
        return label;
    }

    public HashMap<String, Integer> getEffect_fulfillment() {
        return effect_fulfillment;
    }

    public HashMap<String, Integer> getEffect_partisan() {
        return effect_partisan;
    }

    public HashMap<String, Integer> getEffect_resource() {
        return effect_resource;
    }
}
