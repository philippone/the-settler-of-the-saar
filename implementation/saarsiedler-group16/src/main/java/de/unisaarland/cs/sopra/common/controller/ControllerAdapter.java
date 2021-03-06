package de.unisaarland.cs.sopra.common.controller;

import static de.unisaarland.cs.sopra.common.model.Resource.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.unisaarland.cs.sopra.common.model.*;
import de.unisaarland.cs.sopra.common.view.Clickable;
import de.unisaarland.cs.st.saarsiedler.comm.results.AttackResult;

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
	public AttackResult attackSettlement(Path catapult, Intersection settlement) {
		Location l = Model.getLocation(catapult);
		Location i = Model.getLocation(settlement);
		try {
			return controller.attackSettlement(l, i);
		} catch (Exception e) { e.printStackTrace(); System.exit(-1); return null; }		
	}
	
	/**
	 * @param path
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public boolean buildCatapult(Path path) {
		Location l = Model.getLocation(path);
		try {
			return controller.buildCatapult(l);
		} catch (Exception e) { e.printStackTrace(); System.exit(-1); return false; }
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
		} catch (Exception e) { e.printStackTrace(); System.exit(-1); }
		
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
		} catch (Exception e) { e.printStackTrace(); System.exit(-1); }
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
		} catch (Exception e) { e.printStackTrace(); System.exit(-1); }
	}
	
	/**
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void claimVictory() {
		try {
			controller.claimVictory();
		} catch (Exception e) { e.printStackTrace(); System.exit(-1); }
	}
	
	/**
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void endTurn(){
		try {
			controller.endTurn();
		} catch (Exception e) { e.printStackTrace(); System.exit(-1); }
	}
	
	/**
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void leaveMatch() {
		try {
			setEndOfGame(true);
			controller.leaveMatch();
		} catch (Exception e) { e.printStackTrace(); System.exit(-1); }
	}
	
	/**
	 * @param sourcePath
	 * @param destinationPath
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public boolean moveCatapult(Path sourcePath, Path destinationPath) {
		Location l = Model.getLocation(sourcePath);
		Location l2 = Model.getLocation(destinationPath);
		try {
			return controller.moveCatapult(l, l2);
		} catch (Exception e) { e.printStackTrace(); System.exit(-1); return false;}
	}
	
	/**
	 * @param sourceField
	 * @param destinationField
	 * @param victimPlayer
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public Resource moveRobber(Field sourceField, Field destinationField, Player victimPlayer) {
		Point p = Model.getLocation(sourceField);
		Point p2 = Model.getLocation(destinationField);
		Set<Long> keySet = model.getPlayerMap().keySet();
		if (victimPlayer != null){
			for (Long l : keySet) {
				Player player = model.getPlayerMap().get(l);
				if (player.equals(victimPlayer)){
					try {
						return controller.moveRobber(p, p2, l);
					} catch (Exception e) { e.printStackTrace(); System.exit(-1); }
					break;
				}
			}
		} else
			try {
				return controller.moveRobber(p, p2, -1);
			} catch (Exception e) { e.printStackTrace(); System.exit(-1); }
		throw new IllegalStateException();
	}
	
	/**
	 * @param resourcePackage
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public long offerTrade(ResourcePackage resourcePackage) {
		// TODO: Ueberpruefe ob Handel so erlaubt (Verhaeltnisse)
		int lumber = resourcePackage.getResource(LUMBER);
		int brick =  resourcePackage.getResource(BRICK);
		int wool =  resourcePackage.getResource(WOOL);
		int grain =  resourcePackage.getResource(GRAIN);
		int ore =   resourcePackage.getResource(ORE);
		try {
			return controller.offerTrade(lumber, brick, wool, grain, ore);
		} catch (Exception e) { e.printStackTrace(); System.exit(-1); return -1;}
	}
	
	/**
	 * @param decision
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public long respondTrade(boolean decision) {
		try {
			return controller.respondTrade(decision);
		} catch (Exception e) { e.printStackTrace(); System.exit(-1); return -1;}
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
		} catch (Exception e) { e.printStackTrace(); System.exit(-1); }
	}



	public void setEndOfGame(boolean b) {
		controller.setEndOfGame(b);
	}
	
	public void addGuiEvent(Clickable c) {
		controller.addGuiEvent(c);
	}
	
}
