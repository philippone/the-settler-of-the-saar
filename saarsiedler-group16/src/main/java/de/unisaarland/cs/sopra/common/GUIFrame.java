/*
 * Created by JFormDesigner on Sun Sep 11 14:10:45 CEST 2011
 */

package de.unisaarland.cs.sopra.common;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.table.*;

/**
 * @author Hans Lange der
 */
public class GUIFrame extends JFrame {
	private ButtonListener actLis;
	public long focusedWordID;
	
	
	public GUIFrame(Client client) {
		actLis = new ButtonListener(this, client);
		this.setLocation(150, 30);
//		this.setPreferredSize(new Dimension(900,600));
//		this.pack();
		this.setResizable(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		setActionListner();
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
		worldRepoBox.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				focusedWordID = -1L;
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				focusedWordID = worldRepoBox.getSelectedIndex();
			}
		});
		gameTable.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
//				Client.matchInfo = null;
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
	public void worldRepoChooser(){
		worldRepoBox.setModel(new ComboBoxModel() {
			
			@Override
			public void removeListDataListener(ListDataListener arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public int getSize() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getElementAt(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void addListDataListener(ListDataListener arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setSelectedItem(Object anItem) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object getSelectedItem() {
				// TODO Auto-generated method stub
				return null;
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
		settingsPanel = new JPanel();
		panel10 = new JPanel();
		back_Settings = new JButton();
		menuPanel = new JPanel();
		panel11 = new JPanel();
		play = new JButton();
		playAsAI = new JButton();
		settings_menu = new JButton();
		exit_menu = new JButton();
		panel12 = new JPanel();
		label1 = new JLabel();
		createPanel = new JPanel();
		panel13 = new JPanel();
		label2 = new JLabel();
		gameTitleField = new JTextField();
		label3 = new JLabel();
		numPlayersField = new JTextField();
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
			lobbyPanel.setVisible(false);

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

				//---- playAsAI ----
				playAsAI.setText("Play as AI");
				panel11.add(playAsAI, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
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

		//======== createPanel ========
		{
			createPanel.setVisible(false);
			createPanel.setLayout(new GridBagLayout());
			((GridBagLayout)createPanel.getLayout()).columnWidths = new int[] {730, 0, 0};
			((GridBagLayout)createPanel.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)createPanel.getLayout()).columnWeights = new double[] {0.5, 1.0, 1.0E-4};
			((GridBagLayout)createPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

			//======== panel13 ========
			{
				panel13.setLayout(new GridBagLayout());
				((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {168, 145, 237, 13, 0};
				((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {175, 0, 0, 0, 32, 0, 227, 0, 23, 0, 0};
				((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.36, 1.0E-4};
				((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//---- label2 ----
				label2.setText("Gametitle: ");
				panel13.add(label2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
				panel13.add(gameTitleField, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label3 ----
				label3.setText("Number of Players:");
				panel13.add(label3, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
				panel13.add(numPlayersField, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

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
		createPanel.setBounds(0, 0, 1000, 675);

		//======== joinPanel ========
		{
			joinPanel.setLayout(new GridBagLayout());
			((GridBagLayout)joinPanel.getLayout()).columnWidths = new int[] {999, 0};
			((GridBagLayout)joinPanel.getLayout()).rowHeights = new int[] {597, 91, 0};
			((GridBagLayout)joinPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
			((GridBagLayout)joinPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

			//======== panel14 ========
			{
				panel14.setLayout(new GridBagLayout());
				((GridBagLayout)panel14.getLayout()).columnWidths = new int[] {752, 245, 0};
				((GridBagLayout)panel14.getLayout()).rowHeights = new int[] {589, 0};
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
				((GridBagLayout)panel15.getLayout()).columnWidths = new int[] {0, 5, 75, 5, 0, 5, 75, 5, 0, 5, 75, 5, 195, 5, 75, 5, 101, 5, 75, 5, 0, 0};
				((GridBagLayout)panel15.getLayout()).rowHeights = new int[] {0, 5, 0, 5, 0, 0};
				((GridBagLayout)panel15.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
				((GridBagLayout)panel15.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//---- readyToggle ----
				readyToggle.setText("Set Ready!");
				panel15.add(readyToggle, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- back_join ----
				back_join.setText("back to Lobby");
				panel15.add(back_join, new GridBagConstraints(10, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			joinPanel.add(panel15, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(joinPanel);
		joinPanel.setBounds(0, 0, 1000, 665);

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
	public JPanel settingsPanel;
	private JPanel panel10;
	public JButton back_Settings;
	public JPanel menuPanel;
	private JPanel panel11;
	public JButton play;
	public JButton playAsAI;
	public JButton settings_menu;
	public JButton exit_menu;
	private JPanel panel12;
	private JLabel label1;
	public JPanel createPanel;
	private JPanel panel13;
	private JLabel label2;
	public JTextField gameTitleField;
	private JLabel label3;
	public JTextField numPlayersField;
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
