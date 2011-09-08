package de.unisaarland.cs.sopra.common.view;

import java.io.IOException;
import java.util.Map;

import model.TestUtil;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import de.unisaarland.cs.sopra.common.Setting;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class GameGUI extends View{

	private Map<Player,String> playerNames;
	
	GameGUI(Player me, ModelReader modelReader, ControllerAdapter controllerAdapter, Map<Player,String> playerNames, Setting setting) throws Exception {
		super(me, modelReader, controllerAdapter);
		Display.setDisplayMode(new DisplayMode(setting.getWindowWidth(), setting.getWindowHeight()));
		Display.setFullscreen(setting.isFullscreen());
		Display.create();
		Thread.sleep(10000);
	}
	
	public void drawTradeMenu() {
		throw new UnsupportedOperationException();
	}
	
	public void drawBuildMenu() {
		throw new UnsupportedOperationException();
	}
	
	public void drawResource() {
		throw new UnsupportedOperationException();
	}
	
	public String getName(Player player) {
		if (player == null) throw new IllegalArgumentException();
		return playerNames.get(player);
	}

	@Override
	public void updatePath(Path path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateIntersection(Intersection intersection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateField(Field field) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateResources() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateVictoryPoints() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCatapultCount() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateSettlementCount(BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void eventRobber() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void eventTrade(ResourcePackage resourcePackage) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void eventNewRound(boolean itsMyTurn) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTradePossibilities() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void eventPlayerLeft(long playerID) {
		throw new UnsupportedOperationException();
	}
	
	public static void main(String[] args) throws Exception {
		WorldRepresentation worldrep = new WorldRepresentation(4, 4, 2, 9, 5, 4,  
				new byte[] {1,2,3,4,
							5,1,2,3,
							4,5,1,2,
							3,4,5,1},
				new byte[] {1,4,
							2,5,
							3,6},
				new byte[] {});
				
		MatchInformation matchinfo = new MatchInformation() 
		{
			
			@Override
			public boolean isStarted() {
				return true;
			}
			
			@Override
			public long getWorldId() {
				throw new IllegalStateException();
			}
			
			@Override
			public String getTitle() {
				return "Test-Spiel";
			}
			
			@Override
			public boolean[] getReadyPlayers() {
				throw new IllegalStateException();
			}
			
			@Override
			public int getNumPlayers() {
				return 2;
			}
			
			@Override
			public long getId() {
				return 0;
			}
			
			@Override
			public long[] getCurrentPlayers() {
				return new long[] {0,1};
			}
			
			@Override
			public long[] getCurrentObservers() {
				return new long[] {};
			}
		};
		Model model = new Model(worldrep, matchinfo, 0);
		model.matchStart(new long[] {0,1}, new byte[] {2,3,4,5,
														 6,8,9,10,
														 11,12,11,10,
														 9,8,6,5});
		Setting setting = new Setting(800, 600, false);
		GameGUI gameGUI = new GameGUI(null, model, null, null, setting);
	
	}
	
}
