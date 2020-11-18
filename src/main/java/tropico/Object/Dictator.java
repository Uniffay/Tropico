package tropico.Object;

public class Dictator {

	private final String name;
	private final Resource resource;
	private final FactionsList factions;
	
	public Dictator(String name, String jsonPathResource, String jsonPathFaction) {
		this.name = name;
		this.resource = new Resource(jsonPathResource);
		factions = new FactionsList(jsonPathFaction);
	}
	
	public Dictator(String name) {
		this(name, "json/setting/setting.json", "json/faction/faction.json");
	}

	@Override
	public String toString(){
		return name + ":\n" + resource + factions;
	}
}
