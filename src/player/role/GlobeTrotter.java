package player.role;

import game.Game;
import map.City;
import player.Player;
import player.action.*;
import player.action.action_role.MoveGlobeTrotter;

public class GlobeTrotter extends Player {

	public GlobeTrotter(String n, City c, Game g) {
		super(n, c, g);
	}
	
	/**
	 * give the player's name and the player's role
	 * 
	 * @return a string who describe the player's name and the player's role
	 */
	public String getName() {
		return this.name + ", the GlobeTrotter";
	}

	/**
	 * @see Player#initAction()
	 */
	@Override
	public void initAction() {
		this.actions.add(new MoveGlobeTrotter(this));
		this.actions.add(new BuildStation(this));
		this.actions.add(new FindCure(this));
		this.actions.add(new TreatSickness(this));
		this.actions.add(new Nothing(this));
		this.actions.add(new ShowCityStatus(this));
		this.actions.add(new ShowHands(this));
		this.actions.add(new ShowNeighboursOfACity(this));
	}
}
