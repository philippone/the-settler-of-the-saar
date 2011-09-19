package de.unisaarland.cs.sopra.common.view;

import java.util.List;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Street;

public class BuildLongestRoadStrategy extends Strategy {

	public BuildLongestRoadStrategy() {
		super(2, Street.getPrice());
	}

	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		List<List<Path>> longestRoads = mr.calculateLongestRoads(mr.getMe());
		
		for ( List<Path> lr : longestRoads) 
			{
		if (lr.size() >= 5 && mr.getLongestClaimedRoad().size() < lr.size())
			ca.claimLongestRoad(lr);
		}	
		
		//TODO check how many victory Points we wet have to achieve
	}

	@Override
	public boolean useable(ModelReader mr) {
		double vp = mr.getMe().getVictoryPoints() / mr.getMaxVictoryPoints();
			if ( vp <= 0.5) {
				return false;
			}
			return true;
	}
	
}