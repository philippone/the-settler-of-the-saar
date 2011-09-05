package de.unisaarland.cs.sopra.common.model;

public class Path {

	private Location location;
	private Street street;
	private Catapult catapult;
	private HarborType harborType;
	
	public Path(Location location) {
		this.location = location;
	}
	
	public Player getCatapultOwner() {
		throw new UnsupportedOperationException();
	}
	
	public Player getStreetOwner() {
		throw new UnsupportedOperationException();
	}
	
	public boolean hasCatapultOwner() {
		throw new UnsupportedOperationException();
	}
	
	public boolean hasStreetOwner() {
		throw new UnsupportedOperationException();
	}
	
	public void removeCatapult() {
		throw new UnsupportedOperationException();
	}
	
	public void setHarborType (HarborType harborType) {
		throw new UnsupportedOperationException();
	}
	
	public HarborType getHarborType(){
		throw new UnsupportedOperationException();
	}
	
	public void createStreet(Player owner) {
		throw new UnsupportedOperationException();
	}
	
	public void createCatapult(Player owner) {
		throw new UnsupportedOperationException();
	}
	
	public Location getLocation() {
		throw new UnsupportedOperationException();
	}
	
}
