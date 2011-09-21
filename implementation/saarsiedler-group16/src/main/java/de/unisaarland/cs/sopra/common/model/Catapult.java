package de.unisaarland.cs.sopra.common.model;

public class Catapult {

	private static final ResourcePackage buildingPrice = new ResourcePackage(-1,0,-1,0,-1);
	private static final ResourcePackage attackCatapultPrice = new ResourcePackage(0,0,0,-1,0);
	private static final ResourcePackage attackBuildingPrice = new ResourcePackage(0,0,0,0,-1);
	
	private Player owner;
	
	public static ResourcePackage getBuildingprice() {
		return buildingPrice;
	}
	
	public static ResourcePackage getAttackcatapultprice() {
		return attackCatapultPrice;
	}
	
	public static ResourcePackage getAttackbuildingprice() {
		return attackBuildingPrice;
	}

	public Catapult(Player owner) {
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}
	
	
	
	
}
