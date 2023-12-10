package card;

import game.Game;
import map.*;
import sickness.Sickness;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;


public class CityCardTest {
	
	private City c1;
	private Game g;
	private WorldMap m;
	
	@Before
	public void init() {
		this.c1 = new City("ville1", Sickness.YELLOW);
		this.m = new JsonMap();
		this.g = new Game(this.m);
	}

	@Test
	public void testConstructorAndAccessor() {
		CityCard card = new CityCard(this.g, this.c1);
		assertEquals(this.c1, card.getCity());
		assertEquals(this.g, card.getGame());
	}

}
