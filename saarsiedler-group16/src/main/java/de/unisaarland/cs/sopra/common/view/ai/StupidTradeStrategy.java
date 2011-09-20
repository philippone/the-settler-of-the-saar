package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class StupidTradeStrategy implements TradeStrategy {

	public StupidTradeStrategy(){
	}

	@Override
	public boolean accepts() {
		return false;
	}
	
}
