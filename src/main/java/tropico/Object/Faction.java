package tropico.Object;

public class Faction {
	
	private final String name;
	private final String englishName;
	private int partisan;
	private short fulfillment; // %
	private final String image;

	public Faction(String name, double partisan, double fulfillment, String image) {
		this.name = name;
		this.englishName = image.substring(0, image.length() - 4); // We remove .png from the image name that is englishName.png
		this.partisan = (int)partisan;
		this.fulfillment = (short)fulfillment;
		this.image = image;
	}

	@Override
	public String toString(){
		return name + " " + partisan + " " + fulfillment;
	}

	public int getPartisan() {
		return partisan;
	}

	public short getFulfillment() {
		return fulfillment;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void changePartisan(Integer partisan) {
		this.partisan = Math.max(this.partisan * (100 + partisan) / 100, 0);
	}

	public void changeFulfillment(short fulfillment) {
		this.fulfillment =(this.fulfillment > 0) ? (short) Math.min(Math.max(this.fulfillment + fulfillment, 0), 100) : 0;
	}

    public void killAPartisan() {
		partisan -= 1;
    }

	public void addAPartisan() {
		partisan += 1;
	}

	public int addFulfillment(Integer number) {
		fulfillment += Math.min(100, number);
		return ((number + 9) / 10) * 15 * partisan / 10;
	}

	public void loseFulfillment(Integer number) {
		fulfillment -=Math.max(number, 0);
	}
}
