package de.unisaarland.cs.sopra.common.view.ai.copy;

import de.unisaarland.cs.sopra.common.controller.Controller;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.view.ai.Ai;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.QualifikationMaps;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class AutomatischeAISpieleServer {

	public static void main(String[] args){
		for (int i = 0; i < AutomatischeAISpieleClient.ANZAHL_SPIELE; i++) {
			try {
				//prepare referenz ai
				Connection refConnection = Connection.establish("sopra.cs.uni-saarland.de", true);
				refConnection.changeName("Referenz KI");
				MatchInformation refMatchInfo = refConnection.newMatch("private Gruppe 16 Automatischer AI Test", 2, QualifikationMaps.getMap1(), false);					
				WorldRepresentation refWorldRepresentation = refConnection.getWorld(refMatchInfo.getWorldId());
				Model refModel = new Model(refWorldRepresentation, refMatchInfo, refConnection.getClientId());
				Controller refController = new Controller(refConnection, refModel);
				ControllerAdapter refAdapter = new ControllerAdapter(refController, refModel);
				new Ai(refModel, refAdapter);
				refConnection.changeReadyStatus(true);
				refController.run();
				refConnection.close();
			} catch (Exception e) { e.printStackTrace(); }
		}
	}
	
}
