package de.unisaarland.cs.sopra.common.view;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public abstract class Strategy {

	private final int victoryPoints;
	private final ResourcePackage price;
	
	public Strategy(int victoryPoints, ResourcePackage price){
		this.victoryPoints = victoryPoints;
		this.price = price;
	}
	
	public abstract void execute(ModelReader mr, ControllerAdapter ca) throws Exception;

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public ResourcePackage getPrice() {
		return price;
	}
	
}
