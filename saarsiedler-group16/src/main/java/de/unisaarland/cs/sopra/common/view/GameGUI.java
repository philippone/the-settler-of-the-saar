package de.unisaarland.cs.sopra.common.view;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import static de.unisaarland.cs.sopra.common.PlayerColors.*;

public class GameGUI extends View implements Runnable{

	private Map<Player,String> playerNames;
	private Setting setting;
	private Map<FieldType,Texture> fieldTextureMap;
	private HashMap<BuildingType, Texture> intersectionTextureMap;
	private Map<Integer,Texture> numberTextureMap;
	private Map<String,Texture> uiTextureMap;
	private Map<String,Texture> markTextureMap;
	private List<Field> renderFieldList;
	private Texture streetTexture;
	private Texture catapultTexture;
	private int x,y,z;
	private int maxX, maxY, maxZ;
	private int minX, minY, minZ;
	private UnicodeFont debugFont;
	private UnicodeFont uiFont;

	private int selectionMode;
	private static final int NONE = 0;
	private static final  int INTERSECTIONS = 1;
	private static final  int PATHS = 2;
	private static final  int FIELDS = 3;
	private List<Point> locations;
	
	private int uiMode;
	private static final int RESOURCE_VIEW = 0;
	private static final int TRADE_VIEW = 1;
	private static final int BUILD_VIEW = 2;
	
	private static int windowWidth;
	private static int windowHeight;
	private static float aspectRatio;
	private static int xOffset;
	private static int xOffsetUI, yOffsetUI, zOffsetUI;
	private static int viewportXwidth;
	private static int viewportYwidth;
	private static float screenToOpenGLX;
	private static float screenToOpenGLY;
	
	private int village;
	private int town;
	private int catapult;
	
	private Map<Player,PlayerColors> colorMap;
	
	public GameGUI(ModelReader modelReader, ControllerAdapter controllerAdapter, Map<Player,String> playerNames, Setting setting) throws Exception {
		super(modelReader, controllerAdapter);
		this.modelReader.addModelObserver(this);
		this.setting = setting;
		this.playerNames = playerNames;
		this.uiMode = RESOURCE_VIEW;
		this.selectionMode = NONE;
		windowWidth = setting.getDisplayMode().getWidth();
		windowHeight = setting.getDisplayMode().getHeight();
		aspectRatio = ((float)windowWidth)/windowHeight;
		viewportXwidth = (int)(870*aspectRatio);
		viewportYwidth = 870;
		screenToOpenGLX = ((float)viewportXwidth/windowWidth);
		screenToOpenGLY = ((float)viewportYwidth/windowHeight);
		xOffset = (int)(366*aspectRatio);
		xOffsetUI = (int)((viewportXwidth-1075)/2);
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
		tmp.addAll(Arrays.asList(new PlayerColors[] {RED,BLUE,GREEN,YELLOW,ORANGE,BROWN,WHITE,PURPLE,BLACK}));
		tmp.remove(setting.getPlayerColor());
		
		//set color of players
		colorMap = new HashMap<Player,PlayerColors>();
		Iterator<Player> iterP = modelReader.getTableOrder().iterator();
		Iterator<PlayerColors> iterC = tmp.iterator();
		while (iterP.hasNext()) {
			Player act = iterP.next();
			if (act == modelReader.getMe()) {
				colorMap.put(act, setting.getPlayerColor());
			}
			else {
				colorMap.put(act, iterC.next());
			}
		}
		
	}
	
