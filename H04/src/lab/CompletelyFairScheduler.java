package lab;

import frame.TreeNode;
import frame.Process;

/**
 * Aufgabe H1b)
 * 
 * @author Emre Berber 2957148
 * @author Christoph Berst 2743394
 * @author Jan Braun 2768531
 */
public class CompletelyFairScheduler {
	
	private RedBlackTree tree;
	private int windowMaxLength;
	
	/**
	 * Creating a new CompletelyFairScheduler
	 * @param windowMaxLength is the maximal length of one execution window
	 */
	public CompletelyFairScheduler(int windowMaxLength) {
		tree = new RedBlackTree();
		this.windowMaxLength = windowMaxLength;
	}
	
	/**
	 * Distribute execution windows to the processes.
	 * @param windows The number of execution windows to distribute
	 */
	public void run(int windows) {
		for(int i = 0; i < windows; i++) {		//Damit windows(int) viele Fenster verteilt werden
			TreeNode prcsMin = tree.minimum();	//Suche des Prozesses mit geringster Ausführungszeit
			if(prcsMin == tree.nil()) break;	//Verwerfen der restlichen Fenster, wenn RBTree leer/abgearbeitet ist;
			prcsMin.value.run(windowMaxLength);	//Ausführen des Prozesses für windowMaxLength(int) des Schedulers
			tree.delete(prcsMin);				//Entfernen des Knotens aus dem RBTree
			if(!prcsMin.value.finished()) {		//Wenn der Prozess noch nicht fertig ist
				add(prcsMin.value);				//Füge den Prozess wieder in den RBTree ein
			}
		}
	}
	
	/**
	 * Add a process to the Scheduler.
	 */
	public void add(Process process) {
		TreeNode prcs = new TreeNode();						//Erstellen eines neuen Knotens
		prcs.key = process.executionTime();					
		prcs.value = process;								//Abspeichern des Prozesses in value der treeNode
		if(tree.search(prcs.key) != tree.nil()) {			//Wenn der Baum nicht leer ist
			while(tree.search(prcs.key).key == prcs.key) {	//Solange es einen Knoten mit gleichem key gint
				prcs.key++;									// erhöhe key um 1
			}
		}
		tree.insert(prcs);									//Einfügen des Knotens
	}
	
	// DO NOT MODIFY
	// used for the tests
	public RedBlackTree getTree() {
		return tree;
	}

}
