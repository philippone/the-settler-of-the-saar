package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import help.TestUtil;

import java.io.IOException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class ModelTest2 {	
	
	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel1();
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(100,100,100,100,100));
		model.newRound(4);
	}
	
	@Test
	public void buildCatapultTestPositive() {
		Location path = new Location(1,1,2);
		assertFalse(model.getPath(path).hasCatapult());
		model.buildSettlement(new Location(1, 1, 2), BuildingType.Village);	
		model.buildSettlement(new Location(1, 1, 2), BuildingType.Town);
		townAround();
		model.buildCatapult(path, true);
		assertTrue(model.getPath(path).hasCatapult());
	}
	
	@Test
	public void buildCatapultTestPositive2() {
		Location path = new Location(1,1,2);
		townAround();
		model.buildCatapult(path, true);
		assertTrue(model.getPath(path).hasCatapult());
		assertEquals( model.getPath(path).getCatapultOwner() , model.getTableOrder().get(0));
		model.buildCatapult(path, true);
		assertTrue(model.getPath(path).hasCatapult());
	}
	
	private void townAround() {
		Location path = new Location(1,1,2);
		Set<Intersection> li = model.getIntersectionsFromPath(model.getPath(path));
		boolean hasTown = false;
		for (Intersection i : li) {
			hasTown = hasTown?true:(i.getBuildingType() == BuildingType.Town);
		}
		assertTrue("No Town around!", hasTown);
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
