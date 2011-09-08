package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.Board;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Point;

public class BoardTest {
   private	 Field f3;
   private	Board b;
   private	Intersection  i3;
   private	Path  path3, path4, path5, path6, path7, path9, path10, path11;
   private	Set<Path> pathSet, pathSet2, pathSet3, pathSet4, pathSet5;
   private	Set<Path> pathSet6, pathSet7, pathSet8, pathSet11, pathSet12;
   private	Set<Path> pathSet13, pathSet14, pathSet15, pathSet16;

	@Before
	public void setUp() {
		Random r1 = new Random();
		r1.nextInt(10);

		b = TestUtil.getTestBoard();

			
	}
	@Test
	public void testGetField() {

		// Create field of type WATER
		Field f1 = new Field(FieldType.WATER, new Point(1, 0));
		// Create TestBoard
		assertEquals(f1, b.getField(new Point(1,0)));
		assertFalse(b.getField(new Point(1,0)).equals(b.getField(new Point(1,1))));

	}
	@Test
	public void testGetIntersection() {
		assertEquals(new Intersection(new Location(1, 0, 3)), b.getIntersection(new Location(1, 0, 3)));
		assertEquals(b.getIntersection(new Location(1, 0, 3)), b.getIntersection(new Location(2, 1, 4)));
		assertEquals(b.getIntersection(new Location(1, 0, 3)), b.getIntersection(new Location(3, 1, 0)));
	}
	
	@Test
	public void testGetIntersectionRand(){
		assertEquals(new Intersection(new Location(3, 2, 4)), b.getIntersection(new Location(3, 2, 4)));
	}
	
	@Test
	public void testGetPath() {
		assertEquals(new Path(new Location(1, 0, 4)), b.getPath(new Location(1, 0, 4)));
		assertEquals(b.getPath(new Location(1, 0, 4)), b.getPath(new Location(0, 1, 2)));
	}
	
	@Test
	public void testGetPath2(){
		assertEquals(new Path(new Location(0, 2, 5)), b.getPath(new Location(0, 2, 5)));
		assertEquals(false, b.getPath(new Location(0, 2, 5)).equals(b.getPath(new Location(2, 0, 5))));
	}
	
	@Test
	public void testGetPathsFromPaths() {
		//initialize path3;
		path3 = new Path(new Location(1, 1, 2));
		// initialize the 4 neighbours from path3
		path4 = new Path(new Location(1, 1, 1));
		path5 = new Path(new Location(1, 1, 3));
		path6 = new Path(new Location(2, 1, 0));
		path7 = new Path(new Location(2, 1, 4));
		//create both sets
		pathSet = b.getPathsFromPath(path3);
		pathSet2 = new HashSet<Path>();
		pathSet2.add(path4);
		pathSet2.add(path5);
		pathSet2.add(path6);
		pathSet2.add(path7);
		//test if both sets have identical content
		assertTrue (pathSet.containsAll(pathSet2));
		assertTrue (pathSet2.containsAll(pathSet));
		
		

	}
	@Test
	public void testGetPathsFromPathsRand1(){
		//create both Sets
		pathSet13 = b.getPathsFromPath(new Path(new Location(1, 0, 3)));
		pathSet14 = new HashSet<Path>();
		pathSet14.add(new Path(new Location(1, 0, 4)));
		pathSet14.add(new Path(new Location(1, 0, 2)));
		pathSet14.add(new Path(new Location(2, 0, 4)));
		//test if both sets have identical content
		assertTrue (pathSet14.containsAll(pathSet13));
		assertTrue (pathSet13.containsAll(pathSet14));
		
	}
	@Test
	public void testGetPathsFromPathsRand2(){
		//create both Sets
		pathSet15 = b.getPathsFromPath(new Path(new Location(0, 2, 0)));
		pathSet16 = new HashSet<Path>();
		pathSet16.add(new Path(new Location(0, 2, 1)));
		pathSet16.add(new Path(new Location(0, 2, 5)));
		//test if both sets have identical content
		assertTrue (pathSet15.containsAll(pathSet16));
		assertTrue (pathSet16.containsAll(pathSet15));
		
	}
	
	@Test
	public void testGetPathsFromIntersection() {
		
		//create an intersection
		i3 = new Intersection(new Location (0, 0, 2));
		
		//create the neighbours of the intersection
		path9 = new Path(new Location(1, 1, 1));
		path10 = new Path(new Location(1, 1, 2));
		path11 = new Path(new Location(2, 1, 0));
		//create both Sets
		pathSet3 = b.getPathsFromIntersection(i3);
		pathSet4 = new HashSet<Path>();
		pathSet4.add(path9);
		pathSet4.add(path10);
		pathSet4.add(path11);
		//test if both sets have identical content
		assertTrue(pathSet3.containsAll(pathSet4));
		assertTrue(pathSet4.containsAll(pathSet3));
	}
	
