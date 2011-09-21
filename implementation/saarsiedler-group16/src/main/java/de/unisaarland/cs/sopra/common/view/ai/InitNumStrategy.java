package de.unisaarland.cs.sopra.common.view.ai;

import java.util.Set;

import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

public class InitNumStrategy extends Strategy {

	public InitNumStrategy(ModelReader mr) {
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
			return true;
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
		double villageValue = 0.0;
		double numValue = 0.0;
		int n = 0;
		Intersection location = stroke.getDestination();
		Set<Field> fields = mr.getFieldsFromIntersection(location);
		for (Field f : fields){
			n = f.getNumber();
			if ( n == 2 || n == 12){
				numValue = numValue + 0.07143;
			} else 
				if (n == 3 || n == 11)
					numValue = numValue +  0.14286;
				else 
					if(n == 4 || n == 10)
						numValue = numValue + 0.19048;
					else 
						if (n == 5 || n == 9)
							numValue = numValue + 0.2619;
						else 
							if (n == 6 || n == 8)
								numValue = numValue + 0.3333;
					
		}
		villageValue = villageValue + numValue;
		return villageValue;
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
		Path path = stroke.getDestination();
		// IVValue is the value of the intersection to which we want to build on
		double IValue = 0.0;
		// NFValue evaluates the FieldType of the intersection we want to build on
		double NFValue = 0.0;
		double PathValue = 0.0;
		//if we can build a village, PathValue <= 0.5
		double villagesFactor =0.0;
		Set<Intersection> intersections = mr.getIntersectionsFromPath(path);
		for (Intersection i : intersections){
			if (!i.hasOwner()) {
				IValue = IValue + 0.7;
				Set<Field> fields =mr.getFieldsFromIntersection(i);
				for (Field f : fields){
					if (f.getNumber() != -1)
						NFValue = NFValue + 0.1;
				}
			} else 
				if (i.getOwner() == mr.getMe())
					IValue = IValue + 0.2;
		}
		PathValue = NFValue + IValue;
		if (mr.buildableVillageIntersections(mr.getMe()).size() > 0)
			PathValue = PathValue*villagesFactor;
		return PathValue;
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
