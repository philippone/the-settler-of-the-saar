package de.unisaarland.cs.sopra.common.controller;

import java.util.List;

import de.unisaarland.cs.sopra.common.model.*;

public class ControllerAdapter {

	private Model model;
	private Controller controller;
	
	ControllerAdapter(Controller controller, Model model) {
		this.controller = controller;
		this.model = model;
	}
	
	public void attackSettlement(Path catapult, Intersection settlement) {
		throw new UnsupportedOperationException();
	}
	
	public void buildCatapult(Path path) {
		throw new UnsupportedOperationException();
	}
	
	public void buildSettlement(Intersection intersection, BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}
	
	public void buildStreet(Path path) {
		throw new UnsupportedOperationException();
	}
	
	public void claimLongestRoad(List<Path> road) {
		throw new UnsupportedOperationException();
	}
	
	public void claimVictory() {
		controller.claimVictory();
	}
	
	public void endTurn() {
		controller.endTurn();
	}
	
	public void leaveMatch() {
		controller.leaveMatch();
	}
	
	public void moveCatapult(Path sourcePath, Path destinationPath) {
		throw new UnsupportedOperationException();
	}
	
	public void moveRobber(Field sourceField, Field destinationField, Player victimPlayer) {
		throw new UnsupportedOperationException();
	}
	
	public void offerTrade(ResourcePackage resourcePackage) {
		throw new UnsupportedOperationException();
	}
	
	public void respondTrade(boolean decision) {
		controller.respondTrade(decision);
	}
	
	public void returnResources(ResourcePackage resourcePackage) {
		throw new UnsupportedOperationException();
	}
	
}
