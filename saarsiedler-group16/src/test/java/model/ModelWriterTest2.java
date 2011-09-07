package model;

import static org.junit.Assert.*;
import help.TestUtil;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

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
		model.buildStreet(new Location(2,1,3));
		model.buildStreet(new Location(2,1,2));
		model.buildStreet(new Location(2,1,1));
		model.buildStreet(new Location(1,2,2));
		List<Location> liste2 = new LinkedList<Location>();
		liste.add(new Location(2,1,4));
		liste.add(new Location(2,1,3));
		liste.add(new Location(2,1,2));
		liste.add(new Location(2,1,1));
		liste.add(new Location(1,1,2));
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
		List<Player> playerList = new LinkedList();
		playerList.add(model.getPlayerMap().get(0L));
		playerList.add(model.getPlayerMap().get(1L));
		playerList.add(model.getPlayerMap().get(2L));
		assertEquals("Tableorder wasn't set correctly", model.getTableOrder(), playerList);
		Point p1 = new Point()
	}
	
	@Test
	public void catapultMovedTestPositive() {
		
	}
	//Auch attack
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
	
}
