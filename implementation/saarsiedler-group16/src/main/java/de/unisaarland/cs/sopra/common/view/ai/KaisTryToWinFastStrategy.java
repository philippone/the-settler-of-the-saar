package de.unisaarland.cs.sopra.common.view.ai;

import java.util.HashSet;
import java.util.Set;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

public class KaisTryToWinFastStrategy extends Strategy {

	public KaisTryToWinFastStrategy(ModelReader mr) {
		super(mr);
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
			int numberOfVillages = mr.getSettlements(mr.getMe(), BuildingType.Village).size();
			int numberOfTowns = mr.getSettlements(mr.getMe(), BuildingType.Town).size();
			int possibleTowns = Math.min(mr.getMaxBuilding(BuildingType.Town)-numberOfTowns, numberOfVillages);
			int missingVicotryPoints = mr.getMaxVictoryPoints()-mr.getMe().getVictoryPoints();
			return (missingVicotryPoints < possibleTowns);
		case BUILD_CATAPULT:
			return false;
		case BUILD_STREET:
			return (mr.getMe().getVictoryPoints()+2 == mr.getMaxVictoryPoints());
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
		return 1;
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
		return 1;
	}

	@Override
	public double evaluate(BuildCatapult stroke) {
		return 0;
	}

	@Override
	public double evaluate(BuildStreet stroke) {
		boolean hasUpperStreet = false;
		boolean hasLowerStreet = false;
		boolean isUpper = true;
		for (Intersection inter : mr.getIntersectionsFromPath(stroke.getDestination())){
			for (Path neighbour : mr.getPathsFromIntersection(inter)){
				if (neighbour != stroke.getDestination() && neighbour.hasStreet() && neighbour.getStreetOwner() == mr.getMe()){
					if (isUpper){
						hasUpperStreet = true;
						isUpper = false;
					}
					else {
						hasLowerStreet = true;
					}
				}
			}
		}
		if (hasUpperStreet && hasLowerStreet) return 1;
		return 0;
	}

	@Override
	public double evaluate(MoveCatapult stroke) {
		return 0;
	}

	@Override
	public double evaluate(MoveRobber stroke) {
		return 0;
	}

	@Override
	public double evaluate(ReturnResources stroke) {
		return 0;
	}

}
