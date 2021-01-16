package tropico.Object;

import tropico.Model.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * manage all the players
 * @author Cléis & Quentin
 */
public class DictatorManagement implements Serializable {


	static final long serialVersionUID = 1174138802860427L;
	/**
	 * list of dictator
	 */
	private final ArrayList<Dictator> dictators = new ArrayList<>();
	
	public DictatorManagement(int number, String[] name, Map<String, Double> resource, String jsonParserFactions) {
		for (int i = 0; i < number; i++) {
			if(i < name.length) {
				dictators.add(new Dictator(name[i], resource, jsonParserFactions));
			}
			else{
				dictators.add(new Dictator("player" + i, resource, jsonParserFactions));
			}
		}
	}

	/**
	 * get the number of element in the list
	 * @return number of element in the list
	 */
	public int size() {
		return dictators.size();
	}

	/**
	 * get the dictator at the given position
	 * @param dictator
	 * 		position of the dictator
	 * @return dictator at the given position
	 */
    public Dictator get(int dictator) {
		return dictators.get(dictator);
    }

	/**
	 * remove a dictator from the list
	 * @param dictator
	 * 		dictator that will be removed
	 */
	public void remove(Dictator dictator) {
		dictators.remove(dictator);
	}

	/**
	 * test if the list is empty
	 * @return true if the list is empty
	 */
	public boolean isEmpty() {
		return dictators.isEmpty();
	}

	/**
	 *  add an event id for all players that match the predicate
	 * @param season
	 * 		List of season of the event
	 * @param id
	 * 		id of the event
	 */

	public void addForAllFilteredPlayer(List<Season> season, Integer id, int turn, Predicate<Dictator> filter) {
    	dictators.stream().filter(filter).forEach(dictator -> dictator.addEvent(Utils.getRandom(season), id, turn));
	}

	/**
	 * ad en event id for all player
	 * @param seasons
	 * 		List of season of the event
	 * @param id
	 * 		id of the event
	 */
	public void addForAllFilteredPlayer(List<Season> seasons, int turn, int id) {
		addForAllFilteredPlayer(seasons, id, turn, dictator -> true);
	}

	/**
	 * return stream of dictator
	 * @return stream of dictator
	 */
	public Stream<Dictator> stream(){
		return dictators.stream();
	}
}
