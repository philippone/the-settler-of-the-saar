package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.Building;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class IntersectionTest {
	Location l1;
	Location l2;
	Intersection i1,i2;
	Player p1, p2, p3;
	Building b1,b2;
	ResourcePackage r1,r2,r3,r4;

	@Before
	public void setUp() {
		p1 = new Player();
		b1 = new Building(p1, BuildingType.Village);
		b2 = new Building(p1, BuildingType.Town);
		l1 = new Location(1, 2, 3);
		l2 = new Location(10, 20, 2);
		i1 = new Intersection(l1);
		i2 = new Intersection(l2);
		i1.createBuilding(BuildingType.Village, p1);
		i2.createBuilding(BuildingType.Town, p2);
	}
	
	@Test
	public void testLocation(){
		assertEquals(l1, i1.getLocation());
		assertEquals(false, i1.equals(i2));
		
	}
	
	@Test
	public void testOwner(){
		assertEquals(p1, i1.getOwner());
	    p3 = new Player();
		i1.setOwner(p3);
		assertEquals(p3, i1.getOwner());
	}
	
	@Test
	public void testBuildingType(){
		assertEquals(BuildingType.Village, i1.getBuildingType());
		assertEquals(BuildingType.Town, i2.getBuildingType());
	}
	
	@Test
	public void testGain(){
		r1 = p1.getResources();
		i1.generateGain(Resource.LUMBER);
		r2 = new ResourcePackage(1,0,0,0,0);
		r1.add(r2);
		assertEquals( r1,p1.getResources());
		r3 = p2.getResources();
		i2.generateGain(Resource.ORE);
		r4 = new ResourcePackage(0,0,0,0,2);
		r3.add(r4);
		assertEquals(r3, p2.getResources());
	}
	
	@Test
	public void testRemoveBuilding(){
		i1.removeBuilding();
		assertEquals(false, i1.hasOwner());
		assertEquals(null, i1.getOwner());
		i2.removeBuilding();
		assertEquals(false, i2.hasOwner());
		assertEquals(null, i2.getOwner());
		
	}
	
}

