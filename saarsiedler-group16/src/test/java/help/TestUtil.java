package help;

import java.io.IOException;
import java.net.UnknownHostException;

import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;


public class TestUtil {
	Model model;
	public void setUp() throws UnknownHostException, IOException{
		Connection c = Connection.establish("bla", false);
		model= new Model(WorldRepresentation.getDefault(), new MatchInformation() 
		{
			
			@Override
			public boolean isStarted() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public long getWorldId() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getTitle() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean[] getReadyPlayers() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getNumPlayers() {
				// TODO Auto-generated method stub
				return 2;
			}
			
			@Override
			public long getId() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public long[] getCurrentPlayers() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long[] getCurrentObservers() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		model.matchStart(players, number)
	}
	
	public static Model getTestModel() {
		return null;
	}
	
	public static Connection getTestConnection() {
		return null;
	}

}
