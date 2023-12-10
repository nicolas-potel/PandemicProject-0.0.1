package player.action;

import java.io.IOException;
import java.util.Iterator;

import exception.NeighbourException;
import map.*;
import player.Player;
import sickness.Sickness;

public class ShowCityStatus extends Action{

	public ShowCityStatus(Player p) {
		super(p);
	}

	@Override
	public void execute() throws NeighbourException, IOException{
		String res = "";
		WorldMap m = player.getGame().getMap();
		Iterator<City> ite1 = m.getCities().iterator();
		while (ite1.hasNext()) {
			City c = ite1.next();
			boolean cubes = true;
			for(Sickness s : c.getMapCubes().keySet()) {
				cubes = (c.getCubes(s)==0) && cubes;
			}
			if (!cubes) c.displayPresentSickness();
		}
		this.player.performAction();
	}

	@Override
	public String toString() {
		return "To show the status of each city";
	}

	@Override
	public void displayInformation() {
		// TODO Auto-generated method stub
		
	}

}
