package model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Point;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class ModelReaderTest4 {
	private Model model1;
	private Model model2;
	private Model model3;
	
	@Before
	public void setUp() throws IOException {
		model1 = TestUtil.getStandardModel1();
		model2 = TestUtil.getStandardModel2();
		model3 = TestUtil.getStandardModel3();
	}	
	
	@Test
	public void testGetCatapults() {
		//gibt den akt. Playern alle Resourcen um Komplikationen mit build zu vermeiden.
		model3.getTableOrder().get(0).modifyResources(new ResourcePackage(333,333,333,333,333)); 
		model3.getTableOrder().get(1).modifyResources(new ResourcePackage(333,333,333,333,333)); 
		model3.getTableOrder().get(2).modifyResources(new ResourcePackage(333,333,333,333,333)); 
		//Init-round 
			//first Player builds first Village
		model3.buildSettlement(new Location(0,0,1) , BuildingType.Village);
		model3.buildStreet(new Location(0,0,1));
			//second Player builds first Village
		model3.buildSettlement(new Location(0,0,5) , BuildingType.Village);
		model3.buildStreet(new Location(0,0,5));
			//third Player builds first Village 
		model3.buildSettlement(new Location(3,1,0) , BuildingType.Village);
		model3.buildStreet(new Location(3,1,0));	
			//third Player builds second Village 
		model3.buildSettlement(new Location(3,0,0) , BuildingType.Village);
		model3.buildStreet(new Location(3,0,0));	
			//second Player builds second Village
		model3.buildSettlement(new Location(1,1,5) , BuildingType.Village);
		model3.buildStreet(new Location(1,1,5));
			//first Player builds second Village
		model3.buildSettlement(new Location(1,0,1) , BuildingType.Village);
		model3.buildStreet(new Location(1,0,1));
		
		//new round -> first player builds
		model3.newRound(12);
		//Bauende Towns und Catapulte bauen
		model3.buildSettlement(new Location(0,0,1) , BuildingType.Town);
		model3.buildSettlement(new Location(1,0,1) , BuildingType.Town);
		model3.buildCatapult(new Location(0,0,1), true);
		model3.buildCatapult(new Location(1,0,1), true);
		
		//new round -> second player builds
		model3.newRound(12);
		//Bauende Towns und Catapulte bauen
		model3.buildSettlement(new Location(0,0,5) , BuildingType.Town);
		model3.buildSettlement(new Location(1,0,5) , BuildingType.Town);
		model3.buildCatapult(new Location(0,0,4), true);
		model3.buildCatapult(new Location(1,0,4), true);
		model3.buildCatapult(new Location(1,0,5), true);
		
		//Attribut-Player erstellen
		Player p0 = model3.getTableOrder().get(0);
		Player p1 = model3.getTableOrder().get(1);
		Player p2 = model3.getTableOrder().get(2);
		
		//"richtige" Vergleichs-Sets erstellen
		HashSet<Path> t0 = new HashSet<Path>();
		t0.add(model3.getPath(new Location(0,0,1)));
		t0.add(model3.getPath(new Location(1,0,1)));
		assertEquals(t0, model3.getCatapults(p0));
		
		//"richtige" Vergleichs-Sets erstellen
		HashSet<Path> t1 = new HashSet<Path>();
		t1.add(model3.getPath(new Location(0,0,4)));
		t1.add(model3.getPath(new Location(1,0,4)));
		t1.add(model3.getPath(new Location(1,0,5)));
		assertEquals(t1, model3.getCatapults(p1));
		
		assertEquals("Methode sollte null liefern, wenn der Player keine Catapulte hat", null, model3.getCatapults(p2));
	
	}
	
	@Test
	public void testGetLongestClaimedRoad() {
		assertEquals(null, model2.getLongestClaimedRoad());
		
		Player currentPlayer = model2.getCurrentPlayer();
		model2.getIntersection(new Location(1,0,1)).createBuilding(BuildingType.Village, currentPlayer);
		// longest Road bauen
		model2.getPath(new Location(1,0,1)).createStreet(currentPlayer);
		model2.getPath(new Location(1,0,2)).createStreet(currentPlayer);
		model2.getPath(new Location(2,0,5)).createStreet(currentPlayer);
		model2.getPath(new Location(2,0,3)).createStreet(currentPlayer);
		model2.getPath(new Location(2,0,2)).createStreet(currentPlayer);
		//longest Road claim
		List<List<Path>> longestRoad = model2.calculateLongestRoads(currentPlayer);
		model2.longestRoadClaimed(Model.getLocationList(longestRoad.get(0)));
		
		assertEquals(longestRoad.get(0), model2.getLongestClaimedRoad());
	}
	
	@Test
	public void testGetMaxVictoryPoints() {
		assertEquals("Standardmodel hat 5 maxVillages nicht: "+ model2.getMaxVictoryPoints(),5,model2.getMaxVictoryPoints());
	}
	
	@Test
	public void testGetCurrentVictoryPoints() {
		assertEquals(0,model2.getCurrentVictoryPoints(model2.getTableOrder().get(0)));
		// in Runde 1 gehen
		model2.newRound(8);
		// current Player genug Resourcen geben
		Player currentPlayer = model2.getCurrentPlayer();
		currentPlayer.modifyResources(new ResourcePackage(10000,10000,10000,10000,10000));
		// eigenes Haus um Victory-P zu erhoehen
		model2.buildSettlement(new Location(1,0,1), BuildingType.Village);
		
		assertEquals("Nach Gebaeudebau muss vp auf 1 sein", 1 ,model2.getCurrentVictoryPoints(model2.getTableOrder().get(0)));

	}
	
	@Test
	public void testCanPlaceRobber() throws IOException {
		Set<Field> canSet = new HashSet<Field>();
		
		canSet.add(model1.getField(new Point(0, 0)));
		canSet.add(model1.getField(new Point(0, 1)));
		canSet.add(model1.getField(new Point(0, 2)));
		
		canSet.add(model1.getField(new Point(1, 0)));
		canSet.add(model1.getField(new Point(1, 1)));
		canSet.add(model1.getField(new Point(1, 2)));
	
		canSet.add(model1.getField(new Point(2, 0)));
		canSet.add(model1.getField(new Point(2, 1)));
		canSet.add(model1.getField(new Point(2, 2)));

	
		canSet.add(model1.getField(new Point(3, 1)));
		canSet.add(model1.getField(new Point(3, 2)));

		Set<Field> expSet = model1.canPlaceRobber();
		
		assertTrue(canSet.containsAll(expSet));
		assertTrue(expSet.containsAll(canSet));
	
		// ingore robber ?!
	model1.getField(new Point(2, 2)).setRobber(true);
	canSet.remove(model1.getField(new Point(2, 2)));
	model1.getField(new Point(2, 1)).setRobber(true);
	canSet.remove(model1.getField(new Point(2, 1)));
	Set<Field> expSet1 = model1.canPlaceRobber();
	System.out.println(expSet1);
	System.out.println(canSet);
	assertTrue(canSet.containsAll(expSet1));
	assertTrue(expSet1.containsAll(canSet));

	}
	
	@Test
	public void testGetRobberFields() {
		assertTrue("keine Rauber vorhanden, set muss also leer sein", model2.getRobberFields().size() == 0);
		
		Set<Field> placedRobber = new HashSet<Field>();
		Field f1 = model2.getField(new Point(0, 0));
		f1.setRobber(true);	
		placedRobber.add(f1);
		assertEquals("FieldSet stimmt nicht ueberein", placedRobber , model2.getRobberFields());
		
		Field f2 = model2.getField(new Point(3, 3));
		f2.setRobber(true);	
		placedRobber.add(f2);
		assertEquals("FieldSet stimmt nicht ueberein", placedRobber , model2.getRobberFields());
		
		Field f3 = model2.getField(new Point(2, 1));
		f3.setRobber(true);	
		placedRobber.add(f3);
		assertEquals("FieldSet stimmt nicht ueberein", placedRobber , model2.getRobberFields());
	}
	
	@Test
	public void testGetFieldIterator() {
		Iterator it = model1.getFieldIterator();
		assertEquals(model1.getField(new Point(0, 0)), it.next());
		assertEquals(model1.getField(new Point(0, 1)), it.next());
		assertEquals(model1.getField(new Point(0, 2)), it.next());
		assertEquals(model1.getField(new Point(1, 0)), it.next());
		assertEquals(model1.getField(new Point(1, 1)), it.next());
		assertEquals(model1.getField(new Point(1, 2)), it.next());	
	}
	
//	@Test
//	public void testGetPathIterator() {
//		
//	}
	
//	@Test
//	public void testGetIntersectionIterator() {
//		
//	}
	
	@Test
	public void testGetHarborIntersections() {
		Set<Intersection> iset = new HashSet<Intersection>();
		iset.add(model1.getIntersection(new Location(0,0,3)));
		iset.add(model1.getIntersection(new Location(1,0,2)));
		iset.add(model1.getIntersection(new Location(1,2,3)));
		iset.add(model1.getIntersection(new Location(2,1,2)));
		assertEquals(iset, model1.getHarborIntersections());
		
	}
	
	@Test
	public void testGetHarborType() {
		assertEquals(HarborType.GENERAL_HARBOR, model1.getHarborType(model1.getIntersection(new Location(1,0,1))));
		assertEquals(HarborType.LUMBER_HARBOR, model1.getHarborType(model1.getIntersection(new Location(2,1,1))));
		assertNotSame(HarborType.GRAIN_HARBOR, model1.getHarborType(model1.getIntersection(new Location(1,0,1))));
		assertNotSame(HarborType.GENERAL_HARBOR, model1.getHarborType(model1.getIntersection(new Location(2,0,1))));
	}
	
	@Test
	public void testAttackableSettlements() {
		setUp2();
		
		//Attribut-Player erstellen
		Player p0 = model2.getTableOrder().get(0);
		Player p1 = model2.getTableOrder().get(1);
		Player p2 = model2.getTableOrder().get(2);
		
		//set Catapults
		model2.getPath(new Location (0,0,5)).createCatapult(p0);
		model2.getPath(new Location (1,1,5)).createCatapult(p0);
		model2.getPath(new Location (1,1,0)).createCatapult(p1);
		model2.getPath(new Location (0,0,1)).createCatapult(p2);
		model2.getPath(new Location (0,0,4)).createCatapult(p2);
		
		
		// "richtige" vergleichssets erstellen
		Set<Intersection> p0AttackableVillageSet = new HashSet<Intersection>();
		p0AttackableVillageSet.add(model2.getIntersection(new Location(0, 0, 5)));
		assertEquals(p0AttackableVillageSet ,model2.attackableSettlements(p0, BuildingType.Village)); 
		
		Set<Intersection> p0AttackableTownsSet = new HashSet<Intersection>();
		p0AttackableTownsSet.add(model2.getIntersection(new Location(1, 1, 5)));
		assertEquals(p0AttackableTownsSet ,model2.attackableSettlements(p0, BuildingType.Town)); 
		
		Set<Intersection> p1AttackableTownsSet = new HashSet<Intersection>();
		p1AttackableTownsSet.add(model2.getIntersection(new Location(1, 0, 1)));
		assertEquals(p1AttackableTownsSet ,model2.attackableSettlements(p0, BuildingType.Town)); 
		
		Set<Intersection> p2AttackableVillageSet = new HashSet<Intersection>();
		p2AttackableVillageSet.add(model2.getIntersection(new Location(0, 0, 1)));
		p2AttackableVillageSet.add(model2.getIntersection(new Location(0, 0, 5)));
		assertEquals(p2AttackableVillageSet ,model2.attackableSettlements(p0, BuildingType.Village)); 
	}
	
	

	@Test
	public void testAttackableCatapults() {
		setUp2();
		//Attribut-Player erstellen
		Player p0 = model2.getTableOrder().get(0);
		Player p1 = model2.getTableOrder().get(1);
		Player p2 = model2.getTableOrder().get(2);
		Player p3 = model2.getTableOrder().get(3);
		
		//set Catapults
		model2.getPath(new Location (1,2,0)).createCatapult(p0);
		model2.getPath(new Location (1,2,1)).createCatapult(p0);
		model2.getPath(new Location (1,3,3)).createCatapult(p0);
		model2.getPath(new Location (1,2,2)).createCatapult(p1);
		model2.getPath(new Location (3,2,0)).createCatapult(p2);
		
		//"antwortssets"
		Set<Path> p0Attack = new HashSet<Path>();
		p0Attack.add(model2.getPath(new Location(1,2,2)));
		assertEquals(p0Attack, model2.attackableCatapults(p0));
		
		Set<Path> p1Attack = new HashSet<Path>();
		p1Attack.add(model2.getPath(new Location(1,3,3)));
		p1Attack.add(model2.getPath(new Location(1,3,4)));
		assertEquals(p1Attack, model2.attackableCatapults(p1));
		
		assertEquals(null , model2.attackableCatapults(p2));
		assertEquals(null , model2.attackableCatapults(p3));
		
		// durch eigene villages angreifen
		model2.getIntersection(new Location(2,2,0)).createBuilding(BuildingType.Village, p0);
		assertEquals(p0Attack, model2.attackableCatapults(p0));
		// durch fremde villages angreifen
		assertEquals(null , model2.attackableCatapults(p1));
	}
	
	@Test
	public void testGetStreets() {
		setUp2();
		//Player 2 noch 1 strasse dazu
		model2.buildStreet(new Location(1,1,4));
		
		//Attribut-Player erstellen
		Player p0 = model2.getTableOrder().get(0);
		Player p1 = model2.getTableOrder().get(1);
		
		//"StreetSets"
		Set<Path> p0Paths = new HashSet<Path>();
		p0Paths.add(model2.getPath(new Location(0, 0, 1)));
		p0Paths.add(model2.getPath(new Location(1, 0, 1)));
		assertEquals(p0Paths, model2.getStreets(p0));
		
		Set<Path> p1Paths = new HashSet<Path>();
		p1Paths.add(model2.getPath(new Location(0, 0, 5)));
		p1Paths.add(model2.getPath(new Location(1, 0, 5)));
		p1Paths.add(model2.getPath(new Location(1, 0, 4)));
		assertEquals(p1Paths, model2.getStreets(p1));
	}
	
	@Test
	public void testGetSettlements() {
		setUp2();
		//player2 only towns
		model2.buildSettlement(new Location(0,0,5) , BuildingType.Town);
		//Attribut-Player erstellen
		Player p0 = model2.getTableOrder().get(0);
		Player p1 = model2.getTableOrder().get(1);
		Player p2 = model2.getTableOrder().get(2);
		
		//SettlementSets
		Set<Intersection> p0Villages = new HashSet<Intersection>();
		p0Villages.add(model2.getIntersection(new Location(0,0,1)));
		assertEquals(p0Villages, model2.getSettlements(p0, BuildingType.Village));
		
		Set<Intersection> p0Towns = new HashSet<Intersection>();
		p0Towns.add(model2.getIntersection(new Location(1,0,1)));
		assertEquals(p0Towns, model2.getSettlements(p0, BuildingType.Town));
		
		Set<Intersection> p1Towns = new HashSet<Intersection>();
		p1Towns.add(model2.getIntersection(new Location(0,0,5)));
		p1Towns.add(model2.getIntersection(new Location(1,0,5)));
		assertEquals(p1Towns, model2.getSettlements(p1, BuildingType.Town));
		
		Set<Intersection> p2Villages = new HashSet<Intersection>();
		p2Villages.add(model2.getIntersection(new Location(3,0,0)));
		p2Villages.add(model2.getIntersection(new Location(3,1,0)));
		assertEquals(p2Villages, model2.getSettlements(p2, BuildingType.Village));
		
	}
	
	private void setUp2() {
		//gibt den akt. Playern alle Resourcen um Komplikationen mit build zu vermeiden.
		model2.getTableOrder().get(0).modifyResources(new ResourcePackage(333,333,333,333,333)); 
		model2.getTableOrder().get(1).modifyResources(new ResourcePackage(333,333,333,333,333)); 
		model2.getTableOrder().get(2).modifyResources(new ResourcePackage(333,333,333,333,333));   // Welt2 hat nur 2 Spieler hier tritt der Fehler auf  (Index out of Bounds: 2) TODO
		//Init-round 
			//first Player builds first Village
		model2.buildSettlement(new Location(0,0,1) , BuildingType.Village);
		model2.buildStreet(new Location(0,0,1));
			//second Player builds first Village
		model2.buildSettlement(new Location(0,0,5) , BuildingType.Village);
		model2.buildStreet(new Location(0,0,5));
			//third Player builds first Village 
		model2.buildSettlement(new Location(3,1,0) , BuildingType.Village);
		model2.buildStreet(new Location(3,1,0));	
			//third Player builds second Village 
		model2.buildSettlement(new Location(3,0,0) , BuildingType.Village);
		model2.buildStreet(new Location(3,0,0));	
			//second Player builds second Village
		model2.buildSettlement(new Location(1,1,5) , BuildingType.Village);
		model2.buildStreet(new Location(1,1,5));
			//first Player builds second Village
		model2.buildSettlement(new Location(1,0,1) , BuildingType.Village);
		model2.buildStreet(new Location(1,0,1));
		
		
		//new round -> first player builds
		model2.newRound(12);
		//Towns
		model2.buildSettlement(new Location(1,0,1) , BuildingType.Town);
		
		//new round -> second player builds
		model2.newRound(12);
		//Towns
		// Vorschlag: model2.buildSettlement(new Location(1,0,5, BuildingType.Village);
		model2.buildSettlement(new Location(1,0,5) , BuildingType.Town);	// geht glaube ich nicht an dieser Location, da hie noch keine Village stand (Kommentar von Philipp) TODO
		
	}
}
