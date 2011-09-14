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
		// TODO Auto-generated method stub
//		table.
	}

	@Override
	public void removedMatch(long matchId) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void matchUpdate(MatchInformation info) {
		// TODO Auto-generated method stub

	}

}
