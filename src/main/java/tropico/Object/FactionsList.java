package tropico.Object;

import com.google.gson.Gson;
import tropico.Model.Utils;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * manage all the faction of a player
 * @author Cléis & Quentin
 */
public class FactionsList implements Serializable {

	static final long serialVersionUID = -1988109764979679938L;

	/**
	 * list of all the factions
	 */
	private final ArrayList<Faction> factions= new ArrayList<>();

	/**
	 * Map with english name as faction and french name as value
	 */
	private static HashMap<String, String> FACTIONS_NAME;
	
	public FactionsList(String jsonPath) {
		try {
			initializeFactionName();
			// create Gson instance
			Gson gson = new Gson();

			// create a reader
			Reader reader = Files.newBufferedReader(Path.of(jsonPath));

			// convert JSON file to map
			Map<?, ?>[] map = gson.fromJson(reader, Map[].class);

			// create faction from value of json file
			for (Map<?, ?> value : map){
				factions.add(new Faction((double)value.get("partisan"), (double)value.get("fulfillment"), (String)value.get("image")));
			}
			// close reader
			reader.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * get the french name of the faction corresponding to name (in english)
	 * @param name
	 * 		name in english of the faction
	 * @return name in french
	 */
	public static String get(String name) {
		return FACTIONS_NAME.get(name);
	}

	/**
	 * get list all factions
	 * @return list of all factions
	 */
	public List<Faction> getFactions(){
		return factions;
	}

	/**
	 * get the faction corresponding to the name
	 * @param name
	 * 		english name of the faction
	 * @return faction corresponding to the name
	 */
	public Faction getFaction(String name){
		return factions.stream().filter(f -> f.getEnglishName().equals(name)).findFirst().orElse(null);
	}

	@Override
	public String toString(){
		return factions.toString();
	}

	/**
	 * change partisan for all faction in the map by the integer value
	 * @param effect_partisan
	 * 		map with faction name as key and changes as value
	 */
	public void changePartisan(Map<String, Integer> effect_partisan) {
		boolean verify = false;
		for(String effect: effect_partisan.keySet()) {
			for (Faction faction : factions) {
				if (faction.getEnglishName().equals(effect)){
					faction.changePartisan(Utils.modifiedByDifficulty(effect_partisan.get(effect)));
					verify = true;
					break;
				}
			}
			if(!verify){
				throw new IllegalArgumentException(effect + " is not a faction");
			}
			verify = false;
		}
	}

	/**
	 * change fulfillment for all faction in the map by the integer value
	 * @param effect_fulfillment
	 * 		map with name faction as key and changes as value
	 */
	public void changeFulfillment(Map<String, Integer> effect_fulfillment) {
		for(String effect: effect_fulfillment.keySet()) {
			if(!changeFulfillmentFaction(effect, effect_fulfillment)){
				throw new IllegalArgumentException(effect + " is not a faction");
			}
		}
	}

	/**
	 * change fulfillment of a faction
	 * @param effect
	 * 		name of the faction affected
	 * @param effect_fulfillment
	 * 		map with the name of the faction as key and value to add as value
	 * @return true if the faction were found false otherwise
	 */
	private boolean changeFulfillmentFaction(String effect, Map<String, Integer> effect_fulfillment){
		for (Faction faction : factions) {
			if (faction.getEnglishName().equals(effect)){
				faction.changeFulfillment((short)Utils.modifiedByDifficulty(effect_fulfillment.get(effect)));
				return true;
			}
		}
		return false;
	}

	/**
	 * get the total number of partisan
	 * @return total number of partisan
	 */
	public int getTotalPartisan() {
		return factions.stream().mapToInt(Faction::getPartisan).sum();
	}

	/**
	 * kill a partisan of a random faction
	 */
	public void killARandomPartisan() {
		getRandomFactionFilter(faction -> faction.getPartisan() > 0).killAPartisan();
	}

	/**
	 * add a number of partisan equals to a random percentage between 1 to 10% to random faction
	 */
	public void birth() {
		int randomInteger = getRandomInteger(10) + 1;
		while(randomInteger > 0) {
			getRandomFaction().addAPartisan();
			randomInteger --;
		}
	}

	private Faction getRandomFactionFilter(Predicate<Faction> filter) {
		Random r = new Random();
		List<Faction> factionFiltered = factions.stream().filter(filter).collect(Collectors.toList());
		return factionFiltered.get(r.nextInt(factionFiltered.size()));
	}

	private int getRandomInteger(int bounds){
		Random random = new Random();
		return random.nextInt(bounds);
	}

	private Faction getRandomFaction(){
		return factions.get(getRandomInteger(factions.size()));
	}

	/**
	 * add fulfillment to a faction
	 * @param name
	 * 		name of the faction
	 * @param number
	 * 		added fulfillment
	 * @return level of fulfillment added (1 level = 10%)
	 */
	public int addFulfillment(String name, Integer number) {
		return getFaction(name).addFulfillment(number);
	}

	/**
	 * remove fulfillment of all faction by number
	 * @param number
	 * 		number of fulfillment to remove
	 */
    public void loseFulfillment(int number) {
		factions.forEach(faction -> faction.loseFulfillment(number));
    }

	/**
	 * get the average fulfillment of all the factions
	 * @return average fulfillment
	 */
	public int getAverageFulfillment() {
		int partisans = 0;
		int sumFulfillment = 0;
		int factionPartisan;
		for (Faction faction: factions){
			factionPartisan = faction.getPartisan();
			partisans += factionPartisan;
			sumFulfillment += faction.getFulfillment() * factionPartisan;
		}
		return (partisans == 0) ? 0 : sumFulfillment / partisans;
	}

	/**
	 * initialise a Hashmap with the name of the faction in english as key and in french as value
	 * @throws IOException if an I/O error occurs
	 */
	public static void initializeFactionName() throws IOException {
		Gson gson = new Gson();
		Reader readerName = Files.newBufferedReader(Path.of("json/faction/name.json"));
		FACTIONS_NAME = gson.fromJson(readerName, HashMap.class);
	}
}
