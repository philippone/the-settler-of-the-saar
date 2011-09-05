package de.unisaarland.cs.sopra.common.controller;

import java.util.List;

import de.unisaarland.cs.sopra.common.model.*;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;

public class Controller {

	private Connection connection;
	private ModelWriter modelWriter;
	
	public Controller(Connection connection, ModelWriter modelWriter) {
		this.connection = connection;
		this.modelWriter = modelWriter;
	}
	
	public void handleEvent() {
		throw new UnsupportedOperationException();
	}
	
	public void attackSettlement(Location catapult, Location settlement) {
		throw new UnsupportedOperationException();
	}
	
	public void buildCatapult(Location path) {
		throw new UnsupportedOperationException();
	}
	
	public void buildSettlement(Location intersection, BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}
	
	public void buildStreet(Location path) {
		throw new UnsupportedOperationException();
	}
	
	public void claimLongestRoad(List<Location> road) {
		throw new UnsupportedOperationException();
	}
	
	public void claimVictory() {
		throw new UnsupportedOperationException();
	}
	
	public void endTurn() {
		throw new UnsupportedOperationException();
	}
	
	public void leaveMatch() {
		throw new UnsupportedOperationException();
	}
	
	public void moveCatapult(Location sourcePath, Location destinationPath) {
		throw new UnsupportedOperationException();
	}
	
	public void moveRobber(Point sourceField, Point destinationField, long victimPlayer) {
		throw new UnsupportedOperationException();
	}
	
	public void offerTrade(int lumber, int brick, int wool, int grain, int ore) {
		throw new UnsupportedOperationException();
	}
	
	public void respondTrade(boolean decision) {
		throw new UnsupportedOperationException();
	}
	
	public void returnResources(int lumber, int brick, int wool, int grain, int ore) {
		throw new UnsupportedOperationException();
	}
	
	public void mainLoop() {
		throw new UnsupportedOperationException();
	}
	
}
