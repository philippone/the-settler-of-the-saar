package de.unisaarland.cs.sopra.common.model;


public enum Resource {
	LUMBER, BRICK, WOOL, GRAIN, ORE;

	public static Resource convert (de.unisaarland.cs.st.saarsiedler.comm.Resource resource) {
		if (resource == null) return null;
		switch(resource) {
			case Lumber:
				return LUMBER;
			case Brick:
				return BRICK;
			case Grain:
				return GRAIN;
			case Ore:
				return ORE;
			case Wool:
				return WOOL;
			default: 
				throw new IllegalStateException();
		}
	}

}


