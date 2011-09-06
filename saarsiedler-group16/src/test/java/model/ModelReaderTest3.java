package model;

import help.TestUtil;

import java.io.IOException;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Player;

public class ModelReaderTest3 {

	private Model model;
	private int villageCount;

	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel2();
		villageCount = 		Random r1 = new Random();
		r1.nextInt(10);
	}	


	@Test
	public void testAffordableCatapultBuild() {
		if (model.affordableCatapultBuild() < 0){
			throw new IllegalArgumentException();
		}
	}
	
	@Test
	public void testAffordableCatapultAttack() {
		if (model.affordableCatapultAttack() < 0){
			throw new IllegalArgumentException();
		}
	}
	
	@Test
	public void testAffordableSettlementAttack() {
		if (model.affordableSettlementAttack() < 0){
			throw new IllegalArgumentException();
		}
			
		
	}
	
	@Test
	public void testAffordablePathsAfterVillage(int villageCount) {
		if (model.affordablePathsAfterVillage(villageCount) < 0){
			throw new IllegalArgumentException();
		}
	}
	
	@Test
	public void testAffordableSettlements(BuildingType buildingType){
		
	}
	
	@Test
	public void testAffordableStreets(){
		
	}
	
}
