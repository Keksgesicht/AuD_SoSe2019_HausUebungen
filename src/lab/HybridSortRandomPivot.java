package lab;

import frame.SortArray;

/**
 * Aufgabe H1c)

 * 
 * Abgabe von: Jan Braun, Christoph Berst und Emre Berber
 */

/**
 * Use a random pivot within Quick Sort.
 */
public class HybridSortRandomPivot extends HybridSort {
	
	protected int partition(SortArray array, int left, int right) {
		int p = (int)(Math.random() * right-left) + left; 	 
		Card pivot = array.getElementAt(p); // Pivot-Element ist ein zuf‰lliges Element zwischen left und right
		int i = right;
		for(int j = right; j > left; j--) {
			if(array.getElementAt(j).compareTo(pivot) > 0) {	// > oder >= ??
				swap(array, i, j);
				i--;
			}
		} swap(array, i, p); // Pivot an die richtige Stelle bringen		
		return i; // neue Position zur√ºckgeben
	}
	
}
