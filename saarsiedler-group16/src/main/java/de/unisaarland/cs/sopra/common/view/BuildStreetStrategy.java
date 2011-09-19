package de.unisaarland.cs.sopra.common.view;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.sopra.common.model.Street;

public class BuildStreetStrategy extends Strategy {
	
	public BuildStreetStrategy() {
		super(0, Street.getPrice());
	}

	Path bestStreet;
 	float bestStreetValue = 0;
 	ResourcePackage resourcePackageTrade, tradeOffer;
 
	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		if (mr.affordableStreets() > 0 && mr.buildableStreetPaths(mr.getMe()).size() > 0){
			bestStreet = evaluateStreet(mr);
			ca.buildStreet(bestStreet);
			ca.endTurn();
		} else {
			ca.endTurn();
		}
		if (mr.affordableStreets() > 0 && mr.buildableStreetPaths(mr.getMe()).size() > 0){
			bestStreet = evaluateStreet(mr);
			ca.buildStreet(bestStreet);
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
		double value=0;
		Set<Intersection> intersections=mr.getIntersectionsFromPath(p);
		for (Intersection i: intersections){
			if (i.hasOwner()) value=value+0.1;
			else value=value+0.15;
		}
		return (float) value;
	}
	


	
	@Override
	public boolean useable() {
		//TODO implement this operation
		throw new UnsupportedOperationException();
	}

}
