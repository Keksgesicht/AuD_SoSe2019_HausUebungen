package lab;

/**
 * Aufgabe H1a)
 * 
 * @author Emre Berber 2957148
 * @author Christoph Berst 2743394
 * @author Jan Braun 2768531
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import frame.City;

public class CityParser {
	
	private String _filename;
	
	public CityParser(String filename) {
		_filename = filename;
	}
	
	/**
	 * Read cities from the given file and enter them into a LinkedList.
	 * Each city is in its own line, written as "x;y". 
	 * Throw a RuntimeException if anything goes wrong.
	 * Return the LinkedList of the cities in the same order as they were in the file.
	 */
	public LinkedList<City> readFile() {
		LinkedList<City> cities = new LinkedList<City>();
		try(BufferedReader br = new BufferedReader(new FileReader(_filename))) {
			String line;
		    for(int i=0; (line = br.readLine()) != null; i++) {
		    	String[] coordinates = line.split(";", 2);
		    	cities.add(new City(i, Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1])));
		    }
		} catch(FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe.getMessage());
		} catch (IOException ioe) {
			throw new RuntimeException();
		}
		return cities;
	}

}
