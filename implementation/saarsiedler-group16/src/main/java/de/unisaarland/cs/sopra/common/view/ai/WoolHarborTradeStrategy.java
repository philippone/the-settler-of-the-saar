package de.unisaarland.cs.sopra.common.view.ai;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class WoolHarborTradeStrategy extends TradeOfferStrategy {

	public WoolHarborTradeStrategy(ControllerAdapter ca, ModelReader mr) {
		super(ca, mr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(ResourcePackage price) {
		Set<HarborType> harbors = mr.getHarborTypes(mr.getMe());
		if (harbors == null)
			return false;
		else {
			ResourcePackage res = mr.getMe().getResources().copy().add(price);
			ResourcePackage tradePackage = new ResourcePackage();
			if (harbors.contains(HarborType.WOOL_HARBOR)) {
				if (res.getResource(Resource.WOOL) < 2)
					return false;
				while (res.hasNegativeResources() && res.getResource(Resource.WOOL) >= 2) {
					tradePackage.modifyResource(getMinResource(res), 1);
					tradePackage.modifyResource(Resource.WOOL, -2);
					res.modifyResource(Resource.WOOL, -2);
					ca.offerTrade(tradePackage);
					tradePackage.modifyResource(getMinResource(res), -1);
					tradePackage.modifyResource(Resource.WOOL, 2);
					res.modifyResource(getMinResource(res), 1);
				}
					
			}
			return false;
		}
	}
	

}
