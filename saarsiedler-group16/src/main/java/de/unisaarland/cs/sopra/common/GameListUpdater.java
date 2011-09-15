package de.unisaarland.cs.sopra.common;

import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.MatchListUpdater;

public class GameListUpdater implements MatchListUpdater {

	@Override
	public void newMatch(MatchInformation info) {
		Client.refreshGameList();
	}

	@Override
	public void removedMatch(long matchId) {
		Client.refreshGameList();
		

	}

	@Override
	public void matchUpdate(MatchInformation info) {
		Client.refreshGameList();
		if (Client.matchInfo != null) { 
			Client.refreshPlayerList();
		}
	}

}
