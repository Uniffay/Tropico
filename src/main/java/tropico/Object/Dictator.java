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
	private int debt = 0;
	
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
		for (String effect: choice.getEffect_resource().keySet()){
			try {
				int resourceValue = choice.getEffect_resource().get(effect);
				if (effect.equals("money")) {
					resource.replace(effect, resource.get(effect) + resourceValue);
					continue;
				}
				resourceValue = (resource.get("farming") + resourceValue + resource.get("industry") > 100) ? 100 - (resource.get("farming") + resource.get("industry")): resourceValue;
				resource.replace(effect, Math.max(resource.get(effect) + resourceValue, 0));
			} catch (NullPointerException e){
				throw new IllegalArgumentException(effect + " is not a resource");
			}
		}
		factions.changeFulfillment(choice.getEffect_fulfillment());
		factions.changePartisan(choice.getEffect_partisan());
    }

	public Map<String, Integer> getResource() {
		return new HashMap<>(resource);
	}


	public boolean canLoan(int loanValue) {
		return debt + loanValue <= 10000;
	}

	public void addDebt(int loanValue) {
		debt += loanValue * 1.1;
	}

	public void addMoney(int loanValue){
		resource.replace("money", resource.get("money") + loanValue);
	}

	public void addInterest() {
		debt += debt * 0.1;
	}

	public boolean haveDebt() {
		return debt > 0;
	}

	public int getDebt() {
		return debt;
	}
}
