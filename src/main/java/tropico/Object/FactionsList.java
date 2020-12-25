package tropico.Object;

import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactionsList {
	
	private final ArrayList<Faction> factions= new ArrayList<>();
	private static HashMap<String, String> FACTIONS_NAME;
	
	public FactionsList(String jsonPath) {
		try {
			// create Gson instance
			Gson gson = new Gson();

			// create a reader
			Reader reader = Files.newBufferedReader(Paths.get(jsonPath));
			Reader readerName = Files.newBufferedReader(Paths.get("json/faction/name.json"));
			FACTIONS_NAME = gson.fromJson(readerName, HashMap.class);
			// convert JSON file to map
			Map<?, ?>[] map = gson.fromJson(reader, Map[].class);

			// create faction from value of json file
			for (Map<?, ?> value : map){
				factions.add(new Faction((String)value.get("name"), (double)value.get("partisan"), (double)value.get("fulfillment"), (String)value.get("image")));
			}
			// close reader
			reader.close();
			Reader reader12 = Files.newBufferedReader(Paths.get(jsonPath));
			reader.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static String get(String name) {
		return FACTIONS_NAME.get(name);
	}

	public List<Faction> getFactions(){
		return new ArrayList<>(factions);
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
					faction.changePartisan(effect_partisan.get(effect));
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
		boolean verify = false;
		for(String effect: effect_fulfillment.keySet()) {
			for (Faction faction : factions) {
				if (faction.getEnglishName().equals(effect)){
					faction.changeFulfillment(effect_fulfillment.get(effect).shortValue());
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
}
