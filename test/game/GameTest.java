package game;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import exception.NeighbourException;
import sickness.Sickness;
import map.*;
import player.Player;
import player.action.Move;
import player.role.Medic;

public class GameTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();	
	private Game g;
	private int GLOBAL_CUBE_LIMIT = 24;
	private WorldMap m;
	


	/** permet de simuler une saisie au clavier en fournissant la chaine qui serait saisie
    * @param input la chaine saisie, qui sera donc récupérée par un scanner qui lirait sur Scanner.in  
	*/
	public void simulateInput(String input) {   
		InputStream in = new ByteArrayInputStream(input.getBytes()); 
		System.setIn(in);  // détourne System.in vers in
	}
	
	@Before
	public void init() {
		this.m = new JsonMap();
		this.g = new Game(this.m);
		System.setOut(new PrintStream(outContent));
	}
	
	
	
	@Test
	public void GameIsCorrectAtTheInitialization() {
		assertEquals(this.g.isCured(Sickness.BLACK), false);
		assertEquals(this.m, this.g.getMap());
		assertEquals(0, this.g.getIndex());
		List<Player> list1 = new ArrayList<>();
		assertEquals(list1, this.g.getPlayersList());
		List<City> list2 = new ArrayList<>();
		assertEquals(list2, this.g.getStationList());
		assertEquals(0, this.g.getNbOfCubes(Sickness.BLACK));
	}
	
	@Test
	public void ASicknessIsHealWhileTheGame() {
		this.g.setCure(Sickness.BLUE);
		assertEquals(this.g.isCured(Sickness.BLUE), true);
	}
	
	@Test
	public void GlobalInfectionRateIsWellSetted() {
		assertEquals(this.g.getGlobalInfectionRate(), 2);
		this.g.increaseGlobalInfectionRate();
		assertEquals(this.g.getGlobalInfectionRate(), 2);
		this.g.increaseGlobalInfectionRate();
		assertEquals(this.g.getGlobalInfectionRate(), 2);
		this.g.increaseGlobalInfectionRate();
		assertEquals(this.g.getGlobalInfectionRate(), 3);
	}

	@Test
	public void GetCubeLimit() {
		assertEquals(this.g.getCubeLimit(), this.GLOBAL_CUBE_LIMIT);
	}
	
	@Test
	public void TheTotalNumberOfCubesIsRightWhenSomeAdded() {
		assertEquals(this.g.getNbOfCubes(Sickness.BLACK), 0);
		this.g.increaseNbOfCubes(Sickness.BLACK, 5);
		assertEquals(this.g.getNbOfCubes(Sickness.BLACK), 5);
		this.g.increaseNbOfCubes(Sickness.BLACK, 2);
		assertEquals(this.g.getNbOfCubes(Sickness.BLACK), 7);
		this.g.increaseNbOfCubes(Sickness.YELLOW, 3);	
		/**
		 * Say the type of the card when this is draw
		 */
		assertEquals(this.g.getNbOfCubes(Sickness.YELLOW), 3);
	}
	
	@Test
	public void TheLimitOfCubeHasBeenReachedByASickness() {
		assertEquals(this.g.getNbOfCubes(Sickness.BLACK), 0);
		this.g.increaseNbOfCubes(Sickness.BLACK, 24);
		assertEquals(this.g.getNbOfCubes(Sickness.BLACK), 24);
		this.g.increaseNbOfCubes(Sickness.BLACK, 12);
		assertEquals(this.g.getNbOfCubes(Sickness.BLACK), 24);

	}
	
	@Test
	public void GetRandomNumberIsLowerThanMaxAndHigherThanMin() {
		int randNum = Game.getRandomNumber(4);
		assertTrue(randNum >= 0);
		assertTrue(randNum <= 4);
	}
	
	@Test
	public void APlayerIsAddInTheGame() {
		City c = new City("ville1", Sickness.BLUE);
		Player p = new Medic("PlayerTest", c, g);
		List<Player> list = new ArrayList<Player>();
		list.add(p);
		this.g.addPlayer(p);
		assertEquals(list, g.getPlayersList());
		
	}
	
	@Test
	public void AddStationInGameAddInAttributesAndInTheCity() {
		City c = new City("ville1", Sickness.BLUE);
		Player p = new Medic("PlayerTest", c, g);
		this.g.addPlayer(p);
		this.g.nextPlayer();
		this.g.createStation(c);
		assertTrue(c.getStation());
		assertEquals(this.g.getNbOfStations(), 1);
	}
	
	
	@Test
	public void AStationIsRemoveInGame() throws NeighbourException {
		City c1 = new City("ville1", Sickness.BLUE);
		City c2 = new City("ville2", Sickness.BLUE);
		City c3 = new City("ville3", Sickness.BLUE);
		City c4 = new City("ville4", Sickness.BLUE);
		Player p = new Medic("PlayerTest", c1, g);
		Move move = new Move(p);
		this.g.addPlayer(p);
		this.g.getMap().add(c1);
		this.g.getMap().add(c2);
		this.g.getMap().add(c3);
		this.g.getMap().add(c4);
		c1.addNeighbour(c2);
		c2.addNeighbour(c3);
		c3.addNeighbour(c4);
		this.g.nextPlayer();
		assertEquals(this.g.getNbOfStations(), 0);
		this.g.createStation(c1);
		assertEquals(this.g.getNbOfStations(), 1);
		move.action(c2);
		this.g.createStation(c2);
		assertEquals(this.g.getNbOfStations(), 2);
		move.action(c3);
		this.g.createStation(c3);
		assertEquals(this.g.getNbOfStations(), 3);
		move.action(c4);
		this.g.createStation(c4);
		assertEquals(this.g.getNbOfStations(), 4);
		this.g.removeStation(c2);
		assertTrue(c2.getStation());
		assertEquals(this.g.getNbOfStations(), 3);
	}
	
	@Test
	public void SetCureSetTrueInAttribute() {
		assertFalse(this.g.isCured(Sickness.BLACK));
		assertFalse(this.g.isCured(Sickness.YELLOW));
		this.g.setCure(Sickness.BLACK);
		this.g.setCure(Sickness.YELLOW);
		assertTrue(this.g.isCured(Sickness.BLACK));
		assertTrue(this.g.isCured(Sickness.YELLOW));
	}
	
	@Test
	public void ASicknessIsEradicatedWhen0CubesInTheGame() {
		this.g.setInfectionTimeFalse();
		this.g.increaseNbOfCubes(Sickness.RED, 6);
		this.g.decreaseNbOfCubes(Sickness.RED, 4);
		assertFalse(this.g.isEradicated(Sickness.RED));
		this.g.decreaseNbOfCubes(Sickness.RED, 2);
		assertTrue(this.g.isEradicated(Sickness.RED));
	}
	
	@Test
	public void AGameIsFinishedWithAWin() {
		this.g.initDeck();
		assertEquals(Results.UNFINISHED,this.g.getResult());
		this.g.setCure(Sickness.BLACK);
		this.g.setCure(Sickness.YELLOW);
		this.g.setCure(Sickness.BLUE);
		assertEquals(Results.UNFINISHED,this.g.getResult());
		this.g.setCure(Sickness.RED);
		assertEquals(Results.WON,this.g.getResult());	
	}
	
	@Test
	public void AGameIsFinishedWithALoose() {
		this.g.initDeck();
		assertEquals(Results.UNFINISHED,this.g.getResult());
		this.g.increaseNbOfCubes(Sickness.RED, 23);
		assertEquals(Results.UNFINISHED,this.g.getResult());
		this.g.increaseNbOfCubes(Sickness.RED, 1);
		assertEquals(Results.LOST,this.g.getResult());			
	}
	
	@Test
	public void testDrawInfectionCardReturnTheCardAtTheTopOfTheDeck() {
		simulateInput("12");
		this.g.initMap();
		this.g.initDeck();
		assertEquals(this.g.getInfectionDeck().getStack().get(0), this.g.drawInfectionCard());
	}
	
	/**
     * -------------------------------------------------------------
     * ----------------------- String test -----------------------
     * -------------------------------------------------------------      
     */
	
	@Test
	public void TheGameDoesntDisplayAListOfStationExisting() {
		this.g.displayStationList();
		assertEquals("", this.outContent.toString().trim());
	}
	
}
