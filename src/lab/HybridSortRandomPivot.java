package lab;

/**
 * Aufgabe H1c)
 * Use a random pivot within Quick Sort.
 * 
 * @author Emre Berber 2957148
 * @author Christoph Berst 2743394
 * @author Jan Braun 2768531
 */
public class HybridSortRandomPivot extends HybridSort {
	
	@Override
	protected int getPivot(int left, int right) {
		return left + (int) (Math.random() * (right - left + 1));	// wählt eine zufällige Position zwischen den Schranken
	}
	
}
