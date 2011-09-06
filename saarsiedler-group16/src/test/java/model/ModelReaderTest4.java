package model;

import help.TestUtil;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Player;

public class ModelReaderTest4 {

	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel2();
	}	
	
	@Test
	public void testGetCatapults(Player player) {
		
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
