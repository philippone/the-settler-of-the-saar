package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Intersection;

public class BuildTown extends Stroke {

	private final Intersection destination;
	
	public BuildTown(Intersection destination){
		super(StrokeType.BUILD_TOWN, BuildingType.Town.getPrice());
		this.destination = destination;
	}
	
	public void execute(ControllerAdapter c) {
		c.buildSettlement(destination, BuildingType.Town);
	}

	public Intersection getDestination() {
		return destination;
	}	
	
}
