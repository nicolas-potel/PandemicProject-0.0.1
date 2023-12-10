package main;
import exception.OutBreakRateTooHighException;
import exception.TooMuchCubesException;
import game.Game;
import map.JsonMap;


public class Delivery3Main {

	public static void main(String[] args) throws TooMuchCubesException, OutBreakRateTooHighException {
		JsonMap m = new JsonMap();
    	Game g = new Game(m);
    	if (args.length!=0 && args.length < 5) {
    		//for (int i=0; i<args.length; i++) g.getPlayersList()
    	}
    	g.initMap();
    	g.initPlayer();
    	g.initDeck();
    	g.initInfection();
    	
    	g.nextPlayer();
    	for (int i=0; i<g.getPlayersList().size(); i++) {
    		for (int j=0; j<4; j++) {
    			g.getActifPlayer().performAction();
    		}
    		for (int k=0; k<2; k++) g.drawPlayerCard().action();
    		
    		for (int l=0; l<g.getGlobalInfectionRate(); l++) g.drawInfectionCard().action();
    		
    		g.nextTurn();
    		g.nextPlayer();
    	}

	}

}
