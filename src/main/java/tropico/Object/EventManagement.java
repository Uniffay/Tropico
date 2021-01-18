package tropico.Object;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import tropico.Model.Difficulty;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
/**
 * static methods for managing all event in the game
 * @author Cléis & Quentin
 */
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
            if(event.isUnlocked())
                players.addForAllFilteredPlayer(event.getSeason(), 0, event.getId());
        }
        return events;
    }

    /**
     * instantiate all event in the directory event.
     * @return Map with id event as key and event as value
     */
    public static Map<Integer, Event> instantiateAllEvent(Difficulty difficulty, DictatorManagement players) {
        HashMap<Integer, Event> eventsByID = new HashMap<>();
        File directory = Path.of("json/event").toFile();
        File[] listOfFiles = directory.listFiles();
        int id = 0;
        for(int i = 0; i < listOfFiles.length; i++){
            id = manageAllEvent(eventsByID, listOfFiles, i, id, difficulty, players);
        }
        return eventsByID;
    }

    private static int manageAllEvent(HashMap<Integer, Event> eventsByID, File[] listOfFiles,
                                      int i, int id, Difficulty difficulty, DictatorManagement players){
        try {
            Event[] events = getEvenFromJson(listOfFiles[i].getPath());
            for (Event event: events){
                event.cleanChoice(difficulty);
                eventsByID.put(id, event);
                event.setId(id);
                id++;
                if(event.isUnlocked())
                    players.addForAllFilteredPlayer(event.getSeason(), 0, event.getId());
            }
        }catch (IOException ignored){}
        return id;
    }
}
