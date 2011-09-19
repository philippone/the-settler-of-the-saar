package de.unisaarland.cs.sopra.common.view;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

//TODO nochmal überlegen ob das wirklich ne Strategie ist.

public class TradeStrategy extends Strategy {

	public TradeStrategy() {
		super(0, new ResourcePackage());
	}

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
	public boolean useable() {
		//TODO implement this operation
		throw new UnsupportedOperationException();
	}

}
