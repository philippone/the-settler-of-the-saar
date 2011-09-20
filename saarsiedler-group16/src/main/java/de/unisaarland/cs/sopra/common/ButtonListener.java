package de.unisaarland.cs.sopra.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import org.lwjgl.opengl.Display;

import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

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
				Client.loadSettings();
				gui.lobbyPanel.setVisible(false);
				gui.menuPanel.setVisible(false);
				gui.createPanel.setVisible(false);
				gui.joinPanel.setVisible(false);
				gui.settingsPanel.setVisible(true);
//				Client.closeConnection();
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
				Client.refreshGameList();
				gui.menuPanel.setVisible(false);
				gui.lobbyPanel.setVisible(true);
				Client.setUpListUpdater();
				Client.changeName(Setting.getName());
				
			}
			if (arg0.getSource() == gui.playAsAI){
				Client.joinAsAI=true;
				Client.createConnection("sopra.cs.uni-saarland.de");
				Client.refreshGameList();
				gui.menuPanel.setVisible(false);
				gui.lobbyPanel.setVisible(true);
				Client.setUpListUpdater();
				Client.changeName(Setting.getName());
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
				if(Client.matchInfo!=null 
						&& !Client.matchInfo.isStarted() 
						&& !(Client.matchInfo.getNumPlayers()==Client.matchInfo.getCurrentPlayers().length)){
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
					Setting.setDisplayMode(GUIFrame.displaymodes[0]);
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
				try {Client.connection.leaveMatch();} catch (Exception e) {e.printStackTrace();}
				gui.joinPanel.setVisible(false);
				gui.lobbyPanel.setVisible(true);
			}
		//
	}

	
}
