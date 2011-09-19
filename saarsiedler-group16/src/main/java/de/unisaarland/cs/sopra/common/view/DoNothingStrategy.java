package de.unisaarland.cs.sopra.common.view;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class DoNothingStrategy extends Strategy {
	
	public DoNothingStrategy() {
		super(0, new ResourcePackage());
	}

	public void execute(ModelReader mr, ControllerAdapter ca) {
		try {
			ca.endTurn();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
