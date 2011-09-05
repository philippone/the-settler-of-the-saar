package de.unisaarland.cs.sopra.common.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.unisaarland.cs.sopra.common.ModelObserver;
import de.unisaarland.cs.st.saarsiedler.comm.MatchInformation;
import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;
import de.unisaarland.cs.st.saarsiedler.comm.results.AttackResult;

public class Model implements ModelReader, ModelWriter{

	private Board board;
	private Player[] players;
	private Map<Long,Player> playerMap;
	private int round;
	private List<ModelObserver> modelObserver;
	private Map<BuildingType,Integer> maxBuilding;
	private int maxCatapult;
	private ResourcePackage lastTrade;
	private int initVillages;
	private List<Path> longestClaimedRoad;
	private int maxVictoryPoints;
	
	public Model(WorldRepresentation worldRepresentation, MatchInformation matchInformation) {
		throw new UnsupportedOperationException();
	}
	
	public void addModelObserver(ModelObserver modelObserver) {
		if (modelObserver == null) throw new IllegalArgumentException();
		this.modelObserver.add(modelObserver);
	}
	
	public void removeModelObserver(ModelObserver modelObserver) {
		if (modelObserver == null) throw new IllegalArgumentException();
		this.modelObserver.remove(modelObserver);
	}
	
	public static Point getLocation(Field field) {
		throw new UnsupportedOperationException();
	}
	
	public static Location getLocation(Path path) {
		throw new UnsupportedOperationException();
	}
	
	public static Location getLocation(Intersection intersection) {
		throw new UnsupportedOperationException();
	}
	
	public Player getCurrentPlayer() {
		throw new UnsupportedOperationException();
	}
	
	public List<List<Path>> calculateLongestRoads(Player player) {
		throw new UnsupportedOperationException();
	}
	
	public void setTableOrder(long[] playerIDs) {
		throw new UnsupportedOperationException();
	}
	
	public void setFieldNumbers(int[] numbers) {
		throw new UnsupportedOperationException();
	}
	
	public void updateLongestRoad(Intersection intersection) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int getInitBuilding(BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMaxBuilding(BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRound() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Intersection> buildableIntersections(Player player) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Path> buildableStreetPaths(Player player) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Path> buildableCatapultPaths(Player player) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int affordableSettlements(BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int affordableStreets() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int affordableCatapultBuild() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int affordableCatapultAttack() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int affordableSettlementAttack() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int affordablePathsAfterVillage(int villageCount) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Intersection> attackableSettlements(Player player, BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Path> attackableCatapults(Player player) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Path> getStreets(Player player) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Intersection> getSettlements(Player player, BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Path> getCatapults(Player player) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Path> getLongestClaimedRoad() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMaxVictoryPoints() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getCurrentVictoryPoints(Player player) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Field> canPlaceRobber() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Field> getRobberFields() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<Field> getFieldIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<Path> getPathIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<Intersection> getIntersectionIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Intersection> getHarborIntersections() {
		throw new UnsupportedOperationException();
	}

	@Override
	public HarborType getHarborType(Intersection intersection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<HarborType> getHarborTypes(Player player) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResourcePackage getResources() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFieldNumber(Field field) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Resource getFieldResource(Field field) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Field> getFieldsFromField(Field field) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Field> getFieldsFromIntersection(Intersection intersection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Field> getFieldsFromPath(Path path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Intersection> getIntersectionsFromField(Field field) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Intersection> getIntersectionsFromIntersection(Intersection intersection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Intersection> getIntersectionsFromPath(Path path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Path> getPathsFromField(Field field) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Path> getPathsFromIntersection(Intersection intersection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Path> getPathsFromPath(Path path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void newRound(int number) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void attackSettlement(Location catapultPath, Location settlementIntersection, AttackResult result) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void buildCatapult(Location destination, boolean fightOutCome) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void buildStreet(Location destination) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void buildSettlement(Location location, BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void longestRaodClaimed(List<Location> road) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void matchStart(long[] players, byte[] number) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void catapultMoved(Location sourcePath, Location destinationPath, boolean fightOutCome) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void playerLeft(long playerID) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void robberMoved(Point sourceField, Point destinationField, long victimPlayer, Resource stolenResource) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void tradeOffer(int lumber, int brick, int wool, int grain, int ore) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void respondTrade(long playerID) {
		throw new UnsupportedOperationException();
	}

}
