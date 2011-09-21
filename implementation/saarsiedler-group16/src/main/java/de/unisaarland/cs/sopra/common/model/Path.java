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
		if (this.catapult == null) throw new IllegalArgumentException();
		return this.catapult.getOwner();
	}
	
	public Player getStreetOwner() {
		if (this.street == null) throw new IllegalStateException();
		return this.street.getOwner();
	}
	
	public boolean hasCatapult() {
		return this.catapult != null;
	}
	
	public boolean hasStreet() {
		return this.street != null;
	}
	
	public void removeCatapult() {
		this.catapult = null;
	}
	
	public void setHarborType (HarborType harborType) {
		if (harborType == null) throw new IllegalStateException();
		this.harborType = harborType;
	}
	
	public HarborType getHarborType(){
		return this.harborType;
	}
	
	public void createStreet(Player owner) {
		if (owner == null) throw new IllegalStateException();
		this.street = new Street(owner);
	}
	
	public void createCatapult(Player owner) {
		if (owner == null) throw new IllegalStateException();
		this.catapult = new Catapult(owner);
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Path) {
			return ((Path)o).location.equals(this.location);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return location.hashCode();
	}
	
	@Override
	public String toString() {
		return String.format("Loc:%s; Street:%s; Catapult:%s; HarbourType:%s;", location, street, catapult, harborType);
	}
	
}
