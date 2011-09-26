package de.unisaarland.cs.sopra.common.view.ai;

import java.util.HashSet;
import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class KaisTradeOfferStrategy extends TradeOfferStrategy {
	
	private final Set<Resource> habourResources;
	private int failTrys;
	private int successTrys;
	private boolean execute;
	
	public KaisTradeOfferStrategy(ControllerAdapter ca, ModelReader mr) {
		super(ca, mr);
		this.habourResources = new HashSet<Resource>();
		for (HarborType ht : mr.getHarborTypes(mr.getMe())){
			switch(ht){
			case BRICK_HARBOR:
				habourResources.add(Resource.BRICK);
				break;
			case GRAIN_HARBOR:
				habourResources.add(Resource.GRAIN);
				break;
			case LUMBER_HARBOR:
				habourResources.add(Resource.LUMBER);
				break;
			case ORE_HARBOR:
				habourResources.add(Resource.ORE);
				break;
			case WOOL_HARBOR:
				habourResources.add(Resource.WOOL);
				break;
			}
		}
		this.execute = true;
	}

	@Override
	public boolean execute(ResourcePackage price) {
		ResourcePackage difference = price.add(mr.getResources());
		int i = 0;
		while (difference.hasNegativeResources() && difference.hasPositiveResources() && execute){
			//System.out.println(i++);
			execute = false;
			Resource minRes = getMinResource(difference);
			for (Resource aboveTwo : getResourcesAboveTwo(difference)){
				if (habourResources.contains(aboveTwo)){
					ResourcePackage offerPackage = new ResourcePackage();
					offerPackage.modifyResource(minRes, 1);
					offerPackage.modifyResource(aboveTwo, -1);
					if (ca.offerTrade(offerPackage) == -1){
						failTrys++;
						offerPackage.modifyResource(aboveTwo, -1);
						ca.offerTrade(offerPackage);
					}
					difference.add(offerPackage);
					failTrys = 0;
					successTrys++;
					execute = true;
				}
			}
			if (successTrys >= 128 || failTrys >= 4) break;
			else tryTheOrdinaryOffer(difference);
			if (successTrys >= 128 || failTrys >= 4) break;
		}
		//System.out.printf("The difference: %s\nThe successTrys: %d\nTheFailTrys: %d\n-----------------\n", difference, successTrys, failTrys);
		return !difference.hasNegativeResources();
	}
	
	private Set<Resource> getResourcesAboveTwo(ResourcePackage resPack) {
		Set<Resource> resAboveTwo = new HashSet<Resource>();
		for (Resource r : Resource.values()){
			if (resPack.getResource(r) >= 2) resAboveTwo.add(r);
		}
		return resAboveTwo;
	}

	private void tryTheOrdinaryOffer(ResourcePackage difference){
		ResourcePackage tradePackage = new ResourcePackage();
		tradePackage.modifyResource(getMinResource(difference), 1);
		for (int i = 1; i < 3; i++){
			Resource maxRes = getMaxResource(difference);
			if (difference.getResource(maxRes) >= i){
				tradePackage.modifyResource(maxRes, -1);
				if (ca.offerTrade(tradePackage) == -1){
					failTrys++;
				}
				else {
					difference.add(tradePackage);
					failTrys = 0;
					successTrys++;
					execute = true;
				}
			}
		}
		if (failTrys < 4 && (difference.getNegativeResourcesCount() == 1 || 
				mr.getMe().getResources().size() > 7)) stupidTrade(difference);
	}
	
	private void stupidTrade(ResourcePackage difference){
		ResourcePackage tradePackage = new ResourcePackage();
		tradePackage.modifyResource(getMinResource(difference), 1);
		Resource maxRes = getMaxResource(difference);
		if (difference.getResource(maxRes) >= 4){
			tradePackage.modifyResource(maxRes, -4);
			ca.offerTrade(tradePackage);
			successTrys++;
			execute = true;
			difference.add(tradePackage);
		}
	}

	public boolean isProbable(ResourcePackage price) {
		ResourcePackage difference = price.add(mr.getResources());
		if (difference.hasNegativeResources() && difference.hasPositiveResources()){
			Resource minRes = getMinResource(difference);
			for (Resource aboveTwo : getResourcesAboveTwo(difference)){
				if (habourResources.contains(aboveTwo)){
					ResourcePackage offerPackage = new ResourcePackage();
					offerPackage.modifyResource(minRes, 1);
					offerPackage.modifyResource(aboveTwo, -2);
					difference.add(offerPackage);
				}
			}
		}
		if (difference.hasNegativeResources()){
			return difference.getNegativeResourcesCount()*4 < difference.getPositiveResourcesCount();
		} else return true;
	}

}
