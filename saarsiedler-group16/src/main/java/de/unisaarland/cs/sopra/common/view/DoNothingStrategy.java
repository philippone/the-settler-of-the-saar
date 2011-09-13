package de.unisaarland.cs.sopra.common.view;

import java.io.IOException;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;

public class DoNothingStrategy implements Strategy {
	
	public void execute(ModelReader mr, ControllerAdapter ca) {
		try {
			ca.endTurn();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
