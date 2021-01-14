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

    /**
     *      Instantiate all the event and put them in a map
     * @param jsonPath
     *      path to the json file that contains the event
     * @param difficulty
     *      difficulty of the game
     * @param players
     *      all players of the game
     * @return a map with id event as key and event as value
     */
    public static Map<Integer, Event> instantiateEventMap(String jsonPath, Difficulty difficulty, DictatorManagement players) {
        try {
            var eventTab = getEvenFromJson(jsonPath);
            return setEventForSeason(eventTab, difficulty, players);
        } catch (IOException | JsonIOException ioException) {
            ioException.printStackTrace();
        }
        return null;

    }

    private static Event[] getEvenFromJson(String jsonPath) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(jsonPath));
        return gson.fromJson(reader, Event[].class);
    }

    private static HashMap<Integer, Event> setEventForSeason(Event[] eventTab, Difficulty difficulty, DictatorManagement players){
        HashMap<Integer, Event> events = new HashMap<>();
        for (Event event : eventTab) {
            event.cleanChoice(difficulty);
            events.put(event.getId(), event);
            players.addForAllFilteredPlayer(event.getSeason(), 0, event.getId());
            Arrays.stream(Season.values()).forEach(season -> players.addForAllFilteredPlayer(
                    new ArrayList<>(Collections.singleton(season)), 0,event.getId()));
        }
        return events;
    }
}
