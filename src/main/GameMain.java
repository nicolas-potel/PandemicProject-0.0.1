package main;
import game.*;
import map.*;

public class GameMain {

	public static void main(String[] args) {
		JsonMap m = new JsonMap();
    	Game g = new Game(m);
    	g.play();
	}

}
