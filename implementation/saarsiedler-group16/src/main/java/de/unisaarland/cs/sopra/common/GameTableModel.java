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

public class GameTableModel extends AbstractTableModel implements MatchListUpdater, ListSelectionModel{
    
	List<MatchInformation> matchList=null;
	private int select;

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

	@Override
	public void addListSelectionListener(ListSelectionListener x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSelectionInterval(int index0, int index1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearSelection() {
		select=-1;
	}

	@Override
	public int getAnchorSelectionIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLeadSelectionIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxSelectionIndex() {
		return 1;
	}

	@Override
	public int getMinSelectionIndex() {
		return 0;
	}

	@Override
	public int getSelectionMode() {
		return 0;
	}

	@Override
	public boolean getValueIsAdjusting() {
		return false;
	}

	@Override
	public void insertIndexInterval(int index, int length, boolean before) {
		
	}

	@Override
	public boolean isSelectedIndex(int index) {
		if(select<0){
			return false;
		}else 
			return true;
	}

	@Override
	public boolean isSelectionEmpty() {
		if(select>=0){
			return false;
		}else 
			return true;
	}

	@Override
	public void removeIndexInterval(int index0, int index1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListSelectionListener(ListSelectionListener x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSelectionInterval(int index0, int index1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAnchorSelectionIndex(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLeadSelectionIndex(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectionInterval(int index0, int index1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectionMode(int selectionMode) {
		select=selectionMode;
	}

	@Override
	public void setValueIsAdjusting(boolean valueIsAdjusting) {
		// TODO Auto-generated method stub
		
	}

	


}
