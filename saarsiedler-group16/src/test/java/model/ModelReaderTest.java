package model;

import static org.junit.Assert.*;
import help.TestUtil;

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
		model.newRound(8); // in Runde 1
		// noch kein Settlement an einem der zwei Harbors
		Player currentPlayer = model.getCurrentPlayer();
		Set<HarborType> currentSet = model.getHarborTypes(currentPlayer);
		assertTrue("es gibt eig. keine HarborTypes", currentSet.size() == 0);
		// Village am LumberHarbor (Spezialhafen)
		currentPlayer.modifyResources(new ResourcePackage(10000,10000,10000,10000,10000));
		model.buildSettlement(new Location(2,1,0), BuildingType.Village);
		Set<HarborType> expectedSet = new TreeSet<HarborType>();
		expectedSet.add(HarborType.LUMBER_HARBOR);
		currentSet = model.getHarborTypes(currentPlayer);
		assertTrue("es gibt einen HarborTypes", currentSet.size() == 1);
		assertEquals("nicht den richtigen Harbor hinzugefuegt", currentSet, expectedSet);
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
		
		// Field without number (dessert)
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
	
	@Test
	public void testGetFieldsFromField() {
		// Case 6 neighbor fields
		Set<Field> f1=model.getFieldsFromField(model.getField(new Point(1,1)));
		Set<Field> f2=new TreeSet<Field>();
		f2.add(model.getField(new Point(0,0)));
		f2.add(model.getField(new Point(1,0)));
		f2.add(model.getField(new Point(0,1)));
		f2.add(model.getField(new Point(2,1)));
		f2.add(model.getField(new Point(0,2)));
		f2.add(model.getField(new Point(1,2)));
		assertTrue(f1.containsAll(f2));
		assertTrue(f2.containsAll(f1));
		
		// Case 3 neighbor fields
		f1=model.getFieldsFromField(model.getField(new Point(0,0)));
		f2=new TreeSet<Field>();
		f2.add(model.getField(new Point(1,1)));
		f2.add(model.getField(new Point(1,0)));
		f2.add(model.getField(new Point(0,1)));
		assertTrue(f1.containsAll(f2));
		assertTrue(f2.containsAll(f1));
		// noch mehr Faelle; es koennen auch nur 3,4,5 Felder herum liegen
	}
	
	@Test
	public void testGetFieldsFromIntersection() {
		Set<Field> f1=model.getFieldsFromIntersection(model.getIntersection(new Location(0,0,2)));
		Set<Field> f2=new TreeSet<Field>();
		f2.add(model.getField(new Point(0,0)));
		f2.add(model.getField(new Point(1,0)));
		f2.add(model.getField(new Point(1,1)));
		assertTrue(f1.containsAll(f2));
		assertTrue(f2.containsAll(f1));
		
		// noch mehr Faelle
	}
	
	@Test
	public void testGetFieldsFromPath() {
		Set<Field> f1=model.getFieldsFromPath(model.getPath(new Location(0,0,2)));
		Set<Field> f2=new TreeSet<Field>();
		f2.add(model.getField(new Point(0,0)));
		f2.add(model.getField(new Point(1,1)));
		assertTrue(f1.containsAll(f2));
		assertTrue(f2.containsAll(f1));	// darf glaube ich nicht sein, koennte schon wahr sein, obwohl es nicht ausreicht
		
		// Fall mit nur einem Field
	}
	
	@Test
	public void testGetIntersectionsFromField() {
		Set<Intersection> i1=model.getIntersectionsFromField(model.getField(new Point(1,1)));
		Set<Intersection> i2=new TreeSet<Intersection>();
		i2.add(model.getIntersection(new Location(0,0,0)));
		i2.add(model.getIntersection(new Location(0,0,1)));
		i2.add(model.getIntersection(new Location(0,0,2)));
		i2.add(model.getIntersection(new Location(0,0,3)));
		i2.add(model.getIntersection(new Location(0,0,4)));
		i2.add(model.getIntersection(new Location(0,0,5)));
		assertTrue(i1.containsAll(i2));
		assertTrue(i2.containsAll(i1));	// darf glaube ich nicht sein
	}
	
	@Test
	public void testGetIntersectionsFromIntersection() {
		Set<Intersection> i1=model.getIntersectionsFromIntersection(model.getIntersection(new Location(0,0,2)));
		Set<Intersection> i2=new TreeSet<Intersection>();
		i2.add(model.getIntersection(new Location(0,0,1)));
		i2.add(model.getIntersection(new Location(0,0,3)));
		i2.add(model.getIntersection(new Location(1,0,3)));
		assertTrue(i1.containsAll(i2));
		assertTrue(i2.containsAll(i1));
	}
	
	@Test
	public void testGetIntersectionsFromPath() {
		Set<Intersection> i1=model.getIntersectionsFromPath(model.getPath(new Location(0,0,2)));
		Set<Intersection> i2=new TreeSet<Intersection>();
		i2.add(model.getIntersection(new Location(0,0,2)));
		i2.add(model.getIntersection(new Location(0,0,3)));
		assertTrue(i1.containsAll(i2));
		assertTrue(i2.containsAll(i1));
	}
	
	@Test
	public void testGetPathsFromField() {
		Set<Path> p1=model.getPathsFromField(model.getField(new Point(1,1)));
		Set<Path> p2=new TreeSet<Path>();
		p2.add(model.getPath(new Location(1,1,0)));
		p2.add(model.getPath(new Location(1,1,1)));
		p2.add(model.getPath(new Location(1,1,2)));
		p2.add(model.getPath(new Location(1,1,3)));
		p2.add(model.getPath(new Location(1,1,4)));
		p2.add(model.getPath(new Location(1,1,5)));
		assertTrue(p1.containsAll(p2));
		assertTrue(p2.containsAll(p1));
	}
	
	@Test
	public void testGetPathsFromIntersection() {
		Set<Path> p1=model.getPathsFromIntersection(model.getIntersection(new Location(0,0,2)));
		Set<Path> p2=new TreeSet<Path>();
		p2.add(model.getPath(new Location(0,0,1)));
		p2.add(model.getPath(new Location(0,0,3)));
		p2.add(model.getPath(new Location(1,0,3)));
		assertTrue(p1.containsAll(p2));
		assertTrue(p2.containsAll(p1));
		
		// weitere Faelle
	}
	
	@Test
	public void testGetPathsFromPath() {
		Set<Path> p1=model.getPathsFromPath(model.getPath(new Location(0,0,2)));
		Set<Path> p2=new TreeSet<Path>();
		p2.add(model.getPath(new Location(0,0,1)));
		p2.add(model.getPath(new Location(1,1,0)));
		p2.add(model.getPath(new Location(0,1,0)));
		p2.add(model.getPath(new Location(1,1,4)));
		assertTrue(p1.containsAll(p2));
		assertTrue(p2.containsAll(p1));
		
		// Randfaelle, nur 2 weitere Pfade
	}
	
	@Test
	public void testGetPath() {
		//testet glaube ich nicht getPath sondern eher die ob die welt richitg erstellt wurde
		assertTrue(model.getPath(new Location(0,0,2)).equals(model.getPath(new Location(1,1,5))));
	}
	
	@Test
	public void testGetField() {
		// muss noch gemacht werden
	}
	
	@Test
	public void testGetIntersection() {
		// nicht sicher
		assertTrue(model.getIntersection(new Location(0,0,2))==model.getIntersection(new Location(1,0,4)));
		assertTrue(model.getIntersection(new Location(0,0,2))==model.getIntersection(new Location(1,1,0)));
	}

}
