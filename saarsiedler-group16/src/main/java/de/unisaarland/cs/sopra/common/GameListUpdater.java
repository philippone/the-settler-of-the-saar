package de.unisaarland.cs.sopra.common;

import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.MatchListUpdater;

public class GameListUpdater implements MatchListUpdater {
	
	private ButtonListener buttonlistener;

	public GameListUpdater(ButtonListener buttonlistener) {
		this.buttonlistener = buttonlistener;
	}

	@Override
	public void newMatch(MatchInformation info) {
		buttonlistener.refreshGameList();
	}

	@Override
	public void removedMatch(long matchId) {
		buttonlistener.refreshGameList();
		

	}

	@Override
	public void matchUpdate(MatchInformation info) {
		buttonlistener.refreshGameList();
		if (Client.matchInfo != null) {
			buttonlistener.refreshPlayerList();
		}
	}

}
