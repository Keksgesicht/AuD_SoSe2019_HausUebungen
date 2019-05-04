package lab;

import frame.SortArray;

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
	
	protected int partition(SortArray array, int left, int right) {
		int p = left + (int)(Math.random() * (right-left+1));
		Card pivot = array.getElementAt(p);						// Pivot-Element ist ein zuf�lliges Element zwischen left und right
		swap(array,p,left);
		int i = right;
		for(int j = right; j > left; j--) {
			if(array.getElementAt(j).compareTo(pivot) > 0) {	// > oder >= ??
				swap(array, i, j);
				i--;
			}
		} swap(array, i, left);									// Pivot an die richtige Stelle bringen
		return i;												// neue Position zurückgeben
	}
	
}
