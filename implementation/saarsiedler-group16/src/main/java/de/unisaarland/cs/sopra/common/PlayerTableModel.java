package de.unisaarland.cs.sopra.common;

import java.io.IOException;

import javax.swing.table.AbstractTableModel;

import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.MatchListUpdater;

public class PlayerTableModel extends AbstractTableModel implements MatchListUpdater {
    
//	private MatchInformation info;

//	public PlayerTableModel(MatchInformation info) {
//		this.info=info;
//	}
	
	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return Client.matchInfo.getCurrentPlayers().length;
	}

	@Override
	public Object getValueAt(int row, int col){
		if(col==0){
			return Client.matchInfo.getCurrentPlayers()[row];
		}
		if(col==1){
			try {
				return Client.connection.getPlayerInfo(Client.matchInfo.getCurrentPlayers()[row]).getName();
			} catch (IOException e) {e.printStackTrace();}
			return null;
		}else{
			return Client.matchInfo.getReadyPlayers()[row];
		}
	}
	@Override
	public String getColumnName(int column) {
		if(column==0){
			return "ID";
		}
		if(column==1){
			return "Name";
		}else{
			return "Ready-Status";
		}
	}
	
	@Override
	public void newMatch(MatchInformation info) { 
	}

	@Override
	public void removedMatch(long matchId) {
		if (matchId==Client.matchInfo.getId()) throw new IllegalStateException("Actual Matchinfo was removed");
	}

	@Override
	public void matchUpdate(MatchInformation info) {
		if (Client.matchInfo!=null && Client.matchInfo.getId()==info.getId()){
			Client.matchInfo=info;
		}
		fireTableDataChanged();
	}

}
