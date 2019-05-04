package lab;

/**
 * Aufgabe H1
 * 
 * @author Emre Berber 2957148
 * @author Christoph Berst
 * @author Jan Braun 2768531
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import frame.SortArray;
import lab.Card.Suit;

/**
 * Use this class to implement your own tests.
 */
class YourTests {

	@Test
	void test() {
		ArrayList<Card> alc = new ArrayList<Card>();
		for(int i=0; i<10; i++) {
			alc.add(new Card((int) (Math.random() * 100), Suit.Diamonds));
		}
		System.out.println(alc);
		SortArray sa = new SortArray(alc);
		HybridSort sortAlgo = new HybridSort();
		sortAlgo.sort(sa, 0);
		System.out.println(alc);
	}

	@Test
	void testRandom() {
		ArrayList<Card> alc = new ArrayList<Card>();
		for(int i=0; i<10; i++) {
			alc.add(new Card((int) (Math.random() * 100), Suit.Diamonds));
		}
		System.out.println(alc);
		SortArray sa = new SortArray(alc);
		HybridSortRandomPivot sortAlgo = new HybridSortRandomPivot();
		sortAlgo.sort(sa, 0);
		System.out.println(alc);
	}
}
