package map;

import player.Player;
import java.util.*;
import java.util.Map.Entry;

import exception.NeighbourException;
import exception.OutBreakRateTooHighException;
import exception.TooMuchCubesException;
import game.Game;
import sickness.Sickness;

/*
 * a class which represents the City object
 */
public class City {
	
	/* The number of cubes for each Sickness */
	private Map<Sickness,Integer> cubes;
	
	/* True if this City contains a station, False else */
	private boolean station;
	
	/* The sector of this City defined by the sickness */
	private Sickness sector;
	
	/* The neighbours of this City */
	private Map<String, City> neighbours;
	
	/* The players that are actually in this City */
	private List<Player> players;
	
	/* The name of this City */
	private String name;

	/* The infected status of the City */
	private boolean infected;

	private static int PROPAGATION_CUBES_LIMIT = 3;
	
	private Game game;
	
	/**
	 * the constructor of a City
	 * 
	 * @param name the name of the City
	 * @param s the sickness of the City
	 */
	public City(String name,Sickness s) {
		this.sector = s;
		this.name = name;
		this.cubes = new HashMap<Sickness, Integer>();
		Sickness[] a = Sickness.values();
		for (Sickness i : a) {
			this.cubes.put(i,0);
		}
		
		this.neighbours = new HashMap<String, City>();
		this.players = new ArrayList<Player>();
	}
	
	/**
	 * Getting if this City have Station or not
	 * 
	 * @return boolean
	 */
	public boolean getStation() {
		return this.station;
	}
	
	/**
	 * Getting the sector of the City
	 * 
	 * @return the sickness of the sector
	 */
	public Sickness getSector() {
		return this.sector;
	}
	
	/**
	 * Getting the number of cubes for the selected Sickness
	 * 
	 * @param s Sickness
	 * @return int the number of cubes 
	 */
	public int getCubes(Sickness s) {
		return this.cubes.get(s);
	}
	
	/**
	 * Gets each Sickness with their present cube's numbers 
	 * @return Map the cubes
	 */
	public Map<Sickness, Integer> getMapCubes(){
		return this.cubes;
		
	}
	
	/**
	 * get the list of present sickness in the city
	 * @return List representing the sickness list
	 */
	public List<Sickness> getSicknessList(){
		List<Sickness> res = new ArrayList<Sickness>();
		for (Sickness i : this.cubes.keySet()) {
			if (this.cubes.get(i)!=0) {
				res.add(i);
			}
		}
		return res;
	}
	
	/**
	 * Getting all the neighbours of the city
	 * @return Map representing the neighbours
	 */
	public Map<String, City> getNeighbours(){
		return this.neighbours;
	}
	
	/**
	 * Getting the players on the city
	 * 
	 * @return List the players
	 */
	public List<Player> getPlayers(){
		return this.players;
	}
	
	/**
	 * Getting the name of the city
	 * 
	 * @return String the name 
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return the name of the City and his state if she is infeccted
	 */
	public String toString() {
		String res = this.name;
		if (!this.getSicknessList().isEmpty()) {
			for(int i=0;i<this.getSicknessList().size();i++) {
				Sickness s = this.getSicknessList().get(i);
				res += " infected by " + s+"("+this.cubes.get(s)+")";
			}
		}
		if (this.getPlayers().size()>0) {
			res += " with player(s) : ";
			for(Player p : this.getPlayers()) {
				res += "("+p.getName() + ") ";
			}
		}
		return res;
	}
	
	/**
	 * Adding neighbour to city
	 * 
	 * @param c City to add
	 */
	public void addNeighbour(City c) {
		this.neighbours.put(c.toString(), c);
	}

