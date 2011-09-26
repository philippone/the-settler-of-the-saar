package de.unisaarland.cs.sopra.common.view.ai.copy;

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
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.sopra.common.view.ai.Ai;
import de.unisaarland.cs.sopra.common.view.ai.AttackCatapult;
import de.unisaarland.cs.sopra.common.view.ai.AttackSettlement;
import de.unisaarland.cs.sopra.common.view.ai.BuildCatapult;
import de.unisaarland.cs.sopra.common.view.ai.BuildStreet;
import de.unisaarland.cs.sopra.common.view.ai.BuildTown;
import de.unisaarland.cs.sopra.common.view.ai.BuildVillage;
import de.unisaarland.cs.sopra.common.view.ai.ExpandStrategy;
import de.unisaarland.cs.sopra.common.view.ai.InitELIStrategy;
import de.unisaarland.cs.sopra.common.view.ai.KaisExpandStrategy;
import de.unisaarland.cs.sopra.common.view.ai.KaisTradeOfferStrategy;
import de.unisaarland.cs.sopra.common.view.ai.KaisTryToWinFastStrategy;
import de.unisaarland.cs.sopra.common.view.ai.MoveCatapult;
import de.unisaarland.cs.sopra.common.view.ai.MoveRobber;
import de.unisaarland.cs.sopra.common.view.ai.MoveRobberStrategy;
import de.unisaarland.cs.sopra.common.view.ai.NaiveTradeStrategy;
import de.unisaarland.cs.sopra.common.view.ai.ReturnResources;
import de.unisaarland.cs.sopra.common.view.ai.ReturnResourcesStrategy;
import de.unisaarland.cs.sopra.common.view.ai.Strategy;
import de.unisaarland.cs.sopra.common.view.ai.Stroke;
import de.unisaarland.cs.sopra.common.view.ai.TradeStrategy;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;
import de.unisaarland.cs.st.saarsiedler.comm.results.JoinResult;

public class AutomatischeAISpieleClient implements ModelObserver {
	
	public static int ANZAHL_SPIELE = 15;
	
	private final ModelReader mr;
	private final ControllerAdapter ca;
	private final Set<Strategy> generalStrategies;
	private final Set<Strategy> moveRobberStrategies;
	private final Set<Strategy> returnResourcesStrategies;
	private final Set<Strategy> initStrategies;
	
	public AutomatischeAISpieleClient(ModelReader mr, ControllerAdapter ca){
		this.mr = mr;
		this.ca = ca;
		this.generalStrategies = new HashSet<Strategy>();
		this.generalStrategies.add(new KaisExpandStrategy(mr));
		//this.generalStrategies.add(new KaisChooseVillageAndTownsHarbourStrategy(mr));
		this.generalStrategies.add(new KaisTryToWinFastStrategy(mr));
		//this.generalStrategies.add(new BuildStreetStrategy(mr));
		this.generalStrategies.add(new ExpandStrategy(mr));
		//this.generalStrategies.add(new AttackStrategy(mr));
		//this.generalStrategies.add(new DeffenceStrategy(mr));
		this.moveRobberStrategies = new HashSet<Strategy>();
		this.moveRobberStrategies.add(new MoveRobberStrategy(mr));
		this.returnResourcesStrategies = new HashSet<Strategy>();
		this.returnResourcesStrategies.add(new ReturnResourcesStrategy(mr));
		this.initStrategies = new HashSet<Strategy>();
		//this.initStrategies.add(new InitializeStrategy(mr));
		//this.initStrategies.add(new KaisInitNumberStrategy(mr));
		this.initStrategies.add(new InitELIStrategy(mr));
		//this.initStrategies.add(new KaisInitResourceStrategy(mr));
		mr.addModelObserver(this);
	}
	
