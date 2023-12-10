package card;

import exception.OutBreakRateTooHighException;
import exception.TooMuchCubesException;
import game.Game;

public class PandemicCard extends PlayerCard {
	
	/**
	 * Create a new pandemic Card
	 * @param g the game of the card
	 */
	public PandemicCard(Game g) {
		super(g);
	}
	
	/**
	 * Increase the global infection rate of the game
	*/
	public void acceleration() {
		Game.displayLine();
		System.out.println("Playing the acceleration of the pandemic card.");
		this.game.increaseGlobalInfectionRate();
		System.out.println("The global infection rate has been increased to "+this.game.getGlobalInfectionRate()+".");
		Game.sleep(100);
	}
	
	/**
	 * Draw the first InfectionCard and add 3 cubes at the city 
	  of this InfectionCard
	 */
	public void infection() throws TooMuchCubesException,  OutBreakRateTooHighException {
		Game.displayLine();
		System.out.println("Playing the infection of the pandemic card.");
		InfectionCard IC = this.game.drawInfectionCard();
		if (!(this.game.isEradicated(IC.getCity().getSector()))) {
			IC.getCity().setInfectedFalse();
			int cubesBeforeThree = 3 - IC.getCity().getCubes(IC.getCity().getSector());
			for (int i=0; i<cubesBeforeThree; i++) {
				IC.action();
				this.game.nextTurn();
			}
		}
		
		
	}
	
	/**
	 * Shuffle the discard of infection deck of this game then, set the discard 
	 * in the start oh the stack.
	 */
	public void intensification() {
		Game.displayLine();
		System.out.println("Playing the intensification of the pandemic card.");
		this.game.getInfectionDeck().shuffleDiscard();
		System.out.println("The discard of the infection deck has been shuffled.");
		Game.sleep(100);
		while (!( this.game.getInfectionDeck().getDiscard().isEmpty() )) {
			InfectionCard c = this.game.getInfectionDeck().drawInDiscard();
			this.game.getInfectionDeck().setCard(c, 1);
		}
		System.out.println("The discard of the infection deck has been added to the top of it.");
		Game.sleep(100);
	}
	
	/** @see Card#displayCard() */
	public void displayCard() {
		System.out.println("The card that has been drawn is a pandemic card.");
		Game.sleep(100);
	}
	
	/**
	 * The action performed by this card
	 */
	public void action() throws TooMuchCubesException, OutBreakRateTooHighException {
		this.displayCard();
		this.acceleration();
		this.infection() ;
		this.intensification();
		Game.displayLine();
		Game.sleep(100);
	}
	
	/**
	 * Returns the String value for the card
	 * @return String
	 */
	public String toString() {
		return "pandemic card";
	}

	
	
}
