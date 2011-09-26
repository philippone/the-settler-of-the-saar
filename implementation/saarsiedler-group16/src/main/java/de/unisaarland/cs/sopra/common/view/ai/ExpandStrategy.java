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
		int numOfVillages = 0;
		int resources = 0;
		 Intersection location = stroke.getDestination();
		 Set<Field> fields = mr.getFieldsFromIntersection(location);
		 
		 Player player = mr.getMe();
		 Set<Intersection> buildings = mr.getSettlements(player, BuildingType.Village);
		 buildings.addAll(mr.getSettlements(player, BuildingType.Town));
		 //playerFields contains all fields on which we have a building
		 Set<Field> playerFields = new HashSet<Field>();
		 //numbers contains all numbers of the fields on which we have a building 
		 Set<Integer> numbers = new HashSet<Integer>();
			for (Intersection i : buildings){
				Set<Field> fieldsforIntersection = mr.getFieldsFromIntersection(i);
				playerFields.addAll(fieldsforIntersection);
				for (Field f : fieldsforIntersection){
					numbers.add(f.getNumber());
				}
			}
		 for (Field field : fields) {
			FieldType type = field.getFieldType();
			if (type == FieldType.FOREST) {
				resources += 1;
				if (playerFields.contains(FieldType.FOREST)) {
					// we want to make sure, that we will get a resource
					// every turn
					if (!numbers.contains(field.getNumber()))
					resourceValue = resourceValue + 0.3334;
						resourceValue = resourceValue + 0.1667;
				}
				// if we do not own the resource yet, it has understandably a higher value
				resourceValue = resourceValue + 0.3334;
			} else if (type == FieldType.HILLS) {
				resources += 1;
				if (playerFields.contains(FieldType.HILLS)) {
					if (!numbers.contains(field.getNumber()))
					resourceValue = resourceValue + 0.3334;
						resourceValue = resourceValue + 0.1667;
				}
				resourceValue = resourceValue + 0.3334;
			} else if (type == FieldType.PASTURE) {
				resources += 1;
				if (playerFields.contains(FieldType.PASTURE)) {
					if (!numbers.contains(field.getNumber()))
					resourceValue = resourceValue + 0.3334;
						resourceValue = resourceValue + 0.1;
				}
				resourceValue = resourceValue + 0.3334;
			} else if (type == FieldType.FIELDS) {
				resources += 1;
				if (playerFields.contains(FieldType.FIELDS)) {
					if (!numbers.contains(field.getNumber()))
					resourceValue = resourceValue + 0.3334;
						resourceValue = resourceValue + 0.1667;
				}
				resourceValue = resourceValue + 0.3334;
			} else if (type == FieldType.MOUNTAINS) {
				resources += 1;
				if (playerFields.contains(FieldType.MOUNTAINS)) {
					if (!numbers.contains(field.getNumber()))
					resourceValue = resourceValue + 0.3334;
						resourceValue = resourceValue + 0.1667;
				}
				resourceValue = resourceValue + 0.3334;
			}
			Set<Intersection> intersections = mr.getIntersectionsFromField(field);
			for (Intersection i : intersections){
				if (i.hasOwner() && i.getOwner() == mr.getMe())
					numOfVillages += 1;
			}

		}   
		 // we want to prevent(as far as possible) building more than 2 villages on one field 
			if (resources >= 2 && numOfVillages == 2){
				intersectionValue = resourceValue*0.25;
				return intersectionValue;
			}  else if (resources <= 1) {
				intersectionValue = resourceValue*0.05;
				return  intersectionValue;
			}
			// when we do not have the resources for a village, but for town instead, 
			// the village has a lower value
			if (mr.affordableSettlements(BuildingType.Town) > 0 
					&& mr.affordableSettlements(BuildingType.Village) < 1){
				intersectionValue = resourceValue*0.6;
				return intersectionValue;
			}
		 intersectionValue = resourceValue;
		return Math.min(1,intersectionValue);
	}

	@Override
	public double evaluate(BuildTown stroke) {
		double townValue = 0.0;
		double resourceValue = 0.0; 
		Intersection i = stroke.getDestination();
		Set<Field> neighbourFields = mr.getFieldsFromIntersection(i);
		for (Field f: neighbourFields){
			// if there is a robber on one of the fields, we don't want to upgrade
			if (!f.hasRobber()) {
				// we do not want to upgrade first the towns that are near deserts or water 
				if (f.getNumber() != -1) 
					resourceValue = resourceValue + 0.3333;
			}
		}
		// if there is currently no place to build a village, it is better to build a town
//		if (mr.affordableSettlements(BuildingType.Village) < 1 
//				&& mr.buildableVillageIntersections(mr.getMe()).size() < 1)
		townValue = resourceValue;
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


	@Override
	public double importance() {
		return 1;
	}

}
