package lab;

import frame.TreeNode;

/**
 * Aufgabe H1a)
 * An implementation of a Black-Red-Tree
 * 
 * @author Emre Berber 2957148
 * @author Christoph Berst 2743394
 * @author Jan Braun 2768531
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
	
	/**
	 * @param key the key of the TreeNode which should be found
	 * @return the nil-Node when there is no Node with that key <br>
	 * 		   or a TreeNode with that key
	 */
	public TreeNode search(int key) {
		TreeNode s = _root;
		while(s != _nil) {
			if(s.key == key)
				break;
			s = key < s.key ? 
				s.left : 
				s.right; 
		} return s;
	}
	
	public TreeNode minimum() {
		return minimumSubtree(_root);
	}
	
	/**
	 * @param x at this TreeNode begins the search
	 * @return the TreeNode with minimum keyvalue
	 */
	public TreeNode minimumSubtree(TreeNode x) {
		return subtreeMinMax(x, false);
	}
	
	public TreeNode maximum() {
		return maximumSubtree(_root);
	}
	
	/**
	 * @param x at this TreeNode begins the search
	 * @return the TreeNode with maximum keyvalue
	 */
	public TreeNode maximumSubtree(TreeNode x) {
		return subtreeMinMax(x, true);
	}
	
	/**
	 * @param x at this TreeNode begins the search
	 * @param max whether it should search the maximum (true) or minimum (false)
	 * @return the TreeNode with maximum or minimum keyvalue
	 */
	private TreeNode subtreeMinMax(TreeNode x, boolean max) {
		TreeNode n = x;
		while(n != null)
			n = max ? 
				x.right : 
				x.left;
		return n;
	}
	
	/**
	 * cleares the pointers to the child TreeNodes
	 * @param z the TreeNode which cildren get kidnapped
	 */
	private void clearChildren(TreeNode z) {
		z.left = z.right = _nil;
	}

	/**
	 * inserts a new TreeNode to this RedBlackTree
	 * @param newNode the new TreeNode
	 */
	public void insert(TreeNode newNode) {
		clearChildren(newNode);
		// TODO
	}
	
	private void transplant(TreeNode u, TreeNode v) {
		if(u.p == _nil)
			_root = v;
		else {
			if(u == u.p.left)
				u.p.left = v;
			else
				u.p.right = v;
		}
		if(v != _nil)
			v.p = u.p;
	}
	
	/**
	 * removes a TreeNode from this RedBlackTree
	 * @param toDelete the TreeNode that gets removed
	 */
	public void delete(TreeNode toDelete) {
		// TODO
	}
}
