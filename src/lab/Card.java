package lab;

/**
 * Aufgabe H1b)
 * 
 * @author Emre Berber 2957148
 * @author Christoph Berst 2743394
 * @author Jan Braun 2768531
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
		int compare = comapareValue(other);
		if(compare != 0)
			return compare;
		return compareSuit(other);
	}
	
	/**
	 * compares the suit attribute
	 * @param other The object we compare this to.
	 * @return -1, 0 or 1
	 */
	private int compareSuit(Card other) {
		switch(this.suit) {
			case Diamonds:
				return other.suit == Suit.Diamonds ? 0 :
													-1 ;
			case Hearts:
				return other.suit == Suit.Hearts ? 		0 :
					   other.suit == Suit.Diamonds ? 	1 :
						   							   -1 ;
			case Spades:
				return other.suit == Suit.Spades ? 	0 :
					   other.suit == Suit.Clubs ?  -1 :
						   							1 ;
			case Clubs:
				return other.suit == Suit.Clubs ? 0 :
												  1 ;
		} return 0;
	}
	
	/**
	 * compares the value attribute
	 * @param other The object we compare this to.
	 * @return -1, 0 or 1
	 */
	private int comapareValue(Card other) {
		return this.value < other.value ? -1 :
			   this.value > other.value ?  1 :
				   						   0 ;
	}
	
}
