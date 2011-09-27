package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

public class KaisBuildStreetNegativeStrategy extends Strategy {

	public KaisBuildStreetNegativeStrategy(ModelReader mr) {
		super(mr);
	}

	@Override
	public boolean evaluates(Stroke s) {
		boolean evaluates = false;
		if (s.getType() == StrokeType.BUILD_STREET)
			evaluates = true;
		return evaluates;
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
		return 0;
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
		double evaluation = 1;
		if (maxStreetsOnNeighbourFields(stroke.getDestination()) >= 3) evaluation = 0;
		else if (hasUselessNeighbours(stroke.getDestination())) evaluation = 0;
		else if (hitsPathOfEnemy(stroke.getDestination())) evaluation = 0;
		else if (maxStreetsOnNeighbourFields(stroke.getDestination()) == 2) evaluation = 0.5;
		int neighbourStreets = maxNeighbourPathsOfPath(stroke.getDestination());
		if (neighbourStreets > 0) evaluation = evaluation/neighbourStreets;
		int buildableIntersections = mr.buildableVillageIntersections(mr.getMe()).size();
		if (buildableIntersections > 0) evaluation = evaluation/buildableIntersections;
		if (hasVillageOnNeighbourIntersection(stroke.getDestination())) evaluation = evaluation/2;
		return evaluation;
	}
	
	private boolean hasVillageOnNeighbourIntersection(Path destination){
		boolean hasNeighbourVillage = false;
		for (Intersection inter : mr.getIntersectionsFromPath(destination)){
			if (inter.hasOwner() && inter.getOwner() == mr.getMe()) hasNeighbourVillage = true;
		}
		return hasNeighbourVillage;
	}
	
	private boolean hitsPathOfEnemy(Path destination) {
		boolean hitsPathOfEnemy = false;
		for (Intersection neighbourInters : mr.getIntersectionsFromPath(destination)){
			int numberOfEnemyPaths = 0;
			for (Path neighbourPaths : mr.getPathsFromIntersection(neighbourInters)){
				if (neighbourPaths.hasStreet() && neighbourPaths.getStreetOwner() != mr.getMe())
					numberOfEnemyPaths++;
			}
			if (numberOfEnemyPaths == 2) hitsPathOfEnemy = true;
		}
		return hitsPathOfEnemy;
	}

	private boolean hasUselessNeighbours(Path destination){
		boolean hasUselessNeighbours = false;
		for (Field neighbour : mr.getFieldsFromPath(destination)){
			if (neighbour.getNumber() == -1) hasUselessNeighbours = true;
		}
		return hasUselessNeighbours;
	}
	
	private int maxNeighbourPathsOfPath(Path destination) {
		int numberOfNeighbours = 0;
		for (Path neighbour : mr.getPathsFromPath(destination)){
			if (neighbour.hasStreet() && neighbour.getStreetOwner() == mr.getMe()){
				numberOfNeighbours++;
			}
		}
		return numberOfNeighbours;
	}

	private int maxStreetsOnNeighbourFields(Path path){
		int maxStreets = 0;
		for (Field neighbour : mr.getFieldsFromPath(path)){
			int pathsOnField = 0;
			for (Path neighbourPath : mr.getPathsFromField(neighbour)){
				if (neighbourPath.hasStreet() && neighbourPath.getStreetOwner() == mr.getMe()){
					pathsOnField++;
				}
			}
			if (pathsOnField > maxStreets) maxStreets = pathsOnField;
		}
		return maxStreets;
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
