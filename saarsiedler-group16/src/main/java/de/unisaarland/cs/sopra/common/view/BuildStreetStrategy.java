package de.unisaarland.cs.sopra.common.view;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Intersection;

public class BuildStreetStrategy implements Strategy {
 Path bestStreet;
 float bestStreetValue = 0;
Strategy s;
	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
	if (mr.affordableStreets() > 0 && mr.buildableStreetPaths(mr.getMe()).size() > 0){
		bestStreet = evaluateStreet(mr);
		ca.buildStreet(bestStreet);
		ca.endTurn();
	} else {
		ca.endTurn();
	}

	}
	
	public float evaluate(ModelReader mr, ControllerAdapter ca) throws Exception {
		if (!(mr.affordableStreets() > 0 
			&& mr.buildableStreetPaths(mr.getMe()).size() > 0)) return -1;
		float value=0;
		value=evaluateStreetValue(mr,evaluateStreet(mr));
		return value/4;
	}
	
	public Path evaluateStreet(ModelReader mr){
		
		Set<Path> buildableStreet = mr.buildableStreetPaths(mr.getMe());
		for (Path p : buildableStreet){
			float currentValue = evaluateStreetValue(mr, p);
			if (currentValue > bestStreetValue){
				bestStreetValue = currentValue;
				bestStreet = p;
			}
		}
		return bestStreet;
	}
	
	public float evaluateStreetValue(ModelReader mr,Path p){
		double value=0;
		Set<Intersection> intersections=mr.getIntersectionsFromPath(p);
		for (Intersection i: intersections){
			if (i.hasOwner()) value=value+0.1;
			else value=value+0.15;
		}
		return (float) value;
	}
	
}
