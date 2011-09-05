package model;

import static org.junit.Assert.*;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.sopra.common.model.Street;

public class StreetTest {
	
	Player pl;
	Street s;
	
	public void testOwner(){
		pl=new Player();
		s=new Street(pl);
		assertEquals(pl, s.getOwner());
	}
	
	public void testPrice(){
		ResourcePackage price = new ResourcePackage(-1,-1,0,0,0);
		assertEquals(price, Street.getPrice());
	}
	
}
