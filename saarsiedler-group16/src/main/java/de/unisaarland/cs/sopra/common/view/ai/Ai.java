package de.unisaarland.cs.sopra.common.view.ai;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.unisaarland.cs.sopra.common.ModelObserver;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.sopra.common.view.Strategy;

public class Ai implements ModelObserver {
	
	private final ModelReader mr;
	private final ControllerAdapter ca;
	private final Player me;
	private final Set<Strategy> strategies;
	
	
	public Ai(ModelReader mr, ControllerAdapter ca){
		this.mr = mr;
		this.ca = ca;
		this.me = mr.getMe();
		this.strategies = new HashSet<Strategy>();
	}
	
	public void 
	
	public List<Stroke> generateAllPossibleStrokes(){
		List<Stroke> strokeSet = new LinkedList<Stroke>();
		// create build village strokes
		for (Intersection inter : mr.buildableVillageIntersections(me)){
			strokeSet.add(new BuildBuilding(inter, BuildingType.Village));
		}
		// create build town strokes
		for (Intersection inter : mr.buildableTownIntersections(me)){
			strokeSet.add(new BuildBuilding(inter, BuildingType.Town));
		}
		// create catapult strokes
		for (Path path : mr.buildableCatapultPaths(me)){
			strokeSet.add(new BuildCatapult(path));
		}
		// move catapult strokes
		for (Path source : mr.getCatapults(me)){
			for (Path destination : mr.catapultMovePaths(source)){
				strokeSet.add(new MoveCatapult(source, destination));
			}
		}
		// attack settlement strokes
		for (Path source : mr.getCatapults(me)){
			for (Intersection destination : mr.attackableSettlements(BuildingType.Town, source)){
				strokeSet.add(new AttackSettlement(source, destination));
			}
		}
		// attack catapult paths
		for (Path source : mr.getCatapults(me)){
			for (Path destination : mr.attackableCatapults(source)){
				strokeSet.add(new AttackCatapult(source, destination));
			}
		}
		return strokeSet;
	}
	
	private void evaluateStrokes(List<Stroke> possibleStrokes) {
		
	}

	@Override
	public void updatePath(Path path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateIntersection(Intersection intersection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateField(Field field) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateResources() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateVictoryPoints() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCatapultCount() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSettlementCount(BuildingType buildingType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTradePossibilities() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventPlayerLeft(long playerID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventRobber() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventTrade(ResourcePackage resourcePackage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventNewRound(int number) {
		List<Stroke> possibleStrokes = generateAllPossibleStrokes();
		evaluateStrokes(possibleStrokes);
	}

	@Override
	public void initTurn() {
		// TODO Auto-generated method stub
		
	}

}