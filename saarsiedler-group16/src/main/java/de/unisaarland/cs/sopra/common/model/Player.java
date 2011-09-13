package de.unisaarland.cs.sopra.common.model;

import java.util.List;

public class Player {

	private ResourcePackage resources;
	private int victoryPoints;
	private List<List<Path>> lrlist;
	private List<Building> blist;
	
	public Player() {
		this.resources = new ResourcePackage(0,0,0,0,0);
	}
	
	public ResourcePackage getResources() {
		return resources;
	}
	
	public void modifyResources(ResourcePackage resourcePackage) {
		if (resourcePackage == null) throw new IllegalArgumentException();
		resources.add(resourcePackage);
		if (this.resources.hasNegativeResources()) throw new IllegalArgumentException();
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
	
	public List<List<Path>> getLrlist() {
		return lrlist;
	}

	public void setLrlist(List<List<Path>> lrlist) {
		this.lrlist = lrlist;
	}

	public List<Building> getBlist() {
		return blist;
	}

	public void setBlist(List<Building> blist) {
		this.blist = blist;
	}
	
}
