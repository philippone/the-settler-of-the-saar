package help;

import java.io.IOException;

import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;


public class TestUtil {
	
	public static Model getStandardModel() throws IOException {
		
		Model model = new Model(WorldRepresentation.getDefault(), new MatchInformation() 
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
				return playerCount;
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
		});
		model.matchStart(new long[] {0,1,2}, new byte[] {1,2,3,4,5});
		model.setTableOrder(new long[] {2,1,0});
		return model;
	}
	
	public static Model getTestModel() {
		return null;
	}
	
	public static Connection getTestConnection() {
		return null;
	}

}
