package lab;

/**
 * Aufgabe H1 b) und c)
 * 
 * @author Emre Berber 2957148
 * @author Christoph Berst 2743394
 * @author Jan Braun 2768531
 */

import java.util.Random;

import frame.ListNode;
import frame.TableEntry;

public class HashTable {
	
	private int capacity;
	private int hash_a;
	private int hash_b;
	private LinkedList[] entryLists;
	
	// add your own variables if you want
	
	 /**
     * The constructor
     * 
     * @param initialCapacity
     *            represents the initial size of the Hash Table.
     * 
     * The Hash-Table itself should be implemented in the array entryLists. When the
     * load factor exceeds 75%, the capacity of the Hash-Table should be
     * increased as described in the method rehash below.
     */
	public HashTable(int initialCapacity) {
		entryLists = new LinkedList[initialCapacity];
		
		int p = entryLists.length;
		hash_a = (int) (Math.random() * (p - 1));
		hash_b = (int) (Math.random() * (p - 1));
		while(hash_a == 0) {
			hash_a = (int) (Math.random() * (p - 1));
		}
	}
	
	/**
	 * Search for an TableEntry with the given key. If no such TableEntry is found, return null.
	 */
	public TableEntry find(String key) {
		// TODO
		return null;
	}
	
	/**
	 * Insert the given entry in the hash table. If there exists already an entry with this key,
	 * this entry should be overwritten. After inserting a new element, it might be necessary
	 * to increase the capacity of the hash table.
	 */
	public void insert(TableEntry entry) {
		// TODO
	}
	
	/**
	 * Delete the TableEntry with the given key from the hash table and return the entry.
	 * Return null if key was not found.  
	 */
	public TableEntry delete(String key) {
		// TODO
		return null;
	}
	
	/**
	 * The hash function used in the hash table.
	 */
	public int hash(String s) {
		int x = 0;
		for (int i=0; i < s.length(); i++) {	
			x += (i + 1) * ((int) s.charAt(i));
		}
		// Vorlesung Hash
		int p = entryLists.length;
		int hash_x = (int) ((hash_a * x + hash_b % p) % p);
		return hash_x;
	}
	
	/**
	 * Return the number of TableEntries in this hash table.
	 */
	public int size() {
		// TODO
		return -1;
	}
	
	/**
	 * Increase the capacity of the hash table and reorder all entries.
	 */
	private void rehash() {
		// TODO
	}
	
	/**
	 * Return the current "quality" of the hash table. The quality is measured by calculating
	 * the maximum number of collisions between entries in the table (i.e., the longest linked
	 * list in the hash table).
	 */
	public int quality() {
		// TODO
		return -1;
	}
	
	public int getHash_a() {
		return hash_a;
	}
	
	public int getHash_b() {
		return hash_b;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public LinkedList[] getEntryLists() {
		return entryLists;
	}

}
