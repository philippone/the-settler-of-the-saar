package de.unisaarland.cs.sopra.common.view;

import static de.unisaarland.cs.sopra.common.PlayerColors.BLACK;
import static de.unisaarland.cs.sopra.common.PlayerColors.BLUE;
import static de.unisaarland.cs.sopra.common.PlayerColors.BROWN;
import static de.unisaarland.cs.sopra.common.PlayerColors.ORANGE;
import static de.unisaarland.cs.sopra.common.PlayerColors.PURPLE;
import static de.unisaarland.cs.sopra.common.PlayerColors.RED;
import static de.unisaarland.cs.sopra.common.PlayerColors.WHITE;
import static de.unisaarland.cs.sopra.common.PlayerColors.YELLOW;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
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
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import de.unisaarland.cs.sopra.common.PlayerColors;
import de.unisaarland.cs.sopra.common.Setting;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
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
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class GameGUI extends View implements Runnable{

	private Map<Player, String> playerNames;
	private String gameTitle;
	private Setting setting;
	private Map<FieldType,Texture> fieldTextureMap;
	private HashMap<BuildingType, Texture> intersectionTextureMap;
	private Map<Integer,Texture> numberTextureMap;
	private Map<String,Texture> uiTextureMap;
	private List<Field> renderFieldList;
	private Texture streetTexture;
	private Texture catapultTexture;
	private Texture robberTexture;
	private Texture pathMarkTexture;
	private Texture fieldMarkTexture;
	private Texture intersectionMarkTexture;
	private int x,y,z;
	private int maxX, maxY, maxZ;
	private int minX, minY, minZ;
	private UnicodeFont debugFont;
	private UnicodeFont uiFont20;
	private UnicodeFont uiFont40;

	private int selectionMode;
	private static final int NONE = 0;
	private static final  int INTERSECTIONS = 1;
	private static final  int PATHS = 2;
	private static final  int FIELDS = 3;
	private List<Point> selectionPoint;
	private List<Location> selectionLocation;
	
	private int uiMode;
	private static final int RESOURCE_VIEW = 0;
	private static final int TRADE_VIEW = 1;
	private static final int BUILD_VIEW = 2;
	
	private static int windowWidth;
	private static int windowHeight;
	private static float aspectRatio;
	private static int xOffset;
	private static int xOffsetUI, yOffsetUI, zOffsetUI;
	
	private int[] village;
	private int[] town;
	private int[] catapult;
	private int[] road;
	
	private Clickable claimLongestRoad;
	private Clickable claimVictory;
	private Clickable endTurn;
	private Clickable offerTrade;
	private Clickable buildStreet;
	private Clickable buildVillage;
	private Clickable buildTown;	
	private Clickable buildCatapult;
	
	private boolean observer;
	private int lastNumberDiced;
	
	private Map<Player,PlayerColors> colorMap;
	
	private CyclicBarrier barrier;

	
	public GameGUI(ModelReader modelReader, ControllerAdapter controllerAdapter, Map<Player, String> names, Setting setting, String gameTitle, boolean observer, CyclicBarrier barrier) {
		super(modelReader, controllerAdapter);
		this.modelReader.addModelObserver(this);
		this.playerNames = names;
		this.setting = setting;
		this.uiMode = RESOURCE_VIEW;
		this.selectionMode = NONE;
		this.gameTitle = gameTitle;
		this.catapult = new int[names.size()];
		this.village = new int[names.size()];
		this.town = new int[names.size()];
		this.road = new int[names.size()];
		this.observer = observer;
		this.barrier = barrier;
		windowWidth = Setting.getDisplayMode().getWidth();
		windowHeight = Setting.getDisplayMode().getHeight();
		aspectRatio = ((float)windowWidth)/windowHeight;
		xOffset = (int)(366*aspectRatio);
		xOffsetUI = (int)(((870*aspectRatio)-1075)/2);
		yOffsetUI = 955;
		zOffsetUI = -950;
		//center to the area where the first field is drawn
		this.x = (int)(812.8125f*aspectRatio);
		this.y = 745;
		this.z = -1450;
		//center camera on map according to map size
		this.x += (modelReader.getBoardWidth()-1)*-106;
		this.y += (modelReader.getBoardHeight()-1)*-170;
		this.z += Math.max((modelReader.getBoardWidth()-1)*450,(modelReader.getBoardHeight()-1)*450);
	
		//set the max and min for camera
		this.maxZ = this.z;
		this.minZ = -1500;
		//TODO: set and use min,max for x,y 
		
		List<PlayerColors> tmp = new LinkedList<PlayerColors>();
		tmp.addAll(Arrays.asList(new PlayerColors[] {RED,BLUE,YELLOW,ORANGE,BROWN,WHITE,PURPLE,BLACK}));
		tmp.remove(Setting.getPlayerColor());
		
		//set color of players
		colorMap = new HashMap<Player,PlayerColors>();
		Iterator<Player> iterP = modelReader.getTableOrder().iterator();
		Iterator<PlayerColors> iterC = tmp.iterator();
		while (iterP.hasNext()) {
			Player act = iterP.next();
			if (act == modelReader.getMe()) {
				colorMap.put(act, Setting.getPlayerColor());
			}
			else {
				colorMap.put(act, iterC.next());
			}
		}
		
		int i = 0;
		for (Player act :modelReader.getTableOrder()) {
			this.village[i] = modelReader.getSettlements(act, BuildingType.Village).size();
			this.town[i] = modelReader.getSettlements(act, BuildingType.Town).size();
			this.catapult[i] = modelReader.getCatapults(act).size();
			List<List<Path>> streets = modelReader.calculateLongestRoads(act);
			this.road[i++] = streets.size() > 0 ? streets.get(0).size() : 0;
		}
	}
	
	private static int screenToOpenGLx (int zoom) {
//		viewportXwidth = (int)(870*aspectRatio);
//		viewportYwidth = 870;
//		viewportXwidthZoom = (int)(870*aspectRatio);
//		viewportYwidthZoom = 870;
//		screenToOpenGLX = ((float)(870*aspectRatio)/windowWidth);
//		screenToOpenGLY = ((float)870/windowHeight);
			return (int)((float)(870*aspectRatio)/windowWidth);
		}
	
	private static int screenToOpenGLy (int zoom) {
//		viewportXwidth = (int)(870*aspectRatio);
//		viewportYwidth = 870;
//		viewportXwidthZoom = (int)(870*aspectRatio);
//		viewportYwidthZoom = 870;
//		screenToOpenGLX = ((float)(870*aspectRatio)/windowWidth);
//		screenToOpenGLY = ((float)870/windowHeight);
			return (int)((float)870/windowHeight);
		}
	
	private void deactivateUI() {
		for (Clickable click : Clickable.getRenderList()) {
			click.setActive(false);
		}
	}
	
	private void reinitiateUI() {
		//TODO vervollständigen!
		for (Clickable click : Clickable.getRenderList()) {
			click.setActive(true);
		}
		if (modelReader.affordableSettlements(BuildingType.Village) == 0) 
			buildVillage.setActive(false);
		if (modelReader.affordableSettlements(BuildingType.Town) == 0) 
			buildTown.setActive(false);
		if (modelReader.affordableCatapultBuild() == 0) 
			buildCatapult.setActive(false);
		if (modelReader.affordableStreets() == 0) 
			buildStreet.setActive(false);
	}

	private void setColor(PlayerColors playerColor) {
		switch(playerColor) {
		case BLUE:
			GL11.glColor4f(0.30f,0.30f,1.0f,1.0f); break;
		case RED:
			GL11.glColor4f(1.0f,0.0f,0.0f,1.0f); break;
		case YELLOW:
			GL11.glColor4f(1.0f,1.0f,0.0f,1.0f); break;
		case ORANGE:
			GL11.glColor4f(1.0f,0.5f,0.0f,1.0f); break;
		case BROWN:
			GL11.glColor4f(0.5f,0.25f,0.05f,1.0f); break;
		case WHITE:
			GL11.glColor4f(1.0f,1.0f,1.0f,1.0f); break;
		case PURPLE:
			GL11.glColor4f(0.5f,0.25f,0.5f,1.0f); break;
		case BLACK:
			GL11.glColor4f(1.0f,1.0f,1.0f,1.0f); break;
		}
	}
	
	private void drawSquareMid(int width, int height) {
	     GL11.glBegin(GL11.GL_POLYGON);
	       GL11.glTexCoord2f(0,0);
	       GL11.glVertex3i(-width/2, -height/2, 0);
	       GL11.glTexCoord2f(1,0);
	       GL11.glVertex3i(width/2, -height/2, 0);
	       GL11.glTexCoord2f(1,1);
	       GL11.glVertex3i(width/2, height/2, 0);
	       GL11.glTexCoord2f(0,1);
	       GL11.glVertex3i(-width/2, height/2, 0);
	     GL11.glEnd();
	}
	
	private void drawSquareLeftTop(int width, int height) {
	     GL11.glBegin(GL11.GL_POLYGON);
	       GL11.glTexCoord2f(0,0);
	       GL11.glVertex3i(0, 0, 0);
	       GL11.glTexCoord2f(1,0);
	       GL11.glVertex3i(width, 0, 0);
	       GL11.glTexCoord2f(1,1);
	       GL11.glVertex3i(width, height, 0);
	       GL11.glTexCoord2f(0,1);
	       GL11.glVertex3i(0, height, 0);
	     GL11.glEnd();
	}

	private void renderField(Field f) {
		int fx = 0;
		int fy = 0;
		   switch(f.getLocation().getY()%2) {
		   case 0:
			   fx = f.getLocation().getX()*250;
			   fy = f.getLocation().getY()*215; 
			   break;
		   case 1:
		   case -1:
			   fx = f.getLocation().getX()*250-125;
			   fy = f.getLocation().getY()*215;
			   break;
		   }
		   GL11.glPushMatrix(); 
		   setColor(BLACK);
		   GL11.glTranslatef(fx+x, fy+y, 0+z);
		   fieldTextureMap.get(f.getFieldType()).bind(); 
		   if (f.hasRobber()) {
			   if(f.getFieldType().equals(FieldType.DESERT) || f.getFieldType().equals(FieldType.WATER)) {
				   drawSquareMid(300, 300);
				   robberTexture.bind();
				   drawSquareMid(60, 60);
			   }
			   else {
			   drawSquareMid(300, 300);
			   GL11.glTranslatef(-50, 0, 0);
			   robberTexture.bind();
			   drawSquareMid(60, 60);
			   GL11.glTranslatef(100, 0, 0);
			   }
		   }
		   else {
			   drawSquareMid(300, 300);
		   }
		   
		   if (f.getFieldType() != FieldType.DESERT && f.getFieldType() != FieldType.WATER) { 
			   numberTextureMap.get(f.getNumber()).bind();
			   drawSquareMid(300, 300);
		   }
		   GL11.glPopMatrix();
	}
	
	private void renderIntersection(Intersection i) {
		if (i.hasOwner()) {
			int ix = 0;
			int iy = 0;
			   switch(i.getLocation().getY()%2) {
				   case 0:
					   ix = i.getLocation().getX()*250;
					   iy = i.getLocation().getY()*215; 
					   break;
				   case 1:
					   ix = i.getLocation().getX()*250-125;
					   iy = i.getLocation().getY()*215;
					   break;
			   }
			  switch(i.getLocation().getOrientation()) {
				   case 0:
					   iy+=-135;
					   break;
				   case 1:
					   ix+=125;
					   iy+=-70;
					   break;
				   case 2:
					   ix+=125;
					   iy+=80;
					   break;
				   case 3:
					   iy+=140;
					   break;
				   case 4:
					   ix+=-120;
					   iy+=80;
					   break;
				   case 5:
					   ix+=-120;
					   iy+=-70;
					   break;
				   default:
					   throw new IllegalArgumentException();
			   }
			   GL11.glPushMatrix();
			   intersectionTextureMap.get(i.getBuildingType()).bind();
			   setColor(colorMap.get(i.getOwner()));
			   GL11.glTranslatef(ix+x, iy+y, 1+z);
			   drawSquareMid(180, 180);
			   GL11.glPopMatrix();
		}
	}

	private void renderPath(Path p) {
		int px = 0;
		int py = 0;
		int po = 0;
		if (p.hasStreet() || p.hasCatapult()) {
			   switch(p.getLocation().getY()%2) {
				   case 0:
					   px = p.getLocation().getX()*250;
					   py = p.getLocation().getY()*215; 
					   break;
				   case 1:
					   px = p.getLocation().getX()*250-125;
					   py = p.getLocation().getY()*215;
					   break;
			   }
			  switch(p.getLocation().getOrientation()) {
				   case 0:
					   px+=67;
					   py+=-107;
					   po+=30;
					   break;
				   case 1:
					   px+=120;
					   py+=14;
					   po+=90;
					   break;
				   case 2:
					   px+=40;
					   py+=118;
					   po+=150;
					   break;
				   case 3:
					   px+=-82;
					   py+=95;
					   po+=210;
					   break;
				   case 4:
					   px+=-128;
					   py+=-21;
					   po+=270;
					   break;
				   case 5:
					   px+=-52;
					   py+=-115;
					   po+=330;
					   break;
				   default:
					   throw new IllegalArgumentException();
			   }
		}
		if (p.hasStreet()) {	
			GL11.glPushMatrix();
			GL11.glTranslatef(px+x, py+y, 1+z);
			GL11.glRotatef(po, 0, 0, 1);
		    streetTexture.bind();
		    setColor(colorMap.get(p.getStreetOwner()));
		    drawSquareMid(170,20);
		    GL11.glPopMatrix();
		}
		if (p.hasCatapult()) {	
			GL11.glPushMatrix();
			GL11.glTranslatef(px+x, py+y, 1+z);
		    catapultTexture.bind();
		    setColor(colorMap.get(p.getCatapultOwner()));
		    drawSquareMid(140, 140);
		    GL11.glPopMatrix();
		}
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
			renderUI("PlayerBackgroundHighlight", xOffsetUI+px-5, yOffsetUI+py-10, 2, 440, 80);
			setColor(BLACK);
		}
		GL11.glPushMatrix();
		String name = getName(player);
		uiFont20.drawString(xOffsetUI+px+30, yOffsetUI+py-3, name);
		setColor(colorMap.get(player));
		renderUI("PlayerColor", xOffsetUI+px, yOffsetUI+py, 2, 30, 30);
		setColor(BLACK);
		renderUI("Cup", xOffsetUI+px, yOffsetUI+py+20, 2, 30, 50);
		// draw currentScorePoints 0/??
		uiFont20.drawString(xOffsetUI+px+22, yOffsetUI+py+25, ""+modelReader.getCurrentVictoryPoints(player) + "/" + modelReader.getMaxVictoryPoints());
		setColor(colorMap.get(player));
		GL11.glPushMatrix();
		GL11.glTranslatef(xOffsetUI+px+90, yOffsetUI+py+42, 1);
		intersectionTextureMap.get(BuildingType.Village).bind();
		drawSquareMid(30, 30);
		GL11.glPopMatrix();
		setColor(BLACK);
		//draw VillageScore 0/??
		uiFont20.drawString(xOffsetUI+px+99, yOffsetUI+py+25, ""+village[(int)pos] + "/" + modelReader.getMaxBuilding(BuildingType.Village));
		setColor(colorMap.get(player));
		GL11.glPushMatrix();
		GL11.glTranslatef(xOffsetUI+px+155, yOffsetUI+py+42, 1);
		intersectionTextureMap.get(BuildingType.Town).bind();
		drawSquareMid(30,30);
		GL11.glPopMatrix();
		setColor(BLACK);
		//draw TownScore
		uiFont20.drawString(xOffsetUI+px+163, yOffsetUI+py+25, ""+town[(int)pos] + "/" + modelReader.getMaxBuilding(BuildingType.Town));
		setColor(colorMap.get(player));
		GL11.glPushMatrix();
		GL11.glTranslatef(xOffsetUI+px+220, yOffsetUI+py+42, 1);
		catapultTexture.bind();
		drawSquareMid(30,30);
		GL11.glPopMatrix();
		setColor(BLACK);
		//draw CatapultScore
		uiFont20.drawString(xOffsetUI+px+230, yOffsetUI+py+25, ""+catapult[(int)pos] + "/" + modelReader.getMaxCatapult());
		setColor(colorMap.get(player));
		GL11.glPushMatrix();
		GL11.glTranslatef(xOffsetUI+px+270, yOffsetUI+py+37, 1);
		streetTexture.bind();
		drawSquareMid(5,30);
		GL11.glPopMatrix();
		setColor(BLACK);
		// draw LongestRoad (as int)
		uiFont20.drawString(xOffsetUI+px+277, yOffsetUI+py+24, ""+road[(int)pos]);
		// draw seperation for players
		
		GL11.glPopMatrix();
	}

	private void renderMarks() {
		switch(selectionMode) {
		case NONE: 
			break;
		case FIELDS:
			for (Point p : selectionPoint) {
			    int fx = 0;
			    int fy = 0;
			    switch(p.getY()%2) {
			    case 0:
				   fx = p.getX()*250;
				   fy = p.getY()*215; 
				   break;
			    case 1:
				   fx = p.getX()*250-125;
				   fy = p.getY()*215;
				   break;
			    }
			    GL11.glPushMatrix();
			    fieldMarkTexture.bind();
			    setColor(BLACK);
			    GL11.glTranslatef(fx+x, fy+y, 2+z);
			    drawSquareMid(300, 300);
			    GL11.glPopMatrix();
			}
			break;
		case INTERSECTIONS:
			for (Location l : selectionLocation) {
				int ix = 0;
				int iy = 0;
				   switch(l.getY()%2) {
					   case 0:
						   ix = l.getX()*250;
						   iy = l.getY()*215; 
						   break;
					   case 1:
						   ix = l.getX()*250-125;
						   iy = l.getY()*215;
						   break;
				   }
				  switch(l.getOrientation()) {
					   case 0:
						   iy+=-135;
						   break;
					   case 1:
						   ix+=125;
						   iy+=-70;
						   break;
					   case 2:
						   ix+=125;
						   iy+=80;
						   break;
					   case 3:
						   iy+=140;
						   break;
					   case 4:
						   ix+=-120;
						   iy+=80;
						   break;
					   case 5:
						   ix+=-120;
						   iy+=-70;
						   break;
					   default:
						   throw new IllegalArgumentException();
				   }
			    GL11.glPushMatrix();
			    intersectionMarkTexture.bind();
			    setColor(BLACK);
			    GL11.glTranslatef(ix+x, iy+y, 3+z);
			    drawSquareMid(50, 50);
			    GL11.glPopMatrix();
			}
			break;
		case PATHS:
			break;
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
		   
		   GL11.glPushMatrix();
		   //GL11.glColor4f(0.3f, 0.3f, 0.3f, 0.3f); // (Mouse.getX()*screenToOpenGLX)/, ((windowHeight-Mouse.getY())*screenToOpenGLY+380)*(zOffsetUI/z), maxX
		   GL11.glTranslatef(Mouse.getX()+minX, (windowHeight-Mouse.getY())+minY, maxX + z);
		   intersectionMarkTexture.bind();
		   drawSquareMid(300, 300);
		   //setColor(BLACK);
		   GL11.glPopMatrix();
		   
		   //Render Selections
		   renderMarks();
		   //Render UI
		   GL11.glPushMatrix();
		   GL11.glTranslatef(xOffset, 0, zOffsetUI);
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
		   //Render Fonts on UI
		   GL11.glPushMatrix();
		   GL11.glTranslatef(xOffset+20, 400, -950);
		   debugFont.drawString(300, 0, "Debug:", Color.white);
		   debugFont.drawString(300, 30, "x: " + x + ", y: " + y + ", z: " + z, Color.white);
		   debugFont.drawString(300, 60, "mx: " + Mouse.getX() + ", my: " + Mouse.getY() + ", mw: " + Mouse.getEventDWheel(), Color.white);
		   debugFont.drawString(300, 90, "minX: " + minX + ", minY: " + minY + ", minZ: " + maxX, Color.white);
		   GL11.glPopMatrix();

		   GL11.glPushMatrix();
		   GL11.glTranslatef(xOffset+xOffsetUI, yOffsetUI, zOffsetUI);
		   uiFont20.drawString(640, 75, "Round "+modelReader.getRound(), Color.black);
		   uiFont20.drawString(640, 100, ""+ lastNumberDiced + " was diced", Color.black);
		   uiFont20.drawString(640, 125, "It's "+ getName(modelReader.getCurrentPlayer()) + "'s turn", Color.black);
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
			List<List<Path>> streets = modelReader.calculateLongestRoads(act);
			this.road[i++] = streets.size() > 0 ? streets.get(0).size() : 0;
		}
	}

	@Override
	public void updateField(Field field) {
		//TODO: implement it!
	}

	@Override
	public void updateResources() {
		//TODO: implement it!
		//überprüfen ob bau-
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
		//TODO: implement it!
		// in selektionsmodus für felder gehn und evtl nachricht auf console
	}

	@Override
	public void eventTrade(ResourcePackage resourcePackage) {
		//TODO: implement it!
		// handel auf console ausgeben und 2 knöpfe einblenden für annehmen ablehnen
	}

	@Override
	public void eventNewRound(int number) {
		if (modelReader.getCurrentPlayer() == modelReader.getMe() && !observer) {
			reinitiateUI();
		}
		this.lastNumberDiced = number;
	}

	@Override
	public void updateTradePossibilities() {
		//TODO: implement it!
	}

	@Override
	public void eventPlayerLeft(long playerID) {
		//TODO: implement it!
	}
	
	@Override
	public void run() {
		init();
		try {
			barrier.await();
		} catch (Exception e) {e.printStackTrace();} 
		
	    boolean finished = false;
		while (!finished) {
			  handleInput();
		      // Always call Window.update(), all the time - it does some behind the
		      // scenes work, and also displays the rendered output
			  Display.update();
		      // Check for close requests
		      if (Display.isCloseRequested()) {
			  finished = true;
		      } 
		      else if (Display.isActive()) {
		          render();
		          Display.sync(60);
		        } 
		      // The window is not in the foreground, so we can allow other stuff to run and
		      // infrequently update
		      else {
		        try {
		          Thread.sleep(100);
		        } catch (InterruptedException e) {e.printStackTrace();}
		      }
		      if (Display.isVisible() || Display.isDirty()) {
		          render();
		      }
		}
		Display.destroy();
		System.exit(0);
	}
	
	@SuppressWarnings("unchecked")
	private void init() {
		try {//Display.getDesktopDisplayMode()
			Display.setDisplayMode(Setting.getDisplayMode());
			Display.setTitle("Die Siedler von der Saar: " + getName(modelReader.getMe()) + "@" + gameTitle);
			Display.setVSyncEnabled(true);
			Display.setFullscreen(Setting.isFullscreen());
			Display.create();
			
			//TODO zeit messen
			fieldTextureMap = new HashMap<FieldType,Texture>();
			fieldTextureMap.put(FieldType.MOUNTAINS, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Mountains.png")));
			fieldTextureMap.put(FieldType.DESERT, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Desert.png")));
			fieldTextureMap.put(FieldType.FIELDS, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Fields.png")));
			fieldTextureMap.put(FieldType.FOREST, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Forest.png")));
			fieldTextureMap.put(FieldType.HILLS, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Hills.png")));
			fieldTextureMap.put(FieldType.PASTURE, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Pasture.png")));
			fieldTextureMap.put(FieldType.WATER, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Water.png")));
			intersectionTextureMap = new HashMap<BuildingType,Texture>();
			intersectionTextureMap.put(BuildingType.Village, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Intersections/Village.png")));
			intersectionTextureMap.put(BuildingType.Town, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Intersections/Town.png")));
			
			numberTextureMap = new HashMap<Integer,Texture>();
			numberTextureMap.put(2, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/2.png")));
			numberTextureMap.put(3, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/3.png")));
			numberTextureMap.put(4, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/4.png")));
			numberTextureMap.put(5, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/5.png")));
			numberTextureMap.put(6, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/6.png")));
			numberTextureMap.put(8, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/8.png")));
			numberTextureMap.put(9, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/9.png")));
			numberTextureMap.put(10, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/10.png")));
			numberTextureMap.put(11, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/11.png")));
			numberTextureMap.put(12, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/12.png")));
		
			streetTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Paths/Street.png"));
			catapultTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Paths/Catapult.png"));
			robberTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Robber.png"));
			fieldMarkTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Mark.png"));
			intersectionMarkTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Intersections/Mark.png"));
			pathMarkTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Paths/Mark.png"));
			
			uiTextureMap = new HashMap<String,Texture>();
			uiTextureMap.put("Background", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/menue_background.png")));
			uiTextureMap.put("EndTurn", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_endTurn.png")));
			uiTextureMap.put("ClaimVictory", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_claimVictory.png")));
			uiTextureMap.put("ClaimLongestRoad", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_claimLongestRoad.png")));
			uiTextureMap.put("offerTrade", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_offerTrade.png")));
			uiTextureMap.put("BuildStreet", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_BuildStreet.png")));
			uiTextureMap.put("BuildVillage", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_BuildVillage.png")));
			uiTextureMap.put("BuildTown", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_BuildTown.png")));
			uiTextureMap.put("BuildCatapult", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_BuildCatapult.png")));
			uiTextureMap.put("LumberScore", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Lumber_Score.png")));
			uiTextureMap.put("BrickScore", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Brick_Score.png")));
			uiTextureMap.put("GrainScore", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Grain_Score.png")));
			uiTextureMap.put("WoolScore", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Wool_Score.png")));
			uiTextureMap.put("OreScore", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Ore_Score.png")));
			uiTextureMap.put("Console", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/console.png")));
			uiTextureMap.put("PlayerColor", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Player_Color.png")));
			uiTextureMap.put("Cup", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Cup.png")));
			uiTextureMap.put("Trenner", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/PlayerTrenner.png")));
			uiTextureMap.put("PlayerBackgroundHighlight", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/PlayerBackgroundHighlight.png")));
			
			claimLongestRoad = new Clickable("ClaimLongestRoad", xOffsetUI+542, yOffsetUI+22, 2, 373, 77, false, true, true) {
				@Override
				public void execute() {
					// vorerst gerade die erste longestRoad
					List<List<Path>> road = modelReader.calculateLongestRoads(modelReader.getMe());
					controllerAdapter.claimLongestRoad(road.get(0));
				}
			};

			claimVictory = new Clickable("ClaimVictory", xOffsetUI+775, yOffsetUI+22, 2, 185, 77, false, false, true) {
				@Override
				public void execute() {
					controllerAdapter.claimVictory();
				}
			};
			
			endTurn = new Clickable("EndTurn", xOffsetUI+930, yOffsetUI+22, 2, 185, 77, false, false, true) {
				@Override
				public void execute() {
					controllerAdapter.endTurn();
				}
			};
			
			offerTrade = new Clickable("offerTrade", xOffsetUI+450, yOffsetUI+65, 2, 185, 77, false, true, true) {
				@Override
				public void execute() {
					minX = 42;
				}
			};
			
			buildStreet = new Clickable("BuildStreet", xOffsetUI+450, yOffsetUI+110, 2, 185, 77, false, true, true) {
				@Override
				public void execute() {
					selectionLocation = Model.getLocationListPath(modelReader.buildableStreetPaths(modelReader.getMe()));
					selectionMode = PATHS;
					
				}
			};
			
			buildVillage = new Clickable("BuildVillage", xOffsetUI+450, yOffsetUI+155, 2, 185, 77, true, true, true) {
				@Override
				public void execute() {
					selectionLocation = Model.getLocationListIntersection(modelReader.buildableVillageIntersections(modelReader.getMe()));
					selectionMode = INTERSECTIONS;
				}
			};
			
			buildTown = new Clickable("BuildTown", xOffsetUI+450, yOffsetUI+200, 2, 185, 77, false, true, true) {
				@Override
				public void execute() {
					selectionLocation = Model.getLocationListIntersection(modelReader.buildableTownIntersections(modelReader.getMe()));
					selectionMode = INTERSECTIONS;
				}
			};
						
			buildCatapult = new Clickable("BuildCatapult", xOffsetUI+450, yOffsetUI+245, 2, 185, 77, false, true, true) {
				@Override
				public void execute() {
					selectionLocation = Model.getLocationListPath(modelReader.buildableStreetPaths(modelReader.getMe()));
					selectionMode = PATHS;
				}
			};
			
			if (modelReader.getMe() != modelReader.getCurrentPlayer() || observer) {
				deactivateUI();
			}
			else {
				reinitiateUI();
			}
			
		} catch (Exception e) {e.printStackTrace();}
		
		GL11.glDisable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameterf( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameterf( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glClearDepth(1.0f); // Depth Buffer Setup
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(+45.0f, aspectRatio, 0.1f, z+2500); //-5000.f ist die maximale z tiefe
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
        
        renderFieldList = new LinkedList<Field>();
        Iterator<Field> iter = modelReader.getFieldIterator();
        while (iter.hasNext()) {
        	renderFieldList.add(iter.next());
        }
        
        Display.update();
        render();
        Display.update();
        render();
        Display.update();
        render();
        //render two/three times to fill the double/triple buffer
	}

	private void handleInput() {
		int mx = Mouse.getX();
		int my = Mouse.getY();
		
		if (Mouse.isButtonDown(0)) {
			for (Clickable c : Clickable.executeModelClicks(mx*screenToOpenGLx(zOffsetUI), (windowHeight-my)*screenToOpenGLy(zOffsetUI)+380)) {
				controllerAdapter.addGuiEvent(c);
			}
			for (Clickable c : Clickable.executeUIClicks(mx*screenToOpenGLx(zOffsetUI), (windowHeight-my)*screenToOpenGLy(zOffsetUI)+380)) {
				c.execute();
			}
			//TODO hier kommen die sachen mit der selektion hin- wenn in mode intersection koordinatenumrechnung und validierung
			
		}
		
		if (Mouse.isInsideWindow()) {
			if (mx < 50) {
				x+=5;
			}
			else if (mx > windowWidth-50) {
				x-=5;
			}
			if (my < 50) {
				y-=5;
			}
			else if (my > windowHeight-50) {
				y+=5;
			}
		}
		
		if (Mouse.next()) {
			if (Mouse.getEventDWheel() < 0 && z+50 < this.maxZ) {
				z+=50; 
			}
			else if (Mouse.getEventDWheel() > 0 && z-50 > this.minZ) {	
				z-=50; 
			}
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			//x+=5;
			minX+=5;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			//x-=5;
			minX-=5;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			//y+=5;
			minY+=5;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			//y-=5;
			minY-=5;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_N)) {
			if (z+50 < this.maxZ)
				z+=50;
			maxX+=10;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
			if (z-50 > this.minZ)
				z-=50;
			maxX-=10;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Display.destroy();
			System.exit(0);
		}
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
		/*WorldRepresentation worldrep = new WorldRepresentation(1, 1, 2, 9, 5, 4,  
				new byte[] {1},
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
		Map<Player,String> names = new HashMap<Player,String>();
		names.put(model.getTableOrder().get(3), "Ichbinkeinreh");
		names.put(model.getTableOrder().get(2), "Herbert");
		names.put(model.getTableOrder().get(1), "Hubert");
		names.put(model.getTableOrder().get(0), "Hannes");
		model.getTableOrder().get(3).modifyResources(new ResourcePackage(100,200,140,130,120));
		
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
		
//		Setting setting = new Setting(Display.getDesktopDisplayMode(), true, PlayerColors.RED);
		Setting setting = new Setting(new DisplayMode(1024, 550), false, PlayerColors.RED);  /// Display.getDesktopDisplayMode()
		
		CyclicBarrier barrier = new CyclicBarrier(2);
		GameGUI gameGUI = new GameGUI(model, null, names, setting, "TestSpiel", false, barrier);
		new Thread(gameGUI).start();
		barrier.await();
	}
	
	public static void saveFile(String filename, InputStream inputStr) throws IOException {
		FileOutputStream fos = new FileOutputStream(filename);
		   int len = -1;
		   byte[] buffer = new byte[4096];
		   while ((len = inputStr.read(buffer)) != -1) {
		      fos.write(buffer,0,len);
		   }
		   fos.close();
		}

	@Override
	public void initTurn() {
		//TODO: implement it!
	}

	@Override
	public void updateIntersection(Intersection intersection) {
		// TODO Auto-generated method stub
		
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
		
		Setting setting = new Setting(new DisplayMode(1024,580), true, PlayerColors.RED);
		GameGUI gameGUI = null;
		try {
			gameGUI = new GameGUI(model, null, null, setting, true);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		new Thread(gameGUI).start();
		*/
	
}
