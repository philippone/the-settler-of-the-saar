package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.model.ModelReader;

public abstract class Strategy {
	
	private final ModelReader mr;
	
	public Strategy(ModelReader mr){
		this.mr = mr;
	}
	
	public boolean evaluates(Stroke s){
		switch(s.getType()){
		default:
			throw new IllegalArgumentException("The stroke is no valid stroke!");
		case ATTACK_CATAPULT:
			return false;
		case ATTACK_SETTLEMENT:
			return false;
		case BUILD_BUILDING:
			return false;
		case BUILD_CATAPULT:
			return false;
		case BUILD_STREET:
			return false;
		case MOVE_CATAPULT:
			return false;
		}
	}
	
	public final double evaluate(Stroke s){
		switch(s.getType()){
		default:
			throw new IllegalArgumentException("The stroke is no valid stroke!");
		case ATTACK_CATAPULT:
			return evaluate((AttackCatapult)s);
		case ATTACK_SETTLEMENT:
			return evaluate((AttackSettlement)s);
		case BUILD_BUILDING:
			return evaluate((BuildBuilding)s);
		case BUILD_CATAPULT:
			return evaluate((BuildCatapult)s);
		case BUILD_STREET:
			return evaluate((BuildStreet)s);
		case MOVE_CATAPULT:
			return evaluate((MoveCatapult)s);
		}
	}
	
	public abstract double evaluate(AttackCatapult stroke);
	public abstract double evaluate(AttackSettlement stroke);
	public abstract double evaluate(BuildBuilding stroke);
	public abstract double evaluate(BuildCatapult stroke);
	public abstract double evaluate(BuildStreet stroke);
	public abstract double evaluate(MoveCatapult stroke);

}
