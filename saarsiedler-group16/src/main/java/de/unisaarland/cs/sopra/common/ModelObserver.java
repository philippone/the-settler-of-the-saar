package de.unisaarland.cs.sopra.common;

import de.unisaarland.cs.sopra.common.model.*;

public interface ModelObserver {

	public void updatePath(Path path);
	public void updateIntersection(Intersection intersection);
	public void updateField(Field field);
	public void updateResources();
	public void updateVictoryPoints();
	public void updateCatapultCount();
	public void updateSettlementCount(BuildingType buildingType);
	public void updateTradePossibilities();
	public void eventRobber();
	public void eventTrade(ResourcePackage resourcePackage);
	public void eventNewRound(boolean itsMyTurn);
	
}
