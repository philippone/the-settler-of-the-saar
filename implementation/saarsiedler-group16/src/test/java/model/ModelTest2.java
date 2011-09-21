package model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Point;

public class ModelTest2 {
	private Model model2;
	
	@Before
	public void setUp() throws IOException {
		model2 = TestUtil.getStandardModel2();
	}

	
	@Test
	public void testgetFieldType(){
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.FOREST, model2.getField(new Point(0, 0)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.HILLS, model2.getField(new Point(0, 1)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.PASTURE, model2.getField(new Point(0, 2)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.FIELDS, model2.getField(new Point(0, 3)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.MOUNTAINS, model2.getField(new Point(1, 0)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.FOREST, model2.getField(new Point(1, 1)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.HILLS, model2.getField(new Point(1, 2)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.PASTURE, model2.getField(new Point(1, 3)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.FIELDS, model2.getField(new Point(2, 0)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.MOUNTAINS, model2.getField(new Point(2, 1)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.FOREST, model2.getField(new Point(2, 2)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.HILLS, model2.getField(new Point(2, 3)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.PASTURE, model2.getField(new Point(3, 0)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.FIELDS, model2.getField(new Point(3, 1)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.MOUNTAINS, model2.getField(new Point(3, 2)).getFieldType());
		assertEquals(de.unisaarland.cs.sopra.common.model.FieldType.FOREST, model2.getField(new Point(3, 3)).getFieldType());
		
	}
	
		
	@Test
	public void testInitVillages(){
		assertEquals(2, model2.getInitVillages());
	}

	
	@Test
	public void testMaxVictoryPoints(){
		 int currentVP = model2.getMaxVictoryPoints();
		 //maxVictoryPoints ??
		 int expectedVP =  5;
		 assertEquals("MaxVictory Points did not match those given in the WorldRepresentation ",
				 currentVP, expectedVP);
		
	}
	
	@Test
	public void testMaxVillages(){
		 int currentMV = model2.getMaxBuilding(BuildingType.Village);
		 int expectedMV =  5;
		 assertEquals("MaxVillages did not match those given in the WorldRepresentation ",
				 currentMV, expectedMV);
		
	}
	
	@Test
	public void testMaxTowns(){
		 int currentMT= model2.getMaxBuilding(BuildingType.Town);
		 int expectedMT =  5;
		 assertEquals("MaxTowns did not match those given in the WorldRepresentation ",
				 currentMT, expectedMT);
		
	}
	
	public void testMaxCatapults(){
		 int currentMC= model2.getMaxCatapult();
		 
		 int expectedMC = 4;
		 assertEquals("MaxCatapults did not match those given in the WorldRepresentation ",
				 currentMC, expectedMC);
		
	}
	
//	@Test
//	public void testNumberPlayers(){
//		int currentNP = model.getNumPlayers();
//		int expectedNP = 3;
//		assertEquals("The number of Players did not match the one given in the Match Information",
//				currentNP, expectedNP);
//	}
	

}
