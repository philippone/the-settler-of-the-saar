package de.unisaarland.cs.sopra.common.controller;

import static de.unisaarland.cs.sopra.common.model.Resource.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.unisaarland.cs.sopra.common.model.*;
import de.unisaarland.cs.sopra.common.view.Clickable;

public class ControllerAdapter {

	private Model model;
	private Controller controller;
	
	/**
	 * @param controller
	 * @param model
	 */
	public ControllerAdapter(Controller controller, Model model) {
		this.controller = controller;
		this.model = model;
	}
	
	
	
	/**
	 * @param catapult
	 * @param settlement
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void attackSettlement(Path catapult, Intersection settlement) {
		Location l = Model.getLocation(catapult);
		Location i = Model.getLocation(settlement);
		try {
			controller.attackSettlement(l, i);
		} catch (Exception e) { e.printStackTrace(); }		
	}
	
	/**
	 * @param path
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void buildCatapult(Path path) {
		Location l = Model.getLocation(path);
		try {
			controller.buildCatapult(l);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * @param intersection
	 * @param buildingType
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void buildSettlement(Intersection intersection, BuildingType buildingType) {
		Location l = Model.getLocation(intersection);
		try {
			controller.buildSettlement(l, buildingType);
		} catch (Exception e) { e.printStackTrace(); }
		
	}
	
	/**
	 * @param path
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void buildStreet(Path path) {
		Location l = Model.getLocation(path);
		try {
			controller.buildStreet(l);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * @param road
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void claimLongestRoad(List<Path> road) {
		List<Location> roadList = new LinkedList<Location>();
		for (Path p : road){
			 Location l = Model.getLocation(p);
			 roadList.add(l);
		}
		try {
			controller.claimLongestRoad(roadList);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void claimVictory() {
		try {
			controller.claimVictory();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void endTurn(){
		try {
			controller.endTurn();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void leaveMatch() {
		try {
			controller.leaveMatch();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * @param sourcePath
	 * @param destinationPath
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void moveCatapult(Path sourcePath, Path destinationPath) {
		Location l = Model.getLocation(sourcePath);
		Location l2 = Model.getLocation(destinationPath);
		try {
			controller.moveCatapult(l, l2);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * @param sourceField
	 * @param destinationField
	 * @param victimPlayer
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void moveRobber(Field sourceField, Field destinationField, Player victimPlayer) {
		Point p = Model.getLocation(sourceField);
		Point p2 = Model.getLocation(destinationField);
		Set<Long> keySet = model.getPlayerMap().keySet();
		if (victimPlayer != null){
			for (Long l : keySet) {
				Player player = model.getPlayerMap().get(l);
				if (player.equals(victimPlayer)){
					try {
						controller.moveRobber(p, p2, l);
					} catch (Exception e) { e.printStackTrace(); }
					break;
				}
			}
		} else
			try {
				controller.moveRobber(p, p2, -1);
			} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * @param resourcePackage
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void offerTrade(ResourcePackage resourcePackage) {
		// TODO: Ueberpruefe ob Handel so erlaubt (Verhaeltnisse)
		int lumber = resourcePackage.getResource(LUMBER);
		int brick =  resourcePackage.getResource(BRICK);
		int wool =  resourcePackage.getResource(WOOL);
		int grain =  resourcePackage.getResource(GRAIN);
		int ore =   resourcePackage.getResource(ORE);
		try {
			controller.offerTrade(lumber, brick, wool, grain, ore);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * @param decision
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void respondTrade(boolean decision) {
		try {
			controller.respondTrade(decision);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * @param resourcePackage
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void returnResources(ResourcePackage resourcePackage) {
		int lumber = resourcePackage.getResource(LUMBER);
		int brick =  resourcePackage.getResource(BRICK);
		int wool =  resourcePackage.getResource(WOOL);
		int grain =  resourcePackage.getResource(GRAIN);
		int ore =   resourcePackage.getResource(ORE);
		try {
			controller.returnResources(lumber, brick, wool, grain, ore);
		} catch (Exception e) { e.printStackTrace(); }
	}



	public void setEndOfGame(boolean b) {
		controller.setEndOfGame(b);
	}
	
	public void addGuiEvent(Clickable c) {
		controller.addGuiEvent(c);
	}
	
}
