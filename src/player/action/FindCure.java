package player.action;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import card.CityCard;
import exception.NeighbourException;
import listchooser.InteractiveListChooser;
import listchooser.ListChooser;
import player.Player;
import sickness.Sickness;

public class FindCure extends Action{

	/**
	 * @see Action
	 */
	public FindCure(Player p) {
		super(p) ;
	}
	
	/**
	 * return true if the player has 5 cards of the same sickness otherwise false.
	 * @param s the sickness to analyze
	 * @return boolean
	 */
	protected boolean hasIdenticCards(Sickness s, int n) {
		int cpt = 0;
		if (this.player.getHand().size() >= n) {
			Iterator<CityCard> ite = this.player.getHand().iterator();
			while(ite.hasNext()) {
				CityCard c = ite.next();
				if(c.getCity().getSector().equals(s)) {
					this.player.addCardInListDiscardable(c);
					cpt++;
				}
			}
			
		}
		return cpt >= n;
	}
	
	/**
	 * Find if a cure is possible for any sickness.
	 * If the sickness can have a cure add this sickness is a list of possible cure.
	 * @return boolean
	 */
	protected boolean aCureIsPossible(int n) {
		boolean found = false;
		for (Entry<Sickness, Boolean> entry : this.player.getGame().getCures().entrySet()) {
			if (entry.getValue() == false) {
				if (this.hasIdenticCards(entry.getKey(), n)) {
					found = true;
					this.player.getPossibleCure().add(entry.getKey());
				}
			}
			
		}
		return found;
	}
	
	/**
	 * @see Action#getName()
	 */
	public String toString() {
		return "To find a cure";
	}
	
	/**
	  * find a cure if the player has 5 cards of the same sickness and the sickness isn't safe.
	 * @param s a Sickness
	 */
	public void action(Sickness s) {
		int cpt = 0;
		Iterator<CityCard> ite = player.getHand().iterator();
		while(ite.hasNext() && cpt < 5) {
			CityCard c = ite.next();
			if (c.getCity().getSector().equals(s)) {
				this.player.getGame().getPlayerDeck().discardCard(c);
				ite.remove();
				cpt++;
			}
		}
		if (cpt == 5) {
			player.getGame().setCure(s);
		}
	}
	
	/**
	 * @throws NeighbourException 
	 * @see Action#execute()
	 */
	public void execute()  throws IOException, NeighbourException {
		int cpt = 0;
		Sickness s = null;
		for (Entry<Sickness, Boolean> entry : player.getGame().getCures().entrySet()) {
			if(!entry.getValue()) {
				cpt++;
			}
			s = entry.getKey();
		}
		if(cpt == 1) {
			this.action(s);
		}
		else {
			this.displayInformation();
			ListChooser<Sickness> lc = new InteractiveListChooser<>();
			Sickness choice = lc.choose("please choose :", this.player.getPossibleCure());
			if (choice == null) {
				this.player.performAction();
			}
			else {
				this.action(choice);
			}
		}
	}
	
	/**
	 * @return 
	 * @see Action#isPossible()
	 */
	@Override
	public boolean isPossible() {
		return this.player.getCity().getStation() && this.aCureIsPossible(5);
	}

	@Override
	public void displayInformation() {
		System.out.println("Many cure can be found");
		System.out.println("You must to choose a sickness you want to heal");
	}
	
}
