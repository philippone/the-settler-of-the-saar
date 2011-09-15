package de.unisaarland.cs.sopra.common;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeMap;

import org.lwjgl.opengl.DisplayMode;

import de.unisaarland.cs.sopra.common.controller.Controller;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.ModelWriter;
import de.unisaarland.cs.sopra.common.view.AI;
import de.unisaarland.cs.sopra.common.view.GameGUI;
import de.unisaarland.cs.sopra.common.view.View;
import de.unisaarland.cs.st.saarsiedler.comm.*;
import de.unisaarland.cs.st.saarsiedler.comm.GameEvent.EventType;
import de.unisaarland.cs.st.saarsiedler.comm.GameEvent.MatchStart;
import de.unisaarland.cs.st.saarsiedler.comm.results.*;

public class Client {
	
	public static Connection connection;
	public static MatchInformation matchInfo;
	private static GUIFrame clientGUI;
	private static Setting setting;
	private static WorldRepresentation worldRepo;
	public static boolean joinAsAI;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
//		Client me = new Client();
		clientGUI = new GUIFrame();
	}
	
	
	public static void joinMatch(boolean asObserver) {
		try {
			System.out.println(matchInfo);
			JoinResult res=connection.joinMatch(matchInfo.getId(), asObserver);
			if(res==JoinResult.ALREADY_RUNNING
					|| res==JoinResult.CLOSED
					|| res==JoinResult.FULL
					|| res==JoinResult.NOT_EXISTING)
				throw new IllegalStateException("Running/closed/full/not_existing");
			} catch (Exception e) {e.printStackTrace();}
	}
	
	public static void createMatch(String title, int numPlayer, WorldRepresentation world, boolean asObserver) {
		worldRepo=world;
		try {	//erstellt Match udn setzt aktuelle Matchinfo auf das erstellste spiel
			matchInfo = connection.newMatch(title, numPlayer,worldRepo, asObserver);	} catch (Exception e) {	e.printStackTrace();}
	}
	
	public static void ready(boolean ready) {
		ChangeReadyResult result = null;
		
		try {
			result = connection.changeReadyStatus(ready);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if (result == ChangeReadyResult.MATCH_STARTED ) {
			throw new IllegalStateException("Cant change ReadyStatus, GAME STARTED");
		}
		if(result == ChangeReadyResult.UNCHANGED){
			throw new IllegalStateException("Cant change ReadyStatus, JUST EPIC FAIL");
		}
		
	}
	
	public static void closeConnection() {
		try {connection.close();	} catch (IOException e) {e.printStackTrace();	}
	}
	
	public static void createConnection(String serverAdress) {
		try {
			connection= Connection.establish(serverAdress, joinAsAI);
		} catch (Exception e) {	e.printStackTrace();}
	}
	
	public Connection getConnection(){
		return this.connection;
	}
	
	public static void changeName(String name){
		try {
			Client.connection.changeName(name);} catch (Exception e) {e.printStackTrace();	}
	}

	public static Model buildModel() {
		try {
			return new Model(worldRepo,matchInfo, connection.getClientId());
		} catch (IOException e) {e.printStackTrace();}
		throw new IllegalStateException("couldnt build model");
	}
	
	public static Controller buildController(ModelWriter modelWriter) {
		return new Controller(connection, modelWriter);
	}
	
	public AI buildAI(Controller controller, Model model) {
		return new AI(model, new ControllerAdapter(controller, model));
	}
	
	public static GameGUI buildGameGUI(Controller controller, Model model, long[] playerIds) {
		Map<Long, String> iDsToNames = new TreeMap<Long, String>();
		for (long l : playerIds) {  // erstellt long-> names map
			try {
				iDsToNames.put(l, connection.getPlayerInfo(l).getName());
			} catch (IOException e) {e.printStackTrace();}
		}
		try {
			return new GameGUI(model, new ControllerAdapter(controller, model), iDsToNames , setting, matchInfo.getTitle());
		} catch (Exception e) {e.printStackTrace();	}
		throw new IllegalStateException("couldnt build GameGui");
	}
	
	public static void initializeMatch() {
		Model m = buildModel();
		Controller c = buildController(m);
		
		if(joinAsAI){
			//TODO kb drauf
		}else{
			GameEvent event = null;
			
			setting = new Setting(new DisplayMode(1024,580), true, PlayerColors.RED); //TODO change this!
			
			try {
				event = connection.getNextEvent(10000);
			} catch (Exception e) {e.printStackTrace();	}
			//wenn event hier noch null ist, dann wurde kein event geliefert
			if(event==null) throw new IllegalStateException("kein event erhalten");
			
			if(!(event.getType()!=EventType.MATCH_END)) throw new IllegalArgumentException("sollte ein MatchStart liefert");
			MatchStart startEvent = ((MatchStart)event);
			GameGUI gui = buildGameGUI(c, m, startEvent.getPlayerIds());
			m.addModelObserver(gui);
			
			long[] players = startEvent.getPlayerIds();
			byte[] number = startEvent.getNumbers();
			m.matchStart(players, number);
			
			System.out.println("Das Spiel war erfolgreich! =)");
			new Thread(gui).start();
			try {
				Thread.sleep(5000);
				c.mainLoop();
			} catch (Exception e) {e.printStackTrace();
			}
			
		}
	}
	
}
