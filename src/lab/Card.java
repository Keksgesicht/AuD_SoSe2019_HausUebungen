package lab;

/**
 * Aufgabe H1b)
 * 
 * Abgabe von: <name>, <name> und <name>
 */

public class Card {
	
	// DO NOT MODIFY
	public enum Suit {
		Hearts, Diamonds, Clubs, Spades
	}
	
	// DO NOT MODIFY
	public int value;
	public Suit suit;
	
	// DO NOT MODIFY
	public Card() {
	}
	
	// DO NOT MODIFY
	public Card(int value, Suit suit) {
		this.value = value;
		this.suit = suit;
	}
	
	// DO NOT MODIFY
	public Card(Card other) {
		this.value = other.value;
		this.suit = other.suit;
	}
	
	public String toString() {
		return value+";"+suit;
	}
	
	/**
	 * Compare two card objects. Return -1 if this is deemed smaller than the object other, 0 if they are
	 * deemed of identical value, and 1 if this is deemed greater than the object other.
	 * @param other The object we compare this to.
	 * @return -1, 0 or 1
	 */
	public int compareTo(Card other) {
		// TODO: implement
	}
	
}
