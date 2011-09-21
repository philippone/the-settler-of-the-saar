package de.unisaarland.cs.sopra.common.view.ai;

import java.util.Set;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

public class DeffenceStrategy extends Strategy {

	public DeffenceStrategy(ModelReader mr) {
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
			return false;
		case BUILD_TOWN:
			return false;
		case BUILD_CATAPULT:
			return true;
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
		//TODO maybe set townValue to 0.5
		double townValue = 0.0;
		Set<Intersection> villages = mr.getSettlements(mr.getMe(), BuildingType.Village);
		for (Intersection i : villages){
			Set<Path> neighbours = mr.getPathsFromIntersection(i);
			for (Path p : neighbours){
				if (p.hasCatapult() && p.getCatapultOwner() != mr.getMe())
					townValue = 1.0;
			}
		}
		return townValue;
	}

	@Override
	public double evaluate(BuildCatapult stroke) {
		double buildCatapultValue = 0.0;
		Set<Intersection> playerTowns = mr.getSettlements(mr.getMe(),BuildingType.Town);
		if (playerTowns.size() > 0) {
			for (Intersection i : playerTowns) {
				Set<Path> neighbourPaths = mr.getPathsFromIntersection(i);
				for (Path p : neighbourPaths) {
					if (p.hasCatapult() && p.getCatapultOwner() != mr.getMe())
						buildCatapultValue = buildCatapultValue + 1.0;
				}

			}
		}
		Set<Intersection> playerVillages = mr.getSettlements(mr.getMe(), BuildingType.Village);
		for (Intersection i : playerVillages){
			Set<Path> neighboursPathsv = mr.getPathsFromIntersection(i);
			for (Path p : neighboursPathsv) {
				if (p.hasCatapult() && p.getCatapultOwner() != mr.getMe())
					buildCatapultValue = buildCatapultValue + 0.9;
			}
		}
		return buildCatapultValue;
	}

	@Override
	public double evaluate(BuildStreet stroke) {
		
		return 0;
	}

	@Override
	public double evaluate(MoveCatapult stroke) {
		double moveCatapultValue = 0.0;
		Set<Intersection> playerVillages = mr.getSettlements(mr.getMe(), BuildingType.Village);
		Set<Intersection> playerSettlements = mr.getSettlements(mr.getMe(), BuildingType.Town);
		playerSettlements.addAll(playerVillages);
		for (Intersection i : playerSettlements){
			Set<Path> neighboursPathsS = mr.getPathsFromIntersection(i);
			for (Path p : neighboursPathsS) {
				if (p.hasCatapult() && p.getCatapultOwner() != mr.getMe()){
					Set<Path> neighboursPathp = mr.getPathsFromPath(p);
					for (Path path : neighboursPathp){
						if (path.hasCatapult() && path.getCatapultOwner() == mr.getMe())
							moveCatapultValue = 1.0;
					}
				}
					
			}
		}
		return moveCatapultValue;
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
