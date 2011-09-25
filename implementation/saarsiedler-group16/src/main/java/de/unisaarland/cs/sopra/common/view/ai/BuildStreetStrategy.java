package de.unisaarland.cs.sopra.common.view.ai;

import java.util.Set;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

public class BuildStreetStrategy extends Strategy {

	public BuildStreetStrategy(ModelReader mr) {
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
		double streetValue = 0.0;
		boolean hasVillages = false;
		int resources = 0;
		int n = 0;
		Path path = stroke.getDestination();
		// special case for owning a harbor
//		if (mr.getSettlements(mr.getMe(), BuildingType.Village).size() == mr.getInitVillages()
//				&& mr.getStreets(mr.getMe()).size() == mr.getInitVillages()){
//			
//			
//		}
		Set<Intersection> intersectionfromPath = mr.getIntersectionsFromPath(path);
		for (Intersection i: intersectionfromPath) {
			if (i.hasOwner()){
				hasVillages = true;
				if (i.getOwner() == mr.getMe())
					streetValue = streetValue + 0.1;
						return 0.0;
			}
		}
		if (!hasVillages){
			Set<Path> neighbourPaths = mr.getPathsFromPath(path);
			for (Path p : neighbourPaths){
				Set<Intersection> intersections = mr.getIntersectionsFromPath(p);
					for (Intersection intersection : intersections){
						if (intersection.hasOwner()){
							if (intersection.getOwner() != mr.getMe())
								n += 1;
								
						}
					
					}
					if (n == 2)
						return 0.0;
			}
			Set<Field> neighborFields = mr.getFieldsFromPath(path);
			int myVillages = 0;
			int opponentsVillages = 0;
			for (Field f : neighborFields){
				if (f.getNumber() != -1){
					resources += 1;
				}

				Set<Intersection> intersectFromField = mr.getIntersectionsFromField(f);
				for (Intersection intersect : intersectFromField){
					if (intersect.hasOwner()){
						if (intersect.getOwner() == mr.getMe())
							myVillages += 1;
								opponentsVillages +=1;
					}
				}
				if (opponentsVillages == 6)
					return 0.0;
				if (myVillages <= 2)
					streetValue = streetValue + 0.6;
				else if (myVillages > 1 && myVillages < 4)
					streetValue = streetValue + 0.3;
				else if (myVillages >4)
					return 0.0;
			}
		}
		if (mr.buildableVillageIntersections(mr.getMe()).size() > 0)
			streetValue = streetValue*0.4;
		if (resources < 3)
			streetValue = streetValue*0.05;
		return streetValue;
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
