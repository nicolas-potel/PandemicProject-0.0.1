package player.action.action_role;

import player.Player;
import player.action.FindCure;

public class FindCureScientific  extends FindCure{

	public FindCureScientific(Player p) {
		super(p);
	}
	
	/**
	 *@see Action#isPossible
	 */
	public boolean isPossible() {
		return this.player.getCity().getStation() && super.aCureIsPossible(4);
	}

}
