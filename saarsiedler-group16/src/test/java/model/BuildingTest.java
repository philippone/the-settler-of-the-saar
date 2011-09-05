package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import de.unisaarland.cs.sopra.common.model.Building;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Player;

public class BuildingTest {
	Building b;
	Player p1;
	Building b2;
	
	@Before
	public void setUp(){
		p1 = new Player();
		b = new Building(p1, BuildingType.Village);
		b2 = new Building(p1, BuildingType.Town);
	}
	@Test
	public void testOwner(){
		assertEquals(p1, b.getOwner());
		
		Player p2 = new Player();
		b.setOwner(p2);
		assertEquals(p2, b.getOwner());
	}
	@Test
	public void testBuildingType(){
		assertEquals(BuildingType.Village, b.getBuildingType());
		assertEquals(BuildingType.Town, b2.getBuildingType());
	}
	@Test
	public void testGain(){
		assertEquals(1, b.getGain());
		assertEquals(2, b2.getGain());
	}
	@Test
	public void testPrice(){
		assertEquals(BuildingType.Village.getPrice(), b.getPrice());
		assertEquals(BuildingType.Town.getPrice(), b2.getPrice());
	}
}
