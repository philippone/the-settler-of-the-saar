package model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.lwjgl.opengl.DisplayMode;

import de.unisaarland.cs.sopra.common.ModelObserver;
import de.unisaarland.cs.sopra.common.PlayerColors;
import de.unisaarland.cs.sopra.common.Setting;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Point;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.sopra.common.view.GameGUI;
import de.unisaarland.cs.st.saarsiedler.comm.results.AttackResult;


public class ModelTest {
	
	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel2();
	}
	
	@Test
	public void testAddObserver() throws IOException {
		Player p1 = model.getTableOrder().get(0);
		ModelObserver view = TestUtil.getTestView(p1);
		// erster Observer hinzufuegen
		model.addModelObserver(view);
		assertNotNull("Model == null", model);
		assertNotNull("View == null", view);
		assertTrue("ModelObserver-Liste ist leer (sollte es aber nicht)", model.getModelObservers().size() == 1);
		// gleicher Observer wieder adden
		model.addModelObserver(view);
		assertTrue("ModelObserver-Liste hat zwei gleiche Elemente", model.getModelObservers().size() == 1);
		// zweiter Observer adden
		Player p2 = model.getTableOrder().get(1);
		ModelObserver view2 = TestUtil.getTestView(p2);
		model.addModelObserver(view2);
		assertTrue("es wurde kein 2. Observer hinzugefuegt",model.getModelObservers().size()==2);
	}
	
	
	@Test
	public void testRemoveObserver() throws IOException {
		// erster Observer einfuegen und dann loeschen
		Player p1 = model.getTableOrder().get(0);
		ModelObserver view = TestUtil.getTestView(p1);
		model.addModelObserver(view);
		
		model.removeModelObserver(view);
		assertNotNull("Model == null", model);
		assertNotNull("View == null", view);
		assertTrue("ModelObserver-Liste ist leider nicht leer, sollte es aber sein", model.getModelObservers().size()==0);
		// Liste leer, trotzdem entfernen
		try {
			model.removeModelObserver(view);
			fail("sollte eine Ausnahme werfen, da Liste bereits Leer ist");
		} catch(Exception e) {
			//Test sollte durchlaufen
		}
	}
	
	
	
	@Test
	public void testCalculateLongestRoad() {
		 //neue Runde (die erste)
		 model.newRound(8);
		 // owner = currentPlayer
		 Player owner = model.getTableOrder().get(0);
		 Player gegner = model.getTableOrder().get(1);
		 // verteile viele Resourcen
		 owner.modifyResources(new ResourcePackage(10000,10000,10000,10000,10000));
		 gegner.modifyResources(new ResourcePackage(10000,10000,10000,10000,10000));
		 // erwartete Liste von longestRoads
		 List< List<Path> > expectedLongestRoad = new ArrayList<List<Path>>();
		 List<Path> longRoad0 = new LinkedList<Path>();
		 List<Path> longRoad1 = new LinkedList<Path>();
		 List<Path> longRoad2 = new LinkedList<Path>();		
		 List<Path> reversedLongRoad0 = new LinkedList<Path>();
		 List<Path> reversedLongRoad1 = new LinkedList<Path>();
		 List<Path> reversedLongRoad2 = new LinkedList<Path>();
		 
		 model.getIntersection(new Location(1,1,0)).createBuilding(BuildingType.Village, owner); 
		 model.getIntersection(new Location(2,0,3)).createBuilding(BuildingType.Village, owner); 
		 
		 model.getPath(new Location(0,0,2)).createStreet(owner);
		 model.getPath(new Location(1,0,1)).createStreet(owner);
		 model.getPath(new Location(1,0,2)).createStreet(owner);
		 model.getPath(new Location(1,1,3)).createStreet(owner);
		 model.getPath(new Location(1,1,2)).createStreet(owner);
		 model.getPath(new Location(1,2,3)).createStreet(owner);
		 model.getPath(new Location(1,2,2)).createStreet(owner);
		 model.getPath(new Location(1,2,1)).createStreet(owner);
		 model.getPath(new Location(2,0,4)).createStreet(owner);
		 model.getPath(new Location(2,0,1)).createStreet(owner);
		 model.getPath(new Location(2,0,2)).createStreet(owner);
		 model.getPath(new Location(2,0,3)).createStreet(owner);
		 model.getPath(new Location(2,1,3)).createStreet(owner);
		 model.getPath(new Location(2,1,2)).createStreet(owner);
		 model.getPath(new Location(2,2,3)).createStreet(owner);
		 model.getPath(new Location(3,0,1)).createStreet(owner);
		 model.getPath(new Location(3,1,3)).createStreet(owner);
		 model.getPath(new Location(3,1,2)).createStreet(owner);
		 model.getPath(new Location(3,2,3)).createStreet(owner);
		 model.getPath(new Location(3,2,1)).createStreet(owner);
		 // this last one is  on the other side of an opponent settlement
		 
		 longRoad0.add(model.getPath(new Location(3,2,3)));
		 longRoad0.add(model.getPath(new Location(3,1,2)));
		 longRoad0.add(model.getPath(new Location(3,1,3)));
		 longRoad0.add(model.getPath(new Location(3,1,4)));
		 longRoad0.add(model.getPath(new Location(2,0,3)));
		 longRoad0.add(model.getPath(new Location(2,0,4)));
		 longRoad0.add(model.getPath(new Location(2,0,5)));
		 longRoad0.add(model.getPath(new Location(1,1,3)));
		 longRoad0.add(model.getPath(new Location(1,1,2)));
		 longRoad0.add(model.getPath(new Location(1,2,3)));
		 longRoad0.add(model.getPath(new Location(1,2,2)));
		 longRoad0.add(model.getPath(new Location(1,2,1)));
		 reversedLongRoad0.addAll(longRoad0);
		 Collections.reverse(reversedLongRoad0);
		 
		 longRoad1.add(model.getPath(new Location(3,2,3)));
		 longRoad1.add(model.getPath(new Location(3,1,2)));
		 longRoad1.add(model.getPath(new Location(3,1,3)));
		 longRoad1.add(model.getPath(new Location(3,1,4)));
		 longRoad1.add(model.getPath(new Location(2,0,3)));
		 longRoad1.add(model.getPath(new Location(2,0,4)));
		 longRoad1.add(model.getPath(new Location(2,0,5)));
		 longRoad1.add(model.getPath(new Location(1,1,3)));
		 longRoad1.add(model.getPath(new Location(2,0,1)));
		 longRoad1.add(model.getPath(new Location(2,1,3)));
		 longRoad1.add(model.getPath(new Location(2,1,2)));
		 longRoad1.add(model.getPath(new Location(2,2,3)));
		 reversedLongRoad1.addAll(longRoad1);
		 Collections.reverse(reversedLongRoad1);
		 
		 longRoad2.add(model.getPath(new Location(2,2,3)));
		 longRoad2.add(model.getPath(new Location(2,1,2)));
		 longRoad2.add(model.getPath(new Location(2,1,3)));
		 longRoad2.add(model.getPath(new Location(2,0,2)));
		 longRoad2.add(model.getPath(new Location(2,0,3)));
		 longRoad2.add(model.getPath(new Location(2,0,4)));
		 longRoad2.add(model.getPath(new Location(2,0,5)));
		 longRoad2.add(model.getPath(new Location(1,1,3)));
		 longRoad2.add(model.getPath(new Location(1,1,2)));
		 longRoad2.add(model.getPath(new Location(1,2,3)));
		 longRoad2.add(model.getPath(new Location(1,2,2)));
		 longRoad2.add(model.getPath(new Location(1,2,1)));
		 reversedLongRoad2.addAll(longRoad2);
		 Collections.reverse(reversedLongRoad2);
		 
		 // gegnerisches Dorf und Strassen
		 model.getIntersection(new Location(2,2,3)).createBuilding(BuildingType.Village,gegner);
		 model.getPath(new Location(2,2,2)).createStreet(gegner);
		 model.getPath(new Location(2,2,1)).createStreet(gegner);
		 model.getPath(new Location(2,2,0)).createStreet(gegner);
		 
		 expectedLongestRoad.add(longRoad0);
		 expectedLongestRoad.add(longRoad1);
		 expectedLongestRoad.add(longRoad2);
		 expectedLongestRoad.add(reversedLongRoad0);
		 expectedLongestRoad.add(reversedLongRoad1);
		 expectedLongestRoad.add(reversedLongRoad2);
		 
		 List<List<Path>> currentLongestRoad = model.calculateLongestRoads(owner);
		 
		 assertEquals(currentLongestRoad.size(),expectedLongestRoad.size());
		 //assertTrue(expectedLongestRoad.containsAll(currentLongestRoad));
		 assertTrue(currentLongestRoad.containsAll(expectedLongestRoad) && expectedLongestRoad.containsAll(currentLongestRoad));
		 
		 // claim longest Road:Road1
		 //model.longestRaodClaimed(Model.getLocationList(longRoad0));
	}
	
	@Test
	public void testSetTableOrder() {
		assertEquals("TableOrder nicht richitg gesetzt", model.getPlayerMap().get(1L), model.getTableOrder().get(0));
		assertEquals("TableOrder nicht richitg gesetzt", model.getPlayerMap().get(0L), model.getTableOrder().get(1));
	}
	
	@Test
	public void testSetFieldNumbers() {
		Iterator<Field> fieldIterator = model.getFieldIterator();
		// an neue Welt anpassen!!!!
		long[] fieldnumbers = new long[] {2,3,4,5,
				 6,8,9,10,
				 11,12,11,10,
				 9,8,6,5}; 
		int i = 0;
		long[] reihenfolge = new long[16];
		while (fieldIterator.hasNext()) {
			Field f = fieldIterator.next();
			if (f.getNumber() != 0) {
				reihenfolge[i++] = f.getNumber();
			}
		}
		boolean status = false;
		i = 0;
		while(i < 16) {
			if(fieldnumbers[i] == reihenfolge[i]) {
				status = true;
			} break;
		}
		
		// ist die Reihenfolge der Felder richitg
		assertTrue("Feldnummern nicht richitg gesetzt", status);
	}
	
	@Test
	public void testUpdateLongestRoad() {
		model.buildSettlement(new Location(1,0,1), BuildingType.Village);
		model.buildStreet(new Location(1,0,1));
		
		model.buildSettlement(new Location(3,1,2), BuildingType.Village);
		model.buildStreet(new Location(3,1,1));
		// in Runde 1 gehen
		model.newRound(8);
		// current Player genug Resourcen geben
		model.getCurrentPlayer().modifyResources(new ResourcePackage(10000,10000,10000,10000,10000));
		// longest Road bauen
		model.buildStreet(new Location(1,0,2));
		model.buildStreet(new Location(2,0,4));
		model.buildStreet(new Location(2,0,3));
		model.buildStreet(new Location(2,0,2));
		model.buildStreet(new Location(2,0,1));
		model.buildStreet(new Location(2,1,5));
		model.buildStreet(new Location(2,1,0));
		model.buildStreet(new Location(2,1,1));
		model.buildStreet(new Location(2,1,2));
		//longest Road claim
		List<List<Path>> longestRoad = model.calculateLongestRoads(model.getCurrentPlayer());
		model.longestRoadClaimed(Model.getLocationList(longestRoad.get(0)));
		
		// neue Runde (Gegner an der Reihe)
		model.newRound(6);
		model.getCurrentPlayer().modifyResources(new ResourcePackage(10000,10000,10000,10000,10000));
		// Gegner: Village auf longestClaimedRoad bauen
		model.buildStreet(new Location(3,1,0));
		model.buildSettlement(new Location(3,1,0), BuildingType.Village);
		// erwartete Liste
		List<Path> expected = new LinkedList<Path>();
		for (int i = 0; i < 5; i++) {
			expected.add(model.getLongestClaimedRoad().get(i));
		}

		assertTrue("updateLongestRoad fehlgeschlagen", model.getLongestClaimedRoad().size() == 5);
		assertEquals("nicht die richige LongestRoad geupdatet", model.getLongestClaimedRoad(), expected);
	}
	
	
	@Test
	public void testUpdateLongestRoad2() {

		
		model.buildSettlement(new Location(1,0,1), BuildingType.Village);
		model.buildStreet(new Location(1,0,1));
		
		model.buildSettlement(new Location(3,2,2), BuildingType.Village);
		model.buildStreet(new Location(3,2,1));
		// in Runde 1 gehen
		model.newRound(8);
		// current Player genug Resourcen geben
		model.getCurrentPlayer().modifyResources(new ResourcePackage(10000,10000,10000,10000,10000));
		// longest Road bauen
		model.buildStreet(new Location(1,0,2));
		model.buildStreet(new Location(2,0,4));
		model.buildStreet(new Location(2,0,3));
		model.buildStreet(new Location(2,0,2));
		model.buildStreet(new Location(2,0,1));
		model.buildStreet(new Location(2,1,5));
		model.buildStreet(new Location(2,1,0));
		model.buildStreet(new Location(2,1,1));
		model.buildStreet(new Location(2,1,2));
		//longest Road claim
		List<List<Path>> longestRoad = model.calculateLongestRoads(model.getCurrentPlayer());
		model.longestRoadClaimed(Model.getLocationList(longestRoad.get(0)));
		
		// neue Runde (Gegner an der Reihe)
		model.newRound(6);
		model.getCurrentPlayer().modifyResources(new ResourcePackage(10000,10000,10000,10000,10000));
		// Gegner: Village auf longestClaimedRoad bauen
		model.buildStreet(new Location(3,2,0));
		model.buildSettlement(new Location(3,2,0), BuildingType.Village);
		// erwartete Liste
		List<Path> expected = new LinkedList<Path>();
		for (int i = 0; i < 9; i++) {
			expected.add(model.getLongestClaimedRoad().get(i));
		}

		assertTrue("updateLongestRoad fehlgeschlagen", model.getLongestClaimedRoad().size() == 5);
		assertEquals("nicht die richige LongestRoad geupdatet", model.getLongestClaimedRoad(), expected);
	}
	
	@Test
	public void testGetLocationField() {
		Field f = model.getField(new Point(1,1));
		Point p = Model.getLocation(f);
		assertEquals("Felder sind ungleich",f.getLocation(), p);
		// Feld ausserhalb des Spielfeldes
		try {
			Field f2 = model.getField(new Point(20,20));
			fail("Point ausserhalb des Spielfeldes, sollte IllegalArgumentException werfen");
		} catch(IllegalArgumentException e) {
			// Test sollte durchlaufen
		}
		try {
			Field f2 = model.getField(new Point(-1,-1));
			fail("Point ausserhalb des Spielfeldes, sollte IllegalArgumentException werfen");
		} catch(IllegalArgumentException e) {
			// Test sollte durchlaufen
		}
	}
	
	@Test
	public void testGetLocationIntersection() {
		Intersection i = model.getIntersection(new Location(1,1,1));
		Location l = Model.getLocation(i);
		assertEquals("Intersections sind ungleich", i.getLocation(), l);
		try {
			Intersection i2 = model.getIntersection(new Location(50,50,50));
			fail("Intersection ausserhalb des Spielfeldes, sollte IllegalArumentException werfen");
		} catch(IllegalArgumentException e) {
			//Test sollte durchlaufen
		}
		try {
			Intersection i2 = model.getIntersection(new Location(-5,-5,-5));
			fail("Intersection ausserhalb des Spielfeldes, sollte IllegalArumentException werfen");
		} catch(IllegalArgumentException e) {
			//Test sollte durchlaufen
		}
	}
	
	@Test
	public void testGetLocationPath() {
		Path p = model.getPath(new Location(1,1,1));
		Location l = Model.getLocation(p);
		assertEquals("Intersections sind ungleich", p.getLocation(), l);
		try {
			Path p2 = model.getPath(new Location(50,50,50));
			fail("Intersection ausserhalb des Spielfeldes, sollte IllegalArumentException werfen");
		} catch(IllegalArgumentException e) {
			//Test sollte durchlaufen
		}
		try {
			Path p2 = model.getPath(new Location(-5,-5,-5));
			fail("Intersection ausserhalb des Spielfeldes, sollte IllegalArumentException werfen");
		} catch(IllegalArgumentException e) {
			//Test sollte durchlaufen
		}
	}
	
	@Test
	public void testGetCurrentPlayer() {
		// naechste Runde
		model.newRound(8);
		Player expectedPlayer = model.getTableOrder().get(0);
		Player currentPlayer = model.getCurrentPlayer();
		assertEquals("nicht der aktuelle Player", expectedPlayer, currentPlayer);
		// naechste Runde
		model.newRound(6);
		Player expectedPlayer1 = model.getTableOrder().get(1);
		Player currentPlayer1 = model.getCurrentPlayer();
		assertEquals("nicht der aktuelle Player", expectedPlayer1, currentPlayer1);
	}
	
	
	@Test
	public void testNewRound(){
		assertEquals(0 ,model.getRound());
		model.newRound(12);
		assertEquals(1, model.getRound());
		assertNotSame(0, model.getRound());
		
	}
	/**
	 * Angriff gegen gegnerische Village und Town - erfolgreich (beide male)
	 */
	@Test
	public void testAttackSettlement() throws IOException{
		
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
		
		// Gegner
		Player gegner = model.getTableOrder().get(0);

		//gibt den akt. Playern alle Resourcen um Komplikationen mit build zu vermeiden.
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		model.getTableOrder().get(1).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		//Angriffsziele bauen (Initialisierungsphase)
		// 1.Player
		model.buildSettlement(new Location(0,0,0) , BuildingType.Village);
		model.buildStreet(new Location(0,0,0));
		// 2. Player
		model.buildSettlement(new Location(0,0,4) , BuildingType.Village);
		model.buildStreet(new Location(1,0,0));
		// 3. Player
		model.buildSettlement(new Location(3,3,0) , BuildingType.Village);
		model.buildStreet(new Location(3,3,0));
		// wieder 3
		model.buildSettlement(new Location(3,3,3), BuildingType.Village);
		model.buildStreet(new Location(3,3,2));
		// 2. Player
		model.buildSettlement(new Location(1,0,4), BuildingType.Village);
		model.buildStreet(new Location(1,0,4));
		// 1. Player
		model.buildSettlement(new Location(1,1,0) , BuildingType.Village);
		model.buildStreet(new Location(1,1,0));
		
		//neue Runde (1.Player)
		model.newRound(12);
		// Upgrade 1 Village
		model.buildSettlement(new Location(1,1,0), BuildingType.Town);
		
		//neue Runde (2. Player)
		model.newRound(3);
		model.buildSettlement(new Location(1,0,0), BuildingType.Town);
		model.buildCatapult(new Location(0,0,4), true);
		model.catapultMoved(new Location(0,0,4), new Location(0,0,5), true);
		model.attackSettlement(new Location(0,0,5), new Location(0,0,0), AttackResult.SUCCESS);
		assertTrue("Village nicht in den Besitz des Angreifenden uebergegangen", model.getIntersection(new Location(0,0,0)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue("das angreifende Katapult ist weg", model.getPath(new Location(0,0,5)).getCatapultOwner().equals(model.getCurrentPlayer()));
		int expectedVP_gegner = 2;
		int expectedVP_angreifer = 4;
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		assertEquals("VictoryPoints fehlerhaft beim Gegner", expectedVP_gegner, model.getCurrentVictoryPoints(gegner));
		
		model.catapultMoved(new Location(0,0,5), new Location(0,0,0), true);
		model.catapultMoved(new Location(0,0,0),new Location(0,0,1), true);
		model.attackSettlement(new Location(0,0,1), new Location(0,0,2), AttackResult.SUCCESS);
		assertTrue("Town wurde nciht richitg gedowngradet", model.getIntersection(new Location(0,0,2)).getBuildingType().equals(BuildingType.Village));
		assertFalse("Village gehoert dem falschen Spieler", model.getIntersection(new Location(0,0,2)).getOwner().equals(model.getCurrentPlayer()));	
		assertTrue("Village gehoert nicht mehr dem Opfer", model.getIntersection(new Location(0,0,2)).getOwner().equals(gegner));
		assertTrue("das angreifende Katapult ist weg", model.getPath(new Location(0,0,1)).getCatapultOwner().equals(model.getCurrentPlayer()));
		int expectedVP2_gegner = 0;
		int expectedVP2_angreifer = 5;
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP2_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		assertEquals("VictoryPoints fehlerhaft beim Gegner", expectedVP2_gegner, model.getCurrentVictoryPoints(gegner));
	}
	
	/**
	 * Angriff gegen gegnerische Village und Town - erfolgreich (beide male), aber habe selbst genug davon
	 */
	@Test
	public void testAttackSettlement2(){
		// Gegner
		Player gegner = model.getTableOrder().get(0);
		//gibt den akt. Playern alle Resourcen um Komplikationen mit build zu vermeiden.
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		model.getTableOrder().get(1).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		//Angriffsziele bauen (Initialisierungsphase)
		// 1.Player
		model.buildSettlement(new Location(0,0,0) , BuildingType.Village);
		model.buildStreet(new Location(0,0,0));
		// 2. Player
		model.buildSettlement(new Location(0,0,4) , BuildingType.Village);
		model.buildStreet(new Location(1,0,0));
		// 3. Player
		model.buildSettlement(new Location(3,3,0) , BuildingType.Village);
		model.buildStreet(new Location(3,3,0));
		// wieder 3
		model.buildSettlement(new Location(3,3,3), BuildingType.Village);
		model.buildStreet(new Location(3,3,2));
		// 2. Player
		model.buildSettlement(new Location(1,0,4), BuildingType.Village);
		model.buildStreet(new Location(1,0,4));
		// 1. Player
		model.buildSettlement(new Location(1,1,0) , BuildingType.Village);
		model.buildStreet(new Location(1,1,0));
		
		//neue Runde (1.Player)
		model.newRound(12);
		// Upgrade 1 Village
		model.buildSettlement(new Location(1,1,0), BuildingType.Town);
		
		//neue Runde (2. Player)
		model.newRound(3);
		model.buildSettlement(new Location(1,0,0), BuildingType.Town);
		model.buildStreet(new Location(1,0,3));
		model.buildStreet(new Location(2,0,4));
		model.buildSettlement(new Location(2,0,4), BuildingType.Village);
		model.buildStreet(new Location(3,0,5));
		model.buildStreet(new Location(3,0,4));
		model.buildSettlement(new Location(3,0,4), BuildingType.Village);
		model.buildStreet(new Location(3,0,3));
		model.buildStreet(new Location(3,0,2));
		model.buildSettlement(new Location(3,0,2), BuildingType.Village);
		model.buildStreet(new Location(3,0,0));
		model.buildStreet(new Location(3,1,5));
		model.buildSettlement(new Location(3,1,0), BuildingType.Village);
		model.buildStreet(new Location(3,1,0));
		model.buildStreet(new Location(3,1,1));
		model.buildSettlement(new Location(3,1,2), BuildingType.Village);
		model.buildStreet(new Location(3,2,5));
		model.buildSettlement(new Location(3,2,0), BuildingType.Village);
		model.buildStreet(new Location(2,1,1));
		model.buildStreet(new Location(2,1,0));
		model.buildSettlement(new Location(2,1,0), BuildingType.Village);
		model.buildStreet(new Location(2,0,5));
		model.buildSettlement(new Location(2,0,0), BuildingType.Village);
		model.buildSettlement(new Location(2,0,0), BuildingType.Town);
		model.buildSettlement(new Location(1,0,4), BuildingType.Town);
		model.buildSettlement(new Location(3,0,4), BuildingType.Town);
		model.buildSettlement(new Location(3,0,0), BuildingType.Town);
		// Player 2 hat jetzt maxVillages und maxTowns
		
		
		model.buildCatapult(new Location(0,0,4), true);
		model.catapultMoved(new Location(0,0,4), new Location(0,0,5), true);
		model.attackSettlement(new Location(0,0,5), new Location(0,0,0), AttackResult.SUCCESS);
		// village sollte zerstoert werden, da der Angreifer genug Villages hat
		try {
			model.getIntersection(new Location(0,0,0)).getOwner();
			fail("Intersection darf kein Gebaeude mehr haben");
		} catch (Exception e) {
			// Test lauft durch
		}
		assertTrue("Katapult nicht mehr da",model.getPath(new Location(0,0,5)).getCatapultOwner().equals(model.getCurrentPlayer()));
		int expectedVP_gegner = 2;
		int expectedVP_angreifer = 15;
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		assertEquals("VictoryPoints fehlerhaft beim Gegner", expectedVP_gegner, model.getCurrentVictoryPoints(gegner));
		
		// Town wird degradiert zur Village, bleibt im Besitz des Opfers
		model.catapultMoved(new Location(0,0,5), new Location(0,0,0), true);
		model.catapultMoved(new Location(0,0,0),new Location(0,0,1), true);
		model.attackSettlement(new Location(0,0,1), new Location(0,0,2), AttackResult.SUCCESS);
		assertTrue("Village nicht mehr im Besitz des Opfers", model.getIntersection(new Location(0,0,2)).getOwner().equals(gegner));
		assertTrue("Katapult wurde zerstoert", model.getPath(new Location(0,0,2)).getCatapultOwner().equals(model.getCurrentPlayer()));
		assertTrue("Village ist noch Town (BuildingType", model.getIntersection(new Location(0,0,2)).getBuildingType().equals(BuildingType.Village));
		int expectedVP2_gegner = 1;
		int expectedVP2_angreifer = 15;
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP2_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		assertEquals("VictoryPoints fehlerhaft beim Gegner", expectedVP2_gegner, model.getCurrentVictoryPoints(gegner));
	}
	
	
	/**
	 * Angriff gegen gegnerische Village und Town - erfolgreich (beide male), aber habe selbst genug davon, Opfer hat genug Villages -> AngriffsTown wird zerstoert
	 */
	@Test
	public void testAttackSettlement2_2(){
		// Gegner
		Player gegner = model.getTableOrder().get(0);
		//gibt den akt. Playern alle Resourcen um Komplikationen mit build zu vermeiden.
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		model.getTableOrder().get(1).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		//Angriffsziele bauen (Initialisierungsphase)
		// 1.Player
		model.buildSettlement(new Location(0,0,0) , BuildingType.Village);
		model.buildStreet(new Location(0,0,0));
		// 2. Player
		model.buildSettlement(new Location(0,0,4) , BuildingType.Village);
		model.buildStreet(new Location(1,0,0));
		// 3. Player
		model.buildSettlement(new Location(3,3,0) , BuildingType.Village);
		model.buildStreet(new Location(3,3,0));
		// wieder 3
		model.buildSettlement(new Location(3,3,3), BuildingType.Village);
		model.buildStreet(new Location(3,3,2));
		// 2. Player
		model.buildSettlement(new Location(1,0,4), BuildingType.Village);
		model.buildStreet(new Location(1,0,4));
		// 1. Player
		model.buildSettlement(new Location(1,1,0) , BuildingType.Village);
		model.buildStreet(new Location(1,1,0));
		
		//neue Runde (1.Player)
		model.newRound(12);
		// Upgrade 1 Village
		model.buildSettlement(new Location(1,1,0), BuildingType.Town);
		model.buildStreet(new Location(0,1,5));
		model.buildSettlement(new Location(0,1,0), BuildingType.Village);
		model.buildStreet(new Location(0,1,0));
		model.buildStreet(new Location(0,1,1));
		model.buildSettlement(new Location(0,1,2), BuildingType.Village);
		model.buildStreet(new Location(0,2,5));
		model.buildSettlement(new Location(0,2,0), BuildingType.Village);
		model.buildStreet(new Location(0,2,0));
		model.buildStreet(new Location(0,3,5));
		model.buildSettlement(new Location(0,3,0), BuildingType.Village);
		// Player 1 (Gegner) hat 5 Villages
		
		//neue Runde (2. Player)
		model.newRound(3);
		model.buildSettlement(new Location(1,0,0), BuildingType.Town);
		model.buildStreet(new Location(1,0,3));
		model.buildStreet(new Location(2,0,4));
		model.buildSettlement(new Location(2,0,4), BuildingType.Village);
		model.buildStreet(new Location(3,0,5));
		model.buildStreet(new Location(3,0,4));
		model.buildSettlement(new Location(3,0,4), BuildingType.Village);
		model.buildStreet(new Location(3,0,3));
		model.buildStreet(new Location(3,0,2));
		model.buildSettlement(new Location(3,0,2), BuildingType.Village);
		model.buildStreet(new Location(3,0,0));
		model.buildStreet(new Location(3,1,5));
		model.buildSettlement(new Location(3,1,0), BuildingType.Village);
		model.buildStreet(new Location(3,1,0));
		model.buildStreet(new Location(3,1,1));
		model.buildSettlement(new Location(3,1,2), BuildingType.Village);
		model.buildStreet(new Location(3,2,5));
		model.buildSettlement(new Location(3,2,0), BuildingType.Village);
		model.buildStreet(new Location(2,1,1));
		model.buildStreet(new Location(2,1,0));
		model.buildSettlement(new Location(2,1,0), BuildingType.Village);
		model.buildStreet(new Location(2,0,5));
		model.buildSettlement(new Location(2,0,0), BuildingType.Village);
		model.buildSettlement(new Location(2,0,0), BuildingType.Town);
		model.buildSettlement(new Location(1,0,4), BuildingType.Town);
		model.buildSettlement(new Location(3,0,4), BuildingType.Town);
		model.buildSettlement(new Location(3,0,0), BuildingType.Town);
		// Player 2 hat jetzt maxVillages und maxTowns
		
		
		model.buildCatapult(new Location(0,0,3), true);
		model.catapultMoved(new Location(0,0,3), new Location(0,0,2), true);
		// Town wird degradiert zur Village, wird aber zerstoert, da der Gegner schon maxVillages hat
		model.attackSettlement(new Location(0,0,2), new Location(0,0,2), AttackResult.SUCCESS);
		try {
			model.getIntersection(new Location(0,0,2)).getOwner();
			fail("Hier darf keine Village mehr stehen!");
		} catch(Exception e) {
			//Test laeuft durch
		}
		assertTrue("Katapult ist verschwunden",model.getPath(new Location(0,0,3)).getCatapultOwner().equals(model.getCurrentPlayer()));
		int expectedVP_gegner = 5;
		int expectedVP_angreifer = 15;
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		assertEquals("VictoryPoints fehlerhaft beim Gegner", expectedVP_gegner, model.getCurrentVictoryPoints(gegner));
	}
	
	/**
	 * Angriff gegen gegnerische Village und Town - unentschieden (beide male)
	 */
	@Test
	public void testAttackSettlement3(){
		// Gegner
		Player gegner = model.getTableOrder().get(0);
		
		//gibt den akt. Playern alle Resourcen um Komplikationen mit build zu vermeiden.
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		model.getTableOrder().get(1).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		//Angriffsziele bauen (Initialisierungsphase)
		// 1.Player
		model.buildSettlement(new Location(0,0,0) , BuildingType.Village);
		model.buildStreet(new Location(0,0,0));
		// 2. Player
		model.buildSettlement(new Location(0,0,4) , BuildingType.Village);
		model.buildStreet(new Location(1,0,0));
		// 3. Player
		model.buildSettlement(new Location(3,3,0) , BuildingType.Village);
		model.buildStreet(new Location(3,3,0));
		// wieder 3
		model.buildSettlement(new Location(3,3,3), BuildingType.Village);
		model.buildStreet(new Location(3,3,2));
		// 2. Player
		model.buildSettlement(new Location(1,0,4), BuildingType.Village);
		model.buildStreet(new Location(1,0,4));
		// 1. Player
		model.buildSettlement(new Location(1,1,0) , BuildingType.Village);
		model.buildStreet(new Location(1,1,0));
		
		//neue Runde (1.Player)
		model.newRound(12);
		// Upgrade 1 Village
		model.buildSettlement(new Location(1,1,0), BuildingType.Town);
		
		//neue Runde (2. Player)
		model.newRound(3);
		model.buildSettlement(new Location(1,0,0), BuildingType.Town);
		model.buildCatapult(new Location(0,0,4), true);
		model.catapultMoved(new Location(0,0,4), new Location(0,0,5), true);
		// es darf nichts passieren
		model.attackSettlement(new Location(0,0,5), new Location(0,0,0), AttackResult.DEFEAT);
		assertTrue("Gegener nicht merh im Besitz seiner Village", model.getIntersection(new Location(0,0,0)).getOwner().equals(gegner));
		assertTrue("Village ist keine Village mehr", model.getIntersection(new Location(0,0,0)).getBuildingType().equals(BuildingType.Village));
		try {
			model.getPath(new Location(0, 0, 5)).getCatapultOwner();
			fail("Katapult muesste zerstoert sein");
		} catch(Exception e) {
			//Test laueft durch
		}
		int expectedVP_gegner = 3;
		int expectedVP_angreifer = 3;
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		assertEquals("VictoryPoints fehlerhaft beim Gegner", expectedVP_gegner, model.getCurrentVictoryPoints(gegner));
		
		// es darf nichts passieren
		model.catapultMoved(new Location(0,0,5), new Location(0,0,0), true);
		model.catapultMoved(new Location(0,0,0),new Location(0,0,1), true);
		model.attackSettlement(new Location(0,0,1), new Location(0,0,2), AttackResult.DEFEAT);
		assertTrue("Gegener nicht merh im Besitz seiner Village", model.getIntersection(new Location(0,0,2)).getOwner().equals(gegner));
		assertTrue("Village ist keine Village mehr", model.getIntersection(new Location(0,0,2)).getBuildingType().equals(BuildingType.Village));
		try {
			model.getPath(new Location(0, 0, 1)).getCatapultOwner();
			fail("Katapult muesste zerstoert sein");
		} catch(Exception e) {
			//Test laueft durch
		}
		int expectedVP2_gegner = 3;
		int expectedVP2_angreifer = 3;
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP2_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		assertEquals("VictoryPoints fehlerhaft beim Gegner", expectedVP2_gegner, model.getCurrentVictoryPoints(gegner));
	}
	
	
	/**
	 * Angriff gegen gegnerische Village und Town - verloren (beide male)
	 */
	@Test
	public void testAttackSettlement4(){
		// Gegner
		Player gegner = model.getTableOrder().get(0);
		
		//gibt den akt. Playern alle Resourcen um Komplikationen mit build zu vermeiden.
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		model.getTableOrder().get(1).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		//Angriffsziele bauen (Initialisierungsphase)
		// 1.Player
		model.buildSettlement(new Location(0,0,0) , BuildingType.Village);
		model.buildStreet(new Location(0,0,0));
		// 2. Player
		model.buildSettlement(new Location(0,0,4) , BuildingType.Village);
		model.buildStreet(new Location(1,0,0));
		// 3. Player
		model.buildSettlement(new Location(3,3,0) , BuildingType.Village);
		model.buildStreet(new Location(3,3,0));
		// wieder 3
		model.buildSettlement(new Location(3,3,3), BuildingType.Village);
		model.buildStreet(new Location(3,3,2));
		// 2. Player
		model.buildSettlement(new Location(1,0,4), BuildingType.Village);
		model.buildStreet(new Location(1,0,4));
		// 1. Player
		model.buildSettlement(new Location(1,1,0) , BuildingType.Village);
		model.buildStreet(new Location(1,1,0));
		
		//neue Runde (1.Player)
		model.newRound(12);
		// Upgrade 1 Village
		model.buildSettlement(new Location(1,1,0), BuildingType.Town);
		
		//neue Runde (2. Player)
		model.newRound(3);
		model.buildSettlement(new Location(1,0,0), BuildingType.Town);
		model.buildCatapult(new Location(0,0,4), true);
		model.catapultMoved(new Location(0,0,4), new Location(0,0,5), true);
		// katapult wird zerstoert
		model.attackSettlement(new Location(0,0,5), new Location(0,0,0), AttackResult.DRAW);
		assertFalse("es hat sich etwas veraendert", model.getIntersection(new Location(0,0,0)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue("der Gegner ist nicht mehr im Besitz seiner Village", model.getIntersection(new Location(0,0,0)).getOwner().equals(gegner));
		assertTrue("das angreifende Katapult ist weg", model.getPath(new Location(0,0,5)).getCatapultOwner().equals(model.getCurrentPlayer()));
		int expectedVP_gegner = 3;
		int expectedVP_angreifer = 3;
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		assertEquals("VictoryPoints fehlerhaft beim Gegner", expectedVP_gegner, model.getCurrentVictoryPoints(gegner));
		
		model.buildCatapult(new Location(0,0,3), true);
		model.buildStreet(new Location(0,0,3));
		model.buildStreet(new Location(0,0,2));
		// katapult wird zerstoert
		model.catapultMoved(new Location(0,0,3), new Location(0,0,2), true);
		model.attackSettlement(new Location(0,0,2), new Location(0,0,2), AttackResult.DRAW);
		assertTrue("Zustand hat sich geaendert", model.getIntersection(new Location(0,0,2)).getBuildingType().equals(BuildingType.Town));
		assertTrue("Village gehoert dem falschen Spieler (Zustand hat sich geandert)", model.getIntersection(new Location(0,0,2)).getOwner().equals(gegner));
		assertTrue("das angreifende Katapult ist weg", model.getPath(new Location(0,0,1)).getCatapultOwner().equals(model.getCurrentPlayer()));
		int expectedVP2_gegner = 3;
		int expectedVP2_angreifer = 3;
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP2_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		assertEquals("VictoryPoints fehlerhaft beim Gegner", expectedVP2_gegner, model.getCurrentVictoryPoints(gegner));
	}
	
	
	private void beforeOwnAttack() {
		//gibt den akt. Playern alle Resourcen um Komplikationen mit build zu vermeiden.
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		model.getTableOrder().get(1).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		//Angriffsziele bauen (Initialisierungsphase)
		// 1.Player
		model.buildSettlement(new Location(0,0,0) , BuildingType.Village);
		model.buildStreet(new Location(0,0,0));
		// 2. Player
		model.buildSettlement(new Location(0,0,4) , BuildingType.Village);
		model.buildStreet(new Location(1,0,0));
		// 3. Player
		model.buildSettlement(new Location(3,3,0) , BuildingType.Village);
		model.buildStreet(new Location(3,3,0));
		// wieder 3
		model.buildSettlement(new Location(3,3,3), BuildingType.Village);
		model.buildStreet(new Location(3,3,2));
		// 2. Player
		model.buildSettlement(new Location(1,0,4), BuildingType.Village);
		model.buildStreet(new Location(1,0,4));
		// 1. Player
		model.buildSettlement(new Location(1,1,0) , BuildingType.Village);
		model.buildStreet(new Location(1,1,0));
				
		//neue Runde (1.Player)
		model.newRound(12);
		//neue Runde (2. Player)
		model.newRound(3);
		model.buildSettlement(new Location(0,0,4), BuildingType.Town);
		model.buildCatapult(new Location(1,0,5), true);
				
	}
	
	
	/**
	 * Angriff gegen eigene Village und Town - gewonnen (beide male) -> Angreifer bekommt eine Village mehr, eine Town weniger
	 */
	@Test
	public void testAttackOwnSettlement5(){
		// Initialisierungsrunde
		beforeOwnAttack();
		//Angriff auf eigene Town
		model.attackSettlement(new Location(1,0,5), new Location(0,0,4), AttackResult.SUCCESS);
		assertTrue("Town noch immer Town (wurde nicht zur Village degradiert)", model.getIntersection(new Location(0,0,4)).getBuildingType().equals(BuildingType.Village));
		assertTrue("degradierte Village gehoert nciht mehr dem Angreifer",model.getIntersection(new Location(0,0,4)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue("katapult ist weg oder einem anderen Benutzer", model.getPath(new Location(1,0,5)).getCatapultOwner().equals(model.getCurrentPlayer()));
		int expectedVP_angreifer = 2;
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		
		// Angriff auf eigene Village
		model.catapultMoved(new Location(1,0,5), new Location(1,0,4), true);
		model.attackSettlement(new Location(1,0,4), new Location(1,0,4), AttackResult.SUCCESS);
		assertTrue("Village wurde zerstoert", model.getIntersection(new Location(1,0,4)).hasOwner());
		assertTrue("Village gehoert nicht mehr dem Angreifer", model.getIntersection(new Location(1,0,4)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue("Village ist nicht mehr vom Typ Village", model.getIntersection(new Location(1,0,4)).getBuildingType().equals(BuildingType.Village));
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		assertTrue("katapult ist weg oder einem anderen Benutzer", model.getPath(new Location(1,0,4)).getCatapultOwner().equals(model.getCurrentPlayer()));
	}
	
	
	/**
	 * Angriff gegen eigene Village und Town - unentschieden (beide male) -> es passiert nichts
	 */
	@Test
	public void testAttackOwnSettlement6(){
		// Initialisierungsrunde
		beforeOwnAttack();
		
		//Angriff auf eigene Town
		model.attackSettlement(new Location(1,0,5), new Location(0,0,4), AttackResult.DRAW);
		assertFalse("Town keine Town mehr (wurde degradiert)", model.getIntersection(new Location(0,0,4)).getBuildingType().equals(BuildingType.Village));
		assertTrue("degradierte Village gehoert nciht mehr dem Angreifer",model.getIntersection(new Location(0,0,4)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue("katapult ist weg oder einem anderen Benutzer", model.getPath(new Location(1,0,5)).getCatapultOwner().equals(model.getCurrentPlayer()));
		int expectedVP_angreifer = 3;
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		
		// Angriff auf eigene Village
		model.catapultMoved(new Location(1,0,5), new Location(1,0,4), true);
		model.attackSettlement(new Location(1,0,4), new Location(1,0,4), AttackResult.DRAW);
		assertTrue("Village wurde zerstoert", model.getIntersection(new Location(1,0,4)).hasOwner());
		assertTrue("Village gehoert nicht mehr dem Angreifer", model.getIntersection(new Location(1,0,4)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue("Village ist nicht mehr vom Typ Village", model.getIntersection(new Location(1,0,4)).getBuildingType().equals(BuildingType.Village));
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		assertTrue("katapult ist weg oder einem anderen Benutzer", model.getPath(new Location(1,0,4)).getCatapultOwner().equals(model.getCurrentPlayer()));
	}
	
	
	/**
	 * Angriff gegen eigene Village und Town - verloren (beide male) -> Angreifer verliert 2 Katapulte
	 */
	@Test
	public void testAttackOwnSettlement7(){
		// Initialisierungsrunde
		beforeOwnAttack();
		
		// Angriff auf eigene Village
		model.catapultMoved(new Location(1,0,5), new Location(1,0,4), true);
		model.attackSettlement(new Location(1,0,4), new Location(1,0,4), AttackResult.DEFEAT);
		assertTrue("Village wurde zerstoert", model.getIntersection(new Location(1,0,4)).hasOwner());
		assertTrue("Village gehoert nicht mehr dem Opfer", model.getIntersection(new Location(1,0,4)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue("Village ist nicht mehr vom Typ Village", model.getIntersection(new Location(1,0,4)).getBuildingType().equals(BuildingType.Village));
		int expectedVP_angreifer = 3;
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		try {
			model.getPath(new Location(1,0,4)).getCatapultOwner();
			fail("Katapult sollte zerstoert sein");
		} catch(Exception e) {
			// alles roger
		}
		
		//Angriff auf eigene Town
		model.buildCatapult(new Location(1,0,5), true);
		model.attackSettlement(new Location(1,0,5), new Location(0,0,4), AttackResult.DEFEAT);
		assertTrue("Town sollte noch immer eine Town sein (wurde vermutlich degradiert)", model.getIntersection(new Location(0,0,4)).getBuildingType().equals(BuildingType.Town));
		assertTrue("Village gehoert nciht mehr dem Opfer",model.getIntersection(new Location(0,0,4)).getOwner().equals(model.getCurrentPlayer()));
		assertEquals("VictoryPoints fehlerhaft beim Angreifer", expectedVP_angreifer, model.getCurrentVictoryPoints(model.getCurrentPlayer()));
		try {
			model.getPath(new Location(1,0,5)).getCatapultOwner();
			fail("Katapult sollte zerstoert sein");
		} catch(Exception e) {
			// alles roger
		}
		
	}
	
	
	
	
	/**
	 * Angriff gegen eigene Village und Town - erfolgreich (beide male), aber habe selbst genug Villages
	 */
	@Test
	public void testAttackOwnSettlement8(){
		//gibt den akt. Playern alle Resourcen um Komplikationen mit build zu vermeiden.
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		model.getTableOrder().get(1).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		//Angriffsziele bauen (Initialisierungsphase)
		// 1.Player
		model.buildSettlement(new Location(0,0,0) , BuildingType.Village);
		model.buildStreet(new Location(0,0,0));
		// 2. Player
		model.buildSettlement(new Location(0,0,4) , BuildingType.Village);
		model.buildStreet(new Location(1,0,0));
		// 3. Player
		model.buildSettlement(new Location(3,3,0) , BuildingType.Village);
		model.buildStreet(new Location(3,3,0));
		// wieder 3
		model.buildSettlement(new Location(3,3,3), BuildingType.Village);
		model.buildStreet(new Location(3,3,2));
		// 2. Player
		model.buildSettlement(new Location(1,0,4), BuildingType.Village);
		model.buildStreet(new Location(1,0,4));
		// 1. Player
		model.buildSettlement(new Location(1,1,0) , BuildingType.Village);
		model.buildStreet(new Location(1,1,0));
		
		//neue Runde (1.Player)
		model.newRound(12);
		// Upgrade 1 Village
		model.buildSettlement(new Location(1,1,0), BuildingType.Town);
		
		//neue Runde (2. Player)
		model.newRound(3);
		model.buildSettlement(new Location(1,0,0), BuildingType.Town);
		model.buildStreet(new Location(1,0,3));
		model.buildStreet(new Location(2,0,4));
		model.buildSettlement(new Location(2,0,4), BuildingType.Village);
		model.buildStreet(new Location(3,0,5));
		model.buildStreet(new Location(3,0,4));
		model.buildSettlement(new Location(3,0,4), BuildingType.Village);
		model.buildStreet(new Location(3,0,3));
		model.buildStreet(new Location(3,0,2));
		model.buildSettlement(new Location(3,0,2), BuildingType.Village);
		model.buildStreet(new Location(3,0,0));
		model.buildStreet(new Location(3,1,5));
		model.buildSettlement(new Location(3,1,0), BuildingType.Village);
		model.buildStreet(new Location(3,1,0));
		model.buildStreet(new Location(3,1,1));
		model.buildSettlement(new Location(3,1,2), BuildingType.Village);
		model.buildStreet(new Location(3,2,5));
		model.buildSettlement(new Location(3,2,0), BuildingType.Village);
		model.buildStreet(new Location(2,1,1));
		model.buildStreet(new Location(2,1,0));
		model.buildSettlement(new Location(2,1,0), BuildingType.Village);
		model.buildStreet(new Location(2,0,5));
		model.buildSettlement(new Location(2,0,0), BuildingType.Village);
		model.buildSettlement(new Location(2,0,0), BuildingType.Town);
		model.buildSettlement(new Location(1,0,4), BuildingType.Town);
		model.buildSettlement(new Location(3,0,4), BuildingType.Town);
		model.buildSettlement(new Location(3,0,0), BuildingType.Town);
		// Player 2 hat jetzt maxVillages und maxTowns
		
		model.buildCatapult(new Location(1,0,5), true);
		// Angriff auf eine Town -> sollte zerstoert werden
		model.attackSettlement(new Location(1,0,5), new Location(1,0,0), AttackResult.SUCCESS);
		assertTrue(model.getIntersection(new Location(1,0,0)).hasOwner());
		assertTrue(model.getIntersection(new Location(1,0,0)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue(model.getIntersection(new Location(1,0,0)).getBuildingType().equals(BuildingType.Town));
		assertTrue(model.getPath(new Location(1,0,5)).hasCatapult());
		assertTrue(model.getPath(new Location(1,0,5)).getCatapultOwner().equals(model.getCurrentPlayer()));
		// Angriff auf eine Village -> sollte sich nichts veraendern
		model.catapultMoved(new Location(1,0,5), new Location(1,0,4), true);
		model.attackSettlement(new Location(1,0,4), new Location(1,0,4), AttackResult.SUCCESS);
		assertTrue(model.getIntersection(new Location(1,0,4)).hasOwner());
		assertTrue(model.getIntersection(new Location(1,0,4)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue(model.getIntersection(new Location(1,0,4)).getBuildingType().equals(BuildingType.Village));
		assertTrue(model.getPath(new Location(1,0,4)).hasCatapult());
		assertTrue(model.getPath(new Location(1,0,4)).getCatapultOwner().equals(model.getCurrentPlayer()));

		
	}
	
}
