package de.unisaarland.cs.sopra.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.lwjgl.opengl.Display;

public class ButtonListener implements ActionListener {

	private GUIFrame gui;
//	private Client client;
	public static boolean readyStatus;
	public boolean joinAsObserver;

	public ButtonListener(GUIFrame guiFrame) {
		this.gui = guiFrame;
//		this.client= client;
		ButtonListener.readyStatus=false;
		joinAsObserver=false;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Menu:
			if (arg0.getSource() == gui.exit){
				System.exit(0);
			}
			if (arg0.getSource() == gui.menuItemSettings){
				Client.loadSettings();
				gui.lobbyPanel.setVisible(false);
				gui.menuPanel.setVisible(false);
				gui.createPanel.setVisible(false);
				gui.joinPanel.setVisible(false);
				gui.settingsPanel.setVisible(true);
			}
		//
		
		//StartMenu	
			if (arg0.getSource() == gui.settings_menu){
				Client.loadSettings();
				gui.menuPanel.setVisible(false);
				gui.settingsPanel.setVisible(true);
			}
			if (arg0.getSource() == gui.play){
				Client.joinAsAI=false;
				Client.createConnection("sopra.cs.uni-saarland.de");
//				Client.createConnection("sopra.hammacher.name"); //Backup server
				Client.setUpListUpdater();
				Client.refreshGameList();
				Client.changeName(Setting.getName());
				gui.menuPanel.setVisible(false);
				gui.lobbyPanel.setVisible(true);
				
			}
			if (arg0.getSource() == gui.playAsAI){
				Client.joinAsAI=true;
				Client.createConnection("sopra.cs.uni-saarland.de");
//				Client.createConnection("sopra.hammacher.name"); //Backup server
				Client.setUpListUpdater();
				Client.refreshGameList();
				Client.changeName(Setting.getName());
				gui.menuPanel.setVisible(false);
				gui.lobbyPanel.setVisible(true);
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
				long focusedGameID= (Long) gui.gameTable.getModel().getValueAt(gui.gameTable.getSelectedRow(), 0);
				try { //set focused Matchinfo as actual Client.matchInfo
					Client.matchInfo= Client.connection.getMatchInfo(focusedGameID);	} catch (IOException e1) {e1.printStackTrace();	}
					
				if(Client.matchInfo!=null 
						&& !Client.matchInfo.isStarted() 
						&& (Client.matchInfo.getNumPlayers()!=Client.matchInfo.getCurrentPlayers().length)
						&& (Client.matchInfo.getCurrentPlayers().length!=0)){
					Client.joinMatch(joinAsObserver);
					gui.lobbyPanel.setVisible(false);
					gui.joinPanel.setVisible(true);
					Client.refreshPlayerList();
				}
			}
			if (arg0.getSource() == gui.back_lobby){
				gui.lobbyPanel.setVisible(false);
				gui.menuPanel.setVisible(true);
				Client.closeConnection();
			}
		//
			
		//Settings
			if (arg0.getSource() == gui.back_Settings){
				Setting.setName(gui.playerName.getText());
				gui.settingsPanel.setVisible(false);
				gui.menuPanel.setVisible(true);
				Client.saveSettings();
			}
			if (arg0.getSource() == gui.fullscreenToggle){
				if(!Setting.isFullscreen()){
					gui.fullscreenToggle.setText("ON");
					gui.resolutionBox.setSelectedIndex(0);
					gui.resolutionBox.setEnabled(false);
					Setting.setDisplayMode(Display.getDisplayMode());
					Setting.setFullscreen(true);
				}
				else{
					gui.fullscreenToggle.setText("OFF");
					gui.resolutionBox.setEnabled(true);
					Setting.setFullscreen(false);
				}
			}
		//
		
		//CreatePanel
			if (arg0.getSource() == gui.createMatch){
				
				int numP=0; 
				try{
					numP =Integer.valueOf(gui.numPlayersField.getText());					
					try {						
						Client.createMatch(gui.gameTitleField.getText(),numP,
								Client.worldRepo, joinAsObserver);} catch (Exception e) {e.printStackTrace();}
					gui.createPanel.setVisible(false);
					gui.joinPanel.setVisible(true);
					Client.refreshPlayerList();
					gui.numWarning.setVisible(false);
				}catch (NumberFormatException e) {
					gui.numWarning.setVisible(true);
				}
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
				readyStatus=false;
				gui.readyToggle.setText("Set Ready");
				gui.readyToggle.setSelected(false);
				try {Client.connection.leaveMatch();} catch (Exception e) {e.printStackTrace();}
				gui.joinPanel.setVisible(false);
				gui.lobbyPanel.setVisible(true);
			}
		//
	}

	
}
