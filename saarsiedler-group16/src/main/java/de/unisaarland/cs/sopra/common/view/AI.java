package de.unisaarland.cs.sopra.common.view;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class AI extends View{

	Strategy s;
	
	public AI(ModelReader modelReader, ControllerAdapter controllerAdapter){
		super(modelReader, controllerAdapter);
		evaluateBestStrategy();
	}
	
	public void evaluateBestStrategy(){
		s = new DoNothingStrategy();
	}
	
	public void executeBestStrategy(){
		s.execute(modelReader, controllerAdapter);
	}

	@Override
	public void updatePath(Path path) {
		// TODO
	}

	@Override
	public void updateIntersection(Intersection intersection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateField(Field field) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateResources() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateVictoryPoints() {
		// TODO
	}

	@Override
	public void updateSettlementCount(BuildingType buildingType) {
		// TODO
	}

	@Override
	public void updateTradePossibilities() {
		// TODO Auto-generated method stub
	}

	@Override
	public void eventPlayerLeft(long playerID) {
		// TODO Auto-generated method stub
	}

	@Override
	// a seven was diced
	public void eventRobber() {
		s = RobberStrategy(controllerAdapter);
	}

	@Override
	public void eventTrade(ResourcePackage resourcePackage) {
		s = new TradeStrategy(controllerAdapter);
	}

	@Override
	public void eventNewRound() {
		// TODO Auto-generated method stub
		if (modelReader.getMe() == modelReader.getCurrentPlayer()) {
			evaluateBestStrategy();
			executeBestStrategy();
		}
	}

	@Override
	public void updateCatapultCount() {
		// TODO Auto-generated method stub
		
	}
	
}
