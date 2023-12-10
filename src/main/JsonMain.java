package main;
import map.*;

import java.io.FileNotFoundException;


public class JsonMain {

	public static void main(String[] args) throws FileNotFoundException {
		WorldMap map = new JsonMap();
		if (args.length == 1) {
			if (args[0].equals("12Cities.json")) {
				map.initMap("12Cities.json");
			}
			else if (args[0].equals("48Cities.json")) {
				map.initMap("48Cities.json");
			}
			else {
				throw new FileNotFoundException("Fichier incorrect");
			}
			
		}
		else {
			throw new FileNotFoundException("Fichier non trouvé ou incorrect");
		}
		for (City c : map.getCities()) {
			System.out.println(c);
		}

	}

}
