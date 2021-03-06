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
		
		assertEquals("Expected 3 as Result", respack.getResource(LUMBER),3);
		assertEquals("Expected 6 as Result", respack.getResource(BRICK),6);
		assertEquals("Expected 9 as Result", respack.getResource(WOOL),9);
		assertEquals("Expected 12 as Result", respack.getResource(GRAIN),12);
		assertEquals("Expected 15 as Result", respack.getResource(ORE),15);
	}
	
	@Test
	public void testGet() {
		assertEquals("Expected 1 as Result", respack.getResource(LUMBER),1);
		assertEquals("Expected 2 as Result", respack.getResource(BRICK),2);
		assertEquals("Expected 3 as Result", respack.getResource(WOOL),3);
		assertEquals("Expected 4 as Result", respack.getResource(GRAIN),4);
		assertEquals("Expected 5 as Result", respack.getResource(ORE),5);
	}
	
	@Test
	public void testAdd() {
		ResourcePackage res = new ResourcePackage(2,7,11,4,8);
		ResourcePackage res2 = new ResourcePackage(6,1,4,9,5);
		res.add(res2);
		assertEquals("Expected 8 lumber, 8 brick, 15 wool, 13 grain and 13 ore", res, new ResourcePackage(8,8,15,13,13));
	}
	
	@Test
	public void hasNegativeResourcesTest() {
		assertFalse("0,0,0,0,0 is no negative resourcepackage",new ResourcePackage(0,0,0,0,0).hasNegativeResources());
	}
	
	@Test
	public void negativeCountTest() {
		assertEquals(new ResourcePackage(3,6,-7,3,-7).getNegativeResourcesCount(),14);
	}
	
	@Test
	public void positiveCountTest() {
		assertEquals(new ResourcePackage(3,6,-7,3,-7).getPositiveResourcesCount(),12);
	}
	
	
	@Test
	public void negateResourcePackageTest1() {
		ResourcePackage rp = new ResourcePackage(0,0,0,0,0);
		rp.neagateResourcePackage();
		assertTrue(rp.equals(new ResourcePackage(0,0,0,0,0)));
	}
	
	@Test
	public void negateResourcePackageTest2() {
		ResourcePackage rp = new ResourcePackage(1,0,5,5,10);
		ResourcePackage rp2 = rp.neagateResourcePackage();
		assertTrue(rp2.equals(new ResourcePackage(-1,0,-5,-5,-10)));
	}
	
}