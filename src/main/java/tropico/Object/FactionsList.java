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

public class FactionsList implements Serializable {

	static final long serialVersionUID = 185174138802860427L;
	
	private final ArrayList<Faction> factions= new ArrayList<>();
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
				factions.add(new Faction((String)value.get("name"), (double)value.get("partisan"), (double)value.get("fulfillment"), (String)value.get("image")));
			}
			// close reader
			reader.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static String get(String name) {
		return FACTIONS_NAME.get(name);
	}

	public List<Faction> getFactions(){
		return factions;
	}

	public Faction getFaction(String name){
		return factions.stream().filter(f -> f.getEnglishName().equals(name)).findFirst().orElse(null);
	}

	@Override
	public String toString(){
		return factions.toString();
	}

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

	public void changeFulfillment(Map<String, Integer> effect_fulfillment) {
		for(String effect: effect_fulfillment.keySet()) {
			if(!changeFulfillmentFaction(effect, effect_fulfillment)){
				throw new IllegalArgumentException(effect + " is not a faction");
			}
		}
	}

	private boolean changeFulfillmentFaction(String effect, Map<String, Integer> effect_fulfillment){
		for (Faction faction : factions) {
			if (faction.getEnglishName().equals(effect)){
				faction.changeFulfillment((short)Utils.modifiedByDifficulty(effect_fulfillment.get(effect)));
				return true;
			}
		}
		return false;
	}

	public int getTotalPartisan() {
		return factions.stream().mapToInt(Faction::getPartisan).sum();
	}

	public void killARandomPartisan() {
		getRandomFactionFilter(faction -> faction.getPartisan() > 0).killAPartisan();
	}

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

	public int addFulfillment(String name, Integer number) {
		return getFaction(name).addFulfillment(number);
	}

    public void loseFulfillment(int number) {
		factions.forEach(faction -> faction.loseFulfillment(number));
    }

	public int getAverageFulfillment() {
		int partisans = 0;
		int sumFulfillment = 0;
		int factionPartisan = 0;
		for (Faction faction: factions){
			factionPartisan = faction.getPartisan();
			partisans += factionPartisan;
			sumFulfillment += faction.getFulfillment() * factionPartisan;
		}
		return (partisans == 0) ? 0 : sumFulfillment / partisans;
	}

	public static void initializeFactionName() throws IOException {
		Gson gson = new Gson();
		Reader readerName = Files.newBufferedReader(Path.of("json/faction/name.json"));
		FACTIONS_NAME = gson.fromJson(readerName, HashMap.class);
	}
}
