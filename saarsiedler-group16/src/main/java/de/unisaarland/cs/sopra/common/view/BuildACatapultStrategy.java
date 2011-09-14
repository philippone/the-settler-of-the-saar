package de.unisaarland.cs.sopra.common.view;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;

public class BuildACatapultStrategy implements Strategy {

	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		// TODO Auto-generated method stub
		if (mr.affordableCatapultBuild()>0){
			Path bestPath=chooseBestPath(mr);
			ca.buildCatapult(bestPath);
		}
	}
	
	private Path chooseBestPath(ModelReader mr){
		Path bestPath=null;
		Set<Path> paths=mr.buildableCatapultPaths(mr.getCurrentPlayer());
		float bestValue=0;
		float value;
		for (Path p: paths){
			value=evaluatePath(mr,p);
			if (value>=bestValue){
				bestValue=value;
				bestPath=p;
			}
		}
		return bestPath;
	}
	
	private float evaluatePath(ModelReader mr,Path p){
		float pathValue=0;
		Player player=mr.getCurrentPlayer();
		// checking if we can attack a catapult immediately
		if (p.hasCatapult() && p.getCatapultOwner()!=player) pathValue=(float)0.4;
		Set<Path> neighborPaths=mr.getPathsFromPath(p);
		// checking if we can attack a catapult soon
		for(Path p1 : neighborPaths){
			if (p1.hasCatapult() && p1.getCatapultOwner()!=player) {
				pathValue=(float)(pathValue+0.15);
			}
		}
		Set<Intersection> neighborIntersections=mr.getIntersectionsFromPath(p);
		// checking if we can attack a building soon
		for(Intersection i : neighborIntersections){
			if (i.hasOwner() && i.getOwner()!=player) {
				if (i.getBuildingType()==BuildingType.Town) pathValue=(float)(pathValue+0.15);
				else if (i.getBuildingType()==BuildingType.Village) pathValue=(float)(pathValue+0.1);
			}
		}
		return pathValue;
	}

}
