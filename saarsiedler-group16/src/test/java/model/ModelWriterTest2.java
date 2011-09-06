package model;

import help.TestUtil;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class ModelWriterTest2 {

	private Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel1();
		model.getTableOrder().get(0).modifyResources(new ResourcePackage(100,100,100,100,100));
		model.newRound(4);
	}
	
	@Test
	public void longestRaodClaimedTest() {
		
	}
	
	@Test
	public void matchStartTest() {
		
	}
	
	@Test
	public void catapultMovedTestPositive() {
		
	}
	
	@Test
	public void catapultMovedTestNegative() {
		
	}
	
	@Test
	public void playerLeftTest() {
		
	}
	
	@Test
	public void robberMovedTestSelf() {
		
	}

	@Test
	public void robberMovedTestOther() {
	
	}
	
}
