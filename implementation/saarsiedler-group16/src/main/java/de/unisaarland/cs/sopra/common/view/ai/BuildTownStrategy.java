package de.unisaarland.cs.sopra.common.view.ai;

import java.util.Set;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;

public class BuildTownStrategy extends Strategy {

	public BuildTownStrategy(ModelReader mr) {
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
		double fieldValue = 0.0;
		double numberValue = 0.0;
		double earlyGameStageFactor = 0.3;
		Intersection town = stroke.getDestination();
		Set<Field> fields = mr.getFieldsFromIntersection(town);
		int n = 0;
		for (Field f : fields){
			//check wheter it is not a desert or a waterfield
			if (f.getNumber() != -1){
				// check if there is currently a robber on the field
				if (!f.hasRobber()){
					fieldValue = fieldValue + 0.3;
					n = f.getNumber();
					if ( n == 2 || n == 12){
						numberValue = numberValue + 0.007143;
					} else 
						if (n == 3 || n == 11)
							numberValue = numberValue +  0.014286;
						else 
							if(n == 4 || n == 10)
								numberValue = numberValue + 0.019048;
							else 
								if (n == 5 || n == 9)
									numberValue = numberValue + 0.02619;
								else 
									if (n == 6 || n == 8)
										numberValue = numberValue + 0.03334;
				}
			}
		}
//		if (mr.getMe().getVictoryPoints() <= (mr.getMaxVictoryPoints()/2)){
//		townValue = (fieldValue + numberValue )* earlyGameStageFactor;
//		return townValue;
//		}
		
		
		// if building a town is the only thing we have resources for
		// we should build it instead of holding on to the resources
		if (mr.affordableSettlements(BuildingType.Town) > 0 && mr.affordableSettlements(BuildingType.Village) < 1 && mr.affordableStreets() < 1){
		townValue = fieldValue + numberValue;
		return townValue;
		}
		if (mr.getMe().getVictoryPoints() <= (mr.getMaxVictoryPoints()/2)){
		townValue = (fieldValue + numberValue) * 0.5;
		return townValue;
		}
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