	public static void main(String[] args){
		int mypoints = 0;
		int otherpoints = 0;
		int mywins = 0;
		int otherwins = 0;
		
		for (int i = 0; i < ANZAHL_SPIELE; i++) {
			try {
				//prepare aktuelle ai
				Connection toonConnection = Connection.establish("sopra.cs.uni-saarland.de", true);
				toonConnection.changeName("Aktuelle KI");
				MatchInformation toonMatchInfo = null;
				boolean joined = false;
				
				while (!joined) {
					for (MatchInformation mi: toonConnection.listMatches()){
						if (mi.getTitle().equals("private Gruppe 16 Automatischer AI Test")) {
							JoinResult jr = toonConnection.joinMatch(mi.getId(), false);
							if (jr == JoinResult.JOINED){
								toonMatchInfo = mi;
								joined = true;
								break;
							}
						}
					}
				}
				
				WorldRepresentation toonWorldRepresentation = toonConnection.getWorld(toonMatchInfo.getWorldId());
				Model toonModel = new Model(toonWorldRepresentation, toonMatchInfo, toonConnection.getClientId());
				Controller toonController = new Controller(toonConnection, toonModel);
				ControllerAdapter toonAdapter = new ControllerAdapter(toonController, toonModel);
				new Ai(toonModel, toonAdapter);
				toonConnection.changeReadyStatus(true);
				toonController.run();
				
				//ergebnise zwischenspeichern
				int other = 0;
				int my = 0;
				for (long act : toonModel.getPlayerMap().keySet()) {
					if (act != toonConnection.getClientId()) 
						other = toonModel.getPlayerMap().get(act).getVictoryPoints();
					else
						my = toonModel.getPlayerMap().get(act).getVictoryPoints();
				}
				otherpoints += other;
				mypoints += my;
				if (my > other && my >= 22) {
					mywins += 1;
					System.out.println("Aktuelle KI gewinnt mit " + my + " Punkten");
				}
				else if (other > my && other >= 22) {
					otherwins += 1;
					System.out.println("Aktuelle KI gewinnt mit " + other + " Punkten");
				}
						
			} catch (Exception e) { e.printStackTrace(); }
		}
		
		//ergebnis auslesen
		System.out.println();
		System.out.println("---------------------");
		System.out.println("Ergebnis:");
		System.out.println("Aktuelle KI Punkte: " + mypoints + "/" + (22*ANZAHL_SPIELE) + " und " + mywins + "/" + ANZAHL_SPIELE + " Siege");
		System.out.println("Referenz KI Punkte: " + otherpoints + "/" + (22*ANZAHL_SPIELE) + " und " + otherwins + "/" + ANZAHL_SPIELE + " Siege" );
	}
	
//		public void execute(List<Stroke> sortedStroke){
//		if (sortedStroke.size() > 0){
//			//TODO remove the random crap
//			//Collections.shuffle(sortedStroke);
//		Stroke bestStroke = sortedStroke.get(0);
//		boolean execute = true;
//			if (!mr.getMe().checkResourcesSufficient(bestStroke.getPrice())){
//				execute = new TradeBrickHarborStrategy(ca, mr).execute(bestStroke.getPrice());
//				execute = false;
//			}
//			if (execute) {
//			System.out.println(bestStroke);
//			bestStroke.execute(ca);
//			//claimVictoryIfPossible();
//		}
//		}
//		// TODO vll loop?
//		ca.endTurn();
//	}
//	
//	public void executeLoop(List<Stroke> sortedStroke){
//		boolean execute = sortedStroke.size() > 0;
//		Player me = mr.getMe();
//		int i = 0;
//		while (execute && i < sortedStroke.size()){
//			sortStrokeList(sortedStroke, generalStrategies);
//			Stroke bestStroke = sortedStroke.get(i);
//			if (!mr.getMe().checkResourcesSufficient(bestStroke.getPrice())){
//				execute = new StupidTradeOfferStrategy(ca, mr).execute(bestStroke.getPrice());
//			}
//			if (execute) {
//				System.out.println(bestStroke);
//				bestStroke.execute(ca);
//				claimVictoryIfPossible();
//			}
//		}
//		ca.endTurn();
//	}

	public void executeLoop(){
		Player me = mr.getMe();
		List<Stroke> sortedStrokes;
		boolean execute = true;
		int executed = 0;
		while (execute){
			sortedStrokes = getSortedStrokeList(generalStrategies);
			Stroke bestStroke = getTheBestStroke(sortedStrokes);
			if (bestStroke != null){
				bestStroke.execute(ca);
				executed++;
				claimLongestRoadIfPossible();
				if (claimVictoryIfPossible()) 
					return;
			}
			else execute = false;
		}
		ca.endTurn();
	}

	
	private Stroke getTheBestStroke(List<Stroke> sortedStrokes) {
		List<Stroke> theBestStrokes = sortedStrokes.subList(0, 1);
		KaisTradeOfferStrategy trade = new KaisTradeOfferStrategy(ca, mr);
		for (Stroke s : theBestStrokes){
			if (mr.getMe().checkResourcesSufficient(s.getPrice()))
				return s;
			else if (trade.isProbable(s.getPrice())){
				if (trade.execute(s.getPrice())) return s;
			}
		}
		return null;
	}

