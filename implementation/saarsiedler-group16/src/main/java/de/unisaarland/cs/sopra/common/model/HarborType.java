package de.unisaarland.cs.sopra.common.model;

public enum HarborType {

	GENERAL_HARBOR, LUMBER_HARBOR, BRICK_HARBOR, WOOL_HARBOR, GRAIN_HARBOR, ORE_HARBOR;
	
	//Bin mir nicht sicher ob das so funktioniert, man muss warscheinlich noch den HarborType mitgeben
	public boolean tradePossible(ResourcePackage resourcePackage) {
		throw new UnsupportedOperationException();
	}
}
