package de.unisaarland.cs.sopra.common.view.ai;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.unisaarland.cs.sopra.common.ModelObserver;
import de.unisaarland.cs.sopra.common.controller.Controller;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;
import de.unisaarland.cs.st.saarsiedler.comm.results.JoinResult;

public class Ai implements ModelObserver {
	
	private final ModelReader mr;
	private final ControllerAdapter ca;
	private final Set<Strategy> generalStrategies;
	private final Set<Strategy> moveRobberStrategies;
	private final Set<Strategy> returnResourcesStrategies;
	private final Set<Strategy> initStrategies;
	
	public Ai(ModelReader mr, ControllerAdapter ca){
		this.mr = mr;
		this.ca = ca;
		this.generalStrategies = new HashSet<Strategy>();
		this.generalStrategies.add(new ExpandStrategy(mr));
		this.generalStrategies.add(new AttackStrategy(mr));
		this.generalStrategies.add(new DeffenceStrategy(mr));
		//this.generalStrategies.add(new DeffenceStrategy(mr));
		this.moveRobberStrategies = new HashSet<Strategy>();
		this.moveRobberStrategies.add(new MoveRobberStrategy(mr));
		this.returnResourcesStrategies = new HashSet<Strategy>();
		this.returnResourcesStrategies.add(new ReturnResourcesStrategy(mr));
		this.initStrategies = new HashSet<Strategy>();
		this.initStrategies.add(new InitializeStrategy(mr));
		this.initStrategies.add(new InitNumStrategy(mr));
		mr.addModelObserver(this);
	}
	
