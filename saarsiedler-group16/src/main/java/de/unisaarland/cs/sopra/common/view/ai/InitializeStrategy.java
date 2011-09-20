package de.unisaarland.cs.sopra.common.view.ai;

import java.util.HashSet;
import java.util.Set;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Resource;

public class InitializeStrategy extends Strategy {
	private Set<Resource> playersResources = new HashSet<Resource>();
	
	public InitializeStrategy(ModelReader mr) {
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
		
		return 0;
	}

	@Override
	public double evaluate(AttackSettlement stroke) {
		
		return 0;
	}

	@Override
	public double evaluate(BuildVillage stroke) {
		double intersectionValue= 0.0;
		double resourceValue= 0.0;
		double numberValue= 0.0;
		int n = 0;
		 Intersection location = stroke.getDestination();
		 Set<Field> fields = mr.getFieldsFromIntersection(location);
		 for (Field field : fields) {
			 n=field.getNumber();
					if (n==2 || n==12)
						numberValue= numberValue + 0.02143;
				else 
					if (n==3 || n==11)
						numberValue= numberValue + 0.04286;
				else 
					if (n==4 || n==10)
						numberValue= numberValue + 0.05714;
				else 
					if (n==5 || n==9) 
						numberValue= numberValue + 0.07857;
				else 
					if (n==6 || n==8) 
						numberValue= numberValue + 0.10;
				
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
					resourceValue = resourceValue + 0.1167;
				}
				resourceValue = resourceValue + 0.2333;
			} else if (type == FieldType.HILLS) {
				if (playerFields.contains(FieldType.HILLS)) {
					resourceValue = resourceValue + 0.1167;
				}
				resourceValue = resourceValue + 0.2333;
			} else if (type == FieldType.PASTURE) {
				if (playerFields.contains(FieldType.PASTURE)) {
					resourceValue = resourceValue + 0.1167;
				}
				resourceValue = resourceValue + 0.2333;
			} else if (type == FieldType.FIELDS) {
				if (playerFields.contains(FieldType.FIELDS)) {
					resourceValue = resourceValue + 0.1167;
				}
				resourceValue = resourceValue + 0.2333;
			} else if (type == FieldType.MOUNTAINS) {
				if (playerFields.contains(FieldType.MOUNTAINS)) {
					resourceValue = resourceValue + 0.1167;
				}
				resourceValue = resourceValue + 0.2333;
			}
		}
		 intersectionValue = resourceValue + numberValue;
		return intersectionValue;
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
