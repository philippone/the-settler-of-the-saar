package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Intersection;

public class BuildVillage extends Stroke {

	private final Intersection destination;
	
	public BuildVillage(Intersection destination){
		super(StrokeType.BUILD_VILLAGE, BuildingType.Village.getPrice());
		this.destination = destination;
	}
	
	public void execute(ControllerAdapter c) {
		c.buildSettlement(destination, BuildingType.Village);
	}

	public Intersection getDestination() {
		return destination;
	}	
	
}
