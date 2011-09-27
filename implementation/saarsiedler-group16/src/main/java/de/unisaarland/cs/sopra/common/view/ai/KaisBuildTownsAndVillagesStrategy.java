package de.unisaarland.cs.sopra.common.view.ai;

import java.util.HashSet;
import java.util.Set;

import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;

public class KaisBuildTownsAndVillagesStrategy extends Strategy {

	public KaisBuildTownsAndVillagesStrategy(ModelReader mr) {
		super(mr);
	}

	@Override
	public boolean evaluates(Stroke s) {
		boolean evaluates = false;
		if (s.getType() == StrokeType.BUILD_VILLAGE){
			evaluates = mr.buildableVillageIntersections(mr.getMe()).size() > 0;
		}
		else if (s.getType() == StrokeType.BUILD_TOWN){
			evaluates = mr.buildableTownIntersections(mr.getMe()).size() > 0;
		}
		return evaluates;
	}

	@Override
	public double importance() {
		return 1.5;
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
		if (numberOfDifferentFields(stroke.getDestination()) <= 2) return 0.75;
		if (numberOfUselessFields(stroke.getDestination()) > 0) return 0;
		else return 1;
	}
	
	private int numberOfUselessFields(Intersection destination){
		int uselessFields = 0;
		for (Field neighbour : mr.getFieldsFromIntersection(destination)){
			if (neighbour.getNumber() == -1) uselessFields++;
		}
		return uselessFields;
	}

	@Override
	public double evaluate(BuildTown stroke) {
		if (numberOfDifferentFields(stroke.getDestination()) <= 2) return 0.75;
		else return 1;
	}
	
	private int numberOfDifferentFields(Intersection destination){
		Set<Resource> resset = new HashSet<Resource>();
		for (Field field : mr.getFieldsFromIntersection(destination)){
			if (field.getNumber() != -1) resset.add(field.getResource());
		}
		return resset.size();
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
