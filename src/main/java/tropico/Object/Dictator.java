package tropico.Object;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Dictator {

	private final String name;
	private final HashMap<String, Integer> resource = new HashMap<>();
	private final FactionsList factions;
	
	public Dictator(String name, String jsonPathResource, String jsonPathFaction) {
		this.name = name;
		try {
			Gson gson = new Gson();
			Reader reader = Files.newBufferedReader(Paths.get(jsonPathResource));
			Map<String, Double> map = gson.fromJson(reader, Map.class);
			resource.put("farming", map.get("farming").intValue());
			resource.put("industry", map.get("industry").intValue());
			if(resource.get("farming") + resource.get("industry") > 100) {
				throw new IllegalArgumentException("sum of farming and industry musn't be superior to 100");
			}
			resource.put("money", map.get("money").intValue());
		}catch (IOException e){
			e.printStackTrace();
		}
		factions = new FactionsList(jsonPathFaction);
	}
	
	public Dictator(String name) {
		this(name, "json/setting/setting.json", "json/faction/faction.json");
	}

	@Override
	public String toString(){
		return name + ":\n" + resource + factions;
	}

	public FactionsList getFactions(){
		return factions;
	}

    public void haveChosen(Choice choice) {
		if(choice.getEffect_resource() != null){
			for (String effect: choice.getEffect_resource().keySet()){
				try {
					int resourceValue = choice.getEffect_resource().get(effect);
					if (!effect.equals("money")) {
						if (resource.get("farming") + resourceValue + resource.get("industry") > 100) {
							resourceValue = 100 - (resource.get("farming") + resource.get("industry"));
						}
					}
					resource.replace(effect, Math.max(resource.get(effect) + resourceValue, 0));
				} catch (NullPointerException e){
					throw new IllegalArgumentException(effect + " is not a resource");
				}
			}
		}
		if(choice.getEffect_fulfillment() != null){
			factions.changeFulfillment(choice.getEffect_fulfillment());
		}
		if(choice.getEffect_partisan() != null){
			factions.changePartisan(choice.getEffect_partisan());
		}
    }

	public HashMap<String, Integer> getResource() {
		return resource;
	}


}
