package lab;

/**
 * Aufgabe H1b)
 * @author Emre Berber
 * @author Christoph Berst
 * @author Jan Braun
 *
 * Abgabe von: <name>, <name> und <name>
 */

import frame.SortArray;

public class HybridSort {
	
	/**
	 * Sort the given array using a hybrid method of Quick Sort and Insertion Sort.
	 * 
	 * @param array The array to sort.
	 * @param k Parameter k when we switch from Quick Sort to Insertion Sort: If the size of the subset which should be sorted is less than k, use Insertion Sort,
	 * 			otherwise keep on using Quick Sort.
	 */
	public void sort(SortArray array, int k) {
		assert(k>=0);
		
		// Sorting
		
		return;
	}
	
	/**
	 * wendet den Partition-Algorithmus von QuickSort auf das Array an
	 * @param array zu sortierendes Array
	 * @param p untere Schranke
	 * @param r obere Schranke
	 * @return Postion der Problemzerlegung
	 */
	private int partition(SortArray array, int p, int r) {
		Card x = array.getElementAt(p);
		int i = p-1;
		for(int j=0; j < r-1; j++) {
			if(array.getElementAt(j).compareTo(x) <= 0) {
				i++;
				swap(array, i, j);
			}
		} swap(array, ++i, p);
		return i;
	}
	
	/**
	 * Vertauscht zwei Elemente im Array
	 * @param array zu sortierendes Array
	 * @param i Position des ersten Element
	 * @param j Position des zweiten Elements
	 */
	private void swap(SortArray array, int i, int j) {
		Card cache = array.getElementAt(i);				// Zwischenspeicher für Element i erzeugen
		array.setElementAt(i, array.getElementAt(j));	// i mit j überschreiben
		array.setElementAt(j, cache);					// j auf den Wert des Zwischenspeichers setzen
	}
	
	/**
	 * sortiert Array mit Insertion-Sort zwischen der unteren und oberen Schranke
	 * @param array zu sortierendes Array
	 * @param p untere Schranke
	 * @param r obere Schranke
	 */
	private void insertionSort(SortArray array, int p, int r) {
		for(int j=p+1; j < r-1; j++) {
			Card key = array.getElementAt(j);
			int i = j-1;
			while(i >= p && array.getElementAt(i).compareTo(key) > 0) {
				array.setElementAt(i+1, array.getElementAt(i));
				i--;
			} array.setElementAt(i+1, key);
		}
	}
	
}
