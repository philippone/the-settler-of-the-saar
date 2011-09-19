package de.unisaarland.cs.sopra.common.view;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

import org.lwjgl.opengl.DisplayMode;

import de.unisaarland.cs.sopra.common.PlayerColors;
import de.unisaarland.cs.sopra.common.Setting;
import de.unisaarland.cs.sopra.common.controller.Controller;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.GameEvent;
import de.unisaarland.cs.st.saarsiedler.comm.GameEvent.EventType;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class AI extends View{
	
	
	public static void main(String[] args){
		
		////////////initgui///////////////////
	 	String[] list = new String[] {
				"jinput-dx8_64.dll", "jinput-dx8.dll", "jinput-raw_64.dll",
				"jinput-raw.dll", "libjinput-linux.so", "libjinput-linux64.so",
				"libjinput-osx.jnilib", "liblwjgl.jnilib", "liblwjgl.so",
				"liblwjgl64.so", "libopenal.so", "libopenal64.so",
				"lwjgl.dll", "lwjgl64.dll", "openal.dylib", "OpenAL32.dll", "OpenAL64.dll" };
		String tmpdir = System.getProperty("java.io.tmpdir");
		for (String act : list) {
			InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("native/" + act);
			try {
				GameGUI.saveFile(tmpdir + "/" + act, input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String seperator;
		if (System.getProperty("sun.desktop") != null && System.getProperty("sun.desktop").equals("windows")) seperator = ";";
		else seperator = ":";
		System.setProperty("java.library.path", System.getProperty("java.library.path") + seperator + tmpdir);
		java.lang.reflect.Field vvv = null;
		try {
			vvv = ClassLoader.class.getDeclaredField("sys_paths");
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		vvv.setAccessible(true); 
		try {
			vvv.set(null, null);
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		////////////////ende init gui/////////////
		
		try {
			Connection c = Connection.establish("sopra.cs.uni-saarland.de", true);
			WorldRepresentation wr = WorldRepresentation.getDefault();

// ---------Create Code-----------
		MatchInformation mi = c.newMatch("K(a)I!", 1, wr, false);
// -------------------------------	

// --------Join Code---------
//		long matchId = 6534;
//	c.joinMatch(matchId, false);
//		MatchInformation mi = c.getMatchInfo(matchId);
// --------------------------
			 

			Model m = new Model(wr, mi, c.getClientId());
			System.out.printf("MatchID: %s", mi.getId());
			Controller cont = new Controller(c, m);
			ControllerAdapter contAdap = new ControllerAdapter(cont, m);
			AI ai = new AI(m, contAdap);
			//Thread.sleep(15000);
			c.changeReadyStatus(true);
			GameEvent ge = c.getNextEvent(0);
			if (ge.getType() != EventType.MATCH_START)
				throw new IllegalStateException();
			long[] players = ((GameEvent.MatchStart) ge).getPlayerIds();
			byte[] number = ((GameEvent.MatchStart) ge).getNumbers();
			m.matchStart(players, number);
			Setting setting = new Setting(new DisplayMode(1024,580), true, PlayerColors.RED);
			Map<Player, String> plToNames = new HashMap<Player, String>();
			Iterator<Player> iterPl = m.getTableOrder().iterator();
			for (long l : players) {  // erstellt Player-> names map
				try {
					System.out.println(c.getPlayerInfo(l).getName());
					plToNames.put(iterPl.next(), c.getPlayerInfo(l).getName());
				} catch (IOException e) {e.printStackTrace();}
			}
			CyclicBarrier barrier = new CyclicBarrier(2);
			GameGUI gameGUI = null;
			try {
				gameGUI = new GameGUI(m, null, plToNames, setting, "K(A)I", true, barrier);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			new Thread(gameGUI).start();
			barrier.await();
			cont.mainLoop();
			System.out.println("Das Spiel war erfolgreich! =)");
		} catch (Exception e){
			System.out.println("Das Spiel war nicht erflogreich!");
			e.printStackTrace();
		}
	}

	Strategy s,s1;
	
	public AI(ModelReader modelReader, ControllerAdapter controllerAdapter){
		super(modelReader, controllerAdapter);
		s=new DoNothingStrategy();
		modelReader.addModelObserver(this);
	}
	
	public void evaluateBestStrategy(){
		
//		float strategyValue=(float) Math.random()*3;
//		if (strategyValue<1) s=new BuildStreetStrategy();
//		if (strategyValue>2) s=new BuildATownStrategy();
//		if (strategyValue==2) s=new BuildACatapultStrategy();
//		if (strategyValue>1 && strategyValue<2) s=new BuildVillage();
		//float strategyValue=(float) Math.random()*4;
		//if (strategyValue<1) s=new BuildStreetStrategy();
		//if (strategyValue>1 && strategyValue<2) s=new BuildVillage();
		//if (strategyValue>2 && strategyValue<3) s=new BuildATownStrategy();
		//if (strategyValue>3) s=new BuildACatapultStrategy();
//		float strategyValue = (float) Math.random() * 4;
//		if (strategyValue < 1)
//			s = new BuildStreetStrategy();
//		if (strategyValue > 1 && strategyValue < 2)
//			s = new BuildVillage();
//		if (strategyValue > 2 && strategyValue < 3)
//			s = new BuildATownStrategy();
//		if (strategyValue > 3)
//			s = new BuildACatapultStrategy();

		//s = new BuildStreetStrategy();
		//s= new BuildATownStrategy();
		//s1 = new BuildACatapultStrategy();
		
		/*______________________________________________________________
		s=new DoNothingStrategy();
		
		ArrayList<Strategy> strategies=new ArrayList<Strategy>();
		strategies.add(new BuildVillage());
		strategies.add(new BuildATownStrategy());
		strategies.add(new BuildACatapultStrategy());
		strategies.add(new BuildStreetStrategy());
		strategies.add(new MoveCatapultStrategy());
		strategies.add(new AttackSettlementStrategy());
		
		float bestValue=0;
		float strategyValue=0;
		for (Strategy strat: strategies){
			try {
				strategyValue=strat.evaluate(modelReader, controllerAdapter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (strategyValue>bestValue){
				bestValue=strategyValue;
				s=strat;
			}
		}
		____________________________________________________________*/
		
	}
	
	public void executeBestStrategy() {
		try{
			//Thread.sleep(5000);

		s.execute(modelReader, controllerAdapter);
		//s1.execute(modelReader, controllerAdapter);
		}
	catch (Exception e){ e.printStackTrace(); }
		
		
		/*_______________________________________________________________
		evaluateBestStrategy();
		while (!s.instanceOf(DoNothingStrategy)){
			try{
				s.execute(modelReader, controllerAdapter);
			}
			catch (Exception e){ e.printStackTrace(); }
			evaluateBestStrategy();
		} 
		ca.endTurn();
		// WARNING: Exceptions + remove endTurn() in strategies 
		__________________________________________________________________*/
		
		
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
		 controllerAdapter.setEndOfGame(modelReader.getMe() == modelReader.getPlayerMap().get(playerID));
	}

	@Override
	// a seven was diced
	public void eventRobber() {
		s = new MoveRobberStrategy();
		executeBestStrategy();
		// s=new MoveRobberStrategy
		// s.execute(modelReader,controllerAdapter);
		// WARNING Try Catch block
	}

	@Override
	public void eventTrade(ResourcePackage resourcePackage) {
		s = new TradeStrategy();
		executeBestStrategy();
	}

	@Override
	public void eventNewRound(int number) {
		if (modelReader.getMe() == modelReader.getCurrentPlayer()){
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