	private void setColor(PlayerColors playerColor) {
		switch(playerColor) {
		case BLUE:
			GL11.glColor4f(0.0f,0.0f,1.0f,1.0f); break;
		case RED:
			GL11.glColor4f(1.0f,0.0f,0.0f,1.0f); break;
		case GREEN:
			GL11.glColor4f(0.0f,1.0f,0.0f,1.0f); break;
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
			   fx = f.getLocation().getX()*250-125;
			   fy = f.getLocation().getY()*215;
			   break;
		   }
		   GL11.glPushMatrix();
		   fieldTextureMap.get(f.getFieldType()).bind();  
		   setColor(BLACK);
		   GL11.glTranslatef(fx+x, fy+y, 0+z);
		   drawSquareMid(300, 300);
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
			   drawSquareMid(70, 70);
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
		    drawSquareMid(140,20);
		    GL11.glPopMatrix();
		}
		if (p.hasCatapult()) {	
			GL11.glPushMatrix();
			GL11.glTranslatef(px+x, py+y, 1+z);
		    catapultTexture.bind();
		    setColor(colorMap.get(p.getCatapultOwner()));
		    drawSquareMid(70, 70);
		    GL11.glPopMatrix();
		}
	}

	private void renderMarks() {
		switch(selectionMode) {
		case NONE: 
			break;
		case FIELDS:
			for (Point p : locations) {
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
			    markTextureMap.get("Field").bind();
			    setColor(BLACK);
			    GL11.glTranslatef(fx+x, fy+y, 2+z);
			    drawSquareMid(300, 300);
			    GL11.glPopMatrix();
			}

			break;
		case INTERSECTIONS:
			break;
		case PATHS:
			break;
		}
		
		     
		
	}
	
	private void renderUI(String name, int x, int y, int z, int width, int height) {
		GL11.glPushMatrix(); 
		uiTextureMap.get(name).bind();
		setColor(BLACK);
	    GL11.glTranslatef(x, y, z);
		drawSquareLeftTop(width, height);
		GL11.glPopMatrix();
	}
	
	private void renderUI(Clickable click) {
		renderUI(click.getName(),click.getX(),click.getY(),click.getZ(),click.getWidth(),click.getHeight());
		//TODO render grey for inactive clickables
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
		   renderMarks(); //TODO: implement markierungen
		   //Render UI
		   GL11.glPushMatrix();
		   GL11.glTranslatef(xOffset, 0, 0);
		   renderUI("Background", xOffsetUI, yOffsetUI, zOffsetUI, 1500, 550);
		   renderUI("Res", xOffsetUI+957, yOffsetUI+20, zOffsetUI+1, 42, 330);
		   for (Clickable act : Clickable.getList())
			   renderUI(act);
		   GL11.glPopMatrix();
		   //Render Fonts on UI
		   GL11.glPushMatrix();
		   GL11.glTranslatef(xOffset+20, 400, -950);
		   debugFont.drawString(300, 0, "Debug:", Color.white);
		   debugFont.drawString(300, 30, "x: " + x + ", y: " + y + ", z: " + z, Color.white);
		   debugFont.drawString(300, 60, "mx: " + Mouse.getX() + ", my: " + Mouse.getY() + ", mw: " + Mouse.getEventDWheel(), Color.white);
		   debugFont.drawString(300, 90, "minX: " + minX + ", minY: " + minY , Color.white);
		   GL11.glPopMatrix();
		   GL11.glTranslatef(xOffset+xOffsetUI, 955, -950);
		   uiFont.drawString(1000, 20, ""+modelReader.getResources().getResource(Resource.LUMBER), Color.black);
		   uiFont.drawString(1000, 52, ""+modelReader.getResources().getResource(Resource.BRICK), Color.black);
		   uiFont.drawString(1000, 84, ""+modelReader.getResources().getResource(Resource.WOOL), Color.black);
		   uiFont.drawString(1000, 116, ""+modelReader.getResources().getResource(Resource.GRAIN), Color.black);
		   uiFont.drawString(1000, 147, ""+modelReader.getResources().getResource(Resource.ORE), Color.black);
		   uiFont.drawString(1000, 178, ""+ village + "/" + modelReader.getMaxBuilding(BuildingType.Village), Color.black);
		   uiFont.drawString(1000, 209, ""+ town + "/" + modelReader.getMaxBuilding(BuildingType.Town), Color.black);
		   uiFont.drawString(1000, 240, ""+ catapult + "/" + modelReader.getMaxVictoryPoints(), Color.black);
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
		//TODO: implement it!
	}

	@Override
	public void updateIntersection(Intersection intersection) {
		//TODO: implement it!
	}

	@Override
	public void updateField(Field field) {
		//TODO: implement it!
	}

	@Override
	public void updateResources() {
		//TODO: implement it!
	}

	@Override
	public void updateVictoryPoints() {
		//TODO: implement it!
	}

	@Override
	public void updateCatapultCount() {
		this.catapult = modelReader.getCatapults(modelReader.getMe()).size();
	}

	@Override
	public void updateSettlementCount(BuildingType buildingType) {
		switch(buildingType) {
		case Village:
			this.village = modelReader.getSettlements(modelReader.getMe(), BuildingType.Village).size(); 
			break;
		case Town:
			this.town = modelReader.getSettlements(modelReader.getMe(), BuildingType.Town).size();
			break;
		}
	}

	@Override
	public void eventRobber() {
		//TODO: implement it!
	}

	@Override
	public void eventTrade(ResourcePackage resourcePackage) {
		//TODO: implement it!
	}

	@Override
	public void eventNewRound() {
		//TODO: implement it!
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
		        } catch (InterruptedException e) {}
		      }
		      if (Display.isVisible() || Display.isDirty()) {
		          render();
		      }
		}
		Display.destroy();
	}
	
	@SuppressWarnings("unchecked")
	private void init() {
		try {//Display.getDesktopDisplayMode()
			Display.setDisplayMode(setting.getDisplayMode());
			Display.setTitle("Die Siedler von der Saar");
			Display.setVSyncEnabled(true);
			Display.setFullscreen(setting.isFullscreen());
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
			
			
			uiTextureMap = new HashMap<String,Texture>();
			uiTextureMap.put("Background", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Background.png")));
			uiTextureMap.put("Res", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Res.png")));
			uiTextureMap.put("EndTurn", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/EndTurn.png")));
			uiTextureMap.put("ClaimVictory", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/ClaimVictory.png")));
			
			markTextureMap = new HashMap<String,Texture>();
			markTextureMap.put("Field", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/FieldMark.png")));
			
			new Clickable("EndTurn", xOffsetUI+140, yOffsetUI+240, zOffsetUI+2, 158, 65, true) {
				@Override
				public void execute() {
					minX = 1337;
				}
			};
			new Clickable("ClaimVictory", xOffsetUI+300, yOffsetUI+240, zOffsetUI+2, 302, 65, true) {
				@Override
				public void execute() {
					minX = 42;
				}
			};
			
		} catch (Exception e) {}
		
		GL11.glDisable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameterf( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameterf( GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glClearDepth(1.0f); // Depth Buffer Setup
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(+45.0f, aspectRatio, 0.1f, 5000.0f); //-5000.f ist die maximale z tiefe
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
        Keyboard.enableRepeatEvents(true);
        Mouse.poll();
        
        Font awtFont = new Font("Arial", Font.BOLD, 72);
        debugFont = new UnicodeFont(awtFont, 22, false, false);
        debugFont.addAsciiGlyphs();
        debugFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); 
        try {
			debugFont.loadGlyphs();
		} catch (SlickException e1) {}
		
        uiFont = new UnicodeFont(awtFont, 20, false, false);
        uiFont.addAsciiGlyphs();
        uiFont.getEffects().add(new ColorEffect(java.awt.Color.BLACK)); 
        try {
			uiFont.loadGlyphs();
		} catch (SlickException e1) {}

        
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
			Clickable.executeClicks(mx*screenToOpenGLX, (windowHeight-my)*screenToOpenGLY+380);
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
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
			if (z-50 > this.minZ)
				z-=50;
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
		for (String act : list) {
			InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("native/" + act);
			saveFile(tmpdir + "/" + act, input);
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
		WorldRepresentation worldrep = new WorldRepresentation(1, 1, 2, 9, 5, 4,  
				new byte[] {1},
				new byte[] {1,4,
							2,5,    
							3,6},
				new byte[] {});
		Model model = new Model(/*worldrep*/WorldRepresentation.getDefault(), matchinfo, 0);
		model.matchStart(new long[] {0,1}, new byte[]   {2,3,4,
														 6,8,9,10,
														 11,12,11,10,
														 9,8,6,5,
														 2,6,9});
		model.buildSettlement(new Location(3,3,0), BuildingType.Village);
		model.buildStreet(new Location(3,3,0));
		
		model.buildSettlement(new Location(3,3,2), BuildingType.Village);
		model.buildStreet(new Location(3,3,2));
		
		model.buildSettlement(new Location(3,3,4), BuildingType.Village);
		model.buildStreet(new Location(3,3,4));
		
		model.buildSettlement(new Location(2,2,0), BuildingType.Village);
		model.buildStreet(new Location(2,2,0));
		
		model.newRound(3);
		
		//Setting setting = new Setting(new DisplayMode(1920, 1080), true);
		//Setting setting = new Setting(new DisplayMode(1280, 1024), true);
		//Setting setting = new Setting(new DisplayMode(800, 600), true);
		//Setting setting = new Setting(new DisplayMode(400, 300), true);
		Setting setting = new Setting(Display.getDesktopDisplayMode(), true, PlayerColors.RED);
		
		GameGUI gameGUI = new GameGUI(model, null, null, setting);
		new Thread(gameGUI).start();
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
	
	
	/*
	 		String[] list = new String[] {
				"jinput-dx8_64.dll", "jinput-dx8.dll", "jinput-raw_64.dll",
				"jinput-raw.dll", "libjinput-linux.so", "libjinput-linux64.so",
				"libjinput-osx.jnilib", "liblwjgl.jnilib", "liblwjgl.so",
				"liblwjgl64.so", "libopenal.so", "libopenal64.so",
				"lwjgl.dll", "lwjgl64.dll", "openal.dylib", "OpenAL32.dll", "OpenAL64.dll" };
		String tmpdir = System.getProperty("java.io.tmpdir");
		for (String act : list) {
			InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("native/" + act);
			try {
				GameGUI.saveFile(tmpdir + "/" + act, input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String seperator;
		if (System.getProperty("sun.desktop") != null && System.getProperty("sun.desktop").equals("windows")) seperator = ";";
		else seperator = ":";
		System.setProperty("java.library.path", System.getProperty("java.library.path") + seperator + tmpdir);
		java.lang.reflect.Field vvv = null;
		try {
			vvv = ClassLoader.class.getDeclaredField("sys_paths");
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		vvv.setAccessible(true); 
		try {
			vvv.set(null, null);
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Setting setting = new Setting(new DisplayMode(1024,580), true, PlayerColors.RED);
		GameGUI gameGUI = null;
		try {
			gameGUI = new GameGUI(model, null, null, setting);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		new Thread(gameGUI).start();
		*/
	
}
