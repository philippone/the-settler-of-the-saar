package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class HarborTypeTest {
	
	HarborType genHar;
	HarborType lumHar;
	HarborType briHar;
	HarborType wooHar;
	HarborType graHar;
	HarborType oreHar;
	
	@Before
	public void setUp() {
		genHar = HarborType.GENERAL_HARBOR;
		lumHar = HarborType.LUMBER_HARBOR;
		briHar = HarborType.BRICK_HARBOR;
		wooHar = HarborType.WOOL_HARBOR;
		graHar = HarborType.GRAIN_HARBOR;
		oreHar = HarborType.ORE_HARBOR;
	}
	
	@Test
	public void testTradePossible() {
		assertTrue(genHar.tradePossible(new ResourcePackage(-1, -1, -1, 0, 1)));
		assertFalse(lumHar.tradePossible(new ResourcePackage(-1, -1, -1, 1, 0)));
		assertFalse(briHar.tradePossible(new ResourcePackage(-1, -1, -1, 1, 0)));
		assertFalse(wooHar.tradePossible(new ResourcePackage(-1, -1, -1, 1, 0)));
		assertFalse(graHar.tradePossible(new ResourcePackage(-1, -1, -1, 1, 0)));
		assertFalse(oreHar.tradePossible(new ResourcePackage(-1, -1, -1, 1, 0)));
		assertTrue(lumHar.tradePossible(new ResourcePackage(-2, 0, 0, 0, 1)));
		assertTrue(briHar.tradePossible(new ResourcePackage(0, -2, 0, 1, 0)));
		assertTrue(wooHar.tradePossible(new ResourcePackage(0, 1, -2, 0, 0)));
		assertTrue(graHar.tradePossible(new ResourcePackage(1, 0, 0, -2, 0)));
		assertTrue(oreHar.tradePossible(new ResourcePackage(1, 0, 0, 0, -2)));
		assertFalse(lumHar.tradePossible(new ResourcePackage(-2, 0, 0, 0, 0)));
		assertFalse(oreHar.tradePossible(new ResourcePackage(-2, 0, 0, 0, -2)));
		assertFalse(genHar.tradePossible(new ResourcePackage(0, 0, 0, 0, 0)));
		assertFalse(wooHar.tradePossible(new ResourcePackage(0, 0, 0, 0, 0)));
	}
	
	@After
	public void tearDown() {
		genHar = lumHar = briHar = wooHar = graHar = oreHar = null;
	}

}
