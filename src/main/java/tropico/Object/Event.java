package tropico.Object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Event implements Serializable {

    private final String label;
    private final ArrayList<Choice> choices;
    private final ArrayList<Season> seasons;
    private final String image;


    public Event(String label, ArrayList<Event> nextForMe, ArrayList<Event> nextForMultiplayer, String image) {
        this.label = label;
        this.image = image;
        this.choices = new ArrayList<>();
        this.seasons = new ArrayList<>();

    }

    @Override
    public String toString() {
        return "Event{" +
                "label='" + label + '\'' +
                ", choices=" + choices +
                ", seasons=" + seasons +
                '}';
    }

    public List<Season> getSeason() {
        return seasons;
    }

    public String getLabel() {
        return label;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public String getImage() {
        return image;
    }
}