	public static void main(String[] args){
		try {
			String[] params = args[0].split(":");
			int port = Connection.DEFAULT_PORT;
			InetAddress ia = InetAddress.getByName(params[0]);
			if (params.length == 2) port = Integer.parseInt(params[1]);
			Connection connection = Connection.establish(ia, port, true);
			MatchInformation matchInformation = null;
			if (args.length == 2) {
				matchInformation = connection.getMatchInfo(Long.parseLong(args[1]));
				connection.joinMatch(matchInformation.getId(), false);
			}
			else {
				for (MatchInformation mi: connection.listMatches()){
					JoinResult jr = connection.joinMatch(mi.getId(), false);
					if (jr == JoinResult.JOINED){
						matchInformation = mi;
						break;
					}
				}
			}
			long worldId = matchInformation.getWorldId();
			WorldRepresentation worldRepresentation = connection.getWorld(worldId);
			long myId = connection.getClientId();
			Model model = new Model(worldRepresentation, matchInformation, myId);
			Controller controller = new Controller(connection, model);
			ControllerAdapter adapter = new ControllerAdapter(controller,model);
			connection.changeReadyStatus(true);
			System.out.println(myId);
			Ai ai = new Ai(model, adapter);
			controller.run();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void execute(List<Stroke> sortedStroke){
		if (sortedStroke.size() > 0){
			//TODO remove the random crap
			//Collections.shuffle(sortedStroke);
			Stroke bestStroke = sortedStroke.get(0);
			boolean execute = true;
			if (!mr.getMe().checkResourcesSufficient(bestStroke.getPrice())){
				execute = new StupidTradeOfferStrategy(ca, mr).execute(bestStroke.getPrice());
				execute = false;
			}
			if (execute) {
				System.out.println(bestStroke);
				bestStroke.execute(ca);
				//claimVictoryIfPossible();
			}
		}
		// TODO vll loop?
		ca.endTurn();
	}
	
	public void claimVictoryIfPossible(){
		if (mr.getMaxVictoryPoints() <= mr.getMe().getVictoryPoints()){
			ca.claimVictory();
		}
	}
	
	public List<Stroke> getSortedStrokeList(Set<Strategy> strategySet){
		List<Stroke> strokeList = generateAllPossibleStrokes();
		evaluateStrokes(strokeList, strategySet);
		Collections.sort(strokeList, Collections.<Stroke>reverseOrder());
		return strokeList;
	}
	
	public void sortStrokeList(List<Stroke> strokeList, Set<Strategy> strategySet){
		evaluateStrokes(strokeList, strategySet);
		Collections.sort(strokeList, Collections.<Stroke>reverseOrder());
	}
	
	public void evaluateStrokes(List<Stroke> strokeList, Set<Strategy> strategySet){
		for (Stroke stroke : strokeList){
			double evaluation = 0;
			int evaluationParticipants = 0;
			for (Strategy s : strategySet){
				if (s.evaluates(stroke)){
					evaluationParticipants++;
					evaluation += s.evaluate(stroke)*s.importance();
				}
			}
			stroke.setEvaluation(evaluation/evaluationParticipants);
		}
	}
	
	public List<Stroke> generateAllReturnResourcesStrokes(){
		List<Stroke> strokeSet = new LinkedList<Stroke>();
		// TODO Calculate ALL return resources Strokes!!
		if (mr.getMe().getResources().size() > 7){
			ResourcePackage myrp = mr.getMe().getResources().copy();
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
	
	public List<Stroke> generateAllStreetStrokes(){
		List<Stroke> strokeSet = new LinkedList<Stroke>();
		for (Path path : mr.buildableStreetPaths(mr.getMe())){
			strokeSet.add(new BuildStreet(path));
		}
		return strokeSet;
	}
	
	public List<Stroke> generateAllVillageStrokes(){
		List<Stroke> strokeSet = new LinkedList<Stroke>();
		for (Intersection inter : mr.buildableVillageIntersections(mr.getMe())){
			strokeSet.add(new BuildVillage(inter));
		}
		return strokeSet;
	}
	
	public List<Stroke> generateAllPossibleStrokes(){
		List<Stroke> strokeSet = new LinkedList<Stroke>();
		// create build village strokes
		if (mr.getMaxBuilding(BuildingType.Village) > mr.getSettlements(mr.getMe(), BuildingType.Village).size()){
			for (Intersection inter : mr.buildableVillageIntersections(mr.getMe())){
				strokeSet.add(new BuildVillage(inter));
			}
		}
		// create build town strokes
		if (mr.getMaxBuilding(BuildingType.Town) > mr.getSettlements(mr.getMe(), BuildingType.Town).size()){
			for (Intersection inter : mr.buildableTownIntersections(mr.getMe())){
				strokeSet.add(new BuildTown(inter));
			}
		}
		// create catapult strokes
		if (mr.getMaxCatapult() > mr.getCatapults(mr.getMe()).size()) {
			for (Path path : mr.buildableCatapultPaths(mr.getMe())){
				strokeSet.add(new BuildCatapult(path));
			}
		}
		// move catapult strokes
		for (Path source : mr.getCatapults(mr.getMe())){
			for (Path destination : mr.catapultMovePaths(source)){
				strokeSet.add(new MoveCatapult(source, destination));
			}
		}
		// attack settlement strokes
		for (Path source : mr.getCatapults(mr.getMe())){
			for (Intersection destination : mr.attackableSettlements(BuildingType.Town, source)){
				strokeSet.add(new AttackSettlement(source, destination));
			}
		}
		// attack catapult paths
		for (Path source : mr.getCatapults(mr.getMe())){
			for (Path destination : mr.attackableCatapults(source)){
				strokeSet.add(new AttackCatapult(source, destination));
			}
		}
		// create steets
		for (Path path : mr.buildableStreetPaths(mr.getMe())){
			strokeSet.add(new BuildStreet(path));
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
		if (returnResourcesStrokes.size() > 0)
			returnResourcesStrokes.get(0).execute(ca);
		if (mr.getCurrentPlayer() == mr.getMe()){
			List<Stroke> moveRobberStrokes = generateAllMoveRobberStrokes();
			sortStrokeList(moveRobberStrokes, moveRobberStrategies);
			moveRobberStrokes.get(0).execute(ca);
		}
	}

	@Override
	public void eventTrade(ResourcePackage offer) {
		Stroke bestStroke = getSortedStrokeList(generalStrategies).get(0);
		TradeStrategy tradeStrategy = new NaiveTradeStrategy(mr, ca);
		tradeStrategy.accept(bestStroke.getPrice(), offer);
	}

	@Override
	public void eventNewRound(int number) {
		if (mr.getCurrentPlayer() == mr.getMe()){
			List<Stroke> sortedStrokes = getSortedStrokeList(generalStrategies);
			execute(sortedStrokes);
		}
	}

	@Override
	public void initTurn() {
		List<Stroke> villageStrokes = generateAllVillageStrokes();
		sortStrokeList(villageStrokes, initStrategies);
		villageStrokes.get(0).execute(ca);
		List<Stroke> streetStrokes = generateAllStreetStrokes();
		sortStrokeList(streetStrokes, initStrategies);
		streetStrokes.get(0).execute(ca);
	}


	@Override
	public void eventMatchEnd(long winnerID) {
		if (mr.getPlayerMap().get(winnerID) == mr.getMe())
			System.out.println("You have won the macht! =)");
		else System.out.println("You do not have won the match! =(");
	} 

}