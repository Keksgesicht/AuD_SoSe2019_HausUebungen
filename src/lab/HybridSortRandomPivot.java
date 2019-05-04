package lab;

/**
 * Aufgabe H1c)
 * 
 * @author Emre Berber
 * @author Christoph Berst
 * @author Jan Braun
 */

/**
 * Use a random pivot within Quick Sort.
 */
public class HybridSortRandomPivot extends HybridSort {
	
	@Override
	protected int getPivot(int left, int right) {
		return left + (int) (Math.random() * (right-left+1));	// wählt eine zufällige Position zwischen den Schranken
	}
	
}
