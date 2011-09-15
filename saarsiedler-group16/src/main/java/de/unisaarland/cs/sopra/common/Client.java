package de.unisaarland.cs.sopra.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.table.DefaultTableModel;

import org.lwjgl.opengl.DisplayMode;

import de.unisaarland.cs.sopra.common.controller.Controller;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ModelWriter;
import de.unisaarland.cs.sopra.common.view.AI;
import de.unisaarland.cs.sopra.common.view.GameGUI;
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
		initializeMatch();	
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
		return connection;
	}
	
	public static void changeName(String name){
		try {
			connection.changeName(name);} catch (Exception e) {e.printStackTrace();	}
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
	
	public static AI buildAI(Controller controller, Model model) {
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
		clientGUI.setVisible(false);
//		clientGUI.
		Model m = buildModel();
		Controller c = buildController(m);
		AI ai;
		if(joinAsAI)
			 ai = buildAI(c, m);
		
		GameEvent event = null;
			
		try {
			event = connection.getNextEvent(0);
		} catch (Exception e) {e.printStackTrace();	}
		//wenn event hier noch null ist, dann wurde kein event geliefert
		if(event==null) throw new IllegalStateException("kein event erhalten");
		
		if(!(event.getType()!=EventType.MATCH_END)) throw new IllegalArgumentException("sollte ein MatchStart liefert");
		MatchStart startEvent = ((MatchStart)event);
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
		
		long[] players = startEvent.getPlayerIds();
		byte[] number = startEvent.getNumbers();
		m.matchStart(players, number);
		
		
		Setting setting = new Setting(new DisplayMode(1024, 600), true, PlayerColors.RED);
		GameGUI gameGUI = null;
		try {
			gameGUI = buildGameGUI(c, m, startEvent.getPlayerIds());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		new Thread(gameGUI).start();
//		
	
		
		System.out.println("Das Spiel war erfolgreich! =)");
//		new Thread(gui).start();
		try {
			Thread.sleep(5000);
			c.mainLoop();
		} catch (Exception e) {e.printStackTrace();
		}
		
	}
	
	public static  void setUpListUpdater(){
		try {
			Client.connection.registerMatchListUpdater(new GameListUpdater());	}catch(IOException e){throw new IllegalStateException("iwas mit Matchlistupdater faul!!!");}
	
	}
	
	
	public static void refreshGameList(){
		List<MatchInformation> matchList=null;
		try {
			matchList =Client.connection.listMatches();		} catch (IOException e1) {	e1.printStackTrace();		}
			
			clientGUI.gameTable.setModel(new DefaultTableModel(
					parseMatchList(matchList),
					new String[] {"MatchID", "Name", "Players", "WorldID", "Already started"	}));
	}
	
	public static void refreshPlayerList(){
		long[] players;
		boolean[]readyPlayers;
		if(Client.matchInfo==null) throw new IllegalStateException("Tries to setUp PlayersList, but actual machtlist is null");
		
		players = Client.matchInfo.getCurrentPlayers();
		readyPlayers = Client.matchInfo.getReadyPlayers();
		Object[][] table = new Object[players.length][2];
		for (int i = 0; i < table.length; i++) {
			try {
				table[i][0]= Client.connection.getPlayerInfo(players[i]).getName();} catch (IOException e) {e.printStackTrace();
			}
			table[i][1]= readyPlayers[i];
		}												
		clientGUI.playerTable.setModel(new DefaultTableModel( table ,new String[] {"Players", "ready-Status"	}));
	}
	
	private static Object[][] parseMatchList(List<MatchInformation> matchList1){
		if (matchList1 == null)	throw new IllegalArgumentException("matchList is null");
		Object[][] ret = new Object[matchList1.size()][5];
		int i =0;
		for (MatchInformation m : matchList1) {
			ret[i][0]=m.getId();
			ret[i][1]=m.getTitle();
			ret[i][2]=m.getCurrentPlayers().length+"/"+m.getNumPlayers();
			ret[i][3]=m.getWorldId();
			ret[i][4]=m.isStarted();
			i++;
		}
		return ret;
	}
}
