package tropico.Object;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class EventManagement {

    public static Map<Season , List<Event>> getEvent(String jsonPath) throws IOException {
        Map<Season, List<Event>> events =  initialiseMapSeasonEvent();
        try {
            var eventTab = getEvenFromJson(jsonPath);
            for (Season season : events.keySet())
                events.get(season).addAll(getEventForSeason(season, eventTab));
        } catch (IOException | JsonIOException ioException) {
            ioException.printStackTrace();
        }
        return events;
    }

    private static Event[] getEvenFromJson(String jsonPath) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(jsonPath));
        return gson.fromJson(reader, Event[].class);
    }

    private static Map<Season, List<Event>> initialiseMapSeasonEvent(){
        Map<Season, List<Event>> events =  new HashMap<>();
        events.put(Season.SPRING, new ArrayList<>());
        events.put(Season.WINTER, new ArrayList<>());
        events.put(Season.SUMMER, new ArrayList<>());
        events.put(Season.AUTUMN, new ArrayList<>());
        return events;
    }

    private static List<Event> getEventForSeason(Season season, Event[] eventTab){
        List<Event> events = new ArrayList<>();
        for (Event event : eventTab) {
            if(event.getSeason().contains(season)){
                events.add(event);
            }
        }
        return events;
    }
}
