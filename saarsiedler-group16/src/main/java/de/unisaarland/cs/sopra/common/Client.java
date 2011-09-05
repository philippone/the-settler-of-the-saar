package de.unisaarland.cs.sopra.common;

import de.unisaarland.cs.sopra.common.controller.Controller;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.ModelWriter;
import de.unisaarland.cs.sopra.common.view.View;
import de.unisaarland.cs.st.saarsiedler.comm.*;
import de.unisaarland.cs.st.saarsiedler.comm.results.*;

public class Client {
	
	private Connection connection;
	private ClientGUI clientGUI;
	private Setting setting;
	
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
