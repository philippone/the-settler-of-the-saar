package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Intersection;

public class BuildBuilding extends Stroke {

	private final Intersection destination;
	private final BuildingType buildingType;
	
	public BuildBuilding(Intersection destination, BuildingType buildingType){
		super(StrokeType.BUILD_BUILDING);
		this.destination = destination;
		this.buildingType = buildingType;
	}
	
	public void execute(ControllerAdapter c) {
		c.buildSettlement(destination, buildingType);
	}

	public Intersection getDestination() {
		return destination;
	}

	public BuildingType getBuildingType() {
		return buildingType;
	}

}
