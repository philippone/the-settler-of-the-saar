package model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import de.unisaarland.cs.sopra.common.PlayerColors;
import de.unisaarland.cs.sopra.common.Setting;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Point;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.sopra.common.view.GameGUI;

public class ModelWriterTest2 {

	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel1();
		model.buildSettlement(new Location(1,1,0), BuildingType.Village);
		model.buildStreet(new Location(1,1,5));
		model.buildSettlement(new Location(2,1,3), BuildingType.Village);
		model.buildStreet(new Location(2,1,3));
		model.newRound(2);
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(100,100,100,100,100));
		model.getTableOrder().get(1).modifyResources(new ResourcePackage(100,100,100,100,100));
	}
	
	@Test
	public void longestRoadClaimedTest() {
		model.buildStreet(new Location(1,1,4));
		model.buildStreet(new Location(1,1,3));
		model.buildStreet(new Location(1,1,2));
		model.buildStreet(new Location(1,1,1));
		List<Location> liste2 = new LinkedList<Location>();
		liste2.add(new Location(1,1,1));
		liste2.add(new Location(1,1,2));
		liste2.add(new Location(1,1,3));
		liste2.add(new Location(1,1,4));
		liste2.add(new Location(1,1,5));
		model.longestRoadClaimed(liste2);
		model.newRound(10);
		model.buildStreet(new Location(2,1,4));
		model.buildStreet(new Location(2,1,2));
		model.buildStreet(new Location(2,1,1));
		model.buildStreet(new Location(1,2,2));
		model.buildStreet(new Location(1,2,1));
		List<Location> liste = new LinkedList<Location>();
		liste.add(new Location(2,1,4));
		liste.add(new Location(2,1,3));
		liste.add(new Location(2,1,2));
		liste.add(new Location(2,1,1));
		liste.add(new Location(1,2,2));
		liste.add(new Location(1,2,1));
		try {
			model.longestRoadClaimed(liste);
			//Expect that it works
		}
		catch (Exception e) {
			fail("Longest road should be claimable");
		}
	}
	
	@Test
	public void longestRoadClaimedFailTest() {
		List<Location> liste = new LinkedList<Location>();
		liste.add(new Location(1,1,5));
		try {
			model.longestRoadClaimed(liste);
			fail("Can't claim a road with less than 5 streets");
		}
		catch (Exception e) {
			//Expect this
		}
	}
	
	@Test
	public void longestRoadClaimedFailTest2() {
		model.buildStreet(new Location(1,1,4));
		model.buildStreet(new Location(1,1,3));
		model.buildStreet(new Location(1,1,2));
		model.buildStreet(new Location(1,1,1));
		model.buildStreet(new Location(1,1,0));
		List<Location> liste = new LinkedList<Location>();
		liste.add(new Location(1,1,1));
		liste.add(new Location(1,1,2));
		liste.add(new Location(1,1,3));
		liste.add(new Location(1,1,4));
		liste.add(new Location(1,1,5));
		liste.add(new Location(1,1,0));
		model.longestRoadClaimed(liste);
		model.newRound(5);
		model.buildStreet(new Location(2,1,4));
		model.buildStreet(new Location(2,1,2));
		model.buildStreet(new Location(2,1,1));
		model.buildStreet(new Location(1,2,2));
		List<Location> liste2 = new LinkedList<Location>();
		liste2.add(new Location(2,1,4));
		liste2.add(new Location(2,1,3));
		liste2.add(new Location(2,1,2));
		liste2.add(new Location(2,1,1));
		liste2.add(new Location(1,1,2));
		try {
			model.longestRoadClaimed(liste2);
			fail("Road is not longer than the other road that was already claimed");
		}
		catch (Exception e) {
			//Expect this
		}
	}
	
	@Test
	public void longestRoadClaimedFailTest3() {
		model.buildStreet(new Location(1,1,4));
		model.buildStreet(new Location(1,1,3));
		model.buildStreet(new Location(1,1,2));
		model.buildStreet(new Location(1,1,1));
		model.buildStreet(new Location(1,1,0));
		List<Location> liste = new LinkedList<Location>();
		liste.add(new Location(1,1,1));
		liste.add(new Location(1,1,2));
		liste.add(new Location(1,1,3));
		liste.add(new Location(1,1,4));
		liste.add(new Location(1,1,0));
		liste.add(new Location(1,1,5));
		try {
			model.longestRoadClaimed(liste);
			fail("Claimed road is not a valid road, because it isn't connected");
		}
		catch (Exception e) {
			//Expect this
		}
	}
	
	@Test
	public void longestRoadClaimedFailTest4() {
		model.buildStreet(new Location(1,1,4));
		model.buildStreet(new Location(1,1,3));
		model.buildStreet(new Location(1,1,2));
		model.buildStreet(new Location(1,1,1));
		model.buildStreet(new Location(1,1,0));
		List<Location> liste = new LinkedList<Location>();
		liste.add(new Location(1,1,1));
		liste.add(new Location(1,1,2));
		liste.add(new Location(1,1,3));
		liste.add(new Location(1,1,4));
		liste.add(new Location(1,1,0));
		liste.add(new Location(1,1,5));
		model.newRound(9);
		try {
			model.longestRoadClaimed(liste);
			fail("Claimed road is not a valid road, because it isn't owned by the current player");
		}
		catch (Exception e) {
			//Expect this
		}
	}
	
	@Test
	public void matchStartTest() {
		List<Player> playerList = new LinkedList<Player>();
		playerList.add(model.getPlayerMap().get(0L));
		playerList.add(model.getPlayerMap().get(2L));
		System.out.println("expected playerList: " + playerList);
		System.out.println("current playerList: " + model.getTableOrder());
		assertEquals("Tableorder wasn't set correctly", model.getTableOrder(), playerList);
		Point p1 = new Point(1,1);
		Point p2 = new Point(2,1);
		assertEquals("Fieldnumber wasn't set correctly", model.getField(p1).getNumber(), 8);
		assertEquals("Fieldnumber wasn't set correctly", model.getField(p2).getNumber(), 6);
	}
	
	@Test
	public void catapultMovedTestPositive() {
		Location l1 = new Location(1,1,0);
		Location l2 = new Location(1,1,1);
		//model.getCurrentPlayer().modifyResources(new ResourcePackage(1000,1000,1000,1000,1000));
		model.buildSettlement(l1, BuildingType.Town);
		model.buildCatapult(l1, true);
		model.catapultMoved(l1, l2, true);
		//Expect this
	}
	
	@Test
	public void catapultMovedTestPositive2() {
		Location l1 = new Location(1,1,0);
		Location l2 = new Location(1,1,1);
		Player p1 = model.getTableOrder().get(0);
		Player p2 = model.getTableOrder().get(1);
		model.buildSettlement(l1, BuildingType.Town);
		model.buildCatapult(l1, true);
		model.getPath(l2).createCatapult(p2);
		model.catapultMoved(l1, l2, true);
		assertEquals("Owner of catapult on field 1,1,1 should be player1", model.getPath(l2).getCatapultOwner(), p1);
	}
	
	@Test
	public void catapultMovedTestPositive3() {
		Location l1 = new Location(1,1,0);
		Location l2 = new Location(1,1,1);
		Player p2 = model.getTableOrder().get(1);
		model.buildSettlement(l1, BuildingType.Town);
		model.buildCatapult(l1, true);
		model.getPath(l2).createCatapult(p2);
		model.catapultMoved(l1, l2, false);
		assertEquals("Owner of catapult on field 1,1,1 should be player2", model.getPath(l2).getCatapultOwner(), p2);
		assertFalse("On field 1,1,0 should be no catapult", model.getPath(l1).hasCatapult());
	}
	
	@Test
	public void catapultMovedTestNegative() {
		Location l1 = new Location(1,1,0);
		Location l2 = new Location(1,1,1);
		model.buildSettlement(l1, BuildingType.Town);
		model.buildCatapult(l1, true);
		try {
		model.catapultMoved(l1, l2, false);
		fail("fightoutcome can't be false because on path 1,1,1 is no other catapult");
		}
		catch (Exception e) {
		//Expect this
		}
	}
	
	@Test
	public void robberMovedTestSelf() {
		TestModelObserver modelObserver = new TestModelObserver();
		model.addModelObserver(modelObserver);
		Point p1 = new Point(1,2);
		Point p2 = new Point(1,1);
		Player player1 = model.getTableOrder().get(0);
		model.robberMoved(p1, p2, 0, Resource.LUMBER);
		assertFalse("Robber shouldn't be on field 1,2", model.getField(p1).hasRobber());
		assertTrue("Robber should be on field 1,1", model.getField(p2).hasRobber());
		assertTrue(modelObserver.eventRobberCalled);
		//assertTrue(modelObserver.updateResourcesCalled);
		assertEquals("Resources not right expected 100,100,100,100,100",player1.getResources(), new ResourcePackage(99,100,100,100,100));
	}
	
	@Test
	public void robberMovedTestOther() {
		TestModelObserver modelObserver = new TestModelObserver();
		model.addModelObserver(modelObserver);
		Point p1 = new Point(1,2);
		Point p2 = new Point(2,1);
		model.robberMoved(p1, p2, 0, null);
		assertFalse("Robber shouldn't be on field 1,2", model.getField(p1).hasRobber());
		assertTrue("Robber should be on field 2,1", model.getField(p2).hasRobber());
		assertTrue(modelObserver.eventRobberCalled);
	}
	
	@Test
	public void returnResources() {
		TestModelObserver modelObserver = new TestModelObserver();
		model.addModelObserver(modelObserver);
		try {
			model.returnResources(-49, -50, -50, -50, -50);
			fail("Wrong count of Resources returned, expected 250 but was only 249");
		}
		catch (Exception e) {
			//Expect this
		}
	}
	
	@Test
	public void returnResources2() {
		TestModelObserver modelObserver = new TestModelObserver();
		model.addModelObserver(modelObserver);
		model.returnResources(-50, -50, -75, -25, -50);
		//Expect this
	}
	
	@Test
	public void returnResources3() {
		TestModelObserver modelObserver = new TestModelObserver();
		model.addModelObserver(modelObserver);
		try {
			model.returnResources(-250, 0, 0, 0, 0);
			fail("returned more of one resource then available");
		}
		catch (Exception e) {
			//Expect this
		}
	}
	
	@Test
	public void newRoundTest() {
		TestModelObserver modelObserver = new TestModelObserver();
		model.addModelObserver(modelObserver);
		model.newRound(2);
		assertTrue("The modelobserver method eventNewRound should be called", modelObserver.eventNewRoundCalled) ;
	}
	
	@Test
	public void newRoundTest2() {
		TestModelObserver modelObserver = new TestModelObserver();
		model.addModelObserver(modelObserver);
		model.newRound(7);
		assertTrue("The modelobserver method eventRobber should be called", modelObserver.eventRobberCalled) ;
	}
	
	@Test
	public void newRoundTest3() {
		TestModelObserver modelObserver = new TestModelObserver();
		model.addModelObserver(modelObserver);
		model.newRound(8);
		assertTrue("The modelobserver method updateResources should be called", modelObserver.updateResourcesCalled) ;
		assertEquals("resources were not updated correctly", model.getTableOrder().get(0).getResources(), new ResourcePackage(101,100,100,100,100));
	}

}
