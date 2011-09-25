package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

public class KaisInitNumberStrategy extends Strategy {

	public KaisInitNumberStrategy(ModelReader mr) {
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
		double evaluation = 0;
		Intersection destination = stroke.getDestination();
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
		double evaluation = 0;
		int numberOfNeighbours = 0;
		Path destination = stroke.getDestination();
		for (Field neighbour : mr.getFieldsFromPath(destination)){
			int number = neighbour.getNumber();
			if (number >= 2 && number <= 12){
				numberOfNeighbours++;
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
		return evaluation/numberOfNeighbours;
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
		return 0.75;
	}

}
