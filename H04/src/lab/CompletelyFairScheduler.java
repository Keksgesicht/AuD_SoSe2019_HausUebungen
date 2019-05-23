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
		for(int i = 0; i < windows; i++) {
			TreeNode prcsMin = tree.minimum();
			if(prcsMin == tree.nil()) break;
			prcsMin.value.run(windowMaxLength);
			tree.delete(prcsMin);
			if(!prcsMin.value.finished()) {
				add(prcsMin.value);
			}
		}
	}
	
	/**
	 * Add a process to the Scheduler.
	 */
	public void add(Process process) {
		TreeNode prcs = new TreeNode();
		prcs.key = process.executionTime();
		prcs.value = process;
		if(tree.search(prcs.key) != tree.nil()) {
			while(tree.search(prcs.key).key == prcs.key) {
				prcs.key++;
			}
		}
		tree.insert(prcs);
	}
	
	// DO NOT MODIFY
	// used for the tests
	public RedBlackTree getTree() {
		return tree;
	}

}