	private void claimLongestRoadIfPossible(){
		List<Path> longestroad = mr.calculateLongestRoads(mr.getMe()).get(0); //TODO perhaps improvable
		int lengthOfLongestClaimedRoad = mr.getLongestClaimedRoad() == null ? 4 : mr.getLongestClaimedRoad().size();
		if (longestroad.size() > lengthOfLongestClaimedRoad){
			System.out.println(longestroad);
			ca.claimLongestRoad(longestroad);
		}
	}
	
	private boolean claimVictoryIfPossible(){
		if (mr.getMaxVictoryPoints() <= mr.getMe().getVictoryPoints()){
			ca.claimVictory();
			ca.setEndOfGame(true);
			return true;
		}
		return false;
	}
	
	private List<Stroke> getSortedStrokeList(Set<Strategy> strategySet){
		List<Stroke> strokeList = generateAllPossibleStrokes();
		evaluateStrokes(strokeList, strategySet);
		Collections.shuffle(strokeList);
		Collections.sort(strokeList, Collections.<Stroke>reverseOrder());
		return strokeList;
	}
	
	private void sortStrokeList(List<Stroke> strokeList, Set<Strategy> strategySet){
		evaluateStrokes(strokeList, strategySet);
		Collections.shuffle(strokeList);
		Collections.sort(strokeList, Collections.<Stroke>reverseOrder());
	}
	
	private void evaluateStrokes(List<Stroke> strokeList, Set<Strategy> strategySet){
		for (Stroke stroke : strokeList){
			double evaluation = 0;
			double evaluationParticipants = 0;
			for (Strategy s : strategySet){
				if (s.evaluates(stroke)){
					evaluationParticipants++;
					evaluation += s.evaluate(stroke)*s.importance();
				}
			}
			evaluation = evaluation/evaluationParticipants;
			if (!Double.isNaN(evaluation))
				stroke.setEvaluation(evaluation);
		}
	}
	
	private List<Stroke> generateAllReturnResourcesStrokes(){
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
	
	private List<Stroke> generateAllMoveRobberStrokes(){
		List<Stroke> strokeSet = new LinkedList<Stroke>();
		for (Field source : mr.getRobberFields()){
			for (Field destination : mr.canPlaceRobber()){
				strokeSet.add(new MoveRobber(source, destination, null));
				//TODO change null to all possible players
			}
		}
		return strokeSet;
	}
	
	private List<Stroke> generateAllStreetStrokes(){
		List<Stroke> strokeSet = new LinkedList<Stroke>();
		for (Path path : mr.buildableStreetPaths(mr.getMe())){
			strokeSet.add(new BuildStreet(path));
		}
		return strokeSet;
	}
	
	private List<Stroke> generateAllVillageStrokes(){
		List<Stroke> strokeSet = new LinkedList<Stroke>();
		for (Intersection inter : mr.buildableVillageIntersections(mr.getMe())){
			strokeSet.add(new BuildVillage(inter));
		}
		return strokeSet;
	}
	
	private List<Stroke> generateAllPossibleStrokes(){
		List<Stroke> strokeSet = new LinkedList<Stroke>();
		// create build town strokes
		if (mr.getMaxBuilding(BuildingType.Town) > mr.getSettlements(mr.getMe(), BuildingType.Town).size()){
			for (Intersection inter : mr.buildableTownIntersections(mr.getMe())){
				strokeSet.add(new BuildTown(inter));
			}
		}
		// create build village strokes
		if (mr.getMaxBuilding(BuildingType.Village) > mr.getSettlements(mr.getMe(), BuildingType.Village).size()){
			for (Intersection inter : mr.buildableVillageIntersections(mr.getMe())){
				strokeSet.add(new BuildVillage(inter));
			}
		}
		// create steets
				for (Path path : mr.buildableStreetPaths(mr.getMe())){
					strokeSet.add(new BuildStreet(path));
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
			executeLoop();
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
