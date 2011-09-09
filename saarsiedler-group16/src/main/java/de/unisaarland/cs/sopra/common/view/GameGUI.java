package de.unisaarland.cs.sopra.common.view;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
//import org.lwjgl.util.glu.GLU;
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
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class GameGUI extends View implements Runnable{

	private Map<Player,String> playerNames;
	private Setting setting;
	private Map<FieldType,Texture> textureMap;
	private Map<Integer,Texture> numberTextureMap;
	private List<Field> renderFieldList;
	float x = -400.0f;
	float y = 600.0f;
	float z = -2000.0f;
	
	GameGUI(long meID, ModelReader modelReader, ControllerAdapter controllerAdapter, Map<Player,String> playerNames, Setting setting) throws Exception {
		super(meID, modelReader, controllerAdapter);
		this.modelReader = modelReader;
		this.setting = setting;
		this.playerNames = playerNames;
		this.controllerAdapter = controllerAdapter;
	}
	
	private void renderField(Field f) {
		int x = 0;
		int y = 0;
		   switch(f.getLocation().getY()%2) {
		   case 0:
			   x = f.getLocation().getX()*250;
			   y = f.getLocation().getY()*215; 
			   break;
		   case 1:
			   x = f.getLocation().getX()*250-125;
			   y = f.getLocation().getY()*215;
			   break;
		   }
		
	     textureMap.get(f.getFieldType()).bind();
	     GL11.glBegin(GL11.GL_POLYGON);
	      // GL11.glColor4f(1.0f,1.0f,1.0f,1.0f); transparenz
	       GL11.glTexCoord2f(0,0);
	       GL11.glVertex2i(-150+x, -150+y);
	       GL11.glTexCoord2f(1,0);
	       GL11.glVertex2i(150+x, -150+y);
	       GL11.glTexCoord2f(1,1);
	       GL11.glVertex2i(150+x, 150+y);
	       GL11.glTexCoord2f(0,1);
	       GL11.glVertex2i(-150+x, 150+y);
	     GL11.glEnd();
	}
	
	
	
	private void render() {
		   GL11.glMatrixMode(GL11.GL_PROJECTION);
		   GL11.glLoadIdentity();
		   //GLU.gluPerspective(45.0f, ((float)setting.getWindowWidth())/setting.getWindowHeight(), 0.1f, 5000.0f); //-5000.f ist die maximale z tiefe
		   GL11.glMatrixMode(GL11.GL_MODELVIEW);
		   // clear the screen
		   GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		   // center square according to screen size
		   GL11.glPushMatrix();
		   GL11.glTranslatef(x,y,z); //-4000 ist z koord
			// rotate square according to angle
		   GL11.glRotatef(180, 1.0f, 0, 0);
		   GL11.glColor3f(1.0f, 1.0f, 1.0f);
		   //TODO: center map and calculate initial zoom!!!
	
		  // GL11.glTranslatef(-modelReader.getBoardWidth()*215/2, modelReader.getBoardHeight()*250/2, 0.0f);
		   
		   Iterator<Field> iter = renderFieldList.iterator();
		   while (iter.hasNext()) {
			   		renderField(iter.next());
			   		//iter.remove();
			   }
		   GL11.glPopMatrix();
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
	
	@Override
	public void run() {
		
		try {
			Display.setDisplayMode(new DisplayMode(setting.getWindowWidth(), setting.getWindowHeight()));
			Display.setFullscreen(setting.isFullscreen());
			Display.setTitle("Die Siedler von der Saar");
			Display.setVSyncEnabled(true);
			Display.create();
			
			textureMap = new HashMap<FieldType,Texture>();
			textureMap.put(FieldType.MOUNTAINS, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Mountains.png")));
			textureMap.put(FieldType.DESERT, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Desert.png")));
			textureMap.put(FieldType.FIELDS, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields.png")));
			textureMap.put(FieldType.FOREST, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Forest.png")));
			textureMap.put(FieldType.HILLS, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Hills.png")));
			textureMap.put(FieldType.PASTURE, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Pasture.png")));
			textureMap.put(FieldType.WATER, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Water.png")));
			
			numberTextureMap = new HashMap<Integer,Texture>();
			numberTextureMap.put(5, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/5.png")));
		} catch (Exception e) {}
		
		GL11.glDisable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glClearDepth(1.0f); // Depth Buffer Setup
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE);
		
        Keyboard.enableRepeatEvents(true);
        
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
        //render two times to fill the double buffer

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
	
	private void handleInput() {
		int mx = Mouse.getX();
		int my = Mouse.getY();
		
		if (mx < 50) {
			x+=5;
		}
		else if (mx > setting.getWindowWidth()-50) {
			x-=5;
		}
		if (my < 50) {
			y+=5;
		}
		else if (my > setting.getWindowHeight()-50) {
			y-=5;
		}
		
		switch (Mouse.getEventDWheel()) {
			case -1: 			
				if (z+50 < -500)
					z+=50;
				break;
			case 1: 			
				if (z-50 > -5000)
					z-=50; 
				break;
		}
		
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			x+=5;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			x-=5;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			y-=5;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			y+=5;
		}
		if (Keyboard.isKeyDown(0)) {
			if (z+50 < -500)
				z+=50;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
			if (z-50 > -5000)
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
		
		System.setProperty("java.library.path", System.getProperty("java.library.path") + ":/tmp");

		
		java.lang.reflect.Field vvv = ClassLoader.class.getDeclaredField("sys_paths");
		
		vvv.setAccessible(true); 
		vvv.set(null, null);
		
		
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
		model.matchStart(new long[] {0,1}, new byte[]   {2,3,4,5,
														 6,8,9,10,
														 11,12,11,10,
														 9,8,6,5});
		
		Setting setting = new Setting(Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight(), false);
		GameGUI gameGUI = new GameGUI(0, model, null, null, setting);
		//new Thread(gameGUI).start();
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
