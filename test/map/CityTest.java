package map;
import sickness.Sickness;
import player.Player;
import player.role.Medic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import exception.NeighbourException;
import exception.OutBreakRateTooHighException;
import exception.TooMuchCubesException;
import game.Game;


public class CityTest {
	
	private City c1;
	private City c2;
	private City c3;
	private Player p1;
	private WorldMap m;
	
	@Before
	public void init() {
		this.c1 = new City("ville1", Sickness.RED);
		this.c1.addGame(new Game(m));
		this.c2 = new City("ville2", Sickness.YELLOW);
		this.c2.addGame(new Game(m));
		this.c3 = new City("ville3", Sickness.BLACK);
		this.c3.addGame(new Game(m));
		this.m = new JsonMap();
		this.p1 = new Medic("Nathan", c1, new Game(this.m));
		}
		
	
	
	
	@Test
	public void testConstructorAndAccessorOfCity() {
		
		assertEquals(c1.getStation(),false);
		assertEquals(c1.getSector(),Sickness.RED);
		assertEquals(c1.getCubes(Sickness.BLACK),0); 
		Map<Sickness, Integer> testMapCubes = new HashMap<Sickness, Integer>();
		testMapCubes.put(Sickness.RED, 0);
		testMapCubes.put(Sickness.BLACK, 0);
		testMapCubes.put(Sickness.BLUE, 0);
		testMapCubes.put(Sickness.YELLOW, 0);
		assertEquals(c1.getMapCubes(), testMapCubes);
		assertEquals(c1.getSicknessList(), new ArrayList<Sickness>());
		assertEquals(c1.getNeighbours(),new HashMap<String, City>());
		assertEquals(c1.getPlayers(),new ArrayList<Player>());
		assertEquals(c1.toString(),"ville1");
		
	}
	
	@Test
	public void testAddNeighbour() {
		c1.addNeighbour(c2) ;
		HashMap<String, City> neighbour = new HashMap<String, City>();
		neighbour.put(c2.toString(),c2);
		assertEquals(c1.getNeighbours(), neighbour );
	}
	
	
	@Test
	public void testAddCubes() throws TooMuchCubesException, OutBreakRateTooHighException {
		c1.addCubes(Sickness.BLACK, 2);
		assertEquals(c1.getCubes(Sickness.BLACK),2);
	}
	
	@Test
	public void ACubeIsAddInTheCitySoASicknessAppearedInTheCity() throws TooMuchCubesException, OutBreakRateTooHighException {
		c1.addCubes(Sickness.BLACK, 2);
		List<Sickness> testSicknessAppeared = new ArrayList<Sickness>();
		testSicknessAppeared.add(Sickness.BLACK);
		assertEquals(c1.getSicknessList(), testSicknessAppeared);
		
	}
	
	@Test
	public void testRemoveCubes() throws TooMuchCubesException, OutBreakRateTooHighException {
		c1.addCubes(Sickness.BLACK, 2);
		c1.removeCubes(Sickness.BLACK, 1);
		
		assertEquals(c1.getCubes(Sickness.BLACK), 1);
	}
	
	@Test
	public void OneInThreeCubesIsRemoveSoTheSicknessNotVanished() throws TooMuchCubesException, OutBreakRateTooHighException {
		c1.addCubes(Sickness.BLACK, 3);
		List<Sickness> testSicknessAppeared = new ArrayList<Sickness>();
		testSicknessAppeared.add(Sickness.BLACK);
		c1.removeCubes(Sickness.BLACK, 1);
		assertEquals(c1.getSicknessList(), testSicknessAppeared);
	}
	
	@Test
	public void AllCubesAreRemoveSoTheSicknessVanish() throws TooMuchCubesException, OutBreakRateTooHighException {
		c1.addCubes(Sickness.BLACK, 3);
		c1.removeCubes(Sickness.BLACK, 3);
		
		assertEquals(c1.getSicknessList(), new ArrayList<Sickness>());
	}
	
	@Test
	public void testSetStation() {
		c1.setStation(true);
		assertEquals(c1.getStation(), true);
	}
	
	@Test
	public void testGetOneNeighbourWithoutError()throws NeighbourException {
		c1.addNeighbour(c2);
		assertEquals(c1.getOneNeighbour("ville2"), c2);
	}
	
	@Test( expected = NeighbourException.class )
	public void testGetOneNeighbourWithError()throws NeighbourException {
		c1.getOneNeighbour("ville2");
	}
	
	@Test
	public void testAddPlayer() {
		List<Player> players = new ArrayList<Player>();
		players.add(p1);
		c1.addPlayer(p1);
		assertEquals(c1.getPlayers(),players);
	}
	
	@Test
	public void testRemovePlayer() {
		c1.addPlayer(p1);
		c1.removePlayer(p1);
		List<Player> players = new ArrayList<Player>();
		assertEquals(c1.getPlayers(),players);
	}
	
	@Test
	public void testAddCubesWithNeighbourAndMore3Cubes() throws TooMuchCubesException, OutBreakRateTooHighException {
		c1.addNeighbour(c2);
		c1.addCubes(Sickness.BLACK, 4);
		assertEquals(c1.getCubes(Sickness.BLACK),3);
		assertEquals(c2.getCubes(Sickness.BLACK),1);
	}
	
	@Test
	public void testAddCubesWithNeighbourAndMore3CubesAndNeighbourHaveMoreThan3Cubes() throws TooMuchCubesException, OutBreakRateTooHighException {
		c1.addNeighbour(c2);
		c2.addNeighbour(c3);
		c1.addCubes(Sickness.BLACK, 3);
		c1.setInfectedFalse();
		c2.addCubes(Sickness.BLACK, 3);
		c2.setInfectedFalse();
		c1.addCubes(Sickness.BLACK, 2);
		
		assertEquals(c1.getCubes(Sickness.BLACK),3);
		assertEquals(c2.getCubes(Sickness.BLACK),3);
		assertEquals(c3.getCubes(Sickness.BLACK),1);
	}
	
	@Test
	public void cityIsNeighboor() {
		c1.addNeighbour(c2);
		assertTrue(c1.isNeighbour(c2));
		assertFalse(c1.isNeighbour(c3));
	}
	
	@Test 
	public void CityHasThePlayer() {
		c1.addPlayer(p1);
		assertTrue(c1.hasPlayer(p1));
	}
}
