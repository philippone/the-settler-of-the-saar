package de.unisaarland.cs.sopra.common;

import javax.swing.JTable;

import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.MatchListUpdater;

public class GameListUpdater implements MatchListUpdater {
	
	private JTable table;

	public GameListUpdater(JTable table) {
		this.table = table;
	}

	@Override
	public void newMatch(MatchInformation info) {
		// TODO Auto-generated method stub

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
