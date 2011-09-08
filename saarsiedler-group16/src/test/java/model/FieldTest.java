package model;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.Point;
import de.unisaarland.cs.sopra.common.model.Resource;

public class FieldTest {
	Field f1;
	Field f2;
	Field f3;
	Field f4;
	Field f5;
	Field f6;
	Field f7;
	Field f8;
	Point p;
	@Before
	public void setUp(){
		p = new Point (0, 0);
		f1= new Field(FieldType.DESERT, p);
		f2= new Field(FieldType.FIELDS, p);
		f3= new Field(FieldType.FOREST, p);
		f4= new Field(FieldType.HILLS, p);
		f5= new Field(FieldType.MOUNTAINS, p);
		f6= new Field(FieldType.PASTURE, p);
		f7= new Field(FieldType.WATER, p);
		
		f8= new Field(FieldType.FIELDS, p);
		
		try {
			f1.setNumber(3); // catch exception IAE, Dessert/Water should not have numbers
			fail();
		}catch(IllegalArgumentException e){}
		try {
			f7.setNumber(3); // catch exception IAE, Dessert/Water should not have numbers
			fail();
		}catch(IllegalArgumentException e){}
		
		f2.setNumber(3);
		f3.setNumber(3);
		f4.setNumber(3);
		f5.setNumber(3);
		f6.setNumber(3);
		
		
	}
	@Test
	public void testResource(){
		
		assertEquals(null, f1.getResource(3));
		assertEquals(Resource.GRAIN, f2.getResource(3));
		assertEquals(Resource.LUMBER, f3.getResource(3));
		assertEquals(Resource.BRICK, f4.getResource(3));
		assertEquals(Resource.ORE, f5.getResource(3));
		assertEquals(Resource.WOOL, f6.getResource(3));
		assertEquals(null, f7.getResource(3));
		
		assertNotSame(Resource.GRAIN, f2.getResource(2));
		assertNotSame(Resource.LUMBER, f3.getResource(2));
		assertNotSame(Resource.BRICK, f4.getResource(2));
		assertNotSame(Resource.ORE, f5.getResource(2));
		assertNotSame(Resource.WOOL, f6.getResource(2));
	
		
		f2.setRobber(true);
		f3.setRobber(true);
		f4.setRobber(true);
		f5.setRobber(true);
		f6.setRobber(true);
		
		assertEquals(null, f2.getResource(3));
		assertEquals(null, f3.getResource(3));
		assertEquals(null, f4.getResource(3));
		assertEquals(null, f5.getResource(3));
		assertEquals(null, f6.getResource(3));
	}
	@Test
	public void testRobber(){
		assertFalse(f1.hasRobber());
		f1.setRobber(true);
		assertTrue(f1.hasRobber());
		f1.setRobber(false);
		assertFalse(f1.hasRobber());
	}
	@Test
	public void testNumber(){
		assertEquals(-1, f8.getNumber());
		f8.setNumber(12);
		assertEquals(12, f8.getNumber());
	}
	
	@Test
	public void testEquals(){ 	
		assertNotSame(f2, f1);
		assertEquals(f1, f1);
		
		//Fields are equal to one another, 
		//when all of their attributes are equal (by Valentin)
		Field f1WithSameAttributes= new Field(FieldType.DESERT, p);	
		assertEquals(f1, f1WithSameAttributes);
		
		f1WithSameAttributes.setRobber(true);
		assertNotSame(f1, f1WithSameAttributes);
	}
	@Test
	public void testHashCode(){ 	
		assertNotSame(f2.hashCode(), f1.hashCode());
		assertEquals(f1.hashCode(), f1.hashCode());

		//Fields are equal to one another,
		//when all of their attributes are equal (by Valentin)
		Field f1WithSameAttributes= new Field(FieldType.DESERT, p);	
		assertEquals(f1.hashCode(), f1WithSameAttributes.hashCode());
	}
}
