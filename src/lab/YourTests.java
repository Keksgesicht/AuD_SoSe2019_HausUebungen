package lab;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import frame.SortArray;
import lab.Card.Suit;

/**
 * Aufgabe H1
 * Use this class to implement your own tests.
 * 
 * @author Emre Berber 2957148
 * @author Christoph Berst 2743394
 * @author Jan Braun 2768531
 */
class YourTests {
	
	long[] readOPS;
	long[] writeOPS;
	
	/**
	 * @BeforeEach
	 */
	void clearData() {
		readOPS = new long[3];
		writeOPS = new long[3];
	}
	
	/**
	 * @AfterEach
	 */
	void printData() {
		System.out.println("ReadOPS: minimal " + readOPS[0] + ", avarage " + readOPS[1] / 100 + ", maximal " + readOPS[2]);
		System.out.println("writeOPS: minimal " + writeOPS[0] + ", avarage " + writeOPS[1] / 100 + ", maximal " + writeOPS[2]);
		System.out.println();
		Runtime.getRuntime().gc();
	}
	
	/**
	 * @param array zu überprüfendes Array
	 * @return ob das Array sortiert ist
	 */
	private boolean sorted(SortArray array) {
		Card last = null;
		Card current = null;
		for(int i=0; i < array.getNumberOfItems(); i++) {
			if(i==0) {
				last = array.getElementAt(i);
				continue;
			}
			current = array.getElementAt(i);
			if(last.compareTo(current) > 0)
				return false;
			last = current;
		} 
		return true;
	}
	
	/**
	 * @param array zu sortierendes Array
	 * @param t entscheidet welches Sortierverfahren verwendet werden soll (zwischen 0 und 3)
	 */
	void sortAlgo(SortArray array, int t) {
		if(t<2) sortAlgo(array, t, new HybridSort());			// 0 und 1
		else sortAlgo(array, t, new HybridSortRandomPivot());	// 3 und 4
	}
	
	/**
	 * 
	 * @param array zu sortierendes Array
	 * @param t entscheidet welche Hybridstufe verwendet werden soll (zwischen 0 und 3)
	 * @param sortAlgo das Sortierverfahren
	 */
	void sortAlgo(SortArray array, int t, HybridSort sortAlgo) {
		if(t % 2 == 0) sortAlgo.sort(array, 0);		// 0 oder 2
		else sortAlgo.sort(array, 1);				// 1 oder 3
	}

	/**
	 * Erstellt und sammelt Messdaten für und über das Sortierverfahren
	 * @param t entscheidet welches Sortierverfahren mit welcher Hybridstufe verwendet werden soll (zwischen 0 und 3)
	 */
	void sortUnit(int t) {
		ArrayList<Card> alc;
		SortArray sa;
		clearData();
		
		for(int c=0; c < 100; c++) {
			alc = new ArrayList<Card>();
			for(int i=0; i<100000; i++) {
				alc.add(new Card((int) (Math.random() * 100), Suit.values()[(int) Math.random() * 4]));
			} 
			sa = new SortArray(alc);
			sortAlgo(sa, t);
			
			int ro = sa.getReadingOperations();
			int wo = sa.getWritingOperations();
			
			readOPS[0] = readOPS[0] == 0 ? ro : readOPS[0] > ro ? ro : readOPS[0];
			writeOPS[0] = writeOPS[0] == 0 ? wo : writeOPS[0] > wo ? wo : writeOPS[0];
			
			readOPS[1] += sa.getReadingOperations();
			writeOPS[1] += sa.getWritingOperations();
			
			readOPS[2] = readOPS[2] == 0 ? ro : readOPS[2] < ro ? ro : readOPS[2];
			writeOPS[2] = writeOPS[2] == 0 ? wo : writeOPS[2] < wo ? wo : writeOPS[2];
			
			assertTrue(sorted(sa));
		}
		printData();
	}
	
	@Test
	void tester() {
		System.out.println("pivot=left | k=0");		sortUnit(0);
		System.out.println("pivot=left | k=10");	sortUnit(1);
		System.out.println("pivot=random | k=0");	sortUnit(2);
		System.out.println("pivot=random | k=10");	sortUnit(3);
	}
	
}