	@Test
	public void testGetPathsFromIntersectionRand(){
		//create both Sets
		pathSet11 = b.getPathsFromIntersection(new Intersection(new Location(0, 1, 1)));
		pathSet12 = new HashSet<Path>();
		pathSet12.add(new Path(new Location(0, 1, 0)));
		pathSet12.add(new Path(new Location(0, 1, 5)));
		//test if both sets have identical content
		assertTrue(pathSet11.containsAll(pathSet12));
		assertTrue(pathSet12.containsAll(pathSet11));
	}
	
	@Test
	public void testGetPathsFromField() {
		f3 = new Field(FieldType.WATER, new Point(0, 0));

		// create both Sets
		pathSet5 = b.getPathsFromField(f3);
		pathSet6 = new HashSet<Path>();
		pathSet6.add(new Path(new Location(0, 0, 0)));
		pathSet6.add(new Path(new Location(0, 0, 1)));
		pathSet6.add(new Path(new Location(0, 0, 2)));
		pathSet6.add(new Path(new Location(0, 0, 3)));
		pathSet6.add(new Path(new Location(0, 0, 4)));
		pathSet6.add(new Path(new Location(0, 0, 5)));
		// test if both sets have identical content
		assertTrue(pathSet5.containsAll(pathSet6));
		assertTrue(pathSet6.containsAll(pathSet5));
	}
	 
	@Test
	public void testgetPathsFromIntersectionRand1() {
		// create both Sets
		pathSet7 = b.getPathsFromIntersection(new Intersection(new Location(0, 0, 0)));
		pathSet8 = new HashSet<Path>();
		pathSet8.add(new Path(new Location(0, 0, 0)));
		pathSet8.add(new Path(new Location(0, 0, 5)));
		// test if both sets have identical content
		assertTrue(pathSet7.containsAll(pathSet8));
		assertTrue(pathSet8.containsAll(pathSet7));
	}

	@Test
	 public void testgetPathsFromIntersectionRand2(){
		 //create both Sets
		 Set<Path> cSet = b.getPathsFromIntersection(new Intersection(new Location(3, 2, 3)));
		 Set<Path> eSet =  new HashSet<Path>();
		 eSet.add(new Path(new Location(3, 2, 2)));
		 eSet.add(new Path(new Location(3, 2, 3)));
		//test if both sets have identical content
			assertTrue(eSet.containsAll(cSet));
			assertTrue(cSet.containsAll(eSet));
	 }
	
	@Test
	public void testGetFieldsFromField1(){
		Point pp=new Point(1,1);
		Field f=b.getField(pp);
		Set<Field> ff=b.getFieldsFromField(f);
		
		Set<Field> fff=new HashSet<Field>();
		fff.add(b.getField(new Point(0,0)));
		fff.add(b.getField(new Point(0,1)));
		fff.add(b.getField(new Point(1,0)));
		fff.add(b.getField(new Point(1,2)));
		fff.add(b.getField(new Point(2,0)));
		fff.add(b.getField(new Point(2,1)));
		 
		assertTrue(ff.containsAll(fff));
		assertTrue(fff.containsAll(ff));

	}
	
	@Test
	public void testGetFieldsFromField2(){
		//same but on the border, only 3 neighbor fields
		Point pp=new Point(0,0);
		Field f=b.getField(pp);
		Set<Field> ff=b.getFieldsFromField(f);
		
		Set<Field> fff=new HashSet<Field>();
		fff.add(b.getField(new Point(0,1)));
		fff.add(b.getField(new Point(1,0)));
		fff.add(b.getField(new Point(1,1)));
		
		assertTrue(ff.containsAll(fff));
		assertTrue(fff.containsAll(ff));
	}
	
	@Test
	public void testGetFieldsFromField3(){
		// this time 2 neighbors
		Field f4 = b.getField(new Point(3,0));
		//create both Sets
		Set<Field> expectedSet = new HashSet<Field>();
		expectedSet.add(b.getField(new Point(2,0)));
		expectedSet.add(b.getField(new Point(3,1)));
		Set<Field> currentSet = b.getFieldsFromField(f4);
		//test if both sets have identical content
		assertTrue(currentSet.containsAll(expectedSet));
		assertTrue(expectedSet.containsAll(currentSet));
		
	}
	
	@Test
	public void TestGetFieldsFromField4(){
		//this time 4 neighbors
		Field f5 = b.getField(new Point(3,1));
		//create both Sets
		Set<Field> eSet = new HashSet<Field>();
		eSet.add(b.getField(new Point(3,2)));
		eSet.add(b.getField(new Point(3,0)));
		eSet.add(b.getField(new Point(2,1)));
		eSet.add(b.getField(new Point(2,0)));
		Set<Field> cSet = b.getFieldsFromField(f5);
		//test if both sets have identical content
		assertTrue(eSet.containsAll(cSet));
		assertTrue(cSet.containsAll(eSet));
	}
	
