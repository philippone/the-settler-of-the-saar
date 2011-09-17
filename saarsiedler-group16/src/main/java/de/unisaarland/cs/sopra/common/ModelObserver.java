package de.unisaarland.cs.sopra.common;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public interface ModelObserver {

	public void updatePath(Path path);
	public void updateIntersection(Intersection intersection);
	public void updateField(Field field);
	public void updateResources();
	public void updateVictoryPoints();
	public void updateCatapultCount();
	public void updateSettlementCount(BuildingType buildingType);
	public void updateTradePossibilities();
	public void eventPlayerLeft(long playerID);
	public void eventRobber();
	public void eventTrade(ResourcePackage resourcePackage);
	public void eventNewRound(int number);
	public void initTurn();
	
}
