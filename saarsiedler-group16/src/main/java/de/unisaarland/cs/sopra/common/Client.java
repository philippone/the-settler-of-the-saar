package de.unisaarland.cs.sopra.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import de.unisaarland.cs.sopra.common.controller.Controller;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ModelWriter;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.sopra.common.view.GameGUI;
import de.unisaarland.cs.sopra.common.view.ai.Ai;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.GameEvent;
import de.unisaarland.cs.st.saarsiedler.comm.GameEvent.EventType;
import de.unisaarland.cs.st.saarsiedler.comm.GameEvent.MatchStart;
import de.unisaarland.cs.st.saarsiedler.comm.GameEvent.PlayerLeft;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;
import de.unisaarland.cs.st.saarsiedler.comm.results.ChangeReadyResult;
import de.unisaarland.cs.st.saarsiedler.comm.results.JoinResult;

public class Client {
	
	public static Connection connection;
	public static MatchInformation matchInfo;
	private static GUIFrame clientGUI;
	private static Setting setting;
	static WorldRepresentation worldRepo;
	public static boolean joinAsAI;
	static ResourcePackage returnPackage;
	private static Popup popup;
	public static int acceptTrade;
	private static DefaultTableModel gameTableModel;
	private static DefaultTableModel playerTableModel;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		initOpenGL();
		setting = new Setting(new DisplayMode(1024, 600), true, PlayerColors.BLUE);
		clientGUI = new GUIFrame();
		popup = new Popup();
		loadSettings();
		
	}
	private static void initOpenGL(){
		
		String[] list = new String[] {
				"jinput-dx8_64.dll", "jinput-dx8.dll", "jinput-raw_64.dll",
				"jinput-raw.dll", "libjinput-linux.so", "libjinput-linux64.so",
				"libjinput-osx.jnilib", "liblwjgl.jnilib", "liblwjgl.so",
				"liblwjgl64.so", "libopenal.so", "libopenal64.so",
				"lwjgl.dll", "lwjgl64.dll", "openal.dylib", "OpenAL32.dll", "OpenAL64.dll" };
		String tmpdir = System.getProperty("java.io.tmpdir");
		
		boolean everythingIsBad = true;
		while (everythingIsBad) {
			everythingIsBad = false;
			for (String act : list) {
				InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("native/" + act);
				Random r = new Random();
	
				try {
					GameGUI.saveFile(tmpdir + "/" + act, input);
				} catch (IOException e) {
					tmpdir = System.getProperty("java.io.tmpdir") + r.nextInt();
					new File(tmpdir).mkdirs();
					everythingIsBad = true;
					break;
				}
			}
		}
		String seperator;
		if (System.getProperty("sun.desktop") != null && System.getProperty("sun.desktop").equals("windows")) seperator = ";";
		else seperator = ":";
		System.setProperty("java.library.path", System.getProperty("java.library.path") + seperator + tmpdir);
		java.lang.reflect.Field vvv = null;
		try {
			vvv = ClassLoader.class.getDeclaredField("sys_paths");
		} catch (Exception e) { e.printStackTrace(); }
		vvv.setAccessible(true); 
		try {
			vvv.set(null, null);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public static void joinMatch(boolean asObserver) {
		try {
			worldRepo=connection.getWorld(matchInfo.getWorldId());
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
			return new Model(worldRepo, matchInfo, connection.getClientId());
		} catch (IOException e) {e.printStackTrace();}
		throw new IllegalStateException("couldnt build model");
	}
	
	public static Controller buildController(ModelWriter modelWriter) {
		return new Controller(connection, modelWriter);
	}
	
	public static Ai buildAI(Controller controller, Model model) {
		return new Ai(model, new ControllerAdapter(controller, model));
	}
	
	public static GameGUI buildGameGUI(Controller controller, Model model, long[] playerIDs, boolean AIisPlaying, CyclicBarrier barrier) {
		Map<Player, String> plToNames = new HashMap<Player, String>();
		Iterator<Player> iterPl = model.getTableOrder().iterator();
		for (long l : playerIDs) {  // erstellt Player-> names map
			try {
				System.out.println(connection.getPlayerInfo(l).getName());
				plToNames.put(iterPl.next(), connection.getPlayerInfo(l).getName());
			} catch (IOException e) {e.printStackTrace();}
		}
		return new GameGUI(model, new ControllerAdapter(controller, model), plToNames , setting, matchInfo.getTitle(), AIisPlaying, barrier);
	}
	
	public static void changeSettings(DisplayMode mode, boolean fullscreen,PlayerColors playerColor, String name){
		setting = new Setting(mode, fullscreen, playerColor);
		Setting.setName(name);
	}
	
	public static void initializeMatch() {					
		GameEvent event = null;
		boolean jetztgehtslos = false;
		MatchStart startEvent = null;
		while(!jetztgehtslos) {
			try {
				event = connection.getNextEvent(0);
			} catch (Exception e) {e.printStackTrace();	}
			if (event==null) {
				throw new IllegalStateException("kein event erhalten");
			}
			else if ((event.getType()==EventType.MATCH_START)) {
				startEvent = ((MatchStart)event);
				jetztgehtslos = true;
			}
			else if ((event.getType()==EventType.PLAYER_LEFT)) {
				//TODO: handle player left
				System.out.println("Client "+((PlayerLeft)event).getClientId()+" LEFT");
			}
			else {
				System.out.println(event);
				throw new IllegalArgumentException("illegal event returned");
			}
		}
		
		Model m = buildModel();
		Controller c = buildController(m);
		
		if(joinAsAI)
			 buildAI(c, m);
		
		long[] players = startEvent.getPlayerIds();
		byte[] number = startEvent.getNumbers();
		m.matchStart(players, number);
		
		GameGUI gameGUI = null;
		CyclicBarrier barrier = new CyclicBarrier(2);
		try {
			gameGUI = buildGameGUI(c, m, players, joinAsAI, barrier);
		} catch (Exception e1) {e1.printStackTrace();
		}
		
		clientGUI.setVisible(false); // versteckt die ClientGui
		new Thread(gameGUI).start();
		try {
			barrier.await();
		} catch (Exception e) {e.printStackTrace();}
		
		System.out.println("Das Spiel wurde erfolgreich gestartet! =)");
		try {
			new Thread(c).start();
		} catch (Exception e) {e.printStackTrace();
		}
		
	}
	
	public static  void setUpListUpdater(){
		try {
			Client.connection.registerMatchListUpdater(new GameListUpdater(gameTableModel,playerTableModel));	}catch(IOException e){throw new IllegalStateException("iwas mit Matchlistupdater faul!!!");}
	
	}
	
	
	public static void refreshGameList(){
		List<MatchInformation> matchList=null;
		try {
			matchList =Client.connection.listMatches();		
		} catch (IOException e1) {	e1.printStackTrace();}
		gameTableModel=new DefaultTableModel(
				parseMatchList(matchList)
				,new String[] {"MatchID", "Name", "Players", "WorldID", "Already started"
					});
		clientGUI.gameTable.setModel(gameTableModel);
	
		TableColumnModel cm = clientGUI.gameTable.getColumnModel();
		cm.getColumn(0).setMinWidth(50);
		cm.getColumn(0).setMaxWidth(60);
		cm.getColumn(1).setMinWidth(220);
		cm.getColumn(2).setMinWidth(60);
		cm.getColumn(2).setMaxWidth(60);
		cm.getColumn(3).setMinWidth(50);
		cm.getColumn(3).setMaxWidth(50);
		
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
		playerTableModel=new DefaultTableModel( table ,new String[] {"Players", "ready-Status"});
		clientGUI.playerTable.setModel(playerTableModel);
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
	public static Object[] parseMatchInfo(MatchInformation matchList1){
		if (matchList1 == null)	throw new IllegalArgumentException("matchInfo is null");
		Object[] ret = new Object[5];
			ret[0]=matchList1.getId();
			ret[1]=matchList1.getTitle();
			ret[2]=matchList1.getCurrentPlayers().length+"/"+matchList1.getNumPlayers();
			ret[3]=matchList1.getWorldId();
			ret[4]=matchList1.isStarted();
		return ret;
	}
	public static void saveSettings(){
		String color= (String) clientGUI.playerColorBox.getItemAt(clientGUI.playerColorBox.getSelectedIndex());
		String resol= (String) clientGUI.resolutionBox.getItemAt(clientGUI.resolutionBox.getSelectedIndex());
		String separator = System.getProperties().getProperty("file.separator ");
		
		File f = new File("."+separator+"settings");
//		File f = new File("options.properties");
		Properties p = new Properties();
		 
		p.setProperty("Color", color);
		p.setProperty("Resolution", resol);
		p.setProperty("Name", Setting.getName());
		
		if( Setting.isFullscreen() )
			p.setProperty("Fullscreen","true");
		else 
			p.setProperty("Fullscreen","false");
		 
		try {
			p.storeToXML(new FileOutputStream(f), new Date(System.currentTimeMillis()).toString());
		} catch (Exception e) {e.printStackTrace();	}
	}
	
	public static void loadSettings(){
		try
		{
		String separator = System.getProperties().getProperty("file.separator ");
		File f = new File("."+separator+"settings");
//		File f = new File("options.properties");
		Properties p = new Properties();
		p.loadFromXML(new FileInputStream(f));
		 
		String color = p.getProperty("Color");
		for(int i=0; i<GUIFrame.farben.length; i++){
			if(color.equals(GUIFrame.farben[i])){
				Setting.setPlayerColor(GUIFrame.pc[i]);
				clientGUI.playerColorBox.setSelectedIndex(i);
			}
		}
		String resol = p.getProperty("Resolution");
		for(int i=0; i<GUIFrame.dmodes.length; i++){
			if(resol.equals(GUIFrame.dmodes[i])){
				Setting.setDisplayMode(GUIFrame.displaymodes[i]);
				clientGUI.resolutionBox.setSelectedIndex(i);
			}
		}
		String name = p.getProperty("Name");
		Setting.setName(name);
		clientGUI.playerName.setText(name);
		
		String fullscreen = p.getProperty("Fullscreen");
		if(fullscreen.equals("true")){
			clientGUI.fullscreenToggle.setText("ON");
			clientGUI.fullscreenToggle.setSelected(true);
			
			clientGUI.resolutionBox.setEnabled(false);
			Setting.setFullscreen(true);
			Setting.setDisplayMode(Display.getDisplayMode());	
		}
		else {
			clientGUI.fullscreenToggle.setText("OFF");
			Setting.setFullscreen(false);
		}
		}
		catch (Exception e)	{
			System.out.println("No options found, using default!");
		}
	}
	
	public static ResourcePackage returnResources(ResourcePackage rp){
		returnPackage=null;
		popup.incomingTradePanel.setVisible(false);
		popup.tradePanel.setVisible(false);
		popup.returnPackPanel.setVisible(true);
		int n = rp.size();
		popup.setN(n);
		popup.setText("You have to choose "+(n/2)+" Resources!");
		popup.lumberMax.setText(""+rp.getResource(Resource.LUMBER));
		popup.brickMax.setText(""+rp.getResource(Resource.BRICK));
		popup.woolMax.setText(""+rp.getResource(Resource.WOOL));
		popup.grainMax.setText(""+rp.getResource(Resource.GRAIN));
		popup.oreMax.setText(""+rp.getResource(Resource.ORE));
		popup.setVisible(true);
		
		while(returnPackage==null){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		popup.reset();
		popup.setVisible(false);
		return returnPackage;
	}
	
	public static ResourcePackage tradeOffer(ResourcePackage rp, Set<HarborType> set){
		//TODO use the set to show trade possibilites
		returnPackage=null;
		popup.setTitle("Make a Trade Offer");
		popup.incomingTradePanel.setVisible(false);
		popup.tradePanel.setVisible(true);
		popup.returnPackPanel.setVisible(false);
		popup.setVisible(true);
		popup.lumberMax2.setText(""+rp.getResource(Resource.LUMBER));
		popup.brickMax2.setText(""+rp.getResource(Resource.BRICK));
		popup.woolMax2.setText(""+rp.getResource(Resource.WOOL));
		popup.grainMax2.setText(""+rp.getResource(Resource.GRAIN));
		popup.oreMax2.setText(""+rp.getResource(Resource.ORE));
		
		while(returnPackage==null){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		popup.reset();
		popup.setVisible(false);
		return returnPackage;
	}
	
	public static boolean incomingTradeOffer(ResourcePackage rp, ResourcePackage incomingRp){
		acceptTrade=0;
		popup.setTitle("Accept Trade?");
		popup.tradePanel.setVisible(false);
		popup.returnPackPanel.setVisible(false);
		popup.incomingTradePanel.setVisible(true);
		popup.setVisible(true);
		popup.lumberMax3.setText(""+rp.getResource(Resource.LUMBER));
		popup.brickMax3.setText(""+rp.getResource(Resource.BRICK));
		popup.woolMax3.setText(""+rp.getResource(Resource.WOOL));
		popup.grainMax3.setText(""+rp.getResource(Resource.GRAIN));
		popup.oreMax3.setText(""+rp.getResource(Resource.ORE));
		
		while(acceptTrade==0){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		popup.reset();
		popup.setVisible(false);
		if(acceptTrade>0)return true;
		else return false;
	}
	
	public static void backToLobby(){
		clientGUI.joinPanel.setVisible(false);
		clientGUI.lobbyPanel.setVisible(true);
		clientGUI.
		clientGUI.setVisible(true);
	}
}

