package de.unisaarland.cs.sopra.common.view.ai;

import java.util.Set;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;

public class MoveRobberStrategy extends Strategy {

	 
	 public MoveRobberStrategy(ModelReader mr) {
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
				return false;
			case BUILD_CATAPULT:
				return false;
			case BUILD_STREET:
				return false;
			case MOVE_CATAPULT:
				return false;
			case MOVE_ROBBER:
				return true;
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
	
		return 0;
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
		double destinationValue = 0.0;
		double sourceValue = 0.0;
		double ownSettlementFactor = 0.4;
		double moveRobberValue = 0.0;
		Field sourceField = stroke.getSource();
		Field destinationField = stroke.getDestination();
		Set<Intersection>  destIntersections= mr.getIntersectionsFromField(destinationField);
		for (Intersection i: destIntersections){
			if (i.hasOwner()) { 
				if (i.getOwner() != mr.getMe()){
					if (i.getBuildingType() == BuildingType.Village)
						destinationValue = destinationValue + 0.1667;
					else if (i.getBuildingType() == BuildingType.Town )
						destinationValue = destinationValue + 0.2333;
				}	else
						destinationValue = destinationValue * ownSettlementFactor;
			}
			else destinationValue = destinationValue + 0.0833;
		}
		Set<Intersection> sourceIntersections = mr.getIntersectionsFromField(sourceField);
		for (Intersection i : sourceIntersections) {
			if (i.hasOwner()) {
				if (i.getOwner() == mr.getMe()) {
					if (i.getBuildingType() == BuildingType.Village) {
						sourceValue = sourceValue + 0.1667;
					}  else 
						if (i.getBuildingType() == BuildingType.Town) 
							sourceValue = sourceValue + 0.2333;
				}
				sourceValue = sourceValue * 0.9;
			}
			sourceValue = sourceValue + 0.0001;
		}
		moveRobberValue = sourceValue + destinationValue;
		return moveRobberValue;
	}

	@Override
	public double evaluate(ReturnResources stroke) {
	
		return 0;
	}
	
	public double importance(){
		return 1;
	}

}
