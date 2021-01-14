package tropico.Object;

import tropico.Model.DataManagement;
import tropico.Model.Utils;

import java.io.Serializable;
import java.util.*;

/**
 * represent player of the game
 * @author Quentin & Cléis
 */
public class Dictator implements Serializable {

	/**
	 * name of the player
	 */
	private final String name;
	/**
	 * resources of the player
	 */
	private final HashMap<String, Integer> resource = new HashMap<>();
	/**
	 * Factions of the player
	 */
	private final FactionsList factions;
	/**
	 * debt of the player
	 */
	private int debt = 0;
	/**
	 * true if the player lost false otherwise
	 */
	private boolean lost = false;
	/**
	 * turn the player lost
	 */
	private int turnLost = 0;
	/**
	 * map of the event Id by season (season as key and list of id as value)
	 */
	private final HashMap<Season, ArrayList<Integer>> eventsId = new HashMap<>();
	
	public Dictator(String name, Map<String, Double> resource, String jsonPathFaction)  {
		this.name = name;
		this.resource.put("farming", resource.get("farming").intValue());
		this.resource.put("industry", resource.get("industry").intValue());
		if(this.resource.get("farming") + this.resource.get("industry") > 100) {
			throw new IllegalArgumentException("sum of farming and industry musn't be superior to 100");
		}
		this.resource.put("money", resource.get("money").intValue());
		factions = new FactionsList(jsonPathFaction);
		initializeEventsId();
	}

	private void initializeEventsId() {
		Arrays.stream(Season.values()).forEach(season -> eventsId.put(season, new ArrayList<>()));
	}

	@Override
	public String toString(){
		return name + ":\n" + resource + factions;
	}

	/**
	 * get a FactionList instance that contains the factions of the players
	 * @return
	 * 		an instance of FactionList
	 */
	public FactionsList getFactions(){
		return factions;
	}

	/**
	 * add effect to the corresponding resource of the event chosen by the player
	 * @param choice
	 * 		choice made by the player
	 */
    public void haveChosen(Choice choice) {
    	manageEventId(choice);
		for (String effect: choice.getEffect_resource().keySet()){
			try {
				int resourceValue = Utils.modifiedByDifficulty(choice.getEffect_resource().get(effect));
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

	/**
	 * add eventId of the unlocked event for the player
	 * @param choice
	 * 		choice made by the player
	 */
	private void manageEventId(Choice choice) {
		Data gameData = DataManagement.getData();
		choice.getNextForMe().forEach(
				id -> eventsId.get(gameData.getEvent(id).getRandomSeason()).add(id));
		choice.getNextForMultiplayer().forEach(id -> gameData.getPlayers().addForAllFilteredPlayer(
				gameData.getEvent(id).getSeason(), id, DataManagement.getData().getTurn(),dictator -> !(dictator.equals(this))));
	}

	/**
	 * get the resources of the player
	 * @return map with String (name of the resource) as key and number of resource as the value
	 */
	public Map<String, Integer> getResource() {
		return new HashMap<>(resource);
	}

	/**
	 * add debt to the player that is equals to loanValue + 10 % of it
	 * @param loanValue
	 * 		value added to the debt
	 */
	public void addDebt(int loanValue) {
		debt += loanValue * 1.1;
	}

	/**
	 * add money to the player when he is taking loan
	 * @param loanValue
	 * 		value of the loan
	 */
	public void addMoney(int loanValue){
		resource.replace("money", resource.get("money") + loanValue);
	}

	/**
	 * add interest to debt at the end of the year (10 % of the debt value
	 */
	public void addInterest() {
		debt += debt * 0.1;
	}

	/**
	 * test if player have debt
	 * @return true if debt is superior to 0 false otherwise
	 */
	public boolean haveDebt() {
		return debt > 0;
	}

	/**
	 * get debt of the player
	 * @return debt of the player
	 */
	public int getDebt() {
		return debt;
	}

	/**
	 * add money earn by the industry at the end of the year (10 * industry percentage)
	 */
	public void addBonus() {
		int money = resource.get("money");
		resource.replace("money", money + resource.get("industry") * 10);
	}

	/**
	 * manage partisan in function of the foodMissing value by giving birth or killing them
	 * @param foodMissing
	 * 		food missing at the end of the year (can be negative if there is extra food)
	 */
	public void managePartisan(int foodMissing) {
		if(foodMissing < 0){
			factions.birth();
			return;
		}
		while (foodMissing > 0){
			factions.killARandomPartisan();
			foodMissing -= 4;
		}
	}

	/**
	 * manage bribe by adding fulfillment to the faction given and removing some fulfillment to the loyalist
	 * @param name
	 * 		name of the faction
	 * @param number
	 * 		number of fulfillment added
	 */
	public void bribeFulfillment(String name, Integer number) {
		int loyalistDissatisfaction = factions.addFulfillment(name, number);
		factions.getFaction("loyalist").loseFulfillment(loyalistDissatisfaction);
	}

	public void changeMoney(int money) {
		resource.replace("money", resource.get("money") + money);
	}

	/**
	 * get the money of the player
	 * @return money of the player
	 */
	public int getMoney() {
		return resource.get("money");
	}

	/**
	 * player payed some or all his debt
	 * @param loanRefund
	 * 		value the player payed to repay his debt
	 */
	public void repayDebt(int loanRefund) {
		debt -= loanRefund;
	}

	/**
	 * remove 1% of fulfillment for all faction per 1000 $ of debt the player have
	 */
	public void debtDissatisfaction() {
		if(debt / 1000 < 0){
			return;
		}
		factions.loseFulfillment(debt / 1000);
	}

	/**
	 * test if player lost
	 * @return true if player lost false otherwise
	 */
	public boolean havePlayerLost(){
		Data gameData = DataManagement.getData();
		lost = factions.getAverageFulfillment() < gameData.getFulfillmentMin();
		if(lost){
			turnLost = gameData.getTurn();
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Dictator dictator = (Dictator) o;
		return debt == dictator.debt &&
				lost == dictator.lost &&
				turnLost == dictator.turnLost &&
				Objects.equals(name, dictator.name) &&
				Objects.equals(resource, dictator.resource) &&
				Objects.equals(factions, dictator.factions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, resource, factions, debt, lost, turnLost);
	}

	/**
	 * get turn when the dictator lost (0 if he didn't lose)
	 * @return turn when the dictator lost
	 */
	public Integer getTurnLost() {
		return turnLost;
	}

	/**
	 * get name of the dictator
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * add en event in the list at a random place
	 * @param season
	 * 		season of the event
	 * @param id
	 * 		id of the event
	 */
	public void addEvent(Season season, int id, int currentTurn) {
		ArrayList<Integer> eventsId = this.eventsId.get(season);
		if(eventsId.size() == 0) {
			eventsId.add(id);
			return;
		}
		eventsId.add(Utils.getRandom(eventsId.size() - (currentTurn % (eventsId.size()))) + (currentTurn % eventsId.size()) ,id);
	}

	/**
	 * get the event that match the season and turn
	 * @param season
	 * 		season of the event
	 * @param turn
	 * 		turn of the event
	 * @return event that match the season and turn
	 */
	public int getEvent(Season season, int turn) {
		List<Integer> eventsId = this.eventsId.get(season);
		int turnEvent = (turn / 4) % eventsId.size();
		if(turnEvent == 0) {
			Collections.shuffle(eventsId);
		}
		return eventsId.get(turnEvent);
	}
}
