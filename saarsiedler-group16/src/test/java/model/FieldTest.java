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
	@Before
	public void setUp(){
		Point p = new Point (0, 0);
		f1= new Field(FieldType.DESSERT, p);
		f2= new Field(FieldType.FIELD, p);
		f3= new Field(FieldType.FOREST, p);
		f4= new Field(FieldType.HILL, p);
		f5= new Field(FieldType.MOUNTAINS, p);
		f6= new Field(FieldType.PASTURE, p);
		f7= new Field(FieldType.WATER, p);
		
		f8= new Field(FieldType.DESSERT, p);
		
		f1.setNumber(3);
		f2.setNumber(3);
		f3.setNumber(3);
		f4.setNumber(3);
		f5.setNumber(3);
		f6.setNumber(3);
		f7.setNumber(3);
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
	}
	@Test
	public void testRobber(){
		assertFalse(f1.hasRobber());
		f1.setRobber(true);
		assertTrue(f1.hasRobber());
	}
	@Test
	public void testNumber(){
		assertEquals(null, f8.getNumber());
		f8.setNumber(12);
		assertEquals(12, f8.getNumber());
	}
	public void testGain(){
		
	}
}
