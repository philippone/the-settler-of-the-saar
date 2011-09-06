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
import de.unisaarland.cs.sopra.common.controller.Controller;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;


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
	public void setTableOrder() {
		long[] expectedTableOrder = new long[] {2,1,0};
		assertEquals("TableOrder nicht richitg gesetzt", expectedTableOrder, model.getTableOrder());
	}
	
	@Test
	public void setFieldNumbers() {
		Iterator<Field> fieldIterator = model.getFieldIterator();
		// an neue Welt anpassen!!!!
		long[] fieldnumbers = new long[] {8,6}; 
		int i = 0;
		long[] reihenfolge = new long[2];
		while (fieldIterator.hasNext()) {
			Field f = fieldIterator.next();
			if (f.getNumber() != 0) {
				reihenfolge[i++] = f.getNumber();
			}
		}
		// ist die Reihenfolge der Felder richitg
		assertEquals("Feldnummern nicht richitg gesetzt", reihenfolge, fieldnumbers);
	}
	
	@Test
	public void updateLongestRoad() {
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
		model.longestRaodClaimed(Model.getLocationList(longestRoad.get(0)));
		
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
	public void getLocationField() {
		
	}
	
	@Test
	public void getLocationIntersection() {
		
	}
	
	@Test
	public void getLocationPath() {
		
	}
	
	@Test
	public void testGetCurrentPlayer() {
		Player expectedPlayer = model.getTableOrder().get(0);
		Player currentPlayer = model.getCurrentPlayer();
		assertEquals("nicht der aktuelle Player", expectedPlayer, currentPlayer);
		// naechste Runde
	}
	
	/// ab hier Valentin ;)
	
	

}
