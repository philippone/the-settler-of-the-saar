package de.unisaarland.cs.sopra.common.view;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;

public interface Strategy {

	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception;
	
}
