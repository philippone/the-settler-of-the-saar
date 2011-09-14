package de.unisaarland.cs.sopra.common.controller;

import static de.unisaarland.cs.sopra.common.model.Resource.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.unisaarland.cs.sopra.common.model.*;

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
	public void attackSettlement(Path catapult, Intersection settlement) throws IllegalStateException, IllegalArgumentException, IOException {
		Location l = Model.getLocation(catapult);
		l.getX();
		l.getY();
		l.getOrientation();
		Location i = Model.getLocation(settlement);
		controller.attackSettlement(l, i);
		
	}
	
	/**
	 * @param path
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void buildCatapult(Path path) throws IllegalStateException, IllegalArgumentException, IOException {
		Location l = Model.getLocation(path);
		controller.buildCatapult(l);
	}
	
	/**
	 * @param intersection
	 * @param buildingType
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void buildSettlement(Intersection intersection, BuildingType buildingType) throws IllegalStateException, IllegalArgumentException, IOException {
		Location l = Model.getLocation(intersection);
		controller.buildSettlement(l, buildingType);
		
	}
	
	/**
	 * @param path
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void buildStreet(Path path) throws IllegalStateException, IllegalArgumentException, IOException {
		Location l = Model.getLocation(path);
		controller.buildStreet(l);
	}
	
	/**
	 * @param road
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void claimLongestRoad(List<Path> road) throws IllegalStateException, IllegalArgumentException, IOException {
		List<Location> roadList = new LinkedList<Location>();
		for (Path p : road){
			 Location l = Model.getLocation(p);
			 roadList.add(l);
		}
		controller.claimLongestRoad(roadList);
	}
	
	/**
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void claimVictory() throws IllegalStateException, IOException {
		controller.claimVictory();
	}
	
	/**
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void endTurn() throws IllegalStateException, IOException {
		controller.endTurn();
	}
	
	/**
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void leaveMatch() throws IllegalStateException, IOException {
		controller.leaveMatch();
	}
	
	/**
	 * @param sourcePath
	 * @param destinationPath
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void moveCatapult(Path sourcePath, Path destinationPath) throws IllegalStateException, IllegalArgumentException, IOException {
		Location l = Model.getLocation(sourcePath);
		Location l2 = Model.getLocation(destinationPath);
		controller.moveCatapult(l, l2);
	}
	
	/**
	 * @param sourceField
	 * @param destinationField
	 * @param victimPlayer
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void moveRobber(Field sourceField, Field destinationField, Player victimPlayer) throws IllegalStateException, IllegalArgumentException, IOException {
		Point p = Model.getLocation(sourceField);
		Point p2 = Model.getLocation(destinationField);
		Set<Long> keySet = model.getPlayerMap().keySet();
		if (victimPlayer != null){
			for (Long l : keySet) {
				Player player = model.getPlayerMap().get(l);
				if (player.equals(victimPlayer)){
					controller.moveRobber(p, p2, l);
					break;
				}
			}
		} else controller.moveRobber(p, p2, -1);
	}
	
	/**
	 * @param resourcePackage
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void offerTrade(ResourcePackage resourcePackage) throws IllegalStateException, IOException {
		// TODO: Ueberpruefe ob Handel so erlaubt (Verhaeltnisse)
		int lumber = resourcePackage.getResource(LUMBER);
		int brick =  resourcePackage.getResource(BRICK);
		int wool =  resourcePackage.getResource(WOOL);
		int grain =  resourcePackage.getResource(GRAIN);
		int ore =   resourcePackage.getResource(ORE);
		controller.offerTrade(lumber, brick, wool, grain, ore);
	}
	
	/**
	 * @param decision
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void respondTrade(boolean decision) throws IllegalStateException, IllegalArgumentException, IOException {
		controller.respondTrade(decision);
	}
	
	/**
	 * @param resourcePackage
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void returnResources(ResourcePackage resourcePackage) throws IllegalStateException, IllegalArgumentException, IOException {
		int lumber = resourcePackage.getResource(LUMBER);
		int brick =  resourcePackage.getResource(BRICK);
		int wool =  resourcePackage.getResource(WOOL);
		int grain =  resourcePackage.getResource(GRAIN);
		int ore =   resourcePackage.getResource(ORE);
		controller.returnResources(lumber, brick, wool, grain, ore);
	}



	public void setEndOfGame(boolean b) {
		controller.setEndOfGame(b);
	}
	
}
