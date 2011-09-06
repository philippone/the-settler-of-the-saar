package model;

import help.TestUtil;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Player;

public class ModelReaderTest2 {

	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel2();
	}	
	
	@Test
	public void testGetInitBuilding(BuildingType buildingType) {
		
	}
	
	@Test
	public void testGetMaxBuilding(BuildingType buildingType) {
		
	}
	
	@Test
	public void testGetRound() {
		
	}
	
	@Test
	public void testBuildableIntersections(Player player) {
		
	}
	
	@Test
	public void testBuildableStreetPaths(Player player) {
		
	}
	
	@Test
	public void testBuildableCatapultPaths(Player player) {
		
	}
	
	@Test
	public void testAffordableSettlements(BuildingType buildingType){
		
	}
	
	@Test
	public void testAffordableStreets(){
		
	}
	
}
