package de.unisaarland.cs.sopra.common.view;

import static de.unisaarland.cs.sopra.common.PlayerColors.*;
import static de.unisaarland.cs.sopra.common.view.RenderBoard.*;
import static de.unisaarland.cs.sopra.common.view.Textures.*;
import static de.unisaarland.cs.sopra.common.view.Util.*;

import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import de.unisaarland.cs.sopra.common.Client;
import de.unisaarland.cs.sopra.common.PlayerColors;
import de.unisaarland.cs.sopra.common.Setting;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Point;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.Timeouts;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;
import de.unisaarland.cs.st.saarsiedler.comm.results.AttackResult;

public class GameGUI extends View implements Runnable{

	private Map<Player, String> playerNames;
	private String gameTitle;
		
	private UnicodeFont debugFont;
	private UnicodeFont uiFont20;
	private UnicodeFont uiFont40;

	@SuppressWarnings("unused")
	private int gameMode;
	@SuppressWarnings("unused")
	private static final int NORMAL = 0;
	@SuppressWarnings("unused")
	private static final int RETURN_RESOURCES = 1;
	//TODO trade, longestroad, etc
	
	private int selectionMode;
	private static final int NONE 					=	0;
	private static final int VILLAGE 				= 	1;
	private static final int TOWN 					= 	2;
	private static final int STREET 				= 	3;
	private static final int CATAPULT_BUILD 		= 	4;
	private static final int CATAPULT_ACTION_DST 	= 	5;
	private static final int ROBBER_SELECT		 	=   6;
	private static final int ROBBER_PLACE 			=   7;
	private static final int ROBBER_PLAYER_SELECT 	=   8;
	
	private List<Point> selectionPoint;
	private List<Location> selectionLocation;
	private List<Location> selectionLocation2; //For move catapult
	private List<Location> selectionLocation3; //For attackable settlements
	
	private static int xOffset;
	private static int xOffsetUI, yOffsetUI, zOffsetUI;
	
	private int[] village;
	private int[] town;
	private int[] catapult;
	private int[] road;
	public static List<List<Path>> longestroad;
	public static List<Path> selected;
	public static List<Path> ret;
	
	private Clickable claimLongestRoad;
	private Clickable claimVictory;
	private Clickable endTurn;
	private Clickable offerTrade;
	private Clickable respondTrade;
	private Clickable buildStreet;
	private Clickable buildVillage;
	private Clickable buildTown;	
	private Clickable buildCatapult;
	private Clickable catapultAction;
	private Clickable setRobber;
	private Clickable returnResources;
	private Clickable quit;
	
	private boolean observer;
	private int lastNumberDiced;
	
	private CyclicBarrier barrier;
	
	private String console4 = "";
	private String console5 = "";
	private String console6 = "";
	private String console7 = "";
	private boolean init;
	
	boolean finished = false;
	
	private long lastinputcheck;

	public static ModelReader mr;
	
	@SuppressWarnings("unused")
	private Timeouts timeouts;
	
	public GameGUI(ModelReader modelReader, ControllerAdapter controllerAdapter, Map<Player, String> names, String gameTitle, boolean observer, CyclicBarrier barrier, Timeouts timeouts) {
		super(modelReader, controllerAdapter);
		this.modelReader.addModelObserver(this);
		this.timeouts = timeouts; //TODO timeouts nutzen
		mr = this.modelReader;
		initiateRenderBoard();
		initiateUtil();
		this.playerNames = names;
		this.selectionMode = NONE;
		this.gameTitle = gameTitle;
		this.catapult = new int[names.size()];
		this.village = new int[names.size()];
		this.town = new int[names.size()];
		this.road = new int[names.size()];
		this.observer = observer;
		this.barrier = barrier;
		xOffset = (int)(366*aspectRatio);
		xOffsetUI = (int)(((870*aspectRatio)-1075)/2);
		yOffsetUI = 955;
		zOffsetUI = -950;
		
		int i = 0;
		for (Player act : modelReader.getTableOrder()) {
			this.village[i] = modelReader.getSettlements(act, BuildingType.Village).size();
			this.town[i] = modelReader.getSettlements(act, BuildingType.Town).size();
			this.catapult[i] = modelReader.getCatapults(act).size();
			List<List<Path>> streets = modelReader.calculateLongestRoads(act);
			this.road[i++] = streets.size() > 0 ? streets.get(0).size() : 0;
		}
	}
	
	private void deactivateUI() {
		for (Clickable click : Clickable.getRenderList()) {
			click.setActive(false);
		}
		quit.setActive(true);
	}
	
	private void reinitiateUI() {
		deactivateUI();
		Player me = modelReader.getMe();
		if (modelReader.affordableSettlements(BuildingType.Village) > 0 && (modelReader.getSettlements(me, BuildingType.Village).size()!=modelReader.getMaxBuilding(BuildingType.Village))) 
			buildVillage.setActive(true);
		if (modelReader.affordableSettlements(BuildingType.Town) > 0 && (modelReader.getSettlements(me, BuildingType.Town).size()!=modelReader.getMaxBuilding(BuildingType.Town))) 
			buildTown.setActive(true);
		if (modelReader.affordableCatapultBuild() > 0 && (modelReader.getCatapults(me).size()!=modelReader.getMaxCatapult()) && modelReader.getSettlements(me, BuildingType.Town).size() > 0) 
			buildCatapult.setActive(true);
		if (modelReader.affordableStreets() > 0 && modelReader.buildableStreetPaths(me).size()!=0) 
			buildStreet.setActive(true);
		if (longestroad != null && !longestroad.isEmpty() && longestroad.get(0).size() > (modelReader.getLongestClaimedRoad() != null ? modelReader.getLongestClaimedRoad().size() : 4)  )
			claimLongestRoad.setActive(true);
		if (modelReader.getCurrentVictoryPoints(me) >= modelReader.getMaxVictoryPoints())
			claimVictory.setActive(true);
		if (modelReader.getRound() >= 1)
			endTurn.setActive(true);
		if (me.getResources().size() >= 1)
			offerTrade.setActive(true);
	}
	

