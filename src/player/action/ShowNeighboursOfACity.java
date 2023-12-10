package player.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import exception.NeighbourException;
import listchooser.InteractiveListChooser;
import listchooser.ListChooser;
import map.City;
import player.Player;

public class ShowNeighboursOfACity extends Action{

	public ShowNeighboursOfACity(Player p) {
		super(p);
	}
	
	public void action(City c) {
		ListChooser<City> lc = new InteractiveListChooser<>();
		List<City> listNeighbours = new ArrayList<>();
		for (Entry<String, City> entry : c.getNeighbours().entrySet()) {
			listNeighbours.add(entry.getValue());
		}
		City city = lc.choose("You look the neighbours of : " + c.toString(), listNeighbours);
		if (city == null) {
			this.player.performAction();
		}
		else {
			this.action(city);
		}
	}

	@Override
	public void execute() throws NeighbourException, IOException {
		this.action(this.player.getCity());
	}

	@Override
	public String toString() {
		return "To show neighbours of a city"; 
	}

	@Override
	public void displayInformation() {
		// TODO Auto-generated method stub	
	}

}
