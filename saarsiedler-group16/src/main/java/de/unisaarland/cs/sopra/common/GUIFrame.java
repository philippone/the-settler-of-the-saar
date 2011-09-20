/*
 * Created by JFormDesigner on Sun Sep 11 14:10:45 CEST 2011
 */

package de.unisaarland.cs.sopra.common;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import de.unisaarland.cs.st.saarsiedler.comm.QualifikationMaps;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

/**
 * @author Hans Lange der
 */
public class GUIFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ButtonListener actLis;
	public long focusedWordID;
	public final static PlayerColors[] pc= new PlayerColors[]{ PlayerColors.BLUE,PlayerColors.YELLOW,PlayerColors.ORANGE,PlayerColors.BROWN,PlayerColors.WHITE,PlayerColors.PURPLE};
	public final static String[] farben = new String[]{"BLUE","YELLOW","ORANGE","BROWN","WHITE","PURPLE"};
	public final static DisplayMode[] displaymodes = new DisplayMode[]{new DisplayMode(Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight()-100), new DisplayMode(1024, 530), new DisplayMode(800, 600)};
	public final static String[] dmodes = new String[]{"AUTO","1024x530","800x600"};
	public  static WorldRepresentation[] worldRepos; //= new WorldRepresentation[]{WorldRepresentation.getDefault(), QualifikationMaps.getMap1(), QualifikationMaps.getMap2(), QualifikationMaps.getMap3()};
	public final static String[] worldNames = new String []{"Default", "Quali 1", "Quali 2", "Quali 3"};
	
	public GUIFrame() {
		actLis = new ButtonListener(this);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		setActionListner();
		playerColorChooser();
		resolutionChooser();
		worldRepoChooser();

	}
	private void setActionListner() {
		menuItemSettings.addActionListener(actLis);
		exit.addActionListener(actLis);
		create.addActionListener(actLis);
		join.addActionListener(actLis);
		back_lobby.addActionListener(actLis);
		back_Settings.addActionListener(actLis);
		play.addActionListener(actLis);
		settings_menu.addActionListener(actLis);
		exit_menu.addActionListener(actLis);
		observerToggle.addActionListener(actLis);
		back_Create.addActionListener(actLis);
		createMatch.addActionListener(actLis);
		playAsAI.addActionListener(actLis);
		readyToggle.addActionListener(actLis);
		back_join.addActionListener(actLis);
		fullscreenToggle.addActionListener(actLis);
		gameTable.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				//TODO evtl noch was zu tun
				System.out.println("DESELECTED GameList");
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				int row = gameTable.getSelectedRow();
				long focusedGameID= (Long)gameTable.getModel().getValueAt(row, 0);
				try { //set focused Matchinfo as actual Client.matchInfo
					Client.matchInfo= Client.connection.getMatchInfo(focusedGameID);	} catch (IOException e1) {e1.printStackTrace();	}
					System.out.println("row: "+row+"listID: "+focusedGameID+ "matchinfo: "+ Client.matchInfo.getId());
			}
		});
	}
