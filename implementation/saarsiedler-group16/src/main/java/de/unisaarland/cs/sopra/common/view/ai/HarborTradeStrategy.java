package de.unisaarland.cs.sopra.common.view.ai;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class HarborTradeStrategy extends TradeOfferStrategy {

	public HarborTradeStrategy(ControllerAdapter ca, ModelReader mr) {
		super(ca, mr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(ResourcePackage price) {
		Set<HarborType> harbors = mr.getHarborTypes(mr.getMe());
		if (harbors != null) {
		
			ResourcePackage res = mr.getMe().getResources().copy().add(price);
			ResourcePackage tradePackage = new ResourcePackage();
			if (harbors.contains(HarborType.WOOL_HARBOR) && (res.getResource(Resource.WOOL) > 1)) {
				while (res.hasNegativeResources() && res.getResource(Resource.WOOL) >= 2) {
					tradePackage.modifyResource(getMinResource(res), 1);
					tradePackage.modifyResource(Resource.WOOL, -2);
					res.modifyResource(Resource.WOOL, -2);
					ca.offerTrade(tradePackage);
					tradePackage.modifyResource(getMinResource(res), -1);
					tradePackage.modifyResource(Resource.WOOL, 2);
					res.modifyResource(getMinResource(res), 1);
				}
				if (!res.hasNegativeResources())
					return true;
					
			} else
			if (harbors.contains(HarborType.BRICK_HARBOR) && (res.getResource(Resource.BRICK) > 1)) {
				while (res.hasNegativeResources() && res.getResource(Resource.BRICK) >= 2) {
					tradePackage.modifyResource(getMinResource(res), 1);
					tradePackage.modifyResource(Resource.BRICK, -2);
					res.modifyResource(Resource.BRICK, -2);
					ca.offerTrade(tradePackage);
					tradePackage.modifyResource(getMinResource(res), -1);
					tradePackage.modifyResource(Resource.BRICK, 2);
					res.modifyResource(getMinResource(res), 1);
				}
				if (!res.hasNegativeResources())
					return true;
					
			} 
			 else
					if (harbors.contains(HarborType.LUMBER_HARBOR) && (res.getResource(Resource.LUMBER) > 1)) { 
						while (res.hasNegativeResources() && res.getResource(Resource.LUMBER) >= 2) {
							tradePackage.modifyResource(getMinResource(res), 1);
							tradePackage.modifyResource(Resource.LUMBER, -2);
							res.modifyResource(Resource.LUMBER, -2);
							ca.offerTrade(tradePackage);
							tradePackage.modifyResource(getMinResource(res), -1);
							tradePackage.modifyResource(Resource.LUMBER, 2);
							res.modifyResource(getMinResource(res), 1);
						}
						if (!res.hasNegativeResources())
							return true;
							
					} 
						else
							if (harbors.contains(HarborType.ORE_HARBOR) && res.getResource(Resource.ORE) > 1) {
								while (res.hasNegativeResources() && res.getResource(Resource.ORE) >= 2) {
									tradePackage.modifyResource(getMinResource(res), 1);
									tradePackage.modifyResource(Resource.ORE, -2);
									res.modifyResource(Resource.ORE, -2);
									ca.offerTrade(tradePackage);
									tradePackage.modifyResource(getMinResource(res), -1);
									tradePackage.modifyResource(Resource.ORE, 2);
									res.modifyResource(getMinResource(res), 1);
								}
								if (!res.hasNegativeResources())
									return true;
									
							} 
							else
								if (harbors.contains(HarborType.GRAIN_HARBOR) && res.getResource(Resource.GRAIN) > 1) {
									while (res.hasNegativeResources() && res.getResource(Resource.GRAIN) >= 2) {
										tradePackage.modifyResource(getMinResource(res), 1);
										tradePackage.modifyResource(Resource.GRAIN, -2);
										res.modifyResource(Resource.GRAIN, -2);
										ca.offerTrade(tradePackage);
										tradePackage.modifyResource(getMinResource(res), -1);
										tradePackage.modifyResource(Resource.GRAIN, 2);
										res.modifyResource(getMinResource(res), 1);
									}
									if (!res.hasNegativeResources())
										return true;
										
								} else if (harbors.contains(HarborType.GENERAL_HARBOR)){
									ResourcePackage ref = price.copy().add(mr.getMe().getResources());
									tradePackage.modifyResource(getMinResource(ref), 1);
									while (numberOfPossitiveResources(ref) >= 3 && ref.hasNegativeResources()){
										tradePackage = new ResourcePackage();
										tradePackage.modifyResource(getMinResource(ref), 1);
										for (int i = 0; i < 3; i++){
											tradePackage.modifyResource(getMaxResource(ref), -1);
											ref.modifyResource(getMaxResource(ref), -1);
										}
										ca.offerTrade(tradePackage);
										ref.modifyResource(getMinResource(ref), 1);
									}
									if (!ref.hasNegativeResources())
										return true;
								}
		}
		ResourcePackage ref = price.copy().add(mr.getMe().getResources());
		ResourcePackage tradePackage = new ResourcePackage();
		tradePackage.modifyResource(getMinResource(ref), 1);
		while (numberOfPossitiveResources(ref) >= 4 && ref.hasNegativeResources()){
			tradePackage = new ResourcePackage();
			tradePackage.modifyResource(getMinResource(ref), 1);
			for (int i = 0; i < 4; i++){
				tradePackage.modifyResource(getMaxResource(ref), -1);
				ref.modifyResource(getMaxResource(ref), -1);
			}
			ca.offerTrade(tradePackage);
			ref.modifyResource(getMinResource(ref), 1);
		}
			if (!ref.hasNegativeResources())
				return true;
			return false;
		} 
		
	}
	


