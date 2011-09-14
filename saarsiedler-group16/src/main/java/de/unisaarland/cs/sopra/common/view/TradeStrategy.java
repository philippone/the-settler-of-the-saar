package de.unisaarland.cs.sopra.common.view;

import java.io.IOException;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;

public class TradeStrategy implements Strategy {

	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) {
		try {
			ca.respondTrade(false);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int evaluate(){
		// TODO implement this method
		return 0;
	}
}
