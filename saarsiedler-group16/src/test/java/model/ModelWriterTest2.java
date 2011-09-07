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
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class ModelWriterTest2 {

	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel1();
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(100,100,100,100,100));
		model.getTableOrder().get(1).modifyResources(new ResourcePackage(100,100,100,100,100));
		model.buildSettlement(new Location(1,1,0), BuildingType.Village);
		model.buildStreet(new Location(1,1,5));
		model.buildSettlement(new Location(2,1,3), BuildingType.Village);
		model.buildStreet(new Location(2,1,3));
		model.newRound(2);
	}
	
	@Test
	public void longestRoadClaimedFailTest() {
		try {
			List<Location> liste = new LinkedList<Location>();
			liste.add(new Location(1,1,5));
			model.longestRoadClaimed(liste);
			fail();
		}
		catch (Exception e) {
			//Expect this
		}
	}
	
	@Test
	public void longestRoadClaimedFailTest2() {
		try {
			List<Location> liste = new LinkedList<Location>();
			liste.add(new Location(1,1,5));
			model.longestRoadClaimed(liste);
			fail();
		}
		catch (Exception e) {
			//Expect this
		}
	}
	
	@Test
	public void matchStartTest() {
		
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
