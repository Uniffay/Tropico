package tropico.Object;

import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FactionsList {
	
	private final ArrayList<Faction> factions= new ArrayList<>();;
	
	public FactionsList(String jsonPath) {
		try {
			// create Gson instance
			Gson gson = new Gson();

			// create a reader
			Reader reader = Files.newBufferedReader(Paths.get(jsonPath));

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
	
	public ArrayList<Faction> getFactions(){
		return factions;
	}

	@Override
	public String toString(){
		return factions.toString();
	}

	public void changePartisan(HashMap<String, Integer> effect_partisan) {
		for(String effect: effect_partisan.keySet()) {
			for (Faction faction : factions) {
				if (faction.getEnglishName().equals(effect)){
					faction.changePartisan(effect_partisan.get(effect));
					break;
				}
			}
		}
	}

	public void changeFulfillment(HashMap<String, Integer> effect_fulfillment) {
		for(String effect: effect_fulfillment.keySet()) {
			for (Faction faction : factions) {
				if (faction.getEnglishName().equals(effect)){
					faction.changeFulfillment(effect_fulfillment.get(effect).shortValue());
					break;
				}
			}
		}
	}
}
