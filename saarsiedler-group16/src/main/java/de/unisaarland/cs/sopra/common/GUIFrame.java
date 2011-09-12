/*
 * Created by JFormDesigner on Sun Sep 11 14:10:45 CEST 2011
 */

package de.unisaarland.cs.sopra.common;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.table.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import info.clearthought.layout.*;
import java.util.List;

/**
 * @author Hans Lange der
 */
public class GUIFrame extends JFrame {
	private ButtonListener actLis;
	private Connection connect;
	private Long focusedGameID;
	
	public GUIFrame(Connection connection) {
		this.connect =connection;
		actLis = new ButtonListener(this, connect);
		this.setLocation(150, 30);
//		this.setPreferredSize(new Dimension(900,600));
//		this.pack();
		this.setResizable(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		setActionListner();
		refreshGameList();
	}

	private void refreshGameList(){
//		gameTable.getCellEditor().
		List<MatchInformation> matchList=null;
		try {
			matchList =connect.listMatches();		} catch (IOException e1) {			e1.printStackTrace();		}
		try {
			connect.registerMatchListUpdater(new GameListUpdater(gameTable));		}catch(IOException e){throw new IllegalStateException("iwas mit Matchlistupdater faul!!!");}
			
			gameTable.setModel(new DefaultTableModel(
					parseMatchList(matchList),
					new String[] {"MatchID", "Name", "Players", "WorldID"	}));
	}
	public static Object[][] parseMatchList(List<MatchInformation> matchList){
		if (matchList == null)	throw new IllegalArgumentException("matchList is null");
		Object[][] ret = new Object[matchList.size()][4];
		int i =0;
		for (MatchInformation m : matchList) {
			ret[i][0]=m.getId();
			ret[i][1]=m.getTitle();
			ret[i][2]=m.getCurrentPlayers().length+"/"+m.getNumPlayers();
			ret[i][3]=m.getWorldId();
			i++;
		}
		return ret;
	}
	private void setActionListner() {
		menuItemSettings.addActionListener(actLis);
		exit.addActionListener(actLis);
		create.addActionListener(actLis);
		join.addActionListener(actLis);
		back_lobby.addActionListener(actLis);
		refresh.addActionListener(actLis);
		toggleAI.addActionListener(actLis);
		back_Settings.addActionListener(actLis);
		play.addActionListener(actLis);
		settings_menu.addActionListener(actLis);
		exit_menu.addActionListener(actLis);
		gameTable.addFocusListener(new FocusListener() {
			
			

			@Override
			public void focusLost(FocusEvent e) {
				focusedGameID =-1L;
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				int colm = gameTable.getSelectedColumn();
				focusedGameID= (Long)gameTable.getModel().getValueAt(colm, 0);
				System.out.println("Table focused");
			}
		});
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Hans Lange der
		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		menuItemSettings = new JMenuItem();
		exit = new JMenuItem();
		lobbyPanel = new JPanel();
		panel8 = new JPanel();
		scrollPane4 = new JScrollPane();
		gameTable = new JTable();
		panel9 = new JPanel();
		create = new JButton();
		join = new JButton();
		back_lobby = new JButton();
		refresh = new JButton();
		toggleAI = new JToggleButton();
		settingsPanel = new JPanel();
		panel10 = new JPanel();
		back_Settings = new JButton();
		menuPanel = new JPanel();
		panel11 = new JPanel();
		play = new JButton();
		settings_menu = new JButton();
		exit_menu = new JButton();
		panel12 = new JPanel();
		label1 = new JLabel();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("Spiel");

				//---- menuItemSettings ----
				menuItemSettings.setText("Einstellungen");
				menu1.add(menuItemSettings);

				//---- exit ----
				exit.setText("Beenden");
				menu1.add(exit);
			}
			menuBar1.add(menu1);
		}
		setJMenuBar(menuBar1);

