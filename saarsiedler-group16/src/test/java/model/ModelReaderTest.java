package model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Point;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;



public class ModelReaderTest {
	
	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel1();
	}		
	
	@Test
	public void testGetHarborTypes() {
		model.newRound(8); // Round 1
		// still no Settlement on the Harbors
		Player currentPlayer = model.getCurrentPlayer();
		Set<HarborType> currentSet = model.getHarborTypes(currentPlayer);
		assertTrue("Empty", currentSet.size() == 0);
		// Village on the LumberHarbor (special Harbor)
		currentPlayer.modifyResources(new ResourcePackage(10000,10000,10000,10000,10000));
		model.buildSettlement(new Location(2, 1, 1), BuildingType.Village);
		Set<HarborType> expectedSet = new TreeSet<HarborType>();
		expectedSet.add(HarborType.LUMBER_HARBOR);
		currentSet = model.getHarborTypes(currentPlayer);
		assertTrue("Contains one element : LUMBER_HARBOR TYPE", currentSet.size() == 1);
		//test if both sets have identical content
		assertTrue(currentSet.containsAll(expectedSet));
		assertTrue(expectedSet.containsAll(currentSet));
		
	}
	
	
	/**
	 * Harbor with buccaneer (Seeraeuber)
	 */
	@Test
	public void testGetHarborTypes1() {
		model.newRound(8); // in Runde 1
		// noch kein Settlement an einem den zwei Harbors
		Player currentPlayer = model.getCurrentPlayer();
		Set<HarborType> currentSet = model.getHarborTypes(currentPlayer);
		assertTrue("es gibt eig. keine HarborTypes", currentSet.size() == 0);
		// Village at LumberHarbor (Spezialhafen)
		currentPlayer.modifyResources(new ResourcePackage(10000,10000,10000,10000,10000));
		model.buildSettlement(new Location(2,1,0), BuildingType.Village);
		// buccaneer at LumberHarbor
		Field robberField = model.getField(new Point(2,1));
		Field neighborWaterField = model.getField(new Point(2,1));
		
		
	}
	
	@Test
	public void testGetResources() {
		model.newRound(8);
		Player currentPlayer = model.getCurrentPlayer();
		// leeres resourcePackage
		ResourcePackage expectedPackage = new ResourcePackage(0,0,0,0,0);
		ResourcePackage currentPackage = model.getResources();
		assertEquals("ResoursePackages not equal", expectedPackage, currentPackage);
		
		// gefuelltes ResourcePackage
		expectedPackage = new ResourcePackage(1,2,3,4,5);
		currentPlayer.modifyResources(new ResourcePackage(1,2,3,4,5));
		currentPackage = model.getResources();
		assertEquals("ResoursePackages not equal", expectedPackage, currentPackage);
	}
	
	@Test
	public void testGetFieldNumber() {
		// Field without number (water)
		Field f = model.getField(new Point(0,0));
		int currentNumber = model.getFieldNumber(f);
		int expectedNumber = -1; 	// -1 for fields without numbers
		assertEquals("not the right number (field without number)", currentNumber, expectedNumber);
		
		// Field without number (desert)
		f = model.getField(new Point(2,1));
		currentNumber = model.getFieldNumber(f);
		expectedNumber = -1;
		assertEquals("not the right number (field without number)", currentNumber, expectedNumber);
		
		// Field with number (forest)
		f = model.getField(new Point(1,1));
		currentNumber = model.getFieldNumber(f);
		expectedNumber = 8;
		assertEquals("not the right number (field without number)", currentNumber, expectedNumber);
	}
	
	@Test
	public void testGetFieldResource() {
		// Field without resource (water)
		Field f = model.getField(new Point(0,0));
		Resource currentR = model.getFieldResource(f);
		Resource expectedR = null;
		assertEquals("not the equals resources", currentR, expectedR);
		
		// Field with resource (forest)
		f = model.getField(new Point(1,1));
		currentR = model.getFieldResource(f);
		expectedR = Resource.LUMBER;
		assertEquals("not the equals resources", currentR, expectedR);
	}
	
}



