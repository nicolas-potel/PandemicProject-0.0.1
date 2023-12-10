package sickness;

import java.util.HashMap;
import java.util.Map;

public enum Sickness {
	YELLOW,RED,BLUE,BLACK;
	
	public static Sickness getSickness(String name) {
		if (name == "BLACK") {
			return BLACK;
		}
		if (name == "BLUE") {
			return BLUE;
		}
		if (name == "RED") {
			return RED;
		}
		if (name == "YELLOW") {
			return YELLOW;
		}
		return null;
	}
	
	public static String getString(Sickness s) {
		if (s==Sickness.YELLOW) {
			return "YELLOW";
		}
		else if(s==Sickness.RED) {
			return "RED";
		}
		else if (s==Sickness.BLUE) {
			return "BLUE";
		}
		else return "BLACK";
	}
	
	public static Map<Integer, Sickness> valuesAsHashMap() {
		HashMap<Integer, Sickness> sickMap = new HashMap<>();
		int i = 0;
		for (Sickness s : Sickness.values()) {
			sickMap.put(i++, s);
		}
 		return sickMap;
		
	}
	
}
