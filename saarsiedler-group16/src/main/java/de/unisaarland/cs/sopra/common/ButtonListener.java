package de.unisaarland.cs.sopra.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;
import de.unisaarland.cs.st.saarsiedler.comm.exceptions.IllegalMatchSpecificationException;
import de.unisaarland.cs.st.saarsiedler.comm.results.ChangeReadyResult;
import de.unisaarland.cs.st.saarsiedler.comm.results.JoinResult;

public class ButtonListener implements ActionListener {

	private GUIFrame gui;
//	private Client client;
	private boolean readyStatus;
	public boolean joinAsObserver;

	public ButtonListener(GUIFrame guiFrame) {
		this.gui = guiFrame;
//		this.client= client;
		this.readyStatus=false;
		joinAsObserver=false;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Menu:
			if (arg0.getSource() == gui.exit){
				Client.closeConnection();
				System.exit(0);
			}
			if (arg0.getSource() == gui.menuItemSettings){
				gui.lobbyPanel.setVisible(false);
				gui.menuPanel.setVisible(false);
				gui.createPanel.setVisible(false);
				gui.joinPanel.setVisible(false);
				gui.settingsPanel.setVisible(true);
				Client.closeConnection();
			}
		//
		
		//StartMenu	
			if (arg0.getSource() == gui.settings_menu){
				gui.menuPanel.setVisible(false);
				gui.settingsPanel.setVisible(true);
			}
			if (arg0.getSource() == gui.play){
				Client.joinAsAI=true;
				Client.createConnection("sopra.cs.uni-saarland.de");
				refreshGameList();
				gui.menuPanel.setVisible(false);
				gui.lobbyPanel.setVisible(true);
				setUpListUpdater();
				Client.changeName("Player-Gruppe-16");
				
			}
			if (arg0.getSource() == gui.playAsAI){
				Client.joinAsAI=true;
				Client.createConnection("sopra.cs.uni-saarland.de");
				refreshGameList();
				gui.menuPanel.setVisible(false);
				gui.lobbyPanel.setVisible(true);
				setUpListUpdater();
				Client.changeName("AI-Gruppe-16");
			}
			if (arg0.getSource() == gui.exit_menu){
				System.exit(0);
			}
		//
		
		//Lobby
			if (arg0.getSource() == gui.create){
				gui.lobbyPanel.setVisible(false);
				gui.createPanel.setVisible(true);
			}
			if (arg0.getSource() == gui.join){
//				if(Client.matchInfo!=null){
					Client.joinMatch(joinAsObserver);
					gui.lobbyPanel.setVisible(false);
					gui.joinPanel.setVisible(true);
					refreshPlayerList();
//				}
			}
			if (arg0.getSource() == gui.back_lobby){
				gui.lobbyPanel.setVisible(false);
				gui.menuPanel.setVisible(true);
				Client.closeConnection();
			}
		//
			
		//Settings
			if (arg0.getSource() == gui.back_Settings){
				gui.settingsPanel.setVisible(false);
				gui.menuPanel.setVisible(true);
			}
		//
		
		//CreatePanel
			if (arg0.getSource() == gui.createMatch){
				try {
					Client.createMatch(gui.gameTitleField.getText(), Integer.valueOf(gui.numPlayersField.getText())
					,/*TODO mehr auswahl schaffen*/WorldRepresentation.getDefault(), joinAsObserver);} catch (Exception e) {e.printStackTrace();}
				gui.createPanel.setVisible(false);
				gui.joinPanel.setVisible(true);
				refreshPlayerList();
			}
			if (arg0.getSource() == gui.observerToggle){
				joinAsObserver=!joinAsObserver;
				if(joinAsObserver)
					gui.observerToggle.setText("Observer");
				else
					gui.observerToggle.setText("Player");
			}
			if (arg0.getSource() == gui.back_Create){
				gui.createPanel.setVisible(false);
				gui.lobbyPanel.setVisible(true);
			}
		//
			
			//JoinPanel
			if (arg0.getSource() == gui.readyToggle){
				readyStatus=!readyStatus;
				if(readyStatus)
					gui.readyToggle.setText("Ready!");
				else
					gui.readyToggle.setText("Set Ready");
				
				Client.ready(readyStatus);
			}
			
			if (arg0.getSource() == gui.back_join){
				try {Client.connection.leaveMatch();} catch (Exception e) {e.printStackTrace();}
				gui.joinPanel.setVisible(false);
				gui.lobbyPanel.setVisible(true);
			}
		//
		
		
	}
	private void setUpListUpdater(){
		try {
			Client.connection.registerMatchListUpdater(new GameListUpdater(this));	}catch(IOException e){throw new IllegalStateException("iwas mit Matchlistupdater faul!!!");}
	
	}
	

	public void refreshGameList(){
//		gameTable.getCellEditor().
		List<MatchInformation> matchList=null;
		try {
			matchList =Client.connection.listMatches();		} catch (IOException e1) {	e1.printStackTrace();		}
			
			gui.gameTable.setModel(new DefaultTableModel(
					parseMatchList(matchList),
					new String[] {"MatchID", "Name", "Players", "WorldID", "Already started"	}));
	}
	
	public void refreshPlayerList(){
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
		gui.playerTable.setModel(new DefaultTableModel( table ,new String[] {"Players", "ready-Status"	}));
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
