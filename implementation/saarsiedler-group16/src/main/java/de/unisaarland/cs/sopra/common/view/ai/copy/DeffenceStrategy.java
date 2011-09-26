package de.unisaarland.cs.sopra.common.view.ai.copy;

import java.util.Set;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

public class DeffenceStrategy extends Strategy {

	public DeffenceStrategy(ModelReader mr) {
		super(mr);
		// TODO Auto-generated constructor stub
	}

	public boolean evaluates(Stroke s){
		switch(s.getType()){
		default:
			throw new IllegalArgumentException("The stroke is no valid stroke!");
		case ATTACK_CATAPULT:
			return false;
		case ATTACK_SETTLEMENT:
			return false;
		case BUILD_VILLAGE:
			return false;
		case BUILD_TOWN:
			return true;
		case BUILD_CATAPULT:
			return true;
		case BUILD_STREET:
			return false;
		case MOVE_CATAPULT:
			return true;
		case MOVE_ROBBER:
			return false;
		case RETURN_RESOURCES:
			return false;
		}
	}

	@Override
	public double evaluate(AttackCatapult stroke) {
		
		return 0;
	}

	@Override
	public double evaluate(AttackSettlement stroke) {
		
		return 0;
	}

	@Override
	public double evaluate(BuildVillage stroke) {

		return 0;
	}

	@Override
	public double evaluate(BuildTown stroke) {
		//TODO maybe set townValue to 0.5
		double townValue = 0.4;
		Intersection settlementLocation = stroke.getDestination();
			Set<Path> neighbours = mr.getPathsFromIntersection(settlementLocation);
			for (Path p : neighbours){
				if (p.hasCatapult() && p.getCatapultOwner() != mr.getMe())
					townValue = 1.0;
			}
		
		return townValue;
	}

	@Override
	public double evaluate(BuildCatapult stroke) {
		double buildCatapultValue = 0.0;
		Path path =  stroke.getDestination();
				Set<Path> neighbourPaths = mr.getPathsFromPath(path);
				for (Path p : neighbourPaths) {
					if (p.hasCatapult() && p.getCatapultOwner() != mr.getMe())
						buildCatapultValue = buildCatapultValue + 1.0;
				}

		return buildCatapultValue;
	}

	@Override
	public double evaluate(BuildStreet stroke) {
		
		return 0;
	}

	@Override
	public double evaluate(MoveCatapult stroke) {
		double moveCatapultValue = 0.0;
		 Path source = stroke.getSource();
		 Path destination = stroke.getDestination();
		if (destination.hasCatapult())	{
					if (destination.getCatapultOwner() != mr.getMe()){
						Set<Intersection> destIntersects = mr.getIntersectionsFromPath(destination);
						Set<Intersection> sourceAndDest = mr.getIntersectionsFromPath(source);
						sourceAndDest.addAll(destIntersects);
						for (Intersection i : sourceAndDest){
							if (i.hasOwner() && i.getOwner() == mr.getMe())
								moveCatapultValue = 1.0;
						}
						moveCatapultValue = 0.5;
					}
					moveCatapultValue = 0.0;
					
				}
					moveCatapultValue = 0.1;
			
		
		return moveCatapultValue;
	}

	@Override
	public double evaluate(MoveRobber stroke) {
		
		return 0;
	}

	@Override
	public double evaluate(ReturnResources stroke) {
		
		return 0;
	}

	@Override
	public double importance() {
		return 0.5;
	}

}
