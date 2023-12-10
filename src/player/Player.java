package player;

import map.City;
import player.action.*;
import sickness.Sickness;

import java.io.IOException;
import java.util.*;

import card.CityCard;
import exception.NeighbourException;
import game.Game;
import listchooser.InteractiveListChooser;
import listchooser.ListChooser;

public abstract class Player {
	
	/** The name of this player. */
	protected String name;
	/** The list of card that this player owns. */
	protected List<CityCard> hand;
	/** the list of sickness that the play can cure*/
	protected List<Sickness> possibleCure;
	/** cards that the player want discards*/
	protected List<CityCard> CitiesCardDiscardable;
	/** the list of action */
	protected List<Action> actions; 
	/** the list of action possible  */
	protected List<Action> actionsFilter;
	/** The city where this player is. */
	protected City city;
	/** the card selected if the player can build a station*/
	protected CityCard selectedCard;
	/** The game where the player plays.*/
	protected Game game;
	
	public static int GLOBAL_CARD_LIMIT = 7;
	
	
	/**
	 * The Constructor of this Player.
	 * 
	 * @param n The name of this player.
	 * @param c The city where this player is at the start.
	 * @param g the game of the player
	 */
	public Player (String n, City c, Game g) {
		this.name = n;
		this.city = c;
		this.game = g;
		this.hand = new ArrayList<CityCard>();
		this.actions = new ArrayList<>();
		this.possibleCure = new ArrayList<>();
		this.CitiesCardDiscardable = new ArrayList<>();
		
		this.initAction();
	}
	
	/**
     * ---------------------------------------------------------------
     * ----------------------- Getting Methods -----------------------
     * ---------------------------------------------------------------      
     */
	
	/**
	 * Getting the name of this player.
	 * 
	 * @return The name of this player.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getting the game of the player
	 * @return Game
	 */
	public Game getGame() {
		return this.game;
	}

	/**
	 * Get the city where this player is.
	 * 
	 * @return the city where this player is.
	 */
	public City getCity() {
		return this.city;
	}
	
	/**
	 * Getting the selected card of the player
	 * @return CityCard
	 */
	public CityCard getSelectedCard() {
		return this.selectedCard;
	}

	/**
	 * Get the list of card who this player are in his hands.
	 * 
	 * @return The list of card who this player are in his hands.
	 */
	public List<CityCard> getHand() {
		return this.hand;
	}
	
	/**
	 * get cards that will be discardable.
	 * 
	 * @return List
	 */
	public List<CityCard> getCitiesCardDiscardable(){
		return this.CitiesCardDiscardable;
	}
	
	/**
	 * Get the card's number of the player's hand.
	 * 
	 * @return the card's number of the player's hand.
	 */
	public int getNbCard() {
		return this.hand.size();
	}
	
	/**
	 * get the actions list that the player can to do.
	 * @return List
	 */
	public List<Action> getAction(){
		return this.actions;
	}
	
	/**
	 * get the action list that the player can to do.
	 */
	public List<Action> getActionFilter() {
		return this.actionsFilter;
	}
	
	/**
	 * get the possible cure.
	 * @return List
	 */
	public List<Sickness> getPossibleCure(){
		return this.possibleCure;
	}
	
	/**
	 * Set a new city as the new position of this player.
	 * 
	 * @param c The new city.
	 */
	public void setCity(City c) {
		if (this.city != null) {
			this.city.removePlayer(this);
		}
		this.city = c ;
		this.city.addPlayer(this);
	}
	
	/**
	 * Set a name for the player
	 * 
	 * @parma n the player's name
	 */
	public void setName(String n) {
		this.name = n;
	}
	
	/**
	 * Set a new card in the hands of this player.
	 * 
	 * @param c The new card.
	 */
	public void addCard(CityCard c) {
		this.hand.add(c);
		if (this.hand.size()>Player.GLOBAL_CARD_LIMIT) {
			System.out.println("The player "+this.name+" cannot have more player cards.");
			System.out.println("insert the number of the card to discard: ");
			ListChooser<CityCard> lc = new InteractiveListChooser<>();
			CityCard cardDiscard = lc.choose("please choose a card:", this.hand);
			if (cardDiscard == null) {
				this.hand.remove(c);
				this.addCard(c);
			}
			else {
				this.discardCityCard(cardDiscard);
			}
		}
		System.out.println("The city card of sector " +c.getCity().getSector()+", "+c.getCity().toString()+" has been added to "+this.name+"'s hand.");
		Game.displayLine();
	}
	
	/**
	 * add a card in a list of cards that will be discard
	 * @param c
	 */
	public void addCardInListDiscardable(CityCard c) {
		this.CitiesCardDiscardable.add(c);
	}
	
	
	
	/**
     * -------------------------------------------------------------
     * ----------------------- Useful Methods -----------------------
     * -------------------------------------------------------------      
     */
	
	/**
	 * select a card
	 * @param c the city of the card to be selected
	 */
	public void selectCard(CityCard c) {
		this.selectedCard = c;
	}
	
	/**
	 * the player play a card of his hand and discard this card.
	 * @param c the city of the card to be discarded
	 */
	public void discardCityCard(CityCard c) {
		this.hand.remove(c);
		this.game.getPlayerDeck().discardCard(c);
	}
	
	
	/**
     * -------------------------------------------------------------
     * ----------------------- Action Methods -----------------------
     * -------------------------------------------------------------      
     */
	
	/**
	 * init actions allowed for the player in the game
	 * @throws NeighbourException if the selected city is not a neighbour of the city where the player is
	 * @throws IOException if wrong input type
	 */
	public abstract void initAction();
	
	public void filter() {
		this.actionsFilter = new ArrayList<>();
		Iterator<Action> ite = this.actions.iterator();
		while(ite.hasNext()) {
			Action action = ite.next();
			if (action.isPossible()) {
				this.actionsFilter.add(action);
			}
		}
	}
	
	/**
	 * perform an action choosed by the player.
	 * @throws IOException if wrong input type
	 * @throws NeighbourException if the selected city is not a neighbour of the city where the player is
	 */
	public void performAction(){
		this.displayAction();
		this.filter();
		this.displayCubes();
		System.out.println("You are in the city : " + this.getCity() );
		ListChooser<Action> lc = new InteractiveListChooser<>();
		Action action = lc.choose(this.getName() + ", choose", this.actionsFilter);
		if (action == null) {
			System.out.println("you must choose an action.");
			this.performAction();
		}
		else {
			try {
				action.execute();
			} catch (NeighbourException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
     * -------------------------------------------------------------
     * ----------------------- String Methods -----------------------
     * -------------------------------------------------------------      
     */
	
	/**
	 * Display the name of the player and his card's number.
	 *
	 *@return a string to display the name of the player and his card's number.
	 */
	public String toString() {
		return this.getName() + " has " + this.getNbCard() + " cards." ;
	}
	
	/**
	 * Display the line describing how to choose an action
	 */
	public void displayAction() {
		System.out.println("to choose an action, write the number related with the action.");
	}
	
	/**
	 * Display the total number of cubes for each sickness on the map
	 */
	public void displayCubes() {
		Set<Sickness> sicknesses = this.game.getCubes().keySet();
		System.out.println("There is :");
		for(Sickness s : sicknesses) {
			System.out.println(this.game.getCubes().get(s)+" cubes of sickness "+s+",");
		}
		System.out.println("in total on the map.");
	}

}
