package model;

import static org.junit.Assert.*;
import help.TestUtil;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Point;



public class ModelReaderTest {
	
	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel2();
	}		
	
	@Test
	public void testGetHarborTypes(Player player) {
		
	}
	
	@Test
	public void testGetResources() {
		
	}
	
	@Test
	public void testGetFieldNumber(Field field) {
		
	}
	
	@Test
	public void testGetFieldResource(Field field) {
		
	}
	
	@Test
	public void testGetFieldsFromField(Field field) {
		
	}
	
	@Test
	public void testGetFieldsFromIntersection(Intersection intersection) {
		
	}
	
	@Test
	public void testGetFieldsFromPath(Path path) {
		
	}
	
	@Test
	public void testGetIntersectionsFromField(Field field) {
		
	}
	
	@Test
	public void testGetIntersectionsFromIntersection(Intersection intersection) {
		
	}
	
	@Test
	public void testGetIntersectionsFromPath(Path path) {
		
	}
	
	@Test
	public void testGetPathsFromField(Field field) {
		
	}
	
	@Test
	public void testGetPathsFromIntersection(Intersection intersection) {
		
	}
	
	@Test
	public void testGetPathsFromPath(Path path) {
		
	}
	
	@Test
	public void testGetPath(Location loc) {
		
	}
	
	@Test
	public void testGetField(Point p) {
		
	}
	
	@Test
	public void testGetIntersection(Location location) {
		
	}

}
