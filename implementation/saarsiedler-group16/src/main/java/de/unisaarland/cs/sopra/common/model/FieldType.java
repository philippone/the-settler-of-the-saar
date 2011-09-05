package de.unisaarland.cs.sopra.common.model;

import static de.unisaarland.cs.sopra.common.model.Resource.*;

public enum FieldType {

	FOREST(LUMBER), HILL(BRICK), PASTURE(WOOL), FIELD(GRAIN), MOUNTAINS(ORE), DESSERT(null), WATER(null);
	
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
	
}
