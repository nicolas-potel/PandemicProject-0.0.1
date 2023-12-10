package player.role;

import game.Game;
import map.City;
import player.Player;
import player.action.*;
import player.action.action_role.TreatSicknessMedic;

public class Medic extends Player {

	public Medic(String n, City c, Game g) {
		super(n, c, g);
	}
	
	/**
	 * give the player's name and the player's role
	 * 
	 * @return a string who describe the player's name and the player's role
	 */
	public String getName() {
		return this.name + ", the Medic";
	}

	@Override
	public void initAction() {
		this.actions.add(new Move(this));
		this.actions.add(new BuildStation(this));
		this.actions.add(new FindCure(this));
		this.actions.add(new TreatSicknessMedic(this));
		this.actions.add(new Nothing(this));
		this.actions.add(new ShowCityStatus(this));
		this.actions.add(new ShowHands(this));
		this.actions.add(new ShowNeighboursOfACity(this));
	}
}