package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public abstract class TradeOfferStrategy {
	
	protected final ControllerAdapter ca;
	protected final ResourcePackage possessions;
	protected final ResourcePackage price;
	
	public TradeOfferStrategy(ControllerAdapter ca, ResourcePackage possessions, ResourcePackage price){
		this.possessions = possessions;
		this.price = price;
		this.ca = ca;
	}
	

	public abstract void execute();
	
	protected Resource getMaxResource(ResourcePackage rp){
		Resource max = Resource.LUMBER;
		for (Resource r : Resource.values()){
			max = rp.getResource(max) < rp.getResource(r) ? r : max;
		}
		return max;
	}
	
	protected Resource getMinResource(ResourcePackage rp){
		Resource min = Resource.LUMBER;
		for (Resource r : Resource.values()){
			min = rp.getResource(min) > rp.getResource(r) ? r : min;
		}
		return min;
	}

}
