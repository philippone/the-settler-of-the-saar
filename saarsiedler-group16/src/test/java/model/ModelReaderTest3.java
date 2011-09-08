package model;

import static org.junit.Assert.*;

import java.io.IOException;


import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Model;

import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class ModelReaderTest3 {

	private Model model;
	private ResourcePackage rp1;

	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel2();
		model.getCurrentPlayer().modifyResources(
				new ResourcePackage(3, 3, 2, 2, 4));

	}

	@Test
	public void testAffordableCatapultBuild() {
		if (model.affordableCatapultBuild() < 0) {
			throw new IllegalArgumentException();
		} else {
			rp1 = model.getCurrentPlayer().getResources().copy();
			assertEquals(2, model.affordableCatapultBuild());

			// check whether the players resourcePackage is intact
			assertEquals(rp1, model.getCurrentPlayer().getResources());
		}
	}

	@Test
	public void testAffordableCatapultAttack() {
		if (model.affordableCatapultAttack() < 0) {
			throw new IllegalArgumentException();
		} else {
			assertEquals(2, model.affordableCatapultAttack());
			rp1 = model.getCurrentPlayer().getResources().copy();
			// check whether the players resourcePackage is intact
			assertEquals(rp1, model.getCurrentPlayer().getResources());
		}
	}

	@Test
	public void testAffordableSettlementAttack() {
		if (model.affordableSettlementAttack() < 0) {
			throw new IllegalArgumentException();
		}

		assertEquals(4, model.affordableSettlementAttack());

		// check whether the players resourcePackage is intact
		assertEquals(rp1, model.getCurrentPlayer().getResources());

	}

	@Test
	public void testAffordablePathsAfterVillage() {
		if (model.affordablePathsAfterVillage(1) < 0) {
			throw new IllegalArgumentException();
		}
		rp1 = model.getCurrentPlayer().getResources().copy();
		assertEquals(2, model.affordablePathsAfterVillage(1));

		// check whether the players resourcePackage is intact

		assertEquals(rp1, model.getCurrentPlayer().getResources());
	}

	@Test
	public void testAffordableSettlementsVillage() {
		if (model.affordableSettlements(BuildingType.Village) < 0) {
			throw new IllegalArgumentException();
		}
		rp1 = model.getCurrentPlayer().getResources().copy();
		assertEquals(2, model.affordableSettlements(BuildingType.Village));

		// check whether the players resourcePackage is intact
		assertEquals(rp1, model.getCurrentPlayer().getResources());
	}

	@Test
	public void testAffordableSettlementsTowns() {
		if (model.affordableSettlements(BuildingType.Town) < 0) {
			throw new IllegalArgumentException();
		}
		rp1 = model.getCurrentPlayer().getResources().copy();
		assertEquals(1, model.affordableSettlements(BuildingType.Town));

		// check whether the players resourcePackage is intact
		assertEquals(rp1, model.getCurrentPlayer().getResources());
	}

	@Test
	public void testAffordableStreets() {
		if (model.affordableStreets() < 0) {
			throw new IllegalArgumentException();
		} else
			assertEquals(3, model.affordableStreets());

		// check whether the players resourcePackage is intact
		assertEquals(rp1, model.getCurrentPlayer().getResources());
	}

	@Test
	public void testAffordableTowns2() {
		model.getCurrentPlayer().modifyResources(
				new ResourcePackage(0, 0, 0, 2, 3));
		ResourcePackage rp = model.getCurrentPlayer().getResources();

		if (model.affordableSettlements(BuildingType.Town) < 0)
			throw new IllegalArgumentException();

		rp1 = model.getCurrentPlayer().getResources().copy();
		assertEquals(1, model.affordableSettlements(BuildingType.Town));
		// check whether the players resourcePackage is intact
		assertEquals(rp, model.getCurrentPlayer().getResources());
	}

	public void testAffordableTowns3() {
		model.getCurrentPlayer().modifyResources(
				new ResourcePackage(1, 0, 0, 2, 2));
		ResourcePackage rp = model.getCurrentPlayer().getResources();

		if (model.affordableSettlements(BuildingType.Town) < 0)
			throw new IllegalArgumentException();
		rp = model.getCurrentPlayer().getResources().copy();
		assertEquals(0, model.affordableSettlements(BuildingType.Town));
		// check whether the players resourcePackage is intact
		assertEquals(rp, model.getCurrentPlayer().getResources());
	}

}
