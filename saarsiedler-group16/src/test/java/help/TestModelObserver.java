package help;

import de.unisaarland.cs.sopra.common.ModelObserver;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class TestModelObserver implements ModelObserver {

	public Player eventNewRoundCalled;
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
		public void eventNewRound(Player player) {
			this.eventNewRoundCalled = player;
		}

		@Override
		public void eventTrade(ResourcePackage resourcePackage) {
			this.eventTradeCalled = resourcePackage;
		}

	
}
