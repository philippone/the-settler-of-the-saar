package view;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.ai.AiMain;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class BuildVillageTest{
	Model model;
	
	@Before
	public void setUp(){
		
		WorldRepresentation worldrep = new WorldRepresentation(3, 4, 9, 2, 5, 4, 
				new byte[] {0,0,0,
							0,1,0,
							0,5,0,
							0,0,0},
				new byte[] {1,4,
							2,5},
				new byte[] {0,1,(1 << 4) & 0, 
							2,2,(4 << 4) & 1
								});
				
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
				return new long[] {0,1,2};
			}
			
			@Override
			public long[] getCurrentObservers() {
				return new long[] {};
			}
		};
		
		Model model = new Model(worldrep, matchinfo);
		AiMain ai = new AiMain();
		ai.
		model.addModelObserver(ai);
		model.matchStart(new long[] {0,1,2}, new byte[] {8,6});
		model.setTableOrder(new long[] {2,1,0});
		
	}
}