package de.unisaarland.cs.sopra.common.view;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class AIGameStats  {
	
	private	Player player;
	private ResourcePackage ResourcePackage;
	private int victoryPoints ; 
	
	public AIGameStats(Player player, ResourcePackage resourcePackage, int victoryPoints) {
		if (player == null) throw new IllegalArgumentException();
		this.player = player;
		this.ResourcePackage = resourcePackage;
		this.victoryPoints = victoryPoints;
		
	}

	public ResourcePackage getResources(){
		return this.ResourcePackage;
	}
	
	public int getVictoryPoints(){
		return this.victoryPoints;
	}

	public void setStats(ResourcePackage resourcePackage, int victoryPoints) {
		if (resourcePackage == null) throw new IllegalArgumentException();
		this.ResourcePackage = player.getResources();
		this.victoryPoints = player.getVictoryPoints();
	
	}
	

}
