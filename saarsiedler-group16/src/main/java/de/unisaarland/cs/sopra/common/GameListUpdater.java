package de.unisaarland.cs.sopra.common;

import java.io.IOException;

import javax.swing.table.DefaultTableModel;

import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.MatchListUpdater;

public class GameListUpdater implements MatchListUpdater {

	private DefaultTableModel gm;
	private DefaultTableModel pm;

	public GameListUpdater(DefaultTableModel gameTableModel, DefaultTableModel playerTableModel) {
		this.gm= gameTableModel;
		this.pm= playerTableModel;
	}

	@Override
	public synchronized void newMatch(MatchInformation info) {
		gm.addRow(Client.parseMatchInfo(info));
		gm.fireTableDataChanged();
	}
	@Override
	public synchronized void removedMatch(long matchId) {
		for(int i=0; i<gm.getRowCount();i++){
			if((Long)gm.getValueAt(i, 0)==matchId)gm.removeRow(i);
		}
		gm.fireTableDataChanged();
	}
	@Override
	public synchronized void matchUpdate(MatchInformation info) {
		
		for(int i=0; i<gm.getRowCount();i++){
			if((Long)gm.getValueAt(i, 0)==info.getId()){
				gm.removeRow(i);
				gm.addRow(Client.parseMatchInfo(info));
				gm.fireTableRowsUpdated(i, i);
			}
		}
		if (Client.matchInfo!=null && Client.matchInfo.equals(info)) { 
			long[] players = Client.matchInfo.getCurrentPlayers();
			boolean[]readyPlayers = Client.matchInfo.getReadyPlayers();
			Object[][] table = new Object[players.length][2];
			for (int i = 0; i < table.length; i++) {
				try {
					table[i][0]= Client.connection.getPlayerInfo(players[i]).getName();} catch (IOException e) {e.printStackTrace();
				}
				table[i][1]= readyPlayers[i];
			}
			while(pm.getRowCount()!=0){
				pm.removeRow(0);
			}
			for (Object[] objects : table) {
				pm.addRow( objects);
			}
		}
	}
}
