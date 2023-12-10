package card;

import exception.OutBreakRateTooHighException;
import exception.TooMuchCubesException;
import game.Game;
/*
 * An abstract class to represent the Player cards
 */
public abstract class PlayerCard implements Card {

	/** The game on which the action will happen*/
	protected Game game;
	
	/**
	 * Create a new PlayerCard
	 * @param g the game of the card
	 */
	public PlayerCard(Game g) {
		this.game = g;
	}
	
	/**
	 * Get the game of this card
	 * 
	 * @return the game of this card
	 */
	public Game getGame() {
		return this.game;
	}
	
	/**
	 * The action performed by this card
	 */
	public abstract void action() throws TooMuchCubesException, OutBreakRateTooHighException;
	
	/**
	 * Returns a string representing the name of the class
	 * @return String
	 */
	public String toString() {
		return " player card";
	}
}
