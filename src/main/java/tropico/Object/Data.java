package tropico.Object;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Data {
	
	private final DictatorManagement players; 
	private int turn;
	private Season season;
	private int playerPlaying;
	private final HashMap<Season, ArrayList<Event>> eventsBySeason;
	private Event eventChosen;
	
	
	public Data(int numberOfPlayer, String[] names, String jsonParserResource, String jsonParserFactions, String jsonParserEvents) throws IOException {
		this.turn = 0;
		season = Season.SPRING;
		players = new DictatorManagement(numberOfPlayer, names, jsonParserResource, jsonParserFactions);
		eventsBySeason = EventManagement.getEvent(jsonParserEvents);
		pickRandomEventFromSeason(Season.SPRING);
	}
	
	public void endTurn() {
		if(players.size() > playerPlaying + 1) {
			playerPlaying = ((playerPlaying + 1) % players.size());
		}
		else{
			playerPlaying = 0;
			turn ++;
			season = season.next();
			pickRandomEventFromSeason(season);
		}
		if(turn % 4 == 0){
		}
	}

	public void pickRandomEventFromSeason(Season season){
		Random r = new Random();
		ArrayList<Event> e = eventsBySeason.get(season);
		int x = r.nextInt(e.size());
		eventChosen = e.get(x);
	}

	public Dictator getPlayerPlaying(){
		return players.get(playerPlaying);
	}

	public int getTurn() {
		return turn;
	}

	public Event getEventChosen() {
		return eventChosen;
	}

	public Season getSeason() {
		return season;
	}
}