	private void renderPlayerInfo(Player player, long pos) {
		int px = 10;
		int py = 10+(int)pos*76;
		
		// Trenner
		if (pos >0) {
			setColor(BLACK);
			renderUI("Trenner", xOffsetUI, yOffsetUI+py-10, 2, 410, 2);
		}
		if (player == modelReader.getCurrentPlayer()) {
			GL11.glColor4f(0.15f, 0.15f, 0.15f, 0.15f);
			renderUI("PlayerBackgroundHighlight", xOffsetUI+px-5, yOffsetUI+py-10, 2, 430, 92);
			setColor(BLACK);
		}
		GL11.glPushMatrix();
		String name = getName(player);
		uiFont20.drawString(xOffsetUI+px+30, yOffsetUI+py-3, name);
		setColor(player);
		renderUI("PlayerColor", xOffsetUI+px, yOffsetUI+py, 2, 30, 30);
		setColor(BLACK);
		renderUI("Cup", xOffsetUI+px, yOffsetUI+py+20, 2, 30, 50);
		// draw currentScorePoints 0/??
		uiFont20.drawString(xOffsetUI+px+22, yOffsetUI+py+25, ""+modelReader.getCurrentVictoryPoints(player) + "/" + modelReader.getMaxVictoryPoints());
		setColor(player);
		GL11.glPushMatrix();
		GL11.glTranslatef(xOffsetUI+px+90, yOffsetUI+py+39, 1);
		villageTexture.bind();
		drawSquareMid(30, 30);
		GL11.glPopMatrix();
		setColor(BLACK);
		//draw VillageScore 0/??
		uiFont20.drawString(xOffsetUI+px+100, yOffsetUI+py+25, ""+village[(int)pos] + "/" + modelReader.getMaxBuilding(BuildingType.Village));
		setColor(player);
		GL11.glPushMatrix();
		GL11.glTranslatef(xOffsetUI+px+155, yOffsetUI+py+39, 1);
		townTexture.bind();
		drawSquareMid(30,30);
		GL11.glPopMatrix();
		setColor(BLACK);
		//draw TownScore
		uiFont20.drawString(xOffsetUI+px+168, yOffsetUI+py+25, ""+town[(int)pos] + "/" + modelReader.getMaxBuilding(BuildingType.Town));
		setColor(player);
		GL11.glPushMatrix();
		GL11.glTranslatef(xOffsetUI+px+220, yOffsetUI+py+42, 1);
		catapultTexture.bind();
		drawSquareMid(30,30);
		GL11.glPopMatrix();
		setColor(BLACK);
		//draw CatapultScore
		uiFont20.drawString(xOffsetUI+px+230, yOffsetUI+py+25, ""+catapult[(int)pos] + "/" + modelReader.getMaxCatapult());
		setColor(player);
		GL11.glPushMatrix();
		GL11.glTranslatef(xOffsetUI+px+270, yOffsetUI+py+38, 1);
		streetTexture.bind();
		drawSquareMid(5,30);
		GL11.glPopMatrix();
		setColor(BLACK);
		// draw LongestRoad (as int)
		uiFont20.drawString(xOffsetUI+px+277, yOffsetUI+py+25, ""+road[(int)pos]);
		
		GL11.glPopMatrix();
	}

	private void renderMarks() {
		if (selected != null) {
			renderPathMark(false, Model.getLocationListPath(selected));
		}
		switch(selectionMode) {
		case NONE: 
			break;
		case ROBBER_SELECT:
		case ROBBER_PLACE:
			renderFieldMark(selectionPoint);
			break;
		case ROBBER_PLAYER_SELECT:
			renderIntersectionMark(true, selectionLocation);
			break;
		case VILLAGE:
		case TOWN:
			 renderIntersectionMark(false, selectionLocation);
			break;
		case STREET:
		case CATAPULT_BUILD:
			renderPathMark(false, selectionLocation);
			break;
		case CATAPULT_ACTION_DST:
			renderPathMark(true, selectionLocation);
			renderIntersectionMark(true, selectionLocation3);
			renderPathMark(false, selectionLocation2);
		}
	}
	
	private void renderUI(String name, int x, int y, int z, int width, int height) {
		GL11.glPushMatrix(); 
		uiTextureMap.get(name).bind();
	    GL11.glTranslatef(x, y, z);
		drawSquareLeftTop(width, height);
		GL11.glPopMatrix();
	}
	
	private void renderUI(Clickable click) {
		if (!click.isActive()) {
			GL11.glColor4f(0.3f, 0.3f, 0.3f, 0.3f);
			renderUI(click.getName(),click.getX(),click.getY(),click.getZ(),click.getWidth(),click.getHeight());
			GL11.glColor4f(1, 1, 1, 1);
		}
		else {
			renderUI(click.getName(),click.getX(),click.getY(),click.getZ(),click.getWidth(),click.getHeight());
		}
	}
	
