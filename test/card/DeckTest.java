package card;

import game.Game;
import map.*;

import java.util.*;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class DeckTest {
	
	private WorldMap m;
	private Game g;
	private Deck d;

	@Before
	public void init() {
		this.m = new JsonMap();
		this.g = new Game(this.m);
		this.d = new Deck(this.g);
	}
	
	@Test
	public void testInitializationAndAccessor() {
		assertEquals(0, this.d.getDiscard().size());
		assertEquals(0, this.d.getStack().size());
	}
	
	@Test
	public void testSetCardInTheStack() {
		InfectionCard IC1 = new InfectionCard(this.g, this.m.getCity("ville-1"));
		InfectionCard IC2 = new InfectionCard(this.g, this.m.getCity("ville-2"));
		InfectionCard IC3 = new InfectionCard(this.g, this.m.getCity("ville-3"));
		this.d.setCard(IC1, 0);
		assertEquals(IC1, this.d.getStack().get(0));
		this.d.setCard(IC2, 0);
		assertEquals(IC2, this.d.getStack().get(1));
		this.d.setCard(IC3, 1);
		assertEquals(IC3, this.d.getStack().get(0));
		assertEquals(IC1, this.d.getStack().get(1));
		assertEquals(IC2, this.d.getStack().get(2));
	}
	
	@Test
	public void testSetCardInDiscard() {
		InfectionCard IC1 = new InfectionCard(this.g, this.m.getCity("ville-1"));
		InfectionCard IC2 = new InfectionCard(this.g, this.m.getCity("ville-2"));
		this.d.discardCard(IC1);
		assertEquals(IC1, this.d.getDiscard().get(0));
		this.d.discardCard(IC2);
		assertEquals(IC2, this.d.getDiscard().get(1));
	}
	
	@Test
	public void testSetAStackOfCardInTheStack() {
		InfectionCard IC1 = new InfectionCard(this.g, this.m.getCity("ville-1"));
		InfectionCard IC2 = new InfectionCard(this.g, this.m.getCity("ville-2"));
		InfectionCard IC3 = new InfectionCard(this.g, this.m.getCity("ville-3"));
		List<Card> StackCard = new ArrayList<>();
		StackCard.add(IC1);
		StackCard.add(IC2);
		StackCard.add(IC3);
		this.d.setStack(StackCard);
		assertEquals(StackCard, this.d.getStack());
	}

	@Test
	public void testShuffleStackSameCardsAfter() {
		InfectionCard IC1 = new InfectionCard(this.g, this.m.getCity("ville-1"));
		InfectionCard IC2 = new InfectionCard(this.g, this.m.getCity("ville-2"));
		InfectionCard IC3 = new InfectionCard(this.g, this.m.getCity("ville-3"));
		List<Card> StackCard = new ArrayList<>();
		StackCard.add(IC1);
		StackCard.add(IC2);
		StackCard.add(IC3);
		this.d.setStack(StackCard);
		this.d.shuffleStack();
		for (Card c : StackCard) {
			assertTrue(this.d.getStack().contains(c));
		}
	}
	
	@Test
	public void testShuffleDiscardSameCardsAfter() {
		InfectionCard IC1 = new InfectionCard(this.g, this.m.getCity("ville-1"));
		InfectionCard IC2 = new InfectionCard(this.g, this.m.getCity("ville-2"));
		InfectionCard IC3 = new InfectionCard(this.g, this.m.getCity("ville-3"));
		List<Card> StackCard = new ArrayList<>();
		StackCard.add(IC1);
		StackCard.add(IC2);
		StackCard.add(IC3);
		this.d.discardCard(IC1);
		this.d.discardCard(IC2);
		this.d.discardCard(IC3);
		this.d.shuffleDiscard();
		for (Card c : StackCard) {
			assertTrue(this.d.getDiscard().contains(c));
		}
	}
	
}
