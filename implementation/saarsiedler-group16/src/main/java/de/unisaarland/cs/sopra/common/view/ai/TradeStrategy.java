package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public abstract class TradeStrategy {
	
	protected final ModelReader mr;
	protected final ControllerAdapter ca;
	
	public TradeStrategy(ModelReader mr, ControllerAdapter ca){
		this.mr = mr;
		this.ca = ca;
	}
	
	public abstract void accept(ResourcePackage price, ResourcePackage offer);

}