	/**
	 * Adding cubes for a given sickness
	 * 
	 * @param s  The sickness we wants to add cubes
	 * @param n  The number of cubes to add
	 */
	public void addCubes(Sickness s, int n) throws TooMuchCubesException, OutBreakRateTooHighException {
		if (!(this.game.isEradicated(s))) { 
			if (!(this.infected)) {
				int nbCubes = this.cubes.get(s);
				this.infected=true;
				if (nbCubes + n <= City.PROPAGATION_CUBES_LIMIT) {
					this.cubes.put(s, nbCubes + n);
					this.game.increaseNbOfCubes(s, n);
					if (this.game.getNbOfCubes(s) == this.game.getCubeLimit()) {
						throw new TooMuchCubesException("The number of cubes of sickness : " + s + " has reached the cube limit.");
					}
					System.out.println(n+" "+Sickness.getString(s)+" cube(s) added to the city "+this.name+".");
				} 
				else {
					System.out.println("The city "+this.name+" suffers from propagation of the sickness "+s.name()+".");
					this.cubes.put(s, City.PROPAGATION_CUBES_LIMIT);
					this.game.increaseNbOfCubes(s, City.PROPAGATION_CUBES_LIMIT);
					this.game.increaseOutbreakRate();
					if (this.game.getOutbreakRate() == 8) {
						throw new OutBreakRateTooHighException("The number of outbreak rate as reach 8.");
					}
					for (City c : this.neighbours.values()) {
							c.addCubes(s, 1);
					}
					System.out.println("The city "+this.name+" is now infected.");
				}
				this.displayPresentSickness();
			}
			else {
				System.out.println("The city "+this.name+" has already been infected this turn.");
			}	
		} else {
			System.out.println("The sickness "+Sickness.getString(s)+" is eradicated, a cube cannot be added.");
		}
		Game.sleep(100);
	}
	
	/*
	 * Remove cubes for a given sickness
	 * 
	 * @param s The sickness we wants to remove cubes
	 * @param n The number of cubes to remove 
	 */
	public void removeCubes(Sickness s, int n) {
		this.cubes.put(s, cubes.get(s)-n);
		this.game.decreaseNbOfCubes(s, n);
	}
	
	/**
	 * define if a station is present in the city 
	 * @param b the boolean corresponding to the new status of the city (as station)
	 */
	public void setStation(Boolean b) {
		this.station = b;
	}
	
	
	/**
	 * Show if the city is a neighbour of this city or not.
	 * 
	 * @param c a city 
	 * @return True if c is a neighbour of this city or else false
	 */
	public boolean isNeighbour(City c) {
		return this.neighbours.containsValue(c);
	}
	
	public City getOneNeighbour(String n) throws NeighbourException {
		Map<String, City> neighbours = this.neighbours;
		
		if (!neighbours.containsKey(n)) {
			throw new NeighbourException("Le voisin n'existe pas");
		}
		else {
			return neighbours.get(n);
		}
		
	}
	
	/**
	 * Add the player in param in the list of player of this city
	 * 
	 * @param p The player who you want add
	 */
	public void addPlayer(Player p) {
		this.players.add(p);
	}
	
	/**
	 * Remove the player in param in the list of player of this city
	 * 
	 * @param p The player who you want remove
	 */
	public void removePlayer(Player p) {
		this.players.remove(p);
	}
	
	
	/**
	 * show if the player is in this city
	 * 
	 * @param p a player
	 * @return True if the player is in the city or else false
	 */
	public boolean hasPlayer(Player p) {
		return this.players.contains(p);
	}
	
	/**
	 * Sets the infected status of the city as false for this turn (1 turn for each player)
	 */
	public void setInfectedFalse() {
		this.infected=false;
	}
	
	/**
	 * Sets the game attribute of the city to the specified parameter
	 * @param g the game to be added
	 */
	public void addGame(Game g) {
		if (this.game==null) this.game = g;
	}
	

	/**
	 * Display sickness present in the city
	 * 
	 */
	public void displayPresentSickness() {
		if (this.getSicknessList().isEmpty())
			System.out.println("The city "+this.name+" is not infected by any sickness.");
		else {
			System.out.println("The city "+ this.name + " is infected by : ");
			for(int i=0;i<this.getSicknessList().size();i++) {
				Sickness s = this.getSicknessList().get(i);
				System.out.print(s+"("+this.cubes.get(s)+") ");
			}
			System.out.println("");
		}
	}
	
	/**
	 * Display city's neighbours.
	 */
	public void displayNeighbours() {
		for (Entry<String, City> entry : this.neighbours.entrySet()) {
			System.out.println(entry.getKey());
		}
	}
	
}