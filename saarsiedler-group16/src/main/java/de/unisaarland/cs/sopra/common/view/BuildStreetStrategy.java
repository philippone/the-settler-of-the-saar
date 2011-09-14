package de.unisaarland.cs.sopra.common.view;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

public class BuildStreetStrategy implements Strategy {
private Path bestStreet;
Strategy s;
	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
	if (mr.affordableStreets() > 0){
		ca.buildStreet(bestStreet);
	} else {
		s = new TradeStrategy();
	}

	}
	
	public Path evaluateStreet(ModelReader mr){
		float bestStreetValue = 0;
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
