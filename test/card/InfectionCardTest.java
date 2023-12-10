package card;

import game.Game;
import map.*;
import sickness.Sickness;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import exception.OutBreakRateTooHighException;
import exception.TooMuchCubesException;


public class InfectionCardTest {
	
	private City c1;
	private Game g;
	private WorldMap m;
	
	
	@Before
	public void init() {
		this.c1 = new City("ville1",Sickness.BLUE);
		this.c1.addGame(new Game(m));
		this.m = new JsonMap();
		this.g = new Game(this.m);
	}

	@Test
	public void testConstructorAndAccesors() {
		InfectionCard card = new InfectionCard(g, c1);
		assertEquals(card.getCity(), c1);
		assertEquals(card.getGame(), g);		
	}
	
	@Test
	public void testActionACubeIsWellAdded() throws TooMuchCubesException, OutBreakRateTooHighException  {
		InfectionCard card = new InfectionCard(g, c1);
		card.action();
		assertEquals(this.c1.getCubes(c1.getSector()), 1);
		c1.setInfectedFalse();
		card.action();
		assertEquals(this.c1.getCubes(c1.getSector()), 2);	
	}
	
	

}
