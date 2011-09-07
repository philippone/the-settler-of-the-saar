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
import de.unisaarland.cs.st.saarsiedler.comm.results.AttackResult;

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
				model.getPlayerMap().get(id)
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
	// Tests wheter the currentPlayer has enough resources to attack a settlement.
	public void buildCatapult3_attackSettlementTest() {
		initalize();
		model.newRound(2);
		Path newCatPath = model.getPath(new Location(2, 1, 4));
		newCatPath.createCatapult(model.getCurrentPlayer());
		try {
			model.attackSettlement(new Location(2, 1, 4), new Location(2, 1, 3), AttackResult.SUCCESS);
			fail("You shouldn't have enough money to attack the settlement!");
		}
		catch (IllegalStateException e) { /* everything is fine */ }
	}
	
	@Test
	// Tests wheter it is possible to build more then the maximum number of catapults
	public void buildCatapult4_maxTest() {
		initalize();
		Player currentPlayer = model.getCurrentPlayer();
		Path path1 = model.getPath(new Location(1, 1, 0));
		path1.createCatapult(currentPlayer);
		Path path2 = model.getPath(new Location(1, 1, 1));
		path2.createCatapult(currentPlayer);
		Path path3 = model.getPath(new Location(1, 1, 2));
		path3.createCatapult(currentPlayer);
		Path path4 = model.getPath(new Location(1, 1, 3));
		path4.createCatapult(currentPlayer);
		try {
			model.buildCatapult(new Location(1, 1, 5), true);
		}
		catch (IllegalStateException e) { /* everything is fine */ }
	}
	
	
	@Test
	// Tests wheter it is possible to build a town in the initial phase
	public void buildSettlementTest() {
		try {
			model.buildSettlement(new Location(1, 1, 1), BuildingType.Town);
			fail("You shouldn't be able to build a town in the inital phase!");
		}
		catch (IllegalStateException e) { /* everything is fine */ }
	}
	
	@Test
	// Test wheter we have got enough resources to build the specified Settlement
	public void buildSettlement_costpositiveTest() {
		initalize();
		model.buildStreet(new Location(1, 1, 1));
		try {
			model.buildSettlement(new Location(1, 1, 2), BuildingType.Village);
		} catch (IllegalStateException e) {
			fail("You should have enough resources to build the village!");
		}
	}
	
	@Test
	// Tests wheter we have not got enough resources to build the specified Settlement
	public void buildSettlement_costnegativeTest() {
		initalize();
		model.newRound(2);
		model.getPath(new Location(2, 2, 3)).createStreet(model.getCurrentPlayer());
		try {
			model.buildSettlement(new Location(2, 2, 3), BuildingType.Village);
			fail("You shouldn't have enough resources to build the village!");
		} catch (IllegalStateException e) { /* everything is fine */ }
	}
	
	
	@Test
	// Tests wheter we can build a building although there is a street missing.
	public void buildSettlement_missingStreetTest(){
		initalize();
		try {
			model.buildSettlement(new Location(2, 2, 0), BuildingType.Village);
			fail("There is no steet that allows you to build a new Village!");
		}
		catch (IllegalStateException e) { /* everything is fine */ }
	}
	
	@Test
	// Tests wheter there is another base to close to the intersection on which you
	// wish to place your new village
	public void buildSettlement_toCloseTest() {
		initalize();
		Player currentPlayer = model.getCurrentPlayer();
		model.getPath(new Location(1, 1, 1)).createStreet(currentPlayer);
		model.getPath(new Location(2, 2, 0)).createStreet(currentPlayer);
		model.getPath(new Location(2, 2, 1)).createStreet(currentPlayer);
		try {
			model.buildSettlement(new Location(2, 2, 2), BuildingType.Village);
			fail("You shouldn't be able to build this village because the intersection is to close" +
					"to a intersection that already has a settlement!");
		}
		catch (IllegalStateException e) { /* everything is fine */ }
	}
	
	@Test
	// Tests wheter there is already a settlement on the destination intersection
	public void buildSettlement_alreadyBuiltTest() {
		initalize();
		Player currentPlayer = model.getCurrentPlayer();
		model.getPath(new Location(1, 1, 1)).createStreet(currentPlayer);
		model.getPath(new Location(2, 2, 0)).createStreet(currentPlayer);
		model.getPath(new Location(2, 2, 1)).createStreet(currentPlayer);
		model.getPath(new Location(2, 2, 2)).createStreet(currentPlayer);
		try {
			model.buildSettlement(new Location(2, 2, 3), BuildingType.Village);
			fail("There is already a settlement on this intersection!");
		}
		catch (IllegalStateException e) { /* everything is fine */ }
	}
	
	@Test
	// Tests wheter you are able to upgrade your village to a town
	public void buildSettlement_upgradePositiveTest(){
		initalize();
		model.getIntersection(new Location(1, 1, 3)).createBuilding(BuildingType.Village, model.getCurrentPlayer());
		try {
			model.buildSettlement(new Location(1, 1, 3), BuildingType.Town);
			// everything is fine
		}
		catch (IllegalStateException e){
			fail("You should be able to upgrade your town!");
		}
	}
	
	@Test
	// Tests wheter you are able to upgrade your village to a town
	public void buildSettlement_upgradeNegativeTest(){
		initalize();
		Player currentPlayer = model.getCurrentPlayer();
		model.getPath(new Location(1, 1, 1)).createStreet(currentPlayer);
		model.getPath(new Location())
		try {
			model.buildSettlement(new Location(1, 1, 3), BuildingType.Town);
			// everything is fine
		}
		catch (IllegalStateException e){
			fail("You should be able to upgrade your town!");
		}
	}
	
	
	@Test
	public void buildStreetTest() {
		
	}
	
}
