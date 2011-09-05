package controller;

import static org.junit.Assert.*;
import help.TestUtil;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.ModelWriter;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;

public class ControllerTest {
	
	private Connection connection;
	private ModelWriter modelWriter;
	
	
	@Before
	public void setUp() {
		connection = TestUtil.getTestConnection();
		modelWriter = TestUtil.getTestModel();
	}
	
	
	public void testAttack() {
		fail("haha");
	}
	
	public void testBuiltCatapult() {
		
	}
	
	public void testBuildRoad() {
		
	}
	
	public void testBuildSettlement() {
		
	}
	
	public void testChangeName() {
		
	}
	
	public void testClaimLongestRoad() {
		
	}
	
	public void testClaimVicotry() {
		
	}
	
	public void testEndTurn() {
		
	}
	
	public void testLeaveMatch() {
		
	}
	
	public void testMoveCatapult() {
		
	}
	
	public void testMoveRobber() {
		
	}
	
	public void testRespondTrade() {
		
	}
	
	public void testReturnResources() {
		
	}

}
