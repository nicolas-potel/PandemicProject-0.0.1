package card;

import exception.OutBreakRateTooHighException;
import exception.TooMuchCubesException;

public interface Card {

	/*
	 * Makes the action performed by the card
	 */
	public abstract void action() throws TooMuchCubesException, OutBreakRateTooHighException;
	
	/**
	 * Displays the card
	 */
	public abstract void displayCard();
	
	/**
	 * Displays the name of the class
	 * @return String
	 */
	public abstract String toString();
	
}
