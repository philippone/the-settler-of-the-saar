package de.unisaarland.cs.sopra.common.view.ai.copy;

import de.unisaarland.cs.sopra.common.controller.Controller;
import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.view.ai.Ai;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;
import de.unisaarland.cs.st.saarsiedler.comm.results.JoinResult;




public class AutomatischeAISpieleClient {
	public static final int ANZAHL_SPIELE = 1;
	public static final int POINTS_ON_MAP = 22;
	public static final String NAME = "private Gruppe 16 Automatischer AI Test 7";


	public static void main(String[] args){
		
		int mypoints = 0;
		int otherpoints = 0;
		int mywins = 0;
		int otherwins = 0;
		
		for (int i = 0; i < ANZAHL_SPIELE; i++) {
			try {
				//prepare aktuelle ai
				Connection toonConnection = Connection.establish("sopra.cs.uni-saarland.de", true);
				toonConnection.changeName("Aktuelle KI");
				MatchInformation toonMatchInfo = null;
				boolean joined = false;
				
				while (!joined) {
					for (MatchInformation mi: toonConnection.listMatches()){

						if (mi.getTitle().equals(NAME)) {

							JoinResult jr = toonConnection.joinMatch(mi.getId(), false);
							if (jr == JoinResult.JOINED){
								toonMatchInfo = mi;
								joined = true;
								break;
							}
						}
					}
				}
				
				WorldRepresentation toonWorldRepresentation = toonConnection.getWorld(toonMatchInfo.getWorldId());
				Model toonModel = new Model(toonWorldRepresentation, toonMatchInfo, toonConnection.getClientId());
				Controller toonController = new Controller(toonConnection, toonModel);
				ControllerAdapter toonAdapter = new ControllerAdapter(toonController, toonModel);
				new Ai(toonModel, toonAdapter);
				toonConnection.changeReadyStatus(true);
				toonController.run();
				
				//ergebnise zwischenspeichern
				int other = 0;
				int my = 0;
				for (long act : toonModel.getPlayerMap().keySet()) {
					if (act != toonConnection.getClientId()) 
						other = toonModel.getPlayerMap().get(act).getVictoryPoints();
					else
						my = toonModel.getPlayerMap().get(act).getVictoryPoints();
				}

				otherpoints += other;
				mypoints += my;
				if (my > other && my >= POINTS_ON_MAP) {
					mywins += 1;
					System.out.println("Ich gewinne mit " + my + " Punkten");
				}
				else if (other > my && other >= POINTS_ON_MAP) {
					otherwins += 1;
					System.out.println("Der andere gewinnt mit " + other + " Punkten");
				}
				toonConnection.close();
			} catch (Exception e) { e.printStackTrace(); }
		}
		
		//ergebnis auslesen
		System.out.println();
		System.out.println("---------------------");
		System.out.println("Ergebnis:");
		System.out.println("ich Punkte: " + mypoints + "/" + (POINTS_ON_MAP*ANZAHL_SPIELE) + " und " + mywins + "/" + ANZAHL_SPIELE + " Siege");
		System.out.println("andere Punkte: " + otherpoints + "/" + (POINTS_ON_MAP*ANZAHL_SPIELE) + " und " + otherwins + "/" + ANZAHL_SPIELE + " Siege" );
	}

}
