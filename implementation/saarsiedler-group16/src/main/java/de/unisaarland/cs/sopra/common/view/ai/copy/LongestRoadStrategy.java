package de.unisaarland.cs.sopra.common.view.ai.copy;

import java.util.List;
import java.util.Set;

import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

public class LongestRoadStrategy extends Strategy{

	public LongestRoadStrategy(ModelReader mr) {
		super(mr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean evaluates(Stroke s) {
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
			return false;
		case BUILD_CATAPULT:
			return false;
		case BUILD_STREET:
			return true;
		case MOVE_CATAPULT:
			return false;
		case MOVE_ROBBER:
			return false;
		case RETURN_RESOURCES:
			return false;
		}
	}

	@Override
	public double importance() {
		double importance=0.3;
		int longestClaimedRoadSize=mr.getLongestClaimedRoad().size();
		int myLongestRoadSize=mr.calculateLongestRoads(mr.getMe()).get(0).size();
		if (myLongestRoadSize<longestClaimedRoadSize && myLongestRoadSize>longestClaimedRoadSize-3) importance=importance+0.3;		
		if (mr.getMe().getVictoryPoints()>mr.getMaxVictoryPoints()-3) importance=importance+0.2;
		return importance;
	}

	@Override
	public double evaluate(AttackCatapult stroke) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evaluate(AttackSettlement stroke) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evaluate(BuildVillage stroke) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evaluate(BuildTown stroke) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evaluate(BuildCatapult stroke) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evaluate(BuildStreet stroke) {
		double value=0;
		int myVictoryPoints = mr.getMe().getVictoryPoints();
		double earlyGameFactor =(double) (myVictoryPoints) / (double)(mr.getMaxVictoryPoints());
		if (earlyGameFactor < mr.getMaxVictoryPoints() -2)
			return 0.0;
		else if (earlyGameFactor > mr.getMaxVictoryPoints() -3)
			return 1;
		Path location = stroke.getDestination();
		List<List<Path>> roadList=mr.calculateLongestRoads(mr.getMe());
		Set<Intersection> si=mr.getIntersectionsFromPath(location);
		for (Intersection i: si) 
			if (i.hasOwner() && i.getOwner()!=mr.getMe()) 
				return 0;
				// it would be odd to build a path here
				// since the longest road cannot go through ennemy Intersections
		Set<Path> sp=mr.getPathsFromPath(location);
		for (Path p: sp)
			if (p.hasStreet() && p.getStreetOwner()==mr.getMe())
				value=value+0.1;
				// trying to join the max number of paths
		for (List<Path> road: roadList){
			int howManyPathsOfTheRoadAreConnectedToThisOne=0;
			for (Path p1: road)
				if (sp.contains(p1))
					howManyPathsOfTheRoadAreConnectedToThisOne ++;
			if (howManyPathsOfTheRoadAreConnectedToThisOne==1) value=value+0.5;
			// trying to lenghten the road
			else if (howManyPathsOfTheRoadAreConnectedToThisOne>1) value=value+0.1;
		}
		return Math.min(1, value);
	}

	@Override
	public double evaluate(MoveCatapult stroke) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evaluate(MoveRobber stroke) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evaluate(ReturnResources stroke) {
		// TODO Auto-generated method stub
		return 0;
	}

}
