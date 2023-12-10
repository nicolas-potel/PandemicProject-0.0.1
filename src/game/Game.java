package game;

import java.util.*;
import java.io.FileNotFoundException;

import player.*;
import player.role.*;
import sickness.*;
import card.*;
import listchooser.ListChooser;
import listchooser.RandomListChooser;
import listchooser.util.Input;
import map.*;
import exception.*;

public class Game {

        /* List of players */
        private List<Player> players;
        
        /* The player who play the turn */
        private Player playerActif;

        // Decks of Infection and Player cards
        private Deck<InfectionCard> infectionDeck ;
        private Deck<PlayerCard> playerDeck ;

        /* The global Infection Rate */
        private int[] globalInfectionRate = {2, 2, 2, 3, 3, 4, 4};
        private int infectionRateIndex;

        /* Number of sickness sources on the map */
        private int sources;
        
        /* the index corresponding to the playerActif */
		private int index;

        /* Number of cubes for each sickness */
        private Map<Sickness, Integer> cubes;
        
        /* A list of city with a station*/
        private List<City> StationList;
        
        /* An available role list */
        private List<Player> roles; 

        /* the cube's number limit for each sickness.*/
        private static int GLOBAL_CUBE_LIMIT = 24;

        /** the station's number limit */
        private static int GLOBAL_STATION_LIMIT = 6;
        
        /** the outbreak rate */
        private int outbreakRate;

        /* Number of stations in the game */
        private int stationinGame;
        
        /* The map of this game */
        private WorldMap map;
        
        /* The cure for each sickness */
        private Map<Sickness, Boolean> cures;
        
        /** Show ii it is time for infection at the start of the game*/
        private Boolean infectionTime;

        /**
         * Builder of class Game
         * @param m the worldmap of the game
         */
        public Game (WorldMap m) {
        	this.cures = new HashMap<Sickness, Boolean>();
        	Sickness[] a = Sickness.values();
        	for (Sickness i : a) {
    			this.cures.put(i,false);
    		}
        	this.cubes = new HashMap<Sickness, Integer>();
        	for (Sickness i : a) {
        		this.cubes.put(i, 0);
        	}
        	this.map = m;
        	this.index = 0;
        	this.players = new ArrayList<>();
        	this.StationList = new ArrayList<>();
        	this.outbreakRate = 0;
        	this.infectionRateIndex = 0;
        	this.infectionTime = true;
        	
        	this.initRoles();
        }
        
        /**
         *  ------------------------------------------------------------
         *  -------------------- Getting Methods ----------------------- 
         *  ------------------------------------------------------------ 
         */
        
        /**
         * get the map of this game.
         * @return WorldMap.
         */
        public WorldMap getMap() {
        	return this.map;
        }
        
        /**
         * get the hashmap represented the number of cubes for each sickness
         * @return the hashmap represented the cubes
         */
        public Map<Sickness, Integer> getCubes() {
			return cubes;
		}

		/**
         * Returns the number assigned to the player who is playing his turn
         * @return int
         */
        public int getIndex() {
        	return this.index;
        }
        
        /**
         * Return the global infection rate
         * @return int
         */
        public int getGlobalInfectionRate() {
        	return this.globalInfectionRate[this.infectionRateIndex];
        }
        
        /**
         * Return the number of infection sources
         * @return int
         */
        public int getNbOfSources() {
        	return this.sources;
        }
        
        /**
         * Return the cubes limit per city
         * @return int
         */
        public int getCubeLimit() {
        	return Game.GLOBAL_CUBE_LIMIT;
        }
        
        /**
         * Return the number of cubes for the specified sickness
         * @param s The specified sickness
         * @return int
         */
        public int getNbOfCubes(Sickness s) {
        	return this.cubes.get(s);
        }
        
        /**
         * Return the global station limit
         * @return int 
         */
        public int getStationLimit() {
        	return Game.GLOBAL_STATION_LIMIT;
        }
        
        /**
         * Return the number of stations in game
         * @return int
         */
        public int getNbOfStations() {
        	return this.stationinGame;
        }
        
        /**
         * Return a random number between 0 and the specified parameter
         * @param n the max value that can be returned
         * @return int a random number
         */
        public static int getRandomNumber(int n) {
        	Random r = new Random();
        	int res = r.nextInt(n+1);
        	return res;
        }
        
