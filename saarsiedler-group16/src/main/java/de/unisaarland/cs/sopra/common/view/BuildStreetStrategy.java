package de.unisaarland.cs.sopra.common.view;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

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
		return 1;
	}
	
	public int evaluate(){
		// TODO implement this method
		return 0;
	}
}
