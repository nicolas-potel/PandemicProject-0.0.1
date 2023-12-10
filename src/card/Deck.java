package card;

import java.util.ArrayList;
import java.util.List;

import exception.PlayerDeckIsEmptyException;
import game.Game;


public class Deck< T extends Card >{
	
	//--------------------------------------------Attributs---------------------------------------------------
	
	// The stack of the deck
	private List<T> stack;
	//The Discard of the deck
	private List<T> discard;
	//The game of the deck
	private Game game;
	
	//--------------------------------------------Constructor-------------------------------------------------
	
	/**
	 * The constructor of this class
	 * @param g the game of the deck
	 */
	public Deck(Game g) {

		this.stack = new ArrayList<T>();
		this.discard = new ArrayList<T>();
		this.game = g;
	}
	
	//--------------------------------------------Getting Functions---------------------------------------------
	
	/**
	 * Get the stack of this deck.
	 * @return List
	 */
	public List<T> getStack(){
		return this.stack;
	}
	
	/**
	 * Get the discard of this deck.
	 * @return List
	 */
	public List<T> getDiscard(){
		return this.discard ;
	}
	
	/**
	 * Draw the first card of the stack
	 * @return T
	 */
	public T drawCard() throws PlayerDeckIsEmptyException {
		T c;
		try {
			c = this.stack.get(0) ;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new PlayerDeckIsEmptyException("The player deck is empty.");
		}
		this.stack.remove( c ) ;
		return c ;
	}
	
	/**
	 * Draw the first card of the discard
	 * @return T
	 */
	public T drawInDiscard() {
		if (this.discard.isEmpty())
			return null;
		T c = this.discard.get(0) ;
		this.discard.remove( c ) ;
		return c ;
	}
	
	//--------------------------------------------Setting Functions----------------------------------
	
	/**
	 * Sets a card at the end of the stack of this deck
	 * @param c		The card to set
	 * @param pos  	If position=0, set at the end of the stack.
	  		  		Else, set the card at the start of the stack.
	 */
	public void setCard(T c, int pos) {
		if (pos==0)
			this.stack.add(c);
		else {
			List<T> nList = new ArrayList<T>();
			nList.add(c);
			nList.addAll(this.stack);
			this.stack = nList;
		}
	}

	/**
	 * Adds the card in parameter at the end of the discard.
	 * @param c	The card who set 
	 */
	public void discardCard(T c) {
		this.discard.add(c);
		System.out.println("The card "+c.toString()+" has been added to discard.");
	}
	
	/**
	 * Sets the stack in parameter to the specified deck stack
	 * @param l The stack to set
	 */
	public void setStack(List<T> l) {
		this.stack = l;
	}
	
	//----------------------------------------------------Other Function-----------------------------------------
	
	/**
	 * Shuffles the stack of this deck
	 */
	public void shuffleStack() {
		shuffle(this.stack);
	}
	
	/**
	 * Shuffles the discard of this deck
	 */
	public void shuffleDiscard() {
		shuffle(this.discard);
	}
	
	/**
	 * Shuffles the stack in parameter
	 * @param List the stack you want to shuffle
	 */
	private void shuffle(List<T> Stack) {
		List<T> nList = new ArrayList<T>();
		while ( ! (Stack.isEmpty())) {
			int i = Game.getRandomNumber(Stack.size()-1);
			nList.add(Stack.get(i));
			Stack.remove(i);
		}
		Stack.addAll(nList);
	}	
	
	/**
	 * Returns if the stack of the deck is empty
	 * @return Boolean
	 */
	public Boolean isEmpty() {
		return this.stack.isEmpty();
	}
	
}