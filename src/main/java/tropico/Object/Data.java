package tropico.Object;

import com.google.gson.Gson;
import tropico.Model.Utils;
import tropico.view.StageEnum;
import tropico.view.StageManagement;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;

public class Data implements Serializable{
	
	private final DictatorManagement players;
	private final ArrayList<Dictator> eliminatedPlayer = new ArrayList<>();
	private int turn;
	private Season season;
	private int playerPlaying;
	private final Map<Season, List<Event>> eventsBySeason;
	private Event eventChosen;
	private double difficulty;
	private int fulfillmentMin;
	private boolean gameEnded;
	
	
	public Data(int numberOfPlayer, String[] names, String jsonParserResource, String jsonParserFactions, String jsonParserEvents) throws IOException {
		this.turn = 0;
		season = Season.SPRING;
		Map<String, Double> resource = extractInformationJsonSetting(jsonParserResource);
		players = new DictatorManagement(numberOfPlayer, names, resource, jsonParserFactions);
		eventsBySeason = EventManagement.getEvent(jsonParserEvents);
		pickRandomEventFromSeason(Season.SPRING);
	}

	private Map<String, Double> extractInformationJsonSetting(String jsonParserResource) {
		try{
			Gson gson = new Gson();
			Reader reader = Files.newBufferedReader(Path.of(jsonParserResource));
			Map<String, Double> map = gson.fromJson(reader, Map.class);
			difficulty = map.get("difficulty");
			fulfillmentMin = map.get("fulfillmentMin").intValue();
			return map;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isGameEnded() {
		return gameEnded;
	}

	public int getFulfillmentMin() {
		return fulfillmentMin;
	}

	public double getDifficulty() {
		return difficulty;
	}

	public void endTurn() throws IOException {
		Dictator playerPlaying = players.get(this.playerPlaying);
		playerPlaying.debtDissatisfaction();
		if(playerPlaying.havePlayerLost()) {
			eliminatedPlayer.add(playerPlaying);
			players.remove(playerPlaying);
			this.playerPlaying --;
		}
		if(players.isEmpty()) {
			StageManagement.setScene(StageEnum.END);
			gameEnded = true;
			return;
		}
		if(players.size() > this.playerPlaying + 1) {
			this.playerPlaying ++;
		}
		else{
			this.playerPlaying = 0;
			turn ++;
			season = season.next();
			pickRandomEventFromSeason(season);
		}
		if(isYearEnding()){
			players.get(this.playerPlaying).addBonus();
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
		return turn % 4 == 3 && this.playerPlaying == players.size() - 1;
	}

	public String getStringScore() {
		StringBuilder text = new StringBuilder();
		eliminatedPlayer.stream().sorted(Comparator.comparing(Dictator::getTurnLost)).forEach(
				d-> text.append(d.getName()).append(": Mort le ").append(Utils.getDateFromTurn(d.getTurnLost())).append(
						". Son règne a duré ").append(Utils.getTimeReign(d.getTurnLost())).append("jours. \n"));
		return text.toString();
	}

	public void save() throws IOException {
		PrintWriter writer = new PrintWriter("sauvegarde");
		writer.close();
		Path path = Path.of("sauvegarde");
		try (OutputStream back = Files.newOutputStream(path);
			 ObjectOutputStream out = new ObjectOutputStream(back)){
			out.writeObject(this);
		}
	}

	public static Data recuperation() throws ClassNotFoundException, IOException {
		Data gameData = null;
		Path path = Path.of("sauvegarde");
		try( InputStream back = Files.newInputStream(path);
			 ObjectInputStream in = new ObjectInputStream(back)){
			gameData = (Data) in.readObject();
		}catch(NoSuchFileException e) {
			e.printStackTrace();
		}
		return gameData;
	}

	public boolean isSolo() {
		return players.size() + eliminatedPlayer.size() == 1;
	}
}
