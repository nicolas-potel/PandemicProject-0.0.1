package player.action;

import java.io.IOException;

import exception.NeighbourException;
import player.Player;

public abstract class Action {
	
	protected Player player;
	
	/**
	 * The constructor of this class
	 * @param p  The player who execute this action
	 */
	public Action(Player p) {
		this.player = p ;
	}
	
	/**
	 * Get the player of this action
	 * @return this.player
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * Execute the effect of this action
	 */
	public abstract void execute() throws NeighbourException, IOException ;

	/**
	 * return true if the action is possible, otherwise false
	 * @return boolean 
	 */
	public boolean isPossible() {
		return true;
	}
	
	/**
	 * get the name of this action.
	 * @return a String
	 */
	public abstract String toString();
	
	/**
	 * display all informations before the request for choose
	 */
	public abstract void displayInformation();
}
