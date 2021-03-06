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

public class HarborStrategy extends Strategy {

	public HarborStrategy(ModelReader mr) {
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
		double resourceValue = 0.0;
		double intersectValue = 0.0;
		Intersection village = stroke.getDestination();
		Set<Path> paths = mr.getPathsFromIntersection(village);
		Set<Field> fields = mr.getFieldsFromIntersection(village);
		for (Field f : fields){
			if (f.getResource() != null)
				harborValue = harborValue + 0.15;
		}
		for (Path p : paths){
			if (p.getHarborType() == HarborType.WOOL_HARBOR && 
					!mr.getHarborTypes(mr.getMe()).contains(HarborType.WOOL_HARBOR))
						harborValue = harborValue + 0.0;
			else if (p.getHarborType() == HarborType.BRICK_HARBOR &&
					!mr.getHarborTypes(mr.getMe()).contains(HarborType.BRICK_HARBOR))
						harborValue = harborValue + 0.0;
			else if (p.getHarborType() == HarborType.ORE_HARBOR &&
					!mr.getHarborTypes(mr.getMe()).contains(HarborType.ORE_HARBOR))
						harborValue = harborValue + 5.0;
			else if (p.getHarborType() == HarborType.GRAIN_HARBOR &&
					!mr.getHarborTypes(mr.getMe()).contains(HarborType.GRAIN_HARBOR))
						harborValue = harborValue + 0.0;
			else if (p.getHarborType() == HarborType.LUMBER_HARBOR &&
					!mr.getHarborTypes(mr.getMe()).contains(HarborType.LUMBER_HARBOR))
						harborValue = harborValue + 0.0;
			else if (p.getHarborType() == HarborType.GENERAL_HARBOR &&
					!mr.getHarborTypes(mr.getMe()).contains(HarborType.GENERAL_HARBOR))
						harborValue = harborValue + 0.04;
			Set<Intersection> buildings = mr.getSettlements(mr.getMe(), BuildingType.Village);
			Set<Field> playerFields = new HashSet<Field>();
			for (Intersection i : buildings){
				Set<Field> fieldsforIntersection = mr.getFieldsFromIntersection(i);
				playerFields.addAll(fieldsforIntersection);
			}
			Set<Field> fieldsforPath = mr.getFieldsFromPath(p);
			playerFields.addAll(fieldsforPath);
		for (Field field : fieldsforPath) {
	FieldType type = field.getFieldType();
	if (type == FieldType.FOREST) {
		if (!playerFields.contains(FieldType.FOREST))
			harborValue = harborValue + 0.1;
				harborValue = harborValue + 0.05;
	} else if (type == FieldType.HILLS){
		if (!playerFields.contains(FieldType.HILLS))
			harborValue = 0.1;
		harborValue = 0.05;
	}
	else if (type == FieldType.FIELDS){
		if (!playerFields.contains(FieldType.FIELDS))
			harborValue = harborValue + 0.1;
		harborValue = harborValue + 0.05;
	}
	else if (type == FieldType.PASTURE){
		if (!playerFields.contains(FieldType.PASTURE))
			harborValue = harborValue + 0.1;
				harborValue = harborValue + 0.05;
	}
	else if (type == FieldType.MOUNTAINS){
		if (!playerFields.contains(FieldType.MOUNTAINS))
			harborValue = harborValue + 0.1;
				harborValue = harborValue + 0.05;
	}
 }
		
		}
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
			if (!playerFields.contains(FieldType.FOREST) ||
					mr.getHarborTypes(mr.getMe()).contains(HarborType.LUMBER_HARBOR))
				resourceValue = resourceValue + 0.3333;
					resourceValue = resourceValue + 0.1667;
		} else if (type == FieldType.HILLS){
			if (!playerFields.contains(FieldType.HILLS) ||
					mr.getHarborTypes(mr.getMe()).contains(HarborType.BRICK_HARBOR))
				resourceValue = resourceValue + 0.3333;
					resourceValue = resourceValue + 0.1667;
		}
		else if (type == FieldType.FIELDS){
			if (!playerFields.contains(FieldType.FIELDS) ||
					mr.getHarborTypes(mr.getMe()).contains(HarborType.GRAIN_HARBOR))
				resourceValue = resourceValue + 0.3333;
					resourceValue = resourceValue + 0.1667;
		}
		else if (type == FieldType.PASTURE){
			if (!playerFields.contains(FieldType.PASTURE) ||
					mr.getHarborTypes(mr.getMe()).contains(HarborType.WOOL_HARBOR))
				resourceValue = resourceValue + 0.3333;
					resourceValue = resourceValue + 0.1667;
		}
		else if (type == FieldType.MOUNTAINS){
			if (!playerFields.contains(FieldType.MOUNTAINS) ||
					mr.getHarborTypes(mr.getMe()).contains(HarborType.ORE_HARBOR))
				resourceValue = resourceValue + 0.3333;
					resourceValue =resourceValue + 0.1667;
		}
	 }
		
		intersectValue = harborValue + (resourceValue)/2.0;
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

	@Override
	public double importance() {
		return 1;
	}

}
