package tropico.Object;

import com.google.gson.Gson;
import tropico.Model.Difficulty;
import tropico.Model.SettingManagement;
import tropico.Model.Utils;
import tropico.view.StageEnum;
import tropico.view.StageManagement;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;

/**
 * data of the game
 * @author Cléis & Quentin
 */
public class Data implements Serializable{
	/**
	 * list of players playing
	 */
	private final DictatorManagement players;
	/**
	 * list of eliminated player
	 */
	private final ArrayList<Dictator> eliminatedPlayer = new ArrayList<>();
	/**
	 * current turn
	 */
	private int turn;
	/**
	 * current season
	 */
	private Season season;
	/**
	 * position of the playerPlaying in the players List
	 */
	private int playerPlaying;
	/**
	 * Map of all the event of the game with id as key and event as value
	 */
	private final Map<Integer, Event> eventsById;
	/**
	 * current event of the player playing
	 */
	private Event eventChosen;
	/**
	 * number by which the effect are modified
	 */
	private double difficultyRatio;
	/**
	 * minimum fulfillment for a player to survive
	 */
	private int fulfillmentMin;
	/**
	 * true if the game ended false otherwise
	 */
	private boolean gameEnded;
	
	
	public Data(int numberOfPlayer, String[] names, String jsonParserResource, String jsonParserFactions, String jsonParserEvents) {
		this.turn = 0;
		season = Season.SPRING;
		Difficulty difficulty = SettingManagement.getDifficultyFromMenuItem();
		Map<String, Double> resource = extractInformationJsonSetting(jsonParserResource);
		players = new DictatorManagement(numberOfPlayer, names, resource, jsonParserFactions);
		eventsById = EventManagement.instantiateEventMap(jsonParserEvents, difficulty, players);
		pickNextEvent(Season.SPRING);
	}

	private Map<String, Double> extractInformationJsonSetting(String jsonParserResource) {
		try{
			Gson gson = new Gson();
			Reader reader = Files.newBufferedReader(Path.of(jsonParserResource));
			Map<String, Double> map = gson.fromJson(reader, Map.class);
			difficultyRatio = map.get("difficulty");
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

	/**
	 * get the minimum fulfillment needed to player to survive
	 * @return minimum fulfillment
	 */
	public int getFulfillmentMin() {
		return fulfillmentMin;
	}

	/**
	 * get the difficulty ratio of the game
	 * @return difficulty ratio
	 */
	public double getDifficultyRatio() {
		return difficultyRatio;
	}

	/**
	 * manage end turn by changing player playing, eliminating player that lost, finishing the game if need be,
	 * adding all end malus and bonus (Satisfaction, Money, ...).
	 */
	public void endTurn() {
		Dictator playerPlaying = players.get(this.playerPlaying);
		playerPlaying.debtDissatisfaction();
		eliminatePlayer(playerPlaying);
		if(manageLost())
			return;
		changePlayerPlaying();
		if(isYearEnding())
			players.get(this.playerPlaying).addBonus();
		else
			pickNextEvent(season);
	}

	private void eliminatePlayer(Dictator playerPlaying) {
		if(playerPlaying.havePlayerLost()) {
			eliminatedPlayer.add(playerPlaying);
			players.remove(playerPlaying);
			this.playerPlaying --;
		}
	}

	private boolean manageLost() {
		if(players.isEmpty()) {
			try {
				StageManagement.setScene(StageEnum.END);
			}catch (IOException e){
				e.printStackTrace();
			}
			gameEnded = true;
			return true;
		}
		return false;
	}

	private void changePlayerPlaying() {
		if(players.size() > this.playerPlaying + 1) {
			this.playerPlaying ++;
		}
		else{
			this.playerPlaying = 0;
			turn ++;
			if(!isYearEnding()){
				season = season.next();
			}
		}
	}

	/**
	 * change current event
	 * @param season
	 * 		season of the event that will be picked
	 */
	public void pickNextEvent(Season season){
		eventChosen = eventsById.get(players.get(playerPlaying).getEvent(season, turn));
	}

	/**
	 * get the player currently playing
	 * @return player playing
	 */
	public Dictator getPlayerPlaying(){
		return players.get(playerPlaying);
	}

	/**
	 * get the current turn of the game
	 * @return current turn
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * get the current event of the game
	 * @return current event
	 */
	public Event getEventChosen() {
		return eventChosen;
	}

	/**
	 * get the current season of the game
	 * @return current season
	 */
	public Season getSeason() {
		return season;
	}

	/**
	 * Test if year ends now
	 * @return true if years and now else false
	 */
	public boolean isYearEnding() {
		return turn % 5 == 4;
	}

	/**
	 * get the String of the score displayed at the end of the game
	 * @return the score as a string
	 */
	public String getStringScore() {
		StringBuilder text = new StringBuilder();
		eliminatedPlayer.stream().sorted(Comparator.comparing(Dictator::getTurnLost)).forEach(
				d-> text.append(d.getName()).append(": Mort le ").append(Utils.getDateFromTurn(d.getTurnLost())).append(
						". Son règne a duré ").append(Utils.getTimeReign(d.getTurnLost())).append("jours. \n"));
		return text.toString();
	}

	/**
	 * save the game in a file named save
	 * @throws IOException if an I/O error occurs
	 */
	public void save() throws IOException {
		PrintWriter writer = new PrintWriter("sauvegarde");
		writer.close();
		Path path = Path.of("sauvegarde");
		try (OutputStream back = Files.newOutputStream(path);
			 ObjectOutputStream out = new ObjectOutputStream(back)){
			out.writeObject(this);
		}
	}

	/**
	 * recover Data from the last save
	 * @return an instance Data
	 * @throws ClassNotFoundException class of a Serialized Object can't be found
	 * @throws IOException if an I/O error occurs
	 */
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

	/**
	 * test if there is more than one player
	 * @return true if the game have more than one player else false
	 */
	public boolean nonSolo() {
		return players.size() + eliminatedPlayer.size() != 1;
	}

	/**
	 * get an event from the id
	 * @param id
	 * 		id of the event
	 * @return the corresponding event
	 */
	public Event getEvent(Integer id) {
		return eventsById.get(id);
	}

	/**
	 * get all the players from the game
	 * @return player of the game
	 */
	public DictatorManagement getPlayers() {
		return players;
	}
}
