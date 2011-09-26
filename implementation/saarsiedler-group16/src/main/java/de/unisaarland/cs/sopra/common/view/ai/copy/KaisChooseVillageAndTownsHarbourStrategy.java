package de.unisaarland.cs.sopra.common.view.ai.copy;

import java.util.HashSet;
import java.util.Set;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;

public class KaisChooseVillageAndTownsHarbourStrategy extends Strategy {
	
	private final int maxNumberOfHarbours;

	public KaisChooseVillageAndTownsHarbourStrategy(ModelReader mr, int maxNumberOfHarbours) {
		super(mr);
		this.maxNumberOfHarbours = maxNumberOfHarbours;
	}
	
	public KaisChooseVillageAndTownsHarbourStrategy(ModelReader mr) {
		super(mr);
		this.maxNumberOfHarbours = mr.getMaxBuilding(BuildingType.Village)/3;
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
		Intersection destination = stroke.getDestination();
		Set<Intersection> ownIntersections = mr.getSettlements(mr.getMe(), BuildingType.Village);
		ownIntersections.retainAll(mr.getHarborIntersections());
		int numberOfUselessFields = 0;
		if (ownIntersections.size() <=  maxNumberOfHarbours && mr.getHarborIntersections().contains(destination) && mr.getHarborType(destination) != HarborType.GENERAL_HARBOR){
			for (Field neighbour : mr.getFieldsFromIntersection(destination)){
				if (neighbour.getResource() == null) numberOfUselessFields++;
			}
			if (numberOfUselessFields == 1) return 1;
		}
		return 0;
	}

	@Override
	public double evaluate(BuildTown stroke) {
		Intersection destination = stroke.getDestination();
		Set<Resource> importantResources = new HashSet<Resource>();
		for (HarborType harbourType : mr.getHarborTypes(mr.getMe())){
			switch(harbourType){
			case BRICK_HARBOR:
				importantResources.add(Resource.BRICK);
				break;
			case GRAIN_HARBOR:
				importantResources.add(Resource.GRAIN);
				break;
			case LUMBER_HARBOR:
				importantResources.add(Resource.LUMBER);
				break;
			case ORE_HARBOR:
				importantResources.add(Resource.ORE);
				break;
			case WOOL_HARBOR:
				importantResources.add(Resource.WOOL);
				break;
			}
		}
		int numberOfUsefullFields = 0;
		for (Field neighbour : mr.getFieldsFromIntersection(destination)){
			if (neighbour.getResource() != null && importantResources.contains(neighbour.getResource())) numberOfUsefullFields++;
		}
		if (numberOfUsefullFields > 1){
			double evaluation = 0;
			for (Field neighbour : mr.getFieldsFromIntersection(destination)){
				int number = neighbour.getNumber();
				if (number >= 2 && number <= 12){
					double singleEvaluation = 0;
					if ( number == 2 || number == 12 || number == 3 || number == 11)
						singleEvaluation = 0;
					else if(number == 4 || number == 10)
						singleEvaluation = 0.3;
					else if (number == 5 || number == 9)
						singleEvaluation = 0.6;
					else if (number == 6 || number == 8)
						singleEvaluation = 1;
					evaluation += singleEvaluation;
				}
			}
			return evaluation/3;
		}
		return 0;
	}

	@Override
	public double evaluate(BuildCatapult stroke) {
		return 0;
	}

	@Override
	public double evaluate(BuildStreet stroke) {
		return 0;
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
