package model;

import de.unisaarland.cs.sopra.common.ModelObserver;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class TestModelObserver implements ModelObserver {

	public boolean eventNewRoundCalled;
	public boolean eventNewRoundItsMyTurn;
	public boolean eventRobberCalled;
	public ResourcePackage eventTradeCalled;
	public Path updatePathCalled;
	public Intersection updateIntersectionCalled;
	public Field updateFieldCalled;
	public boolean updateResourcesCalled;
	public boolean updateVictoryPointsCalled;
	public boolean updateCatapultCountCalled;
	public BuildingType updateSettlementCountCalled;
	public boolean updateTradePossibilities;
	public boolean eventPlayerLeftCalled;
	private boolean initTurn;
	private boolean receiveNames;
	private boolean eventMatchEndCalled;
	
		@Override
		public void updatePath(Path path) {
			this.updatePathCalled = path;
		}

		@Override
		public void updateIntersection(Intersection intersection) {
			this.updateIntersectionCalled = intersection;
		}

		@Override
		public void updateField(Field field) {
			this.updateFieldCalled = field;
		}

		@Override
		public void updateResources() {
			this.updateResourcesCalled = true;
		}

		@Override
		public void updateVictoryPoints() {
			this.updateVictoryPointsCalled = true;
		}

		@Override
		public void updateCatapultCount() {
			this.updateCatapultCountCalled = true;
		}

		@Override
		public void updateSettlementCount(BuildingType buildingType) {
			this.updateSettlementCountCalled = buildingType;
		}

		@Override
		public void updateTradePossibilities() {
			this.updateTradePossibilities = true;
		}

		@Override
		public void eventRobber() {
			this.eventRobberCalled = true;
		}

		@Override
		public void eventNewRound(int number) {
			this.eventNewRoundCalled = true;
		}

		@Override
		public void eventTrade(ResourcePackage resourcePackage) {
			this.eventTradeCalled = resourcePackage;
		}

		@Override
		public void eventPlayerLeft(long playerID) {
			this.eventPlayerLeftCalled = true;
		}

		@Override
		public void initTurn() {
			this.setInitTurn(true);
		}

		public void setInitTurn(boolean initTurn) {
			this.initTurn = initTurn;
		}

		public boolean isInitTurn() {
			return initTurn;
		}

		public void setReceiveNames(boolean receiveNames) {
			this.receiveNames = receiveNames;
		}

		public boolean isReceiveNames() {
			return receiveNames;
		}

		@Override
		public void eventMatchEnd(long winnerID) {
			this.setEventMatchEndCalled(true);
		}

		public boolean isEventMatchEndCalled() {
			return eventMatchEndCalled;
		}

		public void setEventMatchEndCalled(boolean eventMatchEndCalled) {
			this.eventMatchEndCalled = eventMatchEndCalled;
		}

	
}
