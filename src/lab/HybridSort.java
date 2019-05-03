package lab;

/**
 * Aufgabe H1b)
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
	 * @param p untere Schranke
	 * @param r obere Schranke
	 */
	private void sort(SortArray array, int k, int p, int r) {
		int d = p - r;							// Abstand zwischen unterer und oberer Schranke bestimmen
		if(d < 2) return;						// Array kleiner gleich der Größe 1 müssen nicht sortiert werden
		if(d < k) {
			insertionSort(array, p, r);			// echt kleiner k wird InsertionSort eingesetzt
		} else {
			int q = partition(array, p, r);		// größer gleich k wird QuickSort verwendet
			sort(array, k, p, q-1);
			sort(array, k, q+1, r);
		}
	}
	
	/**
	 * wendet den Partition-Algorithmus von QuickSort auf das Array an
	 * @param array zu sortierendes Array
	 * @param p untere Schranke
	 * @param r obere Schranke
	 * @return Postion der Problemzerlegung
	 */
	private int partition(SortArray array, int p, int r) {
		Card x = array.getElementAt(p);						// Pivot-Element ist das erste Element im zu sortierenden Abschnitt
		int i = p-1;										// index i setzen
		for(int j=0; j < r-1; j++) {
			if(array.getElementAt(j).compareTo(x) <= 0) {	// aktuelles kleiner gleich dem Pivot?
				i++;
				swap(array, i, j);
			}
		} swap(array, ++i, p);								// Pivot-Element an die richtige Position tauschen
		return i;											// neuen Index des Pivot-Element zurückgeben
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
		for(int j=p+1; j < r; j++) {
			Card key = array.getElementAt(j);								// j-ter Eintrag im Array ist der Schlüsselwert
			int i = j-1;
			while(i >= p && array.getElementAt(i).compareTo(key) > 0) {
				array.setElementAt(i+1, array.getElementAt(i));				// Schieben des Arrayeintrages
				i--;
			} array.setElementAt(i+1, key);									// Key an der richtigen Stelle einfügen
		}
	}
	
}
