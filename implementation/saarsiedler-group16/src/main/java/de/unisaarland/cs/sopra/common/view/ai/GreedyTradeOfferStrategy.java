package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class GreedyTradeOfferStrategy extends TradeOfferStrategy {

	public GreedyTradeOfferStrategy(ControllerAdapter ca, ModelReader mr){
		super(ca, mr);
	}
	
	public boolean execute(ResourcePackage price) {
		boolean success = true;
		Resource min;
		do {
			min = getMinResource(price);
			if (tryToTrade(min, price)) price.modifyResource(min, 1);
			else success = false;
		} while(price.hasNegativeResources());
		return success;
	}
	
	private boolean tryToTrade(Resource min, ResourcePackage price){
		ResourcePackage myrp = price;
		ResourcePackage rp = new ResourcePackage();
		rp.modifyResource(min, 1);
		for (int i = 1; i < 4; i++){
			rp.modifyResource(getMaxResource(myrp), -1);
			if (!price.hasNegativeResources()){
				return ca.offerTrade(rp) != -1;
			}
		}
		return false;
	}

}
