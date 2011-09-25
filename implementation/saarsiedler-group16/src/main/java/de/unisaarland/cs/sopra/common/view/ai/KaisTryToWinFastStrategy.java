package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.ModelReader;

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
			return true;
		case BUILD_CATAPULT:
			return false;
		case BUILD_STREET:
			return false;
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
		/*
		int numberOfVillages = mr.getSettlements(mr.getMe(), BuildingType.Village).size();
		int numberOfTowns = mr.getSettlements(mr.getMe(), BuildingType.Town).size();
		int possibleTowns = Math.min(mr.getMaxBuilding(BuildingType.Town)-numberOfTowns, numberOfVillages);
		int missingVicotryPoints = mr.getMaxVictoryPoints()-mr.getMe().getVictoryPoints();
		if (missingVicotryPoints < possibleTowns) return 1;
		*/
		return 1;
	}

	@Override
	public double evaluate(BuildCatapult stroke) {
		return 0;
	}

	@Override
	public double evaluate(BuildStreet stroke) {
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
