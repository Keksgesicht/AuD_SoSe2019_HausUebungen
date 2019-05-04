package lab;

/**
 * Aufgabe H1c)
 * 
 * @author Emre Berber 2957148
 * @author Christoph Berst 2743394
 * @author Jan Braun 2768531
 */

/**
 * Use a random pivot within Quick Sort.
 */
public class HybridSortRandomPivot extends HybridSort {
	
	@Override
	protected int getPivot(int left, int right) {
		return left + (int) (Math.random() * (right - left + 1));	// wählt eine zufällige Position zwischen den Schranken
	}
	
}
