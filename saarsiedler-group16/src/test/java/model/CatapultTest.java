package model;

import static org.junit.Assert.*;
import org.junit.Test;


import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Catapult;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class CatapultTest {

	Player pl;
	Catapult c;
	
	@Test
	public void testPrice(){
		ResourcePackage buildingPrice = new ResourcePackage(-1,0,-1,0,-1);
		ResourcePackage attackCatapultPrice = new ResourcePackage(0,0,0,-1,0);
		ResourcePackage attackBuildingPrice = new ResourcePackage(0,0,0,0,-1);
		assertEquals(buildingPrice,Catapult.getBuildingprice());
		assertEquals(attackBuildingPrice,Catapult.getAttackbuildingprice());
		assertEquals(attackCatapultPrice,Catapult.getAttackcatapultprice());
	}
	
	@Test
	public void testOwner(){
		pl=new Player();
		c=new Catapult(pl);
		assertEquals(pl,c.getOwner());
	}	
}
