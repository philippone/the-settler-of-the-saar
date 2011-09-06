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
	private List<Player> players;
	private Map<Long,Player> playerMap;
	private int round;
	private List<ModelObserver> modelObserver;
	private Map<BuildingType,Integer> maxBuilding;
	private int maxCatapult;
	private ResourcePackage lastTrade;
	private int initVillages;
	private List<Path> longestClaimedRoad;
	private int maxVictoryPoints;
	
	/**
	 * @param worldRepresentation
	 * @param matchInformation
	 */
	public Model(WorldRepresentation worldRepresentation, MatchInformation matchInformation) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param modelObserver
	 */
	public void addModelObserver(ModelObserver modelObserver) {
		if (modelObserver == null) throw new IllegalArgumentException();
		this.modelObserver.add(modelObserver);
	}
	
	/**
	 * @param modelObserver
	 */
	public void removeModelObserver(ModelObserver modelObserver) {
		if (modelObserver == null) throw new IllegalArgumentException();
		this.modelObserver.remove(modelObserver);
	}
	
	/**
	 * @param field
	 * @return Point of field
	 */
	public static Point getLocation(Field field) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param path
	 * @return Location of Path 
	 */
	public static Location getLocation(Path path) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param intersection
	 * @return Location of Intersection
	 */
	public static Location getLocation(Intersection intersection) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param pathlist
	 * @return
	 */
	public static List<Location> getLocationList(List<Path> pathlist) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @return current Player
	 */
	public Player getCurrentPlayer() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param player
	 * @return List<List<Path>>
	 */
	public List<List<Path>> calculateLongestRoads(Player player) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param playerIDs (set the "Table Order")
	 */
	public void setTableOrder(long[] playerIDs) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param numbers: int[] (set the numbers on the fields)
	 */
	public void setFieldNumbers(int[] numbers) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param intersection (split the longestclaimedRoad at the Location of Intersection)
	 */
	public void updateLongestRoad(Intersection intersection) {
		throw new UnsupportedOperationException();
	}
	
	
	/**
	 * @return
	 */
	public List<ModelObserver> getModelObservers() {
		return this.modelObserver;
	}
	
	public List<Player> getTableOrder() {
		return this.players;
	}
	
	public Path getPath(Location location) {
		return board.getPath(location);
	}
	
	public Intersection getIntersection(Location location) {
		return board.getIntersection(location);
	}

	
	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getInitBuilding(de.unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public int getInitBuilding(BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getMaxBuilding(de.unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public int getMaxBuilding(BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getRound()
	 */
	@Override
	public int getRound() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#buildableIntersections(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Intersection> buildableIntersections(Player player) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#buildableStreetPaths(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Path> buildableStreetPaths(Player player) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#buildableCatapultPaths(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Path> buildableCatapultPaths(Player player) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#affordableSettlements(de.unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public int affordableSettlements(BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#affordableStreets()
	 */
	@Override
	public int affordableStreets() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#affordableCatapultBuild()
	 */
	@Override
	public int affordableCatapultBuild() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#affordableCatapultAttack()
	 */
	@Override
	public int affordableCatapultAttack() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#affordableSettlementAttack()
	 */
	@Override
	public int affordableSettlementAttack() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#affordablePathsAfterVillage(int)
	 */
	@Override
	public int affordablePathsAfterVillage(int villageCount) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#attackableSettlements(de.unisaarland.cs.sopra.common.model.Player, de.unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public Set<Intersection> attackableSettlements(Player player, BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#attackableCatapults(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Path> attackableCatapults(Player player) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getStreets(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Path> getStreets(Player player) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getSettlements(de.unisaarland.cs.sopra.common.model.Player, de.unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public Set<Intersection> getSettlements(Player player, BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getCatapults(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Path> getCatapults(Player player) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getLongestClaimedRoad()
	 */
	@Override
	public List<Path> getLongestClaimedRoad() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getMaxVictoryPoints()
	 */
	@Override
	public int getMaxVictoryPoints() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getCurrentVictoryPoints(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public int getCurrentVictoryPoints(Player player) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#canPlaceRobber()
	 */
	@Override
	public Set<Field> canPlaceRobber() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getRobberFields()
	 */
	@Override
	public Set<Field> getRobberFields() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getFieldIterator()
	 */
	@Override
	public Iterator<Field> getFieldIterator() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getPathIterator()
	 */
	@Override
	public Iterator<Path> getPathIterator() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getIntersectionIterator()
	 */
	@Override
	public Iterator<Intersection> getIntersectionIterator() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getHarborIntersections()
	 */
	@Override
	public Set<Intersection> getHarborIntersections() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getHarborType(de.unisaarland.cs.sopra.common.model.Intersection)
	 */
	@Override
	public HarborType getHarborType(Intersection intersection) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getHarborTypes(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<HarborType> getHarborTypes(Player player) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getResources()
	 */
	@Override
	public ResourcePackage getResources() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getFieldNumber(de.unisaarland.cs.sopra.common.model.Field)
	 */
	@Override
	public int getFieldNumber(Field field) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getFieldResource(de.unisaarland.cs.sopra.common.model.Field)
	 */
	@Override
	public Resource getFieldResource(Field field) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getFieldsFromField(de.unisaarland.cs.sopra.common.model.Field)
	 */
	@Override
	public Set<Field> getFieldsFromField(Field field) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getFieldsFromIntersection(de.unisaarland.cs.sopra.common.model.Intersection)
	 */
	@Override
	public Set<Field> getFieldsFromIntersection(Intersection intersection) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getFieldsFromPath(de.unisaarland.cs.sopra.common.model.Path)
	 */
	@Override
	public Set<Field> getFieldsFromPath(Path path) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getIntersectionsFromField(de.unisaarland.cs.sopra.common.model.Field)
	 */
	@Override
	public Set<Intersection> getIntersectionsFromField(Field field) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getIntersectionsFromIntersection(de.unisaarland.cs.sopra.common.model.Intersection)
	 */
	@Override
	public Set<Intersection> getIntersectionsFromIntersection(Intersection intersection) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getIntersectionsFromPath(de.unisaarland.cs.sopra.common.model.Path)
	 */
	@Override
	public Set<Intersection> getIntersectionsFromPath(Path path) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getPathsFromField(de.unisaarland.cs.sopra.common.model.Field)
	 */
	@Override
	public Set<Path> getPathsFromField(Field field) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getPathsFromIntersection(de.unisaarland.cs.sopra.common.model.Intersection)
	 */
	@Override
	public Set<Path> getPathsFromIntersection(Intersection intersection) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getPathsFromPath(de.unisaarland.cs.sopra.common.model.Path)
	 */
	@Override
	public Set<Path> getPathsFromPath(Path path) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#newRound(int)
	 */
	@Override
	public void newRound(int number) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#attackSettlement(de.unisaarland.cs.sopra.common.model.Location, de.unisaarland.cs.sopra.common.model.Location, de.unisaarland.cs.st.saarsiedler.comm.results.AttackResult)
	 */
	@Override
	public void attackSettlement(Location catapultPath, Location settlementIntersection, AttackResult result) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#buildCatapult(de.unisaarland.cs.sopra.common.model.Location, boolean)
	 */
	@Override
	public void buildCatapult(Location destination, boolean fightOutCome) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#buildStreet(de.unisaarland.cs.sopra.common.model.Location)
	 */
	@Override
	public void buildStreet(Location destination) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#buildSettlement(de.unisaarland.cs.sopra.common.model.Location, de.unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public void buildSettlement(Location location, BuildingType buildingType) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#longestRaodClaimed(java.util.List)
	 */
	@Override
	public void longestRoadClaimed(List<Location> road) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#matchStart(long[], byte[])
	 */
	@Override
	public void matchStart(long[] players, byte[] number) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#catapultMoved(de.unisaarland.cs.sopra.common.model.Location, de.unisaarland.cs.sopra.common.model.Location, boolean)
	 */
	@Override
	public void catapultMoved(Location sourcePath, Location destinationPath, boolean fightOutCome) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#playerLeft(long)
	 */
	@Override
	public void playerLeft(long playerID) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#robberMoved(de.unisaarland.cs.sopra.common.model.Point, de.unisaarland.cs.sopra.common.model.Point, long, de.unisaarland.cs.sopra.common.model.Resource)
	 */
	@Override
	public void robberMoved(Point sourceField, Point destinationField, long victimPlayer, Resource stolenResource) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#tradeOffer(int, int, int, int, int)
	 */
	@Override
	public void tradeOffer(int lumber, int brick, int wool, int grain, int ore) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#respondTrade(long)
	 */
	@Override
	public void respondTrade(long playerID) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Field getField(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

}
