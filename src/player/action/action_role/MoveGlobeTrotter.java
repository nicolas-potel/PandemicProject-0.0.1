package player.action.action_role;

import player.Player;
import player.action.Action;
import player.action.Move;

import exception.NeighbourException;
import listchooser.InteractiveListChooser;
import listchooser.ListChooser;
import map.City;

public class MoveGlobeTrotter extends Move{

	public MoveGlobeTrotter(Player p) {
		super(p);
	}
	
	/**
	 * @see Move#action
	 */
	@Override
	public void action(City c) {
		this.player.setCity(c);
	}
	
	/**
	 * @see Action#execute()
	 */
	@Override
	public void execute() throws NeighbourException {
		this.displayInformation();
		ListChooser<City> lc = new InteractiveListChooser<>();
		City city = lc.choose("please choose a city :", this.player.getGame().getMap().getCities());
		if (city == null) {
			this.player.performAction();
		}
		else {
			this.action(city);
		}
	}
}
