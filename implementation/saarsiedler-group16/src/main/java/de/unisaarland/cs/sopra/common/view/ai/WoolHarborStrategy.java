package de.unisaarland.cs.sopra.common.view.ai;

import java.util.Set;

import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

public class WoolHarborStrategy extends Strategy {

	public WoolHarborStrategy(ModelReader mr) {
		super(mr);
		// TODO Auto-generated constructor stub
	}
	@Override
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
		double harborValue = 0.0;
		Intersection village = stroke.getDestination();
		Set<Path> paths = mr.getPathsFromIntersection(village);
		for (Path p : paths){
			if (p.getHarborType() != null){
				harborValue = harborValue + 0.3;
				HarborType harborType = p.getHarborType();
				if (!mr.getHarborTypes(mr.getMe()).contains(harborType)) {
					if (harborType == HarborType.WOOL_HARBOR)
						harborValue = harborValue + 0.7;
				}  
				harborValue = harborValue + 0.2;
					
			} 
		}
		return harborValue;
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
		double NPValue = 0.0;
		double IValue = 0.0;
		double PathValue = 0.0;
		Set<Path> paths = mr.getPathsFromPath(path);
		for (Path p : paths) {
			Set<Intersection> intersections = mr.getIntersectionsFromPath(p);
			for (Intersection i : intersections) {
				if (i.hasOwner())
					IValue = 0.1;
			}
			IValue = 0.8;
			Set<Field> fields = mr.getFieldsFromPath(p);
			for (Field f : fields){
				if (f.getNumber() != -1)
					NPValue = NPValue + 0.1;
			}
		}
		PathValue = NPValue + IValue;
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
