package model;

import static org.junit.Assert.*;
import help.TestUtil;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class ModelTest2 {	
	
	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel1();
		model.newRound(4);
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(100,100,100,100,100));
	}
	
	@Test
	public void buildCatapultTestPositive() {
		Location path = new Location(1,1,2);
		assertFalse(model.getPath(path).hasCatapult());
		model.buildCatapult(path, true);
		assertTrue(model.getPath(path).hasCatapult());
	}
	
	@Test
	public void buildCatapultTestPositive2() {
		Location path = new Location(1,1,2);
		model.buildCatapult(path, true);
		assertTrue(model.getPath(path).hasCatapult());
		assertEquals( model.getPath(path).getCatapultOwner() , model.getTableOrder().get(0) );

		
		model.buildCatapult(path, true);
		assertTrue(model.getPath(path).hasCatapult());
	}
	
	@Test
	public void buildCatapultTestNegative() {
		
	}

	@Test
	public void buildStreetTest() {
		
	}
	
	@Test
	public void buildSettlementTest() {
		
	}
	
	@Test
	public void longestRaodClaimedTest() {
		
	}
	
	@Test
	public void matchStartTest() {
		
	}
	
	@Test
	public void catapultMovedTestPositive() {
		
	}
	
	@Test
	public void catapultMovedTestNegative() {
		
	}
	
	@Test
	public void playerLeftTest() {
		
	}
	
	@Test
	public void robberMovedTestSelf() {
		
	}

	@Test
	public void robberMovedTestOther() {
	
	}
		
	@Test
	public void tradeOfferTest() {
		
	}
		
	@Test
	public void respondTradeTestPositive() {
		
	}
	
	@Test
	public void respondTradeTestNegative() {
		
	}
	
}
