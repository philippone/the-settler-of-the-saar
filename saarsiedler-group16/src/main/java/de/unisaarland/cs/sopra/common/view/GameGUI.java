package de.unisaarland.cs.sopra.common.view;

import java.util.Map;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class GameGUI extends View{

	private Map<Player,String> playerNames;
	
	GameGUI(Player me, ModelReader modelReader, ControllerAdapter controllerAdapter, Map<Player,String> playerNames) {
		super(me, modelReader, controllerAdapter);
		
		throw new UnsupportedOperationException();
	}
	
	public void drawTradeMenu() {
		throw new UnsupportedOperationException();
	}
	
	public void drawBuildMenu() {
		throw new UnsupportedOperationException();
	}
	
	public void drawResource() {
		throw new UnsupportedOperationException();
	}
	
	public String getName(Player player) {
		if (player == null) throw new IllegalArgumentException();
		return playerNames.get(player);
	}

	@Override
	public void updatePath(Path path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateIntersection(Intersection intersection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateField(Field field) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateResources() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateVictoryPoints() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCatapultCount() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateSettlementCount(BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void eventRobber() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void eventTrade(ResourcePackage resourcePackage) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void eventNewRound(Player player) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTradePossibilities() {
		throw new UnsupportedOperationException();
	}
	
}
