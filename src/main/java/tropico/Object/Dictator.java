package tropico.Object;

import tropico.Model.DataManagement;
import tropico.Model.Utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Dictator implements Serializable {

	private final String name;
	private final HashMap<String, Integer> resource = new HashMap<>();
	private final FactionsList factions;
	private int debt = 0;
	private boolean lost = false;
	private int turnLost = 0;
	
	public Dictator(String name, Map<String, Double> resource, String jsonPathFaction)  {
		this.name = name;
		this.resource.put("farming", resource.get("farming").intValue());
		this.resource.put("industry", resource.get("industry").intValue());
		if(this.resource.get("farming") + this.resource.get("industry") > 100) {
			throw new IllegalArgumentException("sum of farming and industry musn't be superior to 100");
		}
		this.resource.put("money", resource.get("money").intValue());
		factions = new FactionsList(jsonPathFaction);
	}

	@Override
	public String toString(){
		return name + ":\n" + resource + factions;
	}

	public FactionsList getFactions(){
		return factions;
	}

    public void haveChosen(Choice choice) {
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

	public Map<String, Integer> getResource() {
		return new HashMap<>(resource);
	}


	public void addDebt(int loanValue) {
		debt += loanValue * 1.1;
	}

	public void addMoney(int loanValue){
		resource.replace("money", resource.get("money") + loanValue);
	}

	public void addInterest() {
		debt += debt * 0.1;
	}

	public boolean haveDebt() {
		return debt > 0;
	}

	public int getDebt() {
		return debt;
	}

	public void addBonus() {
		int money = resource.get("money");
		resource.replace("money", money + resource.get("industry") * 10);
	}

	public void managePartisan(int food) {
		if(food < 0){
			factions.birth();
			return;
		}
		while (food > 0){
			factions.killARandomPartisan();
			food -= 4;
		}
	}

	public void bribeFulfillment(String name, Integer number) {
		int loyalistDissatisfaction = factions.addFulfillment(name, number);
		factions.getFaction("loyalist").loseFulfillment(loyalistDissatisfaction);
	}

	public void changeMoney(int money) {
		resource.replace("money", resource.get("money") + money);
	}

	public int getMoney() {
		return resource.get("money");
	}

	public void repayDebt(int loanRefund) {
		debt -= loanRefund;
	}

	public void debtDissatisfaction() {
		if(debt / 1000 < 0){
			return;
		}
		factions.loseFulfillment(debt / 1000);
	}

	public boolean haveLost(){
		return lost;
	}

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

	public Integer getTurnLost() {
		return turnLost;
	}

	public String getName() {
		return name;
	}
}
