package model;

import help.TestUtil;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Player;

public class ModelReaderTest3 {

	private Model model;

	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel2();
	}	


	@Test
	public void testAffordableCatapultBuild() {
		
	}
	
	@Test
	public void testAffordableCatapultAttack() {
		
	}
	
	@Test
	public void testAffordableSettlementAttack() {
		
	}
	
	@Test
	public void testAffordablePathsAfterVillage(int villageCount) {
		
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
