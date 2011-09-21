package de.unisaarland.cs.sopra.common.model;

public class Intersection {
	
	private Location location;
	private Building building;
	
	public Intersection(Location location) {
		if (location == null) throw new IllegalArgumentException();
		this.location = location;
	}
	
	public void createBuilding(BuildingType buildingType, Player owner) {
		if (buildingType == null) throw new IllegalArgumentException();
		if (owner == null) throw new IllegalArgumentException();
		this.building = new Building(owner, buildingType);
	}
	
	public void removeBuilding() {
		this.building = null;
	}
	
	public Player getOwner() {
		if (building == null) throw new IllegalArgumentException();
		return this.building.getOwner();
	}
	
	public void setOwner(Player owner) {
		if (building == null) throw new IllegalStateException();
		this.building.setOwner(owner);
	}
	
	public boolean hasOwner() {
		return this.building != null;
	}
	
	public BuildingType getBuildingType() {
		if (building == null) return null;
		return this.building.getBuildingType();
	}
	
	public void generateGain(Resource resource) {
		if (building != null) {
			ResourcePackage res = new ResourcePackage();
			res.modifyResource(resource, building.getGain());
			building.getOwner().modifyResources(res);
		}
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Intersection) {
			return ((Intersection)o) == this;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return location.hashCode();
	}
	
	@Override
	public String toString() {
		
		return "[INT: "+location+", "+building+"]";
	}
}
