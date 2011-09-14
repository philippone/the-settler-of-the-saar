package de.unisaarland.cs.sopra.common.model;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.unisaarland.cs.sopra.common.ModelObserver;

public interface ModelReader {

	/**
	 * @return
	 */
	public int getInitVillages();

	/**
	 * @param buildingType
	 * @return
	 */
	public int getMaxBuilding(BuildingType buildingType);
	
	/**
	 * @return
	 */
	public int getMaxCatapult();
	
	/**
	 * @return
	 */
	public int getRound();
	
	/**
	 * @param player
	 * @return
	 */
	public Set<Intersection> buildableVillageIntersections(Player player);
	
	/**
	 * @param player
	 * @return
	 */
	public Set<Intersection> buildableTownIntersections(Player player);
	
	/**
	 * @param player
	 * @return
	 */
	public Set<Path> buildableStreetPaths(Player player);
	
	/**
	 * @param player
	 * @return
	 */
	public Set<Path> buildableCatapultPaths(Player player);
	
	/**
	 * @param buildingType
	 * @return
	 */
	public int affordableSettlements(BuildingType buildingType);
	
	/**
	 * @return
	 */
	public int affordableStreets();
	
	/**
	 * @return
	 */
	public int affordableCatapultBuild();
	
	/**
	 * @return
	 */
	public int affordableCatapultAttack();
	
	/**
	 * @return
	 */
	public int affordableSettlementAttack();
	
	/**
	 * @param villageCount
	 * @return
	 */
	public int affordablePathsAfterVillage(int villageCount);
	
	/**
	 * @param player
	 * @param buildingType
	 * @return
	 */
	public Set<Intersection> attackableSettlements(Player player, BuildingType buildingType);
	
	/**
	 * @param player
	 * @return
	 */
	public Set<Path> attackableCatapults(Player player);
	
	/**
	 * @param player
	 * @return
	 */
	public Set<Path> getStreets(Player player);
	
	/**
	 * @param player
	 * @param buildingType
	 * @return
	 */
	public Set<Intersection> getSettlements(Player player, BuildingType buildingType);
	
	/**
	 * @param player
	 * @return
	 */
	public Set<Path> getCatapults(Player player);
	
	/**
	 * @return
	 */
	public List<Path> getLongestClaimedRoad();
	
	/**
	 * @return
	 */
	public int getMaxVictoryPoints();
	
	/**
	 * @param player
	 * @return
	 */
	public int getCurrentVictoryPoints(Player player);
	
	/**
	 * @return
	 */
	public Set<Field> canPlaceRobber();
	
	/**
	 * @return
	 */
	public Set<Field> getRobberFields();
	
	/**
	 * @return
	 */
	public Iterator<Field> getFieldIterator();
	
	/**
	 * @return
	 */
	public Iterator<Path> getPathIterator();
	
	/**
	 * @return
	 */
	public Iterator<Intersection> getIntersectionIterator();
	
	/**
	 * @return
	 */
	public Set<Intersection> getHarborIntersections();
	
	/**
	 * @param intersection
	 * @return
	 */
	public HarborType getHarborType(Intersection intersection);
	
	/**
	 * @param player
	 * @return
	 */
	public Set<HarborType> getHarborTypes(Player player);
	
	/**
	 * @return
	 */
	public ResourcePackage getResources();
	
	
	/**
	 * @param field
	 * @return
	 */
	public Resource getFieldResource(Field field);
	
	/**
	 * @param field
	 * @return
	 */
	public Set<Field> getFieldsFromField(Field field);
	
	/**
	 * @param intersection
	 * @return
	 */
	public Set<Field> getFieldsFromIntersection(Intersection intersection);
	
	/**
	 * @param path
	 * @return
	 */
	public Set<Field> getFieldsFromPath(Path path);
	
	/**
	 * @param field
	 * @return
	 */
	public Set<Intersection> getIntersectionsFromField(Field field);
	
	/**
	 * @param intersection
	 * @return
	 */
	public Set<Intersection> getIntersectionsFromIntersection(Intersection intersection);
	
	/**
	 * @param path
	 * @return
	 */
	public Set<Intersection> getIntersectionsFromPath(Path path);
	
	/**
	 * @param field
	 * @return
	 */
	public Set<Path> getPathsFromField(Field field);
	
	/**
	 * @param intersection
	 * @return
	 */
	public Set<Path> getPathsFromIntersection(Intersection intersection);
	
	/**
	 * @param path
	 * @return
	 */
	public Set<Path> getPathsFromPath(Path path);
	
	/**
	 * @param loc
	 * @return
	 */
	public Path getPath(Location loc);
	
	/**
	 * @param p
	 * @return
	 */
	public Field getField(Point p);
	
	/**
	 * @param location
	 * @return
	 */
	public Intersection getIntersection(Location location);

	/**
	 * @return
	 */
	public Player getMe();
	
	/**
	 * @return
	 */
	public int getBoardWidth();
	
	/**
	 * @return
	 */
	public int getBoardHeight();
	
	/**
	 * @return
	 */
	public Player getCurrentPlayer();
	
	/**
	 * @return The List of Player sorted in TableOrder
	 */
	public List<Player> getTableOrder();
	
	/**
	 * @param modelObserver
	 */
	public void removeModelObserver(ModelObserver modelObserver);
	
	/**
	 * @param modelObserver
	 */
	public void addModelObserver(ModelObserver modelObserver);
	
}
