package de.unisaarland.cs.sopra.common.model;

import java.util.List;

import de.unisaarland.cs.st.saarsiedler.comm.results.AttackResult;

public interface ModelWriter {

	/**
	 * @param number
	 */
	public void newRound(int number);
	
	
	/**
	 * @param catapultPath
	 * @param settlementIntersection
	 * @param result
	 */
	public void attackSettlement(Location catapultPath, Location settlementIntersection, AttackResult result);
	
	
	/**
	 * @param destination
	 * @param fightOutCome
	 */
	public void buildCatapult(Location destination, boolean fightOutCome);
	
	
	/**
	 * @param destination
	 */
	public void buildStreet(Location destination);
	
	
	/**
	 * @param location
	 * @param buildingType
	 */
	public void buildSettlement(Location location, BuildingType buildingType);
	
	
	/**
	 * @param road
	 */
	public void longestRoadClaimed(List<Location> road);
	
	
	/**
	 * @param players
	 * @param number
	 * @param names 
	 */
	public void matchStart(long[] players, byte[] number);
	
	
	/**
	 * @param sourcePath
	 * @param destinationPath
	 * @param fightOutCome
	 */
	public void catapultMoved(Location sourcePath, Location destinationPath, boolean fightOutCome);
	
	
	/**
	 * @param playerID
	 */
	public void playerLeft(long playerID);
	
	
	/**
	 * @param sourceField
	 * @param destinationField
	 * @param victimPlayer
	 * @param stolenResource
	 */
	public void robberMoved(Point sourceField, Point destinationField, long victimPlayer, Resource stolenResource);
	
	
	/**
	 * @param lumber
	 * @param brick
	 * @param wool
	 * @param grain
	 * @param ore
	 */
	public void tradeOffer(int lumber, int brick, int wool, int grain, int ore);
	
	
	/**
	 * @param playerID
	 */
	public void respondTrade(long playerID);
	
	/**
	 * @param lumber
	 * @param brick
	 * @param wool
	 * @param grain
	 * @param ore
	 */
	public void returnResources(int lumber, int brick, int wool, int grain, int ore);

	/**
	 * @param player
	 */
	public void setMe(Player player);

	public boolean isOurTurn();


	public void matchEnd(long winnerID);
	
	}