	@Test
	public void TestGetFieldsFromField5(){
		//this time 5 neighbors
		Field f6 = b.getField(new Point(1,2));
		//create both Sets
		Set<Field> eSet = new HashSet<Field>();
		eSet.add(b.getField(new Point(1,2)));
		eSet.add(b.getField(new Point(2,2)));
		eSet.add(b.getField(new Point(3,2)));
		eSet.add(b.getField(new Point(3,1)));
		eSet.add(b.getField(new Point(2,0)));
		Set<Field> cSet =b.getFieldsFromField(f6);
		//test if both sets have identical content
		assertTrue(eSet.containsAll(cSet));
		assertTrue(cSet.containsAll(eSet));
		
	}
	
	@Test
	public void testGetFieldsFromIntersection(){
		Location l=new Location(0,0,2);
		Set<Field> f1=b.getFieldsFromIntersection(b.getIntersection(l));
		Set<Field> f2=new HashSet<Field>();
		f2.add(b.getField(new Point(0,0)));
		f2.add(b.getField(new Point(0,1)));
		f2.add(b.getField(new Point(1,1)));
		
		assertTrue(f1.containsAll(f2));
		assertTrue(f2.containsAll(f1));
	}
	
	@Test
	public void testGetFieldsFromIntersection2(){
		//create both Sets 
		Set<Field> curSet = b.getFieldsFromIntersection(b.getIntersection(new Location(0, 0, 3)));
		Set<Field> expSet = new HashSet<Field>();
		expSet.add(b.getField(new Point(0,0)));
		expSet.add(b.getField(new Point(1,0)));
		expSet.add(b.getField(new Point(1,1)));

		//test if both sets have identical content
		assertTrue(expSet.containsAll(curSet));
		assertTrue(curSet.containsAll(expSet));
	}
	
	@Test
	public void testGetFieldsFromPath(){
		Location l=new Location(0,0,2);
		Set<Field> f1=b.getFieldsFromPath(b.getPath(l));
		Set<Field> f2=new HashSet<Field>();
		f2.add(b.getField(new Point(0,0)));
		f2.add(b.getField(new Point(1,1)));
		
		assertTrue(f1.containsAll(f2));
		assertTrue(f2.containsAll(f1));
	}
	
	public void testGetFieldsFromPath2(){
		//one neighbor field
		Field fi = b.getField(new Point(3, 2));
		Set<Field> cSet = b.getFieldsFromPath(new Path(new Location(3, 2, 3)));
		Set<Field> eSet = new HashSet<Field>();
		eSet.add(fi);
		
		assertTrue(cSet.containsAll(eSet));
		assertTrue(eSet.containsAll(cSet));
	}
	
	@Test
	public void testGetIntersectionsFromField(){
		Set<Intersection> f1=b.getIntersectionsFromField(b.getField(new Point(1,1)));
		
		Set<Intersection> f2=new HashSet<Intersection>();
		f2.add(b.getIntersection(new Location(0,0,2)));
		f2.add(b.getIntersection(new Location(0,1,3)));
		f2.add(b.getIntersection(new Location(1,2,4)));
		f2.add(b.getIntersection(new Location(2,1,5)));
		f2.add(b.getIntersection(new Location(2,0,0)));
		f2.add(b.getIntersection(new Location(1,0,1)));
		
		assertTrue(f1.containsAll(f2));
		assertTrue(f2.containsAll(f1));

	}
	
	@Test
	public void testGetIntersectionsFromIntersection(){
		Set<Intersection> i1=b.getIntersectionsFromIntersection(b.getIntersection(new Location(0,0,2)));
		
		Set<Intersection> i2=new HashSet<Intersection>();
		i2.add(b.getIntersection(new Location(0,0,1)));
		i2.add(b.getIntersection(new Location(0,1,3)));
		i2.add(b.getIntersection(new Location(1,1,5)));
		
		assertTrue(i1.containsAll(i2));
		assertTrue(i2.containsAll(i1));

	}
	
	@Test
	public void testGetIntersectionsFromPath(){
		Set<Intersection> i1=b.getIntersectionsFromPath(b.getPath(new Location(0,0,2)));
		
		Set<Intersection> i2=new HashSet<Intersection>();
		i2.add(b.getIntersection(new Location(0,0,2)));
		i2.add(b.getIntersection(new Location(0,0,3)));
		
		assertTrue(i1.containsAll(i2));
		assertTrue(i2.containsAll(i1));

	}
	

	
	@Test	
	public void testSetHarbors() {

		// check if harbors are being properly initialized. 

		Path p = new Path(new Location(1, 0, 1));
		Path p2 = new Path(new Location(1, 1, 4));
		b.getPath(new Location(1, 0, 1)).setHarborType(HarborType.GENERAL_HARBOR);
		assertEquals(HarborType.GENERAL_HARBOR, p.getHarborType());
		assertEquals(p2.getHarborType(), p.getHarborType());

	}

}
