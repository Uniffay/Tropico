package tropico.Object;

import java.io.Serializable;

/**
 * represents the faction of the game
 * @author Cléis & Quentin
 */
public class Faction implements Serializable {

	/**
	 * english name of the faction
	 */
	private final String englishName;
	/**
	 * number of partisan of the faction
	 */
	private int partisan;
	/**
	 * percentage fulfillment  of the faction
	 */
	private short fulfillment; // %
	/**
	 * path of image of the event
	 */
	private final String image;

	public Faction(double partisan, double fulfillment, String image) {
		this.englishName = image.substring(0, image.length() - 4); // We remove .png from the image name that is englishName.png
		this.partisan = (int)partisan;
		this.fulfillment = (short)fulfillment;
		this.image = image;
	}

	@Override
	public String toString(){
		return " " + partisan + " " + fulfillment;
	}

	/**
	 * get partisan of the faction
	 * @return partisan of the faction
	 */
	public int getPartisan() {
		return partisan;
	}

	/**
	 * get fulfillment of the faction
	 * @return fulfillment of the faction
	 */
	public short getFulfillment() {
		return fulfillment;
	}

	/**
	 * get english name of the faction
	 * @return english name
	 */
	public String getEnglishName() {
		return englishName;
	}

	/**
	 * change partisan by the partisan value
	 * @param partisan
	 * 		partisan to add/remove(if negative)
	 */
	public void changePartisan(Integer partisan) {
		this.partisan = Math.max(this.partisan * (100 + partisan) / 100, 0);
	}

	/**
	 * changes fulfillment by the fulfillment value
	 * @param fulfillment
	 * 		fulfillment to add/remove (if negative)
	 */
	public void changeFulfillment(short fulfillment) {
		this.fulfillment =(this.fulfillment > 0) ? (short) Math.min(Math.max(this.fulfillment + fulfillment, 0), 100) : 0;
	}

	/**
	 * kill a partisan of the faction
	 */
    public void killAPartisan() {
		partisan -= 1;
    }

	/**
	 * add a partisan to the faction
	 */
	public void addAPartisan() {
		partisan += 1;
	}

	/**
	 * add fulfillment corresponding to the number given
	 * @param number
	 * 		number added
	 * @return money used (for bribe)
	 */
	public int addFulfillment(Integer number) {
		fulfillment += number;
		return ((number + 9) / 10) * 15 * partisan / 10;
	}

	/**
	 * remove fulfillment corresponding to the number given
	 * @param number
	 * 		number to remove
	 */
	public void loseFulfillment(Integer number) {
		fulfillment -= number;
		fulfillment = (short)Math.max(fulfillment, 0);
	}
}
