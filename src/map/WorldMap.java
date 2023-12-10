package map;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;

import sickness.Sickness;


/*
 * a class to represent the map
 */
public abstract class WorldMap {
	
	
/** The cities of this map */
protected List<City> cities;

/** Initialization of the WorldMap */ 
public WorldMap() {
	this.cities = new ArrayList<>();
}


/** 
 * Get the cities of the WorldMap
 * 
 * @return the cities of the WorldMap
 */
public List<City> getCities() {
	return this.cities;
}

public void displayCities() {
	Iterator<City>  ite= this.cities.iterator();
	while (ite.hasNext()) {
		System.out.println(ite.next().toString());
	}
}

/**
 * Get the city by its name 
 * 
 * @param name the name of the city you want
 * @return City th city corresponding to the name or null if there is not
 */
public City getCity(String name) {
	for (City i : this.cities) {
		if (i.getName().equals(name)){
			return i;
		}
	}
	return null;
}

/**
 * Add a city in the WorldMap
 * 
 * @param city the city you want to add
 */
public void add(City city) {
	this.cities.add(city);
}


/**
 * Create a new WorldMap
 * @param filename the name of the json file that contains the cities
 * @throws FileNotFoundException if the file is missing
 */
public abstract void initMap(String filename) throws FileNotFoundException;


/**
 * Create all cities in the WorlMap from a file json
 * 
 * @param filename the filename you want to use
 * @throws FileNotFoundException if there is no file untilted filename
 */
public void JsonCity(String filename) throws FileNotFoundException {
	Map<Integer, Sickness> sickMap = Sickness.valuesAsHashMap();
	
	FileReader reader = new FileReader(filename);
    JSONObject book = new JSONObject(new JSONTokener(reader));
    JSONObject cityBook = book.getJSONObject("cities");
    
    Iterator<String> entries = cityBook.keys();
    while (entries.hasNext()) {
    	String entryKey = entries.next();
    	City city = new City(entryKey ,sickMap.get(cityBook.getInt(entryKey)));
    	this.add(city);
    }
	
}

/*
 * Create all neighbors in the WorldMap from a file json
 * 
 * @param filename the filename you want to use
 * @throws FileNotFoundException if there is no file untilted filename
 */
public void JsonNeighbors(String file) throws FileNotFoundException{
	FileReader reader = new FileReader(file);
    JSONObject book = new JSONObject(new JSONTokener(reader));
    JSONObject neighborsBook = book.getJSONObject("neighbors");
    
    Iterator<String> entries = neighborsBook.keys();
    
    while (entries.hasNext()){
    	String entryKey = entries.next();
    	JSONArray array = neighborsBook.getJSONArray(entryKey);
    	City city = this.getCity(entryKey);
    	
    	for (Object o : array) {
    		String i = (String) o;
    		City neighbor = this.getCity(i);
    		city.addNeighbour(neighbor);
    	}
    }
    
    
}

}

