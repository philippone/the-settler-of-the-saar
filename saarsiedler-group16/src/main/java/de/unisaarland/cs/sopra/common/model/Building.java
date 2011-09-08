package de.unisaarland.cs.sopra.common.model;

public class Building {
	
	private BuildingType buildingType;
	private Player owner;
	
	public Building(Player owner, BuildingType buildingType) {
		if (owner == null) throw new IllegalArgumentException();
		if (buildingType == null) throw new IllegalArgumentException();
		this.owner = owner;
		this.buildingType = buildingType;
	}
	
	public int getGain() {
		return this.buildingType.getGain();
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	public void setOwner(Player owner) {
		if (owner == null) throw new IllegalArgumentException();
		this.owner = owner;
	}
	
	public BuildingType getBuildingType() {
		return this.buildingType;
	}
	
}


