package de.unisaarland.cs.sopra.common.model;

public class Player {

	private ResourcePackage resources;
	private int victoryPoints;
	
	public ResourcePackage getResources() {
		return resources;
	}
	
	public void modifyResources(ResourcePackage resourcePackage) {
		if (resourcePackage == null) throw new IllegalArgumentException();
		resources.add(resourcePackage);
	}
	
	public boolean checkResourcesSufficient(ResourcePackage resourcePackage) {
		if (resourcePackage == null) throw new IllegalArgumentException();
		return resources.copy().add(resourcePackage).hasNegativeResources();
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}
	
}
