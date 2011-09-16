package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Set;

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
	
//		model.getTableOrder().get(0).modifyResources(new ResourcePackage(-100,-100,-100,-100,-100));
	}
	
	public void initialize(){
		//Build inital stuff
		//1. Player
		model.buildSettlement(new Location(1, 1, 0), BuildingType.Village);
		model.buildStreet(new Location(1, 1, 0));
		// 2. Payer
		model.buildSettlement(new Location(2, 1, 3), BuildingType.Village);
		model.buildStreet(new Location(2, 1, 3));
		//
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(1000,1000,1000,1000,1000));
		model.getTableOrder().get(1).modifyResources(new ResourcePackage(1000,1000,1000,1000,1000));

		// new Round
		model.newRound(12);
		
	}
	
	
	// Initialisierungstests
	
	@Test
	public void buildAnything_Init() {
		// 1.Spieler
		model.buildSettlement(new Location(1,1,0), BuildingType.Village);
		model.buildStreet(new Location(1,1,0));
		assertTrue(model.getIntersection(new Location(1,1,0)).hasOwner());
		assertTrue("Village-Owner stimmt nicht ueberein",model.getIntersection(new Location(1,1,0)).getOwner().equals(model.getTableOrder().get(0)));
		
		assertTrue(model.getPath(new Location(1,1,0)).hasStreet());
		assertTrue(model.getPath(new Location(1,1,0)).getStreetOwner().equals(model.getTableOrder().get(0)));
		
		// 2. Spieler
		model.buildSettlement(new Location(1,2,2), BuildingType.Village);
		model.buildStreet(new Location(1,2,2));
		assertTrue(model.getIntersection(new Location(1,2,2)).hasOwner());
		assertTrue("Village-Owner stimmt nicht ueberein",model.getIntersection(new Location(1,2,2)).getOwner().equals(model.getTableOrder().get(1)));  //0 da reihenfolge gedreht
		assertTrue(model.getPath(new Location(1,2,2)).hasStreet());
		assertTrue(model.getPath(new Location(1,2,2)).getStreetOwner().equals(model.getTableOrder().get(1)));
	}
	
	@Test
	public void build_Init_fail2Villages() {
		model.buildSettlement(new Location(1,1,0), BuildingType.Village);
		try {
			model.buildSettlement(new Location(1,2,2), BuildingType.Village);
		} catch(Exception e) {
			// Test run
		}
	}
	
	@Test
	public void build_Init_fail2Villages2() {
		model.buildSettlement(new Location(1,1,0), BuildingType.Village);
		try {
			model.buildSettlement(new Location(1,1,1), BuildingType.Village);
		} catch(Exception e) {
			// Test run
		}
	}
	
	@Test
	public void build_Init_failTown() {
		try {
			model.buildSettlement(new Location(1,1,0), BuildingType.Town);
		} catch(Exception e) {
			// Test run
		}
		
	}
	
	
	///// init close
	
	@Test
	// Tests wheter it is possible to build a village
	public void buildSettlementVillageTest() {
		initialize();
		model.buildStreet(new Location(1,1,1));
		model.buildSettlement(new Location(1,1,2), BuildingType.Village);
		assertTrue(model.getIntersection(new Location(1,1,2)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue(model.getIntersection(new Location(1,2,4)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue(model.getIntersection(new Location(2,1,0)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue(model.getIntersection(new Location(1,1,2)).getBuildingType().equals(BuildingType.Village));
	}
	
	
	@Test
	// Tests wheter it is possible to build a village
	public void buildSettlementTownTest() {
		initialize();
		model.buildStreet(new Location(1,1,1));
		model.buildSettlement(new Location(1,1,2), BuildingType.Village);
		model.buildSettlement(new Location(1,1,2), BuildingType.Town);
		assertTrue(model.getIntersection(new Location(1,1,2)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue(model.getIntersection(new Location(1,2,4)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue(model.getIntersection(new Location(2,1,0)).getOwner().equals(model.getCurrentPlayer()));
		assertTrue(model.getIntersection(new Location(1,1,2)).getBuildingType().equals(BuildingType.Town));
	}
	
	
	@Test
	public void buildSettlementOnWater() {
		initialize();
		// on Water
		try {
			model.buildSettlement(new Location(0, 0, 0), BuildingType.Village);
			fail("Du darfst nicht ins Wasser bauen");
		}
		catch (Exception e) { /* everything is fine */ }
	}
	
	@Test
	public void buildSettlementOnExistingVillage() {
		initialize();
		// on Water
		try {
			model.buildSettlement(new Location(1, 1, 0), BuildingType.Village);
			fail("Hier steht bereits ein Dorf");
		}
		catch (Exception e) { /* everything is fine */ }
	}
	
	
	@Test
	public void buildSettlementOnExistingTown1() {
		initialize();
		// build over an existing Town
		model.buildSettlement(new Location(1, 1, 0), BuildingType.Town);
		try {	
			model.buildSettlement(new Location(1,1,0), BuildingType.Village);
			fail("Hier steht bereits ein Dorf");
		}
		catch (Exception e) { /* everything is fine */ }
	}
	
	@Test
	public void buildSettlementOnExistingTown2() {
		initialize();
		// build over an existing Town
		model.buildSettlement(new Location(1, 1, 0), BuildingType.Town);
		try {	
			model.buildSettlement(new Location(1,1,0), BuildingType.Town);
			fail("Hier steht bereits eine Stadt");
		}
		catch (Exception e) { /* everything is fine */ }
	}
	
	
	
	@Test
	// Tests wheter you are able to place a catapult next to a village (!= town).
	// An IllegalStateException should be thrown in that case.
	public void buildCatapultTest() {
		initialize();
		try {
			model.buildCatapult(new Location(1, 1, 0), true);
			fail("You was able to build a catapult close to a village");
		}
		catch (Exception e) { /* everything is fine!*/ }
	}
	
	@Test
	// Tests wheter your catapult was successfully builded and that the owner is the right one.
	public void buildCatapult2Test() {
		initialize();
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
		initialize();
		Player currentPlayer = model.getCurrentPlayer();
		model.buildSettlement(new Location(1, 1, 0), BuildingType.Town);
		model.getPath(new Location(1,1,0)).createCatapult(model.getTableOrder().get(1));
		model.buildCatapult(new Location(1, 1, 0), false);
		Path catapultPath = model.getPath(new Location(1, 1, 0));
		assertTrue("There should be a catapult!", catapultPath.hasCatapult());
		assertFalse("Something went wrong with the owner!", catapultPath.getCatapultOwner().equals(currentPlayer));
	}
	
	@Test
	// Tests wheter the currentPlayer has enough resources to build a catapult.
	public void buildCatapult3_buildTest() {
		initialize();
		model.newRound(2);
		model.getCurrentPlayer().modifyResources(new ResourcePackage(-1000,-1000,-1000,-1000,-1000));
		try {
			model.buildCatapult(new Location(2, 1, 3), false);
			fail("You shouldn't have enough money to build this catapult!");
		} catch (Exception e) { /* everything is fine */ }
	}
	
	@Test
	// Tests catapult move
	public void moveCatapultTrueTest() {
		initialize();
		model.buildSettlement(new Location(1,1,0), BuildingType.Town);
		model.buildCatapult(new Location(1,1,0), true);
		model.catapultMoved(new Location(1,1,0), new Location(1,1,1), true);
		assertTrue(model.getPath(new Location(1,1,1)).hasCatapult());
		assertTrue(model.getPath(new Location(1,1,1)).getCatapultOwner().equals(model.getCurrentPlayer()));
	}
	
	
	@Test
	//move catapult: fightoutcome false
	public void moveCatapultFalseTest() {
		initialize();
		model.buildSettlement(new Location(1,1,0), BuildingType.Town);
		model.buildCatapult(new Location(1, 1, 0), true);
		model.getPath(new Location(1,1,1)).createCatapult(model.getTableOrder().get(1));
		model.catapultMoved(new Location(1,1,0), new Location(1,1,1), false);
		assertTrue(model.getPath(new Location(1,1,1)).hasCatapult());
		assertFalse(model.getPath(new Location(1,1,1)).getCatapultOwner().equals(model.getCurrentPlayer()));
	}
	
	@Test
	// You haven't enougth money to move your catapult
	public void moveCatapultCostTest() {
		initialize();
		model.getCurrentPlayer().modifyResources(new ResourcePackage(-1000,-1000,-1000,-1000,-1000));
		model.getPath(new Location(1,1,0)).createCatapult(model.getCurrentPlayer());
		try {
			model.catapultMoved(new Location(1,1,0), new Location(1,1,1), true);
			fail("You shouldn't have enough money to build this catapult!");
		} catch (Exception e) { /* everything is fine */ }
	}
	
	@Test
	// Tests wheter an exception is thrown when the fightoutcome is false, but there is no catapult that
	// could have destroyed the attacking catapult
	public void catapulCatapult4_buildCatapultExceptionTest() {
		initialize();
		model.buildSettlement(new Location(1,1,0), BuildingType.Town);
		try {
			model.buildCatapult(new Location(1, 1, 0), false);
			fail("You lost against a not existing catapult!");
		} catch (Exception e) { /* everything is fine */ }
		
	}
	
	@Test
	// Tests not enouth money to attack
	public void attackSettlementTest() {
		initialize();

		model.newRound(2);
		model.buildSettlement(new Location(2,1,3), BuildingType.Town);
		model.newRound(12);
		
		model.getPath(new Location(2,1,2)).createCatapult(model.getCurrentPlayer());
		model.getCurrentPlayer().modifyResources(new ResourcePackage(-1000,-1000,-1000,-1000,-1000));
//		try {
//			model.attackSettlement(new Location(2, 1, 2), new Location(2, 1, 3), AttackResult.SUCCESS);
//			fail("You shouldn't have enough money to attack the settlement!");
//		}
//		catch (Exception e) { /* everything is fine */ }
	}
	
	
	@Test
	// Tests wheter it is possible to build more then the maximum number of catapults
	public void buildCatapult4_maxTest() {
		initialize();
		Player currentPlayer = model.getCurrentPlayer();
		Path path1 = model.getPath(new Location(1, 1, 0));
		path1.createCatapult(currentPlayer);
		Path path2 = model.getPath(new Location(1, 1, 1));
		path2.createCatapult(currentPlayer);
		Path path3 = model.getPath(new Location(1, 1, 2));
		path3.createCatapult(currentPlayer);
		Path path4 = model.getPath(new Location(1, 1, 3));
		path4.createCatapult(currentPlayer);
		model.buildSettlement(new Location(1,1,0), BuildingType.Town);
		try {
			model.buildCatapult(new Location(1, 1, 5), true);
		}
		catch (Exception e) { /* everything is fine */ }
	}
	
	

	
	@Test
	// Test wheter we have got enough resources to build the specified Settlement
	public void buildSettlement_costpositiveTest() {
		initialize();
		model.buildStreet(new Location(1, 1, 1));
		try {
			model.buildSettlement(new Location(1, 1, 2), BuildingType.Village);
		} catch (Exception e) {
			fail("You should have enough resources to build the village!");
		}
	}
	
	@Test
	// Tests wheter we have not got enough resources to build the specified Settlement
	public void buildSettlement_costnegativeTest() {
		initialize();
		model.getPath(new Location(1, 2, 3)).createStreet(model.getCurrentPlayer());
		model.getCurrentPlayer().modifyResources(new ResourcePackage(-1000,-1000,-1000,-1000,-1000));
		try {
			model.buildSettlement(new Location(1, 2, 3), BuildingType.Village);
			fail("You shouldn't have enough resources to build the village!");
		} catch (Exception e) { /* everything is fine */ }
	}
	
	
	@Test
	// Tests wheter we can build a building although there is a street missing.
	public void buildSettlement_missingStreetTest(){
		initialize();
		try {
			model.buildSettlement(new Location(1, 2, 0), BuildingType.Village);
			fail("There is no steet that allows you to build a new Village!");
		}
		catch (Exception e) { /* everything is fine */ }
	}
	
	@Test
	// Tests wheter there is another base to close to the intersection on which you
	// wish to place your new village
	public void buildSettlement_toCloseTest() {
		initialize();
		try {
			model.buildSettlement(new Location(1,1,1), BuildingType.Village);
			fail("You shouldn't be able to build this village because the intersection is to close" +
					"to a intersection that already has a settlement!");
		}
		catch (Exception e) { /* everything is fine */ }
	}
	
	
	@Test
	public void buildSettlement_upgradePositiveTest(){
		initialize();
		model.buildStreet(new Location(1,1,1));
		model.getIntersection(new Location(1, 1, 1)).createBuilding(BuildingType.Village, model.getCurrentPlayer());
		try {
			model.buildSettlement(new Location(1, 1, 1), BuildingType.Town);
			// everything is fine
		}
		catch (Exception e){
			fail("You should be able to upgrade your town!");
		}
	}
	
	@Test
	// Tests wheter you are able to upgrade your village to a town
	public void buildSettlement_upgradeNegativeTest(){
		initialize();
		try {
			model.buildSettlement(new Location(1, 1, 3), BuildingType.Town);
			fail("You shouldn't be able to upgrade your town! (No village at this intersection)");
		}
		catch (IllegalArgumentException e){
			// everything is fine
		}
	}
	
	@Test
	// Tests wheter you have enough resoruces to build a street.
	public void buildStreet_resourcesTest(){
		initialize();
		model.getCurrentPlayer().modifyResources(new ResourcePackage(-1000,-1000,-1000,-1000,-1000));
		assertEquals(new ResourcePackage(0, 0, 0, 0, 0), model.getCurrentPlayer().getResources());
		assertEquals(model.getCurrentPlayer(), model.getMe());
		try {
			model.buildStreet(new Location(2, 1, 4));
			//assertEquals(new ResourcePackage (-1,-1, 0, 0, 0), model.getCurrentPlayer().getResources());
			assertEquals(new ResourcePackage(0, 0, 0, 0, 0), model.getCurrentPlayer().getResources());
			fail("You shouldn't have enough resources to build that street!");
		}
		catch (IllegalStateException e) { /* everything is fine */ }
	}
	
	@Test
	// Tests wheter there is a street that connects to it.
	public void buildStreet_adjacentNegativeTest(){
		initialize();
		try {
			model.buildStreet(new Location(2, 1, 4));
			fail("There is no street that is adjacent to your road.");
		}
		catch (IllegalStateException e) { /* everything is fine */ }
	}
	
	@Test
	// Tests wheter there is a street that connects to it.
	public void buildStreet_adjacentPositiveTest(){
		initialize();
		try {
			model.buildStreet(new Location(1, 1, 1));
			/* everything is fine */
		}
		catch (IllegalStateException e) {
			fail("There is no street that is adjacent to your road.");
		}
	}
	
	@Test
	public void respondTradeTestPositive() {
		initialize();

		Player p1 = model.getCurrentPlayer();
		model.tradeOffer(-1, -1, 1, 0, 0);

		Player p2 = model.getTableOrder().get(0);
		Player p3 = model.getTableOrder().get(1);

		if (p2 != p1) {
			Set<Long> keySet = model.getPlayerMap().keySet();
			for (Long l : keySet) {
				Player player = model.getPlayerMap().get(l);
				if (player.equals(p2))
					model.respondTrade(l);
			}

		} else if (p3 != p1) {
			Set<Long> keySet = model.getPlayerMap().keySet();
			for (Long l : keySet) {
				Player player = model.getPlayerMap().get(l);
				if (player.equals(p3))
					model.respondTrade(l);
			}
		}
		assertTrue(new ResourcePackage(999, 999, 1001, 1000, 1000).equals(model.getCurrentPlayer().getResources()));
		//assertEquals("vermute equals von ResourcePAckage ist falsch (Philipp)",new ResourcePackage(3, 4, 0, 2, 1), model.getCurrentPlayer().getResources());
	}
	
	
	@Test
	public void respondTradePositive1(){
		model.newRound(7);
		Player p = model.getCurrentPlayer(); // id = 0
		p.modifyResources(new ResourcePackage(5, 6, 7, 3, 0));
		
		Player p2 = model.getTableOrder().get(1);
		p2.modifyResources(new ResourcePackage(20,20,20,20,20));	// id = 2
		
		model.tradeOffer(-4, 0, 0, 0, 1);
		model.respondTrade(2);
		assertTrue("Spieler der anbietet hat falsche Resourcen",new ResourcePackage(24,20,20,20,19).equals( p2.getResources()));
		assertTrue("Spieler der annimmt hat falsche Resourcen",new ResourcePackage(1, 6, 7, 3, 1).equals( p.getResources()));
		
	}
	
	@Test
	public void respondTradeTestNegative() {
		Player p = model.getCurrentPlayer();
		p.modifyResources(new ResourcePackage(2, 5, 6, 0, 1));
		model.tradeOffer(0, 0, -1, 0, 1);
		try {
			model.respondTrade(-3);
			fail("shoud throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// Test laueft durch
		}
		assertEquals(new ResourcePackage(2, 5, 6, 0, 1), p.getResources());
	}
	
}
