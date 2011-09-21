package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class GreedyTradeOfferStrategy extends TradeOfferStrategy {

	public GreedyTradeOfferStrategy(ControllerAdapter ca, ResourcePackage possessions, ResourcePackage price){
		super(ca, possessions, price);
	}
	
	public void execute() {
		
	}

}
