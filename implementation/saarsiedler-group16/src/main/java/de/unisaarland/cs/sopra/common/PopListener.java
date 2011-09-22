package de.unisaarland.cs.sopra.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class PopListener implements ActionListener {

	private Popup pop;

	public PopListener(Popup popup) {
		this.pop=popup;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Return-Panel
		if(arg0.getSource()==pop.button13){
			increase(pop.textField1);
		}
		if(arg0.getSource()==pop.button18){
			decrease(pop.textField1);
		}
		if(arg0.getSource()==pop.button14){
			increase(pop.textField2);
		}
		if(arg0.getSource()==pop.button19){
			decrease(pop.textField2);
		}
		if(arg0.getSource()==pop.button15){
			increase(pop.textField3);
		}
		if(arg0.getSource()==pop.button20){
			decrease(pop.textField3);
		}
		if(arg0.getSource()==pop.button16){
			increase(pop.textField4);
		}
		if(arg0.getSource()==pop.button21){
			decrease(pop.textField4);
		}
		if(arg0.getSource()==pop.button17){
			increase(pop.textField5);
		}
		if(arg0.getSource()==pop.button22){
			decrease(pop.textField5);
		}
		
		//Trade-Panel
		if(arg0.getSource()==pop.button1){
			increase(pop.textField6);
		}
		if(arg0.getSource()==pop.button2){
			decrease(pop.textField6);
		}
		if(arg0.getSource()==pop.button3){
			increase(pop.textField7);
		}
		if(arg0.getSource()==pop.button4){
			decrease(pop.textField7);
		}
		if(arg0.getSource()==pop.button5){
			increase(pop.textField8);
		}
		if(arg0.getSource()==pop.button6){
			decrease(pop.textField8);
		}
		if(arg0.getSource()==pop.button7){
			increase(pop.textField9);
		}
		if(arg0.getSource()==pop.button10){
			decrease(pop.textField9);
		}
		if(arg0.getSource()==pop.button11){
			increase(pop.textField10);
		}
		if(arg0.getSource()==pop.button12){
			decrease(pop.textField10);
		}
		
		
	}

	private void increase(JTextField tf) {
		tf.setText((Integer.valueOf(tf.getText())+1)+"");
	}
	private void decrease(JTextField tf) {
		tf.setText((Integer.valueOf(tf.getText())-1)+"");
	}

}
