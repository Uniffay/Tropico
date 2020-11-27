package tropico.Object;

public class Faction {
	
	private final String name;
	private int partisan;
	private short fulfillment; // %
	private String image;
	
	public Faction(String name, double partisan, double fulfillment, String image) {
		this.name = name;
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
}
