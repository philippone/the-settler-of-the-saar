package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class GreedyTradeOfferStrategy extends TradeOfferStrategy {

	public GreedyTradeOfferStrategy(ControllerAdapter ca, ResourcePackage possessions, ResourcePackage price){
		super(ca, possessions, price);
	}
	
	public void execute() {
		do {
			Resource maxR = getMaxResource(possessions);
			Resource minR = getMinResource(price);
			
		} while (possessions.add(price).hasNegativeResources());
	}

}
