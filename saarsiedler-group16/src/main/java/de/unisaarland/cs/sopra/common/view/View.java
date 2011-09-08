package de.unisaarland.cs.sopra.common.view;

import de.unisaarland.cs.sopra.common.ModelObserver;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.*;

public abstract class View implements ModelObserver{

	protected ModelReader modelReader;
	protected ControllerAdapter controllerAdapter;
	
	View(long meID, ModelReader modelReader, ControllerAdapter controllerAdapter) {
		//TODO: implement it!
	}
	
}
