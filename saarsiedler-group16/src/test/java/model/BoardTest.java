package model;

import help.TestUtil;
import java.util.Set;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
	Point p1, p2;
	Field f1, f2, f3;
	Board b;
	Location l1, l2, l3, l4;
	Intersection i1, i2, i3;
	Path path1, path2, path3, path4, path5, path6, path7, path8, path9, path10, path11;
	Set<Path> pathSet, pathSet2, pathSet3, pathSet4, pathSet5;
	Set<Path> pathSet6, pathSet7, pathSet8, pathSet9, pathSet10, pathSet11, pathSet12;
	Set<Path> pathSet13, pathSet14, pathSet15, pathSet16;

	@Before
	public void setUp() {
		Random r1 = new Random();
		r1.nextInt(10);
			
			//Create one field of type FOREST
			int x = r1.nextInt(10);
			int y = r1.nextInt(10);
			p1 = new Point(x, y);
			f1 = new Field(FieldType.FOREST, p1);
		   
			//Create second field of type FOREST
			int x3 = r1.nextInt(10);
			int y3 = r1.nextInt(10);
			p2 = new Point(x3, y3);
			f2 = new Field(FieldType.FOREST, p2);
			// Create TestBoard
			b = TestUtil.getTestBoard();
		
			Random r3 = new Random();
			r3.nextInt(5);
			//Create one intersection
			int x1 = r1.nextInt(10);
			int y1 = r1.nextInt(10);
			int o1 = r3.nextInt(5);
			l1 = new Location(x1, y1, o1);
			i1 = new Intersection(l1);
			//Create one path
			int x4 = r1.nextInt(10);
			int y4 = r1.nextInt(10);
			int o4 = r3.nextInt(5);
			l2 = new Location(x4, y4, o4);
			path1 = new Path(l2);
			//Create one intersection
			int x5 = r1.nextInt(10);
			int y5 = r1.nextInt(10);
			int o5 = r3.nextInt(5);
			l3 = new Location(x5, y5, o5);
			i2 = new Intersection(l3);
			// Create one path
			int x6 = r1.nextInt(10);
			int y6 = r1.nextInt(10);
			int o6 = r3.nextInt(5);
			l4 = new Location(x6, y6, o6);
			path2 = new Path(l4);

	}
	@Test
	public void testGetField() {
		assertEquals(f1, b.getField(p1));
		assertFalse(b.getField(p1).equals(b.getField(p2)));

	}
	@Test
	public void testGetIntersection() {
		assertEquals(i1, b.getIntersection(l1));
		assertFalse(b.getIntersection(l1).equals(b.getIntersection(l3)));
		assertEquals(b.getIntersection(new Location(0, 0, 2)), b.getIntersection(new Location(1, 0, 4)));
		assertEquals(b.getIntersection(new Location(1, 1, 0)), b.getIntersection(new Location(1, 0, 4)));
	}
	
	@Test
	public void testGetPath() {
		assertEquals(path1, b.getPath(l2));
		assertFalse(b.getPath(l2).equals(b.getPath(l4)));
		assertEquals(b.getPath(new Location(0, 2, 2)), b.getPath(new Location(1, 3, 5)));
	}
	@Test
	public void testGetPathsFromPaths() {
		//initialize path3;
		path3 = new Path(new Location(1, 1, 2));
		// initialize the 4 neighbours from path3
		path4 = new Path(new Location(1, 1, 1));
		path5 = new Path(new Location(1, 1, 3));
		path6 = new Path(new Location(1, 2, 0));
		path7 = new Path(new Location(1, 2, 4));
		//create both sets
		pathSet = b.getPathsFromPath(path3);
		pathSet2 = new TreeSet<Path>();
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
		pathSet13 = b.getPathsFromPath(new Path(new Location(0, 1, 3)));
		pathSet14 = new TreeSet<Path>();
		pathSet14.add(new Path(new Location(0, 1, 4)));
		pathSet14.add(new Path(new Location(0, 1, 2)));
		pathSet14.add(new Path(new Location(0, 2, 4)));
		//test if both sets have identical content
		assertTrue (pathSet14.containsAll(pathSet13));
		assertTrue (pathSet13.containsAll(pathSet14));
		
	}
	@Test
	public void testGetPathsFromPathsRand2(){
		//create both Sets
		pathSet15 = b.getPathsFromPath(new Path(new Location(2, 0, 0)));
		pathSet16 = new TreeSet<Path>();
		pathSet16.add(new Path(new Location(2, 0, 1)));
		pathSet16.add(new Path(new Location(2, 0, 5)));
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
		path11 = new Path(new Location(1, 2, 0));
		//create both Sets
		pathSet3 = b.getPathsFromIntersection(i3);
		pathSet4 = new TreeSet<Path>();
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
		pathSet11 = b.getPathsFromIntersection(new Intersection(new Location(1, 0, 1)));
		pathSet12 = new TreeSet<Path>();
		pathSet12.add(new Path(new Location(1, 0, 0)));
		pathSet12.add(new Path(new Location(1, 0, 5)));
		//test if both sets have identical content
		assertTrue(pathSet11.containsAll(pathSet12));
		assertTrue(pathSet12.containsAll(pathSet11));
	}
	
	@Test
	public void testGetPathsFromField() {
		f3 = new Field(FieldType.WATER, new Point(0, 0));

		// create both Sets
		pathSet5 = b.getPathsFromField(f3);
		pathSet6 = new TreeSet<Path>();
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
		pathSet8 = new TreeSet<Path>();
		pathSet8.add(new Path(new Location(0, 0, 0)));
		pathSet8.add(new Path(new Location(0, 0, 5)));
		// test if both sets have identical content
		assertTrue(pathSet7.containsAll(pathSet8));
		assertTrue(pathSet8.containsAll(pathSet7));
	}

	@Test
	 public void testgetPathsFromIntersectionRand2(){
		 //create both Sets
		 pathSet9 = b.getPathsFromIntersection(new Intersection(new Location(2, 3, 3)));
		 pathSet10 =  new TreeSet<Path>();
		 pathSet10.add(new Path(new Location(2, 3, 2)));
		 pathSet10.add(new Path(new Location(2, 3, 3)));
		//test if both sets have identical content
			assertTrue(pathSet9.containsAll(pathSet10));
			assertTrue(pathSet10.containsAll(pathSet9));
	 }
	
	@Test
	public void testGetFieldsFromField1(){
		Point pp=new Point(1,1);
		Field f=b.getField(pp);
		Set<Field> ff=b.getFieldsFromField(f);
		
		Set<Field> fff=new TreeSet<Field>();
		fff.add(b.getField(new Point(0,0)));
		fff.add(b.getField(new Point(1,0)));
		fff.add(b.getField(new Point(0,1)));
		fff.add(b.getField(new Point(2,1)));
		fff.add(b.getField(new Point(0,2)));
		fff.add(b.getField(new Point(1,2)));
		 
		assertTrue(ff.containsAll(fff));
		assertTrue(fff.containsAll(ff));

	}
	
	@Test
	public void testGetFieldsFromField2(){
		//same but on the border, only 3 neighbor fields
		Point pp=new Point(0,0);
		Field f=b.getField(pp);
		Set<Field> ff=b.getFieldsFromField(f);
		
		Set<Field> fff=new TreeSet<Field>();
		fff.add(b.getField(new Point(1,0)));
		fff.add(b.getField(new Point(0,1)));
		fff.add(b.getField(new Point(1,1)));
		
		assertTrue(ff.containsAll(fff));
		assertTrue(fff.containsAll(ff));
	}
	
	@Test
	public void testGetFieldsFromIntersection(){
		Location l=new Location(0,0,2);
		Set<Field> f1=b.getFieldsFromIntersection(b.getIntersection(l));
		Set<Field> f2=new TreeSet<Field>();
		f2.add(b.getField(new Point(0,0)));
		f2.add(b.getField(new Point(1,0)));
		f2.add(b.getField(new Point(1,1)));
		
		assertTrue(f1.containsAll(f2));
		assertTrue(f2.containsAll(f1));
	}
	
	@Test
	public void testGetFieldsFromPath(){
		Location l=new Location(0,0,2);
		Set<Field> f1=b.getFieldsFromPath(b.getPath(l));
		Set<Field> f2=new TreeSet<Field>();
		f2.add(b.getField(new Point(0,0)));
		f2.add(b.getField(new Point(1,1)));
		
		assertTrue(f1.containsAll(f2));
		assertTrue(f2.containsAll(f1));
	}
	
	@Test
	public void testGetIntersectionsFromField(){
		Set<Intersection> f1=b.getIntersectionsFromField(b.getField(new Point(1,1)));
		
		Set<Intersection> f2=new TreeSet<Intersection>();
		f2.add(b.getIntersection(new Location(0,0,2)));
		f2.add(b.getIntersection(new Location(1,0,3)));
		f2.add(b.getIntersection(new Location(2,1,4)));
		f2.add(b.getIntersection(new Location(1,2,5)));
		f2.add(b.getIntersection(new Location(0,2,0)));
		f2.add(b.getIntersection(new Location(0,1,1)));
		
		assertTrue(f1.containsAll(f2));
		assertTrue(f2.containsAll(f1));

	}
	
	@Test
	public void testGetIntersectionsFromIntersection(){
		Set<Intersection> i1=b.getIntersectionsFromIntersection(b.getIntersection(new Location(0,0,2)));
		
		Set<Intersection> i2=new TreeSet<Intersection>();
		i2.add(b.getIntersection(new Location(0,0,1)));
		i2.add(b.getIntersection(new Location(1,0,3)));
		i2.add(b.getIntersection(new Location(1,1,5)));
		
		assertTrue(i1.containsAll(i2));
		assertTrue(i2.containsAll(i1));

	}
	
	@Test
	public void testGetIntersectionsFromPath(){
		Set<Intersection> i1=b.getIntersectionsFromPath(b.getPath(new Location(0,0,2)));
		
		Set<Intersection> i2=new TreeSet<Intersection>();
		i2.add(b.getIntersection(new Location(0,0,2)));
		i2.add(b.getIntersection(new Location(0,0,3)));
		
		assertTrue(i1.containsAll(i2));
		assertTrue(i2.containsAll(i1));

	}
	

	
	@Test	
	public void testSetHarbors() {

		// check if harbors are being properly initialized. The fields are for
		// clarity
		Field f1 = new Field(FieldType.WATER, new Point(0, 1));
		Field f2 = new Field(FieldType.FIELD, new Point(1, 1));
		Path p = new Path(new Location(0, 1, 1));
		Path p2 = new Path(new Location(1, 1, 4));
		b.setHarbor(new Location(0, 1, 1), HarborType.GRAIN_HARBOR);
		assertEquals(HarborType.GRAIN_HARBOR, p.getHarborType());
		assertEquals(p2.getHarborType(), p.getHarborType());

	}

}