	private void render() {
	   //Clear and center
	   GL11.glClearColor(0.2f, 0.2f, 0.2f, 0.0f);
	   GL11.glLoadIdentity();
	   GL11.glTranslatef(-812.8125f*aspectRatio,820,-2000);
	   GL11.glRotatef(180, 1, 0, 0);
	   GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	   //Render Board
	   Iterator<Field> iterF = modelReader.getFieldIterator();
	   while (iterF.hasNext())
		   renderField(iterF.next());
	   Iterator<Path> iterP = modelReader.getPathIterator();
	   while (iterP.hasNext()) 
		   renderPath(iterP.next());
	   Iterator<Intersection> iterI = modelReader.getIntersectionIterator();
	   while (iterI.hasNext()) 
		   renderIntersection(iterI.next());
	   //Render Selections
	   renderMarks();
	   //Render UI
	   GL11.glPushMatrix();
	   GL11.glTranslatef(xOffset, 0, zOffsetUI-5);
	   setColor(BLACK);
	   renderUI("Background", xOffsetUI, yOffsetUI, 0, 1500, 305);
	   renderUI("Console", xOffsetUI+630, yOffsetUI+65, 1, 730, 300);	   
	   setColor(BLACK);
	   renderUI("LumberScore", xOffsetUI+345, yOffsetUI+65, 1, 95, 77);
	   uiFont20.drawString(xOffsetUI+396, yOffsetUI+72, ""+modelReader.getResources().getResource(Resource.LUMBER));
	   setColor(BLACK);
	   renderUI("BrickScore", xOffsetUI+345, yOffsetUI+110, 1, 95, 77);
	   uiFont20.drawString(xOffsetUI+396, yOffsetUI+117, ""+modelReader.getResources().getResource(Resource.BRICK));
	   setColor(BLACK);
	   renderUI("WoolScore", xOffsetUI+345, yOffsetUI+155, 1, 95, 77);
	   uiFont20.drawString(xOffsetUI+396, yOffsetUI+162, ""+modelReader.getResources().getResource(Resource.WOOL));
	   setColor(BLACK);
	   renderUI("GrainScore", xOffsetUI+345, yOffsetUI+200, 1, 95, 77);
	   uiFont20.drawString(xOffsetUI+396, yOffsetUI+207, ""+modelReader.getResources().getResource(Resource.GRAIN));
	   setColor(BLACK);
	   renderUI("OreScore", xOffsetUI+345, yOffsetUI+245, 1, 95, 77);
	   uiFont20.drawString(xOffsetUI+396, yOffsetUI+252, ""+modelReader.getResources().getResource(Resource.ORE));
	   setColor(BLACK);
	   for (Clickable act : Clickable.getRenderList()) {
		   if (act.isVisible()) renderUI(act);
	   }
	   int i = 0;
	   for (Player act : modelReader.getTableOrder()) {
		   if (i > 4) break;
		   renderPlayerInfo(act, i++);
	   }
	   
	   GL11.glPopMatrix();

//	   //DEBUG
//	   GL11.glPushMatrix();
//	   GL11.glTranslatef(xOffset+20, 400, -950);
//	   debugFont.drawString(300, 0, "Debug:", Color.white);
//	   debugFont.drawString(300, 30, "x: " + x + ", y: " + y + ", z: " + z, Color.white);
//	   debugFont.drawString(300, 60, "mx: " + Mouse.getX() + ", my: " + Mouse.getY() + ", mw: " + Mouse.getEventDWheel(), Color.white);
//	   debugFont.drawString(300, 90, "minX: " + minX + ", minY: " + minY + ", minZ: " + maxX, Color.white);
//	   debugFont.drawString(300, 120, "oglx: " + (int)(Mouse.getX()*screenToOpenGLx(zOffsetUI)+25) + ", ogly: " + (int)((windowHeight-Mouse.getY())*screenToOpenGLy(zOffsetUI)+380) );
//	   debugFont.drawString(300, 150, "selectionmode: " + selectionMode );
//	   GL11.glPopMatrix();

	   GL11.glPushMatrix();
	   GL11.glTranslatef(xOffset+xOffsetUI, yOffsetUI, zOffsetUI);
	   if (modelReader.getRound() != 0)
		   uiFont20.drawString(640, 75, "Round "+modelReader.getRound());
	   if (lastNumberDiced != 0)
		   uiFont20.drawString(640, 100, ""+ lastNumberDiced + " was diced");
	   uiFont20.drawString(640, 125, "It's "+ getName(modelReader.getCurrentPlayer()) + "'s turn");
	   uiFont20.drawString(640, 150, console4);
	   uiFont20.drawString(640, 175, console5);
	   uiFont20.drawString(640, 200, console6);
	   uiFont20.drawString(640, 225, console7);
	   GL11.glPopMatrix();
	}

	
	
	public void drawTradeMenu() {
		//TODO: implement it!
	}
	
	
	
	public void drawBuildMenu() {
		//TODO: implement it!
	}
	
	
	public void drawResource() {
		//TODO: implement it!
	}

	public String getName(Player player) {
		if (player == null) throw new IllegalArgumentException();
		return playerNames.get(player);
	}

	
	@Override
	public void updatePath(Path path) {
		int i = 0;
		
		for (Player act : modelReader.getTableOrder()) {
			long time = System.currentTimeMillis();
			List<List<Path>> streets = modelReader.calculateLongestRoads(act);
			System.out.println("Time for calculating longest road: " + (System.currentTimeMillis()-time));
			this.road[i++] = streets.size() > 0 ? streets.get(0).size() : 0;
			if (act == modelReader.getMe())
				longestroad = streets;
		}
		if (!observer && longestroad != null && !longestroad.isEmpty() && longestroad.get(0).size() > (modelReader.getLongestClaimedRoad() != null ? modelReader.getLongestClaimedRoad().size() : 4)  )
			claimLongestRoad.setActive(true);
	}


	@Override
	public void updateField(Field field) {
		//TODO: implement it!
	}

	
	@Override
	public void updateIntersection(Intersection intersection) {
		// TODO Auto-generated method stub
	}
	
	
	@Override
	public void updateResources() {
		if (modelReader.getCurrentPlayer() == modelReader.getMe() && !observer
				&& !(selectionMode == ROBBER_SELECT || selectionMode == ROBBER_PLACE || selectionMode == ROBBER_PLAYER_SELECT) ) {
				//deactivateUI();	
				reinitiateUI();
			}
			else {
				deactivateUI();
			}
	}

	
	@Override
	public void updateVictoryPoints() {
		//TODO: implement it!
	}

	
	@Override
	public void updateCatapultCount() {
		int i = 0;
		for (Player act :modelReader.getTableOrder()) {
			this.catapult[i++] = modelReader.getCatapults(act).size();
		}
	}

	
	@Override
	public void updateSettlementCount(BuildingType buildingType) {
		switch(buildingType) {
		case Village:
			int i = 0;
			for (Player act :modelReader.getTableOrder()) {
				this.village[i++] = modelReader.getSettlements(act, BuildingType.Village).size();
			}
			break;
		case Town:
			int i2 = 0;
			for (Player act :modelReader.getTableOrder()) {
				this.town[i2++] = modelReader.getSettlements(act, BuildingType.Town).size();
			}
			break;
		}
	}

	
	@Override
	public void eventRobber() {
		deactivateUI();
		if (!observer) {
			if (modelReader.getMe().getResources().size() > 7) {
				ResourcePackage res = Client.returnResources(modelReader.getResources().copy());
				returnResources.setRes(res);
				controllerAdapter.addGuiEvent(returnResources);
			}
			if (modelReader.getMe() == modelReader.getCurrentPlayer()) {
				selectionPoint = Model.getLocationListField(modelReader.getRobberFields());
				selectionMode = ROBBER_SELECT;
				console4 = "You have to select a Robber to move!";
			}
		}
	}

	@Override
	public void eventTrade(ResourcePackage resourcePackage) {
		if (!observer) {
		boolean decision = Client.incomingTradeOffer(modelReader.getResources().copy(),resourcePackage);
		respondTrade.setDecision(decision);
		controllerAdapter.addGuiEvent(respondTrade);
		}
	}

	@Override
	public void eventNewRound(int number) {
		if (modelReader.getCurrentPlayer() == modelReader.getMe() && !observer
			&& !(selectionMode == ROBBER_SELECT || selectionMode == ROBBER_PLACE || selectionMode == ROBBER_PLAYER_SELECT) ) {
			reinitiateUI();
		}
		else {
			deactivateUI();
		}
		if (!(selectionMode == ROBBER_SELECT || selectionMode == ROBBER_PLACE || selectionMode == ROBBER_PLAYER_SELECT)) {
			console4 = "";
		}
		this.lastNumberDiced = number;
	}
	

