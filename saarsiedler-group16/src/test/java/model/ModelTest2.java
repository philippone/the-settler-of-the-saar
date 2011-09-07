package model;

import static org.junit.Assert.*;
import help.TestUtil;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Point;
import de.unisaarland.cs.st.saarsiedler.comm.FieldType;

public class ModelTest2 {
	private Model model,model1;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel2();
		model1 = TestUtil.getStandardModel1();
		
	}

	
	@Test
	public void testgetFieldType(){
		assertEquals(FieldType.Forest, model.getField(new Point(0, 0)));
		assertEquals(FieldType.Hills, model.getField(new Point(1, 0)));
		assertEquals(FieldType.Pasture, model.getField(new Point(2, 0)));
		assertEquals(FieldType.Fields, model.getField(new Point(3, 0)));
		assertEquals(FieldType.Mountains, model.getField(new Point(0, 1)));
		assertEquals(FieldType.Forest, model.getField(new Point(1, 1)));
		assertEquals(FieldType.Hills, model.getField(new Point(2, 1)));
		assertEquals(FieldType.Pasture, model.getField(new Point(3, 1)));
		assertEquals(FieldType.Fields, model.getField(new Point(0, 2)));
		assertEquals(FieldType.Mountains, model.getField(new Point(1, 2)));
		assertEquals(FieldType.Forest, model.getField(new Point(2, 2)));
		assertEquals(FieldType.Hills, model.getField(new Point(3, 2)));
		assertEquals(FieldType.Pasture, model.getField(new Point(0, 3)));
		assertEquals(FieldType.Fields, model.getField(new Point(1, 3)));
		assertEquals(FieldType.Mountains, model.getField(new Point(2, 3)));
		assertEquals(FieldType.Forest, model.getField(new Point(3, 3)));
		
	}
	
		
	@Test
	public void testInitVillages(){
		assertEquals(2, model.getInitVillages());
	}

	
	@Test
	public void testMaxVictoryPoints(){
		 int currentVP = model.getMaxVictoryPoints();
		 //maxVictoryPoints ??
		 int expectedVP =  6;
		 assertEquals("MaxVictory Points did not match those given in the WorldRepresentation ",
				 currentVP, expectedVP);
		
	}
	
	@Test
	public void testMaxVillages(){
		 int currentMV = model.getMaxBuilding(BuildingType.Village);
		 int expectedMV =  5;
		 assertEquals("MaxVillages did not match those given in the WorldRepresentation ",
				 currentMV, expectedMV);
		
	}
	
	@Test
	public void testMaxTowns(){
		 int currentMT= model.getMaxBuilding(BuildingType.Town);
		 int expectedMT =  5;
		 assertEquals("MaxTowns did not match those given in the WorldRepresentation ",
				 currentMT, expectedMT);
		
	}
	
	public void testMaxCatapults(){
		 int currentMC= model.getMaxCatapult();
		 
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
