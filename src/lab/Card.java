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
		int compare = compareValue(other);	// vergleiche Werte von this und other
		return compare != 0 ?				// falls this.value != other.value
					compare :				// gibt -1 oder 1 zrurück 
		  compareSuit(other);				// bei gleichem Wert vergleiche Farbe
	}
	
	/**
	 * compares the suit attribute
	 * @param other The object we compare this to.
	 * @return -1, 0 or 1
	 */
	private int compareSuit(Card other) {
		switch(this.suit) {									// Diamonds < Hearts < Spades < Clubs
			case Diamonds:
				return other.suit == Suit.Diamonds ? 0 :	// Diamonds == Diamonds
													-1 ;	// Diamonds < Hearts < Spades < Clubs
			case Hearts:
				return other.suit == Suit.Hearts ? 	 0 :	// Hearts == Hearts
					   other.suit == Suit.Diamonds ? 1 :	// Hearts > Diamonds
						   							-1 ;	// Hearts < Spades < Clubs
			case Spades:
				return other.suit == Suit.Spades ? 	0 : 	// Spades == Spades
					   other.suit == Suit.Clubs ?  -1 : 	// Spades < Clubs
						   							1 ; 	// Spades > Hearts > Diamonds
			case Clubs:
				return other.suit == Suit.Clubs ?   0 : 	// Clubs == Clubs
													1 ; 	// Clubs > Spades > Hearts > Diamonds
		} return 0;
	}
	
	/**
	 * compares the value attribute
	 * @param other The object we compare this to.
	 * @return -1, 0 or 1
	 */
	private int compareValue(Card other) {
		return this.value < other.value ? -1 :
			   this.value > other.value ?  1 :
				   						   0 ;
	}
	
}
