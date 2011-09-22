package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class NaiveTradeStrategy extends TradeStrategy {

	public NaiveTradeStrategy(ModelReader mr, ControllerAdapter ca){
		super(mr, ca);
	}

	@Override
	public void accept(ResourcePackage price, ResourcePackage offer) {
		ResourcePackage possessions = mr.getResources().copy();
		if (offer.getPositiveResourcesCount() >= 4 && !mr.getResources().copy().add(offer).hasNegativeResources()) {
			ca.respondTrade(true);
			return;
		}
		if (possessions.add(price).hasNegativeResources())
			if (!possessions.add(offer).hasNegativeResources())
				ca.respondTrade(true);
			else ca.respondTrade(false);
		else ca.respondTrade(false);
	}
	
}
