package de.unisaarland.cs.sopra.common.model;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface ModelReader {

	public int getInitBuilding(BuildingType buildingType);
	public int getMaxBuilding(BuildingType buildingType);
	public int getRound();
	public Set<Intersection> buildableIntersections(Player player);
	public Set<Path> buildableStreetPaths(Player player);
	public Set<Path> buildableCatapultPaths(Player player);
	public int affordableSettlements(BuildingType buildingType);
	public int affordableStreets();
	public int affordableCatapultBuild();
	public int affordableCatapultAttack();
	public int affordableSettlementAttack();
	public int affordablePathsAfterVillage(int villageCount);
	public Set<Intersection> attackableSettlements(Player player, BuildingType buildingType);
	public Set<Path> attackableCatapults(Player player);
	public Set<Path> getStreets(Player player);
	public Set<Intersection> getSettlements(Player player, BuildingType buildingType);
	public Set<Path> getCatapults(Player player);
	public List<Path> getLongestClaimedRoad();
	public int getMaxVictoryPoints();
	public int getCurrentVictoryPoints(Player player);
	public Set<Field> canPlaceRobber();
	public Set<Field> getRobberFields();
	public Iterator<Field> getFieldIterator();
	public Iterator<Path> getPathIterator();
	public Iterator<Intersection> getIntersectionIterator();
	public Set<Intersection> getHarborIntersections();
	public HarborType getHarborType(Intersection intersection);
	public Set<HarborType> getHarborTypes(Player player);
	public ResourcePackage getResources();
	public int getFieldNumber(Field field);
	public Resource getFieldResource(Field field);
	public Set<Field> getFieldsFromField(Field field);
	public Set<Field> getFieldsFromIntersection(Intersection intersection);
	public Set<Field> getFieldsFromPath(Path path);
	public Set<Intersection> getIntersectionsFromField(Field field);
	public Set<Intersection> getIntersectionsFromIntersection(Intersection intersection);
	public Set<Intersection> getIntersectionsFromPath(Path path);
	public Set<Path> getPathsFromField(Field field);
	public Set<Path> getPathsFromIntersection(Intersection intersection);
	public Set<Path> getPathsFromPath(Path path);
	public Path getPath(Location loc);
	public Field getField(Point p);
	public Intersection getIntersection(Location location);
	
}
