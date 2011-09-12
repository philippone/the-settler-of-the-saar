package de.unisaarland.cs.sopra.common;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.PlayerInformation;

public class ClientGUI extends JFrame{
	public ClientGUI(){
		this.setLocation(150, 30);
	}
	
	private static void createMenuBar(ClientGUI clientGui){
		JMenuBar menubar = new JMenuBar();
		JMenu spielmenu = new JMenu("Spiel");
		JMenuItem beenden = new JMenuItem("Catan Beenden");
		ActionEvent quit = new ActionEvent(beenden, EXIT_ON_CLOSE, "blaClose" );
//		beenden.
		spielmenu.add(beenden);
		menubar.add(spielmenu);
		clientGui.setJMenuBar(menubar);
	}
	
	public static void main(String[] args) {
		ClientGUI clientGui = new ClientGUI();
		clientGui.setTitle("Siedler von der Saar");
		clientGui.setPreferredSize(new Dimension(900,600));
		clientGui.pack();
//		clientGui.setSize(1000, 700);
		clientGui.setResizable(true);
		clientGui.setVisible(true);
		clientGui.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		createMenuBar(clientGui);
		
		Container cp =clientGui.getContentPane();
		cp.setLayout(new BorderLayout());
		
		JPanel buttonpanel = createButtonPanel();
//		buttonpanel.
		cp.add(buttonpanel, BorderLayout.SOUTH);
		
	}
	

	private static JPanel createButtonPanel() {
		JPanel bp = new JPanel();
		JButton erstellen = new JButton("Spiel erstellen");
		erstellen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		JButton beitreten = new JButton("Spiel beitreten");
		JButton back = new JButton("Zurück");
		bp.add(erstellen);
		bp.add(beitreten);
		bp.add(back);
		
		return bp;
	}

	public void drawLobby(List<MatchInformation> matches) {
		throw new UnsupportedOperationException();
	}
	
	public void previewMatch(MatchInformation matchInformation) {
		throw new UnsupportedOperationException();
	}
	
	public void updateStatus(PlayerInformation playerInformation) {
		throw new UnsupportedOperationException();
	}
	
	public void drawSetting(Setting setting) {
		throw new UnsupportedOperationException();
	}
	
	public void drawCreate() {
		throw new UnsupportedOperationException();
	}
	
	public void drawJoin(MatchInformation matchInformation) {
		throw new UnsupportedOperationException();
	}
	
}
