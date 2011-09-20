package de.unisaarland.cs.sopra.common.model;

public enum BuildingType {
	Village(new ResourcePackage(-1, -1, -1, -1, 0), 1, 1), 
	Town(new ResourcePackage(0, 0, 0, -2, -3), 2, 2);

	private ResourcePackage price;
	private int gain;
	private int victoryPoints;

	BuildingType(ResourcePackage price, int gain, int victoryPoints) {
		this.price = price;
		this.gain = gain;
		this.victoryPoints = victoryPoints;
	}

	public ResourcePackage getPrice() {
		return this.price;
	}

	public int getGain() {
		return this.gain;
	}

	public int getVictoryPoints() {
		return this.victoryPoints;
	}

	@Override
	public String toString() {
		if (gain == 1)
			return "Village";
		else
			return "Town";
	}
}
