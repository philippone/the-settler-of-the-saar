package model;

import static org.junit.Assert.*;

import static de.unisaarland.cs.sopra.common.model.Resource.*;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.*;

public class ResourcePackageTest {

	private ResourcePackage respack;
	
	@Before
	public void setUp() {
		respack = new ResourcePackage(1,2,3,4,5);
	}
	
	@Test
	public void testClone() {
		ResourcePackage respack2 = respack.copy();
		respack.add(new ResourcePackage(2,4,6,8,10));
		assertEquals("Expected 1 as Result", respack2.getResource(LUMBER),1);
		assertEquals("Expected 2 as Result", respack2.getResource(BRICK),2);
		assertEquals("Expected 3 as Result", respack2.getResource(WOOL),3);
		assertEquals("Expected 4 as Result", respack2.getResource(GRAIN),4);
		assertEquals("Expected 5 as Result", respack2.getResource(ORE),5);
		assertEquals("Expected 1 as Result", respack.getResource(LUMBER),3);
		assertEquals("Expected 2 as Result", respack.getResource(BRICK),6);
		assertEquals("Expected 3 as Result", respack.getResource(WOOL),9);
		assertEquals("Expected 4 as Result", respack.getResource(GRAIN),12);
		assertEquals("Expected 5 as Result", respack.getResource(ORE),15);
	}
	
	@Test
	public void testGet() {
		assertEquals("Expected 1 as Result", respack.getResource(LUMBER),1);
		assertEquals("Expected 2 as Result", respack.getResource(BRICK),2);
		assertEquals("Expected 3 as Result", respack.getResource(WOOL),3);
		assertEquals("Expected 4 as Result", respack.getResource(GRAIN),4);
		assertEquals("Expected 5 as Result", respack.getResource(ORE),5);
	}
	
}

/*		
 * assertEquals("Expected 2 as Result", funktionDieZweiZurückGebenSollte() , 2);
 * assertTrue("Expected True as Result", true);
 * assertFalse("Expected True as Result", false);
 */