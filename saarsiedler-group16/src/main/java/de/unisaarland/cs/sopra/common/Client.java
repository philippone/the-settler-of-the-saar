package de.unisaarland.cs.sopra.common;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import de.unisaarland.cs.sopra.common.controller.Controller;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.ModelWriter;
import de.unisaarland.cs.sopra.common.view.View;
import de.unisaarland.cs.st.saarsiedler.comm.*;
import de.unisaarland.cs.st.saarsiedler.comm.results.*;

public class Client {
	
	public static Connection connection;
	public static MatchInformation matchInfo;
	private static GUIFrame clientGUI;
	private Setting setting;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Client me = new Client();
		clientGUI = new GUIFrame(me);
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
		try {
			connection= Connection.establish(serverAdress, ai);
		} catch (Exception e) {	e.printStackTrace();}
	}
	
	public Connection getConnection(){
		return this.connection;
	}
	
	public void changeName(String name){
		throw new UnsupportedOperationException();
	}
	
}
