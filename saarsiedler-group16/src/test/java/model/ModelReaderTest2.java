package model;

import static org.junit.Assert.*;
import help.TestUtil;

import java.io.IOException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;


public class ModelReaderTest2 {

	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel2();
	}	
	
	@Test
	public void testGetInitVillages() {
		int initVillages=model.getInitVillages();
		assertTrue(initVillages==2);
	}
	
	@Test
	public void testGetMaxBuilding() {
		int maxTowns=model.getMaxBuilding(BuildingType.Town);
		assertTrue(maxTowns==5);
		int maxVillages=model.getMaxBuilding(BuildingType.Village);
		assertTrue(maxVillages==9);
	}
	
	@Test
	public void testGetRound() {
		int roundNumber=model.getRound();
		assertEquals(roundNumber,0);
		
	}
	
	@Test
	public void testBuildableVillageIntersections() {
		Player pl=model.getCurrentPlayer();
		Set<Intersection> si=model.buildableVillageIntersections(pl);
		if (!(si.isEmpty())){
		for (Intersection i : si){ //check for all intersections
			Set<Path> sp=model.getPathsFromIntersection(i);
			boolean b=false;
			for(Path p : sp){ // check for all neighbor PAths
				b= b | (p.getStreetOwner()==pl);
				// check if player owns a street on this path 
			}
			assertTrue(b);
			// check player has a street on a neighbor path leading to this intersection
			Set<Intersection> si1=model.getIntersectionsFromIntersection(i);
			b=false;
			for (Intersection i1 : si1){ // check for all neighbor intersections
				b=b | (i1.hasOwner());
				// check if there is a player on this intersection
			}
			assertFalse(b);
			// check there is no building on the neighbor intersections
			assertFalse(i.hasOwner());
			// check there is no building here
		}
		}
		else assertTrue(si.isEmpty());
	}
	
	@Test
	public void testBuildableTownIntersections() {
		Player pl=model.getCurrentPlayer();
		Set<Intersection> si=model.buildableTownIntersections(pl);
		if (!(si.isEmpty())){
		for (Intersection i : si){ //check for all intersections
			assertTrue((i.getOwner()==pl && i.getBuildingType()==BuildingType.Village));
			// check player owns a village here
		}
		}
		else assertTrue(si.isEmpty());
	}
	
	
	
	@Test
	public void testBuildableStreetPaths() {
		Player pl=model.getCurrentPlayer();
		Set<Path> sp=model.buildableStreetPaths(pl);
		if (!(sp.isEmpty())){
		for(Path p : sp){ //check for all paths
			boolean b=false;
			Set<Path> sp1=model.getPathsFromPath(p);
			for(Path p1: sp1){ // check for each neighbor path
				b=b | (p1.getStreetOwner()==pl);
				/// check if player owns a street on this path
			}
			assertTrue(b);
			//check player has a street on a neighbor path
			assertFalse(p.hasStreet());
			// check this path is free from street
		}
		}
		else assertTrue(sp.isEmpty());
	}
	
	@Test
	public void testBuildableCatapultPaths() {
		Player pl=model.getCurrentPlayer();
		Set<Path> sp=model.buildableCatapultPaths(pl);
		if (!(sp.isEmpty())){
		for(Path p : sp){ // check for all paths
			Set<Intersection> si=model.getIntersectionsFromPath(p);
			boolean b=false;
			for(Intersection i: si){ // check for all neighbor intersections
				b=b | (i.getOwner()==pl && i.getBuildingType()==BuildingType.Town);
				// check if player owns a Town on this Intersection
			}
			assertTrue(b);
			//check player has a Town on a neighbor Intersection of this  path
		}
		}
		else assertTrue(sp.isEmpty());
	}
	

	
}
