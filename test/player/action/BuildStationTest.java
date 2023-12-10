package player.action;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import card.CityCard;
import game.Game;
import map.City;
import map.JsonMap;
import player.Player;
import player.role.Expert;
import sickness.Sickness;

public class BuildStationTest {

	private Game g;
	private City c;
	private Player p;
	private CityCard card;
	private BuildStation build;
	private CityCard cardF; 
	private City cF;
	
	@Before
	public void init() {
		this.g = new Game(new JsonMap());
		this.c = new City("CityTest", Sickness.BLACK);
		this.p = new Expert("PlayerTest", c, g);
		this.build = new BuildStation(p);
		this.card = new CityCard(g, c);
		this.cF = new City("CityTest False", Sickness.RED);
		this.cardF = new CityCard(g, cF);
		g.initDeck();
	}
	
	@Test
	public void TheActionBuildStationIsCorrectAtTheInit() {
		assertEquals(p, build.getPlayer());
		assertEquals("To build a station", build.toString());
	}
	
	@Test
	public void ThePlayerBuildAStation() {
		p.addCard(card);
		p.selectCard(card);
		assertFalse(p.getHand().isEmpty());
		build.action(c);
		assertTrue(p.getHand().isEmpty());
		assertTrue(c.getStation());
	}
	
	@Test
	public void BuildAStationIsPossible() {
		p.addCard(card);
		p.selectCard(card);
		assertTrue(build.isPossible());
	}
	
	@Test
	public void BuildAStationIsNotPossibleBecauseAStationIsAlreadyInTheCity() {
		p.addCard(card);
		p.selectCard(card);
		c.setStation(true);
		assertFalse(build.isPossible());
	}
	
	@Test 
	public void BuildAStationIsNotPossibleBecauseThePlayerDoesn_tHasTheCard() {
		assertFalse(build.isPossible());
	}
	
	@Test
	public void hasCityCardToBuildStationTestWithoutTheCardOfTheCity() {
		this.p.addCard(cardF);
		boolean res = this.build.hasCityCardToBuildStation();
		assertEquals(res,false);
		assertEquals(this.p.getSelectedCard(), null);
	}
	
	@Test
	public void hasCityCardToBuildStationTestWithTheCardOfTheCity() {
		this.p.addCard(card);
		boolean res = this.build.hasCityCardToBuildStation();
		assertEquals(res,true);
		assertEquals(this.p.getSelectedCard(), card);
	}
}
