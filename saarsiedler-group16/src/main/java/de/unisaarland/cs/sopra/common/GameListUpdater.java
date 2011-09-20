package de.unisaarland.cs.sopra.common;

import javax.swing.table.DefaultTableModel;

import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.MatchListUpdater;

public class GameListUpdater implements MatchListUpdater {

	private DefaultTableModel gm;

	public GameListUpdater(DefaultTableModel gameTableModel) {
		this.gm= gameTableModel;
	}

	@Override
	public synchronized void newMatch(MatchInformation info) {
		gm.addRow(Client.parseMatchInfo(info));
		gm.fireTableDataChanged();
	}
//	@Override
//	public void newMatch(MatchInformation info) {
//		Client.refreshGameList();
//	}

	@Override
	public synchronized void removedMatch(long matchId) {
		for(int i=0; i<gm.getRowCount();i++){
			if((Long)gm.getValueAt(i, 0)==matchId)gm.removeRow(i);
		}
		gm.fireTableDataChanged();
	}
//	@Override
//	public void removedMatch(long matchId) {
//		Client.refreshGameList();
//	}
	
	@Override
	public synchronized void matchUpdate(MatchInformation info) {
		
		for(int i=0; i<gm.getRowCount();i++){
			if((Long)gm.getValueAt(i, 0)==info.getId()){
				gm.removeRow(i);
				gm.addRow(Client.parseMatchInfo(info));
				gm.fireTableRowsUpdated(i, i);
			}
		}
//		if (Client.matchInfo.equals(info)) { 
//			Client.refreshPlayerList();
//		}
	}
//	@Override
//	public void matchUpdate(MatchInformation info) {
//		Client.refreshGameList();
//		if (Client.matchInfo != null) { 
//			Client.refreshPlayerList();
//		}
//	}

}
