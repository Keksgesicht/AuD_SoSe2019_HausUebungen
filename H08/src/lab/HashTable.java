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
		capacity = initialCapacity;
		if(!isPrime(capacity))
			capacity = primCapacity(capacity);
		entryLists = new LinkedList[capacity];
		
		int p = entryLists.length;
		hash_a = (int) (Math.random() * (p - 1));
		hash_b = (int) (Math.random() * (p - 1));
		while(hash_a == 0) {
			hash_a = (int) (Math.random() * (p - 1));
		}
	}
	
	/**
	 * @param cap possible prime number
	 * @return the next prime number
	 */
	private int primCapacity(int cap) {
		if(cap % 2 == 0)
			cap++;
		while(!isPrime(cap))
			cap += 2;
		return cap;
	}
	
	/**
	 * @param prim possible prime number
	 * @return whether the given number is prime or not
	 */
	private boolean isPrime(int prim) {
		int sqrt = (int) Math.sqrt(prim);
		for(int i=2; i < sqrt; i++) {
			if(prim % i == 0)
				return false;
		}
		return true;
	}
	
	/**
	 * @param key unhashed key
	 * @return the LinkedList to the given key
	 */
	private LinkedList getKeyList(String key) {
		int hash_key = hash(key);
		if(entryLists[hash_key] == null)
			entryLists[hash_key] = new LinkedList();
		return entryLists[hash_key];
	}
	
	/**
	 * Search for an TableEntry with the given key. If no such TableEntry is found, return null.
	 */
	public TableEntry find(String key) {
		LinkedList keyList = getKeyList(key);
		ListNode node = find(keyList, key);
		return node == null ? null : node.entry();
	}
	
	/**
	 * @param keyList the LinkedList in which the key could be found
	 * @param key unhashed key
	 * @return the ListNode with the given key or null
	 */
	private ListNode find(LinkedList keyList, String key) {
		ListNode headOfKeyList = keyList.head();
		while(headOfKeyList != keyList.nil()) {
			if(headOfKeyList.entry().getKey().equals(key))
				break;
			headOfKeyList = headOfKeyList.next();
		}
		return headOfKeyList == keyList.nil() ? null : headOfKeyList;
	}
	
	/**
	 * Insert the given entry in the hash table. If there exists already an entry with this key,
	 * this entry should be overwritten. After inserting a new element, it might be necessary
	 * to increase the capacity of the hash table.
	 */
	public void insert(TableEntry entry) {
		String key = entry.getKey();
		LinkedList keyList = getKeyList(key);
		ListNode node = find(keyList, key);
		
		if(node == null) {
			keyList.append(entry);
		} else {
			keyList.insertBefore(entry, node);
			keyList.delete(node);
		}
		resize();
	}
	
	private void resize() {
		
	}
	
	/**
	 * Delete the TableEntry with the given key from the hash table and return the entry.
	 * Return null if key was not found.  
	 */
	public TableEntry delete(String key) {
		LinkedList keyList = getKeyList(key);
		ListNode node = find(keyList, key);
		if(node == null)
			return null;
		keyList.delete(node);
		return node.entry();
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
		int size = 0;
		for(LinkedList ll : entryLists) {
			if(ll != null)
				size += ll.length();
		}
		return size;
	}
	
	/**
	 * Increase the capacity of the hash table and reorder all entries.
	 */
	private void rehash() {
		HashTable h = new HashTable(capacity*10);
		ListNode node;
		for (LinkedList l : entryLists) {
			if (l != null) {
				node = l.head();
				for (int i = 0; i < l.length();i++)  {
					h.insert(node.entry());
					node = node.next();
				}
			}
		}
	}
	
	/**
	 * Return the current "quality" of the hash table. The quality is measured by calculating
	 * the maximum number of collisions between entries in the table (i.e., the longest linked
	 * list in the hash table).
	 */
	public int quality() {
		int longestSize = 0;
		for(LinkedList ll : entryLists) {
			if(ll == null)
				continue;
			int listLength = ll.length();
			if(listLength > longestSize)
				longestSize = listLength;
		}
		return longestSize;
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
