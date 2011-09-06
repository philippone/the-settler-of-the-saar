package help;

import java.io.IOException;
import java.net.UnknownHostException;

import de.unisaarland.cs.sopra.common.ModelObserver;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Board;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.view.AI;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;


public class TestUtil {
	
	public static Model getStandardModel2() throws IOException {
		WorldRepresentation worldrep = new WorldRepresentation(4, 4, 2, 9, 5, 4, 
				new byte[] {1,2,3,4,
							5,1,2,3,
							4,5,1,2,
							3,4,5,1},
				new byte[] {1,4,
							2,5,
							3,6},
				new byte[] {});
				
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
				return 3;
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
		model.matchStart(new long[] {0,1,2}, new byte[] {2,3,4,5,
														 6,8,9,10,
														 11,12,11,10,
														 9,8,6,5});
		model.setTableOrder(new long[] {2,1,0});
		return model;
	}
	
	public static Model getStandardModel1() throws IOException {
		WorldRepresentation worldrep = new WorldRepresentation(3, 4, 2, 9, 5, 4, 
				new byte[] {0,0,0,
							0,1,6,
							0,5,0,
							0,0,0},
				new byte[] {1,4,
							2,5,
							3,6},
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
				return 3;
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
		model.matchStart(new long[] {0,1,2}, new byte[] {8,6});
		model.setTableOrder(new long[] {2,1,0});
		return model;
	}
	
	public static Connection getTestConnection() throws UnknownHostException, IOException {
		return Connection.establish("testserver", false);
	}
	
	public static ModelObserver getTestView(Player player) throws IOException {
		ModelReader modelReader = null;
		ControllerAdapter controllerAdapter = null;
		ModelObserver view = new AI(player, modelReader, controllerAdapter);
		return view;
	}

	public static Board getTestBoard() {
			WorldRepresentation worldrep = new WorldRepresentation(3, 4, 9, 2, 5, 4, 
					new byte[] {0,0,0,
								0,1,6,
								0,5,0,
								0,0,0},
					new byte[] {1,4,
								2,5},
					new byte[] {0,1,(1 << 4) & 0, 
								2,2,(4 << 4) & 1
									});
			return new Board(worldrep);
	}
	
}
