package model;

import help.TestUtil;

import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;

import de.unisaarland.cs.sopra.common.model.Board;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Point;

public class BoardTest {
	Point p1,p2;
	Field f1,f2;
	Board b;
	Location l1,l2,l3,l4;
	Intersection i1,i2;
	Path path1,path2;
	
	@Before
	public void setUp(){
		Random r1 = new Random();
		r1.nextInt(10);
		int x=r1.nextInt(10);
		int y=r1.nextInt(10);
		p1 = new Point(x, y);
		f1 = new Field(FieldType.FOREST, p1);
		int x3= r1.nextInt(10);
		int y3 = r1.nextInt(10);
		p2 = new Point (x3,y3);
		f2 = new Field (FieldType.FOREST, p2);
		b = TestUtil.getTestBoard();
		Random r2 = new Random();
		r2.nextInt(10);
		Random r3 = new Random();
		r3.nextInt(5);
		int x1 = r2.nextInt(10);
		int y1 = r2.nextInt(10);
		int o1 = r3.nextInt(5);
		l1 = new Location(x1, y1, o1);
		i1 = new Intersection(l1);
		int x4 = r2.nextInt(10);
		int y4 = r2.nextInt(10);
		int o4 = r3.nextInt(5);
		l2 = new Location(x4,y4, o4);
		path1 = new Path(l2);
		int x5 = r2.nextInt(10);
		int y5 = r2.nextInt(10);
		int o5 = r3.nextInt(5);
		l3 = new Location(x5, y5, o5);
		i2 = new Intersection(l3);
		int x6 = r2.nextInt(10);
		int y6 = r2.nextInt(10);
		int o6 = r3.nextInt(5);
		l4 = new Location(x6, y6, o6);
		path2 = new Path(l4);
	}
	
	public void testGetField(){
		assertEquals(f1, b.getField(p1));
		assertFalse(b.getField(p1).equals(b.getField(p2)));
		
	}
	public void testGetIntersection(){
		assertEquals(i1, b.getIntersection(l1));
		assertFalse(b.getIntersection(l1).equals(b.getIntersection(l3)));
	}
	
	public void testGetPath(){
		assertEquals(path1, b.getPath(l2));
		assertFalse(b.getPath(l2).equals(b.getPath(l4)));
	}

}
