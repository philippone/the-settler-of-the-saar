package model;

import static org.junit.Assert.*;
import help.TestUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.ModelObserver;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Point;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
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
		 List<Path> longRoad3 = new LinkedList<Path>();
		 expectedLongestRoad.add(longRoad0);
		 expectedLongestRoad.add(longRoad1);
		 expectedLongestRoad.add(longRoad2);
		 expectedLongestRoad.add(longRoad3);
		 
		 model.buildSettlement(new Location(1,1,0), BuildingType.Village);
		 
		 model.buildStreet(new Location(1,1,5));
		 longRoad0.add(model.getPath(new Location(1,1,5)));
		 longRoad1.add(model.getPath(new Location(1,1,5)));
		 longRoad2.add(model.getPath(new Location(1,1,5)));
		 longRoad3.add(model.getPath(new Location(1,1,5)));
		 
		 model.buildStreet(new Location(1,1,4));
		 longRoad0.add(model.getPath(new Location(1,1,4)));
		 longRoad1.add(model.getPath(new Location(1,1,4)));
		 longRoad2.add(model.getPath(new Location(1,1,4)));
		 longRoad3.add(model.getPath(new Location(1,1,4)));
		 
		 model.buildStreet(new Location(1,1,3));
		 //model.buildStreet(new Location(1,1,2));
		 model.buildStreet(new Location(2,0,5));
		 longRoad0.add(model.getPath(new Location(2,0,5)));
		 longRoad1.add(model.getPath(new Location(2,0,5)));
		 longRoad2.add(model.getPath(new Location(2,0,5)));
		 longRoad3.add(model.getPath(new Location(1,1,3)));
		 
		 model.buildStreet(new Location(2,0,4));
		 model.buildStreet(new Location(2,0,1));
		 longRoad0.add(model.getPath(new Location(2,0,4)));
		 longRoad1.add(model.getPath(new Location(2,0,4)));
		 longRoad2.add(model.getPath(new Location(2,0,4)));
		 longRoad3.add(model.getPath(new Location(2,0,1)));
		 
		 model.buildStreet(new Location(2,0,3));
		 model.buildStreet(new Location(2,0,2));
		 longRoad0.add(model.getPath(new Location(2,0,3)));
		 longRoad1.add(model.getPath(new Location(2,0,3)));
		 longRoad1.add(model.getPath(new Location(2,0,2)));
		 longRoad2.add(model.getPath(new Location(2,0,3)));
		 longRoad2.add(model.getPath(new Location(2,0,2)));
		 longRoad3.add(model.getPath(new Location(2,0,2)));
		 
		 model.buildStreet(new Location(1,1,2));
		 model.buildStreet(new Location(1,2,3));
		 longRoad2.add(model.getPath(new Location(2,0,1)));
		 longRoad2.add(model.getPath(new Location(1,1,2)));
		 longRoad2.add(model.getPath(new Location(1,2,3)));
		 // 2 fertig
		 
		 model.buildStreet(new Location(3,0,1));
		 longRoad0.add(model.getPath(new Location(3,0,1)));
		 longRoad3.add(model.getPath(new Location(3,0,1)));
		 
		 model.buildStreet(new Location(3,1,3));
		 model.buildStreet(new Location(3,1,2));
		 longRoad0.add(model.getPath(new Location(3,1,3)));
		 longRoad3.add(model.getPath(new Location(3,1,3)));
		 longRoad0.add(model.getPath(new Location(3,1,2)));
		 longRoad3.add(model.getPath(new Location(3,1,2)));
		 // 0 und 3 fertig
		 
		 model.buildStreet(new Location(2,1,3));
		 model.buildStreet(new Location(2,1,2));
		 model.buildStreet(new Location(2,2,3));
		 longRoad1.add(model.getPath(new Location(2,1,3)));
		 longRoad1.add(model.getPath(new Location(2,1,2)));
		 longRoad1.add(model.getPath(new Location(2,2,3)));
		 // 1 fertig
		 
		 // gegnerisches Dorf
		 model.buildSettlement(new Location(2,2,3), BuildingType.Village);
		 model.getIntersection(new Location(2,2,3)).setOwner(gegner);
		 // Street hinter gegnerischem Dorf
		 model.buildSettlement(new Location(3,3,3), BuildingType.Village);
		 model.buildStreet(new Location(3,3,5));
		 model.buildStreet(new Location(3,3,3));
		 
		 List<List<Path>> currentLongestRoad = model.calculateLongestRoads(owner);
		 assertEquals("Street0 0 != 9", longRoad0.size(),9);
		 assertEquals("Street1 0 != 9", longRoad1.size(),9);
		 assertEquals("Street2 0 != 9", longRoad2.size(),9);
		 assertEquals("Street3 0 != 9", longRoad3.size(),9);
		 
		 // Liste reversieren
		 List<List<Path>> reverseLongestRoads = expectedLongestRoad;
		 for(List<Path> l : reverseLongestRoads) {
			 Collections.reverse(l);
		 }
		 // testet ob die normale oder reverseierte Liste == der ausgerechneten ist
		 boolean calculateExpectedLongestRoad = expectedLongestRoad.equals(currentLongestRoad) || currentLongestRoad.equals(reverseLongestRoads);
		 assertEquals("laengste Strassen falsch berechnet (hoffentlich richitger Test)", calculateExpectedLongestRoad, true);
		 // claim longest Road:Road1
		 //model.longestRaodClaimed(Model.getLocationList(longRoad0));
	}
	
	@Test
	public void testSetTableOrder() {
		long[] expectedTableOrder = new long[] {2,1,0};
		assertEquals("TableOrder nicht richitg gesetzt", expectedTableOrder, model.getTableOrder());
	}
	
	@Test
	public void testSetFieldNumbers() {
		Iterator<Field> fieldIterator = model.getFieldIterator();
		// an neue Welt anpassen!!!!
		long[] fieldnumbers = new long[] {8,6}; 
		int i = 0;
		long[] reihenfolge = new long[2];
		while (fieldIterator.hasNext()) {
			Field f = fieldIterator.next();
			if (f.getNumber() != 0) {
				try {
					reihenfolge[i++] = f.getNumber();
				} catch (Exception e) {
					fail("zu viele Nummern eingefuegt (>2)");
				}
			}
		}
		// ist die Reihenfolge der Felder richitg
		assertEquals("Feldnummern nicht richitg gesetzt", reihenfolge, fieldnumbers);
	}
	
	@Test
	public void testUpdateLongestRoad() {
		// in Runde 1 gehen
		model.newRound(8);
		// current Player genug Resourcen geben
		Player currentPlayer = model.getCurrentPlayer();
		currentPlayer.modifyResources(new ResourcePackage(10000,10000,10000,10000,10000));
		// eigenes Haus
		model.buildSettlement(new Location(1,0,1), BuildingType.Village);
		// longest Road bauen
		model.buildStreet(new Location(1,0,1));
		model.buildStreet(new Location(1,0,2));
		model.buildStreet(new Location(2,0,5));
		model.buildStreet(new Location(2,0,3));
		model.buildStreet(new Location(2,0,2));
		//longest Road claim
		List<List<Path>> longestRoad = model.calculateLongestRoads(currentPlayer);
		model.longestRoadClaimed(Model.getLocationList(longestRoad.get(0)));
		
		// neue Runde (Gegner an der Reihe)
		model.newRound(6);
		// Gegner: Village auf longestClaimedRoad bauen
		model.buildSettlement(new Location(2,0,4), BuildingType.Village);
		// updateLongestRoad aufrufen
		model.updateLongestRoad(model.getIntersection(new Location(2,0,4)));
		// erwartete Liste
		List<Path> expectedLongestRoad = new LinkedList<Path>();
		expectedLongestRoad.add(model.getPath(new Location(1,0,1)));
		expectedLongestRoad.add(model.getPath(new Location(1,0,2)));
		expectedLongestRoad.add(model.getPath(new Location(2,0,5)));
		
		assertTrue("updateLongestRoad fehlgeschlagen", model.getLongestClaimedRoad().size() == 3);
		assertEquals("nicht die richige LongestRoad geupdatet", model.getLongestClaimedRoad(), expectedLongestRoad);
	}
	
	
	
	/**
	 * Handelststrasse geanu in der Mitte geteilt
	 */
	@Test
	public void testUpdateLongestRoad2() {
		// in Runde 1 gehen
		model.newRound(8);
		// current Player genug Resourcen geben
		Player currentPlayer = model.getCurrentPlayer();
		currentPlayer.modifyResources(new ResourcePackage(10000,10000,10000,10000,10000));
		// eigenes Haus
		model.buildSettlement(new Location(1,0,1), BuildingType.Village);
		// longest Road bauen (strasse 6 lang)
		model.buildStreet(new Location(1,0,1));
		model.buildStreet(new Location(1,0,2));
		model.buildStreet(new Location(2,0,5));
		model.buildStreet(new Location(2,0,3));
		model.buildStreet(new Location(2,0,2));
		model.buildStreet(new Location(2,0,1));
		//longest Road claim
		List<List<Path>> longestRoad = model.calculateLongestRoads(currentPlayer);
		model.longestRoadClaimed(Model.getLocationList(longestRoad.get(0)));
		
		// neue Runde (Gegner an der Reihe)
		model.newRound(6);
		// Gegner: Village auf longestClaimedRoad bauen
		model.buildSettlement(new Location(2,0,4), BuildingType.Village);
		// updateLongestRoad aufrufen
		model.updateLongestRoad(model.getIntersection(new Location(2,0,4)));
		// erwartete Liste
		List<Path> expectedLongestRoad = new LinkedList<Path>();
		expectedLongestRoad.add(model.getPath(new Location(1,0,1)));
		expectedLongestRoad.add(model.getPath(new Location(1,0,2)));
		expectedLongestRoad.add(model.getPath(new Location(2,0,5)));
		
		assertTrue("updateLongestRoad fehlgeschlagen", model.getLongestClaimedRoad().size() == 3);
		assertEquals("nicht die richige LongestRoad geupdatet", model.getLongestClaimedRoad(), expectedLongestRoad);
	}
	
	@Test
	public void testGetLocationField() {
		Point expectedPoint = new Point(1,1);
		Field f = model.getField(new Point(1,1));
		Point p = Model.getLocation(f);
		assertEquals("Felder sind ungleich",expectedPoint, p);
		// Feld ausserhalb des Spielfeldes
		try {
			Field f2 = model.getField(new Point(5,5));
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
		Location expectedLocation = new Location(1,1,1);
		Intersection i = model.getIntersection(new Location(1,1,1));
		Location l = Model.getLocation(i);
		assertEquals("Intersections sind ungleich", expectedLocation, l);
		// Location ausserhalb des Spielfeldes
		try {
			Intersection i2 = model.getIntersection(new Location(5,5,5));
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
		Location expectedLocation = new Location(1,1,1);
		Path p = model.getPath(new Location(1,1,1));
		Location l = Model.getLocation(p);
		assertEquals("Intersections sind ungleich", expectedLocation, l);
		try {
			Path p2 = model.getPath(new Location(5,5,5));
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
	public void testAttackSettlement(){
		//gibt den akt. Playern alle Resourcen um Komplikationen mit build zu vermeiden.
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		model.getTableOrder().get(1).modifyResources(new ResourcePackage(10000,10000,10000,10000,10000)); 
		//Angriffsziele bauen (Initialisierungsphase)
		// 1.Player
		model.buildSettlement(new Location(0,0,0) , BuildingType.Village);
		model.buildStreet(new Location(0,0,0));
		// 2. Player
		model.buildSettlement(new Location(0,0,1) , BuildingType.Village);
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
		
		model.catapultMoved(new Location(0,0,5), new Location(0,0,0), true);
		model.catapultMoved(new Location(0,0,0),new Location(0,0,1), true);
		model.attackSettlement(new Location(0,0,1), new Location(0,0,2), AttackResult.SUCCESS);
		assertTrue("Town wurde nciht richitg gedowngradet", model.getIntersection(new Location(0,0,2)).getBuildingType().equals(BuildingType.Village));
		assertFalse("Village gehoert dem falschen Spieler", model.getIntersection(new Location(0,0,2)).getOwner().equals(model.getCurrentPlayer()));	
		assertTrue("das angreifende Katapult ist weg", model.getPath(new Location(0,0,1)).getCatapultOwner().equals(model.getCurrentPlayer()));
	}
	
	
	/**
	 * Angriff gegen gegnerische Village und Town - unentschieden (beide male)
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
		model.buildSettlement(new Location(0,0,1) , BuildingType.Village);
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
		model.attackSettlement(new Location(0,0,5), new Location(0,0,0), AttackResult.DRAW);
		assertFalse("es hat sich etwas veraendert", model.getIntersection(new Location(0,0,0)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue("der Gegner ist nicht mehr im Besitz seiner Village", model.getIntersection(new Location(0,0,0)).getOwner().equals(gegner));
		assertTrue("das angreifende Katapult ist weg", model.getPath(new Location(0,0,5)).getCatapultOwner().equals(model.getCurrentPlayer()));
		
		model.catapultMoved(new Location(0,0,5), new Location(0,0,0), true);
		model.catapultMoved(new Location(0,0,0),new Location(0,0,1), true);
		model.attackSettlement(new Location(0,0,1), new Location(0,0,2), AttackResult.DRAW);
		assertTrue("Zustand hat sich geaendert", model.getIntersection(new Location(0,0,2)).getBuildingType().equals(BuildingType.Town));
		assertTrue("Village gehoert dem falschen Spieler (Zustand hat sich geandert)", model.getIntersection(new Location(0,0,2)).getOwner().equals(gegner));
		assertTrue("das angreifende Katapult ist weg", model.getPath(new Location(0,0,1)).getCatapultOwner().equals(model.getCurrentPlayer()));
	}
	
	
	/**
	 * Angriff gegen gegnerische Village und Town - verloren (beide male)
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
		model.buildSettlement(new Location(0,0,1) , BuildingType.Village);
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
		model.attackSettlement(new Location(0,0,5), new Location(0,0,0), AttackResult.DRAW);
		assertFalse("es hat sich etwas veraendert", model.getIntersection(new Location(0,0,0)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue("der Gegner ist nicht mehr im Besitz seiner Village", model.getIntersection(new Location(0,0,0)).getOwner().equals(gegner));
		assertTrue("das angreifende Katapult ist weg", model.getPath(new Location(0,0,5)).getCatapultOwner().equals(model.getCurrentPlayer()));
		
		model.catapultMoved(new Location(0,0,5), new Location(0,0,0), true);
		model.catapultMoved(new Location(0,0,0),new Location(0,0,1), true);
		model.attackSettlement(new Location(0,0,1), new Location(0,0,2), AttackResult.DRAW);
		assertTrue("Zustand hat sich geaendert", model.getIntersection(new Location(0,0,2)).getBuildingType().equals(BuildingType.Town));
		assertTrue("Village gehoert dem falschen Spieler (Zustand hat sich geandert)", model.getIntersection(new Location(0,0,2)).getOwner().equals(gegner));
		assertTrue("das angreifende Katapult ist weg", model.getPath(new Location(0,0,1)).getCatapultOwner().equals(model.getCurrentPlayer()));
	}
	
	

}
