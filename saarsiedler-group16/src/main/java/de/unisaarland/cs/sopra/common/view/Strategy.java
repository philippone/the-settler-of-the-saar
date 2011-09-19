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
	
	/**
	 * Executes itself. Therefore useable() has to return true.
	 */
	public abstract void execute(ModelReader mr, ControllerAdapter ca) throws Exception;

	/**
	 * Tests wheter it is possible to execute the strategy.
	 * This has nothing to do with the resources!
	 * @return Tests wheter it is possible to execute the strategy
	 */
	public abstract boolean useable();
	
	public int getVictoryPoints() {
		return victoryPoints;
	}

	public ResourcePackage getPrice() {
		return price;
	}
	
}
