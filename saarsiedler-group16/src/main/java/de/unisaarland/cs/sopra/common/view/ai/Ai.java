package de.unisaarland.cs.sopra.common.view.ai;

import java.util.Collections;
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
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class Ai implements ModelObserver {
	
	private final ModelReader mr;
	private final ControllerAdapter ca;
	private final Player me;
	private final Set<Strategy> generalStrategies;
	private final Set<Strategy> moveRobberStrategies;
	private final Set<Strategy> returnResourcesStrategies;
	private final Set<Strategy> initStrategies;
	
	public Ai(ModelReader mr, ControllerAdapter ca){
		this.mr = mr;
		this.ca = ca;
		this.me = mr.getMe();
		this.generalStrategies = new HashSet<Strategy>();
		this.moveRobberStrategies = new HashSet<Strategy>();
		this.moveRobberStrategies.add(new MoveRobberStrategy(mr));
		this.returnResourcesStrategies = new HashSet<Strategy>();
		this.returnResourcesStrategies.add(new ReturnResourcesStrategy(mr));
		this.initStrategies = new HashSet<Strategy>();
		this.initStrategies.add(new InitializeStrategy(mr));
	}
	
	
	public void execute(List<Stroke> sortedStroke){
		if (sortedStroke.size() > 0){
			Stroke bestStroke = sortedStroke.get(0);
			if (me.checkResourcesSufficient(bestStroke.getPrice())){
				bestStroke.execute(ca);
			}
			else {
				// TODO insert Trade handling here!
			}
		}
		else ca.endTurn();
	}
	
	public List<Stroke> getSortedStrokeList(Set<Strategy> strategySet){
		List<Stroke> strokeList = generateAllPossibleStrokes();
		evaluateStrokes(strokeList, strategySet);
		Collections.sort(strokeList);
		return strokeList;
	}
	
	public void sortStrokeList(List<Stroke> strokeList, Set<Strategy> strategySet){
		evaluateStrokes(strokeList, strategySet);
		Collections.sort(strokeList);
	}
	
	public void evaluateStrokes(List<Stroke> strokeList, Set<Strategy> strategySet){
		for (Stroke stroke : strokeList){
			double evaluation = 0;
			int evaluationParticipants = 0;
			for (Strategy s : strategySet){
				if (s.evaluates(stroke)){
					evaluationParticipants++;
					evaluation += s.evaluate(stroke);
				}
			}
			stroke.setEvaluation(evaluation/evaluationParticipants);
		}
	}
	
	public List<Stroke> generateAllReturnResourcesStrokes(){
		List<Stroke> strokeSet = new LinkedList<Stroke>();
		// TODO Calculate ALL return resources Strokes!!
		if (me.getResources().size() > 7){
			ResourcePackage myrp = me.getResources().copy();
			int give = myrp.size()/2;
			Resource max = Resource.LUMBER;
			ResourcePackage tmp = new ResourcePackage();
			while (give > 0){
				// find the resource that you have most
				for (Resource r : Resource.values())
					max = myrp.getResource(r)>myrp.getResource(max)?r:max;
				myrp.modifyResource(max, -1);	
				tmp.modifyResource(max, 1);
				give--;
			}
			strokeSet.add(new ReturnResources(tmp));
		}
		return strokeSet;
	}
	
	public List<Stroke> generateAllMoveRobberStrokes(){
		List<Stroke> strokeSet = new LinkedList<Stroke>();
		for (Field source : mr.getRobberFields()){
			for (Field destination : mr.canPlaceRobber()){
				strokeSet.add(new MoveRobber(source, destination, null));
				//TODO change null to all possible players
			}
		}
		return strokeSet;
	}
	
	public List<Stroke> generateAllPossibleStrokes(){
		List<Stroke> strokeSet = new LinkedList<Stroke>();
		// create build village strokes
		for (Intersection inter : mr.buildableVillageIntersections(me)){
			strokeSet.add(new BuildVillage(inter));
		}
		// create build town strokes
		for (Intersection inter : mr.buildableTownIntersections(me)){
			strokeSet.add(new BuildTown(inter));
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

	@Override
	public void updatePath(Path path) {
	}

	@Override
	public void updateIntersection(Intersection intersection) {
	}

	@Override
	public void updateField(Field field) {
	}

	@Override
	public void updateResources() {
	}

	@Override
	public void updateVictoryPoints() {
	}

	@Override
	public void updateCatapultCount() {
	}

	@Override
	public void updateSettlementCount(BuildingType buildingType) {
	}

	@Override
	public void updateTradePossibilities() {
	}

	@Override
	public void eventPlayerLeft(long playerID) {
	}

	@Override
	public void eventRobber() {
		List<Stroke> returnResourcesStrokes = generateAllReturnResourcesStrokes();
		sortStrokeList(returnResourcesStrokes, returnResourcesStrategies);
		execute(returnResourcesStrokes);
		List<Stroke> moveRobberStrokes = generateAllMoveRobberStrokes();
		sortStrokeList(moveRobberStrokes, moveRobberStrategies);
		execute(moveRobberStrokes);
	}

	@Override
	public void eventTrade(ResourcePackage resourcePackage) {
		TradeStrategy tradeStrategy = new StupidTradeStrategy();
		ca.respondTrade(tradeStrategy.accepts());
	}

	@Override
	public void eventNewRound(int number) {
		if (mr.getCurrentPlayer() == me){
			List<Stroke> sortedStrokes = getSortedStrokeList(generalStrategies);
			execute(sortedStrokes);
		}
	}

	@Override
	public void initTurn() {
		execute(getSortedStrokeList(initStrategies));
	}

}
