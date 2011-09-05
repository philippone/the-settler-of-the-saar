package de.unisaarland.cs.sopra.common.view;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class AI extends View{



	AI(Player me, ModelReader modelReader, ControllerAdapter controllerAdapter) {
		super(me, modelReader, controllerAdapter);
		
		throw new UnsupportedOperationException();
	}

	private Strategy strategy;
	
	public void chooseStrategy(ControllerAdapter controllerAdapter) {
		throw new UnsupportedOperationException();
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
