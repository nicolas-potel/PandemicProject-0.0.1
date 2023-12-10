package player.action;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import exception.NeighbourException;
import listchooser.InteractiveListChooser;
import listchooser.ListChooser;
import map.City;
import player.Player;

public class Move extends Action{
	
	/**
	 * @see Action
	 */
	public Move(Player p) {
		super(p) ;
	}
	
	/**
	 * move the player in a another city only if the city is a neighbour of the city where is the player.
	 * @param c a city.
	 * @throws NeighbourException.
	 */
	public void action(City c) throws NeighbourException{
		if (this.player.getCity().isNeighbour(c)) {
			this.player.setCity(c);
		}
		else {
			throw new NeighbourException("Cette ville n'est pas une ville voisine." );
		}
	}
	
	/**
	 * @see Action#execute()
	 */
	public void execute() throws NeighbourException, IOException {
		this.displayInformation();
		ListChooser<City> lc = new InteractiveListChooser<>();
		List<City> listNeighbours = new ArrayList<>();
		for (Entry<String, City> entry : this.player.getCity().getNeighbours().entrySet()) {
			listNeighbours.add(entry.getValue());
		}
		City city = lc.choose("please choose a city :", listNeighbours);
		if (city == null) {
			this.player.performAction();
		}
		else {
			this.action(city);
		}
	}
	
	/**
	 * @see Action#getName()
	 */
	public String toString() {
		return "To move";
	}

	@Override
	public void displayInformation() {
		System.out.println("To travel, choose a city in writing the number related at the city");
	}
}
