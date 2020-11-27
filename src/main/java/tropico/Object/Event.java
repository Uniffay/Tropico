package tropico.Object;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

    public ArrayList<Season> getSeason() {
        return seasons;
    }

    public String getLabel() {
        return label;
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }
}
