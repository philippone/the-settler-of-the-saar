package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import help.TestUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.ModelObserver;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;


public class ModelTest {
	
	private Model model;
	
	@Before
	public void setUp() {
		model = TestUtil.getStandardModel();
	}
	
	@Test
	public void testAddObserver() {
		ModelObserver view = TestUtil.getTestView();
		model.addModelObserver(view);
		assertNotNull("Model == null", model);
		assertNotNull("View == null", view);
		assertTrue("ModelObserver-Liste ist leer", model.getModelObservers().size() == 1); 
	}
	
	
	@Test
	public void testRemoveObserver() {
		ModelObserver view = TestUtil.getTestView();
		model.addModelObserver(view);
		model.removeModelObserver(view);
		assertNotNull("Model == null", model);
		assertNotNull("View == null", view);
		assertTrue("ModelObserver-Liste ist leider nicht leer, sollte es aber sein", model.getModelObservers().size()==0);
	}
	
	@Test
	public void testGetCurrentPlayer() {
		Player expectedPlayer = model.getTableOrder().get(0);
		Player currentPlayer = model.getCurrentPlayer();
		assertEquals("nicht der aktuelle Player", expectedPlayer, currentPlayer);
	}
	
	@Test
	public void testCalculateLongestRoad() {
		Player owner = model.getTableOrder().get(0);
		 Player gegner = model.getTableOrder().get(1);
		 List< List<Path> > expectedLongestRoad = new ArrayList<List<Path>>();
		 List<Path> longRoad0 = new LinkedList<Path>();
		 List<Path> longRoad1 = new LinkedList<Path>();
		 List<Path> longRoad2 = new LinkedList<Path>();
		 List<Path> longRoad3 = new LinkedList<Path>();
		 expectedLongestRoad.add(longRoad0);
		 expectedLongestRoad.add(longRoad1);
		 expectedLongestRoad.add(longRoad2);
		 expectedLongestRoad.add(longRoad3);
		 
		 model.buildSettlement(new Location(1,1,0), BuildingType.Village);
		 
		 model.buildStreet(new Location(1,1,5));
		 longRoad0.add(model.getPath(new Location(1,1,5)));
		 longRoad1.add(model.getPath(new Location(1,1,5)));
		 longRoad2.add(model.getPath(new Location(1,1,5)));
		 longRoad3.add(model.getPath(new Location(1,1,5)));
		 
		 model.buildStreet(new Location(1,1,4));
		 longRoad0.add(model.getPath(new Location(1,1,4)));
		 longRoad1.add(model.getPath(new Location(1,1,4)));
		 longRoad2.add(model.getPath(new Location(1,1,4)));
		 longRoad3.add(model.getPath(new Location(1,1,4)));
		 
		 model.buildStreet(new Location(1,1,3));
		 //model.buildStreet(new Location(1,1,2));
		 model.buildStreet(new Location(2,0,5));
		 longRoad0.add(model.getPath(new Location(2,0,5)));
		 longRoad1.add(model.getPath(new Location(2,0,5)));
		 longRoad2.add(model.getPath(new Location(2,0,5)));
		 longRoad3.add(model.getPath(new Location(1,1,3)));
		 
		 model.buildStreet(new Location(2,0,4));
		 model.buildStreet(new Location(2,0,1));
		 longRoad0.add(model.getPath(new Location(2,0,4)));
		 longRoad1.add(model.getPath(new Location(2,0,4)));
		 longRoad2.add(model.getPath(new Location(2,0,4)));
		 longRoad3.add(model.getPath(new Location(2,0,1)));
		 
		 model.buildStreet(new Location(2,0,3));
		 model.buildStreet(new Location(2,0,2));
		 longRoad0.add(model.getPath(new Location(2,0,3)));
		 longRoad1.add(model.getPath(new Location(2,0,3)));
		 longRoad1.add(model.getPath(new Location(2,0,2)));
		 longRoad2.add(model.getPath(new Location(2,0,3)));
		 longRoad2.add(model.getPath(new Location(2,0,2)));
		 longRoad3.add(model.getPath(new Location(2,0,2)));
		 
		 model.buildStreet(new Location(1,1,2));
		 model.buildStreet(new Location(1,2,3));
		 longRoad2.add(model.getPath(new Location(2,0,1)));
		 longRoad2.add(model.getPath(new Location(1,1,2)));
		 longRoad2.add(model.getPath(new Location(1,2,3)));
		 // 2 fertig
		 
		 model.buildStreet(new Location(3,0,1));
		 longRoad0.add(model.getPath(new Location(3,0,1)));
		 longRoad3.add(model.getPath(new Location(3,0,1)));
		 
		 model.buildStreet(new Location(3,1,3));
		 model.buildStreet(new Location(3,1,2));
		 longRoad0.add(model.getPath(new Location(3,1,3)));
		 longRoad3.add(model.getPath(new Location(3,1,3)));
		 longRoad0.add(model.getPath(new Location(3,1,2)));
		 longRoad3.add(model.getPath(new Location(3,1,2)));
		 // 0 und 3 fertig
		 
		 model.buildStreet(new Location(2,1,3));
		 model.buildStreet(new Location(2,1,2));
		 model.buildStreet(new Location(2,2,3));
		 longRoad1.add(model.getPath(new Location(2,1,3)));
		 longRoad1.add(model.getPath(new Location(2,1,2)));
		 longRoad1.add(model.getPath(new Location(2,2,3)));
		 // 1 fertig
		 
		 // gegnerisches Dorf
		 model.buildSettlement(new Location(2,2,3), BuildingType.Village);
		 model.getIntersection(new Location(2,2,3)).setOwner(gegner);
		 // Street hinter gegnerischem Dorf
		 model.buildSettlement(new Location(3,3,3), BuildingType.Village);
		 model.buildStreet(new Location(3,3,5));
		 model.buildStreet(new Location(3,3,3));
		 
		 List<List<Path>> currentLongestRoad = model.calculateLongestRoads(owner);
		 assertEquals("Street0 0 != 9", longRoad0.size(),9);
		 assertEquals("Street1 0 != 9", longRoad1.size(),9);
		 assertEquals("Street2 0 != 9", longRoad2.size(),9);
		 assertEquals("Street3 0 != 9", longRoad3.size(),9);
		 assertEquals("laengste Strassen falsch berechnet (hoffentlich richitger Test)", expectedLongestRoad, currentLongestRoad);
	}
	
	@Test
	public void setTableOrder() {
		
	}
	
	@Test
	public void setFieldNumbers() {
		
	}
	
	@Test
	public void updateLongestRoad() {
		
	}
	
	@Test
	public void getLocationField() {
		
	}
	
	@Test
	public void getLocationIntersection() {
		
	}
	
	@Test
	public void getLocationPath() {
		
	}
	
	
	
	
	
	

}
