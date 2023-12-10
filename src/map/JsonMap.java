package map;

import java.io.FileNotFoundException;

/*
 * a class to represent the Map created with a json file
 */
public class JsonMap extends WorldMap{

	@Override
	
	public void initMap(String filename) throws FileNotFoundException  {
		this.JsonCity(filename);
		this.JsonNeighbors(filename);
	}
	
	
	
}
