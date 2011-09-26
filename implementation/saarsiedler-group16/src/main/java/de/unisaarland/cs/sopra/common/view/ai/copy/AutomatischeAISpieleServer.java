package de.unisaarland.cs.sopra.common.view.ai.copy;

import de.unisaarland.cs.sopra.common.controller.Controller;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.QualifikationMaps;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class AutomatischeAISpieleServer {

	public static void main(String[] args){
		try {
			//prepare referenz ai
			Connection refConnection = Connection.establish("sopra.cs.uni-saarland.de", true);
			refConnection.changeName("Referenz KI");
			MatchInformation refMatchInfo = refConnection.newMatch("private Gruppe 16 Automatischer AI Test", 2, QualifikationMaps.getMap2(), false);					
			WorldRepresentation refWorldRepresentation = refConnection.getWorld(refMatchInfo.getWorldId());
			Model refModel = new Model(refWorldRepresentation, refMatchInfo, refConnection.getClientId());
			Controller refController = new Controller(refConnection, refModel);
			ControllerAdapter refAdapter = new ControllerAdapter(refController, refModel);
			new AutomatischeAISpieleClient(refModel, refAdapter);
			refConnection.changeReadyStatus(true);
			refController.run();
			
			//ergebnis auslesen
			System.out.println();
			System.out.println("---------------------");
			System.out.println("Ergebnis:");
			System.out.println("Meine ID: " + refConnection.getClientId());
			for (long act : refModel.getPlayerMap().keySet())
				System.out.println("ID: " + act + ", Punkte: " + refModel.getPlayerMap().get(act).getVictoryPoints());
		} catch (Exception e) { e.printStackTrace(); }
	}
	
}
