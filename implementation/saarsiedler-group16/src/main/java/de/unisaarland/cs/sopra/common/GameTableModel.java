package de.unisaarland.cs.sopra.common;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.List;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.MatchListUpdater;

public class GameTableModel extends AbstractTableModel implements MatchListUpdater {
    
	List<MatchInformation> matchList=null;

	public GameTableModel() {
		updateMatchList();		
	}
	
	private void updateMatchList(){
		try {
			matchList =Client.connection.listMatches();		
		} catch (IOException e1) {	e1.printStackTrace();}
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}
	

	@Override
	public int getRowCount() {
//		updateMatchList();
		return matchList.size();
	}

	@Override
	public Object getValueAt(int row, int col){
//		updateMatchList();
		MatchInformation info =matchList.get(row);
		switch(col){
		case 0:	return info.getId();
		case 1:	return info.getTitle();
		case 2:	return info.getCurrentPlayers().length+"/"+info.getNumPlayers();
		case 3:	return info.getWorldId();
		case 4: return info.isStarted();
		}
		throw new IllegalArgumentException("Table has only 5 columns");	
	}
	@Override
	public String getColumnName(int column) {
//		updateMatchList();
		switch(column){
		case 0:	return "MatchID";
		case 1:	return "Name";
		case 2:	return "Players";
		case 3:	return "WorldID";
		case 4: return "Already started";
		}
		throw new IllegalArgumentException("Table has only 5 columns");	
	}
	
	@Override
	public void newMatch(MatchInformation info) {
		matchList.add(info);
		fireTableDataChanged();
	}

	@Override
	public void removedMatch(long matchId) {
		for(int i=0; i< matchList.size();i++){
			if(matchId==matchList.get(i).getId()){
				matchList.remove(i);
			}
		}
		fireTableDataChanged();
	}

	@Override
	public void matchUpdate(MatchInformation info) {
		for(int i=0; i< matchList.size();i++){
			if(info.getId()==matchList.get(i).getId()){
				matchList.remove(i);
				matchList.add(info);
			}
		}
		fireTableDataChanged();
	}

	


}
