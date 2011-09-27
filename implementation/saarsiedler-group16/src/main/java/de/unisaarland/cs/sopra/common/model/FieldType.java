package de.unisaarland.cs.sopra.common.model;

import static de.unisaarland.cs.sopra.common.model.Resource.*;

public enum FieldType {

	FOREST(LUMBER), HILLS(BRICK), PASTURE(WOOL), FIELDS(GRAIN), MOUNTAINS(ORE), DESERT(null), WATER(null);
	
	private Resource resource;
	
	FieldType(Resource resource) {
		this.resource = resource;
	}
	
	public Resource getResource() {
		return this.resource;
	}
	
	public boolean hasResource() {
		return this.resource != null;
	}

	public static FieldType convert(de.unisaarland.cs.st.saarsiedler.comm.FieldType fieldType) {
		switch(fieldType) {
			case Desert: return DESERT;
			case Fields: return FIELDS;
			case Forest: return FOREST;
			case Hills: return HILLS;
			case Mountains: return MOUNTAINS;
			case Pasture: return PASTURE;
			case Water: return WATER;
		}
		throw new IllegalStateException();
	}
	
	public static boolean isLandField(FieldType f) {
		switch(f) {
		case WATER: return false;
		default: return true;
		}
	}
	
}
