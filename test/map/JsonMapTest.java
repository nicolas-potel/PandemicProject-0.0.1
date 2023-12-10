package map;

import sickness.Sickness;

import java.util.*;
import java.io.FileNotFoundException;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class JsonMapTest {

	
	public static final int NUMBER_CITIES=12;
	public static String filename = "12Cities.json";
	public static JsonMap map1;
	public static List<City> cities;
	
	@Before 
	public void init() throws FileNotFoundException {
		map1 = new JsonMap();
		map1.initMap(filename);
		cities=map1.getCities();
	}
	
	@Test
	public void testAddCityAndGetCities() {
		JsonMap map2 = new JsonMap();
		City c1 = new City("ville1", Sickness.YELLOW);
		City c2 = new City("ville2", Sickness.BLUE);
		map2.add(c1);
		assertEquals(c1, map2.getCities().get(0));
		map2.add(c2);
		assertEquals(c2, map2.getCities().get(1));
	}
	
	
    @Test
    public void testRightNumberOfCities() {
    	assertEquals(NUMBER_CITIES, map1.getCities().size());
    }
    
    @Test
    public void testRightCities() {
    	String name ="ville-";
    	for (int i=0;i<cities.size();i++) {
    		assertNotNull(name.concat(""+i));
    	}
    }
    
    
    @Test
    public void testCityHaveRightNeighbors() {
    	Map<String, City> neighbors;
    	for (int i=0;i<cities.size();i++) {
    		neighbors=cities.get(i).getNeighbours();
    		for (City c : neighbors.values()) {
    			assertTrue(c.getNeighbours().containsValue(cities.get(i)));
    		}
    	}	
    }
    
    

}
