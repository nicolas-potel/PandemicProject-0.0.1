package player.action;

import java.io.IOException;

import exception.NeighbourException;
import listchooser.InteractiveListChooser;
import listchooser.ListChooser;
import player.Player;
import sickness.Sickness;

public class TreatSickness extends Action{
	
	/**
	 * @see Action
	 */
	public TreatSickness(Player p) {
		super(p) ;
	}
	
	/**
	 * @see Action#getName()
	 */
	public String toString() {
		return "To treat the sickness";
	}
	
	/**
	 * @see Action#isPossible
	 */
	public boolean isPossible() {
		return (this.player.getCity().getSicknessList().size() > 0) ;
	}
	
	/**
	 * Treat a sickness in a city by deleting a cube. 
	 * If a cure was been found, so the player deletes all sickness's cubes 
	 * @param s a Sickness
	 */
	public void action(Sickness s) {
		if(player.getGame().isCured(s)) {
			player.getCity().removeCubes(s, player.getCity().getCubes(s));
		}
		else {
			player.getCity().removeCubes(s, 1);
		}
	}
	
	/**
	 * @throws NeighbourException 
	 * @see Action#execute()
	 */
	public void execute()  throws IOException, NeighbourException {
		if (player.getCity().getSicknessList().size() > 1) {
			ListChooser<Sickness> lc = new InteractiveListChooser<>();
			Sickness s = lc.choose("please choose a Sickness:", player.getCity().getSicknessList());
			if (s == null ) {
				this.player.performAction();
			}
			else {
				this.action(s);
			}
			
		}
		else {
			Sickness s = player.getCity().getSicknessList().get(0);
			this.action(s);
		}
	}

	@Override
	public void displayInformation() {
		System.out.println("many sickness are present in the city.");
		System.out.println("To choose a sickness to treat, input the number related of the sickness.");
		
	}
	
}
