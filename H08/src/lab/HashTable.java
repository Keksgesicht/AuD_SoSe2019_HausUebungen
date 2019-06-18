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
	private int size = 0;
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
		setCapacity(initialCapacity);
		entryLists = new LinkedList[capacity];
		setHash();
	}
	
	/**
	 * sets the capacity to the nex possible prime number 
	 */
	private void setCapacity(int initialCapacity) {
		if(initialCapacity < 0)
			throw new IllegalArgumentException("capacity must be positive!");
		capacity = initialCapacity;
		if(!isPrime(capacity))					// Wird der Konstruktor nicht mit einer Primzahl aufgerufen,
			capacity = getNexPrime(capacity);	// soll die nächstgrößere Primzahl gewählt werden.
	}
	
	/**
	 * sets random values for hash_a and hash_b
	 */
	private void setHash() {
		Random rnd = new Random(System.currentTimeMillis());
		int p = entryLists.length;		// p = T.length
		hash_a = rnd.nextInt(p);	// a aus [0, p-1]
		hash_b = rnd.nextInt(p);	// b aus [0, p-1]
		while(hash_a == 0) {
			hash_a = rnd.nextInt(p);	// a != 0
		}
	}
	
	/**
	 * @param prim possible prime number
	 * @return the next prime number
	 */
	private int getNexPrime(int prim) {
		if(prim % 2 == 0)		// Falls gerade
			prim++;				// ungerade machen
		while(!isPrime(prim))	// solange es keine primezahl ist
			prim += 2;			// alle ungeraden zahlen durchspringen
		return prim;
	}
	
	/**
	 * @param prim possible prime number
	 * @return whether the given number is prime or not
	 */
	private boolean isPrime(int prim) {
		if(prim == 1)		// 1 ist keine Primzahl
			return false;
		if(prim == 2)		// 2 ist eine Primzahl
			return true;
		if(prim % 2 == 0)	// gerades außer 2 ist keine Primezahl
			return false;
		int sqrt = (int) Math.sqrt(prim);	// Es reicht die Teiler bis zu Wurzel durchzuprobieren
		for(int i=3; i < sqrt; i += 2) {
			if(prim % i == 0)	// sobald es durch etwas teilbar ungeleich 1 oder sich selbst teilbar ist,
				return false;	// kann es keine Primzahl sein
		}
		return true;	// Keinen Teiler gefunden
	}
	
	/**
	 * @param key unhashed key
	 * @return the LinkedList to the given key
	 */
	private LinkedList getKeyList(String key) {
		int hash_key = hash(key);						// key hashen 
		if(entryLists[hash_key] == null)				// falls nicht existent,
			entryLists[hash_key] = new LinkedList();	// Liste erstellen
		return entryLists[hash_key];
	}
	
	/**
	 * Search for an TableEntry with the given key. If no such TableEntry is found, return null.
	 */
	public TableEntry find(String key) {
		LinkedList keyList = getKeyList(key);		// key hashen und Liste finden
		ListNode node = find(keyList, key);			// Knoten in der Liste finden
		return node == null ? null : node.entry();	// falls vorhanden, wert zurückgeben
	}
	
	/**
	 * @param keyList the LinkedList in which the key could be found
	 * @param key unhashed key
	 * @return the ListNode with the given key or null
	 */
	private ListNode find(LinkedList keyList, String key) {
		ListNode nodeOfKeyList = keyList.head();			// mit Kopf der Liste beginnen
		while(nodeOfKeyList != keyList.nil()) {				// nur realle Werte der Liste durchlaufen	
			if(nodeOfKeyList.entry().getKey().equals(key))	// Knoten mit key gefunden?
				break;
			nodeOfKeyList = nodeOfKeyList.next();			// nächster Knoten!
		}
		return nodeOfKeyList == keyList.nil() ? null : nodeOfKeyList; // falls verhanden, Knoten zurückgeben
	}
	
	/**
	 * Insert the given entry in the hash table. If there exists already an entry with this key,
	 * this entry should be overwritten. After inserting a new element, it might be necessary
	 * to increase the capacity of the hash table.
	 */
	public void insert(TableEntry entry) {
		String key = entry.getKey();			// key extrahieren
		LinkedList keyList = getKeyList(key);	// key hashen und Liste finden
		ListNode node = find(keyList, key);		// Knoten in der Liste finden
		
		if(node == null) {			// noch nicht vorhanden,
			keyList.append(entry);	// dann hinten dran hängen
			size++;                 // nach dem Einfügen eines Elements wird size erhöht
		} else {
			keyList.insertBefore(entry, node);	// soll dieser existierende	Eintrag
			keyList.delete(node);	            // durch den neuen Eintrag ersetzt werden
		}
		resize();	// größe gegebenfalls anpassen
	}
	
	private void resize() {
		if(size() < getCapacity() * 0.75) // Größe der Hashtabelle sollte kleiner als 75% der Kapazität sein
			return;
		rehash();	// Wird dieser Wert überschritten, soll rehash aufgerufen werden
	}
	
	/**
	 * Delete the TableEntry with the given key from the hash table and return the entry.
	 * Return null if key was not found.  
	 */
	public TableEntry delete(String key) {
		LinkedList keyList = getKeyList(key);	// key hashen und Liste finden
		ListNode node = find(keyList, key);		// Knoten in der Liste finden
		if(node == null)		// falls nicht existent,
			return null;		// dann gibt es auch nichts zu tun
		size--;                 // nach dem Löschen eines Elements wird size verringert
		keyList.delete(node);	// Knoten aus der Liste entfernen
		return node.entry();	// Wert des Knotens zurückgeben
	}
	
	/**
	 * The hash function used in the hash table.
	 */
	public int hash(String s) {
		int x = 0;
		for (int i=0; i < s.length(); i++) {
			x += (i + 1) * ((int) s.charAt(i));	// Summe der Produkte der Zeichen(ASCI) mit ihrer Position
		}
		// Hash-Funktion aus der Vorlesung 
		int p = entryLists.length;	// p = T.length
		int hash_x = (hash_a * x + hash_b % p) % p; // H(x) = (a * x + b mod p) mod T.length
		return hash_x;
	}
	
	/**
	 * Return the number of TableEntries in this hash table.
	 */
	public int size() {
		return size;      
	}
	
	/**
	 * Increase the capacity of the hash table and reorder all entries.
	 */
	private void rehash() {
	    this.setCapacity(capacity*10);                // die Capacity wird auf sein Zehnfaches gesetzt 
	    this.setHash();							      // hash_a und hash_b werden wieder festgelegt								  
		LinkedList[] oldEntryLists = this.entryLists; // die alte entryLists wird zwischengespeichert
		entryLists = new LinkedList[this.capacity];   // eine neue entyLists wird mit der neuen Capacity erstellt 
		size = 0;                                     // die neue entryLists ist leer, also size = 0
 		for (LinkedList l : oldEntryLists) {          
			if (l != null) {				          // überprüft ob die LinkedList leer ist	  
				ListNode node = l.head();                      
				while (node != l.nil()) {
					this.insert(node.entry());        // entrys werden der neuen entryList hinzugef�gt
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
			// die Länge der längsten LinkedList
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
