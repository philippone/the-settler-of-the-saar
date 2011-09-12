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

import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
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

			lobbyPanel.setLayout(new GridBagLayout());
			((GridBagLayout)lobbyPanel.getLayout()).columnWidths = new int[] {999, 0};
			((GridBagLayout)lobbyPanel.getLayout()).rowHeights = new int[] {597, 91, 0};
			((GridBagLayout)lobbyPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
			((GridBagLayout)lobbyPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

			//======== panel8 ========
			{
				panel8.setLayout(new GridBagLayout());
				((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {752, 245, 0};
				((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {589, 0};
				((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

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
				panel8.add(scrollPane4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			lobbyPanel.add(panel8, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//======== panel9 ========
			{
				panel9.setLayout(new GridBagLayout());
				((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 5, 75, 5, 0, 5, 75, 5, 0, 5, 75, 5, 195, 5, 75, 5, 101, 5, 75, 5, 0, 0};
				((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 5, 0, 5, 0, 0};
				((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//---- create ----
				create.setText("create");
				panel9.add(create, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- join ----
				join.setText("join");
				panel9.add(join, new GridBagConstraints(6, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- back_lobby ----
				back_lobby.setText("back");
				panel9.add(back_lobby, new GridBagConstraints(10, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- refresh ----
				refresh.setText("refresh");
				panel9.add(refresh, new GridBagConstraints(14, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- toggleAI ----
				toggleAI.setText("Join as AI");
				panel9.add(toggleAI, new GridBagConstraints(18, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			lobbyPanel.add(panel9, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(lobbyPanel);
		lobbyPanel.setBounds(0, 0, 1000, 665);

		//======== settingsPanel ========
		{
			settingsPanel.setVisible(false);
			settingsPanel.setLayout(new GridBagLayout());
			((GridBagLayout)settingsPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
			((GridBagLayout)settingsPanel.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)settingsPanel.getLayout()).columnWeights = new double[] {0.25, 0.5, 0.25, 1.0, 1.0E-4};
			((GridBagLayout)settingsPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

			//======== panel10 ========
			{
				panel10.setLayout(new GridBagLayout());
				((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
				((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {157, 0, 0, 0, 0, 0, 0, 0, 338, 0, 0};
				((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.36, 0.28, 0.36, 1.0E-4};
				((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//---- back_Settings ----
				back_Settings.setText("back to Menu");
				panel10.add(back_Settings, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			settingsPanel.add(panel10, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(settingsPanel);
		settingsPanel.setBounds(0, 0, 1000, 675);

		//======== menuPanel ========
		{
			menuPanel.setVisible(false);
			menuPanel.setLayout(new BorderLayout());

			//======== panel11 ========
			{
				panel11.setLayout(new GridBagLayout());
				((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
				((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 15, 0, 0, 0, 0, 0, 0, 0, 62, 0, 0};
				((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {0.36, 0.28, 0.36, 1.0E-4};
				((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//---- play ----
				play.setText("Play");
				panel11.add(play, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- settings_menu ----
				settings_menu.setText("Settings");
				panel11.add(settings_menu, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- exit_menu ----
				exit_menu.setText("Quit Game");
				panel11.add(exit_menu, new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
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
