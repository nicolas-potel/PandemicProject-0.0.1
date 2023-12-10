package card;

import map.City;
import player.Player;
import game.Game;

public class CityCard extends PlayerCard {

	/** The city represented by this city card*/
	private City city;
		
	/**
	 * Create a new City Card
	 * @param g the game of the card
	 * @param c the city of the card
	 */
	public CityCard(Game g, City c) {
		super(g);
		this.city = c;
	}
	
	/** 
	 * Get the city of this city card
	 * 
	 * @return City
	 */
	public City getCity() {
		return this.city;
	}
	
	/** @see Card#displayCard() */
	public void displayCard() {
		System.out.println("The card drawn is a CityCard for : " + this.city.getName());
	}
	
	
	/** 
	 * Adds the specified card to the deck of the active player
	 * @see PlayerCard#action() 
	 */
	public void action() {
		this.game.getActifPlayer().addCard(this);
	}
	
	/**
	 * Returns the String value for the card
	 */
	public String toString() {
		return this.city.getName()+" city card";
	}
	
}
