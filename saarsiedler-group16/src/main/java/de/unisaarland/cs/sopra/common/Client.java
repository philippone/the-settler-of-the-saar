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
	
	
	public void joinMatch(long matchID, boolean asObserver) {
		try {
			JoinResult res=Client.connection.joinMatch(Client.matchInfo.getId(), asObserver);
			if(res==JoinResult.ALREADY_RUNNING
					|| res==JoinResult.CLOSED
					|| res==JoinResult.FULL
					|| res==JoinResult.NOT_EXISTING)
				throw new IllegalStateException("Running/closed/full/not_existing");
			} catch (Exception e) {e.printStackTrace();}
	}
	
	public void createMatch(String title, int numPlayer, WorldRepresentation world, boolean asObserver) {
		try {	//erstellt Match udn setzt aktuelle Matchinfo auf das erstellste spiel
			matchInfo = connection.newMatch(title, numPlayer,world, asObserver);	} catch (Exception e) {	e.printStackTrace();}
	}
	
	public void ready(boolean ready) {
		ChangeReadyResult result = null;
		try {
			result = connection.changeReadyStatus(ready);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if (result == ChangeReadyResult.MATCH_STARTED || result == ChangeReadyResult.UNCHANGED) {
			throw new IllegalStateException();
		}
		
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
	
	public static void changeName(String name){
		try {
			Client.connection.changeName(name);} catch (Exception e) {e.printStackTrace();	}
	}
	
}
