
package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class StupidTradeOfferStrategy extends TradeOfferStrategy {
	
	public StupidTradeOfferStrategy(ControllerAdapter ca, ModelReader mr){
		super(ca, mr);
	}

	@Override
	public boolean execute(ResourcePackage price) {
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
//			System.out.println("-------------------------");
//			System.out.println(mr.getMe().getResources());
//			System.out.println(ref);
//			System.out.println(tradePackage);
			ca.offerTrade(tradePackage);
			ref.modifyResource(getMinResource(ref), 1);
		}
		return false;
	}
	
	

}

