package de.unisaarland.cs.sopra.common.controller;

import static de.unisaarland.cs.sopra.common.model.Resource.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.unisaarland.cs.sopra.common.model.*;

public class ControllerAdapter {

	private Model model;
	private Controller controller;
	
	public ControllerAdapter(Controller controller, Model model) {
		this.controller = controller;
		this.model = model;
	}
	
	public void attackSettlement(Path catapult, Intersection settlement) {
		Location l = Model.getLocation(catapult);
		l.getX();
		l.getY();
		l.getOrientation();
		Location i = Model.getLocation(settlement);
		controller.attackSettlement(l, i);
		
	}
	
	public void buildCatapult(Path path) {
		Location l = Model.getLocation(path);
		controller.buildCatapult(l);
	}
	
	public void buildSettlement(Intersection intersection, BuildingType buildingType) {
		Location l = Model.getLocation(intersection);
		controller.buildSettlement(l, buildingType);
		
	}
	
	public void buildStreet(Path path) {
		Location l = Model.getLocation(path);
		controller.buildStreet(l);
	}
	
	public void claimLongestRoad(List<Path> road) {
		List<Location> roadList = new LinkedList<Location>();
		for (Path p : road){
			 Location l = Model.getLocation(p);
			 roadList.add(l);
		}
		controller.claimLongestRoad(roadList);
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
		Location l = Model.getLocation(sourcePath);
		Location l2 = Model.getLocation(destinationPath);
		controller.moveCatapult(l, l2);
	}
	
	public void moveRobber(Field sourceField, Field destinationField, Player victimPlayer) {
		Point p = Model.getLocation(sourceField);
		Point p2 = Model.getLocation(destinationField);
		Set<Long> keySet = model.getPlayerMap().keySet();
		for (Long l : keySet) {
			Player player = model.getPlayerMap().get(l);
			if (player.equals(victimPlayer))
				controller.moveRobber(p, p2, l);
		}
	}
	
	public void offerTrade(ResourcePackage resourcePackage) {
		int lumber = resourcePackage.getResource(LUMBER);
		int brick =  resourcePackage.getResource(BRICK);
		int wool =  resourcePackage.getResource(WOOL);
		int grain =  resourcePackage.getResource(GRAIN);
		int ore =   resourcePackage.getResource(ORE);
		controller.offerTrade(lumber, brick, wool, grain, ore);
	}
	
	public void respondTrade(boolean decision) {
		controller.respondTrade(decision);
	}
	
	public void returnResources(ResourcePackage resourcePackage) {
		int lumber = resourcePackage.getResource(LUMBER);
		int brick =  resourcePackage.getResource(BRICK);
		int wool =  resourcePackage.getResource(WOOL);
		int grain =  resourcePackage.getResource(GRAIN);
		int ore =   resourcePackage.getResource(ORE);
		controller.returnResources(lumber, brick, wool, grain, ore);
	}
	
}
