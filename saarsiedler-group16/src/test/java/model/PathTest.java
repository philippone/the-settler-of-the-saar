package model;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.IOException;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.HarborType;

public class PathTest {

	private Location l;
	private Path p;
	private Player pl;
	private HarborType h;
	
	
	@Test	
	public void testCatapult(){
		Random r = new Random();
		int x=r.nextInt(10);
		int y=r.nextInt(10);
		int o=r.nextInt(5);
		l=new Location(x,y,o);
		pl=new Player();
		p=new Path(l);
		assertFalse(p.hasCatapult());
		p.createCatapult(pl);
		assertTrue(p.hasCatapult());
		assertEquals(pl,p.getCatapultOwner());
		p.removeCatapult();
		assertFalse(p.hasCatapult());
	}
	
	@Test
	public void testStreet() throws IOException {
		Random r = new Random();
		int x=r.nextInt(10);
		int y=r.nextInt(10);
		int o=r.nextInt(5);
		l=new Location(x,y,o);
		pl=new Player();
		p=new Path(l);
		assertFalse(p.hasStreet());
		p.createStreet(pl);
		assertTrue(p.hasStreet());
		assertEquals(pl,p.getStreetOwner());
		
		Model model = TestUtil.getStandardModel1();
		try {
			model.buildStreet(new Location(0,0,0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// on the border of the world
		try {
			model.buildStreet(new Location(0,0,2));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// on the border beetween water and land
		try {
			model.buildStreet(new Location(1,1,1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// on the middle of the land
		try {
			model.buildStreet(new Location(0,0,1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// on the middle of the sea
		assertFalse(model.getPath(new Location(0,0,0)).hasStreet());
		assertTrue(model.getPath(new Location(0,0,2)).hasStreet());
		assertTrue(model.getPath(new Location(1,1,1)).hasStreet());
		assertFalse(model.getPath(new Location(0,0,1)).hasStreet());
	}
	
	@Test
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
	
	@Test
	public void testLocation(){
		Random r = new Random();
		int x=r.nextInt(10);
		int y=r.nextInt(10);
		int o=r.nextInt(5);
		l=new Location(x,y,o);
		p=new Path(l);
		assertEquals(l,p.getLocation());
	}
	
	@Test
	public void testEquals(){
		Random r = new Random();
		int x=r.nextInt(10);
		int y=r.nextInt(10);
		int o=r.nextInt(5);
		Location l=new Location(x,y,o);
		p=new Path(l);
		assertEquals(new Path(l), new Path(l));
	}
}
