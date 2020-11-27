package tropico.Object;

public class Faction {
	
	private final String name;
	private final String englishName;
	private int partisan;
	private short fulfillment; // %
	private String image;
	
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
		this.partisan = Math.max(this.partisan + partisan, 0);
	}

	public void changeFulfillment(short fulfillment) {
		this.fulfillment = (short) Math.min(Math.max(this.fulfillment + fulfillment, 0), 100);
	}
}
