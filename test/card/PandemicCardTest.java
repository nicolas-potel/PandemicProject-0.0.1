package card;

import game.Game;
import map.*;
import sickness.Sickness;

import java.util.*;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import exception.OutBreakRateTooHighException;
import exception.TooMuchCubesException;

public class PandemicCardTest {

	private Game g;
	private WorldMap m;
	private PandemicCard c;
	
	@Before
	public void init() {
		City city = new City("Test",Sickness.BLUE);
		this.m = new JsonMap();
		this.g = new Game(this.m);
		city.addGame(g);
		this.m.add(city);
		this.c = new PandemicCard(this.g);
		this.g.initDeck();
	}
	
	@Test
	public void testConstructorAndAccessor() {
		assertEquals(this.g, c.getGame());	
	}
	
	@Test
	public void testPlayingAcceleration() {
		int infectionRateBefore = this.g.getGlobalInfectionRate();
		this.c.acceleration();
		assertEquals(infectionRateBefore, this.g.getGlobalInfectionRate());
		this.c.acceleration();
		assertEquals(infectionRateBefore, this.g.getGlobalInfectionRate());
		this.c.acceleration();
		assertEquals(infectionRateBefore + 1, this.g.getGlobalInfectionRate());
	}
	
	@Test
	public void testPlayingInfection() throws TooMuchCubesException, OutBreakRateTooHighException {
		this.c.action();
		assertTrue(this.m.getCity("Test").getCubes(Sickness.BLUE)==3);
	}
	
	@Test
	public void testIntensificationDiscardIsWellAddedAtTheTopOfTheInfectionDeck() throws TooMuchCubesException, OutBreakRateTooHighException {
		List<Card> cardsInDiscard = new ArrayList<>();
		int i = 0;
		for (Card card :this.g.getInfectionDeck().getDiscard()) {
			cardsInDiscard.add(card);
		}
		this.c.action();
		for (Card card : cardsInDiscard) {
			assertTrue(this.g.getInfectionDeck().getStack().contains(card));
		}
	}
	

}
