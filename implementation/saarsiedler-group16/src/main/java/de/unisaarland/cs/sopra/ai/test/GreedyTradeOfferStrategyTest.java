package de.unisaarland.cs.sopra.ai.test;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;
import de.unisaarland.cs.sopra.common.view.ai.GreedyTradeOfferStrategy;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class GreedyTradeOfferStrategyTest {
	
	private ControllerAdapter ca;
	private Model m;
	private GreedyTradeOfferStrategy gtos;
	
	public class ControllerAdapter {
		
		public ControllerAdapter(){}
		
		public long offerTrade(ResourcePackage rp){
			return -2;
		}
		
	}
	
	@Before
	public void setUp() throws IOException {
		MatchInformation matchinfo = new MatchInformation() 
		{
			
			@Override
			public boolean isStarted() {
				return true;
			}
			
			@Override
			public long getWorldId() {
				throw new IllegalStateException();
			}
			
			@Override
			public String getTitle() {
				return "Test-Spiel";
			}
			
			@Override
			public boolean[] getReadyPlayers() {
				throw new IllegalStateException();
			}
			
			@Override
			public int getNumPlayers() {
				return 2;
			}
			
			@Override
			public long getId() {
				return 0;
			}
			
			@Override
			public long[] getCurrentPlayers() {
				return new long[] {0,1};
			}
			
			@Override
			public long[] getCurrentObservers() {
				return new long[] {};
			}
		};
		m = new Model(WorldRepresentation.getDefault(), matchinfo, 0);
		m.matchStart(new long[] {3,2,1,0}, new byte[]   {2,3,4,
		 6,8,9,10,
		 11,12,11,10,
		 9,8,6,5,
		 2,6,9});
		ca = new ControllerAdapter();
		gtos = new GreedyTradeOfferStrategy(ca, m);
	}

	@Test
	public void testTryToTrade1() {
		m.getPlayerMap().get(0L).getResources().add(new ResourcePackage(0, 0, 1, 1, 1));
		assertTrue(gtos.tryToTrade(new ResourcePackage(-1, -1, 1, 1, 1)));
	}
	
	@Test
	public void testTryToTrade2() {
		m.getPlayerMap().get(0L).getResources().add(new ResourcePackage(0, 0, 0, 1, 1));
		assertTrue(gtos.tryToTrade(new ResourcePackage(-1, -1, 0, 1, 1)));
	}
	
	@Test
	public void testTryToTrade3() {
		m.getPlayerMap().get(0L).getResources().add(new ResourcePackage(0, 0, 0, 0, 1));
		assertTrue(gtos.tryToTrade(new ResourcePackage(-1, -1, 0, 0, 1)));
	}
	
	@Test
	public void testTryToTrade4() {
		m.getPlayerMap().get(0L).getResources().add(new ResourcePackage(0, 0, 0, 0, 1));
		assertFalse(gtos.tryToTrade(new ResourcePackage(-1, -1, 0, 0, 0)));
	}
		

}
