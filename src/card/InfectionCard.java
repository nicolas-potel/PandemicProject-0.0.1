package card;

import exception.OutBreakRateTooHighException;
import exception.TooMuchCubesException;
import game.Game;
import sickness.Sickness;
import map.City;

public class InfectionCard implements Card {
	
	/** The game on which the action will happen*/
	private Game game;
	
	/** The city represented by this city card*/
	private City city;
	
	
	/**
	 * The constructor of this class
	 * 
	 * @param g The game links to this card
	 * @param c The city of this card
	 */
	public InfectionCard(Game g, City c) {
		this.city = c;
		this.game = g ;
	}
	
	
	/** 
	 * Get the city of this InfectionCard
	 * 
	 * @return the city of this InfectionCard
	 */
	public City getCity() {
		return this.city;
	}
	

	
	/** 
	 * Get the game of this InfectionCard
	 * 
	 * @return the game of this InfectionCard
	 */
	public Game getGame() {
		return this.game;
	}
	
	/** @see Card#displayCard() */
	public void displayCard() {
		System.out.println("The card drawn is an infection card for : " + this.city.getName());
	}
	
	/** @see PlayerCard#action() */
	public void action() throws TooMuchCubesException, OutBreakRateTooHighException {
		Sickness s = this.city.getSector() ;
		this.city.addCubes(s , 1) ;
	}
	
	/**
	 * Returns the String value for the card
	 */
	public String toString() {
		return this.city.getName()+" infection card";
	}
	
}
