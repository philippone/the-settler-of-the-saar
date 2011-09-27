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
		int mypoints = 0;
		int otherpoints = 0;
		int mywins = 0;
		int otherwins = 0;
		
		for (int i = 0; i < AutomatischeAISpieleClient.ANZAHL_SPIELE; i++) {
			try {
				//prepare referenz ai
				Connection refConnection = Connection.establish("sopra.cs.uni-saarland.de", true);
				refConnection.changeName("Referenz KI");

				MatchInformation refMatchInfo = refConnection.newMatch(AutomatischeAISpieleClient.NAME, 2, QualifikationMaps.getMap1(), false);					

				WorldRepresentation refWorldRepresentation = refConnection.getWorld(refMatchInfo.getWorldId());
				Model refModel = new Model(refWorldRepresentation, refMatchInfo, refConnection.getClientId());
				Controller refController = new Controller(refConnection, refModel);
				ControllerAdapter refAdapter = new ControllerAdapter(refController, refModel);
				new Ai(refModel, refAdapter);
				refConnection.changeReadyStatus(true);
				refController.run();
				
				//ergebnise zwischenspeichern
				int other = 0;
				int my = 0;
				for (long act : refModel.getPlayerMap().keySet()) {
					if (act != refConnection.getClientId()) 
						other = refModel.getPlayerMap().get(act).getVictoryPoints();
					else
						my = refModel.getPlayerMap().get(act).getVictoryPoints();
				}

				otherpoints += other;
				mypoints += my;
				if (my > other && my >= AutomatischeAISpieleClient.POINTS_ON_MAP) {
					mywins += 1;
					System.out.println("Ich gewinne mit " + my + " Punkten");
				}
				else if (other > my && other >= AutomatischeAISpieleClient.POINTS_ON_MAP) {
					otherwins += 1;
					System.out.println("Der andere gewinnt mit " + other + " Punkten");
				}
				
				refConnection.close();
			} catch (Exception e) { e.printStackTrace(); }
			
			System.out.println();
			System.out.println("---------------------");
			System.out.println("Ergebnis:");
			System.out.println("ich Punkte: " + mypoints + "/" + (AutomatischeAISpieleClient.POINTS_ON_MAP*AutomatischeAISpieleClient.ANZAHL_SPIELE) + " und " + mywins + "/" + AutomatischeAISpieleClient.ANZAHL_SPIELE + " Siege");
			System.out.println("andere Punkte: " + otherpoints + "/" + (AutomatischeAISpieleClient.POINTS_ON_MAP*AutomatischeAISpieleClient.ANZAHL_SPIELE) + " und " + otherwins + "/" + AutomatischeAISpieleClient.ANZAHL_SPIELE + " Siege" );
			
		}
	}
	
}
