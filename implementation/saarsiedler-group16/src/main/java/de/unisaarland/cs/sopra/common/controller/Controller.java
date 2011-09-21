package de.unisaarland.cs.sopra.common.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.ModelWriter;
import de.unisaarland.cs.sopra.common.model.Point;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.view.Clickable;
import de.unisaarland.cs.st.saarsiedler.comm.Connection;
import de.unisaarland.cs.st.saarsiedler.comm.Edge;
import de.unisaarland.cs.st.saarsiedler.comm.GameEvent;
import de.unisaarland.cs.st.saarsiedler.comm.Intersection;
import de.unisaarland.cs.st.saarsiedler.comm.results.AttackResult;

public class Controller implements Runnable {

	private Connection connection;
	private ModelWriter modelWriter;
	private Resource r;
	private boolean endOfGame;
	private Queue<Clickable> guiEvents;
	public static boolean requestEventPull = false;

	/**
	 * @param connection
	 * @param modelWriter
	 */
	public Controller(Connection connection, ModelWriter modelWriter) {
		this.connection = connection;
		this.modelWriter = modelWriter;
		guiEvents = new LinkedBlockingQueue<Clickable>();
	}

	/**
	 * @param gameEvent
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	public void handleEvent(GameEvent gameEvent) {
		switch (gameEvent.getType()) {
		case ATTACK:
			Edge e = ((GameEvent.Attack) gameEvent).getSourceLocation();
			Intersection i = ((GameEvent.Attack) gameEvent)
					.getTargetIntersection();
			Location catapult = new Location(e.getRow(), e.getCol(),
					e.getDirection());
			Location settlement = new Location(i.getRow(), i.getCol(),
					i.getDirection());
			AttackResult r = ((GameEvent.Attack) gameEvent).attackResult();
			modelWriter.attackSettlement(catapult, settlement, r);
			break;
		case MATCH_START:
			long[] players = ((GameEvent.MatchStart) gameEvent).getPlayerIds();
			byte[] number = ((GameEvent.MatchStart) gameEvent).getNumbers();
			modelWriter.matchStart(players, number);
			break;
		case BUILT_CATAPULT:
			Edge ed = ((GameEvent.BuiltCatapult) gameEvent).getLocation();
			Location location = new Location(ed.getRow(), ed.getCol(),
					ed.getDirection());
			boolean fightOutcome = ((GameEvent.BuiltCatapult) gameEvent)
					.fightOutcome();
			modelWriter.buildCatapult(location, fightOutcome);
			break;
		case BUILT_ROAD:
			Edge edg = ((GameEvent.BuiltRoad) gameEvent).getLocation();
			Location locatio = new Location(edg.getRow(), edg.getCol(),
					edg.getDirection());
			modelWriter.buildStreet(locatio);
			break;
		case BUILT_SETTLEMENT:
			Intersection in = ((GameEvent.BuiltSettlement) gameEvent)
					.getLocation();
			Location locati = new Location(in.getRow(), in.getCol(),
					in.getDirection());
			boolean isUpgradeToTown = ((GameEvent.BuiltSettlement) gameEvent)
					.isUpgradeToTown();
			if (isUpgradeToTown) {
				modelWriter.buildSettlement(locati, BuildingType.Town);
			} else {
				modelWriter.buildSettlement(locati, BuildingType.Village);
			}
			break;
		case MOVED_CATAPULT:
			Edge edge = ((GameEvent.MovedCatapult) gameEvent)
					.getSourceLocation();
			Location source = new Location(edge.getRow(), edge.getCol(),
					edge.getDirection());
			Edge e1 = ((GameEvent.MovedCatapult) gameEvent)
					.getDestinationLocation();
			Location destination = new Location(e1.getRow(), e1.getCol(),
					e1.getDirection());
			boolean fightOutcome1 = ((GameEvent.MovedCatapult) gameEvent)
					.fightOutcome();
			modelWriter.catapultMoved(source, destination, fightOutcome1);
			break;
		case MATCH_END:
			((GameEvent.MatchEnd) gameEvent).getWinnerClientId();
			// TODO
			// modelObserver.MatchEnd
			break;
		case NEW_ROUND:
			requestEventPull = false;
			byte num = ((GameEvent.NewRound) gameEvent).getSpotSum();
			System.out.println(num);
			modelWriter.newRound(num);
			break;
		case PLAYER_LEFT:
			long id = ((GameEvent.PlayerLeft) gameEvent).getClientId();
			modelWriter.playerLeft(id);
			break;
		case ROBBER_MOVED:
			int y = ((GameEvent.RobberMoved) gameEvent).getSrcRow();
			int x = ((GameEvent.RobberMoved) gameEvent).getSrcCol();
			Point sourceField = new Point(y, x);
			int y1 = ((GameEvent.RobberMoved) gameEvent).getDstRow();
			int x1 = ((GameEvent.RobberMoved) gameEvent).getDstCol();
			Point destinationField = new Point(y1, x1);
			long victimPlayer = ((GameEvent.RobberMoved) gameEvent)
					.getVictimClientId();
			de.unisaarland.cs.st.saarsiedler.comm.Resource r1 = ((GameEvent.RobberMoved) gameEvent)
					.getStolenResource();
			Resource stolenResource = Resource.convert(r1);
			modelWriter.robberMoved(sourceField, destinationField,
					victimPlayer, stolenResource);
			break;
		case TRADE:
			long id2 = ((GameEvent.Trade) gameEvent).getAcceptedPlayerId();
			modelWriter.respondTrade(id2);
			break;
		case TRADE_OFFER:
			int lumber = ((GameEvent.TradeOffer) gameEvent).getLumber();
			int brick = ((GameEvent.TradeOffer) gameEvent).getBrick();
			int wool = ((GameEvent.TradeOffer) gameEvent).getWool();
			int grain = ((GameEvent.TradeOffer) gameEvent).getGrain();
			int ore = ((GameEvent.TradeOffer) gameEvent).getOre();
			modelWriter.tradeOffer(lumber, brick, wool, grain, ore);
			break;
		case LONGEST_ROAD:
			Edge[] edge1 = ((GameEvent.LongestRoad) gameEvent).getEdges();
			List<Location> road = new LinkedList<Location>();
			for (Edge e12 : edge1) {
				Location l = new Location(e12.getRow(), e12.getCol(),
						e12.getDirection());
				road.add(l);
			}
			modelWriter.longestRoadClaimed(road);
			break;
		}
	}

	

	/**
	 * @param catapult
	 * @param settlement
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public AttackResult attackSettlement(Location catapult, Location settlement)
			throws IllegalStateException, IllegalArgumentException, IOException {
		int y = catapult.getY();
		int x = catapult.getX();
		int o = catapult.getOrientation();
		Edge e = new Edge(y, x, o);
		int y1 = settlement.getY();
		int x1 = settlement.getX();
		int o1 = settlement.getOrientation();
		Intersection i = new Intersection(y1, x1, o1);

		AttackResult r = connection.attack(e, i);
		modelWriter.attackSettlement(catapult, settlement, r);
		return r;
	}

	/**
	 * @param path
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public boolean buildCatapult(Location path) throws IllegalStateException,
			IllegalArgumentException, IOException {
		boolean fOC = connection.buildCatapult(new Edge(path.getY(), path
				.getX(), path.getOrientation()));
		modelWriter.buildCatapult(path, fOC);
		return fOC;
	}

	/**
	 * @param intersection
	 * @param buildingType
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void buildSettlement(Location intersection, BuildingType buildingType)
			throws IllegalStateException, IllegalArgumentException, IOException {
		Intersection i = new Intersection(intersection.getY(),
				intersection.getX(), intersection.getOrientation());

		if (buildingType.equals(BuildingType.Village)) {
			connection.buildSettlement(i, false);
			modelWriter.buildSettlement(intersection, buildingType);
		} else {
			connection.buildSettlement(i, true);
			modelWriter.buildSettlement(intersection, buildingType);
		}
	}

	/**
	 * @param path
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void buildStreet(Location path) throws IllegalStateException,
			IllegalArgumentException, IOException {
		Edge e = new Edge(path.getY(), path.getX(), path.getOrientation());
		connection.buildRoad(e);
		modelWriter.buildStreet(path);
	}

	/**
	 * @param road
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void claimLongestRoad(List<Location> road)
			throws IllegalStateException, IllegalArgumentException, IOException {
		List<Edge> roadList = new LinkedList<Edge>();
		for (Location l : road) {
			Edge e = new Edge(l.getY(), l.getX(), l.getOrientation());
			roadList.add(e);
		}
		connection.claimLongestRoad(roadList);
		modelWriter.longestRoadClaimed(road);
	}

	/**
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void claimVictory() throws IllegalStateException, IOException {
		connection.claimVictory();
	}

	/**
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void endTurn() throws IllegalStateException, IOException {
		connection.endTurn();
		Controller.requestEventPull = true;
	}

	/**
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void leaveMatch() throws IllegalStateException, IOException {
		connection.leaveMatch();
	}

	/**
	 * @param sourcePath
	 * @param destinationPath
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public boolean moveCatapult(Location sourcePath, Location destinationPath)
			throws IllegalStateException, IllegalArgumentException, IOException {
		Edge e = new Edge(sourcePath.getY(), sourcePath.getX(),
				sourcePath.getOrientation());
		Edge e1 = new Edge(destinationPath.getY(), destinationPath.getX(),
				destinationPath.getOrientation());
		boolean fOC = connection.moveCatapult(e, e1);
		modelWriter.catapultMoved(sourcePath, destinationPath, fOC);
		return fOC;
	}

	/**
	 * @param sourceField
	 * @param destinationField
	 * @param victimPlayer
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public Resource moveRobber(Point sourceField, Point destinationField,
			long victimPlayer) throws IllegalStateException,
			IllegalArgumentException, IOException {
		int y = sourceField.getY();
		int x = sourceField.getX();
		int y1 = destinationField.getY();
		int x1 = destinationField.getX();
		//TODO BREAKPOINT
		System.out.println(victimPlayer);
		de.unisaarland.cs.st.saarsiedler.comm.Resource r1 = connection
				.moveRobber(y, x, y1, x1, victimPlayer);
		r = Resource.convert(r1);
		modelWriter.robberMoved(sourceField, destinationField, victimPlayer, r);
		return r;
	}

	/**
	 * @param lumber
	 * @param brick
	 * @param wool
	 * @param grain
	 * @param ore
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public long offerTrade(int lumber, int brick, int wool, int grain, int ore)
			throws IllegalStateException, IOException {
		long id = connection.offerTrade(-lumber, -brick, -wool, -grain, -ore);
		modelWriter.tradeOffer(lumber, brick, wool, grain, ore);
		modelWriter.respondTrade(id);
		return id;
	}

	/**
	 * @param decision
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public long respondTrade(boolean decision) throws IllegalStateException,
			IllegalArgumentException, IOException {
		long id = connection.respondTrade(decision);
		modelWriter.respondTrade(id);
		return id;
	}

	/**
	 * @param lumber
	 * @param brick
	 * @param wool
	 * @param grain
	 * @param ore
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void returnResources(int lumber, int brick, int wool, int grain,
			int ore) throws IllegalStateException, IllegalArgumentException,
			IOException {
		connection.returnResources(lumber, brick, wool, grain, ore);
		modelWriter.returnResources(lumber, brick, wool, grain, ore);
	}

	public void addGuiEvent(Clickable c) {
		guiEvents.add(c);
	}

	/**
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Override
	public void run() {
		try {


			while(!endOfGame){
				if (!modelWriter.isOurTurn() || requestEventPull) {
					GameEvent e = connection.getNextEvent(0);
					if (e != null) {
						System.out.println(e);
						handleEvent(e);
					}
				}
				else {
					Iterator<Clickable> iter = guiEvents.iterator();
					while (iter.hasNext()) {
						iter.next().executeController();
						iter.remove();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setEndOfGame(boolean b) {
		endOfGame = b;
	}

}
