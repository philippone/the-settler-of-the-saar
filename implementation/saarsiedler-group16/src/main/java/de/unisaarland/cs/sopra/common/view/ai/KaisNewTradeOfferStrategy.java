package de.unisaarland.cs.sopra.common.view.ai;

import java.util.HashSet;
import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class KaisNewTradeOfferStrategy extends TradeOfferStrategy {

	public KaisNewTradeOfferStrategy(ControllerAdapter ca, ModelReader mr) {
		super(ca, mr);
	}

	@Override
	public boolean execute(ResourcePackage price) {
		boolean successfull = true;
		ResourcePackage difference = price.add(mr.getResources());
		while (difference.hasPositiveResources() && difference.hasNegativeResources() && successfull){
			if (!(tryHabourTrade(difference) || tryFourTrade(difference))) successfull = false;
		}
		return successfull && !difference.hasNegativeResources();
	}
	
	

	private boolean tryHabourTrade(ResourcePackage difference) {
		Set<HarborType> myHarbours = mr.getHarborTypes(mr.getMe());
		Set<Resource> habourResources = getResourcesFromHarbors(myHarbours);
		boolean successfull = myHarbours.size() > 0;
		if (successfull){
			successfull = false;
			for (Resource aboveTwo : getResourcesAboveTwo(difference)){
				if (habourResources.contains(aboveTwo)){
					successfull = true;
					Resource minRes = getMinResource(difference);
					ResourcePackage offerPackage = new ResourcePackage();
					offerPackage.modifyResource(minRes, 1);
					offerPackage.modifyResource(aboveTwo, -1);
					if (ca.offerTrade(offerPackage) == -1){
						offerPackage.modifyResource(aboveTwo, -1);
						ca.offerTrade(offerPackage);
					}
					difference.add(offerPackage);
				}
			}
		}
		return successfull;
	}
	
	private Set<Resource> getResourcesFromHarbors(Set<HarborType> haborSet){
		Set<Resource> habourResources = new HashSet<Resource>();
		for (HarborType ht : haborSet){
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
		return habourResources;
	}
	
	private Set<Resource> getResourcesAboveTwo(ResourcePackage resPack) {
		Set<Resource> resAboveTwo = new HashSet<Resource>();
		for (Resource r : Resource.values()){
			if (resPack.getResource(r) >= 2) resAboveTwo.add(r);
		}
		return resAboveTwo;
	}

	private boolean tryFourTrade(ResourcePackage difference) {
		boolean successfull = difference.getPositiveResourcesCount() >= 4;
		if (successfull){
			Resource minRes = getMinResource(difference);
			ResourcePackage tradePackage = new ResourcePackage();
			tradePackage.modifyResource(minRes, 1);
			difference.modifyResource(minRes, 1);
			for (int i = 0; i < 4; i++){
				Resource maxRes = getMaxResource(difference);
				tradePackage.modifyResource(maxRes, -1);
				difference.modifyResource(maxRes, -1);
			}
			ca.offerTrade(tradePackage);
			successfull = true;
		}
		return successfull;
	}
	
	public boolean isProbable(ResourcePackage price){
		boolean isProbable = true;
		ResourcePackage difference = price.add(mr.getResources());
		while (difference.hasPositiveResources() && difference.hasNegativeResources() && isProbable){
			if (!(tryHabourTrade(difference) || tryFourTrade(difference))) isProbable = false;
		}
		return isProbable;
		// TODO implement this!
	}

}
