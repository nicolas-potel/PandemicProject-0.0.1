package main;

import card.*;
import exception.OutBreakRateTooHighException;
import exception.TooMuchCubesException;
import game.Game;
import map.JsonMap;

public class Delivery2Main {

	public static void main(String[] args) throws TooMuchCubesException, OutBreakRateTooHighException {
		JsonMap m = new JsonMap();
    	Game g = new Game(m);
    	g.initMap();
    	g.initPlayer();
    	g.initDeck();
    	g.initInfection();
    	g.drawInfectionCard().action();
    	g.nextTurn();
    	Game.displayLine();
    	g.drawInfectionCard().action();
    	Game.displayLine();
    	
    	for (int j=0;j<g.getPlayersList().size();j++) {
    		g.nextTurn();
    		g.nextPlayer();
    		
    		for(int i=0;i<2;i++) {
	    		PlayerCard card = g.drawPlayerCard();
	    		if(card instanceof CityCard) {
	    			CityCard c = (CityCard) card;
	    			g.getActifPlayer().addCard(c);
	    		} else {
	    			PandemicCard c = (PandemicCard) card;
	    			c.action();
	    		}
	    	}
    	}
	
	
	
	
	
	
	
	}
}
