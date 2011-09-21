package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class GreedyTradeOfferStrategy extends TradeOfferStrategy {

	public GreedyTradeOfferStrategy(ControllerAdapter ca, ModelReader mr, ResourcePackage price){
		super(ca, mr, price.copy());
	}
	
	public void execute() {
		while(price.hasNegativeResources()){
			Resource min = getMinResource(price);
			tryToTrade(min);
		}
	}
	
	private boolean tryToTrade(Resource min){
		ResourcePackage myrp = mr.getMe().getResources();
		ResourcePackage rp = new ResourcePackage();
		rp.modifyResource(min, 1);
		for (int i = 1; i < 4; i++){
			rp.modifyResource(getMaxResource(myrp), -1);
		}
		if (!myrp.add(rp).hasNegativeResources()){
			return ca.offerTrade(rp) != -1;
		}
		else return false;
	}

}
