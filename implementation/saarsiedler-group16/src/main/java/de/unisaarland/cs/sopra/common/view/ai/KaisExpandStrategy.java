package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

public class KaisExpandStrategy extends Strategy {

	public KaisExpandStrategy(ModelReader mr) {
		super(mr);
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
		// ensure that there is a maximum of two settlements on one field
		Intersection destination = stroke.getDestination();
		for (Field neighbour : mr.getFieldsFromIntersection(destination)){
			int villagesCloseToField = 0;
			for (Intersection possibleVillage : mr.getIntersectionsFromField(neighbour)){
				if (possibleVillage.hasOwner() && possibleVillage.getOwner() == mr.getMe()) villagesCloseToField++;
			}
			if (villagesCloseToField > 1)	return 0;
		}
		return 0.5;
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
		Path destination = stroke.getDestination();
		for (Intersection possibleVillage : mr.getIntersectionsFromPath(destination)){
			if (possibleVillage.hasOwner()) return 0;
		}
		for (Intersection neighbour : mr.getIntersectionsFromPath(destination)){
			if (neighbour.hasOwner() && neighbour.getOwner() == mr.getMe())
				return 0;
		}
		int buildingsAround;
		for (Field neighbour : mr.getFieldsFromPath(destination)){
			buildingsAround = 0;
			for (Intersection possibleVillage : mr.getIntersectionsFromField(neighbour)){
				if (possibleVillage.hasOwner() && possibleVillage.getOwner() == mr.getMe())
					buildingsAround++;
			}
			if (buildingsAround > 1) return 0;
		}
		int pathsAround;
		for (Field neighbour : mr.getFieldsFromPath(destination)){
			pathsAround = 0;
			for (Path neighbourPaths : mr.getPathsFromField(neighbour)){
				if (neighbourPaths.hasStreet()) pathsAround++;
			}
			if (pathsAround > 2) return 0;
		}
		for (Intersection neighbourInters : mr.getIntersectionsFromPath(destination)){
			int waterfields = 0;
			for (Field neighbourField : mr.getFieldsFromIntersection(neighbourInters)){
				if (neighbourField.getFieldType() == FieldType.WATER) waterfields++;
			}
			if (waterfields == 2) return 0;
		}
		int buildableVillages = mr.buildableVillageIntersections(mr.getMe()).size();
		if (buildableVillages > 0 && mr.getSettlements(mr.getMe(), BuildingType.Village).size() < mr.getMaxBuilding(BuildingType.Village)) return 0;
		return 1;
	}
	
	//TODO schlechtere bewertung für intersection an feld derren nachbarfelder schon 2
	// settlements besitzen.
	// Straßen werden noch scheiße geabaut! =D

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
