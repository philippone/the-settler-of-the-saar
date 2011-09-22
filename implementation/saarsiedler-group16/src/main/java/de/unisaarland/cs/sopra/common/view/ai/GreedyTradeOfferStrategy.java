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
			if (tryToTrade(price)) price.modifyResource(min, 1);
			else success = false;
		} while(price.hasNegativeResources());
		return success;
	}
	
	public boolean tryToTrade(ResourcePackage price){
		Resource min = getMinResource(price);
		ResourcePackage tradePackage = new ResourcePackage();
		tradePackage.modifyResource(min, 1);
		for (int i = 1 ; i < 5; i++){
			tradePackage.modifyResource(getMaxResource(price), -1);
			if (!price.copy().add(tradePackage).hasNegativeResources() && ca.offerTrade(tradePackage) != -1){
				price.add(tradePackage);
				return true;
			}
		}
		return false;
	}

}