        /**
         * give the list of players in the game
         * @return List
         */
        public List<Player> getPlayersList(){
        	return this.players;
        }
        
        
        /**
         * get the avaible role list in the game
		 * @return the roles
		 */
		public List<Player> getRoles() {
			return roles;
		}

		/**
         * Give the list of Station of this game.
         * Each station has a number assigned.
         * @return Map.
         */
        public List<City> getStationList() {
        	return this.StationList;
        }
        
        /**
         * give the player Actif in the game
         * @return Player
         */
        public Player getActifPlayer() {
        	return this.playerActif;
        }
        
        /**
         * Give the deck of the infection card
         * @return infectionDeck
         */
        public Deck<InfectionCard> getInfectionDeck(){
        	return this.infectionDeck ;
        }
        
        /**
         * Give the deck of the player card
         * @return playerDeck
         */
        public Deck<PlayerCard> getPlayerDeck(){
        	return this.playerDeck ;
        }
        
        /**
         * Get sickness and their states.  
         * True if the sickness a cure has been found, otherwise false.
         * @return Map
         */
        public Map<Sickness, Boolean> getCures(){
        	return this.cures;
        }
        
        /**
         * Gives the outbreak rate of the game
         * @return int
         */
        public int getOutbreakRate() {
        	return this.outbreakRate;
        }
        
        
        /**
         * ---------------------------------------------------------------
         * ----------------------- Setting Methods -----------------------
         * ---------------------------------------------------------------        
         */
        
        /**
         * Increase the global infection rate
         */
        public void increaseGlobalInfectionRate() {
        	this.infectionRateIndex+=1;
        }
        
        /**
         * Increase the number of sources
         */
        public void increaseNbOfSources() {
        	this.sources++;
        }
        
        /**
         * Add n cubes of the Sickness s in the total number of cubes
         * @param s The sickness to modify
         * @param n The number of cubes to increase
         */
        public void increaseNbOfCubes(Sickness s, int n) {
        	int a = this.cubes.get(s);
        	if (a+n <= Game.GLOBAL_CUBE_LIMIT) {
        		this.cubes.put(s, a+n);
        	}
        }
        
        
        /**
         * Remove n cubes of the Sickness s in the total number of cubes
         * @param s The sickness to modify
         * @param n The number of cubes to decrease
         */
        public void decreaseNbOfCubes(Sickness s, int n) {
        	int a = this.cubes.get(s);
        	this.cubes.put(s, a-n);
        }
        /**
         * define a cure like safe
         * @param s the sickness to cure
         */
        public void setCure(Sickness s) {
        	this.cures.replace(s, true);
        }
        
        /**
         * Sets the player deck of the game at the given parameter
         * @param d The Deck to be set.
         */
        public void setPlayerDeck(Deck<PlayerCard> d) {
        	this.playerDeck = d;
        }
        
        /**
         * Sets the infection deck of the game at the given parameter
         * @param d The Deck to be set.
         */
        public void setInfectionDeck(Deck<InfectionCard> d) {
        	this.infectionDeck = d;
        }
        
        /**
         * Sets the infection time at false because it is over
         */
        protected void setInfectionTimeFalse() {
        	this.infectionTime = false;
        }
               
        /**
         * Increase the outbreak rate of the game
         */
        public void increaseOutbreakRate() {
        	this.outbreakRate++;
        	System.out.println("The outbreak rate has been increased to "+this.outbreakRate+".");
        }
        
        
        /**
         * -------------------------------------------------------------
         * ----------------------- String Methods -----------------------
         * -------------------------------------------------------------      
         */
        
        /**
         * display a list of station existing in the game with a their number assigned.
         */
        public void displayStationList() {
        	Iterator<City> ite = this.StationList.iterator();
        	while (ite.hasNext()) {
        		City c = ite.next();
        		System.out.println(c.toString());
        	}
        }
        
        /**
         * Displays a separating line on terminal
         */
        public static void displayLine() {
        	System.out.println("----------");
        }
        
        
        /**
         * -------------------------------------------------------------
         * ----------------------- Other Methods -----------------------
         * -------------------------------------------------------------      
         */
        
        /**
         * add a player in the player list of the game.
         * @param p, a player 
         */
        protected void addPlayer(Player p) {
        	this.players.add(p);
        }
        
