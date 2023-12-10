package player.action;

import java.util.Iterator;

import card.CityCard;
import exception.NeighbourException;
import listchooser.InteractiveListChooser;
import listchooser.ListChooser;
import map.City;
import player.Player;

public class BuildStation extends Action {
	
	/**
	 * @see Action
	 */
	public BuildStation(Player p) {
		super(p) ;
	}
	
	/**
	 * return true if the player has a card to build a staion in the city, otherwise false.
	 * @return boolean
	 */
	protected boolean hasCityCardToBuildStation() {
		Iterator<CityCard> ite = this.player.getHand().iterator();
		while (ite.hasNext()) {
			CityCard c = ite.next();
			if (this.player.getCity().equals(c.getCity())) {
				this.player.selectCard(c);;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Build a Station in a city where the player is, if the limit of station in the game is reached
	 * the player take an existing station in the game to replace her in the city where the player is.
	 * @param c a city
	 */
	public void action(City c){
		this.player.getGame().createStation(c);
		this.player.discardCityCard(this.player.getSelectedCard());
	}
	
	/**
	 * @throws NeighbourException 
	 * @see Action#execute()
	 */
	public void execute() throws java.io.IOException, NeighbourException {
		if (this.player.getGame().getStationLimit() > this.player.getGame().getNbOfStations()) {
			this.action(this.player.getCity());
		}
		else {
			this.displayInformation();
			ListChooser<City> lc = new InteractiveListChooser<>();
			City city = lc.choose("please choose :", this.player.getGame().getStationList());
			if (this.player.getGame().getStationList().contains(city)) {
				this.player.getGame().removeStation(city);
				this.action(this.player.getCity());
			}
			else{
				this.player.performAction();
			}
		}
	}
	
	/**
	 * @see Action#isPossible()
	 */
	@Override
	public boolean isPossible() {
		return this.hasCityCardToBuildStation() && !this.player.getCity().getStation();
	}
	
	/**
	 * @see Action#getName()
	 */
	public String toString() {
		return "To build a station";
	}

	/**
	 * @see Action#displayInformation()
	 */
	@Override
	public void displayInformation() {
		System.out.println("The station limit has been reached.");
		System.out.println("You must to choose an existing station.");
		System.out.println("The station choosed will gonna be moved of the city where she is then ");
		System.out.println("the staion gonne be built in your city");
		System.out.println("To choose a station, input the number corresponding at the city that has the station.");
		System.out.println("List of existing station :");
	}
	
	
	
}
