package de.unisaarland.cs.sopra.common.model;

import java.util.HashMap;

import static de.unisaarland.cs.sopra.common.model.Resource.*;

/**
 * @author Patrick Bender
 *
 */
public class ResourcePackage {

	private HashMap<Resource,Integer> resources = new HashMap<Resource,Integer>();
	
	
	/**
	 * @param lumber count of lumber in this ResourcePackage
	 * @param brick count of brick in this ResourcePackage
	 * @param wool count of wool in this ResourcePackage
	 * @param grain count of grain in this ResourcePackage
	 * @param ore count of ore in this ResourcePackage
	 */
	public ResourcePackage(int lumber, int brick, int wool, int grain, int ore) {
		resources.put(LUMBER, lumber);
		resources.put(BRICK, brick);
		resources.put(WOOL, wool);
		resources.put(GRAIN, grain);
		resources.put(ORE, ore);
	}
	
	/**
	 * @param resourcePackage The ResourcePackage from which the new ResourcePackage is made
	 */
	private ResourcePackage(ResourcePackage resourcePackage) {
		this.resources = new HashMap<Resource,Integer>(resourcePackage.resources);
	}

	/**
	 * @param resource determines the kind of Resource
	 * @return The count of the given Resource in this ResourcePackage
	 */
	public int getResource(Resource resource) {
		if (resource == null) throw new IllegalArgumentException();
		return resources.get(resource);
	}
	
	
	/**
	 * @param resourcePackage The ResourcePackage to add
	 * @return The ResourcePackage after the merge with the other resourcePackage
	 */
	public ResourcePackage add(ResourcePackage resourcePackage) {
		if (resourcePackage == null) throw new IllegalArgumentException();
		resources.put(LUMBER, (resources.get(LUMBER) + resourcePackage.getResource(LUMBER)) );
		resources.put(BRICK, (resources.get(BRICK) + resourcePackage.getResource(BRICK)) );
		resources.put(WOOL, (resources.get(WOOL) + resourcePackage.getResource(WOOL)) );
		resources.put(GRAIN, (resources.get(GRAIN) + resourcePackage.getResource(GRAIN)) );
		resources.put(ORE, (resources.get(ORE) + resourcePackage.getResource(ORE)) );
		return this;
	}
	
	/**
	 * @return The count of the Resources contained in this ResourcePackage
	 */
	public int size() {
		return resources.get(LUMBER) + resources.get(BRICK) + resources.get(WOOL) + resources.get(GRAIN) + resources.get(ORE);
	}
	
	
	/**
	 * @return A deep copy of this ResourcePackage
	 */
	public ResourcePackage copy() {
		return new ResourcePackage(this);
	}
	
	
	/**
	 * @return True if one of the Resources in the ResourcePackage has a negative Count
	 */
	public boolean hasNegativeResources() {
		if (resources.get(LUMBER) < 0) return true;
		if (resources.get(BRICK) < 0) return true;
		if (resources.get(WOOL) < 0) return true;
		if (resources.get(GRAIN) < 0) return true;
		if (resources.get(ORE) < 0) return true;
		return false;
	}
	
}
