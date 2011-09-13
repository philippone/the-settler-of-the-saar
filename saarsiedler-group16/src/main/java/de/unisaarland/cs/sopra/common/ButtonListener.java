package de.unisaarland.cs.sopra.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import de.unisaarland.cs.st.saarsiedler.comm.Connection;

public class ButtonListener implements ActionListener {

	private GUIFrame gui;
	private Connection connect;
//	private ClientGUI3 gui;
	

	public ButtonListener(GUIFrame guiFrame, Connection connect) {
		this.gui = guiFrame;
		this.connect=connect;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Menu:
			if (arg0.getSource() == gui.exit){
				System.exit(0);
			}
			if (arg0.getSource() == gui.menuItemSettings){
				gui.lobbyPanel.setVisible(false);
				gui.menuPanel.setVisible(false);
				gui.settingsPanel.setVisible(true);
			}
		//
		
		//StartMenu	
			if (arg0.getSource() == gui.settings_menu){
				gui.menuPanel.setVisible(false);
				gui.settingsPanel.setVisible(true);
			}
			if (arg0.getSource() == gui.play){
				//TODO as AI joinen Connection-init hierher verlagern!!
				gui.refreshGameList();
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
			}
			if (arg0.getSource() == gui.back_lobby){
				gui.lobbyPanel.setVisible(false);
				gui.menuPanel.setVisible(true);
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
				
			}
			if (arg0.getSource() == gui.observerToggle){
				gui.joinAsObserver=!gui.joinAsObserver;
				if(gui.joinAsObserver)
					gui.observerToggle.setText("Observer");
				else
					gui.observerToggle.setText("Player");
			}
			if (arg0.getSource() == gui.back_Create){
				try {connect.leaveMatch();} catch (Exception e) {e.printStackTrace();}
				gui.createPanel.setVisible(false);
				gui.lobbyPanel.setVisible(true);
			}
		//
		
		
		
	}
	
	
}
