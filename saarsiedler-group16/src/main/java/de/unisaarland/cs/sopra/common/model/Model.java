package de.unisaarland.cs.sopra.common.model;

import java.util.ArrayList;
import java.util.HashMap;
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
		Set<Path> pathList=getStreets(player);
		// all Paths that player owns
		List<List<Path>> roadList=new ArrayList<List<Path>>();
		// all roads known (none)
		for (Path p : pathList) {
			List<Path> road=new ArrayList<Path>();
			road.add(p);
		}
		// all roads contain only one path
		List<List<Path>> suppressedRoadList=new ArrayList<List<Path>>();
		for (List<Path> road : roadList){
			if (continueRoad(road, roadList)) suppressedRoadList.add(road);
			// if the road has been continued, new longer road(s)'d have been put in roadlist
			// we'll just remove the short one
		}
		for (List<Path> suppressedRoad : suppressedRoadList) roadList.remove(suppressedRoad);
		// now there's only finished roads
		int maxsize=5;
		for (List<Path> road : roadList){
			maxsize=Math.max(maxsize, road.size());
			// what's the maximum size of the roads
		}
		for (List<Path> road : roadList){
			if (road.size()<maxsize) roadList.remove(road);
			// only the longest road(s) stay here
		}
		return roadList;
	}
	
	private boolean continueRoad(List<Path> road, List<List<Path>> roadList){
		boolean b=false;
		for (Path p : road){
			b=b | continueRoadFromPath(p,road,roadList);
		}
		return b;
		// has the road been continued?
	}
	
	private boolean continueRoadFromPath(Path p, List<Path> road, List<List<Path>> roadList){
		Set<Intersection> si=getIntersectionsFromPath(p);
		Player player= p.getStreetOwner();
		boolean b=false;
		for (Intersection i : si){
			if (isExtremityOfRoad(i,road) && ((i.getOwner()==player) | !(i.hasOwner()))) b=b | continueRoadTroughIntersection(i,p,road,roadList);
			// meaning we can continue a road through an intersection and the paths it leads to
			// only when this intersection is already the extremity of the road
			// and only when it is free or owned by the player itself
		}
		return b;
	}
	
	private boolean continueRoadTroughIntersection(Intersection i,Path p, List<Path> road, List<List<Path>> roadList){
		Player player = p.getStreetOwner();
			Set<Path> sp=getPathsFromIntersection(i);
			sp.remove(p);
			// we don't want to go back
			boolean b=false;
			for (Path p1 : sp){
				// we create a new road1 containing the road
				// we try to add the new path
				List<Path> road1=copy(road);
				b=b | addPathToRoad(road1 , player, p1, roadList);
			}
			return b;
	}
	
	private boolean isExtremityOfRoad(Intersection i,List<Path>road){
		Set<Path> sp=getPathsFromIntersection(i);
		int a=0;
		for (Path p : sp){
			if (road.contains(p)) a++;		
		}
		return (a==1);
		// an intersection is the extremity of the road when it has only a neighbor path in the road
	}
	
	private List<Path> copy(List<Path> road){
		List<Path> road1=new ArrayList<Path>();
		for (Path p:road) road1.add(p);
		return road1;
	}
	
	private boolean addPathToRoad( List<Path> road, Player player, Path p ,List<List<Path>> roadList){
		if (p.getStreetOwner()==player && !(road.contains(p))) {
		// 	meaning if this path is owned by player and not already in the road
		// then we can continue the road while adding this path
		// then we add that new road in the roadList
		// the elder one'll be removed from the roadList since it has been continued
			road.add(p);
			roadList.add(road);
			return true;
		}
		return false;
	}
	
	/**
	 * @param playerIDs (set the "Table Order")
	 */
	public void setTableOrder(long[] playerIDs) {
		if (playerIDs == null) throw new IllegalArgumentException();
		this.players = new LinkedList<Player>();
		this.playerMap = new HashMap<Long,Player>();
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
		Iterator<Field> iter = board.getFieldIterator();
		int i = 0;
		while(iter.hasNext()) {
			Field f = iter.next();
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
	 * @return return Set of Paths where you can build a Street
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
		return res;
	}

	/**
	 * @return return Set of Paths where you can build a Catapult
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
		Set<Intersection> attackableSettlements=new TreeSet<Intersection>();
		Set<Path> sp=getCatapults(player);
		for (Path p: sp){
			Set<Intersection> si=getIntersectionsFromPath(p);
			for (Intersection i: si){
				if (i.hasOwner() && i.getOwner()!=player && i.getBuildingType()==buildingType) {
					attackableSettlements.add(i);
				}
			}
		}
		return attackableSettlements;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#attackableCatapults(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Path> attackableCatapults(Player player) {
		Set<Path> attackableCatapults=new TreeSet<Path>();
		Set<Path> sp=getCatapults(player);
		for (Path p: sp){
			Set<Path> sp1=getPathsFromPath(p);
			for (Path p1: sp1){
				if (p.hasCatapult() && p.getCatapultOwner()!=player) {
					attackableCatapults.add(p1);
				}
			}
		}
		return attackableCatapults;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getStreets(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Path> getStreets(Player player) {
		Iterator<Path>ip=getPathIterator();
		Set<Path>sp=new TreeSet<Path>();
		Path p;
		while (ip.hasNext()){
			p=ip.next();
			if (p.getStreetOwner()==player) sp.add(p);
		}
		return sp;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getSettlements(de.unisaarland.cs.sopra.common.model.Player, de.unisaarland.cs.sopra.common.model.BuildingType)
	 */
	@Override
	public Set<Intersection> getSettlements(Player player, BuildingType buildingType) {
		Iterator<Intersection>ii=getIntersectionIterator();
		Set<Intersection>si=new TreeSet<Intersection>();
		Intersection i;
		while (ii.hasNext()){
			i=ii.next();
			if (i.getOwner()==player && i.getBuildingType()==buildingType) si.add(i);
		}
		return si;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getCatapults(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Path> getCatapults(Player player) {
		Iterator<Path>ip=getPathIterator();
		Set<Path>sp=new TreeSet<Path>();
		Path p;
		while (ip.hasNext()){
			p=ip.next();
			if (p.getCatapultOwner()==player) sp.add(p);
		}
		return sp;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getLongestClaimedRoad()
	 */
	@Override
	public List<Path> getLongestClaimedRoad() {
		return longestClaimedRoad;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getMaxVictoryPoints()
	 */
	@Override
	public int getMaxVictoryPoints() {
		return maxVictoryPoints;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getCurrentVictoryPoints(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public int getCurrentVictoryPoints(Player player) {
		return player.getVictoryPoints();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#canPlaceRobber()
	 */
	@Override
	public Set<Field> canPlaceRobber() {
		Iterator<Field>itf=getFieldIterator();
		Set<Field>sf=new TreeSet<Field>();
		Field f;
		while (itf.hasNext()){
			f=itf.next();
			if (f.getFieldType()==FieldType.WATER){
				Set<Field>sf1=getFieldsFromField(f);
				for (Field f1:sf1){
					if(f1.getFieldType()!=FieldType.WATER) sf.add(f);
				}
			}
			else sf.add(f);
		}
		return sf;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getRobberFields()
	 */
	@Override
	public Set<Field> getRobberFields() {
		Iterator<Field>itf=getFieldIterator();
		Set<Field>sf=new TreeSet<Field>();
		Field f;
		while (itf.hasNext()){
			f=itf.next();
			if (f.hasRobber()) sf.add(f);
		}
		return sf;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getFieldIterator()
	 */
	@Override
	public Iterator<Field> getFieldIterator() {
		return board.getFieldIterator();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getPathIterator()
	 */
	@Override
	public Iterator<Path> getPathIterator() {
		return board.getPathIterator();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getIntersectionIterator()
	 */
	@Override
	public Iterator<Intersection> getIntersectionIterator() {
		return board.getIntersectionIterator();
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getHarborIntersections()
	 */
	@Override
	public Set<Intersection> getHarborIntersections() {
		Iterator<Path>ip=getPathIterator();
		Set<Intersection>si=new TreeSet<Intersection>();
		Path p;
		while (ip.hasNext()){
			p=ip.next();
			if (p.getHarborType()!=null){
				Set<Intersection> si1=getIntersectionsFromPath(p);
				for(Intersection i:si1){
					si.add(i);
				}
			}
		}
		return si;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getHarborType(de.unisaarland.cs.sopra.common.model.Intersection)
	 */
	@Override
	public HarborType getHarborType(Intersection intersection) {
		Set<Path>sp=getPathsFromIntersection(intersection);
		HarborType hb;
		for (Path p:sp){
			hb=p.getHarborType();
			if (hb!=null) return hb;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getHarborTypes(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<HarborType> getHarborTypes(Player player) {
		Set<Intersection>si=getHarborIntersections();
		Set<HarborType>sht=new TreeSet<HarborType>();
		for(Intersection i:si){
			if (i.getOwner()==player) sht.add(getHarborType(i));
		}
		return sht;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getResources()
	 */
	@Override
	public ResourcePackage getResources() {
		return me.getResources();
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
		if(number==1 || number>12) throw new IllegalArgumentException();
		
		if(number == 7){
			for(ModelObserver ob : modelObserver){
				ob.eventRobber();
			}
		}
		else{
			for (Iterator<Field> itFields = getFieldIterator(); itFields.hasNext();) {
				Field field =  itFields.next();
				if(field.getNumber()==number){	// nur zur Optimierung, streng genommen nicht noetig
					for(Intersection inter : getIntersectionsFromField(field)){
						if(inter.hasOwner()){
							inter.generateGain(field.getResource(number));
						}
					}
				}
			}
		for(ModelObserver ob : modelObserver){
			ob.updateResources();
		}
		}	
		
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#attackSettlement(de.unisaarland.cs.sopra.common.model.Location, de.unisaarland.cs.sopra.common.model.Location, de.unisaarland.cs.st.saarsiedler.comm.results.AttackResult)
	 */
	@Override
	public void attackSettlement(Location catapultPath, Location settlementIntersection, AttackResult result) {
		
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
		if (location==null) throw new IllegalArgumentException(location+" is null");
		Intersection i=getIntersection(location);
		if (isBuildable(i, buildingType) && (isAffordable(buildingType))){
			ResourcePackage price=buildingType.getPrice();
			if (me.checkResourcesSufficient(price)) throw new IllegalArgumentException("Nicht genug Ressourcen, um es zu bauen");
			me.modifyResources(price);
			i.createBuilding(buildingType, me);
			for(ModelObserver ob: modelObserver){
				ob.updateResources();
				ob.updateSettlementCount(buildingType);
				ob.updateVictoryPoints();
				ob.updateIntersection(i);
			}	
		}
		else throw new IllegalArgumentException("Das Gebaüde wurde nicht gebaut");
	}

	private boolean isBuildable(Intersection i, BuildingType buildingType){
		Set<Intersection>si;
		if (buildingType==BuildingType.Village){
			si=buildableVillageIntersections(me);
			if (si.contains(i)) return true;
			throw new IllegalArgumentException("Kein Dorf darf hier gebaut werden");
		}
		else if (buildingType==BuildingType.Town){
			si=buildableTownIntersections(me);
			if (si.contains(i)) return true;
			throw new IllegalArgumentException("Keine Stadt darf hier gebaut werden");
		}
		throw new IllegalArgumentException("Es fehlt den BuildingType");
	}
	
	private boolean isAffordable(BuildingType buildingType){
		int a=affordableSettlements(buildingType);
		if (a>0) return true;
		throw new IllegalArgumentException("Nicht genug Ressourcen, um es zu bauen");
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
		Player owner = getPath(sourcePath).getCatapultOwner();
		Path path_source = getPath(sourcePath);
		Path path_dest = getPath(destinationPath);
		Set<Intersection> iset1 = getIntersectionsFromPath(path_source);
		Set<Intersection> iset2 = getIntersectionsFromPath(path_dest);
		Intersection interBetweenPaths = null;
		//finds the intersection between both paths
		for (Intersection inter1 : iset1) {
			for (Intersection inter2 : iset2) {
				if(inter1 ==inter2) interBetweenPaths=inter1;
			}
		}
		if(interBetweenPaths.hasOwner() && interBetweenPaths.getOwner()!=owner){
			throw new IllegalStateException("Catapult kann nicht durch feindliche Siedlungen hindurch");
		}
		path_source.removeCatapult();
		path_dest.createCatapult(owner);
		
		if(owner.checkResourcesSufficient(Catapult.getAttackcatapultprice())) throw new IllegalStateException("not enough money on the bankaccount!");
		owner.modifyResources(Catapult.getAttackcatapultprice());
		
		for (ModelObserver ob : modelObserver) {
			ob.updatePath(path_source);
			ob.updatePath(path_dest);
			ob.updateResources();
		}
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelWriter#playerLeft(long)
	 */
	@Override
	public void playerLeft(long playerID) {
		for (ModelObserver ob : modelObserver) {
			ob.eventPlayerLeft(playerID);
		}
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
		return board.getField(p);
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getMaxCatapult()
	 */
	@Override
	public int getMaxCatapult() {
		return maxCatapult;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#getInitVillages()
	 */
	@Override
	public int getInitVillages() {
		return initVillages;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#buildableVillageIntersections(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Intersection> buildableVillageIntersections(Player player) {
		if (player == null)
			throw new IllegalArgumentException(player + " is null");
		Set<Path> streetSet = getStreets(player);
		Set<Intersection> ret = new TreeSet<Intersection>();
		for (Path path : streetSet) {
			Set<Intersection> pathInt = getIntersectionsFromPath(path);
			for (Intersection intersection : pathInt) {
				boolean buildable = true;
				for (Intersection nachbar : getIntersectionsFromIntersection(intersection)) {
					if(nachbar.hasOwner()) buildable=false;
				}
				if(buildable) ret.add(intersection);
			}
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see de.unisaarland.cs.sopra.common.model.ModelReader#buildableTownIntersections(de.unisaarland.cs.sopra.common.model.Player)
	 */
	@Override
	public Set<Intersection> buildableTownIntersections(Player player) {
		if (player == null)
			throw new IllegalArgumentException(player + " is null");
		return getSettlements(player, BuildingType.Village);
	}

	@Override
	public void returnResources(int lumber, int brick, int wool, int grain, int ore) {
		ResourcePackage robberPackage = new ResourcePackage(lumber, brick, wool, grain, ore);
		if(!me.checkResourcesSufficient(robberPackage)) throw new IllegalStateException("Spieler kann nicht mehr Resourcen abgeben als es hat");
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

}
