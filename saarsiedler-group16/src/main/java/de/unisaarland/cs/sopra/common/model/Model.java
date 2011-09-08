package de.unisaarland.cs.sopra.common.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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
	private long meID;
	private Player me;
	
	/**
	 * @param worldRepresentation
	 * @param matchInformation
	 */
	public Model(WorldRepresentation worldRepresentation, MatchInformation matchInformation, long meID) {
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
		this.meID = meID;
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
		this.me = this.playerMap.get(meID);
	}
	
	/**
	 * @param numbers: int[] (set the numbers on the fields)
	 */
	public void setFieldNumbers(byte[] numbers) {
		if (numbers == null) throw new IllegalArgumentException();
		//TODO: ignore water and desert Fields
		Iterator<Field> iter = board.getFieldIterator();
		int i = 0;
		while(iter.hasNext()) {
			Field f= iter.next();
			if(f.getFieldType()!=FieldType.DESERT  &&	f.getFieldType()!=FieldType.WATER){
				f.setNumber(numbers[i++]);
			}
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

	/**
	 * @return return Set of Paths where you can build a Street, or null if non available
	 */
	@Override
	public Set<Path> buildableStreetPaths(Player player) {
		if (player==null) throw new IllegalArgumentException(player+ "is null");
		Iterator<Path> it = getPathIterator();
		Set<Path> res = new TreeSet<Path>();
		while(it.hasNext()){
			Path p = it.next();
			if(!p.hasStreet()){
				Set<Path> nachbarn = getPathsFromPath(p);
				for(Path n: nachbarn){
					if(p.hasStreet()	&&	p.getStreetOwner()==player){	//doppelte abfrage zur sicherheit
						res.add(p);
					}
				}
			}
		}
		if(res.isEmpty())	return null;
		
		return res;
	}

	/**
	 * @return return Set of Paths where you can build a Catapult, or null if non available
	 	 */
	@Override
	public Set<Path> buildableCatapultPaths(Player player) {
		if (player==null) throw new IllegalArgumentException(player+ "is null");
		Iterator<Intersection> it = getIntersectionIterator();
		Set<Path> res = new TreeSet<Path>();
		while(it.hasNext()){
			Intersection inter = it.next();
			if(inter.getBuildingType().equals(BuildingType.Town)	&&	inter.getOwner()==player){
				res.addAll(getPathsFromIntersection(inter));
			}
		}
		if(res.isEmpty())	return null;
		
		return res;
	}
	private int affordableThings(ResourcePackage price){
		ResourcePackage rp = getCurrentPlayer().getResources().copy();
		int ret = -1;
		while(!rp.hasNegativeResources()){
			ret++;
			rp.add(price);
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#affordableSettlements(de.unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public int affordableSettlements(BuildingType buildingType) {
		return affordableThings(buildingType.getPrice());
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#affordableStreets()
	 */
	@Override
	public int affordableStreets() {
		return affordableThings(Street.getPrice());
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#affordableCatapultBuild()
	 */
	@Override
	public int affordableCatapultBuild() {
		return affordableThings(Catapult.getBuildingprice());
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#affordableCatapultAttack()
	 */
	@Override
	public int affordableCatapultAttack() {
		return affordableThings(Catapult.getAttackcatapultprice());
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#affordableSettlementAttack()
	 */
	@Override
	public int affordableSettlementAttack() {
		return affordableThings(Catapult.getAttackbuildingprice());
	}

	/**
	 * @return shows how many Streets are affordable ,when you buy a Village first
	 */
	@Override
	public int affordablePathsAfterVillage(int villageCount) {
		ResourcePackage rp = getCurrentPlayer().getResources().copy();
		rp.add(BuildingType.Village.getPrice());
		if(rp.hasNegativeResources()) return 0;
		int ret = -1;
		while(!rp.hasNegativeResources()){
			ret++;
			rp.add(Street.getPrice());
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#attackableSettlements(de.unisaarland.cs.sopra.common.model.Player, de.unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public Set<Intersection> attackableSettlements(Player player, BuildingType buildingType) {
		throw new UnsupportedOperationException();
		//TODO sollte jmd anders machen, da ichs getestet hab
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#attackableCatapults(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Path> attackableCatapults(Player player) {
		throw new UnsupportedOperationException();
		//TODO sollte jmd anders machen, da ichs getestet hab
		// gilt auch fuer folgende
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
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getFieldResource(de.unisaarland.cs.sopra.common.model.Field)
	 */
	@Override
	public Resource getFieldResource(Field field) {
		Resource res=null;
		switch(field.getFieldType()){
			case FIELDS: 	res=Resource.GRAIN;	break;
			case FOREST: 	res=Resource.LUMBER;break;
			case MOUNTAINS: res=Resource.ORE;	break;
			case HILLS:		res=Resource.BRICK;	break;
			case PASTURE:	res=Resource.WOOL;	break;
			default: break;
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getFieldsFromField(de.unisaarland.cs.sopra.common.model.Field)
	 */
	@Override
	public Set<Field> getFieldsFromField(Field field) {
		return board.getFieldsFromField(field);
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getFieldsFromIntersection(de.unisaarland.cs.sopra.common.model.Intersection)
	 */
	@Override
	public Set<Field> getFieldsFromIntersection(Intersection intersection) {
		return board.getFieldsFromIntersection(intersection);
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getFieldsFromPath(de.unisaarland.cs.sopra.common.model.Path)
	 */
	@Override
	public Set<Field> getFieldsFromPath(Path path) {
		return board.getFieldsFromPath(path);
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getIntersectionsFromField(de.unisaarland.cs.sopra.common.model.Field)
	 */
	@Override
	public Set<Intersection> getIntersectionsFromField(Field field) {
		return board.getIntersectionsFromField(field);
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getIntersectionsFromIntersection(de.unisaarland.cs.sopra.common.model.Intersection)
	 */
	@Override
	public Set<Intersection> getIntersectionsFromIntersection(Intersection intersection) {
		return board.getIntersectionsFromIntersection(intersection);
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getIntersectionsFromPath(de.unisaarland.cs.sopra.common.model.Path)
	 */
	@Override
	public Set<Intersection> getIntersectionsFromPath(Path path) {
		return board.getIntersectionsFromPath(path);
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getPathsFromField(de.unisaarland.cs.sopra.common.model.Field)
	 */
	@Override
	public Set<Path> getPathsFromField(Field field) {
		return board.getPathsFromField(field);
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getPathsFromIntersection(de.unisaarland.cs.sopra.common.model.Intersection)
	 */
	@Override
	public Set<Path> getPathsFromIntersection(Intersection intersection) {
		return board.getPathsFromIntersection(intersection);
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getPathsFromPath(de.unisaarland.cs.sopra.common.model.Path)
	 */
	@Override
	public Set<Path> getPathsFromPath(Path path) {
		return board.getPathsFromPath(path);
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
	public void matchStart(long[] players, byte[] numbers) {
		if (players==null || numbers==null) throw new IllegalArgumentException(players +" oder "+ numbers+ " is null");
		setTableOrder(players);
		setFieldNumbers(numbers);
		//TODO evtl noch mehr zu tun als das
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#catapultMoved(de.unisaarland.cs.sopra.common.model.Location, de.unisaarland.cs.sopra.common.model.Location, boolean)
	 */
	@Override
	public void catapultMoved(Location sourcePath, Location destinationPath, boolean fightOutCome) {
		if (sourcePath==null || destinationPath==null) throw new IllegalArgumentException("iwas is null");
		Set<Intersection> iset1 = getIntersectionsFromPath(getPath(sourcePath));
		Set<Intersection> iset2 = getIntersectionsFromPath(getPath(destinationPath));
		Intersection interBetweenPaths = null;
		//finds the intersection between both paths
		for (Intersection inter1 : iset1) {
			for (Intersection inter2 : iset2) {
				if(inter1 ==inter2) interBetweenPaths=inter1;
			}
		}
		if(interBetweenPaths.hasOwner() && interBetweenPaths.getOwner()!=getPath(sourcePath).getCatapultOwner()){
			throw new IllegalStateException("Catapult kann nicht durch feindliche Siedlungen hindurch");
		}
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
