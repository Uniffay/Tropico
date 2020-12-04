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
        StringBuilder text = new StringBuilder();
        if(effect_fulfillment != null) {
            for (String effect : effect_fulfillment.keySet()) {
                text.append("Satisfaction des ").append(effect).append(": ").append(effect_fulfillment.get(effect)).append("%\n");
            }
        }
        if(effect_partisan != null) {
            for (String effect : effect_partisan.keySet()) {
                text.append("Partisan des ").append(FactionsList.get(effect)).append(": ").append(effect_partisan.get(effect)).append("%\n");
            }
        }
        if(effect_resource != null) {
            for (String effect : effect_resource.keySet()) {
                text.append("Ressource ").append(effect).append(": ").append(effect_resource.get(effect));
                if(!effect.equals("money")){
                    text.append("%");
                }
                else{
                    text.append("$");
                }
                text.append("\n");
            }
        }
        return text.toString();
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
