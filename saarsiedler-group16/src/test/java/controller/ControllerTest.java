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
		
	}

}