        /**
         * Return if specified sickness is eradicated or not
         * @param s The specified sickness
         * @return Boolean
         */
        public Boolean isEradicated(Sickness s) {
        	if (this.infectionTime) {
        		return false;
        	} 
        	if (this.cubes.get(s) == 0)
        		System.out.println("The sickness "+s+" is eradicated.");
        	return this.cubes.get(s) == 0;
        	
        }
    
        
        /**
         * return true if the cure is safe, otherwise false.
         * @param s the sickness InfectionCard c = new InfectionCard();
        	return c;
        	//temporary, method to do
         * @return Boolean
         */
        public Boolean isCured(Sickness s) {
        	return this.cures.get(s);
        }
        
        /**
         * remove a station in the station list 
         * @param value the index corresponding to the station
         */
        public void removeStation(City c) {
        	Iterator<City> iterator = this.StationList.iterator();
            while (iterator.hasNext()) {
                City city = iterator.next();
                if(c.equals(city)) {
                	iterator.remove();
                    this.stationinGame--;
                }
            }
        }
        
        /**
         * Create a station in the game
         * @param c the city to build as station
         */
        public void createStation(City c) {
        	if (this.stationinGame<Game.GLOBAL_STATION_LIMIT) {
        	
        	c.setStation(true);
        	this.StationList.add(c);
        	this.stationinGame++;
        	System.out.println("The city "+c.getName()+" is now a research station.");
        	}
        	else System.out.println("The number of stations has already reached the limit of "+Game.GLOBAL_STATION_LIMIT+".");
        }
        
        
        /**
         * Return the state of the game :
         * UNFINISHED = the game is not finished
         * WON = the game is won
         * LOST = the game is lost
         * @return the value corresponding of the state of the game
         */
        public Results getResult() {
        	
        	// ways to win
        	Boolean allSicknessesAreCured = true;
        	
        	// ways to loose
        	Boolean playerDeckIsEmpty = this.playerDeck.isEmpty();
        	Boolean outbreakRateTooHigh = this.outbreakRate>=8;
        	Boolean allCubesAreUsed = false;
        	
        	for (Boolean b : this.cures.values()) {
        		allSicknessesAreCured = allSicknessesAreCured && b;
        	}
        	if (allSicknessesAreCured) {
        		System.out.println("All sicknesses ar cured !");
        		return Results.WON ;
        	}
        	
        	for (int i : this.cubes.values()) {
        		allCubesAreUsed = allCubesAreUsed || i>=Game.GLOBAL_CUBE_LIMIT;
        	}
        	
        	if (allCubesAreUsed) {
        		System.out.println("All cubes of a sickness are used !");
        	}
        	
        	if (outbreakRateTooHigh) {
        		System.out.println("Outbreak rate has reach 8 or more !");
        	}
        	
        	if (playerDeckIsEmpty) {
        		System.out.println("There is no more card in the player deck !");
        	}
        	
        	if (allCubesAreUsed || outbreakRateTooHigh || playerDeckIsEmpty)
        		return Results.LOST;

        	return Results.UNFINISHED;
        }
        
        /**
         * Draw the first card of the infection stack
         * 
         * @return the first card of the infection stack
         */
        public InfectionCard drawInfectionCard() {
        	InfectionCard IC;
        	try {
        		IC = this.infectionDeck.drawCard() ;
        	}
        	catch (PlayerDeckIsEmptyException e) {
        		System.out.println("The infection card stack is empty.");
        		return null;
        	}
        	System.out.println("The infection card of the city "+IC.getCity().getName()+" has been drawn.");
        	Game.sleep(100);
        	return IC ;
        }
        
        /**
         * Draw the first card of the player stack
         * 
         * @return the first card of the player stack
         */
        public PlayerCard drawPlayerCard() {
        	PlayerCard PC;
        	try {
        		PC = this.playerDeck.drawCard() ;
        	}
        	catch (PlayerDeckIsEmptyException e) {
        		System.out.println("The player card stack is empty.");
        		return null;
        	}
        	System.out.println("A player card has been drawn.");
        	Game.sleep(100);
        	return PC ;
        }
        
        /**
         * Set up the next player as active player
         */
        public void nextPlayer(){
            if(this.index > this.players.size() -1 ){
                this.index = 0;
                this.playerActif = this.players.get(index);
                this.index += 1;
            }
            else{
                this.playerActif = this.players.get(index);
                this.index += 1;
            }
            System.out.println("This is "+this.playerActif.getName()+"'s turn to play.");
            Game.displayLine();
            Game.sleep(100);
            
        }
        
