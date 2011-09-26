package de.unisaarland.cs.sopra.common.view.ai.copy;



import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public abstract class TradeOfferStrategy {
	
	protected final ControllerAdapter ca;
	protected final ModelReader mr;
	
	public TradeOfferStrategy(ControllerAdapter ca2, ModelReader mr){
		this.mr = mr;
		this.ca = ca2;
	}
	

	public abstract boolean execute(ResourcePackage price);
	
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
	
	protected int numberOfPossitiveResources(ResourcePackage rp){
		int nopr = 0;
		for (Resource r : Resource.values()){
			if (rp.getResource(r) > 0) nopr += rp.getResource(r);
		}
		return nopr;
	}

}
