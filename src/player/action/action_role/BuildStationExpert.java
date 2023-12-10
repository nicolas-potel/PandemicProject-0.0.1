package player.action.action_role;

import map.City;
import player.Player;
import player.action.BuildStation;

public class BuildStationExpert extends BuildStation{

	public BuildStationExpert(Player p) {
		super(p);
	}
	
	/**
	 * @see Action#isPossible()
	 */
	@Override
	public boolean isPossible() {
		return !this.player.getCity().getStation(); 
	}

	/** @see BuildStation#action()*/
	@Override
	public void action(City c){
		this.player.getGame().createStation(c);
	}
	
	
}
