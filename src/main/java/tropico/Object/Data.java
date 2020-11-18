package tropico.Object;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Data {
	
	private final DictatorManagement players; 
	private int turn;
	private int playerPlaying;
	private final HashMap<Season, ArrayList<Event>> eventsBySeason;
	
	
	public Data(int numberOfPlayer, String[] names, String jsonParserResource, String jsonParserFactions, String jsonParserEvents) throws IOException {
		this.turn = 0;
		players = new DictatorManagement(numberOfPlayer, names, jsonParserResource, jsonParserFactions);
		eventsBySeason = EventManagement.getEvent(jsonParserEvents);
	}
	
	public void endTurn() {
		if(players.size() > playerPlaying) {
			playerPlaying = ((playerPlaying+ 1) % players.size());
		}
		if(++turn % 4 == 0){
		}
		turn ++;
	}

	public Event pickRandomEventFromSeason(Season season){
		Random r = new Random();
		ArrayList<Event> e = eventsBySeason.get(season);
		int x = r.nextInt(e.size());
		return e.get(x);
	}

}