		//======== lobbyPanel ========
		{

			// JFormDesigner evaluation mark
			lobbyPanel.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), lobbyPanel.getBorder())); lobbyPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			lobbyPanel.setLayout(new TableLayout(new double[][] {
				{999},
				{592, 91}}));
			((TableLayout)lobbyPanel.getLayout()).setHGap(5);
			((TableLayout)lobbyPanel.getLayout()).setVGap(5);

			//======== panel8 ========
			{
				panel8.setLayout(new TableLayout(new double[][] {
					{747, 245},
					{589}}));
				((TableLayout)panel8.getLayout()).setHGap(5);
				((TableLayout)panel8.getLayout()).setVGap(5);

				//======== scrollPane4 ========
				{

					//---- gameTable ----
					gameTable.setAutoCreateRowSorter(true);
					gameTable.setFont(gameTable.getFont().deriveFont(Font.BOLD, gameTable.getFont().getSize() + 2f));
					gameTable.setModel(new DefaultTableModel(
						new Object[][] {
							{null, null, null, null},
							{null, null, null, null},
							{null, null, null, null},
						},
						new String[] {
							"MatchID", "Name", "Players", "WorldID"
						}
					));
					gameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollPane4.setViewportView(gameTable);
				}
				panel8.add(scrollPane4, new TableLayoutConstraints(0, 0, 0, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
			}
			lobbyPanel.add(panel8, new TableLayoutConstraints(0, 0, 0, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

			//======== panel9 ========
			{
				panel9.setLayout(new FormLayout(
					"default, $lcgap, $button, $lcgap, default, $lcgap, $button, $lcgap, default, $lcgap, $button, $lcgap, 130dlu, $lcgap, $button, $lcgap, 67dlu, $lcgap, $button, $lcgap, default",
					"default, $lgap, fill:default, $lgap, default"));

				//---- create ----
				create.setText("create");
				panel9.add(create, CC.xy(3, 3));

				//---- join ----
				join.setText("join");
				panel9.add(join, CC.xy(7, 3));

				//---- back_lobby ----
				back_lobby.setText("back");
				panel9.add(back_lobby, CC.xy(11, 3));

				//---- refresh ----
				refresh.setText("refresh");
				panel9.add(refresh, CC.xy(15, 3));

				//---- toggleAI ----
				toggleAI.setText("Join as AI");
				panel9.add(toggleAI, CC.xy(19, 3));
			}
			lobbyPanel.add(panel9, new TableLayoutConstraints(0, 1, 0, 1, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
		}
		contentPane.add(lobbyPanel);
		lobbyPanel.setBounds(0, 0, 1000, 665);

		//======== settingsPanel ========
		{
			settingsPanel.setVisible(false);
			settingsPanel.setLayout(new TableLayout(new double[][] {
				{0.25, 0.5, 0.25, TableLayout.FILL},
				{TableLayout.FILL}}));

			//======== panel10 ========
			{
				panel10.setLayout(new TableLayout(new double[][] {
					{0.36, 0.28, 0.36},
					{152, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, 333, TableLayout.PREFERRED}}));
				((TableLayout)panel10.getLayout()).setHGap(5);
				((TableLayout)panel10.getLayout()).setVGap(5);

				//---- back_Settings ----
				back_Settings.setText("back to Menu");
				panel10.add(back_Settings, new TableLayoutConstraints(1, 9, 1, 9, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
			}
			settingsPanel.add(panel10, new TableLayoutConstraints(1, 0, 1, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
		}
		contentPane.add(settingsPanel);
		settingsPanel.setBounds(0, 0, 1000, 675);

		//======== menuPanel ========
		{
			menuPanel.setVisible(false);
			menuPanel.setLayout(new BorderLayout());

			//======== panel11 ========
			{
				panel11.setLayout(new TableLayout(new double[][] {
					{0.36, 0.28, 0.36},
					{TableLayout.PREFERRED, 10, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, 57, TableLayout.PREFERRED}}));
				((TableLayout)panel11.getLayout()).setHGap(5);
				((TableLayout)panel11.getLayout()).setVGap(5);

				//---- play ----
				play.setText("Play");
				panel11.add(play, new TableLayoutConstraints(1, 5, 1, 5, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

				//---- settings_menu ----
				settings_menu.setText("Settings");
				panel11.add(settings_menu, new TableLayoutConstraints(1, 7, 1, 7, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

				//---- exit_menu ----
				exit_menu.setText("Quit Game");
				panel11.add(exit_menu, new TableLayoutConstraints(1, 10, 1, 10, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
			}
			menuPanel.add(panel11, BorderLayout.CENTER);

			//======== panel12 ========
			{
				panel12.setLayout(null);

				//---- label1 ----
				label1.setIcon(new ImageIcon(getClass().getResource("/Textures/ClientGui/Saarlogo.png")));
				panel12.add(label1);
				label1.setBounds(0, 0, 1000, 175);

				{ // compute preferred size
					Dimension preferredSize = new Dimension();
					for(int i = 0; i < panel12.getComponentCount(); i++) {
						Rectangle bounds = panel12.getComponent(i).getBounds();
						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
					}
					Insets insets = panel12.getInsets();
					preferredSize.width += insets.right;
					preferredSize.height += insets.bottom;
					panel12.setMinimumSize(preferredSize);
					panel12.setPreferredSize(preferredSize);
				}
			}
			menuPanel.add(panel12, BorderLayout.NORTH);
		}
		contentPane.add(menuPanel);
		menuPanel.setBounds(0, 0, 1000, 665);

		{ // compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++) {
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Hans Lange der
	private JMenuBar menuBar1;
	private JMenu menu1;
	JMenuItem menuItemSettings;
	JMenuItem exit;
	public JPanel lobbyPanel;
	private JPanel panel8;
	private JScrollPane scrollPane4;
	public JTable gameTable;
	private JPanel panel9;
	JButton create;
	JButton join;
	JButton back_lobby;
	JButton refresh;
	JToggleButton toggleAI;
	public JPanel settingsPanel;
	private JPanel panel10;
	public JButton back_Settings;
	public JPanel menuPanel;
	private JPanel panel11;
	public JButton play;
	public JButton settings_menu;
	public JButton exit_menu;
	private JPanel panel12;
	private JLabel label1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
