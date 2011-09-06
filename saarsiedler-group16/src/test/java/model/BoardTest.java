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

import de.unisaarland.cs.sopra.common.model.Board;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Point;

public class BoardTest {
	Point p1, p2;
	Field f1, f2;
	Board b;
	Location l1, l2, l3, l4, l5, l6, l7, l8, l9;
	Intersection i1, i2, i3;
	Path path1, path2, path3, path4, path5, path6, path7, path8, path9, path10, path11;
	Set<Path> pathSet, pathSet2, pathSet3, pathSet4;

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

	public void testGetField() {
		assertEquals(f1, b.getField(p1));
		assertFalse(b.getField(p1).equals(b.getField(p2)));

	}

	public void testGetIntersection() {
		assertEquals(i1, b.getIntersection(l1));
		assertFalse(b.getIntersection(l1).equals(b.getIntersection(l3)));
	}

	public void testGetPath() {
		assertEquals(path1, b.getPath(l2));
		assertFalse(b.getPath(l2).equals(b.getPath(l4)));
	}

	public void testGetPathsFromPaths() {
		//initialize path3;
		l5 = new Location(1, 1, 2);
		path3 = new Path(l5);
		// initialize the 4 neighbours from path3
		l6 = new Location(1, 1, 1);
		path4 = new Path(l6);
		l7 = new Location(1, 1, 3);
		path5 = new Path(l7);
		l8 = new Location(1, 2, 0);
		path6 = new Path(l8);
		l9 = new Location(1, 2, 4);
		path7 = new Path(l9);
		
		pathSet = b.getPathsFromPath(path3);
		pathSet2 = new TreeSet<Path>();
		pathSet2.add(path4);
		pathSet2.add(path5);
		pathSet2.add(path6);
		pathSet2.add(path7);
		
		assertTrue (pathSet.containsAll(pathSet2));
		assertTrue (pathSet2.containsAll(pathSet));
		
		

	}
	
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
		//test if the content of both sets is the same
		assertTrue(pathSet3.containsAll(pathSet4));
		assertTrue(pathSet4.containsAll(pathSet3));
	}
	
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
	
	public void testGetFieldsFromPath(){
		Location l=new Location(0,0,2);
		Set<Field> f1=b.getFieldsFromPath(b.getPath(l));
		Set<Field> f2=new TreeSet<Field>();
		f2.add(b.getField(new Point(0,0)));
		f2.add(b.getField(new Point(1,1)));
		
		assertTrue(f1.containsAll(f2));
		assertTrue(f2.containsAll(f1));
	}
	
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
	
	public void testGetIntersectionsFromIntersection(){
		Set<Intersection> i1=b.getIntersectionsFromIntersection(b.getIntersection(new Location(0,0,2)));
		
		Set<Intersection> i2=new TreeSet<Intersection>();
		i2.add(b.getIntersection(new Location(0,0,1)));
		i2.add(b.getIntersection(new Location(1,0,3)));
		i2.add(b.getIntersection(new Location(1,1,5)));
		
		assertTrue(i1.containsAll(i2));
		assertTrue(i2.containsAll(i1));

	}
	
	public void testGetIntersectionsFromPath(){
		Set<Intersection> i1=b.getIntersectionsFromPath(b.getPath(new Location(0,0,2)));
		
		Set<Intersection> i2=new TreeSet<Intersection>();
		i2.add(b.getIntersection(new Location(0,0,2)));
		i2.add(b.getIntersection(new Location(0,0,3)));
		
		assertTrue(i1.containsAll(i2));
		assertTrue(i2.containsAll(i1));

	}
}
