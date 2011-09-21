package model;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.Location;

public class PointAndLocationTest {
	Point pA;
	Point pB;
	Point p2;
	Location lA;
	Location lB;
	Location l2;
	
	@Before
	public void setUp(){
		 pA = new Point(0, 0);
		 pB = new Point(0, 0);
		 p2 = new Point(0, 1);

		 lA = new Location(0, 0, 0);
		 lB = new Location(0, 0, 0);
		 l2 = new Location(0, 1, 0);
	}
	@Test
	public void testEquals(){
		assertEquals(pA,pA);
		assertEquals(pA,pB);
		assertNotSame(pA,p2);
		
		assertEquals(lA,lA);
		assertEquals(lA,lB);
		assertNotSame(lA,l2);
	}
	@Test
	public void testHashCode(){
		assertEquals(pA.hashCode(),pA.hashCode());
		assertEquals(pA.hashCode(),pB.hashCode());
//		assertNotSame(pA.hashCode(),p2.hashCode());	 siehe:   http://tech-read.com/2009/02/12/use-of-hashcode-and-equals/
		
		assertEquals(lA,lA);
		assertEquals(lA,lB);
//		assertNotSame(lA,l2);  
	}
}
