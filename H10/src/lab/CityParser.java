package lab;

/**
 * Aufgabe H1a)
 * 
 * Abgabe von: <name>, <name> und <name>
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
        // TODO
		return new LinkedList<City>();
	}

}
