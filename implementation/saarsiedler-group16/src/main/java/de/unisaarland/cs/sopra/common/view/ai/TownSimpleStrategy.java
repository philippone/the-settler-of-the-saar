package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.ModelReader;

public class TownSimpleStrategy extends Strategy {

	public TownSimpleStrategy(ModelReader mr) {
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
		// TODO Auto-generated method stub
		return 0;
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
		double townValue = 0.0;
		if (mr.affordableSettlements(BuildingType.Village) < 1 && mr.affordableSettlements(BuildingType.Town) > 0)
			townValue = 1.0;
		return townValue;
	}

	@Override
	public double evaluate(BuildCatapult stroke) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evaluate(BuildStreet stroke) {
		// TODO Auto-generated method stub
		return 0;
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
