package de.unisaarland.cs.sopra.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

	private GUIFrame gui;
//	private ClientGUI3 gui;
	

	public ButtonListener(GUIFrame guiFrame) {
		this.gui = guiFrame;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (arg0.getSource() == gui.exit){
			System.exit(0);
		}
		if (arg0.getSource() == gui.menuItemSettings){
			gui.lobbyPanel.setVisible(false);
			gui.menuPanel.setVisible(false);
			gui.settingsPanel.setVisible(true);
		}
		if (arg0.getSource() == gui.create){
			
		}
		if (arg0.getSource() == gui.join){
			System.exit(0);
		}
		if (arg0.getSource() == gui.refresh){
			
		}
		if (arg0.getSource() == gui.toggleAI){
			System.exit(0);
		}
		if (arg0.getSource() == gui.back_lobby){
			gui.lobbyPanel.setVisible(false);
			gui.menuPanel.setVisible(true);
		}
		if (arg0.getSource() == gui.back_Settings){
			gui.settingsPanel.setVisible(false);
			gui.menuPanel.setVisible(true);
		}
		if (arg0.getSource() == gui.settings_menu){
			gui.menuPanel.setVisible(false);
			gui.settingsPanel.setVisible(true);
		}
		if (arg0.getSource() == gui.play){
			gui.menuPanel.setVisible(false);
			gui.lobbyPanel.setVisible(true);
		}
		if (arg0.getSource() == gui.refresh){
			
		}
		if (arg0.getSource() == gui.refresh){
			
		}
		if (arg0.getSource() == gui.refresh){
			
		}
		
	}

}
