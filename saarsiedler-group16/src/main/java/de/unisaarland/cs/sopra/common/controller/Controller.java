package de.unisaarland.cs.sopra.common.controller;


import java.util.LinkedList;
import java.util.List;

import de.unisaarland.cs.sopra.common.model.*;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.Edge;
import de.unisaarland.cs.st.saarsiedler.comm.GameEvent;
import de.unisaarland.cs.st.saarsiedler.comm.GameEvent.Attack;
import de.unisaarland.cs.st.saarsiedler.comm.GameEvent.*;
import de.unisaarland.cs.st.saarsiedler.comm.Intersection;
import de.unisaarland.cs.st.saarsiedler.comm.results.AttackResult;
import static de.unisaarland.cs.sopra.common.model.Resource.*;



public class Controller {

	private Connection connection;
	private ModelWriter modelWriter;;
	private Resource r;
	
	public Controller(Connection connection, ModelWriter modelWriter) {
		this.connection = connection;
		this.modelWriter = modelWriter;
	}
	
	
	public void handleEvent(GameEvent.Attack attack) {
			
	Edge e =((GameEvent.Attack)attack).getSourceLocation();
	Intersection i=((GameEvent.Attack)attack).getTargetIntersection();
	Location catapult = new Location(e.getCol(), e.getRow(), e.getDirection());
	Location settlement = new Location(i.getCol(), i.getRow(), i.getDirection());
	attackSettlement(catapult, settlement);
	
	}
	
	
	public void handleEvent(GameEvent.MatchStart matchStart) {
		long[] players = ((GameEvent.MatchStart) matchStart).getPlayerIds();
		byte[] number = ((GameEvent.MatchStart) matchStart).getNumbers();
		modelWriter.matchStart(players, number);

	}
	
	public void handleEvent(GameEvent.BuiltCatapult builtCatapult){
		Edge e = ((GameEvent.BuiltCatapult)builtCatapult).getLocation();
		Location location = new Location(e.getCol(), e.getRow(), e.getDirection());
		boolean fightOutcome = ((GameEvent.BuiltCatapult)builtCatapult).fightOutcome();
		modelWriter.buildCatapult(location, fightOutcome);
	}
	
	public void handleEvent(GameEvent.BuiltRoad builtRoad){
		Edge e = ((GameEvent.BuiltRoad)builtRoad).getLocation();
		Location location = new Location(e.getCol(), e.getRow(), e.getDirection());
		modelWriter.buildStreet(location);
	}
	
	public void handleEvent(GameEvent.BuiltSettlement builtSettlement){
		Intersection i = ((GameEvent.BuiltSettlement)builtSettlement).getLocation();
		Location location = new Location(i.getCol(), i.getRow(), i.getDirection());
		boolean isUpgradeToTown = ((GameEvent.BuiltSettlement)builtSettlement).isUpgradeToTown();
		if (isUpgradeToTown){
			modelWriter.buildSettlement(location, BuildingType.Village);
		} else
			modelWriter.buildSettlement(location, BuildingType.Town);
	}
	
	public void handleEvent(GameEvent.MovedCatapult movedCatapult){
		Edge e = ((GameEvent.MovedCatapult)movedCatapult).getSourceLocation();
		Location source = new Location(e.getCol(), e.getRow(), e.getDirection());
		Edge e1 = ((GameEvent.MovedCatapult)movedCatapult).getDestinationLocation();
		Location destination = new Location(e1.getCol(), e1.getRow(), e1.getDirection());
		boolean fightOutcome = ((GameEvent.MovedCatapult)movedCatapult).fightOutcome(); 
		modelWriter.catapultMoved(source, destination, fightOutcome);
		
	}
	
	public void handleEvent(GameEvent.MatchEnd matchEnd){
		long id = ((GameEvent.MatchEnd)matchEnd).getWinnerClientId();
		//modelObserver.MatchEnd
	}
	
	public void handleEvent(GameEvent.NewRound newRound){
		byte num = ((GameEvent.NewRound)newRound).getSpotSum();
		modelWriter.newRound(num);
	}
	
	public void handleEvent(GameEvent.PlayerLeft playerLeft){
		long id = ((GameEvent.PlayerLeft)playerLeft).getClientId();
		modelWriter.playerLeft(id);
	}
	
	public void handleEvent(GameEvent.RobberMoved robberMoved){
		int y = ((GameEvent.RobberMoved)robberMoved).getSrcRow(); 
		int x = ((GameEvent.RobberMoved)robberMoved).getSrcCol();
		Point sourceField = new Point(x,y);
		int y1 = robberMoved.getDstRow();
		int x1 = robberMoved.getDstCol();
		Point destinationField = new Point(x1,y1);
		long victimPlayer = ((GameEvent.RobberMoved)robberMoved).getVictimClientId();
		de.unisaarland.cs.st.saarsiedler.comm.Resource r1 = ((GameEvent.RobberMoved)
				robberMoved).getStolenResource();
		Resource stolenResource = Resource.convert(r1);
		modelWriter.robberMoved(sourceField, destinationField, victimPlayer, stolenResource);
	}

