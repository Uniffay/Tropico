package tropico.Object;

import java.util.HashMap;

public class Choice {

    private final String label;
    private HashMap<String, Integer> effect;

    public Choice(String label){
        this.label = label;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "label='" + label + '\'' +
                ", effect=" + effect +
                '}';
    }

    public String getLabel() {
        return label;
    }
}
