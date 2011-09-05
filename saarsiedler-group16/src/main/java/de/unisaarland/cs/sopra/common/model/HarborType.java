package de.unisaarland.cs.sopra.common.model;

public enum HarborType {

	GENERAL_HARBOR, LUMBER_HARBOR, BRICK_HARBOR, WOOL_HARBOR, GRAIN_HARBOR, ORE_HARBOR;
	
	public boolean tradePossible(ResourcePackage resourcePackage) {
		throw new UnsupportedOperationException();
	}
}
