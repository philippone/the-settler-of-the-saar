package model;

import static org.junit.Assert.*;
import help.TestUtil;

import java.io.IOException;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;

public class ModelReaderTest4 {

	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel2();
	}	
	
	@Test
	public void testGetCatapults() {
		//Bauende Villages und Catapulte bauen
		model.buildSettlement(new Location(0,0,1) , BuildingType.Village);
		model.buildSettlement(new Location(1,1,1) , BuildingType.Village);
		model.buildCatapult(new Location(0,0,5), true);
		model.buildCatapult(new Location(1,1,5), true);
		
		model.newRound(12);
		//Bauende Villages und Catapulte bauen
		model.buildSettlement(new Location(0,0,0) , BuildingType.Village);
		model.buildSettlement(new Location(1,1,0) , BuildingType.Village);
		model.buildCatapult(new Location(0,0,1), true);
		model.buildCatapult(new Location(1,1,1), true);
		model.buildCatapult(new Location(1,1,0), true);
		
		//Attribut-Player erstellen
		Player p0 = model.getTableOrder().get(0);
		Player p1 = model.getTableOrder().get(1);
		Player p2 = model.getTableOrder().get(2);
		
		//"richtige" Vergleichs-Sets erstellen
		TreeSet t0 = new TreeSet<Path>();
		t0.add(model.getPath(new Location(0,0,5)));
		t0.add(model.getPath(new Location(1,1,5)));
		assertEquals(t0, model.getCatapults(p0));
		
		//"richtige" Vergleichs-Sets erstellen
		TreeSet t1 = new TreeSet<Path>();
		t1.add(model.getPath(new Location(0,0,1)));
		t1.add(model.getPath(new Location(1,1,1)));
		t1.add(model.getPath(new Location(1,1,0)));
		assertEquals(t1, model.getCatapults(p1));
		
		assertEquals("Methode sollte null liefern, wenn der Player keine Catapulte hat", null, model.getCatapults(p2));
	
	}
	
	@Test
	public void testGetLongestClaimedRoad() {
		
	}
	
	@Test
	public void testGetMaxVictoryPoints() {
		
	}
	
	@Test
	public void testGetCurrentVictoryPoints(Player player) {
		
	}
	
	@Test
	public void testCanPlaceRobber() {
		
	}
	
	@Test
	public void testGetRobberFields() {
		
	}
	
	@Test
	public void testGetFieldIterator() {
		
	}
	
	@Test
	public void testGetPathIterator() {
		
	}
	
	@Test
	public void testGetIntersectionIterator() {
		
	}
	
	@Test
	public void testGetHarborIntersections() {
		
	}
	
	@Test
	public void testGetHarborType(Intersection intersection) {
		
	}
	
	@Test
	public void testAttackableSettlements(Player player, BuildingType buildingType) {
		
	}
	
	@Test
	public void testAttackableCatapults(Player player) {
		
	}
	
	@Test
	public void testGetStreets(Player player) {
		
	}
	
	@Test
	public void testGetSettlements(Player player, BuildingType buildingType) {
		
	}
	
}
