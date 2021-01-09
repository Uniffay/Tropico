package tropico.Object;

import java.util.ArrayList;
import java.util.List;

public class Event {

    private String label;
    private final ArrayList<Choice> choices;
    private final ArrayList<Season> seasons;
    private final Event next;


    public Event(String label, Event next) {
        this.label = label;
        this.choices = new ArrayList<>();
        this.seasons = new ArrayList<>();
        this.next = next;

    }

    @Override
    public String toString() {
        return "Event{" +
                "label='" + label + '\'' +
                ", season =" + seasons +
                ", choices=" + choices +
                ", next=" + next +
                '}';
    }

    public List<Season> getSeason() {
        return new ArrayList<>(seasons);
    }

    public String getLabel() {
        return label;
    }

    public List<Choice> getChoices() {
        return new ArrayList<>(choices);
    }
}
