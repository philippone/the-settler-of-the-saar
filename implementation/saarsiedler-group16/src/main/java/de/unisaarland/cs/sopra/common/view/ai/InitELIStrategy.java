package de.unisaarland.cs.sopra.common.view.ai;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class InitELIStrategy extends Strategy {

	public InitELIStrategy(ModelReader mr) {
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
		double harborValue = 0.0;
		double resourceValue = 0.0;
		double numberValue = 0.0;
		double intersectionValue = 0.0;
		Iterator<Field> fieldIterator = mr.getFieldIterator();
		ResourcePackage resourcePackage = new ResourcePackage();
		while (fieldIterator.hasNext()){
			Field field = fieldIterator.next();
			if (field.getNumber() != -1)
				resourcePackage.modifyResource(field.getResource(), 1);
			}
		Resource max = Resource.WOOL;
		for (Resource r : Resource.values()){
			max = resourcePackage.getResource(max) < resourcePackage.getResource(r) ? r : max;
		}
			HarborType bestHarbor = HarborType.GENERAL_HARBOR;
			if (max == Resource.LUMBER) {
				bestHarbor = HarborType.LUMBER_HARBOR;
			} else if (max == Resource.BRICK)
				bestHarbor = HarborType.BRICK_HARBOR;
			else if (max == Resource.GRAIN)
				bestHarbor = HarborType.GRAIN_HARBOR;
			else if (max == Resource.ORE) {
				bestHarbor = HarborType.ORE_HARBOR;
			} else if (max == Resource.WOOL)
				bestHarbor = HarborType.WOOL_HARBOR;
			
			Intersection village = stroke.getDestination();
			if (mr.getHarborTypes(mr.getMe()).size() < 1) {
			Set<Path> paths = mr.getPathsFromIntersection(village);
			int m = 0;
				for (Path p : paths) {

					if (p.getHarborType() == bestHarbor){
						harborValue = harborValue + 0.7;
						Set<Field> fields = mr.getFieldsFromIntersection(village);
						for (Field f : fields){
							
							if (f.getNumber() != -1)
								harborValue = harborValue + 0.15;
							m = f.getNumber();
							if ( m == 2 || m == 12){
								numberValue = numberValue + 0.1071;
							} else 
								if (m == 3 || m == 11)
									numberValue = numberValue +  0.2143;
								else 
									if(m == 4 || m == 10)
										numberValue = numberValue + 0.2857;
									else 
										if (m == 5 || m == 9)
											numberValue = numberValue + 0.3929;
										else 
											if (m == 6 || m == 8)
												numberValue = numberValue + 0.5;
							
						}
					}
					else if (p.getHarborType() != null) {
							harborValue = harborValue + 0.5;
							Set<Field> fields = mr.getFieldsFromIntersection(village);
							for (Field f : fields){
								if (f.getNumber() != -1)
									harborValue = harborValue + 0.15;
								m = f.getNumber();
								if ( m == 2 || m == 12){
									numberValue = numberValue + 0.1071;
								} else 
									if (m == 3 || m == 11)
										numberValue = numberValue +  0.2143;
									else 
										if(m == 4 || m == 10)
											numberValue = numberValue + 0.2857;
										else 
											if (m == 5 || m == 9)
												numberValue = numberValue + 0.3929;
											else 
												if (m == 6 || m == 8)
													numberValue = numberValue + 0.5;
							}
					}
				}
				intersectionValue = (harborValue*4.0 + numberValue*0.25)/4.25;
				return intersectionValue;
			}
				Set<Field> fields = mr.getFieldsFromIntersection(village);
				Player player = mr.getMe();
				int n = 0;
				Set<Intersection> buildings = mr.getSettlements(player, BuildingType.Village);
				Set<Field> playerFields = new HashSet<Field>();
				for (Intersection i : buildings){
					Set<Field> fieldsforIntersection = mr.getFieldsFromIntersection(i);
					playerFields.addAll(fieldsforIntersection);
				}
				for (Field f : fields){
					if (f.getNumber() != -1){
							if (f.getFieldType() == FieldType.FOREST){
								if (!playerFields.contains(FieldType.FOREST) || 
										mr.getHarborTypes(player).contains(HarborType.LUMBER_HARBOR))
											resourceValue = resourceValue + 0.3334;
												resourceValue = resourceValue + 0.1667;
							} else
							if (f.getFieldType() == FieldType.HILLS){
								if (!playerFields.contains(FieldType.HILLS) || 
										mr.getHarborTypes(player).contains(HarborType.BRICK_HARBOR))
											resourceValue = resourceValue + 0.3334;
												resourceValue = resourceValue + 0.1667;
							}
							 else
									if (f.getFieldType() == FieldType.PASTURE){
										if (!playerFields.contains(FieldType.PASTURE) || 
												mr.getHarborTypes(player).contains(HarborType.WOOL_HARBOR))
													resourceValue = resourceValue + 0.3334;
														resourceValue = resourceValue + 0.1667;
									}
									 else
											if (f.getFieldType() == FieldType.FIELDS){
												if (!playerFields.contains(FieldType.FIELDS) || 
														mr.getHarborTypes(player).contains(HarborType.GRAIN_HARBOR))
															resourceValue = resourceValue + 0.3334;
																resourceValue = resourceValue + 0.1667;
											}  else
												if (f.getFieldType() == FieldType.MOUNTAINS){
													if (!playerFields.contains(FieldType.MOUNTAINS) || 
															mr.getHarborTypes(player).contains(HarborType.ORE_HARBOR))
																resourceValue = resourceValue + 0.3334;
																	resourceValue = resourceValue + 0.1667;
												}
							n = f.getNumber();
							if ( n == 2 || n == 12){
								numberValue = numberValue + 0.07143;
							} else 
								if (n == 3 || n == 11)
									numberValue = numberValue +  0.14286;
								else 
									if(n == 4 || n == 10)
										numberValue = numberValue + 0.19048;
									else 
										if (n == 5 || n == 9)
											numberValue = numberValue + 0.2619;
										else 
											if (n == 6 || n == 8)
												numberValue = numberValue + 0.3333;
					}
				}
			
		intersectionValue = (resourceValue*4.0 + numberValue*0.25)  / 4.25;
		return intersectionValue;
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