	public void handleEvent(GameEvent.Trade trade){
		long id = ((GameEvent.Trade)trade).getAcceptedPlayerId();
		modelWriter.respondTrade(id);
		}
	
	public void handleEvent(GameEvent.TradeOffer tradeOffer){
		int lumber = tradeOffer.getLumber();
		int brick = tradeOffer.getBrick();
		int wool = tradeOffer.getWool();
		int grain = tradeOffer.getGrain();
		int ore = tradeOffer.getOre();
		modelWriter.tradeOffer(lumber, brick, wool, grain, ore);
	}
	
	
	public void handleEvent(GameEvent.LongestRoad longestRoad){
		Edge[] edge = longestRoad.getEdges();
		List<Location> road = new LinkedList<Location>();
		for (Edge e : edge){
			Location l = new Location(e.getCol(), e.getRow(), e.getDirection());
			road.add(l);
		}
		modelWriter.longestRoadClaimed(road);
		
	}
	
	public void attackSettlement(Location catapult, Location settlement) {
		int y = catapult.getX();
		int x = catapult.getY();
		int o = catapult.getOrientation();
		Edge e = new Edge(x, y, o);
		int y1 = settlement.getX();
		int x1 = settlement.getY();
		int o1 = settlement.getOrientation(); 
		Intersection i = new Intersection(x1, y1, o1);
		
		AttackResult r = connection.attack(e, i);
		modelWriter.attackSettlement(catapult, settlement, r);
	
		
	}
	
	public void buildCatapult(Location path) {
		boolean fOC = connection.buildCatapult(new Edge(path.getY(), path.getX(), path.getOrientation()));
		modelWriter.buildCatapult(path, fOC);
	}

	public void buildSettlement(Location intersection, BuildingType buildingType) {
		Intersection i = new Intersection(intersection.getY(),
				intersection.getX(), intersection.getOrientation());

		if (buildingType.equals(BuildingType.Village)) {
			connection.buildSettlement(i, true);
			modelWriter.buildSettlement(intersection, buildingType);
		} else
			connection.buildSettlement(i, false);
			modelWriter.buildSettlement(intersection, buildingType);
	}

	public void buildStreet(Location path) {
		Edge e = new Edge(path.getY(), path.getX(), path.getOrientation());
		modelWriter.buildStreet(path);
		connection.buildRoad(e);
	}
	
	public void claimLongestRoad(List<Location> road) {
		List<Edge> roadList = new LinkedList<Edge>();
		for (Location l : road) {
			Edge e = new Edge(l.getY(), l.getX(), l.getOrientation());
			roadList.add(e);
		}
		modelWriter.longestRoadClaimed(road);
		connection.claimLongestRoad(roadList);
	}
	
	public void claimVictory() {
		connection.claimVictory();
	}
	
	public void endTurn() {
		connection.endTurn();
	}
	
	public void leaveMatch() {
		connection.leaveMatch();
	}
	
	public void moveCatapult(Location sourcePath, Location destinationPath) {
		Edge e = new Edge(sourcePath.getY(), sourcePath.getX(), sourcePath.getOrientation());
		Edge e1 = new Edge(destinationPath.getY(), destinationPath.getX(), destinationPath.getOrientation());
		boolean fOC = connection.moveCatapult(e, e1);
		modelWriter.catapultMoved(sourcePath, destinationPath, fOC);
	}
	
	public void moveRobber(Point sourceField, Point destinationField, long victimPlayer) {
		int	y = sourceField.getX(); 
		int x = sourceField.getY();
		int y1 = destinationField.getX();
		int x1 = destinationField.getY();
	
		de.unisaarland.cs.st.saarsiedler.comm.Resource r1 =connection.moveRobber(x, y, x1, y1, victimPlayer);
		r = Resource.convert(r1);
		modelWriter.robberMoved(sourceField, destinationField, victimPlayer, r);
	}
	
	public void offerTrade(int lumber, int brick, int wool, int grain, int ore) {
		connection.offerTrade(lumber, brick, wool, grain, ore);
		
	}
	
	public void respondTrade(boolean decision) {
		long id = connection.respondTrade(decision);
		modelWriter.respondTrade(id);
	}
	
	public void returnResources(int lumber, int brick, int wool, int grain, int ore) {
		connection.returnResources(lumber, brick, wool, grain, ore);
		modelWriter.returnResources(lumber, brick, wool, grain, ore);
	}
	
	public void mainLoop() {
	
		GameEvent e = connection.getNextEvent(0);
		handleEvent(e);
		
	}
	
}
