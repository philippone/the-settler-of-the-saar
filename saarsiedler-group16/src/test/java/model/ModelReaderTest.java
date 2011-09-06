package model;

import static org.junit.Assert.*;
import help.TestUtil;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.Model;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Point;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;



public class ModelReaderTest {
	
	Model model;
	
	@Before
	public void setUp() throws IOException {
		model = TestUtil.getStandardModel2();
	}
	
	@Test
	public void testGetInitBuilding(BuildingType buildingType) {
		
	}
	
	@Test
	public void testGetMaxBuilding(BuildingType buildingType) {
		
	}
	
	@Test
	public void testGetRound() {
		
	}
	
	@Test
	public void testBuildableIntersections(Player player) {
		
	}
	
	@Test
	public void testBuildableStreetPaths(Player player) {
		
	}
	
	@Test
	public void testBuildableCatapultPaths(Player player) {
		
	}
	
	@Test
	public void testAffordableSettlements(BuildingType buildingType){
		
	}
	
	@Test
	public void testAffordableStreets(){
		
	}
	
	@Test
	public void testAffordableCatapultBuild() {
		
	}
	
	@Test
	public void testAffordableCatapultAttack() {
		
	}
	
	@Test
	public void testAffordableSettlementAttack() {
		
	}
	
	@Test
	public void testAffordablePathsAfterVillage(int villageCount) {
		
	}
	
	@Test
	public void testAttackableSettlements(Player player, BuildingType buildingType) {
		
	}
	
	@Test
	public void testAttackableCatapults(Player player) {
		
	}
	
	@Test
	public void testGetStreets(Player player) {
		
	}
	
	@Test
	public void testGetSettlements(Player player, BuildingType buildingType) {
		
	}
	
	@Test
	public void testGetCatapults(Player player) {
		
	}
	
	@Test
	public void testGetLongestClaimedRoad() {
		
	}
	
	@Test
	public void testGetMaxVictoryPoints() {
		
	}
	
	@Test
	public void testGetCurrentVictoryPoints(Player player) {
		
	}
	
	@Test
	public void testCanPlaceRobber() {
		
	}
	
	@Test
	public void testGetRobberFields() {
		
	}
	
	@Test
	public void testGetFieldIterator() {
		
	}
	
	@Test
	public void testGetPathIterator() {
		
	}
	
	@Test
	public void testGetIntersectionIterator() {
		
	}
	
	@Test
	public void testGetHarborIntersections() {
		
	}
	
	@Test
	public void testGetHarborType(Intersection intersection) {
		
	}
	
	@Test
	public void testGetHarborTypes(Player player) {
		
	}
	
	@Test
	public void testGetResources() {
		
	}
	
	@Test
	public void testGetFieldNumber(Field field) {
		
	}
	
	@Test
	public void testGetFieldResource(Field field) {
		
	}
	
	@Test
	public void testGetFieldsFromField(Field field) {
		
	}
	
	@Test
	public void testGetFieldsFromIntersection(Intersection intersection) {
		
	}
	
	@Test
	public void testGetFieldsFromPath(Path path) {
		
	}
	
	@Test
	public void testGetIntersectionsFromField(Field field) {
		
	}
	
	@Test
	public void testGetIntersectionsFromIntersection(Intersection intersection) {
		
	}
	
	@Test
	public void testGetIntersectionsFromPath(Path path) {
		
	}
	
	@Test
	public void testGetPathsFromField(Field field) {
		
	}
	
	@Test
	public void testGetPathsFromIntersection(Intersection intersection) {
		
	}
	
	@Test
	public void testGetPathsFromPath(Path path) {
		
	}
	
	@Test
	public void testGetPath(Location loc) {
		
	}
	
	@Test
	public void testGetField(Point p) {
		
	}
	
	@Test
	public void testGetIntersection(Location location) {
		
	}

}
