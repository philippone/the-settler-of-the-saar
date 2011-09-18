package de.unisaarland.cs.sopra.common.view;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;


public class MoveCatapultStrategy implements Strategy {
 Path sourcePath;
	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		if (!(mr.getMe().getResources().copy().add(new ResourcePackage(0,0,0,-1,0))).hasNegativeResources()){
		Path destinationPath = evaluateStreet(mr);
		ca.moveCatapult(sourcePath, destinationPath);
		ca.endTurn();
	} else {
		ca.endTurn();
	  }
}
	public float evaluateStreetValue(ModelReader mr, Path p) {
		if (p.hasCatapult() && p.getCatapultOwner() != mr.getMe()) {
			return 1;
		} 
		float value=0;
		Set<Intersection> intersections=mr.getIntersectionsFromPath(p);
		for (Intersection i: intersections){
			if (i.hasOwner() && i.getOwner()!=mr.getMe()) value=(float) (value+0.3);
		}
		Set<Path> paths=mr.getPathsFromPath(p);
		for (Path p1: paths){
			if (p1.hasCatapult() && p1.getCatapultOwner() != mr.getMe()) value=(float) (value+0.3);
			
		}
		return value;
	}
	
	public Path evaluateStreet(ModelReader mr) {
		float bestValue = 0;
		Path bestPath = null;
		Set<Path> catapults = mr.getCatapults(mr.getMe());
		for (Path p : catapults) {
			Set<Path> neighbourPaths = mr.getPathsFromPath(p);
			for (Path path : neighbourPaths) {
				float currentValue = evaluateStreetValue(mr, path);
				if (currentValue > bestValue){
					bestValue = currentValue;
					sourcePath = new Path(p.getLocation());
					bestPath = path;
				}
			}

		}
		return bestPath;
	}


	public float evaluate(ModelReader mr,ControllerAdapter ca) {

		//TODO return -1 if not enough resources
		if (mr.getCatapults(mr.getMe()).size() < 1) {
			return -1;
		}
		// TODO: check the trade
		return 1;
	}
	
	public AIGameStats getGameStats(ModelReader mr){
		Player player = mr.getMe();
		if (mr.getCatapults(player).size() < 1)
			return new AIGameStats(player, this, new ResourcePackage(0, 0, 0, 0, 0), 0);
		ResourcePackage resourcePackage = player.getResources().copy().add(new ResourcePackage(0, 0, 0 ,-1 ,0));
		int victoryPoints = player.getVictoryPoints();
		AIGameStats gameStats = new AIGameStats(player, this, resourcePackage, victoryPoints);
		return gameStats;
	}

}