        /**
         * Set all cities not infected for the next turn
         */
        public void nextTurn() {
        	for (City c : this.getMap().getCities()) {
        		c.setInfectedFalse();
        	}
        }
        
        /**
         * Pauses the program for the specified time in milliseconds
         * @param n the number of milliseconds to wait
         */
        public static void sleep(int n) {
        	try {
        		Thread.sleep(n);
        	}
        	catch (Exception e) {}
        }
        
        
        /**
         * -------------------------------------------------------------
         * ----------------------- Init Methods ------------------------
         * -------------------------------------------------------------      
         */
        
        /**
         * Initialize the map for the game
         */
        public void initMap() {
        	int nbOfCities = 0;
        	
        	System.out.println("How much cities do you want to play with ?");
        	System.out.println("Only values accepted are: 12 and 48.");
        	try {
        		nbOfCities = Input.readInt();
        	}
        	catch (java.io.IOException e) {
        		System.out.println("Problem with Input Class, try restarting");
        		System.exit(-1);;
        	}
        	
        	try {
        		this.map.initMap(nbOfCities+"Cities.json");
        		for (int i=0;i<this.map.getCities().size();i++) {
        			String name = this.map.getCities().get(i).toString();
        			this.map.getCities().get(i).addGame(this);
        			System.out.println("The city "+name+" has been initialized.");
        			Game.sleep(100);
        		}
        	} catch (FileNotFoundException e) {
        		System.out.println("Invalid number of Cities, try restarting.");
        		System.exit(-1);
        	}
        	Game.displayLine();
        }
        
        /**
         * Initialize roles available in the game
         */
        public void initRoles() {
        	this.roles = new ArrayList<>();
        	this.roles.add(new Expert(null, null, this));
        	this.roles.add(new GlobeTrotter(null, null, this));
        	this.roles.add(new Medic(null, null, this));
        	this.roles.add(new Scientific(null, null, this));
        }
        
        /** 
         * asks the number of players at to create and add players in the Starter cell.
         */
        public void initPlayer(){         
            try{
            	int nbPlayer;
                int randomIndexForCityOfStart = Game.getRandomNumber(this.map.getCities().size()-1);
                City cityOfStart = this.map.getCities().get(randomIndexForCityOfStart);

                
                System.out.println("Give the number of players : ");
                System.out.println("Only values accepted are between 2 and 4 (included)");
                nbPlayer = Input.readInt();
                
                if (nbPlayer < 2 || nbPlayer > 4){
                    System.out.println("Invalid number of players.");
                    System.exit(-1);
                }
                Game.displayLine();
                for (int i=0;i<nbPlayer;i++) {
                	Player e = this.initOnePlayer(nbPlayer, cityOfStart, i);
                	this.addPlayer(e);
                	Game.displayLine();
                	Game.sleep(100);
                }      
                System.out.println("The players start on the city "+cityOfStart.getName()+".");
                this.createStation(cityOfStart);
                Game.displayLine();;
                Game.sleep(100);
                
                
            }
            catch (java.io.IOException e) 
            {
             System.out.println("Problem with IO classes, try restarting.");
             System.exit(-1);
            }
        }

		protected Player initOnePlayer(int nbPlayer, City cityOfStart, int i) {
			Game.sleep(500);
			System.out.println("Give the name of the player "+(i+1)+" : ");
			String name = Input.readString();
			
			/* creating of the player and his role.*/
			ListChooser<Player> lc = new RandomListChooser<>();
			Player p = lc.choose("test", this.roles);
			if (p == null) {
				p = lc.choose("test", this.roles);
			}
			this.roles.remove(this.roles.indexOf(p));
			p.setName(name);
			p.setCity(cityOfStart);
			System.out.println(p.getName());
			
			return p;
		}
        
        /**
         * creates an infection and player deck and initializes the players hands
         */
        public void initDeck() {

        	List<CityCard> PC = initCityCards();
        	initPlayerHands(PC);
        	initPlayerDeck(PC);  	
        	initInfectionDeck();
        }

        /**
         * initializes the infection deck for the game
         */
		protected void initInfectionDeck() {
			List<InfectionCard> IC = new ArrayList<InfectionCard>();
        	
        	for (int i=0;i<this.map.getCities().size();i++) {
        		InfectionCard c = new InfectionCard(this, this.map.getCities().get(i));
        		IC.add(c);
        	}        	
        	
        	this.infectionDeck = new Deck<InfectionCard>(this);
        	this.infectionDeck.setStack(IC);
        	this.infectionDeck.shuffleStack();
		}
		
