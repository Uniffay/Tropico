package tropico.Object;

import com.google.gson.Gson;
import tropico.Controller.Controller;
import tropico.Model.Difficulty;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Data {
	
	private final DictatorManagement players; 
	private int turn;
	private Season season;
	private int playerPlaying;
	private final Map<Season, List<Event>> eventsBySeason;
	private Event eventChosen;
	private double difficulty;
	
	
	public Data(int numberOfPlayer, String[] names, String jsonParserResource, String jsonParserFactions, String jsonParserEvents) throws IOException {
		this.turn = 0;
		season = Season.SPRING;
		setDifficultyFromJSON(jsonParserResource);
		players = new DictatorManagement(numberOfPlayer, names, jsonParserResource, jsonParserFactions);
		eventsBySeason = EventManagement.getEvent(jsonParserEvents);
		pickRandomEventFromSeason(Season.SPRING);
	}

	private void setDifficultyFromJSON(String jsonParserResource) {
		try{
			Gson gson = new Gson();
			Reader reader = Files.newBufferedReader(Path.of(jsonParserResource));
			Map<String, Double> map = gson.fromJson(reader, Map.class);
			difficulty = map.get("difficulty");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double getDifficulty() {
		return difficulty;
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
			players.get(playerPlaying).addInterest();
			players.get(playerPlaying).addBonus();
		}
	}

	public void pickRandomEventFromSeason(Season season){
		Random r = new Random();
		List<Event> e = eventsBySeason.get(season);
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

	public boolean isYearEnding() {
		return turn % 4 == 0;
	}
}
