package de.unisaarland.cs.sopra.common.view;

import java.util.List;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class BuildLongestRoadStrategy implements Strategy {

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



	public int streetsNeeded(ModelReader mr){
		//TODO implement this method
		return 1;
	}


	public float evaluate(ModelReader mr, ControllerAdapter ca) throws Exception {
		//TODO implement this method
				return 1;
			}
	
	public AIGameStats getGameStats(ModelReader mr){
		Player player = mr.getMe();
		int vpAchieved = player.getVictoryPoints() / mr.getMaxVictoryPoints();
		if (vpAchieved <= 0.5)
		return null;
		int n = streetsNeeded(mr);
		ResourcePackage resourcePackage = player.getResources().add(new ResourcePackage(-1*n, -1*n, 0, 0, 0));
		int victoryPoints = player.getVictoryPoints() + 2;
		AIGameStats gameStats = new AIGameStats(player, resourcePackage, victoryPoints);
		return gameStats;
	}
}