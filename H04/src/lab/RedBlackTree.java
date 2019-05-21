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
	private static TreeNode _nil; // mh.. When I try to access this. Do I get a NilPointerException?
	
	static {
		_nil = new TreeNode(); // nil doesn't need to exist multiple times
		_nil.left = _nil;
		_nil.right = _nil;	// nil is our sentinel
		_nil.p = _nil;
	}
	
	public RedBlackTree() {
		_root = _nil; // the root is at the beginning a useless node
	}
	
	/**
	 * @return the root node of this tree
	 */
	public TreeNode root() {
		return this._root;
	}
	
	/**
	 * @return the sentinel of this tree
	 */
	public TreeNode nil() {
		return _nil;
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
	
	/**
	 * @return the TreeNode with minimum keyvalue in this subtree
	 */
	public TreeNode minimum() {
		return minimumSubtree(_root);
	}
	
	/**
	 * @param x at this TreeNode begins the search
	 * @return the TreeNode with minimum keyvalue in this subtree
	 */
	public TreeNode minimumSubtree(TreeNode x) {
		return subtreeMinMax(x, false);
	}
	
	/**
	 * @return the TreeNode with maximum keyvalue in this subtree
	 */
	public TreeNode maximum() {
		return maximumSubtree(_root);
	}
	
	/**
	 * @param x at this TreeNode begins the search
	 * @return the TreeNode with maximum keyvalue in this subtree
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
		while(n != _nil)
			n = max ?
				x.right :	// maximum
				x.left;		// minimum
		return n;
	}
	
	/**
	 * cleares the pointers to the child TreeNodes
	 * @param z the TreeNode which cildren get kidnapped
	 */
	private void clearChildren(TreeNode z) {
		z.left = z.right = _nil;	// sets the children to our sentinel
	}

	/**
	 * inserts a new TreeNode to this RedBlackTree
	 * @param newNode the new TreeNode
	 */
	public void insert(TreeNode newNode) {
		clearChildren(newNode);
		TreeNode x = _root; 
		TreeNode px = _nil;
		
		while(x != _nil) {
			px = x;
			x = x.key > newNode.key ? 
				x.left :
				x.right;
		}
		newNode.p = px;
		if(px == _nil)			// if px didn't change the loop haven't run once
			_root = newNode;
		else {
			if(px.key > newNode.key) // hängt den neuen Knoten in der Sortierung passend als Kind an den Baum
				px.left = newNode;
			else
				px.right = newNode;
		}
		newNode.color = TreeNode.NodeColor.RED;
		fixColorsAfterInsertion(newNode);
	}
	
	/**
	 * lackiert den Baum neu an, sodass er nach dem Einfügen immer noch ein Rot-Schwarz-Baum ist 
	 * @param newNode der Problemknoten, welcher eingefügt wurde, mit dem das neu anstriechen beginnt
	 */
	private void fixColorsAfterInsertion(TreeNode newNode) {
		while(newNode.p.color == TreeNode.NodeColor.RED) {
			if(newNode.p == newNode.p.p.left) {
				TreeNode y = newNode.p.p.right;
				if(y.color == TreeNode.NodeColor.RED) {
					newNode.p.color = TreeNode.NodeColor.BLACK;
					y.color = TreeNode.NodeColor.BLACK;
					newNode.p.p.color = TreeNode.NodeColor.RED;
					newNode = newNode.p.p;
				} else {
					if(newNode == newNode.p.right) {
						newNode = newNode.p;
						rotateLeft(newNode);
					}
					newNode.p.color = TreeNode.NodeColor.BLACK;
					newNode.p.p.color = TreeNode.NodeColor.RED;
					rotateRight(newNode.p.p);
				}
			} else {
				TreeNode y = newNode.p.p.left;
				if(y.color == TreeNode.NodeColor.RED) {
					newNode.p.color = TreeNode.NodeColor.BLACK;
					y.color = TreeNode.NodeColor.BLACK;
					newNode.p.p.color = TreeNode.NodeColor.RED;
					newNode = newNode.p.p;
				} else {
					if(newNode == newNode.p.left) {
						newNode = newNode.p;
						rotateRight(newNode);
					}
					newNode.p.color = TreeNode.NodeColor.BLACK;
					newNode.p.p.color = TreeNode.NodeColor.RED;
					rotateLeft(newNode.p.p);
				}
			}
		} _root.color = TreeNode.NodeColor.BLACK;
	}

	/**
	 * bewegt einen auf die Position seines linken Kindes 
	 * @param x der Knoten der bewegt wird
	 */
	private void rotateLeft(TreeNode x) {
		TreeNode y = x.right;	// markiert das andere Kind
		x.right = y.left;		// der linke Teilbaum des rechten Kindes ist nun das rechte Kind
		if(y.left != _nil)
			y.left.p = x;
		y.p = x.p;
		if(x.p == _nil)
			_root = y;
		else {
			if(x == x.p.left)
				x.p.left = y;
			else
				x.p.right = y;
		}
		y.left = x;
		x.p = y;
	}
	
	/**
	 * bewegt einen auf die Position seines rechten Kindes 
	 * @param x der Knoten der bewegt wird
	 */
	private void rotateRight(TreeNode y) {
		TreeNode x = y.left;	// markiert das andere Kind
		y.left = x.right;		// der rechte Teilbaum des linken Kindes ist nun das linke Kind
		if(x.right != _nil)
			x.right.p = y;
		x.p = y.p;
		if(y.p == _nil)
			_root = x;
		else {
			if(y == y.p.right)
				y.p.right = x;
			else
				y.p.left = x;
		}
		x.right = y;
		y.p = x;
	}
	
	/**
	 * Setzt Knoten v an die Position von Knoten u
	 * @param u Knoten u
	 * @param v Knoten v
	 */
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
		if(toDelete.left == _nil)			
			transplant(toDelete, toDelete.right);
		else if(toDelete.right == _nil)
			transplant(toDelete, toDelete.left);
		else {
			TreeNode y = toDelete.right;
			
			while(y.left != _nil)
				y = y.left;
			
			if(y.p != toDelete) {
				transplant(y, y.right);
				y.right = toDelete.right;
				y.right.p = y;
			}
			transplant(toDelete, y);
			y.left = toDelete.left;
			y.left.p = y;
		}
	}
	
}
