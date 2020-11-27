package tropico.Object;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class EventManagement {


    public static HashMap<Season , ArrayList<Event>> getEvent(String jsonPath) throws IOException {
        HashMap<Season, ArrayList<Event>> events =  new HashMap<>();
        events.put(Season.SPRING, new ArrayList<>());
        events.put(Season.WINTER, new ArrayList<>());
        events.put(Season.SUMMER, new ArrayList<>());
        events.put(Season.AUTUMN, new ArrayList<>());
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(jsonPath));
            Event[] eventTab = gson.fromJson(reader, Event[].class);

             for (Season season : events.keySet())
                 for (Event event : eventTab) {
                     if(event.getSeason().contains(season)){
                         events.get(season).add(event);
                     }
                 }
        } catch (IOException | JsonIOException ioException) {
            ioException.printStackTrace();
        }
        return events;
    }
}