		/**
		 * initializes the player deck for the game
		 * @param PC the list of city cards
		 */
		protected void initPlayerDeck(List<CityCard> PC) {
			int n = PC.size()/4;
        	List<PlayerCard> t1 = new ArrayList<PlayerCard>();
        	List<PlayerCard> t2 = new ArrayList<PlayerCard>();
        	List<PlayerCard> t3 = new ArrayList<PlayerCard>();
        	List<PlayerCard> t4 = new ArrayList<PlayerCard>();
        	for (int i=0;i<PC.size();i++) {
        		CityCard c = PC.get(i);
        		if (i<n) t1.add(c);
        		else if(i>=n && i<2*n) t2.add(c);
        		else if(i>=2*n && i<3*n) t3.add(c);
        		else t4.add(c);
        	}
        	
        	// adding the PandemicCards
        	for (int i=0;i<4;i++) {
        		PandemicCard c = new PandemicCard(this);
        		if (i==0) t1.add(c);
        		else if (i==1) t2.add(c);
        		else if (i==2) t3.add(c);
        		else t4.add(c);
        	}
        	
        	List<PlayerCard> deck = new ArrayList<PlayerCard>();
        	while (!t1.isEmpty()) {
        		deck.add(t1.get(0));
        		t1.remove(0);
        	}
        	while (!t2.isEmpty()) {
        		deck.add(t2.get(0));
        		t2.remove(0);
        	}
        	while (!t3.isEmpty()) {
        		deck.add(t3.get(0));
        		t3.remove(0);
        	}
        	while (!t4.isEmpty()) {
        		deck.add(t4.get(0));
        		t4.remove(0);
        	}
        	
        	this.playerDeck = new Deck<PlayerCard>(this);
        	this.playerDeck.setStack(deck);
		}

		/**
		 * initializes the player hands for the game
		 * @param PC the list of city cards
		 */
		protected void initPlayerHands(List<CityCard> PC) {
			for (int i=0;i<this.players.size();i++) {
        		
        		Player p = this.players.get(i);
        		for (int j=0;j<6-this.players.size();j++) {
        			CityCard c = PC.get(Game.getRandomNumber(PC.size()-1));
        			PC.remove(c);
        			p.addCard(c);
        			Game.sleep(100);
        		}
        	}
		}
		
		/**
		 * initializes the city cards for the game
		 * @return List
		 */
		protected List<CityCard> initCityCards() {
			List<CityCard> PC = new ArrayList<CityCard>();
        	
        	for (int i=0;i<this.map.getCities().size();i++) {
        		CityCard c = new CityCard(this, this.map.getCities().get(i));
        		PC.add(c);      		
        	}
			return PC;
		}
  
        
        /**
         * Initialize the infection for the game
         */
        public void initInfection() {
        	System.out.println("Starting initial infection.");
        	for (int i=0;i<9;i++) {
        		InfectionCard c = this.drawInfectionCard();
        		try {
        		if (i<3) {
        			c.action(); nextTurn();
        			c.action();	nextTurn();
        			c.action(); nextTurn();
        		}
        		else if (i>=3 && i<6) {
        			c.action(); nextTurn();
        			c.action(); nextTurn();
        		}
        		else {
        			c.action(); nextTurn();
        		}
        		} catch (Exception e) {}
        		this.infectionDeck.discardCard(c);
        		Game.displayLine();
        	}
        	this.setInfectionTimeFalse();
        }
        
        
        /**
         * Starts a Pandemic game
         */
        public void play() {
        	this.initMap();
        	this.initPlayer();
        	this.initDeck();
        	this.initInfection();
        	try {
	        	while (this.getResult() == Results.UNFINISHED) {
	        		this.nextTurn();
	        		this.nextPlayer();	
	        		for (int i=0; i<4; i++) {
	        			this.playerActif.performAction();
	        		}
	        		for (int i=0; i<2; i++) this.drawPlayerCard().action();
	        		
	        		for (int i=0; i<this.globalInfectionRate[this.infectionRateIndex]; i++) this.drawInfectionCard().action();
	        			
	        	}
        	} catch (Exception e) {
        	Boolean result = this.getResult() == Results.WON ;
        	String mess = result?"The game is finished, you won." : "The game is finished, you lost.";	
        	System.out.println(mess);	 
        	Game.displayLine();
        	}
        }
        
        
        
        
        
        
        






}