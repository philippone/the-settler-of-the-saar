package model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.HarborType;

public class PathTest {

Location l;
Path p;
Player pl;
HarborType h;
	
public void testCatapult(){
	Random r = new Random();
	int x=r.nextInt(10);
	int y=r.nextInt(10);
	int o=r.nextInt(5);
	l=new Location(x,y,o);
	pl=new Player();
	p=new Path(l);
	assertFalse(p.hasCatapultOwner());
	p.createCatapult(pl);
	assertTrue(p.hasCatapultOwner());
	assertEquals(pl,p.getCatapultOwner());
	p.removeCatapult();
	assertFalse(p.hasCatapultOwner());
}

public void testStreet() {
	Random r = new Random();
	int x=r.nextInt(10);
	int y=r.nextInt(10);
	int o=r.nextInt(5);
	l=new Location(x,y,o);
	pl=new Player();
	p=new Path(l);
	assertFalse(p.hasStreetOwner());
	p.createStreet(pl);
	assertTrue(p.hasStreetOwner());
	assertEquals(pl,p.getStreetOwner());
}

public void testHarbortype(){
	Random r = new Random();
	int x=r.nextInt(10);
	int y=r.nextInt(10);
	int o=r.nextInt(5);
	Location l=new Location(x,y,o);
	p=new Path(l);
	h=HarborType.GENERAL_HARBOR;
	p.setHarborType(h);
	assertEquals(h,p.getHarborType());
}

public void testLocation(){
	Random r = new Random();
	int x=r.nextInt(10);
	int y=r.nextInt(10);
	int o=r.nextInt(5);
	l=new Location(x,y,o);
	p=new Path(l);
	assertEquals(l,p.getLocation());
}
}
