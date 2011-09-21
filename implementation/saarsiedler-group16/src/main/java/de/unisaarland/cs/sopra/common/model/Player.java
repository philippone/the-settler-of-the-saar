package de.unisaarland.cs.sopra.common.model;


public class Player {

	private ResourcePackage resources;
	private int victoryPoints;
	
	public Player() {
		this.resources = new ResourcePackage(0,0,0,0,0);
	}
	
	public ResourcePackage getResources() {
		return resources;
	}
	
	public void modifyResources(ResourcePackage resourcePackage) {
		if (resourcePackage == null) throw new IllegalArgumentException();
		resources.add(resourcePackage);
		//if (this.resources.hasNegativeResources()) throw new IllegalArgumentException("zu viel abgezogen");
	}
	
	public boolean checkResourcesSufficient(ResourcePackage resourcePackage) {
		if (resourcePackage == null) throw new IllegalArgumentException();
		if (resourcePackage.hasPositiveResources()) throw new IllegalArgumentException();
		return !resources.copy().add(resourcePackage).hasNegativeResources();
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}
}
