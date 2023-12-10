package player.action.action_role;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import card.CityCard;
import game.Game;
import map.City;
import map.JsonMap;
import player.action.BuildStation;
import player.role.Expert;
import sickness.Sickness;

public class BuildStationExpertTest {

	private Game g;
	private City c;
	private Expert p;
	private CityCard card;
	private BuildStation build;
	
	@Before
	public void init() {
		this.g = new Game(new JsonMap());
		this.c = new City("CityTest", Sickness.BLACK);
		this.p = new Expert("PlayerTest", c, g);
		this.build = new BuildStationExpert(p); 
		this.card = new CityCard(g, c);
		g.initDeck();
	}
	
	@Test
	public void isPossibleTestWithoutStationInTheCity() {
		c.setStation(false);
		boolean res = this.build.isPossible();
		assertEquals(true, res);
	}
	
	@Test
	public void isPossibleTestWithStationInTheCity() {
		c.setStation(true);
		boolean res = this.build.isPossible();
		assertEquals( false, res);
	}
	
	
}
