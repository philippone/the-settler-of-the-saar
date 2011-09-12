package de.unisaarland.cs.sopra.common.view;

import java.awt.Font;
import java.io.*;
import java.util.HashMap;
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

import de.unisaarland.cs.sopra.common.Setting;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Point;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class GameGUI extends View implements Runnable{

	private Map<Player,String> playerNames;
	private Setting setting;
	private Map<FieldType,Texture> fieldTextureMap;
	private Map<Integer,Texture> numberTextureMap;
	private Map<String,Texture> uiTextureMap;
	private Map<String,Texture> markTextureMap;
	private List<Field> renderFieldList;
	private int x,y,z;
	private int maxX, maxY, maxZ;
	private int minX, minY, minZ;
	private UnicodeFont uniFont;

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

	
	GameGUI(long meID, ModelReader modelReader, ControllerAdapter controllerAdapter, Map<Player,String> playerNames, Setting setting) throws Exception {
		super(meID, modelReader, controllerAdapter);
		this.modelReader = modelReader;
		this.setting = setting;
		this.playerNames = playerNames;
		this.controllerAdapter = controllerAdapter;
		this.uiMode = RESOURCE_VIEW;
		this.selectionMode = NONE;
		
		//center to the area where the first field is drawn
		this.x = (int)(812.8125f*setting.getWindowWidth()/setting.getWindowHeight());
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
	}

	private void renderFields() {
	   Iterator<Field> iter = renderFieldList.iterator();
	   while (iter.hasNext()) {
			Field f = iter.next();
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
			
			     fieldTextureMap.get(f.getFieldType()).bind();
			     GL11.glBegin(GL11.GL_POLYGON);
			       //GL11.glColor4f(1.0f,1.0f,1.0f,1.0f); //transparenz
			       GL11.glTexCoord2f(0,0);
			       GL11.glVertex3i(-150+fx+x, -150+fy+y, 0+z);
			       GL11.glTexCoord2f(1,0);
			       GL11.glVertex3i(150+fx+x, -150+fy+y, 0+z);
			       GL11.glTexCoord2f(1,1);
			       GL11.glVertex3i(150+fx+x, 150+fy+y, 0+z);
			       GL11.glTexCoord2f(0,1);
			       GL11.glVertex3i(-150+fx+x, 150+fy+y, 0+z);
			     GL11.glEnd();
			     
			     if (f.getFieldType() != FieldType.DESERT && f.getFieldType() != FieldType.WATER) {
				     numberTextureMap.get(f.getNumber()).bind();
				     GL11.glBegin(GL11.GL_POLYGON);
				       //GL11.glColor4f(1.0f,1.0f,1.0f,1.0f); //transparenz
				       GL11.glTexCoord2f(0,0);
				       GL11.glVertex3i(-150+fx+x, -150+fy+y, 1+z);
				       GL11.glTexCoord2f(1,0);
				       GL11.glVertex3i(150+fx+x, -150+fy+y, 1+z);
				       GL11.glTexCoord2f(1,1);
				       GL11.glVertex3i(150+fx+x, 150+fy+y, 1+z);
				       GL11.glTexCoord2f(0,1);
				       GL11.glVertex3i(-150+fx+x, 150+fy+y, 1+z);
				     GL11.glEnd();
			     }
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
			    markTextureMap.get("Field").bind();
			    GL11.glBegin(GL11.GL_POLYGON);
			       //GL11.glColor4f(1.0f,1.0f,1.0f,1.0f); //transparenz
			       GL11.glTexCoord2f(0,0);
			       GL11.glVertex3i(-150+fx+x, -150+fy+y, 2+z);
			       GL11.glTexCoord2f(1,0);
			       GL11.glVertex3i(150+fx+x, -150+fy+y, 2+z);
			       GL11.glTexCoord2f(1,1);
			       GL11.glVertex3i(150+fx+x, 150+fy+y, 2+z);
			       GL11.glTexCoord2f(0,1);
			       GL11.glVertex3i(-150+fx+x, 150+fy+y, 2+z);
			    GL11.glEnd();
			}

			break;
		case INTERSECTIONS:
			break;
		case PATHS:
			break;
		}
		
		     
		
	}
	
	private void renderUI(String name, int x, int y, int layer) {
	     int width = uiTextureMap.get(name).getImageWidth();
	     int height = uiTextureMap.get(name).getImageHeight();
		 int xoffset = (int)(373*setting.getWindowWidth()/setting.getWindowHeight())+minX;
		 int yoffset = 955;
		 int zoffset = -950;
	     
		 uiTextureMap.get(name).bind();
	     GL11.glBegin(GL11.GL_POLYGON);
	      // GL11.glColor4f(0.0f,0.0f,1.0f,1.0f); //transparenz
	       GL11.glTexCoord2f(0,0);
	       GL11.glVertex3i(0+xoffset+x, 0+yoffset+y, layer+zoffset);
	       GL11.glTexCoord2f(1,0);
	       GL11.glVertex3i(width+xoffset+x, 0+yoffset+y, layer+zoffset);
	       GL11.glTexCoord2f(1,1);
	       GL11.glVertex3i(width+xoffset+x, height+yoffset+y, layer+zoffset);
	       GL11.glTexCoord2f(0,1);
	       GL11.glVertex3i(0+xoffset+x, height+yoffset+y, layer+zoffset);
	     GL11.glEnd();
	}
	
	private void render() {
		   GL11.glClearColor(0.2f, 0.2f, 0.2f, 0.0f);
		   GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		   GL11.glLoadIdentity();
		   GL11.glPushMatrix();
		   GL11.glTranslatef(-812.8125f*setting.getWindowWidth()/setting.getWindowHeight(),820,-2000);
		   GL11.glRotatef(180, 1, 0, 0);
		   GL11.glColor3f(1.0f, 1.0f, 1.0f);
		   
		   //Spielfeld
		   renderFields();
		   renderIntersections();
		   renderPaths();
		   
		   //Markierungen
		   renderMarks();
		   
		   //UI
		   renderUI("Background", 0, 0, 0);
		   
		   
		   uniFont.drawString(0, 0, "Debug:", Color.white);
		   uniFont.drawString(0, 60, "x: " + x + ", y: " + y + ", z: " + z, Color.white);
		   uniFont.drawString(0, 120, "mx: " + Mouse.getX() + ", my: " + Mouse.getY() + ", mw: " + Mouse.getEventDWheel(), Color.white);
		   uniFont.drawString(0, 180, "xoffset UI: " + minX, Color.white);

		   GL11.glPopMatrix();
		   
		   
	}

	private void renderPaths() {
		// TODO Auto-generated method stub
		
	}

	private void renderIntersections() {
		// TODO Auto-generated method stub
		
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
	
	@SuppressWarnings("unchecked")
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
			Display.setDisplayMode(new DisplayMode(setting.getWindowWidth(), setting.getWindowHeight())); //
			Display.setTitle("Die Siedler von der Saar");
			Display.setVSyncEnabled(true);
			Display.setFullscreen(true); //setting.isFullscreen()
			Display.create();
			
			fieldTextureMap = new HashMap<FieldType,Texture>();
			fieldTextureMap.put(FieldType.MOUNTAINS, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Mountains.png")));
			fieldTextureMap.put(FieldType.DESERT, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Desert.png")));
			fieldTextureMap.put(FieldType.FIELDS, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Fields.png")));
			fieldTextureMap.put(FieldType.FOREST, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Forest.png")));
			fieldTextureMap.put(FieldType.HILLS, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Hills.png")));
			fieldTextureMap.put(FieldType.PASTURE, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Pasture.png")));
			fieldTextureMap.put(FieldType.WATER, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Water.png")));
			
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
		
			uiTextureMap = new HashMap<String,Texture>();
			uiTextureMap.put("Background", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Background.png")));
		
			markTextureMap = new HashMap<String,Texture>();
			markTextureMap.put("Field", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/FieldMark.png")));
			
		} catch (Exception e) {}
		
		GL11.glDisable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glClearDepth(1.0f); // Depth Buffer Setup
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(+45.0f, ((float)setting.getWindowWidth())/setting.getWindowHeight(), 0.1f, 5000.0f); //-5000.f ist die maximale z tiefe
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
        Keyboard.enableRepeatEvents(true);
        Mouse.poll();
        
        //create the font
        Font awtFont = new Font("Arial", Font.BOLD, 48);
        uniFont = new UnicodeFont(awtFont, 48, false, false);
        uniFont.addAsciiGlyphs();
        uniFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); 
        try {
			uniFont.loadGlyphs();
		} catch (SlickException e1) {}

        
        renderFieldList = new LinkedList<Field>();
        Iterator<Field> iter = modelReader.getFieldIterator();
        while (iter.hasNext()) {
        	renderFieldList.add(iter.next());
        }
        
      //TODO: center map and calculate initial zoom!!!
        
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
		
		if (Mouse.isInsideWindow()) {
			if (mx < 50) {
				x+=5;
			}
			else if (mx > setting.getWindowWidth()-50) {
				x-=5;
			}
			if (my < 50) {
				y-=5;
			}
			else if (my > setting.getWindowHeight()-50) {
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
			y+=5;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			y-=5;
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
				"jinput-dx8_64.dll",
				"jinput-dx8.dll",
				"jinput-raw_64.dll",
				"jinput-raw.dll",
				"libjinput-linux.so",
				"libjinput-linux64.so",
				"libjinput-osx.jnilib",
				"liblwjgl.jnilib",
				"liblwjgl.so",
				"liblwjgl64.so",
				"libopenal.so",
				"libopenal64.so",
				"lwjgl.dll",
				"lwjgl64.dll",
				"openal.dylib",
				"OpenAL32.dll",
				"OpenAL64.dll"
		};
		String tmpdir = System.getProperty("java.io.tmpdir");
		
		for (String act : list) {
			InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("native/" + act);
			saveFile(tmpdir + "/" + act, input);
		}
		
		String seperator;
		if (System.getProperty("sun.desktop").equals("windows")) seperator = ";";
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
		Model model = new Model(WorldRepresentation.getDefault(), matchinfo, 0);
		model.matchStart(new long[] {0,1}, new byte[]   {2,3,4,
														 6,8,9,10,
														 11,12,11,10,
														 9,8,6,5,
														 2,6,9});
		
		//Setting setting = new Setting(Display.getDesktopDisplayMode().getWidth(), Display.getDesktopDisplayMode().getHeight(), true);
		//Setting setting = new Setting(1280, 1024, true);
		//Setting setting = new Setting(800, 600, true);
		Setting setting = new Setting(400, 300, true);
		
		GameGUI gameGUI = new GameGUI(0, model, null, null, setting);
		new Thread(gameGUI).start();
	}
	
	private static void saveFile(String filename, InputStream inputStr) throws IOException {
		   FileOutputStream fos = new FileOutputStream(filename);
		   int len = -1;
		   byte[] buffer = new byte[1024];
		   while ((len = inputStr.read(buffer)) != -1) {
		      fos.write(buffer,0,len);
		   }
		   fos.close();
		}
	
}
