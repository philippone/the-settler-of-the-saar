package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class PlayerTest {
	Player p1;
	Player p2;
	@Before
	public void setUp(){
		p1 = new Player();
		p2 = new Player();
	}
	@Test
	public void testModify(){
		p1.modifyResources(new ResourcePackage(1, 2, 3, 4, 5));
		assertEquals(1, p1.getResources().getResource(Resource.LUMBER));
		assertEquals(2, p1.getResources().getResource(Resource.BRICK));
		assertEquals(3, p1.getResources().getResource(Resource.WOOL));
		assertEquals(4, p1.getResources().getResource(Resource.GRAIN));
		assertEquals(5, p1.getResources().getResource(Resource.ORE));
		//probably not worth testing, but nevertheless..
		p2.modifyResources(new ResourcePackage(1,1,1,1,1));		
//		try{
//			p2.modifyResources(new ResourcePackage(-2,-2,-2,-2,-2));
//			fail("Sollte IllegalArgumentException werfen, da Player kein negatives Guthaben haben darf!");
//		}catch(IllegalArgumentException e){}
		
		assertFalse("Player kein negatives Guthaben", 0> p1.getResources().getResource(Resource.LUMBER));
		assertFalse("Player kein negatives Guthaben",0> p1.getResources().getResource(Resource.BRICK));
		assertFalse("Player kein negatives Guthaben",0> p1.getResources().getResource(Resource.WOOL));
		assertFalse("Player kein negatives Guthaben",0> p1.getResources().getResource(Resource.GRAIN));
		assertFalse("Player kein negatives Guthaben",0> p1.getResources().getResource(Resource.ORE));
		
	}
	@Test 
	public void testSuffient(){
		assertFalse(p1.checkResourcesSufficient(new ResourcePackage(-1, 0, 0, 0, 0)));
		assertFalse(p1.checkResourcesSufficient(new ResourcePackage(0, -1, 0, 0, 0)));
		assertFalse(p1.checkResourcesSufficient(new ResourcePackage(0, 0, -1, 0, 0)));
		assertFalse(p1.checkResourcesSufficient(new ResourcePackage(0, 0, 0, -1, 0)));
		assertFalse(p1.checkResourcesSufficient(new ResourcePackage(0, 0, 0, 0, -1)));
		
		p1.modifyResources(new ResourcePackage(1,1,1,1,1));
		assertTrue(p1.checkResourcesSufficient(new ResourcePackage(-1, 0, 0, 0, 0)));
		assertTrue(p1.checkResourcesSufficient(new ResourcePackage(0, -1, 0, 0, 0)));
		assertTrue(p1.checkResourcesSufficient(new ResourcePackage(0, 0, -1, 0, 0)));
		assertTrue(p1.checkResourcesSufficient(new ResourcePackage(0, 0, 0, -1, 0)));
		assertTrue(p1.checkResourcesSufficient(new ResourcePackage(0, 0, 0, 0, -1)));
		
		p2.modifyResources(new ResourcePackage(0,1,2,3,4));
		assertFalse(p2.checkResourcesSufficient(new ResourcePackage(-1, 0, 0, 0, 0)));
		assertFalse(p2.checkResourcesSufficient(new ResourcePackage(0, -2, 0, 0, 0)));
		assertFalse(p2.checkResourcesSufficient(new ResourcePackage(0, 0, -3, 0, 0)));
		assertFalse(p2.checkResourcesSufficient(new ResourcePackage(0, 0, 0, -4, 0)));
		assertFalse(p2.checkResourcesSufficient(new ResourcePackage(0, 0, 0, 0, -5)));
		
		try{
			p2.checkResourcesSufficient(new ResourcePackage( 666 ,-1,-1,-1,-1));
			fail("Sollte IllegalArgumentException werfen, da Suffient nur Kosten(negative Packages) annehmen darf!");
		}catch(IllegalArgumentException e){}
	}
	@Test 
	public void testEquals(){
		assertEquals(p1,p1);
		assertNotSame(p1, p2);
		
//		Player p1WithSameAttributes= new Player();
//		assertNotSame("Du darfst die equals() nicht Ueberschreiben", p1, p1WithSameAttributes); //Buildings nie gleich, nur Identitaet!
	}
	@Test 
	public void testHashCode(){
		assertEquals(p1.hashCode(),p1.hashCode());
		assertNotSame(p1.hashCode(), p2.hashCode());
		
//		Player p1WithSameAttributes= new Player();
//		assertNotSame("Du darfst die hashCode() nicht Ueberschreiben",p1.hashCode(), bWithSameAttributes.hashCode()); //Buildings nie gleich, nur Identitaet!
	}
	
}
