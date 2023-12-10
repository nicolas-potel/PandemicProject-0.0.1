package player.action;

import static org.junit.Assert.*;

import org.junit.Test;

import exception.NeighbourException;
import map.*;
import player.Player;
import player.role.GlobeTrotter;

import org.junit.Before;

public class MoveTest {
	
	private City c;
	private Player p;
	private Move move;
	
	@Before
	public void init() {
		this.c = new City("CityTest", null);
		this.p = new GlobeTrotter("test", c, null);
		this.move = new Move(p);
	}
	
	@Test
	public void TheActionMoveIsCorrectAtTheInit() {
		assertEquals(p, move.getPlayer());
		assertEquals("To move", move.toString());
	}
	
	@Test
	public void PlayerHasChangedCity() throws NeighbourException {
		City newCity = new City("Neighbour", null);
		c.addNeighbour(newCity);
		move.action(newCity);
		assertEquals(newCity, p.getCity());
		assertTrue(newCity.hasPlayer(p));
		assertFalse(c.hasPlayer(p));
	}
	
	@Test ( expected = NeighbourException.class )
	public void thePlayerWantToChangeOfCityButTheCityIsNotANeighboor() throws NeighbourException {
		City newCity = new City("Neighbour", null);
		move.action(newCity);
	}
	
}


