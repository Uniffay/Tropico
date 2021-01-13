package tropico.Object;

import tropico.Model.Difficulty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represent event of the game
 */
public class Event implements Serializable {

    /**
     * label of the event
     */
    private final String label;

    /**
     * Choice of the event
     */
    private final ArrayList<Choice> choices;

    /**
     * Season of the event
     */
    private final ArrayList<Season> seasons;
    /**
     * image of the event
     */
    private final String image;

    /**
     * id of the event
     */
    private final int id;

    public Event(String label, String image, int id) {
        this.label = label;
        this.image = image;
        this.id = id;
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

    /**
     *
     * @return list of season where the event can occurred
     */
    public List<Season> getSeason() {
        return seasons;
    }

    /**
     *
     * @return the event label
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @return list of choice the player can choose
     */
    public List<Choice> getChoices() {
        return choices;
    }

    /**
     *
     * @return image that show up with the event
     */
    public String getImage() {
        return image;
    }

    /**
     * remove choice that are not compatible
     * @param difficulty
     *         difficulty of the game
     */
    public void cleanChoice(Difficulty difficulty) {
        if(difficulty == Difficulty.PERSONALIZED)
            return;
        choices.removeIf(choice -> !choice.getDifficulty().contains(difficulty));
    }

    /**
     *
     * @return id of the event
     */
    public int getId() {
        return id;
    }

    public Season getRandomSeason() {
        Random r = new Random();
        return seasons.get(r.nextInt(seasons.size()));
    }
}