	@Override
	public void updateTradePossibilities() {
		//TODO: implement it!
	}
	

	@Override
	public void eventPlayerLeft(long playerID) {
		playerNames.put(modelReader.getPlayerMap().get(playerID),"Left the Game");
	}
	
	
	@Override
	public void run() {
		try {
			init();
			barrier.await();
			while (!finished) {
				  Display.update();
				  render();
				  handleInput();
			      if (Display.isCloseRequested()) {
			    	  finished = true;
			      } 
			      else if (Display.isActive()) {
			          render();
			          Display.sync(60);
			      } 
			      else {
			    	  Thread.sleep(100);
			      }
			      if (Display.isVisible() || Display.isDirty()) {
			          render();
			      }
			}
			Display.destroy();
			controllerAdapter.leaveMatch();
			Client.backToLobby();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
		

	@SuppressWarnings("unchecked")
	private void init() {
		try {
			Display.setDisplayMode(Setting.getDisplayMode());
			Display.setTitle("Die Siedler von der Saar: " + getName(modelReader.getMe()) + "@" + gameTitle);
			Display.setFullscreen(Setting.isFullscreen());
			Display.create();
			initiateTextures();
			
			claimLongestRoad = new Clickable("ClaimLongestRoad", xOffsetUI+450, yOffsetUI+22, 2, 373, 77, false, true) {
				@Override
				public void executeUI() {
					deactivateUI();
					ret = null;
					selected = longestroad.get(0);
					Client.selectLongestRoad();
				}
				@Override
				public void executeController() {
					controllerAdapter.claimLongestRoad(getRoad());
				}
			};

			claimVictory = new Clickable("ClaimVictory", xOffsetUI+673, yOffsetUI+22, 2, 185, 77, false, true) {
				@Override
				public void executeUI() {
					deactivateUI();
					controllerAdapter.addGuiEvent(this);
					console4 = "You have won the match.";
					console5 = "Congratulations. Press quit";
					console6 = "to leave";
				}
				@Override
				public void executeController() {
					controllerAdapter.claimVictory();
				}
			};
		
			endTurn = new Clickable("EndTurn", xOffsetUI+828, yOffsetUI+22, 2, 185, 77, false, true) {
				@Override
				public void executeUI() {
					this.setActive(false);
					controllerAdapter.addGuiEvent(this);
				}
				@Override
				public void executeController() {
					controllerAdapter.endTurn();
				}
			};
			
			offerTrade = new Clickable("offerTrade", xOffsetUI+450, yOffsetUI+65, 2, 185, 77, false, true) {
				@Override
				public void executeUI() {
					deactivateUI();
					ResourcePackage res = Client.tradeOffer(modelReader.getResources(), modelReader.getHarborTypes(modelReader.getMe()));
					reinitiateUI();
					//TODO anzahl der handel mitzählen?!
					if (res != null) {
						deactivateUI();
						this.setRes(res);
						controllerAdapter.addGuiEvent(this);
					}
				}
				@Override
				public void executeController() {
					long player = controllerAdapter.offerTrade(getRes());
					String name = "Nobody";
					if (player != -1 && player != -2) {
						name = playerNames.get(modelReader.getPlayerMap().get(player));
					}
					else if(player == -2) {
						name = "Bank";
					}
					console4 = "You traded with "+ name;
					reinitiateUI();
				}
			};
			
			buildStreet = new Clickable("BuildStreet", xOffsetUI+450, yOffsetUI+110, 2, 185, 77, false, true) {
				@Override
				public void executeUI() {
					if (selectionMode == STREET) {
						selectionMode = NONE;
					}
					else {
						selectionLocation = Model.getLocationListPath(modelReader.buildableStreetPaths(modelReader.getMe()));
						if (selectionLocation.size() != 0)
							selectionMode = STREET;
					}
				}
				@Override
				public void executeController() {
					controllerAdapter.buildStreet(getPath());
				}
			};
			
			buildVillage = new Clickable("BuildVillage", xOffsetUI+450, yOffsetUI+155, 2, 185, 77, false, true) {
				@Override
				public void executeUI() {
					if (selectionMode == VILLAGE) {
						selectionMode = NONE;
					}
					else {
						selectionLocation = Model.getLocationListIntersection(modelReader.buildableVillageIntersections(modelReader.getMe()));
						if (selectionLocation.size() != 0)
							selectionMode = VILLAGE;
					}
				}
				@Override
				public void executeController() {
					controllerAdapter.buildSettlement(getIntersection(), BuildingType.Village);
				}
			};
			
			buildTown = new Clickable("BuildTown", xOffsetUI+450, yOffsetUI+200, 2, 185, 77, false, true) {
				@Override
				public void executeUI() {
					if (selectionMode == TOWN) {
						selectionMode = NONE;
					}
					else {
						selectionLocation = Model.getLocationListIntersection(modelReader.buildableTownIntersections(modelReader.getMe()));
						if (selectionLocation.size() != 0)
							selectionMode = TOWN;
					}
				}
				@Override
				public void executeController() {
					controllerAdapter.buildSettlement(getIntersection(), BuildingType.Town);
				}
			};
						
			buildCatapult = new Clickable("BuildCatapult", xOffsetUI+450, yOffsetUI+245, 2, 185, 77, false, true) {
				@Override
				public void executeUI() {
					if (selectionMode == CATAPULT_BUILD) {
						selectionMode = NONE;
					}
					else {
						selectionLocation = Model.getLocationListPath(modelReader.buildableCatapultPaths(modelReader.getMe()));
						if (selectionLocation.size() != 0)
							selectionMode = CATAPULT_BUILD;
					}
				}
				@Override
				public void executeController() {
					boolean result = controllerAdapter.buildCatapult(getPath());
					if (!result) {
						console4 = "Catapult was build but defeated";
					}
					reinitiateUI();
				}
			};
			
			catapultAction = new Clickable(null, 0, 0, 0, 0, 0, false, false) {
				@Override
				public void executeUI() {}
				@Override
				public void executeController() {
					if (getPath2() != null) {
						boolean result = controllerAdapter.moveCatapult(getPath(), getPath2());
						if (!result) {
							console4 = "Catapult was defeated";
						}
					}
					else {
						AttackResult r = controllerAdapter.attackSettlement(getPath(), getIntersection());
						switch(r) {
							case DEFEAT:
								console4 = "You lost your catapult!";
								break;
							case DRAW:
								console4 = "AttackResult: Draw";
								break;
							case SUCCESS:
								console4 = "Attack successful";
								break;
						}
					}
					reinitiateUI();
				}
			};
			
			setRobber = new Clickable(null, 0, 0, 0, 0, 0, false, false) {
				@Override
				public void executeUI() {}
				@Override
				public void executeController() {
					Resource stolen = controllerAdapter.moveRobber(getField(), getField2(), getPlayer());
					if (stolen != null)	
						console4 = "You got: " +stolen;
					reinitiateUI();
				}
			};
			
			returnResources = new Clickable(null, 0, 0, 0, 0, 0, false, false) {
				@Override
				public void executeUI() {}
				@Override
				public void executeController() {
					controllerAdapter.returnResources(getRes());
				}
			};
			
			respondTrade = new Clickable(null, 0, 0, 0, 0, 0, false, false) {
				@Override
				public void executeUI() {}
				@Override
				public void executeController() {
					String name = "Nobody";
					long player = controllerAdapter.respondTrade(this.isDecision());
					if (player != -1 && player != -2) {
						name = playerNames.get(modelReader.getPlayerMap().get(player));
					}
					else if(player == -2) {
						name = "Bank";
					}
					String current = playerNames.get(modelReader.getCurrentPlayer());

					console4 = current + " traded with "+ name;
				} 
			};
			
			quit = new Clickable("Quit", xOffsetUI+983, yOffsetUI+22, 2, 100, 77, true, true) {
				@Override
				public void executeUI() {
					finished = true;
				}
				@Override
				public void executeController() {}
			};
			
		} catch (Exception e) {e.printStackTrace();}
		
		if (!observer && modelReader.getMe() == modelReader.getCurrentPlayer()) {
			init = true;
			buildVillage.setActive(true);
			console4 = modelReader.getInitVillages() + " initial villages left";
		}
		
		GL11.glDisable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameterf( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameterf( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glClearDepth(1.0f); // Depth Buffer Setup
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(+45.0f, aspectRatio, 0.1f, getOrgZ()+2500);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
        Keyboard.enableRepeatEvents(true);
        Mouse.poll();
        
        Font awtFont = new Font("Arial", Font.BOLD, 72);
        debugFont = new UnicodeFont(awtFont, 22, false, false);
        debugFont.addAsciiGlyphs();
        debugFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); 
        try {
			debugFont.loadGlyphs();
		} catch (SlickException e1) {e1.printStackTrace();}
		
        uiFont20 = new UnicodeFont(awtFont, 20, false, false);
        uiFont20.addAsciiGlyphs();
        uiFont20.getEffects().add(new ColorEffect(java.awt.Color.BLACK)); 
        try {
			uiFont20.loadGlyphs();
		} catch (SlickException e1) {e1.printStackTrace();}

        uiFont40 = new UnicodeFont(awtFont, 40, false, false);
        uiFont40.addAsciiGlyphs();
        uiFont40.getEffects().add(new ColorEffect(java.awt.Color.BLACK)); 
        try {
			uiFont40.loadGlyphs();
		} catch (SlickException e1) {e1.printStackTrace();}
        
        Display.update();
        render();
        Display.update();
        render();
        Display.update();
        render();
        //render two/three times to fill the double/triple buffer
	}
	

	private void handleInput() throws InterruptedException {
		int INPUT_WAIT_TIME = 250;
		int mx = Mouse.getX();
		int my = Mouse.getY();
		float oglx = mx*screenToOpenGLx(zOffsetUI)+25;
		float ogly = (windowHeight-my)*screenToOpenGLy(zOffsetUI)+380;
		
		//handle longestroad fenster eingabe
		if (ret != null && ret == selected) {
			claimLongestRoad.setRoad(ret);
			controllerAdapter.addGuiEvent(claimLongestRoad);
			reinitiateUI();
			claimLongestRoad.setActive(false);
			ret = null;
			selected = null;
		}
		else if (ret != null && ret != selected) {
			reinitiateUI();
			ret = null;
			selected = null;
		}
		
		if (Mouse.isButtonDown(0) && System.currentTimeMillis() - lastinputcheck > INPUT_WAIT_TIME ) {
			lastinputcheck = System.currentTimeMillis();
			if (!(oglx > xOffsetUI && oglx < xOffsetUI+1281 && ogly > yOffsetUI && ogly < yOffsetUI+240)) {
				switch (selectionMode) {
					case NONE:
						Path source = getMousePath();
						if (source != null && modelReader.getCatapults(modelReader.getMe()).contains(source)) {
							catapultAction.setPath(source);
							boolean moveRes = modelReader.affordableCatapultAttack() > 0;
							boolean attackRes = modelReader.affordableSettlementAttack() > 0;
							if (moveRes) {
								selectionLocation = Model.getLocationListPath(modelReader.attackableCatapults(source));
								selectionLocation2 = Model.getLocationListPath(modelReader.catapultMovePaths(source));
							}
							else {
								selectionLocation = new LinkedList<Location>();
								selectionLocation2 = new LinkedList<Location>();
							}
							if (attackRes) {
								selectionLocation3 = Model.getLocationListIntersection(modelReader.attackableSettlements(BuildingType.Village, source));
								selectionLocation3.addAll(Model.getLocationListIntersection(modelReader.attackableSettlements(BuildingType.Town, source)));
							}
							else {
								selectionLocation3 = new LinkedList<Location>();
							}
							if (selectionLocation.size()!=0 || selectionLocation2.size()!=0 || selectionLocation3.size()!=0 )
								selectionMode = CATAPULT_ACTION_DST;
						}
						break;
					case ROBBER_SELECT:
						Field robberSRC = getMouseField();
						if (robberSRC != null && selectionPoint.contains(Model.getLocation(robberSRC))) {
							setRobber.setField(robberSRC);
							selectionPoint = Model.getLocationListField(modelReader.canPlaceRobber());
							selectionMode = ROBBER_PLACE;
							console4 = "Now place the Robber on another Field!";
						}
						break;
					case ROBBER_PLACE:
						Field robberDST = getMouseField();
						if (robberDST != null && selectionPoint.contains(Model.getLocation(robberDST))) {
							setRobber.setField2(robberDST);
							selectionLocation = Model.getLocationListIntersection(modelReader.getIntersectionsFromField(robberDST));
							
							//remove my own intersections from list
							Iterator<Location> iter = selectionLocation.iterator();
							while (iter.hasNext()) {
								Intersection tmp = modelReader.getIntersection(iter.next());
								if (tmp.hasOwner() && tmp.getOwner() == modelReader.getMe()) {
									iter.remove();
								}
							}
							
							selectionMode = ROBBER_PLAYER_SELECT;
							console4 = "Now choose a Player to Rob or";
							console5 = "click on an empty Intersection";
							console6 = "to rob nobody";
						}
						break;
					case ROBBER_PLAYER_SELECT:
						Intersection player = getMouseIntersection();
						if (player != null && selectionLocation.contains(Model.getLocation(player))) {
							if (player.hasOwner())
								setRobber.setPlayer(player.getOwner());
							selectionMode = NONE;
							console4 = "";
							console5 = "";
							console6 = "";
							controllerAdapter.addGuiEvent(setRobber);
						}
						break;
					case VILLAGE:
						Intersection village = getMouseIntersection();
						if (village != null && selectionLocation.contains(Model.getLocation(village))) {
							buildVillage.setIntersection(village);
							controllerAdapter.addGuiEvent(buildVillage);
							selectionMode = NONE;
							console4 = "";
							if (init) {
								buildStreet.setActive(true);
								buildVillage.setActive(false);
								console4 = (modelReader.getInitVillages()-modelReader.getSettlements(modelReader.getMe(), BuildingType.Village).size()-1) + " initial villages left";
							}
						}
						break;
					case TOWN:
						Intersection town = getMouseIntersection();
						if (town != null && selectionLocation.contains(Model.getLocation(town))) {
							buildTown.setIntersection(town);
							selectionMode = NONE;
							console4 = "";
							controllerAdapter.addGuiEvent(buildTown);
						}
						break;
					case CATAPULT_BUILD:
						Path path = getMousePath();
						if (path != null && selectionLocation.contains(Model.getLocation(path))) {
							buildCatapult.setPath(path);
							selectionMode = NONE;
							console4 = "";
							deactivateUI();
							controllerAdapter.addGuiEvent(buildCatapult);
						}
						break;
					case CATAPULT_ACTION_DST:
						Intersection destInter = getMouseIntersection();
						// Attack Intersection
						if (destInter != null && selectionLocation3.contains(Model.getLocation(destInter))) {
							catapultAction.setIntersection(destInter);
							catapultAction.setPath2(null);
							selectionMode = NONE;
							deactivateUI();
							controllerAdapter.addGuiEvent(catapultAction);
							break;
						}
						
						Path destPath = getMousePath();
						// deactivate selection mode
						if (destPath == catapultAction.getPath()) {
							selectionMode = NONE;
						}
						// move to path 
						else if (modelReader.getCatapults(modelReader.getMe()).contains(destPath)){
							catapultAction.setPath(destPath);
							selectionLocation = Model.getLocationListPath(modelReader.attackableCatapults(destPath));
							selectionLocation2 = Model.getLocationListPath(modelReader.catapultMovePaths(destPath));
							selectionLocation3 = Model.getLocationListIntersection(modelReader.attackableSettlements(BuildingType.Village, destPath));
							selectionLocation3.addAll(Model.getLocationListIntersection(modelReader.attackableSettlements(BuildingType.Town, destPath)));
							if (selectionLocation.size()==0 && selectionLocation2.size()==0 && selectionLocation3.size()==0)
								selectionMode = NONE;
						}
						// Attack catapult
						else if(selectionLocation.contains(Model.getLocation(destPath)) || selectionLocation2.contains(Model.getLocation(destPath))){
							catapultAction.setPath2(destPath);
							selectionMode = NONE;
							deactivateUI();
							controllerAdapter.addGuiEvent(catapultAction);
						}
						break;	
					case STREET:
						Path street = getMousePath();
						if (street != null && selectionLocation.contains(Model.getLocation(street))) {
							buildStreet.setPath(street);
							selectionMode = NONE;
							if (init) {
								buildStreet.setActive(false);
								buildVillage.setActive(false);
								init = false;
							}
							else 
								console4 = "";
							controllerAdapter.addGuiEvent(buildStreet);
						}
						break; 
				}
			}
			for (Clickable c : Clickable.executeClicks(mx*screenToOpenGLx(zOffsetUI)+25, (windowHeight-my)*screenToOpenGLy(zOffsetUI)+380)) {
				c.executeUI();
			}
		}

		if (buildStreet.isActive() && Keyboard.isKeyDown(Keyboard.KEY_1) && System.currentTimeMillis() - lastinputcheck > INPUT_WAIT_TIME) {
			buildStreet.executeUI();
			lastinputcheck = System.currentTimeMillis();
		}
		else if (buildVillage.isActive() && Keyboard.isKeyDown(Keyboard.KEY_2) && System.currentTimeMillis() - lastinputcheck > INPUT_WAIT_TIME) {
			buildVillage.executeUI();
			lastinputcheck = System.currentTimeMillis();
		}
		else if (buildTown.isActive() && Keyboard.isKeyDown(Keyboard.KEY_3) && System.currentTimeMillis() - lastinputcheck > INPUT_WAIT_TIME) {
			buildTown.executeUI();
			lastinputcheck = System.currentTimeMillis();
		}
		else if (buildCatapult.isActive() && Keyboard.isKeyDown(Keyboard.KEY_4) && System.currentTimeMillis() - lastinputcheck > INPUT_WAIT_TIME) {
			buildCatapult.executeUI();
			lastinputcheck = System.currentTimeMillis();
		}
	
		
		
		if (Mouse.isInsideWindow()) {
			if (mx < 50) {
				camLeft();
			}
			else if (mx > windowWidth-50) {
				camRight();
			}
			if (my < 50 && (mx < 200 || mx > windowWidth-200)) {
				camDown();
			}
			else if (my > windowHeight-50 ) {
				camTop();
			}
		}
		
		if (Mouse.next()) {
			if (Mouse.getEventDWheel() < 0) {
				camIn();
			}
			else if (Mouse.getEventDWheel() > 0) {	
				camOut();
			}
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			camLeft();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			camRight();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			camTop();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			camDown();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_N)) {
			camIn();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
			camOut();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
			resetCamera();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			selectionMode = NONE;
		}
	}
	
	public Field getMouseField() {
		int tmpx = getOglx();
		int tmpy = getOgly();
		int ergx,ergy;
		if (tmpy > 0) {
			switch((tmpy/215) % 2) {
			case 1:
				tmpx-=-125;
				break;
			case 0:
				break;
			}
		}
		else {
			switch((tmpy/215) % 2) {
			case -1:
				break;
			case 0:
				tmpx-=-125;
				break;
			}
		}
		
		if (tmpx > 0) {
			ergx = tmpx/250;
		}
		else {
			ergx = tmpx/250-1;
		}
		if (tmpy > 0) {
			ergy = tmpy/215;
		}
		else {
			ergy = tmpy/215-1;
		}
		if (ergx >= -1 && ergx < modelReader.getBoardWidth()+1 && ergy >= -1 && ergy < modelReader.getBoardHeight()+1)
			return modelReader.getField(new Point(ergy, ergx));
		else 
			return null;
		//TODO evtl verbessern da nur klappt wenn man ca genau in die mitte klickt
	}
	
	
	public Intersection getMouseIntersection() {
		int tmpx = getOglx();
		int tmpy = getOgly();
		int ergx,ergy;
		int ergo = -1;
		if (tmpy > 0) {
			switch((tmpy/215) % 2) {
			case 1:
				tmpx-=-125;
				break;
			case 0:
				break;
			}
		}
		else {
			switch((tmpy/215) % 2) {
			case -1:
				break;
			case 0:
				tmpx-=-125;
				break;
			}
		}
		
		if (tmpx > 0) {
			ergx = tmpx/250;
		}
		else {
			ergx = tmpx/250-1;
		}
		tmpx = Math.abs(tmpx%250);
		if (tmpy > 0) {
			ergy = tmpy/215;
		}
		else {
			ergy = tmpy/215-1;
		}
		tmpy = Math.abs(tmpy%215);
		
		if (tmpx > 85 && tmpx < 165) {
			if (tmpy < 40)
				ergo = 0;
			if (tmpy > 245)
				ergo = 3;
		}
		else if (tmpy > 30 && tmpy < 110) {
			if (tmpx > 210)
				ergo = 1;
			if (tmpx < 40)
				ergo = 5;
		}
		else if (tmpy > 175 && tmpy < 255) {
			if (tmpx > 210)
				ergo = 2;
			if (tmpx < 40)
				ergo = 4;
		}
		
		if (ergx >= -1 && ergx < modelReader.getBoardWidth()+1 && ergy >= -1 && ergy < modelReader.getBoardHeight()+1 && ergo != -1)
			return modelReader.getIntersection(new Location(ergy, ergx, ergo));
		else 
			return null;
	}
	
	
	public Path getMousePath() {
		int tmpx = getOglx();
		int tmpy = getOgly();
		int ergx,ergy;
		int ergo = -1;
		if (tmpy > 0) {
			switch((tmpy/215) % 2) {
			case 1:
				tmpx-=-125;
				break;
			case 0:
				break;
			}
		}
		else {
			switch((tmpy/215) % 2) {
			case -1:
				break;
			case 0:
				tmpx-=-125;
				break;
			}
		}
		
		if (tmpx > 0) {
			ergx = tmpx/250;
		}
		else {
			ergx = tmpx/250-1;
		}
		tmpx = Math.abs(tmpx%250);
		if (tmpy > 0) {
			ergy = tmpy/215;
		}
		else {
			ergy = tmpy/215-1;
		}
		tmpy = Math.abs(tmpy%215);
		
		if (tmpy < 70) {
			if (tmpx < 125)
				ergo = 5;
			else 
				ergo = 0;
		}
		else if (tmpy >= 70 && tmpy < 215) {
			if (tmpx < 60)
				ergo = 4;
			if (tmpx > 190)
				ergo = 1;
		}
		else {
			if (tmpx < 125)
				ergo = 3;
			else
				ergo = 2;
		}
		
		if (ergx >= -1 && ergx < modelReader.getBoardWidth()+1 && ergy >= -1 && ergy < modelReader.getBoardHeight()+1 && ergo != -1)
			return modelReader.getPath(new Location(ergy, ergx, ergo));
//			System.out.println(modelReader.getPath(new Location(ergy, ergx, ergo)));
		else 
			return null;
	}
	
	public static void main(String[] args) throws Exception {		
				
		String[] list = new String[] {
				"jinput-dx8_64.dll", "jinput-dx8.dll", "jinput-raw_64.dll",
				"jinput-raw.dll", "libjinput-linux.so", "libjinput-linux64.so",
				"libjinput-osx.jnilib", "liblwjgl.jnilib", "liblwjgl.so",
				"liblwjgl64.so", "libopenal.so", "libopenal64.so",
				"lwjgl.dll", "lwjgl64.dll", "openal.dylib", "OpenAL32.dll", "OpenAL64.dll" };
		String tmpdir = System.getProperty("java.io.tmpdir");
		
		boolean everythingIsBad = true;
		while (everythingIsBad) {
			everythingIsBad = false;
			for (String act : list) {
				InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("native/" + act);
				Random r = new Random();
	
				try {
					saveFile(tmpdir + "/" + act, input);
				} catch (IOException e) {
					//e.printStackTrace();
					tmpdir = System.getProperty("java.io.tmpdir") + r.nextInt();
					new File(tmpdir).mkdirs();
					everythingIsBad = true;
					break;
				}
			}
		}
		String seperator;
		if (System.getProperty("sun.desktop") != null && System.getProperty("sun.desktop").equals("windows")) seperator = ";";
		else seperator = ":";
		System.setProperty("java.library.path", System.getProperty("java.library.path") + seperator + tmpdir);
		java.lang.reflect.Field vvv = ClassLoader.class.getDeclaredField("sys_paths");
		vvv.setAccessible(true); 
		vvv.set(null, null);
		
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
		/*WorldRepresentation worldrep = new WorldRepresentation(20, 2, 2, 9, 5, 4,  
				new byte[] {0,1,2,3,4,5,6,5,4,3,2,1,0,1,2,3,4,5,6,5,
					        4,3,2,1,0,1,2,3,4,5,6,5,4,3,2,1,0,1,2,3},
				new byte[] {1,4,
							2,5,    
							3,6},
				new byte[] {});*/
		
		Model model = new Model(/*worldrep*/WorldRepresentation.getDefault(), matchinfo, 0);
		model.matchStart(new long[] {3,2,1,0}, new byte[]   {2,3,4,
		 6,8,9,10,
		 11,12,11,10,
		 9,8,6,5,
		 2,6,9});
		/*model.matchStart(new long[] {3,2,1,0}, new byte[]   {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,
															 2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2});*/
		Map<Player,String> names = new HashMap<Player,String>();
		names.put(model.getTableOrder().get(3), "Ichbinkeinreh");
		names.put(model.getTableOrder().get(2), "Herbert");
		names.put(model.getTableOrder().get(1), "Hubert");
		names.put(model.getTableOrder().get(0), "Hannes");
		model.getTableOrder().get(3).modifyResources(new ResourcePackage(100,100,100,2,10));
		
		model.buildSettlement(new Location(3,3,0), BuildingType.Village);
		model.buildStreet(new Location(3,3,0));
		
		model.buildSettlement(new Location(3,3,2), BuildingType.Village);
		model.buildStreet(new Location(3,3,2));
		
		model.buildSettlement(new Location(3,3,4), BuildingType.Village);
		model.buildStreet(new Location(3,3,4));
		
		model.buildSettlement(new Location(2,2,0), BuildingType.Village);
		model.buildStreet(new Location(2,2,0));
		
		model.newRound(3);
		
		model.buildSettlement(new Location(3,3,0), BuildingType.Town);
		model.buildCatapult(new Location(3,3,0), true);
		
		model.newRound(2);
		model.newRound(2);
		model.newRound(2);
		
		model.buildStreet(new Location(1,2,2));
		model.buildStreet(new Location(2,1,1));
		model.buildSettlement(new Location(2,2,0), BuildingType.Town);
		model.buildCatapult(new Location(2,2,0), true);
		model.getPath(new Location(3,3,2)).createCatapult(model.getMe());
		model.getPath(new Location(4,3,4)).createCatapult(model.getTableOrder().get(0)); 
		
//		model.getPath(new Location(-1,-1,0)).createStreet(model.getMe());
//		model.getPath(new Location(-1,-1,1)).createStreet(model.getMe());
//		model.getPath(new Location(-1,-1,2)).createStreet(model.getMe());
//		model.getPath(new Location(-1,-1,3)).createStreet(model.getMe());
//		model.getPath(new Location(-1,-1,4)).createStreet(model.getMe());
//		model.getPath(new Location(-1,-1,5)).createStreet(model.getMe());
		
//		model.getPath(new Location(-1,-1,0)).createCatapult(model.getMe());
//		model.getPath(new Location(-1,-1,1)).createCatapult(model.getMe());
//		model.getPath(new Location(-1,-1,2)).createCatapult(model.getMe());
//		model.getPath(new Location(-1,-1,3)).createCatapult(model.getMe());
//		model.getPath(new Location(-1,-1,4)).createCatapult(model.getMe());
//		model.getPath(new Location(-1,-1,5)).createCatapult(model.getMe());
//		
//		model.getIntersection(new Location(-1,-1,0)).createBuilding(BuildingType.Town, model.getMe());
//		model.getIntersection(new Location(-1,-1,1)).createBuilding(BuildingType.Town, model.getMe());
//		model.getIntersection(new Location(-1,-1,2)).createBuilding(BuildingType.Town, model.getMe());
//		model.getIntersection(new Location(-1,-1,3)).createBuilding(BuildingType.Town, model.getMe());
//		model.getIntersection(new Location(-1,-1,4)).createBuilding(BuildingType.Town, model.getMe());
//		model.getIntersection(new Location(-1,-1,5)).createBuilding(BuildingType.Town, model.getMe());
		
//		model.getField(new Point(2,2)).setRobber(true);
		
//		Setting.setSetting(Display.getDesktopDisplayMode(), true, PlayerColors.RED);
		Setting.setSetting(new DisplayMode(1024, 515), false, PlayerColors.YELLOW);  /// Display.getDesktopDisplayMode()
		
		CyclicBarrier barrier = new CyclicBarrier(2);
		GameGUI gameGUI = new GameGUI(model, null, names, "TestSpiel", false, barrier, null);
		new Thread(gameGUI).start();
		barrier.await();
	}
	
	
	public static void saveFile(String filename, InputStream inputStr) throws IOException {
		FileOutputStream fos = new FileOutputStream(filename);
	    int len = -1;
	    byte[] buffer = new byte[4194304]; //4MB
	    while ((len = inputStr.read(buffer)) != -1) {
	      fos.write(buffer,0,len);
	    }
	    fos.close();
	}
	
	@Override
	public void initTurn() {
		init = true;
		buildVillage.setActive(true);
	}

	@Override
	public void eventMatchEnd(long winnerID) {
		console7 = getName(modelReader.getPlayerMap().get(winnerID)) + " is the winner!";
	}
		
	/*
		String[] list = new String[] {
				"jinput-dx8_64.dll", "jinput-dx8.dll", "jinput-raw_64.dll",
				"jinput-raw.dll", "libjinput-linux.so", "libjinput-linux64.so",
				"libjinput-osx.jnilib", "liblwjgl.jnilib", "liblwjgl.so",
				"liblwjgl64.so", "libopenal.so", "libopenal64.so",
				"lwjgl.dll", "lwjgl64.dll", "openal.dylib", "OpenAL32.dll", "OpenAL64.dll" };
		String tmpdir = System.getProperty("java.io.tmpdir");
		
		boolean everythingIsBad = true;
		while (everythingIsBad) {
			everythingIsBad = false;
			for (String act : list) {
				InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("native/" + act);
				Random r = new Random();
	
				try {
					saveFile(tmpdir + "/" + act, input);
				} catch (IOException e) {
					e.printStackTrace();
					tmpdir = System.getProperty("java.io.tmpdir") + r.nextInt();
					new File(tmpdir).mkdirs();
					everythingIsBad = true;
					break;
				}
			}
		}
		String seperator;
		if (System.getProperty("sun.desktop") != null && System.getProperty("sun.desktop").equals("windows")) seperator = ";";
		else seperator = ":";
		System.setProperty("java.library.path", System.getProperty("java.library.path") + seperator + tmpdir);
		java.lang.reflect.Field vvv = ClassLoader.class.getDeclaredField("sys_paths");
		vvv.setAccessible(true); 
		vvv.set(null, null);
		
		Setting setting = new Setting(new DisplayMode(1024,580), true, PlayerColors.RED);
		GameGUI gameGUI = null;
		try {
			gameGUI = new GameGUI(model, null, null, setting, true);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		new Thread(gameGUI).start();
		*/
	
//	   GL11.glPushMatrix();
//	   GL11.glTranslatef((int)(Mouse.getX()*screenToOpenGLx(zOffsetUI)+25), (int)((windowHeight-Mouse.getY())*screenToOpenGLy(zOffsetUI)+380), 0);
//	   uiTextureMap.get("PlayerBackgroundHighlight").bind();
//	   drawSquareLeftTop(159, 37);
//	   GL11.glPopMatrix();
	
}
