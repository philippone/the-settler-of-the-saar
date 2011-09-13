package model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.lwjgl.opengl.DisplayMode;

import de.unisaarland.cs.sopra.common.PlayerColors;
import de.unisaarland.cs.sopra.common.Setting;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.view.GameGUI;


public class ModelReaderTest2 {

	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel2();
	}	
	
	@Test
	public void testGetInitVillages() {
		int initVillages=model.getInitVillages();
		assertTrue(initVillages==2);
	}
	
	@Test
	public void testGetMaxBuilding() {
		int maxTowns=model.getMaxBuilding(BuildingType.Town);
		assertTrue(maxTowns==5);
		int maxVillages=model.getMaxBuilding(BuildingType.Village);
		assertTrue(maxVillages==9);
	}
	
	@Test
	public void testGetRound() {
		int roundNumber=model.getRound();
		assertEquals(roundNumber,0);
		
	}
	
	@Test
	public void testBuildableVillageIntersections() {
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
		
		model.buildSettlement(new Location(0,0,0), BuildingType.Village);
		model.buildStreet(new Location(0,0,0));
		
		model.buildSettlement(new Location(1,0,0), BuildingType.Village);
		model.buildStreet(new Location(1,0,0));
		
		model.buildSettlement(new Location(2,0,0), BuildingType.Village);
		model.buildStreet(new Location(2,0,0));
		
		model.buildSettlement(new Location(3,0,0), BuildingType.Village);
		model.buildStreet(new Location(3,0,0));
		
		Player pl=model.getCurrentPlayer();
		Set<Intersection> si=model.buildableVillageIntersections(pl);
		if (!(si.isEmpty())){
		for (Intersection i : si){ //check for all intersections
			Set<Path> sp=model.getPathsFromIntersection(i);
			boolean b=false;
			for(Path p : sp){ // check for all neighbor PAths
				b= b || (p.hasStreet() && p.getStreetOwner()==pl);
				// check if player owns a street on this path 
			}
			assertTrue(b);
			// check player has a street on a neighbor path leading to this intersection
			Set<Intersection> si1=model.getIntersectionsFromIntersection(i);
			b=false;
			for (Intersection i1 : si1){ // check for all neighbor intersections
				b=b | (i1.hasOwner());
				// check if there is a player on this intersection
			}
			assertFalse(b);
			// check there is no building on the neighbor intersections
			assertFalse(i.hasOwner());
			// check there is no building here
		}
		}
		else assertTrue(si.isEmpty());
	}
	
	@Test
	public void testBuildableTownIntersections() {
		Player pl=model.getCurrentPlayer();
		Set<Intersection> si=model.buildableTownIntersections(pl);
		if (!(si.isEmpty())){
		for (Intersection i : si){ //check for all intersections
			assertTrue((i.getOwner()==pl && i.getBuildingType()==BuildingType.Village));
			// check player owns a village here
		}
		}
		else assertTrue(si.isEmpty());
	}
	
	
	
	@Test
	public void testBuildableStreetPaths() {
		Player pl=model.getCurrentPlayer();
		Set<Path> sp=model.buildableStreetPaths(pl);
		if (!(sp.isEmpty())){
		for(Path p : sp){ //check for all paths
			boolean b=false;
			Set<Path> sp1=model.getPathsFromPath(p);
			for(Path p1: sp1){ // check for each neighbor path
				b=b | (p1.getStreetOwner()==pl);
				// check if player owns a street on this path
			}
			assertTrue(b);
			//check player has a street on a neighbor path
			assertFalse(p.hasStreet());
			// check this path is free from street
		}
		}
		else assertTrue(sp.isEmpty());
	}
	
	@Test
	public void testBuildableCatapultPaths() {
		Player pl=model.getCurrentPlayer();
		Set<Path> sp=model.buildableCatapultPaths(pl);
		if (!(sp.isEmpty())){
		for(Path p : sp){ // check for all paths
			Set<Intersection> si=model.getIntersectionsFromPath(p);
			boolean b=false;
			for(Intersection i: si){ // check for all neighbor intersections
				b=b | (i.getOwner()==pl && i.getBuildingType()==BuildingType.Town);
				// check if player owns a Town on this Intersection
			}
			assertTrue(b);
			//check player has a Town on a neighbor Intersection of this  path
		}
		}
		else assertTrue(sp.isEmpty());
	}
	

	
}
