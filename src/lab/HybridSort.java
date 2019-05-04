package lab;

/**
 * Aufgabe H1b)
 * 
 * @author Emre Berber
 * @author Christoph Berst
 * @author Jan Braun
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
		sort(array, k, 0, array.getNumberOfItems() - 1);
	}
	
	/**
	 * rekursives HybridSort
	 * @param array zu sortierendes Array
	 * @param k komplexity switch
	 * @param left untere Schranke
	 * @param right obere Schranke
	 */
	protected void sort(SortArray array, int k, int left, int right) {
		int d = right - left;						// Abstand zwischen unterer und oberer Schranke bestimmen
		if(d < 1) return;							// Array kleiner gleich der Größe 1 müssen nicht sortiert werden
		if(d < k) {
			insertionSort(array, left, right);		// echt kleiner k wird InsertionSort eingesetzt
		} else {
			int q = partition(array, left, right);	// größer gleich k wird QuickSort verwendet
			sort(array, k, left, q-1);
			sort(array, k, q+1, right);
		}
	}
	
	/**
	 * wendet den Partition-Algorithmus von QuickSort auf das Array an
	 * @param array zu sortierendes Array
	 * @param left untere Schranke
	 * @param right obere Schranke
	 * @return Position der Problemzerlegung
	 */
	protected int partition(SortArray array, int left, int right) {
		Card pivot = array.getElementAt(left);					// Pivot-Element ist das erste Element im zu sortierenden Abschnitt
		int i = right;
		for(int j = right; j > left; j--) {
			if(array.getElementAt(j).compareTo(pivot) > 0) {	// > oder >= ??
				swap(array, i, j);
				i--;
			}
		} swap(array, i, left);									// Pivot an die richtige Stelle bringen
		return i;												// neue Position zurückgeben
	}
	
	/**
	 * Vertauscht zwei Elemente im Array
	 * @param array zu sortierendes Array
	 * @param m Position des ersten Element
	 * @param n Position des zweiten Elements
	 */
	protected void swap(SortArray array, int m, int n) {
		Card cache = array.getElementAt(m);				// Zwischenspeicher für Element i erzeugen
		array.setElementAt(m, array.getElementAt(n));	// i mit j überschreiben
		array.setElementAt(n, cache);					// j auf den Wert des Zwischenspeichers setzen
	}
	
	/**
	 * sortiert Array mit Insertion-Sort zwischen der unteren und oberen Schranke
	 * @param array zu sortierendes Array
	 * @param left untere Schranke
	 * @param right obere Schranke
	 */
	protected void insertionSort(SortArray array, int left, int right) {
		for(int j=left+1; j <= right; j++) {
			Card key = array.getElementAt(j);								// j-ter Eintrag im Array ist der Schlüsselwert
			int i = j-1;
			while(i >= left && array.getElementAt(i).compareTo(key) > 0) {
				array.setElementAt(i+1, array.getElementAt(i));				// Schieben des Arrayeintrages
				i--;
			} array.setElementAt(i+1, key);									// Key an der richtigen Stelle einfügen
		}
	}
	
}
