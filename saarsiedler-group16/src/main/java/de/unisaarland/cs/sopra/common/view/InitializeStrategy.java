package de.unisaarland.cs.sopra.common.view;

import java.io.IOException;
import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

public class InitializeStrategy implements Strategy {

	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		if (mr.getMe() == mr.getCurrentPlayer()){
		Set<Intersection> intersections =mr.buildableVillageIntersections(mr.getMe());
			Intersection i = intersections.iterator().next();
			ca.buildSettlement(i, BuildingType.Village);
			Set<Path> paths = mr.getPathsFromIntersection(i);
			Path p = paths.iterator().next();
			ca.buildStreet(p);
			
		}

	}
	public int evaluate(){
		// TODO implement this method
		return 0;
	}
}
