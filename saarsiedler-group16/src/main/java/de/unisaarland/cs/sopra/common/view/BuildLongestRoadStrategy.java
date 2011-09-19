package de.unisaarland.cs.sopra.common.view;

import java.util.List;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Resource;
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
			return new AIGameStats(player, this, new ResourcePackage(0, 0, 0, 0, 0), 0);
		int n = streetsNeeded(mr);
		ResourcePackage resourcePackage = player.getResources().copy().add(new ResourcePackage(-1*n, -1*n, 0, 0, 0));
		int victoryPoints = player.getVictoryPoints() + 2;
		AIGameStats gameStats = new AIGameStats(player, this ,resourcePackage, victoryPoints);
		return gameStats;
	}
	
	public boolean tradePossible(ModelReader mr){
		ResourcePackage resourcePackage = mr.getMe().getResources().copy();
		if (resourcePackage.getResource(Resource.LUMBER) > 0){
			resourcePackage.add(new ResourcePackage(-1, 0, 0, 0, 0));
		}
		if (resourcePackage.getResource(Resource.BRICK) > 0){
			resourcePackage.add(new ResourcePackage(0, -1, 0, 0, 0));
		}
		if (resourcePackage.getPositiveResourcesCount() > 0)
			return true;
		else
		return false;
	}
}