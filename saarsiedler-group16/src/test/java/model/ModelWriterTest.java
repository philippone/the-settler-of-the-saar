package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import help.TestUtil;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class ModelWriterTest {	
	
	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel1(); // One Village, Two Players
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(100,100,100,100,100));
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(-100,-100,-100,-100,-100));
	}
	
	public void initalize(){
		//Build inital stuff
				model.buildSettlement(new Location(1, 1, 0), BuildingType.Village);
				model.buildStreet(new Location(1, 1, 0));
				model.buildSettlement(new Location(2, 1, 3), BuildingType.Village);
				model.buildStreet(new Location(2, 1, 3));
				model.newRound(12);
	}
	
	@Test
	// Tests wheter you are able to place a catapult next to a village (!= town).
	// An IllegalStateException should be thrown in that case.
	public void buildCatapultTest() {
		initalize();
		try {
			model.buildCatapult(new Location(1, 1, 0), true);
			fail("You was able to build a catapult close to a village");
		}
		catch (IllegalStateException e) { /* everything is fine!*/ }
	}
	
	@Test
	// Tests wheter your catapult was successfully builded and that the owner is the right one.
	public void buildCatapult2Test() {
		initalize();
		Player currentPlayer = model.getCurrentPlayer();
		model.buildSettlement(new Location(1, 1, 0), BuildingType.Town);
		model.buildCatapult(new Location(1, 1, 0), true);
		Path catapultPath = model.getPath(new Location(1, 1, 0));
		assertTrue("There should be a catapult!", catapultPath.hasCatapult());
		assertEquals("Something went wrong with the owner!", catapultPath.getCatapultOwner(), currentPlayer);
	}
	
	@Test
	// Tests wheter your catapult wasn't successfully builded and that the owner is the right one.
	public void buildCatapult2_negativeTest() {
		initalize();
		Player currentPlayer = model.getCurrentPlayer();
		model.buildSettlement(new Location(1, 1, 0), BuildingType.Town);
		model.buildCatapult(new Location(1, 1, 0), false);
		Path catapultPath = model.getPath(new Location(1, 1, 0));
		assertTrue("There should be a catapult!", catapultPath.hasCatapult());
		assertFalse("Something went wrong with the owner!", catapultPath.getCatapultOwner().equals(currentPlayer));
	}
	
	@Test
	// Tests wheter the currentPlayer has enough resources to build a catapult.
	public void buildCatapult3_buildTest() {
		initalize();
		model.newRound(2);
		try {
			model.buildCatapult(new Location(2, 1, 3), false);
			fail("You shouldn't have enough money to build this catapult!");
		} catch (IllegalStateException e) { /* everything is fine */ }
	}
	
	@Test
	// Tests wheter the currentPlayer has enough resources to move a catapult.
	public void buildCatapult3_moveTest() {
		initalize();
		model.newRound(2);
		model.getCurrentPlayer().modifyResources(new ResourcePackage(1000, 1000, 1000, 1000, 1000));
		model.buildCatapult(new Location(2, 1, 3), false);
		try {
			model.getCurrentPlayer().modifyResources(new ResourcePackage(-1000, -1000, -1000, -1000, -1000));
			model.catapultMoved(new Location(2, 1, 3), new Location(2, 1, 4), true);
			fail("You shouldn't have enough money to build this catapult!");
		} catch (IllegalStateException e) { /* everything is fine */ }
	}
	
	@Test
	// Tests wheter an exception is thrown when the fightoutcome is false, but there is no catapult that
	// could have destroyed the attacking catapult
	public void catapulCatapult4_buildCatapultExceptionTest() {
		initalize();
		try {
			model.buildCatapult(new Location(1, 1, 0), false);
			fail("You lost against a not existing catapult!");
		} catch (IllegalStateException e) { /* everything is fine */ }
		
	}
	
	@Test
	// Tests wheter the currentPlayer has enough resources to attack a catapult.
	public void buildCatapult3_attackCatapultTest() {
		initalize();
		model.newRound(2);
		Path newCatPath = model.getPath(new Location(2, 1, 4));
		newCatPath.createCatapult(model.getCurrentPlayer());
		model.attackSettlement(new Location(2, 1, 4), new Location(2, 1, 3), );
		
		
	}
	
	@Test
	// Tests wheter the currentPlayer has enough resources to attack a settlement.
	public void buildCatapult3_attackSettlementTest() {
		throw new UnsupportedOperationException();
	}
	
	private void townAround() {
		
	}	
	
	@Test
	/**
	 * Town nicht in runde 0
	 */
	public void buildSettlementTest() {
		
	}
		
	@Test
	public void buildCatapultTestNegative() {
		
	}

	@Test
	public void buildStreetTest() {
		
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
