package de.unisaarland.cs.sopra.common.model;

public class Street {

	private static final ResourcePackage price = new ResourcePackage(-1,-1,0,0,0);
	
	private Player owner;
	
	public static ResourcePackage getPrice() {
		return price;
	}
	
	public Street(Player owner) {
		this.owner = owner;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	
}
