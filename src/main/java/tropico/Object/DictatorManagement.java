package tropico.Object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class DictatorManagement implements Serializable {
	
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

	public int size() {
		return dictators.size();
	}

    public Dictator get(int dictator) {
		return dictators.get(dictator);
    }

	public void remove(Dictator dictator) {
		dictators.remove(dictator);
	}

	public boolean isEmpty() {
		return dictators.isEmpty();
	}

    public void addForAllPlayer(Season season, int id) {
		dictators.forEach(dictator -> dictator.addEvent(season, id));
    }
}
