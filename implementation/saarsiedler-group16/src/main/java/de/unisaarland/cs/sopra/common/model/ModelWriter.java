package de.unisaarland.cs.sopra.common.model;

import java.util.List;

import de.unisaarland.cs.st.saarsiedler.comm.results.AttackResult;

public interface ModelWriter {

	public void newRound(int number);
	public void attackSettlement(Location catapultPath, Location settlementIntersection, AttackResult result);
	public void buildCatapult(Location destination, boolean fightOutCome);
	public void buildStreet(Location destination);
	public void buildSettlement(Location location, BuildingType buildingType);
	public void longestRaodClaimed(List<Location> road);
	public void matchStart(long[] players, byte[] number);
	public void catapultMoved(Location sourcePath, Location destinationPath, boolean fightOutCome);
	public void playerLeft(long playerID);
	public void robberMoved(Point sourceField, Point destinationField, long victimPlayer, Resource stolenResource);
	public void tradeOffer(int lumber, int brick, int wool, int grain, int ore);
	public void respondTrade(long playerID);
	
}
