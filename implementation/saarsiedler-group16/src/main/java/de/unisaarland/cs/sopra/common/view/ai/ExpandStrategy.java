package de.unisaarland.cs.sopra.common.view.ai;

import java.util.HashSet;
import java.util.Set;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;

public class ExpandStrategy extends Strategy {

	public ExpandStrategy(ModelReader mr) {
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
			return true;
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
		
		return 0;
	}

	@Override
	public double evaluate(AttackSettlement stroke) {
	
		return 0;
	}

	@Override
	public double evaluate(BuildVillage stroke) {
		double intersectionValue = 0.0;
		double resourceValue = 0.0;
		double numberValue = 0.0;
		double harborValue = 0.0;
		 Intersection location = stroke.getDestination();
		 Set<Field> fields = mr.getFieldsFromIntersection(location);
		 for (Field field : fields) {
				
				Player player = mr.getMe();
				Set<Intersection> buildings = mr.getSettlements(player, BuildingType.Village);
				Set<Field> playerFields = new HashSet<Field>();
				for (Intersection i : buildings){
					Set<Field> fieldsforIntersection = mr.getFieldsFromIntersection(i);
					playerFields.addAll(fieldsforIntersection);
				}
			FieldType type = field.getFieldType();
			if (type == FieldType.FOREST) {
				if (playerFields.contains(FieldType.FOREST)) {
					resourceValue = resourceValue + 0.1666;
				}
				resourceValue = resourceValue + 0.3333;
			} else if (type == FieldType.HILLS) {
				if (playerFields.contains(FieldType.HILLS)) {
					resourceValue = resourceValue + 0.1666;
				}
				resourceValue = resourceValue + 0.3333;
			} else if (type == FieldType.PASTURE) {
				if (playerFields.contains(FieldType.PASTURE)) {
					resourceValue = resourceValue + 0.1666;
				}
				resourceValue = resourceValue + 0.3333;
			} else if (type == FieldType.FIELDS) {
				if (playerFields.contains(FieldType.FIELDS)) {
					resourceValue = resourceValue + 0.1666;
				}
				resourceValue = resourceValue + 0.3333;
			} else if (type == FieldType.MOUNTAINS) {
				if (playerFields.contains(FieldType.MOUNTAINS)) {
					resourceValue = resourceValue + 0.0833;
				}
				resourceValue = resourceValue + 0.1666;
			}
		}

		 intersectionValue = resourceValue;
		return intersectionValue;
	}

	@Override
	public double evaluate(BuildTown stroke) {
		double townValue = 0.0;
		double resourceValue = 0.0; 
		double earlyGameStageFactor = 0.5;
		Intersection i = stroke.getDestination();
		Set<Field> neighbourFields = mr.getFieldsFromIntersection(i);
		for (Field f: neighbourFields){
			if (!f.hasRobber()) {
				if (f.getNumber() != -1) 
					resourceValue = resourceValue + 0.3333;
			}
		}
		if (mr.getMe().getVictoryPoints() <= (mr.getMaxVictoryPoints()/2))
		townValue = resourceValue * earlyGameStageFactor;
		return townValue;
		
	}

	@Override
	public double evaluate(BuildCatapult stroke) {

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
