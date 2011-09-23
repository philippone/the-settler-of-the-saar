package de.unisaarland.cs.sopra.common.view.ai;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;


public class HarborTradeStrategy  extends TradeOfferStrategy {

	public HarborTradeStrategy(ControllerAdapter ca, ModelReader mr) {
		super(ca, mr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(ResourcePackage price) {
		Set<HarborType> harbors = mr.getHarborTypes(mr.getMe());
		if (harbors != null){
			if (harbors.contains(HarborType.WOOL_HARBOR)){
				ResourcePackage resPack = mr.getMe().getResources().copy();
				ResourcePackage tradePack = new ResourcePackage();
				while (resPack.getResource(Resource.WOOL) > 1 && resPack.hasNegativeResources()) {
				tradePack.modifyResource(getMinResource(resPack), 1);
				tradePack.modifyResource(Resource.WOOL, -2);
				ca.offerTrade(tradePack);
				resPack.modifyResource(getMinResource(resPack), 1);
				resPack.modifyResource(Resource.WOOL, 2);
				tradePack.modifyResource(getMinResource(resPack), -1);
				tradePack.modifyResource(Resource.WOOL, 2);
				}
			} 
			return false;
		}
		return false;
	}
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
