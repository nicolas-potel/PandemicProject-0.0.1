package player.action.action_role;

import player.Player;
import player.action.TreatSickness;
import sickness.Sickness;

public class TreatSicknessMedic extends TreatSickness{

	public TreatSicknessMedic(Player p) {
		super(p);
	}

	/**
	 * @see TreatSickness#action
	 */
	public void action(Sickness s) {
		player.getCity().removeCubes(s, player.getCity().getCubes(s));
		if (this.player.getGame().isCured(s)) {
			
			// Attention, peut générer IOException et NeighbourException
			// Non traité en cas d'exception
			try { this.player.performAction(); } catch(Exception e) {}
		}
	}
	
}
