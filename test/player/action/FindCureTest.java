package player.action;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import card.CityCard;
import player.Player;
import player.role.Scientific;
import sickness.Sickness;
import map.*;
import game.*;

public class FindCureTest {
	
	//------------------------------ Initialization ---------------------------
	
	private Player p ;
	private Game g ;
	private City c1 ;
	private City c2 ;
	private City c3 ;
	private City c4 ;
	private City c5 ;
	private CityCard card1;
	private CityCard card2;
	private CityCard card3;
	private CityCard card4;
	private CityCard card5;
	private FindCure fD ;
	
	@Before
	public void init() {
		this.g = new Game( new JsonMap());
		g.initDeck();
		this.c1 = new City("City Test 1", Sickness.BLACK);
		this.c2 = new City("City Test 2", Sickness.BLACK);
		this.c3 = new City("City Test 3", Sickness.BLACK);
		this.c4 = new City("City Test 4", Sickness.BLACK);
		this.c5 = new City("City Test 5", Sickness.BLACK);
	
		this.p = new Scientific("Player Test", this.c1 , this.g);
		
		this.card1 = new CityCard( this.g, this.c1 );
		this.card2 = new CityCard( this.g, this.c2 );
		this.card3 = new CityCard( this.g, this.c3 );
		this.card4 = new CityCard( this.g, this.c4 );
		this.card5 = new CityCard( this.g, this.c5 );
		
		this.fD = new FindCure( this.p );
		
	}
	
	//-------------------------------- Test Action -------------------------------
	
	@Test
	public void testActionWhenDonTHaveFiveCardOfSameSickness(){
		this.p.addCard(card1);
		this.fD.action( Sickness.BLACK);
		assertEquals(this.g.isCured(Sickness.BLACK), false);
	}
	
	@Test
	public void testActionWhenHaveFiveCardOfSameSickness(){
		this.p.addCard(card1);
		this.p.addCard(card2);
		this.p.addCard(card3);
		this.p.addCard(card4);
		this.p.addCard(card5);
		assertEquals(5, p.getHand().size());
		this.fD.action(Sickness.BLACK);
		assertEquals(this.g.isCured(Sickness.BLACK), true);
		assertEquals(0, p.getHand().size());
	}
	
	@Test 
	public void ACureCanBeFound() {
		this.p.addCard(card1);
		this.p.addCard(card2);
		this.p.addCard(card3);
		this.p.addCard(card4);
		this.p.addCard(card5);
		c1.setStation(true);
		assertTrue(fD.isPossible());
		
	}
	
	
	
	
}
