package tropico.Object;

import java.util.ArrayList;

public class DictatorManagement {
	
	private final ArrayList<Dictator> dictators = new ArrayList<>();
	
	public DictatorManagement(int number, String[] name, String jsonParserRessource, String jsonParserFactions) {
		for (int i = 0; i < number; i++) {
			if(i < name.length) {
				dictators.add(new Dictator(name[i], jsonParserRessource, jsonParserFactions));
			}
			else{
				dictators.add(new Dictator("player" + i, jsonParserRessource, jsonParserFactions));
			}
		}
	}

	public int size() {
		return dictators.size();
	}

    public Dictator get(int dictator) {
		return dictators.get(dictator);
    }
}
