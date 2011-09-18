package de.unisaarland.cs.sopra.common.view;

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

		}

	}
	
	@Override
	public float evaluate(ModelReader mr, ControllerAdapter ca)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public AIGameStats getGameStats(ModelReader mr) {
		return null;
	}

}
