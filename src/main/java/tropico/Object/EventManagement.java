package tropico.Object;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import tropico.Model.Difficulty;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class EventManagement {

    public static Map<Season , Map<Integer, Event>> getEvent(String jsonPath, Difficulty difficulty, DictatorManagement players) throws IOException {
        Map<Season, Map<Integer, Event>> events =  initialiseMapSeasonEvent();
        try {
            var eventTab = getEvenFromJson(jsonPath);
            setEventForSeason(events, eventTab, difficulty, players);
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

    private static Map<Season, Map<Integer, Event>> initialiseMapSeasonEvent(){
        Map<Season, Map<Integer, Event>> events =  new HashMap<>();
        events.put(Season.SPRING, new HashMap<>());
        events.put(Season.WINTER, new HashMap<>());
        events.put(Season.SUMMER, new HashMap<>());
        events.put(Season.AUTUMN, new HashMap<>());
        return events;
    }

    private static void setEventForSeason(Map<Season, Map<Integer, Event>> eventBySeason,
                                          Event[] eventTab, Difficulty difficulty, DictatorManagement players){
        HashMap<Integer, Event> events = new HashMap<>();
        for (Event event : eventTab) {
            event.cleanChoice(difficulty);
            event.getSeason().forEach(season -> eventBySeason.get(season).put(event.getId(), event));
            //players.addForAllPlayer(event.getRandomSeason(), event.getId());
            Arrays.stream(Season.values()).forEach(season -> players.addForAllPlayer(season, event.getId()));
        }
    }
}
