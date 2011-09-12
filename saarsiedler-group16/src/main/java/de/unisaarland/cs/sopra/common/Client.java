package de.unisaarland.cs.sopra.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.unisaarland.cs.sopra.common.controller.Controller;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.ModelWriter;
import de.unisaarland.cs.sopra.common.view.View;
import de.unisaarland.cs.st.saarsiedler.comm.*;
import de.unisaarland.cs.st.saarsiedler.comm.exceptions.IllegalMatchSpecificationException;
import de.unisaarland.cs.st.saarsiedler.comm.results.*;

public class Client {
	
//	private Connection connection;
//	private ClientGUI clientGUI;
	private Setting setting;
	public static void main(String[] args) {
		
		GUIFrame g = new GUIFrame(new Connection() 
		{
			
			@Override
			public void returnResources(int arg0, int arg1, int arg2, int arg3, int arg4)
					throws IOException, IllegalStateException, IllegalArgumentException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public long respondTrade(boolean arg0) throws IOException,
					IllegalStateException, IllegalArgumentException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public List<MatchInformation> registerMatchListUpdater(MatchListUpdater arg0)
					throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long offerTrade(int arg0, int arg1, int arg2, int arg3, int arg4)
					throws IOException, IllegalStateException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public MatchInformation newMatch(String arg0, int arg1,
					WorldRepresentation arg2, boolean arg3) throws IOException,
					IllegalMatchSpecificationException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Resource moveRobber(int arg0, int arg1, int arg2, int arg3, long arg4)
					throws IOException, IllegalStateException, IllegalArgumentException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean moveCatapult(Edge arg0, Edge arg1) throws IOException,
					IllegalStateException, IllegalArgumentException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public List<MatchInformation> listMatches() throws IOException {
				List<MatchInformation> ret = new ArrayList<MatchInformation>();
				ret.add(new MatchInformation() 
				{
					
					@Override
					public boolean isStarted() {
						// TODO Auto-generated method stub
						return false;
					}
					
					@Override
					public long getWorldId() {
						return 1;
					}
					
					@Override
					public String getTitle() {
						return "Beispielspiel 3";
					}
					
					@Override
					public boolean[] getReadyPlayers() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public int getNumPlayers() {
						// TODO Auto-generated method stub
						return 10;
					}
					
					@Override
					public long getId() {
						// TODO Auto-generated method stub
						return 3;
					}
					
					@Override
					public long[] getCurrentPlayers() {
						// TODO Auto-generated method stub
						return new long[]{9,8,7,6,5};
					}
					
					@Override
					public long[] getCurrentObservers() {
						// TODO Auto-generated method stub
						return null;
					}
				});
				ret.add(new MatchInformation() {
					
					@Override
					public boolean isStarted() {
						// TODO Auto-generated method stub
						return true;
					}
					
					@Override
					public long getWorldId() {
						// TODO Auto-generated method stub
						return 2;
					}
					
					@Override
					public String getTitle() {
						// TODO Auto-generated method stub
						return "Gestartetes spiel 2";
					}
					
					@Override
					public boolean[] getReadyPlayers() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public int getNumPlayers() {
						// TODO Auto-generated method stub
						return 3;
					}
					
					@Override
					public long getId() {
						// TODO Auto-generated method stub
						return 2;
					}
					
					@Override
					public long[] getCurrentPlayers() {
						// TODO Auto-generated method stub
						return new long[]{9,8,7};
					}
					
					@Override
					public long[] getCurrentObservers() {
						// TODO Auto-generated method stub
						return null;
					}
				});
				
				return ret;
			}
			
			@Override
			public void leaveMatch() throws IOException, IllegalStateException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public JoinResult joinMatch(long arg0, boolean arg1) throws IOException,
					IllegalStateException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public WorldRepresentation getWorld(long arg0) throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Timeouts getTimeouts() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PlayerInformation getPlayerInfo(long arg0) throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public GameEvent getNextEvent(int arg0) throws IOException,
					IllegalStateException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public MatchInformation getMatchInfo(long arg0) throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getClientId() throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public void endTurn() throws IOException, IllegalStateException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void deregisterMatchListUpdater(MatchListUpdater arg0)
					throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void close() throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void claimVictory() throws IOException, IllegalStateException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void claimLongestRoad(List<Edge> arg0) throws IOException,
					IllegalStateException, IllegalArgumentException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public ChangeReadyResult changeReadyStatus(boolean arg0)
					throws IOException, IllegalStateException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String changeName(String arg0) throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void buildSettlement(Intersection arg0, boolean arg1)
					throws IOException, IllegalStateException, IllegalArgumentException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void buildRoad(Edge arg0) throws IOException, IllegalStateException,
					IllegalArgumentException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean buildCatapult(Edge arg0) throws IOException,
					IllegalStateException, IllegalArgumentException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public AttackResult attack(Edge arg0, Intersection arg1)
					throws IOException, IllegalStateException, IllegalArgumentException {
				// TODO Auto-generated method stub
				return null;
			}
		}
		);
	
	}
	public MatchInformation getMatchInformation(int matchID) {
		throw new UnsupportedOperationException();
	}
	
	public JoinResult joinMatch(int matchID, boolean asObserver) {
		throw new UnsupportedOperationException();
	}
	
	public MatchInformation createMatch(String title, int numPlayer, WorldRepresentation world, boolean asObserver) {
		throw new UnsupportedOperationException();
	}
	
	public ChangeReadyResult ready(boolean ready) {
		throw new UnsupportedOperationException();
	}
	
	public Model buildModel(MatchInformation matchInfo) {
		throw new UnsupportedOperationException();
	}
	
	public Controller buildController(ModelWriter modelWriter, Connection connection) {
		throw new UnsupportedOperationException();
	}
	
	public View buildView(Controller controller, ModelReader modelReader) {
		throw new UnsupportedOperationException();
	}
	
	public void initializeMatch() {
		throw new UnsupportedOperationException();
	}
	
	public void closeConnection() {
		throw new UnsupportedOperationException();
	}
	
	public void createConnection(String serverAdress, boolean ai) {
		throw new UnsupportedOperationException();
	}
	
	public void changeName(String name){
		throw new UnsupportedOperationException();
	}
	
}
