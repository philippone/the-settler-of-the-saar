package de.unisaarland.cs.sopra.common.controller;


import java.util.LinkedList;
import java.util.List;

import de.unisaarland.cs.sopra.common.model.*;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.Edge;
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
	
	public void handleEvent() {
		throw new UnsupportedOperationException();
	}
	
	public void attackSettlement(Location catapult, Location settlement) {
		int x = catapult.getX();
		int y = catapult.getY();
		int o = catapult.getOrientation();
		Edge e = new Edge(x, y, o);
		int x1 = settlement.getX();
		int y1 = settlement.getY();
		int o1 = settlement.getOrientation(); 
		Intersection i = new Intersection(x1, y1, o1);
		
		AttackResult r = connection.attack(e, i);
		modelWriter.attackSettlement(catapult, settlement, r);
	
		
	}
	
	public void buildCatapult(Location path) {
		boolean fOC = connection.buildCatapult(new Edge(path.getX(), path.getY(), path.getOrientation()));
		modelWriter.buildCatapult(path, fOC);
	}

	public void buildSettlement(Location intersection, BuildingType buildingType) {
		Intersection i = new Intersection(intersection.getX(),
				intersection.getY(), intersection.getOrientation());

		if (buildingType.equals(BuildingType.Village)) {
			connection.buildSettlement(i, true);
			modelWriter.buildSettlement(intersection, buildingType);
		} else
			connection.buildSettlement(i, false);
			modelWriter.buildSettlement(intersection, buildingType);
	}

	public void buildStreet(Location path) {
		Edge e = new Edge(path.getX(), path.getY(), path.getOrientation());
		modelWriter.buildStreet(path);
		connection.buildRoad(e);
	}
	
	public void claimLongestRoad(List<Location> road) {
		List<Edge> roadList = new LinkedList<Edge>();
		for (Location l : road) {
			Edge e = new Edge(l.getX(), l.getY(), l.getOrientation());
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
		Edge e = new Edge(sourcePath.getX(), sourcePath.getY(), sourcePath.getOrientation());
		Edge e1 = new Edge(destinationPath.getX(), destinationPath.getY(), destinationPath.getOrientation());
		boolean fOC = connection.moveCatapult(e, e1);
		modelWriter.catapultMoved(sourcePath, destinationPath, fOC);
	}
	
	public void moveRobber(Point sourceField, Point destinationField, long victimPlayer) {
		int	x = sourceField.getX(); 
		int y = sourceField.getY();
		int x1 = destinationField.getX();
		int y1 = destinationField.getY();
	
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
		throw new UnsupportedOperationException();
	}
	
}