//	public void gameListActivator(){
//		gameTable.addInputMethodListener(l)
//	}
	
	public void playerColorChooser(){
		for ( String s : farben)
		      playerColorBox.addItem( s );
		playerColorBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Setting.setPlayerColor(pc[playerColorBox.getSelectedIndex()]);
			}
		});
	}

	
	public void worldRepoChooser(){
		try {
			worldRepos= new WorldRepresentation[]{WorldRepresentation.getDefault(), QualifikationMaps.getMap1(), QualifikationMaps.getMap2(), QualifikationMaps.getMap3()};
		} catch (IOException e1) {e1.printStackTrace();
		}
		for ( String s : worldNames)
		      worldRepoBox.addItem( s );
		Client.worldRepo = worldRepos[0]; //fix ,that at begin no item selected
		worldRepoBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Client.worldRepo=worldRepos[worldRepoBox.getSelectedIndex()];
			}
		});
	}
	
	public void resolutionChooser(){
		for ( String s : dmodes)
		      resolutionBox.addItem( s );
		resolutionBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Setting.setDisplayMode(displaymodes[resolutionBox.getSelectedIndex()]);
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
		menuPanel = new JPanel();
		panel11 = new JPanel();
		play = new JButton();
		playAsAI = new JButton();
		settings_menu = new JButton();
		exit_menu = new JButton();
		panel12 = new JPanel();
		label1 = new JLabel();
		lobbyPanel = new JPanel();
		panel8 = new JPanel();
		scrollPane4 = new JScrollPane();
		gameTable = new JTable();
		scrollPane1 = new JScrollPane();
		matchTable = new JTable();
		panel9 = new JPanel();
		create = new JButton();
		join = new JButton();
		back_lobby = new JButton();
		settingsPanel = new JPanel();
		panel10 = new JPanel();
		label6 = new JLabel();
		resolutionBox = new JComboBox();
		label7 = new JLabel();
		fullscreenToggle = new JToggleButton();
		label8 = new JLabel();
		playerColorBox = new JComboBox();
		label9 = new JLabel();
		playerName = new JTextField();
		back_Settings = new JButton();
		createPanel = new JPanel();
		panel13 = new JPanel();
		label2 = new JLabel();
		gameTitleField = new JTextField();
		label3 = new JLabel();
		numPlayersField = new JTextField();
		numWarning = new JLabel();
		label4 = new JLabel();
		worldRepoBox = new JComboBox();
		label5 = new JLabel();
		observerToggle = new JToggleButton();
		createMatch = new JButton();
		back_Create = new JButton();
		joinPanel = new JPanel();
		panel14 = new JPanel();
		scrollPane5 = new JScrollPane();
		playerTable = new JTable();
		panel15 = new JPanel();
		readyToggle = new JToggleButton();
		back_join = new JButton();

		//======== this ========
		setVisible(true);
		setResizable(false);
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

		//======== menuPanel ========
		{

			// JFormDesigner evaluation mark
			menuPanel.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), menuPanel.getBorder())); menuPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			menuPanel.setLayout(new BorderLayout());

			//======== panel11 ========
			{
				panel11.setLayout(new GridBagLayout());
				((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
				((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 30, 0, 0};
				((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {0.36, 0.28, 0.36, 1.0E-4};
				((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//---- play ----
				play.setText("Play");
				panel11.add(play, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- playAsAI ----
				playAsAI.setText("Play as AI");
				panel11.add(playAsAI, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- settings_menu ----
				settings_menu.setText("Settings");
				panel11.add(settings_menu, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- exit_menu ----
				exit_menu.setText("Quit Game");
				panel11.add(exit_menu, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
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
		menuPanel.setBounds(0, 0, menuPanel.getPreferredSize().width, 445);

		//======== lobbyPanel ========
		{
			lobbyPanel.setVisible(false);
			lobbyPanel.setLayout(new GridBagLayout());
			((GridBagLayout)lobbyPanel.getLayout()).columnWidths = new int[] {999, 0};
			((GridBagLayout)lobbyPanel.getLayout()).rowHeights = new int[] {338, 80, 0};
			((GridBagLayout)lobbyPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
			((GridBagLayout)lobbyPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

			//======== panel8 ========
			{
				panel8.setLayout(new GridBagLayout());
				((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {752, 245, 0};
				((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {339, 0};
				((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

				//======== scrollPane4 ========
				{

					//---- gameTable ----
					gameTable.setAutoCreateRowSorter(true);
					gameTable.setFont(gameTable.getFont().deriveFont(Font.BOLD, gameTable.getFont().getSize() + 2f));
					gameTable.setModel(new DefaultTableModel(
						new Object[][] {
							{null, null, null, null, null},
							{null, null, null, null, null},
							{null, null, null, null, null},
						},
						new String[] {
							"MatchID", "Name", "Players", "WorldID", null
						}
					));
					{
						TableColumnModel cm = gameTable.getColumnModel();
						cm.getColumn(0).setMinWidth(50);
						cm.getColumn(0).setMaxWidth(60);
						cm.getColumn(1).setMinWidth(220);
						cm.getColumn(2).setMinWidth(60);
						cm.getColumn(2).setMaxWidth(60);
						cm.getColumn(3).setMinWidth(50);
						cm.getColumn(3).setMaxWidth(50);
					}
					gameTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
					gameTable.setDoubleBuffered(true);
					scrollPane4.setViewportView(gameTable);
				}
				panel8.add(scrollPane4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//======== scrollPane1 ========
				{
					scrollPane1.setViewportView(matchTable);
				}
				panel8.add(scrollPane1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			lobbyPanel.add(panel8, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//======== panel9 ========
			{
				panel9.setLayout(new GridBagLayout());
				((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {11, 5, 75, 5, 0, 5, 75, 5, 0, 5, 75, 5, 195, 5, 75, 5, 101, 5, 75, 5, 0, 0};
				((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {14, 5, 0, 5, 22, 0};
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
			}
			lobbyPanel.add(panel9, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(lobbyPanel);
		lobbyPanel.setBounds(5, 0, 1000, 425);

		//======== settingsPanel ========
		{
			settingsPanel.setVisible(false);
			settingsPanel.setLayout(new GridBagLayout());
			((GridBagLayout)settingsPanel.getLayout()).columnWidths = new int[] {0, 0, 106, 256, 0};
			((GridBagLayout)settingsPanel.getLayout()).rowHeights = new int[] {492, 0};
			((GridBagLayout)settingsPanel.getLayout()).columnWeights = new double[] {0.25, 0.5, 0.25, 1.0, 1.0E-4};
			((GridBagLayout)settingsPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

			//======== panel10 ========
			{
				panel10.setLayout(new GridBagLayout());
				((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {262, 115, 180, 250, 0};
				((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {114, 0, 0, 0, 0, 0, 0, 0, 74, 0, 0};
				((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.0, 0.36, 0.28, 0.36, 1.0E-4};
				((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//---- label6 ----
				label6.setText("Resolution: ");
				panel10.add(label6, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
				panel10.add(resolutionBox, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label7 ----
				label7.setText("Fullscreen: ");
				panel10.add(label7, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- fullscreenToggle ----
				fullscreenToggle.setText("OFF");
				panel10.add(fullscreenToggle, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label8 ----
				label8.setText("Playercolor:");
				panel10.add(label8, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
				panel10.add(playerColorBox, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label9 ----
				label9.setText("Playername: ");
				panel10.add(label9, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- playerName ----
				playerName.setText("Gruppe16");
				panel10.add(playerName, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- back_Settings ----
				back_Settings.setText("back to Menu");
				panel10.add(back_Settings, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			settingsPanel.add(panel10, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(settingsPanel);
		settingsPanel.setBounds(0, 0, 995, 400);

		//======== createPanel ========
		{
			createPanel.setVisible(false);
			createPanel.setLayout(new GridBagLayout());
			((GridBagLayout)createPanel.getLayout()).columnWidths = new int[] {730, 0, 0};
			((GridBagLayout)createPanel.getLayout()).rowHeights = new int[] {420, 0};
			((GridBagLayout)createPanel.getLayout()).columnWeights = new double[] {0.5, 1.0, 1.0E-4};
			((GridBagLayout)createPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

			//======== panel13 ========
			{
				panel13.setLayout(new GridBagLayout());
				((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {168, 145, 237, 13, 0};
				((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {68, 0, 0, 0, 32, 0, 67, 0, 23, 0, 0};
				((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.36, 1.0E-4};
				((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//---- label2 ----
				label2.setText("Gametitle: ");
				panel13.add(label2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- gameTitleField ----
				gameTitleField.setText("private Grp16 noname");
				panel13.add(gameTitleField, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label3 ----
				label3.setText("Number of Players:");
				panel13.add(label3, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- numPlayersField ----
				numPlayersField.setText("1");
				panel13.add(numPlayersField, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- numWarning ----
				numWarning.setText("-only numbers allowed!");
				numWarning.setForeground(Color.red);
				numWarning.setVisible(false);
				panel13.add(numWarning, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- label4 ----
				label4.setText("World: ");
				panel13.add(label4, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
				panel13.add(worldRepoBox, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label5 ----
				label5.setText("Join as: ");
				panel13.add(label5, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- observerToggle ----
				observerToggle.setText("Player");
				panel13.add(observerToggle, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- createMatch ----
				createMatch.setText("create Match");
				panel13.add(createMatch, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- back_Create ----
				back_Create.setText("back to Lobby");
				panel13.add(back_Create, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			createPanel.add(panel13, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(createPanel);
		createPanel.setBounds(0, 0, 995, 425);

		//======== joinPanel ========
		{
			joinPanel.setVisible(false);
			joinPanel.setLayout(new GridBagLayout());
			((GridBagLayout)joinPanel.getLayout()).columnWidths = new int[] {999, 0};
			((GridBagLayout)joinPanel.getLayout()).rowHeights = new int[] {337, 76, 0};
			((GridBagLayout)joinPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
			((GridBagLayout)joinPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

			//======== panel14 ========
			{
				panel14.setLayout(new GridBagLayout());
				((GridBagLayout)panel14.getLayout()).columnWidths = new int[] {752, 245, 0};
				((GridBagLayout)panel14.getLayout()).rowHeights = new int[] {330, 0};
				((GridBagLayout)panel14.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel14.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

				//======== scrollPane5 ========
				{

					//---- playerTable ----
					playerTable.setAutoCreateRowSorter(true);
					playerTable.setFont(playerTable.getFont().deriveFont(Font.BOLD, playerTable.getFont().getSize() + 2f));
					playerTable.setModel(new DefaultTableModel(
						new Object[][] {
							{null, null, null, null},
							{null, null, null, null},
							{null, null, null, null},
						},
						new String[] {
							"MatchID", "Name", "Players", "WorldID"
						}
					));
					playerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollPane5.setViewportView(playerTable);
				}
				panel14.add(scrollPane5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			joinPanel.add(panel14, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//======== panel15 ========
			{
				panel15.setLayout(new GridBagLayout());
				((GridBagLayout)panel15.getLayout()).columnWidths = new int[] {15, 5, 75, 5, 33, 5, 75, 5, 0, 0, 5, 75, 5, 195, 5, 75, 5, 101, 5, 75, 5, 0, 0};
				((GridBagLayout)panel15.getLayout()).rowHeights = new int[] {17, 5, 0, 5, 0, 0};
				((GridBagLayout)panel15.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel15.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//---- readyToggle ----
				readyToggle.setText("Set Ready!");
				panel15.add(readyToggle, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- back_join ----
				back_join.setText("back to Lobby");
				panel15.add(back_join, new GridBagConstraints(6, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			joinPanel.add(panel15, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(joinPanel);
		joinPanel.setBounds(0, 0, 995, 415);

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
		setSize(1020, 475);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Hans Lange der
	private JMenuBar menuBar1;
	private JMenu menu1;
	JMenuItem menuItemSettings;
	JMenuItem exit;
	public JPanel menuPanel;
	private JPanel panel11;
	public JButton play;
	public JButton playAsAI;
	public JButton settings_menu;
	public JButton exit_menu;
	private JPanel panel12;
	private JLabel label1;
	public JPanel lobbyPanel;
	private JPanel panel8;
	private JScrollPane scrollPane4;
	public JTable gameTable;
	private JScrollPane scrollPane1;
	public JTable matchTable;
	private JPanel panel9;
	JButton create;
	JButton join;
	JButton back_lobby;
	public JPanel settingsPanel;
	private JPanel panel10;
	private JLabel label6;
	public JComboBox resolutionBox;
	private JLabel label7;
	public JToggleButton fullscreenToggle;
	private JLabel label8;
	public JComboBox playerColorBox;
	private JLabel label9;
	public JTextField playerName;
	public JButton back_Settings;
	public JPanel createPanel;
	private JPanel panel13;
	private JLabel label2;
	public JTextField gameTitleField;
	private JLabel label3;
	public JTextField numPlayersField;
	public JLabel numWarning;
	private JLabel label4;
	public JComboBox worldRepoBox;
	private JLabel label5;
	public JToggleButton observerToggle;
	public JButton createMatch;
	public JButton back_Create;
	public JPanel joinPanel;
	private JPanel panel14;
	private JScrollPane scrollPane5;
	public JTable playerTable;
	private JPanel panel15;
	public JToggleButton readyToggle;
	JButton back_join;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
}
