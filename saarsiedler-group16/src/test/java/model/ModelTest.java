package model;

import static org.junit.Assert.*;
import help.TestUtil;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.ModelObserver;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.view.View;


public class ModelTest {
	
	private Model model;
	
	@Before
	public void setUp() {
		model = TestUtil.getStandardModel();
	}
	
	@Test
	public void testAddObserver() {
		ModelObserver view = TestUtil.getTestView();
		model.addModelObserver(view);
		assertTrue("ModelObserver-Liste ist leer", model.getModelObservers().size() == 1);
		
	}
	
	
	@Test
	public void testRemoveObserver() {
		
	}
	
	@Test
	public void testGetCurrentPlayer() {
		
	}
	
	@Test
	public void testCalculateLongestRoad() {
		
	}
	
	@Test
	public void setTableOrder() {
		
	}
	
	@Test
	public void setFieldNumbers() {
		
	}
	
	@Test
	public void updateLongestRoad() {
		
	}
	
	@Test
	public void getLocationField() {
		
	}
	
	@Test
	public void getLocationIntersection() {
		
	}
	
	@Test
	public void getLocationPath() {
		
	}
	
	
	
	
	
	

}
