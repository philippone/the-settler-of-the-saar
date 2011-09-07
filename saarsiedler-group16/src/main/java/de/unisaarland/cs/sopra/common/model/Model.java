package de.unisaarland.cs.sopra.common.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
	private boolean reversedPlayersList;
	private Player me;
	
	/**
	 * @param worldRepresentation
	 * @param matchInformation
	 */
	public Model(WorldRepresentation worldRepresentation, MatchInformation matchInformation, Player me) {
		this.board = new Board(worldRepresentation);
		this.modelObserver = new LinkedList<ModelObserver>();
		this.maxBuilding = new TreeMap<BuildingType,Integer>();
		this.maxBuilding.put(BuildingType.Village, worldRepresentation.getMaxVillages());
		this.maxBuilding.put(BuildingType.Town, worldRepresentation.getMaxTowns());
		this.maxCatapult = worldRepresentation.getMaxCatapults();
		this.initVillages = worldRepresentation.getInitVillages();
		for(int i = 0; i < worldRepresentation.getNumPlayerConfigs(); i++) {
			if(worldRepresentation.getNumPlayers(i) == matchInformation.getNumPlayers())
				this.maxVictoryPoints = worldRepresentation.getVictoryPoints(i);
		}
		if(this.maxVictoryPoints == 0) throw new IllegalStateException();
		this.me = me;
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
	 * @return Point of field
	 */
	public static Point getLocation(Field field) {
		return field.getLocation();
	}
	
	/**
	 * @return Location of Path 
	 */
	public static Location getLocation(Path path) {
		return path.getLocation();
	}
	
	/**
	 * @return Location of Intersection
	 */
	public static Location getLocation(Intersection intersection) {
		return intersection.getLocation();
	}
	
	/**
	 * @return Locations of the Paths in the List
	 */
	public static List<Location> getLocationList(List<Path> pathlist) {
		if (pathlist == null) throw new IllegalArgumentException();
		List<Location> tmp = new LinkedList<Location>();
		for(Path act : pathlist) {
			tmp.add(act.getLocation());
		}
		return tmp;
	}
	
	/**
	 * @return current Player
	 */
	public Player getCurrentPlayer() {
		return this.players.get( (this.round % this.players.size()) - 1 );
	}
	
	/**
	 * @param player
	 * @return List<List<Path>>
	 */
	public List<List<Path>> calculateLongestRoads(Player player) {
		if (player == null) throw new IllegalArgumentException();
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param playerIDs (set the "Table Order")
	 */
	public void setTableOrder(long[] playerIDs) {
		if (playerIDs == null) throw new IllegalArgumentException();
		this.players = new LinkedList<Player>();
		for(long act : playerIDs) {
			Player player = new Player();
			this.playerMap.put(act,player);
			this.players.add(player);
		}
	}
	
	/**
	 * @param numbers: int[] (set the numbers on the fields)
	 */
	public void setFieldNumbers(int[] numbers) {
		if (numbers == null) throw new IllegalArgumentException();
		Iterator<Field> iter = board.getFieldIterator();
		int i = 0;
		while(iter.hasNext()) {
			iter.next().setNumber(numbers[i++]);
		}
	}
	
	/**
	 * @param intersection (split the longestclaimedRoad at the Location of Intersection)
	 */
	public void updateLongestRoad(Intersection intersection) {
		if (intersection == null) throw new IllegalArgumentException();
		List<Path> tmp = new LinkedList<Path>();
		for(Path act : this.longestClaimedRoad) {
			if(board.getIntersectionsFromPath(act).contains(intersection)) {
				tmp.add(act);
				this.longestClaimedRoad.removeAll(tmp);
				this.longestClaimedRoad = tmp.size() < this.longestClaimedRoad.size() ? tmp : this.longestClaimedRoad;
			}
			tmp.add(act);
		}
	}
	
	/**
	 * @return The list of ModelObservers
	 */
	public List<ModelObserver> getModelObservers() {
		return this.modelObserver;
	}
	
	/**
	 * @return The List of Player sorted in TableOrder
	 */
	public List<Player> getTableOrder() {
		return this.players;
	}
	
	/**
	 * @return The Map containing the mapping of playerID -> Player
	 */
	public Map<Long,Player> getPlayerMap() {
		return this.playerMap;
	}
	
	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getPath(de.unisaarland.cs.sopra.common.model.Location)
	 */
	public Path getPath(Location location) {
		if (location == null) throw new IllegalArgumentException();
		return board.getPath(location);
	}
	
	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getIntersection(de.unisaarland.cs.sopra.common.model.Location)
	 */
	public Intersection getIntersection(Location location) {
		if (location == null) throw new IllegalArgumentException();
		return board.getIntersection(location);
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getMaxBuilding(de.unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public int getMaxBuilding(BuildingType buildingType) {
		if (buildingType == null) throw new IllegalArgumentException();
		return this.getMaxBuilding(buildingType);
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getRound()
	 */
	@Override
	public int getRound() {
		return this.round;
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
	public void longestRoadClaimed(List<Location> road) throws IllegalStateException{
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

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getField(de.unisaarland.cs.sopra.common.model.Point)
	 */
	@Override
	public Field getField(Point p) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getMaxCatapult()
	 */
	@Override
	public int getMaxCatapult() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getInitVillages()
	 */
	@Override
	public int getInitVillages() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#buildableVillageIntersections(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Intersection> buildableVillageIntersections(Player player) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#buildableTownIntersections(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Intersection> buildableTownIntersections(Player player) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void returnResources(int lumber, int brick, int wool, int grain, int ore) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setMe(Player me) {
		this.me = me;
	}

	@Override
	public Player getMe() {
		return me;
	}

}
