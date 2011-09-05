package model;

import static org.junit.Assert.*;
import org.junit.Test;


import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Catapult;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class CatapultTest {

	Player pl;
	Catapult c;
	
	public void testPrice(){
		pl=new Player();
		c=new Catapult(pl);
		ResourcePackage buildingPrice = new ResourcePackage(-1,0,-1,0,-1);
		ResourcePackage attackCatapultPrice = new ResourcePackage(0,0,0,-1,0);
		ResourcePackage attackBuildingPrice = new ResourcePackage(0,0,0,0,-1);
		assertEquals(buildingPrice,c.getBuildingprice());
		assertEquals(attackBuildingPrice,c.getAttackbuildingprice());
		assertEquals(attackCatapultPrice,c.getAttackcatapultprice());
	}
	
	public void testOwner(){
		pl=new Player();
		c=new Catapult(pl);
		assertEquals(pl,c.getOwner());
	}	
}
