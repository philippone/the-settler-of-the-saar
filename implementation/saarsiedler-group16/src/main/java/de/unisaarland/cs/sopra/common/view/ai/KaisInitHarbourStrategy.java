package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;

public class KaisInitHarbourStrategy extends Strategy {

	public KaisInitHarbourStrategy(ModelReader mr) {
		super(mr);
	}

	@Override
	public boolean evaluates(Stroke s) {
		boolean evaluates = false;
		if (s.getType() == StrokeType.BUILD_VILLAGE){
			boolean hasHarbours = mr.getHarborIntersections().size() > 0;
			boolean hasOwnHarbours = mr.getHarborTypes(mr.getMe()).size() > 0;
			evaluates = hasHarbours && !hasOwnHarbours;
		}
		return evaluates;
	}

	@Override
	public double importance() {
		return 1;
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
		double evaluation = 0;
		if (checkForTwoResourceLandFields(stroke.getDestination()) && isHarbourIntersection(stroke.getDestination())){
			if (numberOfDifferentResources(stroke.getDestination()) == 2){
				evaluation = 1;
			}
			else {
				evaluation = 0.5;
			}
		}
		return evaluation;
	}
	
	private int numberOfDifferentResources(Intersection intersection) {
		Resource lastResource = null;
		int numberOfDifferentResources = 0;
		for (Field f : mr.getFieldsFromIntersection(intersection)){
			if (f.getNumber() != -1){
				if (lastResource == null){
					lastResource = f.getResource();
				}
				else if (lastResource == f.getResource()){
					numberOfDifferentResources = 1;
				}
				else {
					numberOfDifferentResources = 2;
				}
			}
		}
		return numberOfDifferentResources;
	}

	private boolean isHarbourIntersection(Intersection intersection){
		return mr.getHarborIntersections().contains(intersection);
	}
	
	private boolean checkForTwoResourceLandFields(Intersection intersection){
		int numberOfLandFields = 0;
		for (Field f : mr.getFieldsFromIntersection(intersection)){
			if (f.getNumber() != -1) numberOfLandFields++;
		}
		return numberOfLandFields == 2;
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
		// TODO Auto-generated method stub
		return 0;
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
