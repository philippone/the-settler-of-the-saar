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
	int n;
	public Popup() {
		initComponents();
		topLabel.setText("You have to choose "+n+" Resources!");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int a1= Integer.valueOf(textField1.getText());
				int a2= Integer.valueOf(textField2.getText());
				int a3= Integer.valueOf(textField3.getText());
				int a4= Integer.valueOf(textField4.getText());
				int a5= Integer.valueOf(textField5.getText());
				if((a1+a2+a3+a4+a5)!=(n/2)){
						warning.setVisible(true);
					}
				else{
					Client.returnPackage=new ResourcePackage(a1,a2,a3,a4,a5);
				}
			}
		});
	}
public void setN(int n){
	this.n=n;
}
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Hans Lange der
		dialogPane = new JPanel();
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
		okButton = new JButton();

		//======== this ========
		setAlwaysOnTop(true);
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

			// JFormDesigner evaluation mark
			dialogPane.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			dialogPane.setLayout(new GridBagLayout());
			((GridBagLayout)dialogPane.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)dialogPane.getLayout()).rowHeights = new int[] {191, 0, 0};
			((GridBagLayout)dialogPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
			((GridBagLayout)dialogPane.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 65, 65, 65, 65, 65, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {17, 0, 0, 0, 0, 0, 34, 0};
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
			dialogPane.add(contentPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {139, 96, 134};
				((GridBagLayout)buttonBar.getLayout()).rowHeights = new int[] {25, 0};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0};

				//---- warning ----
				warning.setText("                            !!-----Wrong resource-carge-----!!");
				warning.setForeground(Color.red);
				warning.setVisible(false);
				buttonBar.add(warning, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- okButton ----
				okButton.setText("OK");
				buttonBar.add(okButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			}
			dialogPane.add(buttonBar, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Hans Lange der
	private JPanel dialogPane;
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
	private JButton okButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
