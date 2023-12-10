package player.action.action_role;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import card.CityCard;
import game.Game;
import map.City;
import map.JsonMap;
import player.Player;
import player.role.Scientific;
import sickness.Sickness;
import player.action.action_role.FindCureScientific;

public class FindCureScientificTest {
	
	//------------------------------ Initialization ---------------------------
	
	private Player p ;
	private Game g ;
	private City c1 ;
	private City c2 ;
	private City c3 ;
	private City c4 ;
	private CityCard card1;
	private CityCard card2;
	private CityCard card3;
	private CityCard card4;
	private FindCureScientific fD ;
	
	@Before
	public void init() {
		this.g = new Game( new JsonMap());
		g.initDeck();
		this.c1 = new City("City Test 1", Sickness.BLACK);
		this.c2 = new City("City Test 2", Sickness.BLACK);
		this.c3 = new City("City Test 3", Sickness.BLACK);
		this.c4 = new City("City Test 4", Sickness.BLACK);
	
		this.p = new Scientific("Player Test", this.c1 , this.g);
		
		this.card1 = new CityCard( this.g, this.c1 );
		this.card2 = new CityCard( this.g, this.c2 );
		this.card3 = new CityCard( this.g, this.c3 );
		this.card4 = new CityCard( this.g, this.c4 );
		
		this.fD = new FindCureScientific( this.p );
		
	}
	
	//-------------------------------------- Test ------------------------------------
	
	@Test
	public void isPossibleTestWithFourCardOfSameSectorAndWithStation() {
		this.p.addCard(card1);
		this.p.addCard(card2);
		this.p.addCard(card3);
		this.p.addCard(card4);
		this.c1.setStation(true);
		assertTrue(this.fD.isPossible());
	}
	
	@Test
	public void isPossibleTestWithoutFourCardOfSameSectorAndWithStation() {
		this.p.addCard(card1);
		this.p.addCard(card2);
		this.c1.setStation(true);
		assertFalse(this.fD.isPossible());
	}
	
	@Test
	public void isPossibleTestWithFourCardOfSameSectorAndWithoutStation() {
		this.p.addCard(card1);
		this.p.addCard(card2);
		this.p.addCard(card3);
		this.p.addCard(card4);
		assertFalse(this.fD.isPossible());
	}
	
	@Test
	public void isPossibleTestWithoutFourCardOfSameSectorAndWithoutStation() {
		this.p.addCard(card1);
		this.p.addCard(card2);
		this.p.addCard(card3);
		assertFalse(this.fD.isPossible());
	}
	
}
