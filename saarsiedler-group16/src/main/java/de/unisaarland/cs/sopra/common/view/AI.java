package de.unisaarland.cs.sopra.common.view;

import de.unisaarland.cs.sopra.common.controller.Controller;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class AI extends View{
	
	public static void main(String[] args){
		try {
			Connection c = Connection.establish("sopra.cs.uni-saarland.de", true);
			WorldRepresentation wr = WorldRepresentation.getDefault();
			MatchInformation mi = c.newMatch("The best KI ever!", 1, wr, false);
			Model m = new Model(wr, mi, c.getClientId());
			Controller cont = new Controller(c, m);
			ControllerAdapter contAdap = new ControllerAdapter(cont, m);
			AI ai = new AI(m, contAdap);
			m.addModelObserver(ai);
			c.changeReadyStatus(true);
			System.out.println("Initalisierung hat funktioniert!");
			cont.mainLoop();
			System.out.println("Das Spiel war erfolgreich! =)");
		} catch (Exception e){
			System.out.println("Das Spiel war nicht erflogreich!");
			e.printStackTrace();
		}
	}

	Strategy s;
	
	public AI(ModelReader modelReader, ControllerAdapter controllerAdapter){
		super(modelReader, controllerAdapter);
		evaluateBestStrategy();
	}
	
	public void evaluateBestStrategy(){
		s = new DoNothingStrategy();
	}
	
	public void executeBestStrategy() {
		try{
		s.execute(modelReader, controllerAdapter);
		
		}
	catch (Exception e){}
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
		s = new RobberStrategy();
	}

	@Override
	public void eventTrade(ResourcePackage resourcePackage) {
		s = new TradeStrategy();
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

	@Override
	public void initTurn() {
		s = new InitializeStrategy();
		executeBestStrategy();
		
	}
	
}
