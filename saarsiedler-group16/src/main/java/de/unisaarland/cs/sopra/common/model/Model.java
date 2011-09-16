package de.unisaarland.cs.sopra.common.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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

public class Model implements ModelReader, ModelWriter {

	private Board board;
	private List<Player> players;
	private Map<Long, Player> playerMap;
	private int round;
	private List<ModelObserver> modelObserver;
	private Map<BuildingType, Integer> maxBuilding;
	private int maxCatapult;
	private ResourcePackage lastTrade;
	private int initVillages;
	private List<Path> longestClaimedRoad;
	private int maxVictoryPoints;
	private boolean reversedPlayersList;
	private long meID;
	private Player me;
	private int initPlayer = 0;						//akt player in der initPhase
	private Intersection initLastVillageIntersection;	//fuer initPhase zur berechnung der erlaubeten street(welche dann den current player durchwechselt)

	/**
	 * @param worldRepresentation
	 * @param matchInformation
	 */
	public Model(WorldRepresentation worldRepresentation,
			MatchInformation matchInformation, long meID) {
		this.board = new Board(worldRepresentation);
		this.modelObserver = new LinkedList<ModelObserver>();
		this.maxBuilding = new TreeMap<BuildingType, Integer>();
		this.maxBuilding.put(BuildingType.Village,
				worldRepresentation.getMaxVillages());
		this.maxBuilding.put(BuildingType.Town,
				worldRepresentation.getMaxTowns());
		this.maxCatapult = worldRepresentation.getMaxCatapults();
		this.initVillages = worldRepresentation.getInitVillages();
		for (int i = 0; i < worldRepresentation.getNumPlayerConfigs(); i++) {
			if (worldRepresentation.getNumPlayers(i) == matchInformation
					.getNumPlayers())
				this.maxVictoryPoints = worldRepresentation.getVictoryPoints(i);
		}
		if (this.maxVictoryPoints == 0)
			throw new IllegalStateException();
		for (int i = 0; i < worldRepresentation.getNumHarbors(); i++) {
			board.getPath(
					new Location(worldRepresentation.getHarborRow(i),
							worldRepresentation.getHarborCol(i),
							worldRepresentation.getHarborDir(i)))
					.setHarborType(
							HarborType.convert(worldRepresentation
									.getHarborType(i)));
		}
		this.meID = meID;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#addModelObserver(de.unisaarland.cs.sopra.common.ModelObserver)
	 */
	public void addModelObserver(ModelObserver modelObserver) {
		if (modelObserver == null)
			throw new IllegalArgumentException();
		if (this.modelObserver.contains(modelObserver)) {
			return;
		} else
			this.modelObserver.add(modelObserver);
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#removeModelObserver(de.unisaarland.cs.sopra.common.ModelObserver)
	 */
	public void removeModelObserver(ModelObserver modelObserver) {
		if (modelObserver == null)
			throw new IllegalArgumentException();
		if (this.modelObserver.size() == 0) {
			throw new IllegalArgumentException();
		} else
			this.modelObserver.remove(modelObserver);
	}

	/**
	 * @return Point of field
	 */
	public static Point getLocation(Field field) {
		if (field == null)
			throw new IllegalArgumentException();
		return field.getLocation();
	}

	/**
	 * @return Location of Path
	 */
	public static Location getLocation(Path path) {
		if (path == null)
			throw new IllegalArgumentException();
		return path.getLocation();
	}

	/**
	 * @return Location of Intersection
	 */
	public static Location getLocation(Intersection intersection) {
		if (intersection == null)
			throw new IllegalArgumentException();
		return intersection.getLocation();
	}

	/**
	 * @return Locations of the Paths in the List
	 */
	public static List<Location> getLocationListPath(Collection<Path> list) {
		if (list == null)
			throw new IllegalArgumentException();
		List<Location> tmp = new LinkedList<Location>();
		for (Path act : list) {
			tmp.add(act.getLocation());
		}
		return tmp;
	}
	
	/**
	 * @return Locations of the Intersections in the List
	 */
	public static List<Location> getLocationListIntersection(Collection<Intersection> list) {
		if (list == null)
			throw new IllegalArgumentException();
		List<Location> tmp = new LinkedList<Location>();
		for (Intersection act : list) {
			tmp.add(act.getLocation());
		}
		return tmp;
	}
	
	/**
	 * @return Locations of the Paths in the List
	 */
	public static List<Point> getLocationListField(Collection<Field> list) {
		if (list == null)
			throw new IllegalArgumentException();
		List<Point> tmp = new LinkedList<Point>();
		for (Field act : list) {
			tmp.add(act.getLocation());
		}
		return tmp;
	}

	/**
	 * @return current Player
	 */
	public Player getCurrentPlayer() {
		if (getRound() == 0) {
			return players.get(initPlayer);
		} else {
			return this.players.get((this.round - 1) % this.players.size());
		}
	}

	public List<List<Path>> calculateLongestRoads(Player player) {
		if (player == null)
			throw new IllegalArgumentException();
		Set<Path> pathList = getStreets(player);
		// all Paths that player owns
		List<List<Path>> roadList = new ArrayList<List<Path>>();
		// all roads known (none)
		for (Path p : pathList) {
			List<Path> road = new ArrayList<Path>();
			road.add(p);
			roadList.add(road);
		}
		// all roads contain only one path

		roadList = continueAllRoads(roadList, player);
		// we obtain all finished roads
		
		roadList=keepOnlyLongestRoads(roadList);	
		roadList=rankRoads(roadList,player);
		
		List<List<Path>> reversedRoadList =new ArrayList<List<Path>>();
		for(List<Path> road: roadList){
			reversedRoadList.add(reverseRoad(road));
		}
		roadList.addAll(reversedRoadList);
		return roadList;
	}
	
	private List<Path> reverseRoad(List<Path> road){
		List<Path> reversedRoad =new ArrayList<Path>();
		reversedRoad.addAll(road);
		Collections.reverse(reversedRoad);
		return reversedRoad;
	}
	
	private List<List<Path>> rankRoads(List<List<Path>> roadList,Player player){
		List<List<Path>> roadList1=new ArrayList<List<Path>>();
		for (List<Path>road: roadList){
			road=rankRoad(road, player);
			roadList1.add(road);
		}
		return roadList1;
	}
	
	private List<Path> rankRoad(List<Path> road,Player player){
		List<Path> road1=new ArrayList<Path>();
		List<Intersection>roadExtremities=searchRoadExtremities(road, player);
		// returning the intersections trough what we can continue the road
		Intersection i=roadExtremities.get(0);
		// we'll rank the road from this extremity
		Set<Path>sp=getPathsFromIntersection(i);
		Path p1=road.get(0);
		for (Path p2:sp){
			if (road.contains(p2)) p1=p2;
		}
		road1.add(p1);
		// we'll rank the road from this path p1
		while (road1.size()<road.size()){
			
			for (Path p:road){
				if (p!=null && getPathsFromPath(p1).contains(p) && !road1.contains(p)) {
					road1.add(p);
					p1=p;
				}
			}
		}
		return road1;
	}
	
	private List<List<Path>> continueAllRoads(List<List<Path>> roadList,Player player){
		List<List<Path>> rList;
		// the roads we'll obtain while lengthening one
		List<List<Path>> roadList1;
		// the roads we'll add to the roadList (lengthened roads)
		List<List<Path>> roadList2;
		// the roads we'll remove from the roadList (not finished roads)
		boolean a;
		roadList1 = new ArrayList<List<Path>>();
		roadList2 = new ArrayList<List<Path>>();
		for (List<Path> road : roadList) {
			// we check for all the roads
			// if we cannot lengthen them
			if (road != null) {
				rList = continueRoad(road, player);
				// returning all lengthened roads starting from this road
				if (!rList.isEmpty()) {
					roadList2.add(road);
					// we'll remove this road
					// since we have at least a new longer version
					a = false;
					for (List<Path> r : rList) {
						if (r != null) {
							for (List<Path> r1 : roadList)
								a = a | r1.containsAll(r);
							for (List<Path> r1 : roadList1)
								a = a | r1.containsAll(r);
							if (!a)
								roadList1.add(r);
							// if we didn't have this one, then we add it
						}
					}
				}
			}
		}

		if (!roadList1.isEmpty())
			roadList1 = continueAllRoads(roadList1, player);
		// if we haven't found any new longer road, we stop
		// else we must see if the new ones cannot be lengthened too
		roadList.addAll(roadList1);
		roadList.remove(roadList2);
		// we update the roadList
		// removing short roads that have been lengthened
		// adding longer versions of these roads

		return roadList;
	}

	private List<List<Path>> keepOnlyLongestRoads(List<List<Path>> roadList) {
		List<List<Path>> roadList1 = new ArrayList<List<Path>>();
		int maxsize = 5;
		for (List<Path> road1 : roadList) {
			if (road1 != null)
				maxsize = Math.max(maxsize, road1.size());
			// what's the maximum size of the roads
		}
		for (List<Path> road2 : roadList) {
			if (road2 != null && road2.size() >= maxsize)
				roadList1.add(road2);
			// we take only the longest road(s)and we return these
		}
		return roadList1;
	}

	/**
	 * @param road
	 * @param roadList
	 * @return
	 */
	private List<List<Path>> continueRoad(List<Path> road, Player player) {
		List<List<Path>> rList = new ArrayList<List<Path>>();
		List<Intersection> roadExtremities = searchRoadExtremities(road, player);
		// returning the intersections trough what we can continue the road
		for (Intersection i : roadExtremities) {
			if (i != null)
				rList.addAll(continueRoadThroughIntersection(i, player, road));
		}
		return rList;
		// returning all lengthened roads
	}

	/**
	 * @param p
	 * @param road
	 * @param roadList
	 * @return
	 */
	private List<Intersection> searchRoadExtremities(List<Path> road,
			Player player) {
		List<Intersection> si = new ArrayList<Intersection>();
		for (Path p : road) {
			Set<Intersection> si1 = getIntersectionsFromPath(p);
			for (Intersection i : si1) {
				if (i != null && isExtremityOfRoad(i, road)
						&& (!(i.hasOwner()) || (i.getOwner() == player)))
					si.add(i);
			}
			// meaning we can continue a road through an intersection and the
			// paths it leads to
			// only when this intersection is already the extremity of the road
			// and only when it is free or owned by the player itself
		}
		return si;
	}

	/**
	 * @param i
	 * @param p
	 * @param road
	 * @param roadList
	 * @return
	 */
	private List<List<Path>> continueRoadThroughIntersection(Intersection i,
			Player player, List<Path> road) {
		Set<Path> sp = getPathsFromIntersection(i);
		List<List<Path>> rList = new ArrayList<List<Path>>();
		for (Path p1 : sp) {
			// we create a new road1 containing the road
			// we try to add the new path
			List<Path> road1 = addPathToRoad(copy(road), player, p1);
			if (road1 != null)
				rList.add(road1);
		}
		return rList;
	}

	/**
	 * @param i
	 * @param road
	 * @return
	 */
	private boolean isExtremityOfRoad(Intersection i, List<Path> road) {
		Set<Path> sp = getPathsFromIntersection(i);
		int a = 0;
		for (Path p : sp) {
			if (road.contains(p))
				a++;
		}
		return (a == 1);
		// an intersection is the extremity of the road when it has only a
		// neighbor path in the road
	}

	/**
	 * @param road
	 * @return
	 */
	private List<Path> copy(List<Path> road) {
		List<Path> road1 = new ArrayList<Path>();
		for (Path p : road)
			road1.add(p);
		return road1;
	}

	/**
	 * @param road
	 * @param player
	 * @param p
	 * @param roadList
	 * @return
	 */
	private List<Path> addPathToRoad(List<Path> road, Player player, Path p) {
		if (p.hasStreet() && p.getStreetOwner() == player
				&& !(road.contains(p))) {
			// meaning if this path is owned by player and not already in the
			// road
			// then we can continue the road while adding this path
			road.add(p);
			return road;
		}
		return null;
	}

	/**
	 * @param playerIDs
	 *            (set the "Table Order")
	 */
	public void setTableOrder(long[] playerIDs) {
		if (playerIDs == null)
			throw new IllegalArgumentException();
		this.players = new LinkedList<Player>();
		this.playerMap = new HashMap<Long, Player>();
		for (long act : playerIDs) {
			Player player = new Player();
			this.playerMap.put(act, player);
			this.players.add(player);
		}
		this.me = this.playerMap.get(meID);
	}

	/**
	 * @param numbers
	 *            : int[] (set the numbers on the fields)
	 */
	public void setFieldNumbers(byte[] numbers) {
		if (numbers == null)
			throw new IllegalArgumentException();
		Iterator<Field> iter = board.getFieldIterator();
		int i = 0;
		while (iter.hasNext()) {
			Field f = iter.next();
			if (f.getFieldType() != FieldType.DESERT
					&& f.getFieldType() != FieldType.WATER) {
				f.setNumber(numbers[i++]);
			}
		}
	}

	/**
	 * @param intersection
	 *            (split the longestclaimedRoad at the Location of Intersection)
	 */
	public void updateLongestRoad(Intersection intersection) {
		if (intersection == null)
			throw new IllegalArgumentException();
		
		if (this.longestClaimedRoad != null) {
			List<Path> tmp = new LinkedList<Path>();
			for (Path act : this.longestClaimedRoad) {
				if (board.getIntersectionsFromPath(act).contains(intersection)) {
					tmp.add(act);
					break;
				}
				tmp.add(act);
			}
			this.longestClaimedRoad.removeAll(tmp);
			this.longestClaimedRoad = tmp.size() > this.longestClaimedRoad.size() ? tmp : this.longestClaimedRoad;
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

	@Override
	public Map<Long, Player> getPlayerMap() {
		return this.playerMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getPath(de.unisaarland
	 * .cs.sopra.common.model.Location)
	 */
	public Path getPath(Location location) {
		if (location == null)
			throw new IllegalArgumentException();
		return board.getPath(location);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getIntersection(de.
	 * unisaarland.cs.sopra.common.model.Location)
	 */
	public Intersection getIntersection(Location location) {
		if (location == null)
			throw new IllegalArgumentException();
		return board.getIntersection(location);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getMaxBuilding(de.
	 * unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public int getMaxBuilding(BuildingType buildingType) {
		if (buildingType == null)
			throw new IllegalArgumentException();
		return this.maxBuilding.get(buildingType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getRound()
	 */
	@Override
	public int getRound() {
		return this.round;
	}

	/**
	 * @return return Set of Paths where you can build a Street
	 */
	@Override
	public Set<Path> buildableStreetPaths(Player player) {
		if (player == null)	throw new IllegalArgumentException(player + "is null");
		Set<Path> res = new HashSet<Path>();
		
		if(getRound()==0){
			for(Path noStreet : getPathsFromIntersection(initLastVillageIntersection)){
				if(!noStreet.hasStreet())
					for (Field f : board.getFieldsFromPath(noStreet)){
						if (f.getFieldType() != FieldType.WATER) res.add(noStreet);
					}
			}
		}else{
			Iterator<Path> it = getPathIterator();
			while (it.hasNext()) {
				Path p = it.next();
				Set<Field> fieldSet = getFieldsFromPath(p);
				boolean hasLand = false;
				for (Field f : fieldSet) {
					if ((f.getFieldType() != FieldType.WATER)) {
						hasLand = true;
					}
				}
				
				if (!p.hasStreet()) {
					Set<Path> nachbarn = getPathsFromPath(p);
					for (Path n : nachbarn) {
						Intersection betweenBothPathsIntersection=null;
						// to find the intersection between the 2 path
						for(Intersection k :getIntersectionsFromPath(p)){
							for(Intersection m :getIntersectionsFromPath(n)){
								if(k.equals(m)) betweenBothPathsIntersection = k;
							}
						}
						if (n.hasStreet() && n.getStreetOwner() == player
								&& (!betweenBothPathsIntersection.hasOwner() || betweenBothPathsIntersection.getOwner()== player)){
							if (hasLand)
								res.add(p);
						}
					}
				}
			}
		}
		return res;
	}

	/**
	 * @return return Set of Paths where you can build a Catapult
	 */
	@Override
	public Set<Path> buildableCatapultPaths(Player player) {
		if (player == null)
			throw new IllegalArgumentException(player + "is null");
		Iterator<Intersection> it = getIntersectionIterator();
		Set<Path> res = new HashSet<Path>();
		while (it.hasNext()) {
			Intersection inter = it.next();
			if (inter.hasOwner()
					&& inter.getBuildingType().equals(BuildingType.Town)
					&& inter.getOwner() == player) {
				res.addAll(getPathsFromIntersection(inter));
			}
		}
		return res;
	}

	private int affordableThings(ResourcePackage price) {
		ResourcePackage rp = getCurrentPlayer().getResources().copy();
		int ret = -1;
		while (!rp.hasNegativeResources()) {
			ret++;
			rp.add(price);
		}

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#affordableSettlements
	 * (de.unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public int affordableSettlements(BuildingType buildingType) {
		return affordableThings(buildingType.getPrice());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#affordableStreets()
	 */
	@Override
	public int affordableStreets() {
		return affordableThings(Street.getPrice());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#affordableCatapultBuild
	 * ()
	 */
	@Override
	public int affordableCatapultBuild() {
		return affordableThings(Catapult.getBuildingprice());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#affordableCatapultAttack
	 * ()
	 */
	@Override
	public int affordableCatapultAttack() {
		return affordableThings(Catapult.getAttackcatapultprice());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#affordableSettlementAttack
	 * ()
	 */
	@Override
	public int affordableSettlementAttack() {
		return affordableThings(Catapult.getAttackbuildingprice());
	}

	/**
	 * @return shows how many Streets are affordable ,when you buy a Village
	 *         first
	 */
	@Override
	public int affordablePathsAfterVillage(int villageCount) {
		ResourcePackage rp = getCurrentPlayer().getResources().copy();
		rp.add(BuildingType.Village.getPrice());
		if (rp.hasNegativeResources())
			return 0;
		int ret = -1;
		while (!rp.hasNegativeResources()) {
			ret++;
			rp.add(Street.getPrice());
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#attackableSettlements
	 * (de.unisaarland.cs.sopra.common.model.Player,
	 * de.unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public Set<Intersection> attackableSettlements(Player player,
			BuildingType buildingType) {
		Set<Intersection> attackableSettlements = new HashSet<Intersection>();
		Set<Path> sp = getCatapults(player);
		for (Path p : sp) {
			Set<Intersection> si = getIntersectionsFromPath(p);
			for (Intersection i : si) {
				if (i.hasOwner() && i.getOwner() != player
						&& i.getBuildingType() == buildingType) {
					attackableSettlements.add(i);
				}
			}
		}
		return attackableSettlements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#attackableCatapults(
	 * de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Path> attackableCatapults(Player player) {
		if (player == null)
			throw new IllegalArgumentException();
		Set<Path> attackableCatapults = new HashSet<Path>();
		Set<Path> sp = getCatapults(player);
		for (Path p : sp) {
			Set<Path> sp1 = getPathsFromPath(p);
			for (Path p1 : sp1) {
				if (p1.hasCatapult() && p1.getCatapultOwner() != player) {
					Set<Intersection> iset1 = getIntersectionsFromPath(p);
					Set<Intersection> iset2 = getIntersectionsFromPath(p1);
					Intersection interBetweenPaths = null;
					// finds the intersection between both paths
					for (Intersection inter1 : iset1) {
						for (Intersection inter2 : iset2) {
							if (inter1 == inter2)
								interBetweenPaths = inter1;
						}
					}
					if (!interBetweenPaths.hasOwner() || interBetweenPaths.getOwner() == player) {
						attackableCatapults.add(p1);
					}
					
				}
			}
		}
		return attackableCatapults;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getStreets(de.unisaarland
	 * .cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Path> getStreets(Player player) {
		if (player == null)
			throw new IllegalArgumentException();
		Iterator<Path> ip = getPathIterator();
		Set<Path> sp = new HashSet<Path>();
		Path p;
		while (ip.hasNext()) {
			p = ip.next();
			if (p.hasStreet() && p.getStreetOwner() == player)
				sp.add(p);
		}
		return sp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getSettlements(de.
	 * unisaarland.cs.sopra.common.model.Player,
	 * de.unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public Set<Intersection> getSettlements(Player player,
			BuildingType buildingType) {
		if (player == null | buildingType == null)
			throw new IllegalArgumentException();
		Iterator<Intersection> ii = getIntersectionIterator();
		Set<Intersection> si = new HashSet<Intersection>();
		Intersection i;
		while (ii.hasNext()) {
			i = ii.next();
			if (i.hasOwner() && i.getOwner() == player && i.getBuildingType() == buildingType)
				si.add(i);
		}
		return si;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getCatapults(de.unisaarland
	 * .cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Path> getCatapults(Player player) {
		if (player == null)
			throw new IllegalArgumentException();
		Iterator<Path> ip = getPathIterator();
		Set<Path> sp = new HashSet<Path>();
		Path p;
		while (ip.hasNext()) {
			p = ip.next();
			if (p.hasCatapult() && p.getCatapultOwner() == player)
				sp.add(p);
		}
		return sp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getLongestClaimedRoad()
	 */
	@Override
	public List<Path> getLongestClaimedRoad() {
		return longestClaimedRoad;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getMaxVictoryPoints()
	 */
	@Override
	public int getMaxVictoryPoints() {
		return maxVictoryPoints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getCurrentVictoryPoints
	 * (de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public int getCurrentVictoryPoints(Player player) {
		if (player == null)
			throw new IllegalArgumentException();
		return player.getVictoryPoints();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#canPlaceRobber()
	 */
	@Override
	public Set<Field> canPlaceRobber() {
		Iterator<Field> itf = getFieldIterator();
		Set<Field> sf = new HashSet<Field>();
		Field f;
		while (itf.hasNext()) {
			f = itf.next();
			if (f.hasRobber())
				continue;
			if (f.getFieldType() == FieldType.WATER) {
				Set<Field> sf1 = getFieldsFromField(f);
				for (Field f1 : sf1) {
					if (f1.getFieldType() != FieldType.WATER)
						sf.add(f);
				}
			} else
				sf.add(f);
		}
		return sf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getRobberFields()
	 */
	@Override
	public Set<Field> getRobberFields() {
		Iterator<Field> itf = getFieldIterator();
		Set<Field> sf = new HashSet<Field>();
		Field f;
		while (itf.hasNext()) {
			f = itf.next();
			if (f.hasRobber())
				sf.add(f);
		}
		return sf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getFieldIterator()
	 */
	@Override
	public Iterator<Field> getFieldIterator() {
		return board.getFieldIterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getPathIterator()
	 */
	@Override
	public Iterator<Path> getPathIterator() {
		return board.getPathIterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getIntersectionIterator
	 * ()
	 */
	@Override
	public Iterator<Intersection> getIntersectionIterator() {
		return board.getIntersectionIterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getHarborIntersections()
	 */
	@Override
	public Set<Intersection> getHarborIntersections() {
		Iterator<Path> ip = getPathIterator();
		Set<Intersection> si = new HashSet<Intersection>();
		Path p;
		while (ip.hasNext()) {
			p = ip.next();
			if (p.getHarborType() != null) {
				Set<Intersection> si1 = getIntersectionsFromPath(p);
				for (Intersection i : si1) {
					si.add(i);
				}
			}
		}
		return si;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getHarborType(de.unisaarland
	 * .cs.sopra.common.model.Intersection)
	 */
	@Override
	public HarborType getHarborType(Intersection intersection) {
		Set<Path> sp = getPathsFromIntersection(intersection);
		HarborType hb;
		for (Path p : sp) {
			hb = p.getHarborType();
			if (hb != null)
				return hb;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getHarborTypes(de.
	 * unisaarland.cs.sopra.common.model.Player)
	 */
	@Override //TODO evtl ueberarbeiten da langsam
	public Set<HarborType> getHarborTypes(Player player) {
		Set<HarborType> sht = new HashSet<HarborType>();
		Iterator<Path> iter = getPathIterator();
		while (iter.hasNext()) {
			Path p = iter.next();
			if (p.getHarborType() != null) {
				for (Field f : getFieldsFromPath(p)) {
					if (f.hasRobber()) {
						sht.remove(p);
						break;
					}
					else {
						for (Intersection i : getIntersectionsFromPath(p)) {
							if (i.hasOwner() && i.getOwner() == getCurrentPlayer())
								sht.add(p.getHarborType());
						}
						
					}
				}
			}
		}
		return sht;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getResources()
	 */
	@Override
	public ResourcePackage getResources() {
		return me.getResources();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getFieldResource(de.
	 * unisaarland.cs.sopra.common.model.Field)
	 */
	@Override
	public Resource getFieldResource(Field field) {
		Resource res = null;
		switch (field.getFieldType()) {
		case FIELDS:
			res = Resource.GRAIN;
			break;
		case FOREST:
			res = Resource.LUMBER;
			break;
		case MOUNTAINS:
			res = Resource.ORE;
			break;
		case HILLS:
			res = Resource.BRICK;
			break;
		case PASTURE:
			res = Resource.WOOL;
			break;
		default:
			break;
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getFieldsFromField(de
	 * .unisaarland.cs.sopra.common.model.Field)
	 */
	@Override
	public Set<Field> getFieldsFromField(Field field) {
		return board.getFieldsFromField(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getFieldsFromIntersection
	 * (de.unisaarland.cs.sopra.common.model.Intersection)
	 */
	@Override
	public Set<Field> getFieldsFromIntersection(Intersection intersection) {
		return board.getFieldsFromIntersection(intersection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getFieldsFromPath(de
	 * .unisaarland.cs.sopra.common.model.Path)
	 */
	@Override
	public Set<Field> getFieldsFromPath(Path path) {
		return board.getFieldsFromPath(path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getIntersectionsFromField
	 * (de.unisaarland.cs.sopra.common.model.Field)
	 */
	@Override
	public Set<Intersection> getIntersectionsFromField(Field field) {
		return board.getIntersectionsFromField(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#
	 * getIntersectionsFromIntersection
	 * (de.unisaarland.cs.sopra.common.model.Intersection)
	 */
	@Override
	public Set<Intersection> getIntersectionsFromIntersection(
			Intersection intersection) {
		return board.getIntersectionsFromIntersection(intersection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getIntersectionsFromPath
	 * (de.unisaarland.cs.sopra.common.model.Path)
	 */
	@Override
	public Set<Intersection> getIntersectionsFromPath(Path path) {
		return board.getIntersectionsFromPath(path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getPathsFromField(de
	 * .unisaarland.cs.sopra.common.model.Field)
	 */
	@Override
	public Set<Path> getPathsFromField(Field field) {
		return board.getPathsFromField(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getPathsFromIntersection
	 * (de.unisaarland.cs.sopra.common.model.Intersection)
	 */
	@Override
	public Set<Path> getPathsFromIntersection(Intersection intersection) {
		return board.getPathsFromIntersection(intersection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getPathsFromPath(de.
	 * unisaarland.cs.sopra.common.model.Path)
	 */
	@Override
	public Set<Path> getPathsFromPath(Path path) {
		return board.getPathsFromPath(path);
	}

	/*
	 * (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#newRound(int)
	 */
	@Override
	public void newRound(int number) {
		if (number < 2 || number > 12)
			throw new IllegalArgumentException();
		
		if (round == 0 && reversedPlayersList) {
			Collections.reverse(players);
			reversedPlayersList = false;
		}
		this.round++;

		if (number == 7) {
			for (ModelObserver ob : modelObserver) {
				ob.eventRobber();
			}
		}
		else {
			for (Iterator<Field> itFields = getFieldIterator(); itFields
					.hasNext();) {
				Field field = itFields.next();
				if (field.getNumber() == number) {
					for (Intersection inter : getIntersectionsFromField(field)) {
						if (inter.hasOwner()) {
							inter.generateGain(field.getResource(number));
						}
					}
				}
			}
		}
		for (ModelObserver ob : modelObserver) {
			ob.updateResources();
		}
		for (ModelObserver ob : modelObserver) {
			ob.eventNewRound();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelWriter#attackSettlement(de.
	 * unisaarland.cs.sopra.common.model.Location,
	 * de.unisaarland.cs.sopra.common.model.Location,
	 * de.unisaarland.cs.st.saarsiedler.comm.results.AttackResult)
	 */
	@Override
	public void attackSettlement(Location catapultPath,
			Location settlementIntersection, AttackResult result) {
		if (catapultPath == null || settlementIntersection == null
				|| result == null)
			throw new IllegalArgumentException("iwas is null");
		Path path_catapult = getPath(catapultPath);
		Intersection inter_settlement = getIntersection(settlementIntersection);
		if (!path_catapult.hasCatapult() || !inter_settlement.hasOwner())
			throw new IllegalArgumentException(
					"kein Catapult oder Siedlung vorhanden");
		switch (result) {
		case DRAW:
			path_catapult.getCatapultOwner().modifyResources(
					Catapult.getAttackbuildingprice());
			for (ModelObserver ob : modelObserver) {
				ob.updateResources();
			}
			break;
		case DEFEAT:
			path_catapult.getCatapultOwner().modifyResources(
					Catapult.getAttackbuildingprice());
			path_catapult.removeCatapult();
			for (ModelObserver ob : modelObserver) {
				ob.updateResources();
				ob.updateCatapultCount();
				ob.updatePath(path_catapult);
			}
			break;
		case SUCCESS:
			Player owner = path_catapult.getCatapultOwner();
			Player ownerBuilding = inter_settlement.getOwner();
			if (inter_settlement.getBuildingType() == BuildingType.Village) {
				if ( (getSettlements(owner, BuildingType.Village).size() < getMaxBuilding(BuildingType.Village)) ||
					 ((getSettlements(owner, BuildingType.Village).size() >= getMaxBuilding(BuildingType.Village)) && owner == ownerBuilding) ) {
					//Village and attacker < maxVillages or
					//Village and attacker = maxVillages but attacker = defender
					inter_settlement.getOwner().setVictoryPoints(inter_settlement.getOwner().getVictoryPoints()-1);
					inter_settlement.removeBuilding();
					inter_settlement.createBuilding(BuildingType.Village, owner);
					inter_settlement.getOwner().setVictoryPoints(inter_settlement.getOwner().getVictoryPoints()+1);
				} else {
					//Village and attacker > maxVillage
					inter_settlement.getOwner().setVictoryPoints(inter_settlement.getOwner().getVictoryPoints()-1);
					inter_settlement.removeBuilding();
				}
			} else {
				if (getSettlements(ownerBuilding, BuildingType.Village).size() < getMaxBuilding(BuildingType.Village)) {
					//Town and defender < maxVillage
					inter_settlement.getOwner().setVictoryPoints(inter_settlement.getOwner().getVictoryPoints()-1);
					inter_settlement.removeBuilding();
					inter_settlement.createBuilding(BuildingType.Village, ownerBuilding);
				} else {
					if (getSettlements(owner, BuildingType.Village).size() < getMaxBuilding(BuildingType.Village)) {
						//Town and defender > maxVillages && attacker < maxVillages 
						inter_settlement.getOwner().setVictoryPoints(inter_settlement.getOwner().getVictoryPoints()-2);
						inter_settlement.removeBuilding();
						inter_settlement.createBuilding(BuildingType.Village, ownerBuilding);
						inter_settlement.getOwner().setVictoryPoints(inter_settlement.getOwner().getVictoryPoints()+1);
					}
					else {
						//Town and defender > maxVillages && attacker > maxVillages 
						inter_settlement.getOwner().setVictoryPoints(inter_settlement.getOwner().getVictoryPoints()-2);
						inter_settlement.removeBuilding();
					}
				}
			}
			owner.modifyResources(Catapult.getAttackbuildingprice());
			for (ModelObserver ob : modelObserver) {
				ob.updateResources();
				ob.updateIntersection(inter_settlement);
				ob.updateSettlementCount(BuildingType.Village);
				ob.updateSettlementCount(BuildingType.Town);
			}
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelWriter#buildCatapult(de.unisaarland
	 * .cs.sopra.common.model.Location, boolean)
	 */
	@Override
	public void buildCatapult(Location destination, boolean fightOutCome) {
		if (destination == null)
			throw new IllegalArgumentException(destination + " is null");
		Path dest = getPath(destination);
		Set<Intersection> nachbarSet = getIntersectionsFromPath(dest);
		boolean nachbarTown = false;
		for (Intersection intersection : nachbarSet) {
			if (intersection.getBuildingType() == BuildingType.Town)
				nachbarTown = true;
		}
		if (!nachbarTown)
			throw new IllegalArgumentException(
					"Keine Stadt in der Naehe(Location mies)");
		if (fightOutCome) {
			if (dest.hasCatapult()) {
				dest.removeCatapult();
			}
			dest.createCatapult(getCurrentPlayer());
		} else {
			if (!dest.hasCatapult())
				throw new IllegalStateException(
						"Wird nicht gebaut, obwohl kein Gegner vorhanden ist");
			if (!getCurrentPlayer().checkResourcesSufficient(
					Catapult.getBuildingprice()))
				throw new IllegalArgumentException(
						"Player has no resources to built a Catapult");
		}
		getCurrentPlayer().modifyResources(Catapult.getBuildingprice());
		for (ModelObserver ob : modelObserver) {
			ob.updateResources();
			ob.updateCatapultCount();
			ob.updatePath(dest);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelWriter#buildStreet(de.unisaarland
	 * .cs.sopra.common.model.Location)
	 */
	@Override
	public void buildStreet(Location destination) {
		if (destination == null)	throw new IllegalArgumentException(destination + " is null");
		//TODO: path nur an village oder town!
		
		//TODO: evtl darf path in der initialisierungsphase nur an der letzten gebauten intersection gebaut werden für den fall auf angrenzen der "initLastVillageIntersection" prüfen
		Path dest = getPath(destination);
		if (dest.hasStreet())	throw new IllegalArgumentException(	"Strasse bereits vorhanden und gehört: "+ dest.getStreetOwner() + " und nicht: "+ getCurrentPlayer());
		if (getRound() != 0) {
			if (getCurrentPlayer() == me){
					if(getCurrentPlayer().checkResourcesSufficient(Street.getPrice())) {
						me.modifyResources(Street.getPrice());
						Set<Path> buildableStreets = buildableStreetPaths(getCurrentPlayer());
						if (!buildableStreets.contains(dest))throw new IllegalStateException("Keine Nachbarstrassen oder WasserFeld");
					}else{
						throw new IllegalStateException("not enough resources");
					}
			}
		} else {
			boolean hasLand = false;
			Set<Field> fieldSet = getFieldsFromPath(dest);
			for (Field f : fieldSet) {
				if ((f.getFieldType() != FieldType.WATER)) {
					hasLand = true;
				} 
			}
			if (!hasLand)
				throw new IllegalStateException(
						"Path ist nur von Wasser umgeben!");
		}
		getPath(destination).createStreet(getCurrentPlayer());
		for (ModelObserver ob : modelObserver) {
			ob.updateResources();
			ob.updatePath(dest);
		}
		if (round == 0) {
			if (initPlayer == players.size() - 1) {
				initPlayer = -1;
				java.util.Collections.reverse(players);
				reversedPlayersList = !reversedPlayersList;
			}
			initPlayer++;
			if (getStreets(getCurrentPlayer()).size() != initVillages) {
				for (ModelObserver act : modelObserver) {
					if (getCurrentPlayer() == getMe())
						act.initTurn();
				}
			}
			else {
				for (Field f : getFieldsFromIntersection(initLastVillageIntersection)) {
					initLastVillageIntersection.generateGain(f.getResource());
				}
				if (reversedPlayersList){
					java.util.Collections.reverse(players);
					reversedPlayersList = false;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#buildSettlement(de.
	 * unisaarland.cs.sopra.common.model.Location,
	 * de.unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public void buildSettlement(Location location, BuildingType buildingType) {
		if (location == null)
			throw new IllegalArgumentException(location + " is null");
		Intersection i = getIntersection(location);
		if (getRound() == 0) {
			Set<Intersection> s = buildableVillageIntersections(getCurrentPlayer());
			if(s.contains(i)) { // wenn i buildable, do it
				i.createBuilding(buildingType, getCurrentPlayer());
				getCurrentPlayer().setVictoryPoints(getCurrentPlayer().getVictoryPoints() + 1);	
				for (ModelObserver ob : modelObserver) {
					ob.updateSettlementCount(buildingType);
					ob.updateVictoryPoints();
					ob.updateIntersection(i);
				}
				if (me == getCurrentPlayer()) {
					initLastVillageIntersection = i;
				}
			} else
			throw new IllegalStateException("geb wurde nicht gebaut, da i nicht in buildableIn...");
		} else {
			if (isBuildable(i, buildingType) && (isAffordable(buildingType)) && getSettlements(getCurrentPlayer(), buildingType).size() < getMaxBuilding(buildingType)) {
				getCurrentPlayer().modifyResources(buildingType.getPrice());
				i.createBuilding(buildingType, getCurrentPlayer());
				getCurrentPlayer().setVictoryPoints(getCurrentPlayer().getVictoryPoints() + 1);
				for (ModelObserver ob : modelObserver) {
					ob.updateResources();
					if (buildingType == BuildingType.Town)
						ob.updateSettlementCount(BuildingType.Village);
					ob.updateSettlementCount(buildingType);
					ob.updateVictoryPoints();
					ob.updateIntersection(i);
				}
				updateLongestRoad(i); //TODO: evtl auch in initialisierungsphase
			} else
				throw new IllegalArgumentException(
						String.format(
								"Das Gebaeude wurde nicht gebaut. isBuildable:%b, isAffordable:%b, hasNotMaxBuildingType:%b",
								isBuildable(i, buildingType),
								isAffordable(buildingType),
								(getSettlements(getCurrentPlayer(), buildingType).size() < getMaxBuilding(buildingType))));
		}
	}

	private boolean isBuildable(Intersection i, BuildingType buildingType) {
		Set<Intersection> si;
		if (buildingType == BuildingType.Village) {
			si = buildableVillageIntersections(getCurrentPlayer());
			if (si.contains(i))
				return true;
			return false;
		} else if (buildingType == BuildingType.Town) {
			si = buildableTownIntersections(getCurrentPlayer());
			if (si.contains(i))
				return true;
			return false;
		}
		throw new IllegalArgumentException("Es fehlt den BuildingType");
	}

	private boolean isAffordable(BuildingType buildingType) {
		int a = affordableSettlements(buildingType);
		if (a > 0)
			return true;
		throw new IllegalArgumentException(
				"Nicht genug Ressourcen, um es zu bauen");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelWriter#longestRaodClaimed(java
	 * .util.List)
	 */
	@Override
	public void longestRoadClaimed(List<Location> road)
			throws IllegalStateException {
		//funktion evtl fehlerhaft!
		// TODO (Philipp)
		if (road.size() >= 5 && ( (longestClaimedRoad != null && road.size() > longestClaimedRoad.size()) || (longestClaimedRoad == null) ) ) {
			List<Path> lr = new LinkedList<Path>();
			boolean rightPlayer = false;
			int i = 1;
			for (Location l : road) {
				Path p = getPath(l);
				Set<Path> s = getPathsFromPath(p);
				if (s.contains(getPath(road.get(i)))) {
					if (i < road.size() - 1) {
						i++;
					}
					else {
						Path tmp = getPath(road.get(i));
						if (tmp.getStreetOwner().equals(getCurrentPlayer())) {
							rightPlayer = true;
						} else {
							rightPlayer = false;
							break;
						}
						lr.add(p);
						lr.add(tmp);
						break;
					}
					lr.add(p);
					if (p.getStreetOwner().equals(getCurrentPlayer())) {
						rightPlayer = true;
					} else {
						rightPlayer = false;
						break;
					}
				}
				else throw new IllegalArgumentException("Road is not properly join"); 
			}
			if (rightPlayer) {
				this.longestClaimedRoad = lr;
			} else
				throw new IllegalArgumentException("not the right Player");

		} else {
			throw new IllegalArgumentException("Roadsize <5 or not longer then longestClaimedRoad");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelWriter#catapultMoved(de.unisaarland
	 * .cs.sopra.common.model.Location,
	 * de.unisaarland.cs.sopra.common.model.Location, boolean)
	 */
	@Override
	public void catapultMoved(Location sourcePath, Location destinationPath,
			boolean fightOutCome) {
		if (sourcePath == null || destinationPath == null)
			throw new IllegalArgumentException("iwas is null");
		Player owner = getPath(sourcePath).getCatapultOwner();
		Path path_source = getPath(sourcePath);
		Path path_dest = getPath(destinationPath);
		Set<Intersection> iset1 = getIntersectionsFromPath(path_source);
		Set<Intersection> iset2 = getIntersectionsFromPath(path_dest);
		Intersection interBetweenPaths = null;
		// finds the intersection between both paths
		for (Intersection inter1 : iset1) {
			for (Intersection inter2 : iset2) {
				if (inter1 == inter2)
					interBetweenPaths = inter1;
			}
		}
		if (interBetweenPaths.hasOwner()
				&& interBetweenPaths.getOwner() != owner) {
			throw new IllegalStateException(
					"Catapult kann nicht durch feindliche Siedlungen hindurch");
		}
		
		if (fightOutCome) {
			path_source.removeCatapult();
			path_dest.createCatapult(owner);
		}
		else {
			if (!path_dest.hasCatapult())
				throw new IllegalArgumentException("No Catapult on Dest_Path");
			path_source.removeCatapult();
		}

		if (!(owner.checkResourcesSufficient(Catapult.getAttackcatapultprice())))
			throw new IllegalStateException(
					"not enough money on the bankaccount!");
		owner.modifyResources(Catapult.getAttackcatapultprice());

		for (ModelObserver ob : modelObserver) {
			ob.updatePath(path_source);
			ob.updatePath(path_dest);
			ob.updateResources();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#playerLeft(long)
	 */
	@Override
	public void playerLeft(long playerID) {
		for (ModelObserver ob : modelObserver) {
			ob.eventPlayerLeft(playerID);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelWriter#robberMoved(de.unisaarland
	 * .cs.sopra.common.model.Point, de.unisaarland.cs.sopra.common.model.Point,
	 * long, de.unisaarland.cs.sopra.common.model.Resource)
	 */
	@Override
	public void robberMoved(Point sourceField, Point destinationField,
			long victimPlayer, Resource stolenResource) {
		// TODO (Philipp)
		// Wenn Wasser drumherum
		boolean hasLand = false;
		for (Field f : getFieldsFromField(getField(destinationField))) {
			if (f.getFieldType() != FieldType.WATER) {
				hasLand = true;
			}
		}
		if (!hasLand)
			throw new IllegalArgumentException("Can not put a robber on water");
		else {
			getField(sourceField).setRobber(false);
			getField(destinationField).setRobber(true);
			if (playerMap.containsKey(victimPlayer)) {
				playerMap.get(victimPlayer).getResources().modifyResource(stolenResource, -1);
				getCurrentPlayer().getResources().modifyResource(stolenResource, 1);
				for (ModelObserver ob : modelObserver) {
					if (me == getCurrentPlayer())
						ob.updateResources();
				}
			}
			for (ModelObserver ob : modelObserver) {
				ob.updateField(getField(sourceField));
				ob.updateField(getField(destinationField));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#tradeOffer(int,
	 * int, int, int, int)
	 */
	@Override
	public void tradeOffer(int lumber, int brick, int wool, int grain, int ore) {
		// TODO: darf ich das ueberhaupt handeln, verhaeltnisse (Philipp)
		lastTrade = new ResourcePackage(lumber, brick, wool, grain, ore);
		for (ModelObserver mo : modelObserver) {
			mo.eventTrade(lastTrade);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#respondTrade(long)
	 */
	@Override
	public void respondTrade(long playerID) {
		// TODO (Philipp)
		if (playerID < -2)
			throw new IllegalArgumentException();
		else if (playerID == -1) {
			return;
		} else if (playerID == -2) {
			getCurrentPlayer().modifyResources(lastTrade);
			for (ModelObserver mo : modelObserver) {
				mo.updateResources();
			}
		} else {
			getCurrentPlayer().modifyResources(lastTrade);
			playerMap.get(playerID).modifyResources(
					lastTrade.neagateResourcePackage());
			for (ModelObserver mo : modelObserver) {
				mo.updateResources();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#getField(de.unisaarland
	 * .cs.sopra.common.model.Point)
	 */
	@Override
	public Field getField(Point p) {
		return board.getField(p);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getMaxCatapult()
	 */
	@Override
	public int getMaxCatapult() {
		return maxCatapult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getInitVillages()
	 */
	@Override
	public int getInitVillages() {
		return initVillages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#
	 * buildableVillageIntersections
	 * (de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Intersection> buildableVillageIntersections(Player player){
		// TODO initial klappt evtl IMMER noch nicht
		if (player == null)
			throw new IllegalArgumentException(player + " is null");

		Set<Intersection> ret = new HashSet<Intersection>();
		if (getRound() == 0) { // wenn runde 0 dann nur Nachbarn und wasser
								// wichtig
			for (Iterator<Intersection> iterator = getIntersectionIterator(); iterator
					.hasNext();) {
				Intersection intersection = iterator.next();
				boolean buildable = true;
				for (Intersection nachbar : getIntersectionsFromIntersection(intersection)) {
					if (nachbar.hasOwner())
						buildable = false;
				}
				// ist intersection bereits bebaut?
				if(intersection.hasOwner()) buildable=false;
				boolean hasLand = false;
				for (Field nachbarField : getFieldsFromIntersection(intersection)) {
					if (nachbarField.getFieldType() != FieldType.WATER)
						hasLand = true;
				}
				if (buildable && hasLand)
					ret.add(intersection);
			}
		} else { // wenn round!=0 nur nachbarStreet und nachbarField wichtig
			Set<Path> streetSet = getStreets(player);
			for (Path path : streetSet) {
				Set<Intersection> pathInt = getIntersectionsFromPath(path);
				for (Intersection intersection : pathInt) {
					boolean buildable = true;
					for (Intersection nachbar : getIntersectionsFromIntersection(intersection)) {
						if (nachbar.hasOwner())
							buildable = false;
					}
					//falls bereits village vorhanden
					if(intersection.hasOwner()) buildable=false;
					if (buildable)
						ret.add(intersection);
				}
			}
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisaarland.cs.sopra.common.model.ModelReader#buildableTownIntersections
	 * (de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Intersection> buildableTownIntersections(Player player) {
		if (player == null)
			throw new IllegalArgumentException(player + " is null");
		return getSettlements(player, BuildingType.Village);

	}

	@Override
	public void returnResources(int lumber, int brick, int wool, int grain,
			int ore) {
		ResourcePackage robberPackage = new ResourcePackage(-lumber, -brick,
				-wool, -grain, -ore);
		if (!me.checkResourcesSufficient(robberPackage))
			throw new IllegalStateException(
					"Spieler kann nicht mehr Resourcen abgeben als es hat");
		if (me.getResources().size() % 2 == 0) {
			// ResourcePackage gerade
			if (robberPackage.neagateResourcePackage().size() != (me
					.getResources().size()) / 2)
				throw new IllegalArgumentException();
		}
		// ungerade
		else {
			if (robberPackage.neagateResourcePackage().size() != (me
					.getResources().size() - 1) / 2)
				throw new IllegalArgumentException();
		}
		me.modifyResources(robberPackage);

		for (ModelObserver ob : modelObserver) {
			ob.updateResources();
		}
	}

	@Override
	public void setMe(Player me) {
		this.me = me;
	}

	@Override
	public Player getMe() {
		return me;
	}

	@Override
	public int getBoardWidth() {
		return board.getWidth();
	}

	@Override
	public int getBoardHeight() {
		return board.getHeight();
	}

	@Override
	public void matchStart(long[] players, byte[] number) {
		if (players == null || number == null)
			throw new IllegalArgumentException(players + " or " + number
					+ " are null");
		setTableOrder(players);
		setFieldNumbers(number);
		for (ModelObserver act : modelObserver) {
			if (getCurrentPlayer() == getMe()) act.initTurn();
		}
	}

}
