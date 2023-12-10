package player.action;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import exception.OutBreakRateTooHighException;
import exception.TooMuchCubesException;
import player.Player;
import player.role.Expert;
import player.role.Medic;
import sickness.Sickness;
import map.*;
import game.*;

public class TreatSicknessTest {
	
	//---------------------------------------- Initialization ------------------------------------------------------------
	
	private Player p; 
	private Game g ;
	private City c ;
	private TreatSickness TS ;
	
	@Before
	public void init() throws TooMuchCubesException, OutBreakRateTooHighException {
		this.g = new Game( new JsonMap());
		this.c = new City("City Test", Sickness.BLACK);
		c.addGame(g);
		this.p = new Expert("Player Test", this.c, this.g);
		this.TS = new TreatSickness(this.p);
		
		this.c.addCubes(Sickness.BLACK, 3);
	}
	
	//---------------------------------------- Test Actions -------------------------------------------------------------
	
	@Test
	public void testActionWhenSicknessIsNotCured() {
		this.TS.action(Sickness.BLACK);
		assertEquals(this.c.getCubes(Sickness.BLACK), 2);
	}
	
	@Test
	public void testActionWhenSicknessIsCured() {
		this.g.setCure(Sickness.BLACK);
		this.TS.action(Sickness.BLACK);
		assertEquals(this.c.getCubes(Sickness.BLACK), 0);
	}
	
	
}
