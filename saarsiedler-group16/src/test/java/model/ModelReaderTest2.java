package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class ModelReaderTest2 {

	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel2();
	}	
	
	public void initialize(){
		//Build inital stuff
		//1. Player
		model.buildSettlement(new Location(0, 2, 3), BuildingType.Village);
		model.buildStreet(new Location(0, 2, 3));
		// 2. Payer
		model.buildSettlement(new Location(2, 0, 1), BuildingType.Village);
		model.buildStreet(new Location(2, 0, 1));
		//
		//2. Player
		model.buildSettlement(new Location(3, 2, 0), BuildingType.Village);
		model.buildStreet(new Location(3, 2, 0));
		//1. Payer
		model.buildSettlement(new Location(2, 2, 3), BuildingType.Village);
		model.buildStreet(new Location(2, 2, 3));
		//
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(1000,1000,1000,1000,1000));
		model.getTableOrder().get(1).modifyResources(new ResourcePackage(1000,1000,1000,1000,1000));

		// new Round
		model.newRound(12);
		
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
		assertTrue(maxVillages==5);
	}
	
	@Test
	public void testGetRound() {
		int roundNumber=model.getRound();
		assertEquals(roundNumber,0);
		
	}
	
	@Test
	public void testBuildableVillageIntersections() {		
		model.buildSettlement(new Location(0,0,0), BuildingType.Village);
		model.buildStreet(new Location(0,0,0));
		
		model.buildSettlement(new Location(1,0,0), BuildingType.Village);
		model.buildStreet(new Location(1,0,0));
		
		model.buildSettlement(new Location(2,0,0), BuildingType.Village);
		model.buildStreet(new Location(2,0,0));
		
		model.buildSettlement(new Location(3,0,0), BuildingType.Village);
		model.buildStreet(new Location(3,0,0));
		
		model.newRound(9);
		
		Player pl=model.getCurrentPlayer();
		Set<Intersection> si=model.buildableVillageIntersections(pl);
		if (!(si.isEmpty())){
		for (Intersection i : si){ //check for all intersections
			Set<Path> sp=model.getPathsFromIntersection(i);
			boolean b=false;
			for(Path p : sp){ // check for all neighbor PAths
				b= b || (p.hasStreet() && p.getStreetOwner()==pl);
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
		
		model.buildSettlement(new Location(0,0,0), BuildingType.Village);
		model.buildStreet(new Location(0,0,0));
		
		model.buildSettlement(new Location(1,0,0), BuildingType.Village);
		model.buildStreet(new Location(1,0,0));
		
		model.buildSettlement(new Location(2,0,0), BuildingType.Village);
		model.buildStreet(new Location(2,0,0));
		
		model.buildSettlement(new Location(3,0,0), BuildingType.Village);
		model.buildStreet(new Location(3,0,0));
		
		model.newRound(9);
		
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
		
		model.buildSettlement(new Location(0,0,0), BuildingType.Village);
		model.buildStreet(new Location(0,0,0));
		
		model.buildSettlement(new Location(1,0,0), BuildingType.Village);
		model.buildStreet(new Location(1,0,0));
		
		model.buildSettlement(new Location(2,0,0), BuildingType.Village);
		model.buildStreet(new Location(2,0,0));
		
		model.buildSettlement(new Location(3,0,0), BuildingType.Village);
		model.buildStreet(new Location(3,0,0));
		
		model.newRound(9);
		
		Player pl=model.getCurrentPlayer();
		Set<Path> sp=model.buildableStreetPaths(pl);
		if (!(sp.isEmpty())){
			for(Path p : sp){ //check for all paths
				boolean b=false;
				Set<Path> sp1=model.getPathsFromPath(p);
				for(Path p1: sp1){ // check for each neighbor path
					b = b || (p1.hasStreet() && p1.getStreetOwner()==pl);
					// check if player owns a street on this path
				}
				assertTrue(b);
				//check player has a street on a neighbor path
				assertFalse(p.hasStreet());
				// check this path is free from street
			}
		}
		else assertTrue(sp.isEmpty());
	}

	public void testBuildableStreetPathsInit() {
		model.buildSettlement(new Location(1, 1, 1), BuildingType.Village);
		//model.setInitVillageIntersection(new Intersection(new Location(1, 1, 1)));
		Player player = model.getCurrentPlayer();
		//model.setMe(player);
		Set<Path> buildablePaths = model.buildableStreetPaths(player);
		Set<Path> expectedPaths = new HashSet<Path>();
		expectedPaths.add(new Path(new Location(0, 1, 3)));
		expectedPaths.add(new Path(new Location(0, 1, 2)));
		expectedPaths.add(new Path(new Location(1, 1, 1)));
		assertTrue(buildablePaths.containsAll(expectedPaths));
		assertTrue(expectedPaths.containsAll(buildablePaths));
		
	}
	
	@Test
	public void testBuildableStreetPathsInit2() {	
		model.buildSettlement(new Location(0, 0, 3), BuildingType.Village);
		//model.setInitVillageIntersection(new Intersection(new Location(1, 1, 4)));
		Player player = model.getCurrentPlayer();
		//model.setMe(player);
		Set<Path> buildablePaths = model.buildableStreetPaths(player);
		Set<Path> expectedPaths = new HashSet<Path>();
		expectedPaths.add(model.getPath(new Location(1, 0, 0)));
		expectedPaths.add(model.getPath(new Location(1, 0, 1)));
		expectedPaths.add(model.getPath(new Location(1, 1, 5)));
		assertEquals(buildablePaths,expectedPaths);
	}
	
	@Test
	public void testBuildableStreetPathsInit1(){
		model.buildSettlement(new Location(1, 1, 1), BuildingType.Village);
		//model.setInitVillageIntersection(new Intersection(new Location(1, 1, 1)));
		Player player1 = model.getCurrentPlayer();
		model.setMe(player1);
		Set<Path> curSet = model.buildableStreetPaths(player1);
		assertNotSame(curSet.size(), 0);
			for( Path p : curSet){
				assertEquals(false, p.hasStreet());
				Set<Intersection> Intersections = model.getIntersectionsFromPath(p);
				boolean hasBuildingOnInters = false;
				for (Intersection i : Intersections){
					if(i.hasOwner()){
						if(i.getOwner().equals(player1))
						hasBuildingOnInters = true;
					}
				}
					assertTrue(hasBuildingOnInters);
					Set<Field> Fields = model.getFieldsFromPath(p);
					boolean hasLand = false;
					for (Field f: Fields){
						if (f.getFieldType() != FieldType.WATER)
							hasLand = true;
					}
						assertTrue(hasLand);
					} 
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
