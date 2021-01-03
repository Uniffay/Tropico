package tropico.Object;

import java.util.ArrayList;
import java.util.stream.Stream;

public class DictatorManagement {
	
	private final ArrayList<Dictator> dictators = new ArrayList<>();
	
	public DictatorManagement(int number, String[] name, String jsonParserResource, String jsonParserFactions) {
		for (int i = 0; i < number; i++) {
			if(i < name.length) {
				dictators.add(new Dictator(name[i], jsonParserResource, jsonParserFactions));
			}
			else{
				dictators.add(new Dictator("player" + i, jsonParserResource, jsonParserFactions));
			}
		}
	}

	public int size() {
		return dictators.size();
	}

    public Dictator get(int dictator) {
		return dictators.get(dictator);
    }

    public Stream<Dictator> stream(){
		return dictators.stream();
	}
}
