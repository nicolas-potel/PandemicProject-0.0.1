package player;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

import org.junit.Test;

import card.CityCard;
import exception.NeighbourException;
import game.Game;
import map.*;
import player.role.Medic;
import sickness.Sickness;

import org.junit.Before;

public class PlayerTest {

	private City c;
	private Player p;
	private WorldMap m;
	private Game g;
	
	@Before
	public void init() {
		this.c = new City("testCity", Sickness.BLACK);
		this.m = new JsonMap();
		this.p = new Medic("test", c, this.g = new Game(this.m));
		c.addPlayer(p);
	}
	
	@Test
	public void PlayerIsCorrectAtTheCreation() {
		assertEquals("test, the Medic", p.getName());
		assertEquals(c, p.getCity());
		assertEquals(g, p.getGame());
		assertEquals(new ArrayList<CityCard>(), p.getHand());
		assertEquals(0, p.getNbCard());
		assertEquals(new ArrayList<>(), p.getPossibleCure());
		assertEquals(new ArrayList<>(), p.getCitiesCardDiscardable());
		assertEquals(null, p.getSelectedCard());
		assertEquals(8, p.getAction().size());
	}
	
	@Test
	public void TestSetCity() {
		City newC = new City("NewCity", Sickness.BLUE);
		p.setCity(newC);
		assertEquals(newC, p.getCity());
	}
	
	@Test
	public void TestSetName() {
		assertEquals("test, the Medic", p.getName());
		p.setName("test2");
		assertEquals("test2, the Medic", p.getName());
	}
	
	@Test
	public void ThePlayerAddACardInHisHand() {
		CityCard card = new CityCard(g, c);
		p.addCard(card);
		assertEquals(1, p.getHand().size());
		assertEquals(card, p.getHand().get(0));
	}
	
	@Test
	public void ThePlayerAddACardInHisListDiscardable() {
		CityCard card = new CityCard(g, c);
		p.addCardInListDiscardable(card);
		assertEquals(1,p.getCitiesCardDiscardable().size());
		assertEquals(card, p.getCitiesCardDiscardable().get(0));
	}
	
	@Test 
	public void ThePlayerSelectACard() {
		CityCard card = new CityCard(g, c);
		p.addCard(card);
		p.selectCard(card);
		assertEquals(card, p.getSelectedCard());
	}
	
	@Test
	public void ThePlayerDiscardACard() {
		g.initDeck();
		CityCard card = new CityCard(g, c);
		p.addCard(card);
		assertEquals(0,g.getPlayerDeck().getDiscard().size());
		assertEquals(1, p.getHand().size());
		p.discardCityCard(card);
		assertEquals(0, p.getHand().size());
		assertEquals(1, g.getPlayerDeck().getDiscard().size());
	}
	
	@Test
	public void ThePlayerFilterHisActions() throws NeighbourException, IOException {
		p.filter();
		/** change after that Medic class is finished*/
		assertEquals(8, p.getAction().size());
		assertEquals(5,p.getActionFilter().size());
	}
	

}
