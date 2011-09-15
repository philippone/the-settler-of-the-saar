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
		Set<Path> paths=mr.buildableCatapultPaths(mr.getCurrentPlayer());
		if (mr.getSettlements(mr.getMe(), BuildingType.Town).size() > 0  && mr.affordableCatapultBuild()>0
				&& paths!=null && paths.size()< mr.getCatapults(mr.getMe()).size() && mr.getCatapults(mr.getMe()).size() < mr.getMaxCatapult()){
			Path bestPath=chooseBestPath(mr);
			ca.buildCatapult(bestPath);
			ca.endTurn();
		}
		ca.endTurn();
	}
	
	public Path chooseBestPath(ModelReader mr){
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
		if (p.hasCatapult() && p.getCatapultOwner()!=player) pathValue=(float)0.3;
		Set<Path> neighborPaths=mr.getPathsFromPath(p);
		for(Path p1 : neighborPaths){
			// checking if we can attack a catapult soon
			if (p1.hasCatapult() && p1.getCatapultOwner()!=player) {
				pathValue=(float)(pathValue+0.15);
			}
			Set<Intersection> neighborIntersections=mr.getIntersectionsFromPath(p1);
			// checking if we can attack a building soon
			for(Intersection i : neighborIntersections){
				if (i.hasOwner() && i.getOwner()!=player) pathValue=(float)(pathValue+0.1);
			}
		}
		return pathValue;
	}

}