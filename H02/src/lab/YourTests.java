package lab;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Random;

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
	Random rnd = new Random(System.currentTimeMillis());
	
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
	private boolean isSorted(SortArray array) {
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
	void chooseSortAlgo(SortArray array, int t) {
		if(t<2) chooseSortAlgo(array, t, new HybridSort());			// 0 und 1
		else chooseSortAlgo(array, t, new HybridSortRandomPivot());	// 2 und 3
	}
	
	/**
	 * @param array zu sortierendes Array
	 * @param t entscheidet welche Hybridstufe verwendet werden soll (zwischen 0 und 3)
	 * @param sortAlgo das Sortierverfahren
	 */
	void chooseSortAlgo(SortArray array, int t, HybridSort sortAlgo) {
		if(t % 2 == 0) sortAlgo.sort(array, 0);		// 0 oder 2
		else sortAlgo.sort(array, 1);				// 1 oder 3
	}

	/**
	 * erstellt und sammelt Messdaten für und über das Sortierverfahren
	 * @param t entscheidet welches Sortierverfahren mit welcher Hybridstufe verwendet werden soll (zwischen 0 und 3)
	 */
	void sortTestUnit(int t) {
		ArrayList<Card> alc;
		SortArray sa;
		clearData();
		
		for(int c=0; c < 100; c++) {
			// create test data
			alc = new ArrayList<Card>();
			for(int i=0; i<100000; i++)
				alc.add(new Card(rnd.nextInt(100), Suit.values()[rnd.nextInt(4)]));
			sa = new SortArray(alc);
			
			// sort
			chooseSortAlgo(sa, t);
			
			// get data
			int ro = sa.getReadingOperations();
			int wo = sa.getWritingOperations();
			
			// minimum
			readOPS[0] = readOPS[0] == 0 ? ro : readOPS[0] > ro ? ro : readOPS[0];
			writeOPS[0] = writeOPS[0] == 0 ? wo : writeOPS[0] > wo ? wo : writeOPS[0];
			
			// average
			readOPS[1] += ro;
			writeOPS[1] += wo;
			
			// maximum
			readOPS[2] = readOPS[2] == 0 ? ro : readOPS[2] < ro ? ro : readOPS[2];
			writeOPS[2] = writeOPS[2] == 0 ? wo : writeOPS[2] < wo ? wo : writeOPS[2];
			
			// check
			assertTrue(isSorted(sa));
		}
		printData();
	}
	
	@Test
	void tester() {
		System.out.println("pivot=left | k=0");		sortTestUnit(0);
		System.out.println("pivot=left | k=10");	sortTestUnit(1);
		System.out.println("pivot=random | k=0");	sortTestUnit(2);
		System.out.println("pivot=random | k=10");	sortTestUnit(3);
	}
	
}
