package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
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
		model.getPath(new Location(2,1,0)).createStreet(currentPlayer);
		model.buildSettlement(new Location(2, 1, 1), BuildingType.Village);
		Set<HarborType> expectedSet = new HashSet<HarborType>();
		expectedSet.add(HarborType.LUMBER_HARBOR);
		currentSet = model.getHarborTypes(currentPlayer);
		assertTrue("Contains one element : LUMBER_HARBOR TYPE", currentSet.size() == 1);
		//test if both sets have identical content
		System.out.println("curSet:" + currentSet );
		System.out.println("expSet" + expectedSet);
		assertTrue(currentSet.containsAll(expectedSet));
		assertTrue(expectedSet.containsAll(currentSet));
		
	}
	
	
	/**
	 * Harbor with buccaneer (Seeraeuber)
	 * TODO Warum kommt nach dem assertTrue noch Code jedoch kein assert mehr?
	 */
	@Test
	public void testGetHarborTypes1() {
		//model.newRound(8); // in Runde 1 // muss runde 0 sein wegen model.buildSettlement später
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
		// leeres resourcePackage
		ResourcePackage expectedPackage = new ResourcePackage(0,0,0,0,0);
		ResourcePackage currentPackage = model.getResources();
		assertEquals("ResoursePackages not equal", expectedPackage, currentPackage);
		
		// gefuelltes ResourcePackage
		expectedPackage = new ResourcePackage(1,2,3,4,5);
		model.getMe().modifyResources(new ResourcePackage(1,2,3,4,5));
		currentPackage = model.getResources();
		assertEquals("ResoursePackages not equal", expectedPackage, currentPackage);
	}
	
	@Test
	public void testGetFieldNumber() {
		// Field without number (water)
		Field f = model.getField(new Point(0,0));
		int currentNumber = f.getNumber();
		int expectedNumber = -1; 	// -1 for fields without numbers
		assertEquals("not the right number (field without number)", currentNumber, expectedNumber);
		
		// Field without number (desert)
		f = model.getField(new Point(1,2));
		currentNumber = f.getNumber();
		expectedNumber = -1;
		assertEquals("not the right number (field without number)", currentNumber, expectedNumber);
		
		// Field with number (forest)
		f = model.getField(new Point(1,1));
		currentNumber = f.getNumber();
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



