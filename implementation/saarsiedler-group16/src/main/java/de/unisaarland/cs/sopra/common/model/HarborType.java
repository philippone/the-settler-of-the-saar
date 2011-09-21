package de.unisaarland.cs.sopra.common.model;


public enum HarborType {

	GENERAL_HARBOR, LUMBER_HARBOR, BRICK_HARBOR, WOOL_HARBOR, GRAIN_HARBOR, ORE_HARBOR;
	
	public boolean tradePossible(ResourcePackage resourcePackage) {
		switch (this) {
			case GENERAL_HARBOR: 
				if (resourcePackage.getNegativeResourcesCount() == 3 && resourcePackage.getPositiveResourcesCount() == 1) return true;
				else return false;
			case LUMBER_HARBOR:
				if (resourcePackage.getResource(Resource.LUMBER) == -2 && resourcePackage.getPositiveResourcesCount() == 1) return true;
				else return false;
			case BRICK_HARBOR:
				if (resourcePackage.getResource(Resource.BRICK) == -2 && resourcePackage.getPositiveResourcesCount() == 1) return true;
				else return false;
			case GRAIN_HARBOR:
				if (resourcePackage.getResource(Resource.GRAIN) == -2 && resourcePackage.getPositiveResourcesCount() == 1) return true;
				else return false;
			case ORE_HARBOR:
				if (resourcePackage.getResource(Resource.ORE) == -2 && resourcePackage.getPositiveResourcesCount() == 1) return true;
				else return false;
			case WOOL_HARBOR:
				if (resourcePackage.getResource(Resource.WOOL) == -2 && resourcePackage.getPositiveResourcesCount() == 1) return true;
				else return false;
			default:
				throw new IllegalStateException();
		}
		
	}
	
	public static HarborType convert(de.unisaarland.cs.st.saarsiedler.comm.Resource resource) {
		if (resource == null) return GENERAL_HARBOR;
		switch (resource) {
			case Brick:
				return BRICK_HARBOR;
			case Grain: 
				return GRAIN_HARBOR;
			case Lumber: 
				return LUMBER_HARBOR;
			case Ore:
				return ORE_HARBOR;
			case Wool: 
				return WOOL_HARBOR;
			default:
				throw new IllegalStateException();
		}
	}
	
}
