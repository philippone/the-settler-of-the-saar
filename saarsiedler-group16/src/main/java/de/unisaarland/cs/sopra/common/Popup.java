/*
 * Created by JFormDesigner on Mon Sep 19 12:12:25 CEST 2011
 */

package de.unisaarland.cs.sopra.common;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

import de.unisaarland.cs.sopra.common.model.ResourcePackage;

/**
 * @author Hans Lange der
 */
public class Popup extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int n;
	public Popup() {
		initComponents();
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		topLabel2.setText("Make a valid offer!");
		topLabel3.setText("Accept Trade-Offer?");
		okReturn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int a1 = Integer.valueOf(textField1.getText());
				int a2 = Integer.valueOf(textField2.getText());
				int a3 = Integer.valueOf(textField3.getText());
				int a4 = Integer.valueOf(textField4.getText());
				int a5 = Integer.valueOf(textField5.getText());
				int r1 = Integer.valueOf(lumberMax.getText());
				int r2 = Integer.valueOf(brickMax.getText());
				int r3 = Integer.valueOf(woolMax.getText());
				int r4 = Integer.valueOf(grainMax.getText());
				int r5 = Integer.valueOf(oreMax.getText());
				if((a1+a2+a3+a4+a5)!=(n/2) || a1 > r1 || a2 > r2 || a3 > r3 | a4 > r4 || a5 > r5 ){
					warning.setText("invalid Selection");	
					if(r1<a1 || r2<a2 || r3<a3 || r4<a4 || r5<a5){
						warning.setText("invalid Selection, can't deliver more Resources than you have!");
					}
					warning.setVisible(true);
					}
				else{
					Client.returnPackage=new ResourcePackage(a1,a2,a3,a4,a5);
				}
			}
		});
		okTrade.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int a1= Integer.valueOf(textField6.getText());
				int a2= Integer.valueOf(textField7.getText());
				int a3= Integer.valueOf(textField8.getText());
				int a4= Integer.valueOf(textField9.getText());
				int a5= Integer.valueOf(textField10.getText());
				int r1= Integer.valueOf(lumberMax2.getText());
				int r2= Integer.valueOf(brickMax2.getText());
				int r3= Integer.valueOf(woolMax2.getText());
				int r4= Integer.valueOf(grainMax2.getText());
				int r5= Integer.valueOf(oreMax2.getText());
				if(r1+a1>=0 || r2+a2>=0 || r3+a3>=0 || r4+a4>=0 || r5+a5>=0){
					warning2.setText("invalid Selection, can't deliver more Resources than you have");	
					warning2.setVisible(true);
					}
				else{
					Client.returnPackage=new ResourcePackage(a1,a2,a3,a4,a5);
				}
			}
		});
		okIncomingTrade.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.acceptTrade = 1;
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.acceptTrade = -1;
			}
		});
	}
	
	public void setN(int n){
		this.n=n;
	}
	
	public void setText(String str) {
		topLabel.setText(str);
	}
	
	public void reset() {
		textField1.setText("0");
		textField2.setText("0");
		textField3.setText("0");
		textField4.setText("0");
		textField5.setText("0");
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Hans Lange der
		returnPackPanel = new JPanel();
		contentPanel = new JPanel();
		topLabel = new JLabel();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();
		lumberMax = new JLabel();
		brickMax = new JLabel();
		woolMax = new JLabel();
		grainMax = new JLabel();
		oreMax = new JLabel();
		separator1 = new JSeparator();
		textField1 = new JTextField();
		textField2 = new JTextField();
		textField3 = new JTextField();
		textField4 = new JTextField();
		textField5 = new JTextField();
		lumberTradeBox = new JComboBox();
		brickTradeBox = new JComboBox();
		woolTradeBox = new JComboBox();
		grainTradeBox = new JComboBox();
		oreTradeBox = new JComboBox();
		buttonBar = new JPanel();
		warning = new JLabel();
		okReturn = new JButton();
		tradePanel = new JPanel();
		contentPanel2 = new JPanel();
		topLabel2 = new JLabel();
		label6 = new JLabel();
		label7 = new JLabel();
		label8 = new JLabel();
		label9 = new JLabel();
		label10 = new JLabel();
		lumberMax2 = new JLabel();
		brickMax2 = new JLabel();
		woolMax2 = new JLabel();
		grainMax2 = new JLabel();
		oreMax2 = new JLabel();
		separator2 = new JSeparator();
		textField6 = new JTextField();
		textField7 = new JTextField();
		textField8 = new JTextField();
		textField9 = new JTextField();
		textField10 = new JTextField();
		lumberTradeBox2 = new JComboBox();
		brickTradeBox2 = new JComboBox();
		woolTradeBox2 = new JComboBox();
		grainTradeBox2 = new JComboBox();
		oreTradeBox2 = new JComboBox();
		buttonBar2 = new JPanel();
		warning2 = new JLabel();
		okTrade = new JButton();
		incomingTradePanel = new JPanel();
		contentPanel3 = new JPanel();
		topLabel3 = new JLabel();
		label11 = new JLabel();
		label12 = new JLabel();
		label13 = new JLabel();
		label14 = new JLabel();
		label15 = new JLabel();
		lumberMax3 = new JLabel();
		brickMax3 = new JLabel();
		woolMax3 = new JLabel();
		grainMax3 = new JLabel();
		oreMax3 = new JLabel();
		separator3 = new JSeparator();
		buttonBar3 = new JPanel();
		okIncomingTrade = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 37, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0, 1.0E-4};

		//======== returnPackPanel ========
		{
			returnPackPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
			returnPackPanel.setVisible(false);
//
//			// JFormDesigner evaluation mark
//			returnPackPanel.setBorder(new javax.swing.border.CompoundBorder(
//				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//					java.awt.Color.red), returnPackPanel.getBorder())); returnPackPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			returnPackPanel.setLayout(new GridBagLayout());
			((GridBagLayout)returnPackPanel.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)returnPackPanel.getLayout()).rowHeights = new int[] {166, 52, 0, 0};
			((GridBagLayout)returnPackPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
			((GridBagLayout)returnPackPanel.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 65, 65, 65, 65, 65, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {21, 0, 0, 0, 0, 0, 14, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
				contentPanel.add(topLabel, new GridBagConstraints(2, 1, 3, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label1 ----
				label1.setText("Lumber:");
				contentPanel.add(label1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label2 ----
				label2.setText("Brick:");
				contentPanel.add(label2, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label3 ----
				label3.setText("Wool:");
				contentPanel.add(label3, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label4 ----
				label4.setText("Grain:");
				contentPanel.add(label4, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label5 ----
				label5.setText("Ore:");
				contentPanel.add(label5, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- lumberMax ----
				lumberMax.setText("text");
				contentPanel.add(lumberMax, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- brickMax ----
				brickMax.setText("text");
				contentPanel.add(brickMax, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- woolMax ----
				woolMax.setText("text");
				contentPanel.add(woolMax, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- grainMax ----
				grainMax.setText("text");
				contentPanel.add(grainMax, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- oreMax ----
				oreMax.setText("text");
				contentPanel.add(oreMax, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
				contentPanel.add(separator1, new GridBagConstraints(0, 4, 6, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- textField1 ----
				textField1.setText("0");
				contentPanel.add(textField1, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- textField2 ----
				textField2.setText("0");
				contentPanel.add(textField2, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- textField3 ----
				textField3.setText("0");
				contentPanel.add(textField3, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- textField4 ----
				textField4.setText("0");
				contentPanel.add(textField4, new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- textField5 ----
				textField5.setText("0");
				contentPanel.add(textField5, new GridBagConstraints(5, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- lumberTradeBox ----
				lumberTradeBox.setVisible(false);
				contentPanel.add(lumberTradeBox, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- brickTradeBox ----
				brickTradeBox.setVisible(false);
				contentPanel.add(brickTradeBox, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- woolTradeBox ----
				woolTradeBox.setVisible(false);
				contentPanel.add(woolTradeBox, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- grainTradeBox ----
				grainTradeBox.setVisible(false);
				contentPanel.add(grainTradeBox, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- oreTradeBox ----
				oreTradeBox.setVisible(false);
				contentPanel.add(oreTradeBox, new GridBagConstraints(5, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			returnPackPanel.add(contentPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {134, 91, 134};
				((GridBagLayout)buttonBar.getLayout()).rowHeights = new int[] {25, 0};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0};

				//---- warning ----
				warning.setText("invalid Selection");
				warning.setForeground(Color.red);
				warning.setVisible(false);
				buttonBar.add(warning, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- okReturn ----
				okReturn.setText("OK");
				buttonBar.add(okReturn, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			returnPackPanel.add(buttonBar, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(returnPackPanel, new GridBagConstraints(0, 0, 1, 3, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== tradePanel ========
		{
			tradePanel.setBorder(new EmptyBorder(12, 12, 12, 12));
			tradePanel.setVisible(false);
			tradePanel.setLayout(new GridBagLayout());
			((GridBagLayout)tradePanel.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)tradePanel.getLayout()).rowHeights = new int[] {139, 34, 0};
			((GridBagLayout)tradePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
			((GridBagLayout)tradePanel.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

			//======== contentPanel2 ========
			{
				contentPanel2.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel2.getLayout()).columnWidths = new int[] {0, 65, 65, 65, 65, 65, 0, 0};
				((GridBagLayout)contentPanel2.getLayout()).rowHeights = new int[] {17, 0, 0, 0, 0, 0, 17, 0};
				((GridBagLayout)contentPanel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
				((GridBagLayout)contentPanel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
				contentPanel2.add(topLabel2, new GridBagConstraints(2, 1, 3, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label6 ----
				label6.setText("Lumber:");
				contentPanel2.add(label6, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label7 ----
				label7.setText("Brick:");
				contentPanel2.add(label7, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label8 ----
				label8.setText("Wool:");
				contentPanel2.add(label8, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label9 ----
				label9.setText("Grain:");
				contentPanel2.add(label9, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label10 ----
				label10.setText("Ore:");
				contentPanel2.add(label10, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- lumberMax2 ----
				lumberMax2.setText("text");
				contentPanel2.add(lumberMax2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- brickMax2 ----
				brickMax2.setText("text");
				contentPanel2.add(brickMax2, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- woolMax2 ----
				woolMax2.setText("text");
				contentPanel2.add(woolMax2, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- grainMax2 ----
				grainMax2.setText("text");
				contentPanel2.add(grainMax2, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- oreMax2 ----
				oreMax2.setText("text");
				contentPanel2.add(oreMax2, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
				contentPanel2.add(separator2, new GridBagConstraints(0, 4, 6, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- textField6 ----
				textField6.setText("0");
				contentPanel2.add(textField6, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- textField7 ----
				textField7.setText("0");
				contentPanel2.add(textField7, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- textField8 ----
				textField8.setText("0");
				contentPanel2.add(textField8, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- textField9 ----
				textField9.setText("0");
				contentPanel2.add(textField9, new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- textField10 ----
				textField10.setText("0");
				contentPanel2.add(textField10, new GridBagConstraints(5, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- lumberTradeBox2 ----
				lumberTradeBox2.setVisible(false);
				contentPanel2.add(lumberTradeBox2, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- brickTradeBox2 ----
				brickTradeBox2.setVisible(false);
				contentPanel2.add(brickTradeBox2, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- woolTradeBox2 ----
				woolTradeBox2.setVisible(false);
				contentPanel2.add(woolTradeBox2, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- grainTradeBox2 ----
				grainTradeBox2.setVisible(false);
				contentPanel2.add(grainTradeBox2, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- oreTradeBox2 ----
				oreTradeBox2.setVisible(false);
				contentPanel2.add(oreTradeBox2, new GridBagConstraints(5, 6, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			tradePanel.add(contentPanel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

			//======== buttonBar2 ========
			{
				buttonBar2.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar2.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar2.getLayout()).columnWidths = new int[] {118, 96, 116};
				((GridBagLayout)buttonBar2.getLayout()).rowHeights = new int[] {25, 0};
				((GridBagLayout)buttonBar2.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0};

				//---- warning2 ----
				warning2.setText("                            !!-----Wrong resource-carge-----!!");
				warning2.setForeground(Color.red);
				warning2.setVisible(false);
				buttonBar2.add(warning2, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- okTrade ----
				okTrade.setText("OK");
				buttonBar2.add(okTrade, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			tradePanel.add(buttonBar2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(tradePanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== incomingTradePanel ========
		{
			incomingTradePanel.setBorder(new EmptyBorder(12, 12, 12, 12));
			incomingTradePanel.setLayout(new GridBagLayout());
			((GridBagLayout)incomingTradePanel.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)incomingTradePanel.getLayout()).rowHeights = new int[] {139, 34, 0};
			((GridBagLayout)incomingTradePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
			((GridBagLayout)incomingTradePanel.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

			//======== contentPanel3 ========
			{
				contentPanel3.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel3.getLayout()).columnWidths = new int[] {0, 65, 65, 65, 65, 65, 0, 0};
				((GridBagLayout)contentPanel3.getLayout()).rowHeights = new int[] {17, 0, 0, 0, 0, 0, 17, 0};
				((GridBagLayout)contentPanel3.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
				((GridBagLayout)contentPanel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
				contentPanel3.add(topLabel3, new GridBagConstraints(2, 1, 3, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label11 ----
				label11.setText("Lumber:");
				contentPanel3.add(label11, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label12 ----
				label12.setText("Brick:");
				contentPanel3.add(label12, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label13 ----
				label13.setText("Wool:");
				contentPanel3.add(label13, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label14 ----
				label14.setText("Grain:");
				contentPanel3.add(label14, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- label15 ----
				label15.setText("Ore:");
				contentPanel3.add(label15, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- lumberMax3 ----
				lumberMax3.setText("text");
				contentPanel3.add(lumberMax3, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- brickMax3 ----
				brickMax3.setText("text");
				contentPanel3.add(brickMax3, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- woolMax3 ----
				woolMax3.setText("text");
				contentPanel3.add(woolMax3, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- grainMax3 ----
				grainMax3.setText("text");
				contentPanel3.add(grainMax3, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

				//---- oreMax3 ----
				oreMax3.setText("text");
				contentPanel3.add(oreMax3, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
				contentPanel3.add(separator3, new GridBagConstraints(0, 4, 6, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));
			}
			incomingTradePanel.add(contentPanel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

			//======== buttonBar3 ========
			{
				buttonBar3.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar3.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar3.getLayout()).columnWidths = new int[] {139, 96, 134};
				((GridBagLayout)buttonBar3.getLayout()).rowHeights = new int[] {25, 0};
				((GridBagLayout)buttonBar3.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0};

				//---- okIncomingTrade ----
				okIncomingTrade.setText("OK");
				buttonBar3.add(okIncomingTrade, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				buttonBar3.add(cancelButton, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			incomingTradePanel.add(buttonBar3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(incomingTradePanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		pack();
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Hans Lange der
	public JPanel returnPackPanel;
	private JPanel contentPanel;
	private JLabel topLabel;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	public JLabel lumberMax;
	public JLabel brickMax;
	public JLabel woolMax;
	public JLabel grainMax;
	public JLabel oreMax;
	private JSeparator separator1;
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private JTextField textField4;
	private JTextField textField5;
	private JComboBox lumberTradeBox;
	private JComboBox brickTradeBox;
	private JComboBox woolTradeBox;
	private JComboBox grainTradeBox;
	private JComboBox oreTradeBox;
	private JPanel buttonBar;
	private JLabel warning;
	private JButton okReturn;
	public JPanel tradePanel;
	private JPanel contentPanel2;
	private JLabel topLabel2;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JLabel label9;
	private JLabel label10;
	public JLabel lumberMax2;
	public JLabel brickMax2;
	public JLabel woolMax2;
	public JLabel grainMax2;
	public JLabel oreMax2;
	private JSeparator separator2;
	private JTextField textField6;
	private JTextField textField7;
	private JTextField textField8;
	private JTextField textField9;
	private JTextField textField10;
	private JComboBox lumberTradeBox2;
	private JComboBox brickTradeBox2;
	private JComboBox woolTradeBox2;
	private JComboBox grainTradeBox2;
	private JComboBox oreTradeBox2;
	private JPanel buttonBar2;
	private JLabel warning2;
	private JButton okTrade;
	public JPanel incomingTradePanel;
	private JPanel contentPanel3;
	private JLabel topLabel3;
	private JLabel label11;
	private JLabel label12;
	private JLabel label13;
	private JLabel label14;
	private JLabel label15;
	public JLabel lumberMax3;
	public JLabel brickMax3;
	public JLabel woolMax3;
	public JLabel grainMax3;
	public JLabel oreMax3;
	private JSeparator separator3;
	private JPanel buttonBar3;
	private JButton okIncomingTrade;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
