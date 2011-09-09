package view;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.controller.Controller;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.view.AI;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.Edge;
import de.unisaarland.cs.st.saarsiedler.comm.GameEvent;
import de.unisaarland.cs.st.saarsiedler.comm.Intersection;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.MatchListUpdater;
import de.unisaarland.cs.st.saarsiedler.comm.PlayerInformation;
import de.unisaarland.cs.st.saarsiedler.comm.Resource;
import de.unisaarland.cs.st.saarsiedler.comm.Timeouts;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;
import de.unisaarland.cs.st.saarsiedler.comm.exceptions.IllegalMatchSpecificationException;
import de.unisaarland.cs.st.saarsiedler.comm.results.AttackResult;
import de.unisaarland.cs.st.saarsiedler.comm.results.ChangeReadyResult;
import de.unisaarland.cs.st.saarsiedler.comm.results.JoinResult;

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
		
		model = new Model(worldrep, matchinfo,0);
		Connection connect = new Connection() {
			
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
				// TODO Auto-generated method stub
				return null;
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
		};
		Controller con = new Controller(connect, model); 
		ControllerAdapter conAda = new ControllerAdapter(con, model);
		AI ai = new AI(0, model, conAda);
		
		model.matchStart(new long[] {0,1,2}, new byte[] {8,6});
		model.setTableOrder(new long[] {2,1,0});
		
	}
}