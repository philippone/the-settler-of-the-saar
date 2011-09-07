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
	 * Creates an empty ResourcePackage
	 */
	public ResourcePackage() {
		resources.put(LUMBER, 0);
		resources.put(BRICK, 0);
		resources.put(WOOL, 0);
		resources.put(GRAIN, 0);
		resources.put(ORE, 0);
	}

	/**
	 * @param resource determines the kind of Resource
	 * @return The count of the given Resource in this ResourcePackage
	 */
	public int getResource(Resource resource) {
		if (resource == null) throw new IllegalArgumentException();
		return resources.get(resource);
	}
	
	public void modifyResource(Resource resource, int modifyCount) {
		if (resource == null) throw new IllegalArgumentException();
		resources.put(resource, resources.get(resource)+modifyCount);
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
		return (resources.get(LUMBER) < 0 || resources.get(GRAIN) < 0 || resources.get(BRICK) < 0 
				|| resources.get(ORE) < 0 || resources.get(WOOL) < 0);
	}
	
	/**
	 * @return The absolute count of negative resources in this resourcepackage
	 */
	public int getNegativeResourcesCount() {
		int tmp = 0;
		if (this.getResource(LUMBER) < 0) tmp -= this.getResource(LUMBER);
		if (this.getResource(BRICK) < 0) tmp -= this.getResource(BRICK);
		if (this.getResource(WOOL) < 0) tmp -= this.getResource(WOOL);
		if (this.getResource(GRAIN) < 0) tmp -= this.getResource(GRAIN);
		if (this.getResource(ORE) < 0) tmp -= this.getResource(ORE);
		return tmp;
	}
	
	/**
	 * @return True if one of the Resources in the ResourcePackage has a positive Count
	 */
	public boolean hasPositiveResources() {
		return (resources.get(LUMBER) > 0 || resources.get(GRAIN) > 0 || resources.get(BRICK) > 0 
				|| resources.get(ORE) > 0 || resources.get(WOOL) > 0);
	}
	
	/**
	 * @return The absolute count of positive resources in this resourcepackage
	 */
	public int getPositiveResourcesCount() {
		int tmp = 0;
		if (this.getResource(LUMBER) > 0) tmp += this.getResource(LUMBER);
		if (this.getResource(BRICK) > 0) tmp += this.getResource(BRICK);
		if (this.getResource(WOOL) > 0) tmp += this.getResource(WOOL);
		if (this.getResource(GRAIN) > 0) tmp += this.getResource(GRAIN);
		if (this.getResource(ORE) > 0) tmp += this.getResource(ORE);
		return tmp;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof ResourcePackage) {
			if ( (((ResourcePackage)o).getResource(LUMBER) == this.getResource(LUMBER)) &&
				 (((ResourcePackage)o).getResource(BRICK) == this.getResource(BRICK)) &&
				 (((ResourcePackage)o).getResource(WOOL) == this.getResource(WOOL)) &&
				 (((ResourcePackage)o).getResource(GRAIN) == this.getResource(GRAIN)) &&
				 (((ResourcePackage)o).getResource(ORE) == this.getResource(ORE)) ) return true;
			else return false;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getResource(LUMBER) * 271 +
			   this.getResource(BRICK) * 269 +
			   this.getResource(WOOL) * 263 +
			   this.getResource(GRAIN) * 257 +
			   this.getResource(ORE) * 251;
	}
	
}
