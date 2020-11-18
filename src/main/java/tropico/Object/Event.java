package tropico.Object;

import java.util.ArrayList;

public class Event {

    private String label;
    private final ArrayList<Choice> choices;
    private final Event next;


    public Event(String label, Event next) {
        this.label = label;
        this.choices = new ArrayList<>();
        this.next = next;

    }

    @Override
    public String toString() {
        return "Event{" +
                "label='" + label + '\'' +
                ", choices=" + choices +
                ", next=" + next +
                '}';
    }
}
