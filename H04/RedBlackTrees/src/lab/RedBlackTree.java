package lab;

/**
 * Aufgabe H1a)
 * 
 * Abgabe von: <name>, <name> und <name>
 */

import frame.TreeNode;

/**
 * An implementation of a Black-Red-Tree
 */
public class RedBlackTree {
	
	private TreeNode _root;
	private TreeNode _nil;
	
	public RedBlackTree() {
		_nil = new TreeNode();
		_root = _nil;
	}
	
	public TreeNode root() {
		return this._root;
	}
	
	public TreeNode nil() {
		return this._nil;
	}
	
	public TreeNode search(int key) {
		// TODO: Implement
	}
	
	public TreeNode minimum() {
		return minimumSubtree(_root);
	}
	
	public TreeNode minimumSubtree(TreeNode x) {
		// TODO: Implement
	}
	
	public TreeNode maximum() {
		return maximumSubtree(_root);
	}
	
	public TreeNode maximumSubtree(TreeNode x) {
		// TODO: Implement
	}

	public void insert(TreeNode newNode) {
		// TODO: Implement
	}
	
	public void delete(TreeNode toDelete) {
		// TODO: Implement
	}
}
