package player.action;

import java.io.IOException;
import java.util.*;

import card.CityCard;
import exception.NeighbourException;
import player.Player;

public class ShowHands extends Action{
	
	/**
	 * @see Action
	 */
	public ShowHands(Player p) {
		super(p) ;
	}
	
	/**
	 * @see Action#getName()
	 */
	public String toString() {
		return "To show the hand";
	}
	
	/**
	  * Show the hands of this player 
	 */
	public void action() {
		List<CityCard> hand = this.player.getHand();
		if (hand.isEmpty()) {
			System.out.println("You don't have card in your hand.");
		}
		else {
			for (CityCard card : hand) {
				card.displayCard();
			}
		}
	}
	
	/**
	 * @see Action#execute()
	 */
	public void execute() throws NeighbourException , IOException{
		action();
		this.player.performAction();
	}

	@Override
	public void displayInformation() {
		// TODO Auto-generated method stub
		
	}
	
}
